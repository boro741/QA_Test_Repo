package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ZwaveScreen;

public class VerifyScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "Z-WAVE CONTROLLER INFO": {
				DASZwaveUtils.isControllerDetailsDisplayed(testCase);
				break;
			}
			case "REPLACE MODE ACTIVE": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isReplaceScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "Z-WAVE DEVICES": {
				if (DASZwaveUtils.verifyZWaveDevicesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "Z-WAVE UTILITIES": {
				if (DASZwaveUtils.verifyZWaveUtilitiesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "INCLUSION MODE ACTIVE":
			case "ACTIVATE Z-WAVE DEVICE": {
				DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isActivateZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "EXCLUSION MODE ACTIVE": {
				DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isExcludeZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD NEW DEVICE":
			case "ADD NEW DEVICE DASHBOARD": {
				AddNewDeviceScreen addDeviceSrceen = new AddNewDeviceScreen(testCase);
				if (addDeviceSrceen.isAddNewDeviceHeaderDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CHOOSE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isChooseLocationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONFIRM YOUR ZIP CODE": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isConfirmYourAddressZipCodeTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME YOUR BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameYourBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "POWER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isPowerYourBaseStationHeaderTitleVisible() && dasDIY.isNextButtonVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "REGISTER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isRegisterBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONNECT TO NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				DIYRegistrationUtils.waitForLookingForNetworkConnectionProgressBarToComplete(testCase);
				if (dasDIY.isConnectToNetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTER YOUR WI-FI PASSWORD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isWiFiPasswordScreenSubTitleTextVisibile()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD A NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isAddANetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ALEXA APP DOWNLOAD PAGE": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.verifyAlexaAppPlayStoreTitleIsVisible(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Amazon Alexa App download page is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Amazon Alexa App download page is not displayed");
				}
				break;
			}

			case "CHECK LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCheckLocationScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME SENSOR": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameSensorScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase, "In " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in excpected screen: " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid Screen " + expectedScreen.get(0));
				return flag;

			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
