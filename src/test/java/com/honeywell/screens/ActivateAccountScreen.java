package com.honeywell.screens;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebElement;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

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
	
	public boolean isActivateAccountCloseButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButton");
	}
	
	public boolean isActivateAccountClickOnCloseButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButton");
	}
	
	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}
}
