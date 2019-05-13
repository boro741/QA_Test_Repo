package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class ActivateAccountScreen extends MobileScreens {

	private static final String screenName = "ActivateAccountScreen";

	public ActivateAccountScreen(TestCases testCase) {
		super(testCase, screenName);
		// osPopUps = new OSPopUps(testCase);
	}

	public boolean isActivateAccountTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateAccountTitle");
	}

	public boolean isActivateAccountAlmostDoneLabelDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlmostDoneLabel");
	}

	public boolean isActivateAccountGoToMailButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GoToMailButton");
	}

	public boolean isActivateAccountNewEmailDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewEmail");
	}

	public boolean isActivateAccountResendEmailLinkDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ResendEmailLink");
	}

	public boolean isActivateAccountBackButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean isActivateAccountClickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}
}
