package com.honeywell.keywords.lyric.das.pushnotifications;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASNotificationUtils;
import com.honeywell.account.information.LocationInformation;

public class VerifyPushNotificationNotReceived extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyPushNotificationNotReceived(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not receive a \"(.+)\" push notification$")
	public boolean keywordSteps() throws KeywordException {
		String notification = "";
		String sensorName = "";
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		DASNotificationUtils.openNotifications(testCase);
		if (exampleData.get(0).equalsIgnoreCase("DOOR OPENED")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
		} else if (exampleData.get(0).equalsIgnoreCase("DOOR CLOSED")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " closed at " + inputs.getInputValue("LOCATION1_NAME");
		} else if (exampleData.get(0).equalsIgnoreCase("WINDOW OPENED")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
		} else if (exampleData.get(0).equalsIgnoreCase("WINDOW CLOSED")) {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
			notification = sensorName + " closed at " + inputs.getInputValue("LOCATION1_NAME");
		} else if (exampleData.get(0).equalsIgnoreCase("SET TO HOME")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
			}
		} else if (exampleData.get(0).equalsIgnoreCase("SET TO AWAY")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ locInfo.getUserFirstName();
			}
		} else if (exampleData.get(0).equalsIgnoreCase("SET TO NIGHT")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ locInfo.getUserFirstName();
			}
		} else if (exampleData.get(0).equalsIgnoreCase("SET TO OFF")) {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Off by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Off by "
						+ locInfo.getUserFirstName();
			}
		} else if (exampleData.get(0).equalsIgnoreCase("ALARM")) {
			notification = "Alarm at " + inputs.getInputValue("LOCATION1_NAME");
		} else if (exampleData.get(0).equalsIgnoreCase("ALARM DISMISSED")) {
			notification = "Alarm at " + inputs.getInputValue("LOCATION1_NAME") + " Cancelled by "
					+ locInfo.getUserFirstName();
		} else if (exampleData.get(0).equalsIgnoreCase("GEOFENCE AWAY")) {
			notification = "Geofence crossed. Everyone is away from '" + inputs.getInputValue("LOCATION1_DEVICE1_NAME")
					+ "'.";
		} else if (exampleData.get(0).equalsIgnoreCase("GEOFENCE HOME")) {
			notification = "Geofence crossed. " + inputs.getInputValue("USER_NAME") + " has arrived at '"
					+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + "'.";
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input");
			DASNotificationUtils.closeNotifications(testCase);
			return flag;
		}
		String locatorValue = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			locatorValue = "//*[@text='" + notification + "']";

		} else {
			locatorValue = "//XCUIElementTypeCell[contains(@name,'" + notification + "')]";
		}
		if (!MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 10)) {
			Keyword.ReportStep_Pass(testCase, "'" + notification + "' Push Notification not present");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"'" + notification + "' Push Notification present");
		}
		DASNotificationUtils.closeNotifications(testCase);
		testCase.getMobileDriver().launchApp();
		Keyword.ReportStep_Pass(testCase, "Launching app to continue testing");
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
