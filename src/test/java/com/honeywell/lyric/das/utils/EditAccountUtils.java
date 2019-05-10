package com.honeywell.lyric.das.utils;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.EditAccountScreen;

public class EditAccountUtils {

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

	public static boolean clearTextDisplayedInEditAccountTextFields(TestCases testCase, TestCaseInputs inputs,
			String textFieldToBeCleared) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		switch (textFieldToBeCleared.toUpperCase()) {
		case "FIRST NAME TEXT FIELD": {
			flag &= eas.clearTextDisplayedInFirstNameTextFieldInEditAccountScreen();
			break;
		}
		case "LAST NAME TEXT FIELD": {
			flag &= eas.clearTextDisplayedInLastNameTextFieldInEditAccountScreen();
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + textFieldToBeCleared);
		}
		}
		return flag;
	}

	public static boolean enterFirstNameInEditAccountScreen(TestCases testCase, TestCaseInputs inputs,
			String firstNameValueInput) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		flag &= eas.enterFirstNameValueInEditAccountScreen(inputs, firstNameValueInput);
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

	public static boolean enterLastNameInEditAccountScreen(TestCases testCase, TestCaseInputs inputs,
			String lastNameValueInput) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		flag &= eas.enterLastNameValueInEditAccountScreen(inputs, lastNameValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Last Name text field:" + lastNameValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in First Last text field:" + lastNameValueInput);
		}
		return flag;
	}

	public static boolean verifyFirstNameDisplayedInEditAccountScreen(TestCases testCase, String updatedFirstName) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		System.out.println("*******updatedFirstName: " + updatedFirstName);
		String firstNameInEditAccountScreen;
		firstNameInEditAccountScreen = eas.getFirstNameValueInNameScreen();
		System.out.println("*******firstNameInEditAccountScreen: " + firstNameInEditAccountScreen);
		if (firstNameInEditAccountScreen.equalsIgnoreCase(updatedFirstName)) {
			Keyword.ReportStep_Pass(testCase,
					"First Name displayed in Edit Account Screen is: " + firstNameInEditAccountScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"First Name displayed in Edit Account Screen is: " + firstNameInEditAccountScreen
							+ ", which is not same as the actual first name: " + updatedFirstName);
		}
		return flag;
	}

	public static boolean verifyLastNameDisplayedInEditAccountScreen(TestCases testCase, String updatedLastName) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		System.out.println("*******updatedLastName: " + updatedLastName);
		String lastNameInEditAccountScreen;
		lastNameInEditAccountScreen = eas.getLastNameValueInNameScreen();
		System.out.println("*******lastNameInEditAccountScreen: " + lastNameInEditAccountScreen);
		if (lastNameInEditAccountScreen.equalsIgnoreCase(updatedLastName)) {
			Keyword.ReportStep_Pass(testCase,
					"Last Name displayed in Edit Account Screen is: " + lastNameInEditAccountScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Last Name displayed in Edit Account Screen is: " + lastNameInEditAccountScreen
							+ ", which is not same as the actual first name: " + updatedLastName);
		}
		return flag;
	}
	
	public static boolean verifyFirstAndLastNameDisplayedInEditAccountScreen(TestCases testCase, String updatedFirstName, String updatedLastName) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		String userNameDisplayed = eas.getNameValueInEditAccountScreen();
		System.out.println("*******userNameDisplayed: " + userNameDisplayed);
		String userName[]= userNameDisplayed.split(" ");
		userName[0]= updatedFirstName;
		userName[1]= updatedLastName;
		if(userName[0].equals(updatedFirstName) && userName[1].equals(updatedLastName)) {
			System.out.println("The updated First Name is "+ updatedFirstName + "and the updated Last Name is: " + updatedLastName);
			Keyword.ReportStep_Pass(testCase,
					"The updated First Name is "+ updatedFirstName + "and the updated Last Name is: " + updatedLastName);
		}else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"The updated First Name is "+ updatedFirstName + "and the updated Last Name is: " + updatedLastName);
		}
		return flag;
	}
	
	public static boolean verifyIfMaxCharsEnteredInFirstNameTxtFieldInEditAccountScreen(TestCases testCase,
			TestCaseInputs inputs, int maxAllowedCharsLength, String enteredMaxChars) {
		EditAccountScreen eas = new EditAccountScreen(testCase);
		boolean flag = true;
		System.out.println("*******maxAllowedCharsLength: " + maxAllowedCharsLength);
		System.out.println("*******enteredMaxChars: " + enteredMaxChars);
		String valueDisplayedInFirstNameTxtFieldInEditAccountScreen = eas.getLastNameValueInNameScreen();
		System.out.println("*******valueDisplayedInFirstNameTxtFieldInEditAccountScreen: "
				+ valueDisplayedInFirstNameTxtFieldInEditAccountScreen);
		if (enteredMaxChars.length() <= maxAllowedCharsLength) {
			if (valueDisplayedInFirstNameTxtFieldInEditAccountScreen.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInFirstNameTxtFieldInEditAccountScreen
								+ " is correctly displayed with character length: "
								+ valueDisplayedInFirstNameTxtFieldInEditAccountScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInFirstNameTxtFieldInEditAccountScreen + " entered is of character length: "
								+ valueDisplayedInFirstNameTxtFieldInEditAccountScreen.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {
			if ((!valueDisplayedInFirstNameTxtFieldInEditAccountScreen.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInFirstNameTxtFieldInEditAccountScreen.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInFirstNameTxtFieldInEditAccountScreen
								+ " with max allowed character length: "
								+ valueDisplayedInFirstNameTxtFieldInEditAccountScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInFirstNameTxtFieldInEditAccountScreen
								+ " with max allowed character length: "
								+ valueDisplayedInFirstNameTxtFieldInEditAccountScreen.length());
			}
		}
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInLastNameTxtFieldInEditAccountScreen(TestCases testCase,
			TestCaseInputs inputs, int maxAllowedCharsLength, String enteredMaxChars) {
		EditAccountScreen eas = new EditAccountScreen(testCase);
		boolean flag = true;
		System.out.println("*******maxAllowedCharsLength: " + maxAllowedCharsLength);
		System.out.println("*******enteredMaxChars: " + enteredMaxChars);
		String valueDisplayedInLastNameTxtFieldInEditAccountScreen = eas.getLastNameValueInNameScreen();
		System.out.println("*******valueDisplayedInLastNameTxtFieldInEditAccountScreen: "
				+ valueDisplayedInLastNameTxtFieldInEditAccountScreen);
		if (enteredMaxChars.length() <= maxAllowedCharsLength) {
			if (valueDisplayedInLastNameTxtFieldInEditAccountScreen.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInLastNameTxtFieldInEditAccountScreen
								+ " is correctly displayed with character length: "
								+ valueDisplayedInLastNameTxtFieldInEditAccountScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						valueDisplayedInLastNameTxtFieldInEditAccountScreen + " entered is of character length: "
								+ valueDisplayedInLastNameTxtFieldInEditAccountScreen.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {
			if ((!valueDisplayedInLastNameTxtFieldInEditAccountScreen.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInLastNameTxtFieldInEditAccountScreen.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInLastNameTxtFieldInEditAccountScreen
								+ " with max allowed character length: "
								+ valueDisplayedInLastNameTxtFieldInEditAccountScreen.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInLastNameTxtFieldInEditAccountScreen
								+ " with max allowed character length: "
								+ valueDisplayedInLastNameTxtFieldInEditAccountScreen.length());
			}
		}
		return flag;
	}

	public static boolean enterOldPasswordInChangePasswordScreen(TestCases testCase, TestCaseInputs inputs,
			String oldPasswordValueInput) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		flag &= eas.enterOldPasswordInChangePasswordScreen(inputs, oldPasswordValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Old Password text field:" + oldPasswordValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Old Password text field:" + oldPasswordValueInput);
		}
		return flag;
	}

	public static boolean enterNewPasswordInChangePasswordScreen(TestCases testCase, TestCaseInputs inputs,
			String newPasswordValueInput) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		flag &= eas.enterNewPasswordInChangePasswordScreen(inputs, newPasswordValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in New Password text field:" + newPasswordValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in New Password text field:" + newPasswordValueInput);
		}
		return flag;
	}

	public static boolean enterVerifyNewPasswordInChangePasswordScreen(TestCases testCase, TestCaseInputs inputs,
			String verifyNewPasswordValueInput) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		flag &= eas.enterVerifyNewPasswordInChangePasswordScreen(inputs, verifyNewPasswordValueInput);
		if (flag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully entered text in Verify New Password text field:" + verifyNewPasswordValueInput);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to enter text in Verify New Password text field:" + verifyNewPasswordValueInput);
		}
		return flag;
	}

	public static boolean verifyErrorMsgTextInOldPwdTextField(TestCases testCase, String expectedErrorMsgText) {
		boolean flag = true;
		String errorMsgText;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		if (expectedErrorMsgText.equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
			errorMsgText = eas.getInvalidPasswordFormatLabelBelowOldPwdTextField();
		} else {
			errorMsgText = eas.getErrorMsgDisplayedInOldPwdTextField();
		}
		System.out.println("*********expectedErrorMsgText: " + expectedErrorMsgText);
		System.out.println("*********errorMsgText: " + errorMsgText);
		if (errorMsgText.equalsIgnoreCase(expectedErrorMsgText)) {
			Keyword.ReportStep_Pass(testCase,
					"Error message displayed in Old Password text field is:" + expectedErrorMsgText);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error message displayed in Old Password text field is not:" + expectedErrorMsgText
							+ ". Displayed error message in Old Password text field is: " + errorMsgText);
		}
		return flag;
	}

	public static boolean verifyErrorMsgTextInNewPwdTextField(TestCases testCase, String expectedErrorMsgText) {
		boolean flag = true;
		String errorMsgText;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		if (expectedErrorMsgText.equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
			errorMsgText = eas.getInvalidPasswordFormatLabelBeloNewPwdTextField();
		} else if (expectedErrorMsgText.equalsIgnoreCase("PASSWORDS DON'T MATCH")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				expectedErrorMsgText = "Passwords don't match";
			}
			errorMsgText = eas.getPasswordsDoNotMatchErrorMsgDisplayedInNewPwdTextField();
		} else {
			errorMsgText = eas.getErrorMsgDisplayedInNewPwdTextField();
		}
		System.out.println("*********expectedErrorMsgText: " + expectedErrorMsgText);
		System.out.println("*********errorMsgText: " + errorMsgText.trim());
		if (errorMsgText.trim().equalsIgnoreCase(expectedErrorMsgText)) {
			Keyword.ReportStep_Pass(testCase,
					"Error message displayed in New Password text field is:" + expectedErrorMsgText);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error message displayed in New Password text field is not:" + expectedErrorMsgText
							+ ". Displayed error message in New Password text field is: " + errorMsgText);
		}
		return flag;
	}

	public static boolean verifyErrorMsgTextInVerifyNewPwdTextField(TestCases testCase, String expectedErrorMsgText) {
		boolean flag = true;
		String errorMsgText;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		if (expectedErrorMsgText.equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
			errorMsgText = eas.getInvalidPasswordFormatLabelBelowVerifyNewPwdTextField();
		} else {
			errorMsgText = eas.getErrorMsgDisplayedInVerifyNewPwdTextField();
		}
		System.out.println("*********expectedErrorMsgText: " + expectedErrorMsgText);
		System.out.println("*********errorMsgText: " + errorMsgText);
		if (errorMsgText.equalsIgnoreCase(expectedErrorMsgText)) {
			Keyword.ReportStep_Pass(testCase,
					"Error message displayed in Verify New Password text field is:" + expectedErrorMsgText);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error message displayed in New Password text field is not:" + expectedErrorMsgText
							+ ". Displayed error message in Verify New Password text field is: " + errorMsgText);
		}
		return flag;
	}
	
	public static boolean verifyExistingFirstNameDisplayedInEditAccountScreen(TestCases testCase, String updatedFirstName) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		String firstNameInEditAccountScreen = eas.getFirstNameValueInNameScreen();
		String firstName[]= firstNameInEditAccountScreen.split(" ");
		//System.out.println("The first name is: "+firstName[0]);
		//System.out.println("*******firstNameInEditAccountScreen: " + firstNameInEditAccountScreen);
		if (firstName[0].equalsIgnoreCase(updatedFirstName)) {
			Keyword.ReportStep_Pass(testCase,
					"First Name displayed in Edit Account Screen is: " + firstNameInEditAccountScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"First Name displayed in Edit Account Screen is: " + firstNameInEditAccountScreen
							+ ", which is not same as the actual first name: " + updatedFirstName);
		}
		return flag;
	}
	
	public static boolean verifyExistingLastNameDisplayedInEditAccountScreen(TestCases testCase, String updatedLastName) {
		boolean flag = true;
		EditAccountScreen eas = new EditAccountScreen(testCase);
		String lastNameInEditAccountScreen = eas.getFirstNameValueInNameScreen();
		String lastName[]= lastNameInEditAccountScreen.split(" ");
		//System.out.println("The last name is: "+lastName[1]);
		//System.out.println("*******firstNameInEditAccountScreen: " + firstNameInEditAccountScreen);
		if (lastName[1].equalsIgnoreCase(updatedLastName)) {
			Keyword.ReportStep_Pass(testCase,
					"Last Name displayed in Edit Account Screen is: " + lastNameInEditAccountScreen);
			return flag;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Last Name displayed in Edit Account Screen is: " + lastNameInEditAccountScreen
							+ ", which is not same as the actual last name: " + updatedLastName);
		}
		return flag;
	}
}