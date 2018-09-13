package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class WLDUpdateFrequency extends MobileScreens {

	private static final String screenName = "WLD_UpdateFrequency";
	public WLDUpdateFrequency(TestCases testCase) {
		super(testCase, screenName);
	}
	public boolean isUpdateFrequencyHeaderVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateFrequencyHeader");
	}
	public String displayUpdateFrequencyHeaderText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "UpdateFrequencyHeader");
	}

	public boolean isHowOftenQuestonTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateFrequencyHeader");
	}
	public String displayHowOftenQuestonTextText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "UpdateFrequencyHeader");
	}

	public boolean isAlwaysNotifiedTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateFrequencyHeader");
	}
	public String displayAlwaysNotifiedText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "UpdateFrequencyHeader");
	}

	public boolean isDailyRadioButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Daily");
	}
	public boolean clickOnDailyRadioButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Daily");
	}

	public boolean isTwiceDailyRadioButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TwiceDaily");
	}
	public boolean clickOnTwiceDailyRadioButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TwiceDaily");
	}

	public boolean isThriceDailyRadioButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThriceDaily");
	}
	public boolean clickOnThriceDailyRadioButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThriceDaily");
	}	
}