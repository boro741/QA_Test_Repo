package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class PrivacyPolicyEULA extends MobileScreens {

	private static final String screenName = "PrivacyPolicyEULAScreen";

	public PrivacyPolicyEULA(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isPrivacyPolicyEULATitleDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyPolicyEULATitle");
	}
	
	public boolean isPrivacyPolicyEULAClickOnBack(){
		return MobileUtils.pressBackButton(testCase, "Clicked on Back button");
	}
	
	public boolean isPrivacyPolicyEULAClickOnIOSBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "IOSBackButton");
	}
}