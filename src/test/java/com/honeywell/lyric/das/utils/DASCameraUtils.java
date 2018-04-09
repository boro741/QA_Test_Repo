package com.honeywell.lyric.das.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.screens.DASCameraSolutionCard;

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

	public static boolean clickOnAttention(TestCases testCase) {
		boolean flag = true;
		DASCameraSolutionCard cameraCard = new DASCameraSolutionCard(testCase);
		flag = flag &cameraCard.clickOnAttention();
		return flag;
	}
	
	public static boolean clickOnCreateAttention(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DASCameraSolutionCard cameraCard = new DASCameraSolutionCard(testCase);
		flag = flag &cameraCard.clickOnCreateAttention(inputs);
		return flag;
	}
	
	public static boolean clickOnCancelAttention(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DASCameraSolutionCard cameraCard = new DASCameraSolutionCard(testCase);
		flag = flag &cameraCard.clickOnCancelAttention();
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
			flag = flag & clickOnAttention(testCase);
			flag = flag & clickOnCreateAttention(testCase, inputs);
			flag = flag & VerifyAttentionAlarmScreen(testCase, inputs);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Could not create attention on camera solution card");
		}
		return flag;
	}

	public static boolean verifyNewToLyricPopUp(TestCases testCase) {
		boolean flag = true;
		DASCameraSolutionCard dc = new DASCameraSolutionCard(testCase);
		if (dc.isNewToLyricCameraPopUpTitleVisible(15)) {
			Keyword.ReportStep_Pass(testCase, "New To Lyric Camera Pop Up Title is displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"New To Lyric Camera Pop Up Title not displayed correctly");
		}
		return flag;
	}
	
 	public static boolean isCameraLiveStreaming(TestCases testCase) throws Exception {
 		try {
 			DASCameraSolutionCard dc = new DASCameraSolutionCard(testCase);
 			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
 			fWait.pollingEvery(3, TimeUnit.SECONDS);
 			fWait.withTimeout(1, TimeUnit.MINUTES);
 			fWait.until(new Function<CustomDriver, Boolean>() {
 				@Override
 				public Boolean apply(CustomDriver driver) {
 					if (dc.isLoadingLiveTextVisible(1) || dc.isCameraStreamLoadingProgressBarVisible(1)) {
 						return false;
 					} else {
 						return true;
 					}
 				}
 			});
 			if(dc.isNewToLyricCameraPopUpTitleVisible(5))
 			{
 				dc.clickOnNotNowButton();
 				CoachMarkUtils.closeCoachMarks(testCase);
 			}
 			if(dc.isLiveTextVisible(5))
 			{
 				return true;
 			}
 			else if(dc.isCameraOffTextVisible(5))
 			{
 				return false;
 			}
 			else
 			{
 				throw new Exception("Invalid Streaming text displayed");
 			}
 		} catch (TimeoutException e) {
 			throw new Exception("Loading Live Stream text did not disappear. Wait Time : 1 minute");
 		}
 	}




}
