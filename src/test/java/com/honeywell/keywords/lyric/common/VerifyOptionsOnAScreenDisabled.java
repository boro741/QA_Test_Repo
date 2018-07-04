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
import com.honeywell.screens.SensorSettingScreen;

public class VerifyOptionsOnAScreenDisabled extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenDisabled(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
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
	@KeywordStep(gherkins = "^the following (.*) options should be disabled:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "SECURITY SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				try {
					if (fieldTobeVerified.equalsIgnoreCase("Geofencing")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.GEOFENCING)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Geofencing Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Geofencing Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Entry/Exit Delay")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry/Exit Delay Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Entry/Exit Delay Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Volume")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.VOLUME)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Volume Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Volume Delay Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Reset Wi-Fi")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.RESETWIFI)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Reset Wi-Fi Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Reset Wi-Fi Option is disabled");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "VIDEO SETTINGS": {
			try {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				for (int i = 0; i < data.getSize(); i++) {
					String fieldTobeVerified = data.getData(i, "Options");
					if (fieldTobeVerified.equalsIgnoreCase("Motion Detection")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.MOTIONDETECTION)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Detection Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Motion Detection Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Night Vision")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.NIGHTVISION)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Night Vision Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Video Quality")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.VIDEOQUALITY)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Video Quality Option is disabled");
						}
					}
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}
			break;
		}
		case "KEYFOB SETTINGS":
		case "SENSOR SETTINGS":{
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("Name field")) {
					if(expectedScreen.get(0).equalsIgnoreCase("KEYFOB SETTINGS")){
						if (bs.isKeyfobNameElementEnabled()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Name field is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Name field is disabled");
						}
					}else{

						if (bs.isNameElementEnabled()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Name field is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Name field is disabled");
						}
					}
				}else if(fieldTobeVerified.equalsIgnoreCase("Cover Tampered")) {
					SensorSettingScreen sensor  = new SensorSettingScreen(testCase);
					if (sensor.isSensorTamperedScreenDisplayed()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Delete should not clickable in away/night mode");
					}
					else {
						Keyword.ReportStep_Pass(testCase, "Cover Tampered field is disabled");
					}
				}
				else if(fieldTobeVerified.equalsIgnoreCase("Signal strength and test")) {
					if(bs.isSensorSignalStrengthAndTestOptionEnabled()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Signal strength and test is enabled");
					}
					else {
						Keyword.ReportStep_Pass(testCase, "Signal strength and test field is disabled");
					}
				}
				else if(fieldTobeVerified.equalsIgnoreCase("Delete")) {


					if(expectedScreen.get(0).equalsIgnoreCase("KEYFOB SETTINGS")){
						if (bs.isKeyfobDeleteElementClickable()){
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Delete should not clickable in away/night mode");
						}
						else {
							Keyword.ReportStep_Pass(testCase, "Delete field is disabled");
						}
					}else{
						if (bs.isDeleteElementClickable()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Delete should not clickable in away/night mode");
						}
						else {
							Keyword.ReportStep_Pass(testCase, "Delete field is disabled");
						}
					}
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
