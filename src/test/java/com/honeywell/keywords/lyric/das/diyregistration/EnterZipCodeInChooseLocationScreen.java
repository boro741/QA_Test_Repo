package com.honeywell.keywords.lyric.das.diyregistration;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class EnterZipCodeInChooseLocationScreen extends Keyword {

	private TestCases testCase;
	public ArrayList<String> expectedOption;
	public boolean flag = true;

	public EnterZipCodeInChooseLocationScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedOption) {
		this.testCase = testCase;
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
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
