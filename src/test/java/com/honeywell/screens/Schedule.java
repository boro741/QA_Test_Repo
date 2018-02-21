package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class Schedule extends MobileScreens{

	private static final String screenName = "ScheduleScreen";
	
	public Schedule(TestCases testCase) {
		super(testCase,screenName);
		//osPopUps = new OSPopUps(testCase);
	}
	
	public boolean clickOnCloseButton(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CrossButton");
	}
	public boolean isCloseButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CrossButton",timeOut);
	}
}
