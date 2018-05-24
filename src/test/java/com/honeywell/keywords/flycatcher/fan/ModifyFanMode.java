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

public class ModifyFanMode extends Keyword {

	private TestCases testCase;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public ModifyFanMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes fan mode to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		String fanMode = exampleData.get(0);
		FanModeScreen fanScreen = new FanModeScreen(testCase);

		if (fanMode.equalsIgnoreCase("ON")) {
			flag = flag && fanScreen.changeFanModeToOnMode();
			if(flag)
				Keyword.ReportStep_Pass(testCase, "Successfully Set App Fan Mode to ON");
			else
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set Fan Mode : ON");
		} else if (fanMode.equalsIgnoreCase("AUTO")) {
			flag = flag && fanScreen.changeFanModeToAutoMode();			
			if(flag)
				Keyword.ReportStep_Pass(testCase, "Successfully Set App Fan Mode to AUTO");
			else
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set Fan Mode : AUTO");			
		} else if (fanMode.equalsIgnoreCase("CIRCULATE")) {
			flag = flag && fanScreen.changeFanModeToCircMode();
			if(flag)
				Keyword.ReportStep_Pass(testCase, "Successfully Set App Fan Mode to CIRCULATE");
			else
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set Fan Mode : CIRCULATE");			
		} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Invalid input : " + fanMode);
		}
		
		flag = flag && fanScreen.clickSaveButton();

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() 
	{		
			if (flag)
				ReportStep_Pass(testCase, "ModifyFanMode : Keyword successfully executed");
			else 
				ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,"ModifyFanMode : Keyword failed during execution");

		return flag;
	}

}
