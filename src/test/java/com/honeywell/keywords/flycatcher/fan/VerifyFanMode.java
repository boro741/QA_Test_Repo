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
	@KeywordStep(gherkins = "^verify current fan mode is \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		String fanMode = exampleData.get(0);
		
		FanModeScreen fanScreen = new FanModeScreen(testCase);
		String fanStatus = fanScreen.getFanRunningSts();

		if (fanStatus.trim().contains("AUTO") && fanMode.equalsIgnoreCase("AUTO")) {
			Keyword.ReportStep_Pass(testCase, "Current Fan Mode is as expected : AUTO");
		} else if (fanStatus.trim().contains("CIRCULATE") && fanMode.equalsIgnoreCase("CIRCULATE")) {
			Keyword.ReportStep_Pass(testCase, "Current Fan Mode is as expected : CIRCULATE");
		} else if (fanStatus.trim().contains("ON") && fanMode.equalsIgnoreCase("ON")) {
			Keyword.ReportStep_Pass(testCase, "Current Fan Mode is as expected : ON");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"VerifyFanMode failed. Current Fan Running Status is not valid : " + fanStatus);
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {

			if (flag) 
				ReportStep_Pass(testCase, "VerifyFanMode : Keyword successfully executed");			
			else
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "VerifyFanMode : Keyword failed during execution");
			
		return flag;
	}

}
