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
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchStatusToggle",false);
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
		if(getSwitchStatus().equalsIgnoreCase("On")||getSwitchStatus().equalsIgnoreCase("true")){
			return true;
		}else{
			return clickOnStatus();
		}
	}

	public boolean switchOff(){
		if(getSwitchStatus().equalsIgnoreCase("Off")||getSwitchStatus().equalsIgnoreCase("false")){
			return true;
		}else{
			return clickOffStatus();
		}
	}

	//Activate elements
	public boolean isActivateZwaveScreenDisplayed(){
		DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceHeader",false) && MobileUtils.isMobElementExists(objectDefinition, testCase, "ActivateTheDeviceTitle",false);
	}

	//Exclude elements
	public boolean isExcludeZwaveScreenDisplayed(){
		DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeScreenHeader",false) && MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeTitle",false);
	}
	public boolean isExcludeZwaveScreenDisplayed(int timer){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeScreenHeader", timer,false) && MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludeModeTitle",timer,false);
	}
	//Settings
	public boolean isSwitchSettingOnZwaveDevicesDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchSettingsMenu",false);
	}

	public boolean isDimmerSettingOnZwaveDevicesDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DimmerSettingsMenu",false);
	}

	public boolean ClickSwitchSettingFromZwaveDevices() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchSettingsMenu");
	}

	public boolean ClickDimmerSettingFromZwaveDevices() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DimmerSettingsMenu");
	}

	public  boolean ClickDeleteFromSettings() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteButton");
	}

	//Remove
	public boolean isRemoveDevicePopUpDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemoveDevicePopupTitle",false);
	}

	//set name
	public boolean setNameToSwitch(String value){
		MobileUtils.getMobElement(objectDefinition, testCase, "NameEditField").clear();
		return MobileUtils.setValueToElement(objectDefinition, testCase, "NameEditField",
				value);
	}

	public  boolean isNamingFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameEditField",false);
	}

	public  boolean isNamingFieldDisplayed(int timer) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameEditField",timer,false);
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

	public boolean clickNavigateBack(){
		return MobileUtils.clickOnElement(objectDefinition,testCase, "NavigateBack");
	}

	public  boolean isEditNamingFieldDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchRenameField",false);
	}
	public boolean editNameToSwitch(String value){
		MobileUtils.getMobElement(objectDefinition, testCase, "SwitchRenameField").clear();
		return MobileUtils.setValueToElement(objectDefinition, testCase, "SwitchRenameField",
				value);
	}

	//ZwaveUtilities
	public  boolean isZwaveUtitiesScreenDisplayed() {
		if(isFixAllProgressDisplayed()){
			DASZwaveUtils.waitForActionToComplete(testCase,"Fix All");
		}
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralDeviceInclusion",false) && MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralDeviceExclusion",false);
	}

	public boolean clickGeneralDeviceInclusionMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralDeviceInclusion");
	}

	public boolean clickGeneralDeviceExclusionMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GeneralDeviceExclusion");
	}

	public boolean isGeneralDeviceExclusionMenuDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GeneralDeviceExclusion");
	}
	
	public boolean clickControllerFactoryResetMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ControllerFactoryReset");
	}

	public boolean clickModelandFirmwareDetailsMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ModelandFirmwareDetails");
	}


	//controller info

	public  boolean isModelandFirmwareDetailsDisplayed() {
		boolean detailsFound= false;
		String details= MobileUtils.getFieldValue(objectDefinition, testCase, "ModelFirmwareSubTitle");
		if(details.contains("Home ID")||details.contains("Product Type : ")
				|| details.contains("Product ID :") || details.contains("Node :")
				|| details.contains("Manufacturer :") || details.contains("Security :")
				){
			detailsFound=true;
		}
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModelFirmwareTitle",false) && detailsFound;
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

	public boolean isRetryOnDeviceNotFoundPopUpDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RetryOption",lookFor,false);
	}

	public boolean isExcludedSuccessPopupMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludedSuccessPopupMessage",false);
	}

	public boolean isExcludedSuccessPopupMessageDisplayed(int timer) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludedSuccessPopupMessage", timer,false);
	}
	public boolean isExcludedSuccessPopupTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ExcludedSuccessPopupTitle",false);
	}

	public boolean isEnteringExclusionModeOverlayDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnteringExclusionModeOverlay",3,false);
	}

	public boolean isNameTheDeviceTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NameTheDeviceTitle",false);
	}
	public boolean isToggleStatusProgressDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ToggleStatusProgress",false);
	}

	public boolean isDeviceNotFoundPopupDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeviceNotFoundPopup",false);
	}

	public boolean isDeviceNotFoundPopupDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeviceNotFoundPopup", lookFor,false);
	}
	public boolean isNoDeviceToExcludePopupHeaderDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoDeviceToExcludePopupHeader",lookFor,false);
	}

	public boolean isEnteringInclusionModeOverlayDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnteringInclusionModeOverlay",3,false);
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

	public boolean isOKOnDeviceExcludedPopUpDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ConfirmDeviceRemovalButton", lookFor);
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
		DASZwaveUtils.waitForActionToComplete(testCase,"Fix all");
		if(!testCase.getPlatform().toUpperCase().contains("IOS")){
			return true;
		}else if(MobileUtils.isMobElementExists(objectDefinition, testCase, "FixAllAcceptResult",false)){
			MobileUtils.clickOnElement(objectDefinition, testCase, "FixAllAcceptResult");
		}
		return true;
	}

	public boolean isCancelFromNavigationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelButton");
	}
	public boolean clickCancelFromNavigation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}

	public boolean clickConfirmOnCancelFromNavigation() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelYesButton");
	}

	public boolean isZwaveDevicesScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveUtilitiesMenu",false);
	}

	public boolean isFixAllProgressDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FixAllProgress",5,false);
	}

	public boolean isNamingProgressDisplayed() {
		return MobileUtils.isMobElementExists("name","In progress",testCase);
		//return MobileUtils.isMobElementExists(objectDefinition, testCase, "FixAllProgress",5,false);
	}

	public boolean clickOnFactoryReset() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ControllerFactoryReset");
	}

	public boolean isFactoryResetPopupHeaderDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetPopupHeader",false);
	}

	public boolean isFactoryResetPopupMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetPopupMessage",false);
	}

	public boolean clickOnFactoryResetPopupCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FactoryResetPopupCancel");
	}

	public boolean clickOnFactoryResetPopupConfirm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FactoryResetPopupConfirm");
	}

	public boolean isFactoryResetProgressDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetProgress",false);
	}

	public boolean isFactoryResetSuccessfullPopupHeaderDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetSuccessfullPopup",false);
	}

	public boolean isFactoryResetSuccessfullPopupMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetSuccessfullPopupMessage",false);
	}

	public boolean clickOnFactoryResetSuccessfullAckConfirm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FactoryResetSuccessfullAck");
	}

	public boolean isFactoryResetFailedPopupMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FactoryResetFailedPopup",false);
	}

	public boolean clickOnFactoryResetFailedPopupAckConfirm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FactoryResetFailedPopupAck");
	}

	//REPLACE FUNCTIONLITY

	public boolean clickOnReplaceButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ReplaceButton");
	}

	public boolean isReplacePopupHeaderDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplacePopupHeader",false);
	}

	public boolean clickOnReplacePopupCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ReplacePopupCancel");
	}

	public boolean clickOnReplacePopupOk() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ReplacePopupOk");
	}

	public boolean isReplaceProgressDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplaceProgress",false);
	}

	public boolean isReplaceScreenDisplayed() {
		DASZwaveUtils.waitForActionToComplete(testCase, "Replace Progress");
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplaceScreen",false);
	}

	public boolean isReplaceScreenMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplaceScreenMessage",false);
	}

	public boolean isReplacedSuccessfullyDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplacedSuccessfully",false);
	}

	public boolean isReplacedSuccessfullyMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ReplacedSuccessfullyMessage",false);
	}

	public boolean clickOnReplacedSuccessfullyMessageAck() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ReplacedSuccessfullyMessageAck");
	}

	public boolean isDeletedSuccessPopupTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeletedSuccessPopupTitle",false);
	}

	public boolean isDeletedSuccessPopupMessageDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeletedSuccessPopupMessage",false);
	}

}
