package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.HashMap;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;


import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.report.FailType;

public class VerifyAppRedirectToAddnewDeviceScreen extends Keyword {	
	private TestCases testCase;
	private HashMap<String, MobileObject> fieldObjects;
	public boolean flag = true;

	public VerifyAppRedirectToAddnewDeviceScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.fieldObjects = MobileUtils.loadObjectFile(testCase, "HomeScreen");
}
	@Override
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify app redirected to Add new device screen$")
	public boolean keywordSteps() throws KeywordException {
		fieldObjects = MobileUtils.loadObjectFile(testCase, "AddNewDevice");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PageTitle", 5, false)) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PageTitle")) {
					Keyword.ReportStep_Pass(testCase,
					"Successfully navigated to Add New device screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to navigate to Add New device screen");
				}
			}
			else if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Search", 5, false)) {
				Keyword.ReportStep_Pass(testCase,
				"Successfully navigated to Country Selection screen");
				
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
				"Failed to navigate to Country Selection screen");
			}
			return flag;
		}
	@Override
	public boolean postCondition() throws KeywordException {	
		if (!testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.clickOnElement(testCase, "Name", "Back");
		}
		else{
			MobileUtils.pressBackButton(testCase);
		}
		if(MobileUtils.isMobElementExists(fieldObjects, testCase, "Logout")){
			MobileUtils.clickOnElement(fieldObjects, testCase, "Logout");
		}
		return flag;
	}
	
}
