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
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.ZwaveScreen;

public class RemoveZwaveDeviceCleanUp extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	private TestCaseInputs inputs;
	public HashMap<String, MobileObject> fieldObjects;

	public RemoveZwaveDeviceCleanUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has no (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (parameters.get(0).toUpperCase()) {
			case "SWITCH1": {
				boolean f = false;
				Dashboard dScreen = new Dashboard(testCase);
				f=dScreen.isDevicePresentOnDashboard("Switch1")||dScreen.isDevicePresentOnDashboard("Switch 001")||dScreen.isDevicePresentOnDashboard("Switch2");
				if(f){
					DASZwaveUtils.navigateToSwitchSettingsFromDashboard(testCase);
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.isRemoveDevicePopUpDisplayed();
					DASZwaveUtils.clickOKOnDeviceExcludedPopUp(testCase);
					DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
					DASZwaveUtils.activateZwaveSwitch(testCase,inputs);
					zwaveScreen.clickOKOnDeviceExcludedPopUp();
					DASZwaveUtils.clickNavigateUp(testCase);
				}else{
					Keyword.ReportStep_Pass(testCase,
							"No switch found");
				}
				break;
			}
			case "DIMMER1": {
				boolean f = false;
				Dashboard dScreen = new Dashboard(testCase);
				f=dScreen.isDevicePresentOnDashboard("Dimmer1")||dScreen.isDevicePresentOnDashboard("Dimmer 001")||dScreen.isDevicePresentOnDashboard("Dimmer2");
				if(f){
					DASZwaveUtils.navigateToDimmerSettingsFromDashboard(testCase);
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.isRemoveDevicePopUpDisplayed();
					DASZwaveUtils.clickOKOnDeviceExcludedPopUp(testCase);
					DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
					DASZwaveUtils.activateZwaveDimmer(testCase,inputs);
					zwaveScreen.clickOKOnDeviceExcludedPopUp();
					DASZwaveUtils.clickNavigateUp(testCase);
				}else{
					Keyword.ReportStep_Pass(testCase,
							"No Dimmer found");
				}
				break;
			}
			case "ZWAVE DEVICES": {
				boolean f = false;
				Dashboard dScreen = new Dashboard(testCase);
				f=dScreen.isDevicePresentOnDashboard("Dimmer1")||dScreen.isDevicePresentOnDashboard("Dimmer 001")||dScreen.isDevicePresentOnDashboard("Dimmer2");
				if(f){
					DASZwaveUtils.navigateToDimmerSettingsFromDashboard(testCase);
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.isRemoveDevicePopUpDisplayed();
					DASZwaveUtils.clickOKOnDeviceExcludedPopUp(testCase);
					DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
					DASZwaveUtils.activateZwaveDimmer(testCase,inputs);
					zwaveScreen.clickOKOnDeviceExcludedPopUp();
					DASZwaveUtils.clickNavigateUp(testCase);
				}else{
					Keyword.ReportStep_Pass(testCase,
							"No Dimmer found");
				}

				f = false;
				f=dScreen.isDevicePresentOnDashboard("Switch1")||dScreen.isDevicePresentOnDashboard("Switch 001")||dScreen.isDevicePresentOnDashboard("Switch2");
				if(f){
					DASZwaveUtils.navigateToSwitchSettingsFromDashboard(testCase);
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					zwaveScreen.isRemoveDevicePopUpDisplayed();
					DASZwaveUtils.clickOKOnDeviceExcludedPopUp(testCase);
					DASZwaveUtils.waitForEnteringExclusionToComplete(testCase);
					DASZwaveUtils.activateZwaveSwitch(testCase,inputs);
					zwaveScreen.clickOKOnDeviceExcludedPopUp();
					DASZwaveUtils.clickNavigateUp(testCase);
				}else{
					Keyword.ReportStep_Pass(testCase,
							"No switch found");
				}
				break;
			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Input not handled " + parameters.get(0).toUpperCase());
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
