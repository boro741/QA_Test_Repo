package com.honeywell.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

public class BaseStationSettingsScreen extends MobileScreens {

	// String values used in the methods
	private static final String screenName = "DASSettings";
	public static final String BASESTATIONCONFIGURATION = "Base Station Configuration";
	public static final String ENTRYEXITDELAYSETTINGS = "Entry/Exit Delay";
	public static final String GEOFENCING = "Geofencing";
	public static final String KEYFOB = "Key Fob";
	public static final String VOLUME = "Base Station Volume";
	public static final String RESETWIFI = "Reset Wi-Fi";
	public static final String SENSORS = "Sensors";
	public static final String AMAZONALEXA = "Amazon Alexa";
	public static final String VIDEOSETTINGS = "Video Settings";
	public static final String MOTIONDETECTION = "Motion Detection";
	public static final String NIGHTVISION = "Night Vision";
	public static final String VIDEOQUALITY = "Video Quality";
	public static final String ZWAVEDEVICES = "Z-Wave Devices";
	public static final String SECURITYMODECHANGE = "Security Mode Change";
	public static final String DOORANDWINDOWS = "Doors and Windows";
	public static final String CAMERASTATUS = "Camera Status";
	public static final String MOTIONEVENT = "Motion Event";
	public static final String EMAILNOTIFICATIONS = "Email Notifications";
	public static final String FROSTPROTECTION = "Frost Protection";
	public static final String HUMIDIFICATION = "Humidification";
	public static final String DEHUMIDIFICATION = "Dehumidification";
	public static final String SLEEPBRIGHTNESSMODE = "Sleep Brightness Mode";
	public static final String SOUND = "Sound";
	public static final String EMERGENCYHEAT = "Emergency Heat";
	public static final String VENTILATION = "Ventilation";
	public static final String ENHANCEDDETERRENCE = "Enhanced Deterrence";
	public static final String OUTDOORMOTIONVIEWERSONINHOMEMODE = "Outdoor motion viewers on in Home";
	// Locator values used in the methods
	public static final String ANDROIDENTRYEXITTABLELOCATORVALUE = "android.widget.RelativeLayout";
	public static final String IOSENTRYEXITTABLELOCATORVALUE = "//XCUIElementTypeCell";
	public static final String ANDROIDTICKMARKLOCATORVALUE = "list_item_lyric_image_view";
	public static final String IOSTICKMARKLOCATORVALUE = "//XCUIElementTypeImage";
	public static final String THERMOSTATCONFIGURATION = "Thermostat Configuration";

	public BaseStationSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean clearDASNameTextBox() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clearTextField(objectDefinition, testCase, "DASNameTextbox");
		} else {
			return MobileUtils.clearTextField(testCase, "XPATH", "//XCUIElementTypeTextField");
		}
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean clickOn15SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "15SecondsOption");
	}

	public boolean clickOn30SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "30SecondsOption");
	}

	public boolean clickOn45SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "45SecondsOption");
	}

	public boolean clickOn60SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "60SecondsOption");
	}

	public boolean clickOnAlexaAppButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlexaAppLink");
	}

	public boolean isSignOutButtonInAmazonAlexaScreenVisible(String labelSignOut) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.isMobElementExists("XPATH",
					"//android.widget.Button[@text='" + labelSignOut + "']", testCase);
		} else {
			flag = flag & MobileUtils.isMobElementExists("NAME", labelSignOut, testCase);
		}
		return flag;
	}

	public boolean clickOnBackButton() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NavBackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "NavBackButton");
		} else {
			return false;
		}
	}

	public boolean clickOnNavBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NavBackButton");
	}

	public boolean clickOnCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean clickOnDeleteDASButton() throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASButton", 3)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteDASButton");
		} else {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
					testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", "Delete Base Station");
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteDASButton");
		}
	}

	public boolean clickOnDeleteSensorButton() throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DASSensorSetting_Delete")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSetting_Delete");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				int startx = (dimensions.width * 20) / 100;
				int starty = (dimensions.height * 62) / 100;
				int endx = (dimensions.width * 22) / 100;
				int endy = (dimensions.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DASSensorSetting_Delete")) {
					return MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSetting_Delete");
				} else {
					LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
				}
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Delete");
			}
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSetting_Delete");
		}
	}

	public boolean clickOnDeleteKeyfobSensorButton() throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobDelete")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "KeyfobDelete");
		} else {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "name", "Delete");
			}
			return MobileUtils.clickOnElement(objectDefinition, testCase, "KeyfobDelete");
		}
	}

	public boolean clickOnModelAndFirmwareOptionsOnBaseStationConfigurationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean clickOnModelAndFirmwareOptionsOnKeyfobSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean clickOnModelAndFirmwareOptionsOnSensorSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean clickOnNoButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NoButton")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
		}
		return flag;
	}

	public boolean clickOnYesButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButton");
		}
		return flag;
	}

	public boolean clickOnAmazonSetUpButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonSetupOption");
	}

	public boolean isAmazonSetUpButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonSetupOption", 3);
	}

	public String getEntryExitTimerValueFromSecuritySettingsScreen() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitTimerValue").getAttribute("text");
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitTimerValue").getAttribute("value");
		}
	}

	public boolean is15SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "15SecondsOption", 3);
	}

	public boolean is30SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "30SecondsOption", 3);
	}

	public boolean is45SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "45SecondsOption", 3);
	}

	public boolean is60SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "60SecondsOption", 3);
	}

	public boolean isAccessSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorDeletePopUpMessage", 3);
	}

	public boolean isAmazonAlexaOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaOption", 3);
	}

	public boolean isAlexaSetUpOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonSetupButton", 3);
	}

	public boolean isAmazonAlexaEmailIdVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonEmailID", 3);
	}

	public boolean isAmazonAlexaPasswordVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonPassword", 3);
	}

	public boolean isAmazonSignInVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonSignInButton", 3);
	}

	public boolean isAmazonSignOutVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaSignOutButton", 3);
	}

	public boolean isBackButtonVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3)) {
			return true;
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return true;
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NavBackButton")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBackButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}

	public boolean isNavBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NavBackButton", 3);
	}

	public boolean isBaseStationConfigurationsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationConfigurationsOption", 3);
	}

	public boolean isBaseStationVolumeValueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeValue", 3);
	}

	public boolean isBaseStationVolumeValueVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeValue", timeOut);
	}

	public boolean isBatteryOptionVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryTitle", 3);
	}

	public boolean isBatteryStatusVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryStatus", 3);
	}

	public boolean isBatteryStatusVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryStatus", 3);
	}

	public boolean isDASNameOptionVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean isDASNameTextBoxVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTextbox", 3);
	}

	public boolean isDASNameTextBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTextbox", timeOut);
	}

	public boolean isKeyfobNameTextBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobNameTextField", timeOut);
	}

	public boolean clickOnKeyfobNameTextField() {
		if (MobileUtils.clickOnElement(objectDefinition, testCase, "KeyfobNameTextField")) {
			return true;
		}
		return false;
	}

	public boolean clearKeyfobNameTextBox() {
		return MobileUtils.clearTextField(objectDefinition, testCase, "KeyfobNameTextField");
	}

	public boolean setValueToKeyfobNameTextBox(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "KeyfobNameTextField", value);
	}

	public boolean isDeleteDASDevicePopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteDASPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteKeyfobPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteKeyfobPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteSensorPopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteSensorPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUpConfirmationTitle", 3);
	}

	public boolean isGeofencePopUpVisible() {
		// Check Geofence settings for global geofence verification pop up is visible
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingOptionPopUpForGlobalGeofence", 3);
	}

	public boolean isOKButtonInGeofenceSettingsPopupVisible() {
		// OK button visible in pop up
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceOptionPopUpOKButton");
	}

	public boolean clickOnOKButtonInGeofenceSettingsPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceOptionPopUpOKButton");
	}

	public boolean isCancelButtonInGeofenceSettingsPopupVisible() {
		// OK button visible in pop up
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofenceOptionPopUpCANCELButton");
	}

	public boolean clickOnCancelButtonInGeofenceSettingsPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofenceOptionPopUpCANCELButton");
	}

	public boolean cliclOnAlexaSetUpOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonSetupButton");
	}

	public boolean clickOnAmazonSignInButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonSignInButton");
	}

	public boolean setEmailAddressValue(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "AmazonEmailID", value);
	}

	public boolean setPasswordValue(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "AmazonPassword", value);
	}

	public boolean isFeatureSetupScreenDisplayed() {
		Boolean b = MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaFeatureSetupScreen");

		if (b) {
			Keyword.ReportStep_Pass(testCase, "AmazonAlexaFeatureSetupScreen Screen is displayed");
		}
		return b;
	}

	public boolean clickOnAmazonSignOutVisible() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonAlexaSignOutButton");
	}

	public boolean isElementEnabled(String elementName) throws Exception {
		if (elementName.equals(BaseStationSettingsScreen.GEOFENCING)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.GEOFENCING);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingCell").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitDelayOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitCell").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.VOLUME)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VOLUME);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(
						MobileUtils.getMobElement(objectDefinition, testCase, "VolumeOption").getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "VolumeCell").getText().equals("enabled")
						? true
						: false);
			}

		} else if (elementName.equals(BaseStationSettingsScreen.RESETWIFI)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationWiFiOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.RESETWIFI);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "BaseStationWiFiOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "BaseStationWiFiCell").getText()
						.equals("enabled") ? true : false);
			}

		} else if (elementName.equals(BaseStationSettingsScreen.MOTIONDETECTION)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionOption", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.MOTIONDETECTION);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionCell").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.NIGHTVISION)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionOption", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.NIGHTVISION);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "NightVisionOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "NightVisionCell").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.VIDEOQUALITY)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityOption", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VIDEOQUALITY);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "VideoQualityOption")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "VideoQualityCell").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.SECURITYMODECHANGE)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeChange", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.SECURITYMODECHANGE);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChange")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChange").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.DOORANDWINDOWS)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "DoorsAndWindows", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.DOORANDWINDOWS);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "DoorsAndWindows")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "DoorsAndWindows").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.CAMERASTATUS)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStatus", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.CAMERASTATUS);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(
						MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatus").getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatus").getText()
						.equals("enabled") ? true : false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.MOTIONEVENT)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionEvent", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.MOTIONEVENT);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(
						MobileUtils.getMobElement(objectDefinition, testCase, "MotionEvent").getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEvent").getText().equals("enabled")
						? true
						: false);
			}
		} else if (elementName.equals(BaseStationSettingsScreen.EMAILNOTIFICATIONS)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotifications", 5)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.EMAILNOTIFICATIONS);
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "EmailNotifications")
						.getAttribute("enabled"));
			} else {
				return (MobileUtils.getMobElement(objectDefinition, testCase, "EmailNotifications").getText()
						.equals("enabled") ? true : false);
			}
		}

		else {
			throw new Exception("Invalid Input : " + elementName);
		}
	}

	public boolean isEntryExitDelaySettingsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", 3);
	}

	public boolean isDoorAndWindowsToggleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoorsAndWindows", 5);
	}

	public boolean isEntryExitDelaySettingsOptionVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", timeOut);
	}

	public boolean isGeofencingSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				return Boolean.parseBoolean(MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingSwitch")
						.getAttribute("value"));
			}
		} else {
			throw new Exception("Could not find Geofencing Status Switch");
		}
	}

	public boolean isKeyfobDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobDeletePopUpMessage", 3);
	}

	public boolean isKeyfobNameOptionVisibleOnKeyfobSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean isKeyFobOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyFobOption", 3);
	}

	public boolean isKeyfobPopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteKeyfobPopUpConfirmationTitle", 3);
	}

	public boolean isKeyfobPresentInKeyfobsList(String keyfobName) {
		List<WebElement> keyfobs = MobileUtils.getMobElements(objectDefinition, testCase, "KeyfobList");
		boolean found = false;
		for (WebElement keyfob : keyfobs) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				System.out.println(keyfob.getAttribute("text"));
				if (keyfob.getAttribute("text").equalsIgnoreCase(keyfobName)) {
					found = true;
					break;
				}
			} else {
				System.out.println(keyfob.getAttribute("value"));
				if (keyfob.getAttribute("value").equalsIgnoreCase(keyfobName)) {
					found = true;
					break;
				}
			}
		}
		return found;
	}

	public boolean isModelAndFirmwareDetailsOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean isModelAndFirmwareOptionsVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean isModelAndFirmwareOptionsVisibleOnKeyfobSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean isMotionSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorDeletePopUpMessage", 3);
	}

	public boolean isISMVDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ISMVDeletePopUpMessage", 3);
	}

	public boolean isOSMVDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OSMVDeletePopUpMessage", 3);
	}

	public boolean isNoKeyFobTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoKeyFobsText", 3);
	}

	public boolean isNoSensorTextVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoSensorsText", timeOut);
	}

	public boolean isSensorBatteryOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryTitle", 3);
	}

	public boolean isSensorNameOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean isSensorPresentInSensorsList(String sensorName) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorList", 10)) {
			List<WebElement> sensors = MobileUtils.getMobElements(objectDefinition, testCase, "SensorList");
			boolean found = false;
			for (WebElement sensor : sensors) {
				if (sensor.getAttribute("text").equalsIgnoreCase(sensorName)) {
					found = true;
					break;
				}
			}
			return found;
		} else {
			throw new Exception("No sensors found");
		}

	}

	public boolean isSensorSignalStrengthAndTestOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthOption", 3);
	}

	public boolean isSensorsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorsOption", 3);
	}

	public boolean isSensorStatusOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorStatusOption", 3);
	}

	public boolean isVideoSettingsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoSettingsOption", 3);
	}

	public boolean isVolumeOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeOption", 3);
	}

	public boolean isZwaveDevicesSettingOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveDevicesSettingsOption", 3);
	}

	public boolean selectKeyfobFromKeyfobList(String keyfobName) {
		return MobileUtils.clickOnElement(testCase, "xpath", "//android.widget.TextView[@text='" + keyfobName + "']");
	}

	public boolean ClickOnBaseStationCongifurtion() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
	}

	public boolean selectOptionFromBaseStationSettings(String option) throws Exception {
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		switch (option) {
		case BaseStationSettingsScreen.BASESTATIONCONFIGURATION: {
			boolean flag = true;
			if (this.isBaseStationConfigurationsOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Base Station Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Base Station Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			if (this.isBaseStationConfigurationsOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Base Station Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS: {
			boolean flag = true;
			if (this.isEntryExitDelaySettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int startx = (dimension.width * 20) / 100;
					int starty = (dimension.height * 62) / 100;
					int endx = (dimension.width * 22) / 100;
					int endy = (dimension.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				} else {
					action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
							.release().perform();
				}
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.KEYFOB: {
			boolean flag = true;
			if (this.isKeyFobOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Key Fob Visible @ 1");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			} else {
				Keyword.ReportStep_Pass(testCase, "Key Fob Visible @ 2");
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				if (this.isKeyFobOptionVisible()) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
				}
			}
			if (this.isKeyFobOptionVisible()) {
				Keyword.ReportStep_Pass(testCase, "Key Fob Visible @ 3");
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.SENSORS: {
			boolean flag = true;
			flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
					testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
					"Base Station Configuration");
			SecuritySolutionCardScreen secScreen = new SecuritySolutionCardScreen(testCase);
			flag = flag & secScreen.clickOnSensorButton();
			return flag;
		}

		case BaseStationSettingsScreen.VOLUME: {
			boolean flag = true;
			if (this.isVolumeOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VolumeOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VOLUME);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VolumeOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.AMAZONALEXA: {
			boolean flag = true;
			if (this.isAmazonAlexaOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonAlexaOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.AMAZONALEXA);

				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AmazonAlexaOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.VIDEOSETTINGS: {
			boolean flag = true;
			if (this.isVideoSettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VideoSettingsOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VIDEOSETTINGS);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VideoSettingsOption");
			}
			return flag;
		}
		case BaseStationSettingsScreen.ZWAVEDEVICES: {
			boolean flag = true;
			if (this.isZwaveDevicesSettingOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveDevicesSettingsOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							BaseStationSettingsScreen.ZWAVEDEVICES);
				} else {
					//int counter = 0;
					// while (!MobileUtils.isMobElementExists(objectDefinition, testCase,
					// "ZwaveDevicesSettingsOption",10) && counter < 3) {
					LyricUtils.scrollUpAList(testCase,
							MobileUtils.getMobElement(testCase, "XPATH", "//XCUIElementTypeOther"));
					// }
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveDevicesSettingsOption");
			}
			return flag;
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

	public boolean selectSensorFromSensorList(String sensorName) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//android.widget.TextView[@text='" + sensorName + "']");
		} else {
			return MobileUtils.clickOnElement(testCase, "xpath",
					"//XCUIElementTypeStaticText[@value='" + sensorName + "']");
		}
	}

	public boolean setValueToDASNameTextBox(String value) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "DASNameTextbox", value);
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			System.out.println("######dimensions.width:- " + dimensions.width);
			System.out.println("######dimensions.height:- " + dimensions.height);
			System.out.println("######(dimensions.width - 100):- " + (dimensions.width - 100));
			System.out.println("######(dimensions.height - 100):- " + (dimensions.height - 100));
			touchAction.tap((dimensions.width - 100), (dimensions.height - 100)).perform();
		} else {
			flag = flag & MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField", value);
			MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
		}
		return flag;
	}

	public boolean setValueToVolumeSlider(String value) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement volumeSlider = MobileUtils.getMobElement(objectDefinition, testCase, "VolumeSlider");
			Dimension d1 = volumeSlider.getSize();
			Point p1 = volumeSlider.getLocation();
			float sliderLength = d1.getWidth();
			float pixelPerPercent = sliderLength / 100;
			float pixelToBeMoved = Integer.parseInt(value.equals("0") ? "1" : value) * pixelPerPercent;
			System.out.println("Setting for " + value);
			System.out.println("X: " + (int) (p1.getX() + pixelToBeMoved));
			System.out.println("Y: " + p1.getY());
			return MobileUtils.clickOnCoordinate(testCase, (int) (p1.getX() + pixelToBeMoved), p1.getY());
		} else {
			MobileUtils.setValueToElement(objectDefinition, testCase, "VolumeSlider", value);
			return true;
		}
	}

	public boolean toggleCameraOnInHomeModeSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOnInHomeModeSwitch");
	}

	public boolean verifyAlexaAppPlayStoreTitleIsVisible(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PlayStoreAppTitle", 15)) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "PlayStoreAppTitle").getAttribute("text")
					.equalsIgnoreCase("Amazon Alexa")) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception("Play Store Download Page not displayed");
		}
	}

	public boolean verifyBaseStationVolumeValueOnSecuritySettings(String value) throws Exception {
		if (this.isBaseStationVolumeValueVisible(10)) {
			String displayedValue = MobileUtils.getMobElement(objectDefinition, testCase, "VolumeValue")
					.getAttribute("text");
			displayedValue = displayedValue.split("%")[0];
			int expectedValue = Integer.parseInt(value);
			int actualValue = Integer.parseInt(displayedValue);
			return (expectedValue <= (actualValue + 5) && expectedValue >= (actualValue - 5));
		} else {
			throw new Exception("Could not find Volume Value Elements");
		}
	}

	public boolean verifyBatteryOptionTextOnBaseStationConfigurationScreen() {
		if (this.isBatteryOptionVisibleOnBaseStationConfigurationScreen()) {
			return (MobileUtils.getFieldValue(objectDefinition, testCase, "DASBatteryTitle")
					.equalsIgnoreCase("Battery"));
		} else {
			return false;
		}
	}

	public boolean verifyBatteryOptionTextOnSensorSettingsScreen() {
		if (this.isSensorBatteryOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getFieldValue(objectDefinition, testCase, "DASBatteryTitle")
					.equalsIgnoreCase("Battery"));
		} else {
			return false;
		}
	}

	public boolean verifyBatteryStatusTextOnBaseStationConfigurationScreen() {
		if (this.isBatteryOptionVisibleOnBaseStationConfigurationScreen()) {
			String status = MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryStatus").getText();
			int batteryPercent = Integer.parseInt(status.split("%")[0]);
			return (batteryPercent >= 0 && batteryPercent <= 100);
		} else {
			return false;
		}
	}

	public boolean verifyDASFirmwareDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareDetailsTitle")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareDetailsTitle").getText()
					.equalsIgnoreCase("Firmware Details"));
		} else {
			flag = false;
		}

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareVersion")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareVersion").getText()
					.toUpperCase().contains("VERSION"));
		} else {
			flag = false;
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareLastUpdatedDate")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareLastUpdatedDate").getText()
					.toUpperCase().contains("LAST UPDATED ON"));
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifyDASModelDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelDetailsLabel")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelDetailsLabel").getText()
					.equalsIgnoreCase("Model Details"));
		} else {
			flag = false;
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelNameLabel")) {
				flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelNameLabel").getText()
						.equalsIgnoreCase("Smart Home Security"));

			} else {
				flag = false;
			}
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelMACID")) {
				flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelMACID").getAttribute("text")
						.toUpperCase().contains("MAC ID : "));
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelNameLabel")) {
				flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelNameLabel").getText()
						.toUpperCase().contains("Smart Home Security\nMAC ID : "));
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean verifyDASNameOptionTextOnBaseStationConfigurationScreen() {
		if (this.isDASNameOptionVisibleOnBaseStationConfigurationScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASNameTitle").getText()
					.equalsIgnoreCase("Name"));
		} else {
			return false;
		}
	}

	public boolean verifyEntryExitDelaySelectedValue(int delayValue) throws Exception {
		boolean flag = true;
		try {
			WebElement entryExitTable = null;
			WebElement tickMark = null;
			int index = -1;
			if (delayValue == 15) {
				index = 0;
			} else if (delayValue == 30) {
				index = 1;
			} else if (delayValue == 45) {
				index = 2;
			} else if (delayValue == 60) {
				index = 3;
			}
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitTable", 10)) {
				entryExitTable = MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitTable");
			} else {
				throw new Exception("Could not find entry/exit delay values");
			}
			List<WebElement> cells = null;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				cells = entryExitTable
						.findElements(By.className(BaseStationSettingsScreen.ANDROIDENTRYEXITTABLELOCATORVALUE));
			} else {
				cells = entryExitTable.findElements(By.xpath(BaseStationSettingsScreen.IOSENTRYEXITTABLELOCATORVALUE));
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				tickMark = cells.get(index).findElement(By.id(BaseStationSettingsScreen.ANDROIDTICKMARKLOCATORVALUE));
			} else {
				tickMark = cells.get(index).findElement(By.xpath(BaseStationSettingsScreen.IOSTICKMARKLOCATORVALUE));
			}
			if (tickMark != null) {
				return flag;
			} else {
				throw new Exception(delayValue + " second option not selected on Entry/Exit Delay screen");
			}
		} catch (NoSuchElementException e) {

			throw new Exception(delayValue + " second option not selected on Entry/Exit Delay screen");
		}
	}

	public boolean verifyKeyfobFirmwareDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareDetailsTitle")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareDetailsTitle")
					.getAttribute("text").equalsIgnoreCase("Firmware Details"));
		} else {
			flag = false;
		}

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareVersion")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareVersion").getAttribute("text")
					.toUpperCase().contains("VERSION"));
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifyKeyfobModelDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelDetailsLabel", 3)) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelDetailsLabel")
					.getAttribute("text").equalsIgnoreCase("Model Details"));
		} else {
			flag = false;
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelMACID", 3)) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelMACID").getAttribute("text")
					.toUpperCase().contains("SERIAL NO : "));
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifyKeyfobNameOptionTextOnKeyfobSettingsScreen() {
		if (this.isKeyfobNameOptionVisibleOnKeyfobSettingsScreen()) {
			return (MobileUtils.getFieldValue(objectDefinition, testCase, "DASNameTitle").equalsIgnoreCase("Name"));
		} else {
			return false;
		}
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnBaseStationConfigurationScreen() {
		if (this.isModelAndFirmwareOptionsVisibleOnBaseStationConfigurationScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "ModelAndFirmwareDetails").getText()
					.equalsIgnoreCase("Model and Firmware Details"));

		} else {
			return false;
		}
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnKeyfobSettingsScreen() {
		if (this.isModelAndFirmwareOptionsVisibleOnKeyfobSettingsScreen()) {
			return (MobileUtils.getFieldValue(objectDefinition, testCase, "ModelAndFirmwareDetails")
					.equalsIgnoreCase("Model and Firmware Details"));
		} else {
			return false;
		}
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnSensorSettingsScreen() {
		if (this.isModelAndFirmwareDetailsOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getFieldValue(objectDefinition, testCase, "ModelAndFirmwareDetails")
					.equalsIgnoreCase("Model and Firmware Details"));

		} else {
			return false;
		}
	}

	public boolean verifyParticularBaseStationSettingsVisible(String settingName) throws Exception {
		String attribute;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
		} else {
			attribute = "value";
		}
		if (settingName.toLowerCase().contains("homekit") && testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return true;
		}
		if (settingName.equalsIgnoreCase("Manage Alerts")) {
			return isManageAlertExist();
		}
		if (settingName.equalsIgnoreCase("Key Fob") || settingName.equalsIgnoreCase("Sensors")
				|| settingName.equalsIgnoreCase("Adaptive Recovery")) {
			return LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute, settingName);
		} else if (settingName.equalsIgnoreCase("Set Filter Reminder")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute, settingName);
			} else {
				settingName = "Filter Reminder";
				return LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute, settingName);
			}
		} else {
			return LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute, settingName);
		}
	}

	public boolean verifyParticularEntryExitDelayOptionVisible(String option) throws Exception {
		return LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
				testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", option);
	}

	public boolean verifySensorFirmwareDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareDetailsTitle")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareDetailsTitle")
					.getAttribute("text").equalsIgnoreCase("Firmware Details"));
		} else {
			flag = false;
		}

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FirmwareVersion")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "FirmwareVersion").getAttribute("text")
					.toUpperCase().contains("VERSION"));
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifySensorModelDetailsOnModelAndFirmwareDetailsPage() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelDetailsLabel")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelDetailsLabel")
					.getAttribute("text").equalsIgnoreCase("Model Details"));
		} else {
			flag = false;
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelMACID")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelMACID").getAttribute("text")
					.toUpperCase().contains("MAC ID :"));
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifySensorNameOptionTextOnSensorSettingsScreen() {
		if (MobileUtils.getFieldValue(objectDefinition, testCase, "SensorSetting_NameTitle").equalsIgnoreCase("Name")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean toggleGeofencingSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofencingSwitch");
	}

	public boolean isCameraOnInHomeModeSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOnInHomeModeSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraOnInHomeModeSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				return Boolean.parseBoolean(MobileUtils
						.getMobElement(objectDefinition, testCase, "CameraOnInHomeModeSwitch").getAttribute("value"));
			}
		} else {
			return false;
		}
	}

	public boolean isEnhancedDeterrenceSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnhancedDeterrenceSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "EnhancedDeterrenceSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				return Boolean.parseBoolean(MobileUtils
						.getMobElement(objectDefinition, testCase, "EnhancedDeterrenceSwitch").getAttribute("value"));
			}
		} else {
			return false;
		}
	}

	public boolean toggleEnhancedDeterrenceSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EnhancedDeterrenceSwitch");
	}

	public boolean verifyStatusOptionTextOnSensorSettingsScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorStatusOption")) {
			System.out.println("Status is found");
			return true;
		}
		return false;
	}

	public boolean verifySensorStatusTextOnSensorSettingsScreen() {

		if (this.verifyStatusOptionTextOnSensorSettingsScreen()) {
			String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
			return (status.equalsIgnoreCase("Open") || status.equalsIgnoreCase("Closed")
					|| status.equalsIgnoreCase("Off") || status.equalsIgnoreCase("Good"));
		} else {
			return false;
		}
	}

	public boolean verifySignalStrengthOptionTextOnSensorSettingsScreen() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthOption", 5)) {
			return true;
		}
		return false;
	}

	public boolean verifyDeleteOptionOnSensorSettingsScreenVisible() {

		try {
			if (!testCase.getPlatform().contains("IOS")) {
				if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", "DELETE")) {
					System.out.println("Delete scroll");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DASSensorSetting_Delete", 5)) {
			return true;
		}
		return false;
	}

	public boolean isNameElementEnabled() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSettingNamingField");
		if (testCase.getPlatform().contains("IOS")) {
			element.click();
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
				return false;
			} else {
				return true;
			}
		} else {
			System.out.println("#######" + element.getAttribute("focusable"));
			if (element.getAttribute("focusable").equalsIgnoreCase("true")) {
				System.out.println(element.getText());
				return true;
			}
		}
		return false;
	}

	public boolean isKeyfobNameElementEnabled() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "KeyfobNameLabel");
		if (testCase.getPlatform().contains("IOS")) {
			element.click();
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
				return false;
			} else {
				return true;
			}
		} else {
			System.out.println("#######" + element.getAttribute("focusable"));
			if (element.getAttribute("focusable").equalsIgnoreCase("true")) {
				System.out.println(element.getText());
				return true;
			}
		}
		return false;
	}

	public boolean isPerformOnlyInModesPopupForGeofence() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingOption");
		if (testCase.getPlatform().contains("IOS")) {
			element.click();
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
				return true;
			} else {
				return false;
			}
		} else {
			element.click();
			// TODO: Need to read Toast message pop up once merged code is available.
			return true;
		}
	}

	public boolean isPerformInModePopupVisible() {
		boolean flag = false;
		if (testCase.getPlatform().contains("Android")) {
			if (this.isVolumeOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VolumeOption");
			} else {
				try {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
							testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
							BaseStationSettingsScreen.VOLUME);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					flag = false;
					e.printStackTrace();
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "VolumeOption");
				String toastMessageText = LyricUtils.getToastMessage(testCase);
				if (toastMessageText.contains("You can perform this action only in Home")
						&& toastMessageText.contains("or Off Mode.")) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "You can perform in home popup is seen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"failed in validating You can perform in Home or Off mode popup");
				}
			}

			return flag;
		} else {
			return false;
		}
	}

	public boolean isDeleteElementClickable() {
		boolean flag = true;

		WebElement element;
		if (testCase.getPlatform().contains("IOS")) {
			try {
				// flag= flag &
				// LyricUtils.scrollToElementUsingExactAttributeValue(testCase,"value",
				// "Delete");
			} catch (Exception e) {
				e.printStackTrace();
			}
			element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSetting_Delete");
			element.click();
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
				return false;
			} else {
				return true;
			}
		} else {
			try {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
			} catch (Exception e) {
				e.printStackTrace();
			}
			element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSetting_Delete");
			if (element.getAttribute("clickable").equalsIgnoreCase("true")) {
				System.out.println(element.getText());
				return true;
			}
		}
		return false;
	}

	public boolean isKeyfobDeleteElementClickable() {
		boolean flag = true;

		WebElement element;
		if (testCase.getPlatform().contains("IOS")) {
			try {
				// flag= flag &
				// LyricUtils.scrollToElementUsingExactAttributeValue(testCase,"value",
				// "Delete");
			} catch (Exception e) {
				e.printStackTrace();
			}
			element = MobileUtils.getMobElement(objectDefinition, testCase, "KeyfobDelete");
			element.click();
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
				return false;
			} else {
				return true;
			}
		} else {
			try {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
			} catch (Exception e) {
				e.printStackTrace();
			}
			element = MobileUtils.getMobElement(objectDefinition, testCase, "KeyfobDelete");
			if (element.getAttribute("clickable").equalsIgnoreCase("true")) {
				System.out.println(element.getText());
				return true;
			}
		}
		return false;
	}

	public boolean isSensorSignalStrengthAndTestOptionEnabled() {
		boolean flag = true;
		if (MobileUtils.clickOnElement(objectDefinition, testCase, "SignalStrengthOption")) {
			if (testCase.getPlatform().contains("IOS")) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "PerformOnlyInModesPopup")) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "PerformOnlyInModesPopupAck");
					return false;
				} else {
					return true;
				}
			} else {
				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
				fWait.pollingEvery(2, TimeUnit.SECONDS);
				fWait.withTimeout(10, TimeUnit.SECONDS);
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						if (MobileUtils.isMobElementExists(objectDefinition, testCase,
								"PleaseWaitForSignalStrengthandTest")) {
							return false;
						} else
							return true;
					}
				});
				if (isEventReceived) {
					flag = false;
				}

				flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "TestSensorTitle");

				return flag;
			}
		}
		return flag;
	}

	public boolean RenameSensorName(String givenSensorName) {
		boolean flag = true;
		SecuritySolutionCardScreen security = new SecuritySolutionCardScreen(testCase);
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSettingNamingField");
		if (element != null) {
			String actualSensorName = element.getText();
			String RenamedString = givenSensorName + " updated";
			if (givenSensorName.equalsIgnoreCase(actualSensorName)) {
				MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
				MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
						MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
						MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
					} catch (Exception e) {
					}
				}
				System.out.println("%%%%%%%%%%%%RenamedString: " + RenamedString);
				MobileUtils.setValueToElement(objectDefinition, testCase, "DASSensorSettingNamingField", RenamedString);
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
				} else {
					try {
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
					} catch (Exception e) {
					}
				}
				if (MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton")) {
					MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdatingStatusForSensorNameUpdate", 3);
				}
				flag = flag & security.clickOnUserGivenSensorName(RenamedString);
				MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
				MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
						MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
						MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
					} catch (Exception e) {
					}
				}
				System.out.println("%%%%%%%%%%%%actualSensorName: " + actualSensorName);
				MobileUtils.setValueToElement(objectDefinition, testCase, "DASSensorSettingNamingField",
						actualSensorName);
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
				} else {
					try {
						MobileUtils.hideKeyboard(testCase.getMobileDriver());
					} catch (Exception e) {
					}
				}
				if (MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton")) {
					MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdatingStatusForSensorNameUpdate", 3);
				}
				flag = flag & security.isSensorDisplayed(givenSensorName);
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "renamed and reverted");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "failed in renaming sensor");
				}
				return true;
			}
		} else {
			System.out.println("Element is null");
		}
		return false;
	}

	public boolean enterExistingSensorName(String ebterExistingSensorName) {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSettingNamingField");
		if (element != null) {

			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
			flag &= MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				try {
					MobileUtils.hideKeyboard(testCase.getMobileDriver());
					flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
					flag &= MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
				} catch (Exception e) {
				}
			}
			System.out.println("%%%%%%%%%%%%ebterExistingSensorName: " + ebterExistingSensorName);
			flag &= MobileUtils.setValueToElement(objectDefinition, testCase, "DASSensorSettingNamingField",
					ebterExistingSensorName);
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
			} else {
				try {
					MobileUtils.hideKeyboard(testCase.getMobileDriver());
				} catch (Exception e) {
				}
			}
			if (MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton")) {
				return flag;
			}
		} else {
			flag = false;
			System.out.println("Element is null");
		}
		return flag;
	}

	public boolean clickOnSignalStrengthandTestOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SignalStrengthOption");
	}

	public boolean isManageAlertExist() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ManageAlerts");
	}

	public boolean clickOnManageAlerts() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ManageAlerts");
	}

	public boolean verifySensorStatusAfterTestSignalStrength(String s) {
		System.out.println(s);
		if (this.verifyStatusOptionTextOnSensorSettingsScreen()) {
			String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
			return (status.equalsIgnoreCase(s));
		} else {
			return false;
		}
	}

	public boolean DuplicateSensorName(String check) {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "DASSensorSettingNamingField");
		if (element != null) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
			MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
			MobileUtils.setValueToElement(objectDefinition, testCase, "DASSensorSettingNamingField", check);
			return true;
		}
		return false;
	}

	public boolean clearCameraNameTextBox() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField");
			return MobileUtils.clearTextField(objectDefinition, testCase, "DASSensorSettingNamingField");
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeTextField"))
					.clear();
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASSensorSettingNamingField");
		}
	}

	public boolean setValueToCameraNameTextBox(String value) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.setValueToElement(objectDefinition, testCase, "DASSensorSettingNamingField", value);
		} else {
			flag &= MobileUtils.setValueToElement(testCase, "XPATH",
					"//XCUIElementTypeCell[1]/XCUIElementTypeTextField", value);
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "ReturnButtonInIOSKeyboard");
		}
		return flag;
	}

	public boolean isCameraNameFieldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASSensorSettingNamingField");
	}

	public boolean clickOnNameTextField() {
		if (MobileUtils.clickOnElement(objectDefinition, testCase, "DASSensorSettingNamingField")) {
			return true;
		}
		return false;
	}

	public boolean verifyBatteryStatusTextOnAccessSensorSettingsScreen(String status) {
		String actualStatus = MobileUtils.getFieldValue(objectDefinition, testCase, "DASBatteryStatus");
		if (actualStatus.equalsIgnoreCase(status)) {
			System.out.println("Battery Status is same!");
			return true;
		}
		return false;
	}

	public boolean clickOnSensorStatusTextOnSensorSettingsScreen(String givenStatus) {

		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorStatusOptionValue");
		if (givenStatus.contains(status.toUpperCase())) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "SensorStatusOptionValue");
			return true;
		}
		return false;
	}

	public boolean isManangeAlertsEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ManageAlerts").isEnabled();
	}

	public boolean isAmazonAlexaEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AmazonAlexaOption").isEnabled();
	}

	public boolean isAmazonAlexaiConVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaiCon");
	}

	public boolean isGeofencingEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingOption").isEnabled();
	}

	public boolean isGeofencingDescriptionEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingDescriptionText").isEnabled();
	}

	public boolean isOKSecurityVoiceCommandsEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "OKSecurityVoiceCommands").isEnabled();
	}

	public boolean isEnhancedDeterrenceEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EnhancedDeterrence").isEnabled();
	}

	public boolean isEnhancedDeterrenceDescriptionEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EnhancedDeterrenceDescription").isEnabled();
	}

	public boolean ClickOnEnhancedDeterrenceOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EnhancedDeterrence");
	}

	public boolean isEnhancedDeterrenceheadervisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnhancedDeterrenceHeader");
	}

	public boolean isEnhancedDeterrenceInsideDetererenceDescriptionvisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"EnhancedDeterrenceInsideDetererenceDescription");
	}

	public boolean isOutdoorMotionViewerOnInHomeModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeMode");
	}

	public boolean isOutdoorMotionViewerOnInHomeModeEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeMode").isEnabled();
	}

	public boolean isEntryExitDelayEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitDelayOption").isEnabled();
	}

	public boolean isEntryExitDelayVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption");
	}

	public boolean isEntryExitDelayDescriptionEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitDescriptionText").isEnabled();
	}

	public boolean isOutdoorMotionViewerOnInHomeModeDescriptionEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeModeDescription")
				.isEnabled();
	}

	public boolean isAboutSecurityModesEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AboutSecurityModes").isEnabled();
	}

	public boolean isAboutSecurityModesVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboutSecurityModes");
	}

	public boolean isKeyFobEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "KeyfobNameLabel").isEnabled();
	}

	public boolean isKeyFobVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobNameLabel");
	}

	public boolean isSensorsOptionEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SensorsOption").isEnabled();
	}

	public boolean isZwaveDevicesEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ZwaveDevicesSettingsOption").isEnabled();
	}

	public boolean isZwaveDevicesVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveDevicesSettingsOption");
	}

	public boolean isBaseStationVolumeEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "VolumeOption").isEnabled();
	}

	public boolean isBaseStationResetWifiEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "BaseStationWiFiOption").isEnabled();
	}

	public boolean isBaseStationResetWifiVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationWiFiOption");
	}

	public boolean isBaseStationConfigurationEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "BaseStationConfigurationsOption").isEnabled();
	}

	public boolean isSecurityModeChangeEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChange").isEnabled();
	}

	public boolean isSecurityModeChangeSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeChangeSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChangeSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChangeSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find the security mode change Switch");
		}
	}

	public boolean toggleSecurityModeChangeSwitch(TestCases testCase) {

		return MobileUtils.clickOnElement(objectDefinition, testCase, "SecurityModeChangeSwitch");
	}

	public boolean isSecurityModeChangeDescription() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChangeDescription").isEnabled();
	}

	public boolean isSecurityModeDoorAndWindowEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "SecurityModeChange").isEnabled();
	}

	public boolean isSecurityModeDoorAndWindowVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoorsAndWindows");
	}

	public boolean isYoucanperformthisactiononlyinVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YoucanperformthisactiononlyinHoomeorOffMode");
	}

	public boolean ClickOnYoucanperformthisactiononlyinOKOption() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"YoucanperformthisactiononlyinHoomeorOffModeOKOption");
	}

	public boolean isSecuritySettingHeaderVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecuritySettingsHeader");
	}

	public boolean isSelectChimeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SelectChime");
	}

	public boolean isPlayDogBarkSoundVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlayDogBarkSound");
	}

	public boolean isPartyIsOnVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PartyIsOn");
	}

	public boolean isVacuumVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Vacuum");
	}

	public boolean clickonPlayDogBarkSoundoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PlayDogBarkSound");
	}

	public boolean clickonPartyIsOnoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PartyIsOn");
	}

	public boolean clickonvacuumoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Vacuum");
	}

	public boolean isPlayDogBarkSoundselected() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PlayDogBarkSoundSelected");
	}

	public boolean isPartyIsOnselected() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PartyIsOnSelected");
	}

	public boolean isVacuumselected() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacuumSelected");
	}

	public boolean isOutdoorMotionViewersOnInHomeModeSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeModeSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeModeSwitch")
						.getText().equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				return Boolean.parseBoolean(
						MobileUtils.getMobElement(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeModeSwitch")
								.getAttribute("value"));
			}
		} else {
			throw new Exception("Could not find Outdoor Motion Viewers On in Home mode Switch");
		}
	}

	public boolean toggleOutdoorMotionViewersOnInHomeModeSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OutdoorMotionViewersOnInHomeModeSwitch");
	}

	public boolean clickonEntryExistDelayoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
	}

	public boolean clickonbasestationvolumeoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VolumeOption");
	}

	public boolean clickonbasestationresetwifioption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationWiFiOption");
	}

	public boolean clickonAboutSecurityModesoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AboutSecurityModes");
	}

	public boolean isSecurityModesHeader() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeHeader");
	}

	public boolean isSecurityModeHomeiConVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeHomeiCon");
	}

	public boolean isSecurityModeHomeModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeHomeMode");
	}

	public boolean isSecurityModeHomeTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeHomeText");
	}

	public boolean isSecurityModeAwayiConVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeAwayiCon");
	}

	public boolean isSecurityModeAwayModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeAwayMode");
	}

	public boolean isSecurityModeAwayTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeAwayText");
	}

	public boolean isSecurityModeNightiConVisible() {
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
		} else {
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
		}

		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeNightiCon");
	}

	public boolean isSecurityModeNightModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeNightMode");
	}

	public boolean isSecurityModeNightTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeNightText");
	}

	public boolean isSecurityModeoffiConVisible() {
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
					.release().perform();
		}
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeoffiCon");
	}

	public boolean isSecurityModeoffModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SecurityModeoffMode");
	}

	public String getTotalKeyFobsEnrolledCount() {
		String totalKeyFobsEnrolled = MobileUtils.getFieldValue(objectDefinition, testCase, "KeyFobOption");
		totalKeyFobsEnrolled = totalKeyFobsEnrolled
				.substring(totalKeyFobsEnrolled.indexOf("(") + 1, totalKeyFobsEnrolled.indexOf(")")).toString();
		return totalKeyFobsEnrolled;
	}

	public String getTotalSensorsEnrolledCount() {
		String totalSensorsEnrolled = MobileUtils.getFieldValue(objectDefinition, testCase, "SensorsOption");
		totalSensorsEnrolled = totalSensorsEnrolled
				.substring(totalSensorsEnrolled.indexOf("(") + 1, totalSensorsEnrolled.indexOf(")")).toString();
		return totalSensorsEnrolled;
	}

	public boolean isKeyFobIssuesLabelVisibleInSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobIssuesLabelInSettingsScreen");
	}

	public String getKeyFobIssuesCount() {
		String totalKeyFobIssuesCount = MobileUtils.getFieldValue(objectDefinition, testCase,
				"KeyfobIssuesLabelInSettingsScreen");
		return totalKeyFobIssuesCount;
	}

	public boolean isSensorsIssuesLabelVisibleInSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorsIssuesLabelInSettingsScreen");
	}

	public String getSensorsIssuesCount() {
		String totalSensorsIssuesCount = MobileUtils.getFieldValue(objectDefinition, testCase,
				"SensorsIssuesLabelInSettingsScreen");
		return totalSensorsIssuesCount;
	}
}
