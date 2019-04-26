package com.honeywell.lyric.das.utils;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.NameEditAccountScreen;

public class NameEditAccountUtils {

	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(duration));
			EditAccountScreen eas = new EditAccountScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (eas.isProgressBarVisible()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
								return true;
							} else {
								return false;
							}
						}
						case "CHECKING SPINNER": {
							if (eas.isProgressBarVisible()) {
								System.out.println("Waiting for Checking loading spinner to disappear");
								return true;
							} else {
								return true;
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

	public static boolean clearTextDisplayedInNameEditAccountTextFields(TestCases testCase, TestCaseInputs inputs,
			String textFieldToBeCleared) {
		boolean flag = true;
		NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
		switch (textFieldToBeCleared.toUpperCase()) {
		case "FIRST NAME TEXT FIELD": {
			flag &= neas.clearTextDisplayedInFirstNameTextFieldInNameEditAccountScreen();
			break;
		}
		case "LAST NAME TEXT FIELD": {
			flag &= neas.clearTextDisplayedInLastNameTextFieldInNameEditAccountScreen();
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + textFieldToBeCleared);
		}
		}
		return flag;
	}

	public static boolean enterFirstNameInNameEditAccountScreen(TestCases testCase, TestCaseInputs inputs,
			String firstNameValueInput) {
		boolean flag = true;
		NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
		flag &= neas.enterFirstNameValueInNameEditAccountScreen(inputs, firstNameValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in First Name text field:" + firstNameValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in First Name text field:" + firstNameValueInput);
		}
		return flag;
	}

	public static boolean enterLastNameInNameEditAccountScreen(TestCases testCase, TestCaseInputs inputs,
			String lastNameValueInput) {
		boolean flag = true;
		NameEditAccountScreen neas = new NameEditAccountScreen(testCase);
		flag &= neas.enterLastNameValueInNameEditAccountScreen(inputs, lastNameValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Last Name text field:" + lastNameValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Last text field:" + lastNameValueInput);
		}
		return flag;
	}

	
}