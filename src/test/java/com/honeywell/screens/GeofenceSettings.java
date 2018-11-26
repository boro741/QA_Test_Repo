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
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals("1")
					|| MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle")
							.equals("ON")) {
				System.out.println("********Geofence This Location toggle is ON by Default");
				return true;
			} else {
				System.out.println("********Geofence This Location toggle will be turned ON");
				// MobileUtils.clickOnElement(testCase, locatorType, locatorValue, handlerPopUp)
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
			}
		}
		case GeofenceSettings.DISABLEGEOFENCETHISLOCATION: {
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals("0")
					|| MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle")
							.equals("OFF")) {
				System.out.println("********Geofence This Location toggle is OFF by Default");
				return true;
			} else {
				MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
				System.out.println("********Geofence This Location toggle will be turned OFF Now.");
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DisablingGeofencingPopupTitle")) {
					Keyword.ReportStep_Pass(testCase, "Disabling Geofencing popup is displayed.");
					MobileUtils.clickOnElement(objectDefinition, testCase, "DisablingGeofencingOK");
				} else {
					if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle").equals("0")
							|| MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceThisLocationToggle")
									.equals("OFF")) {
						System.out.println(
								"********Geofence This Location toggle is turned OFF. But Disabling Geofence Popup is not displayed.");
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Geofence This Location toggle is turned OFF. But Disabling Geofence Popup is not displayed.");
						return false;
					} else {
						System.out.println(
								"********Geofence This Location toggle is Still Turned ON. It will be turned OFF Now.");
						MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceThisLocationToggle");
						if (MobileUtils.isMobElementExists(objectDefinition, testCase,
								"DisablingGeofencingPopupTitle")) {
							Keyword.ReportStep_Pass(testCase, "Disabling Geofencing popup is displayed.");
							MobileUtils.clickOnElement(objectDefinition, testCase, "DisablingGeofencingOK");
						} else {
							System.out.println(
									"********Disabling Geofence Popup is still not displayed, even after trying to toggle ON Geofence this Location twice.");
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Disabling Geofence Popup is still not displayed, even after trying to toggle ON Geofence this Location twice.");
							return false;
						}
					}
				}
				return true;
			}
		}
		case GeofenceSettings.GEOFENCERADIUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceRadius");
		case GeofenceSettings.LOCATIONSTATUS:
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HomekitUsersOption");
		case GeofenceSettings.ENABLEGEOFENCEALERT:
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals("1")
					|| MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals("ON")) {
				System.out.println("********Geofence Alert toggle is ON by Default");
				return true;
			} else {
				System.out.println("********Geofence Alert toggle will be switched to ON");
				return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceAlert");
			}
		case GeofenceSettings.DISABLEGEOFENCEALERT:
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals("0")
					|| MobileUtils.getFieldValue(objectDefinition, testCase, "GeofenceAlert").equals("OFF")) {
				System.out.println("********Geofence Alert toggle is OFF by Default");
				return true;
			} else {
				System.out.println("********Geofence Alert toggle will be switched to OFF");
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

	public boolean clickOnGeofenceRadiusOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceRadius");
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

	public boolean isGeofenceRadiusScreenTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceRadiusScreenTitle");
	}

	public boolean isBackButtonInGeofenceRadiusScreenVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return flag;
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnBackButtonInGeofenceRadiusScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
				flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
			} else {
				flag = false;
			}
		}
		return flag;
	
	}

	public boolean isUpdateGeofenceCenterButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateGeofenceCenterButton");
	}

	public boolean clickOnUpdateGeofenceCenterButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UpdateGeofenceCenterButton");
	}

	public boolean isUpdateGeofenceCenterPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateGeofenceCenterPopupTitle");
	}

	public boolean isUpdateGeofenceCenterPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateGeofenceCenterPopupMsg");
	}

	public boolean isCancelButtonInUpdateGeofenceCenterPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButtonInUpdateGeofenceCenterPopup");
	}

	public boolean clickOnCancelButtonInUpdateGeofenceCenterPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButtonInUpdateGeofenceCenterPopup");
	}

	public boolean isYesUpdateButtonInUpdateGeofenceCenterPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YesUpdateButtonInUpdateGeofenceCenterPopup");
	}

	public boolean clickOnYesUpdateButtonInUpdateGeofenceCenterPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesUpdateButtonInUpdateGeofenceCenterPopup");
	}

	public boolean isSaveButtonInGeofenceRadiusScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButtonInGeofenceRadiusScreen");
	}

	public boolean clickOnSaveButtonInGeofenceRadiusScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInGeofenceRadiusScreen");
	}
	
	public boolean isCancelGeofenceChangesPopupTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelGeofenceChangesPopupTitle");
	}
	
	public boolean isCancelGeofenceChangesPopupMsgVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelGeofenceChangesPopupMsg");
	}
	
	public boolean isNOButtonInCancelGeofenceChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NOButtonInCancelGeofenceChangesPopup");
	}
	
	public boolean clickOnNOButtonInCancelGeofenceChangesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NOButtonInCancelGeofenceChangesPopup");
	}
	
	public boolean isYESButtonInCancelGeofenceChangesPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "YESButtonInCancelGeofenceChangesPopup");
	}
	
	public boolean clickOnYESButtonInCancelGeofenceChangesPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YESButtonInCancelGeofenceChangesPopup");
	}
	
}
