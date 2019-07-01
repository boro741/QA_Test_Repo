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
import com.honeywell.lyric.das.utils.DASSettingsUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.Dashboard;

public class SwipesOnDashboard extends Keyword {

		private TestCases testCase;
		public ArrayList<String> screen;
		public boolean flag = true;
		
		public SwipesOnDashboard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> screen) {
			this.testCase = testCase;
			this.screen = screen;
		}
		@Override
		@BeforeKeyword
		public boolean preCondition() throws KeywordException {
			return flag;
		}
		
		@Override
		@KeywordStep(gherkins = "^user swipes to (.*) on (.*)$")
		public boolean keywordSteps() throws KeywordException { 
			try {
				if (screen.get(1).equalsIgnoreCase("Dashboard Status Area")) {
					switch (screen.get(0).toUpperCase()) {
					case "LEFT": {
						Dashboard d = new Dashboard(testCase);
						LyricUtils.swipeLeft(testCase, d.getDeviceEleInDashboardScreen());
						break;
					}
					case "RIGHT": {
						Dashboard d = new Dashboard(testCase);
						LyricUtils.swipeRight(testCase, d.getDeviceEleInDashboardScreen());
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
					}
					}
				} else if (screen.get(1).equalsIgnoreCase("Dashboard")) {
					switch (screen.get(0).toUpperCase()) {
					case "TOP OF THE SCREEN": {
						Dashboard d = new Dashboard(testCase);
						if(d.isWeatherIconDisplayed()) {
						LyricUtils.scrollUpAList(testCase, d.getDeviceEleInDashboardScreen());
						Keyword.ReportStep_Pass(testCase, "User swipes to top of the screen");
						}else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									screen.get(0) + " does not exists");
						}
						break;
					}
					
					case "BOTTOM OF THE SCREEN": {
						Dashboard d = new Dashboard(testCase);
						if(d.isAddDeviceIconVisible(2)) {
						LyricUtils.scrollDownAList(testCase, d.getDeviceEleInDashboardScreen());
						Keyword.ReportStep_Pass(testCase, "User swipes to bottom of the screen");
						}else {

							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									screen.get(0) + " does not exists");
						}
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
					}
				}
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
