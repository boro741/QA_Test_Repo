package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ZwaveScreen;
import com.honeywell.lyric.utils.LyricUtils;

import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyDisplayedPopUp extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	private TestCaseInputs inputs;
	// private HashMap<String, MobileObject> fieldObjects;

	public boolean flag = true;

	public VerifyDisplayedPopUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedPopUp) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.expectedPopUp = expectedPopUp;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should receive a (.*) popup$")
	public boolean keywordSteps() {

		switch (expectedPopUp.get(0).toUpperCase()) {
		case "PERFORM ONLY IN HOME MODE": {
			SensorSettingScreen sensorSetting = new SensorSettingScreen(testCase);
			flag = flag & sensorSetting.performOnlyInHome();
			break;
		}
		case "CONTROLLER RESET CONFIRMATION": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isFactoryResetPopupHeaderDisplayed()
					& zwaveScreen.isFactoryResetPopupMessageDisplayed();
			break;
		}
		case "FACTORY RESET SUCCESSFUL": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isFactoryResetSuccessfullPopupHeaderDisplayed()
					& zwaveScreen.isFactoryResetSuccessfullPopupMessageDisplayed();
			break;
		}
		case "DIMMER EXCLUDED SUCCESSFULLY":
		case "SWITCH EXCLUDED SUCCESSFULLY": {
			flag = flag & DASZwaveUtils.verifyDeviceExcludedPopUp(testCase, inputs);
			break;
		}
		case "FACTORY RESET FAILED": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isFactoryResetFailedPopupMessageDisplayed()
					& zwaveScreen.clickOnFactoryResetFailedPopupAckConfirm();
			break;
		}

		case "DIMMER REPLACED SUCCESSFULLY":
		case "SWITCH REPLACED SUCCESSFULLY": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isReplacedSuccessfullyDisplayed();
			flag = flag & zwaveScreen.clickOnReplacedSuccessfullyMessageAck();
			break;
		}

		case "SWITCH DELETED SUCCESSFULLY": {
		}
		case "REMOVE DEVICE": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isRemoveDevicePopUpDisplayed();
			break;
		}
		case "DELETE DAS CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteDASConfirmationPopUp(testCase, inputs);
			break;
		}
		case "DELETE ACCESS SENSOR CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteAccessSensorConfirmationPopUp(testCase, inputs);
			break;
		}
		case "DELETE MOTION SENSOR CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteMotionSensorConfirmationPopUp(testCase, inputs);
			break;
		}
		case "DELETE KEYFOB CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteKeyfobConfirmationPopUp(testCase);
			break;
		}
		case "INCLUSION DEVICE NOT FOUND": {
			flag = flag & DASZwaveUtils.verifyDeviceNotFoundPopUp(testCase, inputs);
			break;
		}
		case "EXCLUSION DEVICE NOT FOUND": {
			flag = flag & DASZwaveUtils.verifyDeviceNotFoundPopUp(testCase, inputs);
			break;
		}
		case "DEVICE NOT FOUND": {
			flag = flag & DASZwaveUtils.verifyDeviceNotFoundPopUp(testCase, inputs);
			break;
		}
		case "CANCEL SETUP": {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			if (dasDIY.isCancelPopupVisible()) {
				Keyword.ReportStep_Pass(testCase, "Cancel popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel popup is not displayed");
				return flag;
			}
			break;
		}
		case "INVALID ZIP CODE": {

			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isInvalidZipCodePopupVisible();
			break;
		}
		case "EXISTING LOCATION ERROR": {

			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isCustomLocationNameExistsErrorPopupTitleVisible();
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Custom Location Name exists error popup message is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Custom Location Name exists error popup message is not displayed");
			}
			break;
		}
		case "EXISTING BASE STATION ERROR": {

			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isCustomBaseStationNameExistsErrorPopupTitleVisible();
			break;
		}
		case "CUSTOM NAME SHOULD NOT BE EMPTY": {

			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isCustomBaseStationNameIsEmptyErrorPopupTitleVisible();
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Custom base station name is empty error popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Custom base station name is empty error popup is not displayed");
			}
			break;
		}
		case "BASE STATION NOT FOUND": {

			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isBaseStationNotFoundPopupVisible();
			break;
		}
		case "SCANNING FAILURE": {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isQRCodeScanningFailurePopupVisible();
			break;
		}
		case "WI-FI CONNECTION FAILED": {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			flag = flag & dasDIY.isWiFiConnectionFailedPopupVisible();
			break;
		}
		case "CANCEL GEOFENCE": {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			if (dasDIY.isCancelGeofencePopupTitleVisible()) {
				Keyword.ReportStep_Pass(testCase, "Cancel popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel popup is not displayed");
				return flag;
			}
			break;
		}
		case "NEW TO LYRIC CAMERA": {
			flag = flag & DASCameraUtils.verifyNewToLyricPopUp(testCase);
			break;
		}
		case "SET TO OFF": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			flag = flag & sc.isSetToOffPopupVisible();
			if (flag) {
				Keyword.ReportStep_Pass(testCase, expectedPopUp.get(0) + "' is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedPopUp.get(0) + "' is not displayed");
			}
			break;
		}
		case "SWITCH TO AWAY": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			flag = flag & sc.isSwitchToAwayPopupVisible(5);
			if (flag) {
				Keyword.ReportStep_Pass(testCase, expectedPopUp.get(0) + "' is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedPopUp.get(0) + "' is not displayed");
			}
			break;
		}
		case "SWITCH TO NIGHT": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			flag = flag & sc.isSwitchToNightPopupVisible(5);
			if (flag) {
				Keyword.ReportStep_Pass(testCase, expectedPopUp.get(0) + "' is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedPopUp.get(0) + "' is not displayed");
			}
			break;
		}
		case "UNABLE TO CONNECT TO BASE STATION": {

			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			flag = flag & sc.isUnableToConnectToBaseStationAlertVisible();
			break;
		}
		case "SENSOR TAMPER": {
			SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
			flag = flag & settingScreen.isSensorTamperClearPopupDisplayed();
			break;
		}


		case "YOU CAN PERFORM THIS ACTION ONLY IN HOME AND OFF MODE ON CLICKING BASE STATION VOLUME":{
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag=flag& bs.isPerformInModePopupVisible();
			break;
		}
            

		
		case "CANCEL SENSOR SETUP": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			if (sensor.isCancelSetUpPopUpVisible()) {
				Keyword.ReportStep_Pass(testCase, "Cancel Sensor popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel popup is not displayed");
				return flag;
			}

			break;
		}
		case "SENSOR ENROLLMENT TIME OUT": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			if (sensor.isTimeOutErrorForDiscoveryDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "TimeOut popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "TimeOut popup is not displayed");
				return flag;
			}
			break;
		}
		case "TURN OFF MICROPHONE": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			if (cs.isTurnOffCameraMicrophonePopupHeaderTitleVisible(testCase)
					&& cs.isTurnOffCameraMicrophonePopupMsgVisible(testCase)) {
				Keyword.ReportStep_Pass(testCase, "Turn Off Camera Micrphone Popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Turn Off Camera Micrphone Popup is not displayed");
				return flag;
			}

			break;
		}case "GEOFENCING":{
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.isGeofencePopUpVisible();
			break;
		}
		case "YOU CAN PERFORM THIS ACTION ONLY IN HOME OR OFF MODE":{
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.isPerformOnlyInModesPopupForGeofence();
			return flag;
		}
		case "DELETE THERMOSTAT DEVICE CONFIRMATION": {
			flag = flag & HBNAEMEASettingsUtils.verifyDeleteThermostatDeviceConfirmationPopUp(testCase, inputs);
			break;
		}
		case "TURN OFF CAMERA MICROPHONE": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			flag = flag & cs.verifyCameraTurnOffMicrophonePopUp(testCase);
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
			return flag;
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
