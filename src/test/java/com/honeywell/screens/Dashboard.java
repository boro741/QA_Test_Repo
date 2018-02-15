package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class Dashboard extends MobileScreens {

	private static final String screenName = "Dashboard";

	public Dashboard(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean isWeatherIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon",3,false);
	}

	public boolean isWeatherIconVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon",timeOut,false);
	}
}
