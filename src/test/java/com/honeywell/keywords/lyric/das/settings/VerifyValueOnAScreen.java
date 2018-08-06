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
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyValueOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
			} else if (parameters.get(0).equalsIgnoreCase("EMERGENCY HEAT")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatEmergencyHeatSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Emergency Heat Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Emergency Heat Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatEmergencyHeatSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Emergency Heat Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Emergency Heat Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Emergency Heat Switch is not displayed in the Thermostat Settings Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("FROST PROTECTION MODE")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				String value = parameters.get(1).split("%")[0].split("~")[1];
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (ts.verifyThermostatFrostProtectionValue(value)) {
					Keyword.ReportStep_Pass(testCase,
							"Frost Protection value is displayed correctly: " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Frost Protection value is not displated correctly. Expected : " + parameters.get(1));
				}

			} else if (parameters.get(0).equalsIgnoreCase("HUMIDIFICATION")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT HUMIDIFICATION")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatHumidificationSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Humidification Switch is ON in Thermostat Humidification Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Humidification Switch is OFF in Thermostat Humidification Screen");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatHumidificationSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Humidification Switch is OFF in Thermostat Humidification Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Humidification Switch is ON in Thermostat Humidification Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Humidification Switch is not displayed in the Thermostat Humidification Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("HUMIDIFICATION")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				String actualThermoHumidificationValueInSettingsScreen = null;
				int actualThermostatHumidificationValue = 0;
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					actualThermoHumidificationValueInSettingsScreen = ts
							.getThermostatHumidificationValueInSettingsScreen(testCase, parameters.get(1));
					actualThermostatHumidificationValue = Integer
							.parseInt(actualThermoHumidificationValueInSettingsScreen);
					if (actualThermostatHumidificationValue == Integer
							.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE"))) {
						Keyword.ReportStep_Pass(testCase,
								"Correct Humidification value is displayed in Thermostat Settings screen: "
										+ actualThermostatHumidificationValue);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Humidification value to be displayed in Thermostat Settings screen is: "
										+ actualThermostatHumidificationValue + " .But "
										+ Integer.parseInt(
												inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE")
														+ " is displayed."));
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					actualThermoHumidificationValueInSettingsScreen = ts
							.getThermostatHumidificationValueInSettingsScreen(testCase, parameters.get(1));
					if (actualThermoHumidificationValueInSettingsScreen
							.equals(inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE"))) {
						Keyword.ReportStep_Pass(testCase,
								"Correct Humidification value is displayed in Thermostat Settings screen: "
										+ actualThermoHumidificationValueInSettingsScreen);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Humidification value to be displayed in Thermostat Settings screen is: "
										+ actualThermoHumidificationValueInSettingsScreen + " .But "
										+ inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE")
										+ " is displayed.");
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("DEHUMIDIFICATION")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT DEHUMIDIFICATION")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatDehumidificationSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Dehumidification Switch is ON in Thermostat Dehumidification Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Dehumidification Switch is OFF in Thermostat Dehumidification Screen");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatDehumidificationSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Dehumidification Switch is OFF in Thermostat Dehumidification Screen");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Dehumidification Switch is ON in Thermostat Dehumidification Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Dehumidification Switch is not displayed in the Thermostat Dehumidification Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("DEHUMIDIFICATION")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				String actualThermoDehumidificationValueInSettingsScreen = null;
				int actualThermostatDehumidificationValue = 0;
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					actualThermoDehumidificationValueInSettingsScreen = ts
							.getThermostatDehumidificationValueInSettingsScreen(testCase, parameters.get(1));
					actualThermostatDehumidificationValue = Integer
							.parseInt(actualThermoDehumidificationValueInSettingsScreen);
					if (actualThermostatDehumidificationValue == Integer
							.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE"))) {
						Keyword.ReportStep_Pass(testCase,
								"Correct Dehumidification value is displayed in Thermostat Settings screen: "
										+ actualThermostatDehumidificationValue);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Dehumidification value to be displayed in Thermostat Settings screen is: "
										+ actualThermostatDehumidificationValue + " .But "
										+ Integer.parseInt(
												inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE")
														+ " is displayed."));
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					actualThermoDehumidificationValueInSettingsScreen = ts
							.getThermostatDehumidificationValueInSettingsScreen(testCase, parameters.get(1));
					if (actualThermoDehumidificationValueInSettingsScreen
							.equals(inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE"))) {
						Keyword.ReportStep_Pass(testCase,
								"Correct Dehumidification value is displayed in Thermostat Settings screen: "
										+ actualThermoDehumidificationValueInSettingsScreen);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Dehumidification value to be displayed in Thermostat Settings screen is: "
										+ actualThermoDehumidificationValueInSettingsScreen + " .But "
										+ inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE")
										+ " is displayed.");
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SLEEP BRIGHTNESS")
					&& parameters.get(2).equalsIgnoreCase("SLEEP BRIGHTNESS MODE")) {
				String value = parameters.get(1);
				if (value.contains("%")) {
					value = value.split("%")[0].split("~")[1];
				}
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (ts.verifyThermostatSleepBrightnessValueInSleepBrightnessModeScreen(value)) {
					Keyword.ReportStep_Pass(testCase,
							"Sleep Brightness value in Sleep Brightness Mode screen is displayed correctly: "
									+ parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sleep Brightness value in Sleep Brightness Mode screen is not displated correctly. Expected : "
									+ parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("SLEEP BRIGHTNESS")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				String value = parameters.get(1);
				if (value.contains("%")) {
					value = value.split("%")[0].split("~")[1];
				}
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (ts.verifyThermostatSleepBrightnessValueInSettingsScreen(value)) {
					Keyword.ReportStep_Pass(testCase,
							"Sleep Brightness value in Settings screen is displayed correctly: " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sleep Brightness value in Settings screen is not displated correctly. Expected : "
									+ parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & ts.verifySoundStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					flag = flag & ts.verifySoundStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound status is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("NORMAL")) {
					flag = flag & ts.verifySoundStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound status is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound status is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sound Status is not displayed");
				}
			} else if (parameters.get(0).equalsIgnoreCase("AUTO CHANGEOVER")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.isThermostatAutoChangeOverSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Auto Changeover Switch is ON");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Auto Changeover Switch is OFF");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & !ts.isThermostatAutoChangeOverSwitchEnabled(testCase);
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Auto Changeover Switch is OFF");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Auto Changeover Switch is ON");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Auto Changeover Switch is not displayed in the Thermostat Settings Screen");
				}
			} else if (parameters.get(0).equalsIgnoreCase("VENTILATION")
					&& parameters.get(2).equalsIgnoreCase("THERMOSTAT SETTINGS")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					flag = flag & ts.verifyVentilationStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Ventilation is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Ventilation is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & ts.verifyVentilationStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Ventilation is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Ventilation is not set to: " + parameters.get(1));
					}
				} else if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					flag = flag & ts.verifyVentilationStatusInSettingsScreen(testCase, parameters.get(1));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Sound Ventilation is set to: " + parameters.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sound Ventilation is not set to: " + parameters.get(1));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Sound Ventilation is not displayed");
				}

			} else if (parameters.get(0).equalsIgnoreCase("Email Notifications")
					&& parameters.get(2).equalsIgnoreCase("Manage Alerts Camera Status")) {
				CameraSettingsScreen bs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isCameraEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Camera Email switch is enabled on Base Station Settings");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camear Email is disabled on Manage Alerts screen");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isCameraEmailNotificationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Camera Email switch is disabled on Manage Alerts screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Email switch is enabled on Base Station Settings");
					}

				}
			} else if (parameters.get(0).equalsIgnoreCase("Email Notifications")
				&& parameters.get(2).equalsIgnoreCase("Manage Alerts Sound Events")) {
			CameraSettingsScreen bs = new CameraSettingsScreen(testCase);
			if (parameters.get(1).equalsIgnoreCase("ON")) {
				if (bs.isSoundEmailNotificationSwitchEnabled(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Sound Email switch is enabled on Manage Alerts screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sound Email switch is disabled on Manage Alerts screen");
				}
			} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
				if (!bs.isSoundEmailNotificationSwitchEnabled(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Sound Email switch is disabled on Manage Alerts screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sound Email switch is enabled on Manage Alerts screen");
				}

			}
		} else if (parameters.get(0).equalsIgnoreCase("Email Notifications")
				&& parameters.get(2).equalsIgnoreCase("Manage Alerts Motion Events")) {
			CameraSettingsScreen bs = new CameraSettingsScreen(testCase);
			if (parameters.get(1).equalsIgnoreCase("ON")) {
				if (bs.isMotionEmailNotificationSwitchEnabled(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Motion Email switch is enabled on Manage Alerts screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Motion Email switch is disabled on Manage Alerts screen");
				}
			} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
				if (!bs.isMotionEmailNotificationSwitchEnabled(testCase)) {
					Keyword.ReportStep_Pass(testCase, "Motion Email switch is disabled on Manage Alerts screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Motion Email switch is enabled on Manage Alerts screen");
				}
			}
		}
		}
		catch (Exception e) {
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
