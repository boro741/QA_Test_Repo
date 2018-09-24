package com.honeywell.screens;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;

import bsh.This;

public class WLDConfigurationScreen extends MobileScreens {

	private static final String screenName = "WLD_Configuration";
	public WLDConfigurationScreen(TestCases testCase) {
		super(testCase, screenName);
	}
	public boolean isConfigurationHeaderVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_Header");
	}
	public String getConfigurationHeaderValue() 
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_Header");
	}
	public boolean isConfigurationNameTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_Name_Text");
	}
	public String getConfigurationNameTextValue() 
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_Name_Text");
	}
	public boolean isConfigurationWLDNameVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_WLD_Name_Value");
	}
	public String getConfigurationWLDNameValue() 
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_WLD_Name_Value");
	}
	public boolean clickonConfigurationWLDName()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Configuration_WLD_Name_Value");
	}
	public boolean setValueToWLDNameTextBox(String value) 
	{
		return MobileUtils.setValueToElement(objectDefinition, testCase, "Configuration_WLD_Name_Value", value);
	}
	public boolean isHomeNameAlreadyExistTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HomeNameAlreadyExistText");
	}
	public String getHomeNameAlreadyExistTextValue() 
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HomeNameAlreadyExistText");
	}
	public boolean isHomeNameExistsBodyVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HomeNameExistsBody");
	}
	public String getHomeNameExistsBodyValue() 
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HomeNameExistsBody");
	}
	public boolean isPopUpOkButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PopUpOkButton");
	}
	public boolean clickonPopUpOkButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "PopUpOkButton");
	}
	
	
	
	
	public boolean isBackButtonVisible(int timeOut) 
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", timeOut);
	}
	public boolean clickOnBackButton() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
		} else {
			return false;
		}
	}
	
	public boolean clearWLDNameTextBox() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) 
		{
			MobileUtils.clickOnElement(objectDefinition, testCase, "Configuration_WLD_Name_Value");
			return MobileUtils.clearTextField(objectDefinition, testCase, "Configuration_WLD_Name_Value");
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeTextField"))
			.clear();
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_WLD_Name_Value");
		}
	}
	
	public boolean isConfigurationFirmwareVersionTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_FirmwareVersion_Text");
	}
	public String getConfigurationFirmwareVersionTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_FirmwareVersion_Text");
	}
	public boolean isConfigurationFirmwareVersionValueVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_FirmwareVersion_Value");
	}
	public String getConfigurationFirmwareVersionValueValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_FirmwareVersion_Value");
	}
	public boolean isConfigurationDeleteLeakDetectorLinkVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Configuration_DeleteLink");
	}
	public String getConfigurationDeleteLeakDetectorLinkValue() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "Configuration_DeleteLink").getText();		
	}
	public static boolean navigateFromDashboardToWLDConfigurationScreen(TestCases testCase, TestCaseInputs inputs) 
	{
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				flag=flag & set.clickonLeakDetectorConfiguration();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	public static boolean navigateFromDashboardToWLDSolutionCardScreen(){
		//Later
		return false;
		
	}
	public boolean navigateFromWLDConfigurationScreenToSolutionCard(TestCases testCase) {

		boolean flag = true;
		try {
			clickOnBackButton();
			clickOnBackButton();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	
		
	}
	
	public boolean navigateFromWLDConfigurationScreenToDashboard(TestCases testCase) 
	{
		boolean flag = true;
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) 
			{
			MobileUtils.hideKeyboard(testCase.getMobileDriver());
			this.clickOnBackButton();
			this.clickOnBackButton();
			this.clickOnBackButton();
			this.clickOnBackButton();
			flag = true;
			} else {
				MobileUtils.hideKeyboard(testCase.getMobileDriver());
				this.clickOnBackButton();
				this.clickOnBackButton();
				flag = true;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}	
}