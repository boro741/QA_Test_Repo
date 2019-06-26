package com.honeywell.screens;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class AboutTheAppScreen extends MobileScreens {

	private static final String screenName = "AboutTheAppScreen";

	public AboutTheAppScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isAboutTheAppScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboutTheAppScreenTitle");
	}

	public boolean isRateTheAppOptionInAboutTheAppVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RateTheAppOptionInAboutTheApp");
	}

	public boolean clickOnRateTheAppOptionInAboutTheApp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RateTheAppOptionInAboutTheApp");
	}

	public boolean isGetHelpOptionInAboutTheAppVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetHelpOptionInAboutTheApp");
	}

	public boolean isGetHelpOptionInAboutTheAppArrowVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetHelpOptionInAboutTheAppArrow");
	}

	public boolean clickOnGetHelpOptionInAboutTheApp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetHelpOptionInAboutTheApp");
	}

	public boolean isPrivacyPolicyAndEULAOptionInAboutTheAppVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyPolicyAndEULAOptionInAboutTheApp");
	}

	public boolean isPrivacyPolicyAndEULAOptionInAboutTheAppArrowVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"PrivacyPolicyAndEULAOptionInAboutTheAppArrow");
	}

	public boolean clickOnPrivacyPolicyAndEULAOptionInAboutTheApp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrivacyPolicyAndEULAOptionInAboutTheApp");
	}

	public boolean isAcknowledgementsOptionInAboutTheAppVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcknowledgementsOptionInAboutTheApp");
	}

	public boolean isAcknowledgementsOptionInAboutTheAppArrowVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcknowledgementsOptionInAboutTheAppArrow");
	}

	public boolean clickOnAcknowledgementsOptionInAboutTheApp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AcknowledgementsOptionInAboutTheApp");
	}

	public boolean isVersionOptionInAboutTheAppVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VersionOptionInAboutTheApp");
	}

	public boolean clickOnVersionOptionInAboutTheApp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VersionOptionInAboutTheApp");
	}

	public boolean isPrivacyPolicyAndEULAScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyPolicyAndEULAScreenTitle", timeOut);
	}

	public boolean isDoneButtonInPrivacyPolicyAndEULAScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButtonInPrivacyPolicyAndEULAScreen");
	}

	public boolean clickOnDoneButtonInPrivacyPolicyAndEULAScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInPrivacyPolicyAndEULAScreen");
	}

	public boolean isAcknowledgementsScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcknowledgementsScreenTitle");
	}

	public boolean isDoneButtonInAcknowledgementsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButtonInAcknowledgementsScreen");
	}

	public boolean clickOnDoneButtonInAcknowledgementsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInAcknowledgementsScreen");
	}

	public boolean isGetHelpScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetHelpScreenTitle", timeOut);
	}

	public boolean isNavBackToHoneywellButtonInGetHelpScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NavBackToHoneywellButtonInGetHelpScreen");
	}

	public boolean clickOnNavBackToHoneywellButtonInGetHelpScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NavBackToHoneywellButtonInGetHelpScreen");
	}

	public String getHelpScreenURL() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "GetHelpScreenTitle");
	}

	public boolean isWhatDoYouThinkOfHoneywellHomeAppPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isCloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"CloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnCloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"CloseButtonInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isRateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"RateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnRateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"RateOneCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isRateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"RateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnRateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"RateTwoCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isRateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"RateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnRateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"RateThreeCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isRateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"RateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnRateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"RateFourCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isRateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopupVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"RateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean clickOnRateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"RateFiveCheckboxInWhatDoYouThinkOfHoneywellHomeAppPopup");
	}

	public boolean isAppFeedbackScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppFeedbackScreenTitle");
	}

	public boolean isCloseButtonInAppFeedbackScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInAppFeedbackScreen");
	}

	public boolean clickOnCloseButtonInAppFeedbackScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInAppFeedbackScreen");
	}

	public boolean isAppFeedbackThankYouNoteVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppFeedbackThankYouNote");
	}

	public boolean isAppFeedbackTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppFeedbackTextField");
	}

	public boolean enterTextInAppFeedbackTextField(String inputText) {
		boolean flag = true;
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AppFeedbackTextField");
		testCase.getMobileDriver()
				.findElement(By.xpath(
						"//android.widget.EditText[@resource-id='com.honeywell.android.lyric:id/feedback_text']"))
				.clear();
		flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "AppFeedbackTextField", inputText);
		testCase.getMobileDriver().navigate().back();
		return flag;
	}

	public String getEnteredTextInAppFeedbackTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AppFeedbackTextField");
	}

	public boolean isAnonymousToggleButtonButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AnonymousToggleButton");
	}

	public boolean isAnonymousToggleButtonDisabledByDefault() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AnonymousToggleButton").getAttribute("selected")
				.equalsIgnoreCase("false");
	}

	public boolean isAnonymousToggleButtonEnabled() {
		if (MobileUtils.getFieldValue(objectDefinition, testCase, "AnonymousToggleButton").equalsIgnoreCase("ON")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean selectAnonymousToggleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AnonymousToggleButton");
	}

	public boolean isAnonymousSubTitleTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AnonymousSubTitleText");
	}

	public boolean isSendFeedbackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SendFeedbackButton");
	}

	public boolean isSendFeedbackButtonEnabled() {
		if (MobileUtils.getMobElement(objectDefinition, testCase, "SendFeedbackButton").getAttribute("enabled")
				.equalsIgnoreCase("TRUE")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean selectSendFeedbackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SendFeedbackButton");
	}

	public boolean isThanksForYourRatingPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThanksForYourRatingPopupTitle");
	}

	public boolean isThanksForYourRatingPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThanksForYourRatingPopupMsg");
	}

	public boolean isCloseButtonInThanksForYourRatingPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInThanksForYourRatingPopup");
	}

	public boolean clickOnCloseButtonInThanksForYourRatingPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInThanksForYourRatingPopup");
	}

	public boolean isContinueButtonInThanksForYourRatingPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ContinueButtonInThanksForYourRatingPopup");
	}

	public boolean clickOnContinueButtonInThanksForYourRatingPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ContinueButtonInThanksForYourRatingPopup");
	}
	
	public boolean isGooglePlayHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GooglePlayHeaderTitle");
	}
	
	public boolean isHoneywellHomeTitleInGooglePlayStoreVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HoneywellHomeTitleInGooglePlayStore");
	}
	
	public boolean isResideoTechnolgiesTitleInGooglePlayStoreVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ResideoTechnolgiesTitleInGooglePlayStore");
	}
	
	public boolean isUninstallButtonForHoneywellHomeAppInGooglePlayStoreVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UninstallButtonForHoneywellHomeAppInGooglePlayStore");
	}
	
	public boolean isOPENButtonForHoneywellHomeAppInGooglePlayStoreVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OPENButtonForHoneywellHomeAppInGooglePlayStore");
	}
}