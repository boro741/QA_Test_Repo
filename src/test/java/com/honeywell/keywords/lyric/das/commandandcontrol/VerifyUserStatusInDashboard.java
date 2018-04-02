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
import com.honeywell.screens.Dashboard;

public class VerifyUserStatusInDashboard extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> status;
	public boolean flag = true;

	public VerifyUserStatusInDashboard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> status) {
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
	@KeywordStep(gherkins = "^user status should be set to \"(.*)\" in the dashboard screen$")
	public boolean keywordSteps() {
		Dashboard d = new Dashboard(testCase);
		String currentStatus = d.getSecurityStatusLabel();
		System.out.println("#############currentStatus: " + currentStatus);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (currentStatus.equalsIgnoreCase("Sleep")) {
				currentStatus = "Night";
			}
		}
		if (currentStatus != null) {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (currentStatus.contains(status.get(0).toUpperCase())) {
					Keyword.ReportStep_Pass(testCase,
							"User status is set to " + status.get(0) + " in the dashboard screen");
				}
			} else {
				if (currentStatus.contains(status.get(0))) {
					Keyword.ReportStep_Pass(testCase,
							"User status is set to " + status.get(0) + " in the dashboard screen");
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"User status is not set to " + status.get(0) + " in the dashboard screen");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
