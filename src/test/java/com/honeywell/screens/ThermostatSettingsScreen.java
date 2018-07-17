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
			if (MobileUtils.isMobElementExists("ID", "alert_title_layout", testCase) && MobileUtils
					.getFieldValue(testCase, "ID", "alert_title_layout").equalsIgnoreCase(indoorTempAlertOption)) {
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
}
