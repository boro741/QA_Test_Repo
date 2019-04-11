package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class OSPopUps extends MobileScreens {

	private static final String screenName = "OSPopUps";

	public OSPopUps(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAllowButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton", 3, false);
	}

	public boolean isAllowButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton", timeOut, false);
	}

	public boolean isAlwaysAllowButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlwaysAllowButton", timeOut, false);
	}

	public boolean clickOnAllowButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowButton");
	}

	public boolean clickOnAlwaysAllowButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlwaysAllowButton");
	}

	public boolean isOkButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton", 3, false);
	}

	public boolean isOkButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton", timeOut, false);
	}

	public boolean clickOnOkButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}

	public boolean isCancelButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", 3, false);
	}

	public boolean isCancelButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton", timeOut, false);
	}

	public boolean clickOnCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean isNotNowButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButton", 3, false);
	}

	public boolean isNotNowButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotNowButton", timeOut, false);
	}

	public boolean clickOnNotNowButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NotNowButton");
	}

	public boolean isCloseButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButton", 3, false);
	}

	public boolean isCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButton", timeOut, false);
	}

	public boolean clickOnCloseButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButton");
	}

	public boolean isIgnoreButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IgnoreButton", timeOut);
	}

	public boolean clickOnIgnoreButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "IgnoreButton");
	}

	public boolean isRootedDevicePopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RootedDevicePopup", timeOut, false);
	}

	public boolean clickAcceptOnRootedDevicePopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RootedDevicePopup");
	}

	public boolean isUpdateLocationPermissionsPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateLocationPermissionsPopup");
	}

	public boolean isGoToSettingsButtonVisibleInUpdateLocationPermissionsPopup() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"GoToSettingsButtonInUpdateLocationPermissionsPopup");
	}

	public boolean clickOnGoToSettingsButtonInUpdateLocationPermissionPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"GoToSettingsButtonInUpdateLocationPermissionsPopup");
	}

	public boolean isLocationCellInHoneywellSettingsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationCellInHoneywellSettings");
	}

	public boolean clickOnLocationCellInHoneywellSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LocationCellInHoneywellSettings");
	}

	public boolean isHoneywellButtonInLocationServicesScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HoneywellButtonInLocationServicesScreen");
	}

	public boolean isAlwaysOptionInHoneywellLocationServicesScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"AlwaysOptionInHoneywellLocationServicesScreen");
	}

	public boolean clickOnAlwaysOptionInHoneywellLocationServicesScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlwaysOptionInHoneywellLocationServicesScreen");
	}

	public boolean isAlwaysOptionSelectedInHoneywellLocationServicesScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"AlwaysOptionSelectedInHoneywellLocationServicesScreen");
	}

	public boolean isReturnToHoneywellButtonInHoneywellLocationServicesScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"ReturnToHoneywellButtonInHoneywellLocationServicesScreen");
	}

	public boolean clickOnReturnToHoneywellButtonInHoneywellLocationServicesScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,
				"ReturnToHoneywellButtonInHoneywellLocationServicesScreen");
	}

	public boolean isTurnOnLocationServicesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TurnOnLocationServicesPopup");
	}

	public boolean isSettingsButtonVisibleInTurnOnLocationServicesPopup() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SettingsButton");
	}

	public boolean isSkipButtonVisibleInTurnOnLocationServicesPopup() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SkipButton");
	}

	public boolean clickOnSkipButtonInTurnOnLocationServicesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SkipButton");
	}

	public boolean clickOnSettingsButtonInTurnOnLocationServicesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SettingsButton");
	}

	public boolean isMobileDeviceLocationEnabled() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MobileDeviceLocationEnabled");
	}

	public boolean isMobileDeviceLocationDisabled() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MobileDeviceLocationDisabled");
	}

	public boolean clickToTurnOffMobileDeviceLocation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MobileDeviceLocationEnabled");
	}

	public boolean isAllowHoneywellToAccessDeviceLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowHoneywellToAccessDeviceLocationPopup");
	}

	public boolean isDenyAccessToDeviceLocationButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DenyAccessToDeviceLocation");
	}

	public boolean isAllowAccessToDeviceLocationButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowAccessToDeviceLocation");
	}

	public boolean clickOnDenyAccessToDeviceLocationButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DenyAccessToDeviceLocation");
	}

	public boolean clickOnAllowAccessToDeviceLocationButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowAccessToDeviceLocation");
	}

	public boolean isAllowHoneywellToAccessYourLocationPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowHoneywellToAccessYourLocation",
				timeOut);
	}

	public boolean isGeofencingDescriptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingWillNotWorkUnlessDescription");
	}

	public boolean isOnlyWhileUsingTheAppButtonInAllowHoneywellToAccessYourLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnlyWhileUsingTheAppButton");
	}

	public boolean isDontAllowButtonInAllowHoneywellToAccessYourLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DontAllowButton");
	}

	public boolean isAllowButtonInAllowHoneywellToAccessYourLocationPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton");
	}

	public boolean clickOnOnlyWhileUsingTheAppButtonInAllowHoneywellToAccessYourLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OnlyWhileUsingTheAppButton");
	}

	public boolean clickOnDontAllowButtonInAllowHoneywellToAccessYourLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DontAllowButton");
	}

	public boolean clickOnAllowButtonInAllowHoneywellToAccessYourLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowButton");
	}

	public boolean isHoneywellWouldLikeToSendYouNotificationsPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HoneywellWouldLikeToSendYouNotifications");
	}

	public boolean isDontAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DontAllowButton");
	}

	public boolean isAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowButton");
	}

	public boolean clickOnDontAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DontAllowButton");
	}

	public boolean clickOnAllowButtonInHoneywellWouldLikeToSendYouNotificationsPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowButton");
	}

	public boolean isNotificationsMayIncludeAlertsDescriptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NotificationsMayIncludeAlertsDescription");
	}

	public boolean isAllowHoneywellToAccessThisDevicesLocationPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowHoneywellToAccessDeviceLocationPopup",
				timeOut);
	}

	public boolean isAllowButtonInAllowHoneywellToAccessThisDevicesLocationPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AllowAccessToDeviceLocation", timeOut);
	}

	public boolean isDenyButtonInAllowHoneywellToAccessThisDevicesLocationPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DenyAccessToDeviceLocation", timeOut);
	}

	public boolean clickOnAllowButtonInAllowHoneywellToAccessThisDevicesLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllowAccessToDeviceLocation");
	}

	public boolean clickOnDenyButtonInAllowHoneywellToAccessThisDevicesLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DenyAccessToDeviceLocation");
	}

	public boolean isDeleteInvitedEmailIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteInvitedEmailIcon");
	}

	public boolean clickOnDeleteInvitedEmailIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteInvitedEmailIcon");
	}

	public boolean isDeleteUserPopupLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteUserLabel");
	}

	public boolean isDeleteUserConfirmationTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThisWillDeleteConfirmation");
	}

	public boolean isOkButtonInDeleteUserPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInDeleteUserPopup");
	}

	public boolean isCancelButtonInDeleteUserPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInDeleteUserPopup");
	}

	public boolean clickOnOkButtonInDeleteUserPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInDeleteUserPopup");
	}

	public boolean clickOnCancelButtonInDeleteUserPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInDeleteUserPopup");
	}

	public boolean isDeleteLocationPopupLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteLocationPopup");
	}

	public boolean isDeleteButtonInDeleteLocationPopupLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteButtonInDeleteLocationPopup");
	}

	public boolean isCancelButtonInDeleteLocationPopupLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInDeleteLocationPopup");
	}

	public boolean clickOnDeleteButtonInDeleteLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButtonInDeleteLocationPopup");
	}

	public boolean clickOnCancelButtonInDeleteLocationPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInDeleteLocationPopup");
	}
}