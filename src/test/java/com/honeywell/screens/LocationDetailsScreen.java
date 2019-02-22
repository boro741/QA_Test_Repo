package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import io.appium.java_client.MobileElement;

public class LocationDetailsScreen extends MobileScreens {

	private static final String screenName = "LocationDetailsScreen";
	public static final int d = 176;
	public static final char degree = (char) d;

	public LocationDetailsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isSplashScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SplashScreen", timeOut, false);
	}

	public boolean isProgressBarVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar", timeOut, false);
	}

	public boolean isEditButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EditButton");
	}

	public boolean clickOnEditButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EditButton");
	}

	public boolean isSaveButtonEnabled() {
		MobileElement saveButtonEnabled = MobileUtils.getMobElement(objectDefinition, testCase, "SaveButton");
		if (saveButtonEnabled.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean clickOnSaveButton() {
		if (isSaveButtonEnabled() == true) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
		}
		return false;
	}

	public boolean enterZipCode(String zipCodeValue) {
		MobileUtils.clearTextField(objectDefinition, testCase, "ZipCode");
		return MobileUtils.setValueToElement(objectDefinition, testCase, "ZipCode", zipCodeValue);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

}