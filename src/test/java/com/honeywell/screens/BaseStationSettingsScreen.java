package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class BaseStationSettingsScreen extends MobileScreens {

	// String values used in the methods
	private static final String screenName = "DASSettings";
	public static final String BASESTATIONCONFIGURATION = "Base Station Configuration";
	public static final String ENTRYEXITDELAYSETTINGS = "Entry/Exit Delay";
	public static final String GEOFENCING = "Geofencing";
	public static final String KEYFOB = "Key Fob";
	public static final String VOLUME = "Volume";
	public static final String BASESTATIONWIFI = "Base Station Wi-Fi";
	public static final String SENSORS = "Sensors";

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
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
							BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				}
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
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
							BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			}
			if (this.isBaseStationConfigurationsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.KEYFOB: {
			boolean flag = true;
			if (this.isKeyFobOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, "text",
							BaseStationSettingsScreen.KEYFOB);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, "value",
							BaseStationSettingsScreen.KEYFOB);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "KeyFobOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.SENSORS: {
			boolean flag = true;
			if (this.isSensorsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SensorsOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, "text",
							BaseStationSettingsScreen.SENSORS);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, "value",
							BaseStationSettingsScreen.SENSORS);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SensorsOption");
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

	public boolean isKeyFobOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "KeyFobOption", 3);
	}

	public boolean isSensorsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SensorsOption", 3);
	}

	public boolean isEntryExitDelaySettingsOptionVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", timeOut);
	}

	public boolean clickOnNoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
	}

	public boolean clickOnYesButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "YesButton");
	}

	public boolean isDeleteDASPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean isDeleteSensorPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSensorPopUpConfirmationTitle", 3);
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
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (elementName.equals(BaseStationSettingsScreen.GEOFENCING)) {
				if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "GeofencingOption", 3)) {
					LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.GEOFENCING);
				}
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "GeofencingOption")
						.getAttribute("enabled"));
			} else if (elementName.equals(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS)) {
				if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", 3)) {
					LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				}
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "EntryExitDelayOption")
						.getAttribute("enabled"));
			} else if (elementName.equals(BaseStationSettingsScreen.VOLUME)) {
				if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "VolumeOption", 3)) {
					LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.VOLUME);
				}
				return Boolean.valueOf(
						MobileUtils.getMobElement(objectDefinition, testCase, "VolumeOption").getAttribute("enabled"));
			} else if (elementName.equals(BaseStationSettingsScreen.BASESTATIONWIFI)) {
				if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationWiFiOption", 3)) {
					LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.BASESTATIONWIFI);
				}
				return Boolean.valueOf(MobileUtils.getMobElement(objectDefinition, testCase, "BaseStationWiFiOption")
						.getAttribute("enabled"));
			} else {
				throw new Exception("Invalid Input : " + elementName);
			}
		} else {
			return true;
		}
	}

	public boolean verifyParticularEntryExitDelayOptionVisible(String option) throws Exception {
		String attribute = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
		} else {
			attribute = "value";
		}
		return LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute, option);
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
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASNameTitle").getAttribute("text")
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
			return (MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryTitle").getAttribute("text")
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
			String status = MobileUtils.getMobElement(objectDefinition, testCase, "DASBatteryStatus")
					.getAttribute("text");
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
			return (MobileUtils.getMobElement(objectDefinition, testCase, "ModelAndFirmwareDetails")
					.getAttribute("text").equalsIgnoreCase("Model and Firmware Details"));

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
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelDetailsLabel")
					.getAttribute("text").equalsIgnoreCase("Model Details"));
		} else {
			flag = false;
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelNameLabel")) {
			flag = flag & (MobileUtils.getMobElement(objectDefinition, testCase, "ModelNameLabel").getAttribute("text")
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
		return flag;
	}

	public boolean verifyDASFirmwareDetailsOnModelAndFirmwareDetailsPage() {
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
		return MobileUtils.clickOnElement(testCase, "xpath", "//android.widget.TextView[@text='" + sensorName + "']");
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
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text", "DELETE");
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteDASButton");
		}
	}

	public boolean isMotionSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensorDeletePopUpMessage", 3);
	}

	public boolean isAccessSensorDeletePopUpMessageVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AccessSensorDeletePopUpMessage", 3);
	}
	
	public boolean clickOnCancelButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

}
