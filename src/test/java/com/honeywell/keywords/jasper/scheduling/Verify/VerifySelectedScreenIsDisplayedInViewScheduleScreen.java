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


public class VerifySelectedScreenIsDisplayedInViewScheduleScreen extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public VerifySelectedScreenIsDisplayedInViewScheduleScreen(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^verify \"(.+)\" screen is shown in view schedule screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("No Schedule")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySelectedScreenInViewScheduleScreen(testCase, "No");

			} else if (exampleData.get(0).equalsIgnoreCase("Everyday Schedule")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySelectedScreenInViewScheduleScreen(testCase, "Everyday");

			} else if (exampleData.get(0).equalsIgnoreCase("Weekday and Weekend Schedule")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySelectedScreenInViewScheduleScreen(testCase, "Weekday and Weekend");

			} else if (exampleData.get(0).equalsIgnoreCase("Geofence Schedule")) {
				flag = flag & JasperSchedulingVerifyUtils.verifySelectedScreenInViewScheduleScreen(testCase, "Geofence");

			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Inputs not handled");
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

