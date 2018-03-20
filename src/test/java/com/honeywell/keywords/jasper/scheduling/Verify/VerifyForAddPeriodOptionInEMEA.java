package com.honeywell.keywords.jasper.scheduling.Verify;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;

public class VerifyForAddPeriodOptionInEMEA extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true, addPeriodOptionFlag = true;

	public VerifyForAddPeriodOptionInEMEA(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user is shown with an option to add period for a day to accommodate maximum of six periods$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY).equalsIgnoreCase("One")
					|| inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
							.equalsIgnoreCase("Two")
					|| inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
							.equalsIgnoreCase("Three")
					|| inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
							.equalsIgnoreCase("Four")
					|| inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
							.equalsIgnoreCase("Five")) {
				addPeriodOptionFlag = true;

			} else if (inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
					.equalsIgnoreCase("Six")) {
				addPeriodOptionFlag = false;
			}
			WebElement addperiod = testCase.getMobileDriver().findElement(By.name("icon_add_period"));

			if (addPeriodOptionFlag) {
				if (addperiod!= null) {
					ReportStep_Pass(testCase,
							"Add Period option is shown with "
									+ inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
									+ " period(s)");
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Add Period option");
				}
			} else {
				if (addperiod == null) {
					ReportStep_Pass(testCase,
							"Add Period option is not shown with "
									+ inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
									+ " period(s)");
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Add Period option is shown with "
									+ inputs.getInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY)
									+ " period(s)");
				}
			}

			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
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