package com.honeywell.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

import io.appium.java_client.MobileElement;

public class CameraSolutionCardScreen extends MobileScreens {

	private static final String screenName = "CameraSolutionCard";

	public CameraSolutionCardScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAppSettingsIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AppSettingsIcon");
	}

	public boolean isBackButtonInCameraSolutionCardScreenVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton",timeout);
	}
	public boolean isBackButtonInCameraSolutionCardScreenVisible1(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton1",timeout);
	}

	public boolean clickOnBackButtonInCameraSolutionCardScreen() {
		boolean success= true;
		if(isBackButtonInCameraSolutionCardScreenVisible(20)) {
			success = MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		}
//		else if(isBackButtonInCameraSolutionCardScreenVisible1(4)) {
//			MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton1");
//		}else {
//			success= false;
//		}
		return success;
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
	public boolean isCameraRingClosedVisible1() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraRingClosedTextSolutionCard");
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
	
	public boolean isLiveStreamProgressBarExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LiveStreamProgressBar",timeout);
	}
	
	public boolean isCameraPlayButtonExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraPlayButton", timeout);
	}
	
	public boolean isCameraLiveIndicatorExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraLiveIndicator");
	}
	public boolean isRetryTextExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RetryText");
	}
	public boolean isAllowExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ALLOW",timeout);
	}
	
	public boolean isCancelPushToTalkExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelPushToTalk");
	}
	public boolean isMicrophonePushToTalkExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MicrophonePushToTalk");
	}
	public boolean isMicrophonePushToTalkHeaderExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MicrophonePushToTalkHeader");
	}
	/////
	public boolean isAttentionDescExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AttensionDescription");
	}
	public boolean isAttentionCancelExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AttentionCancel");
	}
	public boolean isAttentionPanicImageExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AttentionPanicImage");
	}
	public boolean isDissmissAlarmExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DissmissAlarm");
	}
	public boolean isAlarmCallButtonExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmCallButton");
	}
	public boolean isGetHelpExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OfflineHelp");
	}
	public boolean isGetHelpScreenExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GetHelpPage");
	}
	public boolean isActivityLogExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivityLog");
	}
	public boolean clickGetHelp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OfflineHelp");
	}
	public boolean clickActivityLog() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ActivityLog");
	}
	public boolean clickAttentionCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionCancel");
	}
	public boolean clickAttentionPanicImag() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionPanicImage");
	}
	public boolean clickDissmissAlarm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DissmissAlarm");
	}
	public boolean clickOK() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButton",5)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButton");
		}else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton1");
		}
		
	}
	public String getTextMicrophonePushToTalk() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "MicrophonePushToTalkHeader");
	}
	public String getRetryText() {
		return MobileUtils.getAttribute(testCase, objectDefinition, "RetryText", "text");
	}
	public boolean clickMicrophonePushToTalk() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MicrophonePushToTalk");
	}
	public WebElement getMicrophonePushToTalk() {
		if(testCase.getMobileDriver().getPlatformName().contains("IOS"))
		{
			return testCase.getMobileDriver().findElement(By.name("Mic Button"));
		}
		else{return testCase.getMobileDriver().findElement(By.id("ivWhite"));}
	}
	public boolean clickLiveIndicator() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraLiveIndicator");
	}
	public boolean clickAllow() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ALLOW");
	}
	public boolean clickFullScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FullScreen");
	}
	public boolean clickSanpShotIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Snap");
	}
	public boolean clickPushtoTalkIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Talk");
	}
	public boolean clickAttentionIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"Attention");
	}
	
	public boolean clickOnCameraPlayButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase,"CameraPlayButton");
	}
	
}
