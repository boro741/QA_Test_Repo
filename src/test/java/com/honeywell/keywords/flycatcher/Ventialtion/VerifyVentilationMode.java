package com.honeywell.keywords.flycatcher.Ventialtion;

import java.util.ArrayList;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;


public class VerifyVentilationMode extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyVentilationMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify Vantilation mode is changed to \"(.+)\"$")
	public boolean keywordSteps() {
		try
		{
			String mode = exampleData.get(0);
			String expectedMode = " ";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String allowedModes = statInfo.getThermoStatVentilationMode();
			if (mode.equalsIgnoreCase("On")) {
				if (!allowedModes.contains("On")) {
					expectedMode = "On";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Ventilation Mode : changed ventialtion mode to  On");
					return true;
				}
			} else if (mode.equalsIgnoreCase("Auto")) {
				if (!allowedModes.equalsIgnoreCase("Auto")) {
					expectedMode = "Auto";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Ventilation Mode : changed ventialtion mode to  Auto");
					return true;
				}
			} else if (mode.equalsIgnoreCase("off")) {
				if (!allowedModes.contains("off")) {
					expectedMode = "off";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Ventilation Mode : changed ventialtion mode to  Off");
					return true;
				}
				
			}else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input : " + mode);
			}
			flag = flag & FlyCatcherVentialtion.verifyVentilationMode(testCase, inputs, expectedMode);
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
		} catch( Exception e){
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {

		if (flag) 
			ReportStep_Pass(testCase, "VerifyVentilationMode : Keyword successfully executed");			
		else
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "VerifyVentilationMode : Keyword failed during execution");

		return flag;
	}

}
