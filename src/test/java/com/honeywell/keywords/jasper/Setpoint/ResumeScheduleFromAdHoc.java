package com.honeywell.keywords.jasper.Setpoint;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperAdhocOverride;

public class ResumeScheduleFromAdHoc extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ResumeScheduleFromAdHoc(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user resumes the schedule$")
	public boolean keywordSteps() throws KeywordException {
		flag = flag & JasperAdhocOverride.resumeScheduleFromAdHoc(testCase);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}

