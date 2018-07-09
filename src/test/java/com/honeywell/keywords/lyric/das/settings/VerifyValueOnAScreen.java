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
