package com.honeywell.keywords.lyric.das.commandandcontrol;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASAlarmUtils;

public class VerifyIfAlarmScreenIsDisplayed extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyIfAlarmScreenIsDisplayed(TestCases testCase, TestCaseInputs inputs) {
		// this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with Alarm screen$")
	public boolean keywordSteps() {
		if (!DASAlarmUtils.verifyAlarmScreenDisplayed(testCase)) {
			Keyword.ReportStep_Pass(testCase, "Alarm screen is not displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Alarm screen is displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
