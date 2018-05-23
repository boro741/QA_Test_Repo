package com.honeywell.keywords.flycatcher.fan;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FanModeScreen;

public class VerifyFanMode extends Keyword {

	private TestCases testCase;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyFanMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify fan mode is changed to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		String fanMode = exampleData.get(0);
		
		FanModeScreen fanScreen = new FanModeScreen(testCase);

		String fanStatus = fanScreen.getFanRunningSts();

		if (fanStatus.trim().contains("AUTO") && fanMode.equalsIgnoreCase("AUTO")) {
			Keyword.ReportStep_Pass(testCase, "App_Verify_Fan_Mode : Fan Auto mode is verified");
			Keyword.ReportStep_Pass(testCase, "Current Mode is " + fanStatus);
			return true;

		} else if (fanStatus.trim().contains("CIRCULATE") && fanMode.equalsIgnoreCase("CIRCULATE")) {
			Keyword.ReportStep_Pass(testCase, "App_Verify_Fan_Mode : Fan Circulate mode is verified");
			Keyword.ReportStep_Pass(testCase, "Current Mode is " + fanStatus);
			return true;
		} else if (fanStatus.trim().contains("ON") && fanMode.equalsIgnoreCase("ON")) {
			Keyword.ReportStep_Pass(testCase, "App_Verify_Fan_Mode : Fan On mode is verified");
			Keyword.ReportStep_Pass(testCase, "Current Mode is " + fanStatus);
			return true;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"AppFanMode_Verify failed. Not in Primary card");
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {

		try {
			if (flag) {
				ReportStep_Pass(testCase, "ChangeFanModeFly_UserInput : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"ChangeFanModeFly_UserInput : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"ChangeFanModeFly_UserInput : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}
