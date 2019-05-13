package com.honeywell.screens;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class NameEditAccountScreen extends MobileScreens {

	private static final String screenName = "NameEditAccountScreen";

	public NameEditAccountScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isNameEditAccountScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameEditAccountTitle");
	}
	
	public boolean isFirstNameTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameTitle");
	}
	
	public boolean isLastNameTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameTitle");
	}
	
	public boolean isSaveButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButton");
	}
	
	public boolean isSaveButtonEnabledInNameEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButton")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "SaveButton")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButton")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "SaveButton")
							.getAttribute("enabled").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean clickOnSaveButtonInNameEditAccountScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
	}
	
	public boolean clearTextDisplayedInFirstNameTextFieldInNameEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			//testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
			MobileUtils.getMobElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen").clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "NextButtonIniOSKeyboard");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean clearTextDisplayedInLastNameTextFieldInNameEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			//testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
			MobileUtils.getMobElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen").clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean enterFirstNameValueInNameEditAccountScreen(TestCaseInputs inputs, String inputFirstNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
			//testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
			MobileUtils.getMobElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen").clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen",
					inputFirstNameText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='First Name']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen",
					inputFirstNameText);
			//flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NextButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean enterLastNameValueInNameEditAccountScreen(TestCaseInputs inputs, String inputLastNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen");
			//testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
			MobileUtils.getMobElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen").clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen",
					inputLastNameText);
			MobileUtils.hideKeyboard(testCase.getMobileDriver(),
					"Hide Android Keyboard for Location Name Text Field in Edit Address Screen");
		} else {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen");
			//testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@label='Last Name']")).click();
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeTextField[@name='Last Name']")).clear();
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen",
					inputLastNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean isFirstNameValueVisibleInNameScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
	}
	
	public boolean isLastNameValueVisibleInNameScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen");
	}
	
	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}
	
	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}
	
	public String getFirstNameValueInNameEditAccountScreen() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen").getAttribute("text");
		}else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen").getAttribute("value");
		}
	}
	
	public String getLastNameValueInNameEditAccountScreen() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen").getAttribute("text");
		}else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen").getAttribute("value");
		}
	}
	
	public boolean isCancelNameChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelNameChangesPopupTitle");
	}
	
	public boolean isCancelNameChangesPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelNameChangesPopupMsg");
	}
	
	public boolean isYesButtonInCancelNameChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelNameChangesPopup");
	}
	
	public boolean isNoButtonInCancelNameChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelNameChangesPopup");
	}
	
	public boolean clickOnYesButtonInCancelNameChangesPopupVisible() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelNameChangesPopup");
	}
	
	public boolean clickOnNoButtonInCancelNameChangesPopupVisible() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInCancelNameChangesPopup");
	}
}