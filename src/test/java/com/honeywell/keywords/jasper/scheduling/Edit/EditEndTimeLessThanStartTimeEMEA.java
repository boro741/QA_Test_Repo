package com.honeywell.keywords.jasper.scheduling.Edit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.openqa.selenium.By;

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
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

public class EditEndTimeLessThanStartTimeEMEA extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	String schedulePeriodToSelect = "", temp = "";
	int i = 0;
	ArrayList<String> arrlist = new ArrayList<String>(8);

	public EditEndTimeLessThanStartTimeEMEA(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins ="^user edits end time value atleast 10 min less than next day period start value$")
	public boolean keywordSteps() throws KeywordException {
		//String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
		String[] schedulePeriods_EMEA = { "1", "2", "3", "4" };
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		Random rn = new Random();
		String changedTime = null ;
		String periodNumber=schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1] ;
		if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
			if(!testCase.getPlatform().contains("IOS")){
				schedulePeriodToSelect = periodNumber + "_Everyday";
				String startTimeOfNextPeriod=testCase.getMobileDriver()
						.findElement(By.xpath("//*[@content-desc='"+(Integer.parseInt(periodNumber)+1) + "_Everyday']//*[contains(@resource-id,'scheduling_period_time')]")).getText();
				testCase.getMobileDriver()
				.findElement(By.xpath("//*[@content-desc='"+schedulePeriodToSelect+"']//*[contains(@resource-id,'scheduling_period_time')]")).click();
				changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,startTimeOfNextPeriod.toLowerCase().replaceAll("^0*", ""),
						true, 0, 10);
			}else{
				schedulePeriodToSelect = "Everyday_"+periodNumber + "_cell";
				String startTimeOfNextPeriod=testCase.getMobileDriver()
						.findElement(By.xpath("//*[@name='Everyday_"+(Integer.parseInt(periodNumber)+1) + "_Time']")).getText();
				testCase.getMobileDriver()
				.findElement(By.xpath("//*[@name='"+schedulePeriodToSelect+"']")).click();
				changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,startTimeOfNextPeriod.toLowerCase().replaceAll("^0*", ""),
						true, 0, 10);
			}

		} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
			schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1] + "_"
					+ days[rn.nextInt((6 - 0) + 1) + 0];
			//TODO
		}


		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		String EndTIME="";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime",5)){
				EndTIME = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getText();
				Keyword.ReportStep_Pass(testCase, "End Time is shown: " +EndTIME);
			} else{
				flag= false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"End Time is not shown");
			}		
		} else {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime",5)){
				EndTIME = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getAttribute("value");
				System.out.println(EndTIME);
				Keyword.ReportStep_Pass(testCase, "End Time is shown: " +EndTIME);
			} else{
				flag= false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"End Time is not shown");
			}	
		}


		if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime",5))
		{
			flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,changedTime ,
					"TimeChooserEndTime", true, true);


		}
		if(MobileUtils.isMobElementExists(fieldObjects, testCase, "SaveButton", 5))
		{
			try
			{
				if(MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton"))
				{	
					Keyword.ReportStep_Pass(testCase, "Clicked on save button");
				}
				else
				{	
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Did not click on save button");
					flag=false;
				}

			}
			catch(Exception e)
			{
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "save button does not exist");
			}
		}

		SchedulingScreen scheduleScreen= new SchedulingScreen(testCase);
		if(scheduleScreen.isViewByGroupedDaysVisible(25)){
			Keyword.ReportStep_Pass(testCase, "Schedule screen displayed");
		}else{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not in Schedule screen");
			flag=false;
		}
		
		if(testCase.getPlatform().contains("IOS")){
			if(changedTime.equalsIgnoreCase(testCase.getMobileDriver()
					.findElement(By.xpath("//*[@name='Everyday_"+(Integer.parseInt(periodNumber)+1) + "_Time']")).getText())){
				Keyword.ReportStep_Pass(testCase, "N period end time is N+1 start time+10 mins");
			}else{
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "N period end time is  not N+1 start time+10 mins");
				flag=false;
			}
		}else{
			String displayedTime=testCase.getMobileDriver()
					.findElement(By.xpath("//*[@content-desc='"+(Integer.parseInt(periodNumber)+1) + "_Everyday']//*[contains(@resource-id,'scheduling_period_time')]")).getText();
			char zeroPrefixHourOfDisplayedTime=displayedTime.charAt(0);
			if(zeroPrefixHourOfDisplayedTime=='0'){
				displayedTime=displayedTime.replaceFirst("0", "");
			}
			char zeroPrefixHourOfExpectedTime=changedTime.charAt(0);
			if(zeroPrefixHourOfExpectedTime=='0'){
				changedTime=changedTime.replaceFirst("0", "");
			}
			if(changedTime.trim().equalsIgnoreCase(displayedTime.trim())){
				Keyword.ReportStep_Pass(testCase, changedTime+ " N period end time is N+1 start time+10 mins "+displayedTime);
			}else{
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, displayedTime+" N period end time is  not N+1 start time+10 mins "+changedTime);
				flag=false;
			}
		}

		return flag;

	}



	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {

		return flag;
	}


}
