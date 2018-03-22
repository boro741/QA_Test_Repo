package com.honeywell.keywords.jasper.scheduling.Verify;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

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
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

public class VerifyIfUserCanSetEndTimeSameAsStart extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	String startTime = "", endTime = "";

	public VerifyIfUserCanSetEndTimeSameAsStart(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user should not be allowed to edit end time with same as start time$")
	public boolean keywordSteps() throws KeywordException {
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			SchedulingScreen ss = new SchedulingScreen(testCase);
			List<WebElement> TimePeriod = ss.getSchedulePeriodTimeElements();
			int TimePeriod_size = TimePeriod.size();
			TimePeriod.get(TimePeriod_size-1).click();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
					startTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getAttribute("name");
					ReportStep_Pass(testCase, "Start time is shown: " + startTime);
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Start time is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
					endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
							.getAttribute("name");
					ReportStep_Pass(testCase, "End time is shown: " + endTime);

					if (MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getAttribute("enabled")
							.equalsIgnoreCase("false")) {
						ReportStep_Pass(testCase, "End time is not editable");

						flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
						return flag;
					} else {
						ReportStep_Pass(testCase, "************* Setting End time to " + startTime + " *************");

						if (!startTime.contains("M") && !startTime.contains("m")) {
							Date tempStartTime, tempEndTime;
							SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
							SimpleDateFormat df12 = new SimpleDateFormat("hh:mm aa");
							String dateStringStartTime = startTime.replaceAll("\\.", "");
							String dateStringEndTime = endTime.replaceAll("\\.", "");
							try {
								tempStartTime = df24.parse(dateStringStartTime);
								startTime = df12.format(tempStartTime);
								tempEndTime = df24.parse(dateStringEndTime);
								endTime = df12.format(tempEndTime);
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error: " + e.getMessage());
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error: " + e.getMessage());
							}
						}

						inputs.setInputValue(InputVariables.VERIFY_SET_PERIOD_TIME, "false");
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, startTime, "TimeChooserEndTime", true, false);
						ReportStep_Pass(testCase, "************* Completed setting End time *************");

						endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
								.getAttribute("name");

						if (!startTime.equalsIgnoreCase(endTime)) {
							ReportStep_Pass(testCase, "Not able to set End time: " + endTime
									+ " to same value as Start time: " + startTime);
						} else {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"End time: " + endTime + "is set to same value as Start time: " + startTime);
						}
						flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					}
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "End time is not shown");
				}
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
					startTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getAttribute("value");
					ReportStep_Pass(testCase, "Start time is shown: " + startTime);
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Start time is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
					endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
							.getAttribute("value");
					System.out.println(endTime);
					ReportStep_Pass(testCase, "End time is shown: " + endTime);

					if (MobileUtils.getMobElement(testCase, "name", "End").getAttribute("value")
							.equalsIgnoreCase("false")) {
						ReportStep_Pass(testCase, "End time is not editable");

						flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
						return flag;
					} else {
						ReportStep_Pass(testCase, "************* Setting End time to " + startTime + " *************");

						if (!startTime.contains("M") && !startTime.contains("m")) {
							Date tempStartTime, tempEndTime;
							SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
							SimpleDateFormat df12 = new SimpleDateFormat("hh:mm aa");
							String dateStringStartTime = startTime.replaceAll("\\.", "");
							String dateStringEndTime = endTime.replaceAll("\\.", "");
							try {
								tempStartTime = df24.parse(dateStringStartTime);
								startTime = df12.format(tempStartTime);
								tempEndTime = df24.parse(dateStringEndTime);
								endTime = df12.format(tempEndTime);
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error: " + e.getMessage());
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error: " + e.getMessage());
							}
						}

						inputs.setInputValue(InputVariables.VERIFY_SET_PERIOD_TIME, "false");
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, startTime, "TimeChooserEndTime", true, false);
						ReportStep_Pass(testCase, "************* Completed setting End time *************");

						endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
								.getAttribute("name");

						if (!startTime.equalsIgnoreCase(endTime)) {
							ReportStep_Pass(testCase, "Not able to set End time: " + endTime
									+ " to same value as Start time: " + startTime);
						} else {
							flag = false;
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"End time: " + endTime + "is set to same value as Start time: " + startTime);
						}
						flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					}
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "End time is not shown");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}