package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.Dashboard;

public class VerifyDeviceNotDisplayedOnDashboard extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> expectedDevice;
	public boolean flag = true;

	public VerifyDeviceNotDisplayedOnDashboard(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedDevice) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.expectedDevice = expectedDevice;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with (.*) device on dashboard$")
	public boolean keywordSteps() {
		Dashboard dashBordScreen = new Dashboard(testCase);
		if (!dashBordScreen.isDevicePresentOnDashboard(expectedDevice.get(0))) {
			Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
