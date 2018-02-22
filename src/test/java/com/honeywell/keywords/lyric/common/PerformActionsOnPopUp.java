package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class PerformActionsOnPopUp extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	private TestCaseInputs inputs;

	public boolean flag = true;
	public PerformActionsOnPopUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedPopUp) {
		this.inputs = inputs;
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

		if (expectedPopUp.get(1).equalsIgnoreCase("Delete DAS Confirmation")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.clickOnNoButton();
				flag = flag & DASSettingsUtils.verifyDeleteDASConfirmationPopUpIsNotDisplayed(testCase, inputs);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}

		} else if (expectedPopUp.get(1).equalsIgnoreCase("Device Excluded")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				zwaveScreen.clickOKOnDeviceExcludedPopUp();
				break;
			}
			case "ADDS DEVICE NOW": {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				zwaveScreen.clickAddNowOnDeviceExcludedPopUp();
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("DELETION ON REMOVE DEVICE")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				DASZwaveUtils.clickOkOnRemoveDevicePopUp(testCase, inputs);
				break;
			}
			case "CANCELS": {
				DASZwaveUtils.clickCancelOnRemoveDevicePopUp(testCase, inputs);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		} else if (expectedPopUp.get(1).equalsIgnoreCase("INCLUSION DEVICE NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				DASZwaveUtils.clickCancelOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "RETRIES THE INCLUSION ON": {
				DASZwaveUtils.clickRetryOnDeviceNotFoundPopUp(testCase);
				break;
			}
			case "TRIES EXCLUSION ON": {
				DASZwaveUtils.clickTryExcludeOnDeviceNotFoundPopUp(testCase, inputs);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		}else if (expectedPopUp.get(1).equalsIgnoreCase("DEVICE NOT FOUND")) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "CONFIRMS": {
				DASZwaveUtils.clickOKOnDeviceNotFoundPopUp(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		}else if (expectedPopUp.get(1).equalsIgnoreCase("FURTHER EXCLUSION OF SWITCH EXCLUDED SUCCESSFULLY")){
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "DISMISSES": {
				flag = flag & DASZwaveUtils.clickCancelFurtherExclusionOnExcludedPopup(testCase);
				break;
			}
			case "CONFIRMS": {
				flag = flag & DASZwaveUtils.clickConfirmFurtherExclusionOnExcludedPopup(testCase);
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
