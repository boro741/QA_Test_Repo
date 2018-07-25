package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyEntryExitDelayDefaultValue extends Keyword {
	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> parameters;
	public boolean flag = true;
	
	
	public VerifyEntryExitDelayDefaultValue(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		
	}
	
	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be display with default (.*) selected$")
	public boolean keywordSteps() throws KeywordException {
		
		BaseStationSettingsScreen bs=new BaseStationSettingsScreen(testCase);
		String displayed = bs.getEntryExitTimerValueFromSecuritySettingsScreen();
		String expected = parameters.get(0) + " Seconds";
		if (displayed.equalsIgnoreCase(expected)) {
			Keyword.ReportStep_Pass(testCase,
					"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
							+ displayed + ". Expected: " + expected);
		}
		return flag;
	}
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
	
}
