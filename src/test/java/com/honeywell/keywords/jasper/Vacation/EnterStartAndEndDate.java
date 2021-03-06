package com.honeywell.keywords.jasper.Vacation;

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
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EnterStartAndEndDate(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be provided with option to enter vacation start and end date$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (vhs.isStartAndEndDateEnabled()) {
			if (vhs.clickOnStartDate()) {
				Keyword.ReportStep_Pass(testCase, String.format("Clicked on Start Date button"));
				if (vhs.isCalendarPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, String.format("Calendar popup is displayed"));
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (vhs.isCancelButtonInCalendarPopupVisible()) {
							vhs.clickOnCancelButtonInCalendarPopup();
							Keyword.ReportStep_Pass(testCase,
									String.format("Clicked on Cancel button in Calendar popup"));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
									String.format("Cancel button in Calendar popup is not displayed"));
						}
					}
				}
			}
			if (vhs.clickOnEndDate()) {
				Keyword.ReportStep_Pass(testCase, String.format("Clicked on End Date button"));
				if (vhs.isCalendarPopupVisible()) {
					Keyword.ReportStep_Pass(testCase, String.format("Calendar popup is displayed"));
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (vhs.isCancelButtonInCalendarPopupVisible()) {
							vhs.clickOnCancelButtonInCalendarPopup();
							Keyword.ReportStep_Pass(testCase,
									String.format("Clicked on Cancel button in Calendar popup"));
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
									String.format("Cancel button in Calendar popup is not displayed"));
						}
					}
				}
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("The Start and End Date is not displayed in Vacations screen"));
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
