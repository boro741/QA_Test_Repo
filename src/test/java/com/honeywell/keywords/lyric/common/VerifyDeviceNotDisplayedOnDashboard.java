package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.ZwaveScreen;

public class VerifyDeviceNotDisplayedOnDashboard extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> expectedDevice;
	public boolean flag = true;

	public VerifyDeviceNotDisplayedOnDashboard(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedDevice) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.expectedDevice = expectedDevice;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with (.*) device on the (.*) screen$")
	public boolean keywordSteps() {
		Dashboard dashBordScreen = new Dashboard(testCase);
		switch (expectedDevice.get(1).toUpperCase()){
		case "DASHBOARD":{
			if (!dashBordScreen.isDevicePresentOnDashboard(expectedDevice.get(0))) {
				Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
			} else {
				Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " displayed");
			}
			break;
		}
		case "ZWAVE DEVICES":{
			ZwaveScreen zwaveScreen =new ZwaveScreen(testCase);
			switch (expectedDevice.get(0).toUpperCase()){
			case "SWITCH":{
				if(!zwaveScreen.isSwitchSettingOnZwaveDevicesDisplayed()){
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
				}else {
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " displayed");
				}
				break;
			}
			case "DIMMER":{
				if(!zwaveScreen.isDimmerSettingOnZwaveDevicesDisplayed()){
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
				}else {
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " displayed");
				}
				break;
			}
			}
			break;
		}
		default:{
			Keyword.ReportStep_Fail(testCase,FailType.FALSE_POSITIVE, "Input "+expectedDevice.get(1).toUpperCase()+" not handled");
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
