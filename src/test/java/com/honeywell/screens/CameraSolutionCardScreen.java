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
	
	public boolean isNoClipsTextAvailable() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoClipsAvailabletext");
	}
	
	public boolean getCameraStatus(int timeOut) {
		boolean cameraStatus = false;
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "PlayStream", timeOut)) {
			cameraStatus = true;
	}
	   return cameraStatus;
	}
	
	public boolean isSanpShotSavedTextExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SnapshotDescription");
	}
	
	public boolean isSanpShotIconExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Snap");
	}
	public boolean isPushIconExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Talk");
	}
	public boolean isAttentionIconExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Attention");
	}
	
	public boolean isLiveStreamProgressBarExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LiveStreamProgressBar");
	}
	
	public boolean isCameraPlayButtonExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraPlayButton", timeout);
	}
	
	public boolean isCameraLiveIndicatorExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraLiveIndicator");
	}
	
	public boolean clickSanpShotIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Snap");
	}
	public boolean clickPushIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Talk");
	}
	public boolean clickAttentionIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Attention");
	}
	
	public boolean clickOnCameraPlayButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"CameraPlayButton");
	}
	
}
