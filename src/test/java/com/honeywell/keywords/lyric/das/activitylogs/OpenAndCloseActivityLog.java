package com.honeywell.keywords.lyric.das.activitylogs;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASActivityLogsUtils;

public class OpenAndCloseActivityLog extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	// private TestCaseInputs inputs;
	public ArrayList<String> activityLogInput;

	public OpenAndCloseActivityLog(TestCases testCase, TestCaseInputs inputs, ArrayList<String> activityLogInput) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.activityLogInput = activityLogInput;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		flag = true;
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"(.*)\" activity log$")
	public boolean keywordSteps() {
		switch (activityLogInput.get(0).toUpperCase()) {
		case "OPENS": {
			try {
				flag = flag & DASActivityLogsUtils.openActivityLogs(testCase);
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to open activity logs");
			}
			break;
		}
		case "CLOSES": {
			try {
				flag = flag & DASActivityLogsUtils.closeActivityLogs(testCase);
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to close activity logs");
			}
			break;
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
