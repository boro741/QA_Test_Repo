package com.honeywell.keywords.lyric.das.diyregistration;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class EnterZipCodeInChooseLocationScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;

	public EnterZipCodeInChooseLocationScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user inputs \"(.*)\"$")
	public boolean keywordSteps() {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		if (dasDIY.isZipCodeTextFieldVisible()) {
			flag = flag & dasDIY.clearEnteredTextInZipCodeTextField();
			flag = flag & dasDIY.enterZipCode(expectedOption.get(0));
		} else if (inputs.isRunningOn("Perfecto") && inputs.getInputValue("SELECTED_COUNTRY") != null
				&& !inputs.getInputValue("SELECTED_COUNTRY").isEmpty()
				&& inputs.getInputValue("SELECTED_COUNTRY").equalsIgnoreCase("United States")) {
			Keyword.ReportStep_Pass(testCase, "Country Selected is: " + inputs.getInputValue("SELECTED_COUNTRY")
					+ ". Hence Confirm Your Zip Code screen will not be displayed.");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to navigate to Confirm Your Zip Code screen");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
