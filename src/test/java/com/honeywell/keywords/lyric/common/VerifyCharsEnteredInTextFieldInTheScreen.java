package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.AboutTheAppUtils;
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.lyric.das.utils.CreateAccountAndForgotPwdUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;

public class VerifyCharsEnteredInTextFieldInTheScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> textEnteredInCustomNameTxtField;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyCharsEnteredInTextFieldInTheScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> textEnteredInCustomNameTxtField) {
		this.testCase = testCase;
		this.textEnteredInCustomNameTxtField = textEnteredInCustomNameTxtField;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be allowed to enter more than \"(.*)\" characters in \"(.*)\" in the \"(.*)\" screen$")
	public boolean keywordSteps() {
		int maxAllowedCharLength = Integer.parseInt(textEnteredInCustomNameTxtField.get(0));
		inputs.setInputValue("MAX_ALLOWED_CHARS_LENGTH", maxAllowedCharLength);
		System.out.println("#####MAX_ALLOWED_CHARS_LENGTH: " + inputs.getInputValue("MAX_ALLOWED_CHARS_LENGTH"));
		if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("NAME YOUR LOCATION")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "LOCATION TEXT FIELD": {
				System.out.println("###########LOCATION1_NAME: " + inputs.getInputValue("LOCATION1_NAME"));
				DIYRegistrationUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs, maxAllowedCharLength,
						inputs.getInputValue("LOCATION1_NAME"));
				break;
			}
			}
		} else if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("NAME YOUR DEVICE")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "DEVICE TEXT FIELD": {
				System.out.println(
						"###########LOCATION1_CAMERA1_NAME: " + inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
				DIYRegistrationUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs, maxAllowedCharLength,
						inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
				break;
			}
			}
		} else if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("EDIT ADDRESS")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "LOCATION NAME": {
				AddressUtils.verifyIfMaxCharsEnteredInLocationNameTxtFieldInEditAddressScreen(testCase, inputs,
						maxAllowedCharLength,
						inputs.getInputValue("TEXT_ENTERED_IN_LOCATION_NAME_IN_EDIT_ADDRESS_SCREEN"));
				break;
			}
			}
		} else if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("EDIT ACCOUNT")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "FIRST NAME": {
				EditAccountUtils.verifyIfMaxCharsEnteredInFirstNameTxtFieldInEditAccountScreen(testCase, inputs,
						maxAllowedCharLength, inputs.getInputValue("UPDATED_FIRST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			case "LAST NAME": {
				EditAccountUtils.verifyIfMaxCharsEnteredInLastNameTxtFieldInEditAccountScreen(testCase, inputs,
						maxAllowedCharLength, inputs.getInputValue("UPDATED_LAST_NAME_IN_EDIT_ACCOUNT"));
				break;
			}
			}
		} else if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("APP FEEDBACK")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "FEEDBACK TEXT FIELD": {
				AboutTheAppUtils.verifyIfMaxCharsEnteredInFeedbackTxtFieldInAppFeedbackScreen(testCase, inputs,
						maxAllowedCharLength, inputs.getInputValue("TEXT_ENTERED_IN_FEEDBACK_TEXT_FIELD"));
				break;
			}
			}
		} else if (textEnteredInCustomNameTxtField.get(2).equalsIgnoreCase("CREATE ACCOUNT")) {
			switch (textEnteredInCustomNameTxtField.get(1).toUpperCase()) {
			case "FIRST NAME": {
				CreateAccountAndForgotPwdUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs,
						maxAllowedCharLength, inputs.getInputValue("FIRST_NAME_WITH_MAX_CHARS"));
				break;
			}
			case "LAST NAME": {
				CreateAccountAndForgotPwdUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs,
						maxAllowedCharLength, inputs.getInputValue("LAST_NAME_WITH_MAX_CHARS"));
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
