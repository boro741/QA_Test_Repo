package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class DRScreens extends MobileScreens {

	private static final String screenName = "DR";
	public static final String VerifyDRMessageContent = null;

	public DRScreens(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isSavingEventTitleDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SavingEventTitle") ;
				
	}
	public String VerifyDRMessageContent()
	{
		return MobileUtils.getMobElement(objectDefinition, testCase, "Message").getText();
				
	}
	
	public boolean ClickOnOkPopup()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OK");
				
	}
	public boolean isSavingEventCancelTitleDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelSavingsEvent");
				
	}
	public String VerifyDRMessageCancelContent()
	{
		return MobileUtils.getMobElement(objectDefinition, testCase, "CancelMessage").getText();
				
	}
	public boolean ClickOnCancelYesPopup()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelYes");
				
	}
	public boolean ClickOnCancelNoPopup()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelNo");
				
	}
	public boolean isSavingEventCancelByUserTitleDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelSavingsEventByUser");
				
	}
	
}
