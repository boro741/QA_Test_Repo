package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class AlarmScreen extends MobileScreens {

	private static final String screenName = "AlarmScreen";

	public AlarmScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAlarmScreenDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Title") 
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Subtitle");
	}

	public boolean clickOnDismissAlarm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlarmDismissButton");
	}

	public boolean clickOnCall() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CallButton");
	}
	
	//Entry delay screen
	
	public boolean isEntryDelayScreenDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton") 
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightProgress");
	}
	
}
