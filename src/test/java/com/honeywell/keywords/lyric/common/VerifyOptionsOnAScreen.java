package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyOptionsOnAScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the following (.*) options:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "SECURITY SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the DAS Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Settings: '" + fieldTobeVerified + "' is not present on the DAS Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "ENTRY-EXIT DELAY": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				try {
					if (bs.verifyParticularEntryExitDelayOptionVisible(data.getData(i, "Delays"))) {
						Keyword.ReportStep_Pass(testCase, "Option: '" + data.getData(i, "Delays")
								+ "' is present on the Entry/Exit Delay screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option: '"
								+ data.getData(i, "Delays") + "' is not present on the Entry/Exit Delay screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "BASE STATION CONFIGURATION": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifyDASNameOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' DAS Option is not displayed on the Base Station Configurations Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Battery")) {
					if (bs.verifyBatteryOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Battery' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery' DAS Option is not displayed on the Base Station Configurations Screen");
					}

					if (bs.verifyBatteryStatusTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Battery Status' is correctly displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery Status' is not correctly displayed");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Base Station Configurations Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "PANEL MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if(bs.verifyModelDetailsOnModelAndFirmwareDetailsPage())
					{
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					}
					else
					{
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if(bs.verifyFirmwareDetailsOnModelAndFirmwareDetailsPage())
					{
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					}
					else
					{
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
