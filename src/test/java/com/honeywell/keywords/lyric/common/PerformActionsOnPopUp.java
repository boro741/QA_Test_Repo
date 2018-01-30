package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class PerformActionsOnPopUp extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	//private TestCaseInputs inputs;
	private HashMap<String, MobileObject> fieldObjects;

	public boolean flag = true;

	public PerformActionsOnPopUp(TestCases testCase, TestCaseInputs inputs,ArrayList<String> expectedPopUp) {
		//this.inputs = inputs;
		this.testCase = testCase;
		this.expectedPopUp = expectedPopUp;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) the (.*) popup$")
	public boolean keywordSteps() {
		if (testCase.isTestSuccessful()) {
			if(expectedPopUp.get(1).equalsIgnoreCase("Delete DAS Confirmation"))
			{
				switch (expectedPopUp.get(0).toUpperCase()) {
				case "DISMISSES":
				{
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "NoButton");
					break;
				}
				default:
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
					return flag;
				}
			}
			
			}
		}
		else
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"Scenario has already failed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
			return flag;
	}

}
