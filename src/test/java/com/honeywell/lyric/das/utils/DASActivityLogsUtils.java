package com.honeywell.lyric.das.utils;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.screens.ActivityLogsScreen;

public class DASActivityLogsUtils {

	public static boolean openActivityLogs(TestCases testCase) throws Exception {
		boolean flag = true;
		ActivityLogsScreen al = new ActivityLogsScreen(testCase);
		if (al.isOpenActivityLogsIconVisible()) {
			al.clickOnOpenActivityLogsIcon();
		}
		return flag;
	}

	public static boolean closeActivityLogs(TestCases testCase) throws Exception {
		boolean flag = true;
		ActivityLogsScreen al = new ActivityLogsScreen(testCase);
		if (al.isCloseActivityLogsIconVisible()) {
			al.clickOnCloseActivityLogsIcon();
		}
		return flag;
	}
}
