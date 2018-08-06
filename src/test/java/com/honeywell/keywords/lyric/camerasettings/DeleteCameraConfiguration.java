package com.honeywell.keywords.lyric.camerasettings;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraConfigurationScreen;


public class DeleteCameraConfiguration extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public String plan;

	public DeleteCameraConfiguration(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) the delete camera confirmation popup$")
	public boolean keywordSteps() throws KeywordException {
		
		CameraConfigurationScreen ccs = new CameraConfigurationScreen(testCase);
		
		if (parameters.get(0).equalsIgnoreCase("accepts")) {
	
			flag = flag & ccs.AcceptDeleteCameraPopup(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "User Accepts Delete Request");
		}
		else if(parameters.get(0).equalsIgnoreCase("dismisses"))
		{
			flag = flag & ccs.CancelDeleteCameraPopup(testCase, inputs);
			Keyword.ReportStep_Pass(testCase, "User Dismisses Delete Request");
		}
		else if(parameters.get(0).equalsIgnoreCase("receives"))
		{
			Keyword.ReportStep_Pass(testCase, "User Receives Delete Request");
			return true;
		}
		else
		{
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
				"Invalid input");
		}
		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
