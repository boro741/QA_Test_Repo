package com.honeywell.keywords.jasper.Setpoint;

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

public class ChangeSystemSwitch extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ChangeSystemSwitch(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user change the (.*) from (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (vhs.clickOnModeText()) {
			Keyword.ReportStep_Pass(testCase, "Mode is been clicked");

			switch (exampleData.get(0).toUpperCase()) {
			case "OFF": {
				flag &= vhs.clickOnSystemModes("OffMode");
				break;
			}
			case "AUTO": {
				flag &= vhs.clickOnSystemModes("AutoMode");
				break;
			}
			case "HEAT": {
				flag &= vhs.clickOnSystemModes("HeatMode");
				break;
			}
			case "COOL": {
				flag &= vhs.clickOnSystemModes("CoolMode");
				break;
			}
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "User Change Mode is been clicked");
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Unable to change the Mode");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Unable to click the Mode");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}

}
