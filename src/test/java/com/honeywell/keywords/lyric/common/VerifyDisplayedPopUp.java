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
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.ManageUsersScreen;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.VacationHoldScreen;
import com.honeywell.screens.WLDConfigurationScreen;
import com.honeywell.screens.ZwaveScreen;

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
	@KeywordStep(gherkins = "^user should receive a \"(.+)\" popup$")
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
		case "DIMMER DELETED SUCCESSFULLY":
		case "SWITCH DELETED SUCCESSFULLY": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			flag = flag & zwaveScreen.isDeletedSuccessPopupTitleDisplayed();
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
		case "DELETE ISMV SENSOR CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteISMVSensorConfirmationPopUp(testCase, inputs);
			break;
		}
		case "DELETE OSMV SENSOR CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteOSMVSensorConfirmationPopUp(testCase, inputs);
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
			flag = flag & dasDIY.isBaseStationNotFoundPopupVisible(10);
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
		// Amresh wld edit starts
		case "DUPLICATE NAME ERROR": {
			flag = true;
			WLDConfigurationScreen sc = new WLDConfigurationScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				// flag = flag & sc.clickOnBackButton();
				flag = flag & sc.isPopUpOkButtonVisible();
			} else {
				flag = flag & sc.isPopUpOkButtonVisible();
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase, expectedPopUp.get(0) + "' is displayed");
				Keyword.ReportStep_Pass(testCase, "Error Header" + sc.getHomeNameAlreadyExistTextValue());
				Keyword.ReportStep_Pass(testCase, "Error Body" + sc.getHomeNameExistsBodyValue());
				// sc.clickonPopUpOkButton();
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						expectedPopUp.get(0) + "' is not displayed");
			}
			break;
		}
		// Amresh wld edit ends
		case "SENSOR TAMPER": {
			SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
			flag = flag & settingScreen.isSensorTamperClearPopupDisplayed(60);
			break;
		}

		case "YOU CAN PERFORM THIS ACTION ONLY IN HOME AND OFF MODE ON CLICKING BASE STATION VOLUME": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.isPerformInModePopupVisible();
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
		}
		case "GEOFENCING": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isGeofencePopUpVisible()) {
				Keyword.ReportStep_Pass(testCase, "Geofencing pop up displayed");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Geofencing pop up not displayed");
			}
			break;
		}
		case "YOU CAN PERFORM THIS ACTION ONLY IN HOME OR OFF MODE": {
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
		case "END VACATION MODE CONFIRMATION": {
			VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			if (vhs.isVacationEndCautionMessageVisible()) {
				Keyword.ReportStep_Pass(testCase, "End Vacation Mode popup message is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"End Vacation Mode popup message is not displayed");
			}
			break;
		}
		case "SENSORDELETE": {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			SchedulingScreen schl = new SchedulingScreen(testCase);
			flag = flag & fly.isDeleteSensorPopUpVisible();
			flag = flag & schl.clickOnOkButton();
			break;
		}
		case "SENSOR NAME ALREADY ASSIGNED ERROR": {
			SensorSettingScreen ss = new SensorSettingScreen(testCase);
			if (ss.isSensorNameAlreadyAssignedErrorPopupVisible() && ss.isSensorNameAlreadyAssignedMsgVisible()
					&& ss.isOKButtonInSensorNameAlreadyAssignedPopupVisible()) {
				flag &= ss.isOKButtonInSensorNameAlreadyAssignedPopupVisible();
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Sensor name already assigned error popup");
			}
			break;
		}
		case "USER ALREADY ADDED TO THIS ACCOUNT ERROR": {
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (mus.isAlreadyInvitedUserMsgVisibleInAndroid()) {
					Keyword.ReportStep_Pass(testCase,
							"User already added to this account error message is displayed in Android");
				} else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"User already added to this account error message is not displayed in Android");
				}
			} else {
				if (mus.isAlreadyInvitedUserErrorPopupTitleVisible() && mus.isAlreadyInvitedUserErrorPopupMsgVisible()
						&& mus.isOKButtonInAlreadyInviteduserErrorPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, "User already added to this account error popup is displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"User already added to this account error popup is not displayed");
				}
			}
			break;
		}
		case "CONFIRM ACCESS REMOVAL": {
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			if (mus.isConfirmAccessRemovalPopupTitleVisible() && mus.isConfirmAccessRemovalPopupMsgVisible()
					&& mus.isCancelButtonInConfirmAccessRemovalPopupVisible()
					&& mus.isRemoveButtonInConfirmAccessRemovalPopupVisible()) {
				Keyword.ReportStep_Pass(testCase, "Confirm Access Removal popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Confirm Access Removal popup is not displayed");
			}
			break;
		}
		case "UPDATE GEOFENCE CENTER": {
			GeofenceSettings gs = new GeofenceSettings(testCase);
			if (gs.isUpdateGeofenceCenterPopupTitleVisible() && gs.isUpdateGeofenceCenterPopupMsgVisible()
					&& gs.isCancelButtonInUpdateGeofenceCenterPopupVisible()
					&& gs.isYesUpdateButtonInUpdateGeofenceCenterPopupVisible()) {
				Keyword.ReportStep_Pass(testCase, "Update Geofence Center popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Update Geofence Center popup is not displayed");
			}
			break;
		}
		case "CANCEL GEOFENCE CHANGES": {
			GeofenceSettings gs = new GeofenceSettings(testCase);
			if (gs.isCancelGeofenceChangesPopupTitleVisible() && gs.isCancelGeofenceChangesPopupMsgVisible()
					&& gs.isNOButtonInCancelGeofenceChangesPopupVisible()
					&& gs.isYESButtonInCancelGeofenceChangesPopupVisible()) {
				Keyword.ReportStep_Pass(testCase, "Cancel Geofence Changes popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Cancel Geofence Changes popup is not displayed");
			}
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
