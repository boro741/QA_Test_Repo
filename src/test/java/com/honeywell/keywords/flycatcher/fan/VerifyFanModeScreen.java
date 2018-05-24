package com.honeywell.keywords.flycatcher.fan;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FanModeScreen;

public class VerifyFanModeScreen extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyFanModeScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;		
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies fan screen UI Elements$")
	public boolean keywordSteps() throws KeywordException {
		try {
						
			FanModeScreen fanScreen = new FanModeScreen(testCase);
			String scrnTitle = fanScreen.getFanScreenTitle();
			
			if (scrnTitle.trim().equals("Fan Mode"))
				Keyword.ReportStep_Pass(testCase, "Fan Screen Title :  " + scrnTitle);
			else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fan Screen title is not as expected");
				flag = false;
			}
				
			if(testCase.getPlatform().toUpperCase().contains("ANDROID")) 
			{
				String deviceName = fanScreen.getDeviceNameOnFanScreen();				
				Keyword.ReportStep_Pass(testCase, "Device Name On FanScreen is :  " + deviceName);
			}
			
			if(fanScreen.isFanAutoModeButtonVisible(10))
				Keyword.ReportStep_Pass(testCase, "Available Fan Modes : AUTO");
			
			if(fanScreen.isFanCircModeButtonVisible(10))
				Keyword.ReportStep_Pass(testCase, "Available Fan Modes : CIRCULATE");
			
			if(fanScreen.isFanOnModeButtonVisible(10))
				Keyword.ReportStep_Pass(testCase, "Available Fan Modes : ON");
			
			Keyword.ReportStep_Pass(testCase, "Current Fan Mode Running : " + fanScreen.getFanRunningSts());
			Keyword.ReportStep_Pass(testCase, "Current Fan Mode Description : " + fanScreen.getFanDescription());
			
			if(fanScreen.isFanInfoButtonVisible(10)) 
			{
				Keyword.ReportStep_Pass(testCase, "Fan Info button displayed as expected.");
				
				flag = flag && fanScreen.clickFanScreenInfoButton();
				
				Keyword.ReportStep_Pass(testCase, "Fan Info Screen Title :  " + fanScreen.getFanInfoToolbarTitle());
				
				Keyword.ReportStep_Pass(testCase, "Fan Info Displayed As Follow");
								
				Keyword.ReportStep_Pass(testCase, "Fan Info Mode : " + fanScreen.getListMode1Name() + " | Desription :  " + fanScreen.getListMode1Desc());
				
				Keyword.ReportStep_Pass(testCase, "Fan Info Mode : " + fanScreen.getListMode2Name() + " | Desription :  " + fanScreen.getListMode2Desc());
				
				Keyword.ReportStep_Pass(testCase, "Fan Info Mode : " + fanScreen.getListMode3Name() + " | Desription :  " + fanScreen.getListMode3Desc());				
			}				
			else 
			{
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fan Info Buton is not visible / not available");
				flag = false;
			}
			
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		
		return flag;

	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;

	}

}
