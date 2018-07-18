package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class ThermostatSettingsScreen extends MobileScreens {

	private static final String screenName = "ThermostatSettings";

	public ThermostatSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isThermostatSettingsHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatSettingsHeaderTitle", timeOut);
	}

	public boolean isThermostatIndoorTempAlertSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Thermostat Indoor Temperature Alert Switch");
		}
	}

	public boolean toggleThermostatIndoorTempAlertSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch");
	}

	public boolean isThermostatIndoorTempAlertOptionVisible(String indoorTempAlertOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorTempAlertTitle")
					&& MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatIndoorTempAlertTitle")
							.equalsIgnoreCase(indoorTempAlertOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']")
							.getAttribute("value").equalsIgnoreCase(indoorTempAlertOption)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertOption + "']")
							.getAttribute("visible").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isThermostatTempAlertRangeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatTempAlertRange");
	}

	public boolean clickOnThermostatTempAlertRange() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatTempAlertRange");
	}

	public boolean isThermostatIndoorTempAlertRangeOptionVisible(String indoorTempAlertRangeOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
							+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]",
					testCase)
					&& MobileUtils.getFieldValue(testCase, "XPATH",
							"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_"
									+ indoorTempAlertRangeOption.toLowerCase() + "_text" + "\'" + "]")
							.equalsIgnoreCase(indoorTempAlertRangeOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@value='" + indoorTempAlertRangeOption + "']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertRangeOption + "']")
							.getAttribute("value").equalsIgnoreCase(indoorTempAlertRangeOption)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@value='" + indoorTempAlertRangeOption + "']")
							.getAttribute("visible").equalsIgnoreCase("true")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnBelowTempAlertRangeOption(String indoorTempAlertRangeOption) {
		boolean flag = true;
		String expectedTempAlertRangeOption = indoorTempAlertRangeOption.contains(" ")
				? indoorTempAlertRangeOption.split(" ")[0]
				: indoorTempAlertRangeOption;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
							+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
							+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]",
					testCase)) {
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/fragment_high_low_alert_setting_"
								+ expectedTempAlertRangeOption.toLowerCase() + "_spinner" + "\'" + "]"
								+ "/android.widget.LinearLayout/android.widget.TextView[@resource-id=" + "\'"
								+ "com.honeywell.android.lyric:id/list_item_lyric_spinner_text'" + "]");
				return flag;
			} else {
				flag = false;
			}
		} else {

		}
		return flag;
	}
}
