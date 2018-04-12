package com.honeywell.keywords.lyric.das.accessories;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.RelayUtils;

public class ResetSensorRelay extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public ResetSensorRelay(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^reset relay as precondition$")
	public boolean keywordSteps() throws KeywordException {
		try {
			RelayUtils.RSIAllSensorOff();
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
