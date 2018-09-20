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
import com.honeywell.lyric.utils.InputVariables;

public class VerifyScheduleCreatedWhenCoolOnly extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public VerifyScheduleCreatedWhenCoolOnly(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^\"(.+)\" scheduling gets activated with \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(0).equalsIgnoreCase("no")) {
				flag = flag & JasperSchedulingUtils.VerifyScheduleWhenHeatonly_CoolOnly(testCase, inputs, "no");
				
			} else if (exampleData.get(0).equalsIgnoreCase("Same Every Day")) {
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
				flag = flag & JasperSchedulingUtils.VerifyScheduleWhenHeatonly_CoolOnly(testCase, inputs, "time based");
				
			} else if (exampleData.get(0).equalsIgnoreCase("Different On Weekdays")) {
				inputs.setInputValueWithoutTarget(InputVariables.TYPE_OF_TIME_SCHEDULE,
						InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
				flag = flag & JasperSchedulingUtils.VerifyScheduleWhenHeatonly_CoolOnly(testCase, inputs, "time based");
			} else if (exampleData.get(0).equalsIgnoreCase("Geofence based")) {
				flag = flag & JasperSchedulingUtils.VerifyScheduleWhenHeatonly_CoolOnly(testCase, inputs, "Geofence based");
			}
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

