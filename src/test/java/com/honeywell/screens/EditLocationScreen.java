package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class EditLocationScreen extends MobileScreens {

	private static final String screenName = "EditLocation";

	public EditLocationScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isEditButtonExist(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditAddressButton", timeOut);
	}

	public boolean clickEditButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EditAddressButton");
	}

	public boolean setLocationName(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "LocationNameText", value);
	}

	public boolean setAddress(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "StreetAddressText", value);
	}

	public boolean setCity(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "CityNameText", value);
	}

	public boolean setState(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "StateText", value);
	}

	public boolean setZipCode(String value) {
		return MobileUtils.setValueToElement(objectDefinition, testCase, "ZipcodeText", value);
	}

	public boolean isSaveButtonAvailable() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveButton", 15);
	}

	public boolean clickOnSaveButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
	}
}
