package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.DR.utils.DRUtils;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.das.utils.VacationSettingsUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.DASCameraSolutionCard;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.DRScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.ManageUsersScreen;
import com.honeywell.screens.NameEditAccountScreen;
import com.honeywell.screens.OSPopUps;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.VacationHoldScreen;
import com.honeywell.screens.ZwaveScreen;

public class PerformActionsOnPopUp extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public PerformActionsOnPopUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedPopUp) {
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
	@KeywordStep(gherkins = "^user \"(.*)\" the \"(.*)\" popup$")
	public boolean keywordSteps() {
		if (expectedPopUp.get(1).equalsIgnoreCase("FACTORY RESET SUCCESSFUL")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				zScreen.clickOnFactoryResetSuccessfullAckConfirm();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}

		} else if(expectedPopUp.get(1).equalsIgnoreCase("TURN ON LOCATION SERVICES")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON SKIP BUTTON IN": {
				OSPopUps osp= new OSPopUps(testCase);
				osp.clickOnSkipButtonInTurnOnLocationServicesPopup();
				break;
			}
			case "CLICKS ON SETTINGS BUTTON IN" : {
				OSPopUps osp= new OSPopUps(testCase);
				osp.clickOnSettingsButtonInTurnOnLocationServicesPopup();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
		  }
		}
		else if (expectedPopUp.get(1).equalsIgnoreCase("CONTROLLER RESET")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CANCELS": {
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				zScreen.clickOnFactoryResetPopupCancel();
				break;
			}
			case "CONFIRMS": {
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				zScreen.clickOnFactoryResetPopupConfirm();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}

		} else if (expectedPopUp.get(1).equalsIgnoreCase("Delete DAS Confirmation")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnNoButton();
				flag = flag & DASSettingsUtils.verifyDeleteDASConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				Dashboard d = new Dashboard(testCase);
				flag = flag & bs.clickOnYesButton();
				flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
						"DELETING LOCATION PROGRESS BAR", 1);
				flag = flag & DASSettingsUtils.verifyDeleteDASConfirmationPopUpIsNotDisplayed(testCase);
				if (d.isAddDeviceIconVisible(1) || d.isAddDeviceIconBelowExistingDASDeviceVisible(1)) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Dashboard screen is dispalyed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dashboard screen is not displayed");
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!testCase.isTestSuccessful() || !flag) {
					flag = flag & DIYRegistrationUtils.deleteDASDeviceThroughCHIL(testCase, inputs);
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}

		} else if (expectedPopUp.get(1).equalsIgnoreCase("Device Excluded")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				zwaveScreen.clickOKOnDeviceExcludedPopUp();
				break;
			}
			case "ADDS DEVICE NOW": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				zwaveScreen.clickAddNowOnDeviceExcludedPopUp();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("DELETION ON REMOVE DEVICE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				DASZwaveUtils.clickOkOnRemoveDevicePopUp(testCase, inputs);
				break;
			}
			case "CANCELS": {
				DASZwaveUtils.clickCancelOnRemoveDevicePopUp(testCase, inputs);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("INCLUSION DEVICE NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASZwaveUtils.clickCancelOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "RETRIES THE INCLUSION ON": {
				DASZwaveUtils.clickRetryOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "TRIES EXCLUSION ON": {
				DASZwaveUtils.clickTryExcludeOnDeviceNotFoundPopUp(testCase, inputs);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("EXCLUSION DEVICE NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASZwaveUtils.clickCancelOnExcludeDeviceNotFoundPopUp(testCase);
				break;
			}
			case "RETRIES": {
				DASZwaveUtils.clickRetryOnExcludeDeviceNotFoundPopUp(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("DEVICE NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				DASZwaveUtils.clickOKOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "DISMISSES": {
				DASZwaveUtils.clickCancelOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "RETRY": {
				DASZwaveUtils.clickRetryOnDeviceNotFoundPopUp(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("FURTHER EXCLUSION OF SWITCH EXCLUDED SUCCESSFULLY")
				|| expectedPopUp.get(1).equalsIgnoreCase("FURTHER EXCLUSION OF DIMMER EXCLUDED SUCCESSFULLY")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				flag = flag & DASZwaveUtils.clickCancelFurtherExclusionOnExcludedPopup(testCase);
				break;
			}
			case "CONFIRMS": {
				flag = flag & DASZwaveUtils.clickConfirmFurtherExclusionOnExcludedPopup(testCase);
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL SETUP")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNoButtonInCancelPopupVisible()) {
					flag = flag & dasDIY.clickOnNoButtonInCancelPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NO button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isYesButtonInCancelPopupVisible()) {
					flag = flag & dasDIY.clickOnYesButtonInCancelPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"YES button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL SENSOR SETUP")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.clickOnDismissCancelButton();
				break;
			}
			case "ACCEPTS": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.clickOnConfirmCancelButton();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("EXISTING LOCATION ERROR")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInCustomLocationNameExistsErrorPopupTitleVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInCustomLocationNameExistsErrorPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CUSTOM NAME SHOULD NOT BE EMPTY")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInCustomLocationNameExistsErrorPopupTitleVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInCustomLocationNameExistsErrorPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("EXISTING BASE STATION ERROR")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInCustomBaseNameExistsErrorPopupTitleVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInCustomBaseNameExistsErrorPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("INVALID ZIP CODE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInInvalidZipCodePopupVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInInvalidZipCodePopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("BASE STATION NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInBaseStationNotFoundPopupVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInBaseStationNotFoundPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "RETRIES BASE STATION PAIRING IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible()) {
					flag = flag & dasDIY.clickOnRetryButtonInBaseStationNotFoundPopup();
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
							"BASE STATION PROGRESS BAR", 1);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Retry button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SCANNING FAILURE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInQRCodeScanningFailurePopupVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInQRCodeScanningFailurePopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("WI-FI CONNECTION FAILED")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInWiFiConnectionFailedPopupVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInWiFiConnectionFailedPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("FIRMWARE UPDATE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isOKButtonInFirmwareUpdatePopupVisible()) {
					flag = flag & dasDIY.clickOnOKButtonInFirmwareUpdatePopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL GEOFENCE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isNoButtonInGeoFencePopupVisible()) {
					flag = flag & dasDIY.clickOnNoButtonInGeoFencePopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NO button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isYesButtonInGeoFencePopupVisible()) {
					flag = flag & dasDIY.clickOnYesButtonInGeoFencePopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"YES button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("Delete Access Sensor Confirmation")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnCancelButton();
				flag = flag & DASSettingsUtils.verifyDeleteSensorConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnYesButton();
				flag = flag & DASSettingsUtils.verifyDeleteSensorConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("Delete Motion Sensor Confirmation")
				|| expectedPopUp.get(1).equalsIgnoreCase("Delete ISMV Sensor Confirmation")
				|| expectedPopUp.get(1).equalsIgnoreCase("Delete OSMV Sensor Confirmation")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnCancelButton();
				flag = flag & DASSettingsUtils.verifyDeleteSensorConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnYesButton();
				flag = flag & DASSettingsUtils.verifyDeleteSensorConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("Delete Keyfob Confirmation")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnNoButton();
				flag = flag & DASSettingsUtils.verifyDeleteKeyfobConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnYesButton();
				flag = flag & DASSettingsUtils.verifyDeleteKeyfobConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}

		} else if (expectedPopUp.get(1).equalsIgnoreCase("New to Lyric Camera")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				DASCameraSolutionCard dc = new DASCameraSolutionCard(testCase);
				flag = flag & dc.clickOnOkButton();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SET TO OFF")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isCancelButtonInSetToOffPopupVisible()) {
					flag = flag & sc.clickOnCancelButtonInSetToOffPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button in set to off popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySetToOffPopUpIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isOKButtonInSetToOffPopupVisible()) {
					flag = flag & sc.clickOnOKButtonInSetToOffPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button in set to off popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySetToOffPopUpIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO AWAY")
				|| expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO AWAY WITH THE MULTIPLE SENSOR FAULT")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isCancelButtonInSwitchToPopupVisible()) {
					flag = flag & sc.clickOnCancelButtonInSwitchToPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button in switch to popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySwitchToAwayPopupIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isOKButtonInSwitchToPopupVisible()) {
					flag = flag & sc.clickOnOKButtonInSwitchToPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button in switch to popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySwitchToAwayPopupIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO NIGHT")
				|| expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO NIGHT WITH MULTILPLE SENSOR FAULT")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isCancelButtonInSwitchToPopupVisible()) {
					flag = flag & sc.clickOnCancelButtonInSwitchToPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button in switch to popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySwitchToNightPopupIsNotDisplayed(testCase);
				break;
			}
			case "ACCEPTS": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isOKButtonInSwitchToPopupVisible()) {
					flag = flag & sc.clickOnOKButtonInSwitchToPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button in switch to popup is not displayed");
					return flag;
				}
				flag = flag & DASCommandControlUtils.verifySwitchToNightPopupIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("GEOFENCING")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isGeofencePopUpVisible() && bs.isCancelButtonInGeofenceSettingsPopupVisible()) {
					flag = flag & bs.clickOnCancelButtonInGeofenceSettingsPopup();
				}
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isGeofencePopUpVisible() && bs.isOKButtonInGeofenceSettingsPopupVisible()) {
					flag = flag & bs.clickOnOKButtonInGeofenceSettingsPopup();
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("UNABLE TO CONNECT TO BASE STATION")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isOKButtonInUnableToConnectToBaseStationAlertVisible()) {
					flag = flag & sc.clickOnOKButtonInUnableToConnectToBaseStationAlert();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SENSOR TAMPER")) {
			SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "OK": {
				flag = flag & settingScreen.clickOnOkTamperClearPopup();
				break;
			}
			case "RETRY": {
				flag = flag & settingScreen.clickOnRetryTamperClearPopup();
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("Time out")) {
			SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				flag = flag & settingScreen.clickOnTimeOutOkPopup();
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("TURN OFF MICROPHONE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CANCELS": {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (cs.isCANCELButtonInTurnOffCameraMicrophonePopupVisible(testCase)) {
					flag = flag & cs.clickOnCANCELButtonInTurnOffCameraMicrophonePopup(testCase);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NO button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "CONFIRMS": {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (cs.isOKButtonInTurnOffCameraMicrophonePopupVisible(testCase)) {
					flag = flag & cs.clickOnOKButtonInTurnOffCameraMicrophonePopup(testCase);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"YES button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("DELETE THERMOSTAT DEVICE CONFIRMATION")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				flag = flag & ts.clickOnCancelButtonInDeleteThermostatDevice();
				flag = flag
						& HBNAEMEASettingsUtils.verifyDeleteThermostatDeviceConfirmationPopUpIsNotDisplayed(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("TURN OFF CAMERA MICROPHONE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				flag = flag & cs.clickOnMicrophonePopupOkbutton(testCase);
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("DR CANCEL")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DRScreens ddc = new DRScreens(testCase);
				flag = flag & ddc.ClickOnCancelNoPopup();
				break;
			}
			case "ACCEPTS": {
				DRScreens ddc = new DRScreens(testCase);
				flag = flag & ddc.ClickOnCancelYesPopup();
				DRUtils.waitForProgressBarToComplete(testCase, "DR Label", 1);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("END VACATION MODE CONFIRMATION")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				VacationHoldScreen vhs = new VacationHoldScreen(testCase);
				if (vhs.isEndVacationModePopupVisible() && vhs.isCancelButtonInEndVacationModePopupVisible()) {
					flag = flag & vhs.clickOnCancelButtonInEndVacationModePopup();
					flag = flag & VacationSettingsUtils.verifyEndVacationModeConfirmationPopUpIsNotDisplayed(testCase);
					break;
				}
			}
			case "ACCEPTS": {
				VacationHoldScreen vhs = new VacationHoldScreen(testCase);
				if (vhs.isEndVacationModePopupVisible() && vhs.isEndButtonInEndVacationPopupModeVisible()) {
					flag = flag & vhs.clickOnEndButtonInEndVacationPopupMode();
					flag = flag & VacationSettingsUtils.verifyEndVacationModeConfirmationPopUpIsNotDisplayed(testCase);
					break;
				}
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("GEOFENCING")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isGeofencePopUpVisible() && bs.isCancelButtonInGeofenceSettingsPopupVisible()
						&& bs.clickOnCancelButtonInGeofenceSettingsPopup()) {
					if (!bs.isGeofencePopUpVisible()) {
						Keyword.ReportStep_Pass(testCase, "Sucessfully Dismissed the Geofencing pop up");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to Dismiss the Geofencing pop up");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Geofencing pop up not displayed");
				}
				break;
			}
			case "ACCEPTS": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (bs.isGeofencePopUpVisible() && bs.isCancelButtonInGeofenceSettingsPopupVisible()
						&& bs.clickOnOKButtonInGeofenceSettingsPopup()) {
					if (gs.isGeofencingthislocationTextvisible() && gs.isGeofencingthislocationDescriptionvisible()) {
						Keyword.ReportStep_Pass(testCase, "Sucessfully accepted the Geofencing pop up");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to accept the Geofencing pop up");
					}

				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Geofencing pop up not displayed");
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SENSOR NAME ALREADY ASSIGNED ERROR")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				SensorSettingScreen ss = new SensorSettingScreen(testCase);
				if (ss.isOKButtonInSensorNameAlreadyAssignedPopupVisible()) {
					flag = flag & ss.clickOnOKButtonInSensorNameAlreadyAssignedPopup();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("ALLOW HONEYWELL TO ACCESS YOUR LOCATION")) {
			OSPopUps ops = new OSPopUps(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "SELECTS DONT ALLOW BUTTON FROM": {
				if(ops.isDontAllowButtonInAllowHoneywellToAccessYourLocationPopupVisible()) {
					flag &= ops.clickOnDontAllowButtonInAllowHoneywellToAccessYourLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Dont Allow button in Allow Honeywell to access your location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Dont Allow button in Allow Honeywell to access your location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Don't Allow button is not displayed in Allow Honeywell to access your location popup");
				}
				break;
			}
			case "SELECTS ALLOW BUTTON FROM" : {
				if(ops.isAlwaysAllowButtonInAllowHoneywellToAccessYourLocationPopupVisible()) {
					flag &= ops.clickOnAlwaysAllowButtonInAllowHoneywellToAccessYourLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Allow button in Allow Honeywell to access your location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Allow button in Allow Honeywell to access your location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Allow button is not displayed in Allow Honeywell to access your location popup");
				}
				break;
			}
			case "SELECTS ONLY WHILE USING THE APP BUTTON FROM" : {
				if(ops.isOnlyWhileUsingTheAppButtonInAllowHoneywellToAccessYourLocationPopupVisible()) {
					flag &= ops.clickOnOnlyWhileUsingTheAppButtonInAllowHoneywellToAccessYourLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on While Using The App button in Allow Honeywell to access your location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on While Using The App button in Allow Honeywell to access your location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Select Only while using the app button is not displayed in Allow Honeywell to access your location popup");
				}
				break;
			}
		  }	
		}
		else if (expectedPopUp.get(1).equalsIgnoreCase("HONEYWELL WOULD LIKE TO SEND YOU NOTIFICATIONS")) {
			OSPopUps ops = new OSPopUps(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "SELECTS DONT ALLOW BUTTON FROM": {
				if(ops.isDontAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopupVisible()) {
					flag &= ops.clickOnDontAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Dont Allow button in Honeywell would like to send you notifications Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Dont Allow button in Honeywell would like to send you notifications Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Don't Allow button is not displayed in Honeywell would like to send you notifications popup");
				}
				break;
			}
			case "SELECTS ALLOW BUTTON FROM" : {
				if(ops.isAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopupVisible()) {
					flag &= ops.clickOnAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Allow button in Honeywell would like to send you notifications Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Allow button in Honeywell would like to send you notifications Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Allow button is not displayed in Honeywell would like to send you notifications popup");
				}
				break;
			}
		  }	
		} 
		else if(expectedPopUp.get(1).equalsIgnoreCase("ALLOW HONEYWELL TO ACCESS THIS DEVICES LOCATION")) {
			OSPopUps ops = new OSPopUps(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "SELECTS ALLOW BUTTON FROM": {
				if(ops.isAllowButtonInAllowHoneywellToAccessThisDevicesLocationPopupVisible(30)) {
					flag &= ops.clickOnAllowButtonInAllowHoneywellToAccessThisDevicesLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Allow button in Allow Honeywell to access this devices location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Allow button in Allow Honeywell to access this devices location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Allow button is not displayed in Allow Honeywell to access this devices location popup");
				}
				break;
			}
			case "SELECTS DENY BUTTON FROM": {
				if(ops.isDenyButtonInAllowHoneywellToAccessThisDevicesLocationPopupVisible(30)) {
					flag &= ops.clickOnDenyButtonInAllowHoneywellToAccessThisDevicesLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Deny button in Allow Honeywell to access this devices location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Deny button in Allow Honeywell to access this devices location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Deny button is not displayed in Allow Honeywell to access this devices location popup");
				}
				break;
			}
		  }
		}
		
		
		else if (expectedPopUp.get(1).equalsIgnoreCase("DELETE USER")) {
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
                if (mus.isOKButtonInDeleteUserPopupVisible()) {
                        flag &= mus.clickOnOKButtonInDeleteUserPopup();
                        if (mus.isManageUsersScreenHeaderVisible(10) && mus.isInviteNewUserButtonVisible()
                                           && mus.isBackButtonVisible()) {
                                 Keyword.ReportStep_Pass(testCase, "User is deleted and is in Users screen");
                         } else {
                                       Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                                    "User is not in Users screen");
                         }
                   } else {
                        flag = false;
                        Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
                                             "Remove button is not displayed in: " + expectedPopUp.get(0));
                }
                break;
	
			}
			case "CLICKS ON CANCEL IN" : {
					if (mus.isCancelButtonInDeleteUserPopupVisible()) {
							flag &= mus.clickOnCancelButtonInDeleteUserPopup();
							if (mus.isManageUsersScreenHeaderVisible(10) && mus.isInviteNewUserButtonVisible()
										&& mus.isBackButtonVisible()) {
									Keyword.ReportStep_Pass(testCase, "User is in Manage Users screen");
							} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"User is not in Manage Users screen");
							}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Cancel button is not displayed in: " + expectedPopUp.get(0));
					}
					break;
				}
			
				/*if(ops.isCancelButtonInDeleteUserPopupVisible()) {
					flag&= ops.clickOnCancelButtonInDeleteUserPopup(); 
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Cancel button in Delete User Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Cancel button in Delete User Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on the Cancel button in Delete User Popup");
				}
				break;
			}*/
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("UPDATE GEOFENCE CENTER")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CANCELS": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isCancelButtonInUpdateGeofenceCenterPopupVisible()) {
					flag &= gs.clickOnCancelButtonInUpdateGeofenceCenterPopup();
					if (gs.isGeofenceRadiusScreenTitleVisible() && gs.isUpdateGeofenceCenterButtonVisible()
							&& gs.isBackButtonInGeofenceRadiusScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "User is in Geofence Radius screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"User is not in Geofence Radius screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "CLICKS ON UPDATE IN": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isYesUpdateButtonInUpdateGeofenceCenterPopupVisible()) {
					flag &= gs.clickOnYesUpdateButtonInUpdateGeofenceCenterPopup();
					DASSettingsUtils.waitForProgressBarToComplete(testCase, "LOADING PROGRESS BAR", 2);
					if (gs.isGeofenceRadiusScreenTitleVisible() && gs.isUpdateGeofenceCenterButtonVisible()
							&& gs.isBackButtonInGeofenceRadiusScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "User is in Geofence Radius screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"User is not in Geofence Radius screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Yes, Update button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL GEOFENCE CHANGES")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CANCELS": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isNOButtonInCancelGeofenceChangesPopupVisible()) {
					flag &= gs.clickOnNOButtonInCancelGeofenceChangesPopup();
					if (gs.isGeofenceRadiusScreenTitleVisible() && gs.isUpdateGeofenceCenterButtonVisible()
							&& gs.isBackButtonInGeofenceRadiusScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "User is in Geofence Radius screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"User is not in Geofence Radius screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NO button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			case "ACCEPTS": {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isYESButtonInCancelGeofenceChangesPopupVisible()) {
					flag &= gs.clickOnYESButtonInCancelGeofenceChangesPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on YES button in Cancel Geofence Changes Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on YES button in Cancel Geofence Changes Popup");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"YES button is not displayed in: " + expectedPopUp.get(0));
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL LOCATION CHANGES")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				if (ads.isNoButtonInCancelLocationChangesPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, "No button in Cancel Location Changes Popup is displayed");
					flag &= ads.clickOnNoButtonInCancelLocationChangesPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on No button in Cancel Location Changes Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on No button in Cancel Location Changes Popup");
					}
				}
				break;
			}
			case "ACCEPTS": {
				if (ads.isYesButtonInCancelLocationChangesPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, "Yes button in Cancel Location Changes Popup is displayed");
					flag &= ads.clickOnYesButtonInCancelLocationChangesPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on No button in Cancel Location Changes Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on No button in Cancel Location Changes Popup");
					}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("INVALID ZIPCODE")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				if (ads.isOKButtonInInvalidZipCodePopupVisible(50)) {
					Keyword.ReportStep_Pass(testCase, "OK button in Invalid ZIPCode Popup is displayed");
					flag &= ads.clickOnOKButtonInInvalidZipCodePopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on OK button in Invalid ZIPCode Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on OK button in Invalid ZIPCode Popup");
					}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("NAME MUST START WITH LETTER OR NUMBER")) {
			AddressScreen ads = new AddressScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				if (ads.isOKButtonInNameMustStartWithPopupVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"OK button in Name Must Start With Letter or Number Popup is displayed");
					flag &= ads.clickOnOKButtonInNameMustStartWithPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Clicked on OK button in Name Must Start With Letter or Number Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on OK button in Name Must Start With Letter or Number Popup");
					}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("FIRST NAME IS REQUIRED")
				|| expectedPopUp.get(1).equalsIgnoreCase("LAST NAME IS REQUIRED")) {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				if (eas.isOKButtonInFirstNameOrLastNameRequiredErrorPopupVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"OK button in First Name or Last Name is required Popup is displayed");
					flag &= eas.clickOnOKButtonInFirstNameOrLastNameRequiredErrorPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Clicked on OK button in First Name or Last Name is required Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on OK button in First Name or Last Name is required Popup");
					}
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("YOUR ACCOUNT AND DATA IS DELETED")) {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				if (eas.isOKButtonInYourAccountAndDataIsDeletedPopupVisible()) {
					flag &= eas.clickOnOKButtonInYourAccountAndDataIsDeletedPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Clicked on OK button in Your account and data is delete Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on OK button in Your account and data is delete Popup");
					}
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("EMAIL OR PASSWORD INCORRECT")) {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "ACCEPTS": {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")
						&& !eas.isOKButtonInEmailOrPwdIncorrectErrorPopupVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"In Android, there is no popup displayed when deleted account credentials are entered.");
				} else {
					if (eas.isOKButtonInEmailOrPwdIncorrectErrorPopupVisible()) {
						flag &= eas.clickOnOKButtonInEmailOrPwdIncorrectErrorPopup();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Clicked on OK button in Email or Password Incorrect Error Popup");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on OK button in Email or Password Incorrect Error Popup");
						}
					}
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("WHAT DO YOU THINK OF HONEYWELL HOME APP")) {
			AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLOSES": {
				if (atas.isCloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopupVisible()) {
					flag &= atas.clickOnCloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Clicked on Close button in What do you think of Honeywell Home App Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Close button in What do you think of Honeywell Home App Popup");
					}
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("THANKS FOR YOUR RATING")) {
			AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON CLOSE BUTTON": {
				if (atas.isCloseButtonInThanksForYourRatingPopupVisible()) {
					flag &= atas.clickOnCloseButtonInThanksForYourRatingPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Close button in Thanks for your rating Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Close button in Thanks for your rating Popup");
					}
				}
				break;
			}
			case "CLICKS ON CONTINUE BUTTON": {
				if (atas.isContinueButtonInThanksForYourRatingPopupVisible()) {
					flag &= atas.clickOnContinueButtonInThanksForYourRatingPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Continue button in Thanks for your rating Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Continue button in Thanks for your rating Popup");
					}
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("EXIT HONEYWELL HOME")) {
			AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON SIGN OUT BUTTON IN": {
				if (ads.isSignOutButtonInExitHoneywellHomePopupVisible()) {
					flag &= ads.clickOnSignOutButtonInExitHoneywellHomePopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Sign Out button in Exit Honeywell Home Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Sign Out button in Exit Honeywell Home Popup");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sign Out button in Exit Honeywell Home Popup is not displayed");
				}
				break;
			}
			case "CLICKS ON DELETE ACCOUNT BUTTON IN": {
				if (ads.isDeleteAccountButtonInExitHoneywellHomePopupVisible()) {
					flag &= ads.clickOnDeleteAccountButtonInExitHoneywellHomePopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Clicked on Delete Account button in Exit Honeywell Home Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Delete Account button in Exit Honeywell Home Popup");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Delete Account button in Exit Honeywell Home Popup is not displayed");
				}
				break;
			}
			case "CLICKS ON CANCEL BUTTON IN": {
				if (ads.isCancelButtonInExitHoneywellHomePopupVisible()) {
					flag &= ads.clickOnCancelButtonInExitHoneywellHomePopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Cancel button in Exit Honeywell Home Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Cancel button in Exit Honeywell Home Popup");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button in Exit Honeywell Home Popup is not displayed");
				}
				break;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SORRY TO SEE YOU GO")) {
			AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON NO BUTTON IN": {
				if (ads.isNoButtonInSorryToSeeYouGoPopupVisible()) {
					flag &= ads.clickOnNoButtonInSorryToSeeYouGoPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on No button in Sorry To See You Go Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on No button in Sorry To See You Go Popup");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"No button in Sorry To See You Go Popup is not displayed");
				}
				break;
			}
			case "CLICKS ON YES BUTTON IN": {
				if (ads.isYesButtonInSorryToSeeYouGoPopupVisible()) {
					flag &= ads.clickOnYesButtonInSorryToSeeYouGoPopup();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Yes button in Sorry To See You Go Popup");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Yes button in Sorry To See You Go Popup");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Yes button in Sorry To See You Go Popup is not displayed");
				}
				break;
			}
			}
		} else if(expectedPopUp.get(1).equalsIgnoreCase("DELETE LOCATION")) {
			AddressScreen ads= new AddressScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON YES IN": {
				if(ads.isYesButtonInDeleteLocationPopupLabelVisible()) {
					flag&= ads.clickOnYesButtonInDeleteLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Yes button in Delete Location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on Yes button in Delete Location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Yes button in Delete Location popup is not displayed");
				}
				break;
			}
			case "CLICKS ON NO IN": {
				if(ads.isNoButtonInDeleteLocationPopupLabelVisible()) {
					flag&= ads.clickOnNoButtonInDeleteLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on No button in Delete Location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on No button in Delete Location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"No button in Delete Location popup is not displayed");
				}
				break;
			}
			case "CLICKS ON OK IN" : {
				if(ads.isOkButtonInDeleteLocationPopupVisible()) {
					flag &=ads.clickOnOkButtonInDeleteLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Ok button in Delete Location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on the Ok button in Delete Location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"OK button in Delete Location popup is not displayed");
				}
				break;
			}
			case "CLICKS ON DELETE IN" : {
				if(ads.isDeleteButtonInDeleteLocationPopupLabelVisible()) {
					flag &= ads.clickOnDeleteButtonInDeleteLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Delete button in Delete Location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on the Delete button in Delete Location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Delete button in Delete Location popup is not displayed");
				}
				break;
			}
			case "CLICKS ON CANCEL IN" : {
				if(ads.isCancelButtonInDeleteLocationPopupLabelVisible()) {
					flag &= ads.clickOnCancelButtonInDeleteLocationPopup();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Cancel button in Delete Location Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click on the Cancel button in Delete Location Popup");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel button in Delete Location popup is not displayed");
				}
				break;
			}
		  }
		} else if(expectedPopUp.get(1).equalsIgnoreCase("CANCEL NAME CHANGES")) {
			NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON YES IN": {
				if(neas.isYesButtonInCancelNameChangesPopupVisible()) {
					flag&= neas.clickOnYesButtonInCancelNameChangesPopupVisible();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on Yes button in Cancel Name Changes Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Yes button in Delete Name Changes popup is not displayed");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on the Yes button in Cancel Name Changes Popup");
				}
				break;
			}
			case "CLICKS ON NO IN": {
				if(neas.isNoButtonInCancelNameChangesPopupVisible()) {
					flag&= neas.clickOnNoButtonInCancelNameChangesPopupVisible();
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Clicked on No button in Cancel Name Changes Popup");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No button in Delete Name Changes popup is not displayed");
					}
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to click on the No button in Cancel Name Changesn Popup");
				}
				break;
			}
		  }
		}
		else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(1));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}