package com.honeywell.keywords.jasper.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.VacationHoldScreen;

import io.appium.java_client.android.AndroidDriver;

public class DisplayIncrementalVacationTimer extends Keyword {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;
	
	public DisplayIncrementalVacationTimer(TestCases testCase, TestCaseInputs inputs,ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed from and to timer field incremental of (.*) minutes$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		switch(exampleData.get(0)) {
		case "10":{
			if(inputs.getInputValue("DeviceType").equalsIgnoreCase("NA")) {
				try {
					if(vhs.ClickOnStartTime()) {
					String newTime=vhs.IncreaseTimerWithStartOrEndTime(CHILUtil.startTime,"10");
					double hour=new SimpleDateFormat("HH mm ss").parse(newTime).getHours();
					if(String.valueOf(hour).equals(vhs.HourInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The hour in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time hour and expected increment does not match"));
						
					}
					double minute=new SimpleDateFormat("HH mm ss").parse(newTime).getMinutes();
					if(String.valueOf(minute).equals(vhs.MinuteInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The minute in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time minute and expected increment does not match"));
					}
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Failure in clicking Start Time Picker"));
					}
					if(vhs.ClickOnEndTime()) {
					String newEndTime=vhs.IncreaseTimerWithStartOrEndTime(CHILUtil.endTime,"10");
					double endHour=new SimpleDateFormat("HH mm ss").parse(newEndTime).getHours();
					if(String.valueOf(endHour).equals(vhs.HourInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The hour in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time hour and expected increment does not match"));
						
					}
					double endMinute=new SimpleDateFormat("HH mm ss").parse(newEndTime).getMinutes();
					if(String.valueOf(endMinute).equals(vhs.MinuteInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The minute in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time minute and expected increment does not match"));
					}
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Failure in clicking End Time Picker"));
					}
				} catch (ParseException e) {
					
					flag=false;
					Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Error in displaying from and to timer field incremental"));
					
				}
			}
			break;
		}
		
		case "15":{
			if(inputs.getInputValue("DeviceType").equalsIgnoreCase("EMEA")) {
				try {
					if(vhs.ClickOnStartTime()) {
					String newTime=vhs.IncreaseTimerWithStartOrEndTime(CHILUtil.startTime,"15");
					double hour=new SimpleDateFormat("HH mm ss").parse(newTime).getHours();
					if(String.valueOf(hour).equals(vhs.HourInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The hour in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time hour and expected increment does not match"));
						
					}
					double minute=new SimpleDateFormat("HH mm ss").parse(newTime).getMinutes();
					if(String.valueOf(minute).equals(vhs.MinuteInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The minute in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time minute and expected increment does not match"));
					}
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Failure in clicking Start Time Picker"));
					}
					if(vhs.ClickOnEndTime()) {
					String newEndTime=vhs.IncreaseTimerWithStartOrEndTime(CHILUtil.endTime,"15");
					double endHour=new SimpleDateFormat("HH mm ss").parse(newEndTime).getHours();
					if(String.valueOf(endHour).equals(vhs.HourInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The hour in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time hour and expected increment does not match"));
						
					}
					double endMinute=new SimpleDateFormat("HH mm ss").parse(newEndTime).getMinutes();
					if(String.valueOf(endMinute).equals(vhs.MinuteInTimePicker())) {
						Keyword.ReportStep_Pass(testCase, String.format("The minute in start date time picker is as expected after incrementation"));
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("The start time minute and expected increment does not match"));
					}
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Failure in clicking End Time Picker"));
					}
				} catch (ParseException e) {
					
					flag=false;
					Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Error in displaying from and to timer field incremental"));
					
				}
			}
			break;
		}
		}
		
	   return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

