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
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Subtitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton");
	}

	public boolean isPleaseWaitDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DismissRequestProcessing");
	}
	
	public boolean isAlarmDismissButtonDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton");
	}
	
	public boolean clickOnDismissAlarm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlarmDismissButton") &&
				MobileUtils.clickOnElement(objectDefinition, testCase, "DismissAlarmPopupOk");
		
	}

	public boolean clickOnCall() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CallButton");
	}
	
	//Entry delay screen
	
	public boolean isEntryDelayScreenDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton") 
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton");
	}
	
	public boolean clickOnSwitchToHome(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToHomeButton");
	}
	
	public boolean clickOnSwitchToNight(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToNightButton");
	}
	
	public boolean clickOnAttention(){
		if(isEntryDelayScreenDisplayed()){
			System.out.println("In Entry delay screen");
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionButton");
	}
	
	public boolean isWaitingToCloseScreenDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WaitingToCloseDoor") ;
	}
}
