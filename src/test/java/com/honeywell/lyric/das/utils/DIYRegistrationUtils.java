package com.honeywell.lyric.das.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.common.DeleteAndRebootDASDevice;
import com.honeywell.lyric.utils.ADBUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;

public class DIYRegistrationUtils {

	public static boolean navigateFromPowerBaseStationToDashboard(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		Dashboard d = new Dashboard(testCase);
		boolean flag = true;
		if (dasDIY.isBackArrowInPowerBaseStationVisible()) {
			flag = flag & dasDIY.clickOnBackArrowInPowerBaseStationScreen();
			System.out.println("#########Clicked on Back arrow in Power base station screen");
			if (dasDIY.isBackArrowInNameYourBaseStationScreenVisible()) {
				flag = flag & dasDIY.clickOnBackArrowInNameYourBaseStationScreen();
				System.out.println("#########Clicked on Back arrow in Name your base station screen");
				if (dasDIY.isBackArrowInChooseLocationScreenVisible()) {
					flag = flag & dasDIY.clickOnBackArrowInChooseLocationScreen();
					System.out.println("#########Clicked on Back arrow in Choose location screen");
					if (dasDIY.isBackArrowInWhatToExpectScreenVisible()) {
						flag = flag & dasDIY.clickOnBackArrowInWhatToExpectScreen();
						System.out.println("#########Clicked on Back arrow in What to Expect screen");
						if (dasDIY.isCancelButtonInAddNewDeviceScreenVisible()) {
							flag = flag & dasDIY.clickOnCancelButtonInAddNewDeviceScreen();
							System.out.println("#########Clicked on Cancel in in Add new device dashboard screen");
							flag = flag & d.isAddDeviceIconVisible(20);
							System.out.println("#########Add new device icon is present in Dashboard screen");
						}
					}
				}
			}
		}
		return flag;
	}

	public static boolean navigateFromPowerBaseStationToLookingForBaseStation(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
		}
		flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
		return flag;
	}

	public static boolean navigateFromPowerBaseStationToRegisterBaseStation(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		int counter = 0;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
		}
		flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
		if (dasDIY.isBaseStationNotFoundPopupVisible(5) && dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible()) {
			while (dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible() && counter < 5) {
				flag = flag & dasDIY.clickOnRetryButtonInBaseStationNotFoundPopup();
				counter++;
				flag = flag
						& DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
			}
		}
		if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
			System.out.println("$$$$$$$$$$$$$$");
			dasDIY.clickOnOKButtonInQRCodeScanningFailurePopup();
		}
		if (dasDIY.isRegisterBaseStationHeaderTitleVisible() && dasDIY.isQRCodeDisplayed()) {
			Keyword.ReportStep_Pass(testCase, "Single base station with Scan QR Code image is displayed");
		}
		return flag;
	}

	public static boolean navigateFromPowerBaseStationToSelectBaseStation(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		int counter = 0;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
		}
		flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
		if (dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible()) {
			while (dasDIY.isRetryButtonInBaseStationNotFoundPopupVisible() && counter < 5) {
				flag = flag & dasDIY.clickOnRetryButtonInBaseStationNotFoundPopup();
				counter++;
				flag = flag
						& DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
			}
		}
		if (dasDIY.isMultipleBaseStationsScreenSubHeaderTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Multiple base stations with MAC ID's are displayed");
		}
		return flag;
	}

	public static boolean navigateFromRegisterBaseStationToConnectToNetwork(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "NETWORK CONNECTION PROGRESS BAR", 1);
		flag = flag & dasDIY.isConnectToNetworkHeaderTitleVisible();
		return flag;
	}

	public static boolean navigateFromConnectToNetworkToSmartHomeSecuritySuccess(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		flag = flag & dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible();
		if (dasDIY.isNoButtonInSmartHomeSecuritySuccessScreenVisible()) {
			flag = flag & dasDIY.clickOnNoButtonInSmartHomeSecuritySuccessScreen();
		}
		return flag;
	}

	public static boolean navigateFromSmartHomeSecuritySuccessToSetUpAccessories(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isYesButtonInSmartHomeSecuritySuccessScreenVisible()) {
			dasDIY.clickYesButtonInSmartHomeSecuritySuccessScreen();
			if (dasDIY.isSetUpAccessoriesScreenTitleVisible(60)) {
				// DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "SENSOR SET UP
				// BUTTON", 1);
			}
		}
		return flag;
	}

	public static boolean navigateFromSetUpAccessoriesToOverview(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isSensorSetUpButtonVisible()) {
			flag = flag & dasDIY.clickOnSensorSetUpButton();
			flag = flag & dasDIY.isOverviewScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromOverviewToLocateSensor(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 1);
			flag = flag & dasDIY.isLocateSensorScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromLocateSensorToNameSensor(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & dasDIY.isNameSensorScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromCheckLocationToCheckLocationSignal(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isCloseDoorSubHeaderTextInCheckLocationScreenVisible() && dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 1);
			flag = flag & dasDIY.isCheckLocationSignalScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromCheckLocationSignalToPrepareSensor(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isSignalStrengthTextVisibleInCheckLocationScreen() && dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & dasDIY.isPrepareSensorScreenTitleVisible(15);
			flag = flag & dasDIY.isUnEvenButtonVisibleInPrepareSensorScreen();
			flag = flag & dasDIY.isEvenButtonVisibleInPrepareSensorScreen();
		}
		return flag;
	}

	public static boolean navigateFromPrepareSensorToPlaceAdhesiveStrips(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isEvenButtonVisibleInPrepareSensorScreen()) {
			flag = flag & dasDIY.clickOnEvenButtonInPrepareSensorScreen();
			flag = flag & dasDIY.isPlaceAdhesiveStripsScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromPlaceAdhesiveStripsToMountSensor(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isPlaceAdhesiveStripsScreenTitleVisible() && dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & dasDIY.isMountSensorScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromMountSensorToSensorReady(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isMountSensorScreenSubHeaderTitleVisible() && dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
			flag = flag & dasDIY.isSensorReadyScreenTitleVisible();
			flag = flag & dasDIY.isSuccessLabelDisplayedInSensorReadyScreen();
		}
		return flag;
	}

	public static boolean navigateFromSensorReadyToSetUpAccConfigured(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNextButtonVisible()) {
			flag = flag & dasDIY.clickOnNextButton();
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "FINISHING UP PROGRESS BAR", 1);
			flag = flag & dasDIY.isSetUpAccessoriesConfiguredScreenTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromSetUpAccConfiguredToEnableGeoFencing(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isConfiguredLabelInSetUpAccessoriesConfiguredScreenVisible()
				&& dasDIY.isSaveButtonInSetUpAccessoriesConfiguredScreenVisible()) {
			flag = flag & dasDIY.clickOnSaveButtonInSetUpAccessoriesConfiguredScreen();
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "FINISHING UP PROGRESS BAR", 1);
			if (dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible()) {
				flag = flag & dasDIY.clickOnNoButtonInSmartHomeSecuritySuccessScreen();
			}
			flag = flag & dasDIY.isGeoFencingHeaderTitleVisible(5);
		}
		return flag;
	}

	public static boolean navigateFromEnableGeoFencingToGeoFence(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isGeoFencingHeaderTitleVisible(10) && dasDIY.isEnableButtonInGeoFencingScreenVisible()) {
			flag = flag & dasDIY.clickOnEnableButtonInGeoFencingScreen();
			flag = flag & dasDIY.isGeoFenceScreenHeaderTitleVisible();
		}
		return flag;
	}

	public static boolean navigateFromGeoFenceToGeoFenceEnabled(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isCancelButtonInGeoFenceScreenVisible() && dasDIY.isUpdateGeoFenceButtonInGeoFenceScreenVisible()
				&& dasDIY.isSaveButtonInGeoFenceScreenVisible()) {
			flag = flag & dasDIY.clickOnSaveButtonInGeoFenceScreen();
			flag = flag
					& DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "SAVING GEOFENCE PROGRESS BAR", 1);
			flag = flag & dasDIY.isGeoFenceEnabledScreenHeaderTitleVisible(15);
		} else {
			flag = false;
			if (dasDIY.isUpdateGeoFenceButtonInGeoFenceScreenVisible()
					&& dasDIY.isSaveButtonInGeoFenceScreenVisible()) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Cancel button is not displayed in Geofence screen");
				flag = flag & dasDIY.clickOnSaveButtonInGeoFenceScreen();
				flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase,
						"SAVING GEOFENCE PROGRESS BAR", 1);
				flag = flag & dasDIY.isGeoFenceEnabledScreenHeaderTitleVisible(15);
			}
		}
		return flag;
	}

	public static boolean navigateFromGeoFenceEnabledToEnableAmazonAlexa(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isGeoFenceEnabledScreenSubHeaderTitleVisible()
				&& dasDIY.isSaveButtonGeoFenceEnabledScreenVisible()) {
			flag = flag & dasDIY.clickOnSaveButtonGeoFenceEnabledScreen();
			flag = flag & dasDIY.isAmazonAlexaHeaderTitleVisible();
			flag = flag & dasDIY.isSkipButtonInAmazonAlexaVisible();
			flag = flag & dasDIY.isEnableButtonInAmazonAlexaVisible();
		}
		return flag;
	}

	public static boolean navigateFromSmartHomeSecuritySuccessToEnableGeoFencing(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		flag = flag & dasDIY.isSmartHomeSecuritySuccessHeaderTitleVisible();
		if (dasDIY.isNoButtonInSmartHomeSecuritySuccessScreenVisible()) {
			flag = flag & dasDIY.clickOnNoButtonInSmartHomeSecuritySuccessScreen();
		}
		return flag;

	}

	public static boolean navigateFromEnableGeoFencingToEnableAmazonAlexa(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		flag = flag & dasDIY.isGeoFencingHeaderTitleVisible(5);
		if (dasDIY.isSkipButtonInGeoFencingScreenVisible()) {
			flag = flag & dasDIY.clickOnSkipButtonInGeoFencingScreen();
		}
		return flag;
	}

	public static boolean navigateFromEnableAmazonAlexaToPeopleDetection(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isAmazonAlexaHeaderTitleVisible() && dasDIY.isSkipButtonInAmazonAlexaVisible()) {
			flag = flag & dasDIY.clickOnSkipButtonInAmazonAlexaScreen();
		}
		return flag;
	}

	public static boolean navigateFromPeopleDetectionToDashboard(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		Dashboard d = new Dashboard(testCase);
		boolean flag = true;
		if (dasDIY.isPeopleDetectionHeaderTitleVisible() && dasDIY.isNotNowButtonInPeopleDetectionScreenVisible()) {
			flag = flag & dasDIY.clickOnNotNowButtonInPeopleDetectionScreen();
		}
		if (dasDIY.isSkipButtonInHoneywellMembershipScreenVisible()) {
			flag = flag & dasDIY.clickOnSkipButtonInHoneywellMembershipScreen();
		}
		if (dasDIY.isIncreaseSecurityPopupVisible()) {
			// flag = flag & LyricUtils.closeCoachMarks(testCase);
			if (dasDIY.isIncreaseSecurityPopupVisible()) {
				flag = flag & dasDIY.clickOnDontUseButtonInIncreaseSecurityPopup();
				flag = flag & LyricUtils.closeCoachMarks(testCase);
			}
		} else if (d.isIncreaseSecurityPopupVisible()) {
			flag &= d.clickOnDontUseButtonInIncreaseSecurityPopup();
			flag = flag & LyricUtils.closeCoachMarks(testCase);
		} else {
			flag = flag & LyricUtils.closeCoachMarks(testCase);
		}
		return flag;
	}

	public static boolean selectAvaiableLocation(TestCases testCase, String availableLocation) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isChooseLocationHeaderTitleVisible() && dasDIY.isAvialbleLocationNameDisplayed(availableLocation)) {
			flag = flag & dasDIY.clickOnAvailableLocationName(availableLocation);
		}
		return flag;
	}

	public static boolean inputNewLocationName(TestCases testCase, String newLocationName) {

		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isCreateLocationHeaderTitleVisible() && dasDIY.isCustomLocationTextFieldVisible()) {
			flag = flag & dasDIY.enterCustomLocationName(newLocationName);
		}
		return flag;
	}

	public static boolean verifyIfMaxCharsEnteredInCustomNameTxtField(TestCases testCase, TestCaseInputs inputs,
			int maxAllowedCharsLength, String enteredMaxChars) {

		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		String valueDisplayedInCustomNameTxtField = dasDIY.getValueDisplayedInCustomNameTxtField();

		if (enteredMaxChars.length() <= maxAllowedCharsLength) {

			if (valueDisplayedInCustomNameTxtField.equalsIgnoreCase(enteredMaxChars)) {
				Keyword.ReportStep_Pass(testCase,
						valueDisplayedInCustomNameTxtField + " is correctly displayed with character length: "
								+ valueDisplayedInCustomNameTxtField.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, valueDisplayedInCustomNameTxtField
						+ " entered is of character length: " + valueDisplayedInCustomNameTxtField.length());
			}
		} else if (enteredMaxChars.length() > maxAllowedCharsLength) {

			if ((!valueDisplayedInCustomNameTxtField.equalsIgnoreCase(enteredMaxChars))
					&& (valueDisplayedInCustomNameTxtField.length() <= maxAllowedCharsLength)) {
				Keyword.ReportStep_Pass(testCase,
						enteredMaxChars + " is trimmed to " + valueDisplayedInCustomNameTxtField
								+ " with max allowed character length: " + valueDisplayedInCustomNameTxtField.length());
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						enteredMaxChars + " is not trimmed to " + valueDisplayedInCustomNameTxtField
								+ " with max allowed character length: " + valueDisplayedInCustomNameTxtField.length());
			}
		}
		return flag;
	}

	public static boolean clickOnNotPulsingBlueLink(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNotPulsingBlueLinkVisible()) {
			flag = flag & dasDIY.clickOnNotPulsingBlueLink();
		}
		return flag;
	}

	public static boolean selectAvaiableBaseStationName(TestCases testCase, String availableBaseStationName) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isNameYourBaseStationHeaderTitleVisible()
				&& dasDIY.isAvailableBaseStationNameDisplayed(availableBaseStationName)) {
			flag = flag & dasDIY.clickOnAvailableBaseStationName(availableBaseStationName);
		}
		return flag;
	}

	public static boolean inputNewBaseStationnName(TestCases testCase, String newBaseStationName) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isCreateBaseStationHeaderTitleVisible() && dasDIY.isCustomNameTextFieldDisplayed()) {
			flag = flag & dasDIY.enterCustomNameInNameYourBaseStationScreen(newBaseStationName);
		}
		return flag;
	}

	public static boolean selectABaseStationFromListOfAvailableBaseStations(TestCases testCase,
			String baseStationMACID) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isMACIDVisible(baseStationMACID.toUpperCase())) {
			flag = flag & dasDIY.clickOnMACID(baseStationMACID.toUpperCase());
			flag = flag & DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "BASE STATION PROGRESS BAR", 1);
		}
		return flag;
	}

	public static boolean selectWiFiNameFromTheListOfAvailableNetworks(TestCases testCase, String wifiName)
			throws Exception {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isConnectToNetworkHeaderDescVisible() && dasDIY.isAddANetworkButtonVisible()) {
			flag = flag & dasDIY.clickOnWiFiNameOnWiFiScreen(wifiName);
		}
		return dasDIY.isWiFiPasswordTextFieldVisibile();
	}

	public static boolean selectAvailableNetworkType(TestCases testCase, String availableNetworkType) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isAvialbleNetworkTypeDisplayed(availableNetworkType)) {
			flag = flag & dasDIY.clickOnAvialbleNetworkType(availableNetworkType);
		}
		return flag;
	}

	public static boolean enableAmazonAlexa(TestCases testCase, TestCaseInputs inputs, String amazonUserName,
			String amazonPassword) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isEnableButtonInAmazonAlexaVisible()) {
			dasDIY.clickOnEnableButtonInAmazonAlexaScreen();
			waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 3);
			if (dasDIY.isSignInToAmazonAlexaScreenTitleVisible()
					&& dasDIY.isEmailTextFieldInSignInToAmazonAlexaScreenVisible()
					&& dasDIY.isPasswordTextFieldInSignInToAmazonAlexaScreenVisible()) {
				flag = flag & dasDIY.enterAmazonUserCredentials(amazonUserName, amazonPassword);
				waitForProgressBarToComplete(testCase, "IN PROGRESS BAR", 5);
				if (dasDIY.isAmazonAlexaSetUpCompletedScreenTitleVisible() && dasDIY.isNextButtonVisible()) {
					flag = flag & dasDIY.clickOnNextButton();
					if (dasDIY.isFeatureSetUpCompletedScreenTitleVisible()) {
						flag = flag & dasDIY.clickOnDoneButtonInFeatureSetUpCompletedScreen();
					}
					if (dasDIY.isSkipButtonInHoneywellMembershipScreenVisible()) {
						flag = flag & dasDIY.clickOnSkipButtonInHoneywellMembershipScreen();
					}
				}
			}
		}
		return flag;
	}

	public static boolean selectAvailableSensorName(TestCases testCase, String availableSensorName) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isAvailableSensorNameVisible(availableSensorName)) {
			flag = flag & dasDIY.clickOnAvailableSensorName(availableSensorName);
		}
		return flag;
	}

	public static boolean deleteLocation(TestCases testCase, TestCaseInputs inputs) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		Dashboard d = new Dashboard(testCase);
		boolean flag = true;
		if (dasDIY.isGlobalDrawerButtonVisible()) {
			flag = flag & dasDIY.clickOnGlobalDrawerButton();
			if (dasDIY.isLocationDetailsVisible()) {
				flag = flag & dasDIY.clickOnLocationDetails();
				if (dasDIY.getLocationNameInDetailsScreen().contains(inputs.getInputValue("LOCATION1_NAME"))) {
					if (dasDIY.isDeleteLocationButtonVisible()) {
						flag = flag & dasDIY.clickOnDeleteLocationButton();
						if (dasDIY.isDeleteLocationPopupVisible() && dasDIY.isYesButtonInDeleteLocationPopupVisible()) {
							flag = flag & dasDIY.clickOnYesButtonInDeleteLocationPopup();
							flag = flag & d.isAddDeviceIconVisible(10);
						}
					}
				}
			}
		}
		return flag;
	}

	public static boolean deleteDefaultLocation(TestCases testCase, TestCaseInputs inputs) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (dasDIY.isGlobalDrawerButtonVisible()) {
			flag = flag & dasDIY.clickOnGlobalDrawerButton();
			if (dasDIY.isLocationDetailsVisible()) {
				flag = flag & dasDIY.clickOnLocationDetails();
				if (dasDIY.getLocationNameInDetailsScreen().contains(inputs.getInputValue("LOCATION1_NAME"))) {
					if (dasDIY.isDeleteLocationButtonVisible()) {
						flag = flag & dasDIY.clickOnDeleteLocationButton();
						if (dasDIY.isDeleteLocationPopupVisible() && dasDIY.isYesButtonInDeleteLocationPopupVisible()) {
							flag = flag & dasDIY.clickOnYesButtonInDeleteLocationPopup();
							Keyword.ReportStep_Pass(testCase,
									"Successfully Deleted Location: " + inputs.getInputValue("LOCATION1_NAME"));
							flag = flag & dasDIY.isAddNewDeviceScreenVisible(10);
						}
					}
				}
			}
		}
		return flag;
	}

	public static boolean deleteSensorThroughCHIL(TestCases testCase, TestCaseInputs inputs, String sensorName) {
		boolean flag = true;
		try {
			System.out.println("%%%%%%%%%%%%sensorName: " + sensorName);
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if (chUtil.getConnection()) {
				try {
					String sensorID = deviceInfo.getDASSensorID(sensorName);
					System.out.println("%%%%%%%%%%%%sensorID: " + sensorID);
					int result = chUtil.deleteSensor(locInfo.getLocationID(), deviceInfo.getDASSensorID(sensorName),
							sensorName, 1);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "Successfully deleted DAS Device");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean deleteDASDeviceThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>("");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(90, TimeUnit.SECONDS);
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			// inputs.setInputValue("LOCATION1_NAME", locationName, false);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			if (chUtil.getConnection()) {
				try {
					int result = chUtil.deleteDevice(locInfo.getLocationID(), locInfo.getDASDeviceID(), false);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "Successfully deleted DAS Device");
						try {
							Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
								public Boolean apply(String a) {

									try {
										LocationInformation locInfo = new LocationInformation(testCase, inputs);
										if (locInfo.getNumberOfDeviceInLocation() == 0) {
											return true;
										} else {
											System.out.println(
													"Waiting for device to get deleted from CHIL. Number of devices : "
															+ locInfo.getNumberOfDeviceInLocation());
											return false;
										}
									} catch (Exception e) {
										return false;
									}
								}
							});
							if (isEventReceived) {
								Keyword.ReportStep_Pass(testCase,
										"Device successfully deleted through CHIL without Client header");
								fWait.withTimeout(30, TimeUnit.SECONDS);
								try {
									fWait.until(new Function<String, Boolean>() {
										public Boolean apply(String a) {
											try {
												if (ADBUtils.isDevicePresentInADBDevices(
														inputs.getInputValue("DAS_DEVICE_UDID"))) {
													System.out.println("Waiting for device to reboot");
													return false;
												} else {
													System.out.println("Device rebooted");
													return true;
												}
											} catch (Exception e) {
												return false;
											}
										}
									});
								} catch (TimeoutException e) {
								} catch (Exception e) {
								}
							}
						} catch (TimeoutException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Device not deleted from CHIL. Wait Time = 1 minute");
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured : " + e.getMessage());
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to delete DAS device. Response Code : " + result);
						result = chUtil.deleteDevice(locInfo.getLocationID(), locInfo.getDASDeviceID(), true);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase, "Successfully deleted DAS Device using Client header");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to delete DAS device. Response Code : " + result);
						}
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
				}

				if (!inputs.getInputValue("LOCATION1_NAME").equals("Home")) {
					try {
						int result = chUtil.deleteLocation(locInfo.getLocationID());
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully deleted location : " + inputs.getInputValue("LOCATION1_NAME"));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to delete location : " + inputs.getInputValue("LOCATION1_NAME")
											+ ". Response Code : " + result);
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForProgressBarToComplete method waits until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-08
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "BASE STATION PROGRESS BAR": {
							if (dasDIY.isLookingForBaseStationProgressBarVisible()) {
								System.out.println("Waiting for progress bar loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "QRCODE SCANNING FAILURE POPUP": {
							if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
								System.out.println("Waiting for QR Code Scanning Failure popup to display");
								return true;
							} else {
								return false;
							}
						}
						case "WAIT UNTIL DAS REBOOT": {
							long waitTime = System.currentTimeMillis() - DeleteAndRebootDASDevice.rebootStartTimer;
							if (waitTime > 90000) {
								System.out.println("90 seconds complete after reboot");
								return true;
							} else {
								System.out.println("Waiting for 90 seconds after reboot command. " + waitTime / 1000
										+ " seconds completed.");
								return false;
							}

						}
						case "NETWORK CONNECTION PROGRESS BAR": {
							if (dasDIY.isLookingForNetworkConnectionProgressBarVisible()) {
								System.out.println("Waiting for Looking for Wi-Fi list loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "SMART HOME SECURITY PROGRESS BAR": {
							if (dasDIY.isConnectingSmartHomeSecurityLoadingSpinnerVisible()) {
								System.out.println(
										"Waiting for Connecting Smart Home Security loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "ALMOST DONE LOADING PROGRESS BAR TEXT": {
							if (dasDIY.isAlmostDoneLoadingSpinnerTextVisible()) {
								System.out.println("Waiting for Almost Done loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "SENSOR SET UP BUTTON": {
							if (!dasDIY.isSensorSetUpButtonVisible()) {
								System.out.println("Waiting for Sensor Set Up button to be displayed");
								return false;
							} else {
								return true;
							}
						}
						case "IN PROGRESS BAR": {
							if (dasDIY.isInProgressLoadingSpinnerTextVisible()) {
								System.out.println("Waiting for In progress loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "SAVING SENSOR PROGRESS BAR": {
							if (dasDIY.isSavingSensorLoadingSpinnerTextVisible()) {
								System.out.println("Waiting for Saving Sensor loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "LOADING SPINNER BAR": {
							if (dasDIY.isVerifyingLoadingSpinnerTextVisible()) {
								System.out.println("Waiting for Verifying loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "FINISHING UP PROGRESS BAR": {
							if (dasDIY.isFinishingUpLoadingSpinnerTextVisible()) {
								System.out.println("Waiting for Finishing up loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "SAVING GEOFENCE PROGRESS BAR": {
							if (dasDIY.isEnablingGeoFencingLoadingProgressBarVisible()) {
								System.out.println("Waiting for Saving Geofencing loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "DELETING LOCATION PROGRESS BAR": {
							if (dasDIY.isDeletingLocationLoadingProgressBarVisible()) {
								System.out.println("Waiting for Deleting Location loading spinner to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "SIGNAL TO BASE STATION: HIGH": {
							if (dasDIY.isSignalStrengthIsHighLabelVisibleInSignalStrengthScreen()) {
								System.out.println("Waiting for SIGNAL TO BASE STATION: HIGH text to appear");
								return true;
							} else if (dasDIY.isSignalStrengthIsLowLabelVisibleInSignalStrengthScreen()) {
								System.out.println("SIGNAL TO BASE STATION: LOW text is still displayed");
								return true;
							} else {
								return false;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + elementProgressBar);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Progress bar loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Select a base station</h1>
	 * <p>
	 * The selectABaseStation method selects a base station from the list of
	 * stations displayed.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @param value
	 *            Value of the attribute used to locate the element
	 * @return boolean Returns 'true' if the element is found. Returns 'false' if
	 *         the element is not found.
	 */
	public static boolean selectABaseStation(TestCases testCase, TestCaseInputs inputs, String macID) throws Exception {
		boolean flag = true;
		String locatorType, locatorValue;
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String displayedText = dasDIY.getToolBarTitleInRegisterBaseStationScreen();
			if (displayedText.equals("Register Base Station")) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "xpath";
			locatorValue = "//android.widget.TextView[@text='" + macID + "']";
		} else {
			if (dasDIY.isSingleBaseStationDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "name";
			locatorValue = macID;
		}

		if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
			flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
		} else {
			int i = 0;
			while (!MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				String locType;
				String locVal;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					locType = "id";
					locVal = "diy_wld_list";
				} else {
					locType = "xpath";
					locVal = "//XCUIElementTypeCollectionView";
				}
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, locType, locVal);
				i++;
				if (i > 5) {
					break;
				}
			}

			if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "MAC ID : " + macID + " not present");
			}
		}
		return flag;
	}

	/**
	 * <h1>Scan Invalid QR code</h1>
	 * <p>
	 * The scanQRCode method is to scan QR code
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-22
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the QR code scanning failure popup gets
	 *         displayed. Returns 'false' if the QR code scanning failure popup does
	 *         not display.
	 */
	public static boolean scanInvalidQRCode(TestCases testCase) {

		JFrame frame = new JFrame();
		boolean flag = true;
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		try {
			String screenShotPath = System.getProperty("user.dir") + File.separator + "QRCodes" + File.separator
					+ "InvalidQRCode.png";

			BufferedImage picture = ImageIO.read(new File(screenShotPath));
			ImageIcon imageIcon = new ImageIcon(
					new ImageIcon(picture).getImage().getScaledInstance(500, 700, Image.SCALE_DEFAULT));
			JLabel label = new JLabel(imageIcon);
			frame.add(label);
			frame.pack();
			frame.setAlwaysOnTop(true);
			frame.setVisible(true);

			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					try {
						if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
							return true;
						} else {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Scanning Failure QR code popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Scanning Failure QR code popup is not displayed");
			}
			frame.setVisible(false);
			frame.dispose();
		} catch (IOException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			frame.setVisible(false);
			frame.dispose();
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			frame.setVisible(false);
			frame.dispose();
		}
		return flag;

	}

	/**
	 * <h1>Scan QR code</h1>
	 * <p>
	 * The scanQRCode method is to scan QR code
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-22
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the QR code is scanned. Returns 'false' if
	 *         the QR code scan is unsuccessful.
	 */
	public static boolean scanQRCode(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (testCase.isTestSuccessful()) {
			JFrame frame = new JFrame();
			try {
				String screenShotPath = LyricUtils.takeScreenShot(
						System.getProperty("user.dir") + File.separator + "QRCodes", testCase.getMobileDriver());
				screenShotPath = System.getProperty("user.dir") + File.separator + "QRCodes" + File.separator
						+ screenShotPath;

				BufferedImage picture = ImageIO.read(new File(screenShotPath));
				ImageIcon imageIcon = new ImageIcon(
						new ImageIcon(picture).getImage().getScaledInstance(500, 700, Image.SCALE_DEFAULT));
				JLabel label = new JLabel(imageIcon);
				frame.add(label);
				frame.pack();
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
				testCase.startTimer("QRCodeScanningTimer");

				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
				fWait.pollingEvery(5, TimeUnit.SECONDS);
				fWait.withTimeout(1, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (dasDIY.isConnectToNetworkHeaderDescVisible()) {
									String displayedText = dasDIY.getToolBarTitleInConnectToNetworkScreen();
									if (displayedText.equals("Connect")) {
										return true;
									} else {
										return false;
									}
								} else if (dasDIY.isLookingForNetworkConnectionProgressBarVisible()) {
									return true;
								} else {
									return false;
								}
							} else {
								if (dasDIY.isConnectToNetworkHeaderDescVisible()) {
									return true;
								} else {
									return false;
								}
							}
						} catch (Exception e) {
							return false;
						}
					}
				});
				if (isEventReceived) {
					Keyword.ReportStep_Pass(testCase, "Successfully scanned QR code");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "QR code not scanned");
				}
				testCase.stopTimer("QRCodeScanningTimer", "", "", "");
				frame.setVisible(false);
				frame.dispose();
			} catch (TimeoutException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to scan QR code");
				frame.setVisible(false);
				frame.dispose();
			} catch (IOException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				frame.setVisible(false);
				frame.dispose();
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				frame.setVisible(false);
				frame.dispose();
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Scenario steps failed already, hence skipping the verification");
		}
		return flag;
	}

	/**
	 * <h1>Minimize and Maximize the app</h1>
	 * <p>
	 * The minimizeAndMaximizeTheApp method is to minimize and maximize the app
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-01
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app is minimized and maximized. Returns
	 *         'false' if unsuccessful.
	 */
	public static boolean minimizeAndMaximizeTheApp(TestCases testCase) {
		boolean flag = true;
		MobileUtils.minimizeApp(testCase, 5);
		return flag;
	}
}
