package com.honeywell.screens;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class AddNewDeviceScreen extends MobileScreens {

	private static final String screenName = "AddNewDevice";

	public AddNewDeviceScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean clickOnZwaveFromAddNewDevice() {
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList", 3)) {
			int counter = 0;
			while (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList", 3) && counter < 8) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						LyricUtils.scrollUpAList(testCase,
								MobileUtils.getMobElement(testCase, "ID", "fragment_add_new_device_list"));
						// LyricUtils.scrollToElementUsingExactAttributeValue();
					} else {
						LyricUtils.scrollUpAList(testCase,
								MobileUtils.getMobElement(objectDefinition, testCase, "DevicesList"));
						// LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "Name", "Z-Wave
						// Device");
					}
					// LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
				} catch (Exception e) {
					e.printStackTrace();
				}
				counter++;
			}
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveList");
	}

	public boolean isFetchingDevicesListProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FetchingDevicesLoadingSpinnerMsg", 3);
	}

	public boolean isAddNewDeviceHeaderDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader", lookFor);
	}

	public boolean isSelectADeviceToInstallHeaderInAddNewDeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"SelectADeviceToInstallHeaderInAddNewDeviceScreen");
	}

	public boolean isCancelButtonInAddNewDeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
	}

	public boolean clickOnCancelButtonOfAddDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean isPageTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PageTitle", timeOut);
	}

	public boolean isSearchVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Search", timeOut);
	}
	
	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isLogoutVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Logout", timeOut);
	}

	public boolean clickOnLogoutButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Logout");
	}

	public boolean isFooterTextInAddNewDeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FooterTextInAddNewDeviceScreen");
	}

	public String getFooterTextDisplayedInAddNewDeviceScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FooterTextInAddNewDeviceScreen");
	}

	public boolean isChangeCountryLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeCountryLink");
	}

	public boolean clickOnChangeCountryLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ChangeCountryLink");
	}

	public boolean isConfirmYourCountryScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmYourCountryScreenTitle");
	}

	public boolean isCurrentCountryButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CurrentCountryButton");
	}

	public String getCurrentCountryInPleaseConfirmYourCountryScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CurrentCountryButton");
	}

	public boolean clickOnCurrentCountryButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CurrentCountryButton");
	}

	public boolean isEnterCountryTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnterCountryTextField");
	}

	public boolean isNewAgreementScreenAfterSelectingACountryVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewAgreementScreenAfterSelectingACountry");
	}

	public boolean isYouHaveSelectedACountryLabelInNewAgreementScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YouHaveSelectedACountryLabelInNewAgreementScreen");
	}

	public boolean isPrivacyPolicyAndEULAInNewAgreementScreenVisible(String countryName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/fragment_new_eula_link_container' and @text='PRIVACY POLICY & EULA : "
							+ countryName + "']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='PRIVACY POLICY & EULA: " + countryName + "']", testCase);
		}
	}

	public boolean isCancelButtonInNewAgreementScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInNewAgreementScreen");
	}

	public boolean clickOnCancelButtonInNewAgreementScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInNewAgreementScreen");
	}

	public boolean isAcceptButtonInNewAgreementScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcceptButtonInNewAgreementScreen");
	}

	public boolean clickOnAcceptButtonInNewAgreementScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AcceptButtonInNewAgreementScreen");
	}

	public boolean enterCountryNameInCountryTextFieldAndAcceptNewAgreement(TestCaseInputs inputs,
			String countryNameInput) {
		boolean flag = true;
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "EnterCountryTextField", countryNameInput);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			touchAction.tap((dimensions.width - 100), (dimensions.height - 100)).perform();
		}
		if (flag && MobileUtils.isMobElementExists(objectDefinition, testCase,
				"CountryListInConfirmYourCountryScreen")) {
			Keyword.ReportStep_Pass(testCase, "List of Countries are present for the entered search text.");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//android.widget.Button[@resource-id='com.honeywell.android.lyric:id/list_item_country_text' and @text='"
								+ countryNameInput + "']",
						testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Country is displayed in the list of countries for the entered search text. Click on it.");
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//android.widget.Button[@resource-id='com.honeywell.android.lyric:id/list_item_country_text' and @text='"
									+ countryNameInput + "']");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected Country is not displayed in the list of countries for the entered search text.");
				}
			} else {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeStaticText[@name='" + countryNameInput + "']", testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Country is displayed in the list of countries for the entered search text. Click on it.");
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeStaticText[@name='" + countryNameInput + "']");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected Country is not displayed in the list of countries for the entered search text.");
				}
			}
			// Verify if New Agreement Screen is displayed after selecting a country
			if (flag && this.isNewAgreementScreenAfterSelectingACountryVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"New Agreement screen is displayed after selecting country from the list of countries.");
				if (flag && this.isYouHaveSelectedACountryLabelInNewAgreementScreenVisible()
						&& this.isPrivacyPolicyAndEULAInNewAgreementScreenVisible(countryNameInput)
						&& this.isCancelButtonInNewAgreementScreenVisible()
						&& this.isAcceptButtonInNewAgreementScreenVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"Successfully identified mobile elements displayed in New Agreement Screen. Click on Accept button in New Agreement screen.");
					flag &= this.clickOnAcceptButtonInNewAgreementScreen();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to identify mobile elements displayed in New Agreement Screen.");
				}
			} else {
				Keyword.ReportStep_Pass(testCase,
						"New Agreement screen is not displayed after selecting country from the list of countries.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"List of Countries are not present for the entered search text.");
		}
		return flag;
	}

	public boolean enterCountryNameInCountryTextField(TestCaseInputs inputs, String countryNameInput) {
		boolean flag = true;
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		inputs.setInputValue("NEW_COUNTRY_ENTERED_IN_PLEASE_CONFIRM_YOUR_COUNT_SCREEN", countryNameInput);
		flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "EnterCountryTextField", countryNameInput);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			touchAction.tap((dimensions.width - 100), (dimensions.height - 100)).perform();
		}
		if (flag && MobileUtils.isMobElementExists(objectDefinition, testCase,
				"CountryListInConfirmYourCountryScreen")) {
			Keyword.ReportStep_Pass(testCase, "List of Countries are present for the entered search text.");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//android.widget.Button[@resource-id='com.honeywell.android.lyric:id/list_item_country_text' and @text='"
								+ countryNameInput + "']",
						testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Country is displayed in the list of countries for the entered search text. Click on it.");
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//android.widget.Button[@resource-id='com.honeywell.android.lyric:id/list_item_country_text' and @text='"
									+ countryNameInput + "']");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected Country is not displayed in the list of countries for the entered search text.");
				}
			} else {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeStaticText[@name='" + countryNameInput + "']", testCase)) {
					Keyword.ReportStep_Pass(testCase,
							"Expected Country is displayed in the list of countries for the entered search text. Click on it.");
					MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeStaticText[@name='" + countryNameInput + "']");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected Country is not displayed in the list of countries for the entered search text.");
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"List of Countries are not present for the entered search text.");
		}
		return flag;
	}

	public boolean verifyDefaultCountryDisplayedInConfirmYourCountryScreen(TestCaseInputs inputs,
			String defaultCountryName) {
		boolean flag = true;
		if (this.getCurrentCountryInPleaseConfirmYourCountryScreen().equalsIgnoreCase(defaultCountryName)) {
			Keyword.ReportStep_Pass(testCase,
					"Default country displayed in Please confirm your country screen is: " + defaultCountryName);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Default country displayed in Please confirm your country screen is: "
							+ this.getCurrentCountryInPleaseConfirmYourCountryScreen());
		}
		return flag;
	}

	public boolean verifyNewAgreementScreen(TestCaseInputs inputs, String countryName) {
		boolean flag = true;
		// Verify if New Agreement Screen is displayed after selecting a country
		if (flag && this.isNewAgreementScreenAfterSelectingACountryVisible()) {
			Keyword.ReportStep_Pass(testCase,
					"New Agreement screen is displayed after selecting country from the list of countries.");
			if (flag && this.isYouHaveSelectedACountryLabelInNewAgreementScreenVisible()
					&& this.isPrivacyPolicyAndEULAInNewAgreementScreenVisible(countryName)
					&& this.isCancelButtonInNewAgreementScreenVisible()
					&& this.isAcceptButtonInNewAgreementScreenVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Successfully identified mobile elements displayed in New Agreement Screen. Click on Accept button in New Agreement screen.");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to identify mobile elements displayed in New Agreement Screen.");
			}
		} else {
			Keyword.ReportStep_Pass(testCase,
					"New Agreement screen is not displayed after selecting country from the list of countries.");
		}
		return flag;
	}

	public boolean isShowingDevicesForCountryLabelInAddNewDeviceScreenVisible(String countryName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/footer_text' and @text='Showing devices for "
							+ countryName + "']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='Showing devices for " + countryName + "']", testCase);
		}
	}

	public String getFirstDeviceFromTheListInAddNewDeviceScreen() {
		String firstDeviceFromTheListInAddNewDeviceScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstDeviceFromTheListInAddNewDeviceScreen")) {
			firstDeviceFromTheListInAddNewDeviceScreen = MobileUtils.getFieldValue(objectDefinition, testCase,
					"FirstDeviceFromTheListInAddNewDeviceScreen");
		}
		return firstDeviceFromTheListInAddNewDeviceScreen;
	}

	public MobileElement getFirstDeviceListWebElement(String firstDeviceInTheListInAddNewDeviceScreen) {
		// return MobileUtils.getMobElement(objectDefinition, testCase, "DevicesList");
		System.out.println(
				"*******firstDeviceInTheListInAddNewDeviceScreen:  " + firstDeviceInTheListInAddNewDeviceScreen);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(testCase, "TEXT", firstDeviceInTheListInAddNewDeviceScreen);
		} else {
			return MobileUtils.getMobElement(testCase, "NAME", firstDeviceInTheListInAddNewDeviceScreen);
		}
	}

	public boolean isCloseButtonInAddNewDeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInAddNewDeviceScreen");
	}

	public boolean clickOnCloseButtonInAddNewDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInAddNewDeviceScreen");
	}

	public boolean isExitHoneywellHomePopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExitHoneywellHomePopupTitle");
	}

	public boolean isSignOutButtonInExitHoneywellHomePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignOutButtonInExitHoneywellHomePopup");
	}

	public boolean clickOnSignOutButtonInExitHoneywellHomePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SignOutButtonInExitHoneywellHomePopup");
	}

	public boolean isDeleteAccountButtonInExitHoneywellHomePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"DeleteAccountButtonInExitHoneywellHomePopup");
	}

	public boolean clickOnDeleteAccountButtonInExitHoneywellHomePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteAccountButtonInExitHoneywellHomePopup");
	}

	public boolean isCancelButtonInExitHoneywellHomePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInExitHoneywellHomePopup");
	}

	public boolean clickOnCancelButtonInExitHoneywellHomePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInExitHoneywellHomePopup");
	}

	public boolean isSorryToSeeYouGoPopupTitleVisbile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SorryToSeeYouGoPopupTitle");
	}

	public boolean isSorryToSeeYouGoPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SorryToSeeYouGoPopupMsg");
	}

	public boolean isNoButtonInSorryToSeeYouGoPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInSorryToSeeYouGoPopup");
	}

	public boolean clickOnNoButtonInSorryToSeeYouGoPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInSorryToSeeYouGoPopup");
	}

	public boolean isYesButtonInSorryToSeeYouGoPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInSorryToSeeYouGoPopup");
	}

	public boolean clickOnYesButtonInSorryToSeeYouGoPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInSorryToSeeYouGoPopup");
	}
	
	public boolean isPrivacyPolicyAndEULALinkInNewAgreementScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"PrivacyPolicyAndEULALinkInNewAgreementScreen");
	}

	public boolean clickOnPrivacyPolicyAndEULALinkInNewAgreementScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrivacyPolicyAndEULALinkInNewAgreementScreen");
	}
	
	public boolean isPrivacyPolicyAndEULAScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyPolicyAndEULAScreenTitle");
	}
	
	public boolean isPrivacyPolicyAndEULAScreenDataVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyPolicyAndEULAScreenData");
	}
}