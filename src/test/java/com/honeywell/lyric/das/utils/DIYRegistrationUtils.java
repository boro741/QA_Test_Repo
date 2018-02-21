package com.honeywell.lyric.das.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class DIYRegistrationUtils {
	
	public static boolean waitForLookingForBaseStationProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isLookingForBaseStationProgressBarVisible()) {
							System.out.println("Waiting for Looking for Base station loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Looking for Base station loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for base station loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	public static boolean selectABaseStation(TestCases testCase, TestCaseInputs inputs, String macID) throws Exception {
		boolean flag = true;
		String locatorType, locatorValue;
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String displayedText = dasDIY.getToolBarTitleInRegisterBaseStationScreen();
			if (displayedText.equals("Register Base Station")) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "xpath";
			locatorValue = "//android.widget.TextView[@text='" + macID + "']";
		} else {
			if (dasDIY.isSingleBaseStationDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "name";
			locatorValue = macID;
		}

		if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
			flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
		} else {
			int i = 0;
			while (!MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				String locType;
				String locVal;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					locType = "id";
					locVal = "diy_wld_list";
				} else {
					locType = "xpath";
					locVal = "//XCUIElementTypeCollectionView";
				}
				LyricUtils.scrollUpAList(testCase, locType, locVal);
				i++;
				if (i > 5) {
					break;
				}
			}

			if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "MAC ID : " + macID + " not present");
			}
		}
		return flag;
	}
	
	public static boolean waitForLookingForNetworkConnectionProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isLookingForNetworkConnectionProgressBarVisible()) {
							System.out.println("Waiting for Looking for Wi-Fi list loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Looking for Wi-Fi list loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for Wi-Fi list loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public static boolean waitForConnectingSmartHomeSecurityProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isConnectingSmartHomeSecurityLoadingSpinnerVisible()) {
							System.out
									.println("Waiting for Connecting Smart Home Security loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Connecting Smart Home Security loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Connecting Smart Home Security loading spinner did not disapper after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean createPasscodeAfterDIYRegistration(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DASDIYRegistrationScreens dd = new DASDIYRegistrationScreens(testCase);
		if (dd.isPasscodeTitlePresent()) {
			if (!inputs.isInputAvailable("PASSCODE")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Passcode not provided in inputs");
				return flag;
			}
			flag = flag & dd.clickOnCreatePasscodeButton();
			flag = flag & LyricUtils.createPasscode(testCase, inputs.getInputValue("PASSCODE"));
		} else {
			Keyword.ReportStep_Pass(testCase, "Passcode not required");
		}
		return flag;
	}
}
