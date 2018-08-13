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

public class SelectStatInVacationSettings extends Keyword {
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public SelectStatInVacationSettings(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects the stat to edit$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != null) {
			if (vhs.isStatInVacationScreenVisible(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))) {
				vhs.clickOnStatInVacationScreen(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
				Keyword.ReportStep_Pass(testCase, String.format("Selected stat in Vacation screen"));
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
						String.format("Failure:Unable to select Stat in vacation screen"));
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
