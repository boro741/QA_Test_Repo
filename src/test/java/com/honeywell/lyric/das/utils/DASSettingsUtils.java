package com.honeywell.lyric.das.utils;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.screens.BaseStationSettingsScreen;

public class DASSettingsUtils {

	public static boolean verifyDeleteDASConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteDASDevicePopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "  This will delete your Smart Home Security and all the connected accessories.     Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?";
			}

			// message =" This will delete your Smart Home Security and all the connected
			// accessories. Are you sure you want to delete &quot;Living Room&quot;?";

			if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete DAS Confirmation Pop Up message not correctly displayed. Expected: '" + message
								+ "'. Displayed : (Refer Image)");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteSensorConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up Title is correctly displayed");
			if (inputs.getInputValue(DASInputVariables.SENSORTYPE).equals(DASInputVariables.MOTIONSENSOR)) {
				flag = flag & bs.isMotionSensorDeletePopUpMessageVisible();
			} else {
				flag = flag & bs.isAccessSensorDeletePopUpMessageVisible();
			}

			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Sensor Confirmation Pop Up message not correctly displayed.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteKeyfobConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isKeyfobPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up Title is correctly displayed");
			if (bs.isKeyfobDeletePopUpMessageVisible()) {
				Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Keyfob Confirmation Pop Up message not correctly displayed.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Keyfob Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteDASConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteDASPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Delete DAS Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteKeyfobConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteKeyfobPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Delete Keyfob Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteSensorConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up not displayed");
		}
		return flag;
	}
}
