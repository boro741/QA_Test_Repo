package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class EndUserLicenseAgreementScreen extends MobileScreens {

	private static final String screenName = "EndUserLicenseAgreementScreen";

	public EndUserLicenseAgreementScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isEULAScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EULAScreenTitle");
	}

	public boolean isEULAScreenContentVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EULAScreenContent");
	}

	public boolean isBackButtonInEULAScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInEULAScreen");
	}

	public boolean clickOnBackButtonInEULAScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInEULAScreen");
	}
}