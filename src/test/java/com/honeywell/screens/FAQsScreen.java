package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInFAQsScreen");
	}

	public boolean cliciOnBackButtonInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInFAQsScreen");
	}

	public boolean isGeneralOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralOptionInFAQsScreen");
	}

	public boolean clickOnGeneralOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralOptionInFAQsScreen");
	}

	public boolean isThermostatOptionInFAQsScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatOptionInFAQsScreen");
	}

	public boolean clickOnThermostatOptionInFAQsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatOptionInFAQsScreen");
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

	public boolean isGeneralScreenHeaderTitleVisible(int timeOut) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//*[(@text='General') or (@text='GENERAL')]", testCase,
					timeOut);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralScreenHeaderTitle", timeOut);
		}
	}

	public boolean isBackButtonInGeneralScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInGeneralScreen");
	}

	public boolean isThermostatScreenHeaderTitleVisible(int timeOut) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//*[(@text='Thermostat') or (@text='THERMOSTAT')]",
					testCase, timeOut);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatScreenHeaderTitle", timeOut);
		}
	}

	public boolean isBackButtonInThermostatScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInThermostatScreen");
	}

	public boolean isWaterLeakDetectorScreenHeaderTitleVisible(int timeOut) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//*[(@text='Water leak detector') or (@text='WATER LEAK DETECTOR')]", testCase, timeOut);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "WaterLeakDetectorScreenHeaderTitle",
					timeOut);
		}
	}

	public boolean isBackButtonInWaterLeakDetectorScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInWaterLeakDetectorScreen");
	}

	public boolean isCameraScreenHeaderTitleVisible(int timeOut) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//*[(@text='Camera') or (@text='CAMERA')]", testCase,
					timeOut);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraScreenHeaderTitle", timeOut);
		}
	}

	public boolean isBackButtonInCameraScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInCameraScreen");
	}

	public boolean isQuestionListInAScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QuestionListInAScreen");
	}

	public boolean isFirstQuestionDisplayedInTheScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstQuestionDisplayedInTheScreen");
	}

	public String getFirstQuestionDisplayedInTheScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FirstQuestionDisplayedInTheScreen");
	}

	public boolean clickOnFirstQuestionDisplayedInTheScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FirstQuestionDisplayedInTheScreen");
	}

	public boolean isQuestionTitleInQuestionScreenVisible(int timeOut) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH", "//android.webkit.WebView/android.view.View[1]", testCase,
					timeOut);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "QuestionTitleInQuestionScreen", timeOut);
		}
	}

	public String getQuestionTitleInQuestionScreen() {
		return MobileUtils.getFieldValue(testCase, "XPATH", "//android.webkit.WebView/android.view.View[1]");
	}

	public boolean isBackButtonInQuestionScreenVisible() {
		boolean flag = true;
		List<WebElement> listOfButtonsDisplayedInQuestionScreenIniOS = new ArrayList<WebElement>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInQuestionScreen");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInQuestionScreen")) {
				listOfButtonsDisplayedInQuestionScreenIniOS = MobileUtils.getMobElements(objectDefinition, testCase,
						"BackButtonInQuestionScreen");
				for (WebElement ele : listOfButtonsDisplayedInQuestionScreenIniOS) {
					if (ele.getAttribute("name").equalsIgnoreCase("General")
							|| ele.getAttribute("name").equalsIgnoreCase("Thermostat")
							|| ele.getAttribute("name").equalsIgnoreCase("Water leak detector")
							|| ele.getAttribute("name").equalsIgnoreCase("Camera")) {
						return flag;
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	public boolean clickOnBackButtonInQuestionScreen() {
		boolean flag = true;
		List<WebElement> listOfButtonsDisplayedInQuestionScreenIniOS = new ArrayList<WebElement>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInQuestionScreen");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInQuestionScreen")) {
				listOfButtonsDisplayedInQuestionScreenIniOS = MobileUtils.getMobElements(objectDefinition, testCase,
						"BackButtonInQuestionScreen");
				for (WebElement ele : listOfButtonsDisplayedInQuestionScreenIniOS) {
					if (ele.getAttribute("name").equalsIgnoreCase("General")
							|| ele.getAttribute("name").equalsIgnoreCase("Thermostat")
							|| ele.getAttribute("name").equalsIgnoreCase("Water leak detector")
							|| ele.getAttribute("name").equalsIgnoreCase("Camera")) {
						ele.click();
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
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

	public boolean enterTextInHelpTextField(String inputText) {
		boolean flag = true;
		flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "HelpSearchTextFieldInFAQsScreen");
		flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "HelpSearchTextFieldInFAQsScreen", inputText);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().navigate().back();
		} else {
			MobileUtils.clickOnElement(objectDefinition, testCase, "SearchButtonInHelpTextKeyboardForiOS");
		}
		return flag;
	}
	
	public boolean isSearchResultsDisplayedInFAQsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SearchResultsDisplayedInFAQsScreen");
	}
	
	public boolean isFirstQuestionInSearchResultsDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstQuestionInSearchResultsDisplayed");
	}
	
	public boolean isNoFAQsFoundLabelInSearchResultsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoFAQsFoundLabelInSearchResults");
	}
	
	public String getNoFAQsFoundLabelInSearchResults() {
		System.out.println("############" + MobileUtils.getFieldValue(objectDefinition, testCase, "NoFAQsFoundLabelInSearchResults"));
		return MobileUtils.getFieldValue(objectDefinition, testCase, "NoFAQsFoundLabelInSearchResults");
	}
}