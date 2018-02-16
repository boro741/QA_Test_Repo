package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;

public class ZwaveScreen extends MobileScreens{

		private static final String screenName = "ZwaveScreen";		
		//private OSPopUps osPopUps;
		
		public ZwaveScreen(TestCases testCase) {
			super(testCase,screenName);
			//osPopUps = new OSPopUps(testCase);
		}
		
		public boolean verifyPresenceOfSwitchStatus(){
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchStatusToggle");
		}
		
		public String getSwitchStatus(){
			return MobileUtils.getFieldValue(objectDefinition, testCase, "SwitchStatusToggle");
		}
		
		public boolean clickOnStatus(){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchStatusToggle");
		}
		
		public boolean clickOffStatus(){
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchStatusToggle");
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
		
		public boolean isActivateZwaveScreenDisplayed(){
			DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceHeader") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceTitle");
		}
		
		public boolean isExcludeZwaveScreenDisplayed(){
			DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeScreenHeader") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeTitle");
		}
}
