package com.honeywell.lyric.das.utils;

import java.util.HashMap;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class DASCameraUtils {
	private static HashMap<String, MobileObject> fieldObjects;

	public static boolean selectDASCameraSolutionCard(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_VideoSolution");
		Keyword.ReportStep_Pass(testCase,
				"Looking for camera named - " + inputs.getInputValue("LOCATION1_CAMERA1_NAME"));
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Camera_Dasboard")) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "Camera_Dasboard");
		} else if (!testCase.getPlatform().toUpperCase().contains("IOS")) {
			if (MobileUtils.isMobElementExists("xpath",
					"//*[@text='" + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "']", testCase)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//*[@text='" + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "']");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera not found");
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("xpath",
					"//*[@value='" + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "']", testCase)) {
				flag = flag & MobileUtils.clickOnElement(testCase, "xpath",
						"//*[@value='" + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "']");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera not found");
				flag = false;
			}
		}
		return flag;
	}

	public static boolean enableCameraMode(TestCases testCase) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_VideoSolution");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CameraOn_toggle", 30)) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "CameraOn_toggle");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera mode is not found");
			flag = false;
		}
		return flag;
	}

	public static boolean verifyLiveStreaming(TestCases testCase) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_VideoSolution");
		DASSolutionCardUtils.waitForElementToDisappear(testCase, fieldObjects, "Progress_Message", 50, 5);
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Live_icon")) {
			Keyword.ReportStep_Pass(testCase, "Live Text is visible");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Live Text is not visible");
			flag = false;
		}
		return flag;
	}

	public static boolean clickOnAttention(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_VideoSolution");
		MobileUtils.clickOnElement(fieldObjects, testCase, "Attention");
		MobileUtils.clickOnElement(fieldObjects, testCase, "AttentionIcon");
		inputs.setInputValue("ALARM_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		Keyword.ReportStep_Pass(testCase, "ALARM_TIME " + inputs.getInputValue("ALARM_TIME"));
		return flag;
	}

	public static boolean VerifyAttentionAlarmScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		// DASCommandControlUtils.waitForProgressBarToComplete(testCase);
		fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "Alarm_Title")
				&& MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmDismissButton")) {
			Keyword.ReportStep_Pass(testCase, "Alarm screen is displayed");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Alarm screen is not displayed");
		}
		return flag;
	}

	public static boolean CreateAttention(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & selectDASCameraSolutionCard(testCase, inputs);
			flag = flag & enableCameraMode(testCase);
			flag = flag & verifyLiveStreaming(testCase);
			flag = flag & clickOnAttention(testCase, inputs);
			flag = flag & VerifyAttentionAlarmScreen(testCase, inputs);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Could not create attention on camera solution card");
		}
		return flag;
	}
}
