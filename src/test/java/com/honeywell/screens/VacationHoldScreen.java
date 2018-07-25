package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class VacationHoldScreen extends MobileScreens {

	private static final String screenName = "VacationHold";
	boolean flag=false;
	
	public VacationHoldScreen(TestCases testCase) {
		super(testCase,screenName);
		
	}
	
	public boolean isStartAndEndDateDisplayed(){
		flag= MobileUtils.isMobElementExists(objectDefinition, testCase,"FromDate");
		flag= MobileUtils.isMobElementExists(objectDefinition, testCase,"ToDate");
		return flag;
	}
	
	public boolean ClickOnStartDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FromDate");
	}
	
	public boolean ClickOnEndDate() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ToDate");
	}
	
	public boolean EnableVacationHold(){
		if(GetVacationHoldStatus().equalsIgnoreCase("On")||GetVacationHoldStatus().equalsIgnoreCase("true")){
			return true;
		}else{
			return ClickOnStatus();
		}
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
	
	public String GetCoolSetPointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CoolSetPointRound");
	}
		
}
