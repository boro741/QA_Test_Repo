package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

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

	//Settings elements
	public boolean verifyPresenceOfSwitchStatus(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchStatusToggle");
	}

	public String getSwitchStatus(){
		return MobileUtils.getFieldValue(objectDefinition, testCase, "SwitchStatusToggle");
	}

	public String getSwitchStatusFromDevicesListScreen(String deviceName){
		List<WebElement> listOFZwaveDevices = MobileUtils.getMobElements(objectDefinition, testCase, "ZwaveDevicesList");
		if(testCase.getPlatform().toUpperCase().contains("IOS")){
			List<WebElement> listOFZwaveDevicesName = MobileUtils.getMobElements(testCase, "name",deviceName);
			if(listOFZwaveDevicesName.size()>0){
					return MobileUtils.getFieldValue(testCase, "name",deviceName+"_value");
			}
		}else{
			List<WebElement> listOFZwaveDevicesName = MobileUtils.getMobElements(objectDefinition, testCase, "ZwaveDevicesName");
			List<WebElement> listOFZwaveDevicesStatus = MobileUtils.getMobElements(objectDefinition, testCase, "ZwaveDevicesStatus");

			for(int i=0; i<listOFZwaveDevices.size();i++){
				if(listOFZwaveDevicesName.get(i).getText().contains(deviceName)){
					return	listOFZwaveDevicesStatus.get(i).getText();
				}
			}
		}
		return null;
	}

	public String getSwitchStatusOffline(){
		return MobileUtils.getFieldValue(objectDefinition, testCase, "SwitchSettingsStatus");
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

	//Activate elements
	public boolean isActivateZwaveScreenDisplayed(){
		DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceHeader") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceTitle");
	}

	//Exclude elements
	public boolean isExcludeZwaveScreenDisplayed(){
		DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeScreenHeader") && MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeTitle");
	}

	//Settings
	public boolean ClickSwitchSettingFromZwaveUtilities() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchSettingsMenu");
	}

	public boolean ClickDimmerSettingFromZwaveUtilities() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DimmerSettingsMenu");
	}

	public  boolean ClickDeleteFromSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton");
	}

	//Remove
	public boolean isRemoveDevicePopUpDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveDevicePopupTitle");
	}

	//set name
	public boolean setNameToSwitch(String value){
		MobileUtils.getMobElement(objectDefinition, testCase, "NameEditField").clear();
		return MobileUtils.setValueToElement(objectDefinition, testCase, "NameEditField",
				value);
	}

	public  boolean isNamingFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameEditField");
	}

	public boolean saveNameToSwitchOnIOS(){
		return MobileUtils.clickOnElement(objectDefinition,testCase, "ReturnKeypad")
				&& MobileUtils.clickOnElement(objectDefinition,testCase, "DoneButtonAfterNaming");
	}
	public boolean saveNameToSwitchOnAndroid(){
		return MobileUtils.pressBackButton(testCase, "Hide keypad")
				&& MobileUtils.clickOnElement(objectDefinition,testCase, "DoneButtonAfterNaming");
	}

	//edit name
	public boolean saveEditedNameToSwitchOnAndroid(){
		return MobileUtils.pressBackButton(testCase, "Hide keypad")
				&& MobileUtils.pressBackButton(testCase, "NavigateBack to save");
	}
	public boolean saveEditedNameToSwitch(){
		return MobileUtils.clickOnElement(objectDefinition,testCase, "ReturnKeypad");
	}

	public  boolean isEditNamingFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchRenameField");
	}
	public boolean editNameToSwitch(String value){
		MobileUtils.getMobElement(objectDefinition, testCase, "SwitchRenameField").clear();
		return MobileUtils.setValueToElement(objectDefinition, testCase, "SwitchRenameField",
				value);
	}

	//ZwaveUtilities
	public  boolean isZwaveUtitiesScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralDeviceInclusion") && MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralDeviceExclusion");
	}

	public boolean clickGeneralDeviceInclusionMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralDeviceInclusion");
	}

	public boolean clickGeneralDeviceExclusionMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralDeviceExclusion");
	}

	public boolean clickControllerFactoryResetMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ControllerFactoryReset");
	}

	public boolean clickModelandFirmwareDetailsMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelandFirmwareDetails");
	}

	//device not found popup

	public boolean clickTryExcludeOnDeviceNotFoundPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TryExcludeOption");
	}

	public boolean clickOKOnDeviceNotFoundPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoDeviceToExcludePopupDismiss");
	}
	public boolean clickRetryOnDeviceNotFoundPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "RetryOption");
	}

	public boolean clickCancelOnDeviceNotFoundPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelOption");
	}

	public boolean clickNoDeviceToExcludePopupCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoDeviceToExcludePopupCancel");
	}

	public boolean clickNoDeviceToExcludePopupRetry() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoDeviceToExcludePopupRetry");
	}
	//Excluded popup

	public boolean clickOKOnDeviceExcludedPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmDeviceRemovalButton");
	}

	public boolean clickAddNowOnDeviceExcludedPopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNowOption");
	}

	public boolean clickCancelFurtherExclusionOnExcludedPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralExcludeCancelFurthur");
	}

	public boolean clickConfirmFurtherExclusionOnExcludedPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralExcludeCorfirmFurthur");
	}

	//Remove popup
	public boolean clickCancelOnRemoveDevicePopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelDeviceRemovalButton");
	}

	public boolean clickOkOnRemoveDevicePopUp() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ConfirmDeviceRemovalButton");
	}



	public boolean clickZwaveUtilitiesMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveUtilitiesMenu");
	}

	public boolean isAllOffEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AllOffButton").isEnabled();
	}

	public boolean clickOnAllOff() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllOffButton");
	}

	public boolean isAllOnEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "AllONButton").isEnabled();
	}

	public boolean clickOnAllOn() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AllONButton");
	}
	public boolean isFixAllEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "FixAllButton").isEnabled();
	}

	public boolean clickOnFixAll() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FixAllButton");
	}
	
	public boolean clickOnFixAllPopupConfirm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FixAllPopupConfirm");
	}
	
	public boolean clickOnFixAllPopupCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FixAllPopupCancel");
	}

	public boolean clickOnFixAllPopupAccept() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FixAllAcceptResult");
	}
}
