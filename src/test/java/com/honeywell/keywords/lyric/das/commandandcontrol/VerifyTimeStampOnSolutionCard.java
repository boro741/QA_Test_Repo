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
		String deviceLocationTime = LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT");
		System.out.println("#########deviceLocationTime: " + deviceLocationTime);
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
		SimpleDateFormat androidFormat = new SimpleDateFormat("h:mm a");
		SimpleDateFormat iosFormat = new SimpleDateFormat("h:mm a");
		SimpleDateFormat iosFormat2 = new SimpleDateFormat("H:mm");
		String deviceTimePlusOneMin = LyricUtils.addMinutesToDate(testCase, deviceLocationTime, 1);
		String deviceTimeMinusOneMin = LyricUtils.addMinutesToDate(testCase, deviceLocationTime, -1);
		System.out.println("#########deviceTimePlusOneMin: " + deviceTimePlusOneMin);
		System.out.println("#########deviceTimeMinusOneMin: " + deviceTimeMinusOneMin);
		String displayedTimeStamp, time1, time2, time3;
		try {
			displayedTimeStamp = sc.getTimeStamp();
			System.out.println("#########displayedTimeStamp: " + displayedTimeStamp);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				time1 = androidFormat.format(timeFormat.parse(deviceLocationTime));
				time2 = androidFormat.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = androidFormat.format(timeFormat.parse(deviceTimeMinusOneMin));
				System.out.println("#########time1: " + time1);
				System.out.println("#########time2: " + time2);
				System.out.println("#########time3: " + time3);
			} else {
				time1 = iosFormat.format(timeFormat.parse(deviceLocationTime));
				time2 = iosFormat.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = iosFormat.format(timeFormat.parse(deviceTimeMinusOneMin));
				System.out.println("#########time1: " + time1);
				System.out.println("#########time2: " + time2);
				System.out.println("#########time3: " + time3);
			}
			if (displayedTimeStamp.equalsIgnoreCase("Since " + time1)
					|| displayedTimeStamp.equalsIgnoreCase("Since " + time2)
					|| displayedTimeStamp.equalsIgnoreCase("Since " + time3)) {
				Keyword.ReportStep_Pass(testCase, "Time stamp is correctly displayed: " + displayedTimeStamp);
			} else {
				time1 = iosFormat2.format(timeFormat.parse(deviceLocationTime));
				time2 = iosFormat2.format(timeFormat.parse(deviceTimePlusOneMin));
				time3 = iosFormat2.format(timeFormat.parse(deviceTimeMinusOneMin));
				System.out.println("#########time1: " + time1);
				System.out.println("#########time2: " + time2);
				System.out.println("#########time3: " + time3);
				if (displayedTimeStamp.equalsIgnoreCase("Since " + time1)
						|| displayedTimeStamp.equalsIgnoreCase("Since " + time2)
						|| displayedTimeStamp.equalsIgnoreCase("Since " + time3)) {
					Keyword.ReportStep_Pass(testCase, "Time stamp is correctly displayed: " + displayedTimeStamp);
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
