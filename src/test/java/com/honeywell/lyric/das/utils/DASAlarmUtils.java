package com.honeywell.lyric.das.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AlarmScreen;

public class DASAlarmUtils {
	private static HashMap<String, MobileObject> fieldObjects;

	
	
	//Activate device screen actions
		public static boolean timeOutForNoSensorAction(TestCases testCase,TestCaseInputs inputs) {
			boolean flag = true;
			AlarmScreen alarmScreen = new AlarmScreen(testCase);
			try {
				FluentWait<String> fWait = new FluentWait<String>(" ");
				fWait.pollingEvery(3, TimeUnit.SECONDS);
				fWait.withTimeout(2, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
					public Boolean apply(String a) {
						try {
							if (alarmScreen.isAlarmScreenDisplayed()) {
								inputs.setInputValue("ALARM_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
								return true;
							} else {
								return false;
							}
						} catch (Exception e) {
							return false;
						}
					}
				});
				if (isEventReceived) {
					Keyword.ReportStep_Pass(testCase, "Switching to completed");
				}
			} catch (TimeoutException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Switching to complete did not complete after waiting for 1 minute");
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			}
			return flag;
		}
	public static boolean confirmDismissAlarm(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmDismissButton")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "DismissAlarmPopupOk", 10)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DismissAlarmPopupOk");
		DASSolutionCardUtils.waitForDismissProcessRequest(testCase);
		inputs.setInputValue("ALARM_DISMISSED_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		Keyword.ReportStep_Pass(testCase, "ALARM_DISMISSED_TIME " + inputs.getInputValue("ALARM_DISMISSED_TIME"));
		inputs.setInputValue("HOME_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));

		return flag;
	}
	
	public static boolean verifyAlarmScreenDisplayed(TestCases testCase){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isAlarmScreenDisplayed();
		
	}
	
	public static boolean verifyProgressDisplayed(TestCases testCase){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isPleaseWaitDisplayed();
		
	}

	public static boolean clickOnDismissAlarm(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.clickOnDismissAlarm();
	}
	
	public static boolean clickOnCall(TestCases testCase, TestCaseInputs inputs) {
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.clickOnCall();
	}
	
	// Entry delay screen
	
	public static boolean verifyEntryDelayScreenDisplayed(TestCases testCase){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		return alarmScreen.isEntryDelayScreenDisplayed();
	}
	
	public static boolean clickOnSwitchToHome(TestCases testCase, TestCaseInputs inputs){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("HOME_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		
		return alarmScreen.clickOnSwitchToHome();
	}
	
	public static boolean clickOnSwitchToNight(TestCases testCase, TestCaseInputs inputs){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("NIGHT_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		
		return alarmScreen.clickOnSwitchToNight();
	}
	
	public static boolean clickOnAttention(TestCases testCase, TestCaseInputs inputs){
		AlarmScreen alarmScreen = new AlarmScreen(testCase);
		inputs.setInputValue("ALARM_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		inputs.setInputValue("ATTENTION_ALARM_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
		return alarmScreen.clickOnAttention();
	}
}
