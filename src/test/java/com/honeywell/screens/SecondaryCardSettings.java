package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class SecondaryCardSettings extends MobileScreens {

	private static final String screenName = "SecondaryCardSettings";
	public static final String GEOFENCE = "Geofence";
	public static final String ALERTSANDNOTIFICATIONS = "Alerts and Notifications";
	public static final String MANAGEUSERS = "Add Users";
	public static final String HOMEKITUSERS = "HomeKit Users";
	public static final String ZWAVEDEVICES = "Z-Wave Devices";
	public static final String ADDNEWDEVICE = "Add New Device";
	public static final String ABOUTTHEAPP = "About the app";
	public static final String EDITACCOUNT = "Edit Account";
	public static final String MESSAGES = "Activity History";
	public static final String LOGOUT = "Logout";
	public static final String ADDRESSDETAILS = "Address";
	public static final String MEMBERSHIPSUBSCRIPTION = "Honeywell Membership";

	public SecondaryCardSettings(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean selectOptionFromSecondarySettings(String option) {
		switch (option) {
		case SecondaryCardSettings.GEOFENCE:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceOption");
		case SecondaryCardSettings.ALERTSANDNOTIFICATIONS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AlertsAndNotificationsOption");
		case SecondaryCardSettings.MANAGEUSERS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ManageUsersOption");
		case SecondaryCardSettings.ADDRESSDETAILS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddressOption");
		case SecondaryCardSettings.HOMEKITUSERS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HomekitUsersOption");
		case SecondaryCardSettings.ZWAVEDEVICES:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ZWaveDevicesOption");
		case SecondaryCardSettings.ADDNEWDEVICE:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceOption");
		case SecondaryCardSettings.ABOUTTHEAPP:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AboutTheAppOption");
		case SecondaryCardSettings.EDITACCOUNT:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "EditAccountOption");
		case SecondaryCardSettings.MESSAGES:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MessagesOption");
		case SecondaryCardSettings.LOGOUT:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LogoutOption");
		case SecondaryCardSettings.MEMBERSHIPSUBSCRIPTION:
			return MobileUtils.clickOnElement(objectDefinition, testCase,

					"HoneywellMembershipOption");
		default: {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + option + "']");
			} else {
				return MobileUtils.clickOnElement(testCase, "value", option);
			}
		}

		}
	}

	public boolean isclickOnBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}

	public boolean clickOnBackButton() {

		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean areSecondaryCardSettingsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecondaryCardSettings", 3);
	}

	public boolean areSecondaryCardSettingsVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecondaryCardSettings", timeOut);
	}

	public boolean isFROptionAvailable(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FaceRecognitionOption", timeOut);
	}

	public List<WebElement> getSecondaryCardSettings() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "SecondaryCardSettings");
	}
}
