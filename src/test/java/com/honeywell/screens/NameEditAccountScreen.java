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
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean clearTextDisplayedInLastNameTextFieldInNameEditAccountScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		} else {
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "LastNameValueInNameEditAccountScreen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonIniOSKeyboard");
		}
		return flag;
	}
	
	public boolean enterFirstNameValueInNameEditAccountScreen(TestCaseInputs inputs, String inputFirstNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "FirstNameValueInNameEditAccountScreen");
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_first_edit_text")).clear();
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
			testCase.getMobileDriver().findElement(By.id("fragment_current_user_last_edit_text")).clear();
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
}