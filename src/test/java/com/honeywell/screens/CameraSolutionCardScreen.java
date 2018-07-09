package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class CameraSolutionCardScreen extends MobileScreens {

	private static final String screenName = "CameraSolutionCard";

	public CameraSolutionCardScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAppSettingsIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppSettingsIcon");
	}

	public boolean isBackButtonInCameraSolutionCardScreenVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton");
	}

	public boolean clickOnBackButtonInCameraSolutionCardScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	public boolean isCameraOnButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOnButton", timeOut);
	}

	public boolean clickOnCameraOnButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOnButton");
	}

	public boolean isCameraOffButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOffButton", timeOut);
	}

	public boolean clickOnCameraOffButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOffButton");
	}
	
	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}
}