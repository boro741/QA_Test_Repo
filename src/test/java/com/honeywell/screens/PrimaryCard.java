package com.honeywell.screens;

import java.util.HashMap;

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

	public boolean isBackButtonVisible() {
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
	public boolean isThermostatCurrentHumidityValueVisible(TestCaseInputs inputs, int timeOut) {
        boolean flag = true;
        String humidityPercentageInPrimaryCardScreen = null;
        if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidityValueInPrimaryCardScreen", 30)) {
                        humidityPercentageInPrimaryCardScreen = MobileUtils
                                                        .getMobElement(objectDefinition, testCase, "HumidityValueInPrimaryCardScreen").getText();
                        humidityPercentageInPrimaryCardScreen = humidityPercentageInPrimaryCardScreen.split(" ")[1].split("%")[0];
                        if (!humidityPercentageInPrimaryCardScreen.equalsIgnoreCase("TO")) {
                                        inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_HUMIDITY_VALUE",
                                                                        humidityPercentageInPrimaryCardScreen);
                        }
        } else if (MobileUtils.isMobElementExists(objectDefinition, testCase,
                                        "HumidityValueFromTheScrollListInPrimaryCardScreen", 30)) {
                        humidityPercentageInPrimaryCardScreen = MobileUtils
                                                        .getMobElement(objectDefinition, testCase, "HumidityValueFromTheScrollListInPrimaryCardScreen")
                                                        .getText();
                        humidityPercentageInPrimaryCardScreen = humidityPercentageInPrimaryCardScreen.split(" ")[1].split("%")[0];
                        if (!humidityPercentageInPrimaryCardScreen.equalsIgnoreCase("TO")) {
                                        inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_HUMIDITY_VALUE",
                                                                        humidityPercentageInPrimaryCardScreen);
                        }

        }
        return flag;
}

	

	public boolean isChangeFanScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle");
	}

	public boolean clickOnInfoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "InfoButton");
	}

	public boolean isThermostatSolutionCardDisplayed() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleButton")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFanModeElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Fanmode");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean clickOnModeButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModeButton");
	}

	public boolean clickOnFanButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FanButton");
	}

	public boolean isChangeModeScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
	}

	public boolean isModeInfoScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
	}

	public boolean isFanInfoScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle");
	}

	public boolean isAutoModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeDefinition", 5);
	}

	public boolean isHeatModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeDefinition", 5);
	}

	public boolean isCoolModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeDefinition", 5);
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
	
	public boolean isAutoModeVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoMode");
	}

	public boolean isSetModeDisplayed(TestCaseInputs inputs) {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String mode = statInfo.getThermoStatMode();
		if (mode.equals("Cool")) {

			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Heat")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Off")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Auto")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "AutoMode");
			if (element.isSelected()) {
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
		String mode = inputs.getInputValue("SystemMode");

		if (mode.equals("COOL")) {

			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("HEAT")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("OFF")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("AUTO")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Auto");
			if (element.isSelected()) {
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

	public boolean clickOnAutoFanButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoFanButton");
	}

	public boolean isSystemOffModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemOffModeDefinition", 5);
	}

	public boolean isAutoFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinition", 5);
	}

	public boolean isCirculateFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinition", 5);
	}

	public boolean isOnFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinition", 5);
	}

	public boolean isModeElementEnabled() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton");
		if (element.isEnabled()) {
			return flag;
		} else {
			return false;
		}
	}

	public boolean isAutoFanDefinitionVisibleOnInfoScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinitionOnInfoScreen", 5);
	}

	public boolean isCirculateFanDefinitionVisibleOnInfoScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinitionOnInfoScreen", 5);
	}

	public boolean isOnFanDefinitionVisibleOnInfoScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinitionOnInfoScreen", 5);
	}

	public boolean isScheduleElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ScheduleButton");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isOffStatusVisibleOnSolutionCard() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "ModeStatus");
		if (status.toUpperCase().contains("OFF")) {
			return true;
		} else {
			return false;
		}
	}
		public boolean isVacationStatusVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationStatus");
		
		}
		public boolean clickOnVacationStatus() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationStatus");
		
		}
		


	public boolean clickOnXButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "XButton");
	}


	public boolean isAutoFanElementSelected() {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "AutoFanButton");
		if (ele.isSelected()) {
			return true;

		}
		return false;
	}

	public boolean clickOnCirculateFanButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CirculateFanButton");
	}

	public boolean clickOnONFanButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OnFanButton");
	}

	public boolean isCirculateFanElementSelected() {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "CirculateFanButton");
		if (ele.isSelected()) {
			return true;
		}
		return false;
	}

	public boolean isONFanElementSelected() {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "OnFanButton");
		if (ele.isSelected()) {
			return true;
		}
		return false;
	}

	public boolean isAutoFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinitionOnChangeFanScreen", 5);
	}

	public boolean isCirculateFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinitionOnChangeFanScreen", 5);
	}

	public boolean isOnFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinitionOnChangeFanScreen", 5);

	}

	public boolean checkCurrentFanMode(String expectedValue) {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "FanButton");
		String value = ele.getAttribute("name");
		if (expectedValue.contains(value.toUpperCase())) {
			return true;
		} else {
			return false;
		}

		}

		public void clickOnEmergencyHeatButton() {
			WebElement element1= MobileUtils.getMobElement(objectDefinition, testCase, "EmergencyHeatToggle");
			if(element1.getText().equalsIgnoreCase("off"))
					{
				MobileUtils.clickOnElement(objectDefinition, testCase, "EmergencyHeatToggle");
					}
			else
			{
				MobileUtils.clickOnElement(objectDefinition, testCase, "EmergencyHeatToggle");
			}
			
		}	


	public boolean checkCurrentMode(String expectedValue) {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton");
		String value = element.getAttribute("name");
		if (expectedValue.contains(value.toUpperCase())) {
			return true;
		} else {
			return false;
       }	
	}

		public boolean setMaxTemperatureByTappingUpStepper(TestCaseInputs inputs) {
			boolean flag=true;
			String maxSetPoint="";
			float maxSetPointFloat=0;
			int maxSetPointInt=0;
			boolean  systemIsCelsius=false;
			String currentSetPoint="";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String currentSystemMode=statInfo.getThermoStatMode();
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints=statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentSystemMode.toUpperCase().contains("HEAT")) {
				 maxSetPoint= setPoints.get("MaxHeat");
			}
			else if(currentSystemMode.toUpperCase().contains("COOL")){
				 maxSetPoint= setPoints.get("MaxCool");
			}
			 if(maxSetPoint.contains(".")) {
	        	   systemIsCelsius=true;
				maxSetPointFloat=Float.parseFloat(maxSetPoint);
	           }
	           else {
	   			maxSetPointInt=(int)Float.parseFloat(maxSetPoint);	   
	           }
			
			WebElement ele=MobileUtils.getMobElement(objectDefinition, testCase,"CurrentSetPoint");
			currentSetPoint = ele.getText();
		if(systemIsCelsius==false) {	
			while(Integer.parseInt(currentSetPoint)<maxSetPointInt) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperUp");
				currentSetPoint = ele.getText();
			}
		}
		else {
			while(Float.parseFloat(currentSetPoint)<maxSetPointFloat) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperUp");
				currentSetPoint = ele.getText();
			}
		}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return flag;
		}
	

	public boolean clickOnSaveButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
	}



		public boolean isMaxTemperatureVisibleOnSolutionCard(TestCaseInputs inputs) {
			String currentSetPoint="";
			int maxSetPointInt=0;
			float maxSetPointFloat=0;
			String maxSetPoint="";
			boolean  systemIsCelsius=false;
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String currentSystemMode=statInfo.getThermoStatMode();
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints=statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentSystemMode.toUpperCase().contains("HEAT")) {
				 maxSetPoint= setPoints.get("MaxHeat");
			}
			else if(currentSystemMode.toUpperCase().contains("COOL")){
				 maxSetPoint= setPoints.get("MaxCool");
			}
           if(maxSetPoint.contains(".")) {
        	   systemIsCelsius=true;
			maxSetPointFloat=Float.parseFloat(maxSetPoint);
           }
           else {
   			maxSetPointInt=(int)Float.parseFloat(maxSetPoint);	   
           }
			WebElement ele=MobileUtils.getMobElement(objectDefinition, testCase,"CurrentSetPoint");
			currentSetPoint = ele.getText();
			if(systemIsCelsius==false) {
			if(maxSetPointInt==(Integer.parseInt(currentSetPoint))) {
				return true;
			}
			}
			else {
				if(maxSetPointFloat==(Float.parseFloat(currentSetPoint))) {
					return true;
				}
			}
			return false;
		
	
	}


		public boolean setMinTemperatureByTappingDownStepper(TestCaseInputs inputs) {
			boolean flag=true;
			String minSetPoint="";
			int minSetPointInt=0;	
			float minSetPointFloat=0;
			boolean  systemIsCelsius=false;
			String currentSetPoint="";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String currentSystemMode=statInfo.getThermoStatMode();
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints=statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentSystemMode.toUpperCase().contains("HEAT")) {
				 minSetPoint= setPoints.get("MinHeat");
			}
			else if(currentSystemMode.toUpperCase().contains("COOL")){
				 minSetPoint= setPoints.get("MinCool");
			} 
			if(minSetPoint.contains(".")) {
	        	   systemIsCelsius=true;
	   			minSetPointFloat=Float.parseFloat(minSetPoint);
	              }
	              else {
	      			minSetPointInt=(int)Float.parseFloat(minSetPoint);	   
	              }
			
			WebElement ele1=MobileUtils.getMobElement(objectDefinition, testCase,"CurrentSetPoint");
			currentSetPoint = ele1.getText();
			if(systemIsCelsius==false) {
			while(Integer.parseInt(currentSetPoint)>minSetPointInt) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperDown");
				currentSetPoint = ele1.getText();
			}
			}
			else {
				while(Float.parseFloat(currentSetPoint)>minSetPointFloat) {
					flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperDown");
					currentSetPoint = ele1.getText();
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return flag;
		} 


		public boolean isMinTemperatureVisibleOnSolutionCard(TestCaseInputs inputs) {
			String currentSetPoint="";
			int minSetPointInt=0;
			String minSetPoint="";
			boolean systemIsCelsius=false;
			float minSetPointFloat=0;
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String currentSystemMode=statInfo.getThermoStatMode();
			HashMap<String, String> setPoints = new HashMap<String, String>();
			try {
				setPoints=statInfo.getDeviceMaxMinSetPoints();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(currentSystemMode.toUpperCase().contains("HEAT")) {
				 minSetPoint= setPoints.get("MinHeat");
			}
			else if(currentSystemMode.toUpperCase().contains("COOL")){
				 minSetPoint= setPoints.get("MinCool");
			}
			  if(minSetPoint.contains(".")) {
	        	   systemIsCelsius=true;
				minSetPointFloat=Float.parseFloat(minSetPoint);
	           }
	           else {
	   			minSetPointInt=(int)Float.parseFloat(minSetPoint);	   
	           }
			WebElement ele=MobileUtils.getMobElement(objectDefinition, testCase,"CurrentSetPoint");
			currentSetPoint = ele.getText();
			if(systemIsCelsius==false) {
			if(minSetPointInt==(Integer.parseInt(currentSetPoint))) {
				return true;
			}
			}
			else {
				if(minSetPointFloat==(Float.parseFloat(currentSetPoint))) {
					return true;
				}
			}
			return false;
		}
		public boolean autoModeButtonVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoMode");
		}
		public boolean disableAutoChangeOver(TestCaseInputs inputs) {
			WebElement ele=MobileUtils.getMobElement(objectDefinition, testCase, "AutoChangeOverToggle");
			String status=ele.getText();
			if(status.equals("OFF")) {
				return true;
			}
			else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoChangeOverToggle");
			}
		}

		public boolean enableAutoChangeOver(TestCaseInputs inputs) {
			WebElement ele=MobileUtils.getMobElement(objectDefinition, testCase, "AutoChangeOverToggle");
			String status=ele.getText();
			if(status.equals("ON")) {
				return true;
			}
			else {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoChangeOverToggle");
			}
		}		
	  






	public boolean isFanButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanButton", 8);
	}

	public boolean isEmergencyHeatOptionDisabled() {
		WebElement element1= MobileUtils.getMobElement(objectDefinition, testCase, "EmergencyHeatToggle");
		if(element1.getText().equalsIgnoreCase("off"))
				{
			return true;
				}
		else
		{
			return false;
		}
	}
}
