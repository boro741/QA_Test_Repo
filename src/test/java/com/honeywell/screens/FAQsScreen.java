package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class FAQsScreen extends MobileScreens {

	private static final String screenName = "FAQsScreen";

	public FAQsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isFAQsScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FAQsScreenTitle");
	}

	public boolean isHelpSearchTextFieldInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HelpSearchTextFieldInFAQsScreen");
	}

	public boolean isBackButtonInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean cliciOnBackButtonInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isGeneralOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralOptionInFAQsScreen");
	}
	
	public boolean clickOnGeneralOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralOptionInFAQsScreen");
	}

	public boolean isThermotatOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermotatOptionInFAQsScreen");
	}
	
	public boolean clickOnThermotatOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermotatOptionInFAQsScreen");
	}

	public boolean isWaterLeakDetectorOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WaterLeakDetectorOptionInFAQsScreen");
	}
	
	public boolean clickOnWaterLeakDetectorOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WaterLeakDetectorOptionInFAQsScreen");
	}

	public boolean isCameraOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOptionInFAQsScreen");
	}
	
	public boolean clickOnCameraOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOptionInFAQsScreen");
	}

	public boolean isGeneralScreenHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralScreenHeaderTitle");
	}

	public boolean isGeneralScreenQuestionListVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralScreenQuestionList");
	}

	public boolean isFirstQuestionDisplayedInGeneralScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstQuestionDisplayedInGeneralScreen");
	}

	public String getFirstQuestionDisplayedInGeneralScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FirstQuestionDisplayedInGeneralScreen");
	}

	public boolean clickOnFirstQuestionDisplayedInGeneralScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FirstQuestionDisplayedInGeneralScreen");
	}

	public boolean isQuestionTitleInQuestionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QuestionTitleInQuestionScreen");
	}
	
	public String getQuestionTitleInQuestionScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "QuestionTitleInQuestionScreen");
	}

	public boolean isAnswerToTheQuestionAskedInQuestionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AnswerToTheQuestionAskedInQuestionScreen");
	}

	public boolean wasThisHelpfulTextInQuestionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WasThisHelpfulTextInQuestionScreen");
	}

	public boolean isYESButtonInWasThisHelpfulTextInQuestionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YESButtonInWasThisHelpfulTextInQuestionScreen");
	}

	public boolean clickOnYESButtonInWasThisHelpfulTextInQuestionScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YESButtonInWasThisHelpfulTextInQuestionScreen");
	}

	public boolean isNOButtonInWasThisHelpfulTextInQuestionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"NOButtonInWasThisHelpfulTextInQuestionScreen");
	}

	public boolean clickOnNOButtonInWasThisHelpfulTextInQuestionScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NOButtonInWasThisHelpfulTextInQuestionScreen");
	}
	
	public boolean isQuestionFooterMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QuestionFooterMessage");
	}
	
	public String getQuestionFooterMessage() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "QuestionFooterMessage");
	}
}