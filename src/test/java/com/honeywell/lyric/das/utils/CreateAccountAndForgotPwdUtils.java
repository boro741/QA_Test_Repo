package com.honeywell.lyric.das.utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivateAccountScreen;
import com.honeywell.screens.CreateAccountScreen;

public class CreateAccountAndForgotPwdUtils {

	public static boolean enterFirstNameInFirstNameTxtFieldInCreateAccountScreen(TestCases testCase,
			TestCaseInputs inputs, String firstName) {
		boolean flag = true;
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		flag &= cas.enterFirstNameTxtInCreateAccountScreen(inputs, firstName);
		return flag;
	}

	public static boolean enterLastNameInLastNameTxtFieldInCreateAccountScreen(TestCases testCase,
			TestCaseInputs inputs, String lastName) {
		boolean flag = true;
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		flag &= cas.enterLastNameTxtInCreateAccountScreen(inputs, lastName);
		return flag;
	}

	public static boolean enterEmailInEmailTxtFieldInCreateAccountScreen(TestCases testCase, TestCaseInputs inputs,
			String email) {
		boolean flag = true;
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		flag &= cas.enterEmailTxtInCreateAccountScreen(inputs, email);
		return flag;
	}

	public static boolean enterPasswordInPasswordTxtFieldInCreateAccountScreen(TestCases testCase,
			TestCaseInputs inputs, String password) {
		boolean flag = true;
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		flag &= cas.enterPasswordTxtInCreateAccountScreen(inputs, password);
		return flag;
	}

	public static boolean enterVerifyPasswordInVerifyPasswordTxtFieldInCreateAccountScreen(TestCases testCase,
			TestCaseInputs inputs, String verifyPassword) {
		boolean flag = true;
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		flag &= cas.enterVerifyPasswordTxtInCreateAccountScreen(inputs, verifyPassword);
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInCustomNameTxtField(TestCases testCase, TestCaseInputs inputs,
			int maxAllowedCharsLength, String enteredMaxChars) {
		CreateAccountScreen cas = new CreateAccountScreen(testCase);
		boolean flag = true;
		String valueDisplayedInCustomNameTxtField = cas.getValueDisplayedInFirstNameTxtField();

		if (enteredMaxChars.length() <= maxAllowedCharsLength) {

			if (valueDisplayedInCustomNameTxtField.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInCustomNameTxtField + " is correctly displayed with character length: "
								+ valueDisplayedInCustomNameTxtField.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, valueDisplayedInCustomNameTxtField
						+ " entered is of character length: " + valueDisplayedInCustomNameTxtField.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {

			if ((!valueDisplayedInCustomNameTxtField.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInCustomNameTxtField.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInCustomNameTxtField
								+ " with max allowed character length: " + valueDisplayedInCustomNameTxtField.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInCustomNameTxtField
								+ " with max allowed character length: " + valueDisplayedInCustomNameTxtField.length());
			}
		}
		return flag;
	}

	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			ActivateAccountScreen aas = new ActivateAccountScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "PROGRESS BAR": {
							if (aas.isProgressBarVisible()) {
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
}
