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
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.VacationHoldScreen;

public class DisplaySystemSwitchOnVacation extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplaySystemSwitchOnVacation(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user should be displayed with (.*) status on (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (exampleData.get(1).toUpperCase() == "SOLUTION CARD") {
			switch (exampleData.get(0).toUpperCase()) {
			case "VACATION": {
				try {
					flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					if (vhs.isVacationLabelPresentOnSolutionCard()) {
						Keyword.ReportStep_Pass(testCase, "Vacation Label is present in Solution card");

					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Vacation Label is not present in Solution card");
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:" + e.getMessage());
				}
				break;
			}
			case "SYSTEM IS OFF": {
				try {
					if (vhs.isSystemIsOffOnSolutionCard()) {
						Keyword.ReportStep_Pass(testCase, "Thermostat is Off Label is present in Solution card");

					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat is Off Label is not present in Solution card");
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:" + e.getMessage());
				}
				break;
			}

			case "USING WHILE HOME/AWAY/SLEEP": {
				try {
					if (vhs.isAdhocOverrideShown()) {
						Keyword.ReportStep_Pass(testCase, "Adhoc  Label is present in Solution card");

					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Adhoc  Label is not present in Solution card");
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:" + e.getMessage());
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
