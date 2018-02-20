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
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.keywords.lyric.common.NavigateToScreen;
import com.honeywell.lyric.utils.InputVariables;

public class VerifyScheduleIsRetained extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public VerifyScheduleIsRetained(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^verify \"(.+)\" schedule is retained$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED, InputVariables.GEOFENCE_BASED_SCHEDULE);

			} else if (exampleData.get(0).equalsIgnoreCase("Everyday")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED, InputVariables.EVERYDAY_SCHEDULE);

			} else if (exampleData.get(0).equalsIgnoreCase("Weekday and Weekend")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);

			}
			flag = flag & JasperSchedulingUtils.verifyScheduleRetained(testCase, inputs);

			flag = flag & NavigateToScreen.NavigateToDashboardFromAnyScreen(testCase);

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
