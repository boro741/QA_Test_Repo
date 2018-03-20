package com.honeywell.jasper.utils;

import java.util.HashMap;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class JasperSchedulingViewUtils {
	public static boolean selectIndividualDaysViewOrGroupedDaysView(TestCases testCase, String viewDays) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		try {
			if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "ViewByIndividualDays", 10)) {
				flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
				if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "ViewByIndividualDays", 5)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "App not in schedule view screen");
				}
			}

			if ("Individual Days".equalsIgnoreCase(viewDays)) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ViewByIndividualDays", 5)) {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ViewByIndividualDays")) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Individual days");
					}
				}
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ViewByGroupedDays", 5)) {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ViewByGroupedDays")) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Grouped days");
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}

		return flag;
	}


}