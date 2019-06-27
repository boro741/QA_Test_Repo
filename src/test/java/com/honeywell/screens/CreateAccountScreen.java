package com.honeywell.screens;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class CreateAccountScreen extends MobileScreens {

	private static final String screenName = "CreateAccountScreen";

	public CreateAccountScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCreateAccountButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateAccountButton");
	}

	public boolean clickOnCreateAccountButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateAccountButton");
	}

	public boolean createAccountTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateAccountScreenTitle");
	}

	public boolean isCreateAccountFirstNameFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameField");
	}

	public boolean enterFirstNameTxtInCreateAccountScreen(TestCaseInputs inputs, String firstNameInput) {
		MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameField");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("fragment_register_firstname")).clear();
		}
		return MobileUtils.setValueToElement(objectDefinition, testCase, "FirstNameField", firstNameInput);
	}

	public String getValueDisplayedInFirstNameTxtField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FirstNameField");
	}

	public boolean isCreateAccountLastNameFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameField");
	}

	public boolean enterEmailTxtInCreateAccountScreen(TestCaseInputs inputs, String emailInput) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "EmailField", emailInput);
	}

	public boolean enterPasswordTxtInCreateAccountScreen(TestCaseInputs inputs, String passwordInput) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "PasswordField", passwordInput);
	}

	public boolean enterVerifyPasswordTxtInCreateAccountScreen(TestCaseInputs inputs, String verifyPasswordInput) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "VerifyPasswordField", verifyPasswordInput);
	}

	public boolean enterLastNameTxtInCreateAccountScreen(TestCaseInputs inputs, String lastNameInput) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "LastNameField", lastNameInput);
	}

	public String getValueDisplayedInLastNameTxtField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LastNameField");
	}

	public String getValueDisplayedInCountrySelection() {
		return MobileUtils.getAttribute(testCase, "XPATH", "//android.widget.Button[@text='United States']", "text");
	}

	public boolean isCreateAccountEmailFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailField");
	}

	public boolean isCreateAccountPasswordFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PasswordField");
	}

	public boolean isCreateAccountVerifyPasswordFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VerifyPasswordField");
	}

	public boolean isCreateAccountPasswordMustHaveTextDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PasswordMustHaveText");
	}

	public boolean isCreateAccountCountrySelectionDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CountrySelection");
	}

	public boolean isCreateAccountByTappingCreateBelowTextDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TapCreateBelowText");
	}

	public boolean isCreateAccountPrivacyStatementLinkDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyStatementLink");
	}

	public boolean isCreateAccountEULALinkDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EULALink");
	}

	public boolean isCreateAccountRegisterButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateAccountRegisterButton");
	}

	public boolean isCreateAccountMarketingCommunicationsSignUpTextDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MarketingCommunicationSignUpText");
	}

	public boolean isCreateAccountSignMeUpForExclusiveUpdatesTextDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignMeUpForExclusiveUpdatesText");
	}

	public boolean isCreateAccountSigningUpConsentLabelDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SigningUpConsentLabelText");
	}

	public boolean isCreateAccountSignMeUpToggleButtonEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SignMeUpToggleButton")
					.getAttribute("clickable").equalsIgnoreCase("false");
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SignMeUpToggleButton").getAttribute("value")
					.equalsIgnoreCase("1");
		}

	}

	public boolean isCreateAccountSignMeUpToggleButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignMeUpToggleButton");
	}

	public boolean isCreateAccountClickOnSignMeUpToggleButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SignMeUpToggleButton");
	}

	public boolean isCreateAccountClickOnBack() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.pressBackButton(testCase, "Clicked on Back button");
		}else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "IOSBackButton");
		}
		
	}

	public boolean isHoneywellHomeLogoDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HoneywellHomeLogo");
	}

	public boolean isLoginButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoginButton");
	}

	public boolean clickOnCreateAccountRegisterButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateAccountRegisterButton");
	}

	public boolean isCreateAccountFirstNameFieldErrorValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameFieldErrorValidation");
	}

	public boolean isCreateAccountLastNameFieldErrorValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameFieldErrorValidation");
	}

	public boolean isCreateAccountEmailFieldErrorValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailFieldErrorValidation");
	}

	public boolean isCreateAccountPasswordFieldErrorValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PasswordFieldErrorValidation");
	}

	public boolean isCreateAccountAlreadyRegisteredEmailValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlreadyRegisteredEmailValidation");
	}

	public boolean isCreateAccountVerifyPasswordDontMatchValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VerifyPasswordDontMatchValidation");
	}

	public boolean isCreateAccountClickOnPrivacyStatementLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrivacyStatementLink");
	}

	public boolean isCreateAccountClickOnEULALink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EULALink");
	}

	public boolean isCreateAccountLoginButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoginButton");
	}

	public boolean clickOnLoginButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LoginButton");
	}

	public String isCreateAccountGetSelectedCountry() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CountrySelection").getAttribute("text");
		} else {
			// ios
			return MobileUtils.getMobElement(objectDefinition, testCase, "CountrySelection").getAttribute("value");
		}
	}

	public boolean isCreateAccountClickOnCountrySelection() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CountrySelection");
	}

	public boolean isCreateAccountClickOnBackButtonOnIOS() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "IOSBackButton");
	}

	public boolean isEmailAddressAlreadyRegisteredPopupDisplayed() {
		// return MobileUtils.isMobElementExists(objectDefinition, testCase,
		// "EmailAddressAlreadyRegisteredPopup", timeOut);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			/*return testCase.getMobileDriver().findElement(By.xpath(
					"//android.widget.TextView[@text='This email address is already registered. Do you want to log in?']"))
					.isEnabled();*/
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailAddressAlreadyRegisteredPopup");
		} else {
			// ios
			return testCase.getMobileDriver()
					.findElement(By.xpath("//XCUIElementTypeStaticText[contains(@name,'This email address is already registered')]"))
					.isEnabled();
		}

	}

	public boolean isInvalidPasswordFormatValidationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvalidPasswordFormatValidation");
	}

}