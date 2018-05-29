package com.honeywell.keywords.lyric.das.diyregistration;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class ClickOnDoneWithoutEnteringCustomName extends Keyword {

	private TestCases testCase;
	public boolean flag = true;

	public ClickOnDoneWithoutEnteringCustomName(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user clicks on done button without entering any custom name$")
	public boolean keywordSteps() {

		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		flag = flag & dasDIY.clickOnDoneButtonInVirtualKeyboard(testCase);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
