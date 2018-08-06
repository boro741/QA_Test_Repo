package com.honeywell.screens;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.CoachMarkUtils;

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

	
}
