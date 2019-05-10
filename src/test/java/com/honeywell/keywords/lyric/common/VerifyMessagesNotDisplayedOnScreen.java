package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class VerifyMessagesNotDisplayedOnScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> parameters;
	public boolean flag = true;

	public VerifyMessagesNotDisplayedOnScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> parameters) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with the \"(.+)\" on the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("ACTIVITY HISTORY")) {
				ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "MESSAGES": {
					if (ah.isNoMessagesLabelVisible(20)) {
						flag= true;
						Keyword.ReportStep_Pass(testCase, "No Messages are displayed " + parameters.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Messages are displayed" + parameters.get(0));
					}
					break;
				}
			}
		  }
		} 
		catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}	
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
