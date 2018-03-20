package com.honeywell.keywords.jasper.scheduling.View;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingViewUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.SchedulingScreen;

public class ViewTimeScheduleByIndividualOrGroupedDays extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;

	public ViewTimeScheduleByIndividualOrGroupedDays(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user selects view by \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		SchedulingScreen s = new SchedulingScreen(testCase);
		try {
			if (s.isTimeScheduleButtonVisible(10)) {
				s.clickOnTimeScheduleButton();
				if (exampleData.get(0).equalsIgnoreCase("Individual days")) {
					flag = flag & JasperSchedulingViewUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Individual Days");
					inputs.setInputValue(InputVariables.SHOW_VIEW_TYPE, "Individual Days");

				} else if (exampleData.get(0).equalsIgnoreCase("Grouped days")) {
					flag = flag & JasperSchedulingViewUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Grouped Days");
					inputs.setInputValue(InputVariables.SHOW_VIEW_TYPE, "Grouped Days");

				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Inputs not handled");
				}
			}else{
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "User not in the Thermostat Primary Card");
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
		return flag;
	}
}