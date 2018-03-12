package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class DASCameraSolutionCard extends MobileScreens {

	private static final String screenName = "DASCameraSolutionCard";

	public DASCameraSolutionCard(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean isNewToLyricCameraPopUpTitleVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewToLyricCameraPopUpTitle",timeOut);
	}
	
	public boolean isNewToLyricCameraPopUpMessageVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewToLyricCameraPopUpMessage",timeOut);
	}
	
	public boolean clickOnOkButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}
	
	public boolean isCameraSettingsIntroImageVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraSettingsIntroImage",timeOut);
	}
	
}
