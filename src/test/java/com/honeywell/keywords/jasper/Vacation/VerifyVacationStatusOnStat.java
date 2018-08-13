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

public class VerifyVacationStatusOnStat extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyVacationStatusOnStat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Vacation mode is (.*) for the Stat$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (exampleData.get(0).equalsIgnoreCase("Active")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != null) {
				if (vhs.isStatInVacationScreenVisible(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))) {
					flag = flag && vhs.clickOnStatInVacationScreen(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					Keyword.ReportStep_Pass(testCase,
							String.format("The Vacation Hold Setpoint of Stat is clicked to edit the Set point"));
					try {
						if (vhs.isVacationSwitchInStatScreenEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									String.format("The Vacation Hold Setpoint of Stat is active as expected"));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
									String.format("The Vacation Hold Setpoint of Stat is not active"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Stat is not present in vacation screen");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device is not present");
			}
		} else if (exampleData.get(0).equalsIgnoreCase("InActive")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != null) {
				if (vhs.isStatInVacationScreenVisible(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))) {
					flag = flag && vhs.clickOnStatInVacationScreen(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					Keyword.ReportStep_Pass(testCase,
							String.format("The Vacation Hold Setpoint of Stat is clicked to edit the Set point"));
					try {
						if (vhs.isVacationSwitchInStatScreenEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									String.format("The Vacation Hold Setpoint of Stat is inactive as expected"));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE,
									String.format("The Vacation Hold Setpoint of Stat is not inactive"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Stat is not present in vacation screen");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device is not present");
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
