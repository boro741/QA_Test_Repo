
package com.honeywell.screens;


import org.openqa.selenium.WebElement;


import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.TestCaseInputs;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

import io.appium.java_client.MobileElement;

public class PrimaryCard extends MobileScreens {

	private static final String screenName = "PrimaryCard";

	public PrimaryCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCogIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CogIcon", 3);
	}

	public boolean clickOnCogIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CogIcon");
	}

	public boolean isclickOnBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}



	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}

	
	public boolean isThermostatCurrentTemperatureVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}
	
	public String getThermostatCurrentTemperatureValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}
 


	
		public boolean isChangeFanScreenDisplayed() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle");
		}
		public boolean clickOnInfoButton() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "InfoButton");
		}

		public boolean isModeInfoScreenDisplayed() {

			return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle") ;
			
		}
		public boolean isFanInfoScreenDisplayed() {

			return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle") ;
			
		}
		public boolean isAutoModeDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeDefinition",5);
		}

		public boolean isHeatModeDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeDefinition",5);
		}

		public boolean isCoolModeDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeDefinition",5);
		}

		public boolean isSystemOffModeDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemOffModeDefinition",5);
		}
		public boolean isAutoFanDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinition",5);
		}
		public boolean isCirculateFanDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinition",5);
		}
		public boolean isOnFanDefinitionVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinition",5);
		}
	

	public boolean isThermostatSolutionCardDisplayed(TestCaseInputs inputs) {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton",5)&&
				MobileUtils.isMobElementExists(objectDefinition, testCase, "FanButton",5)&&
						MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleButton",5)){
			WebElement element =MobileUtils.getMobElement(objectDefinition, testCase,"UserExpectedTemperature");
			String temp=element.getText();
			System.out.println(temp);
			inputs.setInputValue("temperature",temp);
					return true;
				}
		else {
			return false;
		}
	   }
		public boolean isFanModeElementEnabled() {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Fanmode");
			if(element.isEnabled())
			{
				return true;
			}
			return false;
		}
		public boolean isModeElementEnabled() {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton");
			if(element.isEnabled())
			{
				return true;
			}
			return false;
		}

		public boolean isScheduleElementEnabled() {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ScheduleButton");
			if(element.isEnabled())
			{
				return true;
			}
			return false;
		}

		public boolean isOffStatusVisibleOnSolutionCard() {
			String status = MobileUtils.getFieldValue(objectDefinition, testCase, "ModeStatus");
			if(status.toUpperCase().contains("OFF"))
			{
			return true;
		}
			return false;
		}

		public boolean isHeatModeVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatMode");
		}

		public boolean isCoolModeVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolMode");
		}

		public boolean isOffModeVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "OffMode");
		}
     public boolean clickOnModeButton() {
			
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ModeButton");
		}
     public boolean isChangeModeScreenDisplayed() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
		}

	public boolean isSetModeDisplayed(TestCaseInputs inputs) {
		DeviceInformation statInfo = new DeviceInformation(testCase,inputs);
		String mode=statInfo.getThermoStatMode();
		if(mode.equals("Cool"))
		{
			
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("Heat"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("Off"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("Auto"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "AutoMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		return false;
	}

	public Boolean clickOnHeatButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HeatMode");
		
	}

	public Boolean clickOnCoolButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CoolMode");
		
	}

	public Boolean clickOnOffButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OffMode");
		
	}

	public Boolean clickOnAutoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoMode");
		
		
	}

	public boolean isNewSetModeDisplayed(TestCaseInputs inputs) {
		String mode=inputs.getInputValue("SystemMode");
		System.out.println(mode);
		if(mode.equals("COOL"))
		{
			
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("HEAT"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("OFF"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		if(mode.equals("AUTOMODE"))
		{
			MobileElement element=MobileUtils.getMobElement(objectDefinition, testCase, "AutoMode");
		     if(element.isSelected())
			{
				return true;
			}
		}
		return false;
	}

	public boolean isAutoDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeDescription");
	}

	public boolean isHeatDefinitionVisibleOnChangeModeScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeDescription");
	}

	public boolean isOffDefinitionVisibleOnChangeModeScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OffModeDescription");
	}

	public boolean isCoolDefinitionVisibleOnChangeModeScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeDescription");
	}

	public Boolean clickOnXButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "XButton");	
	}
	public boolean checkCurrentMode(String expectedValue) {

		WebElement element=MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton");
		String value=element.getAttribute("name");
		System.out.println(value);
		if(expectedValue.contains(value.toUpperCase())) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isThermostatTemperatureNotChangedinPrimaryCard(TestCaseInputs inputs) {
		WebElement element =MobileUtils.getMobElement(objectDefinition, testCase,"UserExpectedTemperature");
		String temp=element.getText();
		System.out.println(temp);
		String temp2=inputs.getInputValue("temperature");
		if(temp.equals(temp2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isThermostatTemperatureNotChangedinDashboardCard(TestCaseInputs inputs) {
		WebElement element =MobileUtils.getMobElement(objectDefinition, testCase,"UserExpectedTemperature");
		String temp=element.getText();
		System.out.println(temp);
		String temp2=inputs.getInputValue("temperature");
		if(temp.equals(temp2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean clickOnSaveButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
	}

	public boolean isThermostatTemperatureChangedinPrimaryCard(TestCaseInputs inputs) {
		WebElement element =MobileUtils.getMobElement(objectDefinition, testCase,"UserExpectedTemperature");
		String temp=element.getText();
		System.out.println(temp);
		String temp2=inputs.getInputValue("temperature");
		if(temp.equals(temp2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isThermostatTemperatureChangedinDashboardCard(TestCaseInputs inputs) {
		WebElement element =MobileUtils.getMobElement(objectDefinition, testCase,"UserExpectedTemperature");
		String temp=element.getText();
		System.out.println(temp);
		String temp2=inputs.getInputValue("temperature");
		if(temp.equals(temp2))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		

}

