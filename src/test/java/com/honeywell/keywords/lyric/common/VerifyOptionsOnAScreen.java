package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.screens.AdhocScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.WLDSolutionCard;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

import java.util.Random;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;

public class VerifyOptionsOnAScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the following (.+) options:$")
	public boolean keywordSteps() throws KeywordException {
		CHILUtil chUtil = null;
		DeviceInformation deviceInfo = null;

		try {
			chUtil = new CHILUtil(inputs);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		switch (expectedScreen.get(0).toUpperCase()) {
		case "ENTRYDELAY": {
			AlarmScreen check = new AlarmScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Elements");
				switch (parameter.toUpperCase()) {
				case "ENTRY DELAY WITH DOOR OPEN TITLE": {
					flag = flag & check.isEntryDelayTitleVisible();
					break;
				}
				case "ENTRY DELAY WITH SENSOR NAME": {
					flag = flag & check.isEntryDelaySubTitleVisible(inputs);
					break;
				}
				case "ENTRY DELAY SUBTITLE AS LOCATION NAME": {
					flag = flag & check.isEntryDelayLocationVisible(inputs);
					break;
				}
				case "ENTRY DELAY LIVE STREAM": {
					flag = flag & check.verifyLiveStreamingProgress();
					break;
				}
				case "ENTRY DELAY ALARM IN SECS TEXT  ": {
					flag = flag & check.isAlarmWillSoundInTextVisible();
					break;
				}
				case "ENTRY DELAY ALARM IN SECS COUNTER": {
					flag = flag & check.AlarmInSecsCounter();
					break;
				}
				case "ENTRY DELAY SWITCH TO HOME": {
					System.out.println("Inside entry delay switch to home ");
					flag = flag & check.isSwitchToHomeExists();
					break;
				}
				case "ENTRY DELAY SWITCH TO NIGHT": {
					System.out.println("Inside entry delay switch to night ");
					flag = flag & check.isSwitchToNightExists();
					break;
				}
				case "ENTRY DELAY ATTENTION": {
					System.out.println("Inside entry delay attention ");
					flag = flag & check.isAttentionButtonExists();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " has not found");
				}
				flag = true;

			}
			break;
		}
		case "MODE": {
			for (int i = 0; i < data.getSize(); i++) {
				PrimaryCard card = new PrimaryCard(testCase);
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "HEAT": {
					flag = flag & card.isHeatModeVisible();
					break;
				}
				case "COOL": {
					flag = flag & card.isCoolModeVisible();
					break;
				}
				case "OFF": {
					flag = flag & card.isOffModeVisible();
					break;
				}
				case "AUTO": {
					flag = flag & card.isAutoModeVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + parameter + " is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Mode: " + parameter + " is not displayed");
				}
			}
			break;
		}

		case "ALARM": {
			AlarmScreen check = new AlarmScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Elements");
				switch (parameter.toUpperCase()) {
				case "ALARM TITLE": {
					flag = flag & check.isAlarmTitleVisible();
					break;
				}
				case "ALARM SUBTITLE": {
					flag = flag & check.isAlarmLocationVisible(inputs);
					break;
				}
				case "ALARM LIVE STREAM": {
					flag = flag & check.verifyLiveStreamingProgress();

					break;
				}
				case "ALARM NAVIGATE BACK BUTTON": {
					flag = flag & check.isAlarm_NavigatebackVisible();
					break;
				}
				case "CALL": {
					flag = flag & check.isCallButtonVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "The" + parameter + "has not found");
				}
				flag = true;
			}
			break;
		}
		case "SECURITY SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the DAS Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Settings: '" + fieldTobeVerified + "' is not present on the DAS Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "CAMERA SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the Camera Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Settings: '" + fieldTobeVerified + "' is not present on the Camera Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}

		case "CAMERA CONFIGURATION": {
			CameraSettingsScreen ts = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Details");
				try {
					if (ts.isCameraConfigurationsOptionVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the Camera Configuration screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings: '" + fieldTobeVerified
								+ "' is not present on the Camera Configuration screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}

		case "THERMOSTAT SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "ThermostatSettings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the Thermostat Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings: '" + fieldTobeVerified
								+ "' is not present on the Thermostat Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "ENTRY-EXIT DELAY": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				try {
					if (bs.verifyParticularEntryExitDelayOptionVisible(data.getData(i, "Delays"))) {
						Keyword.ReportStep_Pass(testCase, "Option: '" + data.getData(i, "Delays")
								+ "' is present on the Entry/Exit Delay screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option: '"
								+ data.getData(i, "Delays") + "' is not present on the Entry/Exit Delay screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "BASE STATION CONFIGURATION": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifyDASNameOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' DAS Option is not displayed on the Base Station Configurations Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Battery")) {
					if (bs.verifyBatteryOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Battery' DAS Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery' DAS Option is not displayed on the Base Station Configurations Screen");
					}

					if (bs.verifyBatteryStatusTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Battery Status' is correctly displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery Status' is not correctly displayed");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnBaseStationConfigurationScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Base Station Configurations Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Base Station Configurations Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "SENSOR SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			SensorSettingScreen sensorSettingScreen = new SensorSettingScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifySensorNameOptionTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' Sensor Option is present on the Sensors Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' Sensor Option is not displayed on the Sensor Settings Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Status")) {
					if (bs.verifyStatusOptionTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Status' Option is present on the Sensors Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Status' Option is not displayed on the Sensor Settings Screen");
					}
					if (bs.verifySensorStatusTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Sensor Status' is correctly displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Sensor Status' is not correctly displayed");
					}

				} else if (data.getData(i, "Settings").equalsIgnoreCase("Signal Strength And Test")) {
					if (bs.verifySignalStrengthOptionTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Signal Strength And Test' Option is present on the Sensors Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Signal Strength And Test' Option is not displayed on the Sensor Settings Screen");
					}
				}

				else if (data.getData(i, "Settings").equalsIgnoreCase("Battery")) {
					if (bs.verifyBatteryOptionTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Battery' Option is present on the Sensor Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery' Option is not displayed on the Sensor Settings Screen");
					}

					if (sensorSettingScreen.verifyBatteryStatusTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase, "'Battery Status' is correctly displayed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Battery Status' is not correctly displayed");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnSensorSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Sensor Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Sensor Settings Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Delete")) {
					if (bs.verifyDeleteOptionOnSensorSettingsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "'Delete' Option is present on the Sensor Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Delete' Option is not displayed on the Sensor Settings Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "KEYFOB SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Name")) {
					if (bs.verifyKeyfobNameOptionTextOnKeyfobSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Name' Keyfob Option is present on the Keyfob Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Name' Keyfob Option is not displayed on the Keyfob Settings Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Model and Firmware Details")) {
					if (bs.verifyModelAndFirmwareDetailsOptionTextOnKeyfobSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"'Model and Firmware Details' Option is present on the Keyfob Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Model and Firmware Details' Option is not displayed on the Keyfob Settings Screen");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Delete")) {
					if (bs.verifyDeleteOptionOnSensorSettingsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "'Delete' Option is present on the Sensor Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"'Delete' Option is not displayed on the Sensor Settings Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "PANEL MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifyDASModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifyDASFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "KEYFOB MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifyKeyfobModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifyKeyfobFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "SENSOR MODEL AND FIRMWARE DETAILS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				if (data.getData(i, "Settings").equalsIgnoreCase("Model Details")) {
					if (bs.verifySensorModelDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Model Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Model Details are not displayed correctly");
					}
				} else if (data.getData(i, "Settings").equalsIgnoreCase("Firmware Details")) {
					if (bs.verifySensorFirmwareDetailsOnModelAndFirmwareDetailsPage()) {
						Keyword.ReportStep_Pass(testCase, "Firmware Details are displayed correctly");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Firmware Details are not displayed correctly");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + data.getData(i, "Settings"));
				}
			}
			break;
		}
		case "VIDEO SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Settings");
				try {
					if (bs.verifyParticularBaseStationSettingsVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"Video Setting: '" + fieldTobeVerified + "' is present on the Video Settings screen");
					} else {
						flag = false;

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Video Setting: '"
								+ fieldTobeVerified + "' is not present on the Video Settings screen");

					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "MOTION SENSITIVITY SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				int startx = (dimension.width * 20) / 100;
				int starty = (dimension.height * 62) / 100;
				int endx = (dimension.width * 22) / 100;
				int endy = (dimension.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			} else {
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
			}
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "MotionSensitivityOptionsSettings");
				try {
					if (cs.isMotionSensitivityOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Security State: '" + fieldToBeVerified
								+ "' is present on the Motion Sensitivity Options section in Motion Detection screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security State: '"
								+ fieldToBeVerified
								+ "' is not present on the Motion Sensitivity Options section in Motion Detection screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "NIGHT VISION SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "NightVisionSettingsOptions");
				try {
					if (cs.isNightVisionOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Security State: '" + fieldToBeVerified
								+ "' is present in the list of Options in Night Vision screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security State: '"
								+ fieldToBeVerified + "' is not present in the list of Options in Night Vision screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "VIDEO QUALITY SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "VideoQualitySettingsOptions");
				try {
					if (cs.isVideoQualityOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Security State: '" + fieldToBeVerified
								+ "' is present in the list of Options in Video Quality screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Security State: '" + fieldToBeVerified
										+ "' is not present in the list of Options in Video Quality screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "SECURITY MODE": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "SecurityStates");
				try {
					if (sc.isSecurityStateVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Security State: '" + fieldTobeVerified
								+ "' is present on the Security Solutions Card screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security State: '"
								+ fieldTobeVerified + "' is not present on the Security Solutions Card screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "INDOOR TEMPERATURE ALERT": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "IndoorTempAlertOptions");
				try {
					if (ts.isThermostatIndoorTempAlertOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Indoor Temperature Alert Options: '" + fieldToBeVerified
								+ "' is present in the list of Options when Indoor Temperature Alert toggle switch is enabled in Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Indoor Temperature Alert Options: '" + fieldToBeVerified
										+ "' is not present in the list of Options when Indoor Temperature Alert toggle switch is enabled in Manage Alerts screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "TEMPERATURE ALERT FOR THIS RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "AlertTempRangeOptions");
				try {
					if (ts.isThermostatIndoorTempAlertRangeOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Indoor Temperature Alert Range Options: '"
								+ fieldToBeVerified
								+ "' is present in the list of Options when Indoor Temperature Alert Range is selected in Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Indoor Temperature Alert Range Options: '" + fieldToBeVerified
										+ "' is not present in the list of Options when Indoor Temperature Alert Range is selected in Manage Alerts screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "INDOOR HUMIDITY ALERT": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "IndoorHumidityAlertOptions");
				try {
					if (ts.isThermostatIndoorHumidityAlertOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Indoor Humidity Alert Options: '" + fieldToBeVerified
								+ "' is present in the list of Options when Indoor Humidity Alert toggle switch is enabled in Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Indoor Humidity Alert Options: '" + fieldToBeVerified
										+ "' is not present in the list of Options when Indoor Humidity Alert toggle switch is enabled in Manage Alerts screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "HUMIDITY ALERT FOR THIS RANGE": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "AlertHumidityRangeOptions");
				try {
					if (ts.isThermostatIndoorHumidityAlertRangeOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Indoor Humidity Alert Range Options: '" + fieldToBeVerified
								+ "' is present in the list of Options when Indoor Humidity Alert Range is selected in Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Indoor Humidity Alert Range Options: '" + fieldToBeVerified
										+ "' is not present in the list of Options when Indoor Humidity Alert Range is selected in Manage Alerts screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "TEMPERATURE ALERT": {
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "TempRangeAlertMsg");
				try {
					HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
					if (HBNAEMEASettingsUtils.verifyIndoorTempRangeAlertInActivityHistoryScreen(testCase, inputs,
							fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Alert for the set '" + fieldToBeVerified
								+ "' temperature is present in Activity History screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Alert for the set '"
								+ fieldToBeVerified + "' temperature is not present in Activity History screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "HUMIDITY ALERT": {
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "HumidityRangeAlertMsg");
				try {
					HBNAEMEASettingsUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
					if (HBNAEMEASettingsUtils.verifyIndoorHumidityRangeAlertInActivityHistoryScreen(testCase, inputs,
							fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Alert for the set '" + fieldToBeVerified
								+ "' humidity is present in Activity History screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Alert for the set '"
								+ fieldToBeVerified + "' humidity is not present in Activity History screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}

		case "Sound Sensitivity Settings": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Settings");
				try {
					if (cs.isSoundDetectionOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Sound Option: '" + fieldToBeVerified
								+ "' is present in the list of Options in Sound Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera Sound Option: '"
								+ fieldToBeVerified + "' is not present in the list of Options in Sound Screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}

		case "SOUND":
		case "SOUND DETECTION": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "SoundOptions");
				try {
					if (ts.isThermostatSoundOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Sound Option: '" + fieldToBeVerified
								+ "' is present in the list of Options in Sound Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Thermostat Sound Option: '"
								+ fieldToBeVerified + "' is not present in the list of Options in Sound Screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "MODE INFO": {
			PrimaryCard thermo = new PrimaryCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("AUTO - COOL OR HEAT AS NEEDED TO REACH TARGET TEMPERATURE")) {
					if (thermo.isAutoModeDefinitionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("HEAT - HEAT TO REACH TARGET TEMPERATURE")) {
					if (thermo.isHeatModeDefinitionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("COOL - COOL TO REACH TARGET TEMPERATURE")) {
					if (thermo.isCoolModeDefinitionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("OFF - TURN SYSTEM OFF")) {
					if (thermo.isSystemOffModeDefinitionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				}
			}

			break;
		}
		case "FAN INFO": {
			PrimaryCard thermo = new PrimaryCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("AUTO - FAN RUNS WHILE HEATING OR COOLING")) {
					if (thermo.isAutoFanDefinitionVisibleOnInfoScreen()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("CIRCULATE - FAN RUNS INTERMITTENTLY TO CIRCULATE AIR")) {
					if (thermo.isCirculateFanDefinitionVisibleOnInfoScreen()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("ON - FAN RUNS CONTINUOUSLY")) {
					if (thermo.isOnFanDefinitionVisibleOnInfoScreen()) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				}

			}

			break;
		}
		case "ALERTS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			ArrayList<String> lstData = new ArrayList<String>();
			for (int i = 0; i < data.getSize(); i++) {
				lstData.add(data.getData(i, "Alerts"));
			}

			if (lstData.contains("Doors and Windows")) {
				// Check for sensor and if not present add
				deviceInfo = new DeviceInformation(testCase, inputs);
				try {
					ArrayList<String> getDASSensors = deviceInfo.getDASSensorIDsInADevice();
					if (getDASSensors.size() <= 0) {
						try {
							if (chUtil.getConnection()) {
								String serialNumber = java.util.UUID.randomUUID().toString();
								serialNumber = serialNumber.substring(0, serialNumber.indexOf("-") + 2).replace("-",
										":");
								String payload = "{\"config\":{\"identifiers\":{\"id\":\"Sensor Serial No\",\"serialNumber\":\"Sensor Serial No\",\"shortAddress\":\"BridgeId\",\"macAddress\":\"3234454\"},\"modelName\":\"Model name\",\"versions\":{\"hardware\":\"1.0.0\",\"swPackage\":\"1.0.0\"},\"encryptionKey\":\"000102030405060708090A0B0C0D0E0F\",\"expand\":{\"PeripheralConnectedToInterface\":[{\"identifiers\":{\"id\":\"7\"}}],\"PeripheralAssignedDevice\":[{\"identifiers\":{\"id\":\"00158D000052DC20:1\"},\"type\":\"Input\",\"_subType_id\":[\"889\",\"Contact\"],\"supervisionInterval\":\"PT30M\",\"extension\":[{\"name\":\"isomPIRConfig\",\"sensitivity\":50}]}]},\"extension\":[{\"name\":\"wiselinkPeripheralInfo\",\"RadioCycleValue\":10,\"FastCycleValue\":1,\"NonceKey\":\"000000000000000000000000000000000\",\"ClientID\":\"8787\"}]},\"state\":{\"commState\":{\"state\":[\"normal\"],\"linkQuality\":100},\"batteryState\":{\"state\":[\"normal\"],\"batteryLevel\":60}}}";
								payload = payload.replace("Sensor Serial No", serialNumber).replace("BridgeId",
										String.valueOf(new Random().nextInt(100)));
								if (chUtil.postSensorDiscovery(LyricUtils.locationID, deviceInfo.getDeviceID(),
										true) == 202) {
									Keyword.ReportStep_Pass(testCase, "Sensor Discovery enabled through CHIL");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to enable Sensor through CHIL");
								}

								chUtil.FeedDataIntoIOTHub(deviceInfo.getDeviceID().replace("LSC-", ""), payload,
										"POST ISOM/DeviceMgmt/Peripherals/fullEntity", "DAS", "D-Change");
								Thread.sleep(5000);
								getDASSensors = deviceInfo.getDASSensorIDsInADevice();
								if (chUtil.postSensor(LyricUtils.locationID, deviceInfo.getDeviceID(), 1,
										serialNumber) == 202) {
									Keyword.ReportStep_Pass(testCase, "Sensor Created through CHIL");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to create Sensor through CHIL");
								}
								if (chUtil.postSensorDiscovery(LyricUtils.locationID, deviceInfo.getDeviceID(),
										false) == 202) {
									Keyword.ReportStep_Pass(testCase, "Sensor Discovery disabled through CHIL");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to disable Sensor through CHIL");
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured : " + e.getMessage());
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					flag = false;
					e.printStackTrace();
				}
				try {
					if (!bs.clickOnBackButton()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: Unable to navigate to Back Screen after sensor creation");
					}
					if (!bs.clickOnManageAlerts()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: Unable to click on Manage Alerts Screen after sensor creation");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else if (!lstData.contains("Doors and Windows")) {
				/// check for sensor in chil get and delete
				deviceInfo = new DeviceInformation(testCase, inputs);
				try {

					ArrayList<String> getDASSensors = deviceInfo.getDASSensorIDsInADevice();
					if (getDASSensors.size() > 0) {
						for (String sensorID : getDASSensors) {
							try {
								if (chUtil.getConnection()) {
									if (chUtil.deleteSensor(LyricUtils.locationID, deviceInfo.getDeviceID(), sensorID,
											1) == 202) {
										Keyword.ReportStep_Pass(testCase, "Sensor deleted through CHIL");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to delete Sensor through CHIL");
									}

								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error Occured : " + e.getMessage());
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					flag = false;
					e.printStackTrace();
				}

				// This will execute when the requirement file does not have Doors and Windows
				// data
				try {
					if (!bs.clickOnBackButton()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: Unable to navigate to Back Screen after sensor creation");
					}
					if (!bs.clickOnManageAlerts()) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: Unable to click on Manage Alerts Screen after sensor creation");
					}
					if (!bs.isDoorAndWindowsToggleVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"Manage Alerts: Doors and Windows is not present on the DAS Manage Alerts screen for without sensors");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts: Doors and Windows is not present on the DAS Manage Alerts screen for without sensors");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}

			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Alerts");

				try {
					if (bs.isElementEnabled(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Manage Alerts: '" + fieldTobeVerified
								+ "' is present on the DAS Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Manage Alerts: '"
								+ fieldTobeVerified + "' is not present on the DAS Manage Alerts screen");
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "CAMERA STATUS": {
			CameraSettingsScreen st = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("Email Notifications")) {
					if (st.isCameraEmailNotificationsTextVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Email Notification label Not Found")) {

				}
			}
			break;
		}

		case "SOUND EVENT STATUS": {
			CameraSettingsScreen st = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("Email Notifications")) {
					if (st.isSoundEmailNotificationTextVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Email Notification label Not Found")) {

				}
			}
			break;
		}

		case "MOTION EVENT STATUS": {
			CameraSettingsScreen st = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("Email Notifications")) {
					if (st.isMotionEmailNotificationTextVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								fieldTobeVerified + "' is present on the " + expectedScreen.get(0));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								fieldTobeVerified + " is not present on the " + expectedScreen.get(0));
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Email Notification label Not Found")) {

				}
			}
			break;
		}

		case "THERMOSTAT CONFIGURATION": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "ThermostatConfigurationOptions");
				try {
					if (ts.verifyParticularThermostatConfigurationVisible(fieldTobeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Settings: '" + fieldTobeVerified
								+ "' is present on the Thermostat Configuration screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Settings: '" + fieldTobeVerified
								+ "' is not present on the Thermostat Configuration screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "VENTILATION": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "VentilationOptions");
				try {
					if (ts.isThermostatVentilationOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Ventilation Option: '" + fieldToBeVerified
								+ "' is present in the list of Options in Ventilation Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Ventilation Option: '" + fieldToBeVerified
										+ "' is not present in the list of Options in Ventilation Screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "ACTION SHEET": {
			AdhocScreen ActionSheet = new AdhocScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Action Sheet");
				switch (parameter.toUpperCase()) {
				case "A SPECIFIC TIME": {
					flag = flag & ActionSheet.isSpecificHoldVisible();
					break;
				}
				case "PERMANENT": {
					flag = flag & ActionSheet.isPermanentlyVisible();
					break;
				}
				case "REMOVE HOLD": {
					flag = flag & ActionSheet.isRemoveAdhocVisible();
					break;
				}
				case "CANCEL": {
					flag = flag & ActionSheet.isAdhocCancelVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							" The " + parameter + " has not found");
				}
				flag = true;
			}
			break;
		}
		//Amresh(H297378)
				case "WLDSOLUTIONTEMPERATURE": {
					WLDSolutionCard ActionSheet=new WLDSolutionCard(testCase);
					for (int i = 0; i < data.getSize(); i++) {
						String parameter = data.getData(i, "WLD Solution options");
						switch (parameter.toUpperCase()) {
						case "CURRENT TEMPERATURE": {
							
							flag = flag & ActionSheet.isCurrentTemperatureTitleVisible();
								if (flag) {
									Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
										Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getCurrentTemperatureTitleText());
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"The "+parameter+" is not vissible");
								}break;
								
						}
						case "LAST UPDATED TIME": {
							flag = flag & ActionSheet.isLastUpdatedTimeTitleVisible();
								if (flag) {
									Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
									Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getLastUpdateTitleText());						} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"The "+parameter+" is not vissible");
								}break;
							
						}
						case "TEMPERATURE GRAPH": {
							flag = flag & ActionSheet.isTemperatureGraphTitleVisible();
								if(flag) {
									Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
									Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getTemperatureGraphTitleText());
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"The "+parameter+" is not vissible");
								}break;
									
						}
						case "BATTERY PERCENTAGE": {
							flag = flag & ActionSheet.isBatterypercentageTitleVisible();
								if (flag) {
									Keyword.ReportStep_Pass(testCase, "The" +parameter+ "is Vissibe");
									Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getRemainingBatteryTitleText());

								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"The "+parameter+" is not vissible");
								}break;
									
						}
						case "NEXT UPDATE TIME": {
							flag = flag & ActionSheet.isNextUpdateTimeTitleVisible();
								if (flag) {
									Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
									Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getNextUpdateTimeTitleText());

								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"The "+parameter+" is not vissible");
								}break;
						}
						}
						
					}
					break;
				}
		//Amresh(H297378)
		case "WLDSOLUTIONHUMIDITY": {
			WLDSolutionCard ActionSheet=new WLDSolutionCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WLD Humidity Solution options");
				switch (parameter.toUpperCase()) {
				case "CURRENT HUMIDITY": {	
					flag = flag & ActionSheet.isHumidityGraphTitleVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
								Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getCurrentHumidityTitleText());
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"The "+parameter+" is not vissible");
						}break;
				}
				case "LAST UPDATED TIME": {
					flag = flag & ActionSheet.isLastUpdatedTimeTitleVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
							Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getLastUpdateTitleText());						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"The "+parameter+" is not vissible");
						}break;
				}
				case "HUMIDITY GRAPH": {
					flag = flag & ActionSheet.isHumidityGraphTitleVisible();
						if(flag) {
							Keyword.ReportStep_Pass(testCase, "The " +parameter+ " is Vissibe");
							Keyword.ReportStep_Pass(testCase, "Displayed Text: "+ ActionSheet.getHumidityGraphTitleText());
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"The "+parameter+" is not vissible");
						}break;		
				}
				}
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
