package com.honeywell.screens;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class GeofenceSettings extends MobileScreens {

	private static final String screenName = "GeofenceSettings";
	public static final String ENABLEGEOFENCETHISLOCATION = "Geofence this Location - Enable";
	public static final String DISABLEGEOFENCETHISLOCATION = "Geofence this Location - Disable";

	public static final String GEOFENCERADIUS = "Geofence Radius";
	public static final String LOCATIONSTATUS = "Location Status";
	public static final String ENABLEGEOFENCEALERT = "Geofence Alert - Enable";
	public static final String DISABLEGEOFENCEALERT = "Geofence Alert - Disable";

	public GeofenceSettings(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isGeofenceSettingsScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceSettingsScreenTitle");
	}

	public boolean selectOptionFromGeofenceSettings(String option) {
		switch (option) {
		case GeofenceSettings.ENABLEGEOFENCETHISLOCATION: {
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals(1)) {
				return true;
			} else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
			}
		}
		case GeofenceSettings.DISABLEGEOFENCETHISLOCATION: {
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals(0)) {
				return true;
			} else {
				MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DisablingGeofencingPopupTitle")) {
					Keyword.ReportStep_Pass(testCase, "Disabling Geofencing popup is displayed.");
					MobileUtils.clickOnElement(objectDefinition, testCase, "DisablingGeofencingOK");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Disabling Geofencing popup is not displayed.");
					return false;
				}
				return true;
			}
		}
		case GeofenceSettings.GEOFENCERADIUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceRadius");
		case GeofenceSettings.LOCATIONSTATUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HomekitUsersOption");
		case GeofenceSettings.ENABLEGEOFENCEALERT:
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals(1)) {
				return true;
			} else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceAlert");
			}
		case GeofenceSettings.DISABLEGEOFENCEALERT:
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals(0)) {
				return true;
			} else {
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

	public boolean isGeofenceRadiusOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceRadius");
	}

	public boolean isLocationStatusOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationStatus");
	}

	public boolean isGeofenceAlertOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceAlert");
	}

	public boolean clickOnGeofenceRadiusSetting() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SecondaryCardSettings");
	}

	public boolean isGeofencingthislocationTextvisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceThisLocationText");
	}

	public boolean isGeofencingthislocationDescriptionvisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceThisLocationDescription");
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isGeofenceThisLocationSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceThisLocationToggle") && MobileUtils
					.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals("ON")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceThisLocationToggle")
					&& MobileUtils.getMobElement(objectDefinition, testCase, "GeofenceThisLocationToggle")
							.getAttribute("value").equals("1")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

}
