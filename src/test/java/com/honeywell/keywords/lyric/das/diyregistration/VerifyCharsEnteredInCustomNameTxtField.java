package com.honeywell.keywords.lyric.das.diyregistration;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class VerifyCharsEnteredInCustomNameTxtField extends Keyword {

	private TestCases testCase;
	private ArrayList<String> textEnteredInCustomNameTxtField;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyCharsEnteredInCustomNameTxtField(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user should not be allowed to enter more than \"(.*)\" charcters in \"(.*)\" screen$")
	public boolean keywordSteps() {

		System.out.println("###########LOCATION1_NAME: " + inputs.getInputValue("LOCATION1_NAME"));
		System.out.println("###########LOCATION1_CAMERA1_NAME: " + inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
		int maxAllowedCharLength = Integer.parseInt(textEnteredInCustomNameTxtField.get(0));
		if (textEnteredInCustomNameTxtField.get(1).equalsIgnoreCase("NAME YOUR LOCATION")) {
			DIYRegistrationUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs,
					maxAllowedCharLength, inputs.getInputValue("LOCATION1_NAME"));
		} else if (textEnteredInCustomNameTxtField.get(1).equalsIgnoreCase("NAME YOUR DEVICE")) {
			DIYRegistrationUtils.verifyIfMaxCharsEnteredInCustomNameTxtField(testCase, inputs,
					maxAllowedCharLength, inputs.getInputValue("LOCATION1_CAMERA1_NAME"));

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
