package com.honeywell.keywords.lyric.dasAlarms;

import com.honeywell.commons.coreframework.*;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DAS_AlarmUtils;
import com.honeywell.lyric.das.utils.DAS_CameraUtils;

import java.util.ArrayList;


public class TaponElement extends Keyword {

	private TestCases testCase;
	private ArrayList<String> expectedLocator;
	private TestCaseInputs inputs;
	private boolean flag=true;


	public TaponElement(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedLocator) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedLocator = expectedLocator;
	}


	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return true;
	}

	@Override
	@KeywordStep(gherkins = "^user taps on (.*)$")
	public boolean keywordSteps() throws KeywordException {
		//	if (testCase.isTestSuccessful()) {
		switch (expectedLocator.get(0).toUpperCase()) {
		case "ATTENTION FROM CAMERA SOLUTION CARD": {     
			DAS_CameraUtils.CreateAttention(testCase, inputs);
			break;
		}
		case "DISMISS ALARM POPUP":{
			DAS_AlarmUtils.confirmDismissAlarm(testCase, inputs);
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					expectedLocator.get(0) + " input not handled");
		}
		}
		/*} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Scenario steps failed already, hence skipping the verification");
		}*/
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
