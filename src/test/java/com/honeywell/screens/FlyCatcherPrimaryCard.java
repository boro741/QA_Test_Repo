package com.honeywell.screens;


import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class FlyCatcherPrimaryCard extends MobileScreens {

	private static final String screenName = "FlyCatcherPrimaryCard";


	public FlyCatcherPrimaryCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public String getSystemMode()
	{
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "changedModeText").getText().toString();

		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "changedModeText").getText().toString();

		}
	}
	public boolean IsHeatModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeOption",timeOut);
	}

	public boolean IsCoolModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeOption",timeOut);
	}

	public boolean IsOffModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OffModeOption",timeOut);
	}
	public boolean IsAuotModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeOption",timeOut);
	}


	public Boolean changeSystemModeToHeatMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='HEAT']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToCoolMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='COOL']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToOffMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OffModeOption");
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='OFF']")).click();
		}		
		return blnFlag;

	}
	public Boolean changeSystemModeToAutoMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag &MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemModeButton",20);
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (IsAuotModeButtonVisible(5)){
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoModeOption");
			}else{
				testCase.getMobileDriver().findElement(By.xpath("//*[@value='AUTO']")).click();
			}
		}
		return blnFlag;
	}

	public boolean clickOnSetPointSteppeDownButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SetPointStepper_Down");
	}

	public boolean ClickOnSetPointStepperUPButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SetPointStepper_UP");
	}

	public boolean IsDailerButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Dialer",timeOut);
	}

	public String getDailerValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SystemModeButton").getTagName();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "SystemModeButton").getAttribute("value");			
		}
	}

	public boolean ClickCloseButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelOption");
	}
	public boolean IsCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelOption",timeOut);
	}

	public boolean ClickOnMoreButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MoreButton");
	}

	public boolean isMoreButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MoreButton",3);
	}

	public boolean isVentilationIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VentilationButton",3);
	}

	public boolean ClickOnVentilationButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationButton");
	}

	public boolean changeVentilationModeToOff()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationOffActiveButton");
	}

	public boolean changeVentilationModeToOn()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationOnActiveButton");
	}

	public boolean changeVentilationModeToAuto()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationAutoActiveButton");
	}

	public String getVentialtionMode(){
		return MobileUtils.getMobElement(objectDefinition, testCase, "VentilationButton").getTagName();
	}

	public boolean VentilationTimerOnModes(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RunVentialtionButton");
	}

	public boolean ClickVentilationTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RunVentialtionButton");
	}

	public boolean ClickEditVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationEditTimer");
	}

	public boolean ClickStartVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationTimerStart");
	}

	public boolean ClickCancelVentTimer(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VentilationTimerCancel");
	}

	public int getTimerPickerValue(){
		String timervalueString = MobileUtils.getFieldValue(objectDefinition, testCase, "VentilationTimerPicker");
		int timervalue = Integer.parseInt(timervalueString);
		return timervalue;
	}

	public int[] getPickercordinates(){
		Point pointValue1 = MobileUtils.getMobElement(objectDefinition, testCase, "VentilationTimerPicker").getLocation();
		int startx = pointValue1.getX();
		int starty = pointValue1.getY();
		return new  int[] {startx,starty};

	}

	public int getVentilationTimeValue(){
		int TimerValue = 0;
		String ele = MobileUtils.getFieldValue(objectDefinition, testCase, "VentilationTimerValue");
		TimerValue = Integer.parseInt(ele);
		return TimerValue;
	}
	
	public boolean isVentilationTimerText()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VentilationTimerValue",3);
	}


}
