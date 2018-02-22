package com.honeywell.keywords.lyric.das.chil;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class DisablePasscodeThroughCHIL extends Keyword {

	private TestCases testCase;
    private TestCaseInputs inputs;
	public boolean flag = true;

	public DisablePasscodeThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
	    this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user disables the passcode through CHIL$")
	public boolean keywordSteps() {
		try
		{
		@SuppressWarnings("resource")
		CHILUtil chUtil = new CHILUtil(inputs);
		if (chUtil.getConnection()) {
			int result = chUtil.putForgotPasscode();
			System.out.println(result);
			if (result == 200) {
				Keyword.ReportStep_Pass(testCase, "Successfully forgot passcode");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to forget passcode");
			}
		}
		}
		catch(Exception e)
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
