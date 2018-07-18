
package com.honeywell.screens;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

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
 


	public boolean isThermostatSolutionCardDisplayed() {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton",5)&&
				MobileUtils.isMobElementExists(objectDefinition, testCase, "FanButton",5)&&
						MobileUtils.isMobElementExists(objectDefinition, testCase, "ScheduleButton",5)){
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
		public boolean clickOnModeButton() {
			
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ModeButton");
		}
		public boolean isChangeModeScreenDisplayed() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
		}
		public boolean clickOnInfoButton() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "InfoButton");
		}

		public boolean isModeInfoScreenDisplayed() {

			return MobileUtils.clickOnElement(objectDefinition, testCase, "InformationTitle") && MobileUtils.clickOnElement(objectDefinition, testCase, "ChangeModeTitle") ;
			
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


	
}

