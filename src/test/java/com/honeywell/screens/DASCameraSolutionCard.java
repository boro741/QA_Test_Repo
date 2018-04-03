package com.honeywell.screens;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class DASCameraSolutionCard extends MobileScreens {

	private static final String screenName = "DASCameraSolutionCard";

	public DASCameraSolutionCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isNewToLyricCameraPopUpTitleVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NewToLyricCameraPopUpTitle",timeOut);
	}

	public boolean clickOnOkButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}

	public boolean isCameraSettingsIntroImageVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraSettingsIntroImage",timeOut);
	}

	public boolean isLiveTextVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LiveStreamingIcon",timeOut);
	}

	public boolean isLoadingLiveTextVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingLiveFeedText",timeOut);
	}

	public boolean isCameraOffTextVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CamerOffStreamingText",timeOut);
	}

	public boolean isCameraStreamLoadingProgressBarVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStreamLoadingProgressBar",timeOut);
	}

	public boolean clickOnNotNowButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NotNowButton");
	}

	public boolean clickOnAttention()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Attention");
	}
	
	public boolean clickOnCreateAttention(TestCaseInputs inputs)
	{
		inputs.setInputValue("ALARM_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		Keyword.ReportStep_Pass(testCase, "ALARM_TIME " + inputs.getInputValue("ALARM_TIME"));
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionIcon");
	}
	
	public boolean clickOnCancelAttention()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionCancel");
	}
}
