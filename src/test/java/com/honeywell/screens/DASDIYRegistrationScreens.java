package com.honeywell.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class DASDIYRegistrationScreens extends MobileScreens {

	private static final String screenName = "DIY_Registration";

	public DASDIYRegistrationScreens(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isSmartHomeSecurityButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SmartHomeSecurity", 3);
	}

	public boolean clickOnSmartHomeSecurityButton() throws Exception {

		if (this.isSmartHomeSecurityButtonVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SmartHomeSecurity");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Smart Home Security")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "SmartHomeSecurity");
			}
			return false;
		}
	}

	public boolean verifyChooseLocationHeaderTitle() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChooseLocationHeaderTitle");
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
				LyricUtils.scrollList(testCase, "id", "diy_wld_list");
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
}
