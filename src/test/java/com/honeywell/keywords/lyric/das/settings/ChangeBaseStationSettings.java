package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;

import io.appium.java_client.TouchAction;
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.ThermostatSettingsScreen;

public class ChangeBaseStationSettings extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public ChangeBaseStationSettings(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user changes the (.*) to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("Base Station Volume")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.VOLUME);
				String value = parameters.get(1).split("%")[0].split("~")[1];
				if (bs.setValueToVolumeSlider(value)) {
					Keyword.ReportStep_Pass(testCase, "Successfully set the volume to " + parameters.get(1));
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to set the volume to: " + parameters.get(1));
				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofencing Status")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings Screen");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already disabled on the settings Screen");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("Geofencing Option")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);

				if (parameters.get(1).equalsIgnoreCase("ON")) {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					flag = flag & DASSettingsUtils.EnableGlobalGeofence(testCase);
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					Thread.sleep(2000);
					if (bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings page");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}

					flag = flag & DASSettingsUtils.navigateFromSecuritySettingsToSecuritySolutionCard(testCase);
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					Thread.sleep(3000);
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					Thread.sleep(2000);
					flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
					Thread.sleep(2000);
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Geofence is already disabled on the settings page");
					} else {
						flag = flag & bs.toggleGeofencingSwitch(testCase);
					}

				} else if (parameters.get(1).equalsIgnoreCase("ON when Global Geofence disabled")) {
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					Thread.sleep(2000);
					flag = flag & DASSettingsUtils.DisableGlobalGeofence(testCase);
					Thread.sleep(2000);
					if (!bs.isGeofencingSwitchEnabled(testCase)) {
						bs.toggleGeofencingSwitch(testCase);
						Keyword.ReportStep_Pass(testCase, "Geofence is already enabled on the settings page");

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: "
								+ "Failed to disable Geofence settings when Global geofence got disabled");
					}

				}
			} else if (parameters.get(0).equalsIgnoreCase("Camera ON in Home Mode")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (bs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera On in Home Mode is already enabled on the settings Screen");
					} else {
						flag = flag & bs.toggleCameraOnInHomeModeSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!bs.isCameraOnInHomeModeSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera On in Home Mode is already disabled on the settings Screen");
					} else {
						flag = flag & bs.toggleCameraOnInHomeModeSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("CAMERA STATUS ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status On/Off Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Status On/Off Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraStatusONOFFAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraStatusONOFFAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Camera Status On/Off Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION EVENT ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is already enabled in the Camera settings Screen");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Event Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Motion Event Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleMotionEventAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isMotionEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Event Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND EVENT ALERTS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isSoundEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is already enabled in the Camera settings Screen");
					} else {
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is enabled in the Camera settings Screen");
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Sound Event Alerts is already disabled in the Camera settings Screen");
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isSoundEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Sound Event Alerts is enabled in the Camera settings Screen");
							flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Sound Event Alerts is disabled in the Camera settings Screen");
							}
						}
					} else {
						flag = flag & cs.toggleSoundEventsAlertsSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isSoundEventAlertsSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Sound Event Alerts is disabled in the Camera settings Screen");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION DETECTION")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Motion Detection Toggle is already enabled in the Camera Motion Detection Screen");
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
							flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Motion Detection Toggle is enabled in the Camera Motion Detection Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Motion Detection Toggle is already disabled in the Camera Motion Detection Screen");
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned ON");
							flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleCameraMotionDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMotionDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Motion Detection Toggle is turned OFF");
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("MOTION SENSITIVITY")) {
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
				if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("MEDIUM")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					if (cs.isMotionSensitivityStatusSetToExpected(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Motion Sensitivity Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setMotionSensitivityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase,
									"Motion Sensitivity Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Motion Sensitivity Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("SOUND DETECTION")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Sound Detection Toggle is already enabled in the Camera Sound Detection Screen");
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
							flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Camera Sound Detection Toggle is enabled in the Camera Motion Detection Screen");
							}
						}
					} else {
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Sound Detection Toggle is already disabled in the Camera Sound Detection Screen");
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned ON");
							flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
							}
						}
					} else {
						flag = flag & cs.toggleCameraSoundDetectionSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraSoundDetectionSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Sound Detection Toggle is turned OFF");
						}
					}
				}

			} else if (parameters.get(0).equalsIgnoreCase("NIGHT VISION SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (cs.isNightVisionStatusSetToExpectedInNightVisionScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Night Vision Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setNightVisionStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Night Vision Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Night Vision Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("VIDEO QUALITY SETTINGS")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("AUTO")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("LOW")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("HIGH")) {
					if (cs.isVideoQualityStatusSetToExpectedInVideoQualityScreen(testCase, parameters.get(1))) {
						Keyword.ReportStep_Pass(testCase,
								"Video Quality Status is already set to: " + parameters.get(1));
					} else {
						flag = flag & cs.setVideoQualityStatusToExpected(testCase, parameters.get(1));
						if (flag) {
							Keyword.ReportStep_Pass(testCase, "Video Quality Status is set to: " + parameters.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Video Quality Status is not set to: " + parameters.get(1));
						}
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("CAMERA MICROPHONE")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", parameters.get(0));
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (cs.isCameraMicrophoneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Microphone Toggle is already enabled in the Camera Settings Screen");
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!cs.isCameraMicrophoneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Microphone Toggle is turned OFF");
							flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						}
					} else {
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!cs.isCameraMicrophoneSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Camera Microphone Toggle is already disabled in the Camera Settings Screen");
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (cs.isCameraMicrophoneSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase, "Camera Microphone Toggle is turned ON");
							flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
						}
					} else {
						flag = flag & cs.toggleCameraMicroPhoneSwitch(testCase);
					}
				}
			} else if (parameters.get(0).equalsIgnoreCase("INDOOR TEMPERATURE ALERT")) {
				ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
				if (parameters.get(1).equalsIgnoreCase("ON")) {
					if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Temperature Alert Toggle is already enabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned OFF");
							flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Temperature Alert Toggle is enabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned ON");
						}
					}
				} else if (parameters.get(1).equalsIgnoreCase("OFF")) {
					if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase,
								"Thermostat Indoor Temperature Alert Toggle is already disabled in the Manage Alerts Screen");
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned ON");
							flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
							flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
							if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
								Keyword.ReportStep_Pass(testCase,
										"Thermostat Indoor Temperature Alert Toggle is disabled in the Manage Alerts Screen");
							}
						}
					} else {
						flag = flag & ts.toggleThermostatIndoorTempAlertSwitch(testCase);
						flag = flag & CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						if (!ts.isThermostatIndoorTempAlertSwitchEnabled(testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Thermostat Indoor Temperature Alert Toggle is turned OFF");
						}
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