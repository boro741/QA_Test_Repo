package com.honeywell.keywords.lyric.das.commandandcontrol;

import java.text.SimpleDateFormat;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class VerifyTimeStampOnSolutionCard extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyTimeStampOnSolutionCard(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the correct time stamp$")
	public boolean keywordSteps() {
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		String deviceTime = LyricUtils.getDeviceTime(testCase, inputs);
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
		SimpleDateFormat androidFormat = new SimpleDateFormat("h:mm a");
		SimpleDateFormat iosFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat iosFormat2 = new SimpleDateFormat("HH:mm");
		String deviceTimePlusOneMin = LyricUtils.addMinutesToDate(testCase, deviceTime, 1);
		String deviceTimeMinusOneMin = LyricUtils.addMinutesToDate(testCase, deviceTime, -1);
		String displayedTimeStamp, time1, time2, time3;
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedTimeStamp = sc.getTimeStamp();

				time1 = androidFormat.format(timeFormat.parse(deviceTime));
				time2 = androidFormat.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = androidFormat.format(timeFormat.parse(deviceTimeMinusOneMin));
			} else {
				displayedTimeStamp = sc.getTimeStamp();
				time1 = iosFormat.format(timeFormat.parse(deviceTime));
				time2 = iosFormat.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = iosFormat.format(timeFormat.parse(deviceTimeMinusOneMin));
			}
			if (displayedTimeStamp.equalsIgnoreCase("Since " + time1)
					|| displayedTimeStamp.equalsIgnoreCase("Since " + time2)
					|| displayedTimeStamp.equalsIgnoreCase("Since " + time3)) {
				Keyword.ReportStep_Pass(testCase, "Time stamp is correctly displayed");
			} else {
				time1 = iosFormat2.format(timeFormat.parse(deviceTime));
				time2 = iosFormat2.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = iosFormat2.format(timeFormat.parse(deviceTimeMinusOneMin));
				if (displayedTimeStamp.equalsIgnoreCase("Since " + time1)
						|| displayedTimeStamp.equalsIgnoreCase("Since " + time2)
						|| displayedTimeStamp.equalsIgnoreCase("Since " + time3)) {
					Keyword.ReportStep_Pass(testCase, "Time stamp is correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Time stamp is not correctly displayed. Expected: 'Since " + time1 + " || Since " + time2
									+ " || Since " + time3 + "'. Displayed : '" + displayedTimeStamp + "'");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
