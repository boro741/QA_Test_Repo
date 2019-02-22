package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.AboutTheAppUtils;

public class MinimizeAndMaximizeTheAppForDefiniteNumberOfTimes extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedNumberOfTimesToMinimizeAndMaximizeTheApp;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public MinimizeAndMaximizeTheAppForDefiniteNumberOfTimes(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedNumberOfTimesToMinimizeAndMaximizeTheApp) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.expectedNumberOfTimesToMinimizeAndMaximizeTheApp = expectedNumberOfTimesToMinimizeAndMaximizeTheApp;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user minimizes and maximizes the app for \"(.+)\" times$")
	public boolean keywordSteps() {
		int maxCount = Integer.parseInt(expectedNumberOfTimesToMinimizeAndMaximizeTheApp.get(0));
		for (int i = 0; i < maxCount; i++) {
			flag &= AboutTheAppUtils.minimizeAndMaximizeTheApp(testCase);
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Successfully minimized and maximized the app for the count:" + i);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to minimize and maximize the app for the count: " + i);
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
