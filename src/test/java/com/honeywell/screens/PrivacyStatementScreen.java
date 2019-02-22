package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class PrivacyStatementScreen extends MobileScreens {

	private static final String screenName = "PrivacyStatementScreen";

	public PrivacyStatementScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isPrivacyStatementScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyStatementScreenTitle");
	}

	public boolean isPrivacyStatementScreenContentVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyStatementScreenContent");
	}

	public boolean isBackButtonInPrivacyStatmentScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInPrivacyStatmentScreen");
	}

	public boolean clickOnBackButtonInPrivacyStatmentScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInPrivacyStatmentScreen");
	}
}