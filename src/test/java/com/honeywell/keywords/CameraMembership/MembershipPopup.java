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
import com.honeywell.screens.MembershipPopupScreen;

public class MembershipPopup extends Keyword  {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	
	public MembershipPopup(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user clicks on the (.*) option from the (.*) popup$")
	public boolean keywordSteps() throws KeywordException{
		
		MembershipPopupScreen mps = new MembershipPopupScreen(testCase);
				
		if (parameters.get(0).equalsIgnoreCase("Done") && (parameters.get(1).equalsIgnoreCase("Cancel"))) {
			flag = flag & mps.clickOnPopupDone(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "Cancel Done Popup Success");
		}
		else if (parameters.get(0).equalsIgnoreCase("Ok") && (parameters.get(1).equalsIgnoreCase("Membership Canceled"))) {
			flag = flag & mps.clickOnPopupOk(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "Membership Cancellation Success");
		}
		else
		{
			flag = false;
		Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
				"Invalid input " + parameters.get(0));
		}
		return flag;
	}
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
