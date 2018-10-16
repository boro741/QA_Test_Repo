package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.Dimension;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.GlobalVariables;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.ThermostatSettingsScreen;

import io.appium.java_client.TouchAction;

public class VerifyOptionsOnAScreenEnabled extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenEnabled(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
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
	@KeywordStep(gherkins = "^the following \"(.*)\" options should be enabled:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "CAMERA SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				if (fieldToBeVerified.equalsIgnoreCase("Camera Mode")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Mode section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Mode section is disabled");

					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Manage alerts")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Manage Alerts section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Motion Detection")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Motion Detection section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Detection section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Sound Detection")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Sound Detection section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Detection section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Night Vision")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Night Vision section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Video Quality")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Video Quality  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality  section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera LED")) {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						action.press(10, (int) (dimension.getHeight() * .9))
						.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					} else {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					}
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera LED  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera LED  section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera Microphone")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Microphone  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Microphone  section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera Configuration")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Configuration  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Configuration  section is disabled");
					}
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
					flag &= cs.isManangeAlertsEnabled();
					break;
				}
				case "MOTION DETECTION": {
					flag &= cs.isMotionDetectionEnabled();
					break;
				}
				case "PEOPLE DETECTION": {
					flag &= cs.isPeopleDetectionEnabled();
					break;
				}
				case "NIGHT VISION": {
					flag &= cs.isNightVisionEnabled();
					break;
				}
				case "VIDEO QUALITY": {
					flag &= cs.isVideoQualityEnabled();
					break;
				}
				case "CAMERA ON IN HOME MODE": {
					flag &= cs.isCameraOnInHomeModeEnabled();
					break;
				}
				case "CAMERA ON IN NIGHT MODE": {
					flag &= cs.isCameraOnInNigtModeEnabled();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + expectedScreen.get(0) + " for " + expectedScreen.get(1));
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has Enabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							" The " + parameter + " has not Enabled");
				}
			}break;
		}
		case "DAS SECURITY SETTINGS": {
			BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "MANAGE ALERTS": {
					flag &= cs.isManangeAlertsEnabled();
					break;
				}
				case "AMAZON ALEXA": {
					flag &= cs.isAmazonAlexaiConVisible();
					flag &= cs.isAmazonAlexaEnabled();
					break;
				}
				case "GEOFENCING": {
					flag &= cs.isGeofencingEnabled();
					flag &= cs.isGeofencingDescriptionEnabled();
					break;
				}
				case "OK SECURITY VOICE COMMANDS": {
					flag &= cs.isOKSecurityVoiceCommandsEnabled();
					break;
				}
				case "ENHANCED DETERRENCE": {
					flag &= cs.isEnhancedDeterrenceEnabled();
					flag &= cs.isEnhancedDeterrenceDescriptionEnabled();
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
					flag &= cs.isOutdoorMotionViewerOnInHomeModeEnabled();
					flag &= cs.isOutdoorMotionViewerOnInHomeModeDescriptionEnabled();
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
					flag &= cs.isEntryExitDelayEnabled();
					flag &= cs.isEntryExitDelayDescriptionEnabled();
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
					flag &= cs.isAboutSecurityModesEnabled();
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
					flag &= cs.isKeyFobOptionVisible();
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
					flag &= cs.isSensorsOptionEnabled();
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
					flag &= cs.isZwaveDevicesEnabled();
					break;
				}
				case "BASE STATION VOLUME": {
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
					flag &= cs.isBaseStationVolumeEnabled();
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
					flag &= cs.isBaseStationResetWifiEnabled();
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
					flag &= cs.isBaseStationConfigurationEnabled();
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + expectedScreen.get(0) + " for " + expectedScreen.get(1));
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "Option " + parameter + " is enabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							" Option " + parameter + " is not enabled");
				}
			}break;
		}
		case "SECURITY MANAGE ALERTS" :{
			BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Alerts");
				switch (parameter.toUpperCase()) {
				case "SECURITY MODE CHANGE": {
					if(cs.isSecurityModeChangeEnabled() && cs.isSecurityModeChangeDescription()){
						Keyword.ReportStep_Pass(testCase, "Security Mode cange Alert Enabled");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security Mode change alert disabled or not dispalyed");
					}break;
				}
				case "DOOR AND WINDOWS" : {
					if(cs.isSecurityModeDoorAndWindowEnabled()){
						Keyword.ReportStep_Pass(testCase, "Security Mode cange Alert Enabled");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Security Mode change alert disabled or not dispalyed");
					}break;
				}
				}
			}break;
		}
		case "MOTION DETECTION": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				if (fieldToBeVerified.equalsIgnoreCase("MOTION DETECTION ZONE")) {
					if (cs.isMotionDetectionZoneEnabled()) {
						Keyword.ReportStep_Pass(testCase, "Motion Detection Zone section is enabled");

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Detection Zone section is disabled");
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
					if (cs.isMotionSensitivityEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"Motion Sensitivity section is disabled");
					}
				}
			}
			break;
		}
		case "THERMOSTAT ICONS": {
			PrimaryCard thermo = new PrimaryCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				System.out.println(fieldToBeVerified);
				if (fieldToBeVerified.equalsIgnoreCase("mode")) {
					if (thermo.isModeElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "Mode Element is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule Element is not enabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("schedule")) {
					if (thermo.isScheduleElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "Schedule Element is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule Element is not enabled");

					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Manage Alerts")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Manage Alerts section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Motion Detection")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);

					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Motion Detection section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Detection section is disabled");
					}

				}
				else if (fieldToBeVerified.equalsIgnoreCase("UP STEPPER")) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					HashMap<String, String> setPoints = new HashMap<String, String>();
					String CurrentUnits = null;
					String maxHeat, maxCool = null;
					try {
						setPoints = statInfo.getDeviceMaxMinSetPoints();
						CurrentUnits = statInfo.getThermostatUnits();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					String CurrentSetPoint = statInfo.getCurrentSetPoints();
					if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							 maxHeat = setPoints.get("MaxHeat").split("\\.")[0];
						} else {
							maxHeat = setPoints.get("MaxHeat");
						}
						System.out.println(maxHeat.equals(CurrentSetPoint));
						if (!maxHeat.equals(CurrentSetPoint)) {
							if (thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not enabled");
							}
						}else if (maxHeat.equals(CurrentSetPoint)) {
							if (!thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not disabled");
							}
						}
					}else if (statInfo.getThermoStatMode().equalsIgnoreCase("cool")) {
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							maxCool = setPoints.get("MaxCool").split("\\.")[0];	
						} else {
							maxCool = setPoints.get("MaxCool");
						}
						if (!maxCool.equals(CurrentSetPoint)) {
							if (thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not enabled");
							}
						}else if (maxCool.equals(CurrentSetPoint)) {
							if (!thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not disabled");
							}
						}
					}

				} 
				else if (fieldToBeVerified.equalsIgnoreCase("DOWN STEPPER")) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					HashMap<String, String> setPoints = new HashMap<String, String>();
					String CurrentUnits = null;
					String minHeat, mincool = null;
					try {
						setPoints = statInfo.getDeviceMaxMinSetPoints();
						CurrentUnits = statInfo.getThermostatUnits();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					String CurrentSetPoint = statInfo.getCurrentSetPoints();
					if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
						
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							minHeat = setPoints.get("MinHeat").split("\\.")[0];
						} else {
							minHeat = setPoints.get("MinHeat");
						}
						if (!minHeat.equals(CurrentSetPoint)) {
							if (thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not enabled");
							}
						}else if (minHeat.equals(CurrentSetPoint)) {
							if (!thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not disabled");
							}
						}
					}else if (statInfo.getThermoStatMode().equalsIgnoreCase("cool")) {

						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							System.out.println(setPoints.get("MinCool"));
							mincool = setPoints.get("MinCool").split("\\.")[0];
						} else {
							mincool = setPoints.get("MinCool");
						}
						if (mincool.equals(CurrentSetPoint)) {
							if (thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not enabled");
							}
						}else if (mincool.equals(CurrentSetPoint)) {
							if (!thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not disabled");
							}
						}
					}

				}

			}
			break;
		}

		case "THERMOSTAT": {
			Dashboard thermo = new Dashboard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				System.out.println(fieldToBeVerified);
				if (fieldToBeVerified.equalsIgnoreCase("UP STEPPER")) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					HashMap<String, String> setPoints = new HashMap<String, String>();
					String CurrentUnits = null;
					String maxHeat, maxCool = null;
					try {
						setPoints = statInfo.getDeviceMaxMinSetPoints();
						CurrentUnits = statInfo.getThermostatUnits();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					String CurrentSetPoint = statInfo.getCurrentSetPoints();
					if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							 maxHeat = setPoints.get("MaxHeat").split("\\.")[0];
						} else {
							maxHeat = setPoints.get("MaxHeat");
						}
						System.out.println(maxHeat.equals(CurrentSetPoint));
						if (!maxHeat.equals(CurrentSetPoint)) {
							if (thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not enabled");
							}
						}else if (maxHeat.equals(CurrentSetPoint)) {
							if (!thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not disabled");
							}
						}
					}else if (statInfo.getThermoStatMode().equalsIgnoreCase("cool")) {
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							maxCool = setPoints.get("MaxCool").split("\\.")[0];	
						} else {
							maxCool = setPoints.get("MaxCool");
						}
						if (!maxCool.equals(CurrentSetPoint)) {
							if (thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not enabled");
							}
						}else if (maxCool.equals(CurrentSetPoint)) {
							if (!thermo.isUPStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "UP Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"UP Stepper Element is not disabled");
							}
						}
					}

				} else if (fieldToBeVerified.equalsIgnoreCase("DOWN STEPPER")) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					HashMap<String, String> setPoints = new HashMap<String, String>();
					String CurrentUnits = null;
					String minHeat, mincool = null;
					try {
						setPoints = statInfo.getDeviceMaxMinSetPoints();
						CurrentUnits = statInfo.getThermostatUnits();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					String CurrentSetPoint = statInfo.getCurrentSetPoints();
					if (statInfo.getThermoStatMode().equalsIgnoreCase("Heat")) {
						
						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							minHeat = setPoints.get("MinHeat").split("\\.")[0];
						} else {
							minHeat = setPoints.get("MinHeat");
						}
						if (!minHeat.equals(CurrentSetPoint)) {
							if (thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not enabled");
							}
						}else if (minHeat.equals(CurrentSetPoint)) {
							if (!thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not disabled");
							}
						}
					}else if (statInfo.getThermoStatMode().equalsIgnoreCase("cool")) {

						if (CurrentUnits.equalsIgnoreCase(GlobalVariables.FAHRENHEIT)){
							System.out.println(setPoints.get("MinCool"));
							mincool = setPoints.get("MinCool").split("\\.")[0];
						} else {
							mincool = setPoints.get("MinCool");
						}
						if (mincool.equals(CurrentSetPoint)) {
							if (thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not enabled");
							}
						}else if (mincool.equals(CurrentSetPoint)) {
							if (!thermo.isDownStepperElementEnabled()) {
								Keyword.ReportStep_Pass(testCase, "Down Stepper Element is disabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Down Stepper Element is not disabled");
							}
						}
					}

				} else if (fieldToBeVerified.equalsIgnoreCase("Sound Detection")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Sound Detection section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Detection section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Night Vision")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Night Vision section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Video Quality")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Video Quality  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality  section is disabled");

					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera LED")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera LED  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera LED  section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera Microphone")) {
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Microphone  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Microphone  section is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("Camera Configuration")) {
					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						action.press(10, (int) (dimension.getHeight() * .9))
						.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						action.press(10, (int) (dimension.getHeight() * .9))
						.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					} else {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					}
					CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Configuration  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Configuration  section is disabled");
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
					if (ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("FILTER LAST REPLACED")) {
					if (ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is disabled");
					}
				} else if (fieldToBeVerified.equalsIgnoreCase("NEXT SCHEDULED REMINDER")) {
					if (ts.isSetFilterReminderOptionsVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder:" + fieldToBeVerified + " is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder:" + fieldToBeVerified + " is disabled");
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
