package com.honeywell.lyric.das.utils;

import java.util.HashMap;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class DASSettingsUtils {

	public static boolean verifyDeleteDASConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DeleteDASPopUpConfirmationTitle", 3)) {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "  This will delete your Smart Home Security and all the connected accessories.     Are you sure you want to delete \"Living Room\"?";
			}

			// message =" This will delete your Smart Home Security and all the connected
			// accessories. Are you sure you want to delete &quot;Living Room&quot;?";

			if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete DAS Confirmation Pop Up message not correctly displayed. Expected: '" + message + "'. Displayed : (Refer Image)");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteDASConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DeleteDASPopUpConfirmationTitle", 3)) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Delete DAS Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}
}
