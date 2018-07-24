
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
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASCameraUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;

import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.PrimaryCard;

import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class SelectElementOnAScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user selects \"(.*)\" from \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("Entry delay")) {
				switch (parameters.get(0).toUpperCase()) {
				case "PAUSE": {
					AlarmScreen click = new AlarmScreen(testCase);
					boolean flag = false;
					while (flag == false) {
						click.clickLiveStreamingArea();
						flag = click.clickPauseStreaming();
					}
					break;
				}
				case "RESUME": {
					AlarmScreen click = new AlarmScreen(testCase);
					click.clickPlayStreaming();
					break;
				}
				case "SWITCH TO HOME": {
					DASAlarmUtils.clickOnSwitchToHome(testCase, inputs);
					int i = 0;
					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)) {
						System.out.println("Waiting for dismiss alarm request to complete");
					}
					break;
				}
				case "SWITCH TO NIGHT": {
					DASAlarmUtils.clickOnSwitchToNight(testCase, inputs);
					break;
				}
				case "ATTENTION": {
					DASAlarmUtils.clickOnAttention(testCase, inputs);
					break;
				}
				case "NO OPTIONS": {
					DASAlarmUtils.timeOutForNoSensorAction(testCase, inputs);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} 
			else if (parameters.get(1).equalsIgnoreCase("Thermostat Solution Card")) {
				switch (parameters.get(0).toUpperCase()) {
				case "MODE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					thermo.clickOnModeButton();
					
					break;
				}
				}
			}
			else if (parameters.get(1).equalsIgnoreCase("Change Mode")) {
				inputs.setInputValue("SystemMode", parameters.get(0).toUpperCase());
				switch (parameters.get(0).toUpperCase()) {
				case "INFO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					thermo.clickOnInfoButton();
					
					break;
				}
				case "SAVE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnSaveButton())
					{
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					
					break;
				}
				case "X": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnXButton())
					{
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					
					break;
				}
				case "HEAT": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnHeatButton())
					{
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					break;
				}
				case "COOL": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnCoolButton())
					{
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					break;
				}
				case "OFF": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnOffButton())
					{
						Thread.sleep(10);
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					
					break;
				}
				case "AUTO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					if(thermo.clickOnAutoButton())
					{
						Keyword.ReportStep_Pass(testCase,
								" The item " + parameters.get(0).toUpperCase() + "is selected");
					}
					else
					{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" The item " + parameters.get(0).toUpperCase()+ "is not selected");
					}
					
					break;
				}
				}
			}
				
		
				else if (parameters.get(1).equalsIgnoreCase("Camera Solution Card")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRMS ATTENTION": {
					flag = DASCameraUtils.clickOnAttention(testCase);
					flag = flag & DASCameraUtils.clickOnCreateAttention(testCase, inputs);
					break;
				}
				case "CANCELS ATTENTION": {
					flag = flag & DASCameraUtils.clickOnCancelAttention(testCase, inputs);
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("alarm")) {
				switch (parameters.get(0).toUpperCase()) {
				case "PAUSE": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = click.clickPauseStreaming();

					break;
				}
				case "RESUME": {
					AlarmScreen click = new AlarmScreen(testCase);
					click.clickPlayStreaming();
					break;
				}
				case "DISMISS ALARM": {
					inputs.setInputValue("ALARM_DISMISSED_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					inputs.setInputValue("HOME_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					flag = flag & DASAlarmUtils.clickOnDismissAlarm(testCase, inputs);
					AlarmScreen alarmScreen = new AlarmScreen(testCase);
					int i = 0;

					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)
							&& alarmScreen.isAlarmDismissButtonDisplayed()) {
						System.out.println("Waiting for dismiss alarm request to complete");
						i++;
					}
					break;
				}
				case "DISMISS ALARM OVERLAY WITH ZWAVE ACTION IN PROGRESS": {
					inputs.setInputValue("ALARM_DISMISSED_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					inputs.setInputValue("HOME_TIME",
							LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					flag = flag & DASAlarmUtils.clickOnDismissAlarm(testCase, inputs);
					ZwaveScreen ZScreen = new ZwaveScreen(testCase);
					int i = 0;

					while (i < 3 && DASAlarmUtils.verifyProgressDisplayed(testCase)
							&& !ZScreen.isCancelFromNavigationDisplayed()) {
						System.out.println("Waiting for dismiss alarm request to complete");
					}
					break;
				}
				case "CALL": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickOnCall();
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			}

			else if (parameters.get(1).equalsIgnoreCase("Exclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Inclusion Mode Active")
					|| parameters.get(1).equalsIgnoreCase("Activate ZWAVE Device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONFIRM CANCEL": {
					DASZwaveUtils.clickCancelFromNavigation(testCase);
					DASZwaveUtils.clickConfirmOnCancelFromNavigation(testCase);
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("install device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ZWAVE DEVICE": {
					AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
					ads.clickOnZwaveFromAddNewDevice();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ZWAVE Utilities")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CONTROLLER FACTORY RESET": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFactoryReset();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ZWAVE Devices")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ALL ON": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOn();
					break;
				}
				case "ALL OFF": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnAllOff();
					break;
				}
				case "FIX ALL": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnFixAll();
					zwaveScreen.clickOnFixAllPopupConfirm();
					zwaveScreen.clickOnFixAllPopupAccept();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Switch Settings")
					|| parameters.get(1).equalsIgnoreCase("Dimmer Settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "DELETE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.ClickDeleteFromSettings();
					break;
				}
				case "REPLACE": {
					ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
					zwaveScreen.clickOnReplaceButton();
					zwaveScreen.clickOnReplacePopupOk();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "15": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (bs.is15SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn15SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"15 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "30": {
					if (bs.is30SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn30SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"30 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "45": {
					if (bs.is45SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn45SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"45 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "60": {
					if (bs.is60SecondsEntryExitDelayOptionVisible()) {
						flag = flag & bs.clickOn60SecondsEntryExitDelayOption();
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"60 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ADD NEW DEVICE")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SMART HOME SECURITY": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					flag = flag & dasDIY.selectDeviceToInstall(parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("CHOOSE LOCATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CREATE NEW LOCATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCreateNewLocationLinkVisible()) {
						flag = flag & dasDIY.clickOnCreateNewLocationLink();
					}
					break;
				}
				default: {
					inputs.setInputValue("LOCATION1_NAME", parameters.get(0));
					flag = flag & DIYRegistrationUtils.selectAvaiableLocation(testCase, parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("NAME YOUR BASE STATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CREATE NEW BASE STATION": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCreateCustomBaseStationNameLinkVisible()) {
						flag = flag & dasDIY.clickOnCreateCustomBaseStationNameLink();
					}
					break;
				}
				default: {
					if (!inputs.isInputAvailable("LOCATION1_CAMERA1_NAME")) {
						inputs.setInputValue("LOCATION1_CAMERA1_NAME", parameters.get(0));
					} else {
						inputs.setInputValue("LOCATION1_CAMERA2_NAME", parameters.get(0));
					}
					flag = flag & DIYRegistrationUtils.selectAvaiableBaseStationName(testCase, parameters.get(0));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("POWER BASE STATION")) {
				switch (parameters.get(0).toUpperCase()) {
				case "NOT PULSING BLUE": {
					flag = flag & DIYRegistrationUtils.clickOnNotPulsingBlueLink(testCase);
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("SELECT BASE STATION")) {
				flag = flag & DIYRegistrationUtils.selectABaseStationFromListOfAvailableBaseStations(testCase,
						parameters.get(0));
			} else if (parameters.get(1).equalsIgnoreCase("CONNECT TO NETWORK")) {
				switch (parameters.get(0).toUpperCase()) {
				case "ADD A NETWORK": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isAddANetworkButtonVisible()) {
						dasDIY.clickOnAddANetworkButton();
					}
					break;
				}
				case "ANY WIFI NAME": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isWiFiNetworkNameDisplayedInConnectToNetworkScreen()) {
						dasDIY.clickOnWiFiNetworkNameInConnectToNetworkScreen();
					}
					break;
				}
				default: {
					flag = flag & DIYRegistrationUtils.selectWiFiNameFromTheListOfAvailableNetworks(testCase,
							parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("ADD A NETWORK")) {
				DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
				if (dasDIY.isAddANetworkHeaderTitleVisible()) {
					flag = flag & DIYRegistrationUtils.selectAvailableNetworkType(testCase, parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("NAME MOTION SENSOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(0));
			}

			else if (parameters.get(1).toUpperCase().equals("NAME MOTION SENSOR CUSTOM NAME")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = settingScreen.clickOnCustomNameBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
			} else if (parameters.get(1).toUpperCase().equals("DOOR")
					|| parameters.get(1).toUpperCase().equals("WINDOW")
					|| parameters.get(1).toUpperCase().equals("MOTION SENSOR")) {
				flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				if (parameters.get(1).toUpperCase().equals("DOOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_DOORSENSOR1", parameters.get(0));
				} else if (parameters.get(1).toUpperCase().equals("WINDOW")) {
					inputs.setInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1", parameters.get(0));
				} else if (parameters.get(1).toUpperCase().equals("MOTION SENSOR")) {
					inputs.setInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1", parameters.get(0));
				}
				/*
				 * flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
				 * "SAVING SENSOR PROGRESS BAR", 1);
				 */
			} else if (parameters.get(1).toUpperCase().equals("NAME SENSOR")) {
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = settingScreen.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));

				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Call")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickCancelButton();
					break;
				}
				case "CALL THE POLICE": {
					AlarmScreen click = new AlarmScreen(testCase);
					flag = flag & click.clickCallPoliceButton();
					break;

				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Test Access Sensor")
					|| parameters.get(1).equalsIgnoreCase("Test Motion Sensor")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SENSOR NOT WORKING": {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					flag = flag & sensor.clickOnSensorNotWorking();
					break;
				}

				}
			} else if (parameters.get(1).equalsIgnoreCase("Keyfob settings")
					|| parameters.get(1).equalsIgnoreCase("Window Access settings")
					|| parameters.get(1).equalsIgnoreCase("Door Access settings")
					|| parameters.get(1).equalsIgnoreCase("Motion sensor settings")) {
				switch (parameters.get(0).toUpperCase()) {
				case "OFF STATUS": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnSensorStatusTextOnSensorSettingsScreen(parameters.get(0).toUpperCase());
					if (flag) {
						System.out.println("Successfully clicked on" + parameters.get(0));
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on" + parameters.get(0));
					}
					break;
				}
				case "MODEL AND FIRMWARE DETAILS": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = flag & settingScreen.clickOnFirmwareDetailsOption();
					break;
				}
				case "SENSOR COVER TAMPERED": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = flag & settingScreen.clickOnSensorCoverTamperOption();
					break;
				}
				case "SIGNAL STRENGTH AND TEST": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnSignalStrengthandTestOption();
					break;
				}
				case "DELETE SENSOR": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (parameters.get(1).equalsIgnoreCase("Keyfob settings")) {
						flag = flag & bs.clickOnDeleteKeyfobSensorButton();
					} else {
						flag = flag & bs.clickOnDeleteSensorButton();
					}
					break;
				}
				case "NAME TEXT FIELD": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (parameters.get(1).equalsIgnoreCase("Keyfob settings")) {
						flag = flag & bs.clickOnKeyfobNameTextField();
					} else {
						flag = flag & bs.clickOnNameTextField();
					}

					if (flag) {
						System.out.println("Successfully clicked on" + parameters.get(0));
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on" + parameters.get(0));
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("MOTION DETECTION SETTINGS")) {
				switch (parameters.get(0).toUpperCase()) {
				default: {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					cs.selectZone(parameters.get(0));
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor Cover Tamper")) {
				switch (parameters.get(0).toUpperCase()) {
				case "CLEAR TAMPER": {
					SensorSettingScreen settingScreen = new SensorSettingScreen(testCase);
					flag = flag & settingScreen.clickOnClearCoverTamperOption();
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Access Sensor Help")
					|| parameters.get(1).equalsIgnoreCase("Motion Sensor Help")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GET ADDITIONAL HELP": {
					flag = flag & sensor.clickOnGetAdditionalHelpButton();
					break;
				}
				case "TEST SIGNAL STRENGTH": {
					if (parameters.get(1).toUpperCase().contains("ACCESS")) {
						flag = flag & sensor.clickOnTestSignalStrength();
					} else if (parameters.get(1).toUpperCase().contains("MOTION SENSOR")) {
						flag = flag & sensor.clickOnTestSignalStrengthForMotionSensor();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}

				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor List Settings")
					|| parameters.get(1).equalsIgnoreCase("Keyfob List Settings")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "ADD BUTTON": {
					flag = flag & sensor.clickOnAddSensorButton();
					flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("Set Up Accessories")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "MOTION SENSOR SETUP BUTTON":
				case "ACCESS SENSOR SETUP BUTTON":
				case "KEYFOB SETUP BUTTON": {
					flag = flag & sensor.clickOnSetUpButton(inputs, parameters.get(0));
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				case "DONE": {
					Thread.sleep(3000);
					flag = flag & sensor.clickOnDoneButton();
					break;
				}
				case "HELP": {
					flag = flag & sensor.clickOnSensorHelpButton();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully clicked on " + parameters.get(0).toUpperCase() + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("Sensor Keyfob Overview")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WATCH THE HOW TO VIDEO": {
					flag = flag & sensor.clickOnWatchHowToVideoButton();
					break;
				}
				case "GET STARTED": {
					flag = flag & sensor.clickOnGetStartedFromSensorOverview();
					break;
				}
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						flag = flag & sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor on location")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "WONT FIT AS SHOWN": {
					flag = flag & sensor.clickOnWontFitAsShownButton();
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on \"+parameters.get(0)+\" button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Place Sensor")
					|| parameters.get(1).equalsIgnoreCase("Sensor Overview")
					|| parameters.get(1).equalsIgnoreCase("MOUNT IN A CORNER")
					|| parameters.get(1).equalsIgnoreCase("MOUNT ON THE WALL")
					|| parameters.get(1).equalsIgnoreCase("Keyfob Overview")
					|| parameters.get(1).equalsIgnoreCase("NAME Keyfob")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input not hadled", true);
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} 

			else if (parameters.get(1).equalsIgnoreCase("Thermostat Solution Card")) {
				switch (parameters.get(0).toUpperCase()) {
				case "MODE": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnModeButton();
					
					break;
				}
				case "MAX SET TEMPERATURE BY TAPING ON UP STEPPER":{

					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.setMaxTemperatureByTappingUpStepper(inputs);
					break;
				}
				case "MIN SET TEMPERATURE BY TAPING ON DOWN STEPPER":{

					PrimaryCard thermo = new PrimaryCard(testCase);
					flag = flag & thermo.setMinTemperatureByTappingDownStepper(inputs);
					break;
				}
				case "FAN":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnFanButton();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			}

			
			
			
			else if (parameters.get(1).equalsIgnoreCase("Change Fan")) {
				switch (parameters.get(0).toUpperCase()) {
				case "INFO": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnInfoButton();
					
					break;
				}
				case "AUTO FAN":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnAutoFanButton();
					inputs.setInputValue("SelectedFanMode",parameters.get(0).toUpperCase());
					System.out.println(inputs.getInputValue("SelectedFanMode"));
					break;
				}
				case "CIRCULATE":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnCirculateFanButton();
					inputs.setInputValue("SelectedFanMode",parameters.get(0).toUpperCase());
					break;
				}
				case "ON":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnONFanButton();
					inputs.setInputValue("SelectedFanMode",parameters.get(0).toUpperCase());
					break;
				}
				case "X":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnXButton();
					break;
				}
				case "SAVE":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnSaveButton();
					break;
				}
				
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			}
			
			else if (parameters.get(1).equalsIgnoreCase("Mode info")||parameters.get(1).equalsIgnoreCase("Fan info")) {

				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=flag&thermo.clickOnBackButton();
					
					break;
				}
				
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			}
			
			else if (parameters.get(1).equalsIgnoreCase("Locate Sensor")
					|| parameters.get(1).equalsIgnoreCase("Signal Strength")
					|| parameters.get(1).equalsIgnoreCase("Locate Motion Sensor")
					|| parameters.get(1).equalsIgnoreCase("Motion Sensor Signal Strength")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "BACK": {
					flag = sensor.clickOnTestSensorBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}

			} else if (parameters.get(1).equalsIgnoreCase("Test Sensor")
					|| parameters.get(1).equalsIgnoreCase("Configuration Success")
					|| parameters.get(1).equalsIgnoreCase("keyfob Configuration Success")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "DONE": {
					flag = flag & sensor.clickOnDoneButton();
					break;
				}
				case "CANCEL": {
					if (sensor.isCancelButtonDisplayed()) {
						Thread.sleep(3000);
						sensor.clickOnCancelButton();
					}
					break;
				}
				case "BACK": {
					flag = sensor.clickOnSensorSettingBack();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to click: " + parameters.get(1));
					}
					break;
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on \"+parameters.get(0)+\" button");
				}
			}

			else if (parameters.get(1).equalsIgnoreCase("Mount Sensor")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")
						&& parameters.get(0).equals("In a Wall Corner")) {
					String selectedMountSensorValue = parameters.get(0).replace("In a Wall Corner", "In a wall Corner");
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, selectedMountSensorValue);
				} else {
					flag = flag & DIYRegistrationUtils.selectAvailableSensorName(testCase, parameters.get(0));
				}
			} else if (parameters.get(1).equalsIgnoreCase("sensor pairing help")) {
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (parameters.get(0).toUpperCase()) {
				case "GET ADDITIONAL HELP": {
					flag = flag & sensor.clickOnGetAdditionalHelpButton();
					break;
				}
				}
				if (flag) {
					System.out.println("Successfully clicked on " + parameters.get(0) + " button");
					Keyword.ReportStep_Pass(testCase, "Successfully clicked on " + parameters.get(0) + " button");
				}
			} else if (parameters.get(1).equalsIgnoreCase("Setup Amazon Alexa")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SIGN IN": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isAmazonSignInVisible()) {
						flag = flag & bs.clickOnAmazonSignInButton();

					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("AMAZON ALEXA SETTINGS")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SIGN OUT": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					if (bs.isAmazonSignOutVisible()) {
						flag = flag & bs.clickOnAmazonSignOutVisible();
						Thread.sleep(3000);
					}
					break;
				}
				}
			} else if (parameters.get(1).equalsIgnoreCase("MANAGE ALERTS")) {
				switch (parameters.get(0).toUpperCase()) {
				case "TEMPERATURE ALERT FOR THIS RANGE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isThermostatTempAlertRangeVisible()) {
						flag = flag & ts.clickOnThermostatTempAlertRange();
					}
					break;
				}
				case "BELOW TEMPERATURE RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectBelowTemperatureRangeValue(testCase, inputs, parameters.get(0));
					break;
				}
				case "ABOVE TEMPERATURE RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectAboveTemperatureRangeValue(testCase, inputs, parameters.get(0));
					break;
				}
				case "HUMIDITY ALERT FOR THIS RANGE": {
					ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
					if (ts.isThermostatHumidityAlertRangeVisible()) {
						flag = flag & ts.clickOnThermostatHumidityAlertRange();
					}
					break;
				}
				case "BELOW HUMIDITY RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectBelowHumidityRangeValue(testCase, inputs, parameters.get(0));
					break;
				}
				case "ABOVE HUMIDITY RANGE": {
					flag = flag & HBNAEMEASettingsUtils.selectAboveHumidityRangeValue(testCase, inputs, parameters.get(0));
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
