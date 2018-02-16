package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class SecondaryCardSettings extends MobileScreens{

		private static final String screenName = "LoginScreen";
		public static final String GEOFENCE = "geofence";
		public static final String ALERTSANDNOTIFICATIONS = "alertsandnotifications";
		public static final String MANAGEUSERS = "manageusers";
		public static final String HOMEKITUSERS = "homekitusers";
		public static final String ZWAVEDEVICES = "zwavedevices";
		public static final String ADDNEWDEVICE = "addnewdevice";
		public static final String APPSETTINGSANDINFO = "appsettingsandinfo";
		public static final String MYACCOUNT = "myaccount";
		public static final String MESSAGES = "messages";
		public static final String LOGOUT = "logout";
		
		public SecondaryCardSettings(TestCases testCase) {
			super(testCase,screenName);
			//osPopUps = new OSPopUps(testCase);
		}
		
		public boolean selectOptionFromSecondarySettings(String value)
		{
			if(value.equals(SecondaryCardSettings.GEOFENCE))
			{
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceOption");
			}
			else if(value.equals(SecondaryCardSettings.ALERTSANDNOTIFICATIONS))
			{
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AlertsAndNotificationsOption");
			}
			else if(value.equals(SecondaryCardSettings.ADDNEWDEVICE))
			{
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceOption");
			}
			else if(value.equals(SecondaryCardSettings.ZWAVEDEVICES))
			{
				return MobileUtils.clickOnElement(objectDefinition, testCase, "ZWaveDevicesOption");
			}
			return false;
		}
		
}
