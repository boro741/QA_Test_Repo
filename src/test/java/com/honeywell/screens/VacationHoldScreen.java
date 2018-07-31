package com.honeywell.screens;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class VacationHoldScreen extends MobileScreens {

	private static final String screenName = "VacationHold";
	boolean flag=false;
	
	public VacationHoldScreen(TestCases testCase) {
		super(testCase,screenName);
		
	}
	
	public boolean isStartAndEndDateDisplayed(){
		flag&= MobileUtils.isMobElementExists(objectDefinition, testCase,"FromDate");
		flag&= MobileUtils.isMobElementExists(objectDefinition, testCase,"ToDate");
		return flag;
	}
	
	public boolean IsVacationLabelPresentOnSolutionCard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationOnPrimaryCard");
	}
	
	public boolean IsSystemIsOffOnSolutionCard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatOff");
	}
	
	public boolean IsAdhocOverrideShown() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AdhocOnPrimaryCard");
	}
	
	public boolean IsVacationEndCautionMessageShown() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationEndCautionMessage");
	}
	public boolean isStartAndEndTimeDisplayed(){
		flag&= MobileUtils.isMobElementExists(objectDefinition, testCase,"FromTime");
		flag&= MobileUtils.isMobElementExists(objectDefinition, testCase,"ToTime");
		return flag;
	}
	
	public boolean ClickOnModeText() {
		flag&=MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatMode");
		flag&=MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatMode");
		return flag;
	}
	
	public boolean ClickOnSystemModes(String modeElementName) {
		flag&= MobileUtils.clickOnElement(objectDefinition, testCase,modeElementName);
		flag&=MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButtonInMode");
		return flag;
	}
	
	public String HourInTimePicker() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerHour");
	}
	
	public String MinuteInTimePicker() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TimePickerMinute");
	}
	
	@SuppressWarnings("deprecation")
	public String IncreaseTimerWithStartOrEndTime(String time,String incrementalTime) throws ParseException {
		
		int newHour;
		int newMinute;
		int additionalMinute;
		 String newTime="";
		try {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date d = df.parse(time); 
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 additionalMinute=Integer.parseInt(incrementalTime);
		 cal.add(Calendar.MINUTE,additionalMinute);
		 newTime = df.format(cal.getTime());
		 newHour=new SimpleDateFormat("HH mm ss").parse(newTime).getHours();
	     newMinute = new SimpleDateFormat("HH mm ss").parse(newTime).getMinutes();
		 testCase.getMobileDriver().findElementByAccessibilityId(String.valueOf(newHour)).click();
		 testCase.getMobileDriver().findElementByAccessibilityId(String.valueOf(newMinute)).click();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			Keyword.ReportStep_Fail(testCase,FailType.COSMETIC_FAILURE, String.format("Error in setting value to time"));
		}
		return newTime;
		
		
	}
	
	public boolean isVacationHoldHeaderPresentUnderLocationOnDashBoard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationOnLocationHeader");
	}
	
	public String GetTextOfVacationOnTheDashboard() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "VacationOnLocationHeader");
	}
	
	
	public boolean ClickOnStartDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromDate");
	}
	
	public boolean ClickOnEndDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndDate");
	}
	
	public boolean ClickOnStartTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromTime");
	}
	
	public boolean ClickOnEndTime() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EndTime");
	}
	
	public boolean EnableVacationHold(){
		if(GetVacationHoldStatus().equalsIgnoreCase("On")||GetVacationHoldStatus().equalsIgnoreCase("true")){
			return true;
		}else{
			return ClickOnStatus();
		}
	}
	
	public boolean EndVacationButtonInSolutionCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationEndInSolutionCard");
	}
	
	public boolean DisableVacationHold(){
		if(GetVacationHoldStatus().equalsIgnoreCase("Off")||GetVacationHoldStatus().equalsIgnoreCase("false")){
			return true;
		}else{
			return ClickOnStatus();
		}
	}
	
	public boolean ClickOnEndVacationButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationEndButton");
	}
	
	public String GetVacationHoldStatus(){
		return MobileUtils.getFieldValue(objectDefinition, testCase, "VacationHoldSwitch");
	}
	
	public boolean ClickOnStatus(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSwitch");
	}
	
	public boolean isCalendarPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MonthCalendarOption");
	}

	public boolean ClickOnVacationHoldSetpointSettings() {
	
	    if(MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationHoldSetpointRow")) {
	    	return MobileUtils.clickOnElement(objectDefinition, testCase, "VacationHoldSetpointRow");
	    }
	    else
	    {
	    	return false;
	    }
	}
	
	public String GetHeatSetPointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HeatSetPointRound");
	}
	
	public boolean EditHeatSetPointUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatSetPointRoundChooser");
	}
	
	public boolean EditCoolSetPointUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolSetPointRoundChooser");
	}
	
	public boolean EditSetPointUpInPrimaryCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrimaryCardSetPointUp");
	}
	
	public boolean ClickOnCancelButtonInEndVacation() {
		flag&=MobileUtils.isMobElementExists(objectDefinition, testCase, "ClickOnCancelButtonInEndVacation");
	   flag&= MobileUtils.clickOnElement(objectDefinition, testCase, "VacationEndCancelButton");
	   return flag;
	}
	
	public String GetPrimaryCardValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "PrimaryCardSetpoint");
	}
	
	public boolean EditSetPointDownInPrimaryCard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PrimaryCardSetPointDown");
	}
	
	public String GetCoolSetPointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CoolSetPointRound");
	}
		
}
