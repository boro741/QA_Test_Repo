package com.honeywell.keywords.lyric.das.commandandcontrol;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.LyricUtils;

public class GetLocationTime extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public GetLocationTime(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user gets location time$")
	public boolean keywordSteps() {
		String locationTime = LyricUtils.getLocationTime(testCase, inputs, "TIMEINHHMMFORMAT");
		System.out.println("##########locationTime: " + locationTime);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
