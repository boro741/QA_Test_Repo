package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivityHistoryScreen;


public class UnselectElementOnAScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public UnselectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user unselects \"(.+)\" from \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("Activity History")) {
				ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "A MESSAGE": {
					if (ah.isCancelOptionVisible()) {
						flag &= ah.clickOnFirstMsgRadioButton();
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully unselected the option: " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to unselect the option: " + parameters.get(0));
					}
					break;
				}
			}
		}
			
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
