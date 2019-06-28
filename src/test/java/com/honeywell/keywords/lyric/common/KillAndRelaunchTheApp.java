package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DashboardUtils;

public class KillAndRelaunchTheApp extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public KillAndRelaunchTheApp(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user kills and relaunches the app$")
	public boolean keywordSteps() {

		DashboardUtils.killAndRelaunchTheApp(testCase, inputs);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
