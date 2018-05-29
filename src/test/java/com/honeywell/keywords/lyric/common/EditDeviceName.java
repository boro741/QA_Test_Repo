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
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class EditDeviceName extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public EditDeviceName(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user edits the (.*) name to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("DAS Panel") || parameters.get(0).equalsIgnoreCase("Sensor")
					|| parameters.get(0).equalsIgnoreCase("Keyfob")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				if (bs.isDASNameTextBoxVisible(5)) {
					flag = flag & bs.clearDASNameTextBox();
					if (bs.setValueToDASNameTextBox(parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Successfully set " + parameters.get(1) + " to the textbox");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set " + parameters.get(1) + " to the textbox");
					}
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & MobileUtils.hideKeyboardIOS(testCase.getMobileDriver(), "Done");
					} else {
						try {
							MobileUtils.hideKeyboard(testCase.getMobileDriver());
						} catch (Exception e) {
							// Ignoring any exceptions because keyboard is sometimes not displayed on some
							// Android devices.
						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find DAS Name Text Box");
				}
			} else if (parameters.get(0).equalsIgnoreCase("Sensor")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				if (bs.isDASNameTextBoxVisible(5)) {
					flag = flag & bs.clearDASNameTextBox();
					if (bs.setValueToDASNameTextBox(parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase, "Successfully set " + parameters.get(1) + " to the textbox");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set " + parameters.get(1) + " to the textbox");
					}
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & MobileUtils.hideKeyboardIOS(testCase.getMobileDriver(), "Done");
					} else {
						try {
							MobileUtils.hideKeyboard(testCase.getMobileDriver());
						} catch (Exception e) {
							// Ignoring any exceptions because keyboard is sometimes not displayed on some
							// Android devices.
						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find DAS Name Text Box");
				}
			} else if (parameters.get(0).equalsIgnoreCase("Switch")||parameters.get(0).equalsIgnoreCase("Dimmer")) {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isEditNamingFieldDisplayed()) {
					flag = flag & zwaveScreen.editNameToSwitch(parameters.get(1));
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & zwaveScreen.saveEditedNameToSwitch();
						inputs.setInputValue("DEVICE_NAME_TO_REVERT",parameters.get(1));
					} else {
						flag = flag & zwaveScreen.saveEditedNameToSwitchOnAndroid();
						inputs.setInputValue("DEVICE_NAME_TO_REVERT",parameters.get(1));
						if(parameters.get(0).equalsIgnoreCase("Switch")){
							flag = flag & zwaveScreen.ClickSwitchSettingFromZwaveDevices();
						}else if(parameters.get(0).equalsIgnoreCase("Dimmer")){
							flag = flag & zwaveScreen.ClickDimmerSettingFromZwaveDevices();
						}
					}
				}else{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to locate the naming text field");
				}
			}
			else if(parameters.get(0).equalsIgnoreCase("Access Sensor")) {
                String check = parameters.get(1);
                switch(check.toUpperCase()){
                       case "NEW NAME":{
                              String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
                              BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
                              bs.RenameSensorName(givenSensorName);
                break;
                       }
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
