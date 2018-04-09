package com.honeywell.keywords.jasper.Setpoint;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.lyric.utils.InputVariables;

public class VerifyHoldUntilTimeIsSet extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag=true;
	
	public VerifyHoldUntilTimeIsSet(TestCases testCase, TestCaseInputs inputs) {
		this.testCase=testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify hold until time is set to time lesser than 12 hours$")
	public boolean keywordSteps() throws KeywordException {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
		{
			flag = flag & JasperAdhocOverride.verifyHoldUntilTimeIsSet(testCase,"", true);
		}
		else
		{
			flag = flag & JasperAdhocOverride.verifyHoldUntilTimeIsSet(testCase, inputs.getInputValue(InputVariables.HOLD_UNTIL_TIME_DATE), true); 
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
