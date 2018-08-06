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

public class VerifySchedulePeriodNotDeleted extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public VerifySchedulePeriodNotDeleted(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Period is \"(.+)\" on \"(.+)\" Dialog box$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(1).equalsIgnoreCase("canceling")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySchedulePeriodNotDeleted(testCase, false);

			} else if (exampleData.get(1).equalsIgnoreCase("confirming")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySchedulePeriodNotDeleted(testCase, true);

			}
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
