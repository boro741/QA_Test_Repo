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

public class MembershipCancel extends Keyword  {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	
	public MembershipCancel(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user clicks on the (.*) button from the (.*) screen$")
	public boolean keywordSteps() throws KeywordException{
		
		MembershipDetailsScreen mds = new MembershipDetailsScreen(testCase);
		flag = flag & mds.clickOnUnsubscribe(testCase, inputs);
		return flag;
	}
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
