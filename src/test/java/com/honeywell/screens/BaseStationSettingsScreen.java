package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;

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

	// Locator values used in the methods
	public static final String ANDROIDENTRYEXITTABLELOCATORVALUE = "android.widget.RelativeLayout";
	public static final String IOSENTRYEXITTABLELOCATORVALUE = "//XCUIElementTypeCell";
	public static final String ANDROIDTICKMARKLOCATORVALUE = "list_item_lyric_image_view";
	public static final String IOSTICKMARKLOCATORVALUE = "//XCUIElementTypeImage";

	public BaseStationSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean selectOptionFromBaseStationSettings(String option) throws Exception {
		switch (option) {
		case BaseStationSettingsScreen.BASESTATIONCONFIGURATION: {
			boolean flag = true;
			if (this.isBaseStationConfigurationsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			if (this.isBaseStationConfigurationsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS: {
			boolean flag = true;
			if (this.isEntryExitDelaySettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			} else {
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
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.KEYFOB);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.SENSORS: {
			boolean flag = true;
			if (this.isSensorsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SensorsOption");
			} else {
				flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.SENSORS);
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SensorsOption");
			}
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

	public boolean isBaseStationConfigurationsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationConfigurationsOption", 3);
	}

	public boolean isEntryExitDelaySettingsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", 3);
	}

	public boolean isAmazonAlexaOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AmazonAlexaOption", 3);
	}

	public boolean isVideoSettingsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoSettingsOption", 3);
	}

	public boolean isKeyFobOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyFobOption", 3);
	}

	public boolean isSensorsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorsOption", 3);
	}

	public boolean isVolumeOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeOption", 3);
	}

	public boolean isEntryExitDelaySettingsOptionVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", timeOut);
	}

	public boolean clickOnNoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
	}

	public boolean isDeleteDASPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteSensorPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteKeyfobPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteKeyfobPopUpConfirmationTitle", 3);
	}

	public boolean verifyParticularBaseStationSettingsVisible(String settingName) throws Exception {
		String attribute;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
		} else {
			attribute = "value";
		}
		if (settingName.equalsIgnoreCase("Key Fob") || settingName.equalsIgnoreCase("Sensors")) {
			return LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute, settingName);
		} else {
			return LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute, settingName);
		}
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
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.MOTIONDETECTION);
			}
			return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionOption")
					.getAttribute("enabled"));
		} else if (elementName.equals(BaseStationSettingsScreen.NIGHTVISION)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.NIGHTVISION);
			}
			return Boolean.valueOf(
					MobileUtils.getMobElement(objectDefinition, testCase, "NightVisionOption").getAttribute("enabled"));
		} else if (elementName.equals(BaseStationSettingsScreen.VIDEOQUALITY)) {
			if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityOption", 3)) {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.VIDEOQUALITY);
			}
			return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "VideoQualityOption")
					.getAttribute("enabled"));
		}

		else {
			throw new Exception("Invalid Input : " + elementName);
		}
	}

	public boolean verifyParticularEntryExitDelayOptionVisible(String option) throws Exception {
		return LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
				testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value", option);
	}

	public boolean clickOn15SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "15SecondsOption");
	}

	public boolean is15SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "15SecondsOption", 3);
	}

	public boolean clickOn30SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "30SecondsOption");
	}

	public boolean is30SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "30SecondsOption", 3);
	}

	public boolean clickOn45SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "45SecondsOption");
	}

	public boolean is45SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "45SecondsOption", 3);
	}

	public boolean clickOn60SecondsEntryExitDelayOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "60SecondsOption");
	}

	public boolean is60SecondsEntryExitDelayOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "60SecondsOption", 3);
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

	public String getEntryExitTimerValueFromSecuritySettingsScreen() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitTimerValue").getAttribute("text");
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitTimerValue").getAttribute("value");
		}
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}

	public boolean isBackButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isDASNameTextBoxVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTextbox", 3);
	}

	public boolean isDASNameTextBoxVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTextbox", timeOut);
	}

	public boolean clearDASNameTextBox() {
		return MobileUtils.clearTextField(objectDefinition, testCase, "DASNameTextbox");
	}

	public boolean setValueToDASNameTextBox(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "DASNameTextbox", value);
	}

	public boolean isDASNameOptionVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean verifyDASNameOptionTextOnBaseStationConfigurationScreen() {
		if (this.isDASNameOptionVisibleOnBaseStationConfigurationScreen()) {

			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASNameTitle").getText()
					.equalsIgnoreCase("Name"));
		} else {
			return false;
		}
	}

	public boolean isBatteryOptionVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryTitle", 3);
	}

	public boolean verifyBatteryOptionTextOnBaseStationConfigurationScreen() {
		if (this.isBatteryOptionVisibleOnBaseStationConfigurationScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryTitle").getText()
					.equalsIgnoreCase("Battery"));
		} else {
			return false;
		}
	}

	public boolean isBatteryStatusVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryStatus", 3);
	}

	public boolean verifyBatteryStatusTextOnBaseStationConfigurationScreen() {
		if (this.isBatteryOptionVisibleOnBaseStationConfigurationScreen()) {
			String status = MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryStatus").getText();
			return (status.equalsIgnoreCase("Good") || status.equalsIgnoreCase("Low"));
		} else {
			return false;
		}
	}

	public boolean isModelAndFirmwareOptionsVisibleOnBaseStationConfigurationScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnBaseStationConfigurationScreen() {
		if (this.isModelAndFirmwareOptionsVisibleOnBaseStationConfigurationScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "ModelAndFirmwareDetails").getText()
					.equalsIgnoreCase("Model and Firmware Details"));

		} else {
			return false;
		}
	}

	public boolean clickOnModelAndFirmwareOptionsOnBaseStationConfigurationScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
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
						.equalsIgnoreCase("DAS"));

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
						.toUpperCase().contains("DAS\nMAC ID : "));
			} else {
				flag = false;
			}
		}
		return flag;
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

	public boolean isModelAndFirmwareOptionsVisibleOnKeyfobSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean clickOnModelAndFirmwareOptionsOnKeyfobSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnKeyfobSettingsScreen() {
		if (this.isModelAndFirmwareOptionsVisibleOnKeyfobSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "ModelAndFirmwareDetails")
					.getAttribute("text").equalsIgnoreCase("Model and Firmware Details"));
		} else {
			return false;
		}
	}

	public boolean isKeyfobNameOptionVisibleOnKeyfobSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean verifyKeyfobNameOptionTextOnKeyfobSettingsScreen() {
		if (this.isKeyfobNameOptionVisibleOnKeyfobSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASNameTitle").getAttribute("text")
					.equalsIgnoreCase("Name"));
		} else {
			return false;
		}
	}

	public boolean isNoKeyFobTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoKeyFobsText", 3);
	}

	public boolean isNoSensorTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoSensorsText", 3);
	}

	public boolean isKeyfobPresentInKeyfobsList(String keyfobName) {
		List<WebElement> keyfobs = MobileUtils.getMobElements(objectDefinition, testCase, "KeyfobList");
		boolean found = false;
		for (WebElement keyfob : keyfobs) {
			if (keyfob.getAttribute("text").equalsIgnoreCase(keyfobName)) {
				found = true;
				break;
			}
		}
		return found;
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

	public boolean selectKeyfobFromKeyfobList(String keyfobName) {
		return MobileUtils.clickOnElement(testCase, "xpath", "//android.widget.TextView[@text='" + keyfobName + "']");
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
					.toUpperCase().contains("SERIAL NO : "));
		} else {
			flag = false;
		}
		return flag;
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

	public boolean isSensorNameOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASNameTitle", 3);
	}

	public boolean isSensorStatusOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorStatusOption", 3);
	}

	public boolean isSensorSignalStrengthAndTestOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SignalStrengthOption", 3);
	}

	public boolean isSensorBatteryOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryTitle", 3);
	}

	public boolean isBatteryStatusVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DASBatteryStatus", 3);
	}

	public boolean isModelAndFirmwareDetailsOptionVisibleOnSensorSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelAndFirmwareDetails", 3);
	}

	public boolean verifySensorNameOptionTextOnSensorSettingsScreen() {
		if (this.isKeyfobNameOptionVisibleOnKeyfobSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASNameTitle").getAttribute("text")
					.equalsIgnoreCase("Name"));
		} else {
			return false;
		}
	}

	public boolean verifySensorStatusOptionTextOnSensorSettingsScreen() {
		if (this.isSensorStatusOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "SensorStatusOption").getAttribute("text")
					.equalsIgnoreCase("Status"));
		} else {
			return false;
		}
	}

	public boolean verifySensorSignalStrengthAndTestOptionTextOnSensorSettingsScreen() {
		if (this.isSensorSignalStrengthAndTestOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "SignalStrengthOption").getAttribute("text")
					.equalsIgnoreCase("Signal Strength and Test"));
		} else {
			return false;
		}
	}

	public boolean verifyBatteryOptionTextOnSensorSettingsScreen() {
		if (this.isSensorBatteryOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryTitle").getAttribute("text")
					.equalsIgnoreCase("Battery"));
		} else {
			return false;
		}
	}

	public boolean verifyBatteryStatusTextOnSensorSettingsScreen() {
		if (this.isSensorBatteryOptionVisibleOnSensorSettingsScreen()) {
			String status = MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryStatus")
					.getAttribute("text");
			return (status.equalsIgnoreCase("Good") || status.equalsIgnoreCase("Low"));
		} else {
			return false;
		}
	}

	public boolean verifyModelAndFirmwareDetailsOptionTextOnSensorSettingsScreen() {
		if (this.isModelAndFirmwareDetailsOptionVisibleOnSensorSettingsScreen()) {
			return (MobileUtils.getMobElement(objectDefinition, testCase, "ModelAndFirmwareDetails")
					.getAttribute("text").equalsIgnoreCase("Model and Firmware Details"));

		} else {
			return false;
		}
	}

	public boolean clickOnModelAndFirmwareOptionsOnSensorSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelAndFirmwareDetails");
	}

	public boolean clickOnDeleteButton() throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASButton", 3)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteDASButton");
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "(//XCUIElementTypeButton[@name=\"Delete Base Station\"])[2]",
					testCase)) {
				return MobileUtils.clickOnElement(testCase, "XPATH",
						"(//XCUIElementTypeButton[@name=\"Delete Base Station\"])[2]");
			} else {
				LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
				return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteDASButton");
			}
		}
	}

	public boolean clickOnAlexaAppButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlexaAppLink");
	}

	public boolean clickOnDeleteSensorButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteSensorButton");
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isMotionSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorDeletePopUpMessage", 3);
	}

	public boolean isKeyfobDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyfobDeletePopUpMessage", 3);
	}

	public boolean isAccessSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorDeletePopUpMessage", 3);
	}

	public boolean clickOnCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean clickOnYesButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YesButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButton");
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isDeleteDASDevicePopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteSensorPopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUpConfirmationTitle", 3);
	}

	public boolean isKeyfobPopUpTitleVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteKeyfobPopUpConfirmationTitle", 3);
	}

	public boolean setValueToVolumeSlider(String value) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement volumeSlider = MobileUtils.getMobElement(objectDefinition, testCase, "VolumeSlider");
			Dimension d1 = volumeSlider.getSize();
			Point p1 = volumeSlider.getLocation();
			float sliderLength = d1.getWidth();
			float pixelPerPercent = sliderLength / 100;
			float pixelToBeMoved = Integer.parseInt(value) * pixelPerPercent;
			System.out.println("Setting for " + value);
			System.out.println("X: " + (int) (p1.getX() + pixelToBeMoved));
			System.out.println("Y: " + p1.getY());
			return MobileUtils.clickOnCoordinate(testCase, (int) (p1.getX() + pixelToBeMoved), p1.getY());
		} else {
			MobileUtils.setValueToElement(objectDefinition, testCase, "VolumeSlider", value);
			return true;
		}
	}

	public boolean isBaseStationVolumeValueVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeValue", 3);
	}

	public boolean isBaseStationVolumeValueVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeValue", timeOut);
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

	public boolean isGeofencingSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingSwitch", 3)) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingSwitch").getAttribute("text")
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new Exception("Could not find Geofencing Status Switch");
		}
	}

	public boolean toggleGeofencingSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeofencingSwitch");
	}

}
