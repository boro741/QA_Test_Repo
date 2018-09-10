package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;


import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.ThermostatSettingsScreen;
import com.honeywell.screens.ZwaveScreen;

public class VerifyDeviceNotDisplayedOnScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> expectedDevice;
	public boolean flag = true;

	public VerifyDeviceNotDisplayedOnScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedDevice) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.expectedDevice = expectedDevice;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with \"(.*)\" device on the \"(.*)\" screen$")
	public boolean keywordSteps() {
		Dashboard dashBordScreen = new Dashboard(testCase);
		switch (expectedDevice.get(1).toUpperCase()) {
		case "DASHBOARD": {
			if (!dashBordScreen.isDevicePresentOnDashboard(expectedDevice.get(0))) {
				Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedDevice.get(0) + " displayed");
			}
			break;
		}
		case "ZWAVE DEVICES": {
			ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
			switch (expectedDevice.get(0).toUpperCase()) {
			case "SWITCH": {
				if (!zwaveScreen.isSwitchSettingOnZwaveDevicesDisplayed()) {
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedDevice.get(0) + " displayed");
				}
				break;
			}
			case "DIMMER": {
				if (!zwaveScreen.isDimmerSettingOnZwaveDevicesDisplayed()) {
					Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							expectedDevice.get(0) + " displayed");
				}
				break;
			}
			}
			break;
		}
		case "SENSOR LIST": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedDevice.get(0).toUpperCase()) {
			case "DOOR": {
				String sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
				if (sensor.checkSensorNameNotInSensorList(sensorName) == false) {
					Keyword.ReportStep_Pass(testCase, "Sensor " + sensorName + " is not in sensor list");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sensor " + sensorName + " is in sensor list");
				}
				break;
			}
			case "WINDOW": {
				String sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
				if (sensor.checkSensorNameNotInSensorList(sensorName) == false) {
					Keyword.ReportStep_Pass(testCase, "Sensor " + sensorName + " is not in sensor list");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sensor " + sensorName + " is in sensor list");
				}
				break;
			}
			case "MOTION SENSOR": {
				String sensorName = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1");
				if ((sensor.checkSensorNameNotInSensorList(sensorName) == false)
						&& (sensor.isSensorsScreenTitleVisible())) {
					Keyword.ReportStep_Pass(testCase, "Sensor " + sensorName + " is not in sensor list");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sensor " + sensorName + " is in sensor list");
					flag = flag & DIYRegistrationUtils.deleteSensorThroughCHIL(testCase, inputs, sensorName);
				}
				break;
			}
			}
			break;
		}
		case "KEYFOB LIST": {
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedDevice.get(0).toUpperCase()) {
			case "KEYFOB": {
				String sensorName = inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1");
				if (sensor.checkSensorNameNotInSensorList(sensorName) == false) {
					Keyword.ReportStep_Pass(testCase, "Sensor " + sensorName + " is not in sensor list");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sensor " + sensorName + " is in sensor list");
				}
				break;
			}
			}
			break;
		}
		case "THERMOSTAT SETTINGS": {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			if (!ts.isSetUpHomeKitAndSiriOptionVisible("Set up HomeKit & Siri")) {
				Keyword.ReportStep_Pass(testCase, expectedDevice.get(0) + " not be displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedDevice.get(0) + " displayed");
			}
			break;
		}
		default: {
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE,
					"Input " + expectedDevice.get(1).toUpperCase() + " not handled");
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
