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
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.AdhocScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SchedulingScreen;

public class VerifyScheduleStatusInPrimarycardAndScheduleScreen extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;

	public VerifyScheduleStatusInPrimarycardAndScheduleScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
					flag = flag & OverLay.isScheduleOffOverlayVisible(8);
					if(flag){
						Keyword.ReportStep_Pass(testCase, "Schedule OFF Overlay displayed");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF overlay not displayed");
					}
				}break;
				case "SCHEDULE OFF OVERLAY DISABLED": { 
					SchedulingScreen OverLay = new SchedulingScreen(testCase);
					if(!OverLay.isScheduleOffOverlayVisible(8)){
						Keyword.ReportStep_Pass(testCase, "Schedule OFF Overlay disappear");
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF overlay not disappeared");
					}break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + parameters.get(0));
						}
					}
				}else if (parameters.get(1).equalsIgnoreCase("PRIMARY CARD")) {
					switch (parameters.get(0).toUpperCase()) {
					case "SCHEDULE OFF STATUS": {
						SchedulingScreen psoff = new SchedulingScreen(testCase);
						flag = flag & psoff.isScheduleOffStatusVisible(10);	
						if(flag){
							Keyword.ReportStep_Pass(testCase, "Schedule OFF Status displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF status not displayed on primarycard");
						}break;
					}
					case "SCHEDULE OFF STATUS NOT DISPLAYED": {
						SchedulingScreen psoff = new SchedulingScreen(testCase);	
						if(!psoff.isScheduleOffStatusVisible(4)){
							Keyword.ReportStep_Pass(testCase, "Schedule OFF Status not displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule OFF status displayed on primarycard");
						}
						break;
					}
					case "SCHEDULE STATUS NOT DISPLAYED": {
						SchedulingScreen schedulestatus = new SchedulingScreen(testCase);	
						if(!schedulestatus.isUsingAwayVisible(2) || !schedulestatus.isUsingHomeVisible(2) || !schedulestatus.isUsingSleepVisible(2) || !schedulestatus.isFollowingSchedulesVisible(2)){
							Keyword.ReportStep_Pass(testCase, "Schedule Status not displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule status displayed on primarycard");
						}
						break;
					}
					case "SCHEDULE STATUS TIMEGEOFENCE": {
						SchedulingScreen schedulestatus = new SchedulingScreen(testCase);	
						if(schedulestatus.isUsingAwayVisible(2) || schedulestatus.isUsingHomeVisible(2) || schedulestatus.isUsingSleepVisible(2) 
								|| schedulestatus.isFollowingSchedulesVisible(2)){
							Keyword.ReportStep_Pass(testCase, "Schedule Status displayed on primarycard");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule status not displayed on primarycard");
						}
						break;
					}
					case "FOLLOWING SCHEDULE": {
						SchedulingScreen FollowingS = new SchedulingScreen(testCase);
						flag = flag & FollowingS.isFollowingSchedulesVisible(2);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Following Schedule displayed");
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Following Schedule not displayed");
						break;
					}
					case "NO SCHEDULE": {
						PrimaryCard NoSchedule = new PrimaryCard(testCase);
						flag = flag & NoSchedule.isNoScheduleTextAvailable();
						if(flag)
							Keyword.ReportStep_Pass(testCase, "No Schedule displayed");
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Schedule not displayed");
						break;
					}
					case "FOLLOWING SCHEDULE NOT DISPLAYED": {
						SchedulingScreen FollowingS = new SchedulingScreen(testCase);
						flag = flag & FollowingS.isFollowingSchedulesNotVisible(2);
						if(flag){
							Keyword.ReportStep_Pass(testCase, "Following Schedule not displayed");
						}	
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Following Schedule displayed");
						break;
					}
					case "SYSTEM IS OFF": {
						PrimaryCard SS = new PrimaryCard(testCase);
						flag = flag & SS.isSystemIsOffVisible(5);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "System Is Off status is displayed");
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "System Is Off status is not displayed");
						break;
					}
					case "USING HOME SETTINGS": {
						SchedulingScreen HOME = new SchedulingScreen(testCase);
						flag = flag & HOME.isUsingHomeVisible(10);
						if(flag)
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " dispalyed");
						else
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,  parameters.get(0) + " not displayed");
						break;
					}
					case "USING AWAY SETTINGS": {
						SchedulingScreen Away = new SchedulingScreen(testCase);
						flag = flag & Away.isUsingAwayVisible(10);
						if(flag)
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " dispalyed");
						else
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,  parameters.get(0) + " not displayed");
						break;
					}
					case "USING SLEEP SETTINGS": {
						SchedulingScreen SLEEP = new SchedulingScreen(testCase);
						flag = flag & SLEEP.isUsingSleepVisible(10);
						if(flag)
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " dispalyed");
						else 
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,  parameters.get(0) + " not displayed");
						break;
					}
					case "VACATION STATUS": {
						PrimaryCard VacationStatus = new PrimaryCard(testCase);
						flag = flag & VacationStatus.isVacationStatusVisible(); 
						if(flag)
							Keyword.ReportStep_Pass(testCase, parameters.get(0) + " dispalyed");
							else 
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,  parameters.get(0) + " not displayed");
							break;							
//						flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);		
					}

					case "TEMPORARY": {
						flag = flag & DashboardUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
						flag = flag & JasperAdhocOverride.VerificationofTemporaryHold(testCase, inputs);
						break;
						}
					
					case "PERMANENT": {
					    flag = flag & JasperAdhocOverride.VerificationofPermanentHold(testCase, inputs);
						break;
					}
					case "ADHOCOVERRIDE NOT DISPLAYED": {
						AdhocScreen Adhoc = new AdhocScreen(testCase);
						if(!Adhoc.isAdhocStatusVisible())
							Keyword.ReportStep_Pass(testCase, parameters.get(0) + "on primary card");
							else 
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,  "Adhoc override displayed on primary card");
							break;
					}
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