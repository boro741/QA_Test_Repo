package com.honeywell.keywords.lyric.das.pushnotifications;

import java.time.Duration;
import java.util.ArrayList;

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
import com.honeywell.screens.AlarmScreen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
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
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		DASNotificationUtils.openNotifications(testCase);
		switch(exampleData.get(0).toUpperCase()){
		case "MOTION DETECTED":{
			sensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
			notification = "Motion Dectected by Camera \""+ inputs.getInputValue("LOCATION1_CAMERA1_NAME")+"\".";
			break;
		}
		case "SWITCH TO NIGHT FROM DOOR OPEN":{
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
				if(alarmScreen.switchToNightOnNotificationExists()) {
					flag=alarmScreen.clickswitchToNightOnNotification();
				}
				else {
					alarmScreen.swipe(locatorValue);
					flag=alarmScreen.clickswitchToNightOnNotification();
				}	
			}
			else {
				alarmScreen.swipe(locatorValue);
				flag=alarmScreen.clickswitchToNightOnNotification();
				flag=alarmScreen.clickOnSwitchToNight();
			}
			return flag;
		}
		case "SWITCH TO HOME FROM DOOR OPEN":{
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
				if(alarmScreen.switchToHomeOnNotificationExists()) {
					flag=alarmScreen.clickswitchToHomeOnNotification();
				}
				else {
					alarmScreen.swipe(locatorValue);
					flag=alarmScreen.clickswitchToHomeOnNotification();
				}
			}
			else {
				alarmScreen.swipe(locatorValue);
				flag=alarmScreen.clickswitchToHomeOnNotification();
				flag=alarmScreen.clickOnSwitchToHome();

			}
			return flag;
		}
		case "DOOR OPENED":{
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
			if(inputs.getInputValue("LOCATION1_DEVICE1_NAME")!="Security"){
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Home by " + locInfo.getUserFirstName();
			}else{
				notification = "Security "+inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Home by " + locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO AWAY": {
			if(inputs.getInputValue("LOCATION1_DEVICE1_NAME")!="Security"){
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Away by " + locInfo.getUserFirstName();
			}else{
				notification = "Security "+inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Away by " + locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO NIGHT": {
			if(inputs.getInputValue("LOCATION1_DEVICE1_NAME")!="Security"){
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Night by " + locInfo.getUserFirstName();
			}else{
				notification = "Security "+inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Night by " + locInfo.getUserFirstName();
			}
			break;
		}
		case "SET TO OFF": {
			if(inputs.getInputValue("LOCATION1_DEVICE1_NAME")!="Security"){
				notification = inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Off by " + locInfo.getUserFirstName();
			}else{
				notification = "Security "+inputs.getInputValue("LOCATION1_DEVICE1_NAME")+" set to Off by " + locInfo.getUserFirstName();
			}
			break;
		}
		case "ALARM": {
			notification = "Security Alarm in progress at " + inputs.getInputValue("LOCATION1_NAME");
			break;
		}
		case "ALARM DISMISSED":{
			notification = "Alarm at " + inputs.getInputValue("LOCATION1_NAME")+" Cancelled by "+locInfo.getUserFirstName();
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
		if (MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 10)) {
			Keyword.ReportStep_Pass(testCase, "'" + notification + "' Push Notification Present");
			MobileUtils.clickOnElement( testCase,"xpath", locatorValue);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"'" + notification + "' Push Notification not present");
			DASNotificationUtils.closeNotifications(testCase);
			testCase.getMobileDriver().launchApp();
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
