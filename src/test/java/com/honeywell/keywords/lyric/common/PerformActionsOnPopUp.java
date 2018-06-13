package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASCameraSolutionCard;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
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
	@KeywordStep(gherkins = "^user (.*) the (.*) popup$")
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

		} else if (expectedPopUp.get(1).equalsIgnoreCase("CONTROLLER RESET")) {
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
				}
				break;
			}
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isYesButtonInCancelPopupVisible()) {
					flag = flag & dasDIY.clickOnYesButtonInCancelPopup();
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} 
		else if (expectedPopUp.get(1).equalsIgnoreCase("CANCEL SENSOR SETUP")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag &  sensor.clickOnDismissCancelButton();
				break;
			}
			case "ACCEPTS": {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag &  sensor.clickOnConfirmCancelButton();
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
				}
				break;
			}
			case "RETRIES BASE STATION PAIRING IN": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible()) {
					flag = flag & dasDIY.clickOnRetryButtonInBaseStationNotFoundPopup();
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
							"BASE STATION PROGRESS BAR", 1);
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
				}
				break;
			}
			case "ACCEPTS": {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isYesButtonInGeoFencePopupVisible()) {
					flag = flag & dasDIY.clickOnYesButtonInGeoFencePopup();
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		}else if (expectedPopUp.get(1).equalsIgnoreCase("Delete Sensor Confirmation")) {
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
				flag = flag & bs.clickOnCancelButton();
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
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO AWAY")) {
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
		} else if (expectedPopUp.get(1).equalsIgnoreCase("SWITCH TO NIGHT")) {
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
		} else if (expectedPopUp.get(1).equalsIgnoreCase("UNABLE TO CONNECT TO BASE STATION")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CLICKS ON OK IN": {
				SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
				if (sc.isOKButtonInUnableToConnectToBaseStationAlertVisible()) {
					flag = flag & sc.clickOnOKButtonInUnableToConnectToBaseStationAlert();
				}
				break;
			}
			}
		} else if(expectedPopUp.get(1).equalsIgnoreCase("SENSOR TAMPER")){
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
