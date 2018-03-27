package com.honeywell.screens;

import org.openqa.selenium.By;

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
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.xpath("//*[@text='HEAT']")).click();
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='HEAT']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToCoolMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.xpath("//*[@text='COOL']")).click();
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='COOL']")).click();
		}
		return blnFlag;
	}

	public Boolean changeSystemModeToOffMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			testCase.getMobileDriver().findElement(By.xpath("//*[@text='OFF']")).click();
		}else{
			testCase.getMobileDriver().findElement(By.xpath("//*[@value='OFF']")).click();
		}		
		return blnFlag;

	}
	public Boolean changeSystemModeToAutoMode()
	{
		boolean blnFlag = true;
		blnFlag = blnFlag & MobileUtils.clickOnElement(objectDefinition, testCase, "SystemModeButton");
		if (IsAuotModeButtonVisible(5)){
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@text='AUTO']")).click();
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
			return MobileUtils.getMobElement(objectDefinition, testCase, "Dialer")
					.findElement(By.id("Dialer")).getText();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "Dialer");
		}
	}

	public boolean ClickCloseButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelOption");
	}
	public boolean IsCloseButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelOption",timeOut);
	}


}
