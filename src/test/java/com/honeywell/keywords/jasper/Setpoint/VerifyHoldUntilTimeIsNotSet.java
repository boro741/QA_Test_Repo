package com.honeywell.keywords.jasper.Setpoint;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.lyric.utils.InputVariables;

public class VerifyHoldUntilTimeIsNotSet extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag=true;
	
	public VerifyHoldUntilTimeIsNotSet(TestCases testCase, TestCaseInputs inputs) {
		this.testCase=testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify hold until time is not set to time greater than 12 hours$")
	public boolean keywordSteps() throws KeywordException {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
		{
		flag = flag & JasperAdhocOverride.verifyHoldUntilTimeIsSet(testCase,"", false);
		flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='Cancel']");
		}
		else
		{
			flag = flag & JasperAdhocOverride.verifyHoldUntilTimeIsSet(testCase,inputs.getInputValue(InputVariables.HOLD_UNTIL_TIME_DATE), false);
			flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//UIAButton[@name='Cancel']");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
