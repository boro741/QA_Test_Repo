package com.honeywell.keywords.lyric.camerasettings;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.CameraConfigurationScreen;


public class CameraConfiguration extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public String plan;

	public CameraConfiguration(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) camera name to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		
		CameraConfigurationScreen ccs = new CameraConfigurationScreen(testCase);
		
		if (parameters.get(0).equalsIgnoreCase("edits")) {
			String cameraName = parameters.get(1);
			
			flag = flag & ccs.EnterCameraName(testCase, inputs, cameraName);
			Keyword.ReportStep_Pass(testCase, "Edit Success");
		}
		else if(parameters.get(0).equalsIgnoreCase("should be displayed with edited"))
		{
			try {
				String cameraName = parameters.get(1);
				flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
						cameraName);
				flag = flag & ccs.clickOnBackButton();
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}
		}
		else if(parameters.get(0).equalsIgnoreCase("edits back again"))
		{
			String cameraName = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
			flag = flag & ccs.EnterCameraName(testCase, inputs, cameraName);
			Keyword.ReportStep_Pass(testCase, "Edit Again Success");
		}
		else
		{
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
				"Invalid input " + parameters.get(0));
		}
		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
