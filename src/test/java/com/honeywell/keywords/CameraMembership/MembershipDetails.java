package com.honeywell.keywords.CameraMembership;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.MembershipDetailsScreen;

public class MembershipDetails extends Keyword  {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public MembershipDetails(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user accepts the checkout view$")
	public boolean keywordSteps() throws KeywordException{
		
		MembershipDetailsScreen mds = new MembershipDetailsScreen(testCase);
		flag = flag & mds.clickOnProceedToCheckout(testCase, inputs);
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
