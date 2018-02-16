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
		
		public boolean ClickSwitchSettingFromZwaveUtilities() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchSettingsMenu");
		}

		public  boolean ClickDeleteFromSettings() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton");
		}
		
		public boolean isRemoveDevicePopUpDisplayed() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveDevicePopupTitle");
		}

		
		public boolean setNameToSwitch(String value){
			MobileUtils.getMobElement(objectDefinition, testCase, "NameEditField").clear();
			return MobileUtils.setValueToElement(objectDefinition, testCase, "NameEditField",
					value);
		}
		
		public boolean saveNameToSwitch(){
			return MobileUtils.clickOnElement(objectDefinition,testCase, "ReturnKeypad")
			 && MobileUtils.clickOnElement(objectDefinition,testCase, "DoneButtonAfterNaming");
		}
		public boolean saveEditedNameToSwitch(){
			return MobileUtils.clickOnElement(objectDefinition,testCase, "ReturnKeypad");
		}
		
		public  boolean isNamingFieldDisplayed() {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameEditField");
		}
		
		public boolean clickRetryOnDeviceNotFoundPopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "RetryOption");
		}
		public boolean clickOKOnDeviceExcludedPopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmDeviceRemovalButton");
		}
		public boolean clickAddNowOnDeviceExcludedPopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNowOption");
		}
		public boolean clickCancelOnDeviceNotFoundPopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelOption");
		}

		public boolean clickCancelOnRemoveDevicePopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelDeviceRemovalButton");
		}

		public boolean clickOkOnRemoveDevicePopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmDeviceRemovalButton");
		}

		public boolean clickTryExcludeOnDeviceNotFoundPopUp() {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "TryExcludeOption");
		}

}
