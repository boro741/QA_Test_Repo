package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;

public class ChangeSetPointFromDashboardAndPrimaryCard extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;

	public ChangeSetPointFromDashboardAndPrimaryCard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		try {
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			DeviceInformation di = new DeviceInformation(testCase, inputs);
			String deviceID = di.getDeviceID();
			if (exampleData.get(0).toUpperCase().equals("INCREASE")) {
				if (exampleData.get(1).toUpperCase().equals("ABOVE")) {
					if (chUtil.getConnection()) {
						int result = chUtil.setHeatSetPoint(locationID, deviceID, 50);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Set Heat Set Point Through CHIL  : Successfully set Thermostat Set point to 50 using CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Heat Set Point Through CHIL : Failed to set Thermostat Set point to 50 using CHIL");
						}
					}
					if (chUtil.getConnection()) {
						int result = chUtil.setCoolSetPoint(locationID, deviceID, 50);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Set Cool Set Point Through CHIL : Successfully set Thermostat Set point to 50 using CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Cool Set Point Through CHIL : Failed to set Thermostat Set point to 50 using CHIL");
						}
					}
				}
			} else if (exampleData.get(0).toUpperCase().equals("DECREASE")) {
				if (exampleData.get(1).toUpperCase().equals("BELOW")) {
					if (chUtil.getConnection()) {
						int result = chUtil.setHeatSetPoint(locationID, deviceID, 90);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Set Heat Set Point Through CHIL  : Successfully set Thermostat Set point to 90 using CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Heat Set Point Through CHIL : Failed to set Thermostat Set point to 90 using CHIL");
						}
					}
					if (chUtil.getConnection()) {
						int result = chUtil.setCoolSetPoint(locationID, deviceID, 90);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Set Cool Set Point Through CHIL : Successfully set Thermostat Set point to 90 using CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Cool Set Point Through CHIL : Failed to set Thermostat Set point to 90 using CHIL");
						}
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"(.*)\" the setpoint value \"(.*)\" room temperature in \"(.*)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			Dashboard dsp = new Dashboard(testCase);
			PrimaryCard PC = new PrimaryCard(testCase);
			DeviceInformation di = new DeviceInformation(testCase, inputs);
			String IndoorTemperature1 = di.getIndoorTemperature();
			IndoorTemperature1 = IndoorTemperature1.replace(".", "_");
			String[] temp = IndoorTemperature1.split("_");
			int IndoorTemperature = Integer.valueOf(temp[0]);

			if (exampleData.get(0).toUpperCase().equals("INCREASE")) {
				if (exampleData.get(1).toUpperCase().equals("ABOVE")) {
					if (exampleData.get(2).toUpperCase().equals("DASHBOARD")) {
						flag = flag & DashboardUtils.waitForOptionOnScreen(testCase,
								"HEATINGORCOOLING TEXT TO DISAPPEAR", 2);
						double CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						while (CurrentSetPoint <= IndoorTemperature+2) {
							flag = flag & dsp.clickOnUpStepper();
							CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						}
						flag = flag & dsp.clickOnUpStepper();
					}
				 else if (exampleData.get(2).toUpperCase().equals("PRIMARY CARD")) {
					double CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					while (CurrentSetPoint <= IndoorTemperature+2) {
						flag = flag & PC.clickOnUpStepper();
						CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					}
					flag = flag & PC.clickOnUpStepper();
				}
				}
			} else if (exampleData.get(0).toUpperCase().equals("DECREASE")) {
				if (exampleData.get(1).toUpperCase().equals("BELOW")) {
					if (exampleData.get(2).toUpperCase().equals("DASHBOARD")) {
						flag = flag & DashboardUtils.waitForOptionOnScreen(testCase,"HEATINGORCOOLING TEXT TO DISAPPEAR", 2);
						double CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						while (IndoorTemperature <= CurrentSetPoint+2) {
							flag = flag & dsp.clickOnDownStepper();
							CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						}
						flag = flag & dsp.clickOnDownStepper();

					}
					else if (exampleData.get(2).toUpperCase().equals("PRIMARY CARD")) {
						double CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
						while (IndoorTemperature <= CurrentSetPoint+2) {
							flag = flag & PC.clickOnDownStepper();
							CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
						}
						flag = flag & PC.clickOnDownStepper();
					}
				}
			}else if (exampleData.get(0).toUpperCase().equals("DECREASE WITHOUT WAIT")) {
				if (exampleData.get(1).toUpperCase().equals("BELOW")) {
					if (exampleData.get(2).toUpperCase().equals("DASHBOARD")) {
						double CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						while (IndoorTemperature <= CurrentSetPoint+2) {
							flag = flag & dsp.clickOnDownStepper();
							CurrentSetPoint = Dashboard.getCurrentSetPointInDashboard(testCase);
						}
						flag = flag & dsp.clickOnDownStepper();

					}
					else if (exampleData.get(2).toUpperCase().equals("PRIMARY CARD")) {
						double CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
						while (IndoorTemperature <= CurrentSetPoint+2) {
							flag = flag & PC.clickOnDownStepper();
							CurrentSetPoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
						}
						flag = flag & PC.clickOnDownStepper();
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
