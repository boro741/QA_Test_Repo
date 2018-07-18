package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class PrimaryCard extends MobileScreens {

	private static final String screenName = "PrimaryCard";

	public PrimaryCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCogIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CogIcon", 3);
	}

	public boolean clickOnCogIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CogIcon");
	}

	public boolean isclickOnBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}
	
	public boolean isThermostatCurrentTemperatureVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}
	
	public String getThermostatCurrentTemperatureValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}
 }
