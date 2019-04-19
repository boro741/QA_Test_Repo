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
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.AddressUtils;
import com.honeywell.lyric.das.utils.HBNAEMEASettingsUtils;
import com.honeywell.lyric.das.utils.WeatherForecastUtils;
import com.honeywell.screens.AboutTheAppScreen;
import com.honeywell.screens.ActivateAccountScreen;
import com.honeywell.screens.ActivityHistoryScreen;
import com.honeywell.screens.AddressScreen;
import com.honeywell.screens.AdhocScreen;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CreateAccountScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.GlobalDrawerScreen;
import com.honeywell.screens.LoginScreen;
import com.honeywell.screens.ManageUsersScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditAccountScreen;
import com.honeywell.screens.FAQsScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.WLDConfigurationScreen;
import com.honeywell.screens.WLDLeakDetectorSettings;
import com.honeywell.screens.WLDManageAlerts;
import com.honeywell.screens.WLDSolutionCard;
import com.honeywell.screens.WeatherForecastScreen;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
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
	@KeywordStep(gherkins = "^user should be displayed with the following \"(.+)\" options:$")
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
				case "WITHOUT BACK BUTTON": {
					System.out.println("Checking if back button is displayed in Entry delay screen ");
					flag = flag & !check.isAlarm_NavigatebackVisible();
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
		case "SENSOR DETAILS": {
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Elements");
				switch (parameter.toUpperCase()) {
				case "TEMPRETURE": {
					if (fly.isSensorDetialsTempVisible()) {
						Keyword.ReportStep_Pass(testCase, "The " + fly.getSensorDetialsTempText() + "has found");
					}
					break;
				}
				case "HUMIDITY": {
					if (fly.isSensorDetialsHumidityVisible()) {
						Keyword.ReportStep_Pass(testCase, "The " + fly.getSensorDetialsHumidityText() + "has found");
					}
					break;
				}
				case "BATTERY STATUS": {
					if (fly.isSensorDetialsBatteryVisible()) {
						Keyword.ReportStep_Pass(testCase, "The " + fly.getSensorDetialsBatteryText() + "has found");
					}
					break;
				}
				case "SIGNAL STRENGTH": {
					if (fly.isSensorDetialsSignalStrengthVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"The " + fly.getSensorDetialsSignalStrengthText() + "has found");
					}
					break;
				}
				case "MODEL": {
					if (fly.isSensorDetialsModelVisible()) {
						Keyword.ReportStep_Pass(testCase, "The " + fly.getSensorDetialsModelText() + "has found");
					}
					break;
				}
				case "FIRMWARE VERSION": {
					if (fly.isSensorDetialsFirmwareVersionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"The " + fly.getSensorDetialsFirmwareVersionText() + "has found");
					}
					break;
				}
				case "USE MOTION DETECTION": {
					if (fly.isSensorDetialsUseMotionDetectionVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"The " + fly.getSensorMotionDetectionToggleValue() + "has found");
					}
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
			@SuppressWarnings("rawtypes")
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				int startx = (dimension.width * 20) / 100;
				int starty = (dimension.height * 62) / 100;
				int endx = (dimension.width * 22) / 100;
				int endy = (dimension.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			} else {
				/*
				 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
				 * (dimension.getHeight() * .6)) .release().perform(); action.press(10, (int)
				 * (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
				 * .release().perform();
				 */
				action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				
//				action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))//						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
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
		case "DAS VIDEO QUALITY SETTINGS": {
			CameraSettingsScreen Video = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				System.out.println(parameter + "Verify");
				switch (parameter.toUpperCase()) {
				case "AUTO": {
					flag &= Video.isVideoQualityAutoVisible();
					flag &= Video.isVideoQualityAutoTextVisible();
					break;
				}
				case "HIGH": {
					flag &= Video.isVideoQualityHighVisible();
					flag &= Video.isVideoQualityHighTextVisible();
					break;
				}
				case "LOW": {
					flag &= Video.isVideoQualityLowVisible();
					flag &= Video.isVideoQualityLowTextVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "and description has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "The" + parameter + "has not found");
				}
				flag = true;
			}
			break;
		}
		case "DAS NIGHT VISION SETTINGS": {
			CameraSettingsScreen Video = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "AUTO": {
					flag &= Video.isNightVisionAutoVisible();
					flag &= Video.isNightVisionAutoTextVisible();
					break;
				}
				case "ON": {
					flag &= Video.isNightVisionONVisible();
					flag &= Video.isNightVisionONTextVisible();
					break;
				}
				case "OFF": {
					flag &= Video.isNightVisionOFFVisible();
					flag &= Video.isNightVisionOFFTextVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " and description has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " has not found");
				}
				flag = true;
			}
			break;
		}
		case "SECURITY MODE": {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "securityState");
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
		// Amresh(H297378)
		case "GRAPH": {
			WLDSolutionCard sol = new WLDSolutionCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Graph Options");
				switch (parameter.toUpperCase()) {
				case "NO OF DAYS TEMPERATURE TREND": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Fail(testCase, FailType.NO_FAILURE,
								"In Android No of Days Temperature Trend is: Not Vissible");
					} else {
						flag = flag & sol.isNoOfDaysTempTrendVisible();
						if (flag) {
							String str = sol.getNoOfDaysTempTrendText();
							Keyword.ReportStep_Pass(testCase, "No of Days Trend is: " + str);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Vissible");
						}
					}
					break;
				}
				case "NO OF DAYS HUMIDITY TREND": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Fail(testCase, FailType.NO_FAILURE,
								"In Android No of Days Humidity Trend is: Not Vissible");
					} else {
						flag = flag & sol.isNoOfDaysHumidTrendVisible();
						if (flag) {
							String str = sol.getNoOfDaysHumidTrendText();
							Keyword.ReportStep_Pass(testCase, "No of Days Trend is: " + str);
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Vissible");
						}
					}
					break;
				}
				case "MAXIMUM AND MINIMUM TEMPERATURE TREND": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Fail(testCase, FailType.NO_FAILURE,
								"In Android Maximum and Minimum Temperature trend is: Not Vissible");
					} else {
						flag = flag & sol.isMaximumTempGraphValueVisible();
						flag = flag & sol.isMinimumTempGraphValueVisible();

						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Maximum Temperature Vissible is: " + sol.getMaximumTempGraphValueText());
							Keyword.ReportStep_Pass(testCase,
									"Minimum Temperature Vissible is: " + sol.getMinimumTempGraphValueText());
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not Vissible");
						}
					}
					break;
				}
				case "MAXIMUM AND MINIMUM HUMIDITY TREND": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Fail(testCase, FailType.NO_FAILURE,
								"In Android Maximum and Minimum Humidity trend is: Not Vissible");
					} else {
						flag = flag & sol.isMaximumHumidGraphValueVisible();
						flag = flag & sol.isMinimumHumidGraphValueVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Maximum Humidity Vissible is: " + sol.getMaximumHumidGraphValueText());
							Keyword.ReportStep_Pass(testCase,
									"Minimum Humidity Vissible is: " + sol.getMinimumHumidGraphValueText());
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Humidity Graph Max Min Trend Not Vissible");
						}
					}
					break;
				}
				case "MAXIMUM 5 DATES": {
					break;
				}
				}
			}
			break;
		}
		// Amresh Starts again
		case "ALERT FOR THIS TEMPERATURE RANGE": {
			WLDManageAlerts ale = new WLDManageAlerts(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Temperature Alert Range Options");
				switch (parameter.toUpperCase()) {
				case "BELOW": {
					flag = flag && ale.isBelowTextinTemperatureVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ale.getBelowTextinTemperatureValue());
						Keyword.ReportStep_Pass(testCase, "Below Text is Vissible");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Below Text not found");
					}
					break;
				}
				case "ABOVE": {
					flag = flag && ale.isAboveTextinTemperatureVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ale.getAboveTextinTemperatureValue());
						Keyword.ReportStep_Pass(testCase, "Above  Text is Vissible");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Above  Text not found");
					}
					break;
				}
				}
			}
			break;
		}
		case "ALERT FOR THIS HUMIDITY RANGE": {
			WLDManageAlerts ale = new WLDManageAlerts(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Temperature Alert Range Options");
				switch (parameter.toUpperCase()) {
				case "BELOW": {
					flag = flag && ale.isBelowTextinHumidityVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ale.getBelowTextinHumidityValue());
						Keyword.ReportStep_Pass(testCase, "Below Text is Vissible");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Below Text not found");
					}
					break;
				}
				case "ABOVE": {
					flag = flag && ale.isAboveTextinHumidityVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ale.getAboveTextinHumidityValue());
						Keyword.ReportStep_Pass(testCase, "Above  Text is Vissible");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Above  Text not found");
					}
					break;
				}
				}
			}
			break;
		}
		// Amresh ends Again
		case "WLDSOLUTIONTEMPERATURE": {
			WLDSolutionCard ActionSheet = new WLDSolutionCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WLD Solution options");
				switch (parameter.toUpperCase()) {
				case "CURRENT TEMPERATURE": {
					flag = flag & ActionSheet.checkAndDismissControlState();
					flag = flag & ActionSheet.isCurrentTemperatureTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getCurrentTemperatureTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "LAST UPDATED TIME": {
					flag = flag & ActionSheet.checkAndDismissControlState();
					flag = flag & ActionSheet.isLastUpdatedTimeTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ActionSheet.getLastUpdateTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "TEMPERATURE GRAPH": {
					flag = flag & ActionSheet.checkAndDismissControlState();
					flag = flag & ActionSheet.isTemperatureGraphTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "BATTERY PERCENTAGE": {
					flag = flag & ActionSheet.checkAndDismissControlState();
					flag = flag & ActionSheet.isBatterypercentageTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + "is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getRemainingBatteryTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "NEXT UPDATE TIME": {
					flag = flag & ActionSheet.checkAndDismissControlState();
					flag = flag & ActionSheet.isNextUpdateTimeTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getNextUpdateTimeTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		// Amresh(H297378)
		case "LEAK DETECTOR SETTINGS": {
			WLDLeakDetectorSettings ActionSheet = new WLDLeakDetectorSettings(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "MANAGE ALERTS": {
					flag = flag & ActionSheet.isManageAlertsTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The Text: " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayManageAlertsTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Text: " + parameter + " is not vissible");
					}
					break;
				}
				case "BATTERY STATUS": {
					flag = flag & ActionSheet.isBatteryStatusTitleVisible();

					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The Text: " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayBatteryStatusTitleText());
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayBatteryStatusPercentageText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Text: " + parameter + " is not vissible");
					}
					break;
				}
				case "TEMPERATURE UNIT": {
					flag = flag & ActionSheet.isTemperatureUnitTitleVisible();

					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The Text: " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.dislplayTemperatureUnitTitleText());
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.dislplayTemperatureUnitValue());

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Text: " + parameter + " is not vissible");
					}
					break;
				}
				case "UPDATE FREQUENCY": {
					flag = flag & ActionSheet.isUpdateFrequencyTitleTextVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The Text: " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayUpdateFrequencyTitleText());
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayUpdateFrequencyValue());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Text: " + parameter + " is not vissible");
					}
					break;
				}
				case "LEAK DETECTOR CONFIGURATION": {

					flag = flag & ActionSheet.isLeakDetectorConfigurationTitleVisible();

					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The Text: " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.displayLeakDetectorConfigurationTitleText());

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Text: " + parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		// Amresh wld starts
		case "WLD INDOOR TEMPERATURE ALERT": {
			WLDManageAlerts ActionSheet = new WLDManageAlerts(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WLD Manage Alerts Options");
				switch (parameter.toUpperCase()) {
				case "EMAIL FOR ENABLED ALERTS": {
					flag = flag & ActionSheet.isEmailNotificationsforTemperatureAlertsTextVissible();
					flag = flag & ActionSheet.isEmailNotificationsforTemperatureAlertsToggleEnabled();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getEmailNotificationsforTemperatureAlertsTextValue());
						Keyword.ReportStep_Pass(testCase, "Email Notifiction Toggle is Enabled.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "ALERT FOR THIS RANGE": {
					flag = flag & ActionSheet.isAlertforthisRangeTemperatureTextVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The " + parameter + " text is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getAlertforthisRangeTemperatureTextValue());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		// Amresh wld ends
		case "WLD INDOOR HUMIDITY ALERT": {
			WLDManageAlerts ActionSheet = new WLDManageAlerts(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WLD Manage Alerts Options");
				switch (parameter.toUpperCase()) {
				case "EMAIL FOR ENABLED ALERTS": {
					flag = flag & ActionSheet.isEmailNotificationsforHumidityAlertsTextVissible();
					flag = flag & ActionSheet.isEmailNotificationsforHumidityAlertsToggleEnabled();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getEmailNotificationsforHumidityAlertsTextValue());
						Keyword.ReportStep_Pass(testCase, "Email for Humidity Toggle is Enabled");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "ALERT FOR THIS RANGE": {
					flag = flag & ActionSheet.isAlertforthisRangeHumidityTextVissible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "The " + parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getAlertforthisRangeHumidityTextValue());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		case "WLD DASHBOARD": {
			Dashboard das = new Dashboard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "CONTROL STATE": {
					WLDSolutionCard sol = new WLDSolutionCard(testCase);
					flag = flag & sol.isControlStateVissible();
					if (flag) {
						String ControlText = sol.getControlStateTextValue();
						flag = flag & sol.clickOnControlText();
						flag = flag && sol.clickOnDismissButton();
						sol.navigateFromPrimaryCardToDashboard();
						Keyword.ReportStep_Pass(testCase, parameter + ControlText + " is Vissibe");
					} else {
						Keyword.ReportStep_Pass(testCase, parameter + " is Not Vissibe at this moment");
						flag = true;
					}
				}
				case "WLD DEVICE NAME": {
					flag = flag & das.areDevicesVisibleOnDashboard(300);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + das.getDashboardDeviceNameLabel());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "LAST UPDATED TIME": {
					flag = flag & das.isSecurityStatusLabelVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + das.getSecurityStatusLabel());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "CURRENT TEMPERATURE PERCENTAGE": {
					flag = flag & das.isDashboardIndoorTempWldVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + das.getdashboardIndoorTempWldLabel());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				case "CURRENT HUMIDITY PERCENTAGE": {
					flag = flag & das.isDashboardHumidityWldVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + das.getDashboardHumidityWldLabel());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		// Amresh(H297378)
		case "WLDSOLUTIONHUMIDITY": {
			WLDSolutionCard ActionSheet = new WLDSolutionCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WLD Humidity Solution options");
				switch (parameter.toUpperCase()) {
				case "CURRENT HUMIDITY": {
					flag = flag & ActionSheet.isHumidityGraphTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase,
								"Displayed Text: " + ActionSheet.getCurrentHumidityTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The " + parameter + " is not vissible");
					}
					break;
				}
				case "LAST UPDATED TIME": {
					flag = flag & ActionSheet.isLastUpdatedTimeTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
						Keyword.ReportStep_Pass(testCase, "Displayed Text: " + ActionSheet.getLastUpdateTitleText());
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The " + parameter + " is not vissible");
					}
					break;
				}
				case "HUMIDITY GRAPH": {
					flag = flag & ActionSheet.isHumidityGraphTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is Vissibe");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not vissible");
					}
					break;
				}
				}
			}
			break;
		}
		case "OSMV SECURITY SETTINGS": {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			@SuppressWarnings("rawtypes")
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				/*
				 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
				 * (dimension.getHeight() * .6)) .release().perform();
				 */
				action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
			}
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "OSMVSecuritySettingsOptions");
				try {
					if (dasDIY.isOSMVOptionsInDASSettingsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase,
								"DAS Security Setting: '" + fieldToBeVerified + "' is present in DAS Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "DAS Security Setting: '"
								+ fieldToBeVerified + "' is not present in DAS Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
			break;
		}
		case "DETERRENCE SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "SELECT CHIME": {
					flag &= bs.isSelectChimeVisible();
					break;
				}
				case "PLAY DOG BARK SOUND": {
					flag &= bs.isPlayDogBarkSoundVisible();
					break;
				}
				case "PARTY IS ON": {
					flag &= bs.isPartyIsOnVisible();
					break;
				}
				case "VACUUM": {
					flag &= bs.isVacuumVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " has not found");
				}
				flag = true;
			}
			break;
		}
		case "SECURITY MODES": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "HOME MODE ICON": {
					flag &= bs.isSecurityModeHomeiConVisible();
					break;
				}
				case "HOME MODE TEXT": {
					flag &= bs.isSecurityModeHomeModeVisible();
					break;
				}
				case "HOME MODE DESCRIPTION": {
					flag &= bs.isSecurityModeHomeTextVisible();
					break;
				}
				case "AWAY MODE ICON": {
					flag &= bs.isSecurityModeAwayiConVisible();
					break;
				}
				case "AWAY MODE TEXT": {
					flag &= bs.isSecurityModeAwayModeVisible();
					break;
				}
				case "AWAY MODE DESCRIPTION": {
					flag &= bs.isSecurityModeAwayTextVisible();
					break;
				}
				case "NIGHT MODE ICON": {
					flag &= bs.isSecurityModeNightiConVisible();
					break;
				}
				case "NIGHT MODE TEXT": {
					flag &= bs.isSecurityModeNightModeVisible();
					break;
				}
				case "NIGHT MODE DESCRIPTION": {
					flag &= bs.isSecurityModeNightTextVisible();
					break;
				}
				case "OFF MODE ICON": {
					flag &= bs.isSecurityModeoffiConVisible();
					break;
				}
				case "OFF MODE TEXT": {
					flag &= bs.isSecurityModeoffModeVisible();
					break;
				}
				case "OFF MODE DESCRIPTION": {
					flag &= bs.isSecurityModeoffTextVisible();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " has not found");
				}
				flag = true;
			}
			break;
		}
		case "LEAK DETECTOR CONFIGURATION": {
			flag = true;
			WLDConfigurationScreen config = new WLDConfigurationScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Configuration");
				switch (parameter.toUpperCase()) {
				case "LEAK DETECTOR NAME": {
					flag = flag & config.isConfigurationNameTextVisible();
					String wld_name = config.getConfigurationWLDNameValue();
					Keyword.ReportStep_Pass(testCase, "Vissible Text: " + wld_name);
					break;
				}
				case "FIRMWARE DETAILS": {
					flag = flag & config.isConfigurationFirmwareVersionTextVisible();
					String firmware_details = config.getConfigurationFirmwareVersionValueValue();
					Keyword.ReportStep_Pass(testCase, "Vissible Text: " + firmware_details);
					break;
				}
				case "DELETE LEAK DETECTOR": {
					flag = flag & config.isConfigurationDeleteLeakDetectorLinkVisible();
					String delete_link = config.getConfigurationDeleteLeakDetectorLinkValue();
					Keyword.ReportStep_Pass(testCase, "Vissible Text: " + delete_link);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " has not found");
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITHOUT SOLUTION FOR UK LOCATION":
		case "GLOBAL DRAWER WITH WLD SOLUTION FOR UK LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithoutSolutionORWithWLDSolutionForUKLocation");
				switch (parameter.toUpperCase()) {
				case "WITHOUT AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is not displayed");
					}
					break;
				}
				case "WITHOUT GEOFENCE OPTION": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "EDIT ACCOUNT": {
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "FAQS": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITHOUT SOLUTION FOR US LOCATION":
		case "GLOBAL DRAWER WITH WLD SOLUTION FOR US LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithoutSolutionORWithWLDSolutionForUSLocation");
				switch (parameter.toUpperCase()) {
				case "WITHOUT AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is not displayed");
					}
					break;
				}
				case "WITHOUT GEOFENCE OPTION": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "EDIT ACCOUNT": {
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "NO FAQS FOR US LOCATION": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option FAQs is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Option FAQs is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITH DAS C1 C2 SOLUTION FOR UK LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithDASC1C2SolutionForUKLocation");
				switch (parameter.toUpperCase()) {
				case "AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "GEOFENCE": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "HONEYWELL MEMBERSHIP FOR ANDROID": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						flag &= gd.isHoneywellMembershipOptionVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						flag &= gd.isHoneywellMembershipOptionVisible();
						if (!flag) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is displayed");
						}
					}
					break;
				}
				case "EDIT ACCOUNT": {
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "FAQS": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITH DAS C1 C2 SOLUTION FOR US LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithDASC1C2SolutionForUSLocation");
				switch (parameter.toUpperCase()) {
				case "AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "GEOFENCE": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "HONEYWELL MEMBERSHIP FOR ANDROID": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						flag &= gd.isHoneywellMembershipOptionVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						flag &= gd.isHoneywellMembershipOptionVisible();
						if (!flag) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is displayed");
						}
					}
					break;
				}
				case "EDIT ACCOUNT": {
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "NO FAQS FOR US LOCATION": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option FAQs is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Option FAQs is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITH JASPER EMEA SOLUTION FOR UK LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithJasperEMEASolutionForUKLocation");
				switch (parameter.toUpperCase()) {
				case "AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "GEOFENCE": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "VACATION": {
					flag &= gd.isVacationOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "EDIT ACCOUNT": {
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "FAQS": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GLOBAL DRAWER WITH JASPER NA SOLUTION FOR US LOCATION": {
			boolean flag = true;
			GlobalDrawerScreen gd = new GlobalDrawerScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GlobalDrawerWithJasperNASolutionForUSLocation");
				switch (parameter.toUpperCase()) {
				case "AUTOMATION HEADER": {
					flag &= gd.isAutomationHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "GEOFENCE": {
					flag &= gd.isGeofenceOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "VACATION": {
					flag &= gd.isVacationOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "HOME HEADER": {
					flag &= gd.isHomeHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ACTIVITY HISTORY": {
					flag &= gd.isActivityHistoryOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADD USERS": {
					flag &= gd.isAddUsersOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS": {
					flag &= gd.isAddressOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ACCOUNT HEADER": {
					flag &= gd.isAccountHeaderTitleVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "EDIT ACCOUNT": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					@SuppressWarnings("rawtypes")
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					flag &= gd.isEditAccountOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ABOUT THE APP": {
					flag &= gd.isAboutTheAppOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "NO FAQS FOR US LOCATION": {
					flag &= gd.isFAQsOptionVisible();
					if (flag) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option FAQs is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Option FAQs is not displayed");
					}
					break;
				}
				case "LOGOUT": {
					flag &= gd.isLogoutOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "GEOFENCE THIS LOCATION": {
			boolean flag = true;
			GeofenceSettings gs = new GeofenceSettings(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "GeofenceThisLocation");
				switch (parameter.toUpperCase()) {
				case "GEOFENCE RADIUS": {
					flag &= gs.isGeofenceRadiusOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "LOCATION STATUS": {
					flag &= gs.isLocationStatusOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "GEOFENCE ALERT": {
					flag &= gs.isGeofenceAlertOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ACTIVITY HISTORY": {
			ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "ActivityHistoryOptions");
				switch (parameter.toUpperCase()) {
				case "CANCEL": {
					flag &= ah.isCancelOptionVisible();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "INVITED USERS": {
			ManageUsersScreen mus = new ManageUsersScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "InvitedUsersList");
				if (mus.isNoInvitedUsersLabelVisible()) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "There are no Invited Users.");
				} else {
					Keyword.ReportStep_Pass(testCase, "Invited users are present.");
					if (mus.isInviteUsersEmailAddressDisplayedInTheListOfInvitedUsers(inputs, parameter)) {
						Keyword.ReportStep_Pass(testCase, "Invite users email address: " + parameter
								+ " is present in the list of Invited Users.");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invite users email address: "
								+ parameter + " is not present in the list of Invited Users.");
					}
				}
			}
			break;
		}
		case "EDIT ADDRESS": {
			AddressScreen ads = new AddressScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "EditAddressOptions");
				switch (parameter.toUpperCase()) {
				case "LOCATION NAME HEADER": {
					if (ads.isLocationNameHeaderTitleInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "LOCATION NAME TEXT FIELD": {
					if (ads.isLocationNameTextInEditAddressScreenVisible()
							&& !ads.isPlaceHolderValueInLocationNameTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed and " + parameter
								+ " Place holder is not displayed");
						flag &= AddressUtils.verifyLocationNameDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_NAME"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is displayed");
					}
					break;
				}
				case "ADDRESS HEADER": {
					if (ads.isAddressHeaderTitleInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS TEXT FIELD": {
					if (ads.isAddressTextInEditAddressScreenVisible()
							&& !ads.isPlaceHolderValueInAddressTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed and " + parameter
								+ " Place holder is not displayed");
						flag &= AddressUtils.verifyLocationAddressValueDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_ADDRESS"));
					} else if (ads.isPlaceHolderValueInAddressTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "CITY TEXT FIELD": {
					if (ads.isCityTextInEditAddressScreenVisible() && !ads.isPlaceHolderValueInCityTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.verifyLocationCityValueDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_ADDRESS"));
					} else if (ads.isPlaceHolderValueInCityTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "STATE TEXT FIELD": {
					if (ads.isStateTextInEditAddressScreenVisible()
							&& !ads.isPlaceHolderValueInStateTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.verifyLocationStateValueDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_ADDRESS"));
					} else if (ads.isPlaceHolderValueInStateTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "POSTAL CODE TEXT FIELD": {
					if (ads.isPostalCodeTextInEditAddressScreenVisible()
							&& !ads.isPlaceHolderValueInPostalCodeTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.verifyLocationPostalCodeValueDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_ADDRESS"));
					} else if (ads.isPlaceHolderValueInPostalCodeTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, parameter + " Place holder is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed");
					}
					break;
				}
				case "CHANGE COUNTRY": {
					if (ads.isChangeCountryButtonInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Button " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Button " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ADDRESS FIELDS FOR THE SELECTED COUNTRY": {
			AddressScreen ads = new AddressScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "AddressFieldsForTheSelectedCountry");
				switch (parameter.toUpperCase()) {
				case "LOCATION NAME HEADER": {
					if (ads.isLocationNameHeaderTitleInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "LOCATION NAME TEXT FIELD": {
					if (ads.isLocationNameTextInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						flag &= AddressUtils.verifyLocationNameDisplayedInEditAddressScreen(testCase,
								inputs.getInputValue("LOCATION_NAME"));
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS HEADER": {
					if (ads.isAddressHeaderTitleInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Header " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Header " + parameter + " is not displayed");
					}
					break;
				}
				case "ADDRESS TEXT FIELD": {
					if (ads.isAddressTextInEditAddressScreenVisible()
							&& ads.isPlaceHolderValueInAddressTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed. ");
					}
					break;
				}
				case "CITY TEXT FIELD": {
					if (ads.isCityTextInEditAddressScreenVisible() && ads.isPlaceHolderValueInCityTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed. ");
					}
					break;
				}
				case "STATE TEXT FIELD": {
					if (ads.isStateTextInEditAddressScreenVisible()
							&& ads.isPlaceHolderValueInStateTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed. ");
					}
					break;
				}
				case "POSTAL CODE TEXT FIELD": {
					if (ads.isPostalCodeTextInEditAddressScreenVisible()
							&& ads.isPlaceHolderValueInPostalCodeTextFieldVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Option " + parameter
								+ " is not displayed OR " + parameter + " Place holder is not displayed. ");
					}
					break;
				}
				case "CHANGE COUNTRY": {
					if (ads.isChangeCountryButtonInEditAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Button " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Button " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ADDRESS": {
			AddressScreen ads = new AddressScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "AddressOptions");
				switch (parameter.toUpperCase()) {
				case "EDIT ADDRESS LABEL": {
					if (ads.isEditAddressInAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "DELETE LOCATION OPTION": {
					if (ads.isDeleteLocationButtonInAddressScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Button " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Button " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "DELETE ACCOUNT": {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "DeleteAccountOptions");
				switch (parameter.toUpperCase()) {
				case "WE ARE SORRY TO SEE YOU GO": {
					if (eas.isWeAreSorryToSeeYouGoLabelVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "LEARN HOW TO DELETE A DEVICE": {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "LearnHowToDeleteADeviceOptions");
				switch (parameter.toUpperCase()) {
				case "WAS THIS HELPFUL WITH YES AND NO BUTTONS": {
					if (eas.wasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()
							&& eas.isYESButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()
							&& eas.isNOButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "LEARN HOW TO CANCEL A MEMBERSHIP": {
			EditAccountScreen eas = new EditAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "LearnHowToCancelAMembershipOptions");
				switch (parameter.toUpperCase()) {
				case "WAS THIS HELPFUL WITH YES AND NO BUTTONS": {
					if (eas.wasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()
							&& eas.isYESButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()
							&& eas.isNOButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ABOUT THE APP": {
			AboutTheAppScreen atas = new AboutTheAppScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "AboutTheAppOptions");
				switch (parameter.toUpperCase()) {
				case "RATE THE APP FOR ANDROID": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (atas.isRateTheAppOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						if (!atas.isRateTheAppOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					}
					break;
				}
				case "GET HELP": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (atas.isGetHelpOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						if (atas.isGetHelpOptionInAboutTheAppVisible()
								&& atas.isGetHelpOptionInAboutTheAppArrowVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					}
					break;
				}
				case "WITHOUT GET HELP OPTION": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (!atas.isGetHelpOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is displayed");
						}
					} else {
						if (!atas.isGetHelpOptionInAboutTheAppVisible()
								&& !atas.isGetHelpOptionInAboutTheAppArrowVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is not displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is displayed");
						}
					}
					break;
				}
				case "PRIVACY POLICY AND EULA": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (atas.isPrivacyPolicyAndEULAOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						if (atas.isPrivacyPolicyAndEULAOptionInAboutTheAppVisible()
								&& atas.isPrivacyPolicyAndEULAOptionInAboutTheAppArrowVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					}
					break;
				}
				case "ACKNOWLEDGEMENTS": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (atas.isAcknowledgementsOptionInAboutTheAppVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					} else {
						if (atas.isAcknowledgementsOptionInAboutTheAppVisible()
								&& atas.isAcknowledgementsOptionInAboutTheAppArrowVisible()) {
							Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Option " + parameter + " is not displayed");
						}
					}
					break;
				}
				case "VERSION": {
					if (atas.isVersionOptionInAboutTheAppVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "FAQS": {
			FAQsScreen faqsScreen = new FAQsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "FAQsOptions");
				switch (parameter.toUpperCase()) {
				case "GENERAL": {
					if (faqsScreen.isGeneralOptionInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "THERMOSTAT": {
					if (faqsScreen.isThermostatOptionInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "WATER LEAK DETECTOR": {
					if (faqsScreen.isWaterLeakDetectorOptionInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				case "CAMERA": {
					if (faqsScreen.isCameraOptionInFAQsScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Option " + parameter + " is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "QUESTION": {
			FAQsScreen faqsScreen = new FAQsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "QuestionOptions");
				switch (parameter.toUpperCase()) {
				case "WAS THIS HELPFUL WITH YES AND NO BUTTONS": {
					if (faqsScreen.wasThisHelpfulTextInQuestionScreenVisible()
							&& faqsScreen.isYESButtonInWasThisHelpfulTextInQuestionScreenVisible()
							&& faqsScreen.isNOButtonInWasThisHelpfulTextInQuestionScreenVisible()) {
						Keyword.ReportStep_Pass(testCase, "Options " + parameter + " are displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Options " + parameter + " are not displayed");
					}
					break;
				}
				case "SEARCH RESULTS": {
					if (faqsScreen.isSearchResultsDisplayedInFAQsScreen()
							&& faqsScreen.isFirstQuestionInSearchResultsDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + " are displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " are not displayed");
					}
					break;
				}
				case "NO FAQS FOUND": {
					if (faqsScreen.isNoFAQsFoundLabelInSearchResultsVisible()
							&& faqsScreen.getNoFAQsFoundLabelInSearchResults().contains("No FAQs found")) {
						Keyword.ReportStep_Pass(testCase, "Label No FAQs found is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Label No FAQs found is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "CREATE ACCOUNT": {
			boolean flag = true;
			CreateAccountScreen cas = new CreateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "CreateAccountOptions");
				switch (parameter.toUpperCase()) {
				case "FIRST NAME": {
					flag &= cas.isCreateAccountFirstNameFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "LAST NAME": {
					flag &= cas.isCreateAccountLastNameFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "EMAIL": {
					flag &= cas.isCreateAccountEmailFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "PASSWORD": {
					flag &= cas.isCreateAccountPasswordFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "VERIFY PASSWORD": {
					flag &= cas.isCreateAccountVerifyPasswordFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "PASSWORD MUST HAVE": {
					flag &= cas.isCreateAccountPasswordMustHaveTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " text is not displayed");
					}
					break;
				}

				case "COUNTRY SELECTION": {
					flag &= cas.isCreateAccountCountrySelectionDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;

				}

				case "BY TAPPING CREATE BELOW": {
					flag &= cas.isCreateAccountByTappingCreateBelowTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
					} else {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						System.out.println("$$$$$$$$$$$$$$: " + testCase.getPlatform());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
						if (cas.isCreateAccountByTappingCreateBelowTextDisplayed()) {
							Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameter + " text is not displayed");
						}
					}
					break;
				}

				case "PRIVACY STATEMENT": {
					if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + "link is displayed");
					} else {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						System.out.println("$$$$$$$$$$$$$$: " + testCase.getPlatform());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
						if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
							Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameter + " link is not displayed");
						}
					}
					break;
				}

				case "EULA": {
					flag &= cas.isCreateAccountEULALinkDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " link is not displayed");
					}
					break;
				}

				case "CREATE BUTTON": {
					flag &= cas.isCreateAccountRegisterButtonDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;
				}
				case "MARKETING COMMUNICATIONS SIGN UP": {
					flag &= cas.isCreateAccountMarketingCommunicationsSignUpTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " text is displayed");
					}
					break;
				}

				case "SIGN ME UP FOR EXCLUSIVE UPDATES AND TOGGLE": {
					flag &= cas.isCreateAccountSignMeUpForExclusiveUpdatesTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button & toggle is not displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + "button & toggle is displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}

		case "CREATE ACCOUNT FOR UK": {
			boolean flag = true;
			CreateAccountScreen cas = new CreateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "CreateAccountOptions");
				switch (parameter.toUpperCase()) {
				case "FIRST NAME": {
					flag &= cas.isCreateAccountFirstNameFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "LAST NAME": {
					flag &= cas.isCreateAccountLastNameFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "EMAIL": {
					flag &= cas.isCreateAccountEmailFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "PASSWORD": {
					flag &= cas.isCreateAccountPasswordFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "VERIFY PASSWORD": {
					flag &= cas.isCreateAccountVerifyPasswordFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}

				case "PASSWORD MUST HAVE": {
					flag &= cas.isCreateAccountPasswordMustHaveTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " text is not displayed");
					}
					break;
				}

				case "COUNTRY SELECTION": {
					flag &= cas.isCreateAccountCountrySelectionDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + "button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;
				}

				case "MARKETING COMMUNICATIONS SIGN UP": {
					flag &= cas.isCreateAccountMarketingCommunicationsSignUpTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						@SuppressWarnings("rawtypes")
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						System.out.println("$$$$$$$$$$$$$$: " + testCase.getPlatform());
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							/*
							 * action.press(10, (int) (dimensions.getHeight() * .9)) .moveTo(0, -(int)
							 * (dimensions.getHeight() * .6)).release().perform();
							 */
							action.press(point(10, (int) (dimensions.getHeight() * .9))).waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point(0, -(int) (dimensions.getHeight() * .6))).release().perform();
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " text is not displayed");
					}
					break;
				}

				case "SIGN ME UP FOR EXCLUSIVE UPDATES AND TOGGLE": {
					flag &= cas.isCreateAccountSignMeUpForExclusiveUpdatesTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed & toggle is disabled");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + "button & toggle are not displayed");
					}
					break;
				}

				case "SIGNING UP CONSENT LABEL SHOULD NOT BE DISPLAYED": {
					if (!cas.isCreateAccountSigningUpConsentLabelDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " label is displayed");
					}
					break;
				}

				case "SIGNING UP CONSENT LABEL SHOULD BE DISPLAYED": {
					flag &= cas.isCreateAccountSigningUpConsentLabelDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " label is not displayed");
					}
					break;
				}

				case "BY TAPPING CREATE BELOW": {
					flag &= cas.isCreateAccountByTappingCreateBelowTextDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " text is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " text is not displayed");
					}
					break;
				}

				case "PRIVACY STATEMENT": {
					if (cas.isCreateAccountPrivacyStatementLinkDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " link is not displayed");
					}
					break;
				}

				case "EULA": {
					flag &= cas.isCreateAccountEULALinkDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " link is not displayed");
					}
					break;
				}

				case "CREATE BUTTON": {
					flag &= cas.isCreateAccountRegisterButtonDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
			}
			break;
		}
		case "CREATE ACCOUNT FIELD ERROR VALIDATION": {
			boolean flag = true;
			CreateAccountScreen cas = new CreateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "CreateAccountFieldErrorValidation");
				switch (parameter.toUpperCase()) {
				case "YOU MUST ENTER A FIRST NAME": {
					flag &= cas.isCreateAccountFirstNameFieldErrorValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " error validation is displayed for First Name");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " error validation is not displayed for First Name");
					}
					break;
				}
				case "YOU MUST ENTER A LAST NAME": {
					flag &= cas.isCreateAccountLastNameFieldErrorValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " error validation is displayed for Last Name");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " error validation is not displayed for Last Name");
					}
					break;
				}

				case "YOU MUST ENTER A VALID EMAIL ADDRESS": {
					flag &= cas.isCreateAccountEmailFieldErrorValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " error validation is displayed for Email");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " error validation is not displayed for Email");
					}
					break;
				}

				case "THE PASSWORD MUST BE AT LEAST EIGHT CHARACTERS": {
					flag &= cas.isCreateAccountPasswordFieldErrorValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " error validation is displayed for Password");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " error validation is not displayed for Password");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
				flag = true;
			}
			break;
		}
		case "ALREADY REGISTERED EMAIL VALIDATION": {
			boolean flag = true;
			CreateAccountScreen cas = new CreateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "AlreadyRegisteredEmailValidation");
				switch (parameter.toUpperCase()) {
				case "THIS EMAIL ADDRESS HAS ALREADY BEEN REGISTERED.": {
					flag &= cas.isCreateAccountAlreadyRegisteredEmailValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed for Email field");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " is not displayed for Email field");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
			}
			break;
		}
		case "PASSWORD DONT MATCH VALIDATION": {
			boolean flag = true;
			CreateAccountScreen cas = new CreateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "PasswordDontMatchValidation");
				switch (parameter.toUpperCase()) {
				case "THE PASSWORD MUST BE AT LEAST EIGHT CHARACTERS": {
					flag &= cas.isCreateAccountPasswordFieldErrorValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " error validation is displayed for Password");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " error validation is not displayed for Password");
					}
					break;
				}
				case "PASSWORDS DON'T MATCH": {
					flag &= cas.isCreateAccountVerifyPasswordDontMatchValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								parameter + " validation is displayed for Verify Password field");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " validation is not displayed for Verify Password field");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input: " + expectedScreen.get(0));
				}
				}
			}
			break;
		}
		case "LOGIN": {
			boolean flag = true;
			LoginScreen ls = new LoginScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Login");
				switch (parameter.toUpperCase()) {
				case "EMAIL": {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
						flag &= ls.isLoginEmailAddressTextFieldVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameter + " field is not displayed");
						}
						break;
					} else {
						// ios
						ls.ClickOnHoneywellHomeLogo();
						flag &= ls.isLoginEmailAddressTextFieldVisible();
						if (flag) {
							Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameter + " field is not displayed");
						}
						break;
					}
				}
				case "PASSWORD": {
					flag &= ls.isLoginPasswordTextFieldDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " field is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " field is not displayed");
					}
					break;
				}
				case "FORGOT PASSWORD": {
					flag &= ls.isLoginForgotPasswordLinkDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " link is not displayed");
					}
					break;
				}
				case "DISABLED LOGIN BUTTON": {
					flag &= ls.isLoginButtonDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed and disabled");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not displayed");
					}
					break;
				}
				case "CANCEL": {
					flag &= ls.isLoginCancelButtonDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;
				}
				case "INVALID EMAIL AND PASSWORD VALIDATION": {
					flag &= ls.isLoginInvalidEmailAndPasswordValidationDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not displayed");
					}
					break;
				}
				}
			}
			break;
		}
		case "ACTIVATE ACCOUNT DETAILS": {
			boolean flag = true;
			ActivateAccountScreen aas = new ActivateAccountScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "ActivateAccountDetails");
				switch (parameter.toUpperCase()) {
				case "ALMOST DONE": {
					flag &= aas.isActivateAccountAlmostDoneLabelDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " label is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " label is not displayed");
					}
					break;
				}
				case "NEW EMAIL": {
					flag &= aas.isActivateAccountNewEmailDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " Id is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " Id is not displayed");
					}
					break;
				}
				case "GO TO MAIL": {
					flag &= aas.isActivateAccountGoToMailButtonDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " button is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " button is not displayed");
					}
					break;
				}
				case "RESEND EMAIL": {
					flag &= aas.isActivateAccountResendEmailLinkDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " link is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameter + " link is not displayed");
					}
					break;
				}
				}
			}
		}
		case "WEATHER": {
			boolean flag = true;
			String locationValue1 = null;
			Dashboard d = new Dashboard(testCase);
			WeatherForecastScreen w = new WeatherForecastScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "WeatherOptions");
				switch (parameter.toUpperCase()) {
				case "WEATHER ICON": {
					flag &= d.isWeatherIconDisplayed();
					if (flag) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, parameter + " is not displayed");
					}
					break;
				}
				case "WEATHER TEMP IN DASHBOARD SCREEN": {
					try {
						chUtil = new CHILUtil(inputs);
						long locationID;
						double weatherTemperatureFromCHIL, weatherTemperatureDisplayedInTheApp;
						String defaultWeatherTempUnit = null;
						if (chUtil.getConnection()) {
							locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
							System.out.println("The CHIL location ID is: " + locationID);
							weatherTemperatureFromCHIL = Double.parseDouble(chUtil.getWeather(locationID));
							System.out.println("########weatherTemperatureFromCHIL: " + weatherTemperatureFromCHIL);
							if (flag &= d.isDashboardWeatherForecastDisplayed(100)) {
								Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
								defaultWeatherTempUnit = d.getWeatherTempValue();
								weatherTemperatureDisplayedInTheApp = Double.parseDouble(defaultWeatherTempUnit);
								flag &= d.clickOnWeatherTempValue();
								if (w.isWeatherScreenTitleDisplayed()) {
									String tempUnitEnabled = w.whichWeatherTempUnitIsEnabled();
									if (tempUnitEnabled.contains("C")) {
										w.clickOnBackIcon();
									} else {
										w.clickOnCelsiusUnit();
										w.clickOnBackIcon();
									}
									/*
									 * if(tempUnitEnabled.contains("F")) { w.clickOnFarenheitUnit();
									 * w.clickOnBackIcon(); }else { w.clickOnBackIcon(); }
									 */
								}
								if ((Double.compare(weatherTemperatureFromCHIL,
										weatherTemperatureDisplayedInTheApp) == 0)
										|| (Double.compare(weatherTemperatureFromCHIL,
												weatherTemperatureDisplayedInTheApp) >= 2)
										|| (Double.compare(weatherTemperatureFromCHIL,
												weatherTemperatureDisplayedInTheApp) <= 2)) {
									Keyword.ReportStep_Pass(testCase, parameter
											+ "Weather displayed in app is same as the Weather Temperature in CHIL");
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Weather displayed in app: " + weatherTemperatureDisplayedInTheApp
													+ "is not same as the weather displayed in CHIL"
													+ weatherTemperatureFromCHIL);
								}
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										parameter + " is not displayed");
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
					break;
				}
				case "HUMIDITY": {
					try {
						chUtil = new CHILUtil(inputs);
						w = new WeatherForecastScreen(testCase);
						long locationID;
						int humidityFromCHIL, humidityDisplayedInTheApp;
						String getWeatherHumidity = null;
						if (chUtil.getConnection()) {
							locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
							System.out.println("The CHIL location ID is: " + locationID);
							humidityFromCHIL = Integer.parseInt(chUtil.getHumidty(locationID));
							System.out.println("########HumidityFromCHIL: " + humidityFromCHIL);
							if (flag &= w.isHumidityDisplayed(50)) {
								Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
								getWeatherHumidity = w.getHumidity();
								humidityDisplayedInTheApp = Integer.parseInt(getWeatherHumidity);
								if ((Integer.compare(humidityFromCHIL, humidityDisplayedInTheApp) == 0)
										|| (Integer.compare(humidityFromCHIL, humidityDisplayedInTheApp) < 15)) {
									Keyword.ReportStep_Pass(testCase,
											parameter + " displayed in app " + humidityDisplayedInTheApp
													+ " is same as the Humidity in CHIL: " + humidityFromCHIL);
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Humidity displayed in app: " + humidityDisplayedInTheApp
													+ "is not same as the weather displayed in CHIL: "
													+ humidityFromCHIL);
								}
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										parameter + " is not displayed");
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
					break;
				}

				case "MAX WEATHER TEMPERATURE": {
					String getMaxWeather = null;
					if (w.isWeatherMaxTempDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
						getMaxWeather = w.getWeatherMaxTemp();
						flag &= WeatherForecastUtils.compareMaxTempWithCHIL(testCase, inputs, getMaxWeather);
					}
					break;
				}

				case "MIN WEATHER TEMPERATURE": {
					String getMinWeather = null;
					if (w.isWeatherMinTempDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
						getMinWeather = w.getWeatherMinTemp();
						flag &= WeatherForecastUtils.compareMinTempWithCHIL(testCase, inputs, getMinWeather);
					}
					break;
				}

				case "WEATHER TEMP IN FORECAST SCREEN": {
					String getWeatherForecastTemp = null;
					if (w.isWeatherForecastValueDisplayed()) {
						Keyword.ReportStep_Pass(testCase, parameter + " is displayed");
						getWeatherForecastTemp = w.getWeatherForecastValue();
						flag &= WeatherForecastUtils.compareWeatherForecastTempWithCHIL(testCase, inputs,
								getWeatherForecastTemp);
					}
					break;
				}
				}
			}
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
