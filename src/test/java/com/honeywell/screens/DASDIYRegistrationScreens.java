package com.honeywell.screens;

import org.openqa.selenium.By;
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

import static io.appium.java_client.touch.offset.PointOption.point;


public class DASDIYRegistrationScreens extends MobileScreens {

	private static final String screenName = "DIYRegistration";

	public DASDIYRegistrationScreens(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAddNewDeviceScreenVisible(int timeOut) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader")) {
			return flag;
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				flag = false;
			} else {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Add New Device\"])[2]",
						testCase)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"Add New Device\"])[2]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean isDeviceListHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DevicesList");
	}

	public MobileElement getDeviceListWebElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "DevicesList");
	}

	public boolean isBackArrowInSelectADeviceScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader", timeOut);
	}

	public boolean clickOnBackArrowInSelectADeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
	}

	public boolean isCancelButtonInAddNewDeviceScreenVisible() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader")) {
				flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase,
						"BackArrowInSelectANewDeviceHeader");
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInAddNewDeviceScreen")) {
				flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase,
						"CancelButtonInAddNewDeviceScreen");
			}
		}
		return flag;
	}

	public boolean clickOnCancelButtonInAddNewDeviceScreen() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader")) {
				flag = flag
						& MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInAddNewDeviceScreen")) {
				flag = flag
						& MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInAddNewDeviceScreen");
			}
		}
		return flag;
	}

	public boolean isWhatToExpectScreenHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WhatToExpectScreenHeaderTitle");
	}

	public boolean isBackArrowInWhatToExpectScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInWhatToExpectScreen");
	}

	public boolean clickOnBackArrowInWhatToExpectScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInWhatToExpectScreen");
	}

	public boolean isChooseLocationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChooseLocationHeaderTitle");
	}

	public boolean isBackArrowInChooseLocationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInChooseLocationScreen");
	}

	public boolean clickOnBackArrowInChooseLocationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInChooseLocationScreen");
	}

	public boolean isCreateNewLocationLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateCustomLocationLink");
	}

	public boolean clickOnCreateNewLocationLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateCustomLocationLink");
	}

	public boolean isCreateLocationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateLocationHeaderTitle");
	}

	public boolean isCustomLocationTextFieldVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomeLocationTextField");
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeTextField", testCase);
		}
	}

	public boolean enterCustomLocationName(TestCaseInputs inputs, String customLocationNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "CustomeLocationTextField",
					customLocationNameText);
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			if (inputs.isRunningOn("Perfecto")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					testCase.getMobileDriver().findElement(By.xpath("//*[@text='English (US)']")).click();
					System.out.println("######dimensions.width:- " + dimensions.width);
					System.out.println("######dimensions.height:- " + dimensions.height);
					System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
					System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
					// touchAction.tap((dimensions.width - 100), (dimensions.height -
					// 100)).perform();
					// touchAction.tap((dimensions.width - 100), (dimensions.height -
					// 100)).perform();
					touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
					touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
				}
			}
			return flag;
		} else {
			flag = flag & MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField",
					customLocationNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public boolean isCustomLocationNameExistsErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomLocationNameExistsErrorPopupTitle");
	}

	public boolean isOKButtonInCustomLocationNameExistsErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInCustomLocNameExistsErrorPopup");
	}

	public boolean clickOnOKButtonInCustomLocationNameExistsErrorPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInCustomLocNameExistsErrorPopup");
	}

	public boolean isBackButtonInCreateLocationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInCreateLocationScreen");
	}

	public boolean clickOnBackButtonInCreateLocationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInCreateLocationScreen");
	}

	public boolean isConfirmYourAddressZipCodeTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmYourAddressZipCodeTitle");
	}

	public boolean isZipCodeTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ZipCodeTextField");
	}

	public boolean clearEnteredTextInZipCodeTextField() {
		return MobileUtils.clearTextField(objectDefinition, testCase, "ZipCodeTextField");
	}

	public boolean enterZipCode(String zipCodeText) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "ZipCodeTextField", zipCodeText);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			return flag;
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public boolean isInvalidZipCodePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InvalidZipCodeErrorPopup");
	}

	public boolean isOKButtonInInvalidZipCodePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInInvalidZipCodeErrorPopup");
	}

	public boolean clickOnOKButtonInInvalidZipCodePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInInvalidZipCodeErrorPopup");
	}

	public boolean isCancelButtonVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 3)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInSetUp", 3)) {
				flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInSetUp");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (isQRCodeScanningFailurePopupVisible()) {
						clickOnOKButtonInQRCodeScanningFailurePopup();
					}
					if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[3]",
							testCase, 3)) {
						flag = flag & MobileUtils.isMobElementExists("XPATH",
								"(//XCUIElementTypeButton[@name=\"Cancel\"])[3]", testCase);
					}
				}
			}
		}
		return flag;
	}

	public boolean clickOnCancelButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5)) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton",
					"Cancel popup should be retained", false);
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInSetUp", 5)) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInSetUp",
						"Cancel popup should be retained", false);
			} else {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (isQRCodeScanningFailurePopupVisible()) {
						clickOnOKButtonInQRCodeScanningFailurePopup();
					}
					if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[3]",
							testCase)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
								"(//XCUIElementTypeButton[@name=\"Cancel\"])[3]", "Cancel popup should be retained",
								false);
					}
				}
			}
		}
		return flag;
	}

	public boolean isCancelButtonInWiFiScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 3)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase,
						3)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnCancelButtonInWiFiScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]",
						testCase)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]");
				}
			}
		}
		return flag;
	}

	public boolean isCancelPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelPopupTitle");
	}

	public boolean isNoButtonInCancelPopupVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelSetUpPopup");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltNoButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltNoButtonInCancelSetUpPopup");
		}
		return flag;
	}

	public boolean clickOnNoButtonInCancelPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInCancelSetUpPopup");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltNoButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltNoButtonInCancelSetUpPopup");
		}
		return flag;

	}

	public boolean isYesButtonInCancelPopupVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelSetUpPopup");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltYesButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltYesButtonInCancelSetUpPopup");
		}
		return flag;

	}

	public boolean clickOnYesButtonInCancelPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelSetUpPopup");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltYesButtonInCancelSetUpPopup")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltYesButtonInCancelSetUpPopup");
		}
		return flag;

	}

	public boolean isAvialbleLocationNameDisplayed(String availableLocation) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath",
					"//android.widget.TextView[@text='" + availableLocation + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", availableLocation, testCase);
		}
	}

	public boolean clickOnAvailableLocationName(String availableLocation) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//android.widget.TextView[@text='" + availableLocation + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "NAME", availableLocation);
		}
	}

	public boolean isBackArrowInNameYourBaseStationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInNameYourBaseStationScreen");
	}

	public boolean clickOnBackArrowInNameYourBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInNameYourBaseStationScreen");
	}

	public boolean isNameYourBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameYourBaseStationHeaderTitle");
	}

	public boolean isAvailableBaseStationNameDisplayed(String availableBaseStationName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath",
					"//android.widget.TextView[@text='" + availableBaseStationName + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", availableBaseStationName, testCase);
		}
	}

	public boolean clickOnAvailableBaseStationName(String availableBaseStationName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//android.widget.TextView[@text='" + availableBaseStationName + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "NAME", availableBaseStationName);
		}
	}

	public boolean isCreateCustomBaseStationNameLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateCustomBaseStationNameLink");
	}

	public boolean clickOnCreateCustomBaseStationNameLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CreateCustomBaseStationNameLink");
	}

	public boolean isCreateBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CreateBaseStationHeaderTitle");
	}

	public boolean isCustomNameTextFieldDisplayed() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomNameTextField", 3);
		} else {
			return MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeTextField", testCase);
		}
	}

	public boolean isCustomBaseStationNameExistsErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomBaseStationNameExistsErrorPopupTitle");
	}

	public boolean isOKButtonInCustomBaseNameExistsErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInCustomBaseNameExistsErrorPopup");
	}

	public boolean clickOnOKButtonInCustomBaseNameExistsErrorPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInCustomBaseNameExistsErrorPopup");
	}

	public boolean isCustomBaseStationNameIsEmptyErrorPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomBaseStationNameExistsErrorPopupTitle");
	}

	public boolean isBackButtonInCreateBaseStationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInCreateBaseStationScreen");
	}

	public boolean clickOnBackButtonInCreateBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInCreateBaseStationScreen");
	}

	public boolean enterCustomNameInNameYourBaseStationScreen(String customNameText) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag
					& MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameTextField", customNameText);
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			return flag;
		} else {
			flag = flag
					& MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField", customNameText);
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public boolean enterMaxCharsInCustomNameTxtField(String enterMaxCharsInCustomNameTxtField) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameTextField",
					enterMaxCharsInCustomNameTxtField);
		} else {
			flag = flag & MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField",
					enterMaxCharsInCustomNameTxtField);
		}
		return flag;
	}

	public boolean clickOnDoneButtonInVirtualKeyboard(TestCases testCase) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public String getValueDisplayedInCustomNameTxtField() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "CustomNameTextField");
		} else {
			return MobileUtils.getFieldValue(testCase, "XPATH", "//XCUIElementTypeTextField");
		}
	}

	public boolean isPowerYourBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PowerYourBaseStationHeaderTitle");
	}

	public boolean isNotPulsingBlueLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotPulsingBlueLink");
	}

	public boolean clickOnNotPulsingBlueLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NotPulsingBlueLink");
	}

	public boolean isBaseStationHelpHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationHelpHeaderTitle");
	}

	public boolean isBackButtonInBaseStationHelpScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInBaseStationHelpScreen");
	}

	public boolean clickOnBackButtonInBaseStationHelpScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInBaseStationHelpScreen");
	}

	public boolean isNextButtonVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean clickOnNextButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isLookingForBaseStationProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LookingForBaseStationLoadingSpinner", 3);
	}

	public boolean isRegisterBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RegisterBaseStationHeaderTitle");
	}

	public boolean isBaseStationNotFoundPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationNotFoundPopupTitle", timeOut);
	}

	public boolean isOKButtonInBaseStationNotFoundPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInBaseStationNotFounPopup");
	}

	public boolean clickOnOKButtonInBaseStationNotFoundPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInBaseStationNotFounPopup");
	}

	public boolean isRetryButtonInBaseStationNotFoundPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RetryButtonInBaseStationNotFoundPopup");
	}

	public boolean clickOnRetryButtonInBaseStationNotFoundPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RetryButtonInBaseStationNotFoundPopup");
	}

	public boolean isBackArrowInPowerBaseStationVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInPowerBaseStationScreen");
	}

	public boolean clickOnBackArrowInPowerBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInPowerBaseStationScreen");
	}

	public boolean isCancelButtonInRegisterBaseStationVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInRegisterBaseStationHeader");
	}

	public boolean clickOnCancelButtonInRegisterBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInRegisterBaseStationHeader");
	}

	public boolean isQRCodeDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QRCode");
	}

	public boolean isMultipleBaseStationsScreenSubHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MultipleBaseStationsScreenSubHeaderTitle");
	}

	public boolean isMACIDVisible(String dasDeviceMacID) {

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + dasDeviceMacID + "']",
					testCase);
		} else {
			return MobileUtils.isMobElementExists("xpath",
					"//XCUIElementTypeStaticText[@value='" + dasDeviceMacID + "']", testCase);
		}
	}

	public boolean clickOnMACID(String dasDeviceMacID) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//android.widget.TextView[@text='" + dasDeviceMacID + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//XCUIElementTypeStaticText[@value='" + dasDeviceMacID + "']");
		}
	}

	public boolean isRefereshButtonInSelectBaseStationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RefreshButtonInSelectBaseStationScreen");
	}

	public boolean clickOnRefereshButtonInSelectBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RefreshButtonInSelectBaseStationScreen");
	}

	public String getToolBarTitleInRegisterBaseStationScreen() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ToolbarTitle", false).getAttribute("text");
	}

	public boolean isSingleBaseStationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RegisterBaseStationQRCodeScanText", 2,
				false);
	}

	public boolean isQRCodeScanningFailurePopupVisible() {
		System.out.println("@@@@@@@@@@@Scanning Failure Popup Displayed@@@@@@@@@@");
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QRCodeScanningFailurePopupTitle", 3);
	}

	public boolean isOKButtonInQRCodeScanningFailurePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInQRCodeScanningFailurePopup");
	}

	public boolean clickOnOKButtonInQRCodeScanningFailurePopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInQRCodeScanningFailurePopup", 3)) {
			flag = flag
					& MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInQRCodeScanningFailurePopup");
			System.out.println("@@@@@@@@@@@Clicked on RETRY button in Scanning Failure popup@@@@@@@@@@");
		}
		return flag;
	}

	public boolean isLookingForNetworkConnectionProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LookingForNetworkConnectionLoadingSpinner",
				3);
	}

	public boolean isConnectToNetworkHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectToNetworkHeaderTitle");

	}

	public boolean isConnectToNetworkHeaderDescVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectToNetworkHeaderDesc");
	}

	public String getToolBarTitleInConnectToNetworkScreen() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ConnectToNetworkHeaderTitle", false)
				.getAttribute("text");
	}

	public boolean isWiFiNetworkNameDisplayedInConnectToNetworkScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiIconInConnectToNetworkScreen")) {
			flag = flag
					& MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiIconInConnectToNetworkScreen");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"(//XCUIElementTypeImage[@name=\"icon_wifi_signal_Very_Good\"])[1]", testCase)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH", "", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnWiFiNetworkNameInConnectToNetworkScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiIconInConnectToNetworkScreen")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "WiFiIconInConnectToNetworkScreen");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"(//XCUIElementTypeImage[@name=\"icon_wifi_signal_Very_Good\"])[1]", testCase)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeImage[@name=\"icon_wifi_signal_Very_Good\"])[1]");
				}
			}
		}
		return flag;
	}

	public boolean isAddANetworkButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnAddANetworkButton() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddANetworkButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}
	public boolean clickOnContinueSetupButton() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ContinueSetupButton", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ContinueSetupButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ContinueSetupButton");
		}
	}
	
	public boolean clickOnConfirmButton() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmButton", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmButton");
		}
	}

	public boolean isAddANetworkHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkScreenTitle");
	}

	public boolean isCancelButtonInAddANetworkScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInAddANetworkScreen", 5)) {
			return flag;
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				flag = false;
			} else {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase,
						3)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnCancelButtonInAddANetworkScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInAddANetworkScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInAddANetworkScreen");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase,
						3)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]");
				}
			}
		}
		return flag;
	}

	public boolean isAvialbleNetworkTypeDisplayed(String availableNetworkType) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath",
					"//android.widget.TextView[@text='" + availableNetworkType + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("NAME", availableNetworkType, testCase);
		}
	}

	public boolean clickOnAvialbleNetworkType(String availableNetworkType) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//android.widget.TextView[@text='" + availableNetworkType + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "NAME", availableNetworkType);
		}
	}

	public boolean isEnterSSIDNameTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnterSSIDScreenTitle");
	}

	public boolean isCancelButtonInEnterSSIDScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 3)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase,
						3)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnCancelButtonInEnterSSIDScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Cancel\"])[4]",
						testCase)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeButton[@name=\"Cancel\"])[4]");
				}
			}
		}
		return flag;
	}

	public MobileElement getWiFiListWebElement() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "WiFiList");
	}

	public boolean isAvailableNetworkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AvailableNetworkNameInTheWiFiList");
	}

	public boolean isWiFiNamePresentOnWifiScreen(String wifiName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + wifiName + "']",
					testCase, 3);
		} else {
			return MobileUtils.isMobElementExists("name", wifiName, testCase, 3);

		}
	}

	public boolean clickOnWiFiNameOnWiFiScreen(String wifiName) throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + wifiName + "']");
			} else {
				int counter = 0;
				while (!this.isWiFiNamePresentOnWifiScreen(wifiName) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getWiFiListWebElement());
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
							"//android.widget.TextView[@text='" + wifiName + "']");
					if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
								"//android.widget.TextView[@text='" + wifiName + "']");
					}
				} else {
					throw new Exception("Could not find wifi : " + wifiName + " in the list");
				}
			}
		} else {

			if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "name", wifiName);
			} else {
				int counter = 0;
				while (!this.isWiFiNamePresentOnWifiScreen(wifiName) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getWiFiListWebElement());
									}
				if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "name", wifiName);
				} else {
					throw new Exception("Click On WiFi Name : Could not find wifi : " + wifiName + " in the list");
				}
			}
		}
		return flag;
	}

	public boolean isWiFiPasswordScreenSubTitleTextVisibile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnterWiFIPasswordScreenSubTitle");
	}

	public boolean isWiFiPasswordTextFieldVisibile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiPasswordTextField");
	}

	public boolean enterWiFiPassword(String password) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "WiFiPasswordTextField", password);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return true;
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public boolean isWiFiConnectionFailedPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiConnectionFailedPopupTitle");
	}

	public boolean isOKButtonInWiFiConnectionFailedPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInWiFiConnectionFailedPopup");
	}

	public boolean clickOnOKButtonInWiFiConnectionFailedPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInWiFiConnectionFailedPopup");
	}

	public boolean isBackButtonInEnterWiFiPwdScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInEnterWiFiPwdScreen", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInEnterWiFiPwdScreen");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Back\"])[2]", testCase,
						5)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"Back\"])[2]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnBackButtonInEnterWiFiPwdScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInEnterWiFiPwdScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInEnterWiFiPwdScreen");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Back\"])[2]", testCase,
						5)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeButton[@name=\"Back\"])[2]");
				}
			}
		}
		return flag;
	}

	public boolean isJoinButtonInConnectToNetworkScreenVisible() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return true;
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen", 5)) {
				return MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
			} else {
				return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
			}
		}
	}

	public boolean clickOnJoinButtonInConnectToNetworkScreen() {
		@SuppressWarnings("rawtypes")
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			return true;
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen", 5)) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
			} else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
			}
		}
	}

	public boolean isConnectingSmartHomeSecurityLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectingSmartHomeSecurityLoadingSpinner",
				3);
	}

	public boolean isAlmostDoneLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlmostDoneLoadingSpinnerTxt", 3);
	}

	public boolean isSmartHomeSecuritySuccessHeaderTitleVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SmartHomeSecuritySuccessHeaderTitle",timeout);
	}
	
	public boolean isCongratulationsHeaderTitleVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KatanaCongratulationsScreen",timeout);
	}
	
	public boolean isProMonitoringHeaderTitleVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KatanaProMonitoringScreen",timeout);
	}
	public boolean isProMonitoringSetUpCompleteHeaderTitleVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KatanaProMonitoringSetUpCompleteScreen",timeout);
	}

	public boolean isNoButtonInSmartHomeSecuritySuccessScreenVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInSmartHomeSecuritySuccessScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase,
					"NoButtonInSmartHomeSecuritySuccessScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean clickOnNoButtonInSmartHomeSecuritySuccessScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInSmartHomeSecuritySuccessScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInSmartHomeSecuritySuccessScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean isYesButtonInSmartHomeSecuritySuccessScreenVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInSmartHomeSecuritySuccessScreen",
				5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase,
					"YesButtonInSmartHomeSecuritySuccessScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickYesButtonInSmartHomeSecuritySuccessScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInSmartHomeSecuritySuccessScreen",
				5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInSmartHomeSecuritySuccessScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean isFirmwareUpdatePopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareUpdatePopupTitle", timeOut);
	}

	public boolean isOKButtonInFirmwareUpdatePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInFirmwareUpdatePopup");
	}

	public boolean clickOnOKButtonInFirmwareUpdatePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInFirmwareUpdatePopup");
	}

	public boolean isSetUpAccessoriesScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpAccessoriesScreenTitle", timeOut);
	}

	public boolean isBackButtonInSetUpAccessoriesScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInSetUpAccessoriesScreen",
				timeOut);
	}

	public boolean clickOnBackButtonInSetUpAccessoriesScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInSetUpAccessoriesScreen");
	}

	public boolean isSensorSetUpButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorSetUpButton");
	}

	public boolean clickOnSensorSetUpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorSetUpButton");
	}

	public boolean isLocateViewerScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocateViewerScreenTitle", timeOut);
	}

	public boolean isOverviewScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OverviewScreenTitle");
	}

	public boolean isInProgressLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InProgressLoadingSpinnerText");
	}

	public boolean isLocateSensorScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocateSensorScreenTitle");
	}

	public boolean isNameSensorScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameSensorScreenTitle");
	}

	public boolean isAvailableSensorNameVisible(String availableSensorName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (availableSensorName.equalsIgnoreCase("In a Wall Corner")) {
				availableSensorName = "In a wall corner";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall")) {
				availableSensorName = "Flat on a wall";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else if (availableSensorName.equalsIgnoreCase("On a Shelf")) {
				availableSensorName = "On a shelf";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else if (availableSensorName.equalsIgnoreCase("In a Corner")) {
				availableSensorName = "In a corner";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall with Adhesive")) {
				availableSensorName = "Flat on a wall with adhesive";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall with Screws")) {
				availableSensorName = "Flat on a wall with screws";
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			} else {
				flag = flag
						& MobileUtils.isMobElementExists("xpath", "//*[@text='" + availableSensorName + "']", testCase);
			}
		} else {
			flag = flag & MobileUtils.isMobElementExists("xpath", "//*[@name='" + availableSensorName + "']", testCase);
		}
		return flag;
	}

	public boolean clickOnAvailableSensorName(String availableSensorName) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (availableSensorName.equalsIgnoreCase("In a Wall Corner")) {
				availableSensorName = "In a wall corner";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall")) {
				availableSensorName = "Flat on a wall";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else if (availableSensorName.equalsIgnoreCase("On a Shelf")) {
				availableSensorName = "On a shelf";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else if (availableSensorName.equalsIgnoreCase("In a Corner")) {
				availableSensorName = "In a corner";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall with Adhesive")) {
				availableSensorName = "Flat on a wall with adhesive";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else if (availableSensorName.equalsIgnoreCase("Flat on a Wall with Screws")) {
				availableSensorName = "Flat on a wall with screws";
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			} else {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='" + availableSensorName + "']");
			}
		} else {
			flag = flag & MobileUtils.clickOnElement(testCase, "xpath", "//*[@name='" + availableSensorName + "']");
		}
		return flag;
	}

	public boolean isSavingSensorLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SavingSensorLoadingSpinnerText");
	}

	public boolean isCheckLocationScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CheckLocationScreenTitle");
	}

	public boolean isWatchHowToVideoLinkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HowToVideoLink");
	}

	public boolean clickOnWatchHowToVideoLink() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HowToVideoLink");
	}

	public boolean isVideoPlayerControlIconInAndroidVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoPlayerControlIconInAndroid", timeOut);
	}

	public boolean isNavBackIconDisplayedInVideoClipScreen(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInHowToVideoScreen", timeOut);
	}

	public boolean clickOnNavBackIconInVideoClipScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInHowToVideoScreen");
	}

	public boolean isCloseDoorSubHeaderTextInCheckLocationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"CloseDoorSubHeaderTextInCheckLocationScreen");
	}

	public boolean isCheckLocationSignalScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CheckLocationSignalScreenTitle");
	}

	public boolean isSignalStrengthTextVisibleInCheckLocationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthLabelInCheckLocationScreen");
	}

	public boolean isPrepareSensorScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PrepareSensorScreenTitle", timeOut);
	}

	public boolean isUnEvenButtonVisibleInPrepareSensorScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "UnEvenButtonInPrepareSensorScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "UnEvenButtonInPrepareSensorScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean clickOnUnEvenButtonInPrepareSensorScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "UnEvenButtonInPrepareSensorScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "UnEvenButtonInPrepareSensorScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean isEvenButtonVisibleInPrepareSensorScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EvenButtonInPrepareSensorScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EvenButtonInPrepareSensorScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnEvenButtonInPrepareSensorScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EvenButtonInPrepareSensorScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "EvenButtonInPrepareSensorScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean isPlaceAdhesiveStripsScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceAdhesiveStripsScreenTitle");
	}

	public boolean isMountSensorScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MountSensorScreenTitle");
	}

	public boolean isMountSensorScreenSubHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MountSensorScreenSubHeaderTitle");
	}

	public boolean isVerifyingLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VerifyingLoadingSpinnerText");
	}

	public boolean isSensorReadyScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorReadyScreenTitle");
	}

	public boolean isSuccessLabelDisplayedInSensorReadyScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SuccessLabelInSensorReadyScren");
	}

	public boolean isSetUpAccessoriesConfiguredScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpAccessoriesConfiguredScreenTitle");
	}

	public boolean isConfiguredLabelInSetUpAccessoriesConfiguredScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfiguredLabelInSetUpAccessoriesScreen");
	}

	public boolean isSaveButtonInSetUpAccessoriesConfiguredScreenVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInSetUpAccessoriesScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInSetUpAccessoriesScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnSaveButtonInSetUpAccessoriesConfiguredScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInSetUpAccessoriesScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInSetUpAccessoriesScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean isFinishingUpLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FinishingUpLoadingSpinnerText");
	}

	public boolean isGeoFencingHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeoFencingHeaderTitle", timeOut);
	}

	public boolean isSkipButtonInGeoFencingScreenVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInGeoFencingScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean clickOnSkipButtonInGeoFencingScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInGeoFencingScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean isEnableButtonInGeoFencingScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean clickOnEnableButtonInGeoFencingScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isEnablingGeoFencingLoadingProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnablingGeoFencingLoadingSpinner");
	}

	public boolean isGeoFenceScreenHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeoFenceScreenHeaderTitle");
	}

	public boolean isCancelButtonInGeoFenceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInGeoFenceScreen");
	}

	public boolean clickOnCancelButtonInGeoFenceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInGeoFenceScreen");
	}

	public boolean isCancelGeofencePopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelGeofencePopupTitle");
	}

	public boolean isNoButtonInGeoFencePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelGeofencePopup");
	}

	public boolean clickOnNoButtonInGeoFencePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInCancelGeofencePopup");
	}

	public boolean isYesButtonInGeoFencePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelGeofencePopup");
	}

	public boolean clickOnYesButtonInGeoFencePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelGeofencePopup");
	}

	public boolean isUpdateGeoFenceButtonInGeoFenceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateGeoFenceButtonInGeoFenceScreen");
	}

	public boolean isSaveButtonInGeoFenceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceScreen");
	}

	public boolean clickOnSaveButtonInGeoFenceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInGeoFenceScreen");
	}

	public boolean isGeoFenceEnabledScreenHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeoFenceEnabledScreenHeaderTitle", timeOut);
	}

	public boolean isGeoFenceEnabledScreenSubHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeoFenceEnabledScreenSubHeaderTitle");
	}

	public boolean isSaveButtonGeoFenceEnabledScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen", 5)) {
			flag = flag
					& MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean clickOnSaveButtonGeoFenceEnabledScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isAmazonAlexaHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaHeaderTitle");
	}

	public boolean isSkipButtonInAmazonAlexaVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInAmazonAlexa", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInAmazonAlexa");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean clickOnSkipButtonInAmazonAlexaScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInAmazonAlexa", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButtonInAmazonAlexa");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LeftButton");
		}
	}

	public boolean isEnableButtonInAmazonAlexaVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpButtonInAmazonAlexa", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpButtonInAmazonAlexa");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean clickOnEnableButtonInAmazonAlexaScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpButtonInAmazonAlexa", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SetUpButtonInAmazonAlexa");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isSignInToAmazonAlexaScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignInToAmazonScreenTitle");
	}

	public boolean isEmailTextFieldInSignInToAmazonAlexaScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignInToAmazonEmailTextField");
	}

	public boolean isPasswordTextFieldInSignInToAmazonAlexaScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignInToAmazonPasswordTextField");
	}

	public boolean enterAmazonUserCredentials(String amazonUserName, String amazonPassword) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "SignInToAmazonEmailTextField",
				amazonUserName);
		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SignInToAmazonPasswordTextField");
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "SignInToAmazonPasswordTextField",
				amazonPassword);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
			return flag;
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "GoButtonInKeyboard");
		}
		return flag;
	}

	public boolean isAmazonAlexaScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaScreenTitle");
	}

	public boolean isAllowButtonInAmazonAlexaScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButtonInAmazonAlexaScreen");
	}

	public boolean clickOnAllowButtonInAmazonAlexaScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowButtonInAmazonAlexaScreen");
	}

	public boolean isAmazonAlexaSetUpCompletedScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaSetUpCompletedScreenTitle");
	}

	public boolean isFeatureSetUpCompletedScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FeatureSetUpCompletedScreenTitle");
	}

	public boolean clickOnDoneButtonInFeatureSetUpCompletedScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButtonInFeatureSetUpCompletedScreen")) {
			flag = flag
					& MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInFeatureSetUpCompletedScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isSkipButtonInHoneywellMembershipScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInHoneywellMembershipScreen");
	}

	
	public boolean clickOnSkipButtonInHoneywellMembershipScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButtonInHoneywellMembershipScreen");
	}

	public boolean isPeopleDetectionHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PeopleDetectionHeaderTitle");
	}

	public boolean isNotNowButtonInPeopleDetectionScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButtonInPeopleDetectionScreen", 5)) {
			flag = flag
					& MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButtonInPeopleDetectionScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltLeftButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltLeftButton");
		}
		return flag;
	}

	public boolean clickOnNotNowButtonInPeopleDetectionScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButtonInPeopleDetectionScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NotNowButtonInPeopleDetectionScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LeftButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "LeftButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltLeftButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltLeftButton");
		}
		return flag;
	}

	public boolean isEnableButtonInPeopleDetectionScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInPeopleDetectionScreen", 5)) {
			flag = flag
					& MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInPeopleDetectionScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean clickOnEnableButtonInPeopleDetectionScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInPeopleDetectionScreen", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EnableButtonInPeopleDetectionScreen");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltRightButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AltRightButton");
		}
		return flag;
	}

	public boolean isIncreaseSecurityPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IncreaseSecurityPopup");
	}

	public boolean isDontUseButtonInIncreaseSecurityPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DontUseButtonInIncreaseSecurityPopup");
	}

	public boolean clickOnDontUseButtonInIncreaseSecurityPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DontUseButtonInIncreaseSecurityPopup");
	}

	public boolean isGotItButtonInAccessMoreInfoPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotItButtonInAccessMoreInfoPopup");
	}

	public boolean clickOnGotItButtonInAccessMoreInfoPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotItButtonInAccessMoreInfoPopup");
	}

	public boolean isGotItButtonInQuickControlsPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotItButtonInQuickControlsPopup");
	}

	public boolean clickOnGotItButtonInQuickControlsPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotItButtonInQuickControlsPopup");
	}

	public boolean isAddSecurityButtonInIncreaseSecurityPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddSecurityButtonInIncreaseSecurityPopup");
	}

	public boolean clickOnAddSecurityButtonInIncreaseSecurityPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddSecurityButtonInIncreaseSecurityPopup");
	}

	public boolean isGlobalDrawerButtonVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", 5);
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerIcon", 5)) {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerIcon", 5);
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"menu\"])[2]", testCase,
						5)) {
					flag = flag & MobileUtils.isMobElementExists("XPATH",
							"(//XCUIElementTypeButton[@name=\"menu\"])[2]", testCase);
				}
			}
		}
		return flag;
	}

	public boolean clickOnGlobalDrawerButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerIcon", 5)) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerIcon");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"menu\"])[2]", testCase,
						5)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"(//XCUIElementTypeButton[@name=\"menu\"])[2]");
					if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"menu\"])[2]",
							testCase, 5)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
								"(//XCUIElementTypeButton[@name=\"menu\"])[2]");
					}
				}
			}
		}
		return flag;
	}

	public boolean isLocationDetailsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationDetailsInSettings");
	}

	public boolean clickOnLocationDetails() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDetailsInSettings");
	}

	public boolean isDeleteLocationButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteLocation");
	}

	public String getLocationNameInDetailsScreen() {
		String getLocationName = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationNameInDetailsScreen")) {
			getLocationName = MobileUtils.getFieldValue(objectDefinition, testCase, "LocationNameInDetailsScreen");
		}
		return getLocationName;
	}

	public boolean clickOnDeleteLocationButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteLocation");
	}

	public boolean isDeleteLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteLocationPopupTitle");
	}

	public boolean isYesButtonInDeleteLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInDeleteLocationPopup");
	}

	public boolean clickOnYesButtonInDeleteLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInDeleteLocationPopup");
	}

	public boolean isDeletingLocationLoadingProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeletingLocationLoadingSpinner", 3);
	}

	public boolean isSmartHomeSecurityOptionVisible(String deviceName) throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
					testCase, 3)) {
				return flag;
			} else {
				int counter = 0;
				while (!MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
						testCase, 3) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getDeviceListWebElement());
					counter++;
				}
				if (MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
						testCase, 3)) {
					return flag;
				} else {
					throw new Exception(
							"Select Device To Install : Could not find device : " + deviceName + " in the list");
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("name", deviceName, testCase, 3)) {
				return flag;
			} else {
				int counter = 0;
				while (!MobileUtils.isMobElementExists("name", deviceName, testCase, 3) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getDeviceListWebElement());
					counter++;
				}
				if (MobileUtils.isMobElementExists("name", deviceName, testCase, 3)) {
					return flag;
				} else {
					throw new Exception(
							"Select Device To Install : Could not find device : " + deviceName + " in the list");
				}
			}
		}
	}

	public boolean selectDeviceToInstall(String deviceName) throws Exception {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (deviceName.equalsIgnoreCase("Smart Home Security Sensor Accessories")) {
				deviceName = "Sensor Accessories";
			}
			if (MobileUtils.isMobElementExists("xpath",
					"//android.widget.TextView[contains(@text,'" + deviceName + "')]", testCase, 3)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[contains(@text,'" + deviceName + "')]");
			} else {
				int counter = 0;
				while (!MobileUtils.isMobElementExists("xpath",
						"//android.widget.TextView[contains(@text,'" + deviceName + "')]", testCase, 3)
						&& counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getDeviceListWebElement());
					counter++;
				}
				if (MobileUtils.isMobElementExists("xpath",
						"//android.widget.TextView[contains(@text,'" + deviceName + "')]", testCase, 3)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
							"//android.widget.TextView[contains(@text,'" + deviceName + "')]");
					if (MobileUtils.isMobElementExists("xpath",
							"//android.widget.TextView[contains(@text,'" + deviceName + "')]", testCase, 3)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
								"//android.widget.TextView[contains(@text,'" + deviceName + "')]");
					}
				} else {
					throw new Exception(
							"Select Device To Install : Could not find device : " + deviceName + " in the list");
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("name", deviceName, testCase, 3)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "name", deviceName);
			} else {
				int counter = 0;
				while (!MobileUtils.isMobElementExists("name", deviceName, testCase, 3) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, this.getDeviceListWebElement());
					counter++;
				}
				if (MobileUtils.isMobElementExists("name", deviceName, testCase, 3)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "name", deviceName);
				} else {
					throw new Exception(
							"Select Device To Install : Could not find device : " + deviceName + " in the list");
				}
			}

		}
		return flag;
	}

	public boolean isPasscodeTitlePresent(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PasscodePopUpTitle", timeOut);
	}

	public boolean isISMVOSMVNameViewerScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ISMVOSMVNameViewerScreenTitle", timeOut);
	}

	public boolean isPlaceViewerCheckPlacementScreenTitileVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerCheckPlacementScreen", timeOut);
	}

	public boolean isPlaceViewerSelectMountingOptionScreenTitileVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerSelectMountingOptionScreen",
				timeOut);
	}

	public boolean isPlaceViewerWallScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerWallScreenTitle", timeOut);
	}

	public boolean isPlaceViewerMountScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerMountScreenTitle", timeOut);
	}

	public boolean isPlaceViewerArmScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerArmScreenTitle", timeOut);
	}

	public boolean isPlaceViewerMotionViewerScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerMotionViewerScreenTitle",
				timeOut);
	}

	public boolean isPlaceViewerAdjustViewerScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlaceViewerAdjustViewerScreenTitle",
				timeOut);
	}

	public boolean isTestMotionViewerScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TestMotionViewerScreenTitle", timeOut);
	}

	public boolean isOSMVOptionsInDASSettingsVisible(String osmvOptionInDASSettingsScreen) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.isMobElementExists("XPath",
					"//android.widget.TextView[@text='" + osmvOptionInDASSettingsScreen + "']", testCase);
		} else {
			flag = flag & MobileUtils.isMobElementExists("XPath",
					"//XCUIElementTypeStaticText[@value='" + osmvOptionInDASSettingsScreen + "']", testCase);
		}
		return flag;
	}

	public boolean isCustomNameISMVOSMVLocationScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomNameISMVOSMVLocationScreen");
	}

	public boolean isViewerNotWorkingLinkInTestMotionViewerScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"ViewerNotWorkingLinkInTestMotionViewerScreen");
	}

	public boolean clickOnViewerNotWorkingLinkInTestMotionViewerScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ViewerNotWorkingLinkInTestMotionViewerScreen");
	}

	public boolean isMotionViewerHelpScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionViewerHelpScreenTitle", timeOut);
	}

	public boolean isBackButtonInMotionViewerHelpScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInMotionViewerHelpScreen");
	}

	public boolean clickOnBackButtonInMotionViewerHelpScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInMotionViewerHelpScreen");
	}

	public boolean isTestSignalStrengthButtonInMotionViewerHelpScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"TestSignalStrengthButtonInMotionViewerHelpScreen");
	}

	public boolean clickOnTestSignalStrengthButtonInMotionViewerHelpScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"TestSignalStrengthButtonInMotionViewerHelpScreen");
	}

	public boolean isOutOfRangePopupInSignalStrengthScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OutOfRangePopupInSignalStrengthScreen");
	}

	public boolean isOKButtonInOutOfRangePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInOutOfRangePopup");
	}

	public boolean clickOnOKButtonInOutOfRangePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInOutOfRangePopup");
	}

	public boolean isSignalStrengthScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthScreenTitle", timeOut);
	}

	public boolean isBackButtonInSignalStrengthScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInSignalStrengthScreen");
	}

	public boolean clickOnBackButtonInSignalStrengthScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInSignalStrengthScreen");
	}

	public boolean isSignalStrengthIsLowLabelVisibleInSignalStrengthScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthIsLowLabel");
	}

	public boolean isSignalStrengthIsHighLabelVisibleInSignalStrengthScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthIsHighLabel");
	}

	public boolean isSignalStrengthIsHighSubTitleVisibleInSignalStrengthScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthIsHighSubTitle", 5)) {
			return true;
		} else if (MobileUtils.isMobElementExists("XPATH", "//*[@value='You shouldn’t have any connection issues.']",
				testCase)) {
			return true;
		} else if (MobileUtils.isMobElementExists("XPATH", "//*[@value='You shouldn\'t have any connection issues.']",
				testCase)) {
			return true;
		} else if (testCase.getMobileDriver()
				.findElement(By.xpath("//*[@value=\"You shouldn’t have any connection issues.\"]")).isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public String getSensorRangeSubTitleText() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "SensorRangeTestTitle");
	}

	public boolean isSelectADeviceToInstallHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectADeviceToInstallHeaderTitle");
	}

	public boolean isAllMobElementsInAddNewDeviceHeaderSectionVisible() {
		boolean flag = true;
		if (this.isDeviceListHeaderTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Device list is displayed in Add New Device Screen");
			if (this.isCancelButtonInAddNewDeviceScreenVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Cancel button in iOS and Back arrow in Android is displayed in Add New Device Screen");
				if (this.isAddNewDeviceScreenVisible(1)) {
					Keyword.ReportStep_Pass(testCase,
							"Add New Device Header title is displayed in Add New Device Screen");
					if (this.isSelectADeviceToInstallHeaderTitleVisible()) {
						Keyword.ReportStep_Pass(testCase,
								"Select A Device Label is displayed in Add New Device Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Select A Device Label is not displayed in Add New Device Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Add New Device Header title is not displayed in Add New Device Screen");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Cancel button in iOS and Back arrow in Android is displayed in Add New Device Screen");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device list is not displayed in Add New Device Screen");
		}
		return flag;
	}
	public boolean isSecurityProvisionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KatanaSecurityProvsionScreen");
	}
	
	public boolean isFetchingEULAScreenLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FetchingEULAScreenLoadingSpinner");
	}
	
	public boolean isGetStartedButtonInWhatToExpectScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetStartedButtonInWhatToExpectScreen");
	}
	
	public boolean clickOnGetStartedButtonInWhatToExpectScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GetStartedButtonInWhatToExpectScreen");
	}

	public boolean isSecurityProvisionScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KatanaSecurityProvsionScreen");
	}
} 
