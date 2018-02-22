package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class SelectBaseStationMACID extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	private ArrayList<String> expectedMACID;

	public SelectBaseStationMACID(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedMACID) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.expectedMACID = expectedMACID;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects a base station with MAC ID (.*)$")
	public boolean keywordSteps() {
		if (testCase.isTestSuccessful()) {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			try {
				DIYRegistrationUtils.selectABaseStation(testCase, inputs, expectedMACID.get(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			DIYRegistrationUtils.waitForLookingForBaseStationProgressBarToComplete(testCase);
			dasDIY.isRegisterBaseStationHeaderTitleVisible();
			dasDIY.isQRCodeDisplayed();
		} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Scenario steps failed already, hence skipping the verification");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
