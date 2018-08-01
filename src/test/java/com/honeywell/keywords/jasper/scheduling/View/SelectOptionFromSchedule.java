package com.honeywell.keywords.jasper.scheduling.View;


import java.util.ArrayList;


import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.SchedulingScreen;

public class SelectOptionFromSchedule extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	String jasperStatType = "";
	public ArrayList<String> screen;

	public SelectOptionFromSchedule(TestCases testCase, TestCaseInputs inputs ,ArrayList<String> screen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.screen = screen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects \"(.+)\" from Scheduling screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			SchedulingScreen sch = new SchedulingScreen(testCase);
			switch (screen.get(0).toUpperCase()) {
			case "SWITCH TO GEOFENCING": {
				if (sch.isScheduleOptionsVisible(10)){
					flag = flag & sch.clickOnScheduleOptionsButton();
					flag = flag & sch.clickOnSwitchToGeofenceButton();
				}
				break;
			}
			case "CREATE NEW TIME SCHEDULE": {
				if (sch.isScheduleOptionsVisible(10)){
					flag = flag & sch.clickOnScheduleOptionsButton();
					flag = flag & sch.clickOnCreateNewTimeScheduleButton();
				}
				break;
			}
			case "SWITCH TO TIME BASED":{
				if (sch.isScheduleOptionsVisible(10)){
					flag = flag & sch.clickOnScheduleOptionsButton();
					flag = flag & sch.clickOnSwitchToTimeScheduleButton();
				}
				break;
			}
			case "TURN SCHEDULE OFF":{
				if (sch.isScheduleOptionsVisible(10)){
					flag = flag & sch.clickOnScheduleOptionsButton();
					flag = flag & sch.clickOnScheduleOffButton();
				}
				break;
			}
			case "TAP ON RESUME":{
				if (sch.isScheduleOffOverlayVisible(10)){
					flag = flag & sch.clickOnScheduleOffOverlay();
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + screen.get(0));
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
