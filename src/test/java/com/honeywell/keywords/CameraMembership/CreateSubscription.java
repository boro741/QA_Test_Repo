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
import com.honeywell.screens.HoneywellMembershipScreen;

public class CreateSubscription extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public String plan;

	public CreateSubscription(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user selects \"(.+)\" plan from the \"(.+)\" screen and selects \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {

		HoneywellMembershipScreen hm = new HoneywellMembershipScreen(testCase);
		if (parameters.get(0).equalsIgnoreCase("Monthly")) {
			flag = flag & hm.clickOnMonthlyPlan(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "Monthly plan subscription success");
		} else if (parameters.get(0).equalsIgnoreCase("Annual")) {
			flag = flag & hm.clickOnAnnualPlan(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "Annual plan subscription success");
		} else if (parameters.get(0).equalsIgnoreCase("No")) {
			flag = flag & hm.clickOnManageMembership(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "Manage Membership success");
		} else {
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
