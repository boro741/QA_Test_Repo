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

public class VerifyTapToSetForSpecificTimeSchedulePeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;
	String schedulePeriod = "";

	public VerifyTapToSetForSpecificTimeSchedulePeriod(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user should be displayed \"(.+)\" time as \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				schedulePeriod = exampleData.get(0) + "_"
						+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[1];
			} else {
				schedulePeriod = inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[0] + "_"
						+ exampleData.get(0);
			}

			flag = flag & JasperSchedulingVerifyUtils.verifyTimeforSpecificTimeSchedulePeriod(testCase, inputs, exampleData.get(1),
					schedulePeriod);

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

