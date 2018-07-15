package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class GeofenceSettings extends MobileScreens {

	private static final String screenName = "GeofenceSettings";
	public static final String ENABLEGEOFENCETHISLOCATION = "Geofence this Location - Eanble";
	public static final String DISABLEGEOFENCETHISLOCATION = "Geofence this Location - Disable";

	public static final String GEOFENCERADIUS = "Geofence Radius";
	public static final String LOCATIONSTATUS = "Location Status";
	public static final String ENABLEGEOFENCEALERT = "Geofence Alert - Eanble";
	public static final String DISABLEGEOFENCEALERT = "Geofence Alert - Disable";

	public GeofenceSettings(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean selectOptionFromGeofenceSettings(String option) {
		switch (option) {
		case GeofenceSettings.ENABLEGEOFENCETHISLOCATION: {
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals(1)){
				return true;
			}else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
			}
		}
		case GeofenceSettings.DISABLEGEOFENCETHISLOCATION:
		{
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals(0)){
				return true;
			}else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
			}
		}
		case GeofenceSettings.GEOFENCERADIUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceRadius");
		case GeofenceSettings.LOCATIONSTATUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HomekitUsersOption");
		case GeofenceSettings.ENABLEGEOFENCEALERT:
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals(1)){
				return true;
			}else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceAlert");
			}
		case GeofenceSettings.DISABLEGEOFENCEALERT:
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals(0)){
				return true;
			}else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceAlert");
			}
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

	public boolean clickOnGeofenceRadiusSetting()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SecondaryCardSettings");
	}
	
	public boolean isclickOnBackButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton",3);
	}
	
	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}
}
