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
import com.honeywell.screens.AboutTheAppScreen;

public class AboutTheAppUtils {

	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (atas.isProgressBarVisible()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
								return true;
							} else {
								return false;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + elementProgressBar);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Progress bar loading spinner did not disapper after waiting for " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean enterFeedbackText(TestCases testCase, TestCaseInputs inputs, String inputText) {
		boolean flag = true;
		AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
		flag &= atas.enterTextInAppFeedbackTextField(inputText);
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully entered text in Feedback text field:" + inputText);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Feedback text field:" + inputText);
		}
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInFeedbackTxtFieldInAppFeedbackScreen(TestCases testCase,
			TestCaseInputs inputs, int maxAllowedCharsLength, String enteredMaxChars) {
		AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
		boolean flag = true;
		String valueDisplayedInFeedbackTextFieldInAppFeedbackScreen = atas.getEnteredTextInAppFeedbackTextField();
		if (enteredMaxChars.length() <= maxAllowedCharsLength) {
			if (valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInFeedbackTextFieldInAppFeedbackScreen
								+ " is correctly displayed with character length: "
								+ valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInFeedbackTextFieldInAppFeedbackScreen + " entered is of character length: "
								+ valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {
			if ((!valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInFeedbackTextFieldInAppFeedbackScreen
								+ " with max allowed character length: "
								+ valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInFeedbackTextFieldInAppFeedbackScreen
								+ " with max allowed character length: "
								+ valueDisplayedInFeedbackTextFieldInAppFeedbackScreen.length());
			}
		}
		return flag;
	}
	
	public static boolean minimizeAndMaximizeTheApp(TestCases testCase) {
		boolean flag = true;
		MobileUtils.minimizeApp(testCase, 5);
		return flag;
	}
}