package com.honeywell.keywords.CameraMembership;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.MembershipCheckoutScreen;

public class MembershipCheckout extends Keyword  {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	
	public MembershipCheckout(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user enters (.*) payment details and selects pay now option$")
	public boolean keywordSteps() throws KeywordException{
		
		MembershipCheckoutScreen mcs = new MembershipCheckoutScreen(testCase);
		String PaymentValue = parameters.get(0);
		
		if (PaymentValue.equalsIgnoreCase("valid")) {
		flag = flag & mcs.EnterValidCheckoutValues(testCase, inputs);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		else if (PaymentValue.equalsIgnoreCase("invalid"))
		{
			flag = flag & mcs.EnterInValidCheckoutValues(testCase, inputs);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else
		{
			flag = false;
		Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
				"Invalid input : " + PaymentValue);
		}
		return flag;
	}
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
