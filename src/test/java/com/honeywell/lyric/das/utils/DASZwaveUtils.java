package com.honeywell.lyric.das.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class DASZwaveUtils {

	public static boolean waitForEnteringInclusionToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EnteringInclusionModeOverlay", 5)) {
							System.out.println("Waiting for Entering inclusion to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Entering inclusion diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Entering inclusion did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForSwitchingToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwavePrimaryScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SwitchingToOverlay", 5)) {
							System.out.println("Waiting for Switching to complete");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Switching to completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Switching to complete did not complete after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}
	public static boolean verifyDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DeviceNotFoundPopup", 3)) {
			Keyword.ReportStep_Pass(testCase, "Device Not Found Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "No Z-Wave device was found. Try to Exclude and add again.";
			}

			if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "No Z-Wave device Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"No Z-Wave device Pop Up message not correctly displayed. Expected: '" + message + "'. Displayed : (Refer Image)");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"No Z-Wave device Pop Up not displayed");
		}
		return flag;
	}


	public static boolean verifyDeviceExcludedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupTitle")) {
			//MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage");
			Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up Title is correctly displayed");
			/*String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "This device will be permanently removed. Are you sure?";
			}
			 */
			//if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage")){
				Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Device Excluded Pop Up message displayed incorrectly");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device Excluded Pop Up not displayed");
		}
		return flag;
	}
	public static boolean verifyRemoveDevicePopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");


		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "RemoveDevicePopupTitle", 3)) {
			Keyword.ReportStep_Pass(testCase, "Remove Device Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "This device will be permanently removed. Are you sure?";
			}

			if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "Remove Device Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Remove Device Pop Up message not correctly displayed. Expected: '" + message + "'. Displayed : (Refer Image)");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Remove device Pop Up not displayed");
		}
		return flag;
	}

	public static boolean ClickRetryOnDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "RetryOption");
		return flag;
	}
	public static boolean ClickOKOnDeviceExcludedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmDeviceRemovalButton");
		return flag;
	}
	public static boolean ClickAddNowOnDeviceExcludedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "AddNowOption");
		return flag;
	}
	public static boolean ClickCancelOnDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
		return flag;
	}
	public static boolean ClickNavigateUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "NavigateBack");
		return flag;
	}
	public static boolean ClickCancelOnRemoveDevicePopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "CancelDeviceRemovalButton");
		return flag;
	}
	public static boolean ClickOkOnRemoveDevicePopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmDeviceRemovalButton");
		return flag;
	}
	public static boolean ClickZwaveMenuFromGlobalDrawer(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "ZwaveMenu");
		return flag;
	}

	public static boolean ClickSwitchSettingFromZwaveUtilities(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "SwitchSettingsMenu");
		return flag;
	}

	public static boolean ClickDeleteFromSettings(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "DeleteButton");
		return flag;
	}

	public static boolean NamingSwitch(TestCases testCase,String value) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "NameEditField", 5)) {
			flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "NameEditField",
					value);
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects,testCase, "ReturnKeypad");
				flag = flag & MobileUtils.clickOnElement(fieldObjects,testCase, "DoneButtonAfterNaming");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find Zwave Name Text Box");
		}
		return flag;
	}
	public static boolean WaitForNamingScreen(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		MobileUtils.isMobElementExists(fieldObjects, testCase, "NameTheDeviceTitle");
		MobileUtils.isMobElementExists(fieldObjects, testCase, "NameTheDeviceTitle");
		return flag;
	}
	public static boolean TimeOutForNoActivatedDevice(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption");
		flag = MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption");
		flag = MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption");
		flag = MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption");
		flag = MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption");
		return flag;
	}
	public static boolean ClickTryExcludeOnDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "TryExcludeOption");
		return flag;
	}

}
