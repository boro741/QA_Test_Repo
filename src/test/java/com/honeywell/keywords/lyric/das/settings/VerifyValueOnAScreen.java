package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

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
import com.honeywell.screens.ThermostatSettingsScreen;

public class VerifyValueOnAScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyValueOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^(.*) value should be updated to (.*) on (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Entry-Exit Delay")
					&& parameters.get(2).equalsIgnoreCase("Entry-Exit Delay")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				try {
					int delayValue = Integer.parseInt(parameters.get(1));
					if (bs.verifyEntryExitDelaySelectedValue(delayValue)) {
						Keyword.ReportStep_Pass(testCase,
								delayValue + " is correctly displayed on Entry/Exit Delay screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								delayValue + " is not correctly displayed on Entry/Exit Delay screen");
					}
				} catch (NumberFormatException e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cannot parse the parameter " + parameters.get(1) + " to integer");
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			} else if (parameters.get(0).equalsIgnoreCase("Entry-Exit Delay")
					&& parameters.get(2).equalsIgnoreCase("Security Settings")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isEntryExitDelaySettingsOptionVisible(15)) {
					String displayed = bs.getEntryExitTimerValueFromSecuritySettingsScreen();
					String expected = parameters.get(1) + " Seconds";
					if (displayed.equalsIgnoreCase(expected)) {
						Keyword.ReportStep_Pass(testCase,
								"Entry-Exit Delay timer correctly displayed on DAS Settings screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Entry-Exit Delay timer not correctly displayed on DAS Settings screen. Displayed: "
										+ displayed + ". Expected: " + expected);
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Entry-Exit Delay Timer not displayed on DAS Settings screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("Base Station Volume")
					&& parameters.get(2).equalsIgnoreCase("Security Settings")) {
				String value = parameters.get(1).split("%")[0].split("~")[1];
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.verifyBaseStationVolumeValueOnSecuritySettings(value)) {
					Keyword.ReportStep_Pass(testCase, "Volume value is displayed correctly: " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Volume value is not displated correctly. Expected : " + parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofencing")
					&& parameters.get(2).equalsIgnoreCase("Security Settings")) {

				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofencing is enabled on Base Station Settings");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Geofencing is disabled on Base Station Settings");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofencing is disabled on Base Station Settings");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Geofencing is enabled on Base Station Settings");
					}

				}
			} else if (parameters.get(0).equalsIgnoreCase("Camera ON in Home Mode")
					&& parameters.get(2).equalsIgnoreCase("Video Settings")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Camera On in Home Mode is enabled on Video Settings");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera On in Home Mode is disabled on Video Settings");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Camera On in Home Mode is disabled on Video Settings");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera On in Home Mode is enabled on Video Settings");
					}

				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION DETECTION")
					&& parameters.get(2).equalsIgnoreCase("MOTION DETECTION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & cs.isCameraMotionDetectionSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Motion Detection Toggle is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !cs.isCameraMotionDetectionSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Motion Detection Toggle is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Motion detection status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION DETECTION")
					&& parameters.get(2).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & cs.isCameraMotionDetectionStatusYES(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Status is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Motion Detection Status is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !cs.isCameraMotionDetectionStatusYES(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Status is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Motion Detection Status is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Motion detection status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION SENSITIVITY")
					&& parameters.get(2).equalsIgnoreCase("MOTION DETECTION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("off")) {
					flag = flag & cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("low")) {
					flag = flag & cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("medium")) {
					flag = flag & cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("high")) {
					flag = flag & cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Motion Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Motion Sensitivity Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND DETECTION")
					&& parameters.get(2).equalsIgnoreCase("SOUND DETECTION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & cs.isCameraSoundDetectionSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Sound Detection Toggle is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !cs.isCameraSoundDetectionSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Sound Detection Toggle is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Sound detection status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND DETECTION")
					&& parameters.get(2).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & cs.isCameraSoundDetectionStatusYES(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Status is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Sound Detection Status is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !cs.isCameraSoundDetectionStatusYES(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Status is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Sound Detection Status is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Sound detection status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND DETECTION")
					&& parameters.get(2).equalsIgnoreCase("SOUND DETECTION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("off")) {
					flag = flag & cs.isSoundSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("low")) {
					flag = flag & cs.isSoundSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Motion Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("medium")) {
					flag = flag & cs.isSoundSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Sensitivity status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("high")) {
					flag = flag & cs.isSoundSensitivityStatusSetToExpected(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Sensitivity status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Sensitivity status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sound Sensitivity Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("NIGHT VISION")
					&& parameters.get(2).equalsIgnoreCase("NIGHT VISION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					flag = flag & cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Night Vision Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("NIGHT VISION")
					&& parameters.get(2).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					flag = flag
							& cs.isNightVisionStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag
							& cs.isNightVisionStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag
							& cs.isNightVisionStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Night Vision status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Night Vision Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("VIDEO QUALITY")
					&& parameters.get(2).equalsIgnoreCase("VIDEO QUALITY SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					flag = flag & cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					flag = flag & cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					flag = flag & cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Video Quality Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("VIDEO QUALITY")
					&& parameters.get(2).equalsIgnoreCase("CAMERA SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					flag = flag
							& cs.isVideoQualityStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Night Vision status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					flag = flag
							& cs.isVideoQualityStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					flag = flag
							& cs.isVideoQualityStatusSetToExpectedInCameraSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Video Quality status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Video Quality Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("INDOOR TEMPERATURE ALERT")
					&& parameters.get(2).equalsIgnoreCase("MANAGE ALERTS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatIndoorTempAlertSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Temperature Alert Toggle is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Indoor Temperature Alert Toggle is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatIndoorTempAlertSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Temperature Alert Toggle is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Indoor Temperature Alert Toggle is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Indoor Temperature Alert is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("INDOOR HUMIDITY ALERT")
					&& parameters.get(2).equalsIgnoreCase("MANAGE ALERTS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Indoor Humidity Alert Toggle is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatIndoorHumidityAlertSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Thermostat Indoor Humidity Alert Toggle is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Thermostat Indoor Humidity Alert Toggle is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Indoor Humidity Alert is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("SET FILTER REMINDER SWITCH")
					&& parameters.get(2).equalsIgnoreCase("SET FILTER REMINDER")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatSetFilterReminderSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatSetFilterReminderSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Set Filter Reminder Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Filter Reminder Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Set Filter Reminder Switch is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("FINE TUNE")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatFineTuneSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fine Tune Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatFineTuneSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Fine Tune Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fine Tune Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Fine Tune Switch is not displayed in the Thermostat Settings Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("ADAPTIVE RECOVERY")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Adaptive Recovery Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatAdaptiveRecoverySwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Adaptive Recovery Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Adaptive Recovery Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Adaptive Recovery Switch is not displayed in the Thermostat Settings Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("OPTIMISE")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatOptimiseSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Optimise Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Optimise Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatOptimiseSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Optimise Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Optimise Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Optimise Switch is not displayed in the Thermostat Settings Screen");
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
