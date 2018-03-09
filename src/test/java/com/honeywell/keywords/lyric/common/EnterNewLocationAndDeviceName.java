package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class EnterNewLocationAndDeviceName extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputName;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public EnterNewLocationAndDeviceName(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputName) {
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
	@KeywordStep(gherkins = "^user inputs \"(.*)\" in the \"(.*)\" screen$")
	public boolean keywordSteps() {

		if (inputName.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
			inputs.setInputValue("LOCATION1_NAME", inputName.get(0));
			return DIYRegistrationUtils.inputNewLocationName(testCase, inputName.get(0));
		} else if (inputName.get(1).equalsIgnoreCase("NAME YOUR BASE STATION")) {
			inputs.setInputValue("LOCATION1_CAMERA1_NAME", inputName.get(0));
			return DIYRegistrationUtils.inputNewBaseStationnName(testCase, inputName.get(0));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
