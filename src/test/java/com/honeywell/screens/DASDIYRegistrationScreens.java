package com.honeywell.screens;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class DASDIYRegistrationScreens extends MobileScreens {

	private static final String screenName = "DIYRegistration";
	public boolean flag = true;

	
	public DASDIYRegistrationScreens(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean isBackArrowInSelectADeviceScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
	}
	
	public boolean clickOnBackArrowInSelectADeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackArrowInSelectANewDeviceHeader");
	}

	public boolean isSmartHomeSecurityButtonVisible() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SmartHomeSecurity", 3)) {
		return flag;
		}else {
			try {
				if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Smart Home Security")) {
					return flag;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public boolean clickOnSmartHomeSecurityButton() throws Exception {
		
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			if (MobileUtils.isMobElementExists("xpath",
					"//XCUIElementTypeStaticText[contains(@name,'Showing devices for')]", testCase, 5)) {
				if (!MobileUtils
						.getMobElement(testCase, "xpath",
								"//XCUIElementTypeStaticText[contains(@name,'Showing devices for')]")
						.getAttribute("value").contains("United States")) {
					flag = flag & MobileUtils.clickOnElement(testCase, "name", "CHANGE COUNTRY");
					if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeTextField", testCase, 10)) {
						flag = flag & MobileUtils.setValueToElement(testCase, "xpath",
								"//XCUIElementTypeTextField", "United States");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Search Text Field not present when selecting country");
					}
					flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
							"//XCUIElementTypeStaticText[@name='United States']");
					if (MobileUtils.isMobElementExists("xpath",
							"//XCUIElementTypeStaticText[contains(@name,'Showing devices for')]", testCase,
							10)) {
						if (MobileUtils
								.getMobElement(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Showing devices for')]")
								.getAttribute("value").contains("United States")) {
							Keyword.ReportStep_Pass(testCase, "Successfully changed country to United States");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to change country to United States");
						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change country to United States");
					}
				}
			}
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & LyricUtils.selectDeviceToInstall(testCase, "Smart Home Security");
		} 
		return flag;

		/*if (this.isSmartHomeSecurityButtonVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SmartHomeSecurity");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Smart Home Security")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "SmartHomeSecurity");
			}
			return false;
		}*/
	}

	public boolean verifyChooseLocationHeaderTitle() {
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
	
	public boolean clickYesButtonInCancelPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButtonInCancelSetUpPopup");
	}

	public boolean isHomeLocationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectHomeLocation", 3);
	}

	public boolean clickOnHomeLocation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SelectHomeLocation");
	}

	public boolean verifyNameYourBaseStationHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameYourBaseStationHeaderTitle");
	}

	public boolean isLivingRoomBaseStationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectLivingRoomBaseStation", 3);
	}

	public boolean clickOnLivingRoomBaseStation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SelectLivingRoomBaseStation");
	}

	public boolean verifyPowerYourBaseStationHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PowerYourBaseStationHeaderTitle");
	}

	public boolean isNextButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton");
	}

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
	}

	public boolean waitForLookingForBaseStationProgressBarToComplete() {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(objectDefinition, testCase,
								"LookingForBaseStationLoadingSpinner", 5)) {
							System.out.println("Waiting for Looking for Base station loading spinner to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Looking for Base station loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for base station loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public boolean verifyRegisterBaseStationHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RegisterBaseStationHeaderTitle");
	}

	public boolean isQRCodeDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "QRCode");
	}
	
	public boolean verifyMultipleBaseStationsSubHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MultipleBaseStationsScreenSubHeaderTitle");
	}
	
	public boolean selectABaseStation(TestCases testCase,
			TestCaseInputs inputs, String macID) {

		String locatorType, locatorValue;
		HashMap<String, MobileObject> fieldObjects = MobileUtils
				.loadObjectFile(testCase, "DAS_InstallationScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String displayedText = MobileUtils.getMobElement(fieldObjects,
					testCase, "ToolbarTitle", false).getAttribute("text");
			if (displayedText.equals("Register Base Station")) {
				Keyword.ReportStep_Pass(testCase,
						"Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "xpath";
			locatorValue = "//android.widget.TextView[@text='" + macID
					+ "']";
		} else {
			if (MobileUtils
					.isMobElementExists(
							"name",
							"Scan this code by showing it to your Base Station\u2019s camera.",
							testCase, 2, false)) {
				Keyword.ReportStep_Pass(testCase,
						"Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "name";
			locatorValue = macID;
		}

		if (MobileUtils.isMobElementExists(locatorType, locatorValue,
				testCase, 2)) {
			flag = flag
					& MobileUtils.clickOnElement(testCase, locatorType,
							locatorValue);
		} else {
			int i = 0;
			while (!MobileUtils.isMobElementExists(locatorType,
					locatorValue, testCase, 2)) {
				String locType;
				String locVal;
				if (testCase.getPlatform().toUpperCase()
						.contains("ANDROID")) {
					locType = "id";
					locVal = "diy_wld_list";
				} else {
					locType = "xpath";
					locVal = "//XCUIElementTypeCollectionView";
				}
				LyricUtils.scrollList(testCase, locType, locVal);
				i++;
				if (i > 5) {
					break;
				}
			}

			if (MobileUtils.isMobElementExists(locatorType, locatorValue,
					testCase, 2)) {
				flag = flag
						& MobileUtils.clickOnElement(testCase, locatorType,
								locatorValue);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase,
						FailType.FUNCTIONAL_FAILURE, "MAC ID : " + macID
								+ " not present");
			}
		}
		return flag;
	}

	public boolean scanQRCode() {
		return true;
	}

	public boolean waitForLookingForNetworkConnectionProgressBarToComplete() {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(objectDefinition, testCase,
								"LookingForNetworkConnectionLoadingSpinner", 5)) {
							System.out.println("Waiting for Looking for Wi-Fi list loading spinner to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Looking for Wi-Fi list loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for Wi-Fi list loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public boolean verifyConnectToNetworkHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectoNetworkHeaderTitle");
	}

	public boolean verifyConnectToNetworkHeaderDesc() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConnectoNetworkHeaderDesc");
	}

	public boolean isAddANetworkButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddANetworkButton");
	}

	public boolean isAvailableNetworkVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AvailableNetworkNameInTheWiFiList");
	}

	public static boolean scrollWifiList(TestCases testCase) {
		boolean flag = true;
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollList(testCase, "id", "wld_wifi_name");
			} else {
				LyricUtils.scrollList(testCase, "xpath", "//XCUIElementTypeCollectionView");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public boolean selectWifi(String wifiName) {

		boolean flag = true;
		String locatorType, locatorValue;
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locatorType = "xpath";
				locatorValue = "//android.widget.TextView[@text='" + wifiName + "']";
			} else {
				locatorType = "name";
				locatorValue = wifiName;
			}
			if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2, false)) {
				flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
			} else {
				flag = flag & scrollWifiList(testCase);
				if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2, false)) {
					flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
				} else {
					flag = flag & scrollWifiList(testCase);
					if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2, false)) {
						flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
					} else {
						flag = flag & scrollWifiList(testCase);
						if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2, false)) {
							flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not find WiFi from the list");
						}
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public boolean isWiFiPasswordTextFieldVisibile() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WiFiPasswordTextField");
	}
	
	public boolean enterWiFiPassword(String password) {

		flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "WiFiPasswordTextField", password);
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
		{
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
		}
		else
		{
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
	
	public boolean waitForConnectingSmartHomeSecurityProgressBarToComplete() {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(objectDefinition, testCase,
								"ConnectingSmartHomeSecurityLoadingSpinner", 5)) {
							System.out.println("Waiting for Connecting Smart Home Security loading spinner to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Connecting Smart Home Security loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Connecting Smart Home Security loading spinner did not disapper after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}
	
	public boolean verifySmartHomeSecuritySuccessHeaderTitle() {
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
	
	public boolean verifyGeoFencingHeaderTitle() {
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
	
	public boolean verifyAmazonAlexaHeaderTitle() {
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
	
	public boolean verifyDashboardScreenTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "");
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
	
	public boolean deleteDASDeviceThroughApp(TestCases testCase,
			TestCaseInputs inputs, String dasDeviceName) {
		boolean flag = true;
		flag = flag
				& LyricUtils.navigateToDASSettings(testCase,
						dasDeviceName);
		String locatorType;
		String locatorValue;
		HashMap<String, MobileObject> fieldObjects = MobileUtils
				.loadObjectFile(testCase, "DAS_InstallationScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			locatorType = "id";
			locatorValue = "das_settings_recyclerview";
		} else {
			locatorType = "xpath";
			locatorValue = "//XCUIElementTypeTable";
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase,
				"BaseStationSettingsOption", 3, false)) {
			flag = flag
					& MobileUtils.clickOnElement(fieldObjects, testCase,
							"BaseStationSettingsOption");
		} else {
			LyricUtils.scrollList1(testCase, locatorType, locatorValue);
			if (MobileUtils.isMobElementExists(fieldObjects, testCase,
					"BaseStationSettingsOption", 3, false)) {
				flag = flag
						& MobileUtils.clickOnElement(fieldObjects, testCase,
								"BaseStationSettingsOption");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not find Base Station Settings options");
				return flag;
			}
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase,
				"DeleteDASDeviceButton", 5, false)) {
			flag = flag
					& MobileUtils.clickOnElement(fieldObjects, testCase,
							"DeleteDASDeviceButton");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase,
					"YesButton", 5, false)) {
				flag = flag
						& MobileUtils.clickOnElement(fieldObjects, testCase,
								"YesButton");
			} else {
				flag = flag
						& MobileUtils.clickOnElement(fieldObjects, testCase,
								"DeleteDASDeviceButton");
				if (MobileUtils.isMobElementExists(fieldObjects, testCase,
						"YesButton", 5, false)) {
					flag = flag
							& MobileUtils.clickOnElement(fieldObjects,
									testCase, "YesButton");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase,
							FailType.FUNCTIONAL_FAILURE,
							"Could not find Yes option in pop up");
				}
				return flag;
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Could not find Base Station Settings options");
			return flag;
		}

		return flag;
	}
	
	public boolean createPasscodeAfterDIYRegistration(TestCases testCase, TestCaseInputs inputs) {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "PasscodePopUpTitle",5))
		{
			if(!inputs.isInputAvailable("PASSCODE"))
			{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Passcode not provided in inputs");
				return flag;
			}
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CreatePasscodeButton");
			flag = flag & LyricUtils.createPasscode(testCase, inputs.getInputValue("PASSCODE"));
		}
		else
		{
			Keyword.ReportStep_Pass(testCase, "Passcode not required");
		}
		return flag;
	
	}
}
