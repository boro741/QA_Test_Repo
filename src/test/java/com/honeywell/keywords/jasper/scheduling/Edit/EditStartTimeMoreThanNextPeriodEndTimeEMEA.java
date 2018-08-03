package com.honeywell.keywords.jasper.scheduling.Edit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.openqa.selenium.By;

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
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SchedulingScreen;

public class EditStartTimeMoreThanNextPeriodEndTimeEMEA extends Keyword {
	
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	String schedulePeriodToSelect = "", temp = "";
	int i = 0;
	ArrayList<String> arrlist = new ArrayList<String>(8);

	public EditStartTimeMoreThanNextPeriodEndTimeEMEA(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins ="^user edits start time value atleast 10 min more than next day period start value$")
	public boolean keywordSteps() throws KeywordException {
		try{
		String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
		String[] schedulePeriods_EMEA = { "1", "2", "3", "4" };
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		Random rn = new Random();
		String changedTime = null ;
		String periodNumber="1" ;
		if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
			schedulePeriodToSelect = periodNumber + "_Everyday";
			String EndTimeOfNextPeriod=testCase.getMobileDriver()
			.findElement(By.xpath("//*[@content-desc='"+(Integer.parseInt(periodNumber)+2) + "_Everyday']//*[contains(@resource-id,'scheduling_period_time')]")).getText();
			testCase.getMobileDriver()
			.findElement(By.xpath("//*[@content-desc='"+schedulePeriodToSelect+"']//*[contains(@resource-id,'scheduling_period_time')]")).click();
			 changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,EndTimeOfNextPeriod.toLowerCase().replaceAll("^0*", ""),
					true, 0, 10);
			
		} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
			schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 0) + 1] + "_"
					+ days[rn.nextInt((6 - 0) + 1) + 0];
			//TODO
		}
		
		 
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String EndTIME="";
		String StartTime="", StarttimetoSet="",StarttimetoSet24Hour="";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser",5)){
				EndTIME = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getText();
				Keyword.ReportStep_Pass(testCase, "Start Time is shown: " +EndTIME);
			} else{
				flag= false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Start Time is not shown");
			}		
		} else {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime",5)){
				EndTIME = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getAttribute("value");
				System.out.println(EndTIME);
				Keyword.ReportStep_Pass(testCase, "Start Time is shown: " +EndTIME);
			} else{
				flag= false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Start Time is not shown");
			}	
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
		{
		
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser",5))
			{
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,changedTime ,
						"TimeChooser", true, true);

	            
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
		}else 
		if (testCase.getPlatform().toUpperCase().contains("IOS"))

	    	{
			 if(MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser",5)){
					StartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getAttribute("value");
					Keyword.ReportStep_Pass(testCase, "Start time is shown: " +StartTime);
				} else{
					flag= false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Start time is not shown");
				}
					//Random rn = new Random();
					int TimetoAdd= 7;
					System.out.println("Time"+TimetoAdd);
					int MinutestoAdd = 50;
					System.out.println("Minute"+MinutestoAdd);

					MinutestoAdd = (MinutestoAdd/10)*10;
					if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA") && (testCase.getPlatform().toUpperCase().contains("IOS"))){
						StarttimetoSet =  JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, StartTime, true, TimetoAdd, MinutestoAdd);
						System.out.println(StarttimetoSet);
					} else {
						StarttimetoSet =  JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, StartTime, true, TimetoAdd, 0);
					}
					if(StarttimetoSet.contains("m") || StarttimetoSet.contains("M")){
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, StarttimetoSet, "TimeChooser", true, true);
					}else{
						try {
							SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
							SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
							StarttimetoSet24Hour = date12Format.format(date24Format.parse(StarttimetoSet));
							flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, StarttimetoSet24Hour, "TimeChooser", true, true);
						} catch (ParseException e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[ParseException] " + e.getMessage());
							flag=false;
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Exception] " + e.getMessage());
							flag=false;
						}
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
							flag=false;
						}
					}


				}
		SchedulingScreen sScreen = new SchedulingScreen(testCase);
		sScreen.isViewByGroupedDaysVisible(20);
		System.out.println(testCase.getMobileDriver()
					.findElement(By.xpath("//*[@content-desc='"+periodNumber+ "_Everyday']//*[contains(@resource-id,'scheduling_period_time')]")).getText());
		System.out.println(changedTime);
		 if(changedTime.equalsIgnoreCase(testCase.getMobileDriver()
					.findElement(By.xpath("//*[@content-desc='"+periodNumber+ "_Everyday']//*[contains(@resource-id,'scheduling_period_time')]")).getText().trim())){
			 Keyword.ReportStep_Pass(testCase, "N period end time is N+1 start time+10 mins");
		 }else{
			 Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "N period end time is  not N+1 start time+10 mins");
				flag=false;
		 }
		}catch(Exception e)
		{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "exception"+e.toString());
			flag=false;
		}
		
		return flag;

	}
	
	

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		
		return flag;
	}

	
}
