package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.VacationHoldScreen;

public class DisplaySetpointsInCard extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplaySetpointsInCard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user should be displayed with Updated setpoint in (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		CHILUtil chUtil;
		try {
			chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID = statInfo.getDeviceID();
			if (chUtil.isConnected()) {
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
			// flag = true;
			switch (exampleData.get(0).toUpperCase()) {
			case "PRIMARY CARD": {
				try {
					flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					if (Integer.parseInt(vhs.getPrimaryCardValue()) == CHILUtil.setPointInVacationCard) {
						Keyword.ReportStep_Pass(testCase, "Setpoint in vacation and primary card are same");
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Setpoint in vacation and primary card are not same");
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
							if (Integer.parseInt(vhs.getHeatSetPointValue()) == CHILUtil.setPointInPrimaryCard) {
								Keyword.ReportStep_Pass(testCase, "Setpoint in vacation and primary card are same");
							} else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Setpoint in vacation and primary card are not same");
							}
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
			case "VACATION CARD TO MAXIMUM": {
				try {
					VacationHoldScreen VHS = new VacationHoldScreen(testCase);
						String status = VHS.getVacationSetPointValue();
						HashMap<String, String> setPoints = statInfo.getDeviceMaxMinSetPoints();
						String maxSetPointh = setPoints.get("MaxHeat");
						String maxSetPointc = setPoints.get("MaxCool");
						if (statInfo.getThermostatUnits().equals("Celsius")) {
						if (status.contains("Cool to "+ maxSetPointh+ "°")){
							Keyword.ReportStep_Pass(testCase, "Cool Setpoint in vacation Card is Maximum");
						}
					 else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Cool Setpoint in vacation Card is Not Maximum " + status);
							}
						if (status.contains("Heat to "+ maxSetPointc+ "°")){
							Keyword.ReportStep_Pass(testCase, "Heat Setpoint in vacation Card is Maximum");
						}
					 else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Heat Setpoint in vacation Card is Not Maximum " + status);
							}
				}
						else{
							if (status.contains("Cool to "+ maxSetPointh.replace(".0", "")+ "°")){
								Keyword.ReportStep_Pass(testCase, "Cool Setpoint in vacation Card is Maximum");
							}
						 else {
									flag = false;
									Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
											"Cool Setpoint in vacation Card is Not Maximum " + status);
								}
							if (status.contains("Heat to "+ maxSetPointc.replace(".0", "") + "°")){
								Keyword.ReportStep_Pass(testCase, "Heat Setpoint in vacation Card is Maximum");
							}
						 else {
									flag = false;
									Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
											"Heat Setpoint in vacation Card is Not Maximum " + status);
								}
						}
				}
				catch (Exception e) {
					flag = false;
					e.printStackTrace();
				}
				break;
			}
			case "VACATION CARD TO MINIMUM": {
				try {
					VacationHoldScreen VHS = new VacationHoldScreen(testCase);
						String status = VHS.getVacationSetPointValue();
						HashMap<String, String> setPoints = statInfo.getDeviceMaxMinSetPoints();
						String minSetPointh = setPoints.get("MinHeat");
						String minSetPointc = setPoints.get("MinCool");
						if (status.contains("Cool to "+ minSetPointh+ "°")){
							Keyword.ReportStep_Pass(testCase, "Cool Setpoint in vacation Card is Minimum");
						}
					 else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Cool Setpoint in vacation Card is Not Maximum");
							}
						if (status.contains("Heat to "+ minSetPointc+ "°")){
							Keyword.ReportStep_Pass(testCase, "Heat Setpoint in vacation Card is Minimum");
						}
					 else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Heat Setpoint in vacation Card is Not Maximum" + status);
							}
				}
				catch (Exception e) {
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
