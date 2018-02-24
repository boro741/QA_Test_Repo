package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyDeviceNameDisplayedOnDevicesScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> expectedDevice;
	public boolean flag = true;

	public VerifyDeviceNameDisplayedOnDevicesScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedDevice) {
		// this.inputs = inputs;
		this.testCase = testCase;
		this.expectedDevice = expectedDevice;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with \"(.*)\" device on the \"(.*)\" screen$")
	public boolean keywordSteps() {
		try {
			if (expectedDevice.get(1).equalsIgnoreCase("Dashboard")) {
				flag = flag & LyricUtils.verifyDeviceDisplayedOnDashboard(testCase, expectedDevice.get(0));
			} else if (expectedDevice.get(1).equalsIgnoreCase("Sensor")) {
				BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
				if (bs.isSensorPresentInSensorsList(expectedDevice.get(0))) {
					Keyword.ReportStep_Pass(testCase,
							"Sensor : " + expectedDevice.get(0) + " is present in the sensor list");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sensor : " + expectedDevice.get(0) + " is not present in the sensor list");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
