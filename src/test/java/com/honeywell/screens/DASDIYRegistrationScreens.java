package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class DASDIYRegistrationScreens extends MobileScreens {

	private static final String screenName = "DIYRegistration";

	public DASDIYRegistrationScreens(TestCases testCase) {
		super(testCase, screenName);
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

	public boolean isCancelButtonInChooseLocationScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInChooseLocationScreen");
	}

	public boolean clickOnCancelButtonInChooseLocationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInChooseLocationScreen");
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

	public boolean isHomeLocationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectHomeLocation", 3);
	}

	public boolean clickOnHomeLocation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SelectHomeLocation");
	}

	public boolean isNameYourBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameYourBaseStationHeaderTitle");
	}

	public boolean isLivingRoomBaseStationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectLivingRoomBaseStation", 3);
	}

	public boolean clickOnLivingRoomBaseStation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SelectLivingRoomBaseStation");
	}

	public boolean isPowerYourBaseStationHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PowerYourBaseStationHeaderTitle");
	}

	public boolean isNextButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton");
	}

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
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

	public String getToolBarTitleInRegisterBaseStationScreen() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ToolbarTitle", false).getAttribute("text");
	}

	public boolean isSingleBaseStationDisplayed() {
		return MobileUtils.isMobElementExists("name",
				"Scan this code by showing it to your Base Station\u2019s camera.", testCase, 2, false);
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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton");
	}

	public boolean isAvailableNetworkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AvailableNetworkNameInTheWiFiList");
	}

	public boolean isWiFiNamePresentOnWifiScreen(String wifiName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists("xpath", "//android.widget.TextView[@text='" + wifiName + "']",
					testCase, 3);
		} else {
			return true;
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
					throw new Exception("Click On WiFi Name : Could not find wifi : " + wifiName + " in the list");
				}
			}
		} else {
			return true;
		}
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
			MobileUtils.clickOnElement(testCase, "name", "Done");
		}
		return flag;
	}

	public boolean isJoinButtonInConnectToNetworkScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
	}

	public boolean clickOnJoinButtonInConnectToNetworkScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "JoinButtonInConnectToNetworkScreen");
	}

	public boolean isConnectingSmartHomeSecurityLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectingSmartHomeSecurityLoadingSpinner",
				3);
	}

	public boolean isSmartHomeSecuritySuccessHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SmartHomeSecuritySuccessHeaderTitle");
	}

	public boolean isNoButtonInSmartHomeSecuritySuccessScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButtonInSmartHomeSecuritySuccessScreen");
	}

	public boolean clickOnNoButtonInSmartHomeSecuritySuccessScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButtonInSmartHomeSecuritySuccessScreen");
	}

	public boolean isYesButtonInSmartHomeSecuritySuccessScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButtonInSmartHomeSecuritySuccessScreen");
	}

	public boolean clickYesNoButtonInSmartHomeSecuritySuccessScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInSmartHomeSecuritySuccessScreen");
	}

	public boolean isGeoFencingHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeoFencingHeaderTitle");
	}

	public boolean isSkipButtonInGeoFencingScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
	}

	public boolean clickOnSkipButtonInGeoFencingScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
	}

	public boolean isEnableButtonInGeoFencingScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
	}

	public boolean clickEnableSkipButtonInGeoFencingScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EnableButtonInGeoFencingScreen");
	}

	public boolean isAmazonAlexaHeaderTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaHeaderTitle");
	}

	public boolean isSkipButtonInAmazonAlexaVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
	}

	public boolean clickOnSkipButtonInAmazonAlexaScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButtonInGeoFencingScreen");
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
					System.out.println("#######counter: " + counter);
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
					System.out.println("#######counter: " + counter);
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
					System.out.println("#######counter: " + counter);
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
					System.out.println("#######counter: " + counter);
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
