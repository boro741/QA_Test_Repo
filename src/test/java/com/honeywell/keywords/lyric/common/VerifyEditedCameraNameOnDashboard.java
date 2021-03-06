package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;

public class VerifyEditedCameraNameOnDashboard extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyEditedCameraNameOnDashboard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify the Edited name on (.+) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Dashbaord")) {
				BaseStationSettingsScreen ts = new BaseStationSettingsScreen(testCase);
				flag &= ts.clickOnBackButton();
				flag &= ts.clickOnBackButton();
				flag &= LyricUtils.verifyDeviceDisplayedOnDashboard(testCase, inputs.getInputValue("NEW_LOCATION1_CAMERA1_NAME"));
				if(flag){
					Keyword.ReportStep_Pass(testCase,"Camera Edited name udpated in dashbaord");
				}else{
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera edited name not updated in dashboard");
				}	
			} 	
			else if (parameters.get(0).equalsIgnoreCase("WLD Dashboard")) { 
				//BaseStationSettingsScreen ts = new BaseStationSettingsScreen(testCase);
				//flag &= ts.clickOnBackButton();
				//flag &= ts.clickOnBackButton();
				String deviceName="";

				boolean flag = true;
				Dashboard d = new Dashboard(testCase);
				if (d.isDevicePresentOnDashboard(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))) {
					deviceName= inputs.getInputValue("LOCATION1_DEVICE1_NAME");
					Keyword.ReportStep_Pass(testCase,"Camera Edited name udpated to Original Name in dashbaord");
				} else if (d.isDevicePresentOnDashboard("Test WLD Name")){
					deviceName="Test WLD Name";
					Keyword.ReportStep_Pass(testCase,"Camera Edited name udpated to Test WLD Name in dashbaord");
				}
				else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device : " + deviceName + " is not present on the dashboard.");
				}
				return flag;
			}
		}catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
