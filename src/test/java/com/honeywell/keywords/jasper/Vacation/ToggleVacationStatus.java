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

public class ToggleVacationStatus extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ToggleVacationStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user turns \"(.*)\" vacation from \"(.*)\"$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (exampleData.get(1).equalsIgnoreCase("VACATION")) {
			switch (exampleData.get(0).toUpperCase()) {
			case "ON": {
				if (vhs.toggleVacationDetectionSwitch(testCase)) {
					Keyword.ReportStep_Pass(testCase, String.format("The Vaction is turned {0}", exampleData.get(0)));
					flag = true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
							String.format("Unable to turn {0} the Vacation Hold", exampleData.get(0)));
					flag = false;
				}
				break;
			}
			case "OFF": {
				if (vhs.toggleVacationDetectionSwitch(testCase)) {
					Keyword.ReportStep_Pass(testCase, String.format("The Vaction is turned {0}", exampleData.get(0)));
					/*
					 * if (!(testCase.getTestCaseName().equalsIgnoreCase("Verify Guide Message"))) {
					 * flag &= vhs.clickOnEndButtonInEndVacationPopupMode(); }
					 */
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
							String.format("Unable to turn {0} the Vacation Hold", exampleData.get(0)));
					flag = false;
				}
				break;
			}
			}
		} else if (exampleData.get(1).equalsIgnoreCase("SOLUTION CARD")) {
			switch (exampleData.get(0).toUpperCase()) {
			case "OFF": {
				if (vhs.endVacationButtonInSolutionCard()) {
					Keyword.ReportStep_Pass(testCase,
							String.format("The Vaction is turned {0} in solution card", exampleData.get(0)));
					if (!(testCase.getTestCaseName().equalsIgnoreCase("Verify Guide Message"))) {
						flag &= vhs.clickOnEndButtonInEndVacationPopupMode();
					}

				} else {
					Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
							String.format("Unable to turn {0} the Vacation Hold in solution card", exampleData.get(0)));
					flag = false;
				}
				break;
			}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}