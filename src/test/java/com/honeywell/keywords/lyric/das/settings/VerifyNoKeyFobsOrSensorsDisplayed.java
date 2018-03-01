package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyNoKeyFobsOrSensorsDisplayed extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyNoKeyFobsOrSensorsDisplayed(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with (.*) on the (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (parameters.get(0).equalsIgnoreCase("keyfobs")
					&& parameters.get(1).equalsIgnoreCase("keyfob")) {
				if(bs.isNoKeyFobTextVisible())
				{
					Keyword.ReportStep_Pass(testCase, "No Keyfob text is displayed");
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Keyfob text is not displayed");
				}
			}
			else if (parameters.get(0).equalsIgnoreCase("sensors")
					&& parameters.get(1).equalsIgnoreCase("sensors")) {
				if(bs.isNoSensorTextVisible())
				{
					Keyword.ReportStep_Pass(testCase, "No Sensors text is displayed");
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Sensors text is not displayed");
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
