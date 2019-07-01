package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.GeofenceSettings;

public class VerifySpringBackAndDisplay extends Keyword {

	private TestCases testCase;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;

	public VerifySpringBackAndDisplay(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Dashboard status area should move in the direction of the swipe to a certain point, spring back and display (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (expectedScreen.get(0).equalsIgnoreCase("Weather status")) {
				Dashboard d = new Dashboard(testCase);
				if (d.isWeatherIconVisible() || d.isTodaysForecastDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Dashboard displayed with " + expectedScreen.get(0).toUpperCase());
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to Dashboard displayed with " + expectedScreen.get(0).toUpperCase());
				}

			} else if (expectedScreen.get(0).equalsIgnoreCase("Geofence status")) {
				GeofenceSettings gs = new GeofenceSettings(testCase);
				if (gs.isGeofencingTitleDisplayed()) {
					Keyword.ReportStep_Pass(testCase,
							"Dashboard displayed with " + expectedScreen.get(0).toUpperCase());
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to Dashboard displayed with " + expectedScreen.get(0).toUpperCase());
				}

			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid Input: " + expectedScreen.get(1));
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}

}
