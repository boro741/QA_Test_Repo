package com.honeywell.screens;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class EditAccountScreen extends MobileScreens {

	private static final String screenName = "EditAccountScreen";

	public EditAccountScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isCreateAccountButtonInHoneywellHomeScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateAccountButtonInHoneywellHomeScreen",
				timeOut);
	}

	public boolean isLoginButtontInHoneywellHomeScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoginButtontInHoneywellHomeScreen", timeOut);
	}

	public boolean isEditAccountScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditAccountScreenTitleInEditAccount");
	}

	public boolean isBackButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return true;
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean clickOnBackButton() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
		} else {
			return false;
		}
	}

	public boolean isEditAccountScreenTitleInEditAccountVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditAccountScreenTitleInEditAccount");
	}

	public boolean isFirstNameLabelInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameLabelInEditAccountScreen");
	}

	public boolean isFirstNameValueInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameValueInEditAccountScreen");
	}

	public String getFirstNameValueInEditAccountScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "FirstNameValueInEditAccountScreen");
	}

	public boolean clearTextDisplayedInFirstNameTextFieldInEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "FirstNameValueInEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterFirstNameValueInEditAccountScreen(TestCaseInputs inputs, String inputFirstNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameValueInEditAccountScreen");
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "FirstNameValueInEditAccountScreen",
					inputFirstNameText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameValueInEditAccountScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='First Name']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "FirstNameValueInEditAccountScreen",
					inputFirstNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isLastNameLabelInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameLabelInEditAccountScreen");
	}

	public boolean isLastNameValueInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameValueInEditAccountScreen");
	}

	public String getLastNameValueInEditAccountScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LastNameValueInEditAccountScreen");
	}

	public boolean clearTextDisplayedInLastNameTextFieldInEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "LastNameValueInEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterLastNameValueInEditAccountScreen(TestCaseInputs inputs, String inputLastNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LastNameValueInEditAccountScreen");
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LastNameValueInEditAccountScreen",
					inputLastNameText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LastNameValueInEditAccountScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='Last Name']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LastNameValueInEditAccountScreen",
					inputLastNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean isEmailLabelInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailLabelInEditAccountScreen");
	}

	public boolean isEmailValueInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailValueInEditAccountScreen");
	}

	public String getEmailValueInEditAccountScreen() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailValueInEditAccountScreen");
	}

	public boolean isChangePasswordButtonInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangePasswordButtonInEditAccountScreen");
	}

	public boolean clickOnChangePasswordButtonInEditAccountScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ChangePasswordButtonInEditAccountScreen");
	}

	public boolean isDeleteAccountButtonInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteAccountButtonInEditAccountScreen");
	}

	public boolean clickOnDeleteAccountButtonInEditAccountScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteAccountButtonInEditAccountScreen");
	}

	public boolean isPrivacyLabelInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrivacyLabelInEditAccountScreen");
	}

	public boolean isUsePasscodeLabelInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UsePasscodeLabelInEditAccountScreen");
	}

	public boolean isUsePasscodeSwitchInEditAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UsePasscodeSwitchInEditAccountScreen");
	}

	public boolean isUsePasscodeSwitchInEditAccountScreenEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "UsePasscodeSwitchInEditAccountScreen", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "UsePasscodeSwitchInEditAccountScreen")
						.getText().equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "UsePasscodeSwitchInEditAccountScreen")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Passcode ON/OFF Alert Switch");
		}
	}

	public boolean toggleUsePasscodeSwitchInEditAccountScreen(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UsePasscodeSwitchInEditAccountScreen");
	}

	public boolean isSaveButtonInEditAccountScreenVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return testCase.getMobileDriver()
					.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate Up']")).isDisplayed();
		} else {
			return testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='Save']"))
					.isDisplayed();
		}
	}

	public boolean isSaveButtonInEditAccountScreenEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SaveButtonInEditAccountScreen")
					.getAttribute("enabled").equalsIgnoreCase("true");
		}
	}

	public boolean clickOnSaveButtonInEditAccountScreen() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver()
					.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate Up']")).click();
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='Save']")).click();
		}
		return true;
	}

	public boolean isFirstNameOrLastNameRequiredErrorPopupTitleVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase,
					"FirstNameOrLastNameRequiredErrorPopupTitle");
		}
	}

	public boolean isFirstNameIsRequiredErrorPopupMsgVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameIsRequiredErrorPopupMsg");
		}
	}

	public boolean isLastNameIsRequiredErrorPopupMsgVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameIsRequiredErrorPopupMsg");
		}
	}

	public boolean isOKButtonInFirstNameOrLastNameRequiredErrorPopupVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase,
					"OKButtonInFirstNameOrLastNameRequiredErrorPopup");
		}
	}

	public boolean clickOnOKButtonInFirstNameOrLastNameRequiredErrorPopup() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// There is no Save button in Edit Account screen. Hence returning true for this
			// method
			return true;
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase,
					"OKButtonInFirstNameOrLastNameRequiredErrorPopup");
		}
	}

	public boolean isChangePasswordScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangePasswordScreenTitle");
	}

	public boolean isOldPasswordLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OldPasswordLabel");
	}

	public boolean isOldPasswordTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OldPasswordTextField");
	}

	public boolean isCreatePasswordLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreatePasswordLabel");
	}

	public boolean isNewPasswordTextFeildVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewPasswordTextFeild");
	}

	public boolean isVerifyNewPasswordTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VerifyNewPasswordTextField");
	}

	public boolean isSaveButtonInChangePasswordScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInChangePasswordScreen");
	}

	public boolean isSaveButtonInChangePasswordScreenEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SaveButtonInChangePasswordScreen")
					.getAttribute("enabled").equalsIgnoreCase("true");
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SaveButtonInChangePasswordScreen")
					.getAttribute("enabled").equalsIgnoreCase("true");
		}
	}

	public boolean clickOnSaveButtonInChangePasswordScreen() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInChangePasswordScreen");
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeButton[@name='Save']")).click();
		}
		return true;
	}

	public boolean enterOldPasswordInChangePasswordScreen(TestCaseInputs inputs, String inputOldPasswordText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "OldPasswordTextField");
			testCase.getMobileDriver().findElement(By.id("fragment_change_password_old")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "OldPasswordTextField",
					inputOldPasswordText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Change Password Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "OldPasswordTextField");
			testCase.getMobileDriver()
					.findElement(By.xpath("(//XCUIElementTypeSecureTextField[@name='Old Password'])[1]")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "OldPasswordTextField",
					inputOldPasswordText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterNewPasswordInChangePasswordScreen(TestCaseInputs inputs, String inputNewPasswordText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "NewPasswordTextFeild");
			testCase.getMobileDriver().findElement(By.id("fragment_change_password_new")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "NewPasswordTextFeild",
					inputNewPasswordText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Change Password Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "NewPasswordTextFeild");
			testCase.getMobileDriver()
					.findElement(By.xpath("(//XCUIElementTypeSecureTextField[@name='New Password'])[1]")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "NewPasswordTextFeild",
					inputNewPasswordText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public boolean enterVerifyNewPasswordInChangePasswordScreen(TestCaseInputs inputs,
			String inputVerifyNewPasswordText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "VerifyNewPasswordTextField");
			testCase.getMobileDriver().findElement(By.id("fragment_change_password_new_verify")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "VerifyNewPasswordTextField",
					inputVerifyNewPasswordText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Change Password Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "VerifyNewPasswordTextField");
			testCase.getMobileDriver()
					.findElement(By.xpath("(//XCUIElementTypeSecureTextField[@name='Verify New Password'])[1]"))
					.clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "VerifyNewPasswordTextField",
					inputVerifyNewPasswordText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}

	public String getErrorMsgDisplayedInOldPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ErrorMsgDisplayedBelowOldPwdTextField");
	}

	public String getErrorMsgDisplayedInNewPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ErrorMsgDisplayedBelowNewPwdTextField");
	}

	public String getPasswordsDoNotMatchErrorMsgDisplayedInNewPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "PasswordsDoNotMatchLabelBelowNewPwdTextField");
	}

	public String getErrorMsgDisplayedInVerifyNewPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ErrorMsgDisplayedBelowVerifyNewPwdTextField");
	}

	public String getInvalidPasswordFormatLabelBelowOldPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "InvalidPasswordFormatLabelBelowOldPwdTextField");
	}

	public String getInvalidPasswordFormatLabelBeloNewPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "InvalidPasswordFormatLabelBelowNewPwdTextField");
	}

	public String getInvalidPasswordFormatLabelBelowVerifyNewPwdTextField() {
		return MobileUtils.getFieldValue(objectDefinition, testCase,
				"InvalidPasswordFormatLabelBelowVerifyNewPwdTextField");
	}

	public boolean isDeleteAccountScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteAccountScreenTitle");
	}

	public boolean isCloseButtonInDeleteAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInDeleteAccountScreen");
	}

	public boolean clickOnCloseButtonInDeleteAccountScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInDeleteAccountScreen");
	}

	public boolean isActionRequiredLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActionRequiredLabel");
	}

	public boolean isLearnHowToDeleteADeviceLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LearnHowToDeleteADeviceLink");
	}

	public boolean clickOnLearnHowToDeleteADeviceLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LearnHowToDeleteADeviceLink");
	}

	public boolean isLearnHowToDeleteADeviceScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LearnHowToDeleteADeviceScreen");
	}

	public boolean isCloseButtonInLearnHowToDeleteADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInLearnHowToDeleteADeviceScreen");
	}

	public boolean isWebViewInLearnHowToDeleteADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WebViewInLearnHowToDeleteADeviceScreen");
	}

	public boolean wasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"WasThisHelpfulTextInLearnHowToDeleteADeviceScreen");
	}

	public boolean isYESButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YESButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreen");
	}

	public boolean isNOButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"NOButtonInWasThisHelpfulTextInLearnHowToDeleteADeviceScreen");
	}

	public boolean clickOnCloseButtonInLearnHowToDeleteADeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInLearnHowToDeleteADeviceScreen");
	}

	public boolean isLearnHowToCancelAMembershipLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LearnHowToCancelAMembershipLink");
	}

	public boolean clickOnLearnHowToCancelAMembershipLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LearnHowToCancelAMembershipLink");
	}

	public boolean isLearnHowToCancelAMembershipScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LearnHowToCancelAMembershipScreen");
	}

	public boolean isCloseButtonInLearnHowToCancelAMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"CloseButtonInLearnHowToCancelAMembershipScreen");
	}

	public boolean isWebViewInLearnHowToCancelAMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WebViewInLearnHowToCancelAMembershipScreen");
	}

	public boolean wasThisHelpfulTextInLearnHowToCancelAMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"WasThisHelpfulTextInLearnHowToCancelAMembershipScreen");
	}

	public boolean isYESButtonInWasThisHelpfulTextInLearnHowToCancelAMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YESButtonInWasThisHelpfulTextInLearnHowToCancelAMembershipScreen");
	}

	public boolean isNOButtonInWasThisHelpfulTextInLearnHowToCancelAMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"NOButtonInWasThisHelpfulTextInLearnHowToCancelAMembershipScreen");
	}

	public boolean clickOnCloseButtonInLearnHowToCancelAMembershipScreenScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInLearnHowToCancelAMembershipScreen");
	}

	public boolean isWeAreSorryToSeeYouGoLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeAreSorryToSeeYouGoLabel");
	}

	public boolean isDeleteAccountButtonInDeleteAccountScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteAccountButtonInDeleteAccountScreen");
	}

	public boolean clickOnDeleteAccountButtonInDeleteAccountScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteAccountButtonInDeleteAccountScreen");
	}

	public boolean isYourAccountAndDataIsDeletedPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YourAccountAndDataIsDeletedPopup");
	}

	public boolean isOKButtonInYourAccountAndDataIsDeletedPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInYourAccountAndDataIsDeletedPopup");
	}

	public boolean clickOnOKButtonInYourAccountAndDataIsDeletedPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInYourAccountAndDataIsDeletedPopup");
	}

	public boolean isEmailOrPwdIncorrectErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailOrPwdIncorrectErrorPopupTitle");
	}

	public boolean isEmailOrPwdIncorrectErrorPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailOrPwdIncorrectErrorPopupMsg");
	}

	public boolean isOKButtonInEmailOrPwdIncorrectErrorPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInEmailOrPwdIncorrectErrorPopup");
	}

	public boolean clickOnOKButtonInEmailOrPwdIncorrectErrorPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInEmailOrPwdIncorrectErrorPopup");
	}
}