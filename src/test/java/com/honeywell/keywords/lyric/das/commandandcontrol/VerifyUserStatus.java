package com.honeywell.keywords.lyric.das.commandandcontrol;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class VerifyUserStatus extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> status;
	public boolean flag = true;

	public VerifyUserStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> status) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.status = status;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "TIMER PROGRESS BAR", 2);
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user status should be set to (.*)$")
	public boolean keywordSteps() {
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		String currentStatus = sc.getCurrentSecurityState();
		System.out.println("#############currentStatus: " + currentStatus);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (currentStatus.equalsIgnoreCase("Sleep")) {
				currentStatus = "Night";
			}
		}
		if (currentStatus.equalsIgnoreCase(status.get(0))) {
			Keyword.ReportStep_Pass(testCase, "User status is set to " + status.get(0));
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"User status is not set to " + status.get(0));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
