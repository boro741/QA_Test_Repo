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

public class EditSetpointInVacationSettings extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EditSetpointInVacationSettings(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user should allowed to edit set points$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (vhs.editHeatSetPointUp()) {
			Keyword.ReportStep_Pass(testCase, String.format("Able to edit Heat Setpoint in Vacation Settings"));
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("unable to edit Heat Setpoint in Vacation Settings"));
		}
		if (vhs.editCoolSetPointUp()) {
			Keyword.ReportStep_Pass(testCase, String.format("Able to edit Cool Setpoint in Vacation Settings"));
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("unable to edit Cool Setpoint in Vacation Settings"));
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}

}
