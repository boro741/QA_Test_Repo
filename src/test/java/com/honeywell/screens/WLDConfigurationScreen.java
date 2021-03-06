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

public class WLDConfigurationScreen extends MobileScreens {

	private static final String screenName = "WLD_Configuration";
	boolean flag = true;
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
			flag = true;
			return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
			
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AltBackButton")) {
			flag = true;
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AltBackButton");
		} else {
			flag = false;
		}
		return flag;
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
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_DeleteLink").equalsIgnoreCase("Delete Leak Detector")) {
				flag=true;
			}
			else {flag=false;}
		}
		else {
		 String str1 = testCase.getMobileDriver().findElement(By.xpath("//*[@name='Delete Leak Detector']")).getText();
		if(str1.equalsIgnoreCase("Delete Leak Detector")) {
			flag = true;
		}
		else flag=false;
		}
		return flag;
	}
	public String getConfigurationDeleteLeakDetectorLinkValue() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "Configuration_DeleteLink");
		}
		else {
			return testCase.getMobileDriver().findElement(By.xpath("//*[@name='Delete Leak Detector']")).getText();
		}
	}
	public static boolean navigateFromDashboardToWLDConfigurationScreen(TestCases testCase, TestCaseInputs inputs) 
	{
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDSolutionCard sol = new WLDSolutionCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			sol.checkAndDismissControlState();
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
			} else {
				this.clickOnBackButton();
				Thread.sleep(1000);this.clickOnBackButton();
				Thread.sleep(1000);this.clickOnBackButton();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}	
}