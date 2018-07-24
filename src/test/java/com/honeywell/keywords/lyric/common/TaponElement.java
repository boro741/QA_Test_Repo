package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.*;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.screens.ThermostatSettingsScreen;

import java.util.ArrayList;

public class TaponElement extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedLocator;
	private TestCaseInputs inputs;
	private boolean flag = true;

	public TaponElement(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedLocator) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedLocator = expectedLocator;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return true;
	}

	@Override
	@KeywordStep(gherkins = "^user taps on (.*)$")
	public boolean keywordSteps() throws KeywordException {
		// if (testCase.isTestSuccessful()) {
		switch (expectedLocator.get(0).toUpperCase()) {
		case "ATTENTION FROM CAMERA SOLUTION CARD": {
			DASCameraUtils.CreateAttention(testCase, inputs);
			break;
		}
		case "DISMISS ALARM POPUP": {
			DASAlarmUtils.confirmDismissAlarm(testCase, inputs);
			break;
		}
		case "BELOW RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			ts.clickOnBelowAboveTempAlertRangeOption(expectedLocator.get(0));
			break;
		}
		case "ABOVE RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			ts.clickOnBelowAboveTempAlertRangeOption(expectedLocator.get(0));
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					expectedLocator.get(0) + " input not handled");
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
