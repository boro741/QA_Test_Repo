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
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;

import io.appium.java_client.TouchAction;

public class VerifyOptionsOnAScreenDisabled extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenDisabled(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^the following \"(.*)\" options should be disabled:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "SECURITY SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				try {
					if (fieldTobeVerified.equalsIgnoreCase("Geofencing")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.GEOFENCING)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Geofencing Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Geofencing Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Entry/Exit Delay")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Entry/Exit Delay Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Entry/Exit Delay Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Volume")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.VOLUME)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Volume Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Volume Delay Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Reset Wi-Fi")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.RESETWIFI)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Reset Wi-Fi Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Reset Wi-Fi Option is disabled");
						}
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "DAS CAMERA SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "MANAGE ALERTS": {
					flag &= !cs.isManangeAlertsEnabled();
					break;
				}
				case "MOTION DETECTION": {
					flag &= !cs.isMotionDetectionEnabled();
					break;
				}
				case "PEOPLE DETECTION": {
					flag &= !cs.isPeopleDetectionEnabled();
					break;
				}
				case "NIGHT VISION": {
					flag &= !cs.isNightVisionEnabled();
					break;
				}
				case "VIDEO QUALITY": {
					flag &= !cs.isVideoQualityEnabled();
					break;
				}
				case "CAMERA ON IN HOME MODE": {
					flag &= !cs.isCameraOnInHomeModeEnabled();
					break;
				}
				case "CAMERA ON IN NIGHT MODE": {
					flag &= !cs.isCameraOnInNigtModeEnabled();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has Disabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							" The " + parameter + " has not Disabled");
				}
				flag = true;

			}break;
		}
		case "DAS SECURITY SETTINGS": {
			BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "MANAGE ALERTS": {
					flag &= !cs.isManangeAlertsEnabled();
					break;
				}
				case "AMAZON ALEXA": {
					flag &= !cs.isAmazonAlexaEnabled();
					break;
				}
				case "GEOFENCING": {
					flag &= !cs.isGeofencingEnabled();
					flag &= !cs.isGeofencingDescriptionEnabled();
					break;
				}
				case "OK SECURITY VOICE COMMANDS": {
					flag &= !cs.isOKSecurityVoiceCommandsEnabled();
					break;
				}
				case "ENHANCED DETERRENCE": {
					flag &= !cs.isEnhancedDeterrenceEnabled();
					flag &= !cs.isEnhancedDeterrenceDescriptionEnabled();
					break;
				}
				case "OUTDOOR MOTION VIEWERS ON IN HOME MODE": {
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
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					flag &= !cs.isOutdoorMotionViewerOnInHomeModeEnabled();
					flag &= !cs.isOutdoorMotionViewerOnInHomeModeDescriptionEnabled();
					break;
				}
				case "ENTRY/EXIT DELAY": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isEntryExitDelayVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isEntryExitDelayEnabled();
					flag &= !cs.isEntryExitDelayDescriptionEnabled();
					break;
				}
				case "ABOUT SECURITY MODES": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isAboutSecurityModesVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isAboutSecurityModesEnabled();
					break;
				}
				case "KEY FOB": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isKeyFobVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isKeyFobEnabled();
					break;
				}
				case "SENSORS": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isSensorsOptionVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isSensorsOptionEnabled();
					break;
				}
				case "Z-WAVE DEVICES": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isZwaveDevicesVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isZwaveDevicesEnabled();
					break;
				}
				case "BSAE STATION VOLUME": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isBaseStationVolumeValueVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isBaseStationVolumeEnabled();
					break;
				}
				case "RESET WI-FI": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isBaseStationResetWifiVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isBaseStationResetWifiEnabled();
					break;
				}
				case "BASE STATION CONFIGURATION": {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if(!cs.isBaseStationConfigurationsOptionVisible()){
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int startx = (dimension.width * 20) / 100;
							int starty = (dimension.height * 62) / 100;
							int endx = (dimension.width * 22) / 100;
							int endy = (dimension.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						} else {
							action.press(10, (int) (dimension.getHeight() * .9))
							.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						}
					}
					flag &= !cs.isBaseStationConfigurationEnabled();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " has Disabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							" The " + parameter + " has not disabled");
				}
			}break;
		}
		case "VIDEO SETTINGS": {
			try {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				for (int i = 0; i < data.getSize(); i++) {
					String fieldTobeVerified = data.getData(i, "Options");
					if (fieldTobeVerified.equalsIgnoreCase("Motion Detection")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.MOTIONDETECTION)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Detection Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Motion Detection Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Night Vision")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.NIGHTVISION)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Night Vision Option is disabled");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Video Quality")) {
						if (bs.isElementEnabled(BaseStationSettingsScreen.VIDEOQUALITY)) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Option is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Video Quality Option is disabled");
						}
					}
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}
			break;
		}
		case "MOTION DETECTION": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				if (fieldToBeVerified.equalsIgnoreCase("MOTION DETECTION ZONE")) {
					if (cs.isMotionDetectionZoneEnabled()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Detection Zone section is enabled");
					} else {
						Keyword.ReportStep_Pass(testCase, "Motion Detection Zone section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("MOTION SENSITIVITY")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						action.press(10, (int) (dimension.getHeight() * .9))
						.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					}
					if (!cs.isMotionSensitivityEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity section is disabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity section is enabled");
					}
				}
			}
			break;
		}
		case "SOUND DETECTION": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				if (fieldToBeVerified.equalsIgnoreCase("SOUND SENSITIVITY")) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					} else {
						action.press(10, (int) (dimension.getHeight() * .9))
						.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					}
					if (!cs.isSoundSensitivityEnabled()) {
						Keyword.ReportStep_Pass(testCase, "Sound Sensitivity section is disabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Sensitivity section is enabled");
					}
				}
			}
			break;
		}
		case "CAMERA SETTINGS": {
			try {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				for (int i = 0; i < data.getSize(); i++) {
					String fieldToBeVerified = data.getData(i, "Options");
					if (fieldToBeVerified.equalsIgnoreCase("SOUND DETECTION")) {
						if (!cs.isSoundDetectionSectionEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Sound Detection section is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Sound Detection section is enabled");
						}
					} else if (fieldToBeVerified.equalsIgnoreCase("CAMERA MICROPHONE")) {
						if (!cs.isCameraMicrophoneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Microphone switch is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera Microphone switchis enabled");
						}
					}
					else if (fieldToBeVerified.equalsIgnoreCase("MOTION DETECTION")) {
						if (!cs.isMotionDetectionSectionEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion detection is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera Motion detection is enabled");
						}

					}else if (fieldToBeVerified.equalsIgnoreCase("NIGHT VISION")) {
						if (!cs.isNightVisionSectionEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Night vision is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera Night vision is enabled");
						}

					}else if (fieldToBeVerified.equalsIgnoreCase("VIDEO QUALITY")) {
						if (!cs.isVideoQualitySectionEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Video Quality is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera Video  Quality is enabled");
						}

					}else if (fieldToBeVerified.equalsIgnoreCase("CAMERA LED")) {
						if (!cs.isCameraLEDSectionEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera LED  is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera LED  is enabled");
						}

					}
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}
			break;
		}
		case "KEYFOB SETTINGS":
		case "SENSOR SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("Name field")) {
					if (expectedScreen.get(0).equalsIgnoreCase("KEYFOB SETTINGS")) {
						if (bs.isKeyfobNameElementEnabled()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Name field is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Name field is disabled");
						}
					} else {

						if (bs.isNameElementEnabled()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Name field is enabled");
						} else {
							Keyword.ReportStep_Pass(testCase, "Name field is disabled");
						}
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Cover Tampered")) {
					SensorSettingScreen sensor = new SensorSettingScreen(testCase);
					if (sensor.isSensorTamperedScreenDisplayed()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Delete should not clickable in away/night mode");
					} else {
						Keyword.ReportStep_Pass(testCase, "Cover Tampered field is disabled");
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Signal strength and test")) {
					if (bs.isSensorSignalStrengthAndTestOptionEnabled()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Signal strength and test is enabled");
					} else {
						Keyword.ReportStep_Pass(testCase, "Signal strength and test field is disabled");
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Delete")) {

					if (expectedScreen.get(0).equalsIgnoreCase("KEYFOB SETTINGS")) {
						if (bs.isKeyfobDeleteElementClickable()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Delete should not clickable in away/night mode");
						} else {
							Keyword.ReportStep_Pass(testCase, "Delete field is disabled");
						}
					} else {
						if (bs.isDeleteElementClickable()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Delete should not clickable in away/night mode");
						} else {
							Keyword.ReportStep_Pass(testCase, "Delete field is disabled");
						}
					}
				}

			}
			break;
		}
		case "THERMOSTAT": {
			Dashboard dash = new Dashboard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldTobeVerified = data.getData(i, "Options");
				if (fieldTobeVerified.equalsIgnoreCase("UP stepper")) {
					if (dash.isUPStepperElementEnabled()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "UP Stepper option is enabled");
					} else {
						Keyword.ReportStep_Pass(testCase, "UPstepper Option is disabled");
					}
				} else if (fieldTobeVerified.equalsIgnoreCase("Down stepper")) {
					if (dash.isDownStepperElementEnabled()) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Down stepper Option is enabled");
					} else {
						Keyword.ReportStep_Pass(testCase, "Down stepper Option is disabled");
					}
				}
			}

			break;
		}

		case "SET FILTER REMINDER": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "SetFilterReminderOptions");
				if (fieldToBeVerified.equalsIgnoreCase("REPLACE FILTER EVERY")) {
					if (!ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is disabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("FILTER LAST REPLACED")) {
					if (!ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is disabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("NEXT SCHEDULED REMINDER")) {
					if (!ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is disabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					}
				}
			}
			break;
		}

		case "THERMOSTAT SETTINGS": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "ThermostatSettingsOption");
				if (fieldToBeVerified.equalsIgnoreCase("AUTO CHANGEOVER")) {
					try {
						if (!ts.isThermostatAutoChangeOverSwitchEnabled(testCase, fieldToBeVerified)) {
							Keyword.ReportStep_Pass(testCase, "Auto Changeover: " + fieldToBeVerified + " is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Auto Changeover: " + fieldToBeVerified + " is enabled");
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("EMERGENCY HEAT")) {
					try {
						if (!ts.isThermostatEmergencyHeatSwitchEnabled(testCase, fieldToBeVerified)) {
							Keyword.ReportStep_Pass(testCase, "Emergency Heat: " + fieldToBeVerified + " is disabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Emergency Heat: " + fieldToBeVerified + " is enabled");
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured: " + e.getMessage());
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
