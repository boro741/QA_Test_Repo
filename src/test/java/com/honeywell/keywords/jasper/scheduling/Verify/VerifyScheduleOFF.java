package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.screens.SchedulingScreen;

public class VerifyScheduleOFF extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyScheduleOFF(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify the \"(.*)\" on the \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException{
		try {
			
			if (parameters.get(1).equalsIgnoreCase("Scheduling")) {
				switch (parameters.get(0).toUpperCase()) {
				case "SCHEDULE OFF OVERLAY": {
					SchedulingScreen OverLay = new SchedulingScreen(testCase);
					flag = flag & OverLay.isScheduleOffOverlayVisible(10);
					if(flag){
						System.out.println("Successfully schedule overlay " + parameters.get(0) + "screen");
						Keyword.ReportStep_Pass(testCase, "Schedule OFF Overlay displayed");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF overlay not displayed");
					}
				}break;
				case "SCHEDULE OFF OVERLAY DISABLED": {
					SchedulingScreen OverLay = new SchedulingScreen(testCase);
					if(!OverLay.isScheduleOffOverlayVisible(10)){
						Keyword.ReportStep_Pass(testCase, "Schedule OFF Overlay disappear");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF overlay not disappeared");
					}
				}break;
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + parameters.get(0));
						}
					}
				}else if (parameters.get(1).equalsIgnoreCase("PRIMARY CARD")) {
					switch (parameters.get(0).toUpperCase()) {
					case "SCHEDULE OFF STATUS": {
						SchedulingScreen psoff = new SchedulingScreen(testCase);
						flag = flag & psoff.isScheduleOffStatusVisible(4);
								
						if(flag){
							Keyword.ReportStep_Pass(testCase, "Schedule OFF Status displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF status not displayed on primarycard");
						}
					}break;
					case "SCHEDULE OFF STATUS NOT DISPLAYED": {
						SchedulingScreen psoff = new SchedulingScreen(testCase);
						flag = flag & psoff.isScheduleOffStatusVisible(4);		
						if(!flag){
							Keyword.ReportStep_Pass(testCase, "Schedule OFF Status not displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF status displayed on primarycard");
						}
					}break;
					case "FOLLOWING SCHEDULE": {
						SchedulingScreen FollowingS = new SchedulingScreen(testCase);
						flag = flag & FollowingS.isFollowingSchedulesVisible(2);
						if(flag){
							System.out.println("Successfully " + parameters.get(0));
							Keyword.ReportStep_Pass(testCase, "Following Schedule displayed");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Following Scheudle not displayed");
						}
					}break;
					case "SETTINGS": {
						SchedulingScreen UsingS = new SchedulingScreen(testCase);
						if(UsingS.isUsingHomeVisible(10) || UsingS.isUsingSleepVisible(10) || UsingS.isUsingAwayVisible(10)) 
						{
							System.out.println("Using Period " + parameters.get(0));
							Keyword.ReportStep_Pass(testCase, "Geofence period status");
						}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Geofence period status not displayed");
						}
					}break;
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + parameters.get(0));
							}	
				}
				}
			} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"exception occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}