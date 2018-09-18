package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;

public class VerifyScheduleDaysGroupingInTimeSchedule extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public VerifyScheduleDaysGroupingInTimeSchedule(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^\"(.+)\" are grouped separately$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("One Day")) {
			} else if (exampleData.get(0).equalsIgnoreCase("Two Days")) {
			} else if (exampleData.get(0).equalsIgnoreCase("Three Days")) {
			} else if (exampleData.get(0).equalsIgnoreCase("Four Days")) {
			} else if (exampleData.get(0).equalsIgnoreCase("Five Days")) {
			} else if (exampleData.get(0).equalsIgnoreCase("Six Days")) {
			}
			flag = flag & JasperSchedulingVerifyUtils.verifyEditedDayIsSeparatedFromGroupInTimeSchedule(testCase, inputs);

			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
