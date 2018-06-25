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
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ZwaveScreen;

public class ProvidingDeviceName extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public ProvidingDeviceName(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user names the (.*) to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (parameters.get(0).toUpperCase()) {
			
			case "MOTION SENSOR":{
					//Naming Motion  sensor
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.setValueToDASNameTextBox( parameters.get(1))) {
						inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(1));
					}
			}
			case "DIMMER":
			case "SWITCH": {
				DASZwaveUtils.waitForNamingScreen(testCase);
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if(zwaveScreen.isDeviceNotFoundPopupDisplayed()){
					zwaveScreen.clickRetryOnDeviceNotFoundPopUp();
					if(parameters.get(0).toUpperCase().equals("SWITCH")){
						DASZwaveUtils.activateZwaveSwitch(testCase, inputs);
					}else if(parameters.get(0).toUpperCase().equals("DIMMER")){
						DASZwaveUtils.activateZwaveDimmer(testCase, inputs);
					}
				}
				if(zwaveScreen.isNamingFieldDisplayed()){
					flag = flag & zwaveScreen.setNameToSwitch(parameters.get(1));
					if(testCase.getPlatform().toUpperCase().contains("IOS")){
						flag = flag & zwaveScreen.saveNameToSwitchOnIOS();
						DASZwaveUtils.waitForActionToComplete(testCase, "SAVING NAME");
					}else{
						flag = flag & zwaveScreen.saveNameToSwitchOnAndroid();
					}
				}else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Naming screen not displayed");
				}
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isIncreaseSecurityPopupVisible()) {
					flag = flag & dasDIY.clickOnDontUseButtonInIncreaseSecurityPopup();
				}

				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + parameters.get(0));
			}	
			}
		} catch (Exception e) {
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
