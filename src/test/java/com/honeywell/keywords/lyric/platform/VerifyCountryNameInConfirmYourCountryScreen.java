package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DashboardUtils;

public class VerifyCountryNameInConfirmYourCountryScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputName;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public VerifyCountryNameInConfirmYourCountryScreen(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" country selected in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputName.get(1).equalsIgnoreCase("PLEASE CONFIRM YOUR COUNTRY")) {
			switch (inputName.get(0).toUpperCase()) {
			case "DEFAULT": {
				flag &= DashboardUtils.verifyDefaultCountryDisplayedInConfirmYourCountryScreen(testCase, inputs,
						inputs.getInputValue("COUNTRY_DISPLAYED_IN_ADD_NEW_DEVICE_SCREEN"));
				break;
			}
			case "CURRENT": {
				flag &= DashboardUtils.verifyDefaultCountryDisplayedInConfirmYourCountryScreen(testCase, inputs,
						inputs.getInputValue("DEFAULT_COUNTRY_IN_ADDRESS_SCREEN"));
				break;
			}
			default: {
				flag = false;
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