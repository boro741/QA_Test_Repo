package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class DASDIYRegistrationScreens extends MobileScreens {

	private static final String screenName = "DIYRegistration";

	public DASDIYRegistrationScreens(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAddNewDeviceScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader", timeOut);
	}

	public boolean isBackArrowInSelectADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
	}

	public boolean clickOnBackArrowInSelectADeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
	}

	public boolean isChooseLocationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChooseLocationHeaderTitle");
	}

	public boolean isCustomLocationTextFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomeLocationTextField");
	}

	@SuppressWarnings("unchecked")
	public boolean enterCustomLocationName(String customLocationNameText) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "CustomeLocationTextField",
				customLocationNameText);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.ENTER);
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
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

	@SuppressWarnings("unchecked")
	public boolean enterZipCode(String zipCodeText) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "ZipCodeTextField", zipCodeText);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.ENTER);
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonInKeyboard");
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
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

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInSetUp");
		}
	}

	public boolean clickOnCancelButton() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInSetUp");
		}
	}

	public boolean isCancelPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelPopupTitle");
	}

	public boolean isNoButtonInCancelPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInCancelSetUpPopup");
	}

	public boolean clickOnNoButtonInCancelPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInCancelSetUpPopup");
	}

	public boolean isYesButtonInCancelPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInCancelSetUpPopup");
	}

	public boolean clickOnYesButtonInCancelPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelSetUpPopup");
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

	public boolean isCustomNameTextFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CustomNameTextField", 3);
	}

	@SuppressWarnings("unchecked")
	public boolean enterCustomNameInNameYourBaseStationScreen(String customNameText) {
		boolean flag = true;
		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "CustomNameTextField", customNameText);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.ENTER);
		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInKeyboard");
		}
		return flag;
	}

	public boolean isPowerYourBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PowerYourBaseStationHeaderTitle");
	}

	public boolean isNextButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnNextButton() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean isLookingForBaseStationProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LookingForBaseStationLoadingSpinner", 3);
	}

	public boolean isRegisterBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RegisterBaseStationHeaderTitle");
	}

	public boolean isBaseStationNotFoundPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationNotFounPopupTitle");
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

	public boolean isBackArrowInRegisterBaseStationVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInRegisterBaseStationHeader");
	}

	public boolean clickOnBackArrowInRegisterBaseStationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInRegisterBaseStationHeader");
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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QRCodeScanningFailurePopupTitle");
	}

	public boolean isOKButtonInQRCodeScanningFailurePopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInQRCodeScanningFailurePopup");
	}

	public boolean clickOnOKButtonInQRCodeScanningFailurePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInQRCodeScanningFailurePopup");
	}

	public boolean isLookingForNetworkConnectionProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LookingForNetworkConnectionLoadingSpinner",
				3);
	}

	public boolean isConnectToNetworkHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectoNetworkHeaderTitle");

	}

	public boolean isConnectToNetworkHeaderDescVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectoNetworkHeaderDesc");
	}

	public String getToolBarTitleInConnectToNetworkScreen() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ConnectoNetworkHeaderTitle", false)
				.getAttribute("text");
	}

	public boolean isAddANetworkButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnAddANetworkButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddANetworkButton");
	}

	public boolean isAddANetworkHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkScreenTitle");
	}

	public boolean isCancelButtonInAddANetworkScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInAddANetworkScreen");
	}

	public boolean clickOnCancelButtonInAddANetworkScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInAddANetworkScreen");
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
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
				return MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + wifiName + "']");
			} else {
				int counter = 0;
				while (!this.isWiFiNamePresentOnWifiScreen(wifiName) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, objectDefinition, "WiFiList");
				}
				if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
					return MobileUtils.clickOnElement(testCase, "xpath",
							"//android.widget.TextView[@text='" + wifiName + "']");
				} else {
					throw new Exception("Could not find wifi : " + wifiName + " in the list");
				}
			}
		} else {

			if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
				return MobileUtils.clickOnElement(testCase, "name", wifiName);
			} else {
				int counter = 0;
				while (!this.isWiFiNamePresentOnWifiScreen(wifiName) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, objectDefinition, "WiFiList");
				}
				if (this.isWiFiNamePresentOnWifiScreen(wifiName)) {
					return MobileUtils.clickOnElement(testCase, "name", wifiName);
				} else {
					throw new Exception("Click On WiFi Name : Could not find wifi : " + wifiName + " in the list");
				}
			}
		}
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
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
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

	public boolean isJoinButtonInConnectToNetworkScreenVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnJoinButtonInConnectToNetworkScreen() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean isConnectingSmartHomeSecurityLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectingSmartHomeSecurityLoadingSpinner",
				3);
	}

	public boolean isSmartHomeSecuritySuccessHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SmartHomeSecuritySuccessHeaderTitle");
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

	public boolean isSetUpAccessoriesScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetUpAccessoriesScreenTitle");
	}

	public boolean isSensorSetUpButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorSetUpButton");
	}

	public boolean clickOnSensorSetUpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorSetUpButton");
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
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath",
					"//*[@text='" + availableSensorName + "']", testCase);
		} else {
			return MobileUtils.isMobElementExists("xpath",
					"//*[@value='" + availableSensorName + "']", testCase);
		}
	}

	public boolean clickOnAvailableSensorName(String availableSensorName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//*[@text='" + availableSensorName + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//*[@value='" + availableSensorName + "']");
		}
	}

	public boolean isSavingSensorLoadingSpinnerTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SavingSensorLoadingSpinnerText");
	}

	public boolean isCheckLocationScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CheckLocationScreenTitle");
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnEnableButtonInGeoFencingScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
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

	public boolean isUseLocationInGeoFenceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UseLocationButtonInGeoFenceScreen");
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen", 5)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen");
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RightButton");
		}
	}

	public boolean clickOnSaveButtonGeoFenceEnabledScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen", 5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInGeoFenceEnabledScreen");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RightButton");
		}
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

	public boolean isSetUpButtonInAmazonAlexaScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
	}

	public boolean clickSetUpSkipButtonInAmazonAlexaScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
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

	public boolean clickOnGlobalDrawerButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerButton");
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

	public boolean clickOnDeleteLocationButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteLocation");
	}

	public boolean isDeleteLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteLocationPopupTitle");
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
					LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
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
					LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
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
			if (MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
					testCase, 3)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + deviceName + "']");
			} else {
				int counter = 0;
				while (!MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
						testCase, 3) && counter < 4) {
					LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
					counter++;
				}
				if (MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + deviceName + "']",
						testCase, 3)) {
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
							"//android.widget.TextView[@text='" + deviceName + "']");
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
					LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
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
}
