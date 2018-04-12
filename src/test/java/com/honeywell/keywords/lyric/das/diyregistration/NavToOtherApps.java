package com.honeywell.keywords.lyric.das.diyregistration;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class NavToOtherApps extends Keyword {

	private TestCases testCase;
	public boolean flag = true;

	public NavToOtherApps(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user navigates to other apps and navigates back to Lyric app$")
	public boolean keywordSteps() {
		
		DIYRegistrationUtils.minimizeAndMaximizeTheApp(testCase);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
