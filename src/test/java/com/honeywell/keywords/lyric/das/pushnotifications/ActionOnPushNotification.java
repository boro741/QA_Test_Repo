package com.honeywell.keywords.lyric.das.pushnotifications;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASNotificationUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AlarmScreen;

import io.appium.java_client.TouchAction;

public class ActionOnPushNotification extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	public boolean flag = true;

	public ActionOnPushNotification(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user selects the \"(.+)\" push notification$")
	public boolean keywordSteps() throws KeywordException {
		String notification = "";
		String sensorName = "";
		// LocationInformation locInfo = new LocationInformation(testCase, inputs);
		DASNotificationUtils.openNotifications(testCase);
		switch (exampleData.get(0).toUpperCase()) {
		case "ISMV MOTION DETECTED": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMTOIONVIEWER1");
			notification = "Motion detected by Motion Viewer \"" + sensorName + "\"";
			break;
		}
		case "MOTION DETECTED": {
			notification = "Motion Detected by Camera \"" + inputs.getInputValue("LOCATION1_DEVICE1_NAME") + "\".";
			break;
		}
		case "SWITCH TO NIGHT FROM DOOR OPEN": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
			String locatorValue = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locatorValue = "//*[@text='" + notification + "']";
			} else {
				locatorValue = "//XCUIElementTypeCell[contains(@label,'" + notification + "')]";
			}
			AlarmScreen alarmScreen = new AlarmScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (alarmScreen.switchToNightOnNotificationExists()) {
					flag = alarmScreen.clickswitchToNightOnNotification();
				} else {
					alarmScreen.swipe(locatorValue);
					flag = alarmScreen.clickswitchToNightOnNotification();
				}
			} else {
				alarmScreen.swipe(locatorValue);
				flag = alarmScreen.clickswitchToNightOnNotification();
			}
			return flag;
		}
		case "SWITCH TO HOME FROM DOOR OPEN": {
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = sensorName + " opened at " + inputs.getInputValue("LOCATION1_NAME");
			String locatorValue = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locatorValue = "//*[@text='" + notification + "']";
			} else {
				locatorValue = "//XCUIElementTypeCell[contains(@label,'" + notification + "')]";
			}
			System.out.println(locatorValue);
			AlarmScreen alarmScreen = new AlarmScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (alarmScreen.switchToHomeOnNotificationExists()) {
					flag = alarmScreen.clickswitchToHomeOnNotification();
				} else {
					alarmScreen.swipe(locatorValue);
					flag = alarmScreen.clickswitchToHomeOnNotification();
				}
			} else {
				alarmScreen.swipe(locatorValue);
				flag = alarmScreen.clickswitchToHomeOnNotification();
			}
			return flag;
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
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = locInfo.getUserFirstName() + " set " + inputs.getInputValue("LOCATION1_DEVICE1_NAME")
						+ " to Home";
				/*
				 * notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			} else {
				notification = locInfo.getUserFirstName() + " set Security "
						+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " to Home";
				/*
				 * notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "SET TO AWAY": {
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = locInfo.getUserFirstName() + " set " + inputs.getInputValue("LOCATION1_DEVICE1_NAME")
						+ " to Away";
				/*
				 * notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			} else {
				notification = locInfo.getUserFirstName() + " set Security "
						+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " to Away";
				/*
				 * notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "SET TO NIGHT": {
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = locInfo.getUserFirstName() + " set " + inputs.getInputValue("LOCATION1_DEVICE1_NAME")
						+ " to Night";
				/*
				 * notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			} else {
				notification = locInfo.getUserFirstName() + " set Security "
						+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " to Night";
				/*
				 * notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "SET TO OFF": {
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			if (inputs.getInputValue("LOCATION1_DEVICE1_NAME") != "Security") {
				notification = locInfo.getUserFirstName() + " turned " + inputs.getInputValue("LOCATION1_DEVICE1_NAME")
						+ " off";
				/*
				 * notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			} else {
				notification = locInfo.getUserFirstName() + " set Security "
						+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " to OFF";
				/*
				 * notification = "Security " + inputs.getInputValue("LOCATION1_DEVICE1_NAME") +
				 * " set to Away by " + locInfo.getUserFirstName();
				 */
			}
			System.out.println("############notification: " + notification);
			break;
		}
		case "ALARM": {
			notification = "Security Alarm in progress at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "ALARM DISMISSED": {
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
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
		String locatorValue = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			locatorValue = "//*[@text='" + notification + "']";

		} else {
			locatorValue = "//XCUIElementTypeCell[contains(@label,'" + notification + "')]";
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (testCase.getMobileDriver().findElementsByXPath(locatorValue).get(0) != null) {
			Keyword.ReportStep_Pass(testCase, "'" + notification + "' Push Notification Present");
			WebElement ele = testCase.getMobileDriver().findElementsByXPath(locatorValue).get(0);

			int xAxix = ele.getLocation().getX();
			int yAxix = ele.getLocation().getY();
//			if (testCase.getMobileDriver().getPlatformName().contains("Android")){
			System.out.println("In click Method");
				testCase.getMobileDriver().findElementsByXPath(locatorValue).get(0).click();
//			}else{
//				testCase.getMobileDriver().launchApp();
//			}
			testCase.getMobileDriver().swipe(xAxix, yAxix, 300, 0, 1);

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("ID", "com.android.systemui:id/quick_settings_panel", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase, "Quick Settings Panel is displayed.");
					CustomDriver driver = testCase.getMobileDriver();
					Dimension dimension = driver.manage().window().getSize();
					int height = dimension.getHeight();
					int width = dimension.getWidth();
					TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
					touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000))
							.moveTo(width / 2, 82).release();
					touchAction.perform();
					touchAction.tap(width / 2, height / 2).perform();
					touchAction.tap(width / 2, height / 2).perform();
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"'" + notification + "' Push Notification not present");
			DASNotificationUtils.closeNotifications(testCase);
			testCase.getMobileDriver().launchApp();
			LyricUtils.verifyLoginSuccessful(testCase, inputs, false);
			Keyword.ReportStep_Pass(testCase, "Launching app to continue testing");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
