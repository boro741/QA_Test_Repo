package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.VacationHoldScreen;

public class EnterStartAndEndDate extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EnterStartAndEndDate(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user provided with option to enter vacation start and end date$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (vhs.isStartAndEndDateEnabled()) {
			if (vhs.ClickOnStartDate()) {
				Keyword.ReportStep_Pass(testCase, String.format("The Start Date button is clicked"));
				if (vhs.isCalendarPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, String.format("StartCalendar Is Present"));
				}
			}
			if (vhs.ClickOnEndDate()) {
				Keyword.ReportStep_Pass(testCase, String.format("The End Date button is clicked"));
				if (vhs.isCalendarPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, String.format("EndCalendar Is Present"));
				}
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE, String
					.format("The Start and End Date is not displayed for {0} Vacation Hold", exampleData.get(0)));
			flag = false;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
