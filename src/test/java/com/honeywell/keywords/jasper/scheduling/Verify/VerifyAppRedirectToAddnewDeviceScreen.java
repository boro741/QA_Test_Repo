package com.honeywell.keywords.jasper.scheduling.Verify;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.AddNewDeviceScreen;

public class VerifyAppRedirectToAddnewDeviceScreen extends Keyword {	
	private TestCases testCase;
	public boolean flag = true;

	public VerifyAppRedirectToAddnewDeviceScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
}
	@Override
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify app redirected to Add new device screen$")
	public boolean keywordSteps() throws KeywordException {
		AddNewDeviceScreen ad = new AddNewDeviceScreen(testCase);
			if (ad.isPageTitleVisible(5)) {
				if (ad.isPageTitleVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
					"Successfully navigated to Add New device screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to navigate to Add New device screen");
				}
			}
			else if (ad.isSearchVisible(5)) {
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
		AddNewDeviceScreen ad = new AddNewDeviceScreen(testCase);
		if (!testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			ad.clickOnBackButton();
		}
		else{
			MobileUtils.pressBackButton(testCase);
		}
		if(ad.isLogoutVisible(5)){
			ad.clickOnLogoutButton();
		}
		return flag;
	}
	
}
