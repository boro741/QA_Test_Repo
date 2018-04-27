package com.honeywell.keywords.lyric.das.pushnotifications;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASNotificationUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.account.information.LocationInformation;

public class VerifyPushNotification extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyPushNotification(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user receives a \"(.+)\" push notification$")
	public boolean keywordSteps() throws KeywordException {
		String notification = "";
		String sensorName = "";
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		DASNotificationUtils.openNotifications(testCase);
		switch (exampleData.get(0).toUpperCase()) {
		case "MOTION": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = "Motion Dectected by " + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + " at "
					+ inputs.getInputValue("LOCATION1_NAME") + ".";
			break;
		}
		case "DOOR OPENED": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "DOOR CLOSED": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " closed at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "WINDOW OPENED": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "WINDOW CLOSED": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1");
			notification = sensorName + " closed at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "SET TO HOME": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO AWAY": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ locInfo.getUserFirstName();
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "SET TO AWAY BY KEYFOB": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "SET TO NIGHT": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO NIGHT BY KEYFOB": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			}
			break;
		}
		case "SET TO OFF": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned Off by "
						+ locInfo.getUserFirstName();
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned Off by "
						+ locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO OFF BY KEYFOB": {
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned off by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			} else {
				notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned off by "
						+ inputs.getInputValue("KEY_FOB_NAME");
			}
			break;
		}
		case "ALARM": {
			notification = "Security Alarm in progress at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "ALARM DISMISSED": {
			notification = "Alarm at " + inputs.getInputValue("LOCATION1_NAME") + " Cancelled by "
					+ locInfo.getUserFirstName();
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input");
			DASNotificationUtils.closeNotifications(testCase);
			return flag;
		}
		}
		flag = flag & sc.verifyIfPushNotificationIsVisible(notification);
		DASNotificationUtils.closeNotifications(testCase);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
