package com.honeywell.lyric.das.utils;

import java.util.HashMap;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class DASAlarmUtils {
	private static HashMap<String, MobileObject> fieldObjects;

	public static boolean confirmDismissAlarm(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		fieldObjects = MobileUtils.loadObjectFile(testCase, "Das_AlarmScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AlarmDismissButton")) {
			MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "DismissAlarmPopupOk", 10)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "AlarmDismissButton");
		}
		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DismissAlarmPopupOk");
		DASSolutionCardUtils.waitForDismissProcessRequest(testCase);
		inputs.setInputValue("ALARM_DISMISSED_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		Keyword.ReportStep_Pass(testCase, "ALARM_DISMISSED_TIME " + inputs.getInputValue("ALARM_DISMISSED_TIME"));
		inputs.setInputValue("HOME_TIME", LyricUtils.getDeviceTime(testCase, inputs));
		Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));

		return flag;
	}
}
