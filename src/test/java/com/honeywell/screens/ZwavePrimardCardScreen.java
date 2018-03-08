package com.honeywell.screens;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class ZwavePrimardCardScreen extends MobileScreens{

		private static final String screenName = "ZwavePrimaryScreen";		
		//private OSPopUps osPopUps;
		
		public ZwavePrimardCardScreen(TestCases testCase) {
			super(testCase,screenName);
			//osPopUps = new OSPopUps(testCase);
		}
		
		public boolean verifyPresenceOfSwitchStatus(){
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchPrimaryCardStatus");
		}
		
		public boolean isSwitchingToOverlayDisplayed(){
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchingToOverlay");
		}
		
		public String getSwitchStatus(){
			return MobileUtils.getFieldValue(objectDefinition, testCase, "SwitchPrimaryCardStatus");
		}
		
		public boolean clickOnStatus(){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchPrimaryCardOn");
		}
		
		public boolean clickOffStatus(){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchPrimaryCardOff");
		}
		
		public boolean switchOn(){
			if(getSwitchStatus().equalsIgnoreCase("On")){
				return true;
			}else{
				return clickOnStatus();
			}
		}
		
		public boolean switchOff(){
			if(getSwitchStatus().equalsIgnoreCase("Off")){
				return true;
			}else{
				return clickOffStatus();
			}
		}

		public boolean verifyPresenceOfDimmerStatus() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "DimmerPrimaryCardStatus");
		}

		public String getDimmerStatus() {
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "DimmerPrimaryCardStatus").contains("%")){
				return MobileUtils.getFieldValue(objectDefinition, testCase, "DimmerPrimaryCardStatus").replaceAll("%", "");
			}else 
			return MobileUtils.getFieldValue(objectDefinition, testCase, "DimmerPrimaryCardStatus");
		}

		public WebElement getDimmerSeekBar() {
			return MobileUtils.getMobElement(objectDefinition, testCase, "DimmerIntensitySeekbar");
		}
		public boolean isDimmerSeekBarVisible() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "DimmerIntensitySeekbar");
		}
}
