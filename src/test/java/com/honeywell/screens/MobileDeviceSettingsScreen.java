package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class MobileDeviceSettingsScreen extends MobileScreens {

	private static final String screenName = "MobileDeviceLocationSettings";

	public MobileDeviceSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isMobileDeviceLocationHeaderDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MobileDeviceLocationHeader");
	}
	
	public boolean isMobileDeviceLocationEnabled() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableMobileDeviceLocation");
	}
	
	public boolean isMobileDeviceLocationDisabled() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DisableMobileDeviceLocation");
	}
	
	public boolean clickOnMobileDeviceLocationToEnable() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DisableMobileDeviceLocation");
	}
	
	public boolean clickOnMobileDeviceLocationToDisable() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EnableMobileDeviceLocation");
	}
	
	public boolean isAgreeLocationAccracyDisplayed(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AgreeLocationAccuracy", timeOut);
	}
	
	public boolean clickToAgreeLocationAccuracy() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AgreeLocationAccuracy");
	}
	
	public boolean clickOnIOSMobileDeviceSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MobileDeviceSettings");
	}

}