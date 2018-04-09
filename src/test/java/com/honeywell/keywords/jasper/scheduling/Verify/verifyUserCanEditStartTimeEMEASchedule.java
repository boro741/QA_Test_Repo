package com.honeywell.keywords.jasper.scheduling.Verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;


public class verifyUserCanEditStartTimeEMEASchedule extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;
	String schedulePeriodToSelect = "", temp = "";
	int i = 0;
	ArrayList<String> arrlist = new ArrayList<String>(8);

	public verifyUserCanEditStartTimeEMEASchedule(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user should be allowed to edit start time with all possible values for both time formats$")
	public boolean keywordSteps() throws KeywordException {

		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String StartTime = "", StarttimetoSet = "", StarttimetoSet24Hour = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
				StartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getText();
				Keyword.ReportStep_Pass(testCase, "Start time is shown: " + StartTime);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Start time is not shown");
			}
		} else {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
				StartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getAttribute("value");
				Keyword.ReportStep_Pass(testCase, "Start time is shown: " + StartTime);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Start time is not shown");
			}
		}
		Random rn = new Random();
		int TimetoAdd = rn.nextInt((24 - 1) + 1) + 1;
		int MinutestoAdd = rn.nextInt((50 - 10) + 1) + 10;
		MinutestoAdd = (MinutestoAdd / 10) * 10;
		if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")
				&& (testCase.getPlatform().toUpperCase().contains("IOS"))) {
			StarttimetoSet = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, StartTime, true, TimetoAdd, MinutestoAdd);
			System.out.println(StarttimetoSet);
		} else {
			StarttimetoSet = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, StartTime, true, TimetoAdd, 0);
		}
		if (StarttimetoSet.contains("m") || StarttimetoSet.contains("M")) {
			flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, StarttimetoSet, "TimeChooser", true, true);
		} else {
			try {
				SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
				StarttimetoSet24Hour = date12Format.format(date24Format.parse(StarttimetoSet));
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, StarttimetoSet24Hour, "TimeChooser", true, true);
			} catch (ParseException e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[ParseException] " + e.getMessage());
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Exception] " + e.getMessage());
			}
		}
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			if (!MobileUtils.clickOnElement(testCase, "NAME", "Navigation_Left_Bar_Item")) {
				flag = false;
			}
		}
		flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
