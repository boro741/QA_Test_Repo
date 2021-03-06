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
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.WLDConfigurationScreen;
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
			if (parameters.get(0).equalsIgnoreCase("Keyfob Sensor")) {
				inputs.setInputValue("LOCATION1_DEVICE1_KEYFOB1", parameters.get(1));
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.editKeyfobName(parameters.get(1));
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Sensor Overview Screen is displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("Keyfob")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				if (bs.isKeyfobNameTextBoxVisible(5)) {
					flag = flag & bs.clickOnKeyfobNameTextField();
					flag = flag & bs.clearKeyfobNameTextBox();
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						try {
							MobileUtils.hideKeyboard(testCase.getMobileDriver());
							flag = flag & bs.clickOnKeyfobNameTextField();
							flag = flag & bs.clearKeyfobNameTextBox();
						} catch (Exception e) {
						}
					}
					if (bs.setValueToKeyfobNameTextBox(parameters.get(1))) {
						inputs.setInputValue("LOCATION1_DEVICE1_KEYFOB1", parameters.get(1));
						Keyword.ReportStep_Pass(testCase, "Successfully set " + parameters.get(1) + " to the textbox");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set " + parameters.get(1) + " to the textbox");
					}
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DoneButtonOnKeyboard");
					} else {
						try {
							MobileUtils.hideKeyboard(testCase.getMobileDriver());
						} catch (Exception e) {
							// Ignoring any exceptions because keyboard is sometimes not displayed on some
							// Android devices.
						}
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton")) {
						MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton");
						flag = flag & DASSettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find DAS Name Text Box");
				}
			} else if (parameters.get(0).equalsIgnoreCase("DAS Panel")
					|| parameters.get(0).equalsIgnoreCase("Sensor")) {
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
			} else if (parameters.get(0).equalsIgnoreCase("Switch") || parameters.get(0).equalsIgnoreCase("Dimmer")) {
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if (zwaveScreen.isEditNamingFieldDisplayed()) {
					flag = flag & zwaveScreen.editNameToSwitch(parameters.get(1));
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & zwaveScreen.saveEditedNameToSwitch();
						inputs.setInputValue("DEVICE_NAME_TO_REVERT", parameters.get(1));
					} else {
						flag = flag & zwaveScreen.saveEditedNameToSwitchOnAndroid();
						inputs.setInputValue("DEVICE_NAME_TO_REVERT", parameters.get(1));
						if (parameters.get(0).equalsIgnoreCase("Switch")) {
							flag = flag & zwaveScreen.ClickSwitchSettingFromZwaveDevices();
						} else if (parameters.get(0).equalsIgnoreCase("Dimmer")) {
							flag = flag & zwaveScreen.ClickDimmerSettingFromZwaveDevices();
						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Unable to locate the naming text field");
				}
			} else if (parameters.get(0).equalsIgnoreCase("Access Sensor")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "NEW NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.RenameSensorName(givenSensorName);
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Window Sensor")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "NEW NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.RenameSensorName(givenSensorName);
					break;
				}
				case "EXISTING DOOR SENSOR NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.enterExistingSensorName(givenSensorName);
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Motion Sensor")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "NEW NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.RenameSensorName(givenSensorName);
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("ISMV")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "NEW NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.RenameSensorName(givenSensorName);
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("OSMV")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "NEW NAME": {
					String givenSensorName = inputs.getInputValue("LOCATION1_DEVICE1_OUTDOORMOTIONVIEWER1");
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.RenameSensorName(givenSensorName);
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("door") || parameters.get(0).equalsIgnoreCase("window")
					|| parameters.get(0).equalsIgnoreCase("MOTION")
					|| parameters.get(0).equalsIgnoreCase("MOTION SENSOR")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				flag = flag & sensor.editSensorNameToCustom(parameters.get(0), parameters.get(1), inputs);
				if (parameters.get(0).toUpperCase().contains("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", parameters.get(1));
				} else if (parameters.get(0).toUpperCase().contains("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", parameters.get(1));
				} else if (parameters.get(0).toUpperCase().contains("MOTION SENSOR")
						|| parameters.get(0).toUpperCase().contains("MOTION")) {
					inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(1));
				}
			} else if (parameters.get(0).toUpperCase().contains("DUPLICATE NAME")) {
				String check = parameters.get(1);
				switch (check.toUpperCase()) {
				case "CUSTOM NAME": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.editSensorNameToCustom(parameters.get(0).substring(10), inputs);
					break;
				}
				case "ACCESS SENSOR1": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.assigningDuplicateSensorName(inputs)) {
						Keyword.ReportStep_Pass(testCase, "Name has Changed");
					}
					break;
				}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Thermostat")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (ts.isThermostatNameTextBoxVisible(5)) {
					flag = flag & ts.clearThermostatNameTextBox();
					if (ts.setValueToThermostatNameTextBox(parameters.get(1))) {

						inputs.setInputValueWithoutTarget("PREVIOUS_LOCATION1_DEVICE1_NAME",
								inputs.getInputValue("LOCATION1_DEVICE1_NAME"));

						inputs.setInputValue("LOCATION1_DEVICE1_NAME", parameters.get(1));

						Keyword.ReportStep_Pass(testCase, "Successfully set Thermostat Name to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set Thermostat Name to: " + parameters.get(1));
					}
					try {
						if (ts.isBackButtonVisible(5)) {
							ts.clickOnBackButton();
						}
					} catch (Exception e) {
						// Ignoring any exceptions because keyboard is sometimes not displayed on some
						// Android devices.
					}
				}
			} 
			//Amresh WLD Renaming
			else if (parameters.get(0).equalsIgnoreCase("Water Leak Detector")) {
				WLDConfigurationScreen config = new WLDConfigurationScreen(testCase);

				if (config.isConfigurationWLDNameVisible() && parameters.get(1).equals("Original Name")) 
				{
					flag &= config.clearWLDNameTextBox(); 
					flag &= config.setValueToWLDNameTextBox(inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					inputs.setInputValueWithoutTarget("LOCATION1_DEVICE1_NAME",parameters.get(1));
					if(flag){
						Keyword.ReportStep_Pass(testCase, "Successfully set WLD Name to: " + parameters.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set WLD Name to: " + parameters.get(1));
					}
					System.out.println("Keyboard error not vissible");
				}
				else 
				{	
					WLDConfigurationScreen config1 = new WLDConfigurationScreen(testCase);
					flag &= config1.clearWLDNameTextBox(); 
					System.out.println("Cleared Text field");
					flag &= config1.setValueToWLDNameTextBox(parameters.get(1));
					System.out.println("set value");
					//inputs.setInputValueWithoutTarget("LOCATION1_DEVICE1_NAME",parameters.get(1));
					if(flag){
						Keyword.ReportStep_Pass(testCase, "Successfully set WLD Name to: " + parameters.get(1));
					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to set WLD Name to: " + parameters.get(1));
					}
				}
				
			}
//Amresh WLD Ends here
			else if (parameters.get(0).equalsIgnoreCase("DAS Camera")) {
				BaseStationSettingsScreen ts = new BaseStationSettingsScreen(testCase);
				if (ts.isCameraNameFieldVisible()) {
					flag &= ts.clearCameraNameTextBox();
					flag &= ts.setValueToCameraNameTextBox(parameters.get(1));
					inputs.setInputValueWithoutTarget("NEW_LOCATION1_CAMERA1_NAME", parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully set Thermostat Name to: " + parameters.get(1));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set Thermostat Name to: " + parameters.get(1));
					}
					try {
						if (ts.isBackButtonVisible(5)) {
							if (ts.clickOnBackButton()) {
								Keyword.ReportStep_Pass(testCase, "Successfully select on back option");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to select back option");
							}
						}
					} catch (Exception e) {
						// Ignoring any exceptions because keyboard is sometimes not displayed on some
						// Android devices.
					}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not find Thermostat Name Text field");
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
