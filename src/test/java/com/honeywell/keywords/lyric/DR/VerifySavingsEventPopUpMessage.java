// Goal : Login into the application and verify the next screen after tapping into login button

package com.honeywell.keywords.lyric.DR;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.DR.utils.DRUtils;

public class VerifySavingsEventPopUpMessage extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;

	public boolean flag = true;

	public VerifySavingsEventPopUpMessage(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^\"(.*)\" message pop up is displayed on the primary card$")
	public boolean keywordSteps() {
		try {
			if (exampleData.get(0).equals("saving event schedule")) {

				flag = flag & DRUtils.VerifyDRPopUp(testCase, inputs);
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		try {
			if (exampleData.get(0).equals("cancel saving event message with a Yes and No")) {

				flag = flag & DRUtils.VerifyDRCancelPopUp(testCase, inputs);
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		try {
			if (exampleData.get(0).equals("cancel saving event message with OK")) {

				flag = flag & DRUtils.VerifyDRCancelByUserPopUp(testCase, inputs);
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
