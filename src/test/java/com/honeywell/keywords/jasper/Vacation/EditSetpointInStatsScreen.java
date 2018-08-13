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

public class EditSetpointInStatsScreen extends Keyword {
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public EditSetpointInStatsScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be able to edit set points in Stats screen$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (vhs.isHeatToSectionInStatScreenVisible()) {
			String getCurrentHeatSetPoint = vhs.getHeatSetPointValue();
			String getUpdatedHeatSetPoint = null;
			Keyword.ReportStep_Pass(testCase, String.format("Heat Set Point Stepper Up Button is visible"));
			vhs.clickOnHeatSetPointStepperUpButton();
			getUpdatedHeatSetPoint = vhs.getHeatSetPointValue();
			if (!getCurrentHeatSetPoint.equals(getUpdatedHeatSetPoint)) {
				Keyword.ReportStep_Pass(testCase,
						String.format("Updated Heat Set Point Stepper up value: " + getUpdatedHeatSetPoint
								+ " is not equal to the original Heat Set Point value: " + getCurrentHeatSetPoint));
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						String.format("Updated Heat Set Point Stepper up value: " + getUpdatedHeatSetPoint
								+ " is equal to the original Heat Set Point value: " + getCurrentHeatSetPoint));
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("Heat Set Point is not displayed"));
		}
		if (vhs.isCoolToSectionInStatScreenVisible()) {
			String getCurrentCoolSetPoint = vhs.getCoolSetPointValue();
			String getUpdatedCoolSetPoint = null;
			Keyword.ReportStep_Pass(testCase, String.format("Cool Set Point Stepper Up Button is visible"));
			vhs.clickOnCoolSetPointStepperUpButton();
			getUpdatedCoolSetPoint = vhs.getCoolSetPointValue();
			if (!getCurrentCoolSetPoint.equals(getUpdatedCoolSetPoint)) {
				Keyword.ReportStep_Pass(testCase,
						String.format("Updated Cool Set Point Stepper up value: " + getUpdatedCoolSetPoint
								+ " is not equal to the original Cool Set Point value: " + getCurrentCoolSetPoint));
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						String.format("Updated Cool Set Point Stepper up value: " + getUpdatedCoolSetPoint
								+ " is equal to the original Cool Set Point value: " + getCurrentCoolSetPoint));
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
					String.format("Cool Set Point is not displayed"));
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}

}
