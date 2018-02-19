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
	public static final String MANAGEUSERS = "Manage Users";
	public static final String HOMEKITUSERS = "HomeKit Users";
	public static final String ZWAVEDEVICES = "Z-Wave Devices";
	public static final String ADDNEWDEVICE = "Add New Device";
	public static final String APPSETTINGSANDINFO = "App Settings & Info";
	public static final String MYACCOUNT = "My Account";
	public static final String MESSAGES = "Messages";
	public static final String LOGOUT = "Logout";

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
		case SecondaryCardSettings.HOMEKITUSERS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HomekitUsersOption");
		case SecondaryCardSettings.ZWAVEDEVICES:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ZWaveDevicesOption");
		case SecondaryCardSettings.ADDNEWDEVICE:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceOption");
		case SecondaryCardSettings.APPSETTINGSANDINFO:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AppSettingsAndInfoOption");
		case SecondaryCardSettings.MYACCOUNT:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MyAccountOption");
		case SecondaryCardSettings.MESSAGES:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MessagesOption");
		case SecondaryCardSettings.LOGOUT:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LogoutOption");
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

	public boolean areSecondaryCardSettingsVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecondaryCardSettings", 3);
	}
	
	public boolean areSecondaryCardSettingsVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecondaryCardSettings", timeOut);
	}
	
	public List<WebElement> getSecondaryCardSettings()
	{
		return MobileUtils.getMobElements(objectDefinition, testCase, "SecondaryCardSettings");
	}
}
