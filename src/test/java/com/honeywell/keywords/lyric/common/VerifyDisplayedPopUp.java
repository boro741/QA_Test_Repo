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

public class VerifyDisplayedPopUp extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedPopUp;
	private TestCaseInputs inputs;
	//private HashMap<String, MobileObject> fieldObjects;

	public boolean flag = true;

	public VerifyDisplayedPopUp(TestCases testCase, TestCaseInputs inputs,ArrayList<String> expectedPopUp) {
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
	@KeywordStep(gherkins = "^user should receive a (.*) popup$")
	public boolean keywordSteps() {
		//if (testCase.isTestSuccessful()) {
			switch (expectedPopUp.get(0).toUpperCase()) {
			case "SWITCH EXCLUDED SUCCESSFULLY":{
				flag = flag & DASZwaveUtils.verifyDeviceExcludedPopUp(testCase, inputs);
				break;
			}
			case "REMOVE DEVICE":{
				flag = flag & DASZwaveUtils.verifyRemoveDevicePopUp(testCase, inputs);
				break;
			}
			case "DELETE DAS CONFIRMATION":
			{
				flag = flag & DASSettingsUtils.verifyDeleteDASConfirmationPopUp(testCase, inputs);
				break;
			}
			case "INCLUSION DEVICE NOT FOUND":
			{
				flag = flag & DASZwaveUtils.verifyDeviceNotFoundPopUp(testCase, inputs);
				break;
			}
			default:
			{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input " + expectedPopUp.get(0));
				return flag;
			}
			}
		/*}
		else
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"Scenario has already failed");
		}*/
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
