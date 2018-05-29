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
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASCameraSolutionCard;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.SensorSettingScreen;
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
			case "NO ALARM":{
				if (!DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)){
					Keyword.ReportStep_Pass(testCase,
							"Not displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Displayed with " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "PAUSED STREAMING":{
				AlarmScreen check = new AlarmScreen(testCase);
				boolean b = check.isPlayStreamingVisible();
				if(b) {
					Keyword.ReportStep_Pass(testCase, "Paused Streaming is displayed");
				}
				else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Paused Streaming is not displayed");
				}
			break;
			}
			case "ALARM HISTORY": {
				AlarmScreen click = new AlarmScreen(testCase);
				flag= flag & click.isAlarmHistoryDisplayed();
				if(flag) {
					Keyword.ReportStep_Pass(testCase, "Alarm History is Displayed");
				}
				 else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Settings Introduction page is not displayed");
					}
				break;
			}
			case "CALL": {
				AlarmScreen click = new AlarmScreen(testCase);
				flag=click.isCallScreenDisplayed();
				if(flag){
					Keyword.ReportStep_Pass(testCase,expectedScreen.get(0).toUpperCase() +"is displayed");
				}else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "WAITING TO CLOSE": {
				AlarmScreen alarmScreen = new AlarmScreen(testCase);
				if (alarmScreen.isWaitingToCloseScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTRY DELAY": {
				if (DASAlarmUtils.verifyEntryDelayScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NO ENTRY DELAY": {
				if (!DASAlarmUtils.verifyEntryDelayScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Not displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Displayed with " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ALARM": {
				if (DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully displayed with " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Not in expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CAMERA SOLUTION CARD": {
				if (DASCameraUtils.isCameraLiveStreaming(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ZWAVE CONTROLLER INFO": {
				DASZwaveUtils.isControllerDetailsDisplayed(testCase);
				break;
			}
			case "ZWAVE DEVICES": {
				if (DASZwaveUtils.verifyZWaveDevicesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ZWAVE UTILITIES": {
				if (DASZwaveUtils.verifyZWaveUtilitiesScreen(testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "REPLACE MODE ACTIVE": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isReplaceScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "INCLUSION MODE ACTIVE":
			case "ACTIVATE ZWAVE DEVICE": {
				DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isActivateZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "EXCLUSION MODE ACTIVE": {
				DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isExcludeZwaveScreenDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD NEW DEVICE":
			case "ADD NEW DEVICE DASHBOARD": {
				AddNewDeviceScreen addDeviceSrceen = new AddNewDeviceScreen(testCase);
				if (addDeviceSrceen.isAddNewDeviceHeaderDisplayed(60)) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "VIDEO CLIP": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (dasDIY.isVideoPlayerControlIconInAndroidVisible(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
					}

				} else {
					if (dasDIY.isNavBackIconDisplayedInVideoClipScreen(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
					}
				}
				break;
			}
			case "WHAT TO EXPECT": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isWhatToExpectScreenHeaderTitleVisible() && dasDIY.isBackButtonInWhatToExpectScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CHOOSE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isChooseLocationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CREATE LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCreateLocationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONFIRM YOUR ZIP CODE": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isConfirmYourAddressZipCodeTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME YOUR BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameYourBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CREATE NEW BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCreateBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "POWER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isPowerYourBaseStationHeaderTitleVisible() && dasDIY.isNextButtonVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "BASE STATION HELP": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isBaseStationHelpHeaderTitleVisible() && dasDIY.isBackButtonInBaseStationHelpScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "REGISTER BASE STATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isRegisterBaseStationHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CONNECT TO NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "NETWORK CONNECTION PROGRESS BAR", 1);
				if (dasDIY.isConnectToNetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ENTER YOUR WI-FI PASSWORD": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isWiFiPasswordScreenSubTitleTextVisibile()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "ADD A NETWORK": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isAddANetworkHeaderTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "CHECK LOCATION": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isCheckLocationScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
				}
				break;
			}
			case "NAME SENSOR": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNameSensorScreenTitleVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully navigated to " + expectedScreen.get(0).toUpperCase() + " screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to navigate to expected screen " + expectedScreen.get(0).toUpperCase());
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
			case "CAMERA SETTINGS INTRODUCTION": {
				DASCameraSolutionCard dc = new DASCameraSolutionCard(testCase);
				if (dc.isCameraSettingsIntroImageVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Camera Settings Introduction page is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Settings Introduction page is not displayed");
				}
				break;
			}
			case "TEST ACCESS SENSOR":{
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				if(sensor.isTestSensorHeadingDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Test Access Sensor Screen is displayed");	
				}
				else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Test Access Sensor Screen is not displayed");
				}
				break;
			}
			case "SIGNAL STRENGTH":{
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag= flag & sensor.isSignalStrengthScreenDisplayed();
				break;
			}
			case "ACCESS SENSOR HELP":{
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag= flag &sensor.isAccessSensorHelpScreenDisplayed();
				break;
			}
			case "GET ADDITIONAL HELP ON ACCESS SENSOR HELP":{
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag= flag &sensor.isGetAdditionalHelpOnSensorHelpDisplayed();
				break;
			}
			case "SENSOR COVER TAMPER":{
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag= flag  &sensor.isSensorTamperedScreenDisplayed();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input " + expectedScreen.get(0));
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
