package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DashboardUtils;

public class EnterCountryNameInConfirmYourCountryScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputName;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public EnterCountryNameInConfirmYourCountryScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> inputName) {
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
	@KeywordStep(gherkins = "^user enters \"(.+)\" in \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputName.get(2).equalsIgnoreCase("PLEASE CONFIRM YOUR COUNTRY")) {
			switch (inputName.get(1).toUpperCase()) {
			case "SEARCH TEXT FIELD": {
				inputs.setInputValue("SELECTED_COUNTRY", inputName.get(0));
				flag &= DashboardUtils.enterCountryNameAndSelectItInConfirmYourCountryScreen(testCase, inputs,
						inputName.get(0));
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