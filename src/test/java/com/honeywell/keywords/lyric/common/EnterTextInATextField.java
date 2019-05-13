package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.Random;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.das.utils.AboutTheAppUtils;
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.lyric.das.utils.CreateAccountAndForgotPwdUtils;
import com.honeywell.lyric.das.utils.DASManageUsersUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;
import com.honeywell.lyric.das.utils.FAQsUtils;
import com.honeywell.lyric.das.utils.LoginUtils;
import com.honeywell.lyric.das.utils.NameEditAccountUtils;
import com.honeywell.screens.LocationDetailsScreen;

public class EnterTextInATextField extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputName;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public EnterTextInATextField(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputName) {
		this.testCase = testCase;
		this.inputName = inputName;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user inputs \"(.+)\" in \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputName.get(2).equalsIgnoreCase("INVITE NEW USER")) {
			switch (inputName.get(1).toUpperCase()) {
			case "EMAIL TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("LOGGED IN USERS EMAIL ADDRESS")) {
					flag &= DASManageUsersUtils.inputEmailAddressInInviteUserScreen(testCase, inputs,
							inputs.getInputValue("USERID"));
				} else {
					inputs.setInputValue("INVITED_USERS_EMAIL_ADDRESS", inputName.get(0));
					flag &= DASManageUsersUtils.inputEmailAddressInInviteUserScreen(testCase, inputs, inputName.get(0));
				}
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("PLEASE CONFIRM YOUR COUNTRY")) {
			switch (inputName.get(1).toUpperCase()) {
			case "SEARCH TEXT FIELD": {
				inputs.setInputValue("SELECTED_COUNTRY", inputName.get(0));
				flag &= DashboardUtils.enterCountryNameAndSelectItInConfirmYourCountryScreen(testCase, inputs,
						inputName.get(0));
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("EDIT ADDRESS")) {
			switch (inputName.get(1).toUpperCase()) {
			case "LOCATION NAME TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("PREVIOUS VALUE")) {
					flag &= AddressUtils.enterLocationNameInEditAddressScreen(testCase, inputs,
							inputs.getInputValue("LOCATION_NAME"));
				} else if (inputName.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					Random rand = new Random();
					int randomInt = rand.nextInt(100);
					String specialCharsInLocationNameTxtField = "&#$" + inputs.getInputValue("LOCATION_NAME")
							+ randomInt + "%^&*";
					inputs.setInputValue("TEXT_ENTERED_WITH_SPECIAL_CHARACTERS_IN_LOCATION_NAME_TEXT_FIELD",
							specialCharsInLocationNameTxtField);
					flag &= AddressUtils.enterLocationNameInEditAddressScreen(testCase, inputs,
							specialCharsInLocationNameTxtField);
				} else if (inputName.get(0).equalsIgnoreCase("EMPTY SPACES")) {
					String enterEmptySpaceBeforeAndAfterTextInLocationNameTxtField = " "
							+ inputs.getInputValue("LOCATION_NAME") + " ";
					inputs.setInputValue("TEXT_ENTERED_WITH_EMPTY_SPACES_IN_LOCATION_NAME_TEXT_FIELD",
							enterEmptySpaceBeforeAndAfterTextInLocationNameTxtField);
					flag &= AddressUtils.enterLocationNameInEditAddressScreen(testCase, inputs,
							enterEmptySpaceBeforeAndAfterTextInLocationNameTxtField);
				} else {
					inputs.setInputValue("TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN", inputName.get(0));
					flag &= AddressUtils.enterLocationNameInEditAddressScreen(testCase, inputs, inputName.get(0));
				}
				break;
			}
			case "ADDRESS TEXT FIELD": {
				inputs.setInputValue("TEXT_ENTERED_IN_ADDRESS_IN_EDIT_ADDRESS_SCREEN", inputName.get(0));
				flag &= AddressUtils.enterAddressInEditAddressScreen(testCase, inputs, inputName.get(0));
				break;
			}
			case "CITY TEXT FIELD": {
				inputs.setInputValue("TEXT_ENTERED_IN_CITY_IN_EDIT_ADDRESS_SCREEN", inputName.get(0));
				flag &= AddressUtils.enterCityInEditAddressScreen(testCase, inputs, inputName.get(0));
				break;
			}
			case "STATE TEXT FIELD": {
				inputs.setInputValue("TEXT_ENTERED_IN_STATE_IN_EDIT_ADDRESS_SCREEN", inputName.get(0));
				flag &= AddressUtils.enterStateInEditAddressScreen(testCase, inputs, inputName.get(0));
				break;
			}
			case "POSTAL CODE TEXT FIELD": {
				inputs.setInputValue("TEXT_ENTERED_IN_POSTAL_CODE_IN_EDIT_ADDRESS_SCREEN", inputName.get(0));
				flag &= AddressUtils.enterPostalCodeInEditAddressScreen(testCase, inputs, inputName.get(0));
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("EDIT ACCOUNT")) {
			switch (inputName.get(1).toUpperCase()) {
			case "FIRST NAME TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("PREVIOUS VALUE")) {
					flag &= EditAccountUtils.enterFirstNameInEditAccountScreen(testCase, inputs,
							inputs.getInputValue("FIRST_NAME_IN_EDIT_ACCOUNT"));
				} else if (inputName.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					Random rand = new Random();
					int randomInt = rand.nextInt(100);
					String specialCharsInFirstNameTxtField = "&#$" + inputs.getInputValue("FIRST_NAME_IN_EDIT_ACCOUNT")
							+ randomInt + "%^&*";
					System.out.println("#########specialCharsInFirstNameTxtField: " + specialCharsInFirstNameTxtField);
					inputs.setInputValue("UPDATED_FIRST_NAME_IN_EDIT_ACCOUNT", specialCharsInFirstNameTxtField);
					flag &= EditAccountUtils.enterFirstNameInEditAccountScreen(testCase, inputs,
							specialCharsInFirstNameTxtField);
				} else {
					inputs.setInputValue("UPDATED_FIRST_NAME_IN_EDIT_ACCOUNT", inputName.get(0));
					flag &= EditAccountUtils.enterFirstNameInEditAccountScreen(testCase, inputs, inputName.get(0));
				}
				break;
			}
			case "LAST NAME TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("PREVIOUS VALUE")) {
					flag &= EditAccountUtils.enterLastNameInEditAccountScreen(testCase, inputs,
							inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT"));
				} else if (inputName.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					Random rand = new Random();
					int randomInt = rand.nextInt(100);
					String specialCharsInLastNameTxtField = "&#$" + inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT")
							+ randomInt + "%^&*";
					System.out.println("#########specialCharsInLastNameTxtField: " + specialCharsInLastNameTxtField);
					inputs.setInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT", specialCharsInLastNameTxtField);
					flag &= EditAccountUtils.enterLastNameInEditAccountScreen(testCase, inputs,
							specialCharsInLastNameTxtField);
				} else {
					inputs.setInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT", inputName.get(0));
					flag &= EditAccountUtils.enterLastNameInEditAccountScreen(testCase, inputs, inputName.get(0));
				}
			}
				break;
			}
		} else if (inputName.get(2).equalsIgnoreCase("CHANGE PASSWORD")) {
			switch (inputName.get(1).toUpperCase()) {
			case "OLD PASSWORD TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("VALID OLD PASSWORD")) {
					flag &= EditAccountUtils.enterOldPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("UPDATED OLD PASSWORD")
						|| inputName.get(0).equalsIgnoreCase("UPDATED OLD PASSWORD WITH SPECIAL CHARACTERS")) {
					flag &= EditAccountUtils.enterOldPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("UPDATED_PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("INCORRECT OLD PASSWORD")
						|| inputName.get(0).equalsIgnoreCase("INVALID OLD PASSWORD")) {
					flag &= EditAccountUtils.enterOldPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("INCORRECT_PASSWORD"));
				}
				break;
			}
			case "NEW PASSWORD TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("VALID NEW PASSWORD FORMAT")
						|| inputName.get(0).equalsIgnoreCase("VALID NEW PASSWORD FORMAT WITH SPECIAL CHARACTERS")) {
					flag &= EditAccountUtils.enterNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("UPDATED_PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("PREVIOUS NEW PASSWORD FORMAT VALUE")) {
					flag &= EditAccountUtils.enterNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("INVALID NEW PASSWORD FORMAT")) {
					flag &= EditAccountUtils.enterNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("INCORRECT_PASSWORD"));
				}
				break;
			}
			case "VERIFY NEW PASSWORD TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("VALID VERIFY NEW PASSWORD FORMAT") || inputName.get(0)
						.equalsIgnoreCase("VALID VERIFY NEW PASSWORD FORMAT WITH SPECIAL CHARACTERS")) {
					flag &= EditAccountUtils.enterVerifyNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("UPDATED_PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("PREVIOUS VERIFY NEW PASSWORD FORMAT VALUE")) {
					flag &= EditAccountUtils.enterVerifyNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("PASSWORD"));
				} else if (inputName.get(0).equalsIgnoreCase("INVALID VERIFY NEW PASSWORD FORMAT")) {
					flag &= EditAccountUtils.enterVerifyNewPasswordInChangePasswordScreen(testCase, inputs,
							inputs.getInputValue("INCORRECT_PASSWORD"));
				}
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("APP FEEDBACK")) {
			switch (inputName.get(1).toUpperCase()) {
			case "FEEDBACK TEXT FIELD": {
				inputs.setInputValue("TEXT_ENTERED_IN_FEEDBACK_TEXT_FIELD", inputName.get(0));
				flag &= AboutTheAppUtils.enterFeedbackText(testCase, inputs, inputName.get(0));
				return flag;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("FAQs")) {
			switch (inputName.get(1).toUpperCase()) {
			case "HELP TEXT": {
				flag &= FAQsUtils.enterHelpTextInFAQsScreen(testCase, inputs, inputName.get(0));
				return flag;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("CREATE ACCOUNT")) {
			switch (inputName.get(1).toUpperCase()) {
			case "FIRST NAME TEXT FIELD": {
				inputs.setInputValue("FIRST_NAME_WITH_MAX_CHARS", inputName.get(0));
				flag &= CreateAccountAndForgotPwdUtils.enterFirstNameInFirstNameTxtFieldInCreateAccountScreen(testCase,
						inputs, inputs.getInputValue("FIRST_NAME_WITH_MAX_CHARS"));
				break;
			}
			case "LAST NAME TEXT FIELD": {
				inputs.setInputValue("LAST_NAME_WITH_MAX_CHARS", inputName.get(0));
				flag &= CreateAccountAndForgotPwdUtils.enterLastNameInLastNameTxtFieldInCreateAccountScreen(testCase,
						inputs, inputName.get(0));
				break;
			}
			case "EMAIL TEXT FIELD": {
				String value = inputName.get(0).split("@")[0];
				String value1 = inputName.get(0).split("@")[1];
				// System.out.println(value);
				Random rand = new Random();
				int rn = rand.nextInt(10000);
				value = value + rn + "@" + value1;
				inputs.setInputValue("EMAIL_TEXT", value);
				// System.out.println(value);
				flag &= CreateAccountAndForgotPwdUtils.enterEmailInEmailTxtFieldInCreateAccountScreen(testCase, inputs,
						value);
				break;
			}

			case "EMAIL FIELD": {
				inputs.setInputValue("EMAIL_TEXT", inputName.get(0));
				flag &= CreateAccountAndForgotPwdUtils.enterEmailInEmailTxtFieldInCreateAccountScreen(testCase, inputs,
						inputName.get(0));
				break;
			}

			case "PASSWORD TEXT FIELD": {
				inputs.setInputValue("PASSWORD_TEXT", inputName.get(0));
				flag &= CreateAccountAndForgotPwdUtils.enterPasswordInPasswordTxtFieldInCreateAccountScreen(testCase,
						inputs, inputName.get(0));
				break;
			}
			case "VERIFY PASSWORD TEXT FIELD": {
				inputs.setInputValue("VERIFY_PASSWORD_TEXT", inputName.get(0));
				flag &= CreateAccountAndForgotPwdUtils.enterVerifyPasswordInVerifyPasswordTxtFieldInCreateAccountScreen(
						testCase, inputs, inputName.get(0));

				if (flag) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						// MobileUtils.pressBackButton(testCase, "Keyboard minimized");
						// MobileUtils.hideKeyboard(testCase.getMobileDriver(), "Keyboard minimized");
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
					} else {
						// ios
						MobileUtils.clickOnElement(testCase, "Name", "Done");
					}
				}
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("LOGIN")) {
			switch (inputName.get(1).toUpperCase()) {
			case "EMAIL TEXT FIELD": {
				inputs.setInputValue("EMAIL_ID", inputName.get(0));
				flag &= LoginUtils.enterEmailIdInLoginScreen(testCase, inputs, inputName.get(0));
				break;
			}
			case "PASSWORD TEXT FIELD": {
				inputs.setInputValue("PASSWORD_FIELD", inputName.get(0));
				flag &= LoginUtils.enterPasswordInLoginScreen(testCase, inputs, inputName.get(0));
				if (flag) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						// MobileUtils.pressBackButton(testCase, "Keyboard minimized");
						MobileUtils.hideKeyboard(testCase.getMobileDriver(), "Keyboard minimized");
					} else {
						// ios
						MobileUtils.clickOnElement(testCase, "Name", "Go");
					}
				}
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("LOCATION DETAILS")) {
			switch (inputName.get(1).toUpperCase()) {
			case "ZIP CODE": {
				LocationDetailsScreen ld = new LocationDetailsScreen(testCase);
				flag &= ld.enterZipCode(inputName.get(0));
				if (flag) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						// MobileUtils.pressBackButton(testCase, "Keyboard minimized");
						MobileUtils.hideKeyboard(testCase.getMobileDriver(), "Keyboard minimized");
					} else {
						// ios
						MobileUtils.clickOnElement(testCase, "Name", "Go");
					}
				}
				break;
			}
			}
		} else if (inputName.get(2).equalsIgnoreCase("LOGIN WITH CREATED CREDENTIALS")) {
			switch (inputName.get(1).toUpperCase()) {
			case "EMAIL TEXT FIELD": {
				flag &= LoginUtils.enterEmailIdInLoginScreen(testCase, inputs, inputs.getInputValue("EMAIL_TEXT"));
				break;
			}
			case "PASSWORD TEXT FIELD": {
				inputs.setInputValue("PASSWORD_FIELD", inputName.get(0));
				flag &= LoginUtils.enterPasswordInLoginScreen(testCase, inputs, inputName.get(0));
				if (flag) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						// MobileUtils.pressBackButton(testCase, "Keyboard minimized");
						MobileUtils.hideKeyboard(testCase.getMobileDriver(), "Keyboard minimized");
					} else {
						// ios
						MobileUtils.clickOnElement(testCase, "Name", "Go");
					}
				}
				break;
			}
		  }
		}
		else if (inputName.get(2).equalsIgnoreCase("NAME EDIT ACCOUNT")) {
			switch (inputName.get(1).toUpperCase()) {
			case "FIRST NAME TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("PREVIOUS VALUE")) {
					flag &= NameEditAccountUtils.enterFirstNameInNameEditAccountScreen(testCase, inputs,
							inputs.getInputValue("FIRST_NAME_IN_NAME_EDIT_ACCOUNT"));
				} else if(inputName.get(0).equalsIgnoreCase("MAX CHARACTERS")) {
					flag &= NameEditAccountUtils.enterFirstNameInNameEditAccountScreen(testCase, inputs, 
							inputs.getInputValue("FIRST_NAME_IN_NAME_EDIT_ACCOUNT"));
				} else if (inputName.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					Random rand = new Random();
					int randomInt = rand.nextInt(100);
					String specialCharsInFirstNameTxtField = "&#$" + inputs.getInputValue("FIRST_NAME_IN_NAME_EDIT_ACCOUNT")
							+ randomInt + "%^&*";
					//System.out.println("#########specialCharsInFirstNameTxtField: " + specialCharsInFirstNameTxtField);
					inputs.setInputValue("UPDATED_FIRST_NAME_IN_NAME_EDIT_ACCOUNT", specialCharsInFirstNameTxtField);
					flag &= NameEditAccountUtils.enterFirstNameInNameEditAccountScreen(testCase, inputs,
							specialCharsInFirstNameTxtField);
				} else {
					inputs.setInputValue("UPDATED_FIRST_NAME_IN_NAME_EDIT_ACCOUNT", inputName.get(0));
					flag &= NameEditAccountUtils.enterFirstNameInNameEditAccountScreen(testCase, inputs, inputName.get(0));
				}
				break;
			}
			case "LAST NAME TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("PREVIOUS VALUE")) {
					flag &= NameEditAccountUtils.enterLastNameInNameEditAccountScreen(testCase, inputs,
							inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT"));
				} else if(inputName.get(0).equalsIgnoreCase("MAX CHARACTERS")) {
					flag &= NameEditAccountUtils.enterLastNameInNameEditAccountScreen(testCase, inputs, 
							inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT"));
				} else if (inputName.get(0).equalsIgnoreCase("SPECIAL CHARACTERS")) {
					Random rand = new Random();
					int randomInt = rand.nextInt(100);
					String specialCharsInLastNameTxtField = "&#$" + inputs.getInputValue("LAST_NAME_IN_EDIT_ACCOUNT")
							+ randomInt + "%^&*";
					//System.out.println("#########specialCharsInFirstNameTxtField: " + specialCharsInLastNameTxtField);
					inputs.setInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT", specialCharsInLastNameTxtField);
					flag &= NameEditAccountUtils.enterLastNameInNameEditAccountScreen(testCase, inputs,
							specialCharsInLastNameTxtField);
				} else {
					inputs.setInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT", inputName.get(0));
					flag &= NameEditAccountUtils.enterLastNameInNameEditAccountScreen(testCase, inputs, inputName.get(0));
				}
				break;
			}
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