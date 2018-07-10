

package com.honeywell.keywords.CameraMembership;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.MembershipCompleteScreen;
import com.honeywell.CHIL.CHILUtil;

public class MembershipComplete extends Keyword  {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

public ArrayList<String> parameters;
	
	public MembershipComplete(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^When user selects (.*) option from the (.*) screen$")
	public boolean keywordSteps() throws KeywordException{
		
		MembershipCompleteScreen mcs = new MembershipCompleteScreen(testCase);
		flag = flag & mcs.ClickDoneButton(testCase, inputs);
		
		//Below code is to cancel the created subscription explicitly from CHIL
		TestCaseInputs inputs = new TestCaseInputs();
		CHILUtil util;
		
		try {
			util = new CHILUtil(inputs);
			int result  = util.getStripeCustomerAndDeleteSubscription("cus_CMJcPyC6ULPICF", "sk_test_EiqRnwdUj64PX5nfRV9nCumB");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(5000);
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
