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
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.ZwaveScreen;

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

		case "DIMMER DELETED SUCCESSFULLY":{
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			if(DASZwaveUtils.verifyDeviceDeletedPopUp(testCase, inputs)){
				return true;
			}else
				if(zwaveScreen.isDeviceNotFoundPopupDisplayed()){
					zwaveScreen.clickCancelOnDeviceNotFoundPopUp();
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.clickOkOnRemoveDevicePopUp();
					zwaveScreen.isExcludeZwaveScreenDisplayed();
					try {
						DASZwaveUtils.activateZwaveDimmer(testCase, inputs);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			flag = flag & DASZwaveUtils.verifyDeviceDeletedPopUp(testCase, inputs);
			break;

		}
		case "SWITCH DELETED SUCCESSFULLY":{
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			if(DASZwaveUtils.verifyDeviceDeletedPopUp(testCase, inputs)){
				return true;
			}else
				if(zwaveScreen.isDeviceNotFoundPopupDisplayed()){
					zwaveScreen.clickCancelOnDeviceNotFoundPopUp();
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.clickOkOnRemoveDevicePopUp();
					zwaveScreen.isExcludeZwaveScreenDisplayed();
					try {
						DASZwaveUtils.activateZwaveSwitch(testCase, inputs);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			flag = flag & DASZwaveUtils.verifyDeviceDeletedPopUp(testCase, inputs);
			break;
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
		case "DELETE SENSOR CONFIRMATION": {
			flag = flag & DASSettingsUtils.verifyDeleteSensorConfirmationPopUp(testCase, inputs);
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

		case "NEW TO LYRIC CAMERA" : {
			flag = flag & DASCameraUtils.verifyNewToLyricPopUp(testCase);
			break;
		}
		
		case "SET TO OFF": {

			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			flag = flag & sc.isSetToOffPopupVisible();
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
