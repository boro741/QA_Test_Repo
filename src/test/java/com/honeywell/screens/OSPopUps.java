package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class OSPopUps extends MobileScreens {

	private static final String screenName = "OSPopUps";

	public OSPopUps(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAllowButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton",3,false);
	}
	
	public boolean isAllowButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton",timeOut,false);
	}
	public boolean isAlwaysAllowButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlwaysAllowButton",timeOut,false);
	}
	
	public boolean clickOnAllowButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowButton");
	}
	public boolean clickOnAlwaysAllowButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlwaysAllowButton");
	}
	
	public boolean isOkButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton",3,false);
	}
	
	public boolean isOkButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton",timeOut,false);
	}
	
	public boolean clickOnOkButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}
	public boolean isCancelButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton",3,false);
	}
	
	public boolean isCancelButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton",timeOut,false);
	}
	
	public boolean clickOnCancelButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
	
	public boolean isNotNowButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButton",3,false);
	}
	
	public boolean isNotNowButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButton",timeOut,false);
	}
	public boolean isGotitButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotIT",timeOut,false);
	}
	
	public boolean clickOnGotitButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotIT");
	}
	public boolean clickOnNotNowButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NotNowButton");
	}
	
	public boolean isCloseButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButton",3,false);
	}
	
	public boolean isCloseButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButton",timeOut,false);
	}
	
	public boolean clickOnCloseButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButton");
	}
}
