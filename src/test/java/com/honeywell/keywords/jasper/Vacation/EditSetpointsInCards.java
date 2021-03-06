package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.VacationSettingsUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.VacationHoldScreen;

public class EditSetpointsInCards extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EditSetpointsInCards(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user edits set point from (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		CHILUtil chUtil;
		try {
			chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID = statInfo.getDeviceID();
			if (chUtil.getConnection()) {
				if (chUtil.changeSystemMode(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID,
						"Heat") == 200) {
					Keyword.ReportStep_Pass(testCase,
							"Change System Mode Using CHIL : Successfully changed system mode to Heat through CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Change System Mode Using CHIL : Failed to change system mode to Heat through CHIL");
				}
			}
			switch (exampleData.get(0).toUpperCase()) {
			case "PRIMARY CARD": {
				try {
					flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					flag &= vhs.editSetPointUpInPrimaryCard();
					VacationSettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					if(statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")){

					}else{
						try{
							CHILUtil.setPointInPrimaryCard = Integer.valueOf(vhs.getPrimaryCardValue());
						}catch(Exception e){
							Keyword.ReportStep_Pass(testCase,  "Number format exception");
						}
					}
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isBackButtonVisible()) {
						flag = flag & bs.clickOnBackButton();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Back button");
					}

				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
				break;
			}
			case "VACATION CARD": {
				try {
					Dashboard dScreen = new Dashboard(testCase);
					if (dScreen.clickOnGlobalDrawerButton()) {
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.VACATION)) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Activity history menu from Global drawer");
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not navigate to Global drawer");
					}
					if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != null) {
						if (vhs.isStatInVacationScreenVisible(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))) {
							flag = flag
									&& vhs.clickOnStatInVacationScreen(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
							flag = flag && vhs.editHeatSetPointUp();
							VacationSettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
							BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
							if (bs.isBackButtonVisible()) {
								flag = flag & bs.clickOnBackButton();
								flag = flag & bs.clickOnBackButton();
								flag = flag & bs.clickOnBackButton();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not find Back button");
							}

							statInfo = new DeviceInformation(testCase, inputs);
							CHILUtil.setPointInVacationCard = (int) Double
									.parseDouble(statInfo.getVacationHeatSetPoint());
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Stat is not present in vacation screen");
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device is not present");
					}
				} catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
				break;
			}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed in Setting Setpoint");

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}

}
