package com.honeywell.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;

import io.appium.java_client.TouchAction;

public class WLDManageAlerts extends MobileScreens {

	private static final String screenName = "WLD_ManageAlerts";
	private static final boolean NULL = false;
	public WLDManageAlerts(TestCases testCase) {
		super(testCase, screenName);
	}
	public  boolean isTemperatureAlertsHeaderTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "");
	}
	public  String getTemperatureAlertsHeaderTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "");	
	}

	public  boolean isIndoorTemperatureAlertsTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "");
	}
	public  String getIndoorTemperatureAlertsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");	
	}

	public  boolean isIndoorTemperatureAlertsToggleVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");

	}
	public String getIndoorTemperatureAlertsToggleValue() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");
		}else {//for ios
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[1]"));
			if(toggleSwitch.getAttribute("value").equals("1")) {
				return "ON";
			}else {
				return "OFF";
			}
		}

	}
	
	public  boolean isIndoorHumidityAlertsToggleVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorHumidityAlertsToggle");
	}
	public  String getIndoorHumidityAlertsToggleValue() {
		

		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "IndoorHumidityAlertsToggle");	
		}else {//for ios
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[1]"));
			if(toggleSwitch.getAttribute("value").equals("1")) {
				return "ON";
			}else {
				return "OFF";
			}
		}

	
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean clickIndoorTemperatureAlertsToggle() {
			if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileUtils.clickOnElement(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");
			}else {
				WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[1]"));
				toggleSwitch.click();
			}
			return true;
	}
	
	
	public boolean clickIndoorHumidityAlertsToggle() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "IndoorHumidityAlertsToggle");
		}else {
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[1]"));
			toggleSwitch.click();
		}
		return true;
}
	
	
	
	
	
	
	public boolean isIndoorTemperatureAlertToggleEnabled() 
	{
		try{
			//WebElement toggleSwitch = MobileUtils.getMobElement(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[1]"));
			if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
				System.out.println("Enabled");
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e){
			System.out.println("Exception found");
			
		}
		return true;
	
	}
	
	
	public boolean isIndoorHumidityAlertToggleEnabled() 
	{
		try{
			//WebElement toggleSwitch = MobileUtils.getMobElement(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[1]"));
			if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
				System.out.println("Enabled");
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e){
			System.out.println("Exception found");
			
		}
		return true;
	
	}
	
	

	public  boolean isEmailNotificationsforTemperatureAlertsTextVissible() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {

		
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsText");

		}
		else {
			WebElement email = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_subTitle'])[2]"));

			return email.isDisplayed();
		}
		
		
		

		
	}
	public  String getEmailNotificationsforTemperatureAlertsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsText");	
	}
	public  boolean isEmailNotificationsforTemperatureAlertsToggleVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsToggle");
		
	}
	public  String getEmailNotificationsforTemperatureAlertsToggleValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsToggle");	
	}

	public  boolean isAlertforthisRangeTemperatureTextVissible() {
		
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeTemperatureText");

		}
		else {
		WebElement range = testCase.getMobileDriver().findElement(By.xpath("(//*[@value='Alert for this range'])[1]"));

		return range.isDisplayed();
		}
		
	}
	public  String getAlertforthisRangeTemperatureTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeTemperatureText");	
	}

	public  boolean isAlertforthisRangeTemperaturePercentageButtonVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeTemperaturePercentageButton");
	}
	public  String getAlertforthisRangeTemperaturePercentageButtonValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeTemperaturePercentageButton");	
	}

	public  boolean isBelowTextinTemperatureVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BelowTextinTemperature");
	}
	public  String getBelowTextinTemperatureValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BelowTextinTemperature");	
	}

	public  boolean isBelowTemperatureDropDownVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BelowTemperatureDropDown");
	}
	public  String getBelowTemperatureDropDownValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BelowTemperatureDropDown");	
	}

	public  boolean isAboveTextinTemperatureVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboveTextinTemperature");
	}
	public  String getAboveTextinTemperatureValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AboveTextinTemperature");	
	}

	public  boolean isAboveTemperatureDropDownVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboveTemperatureDropDown");
	}
	public  String getAboveTemperatureDropDownValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AboveTemperatureDropDown");	
	}

	public  boolean isHumidityAlertsHeaderTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidityAlertsHeaderText");
	}
	public  String getHumidityAlertsHeaderTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HumidityAlertsHeaderText");	
	}
	public  boolean isIndoorHumidityAlertsTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorHumidityAlertsText");
	}
	public  String getIndoorHumidityAlertsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "IndoorHumidityAlertsText");	
	}
	
	public  boolean isEmailNotificationsforHumidityAlertsTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforHumidityAlertsText");
	}
	public  String getEmailNotificationsforHumidityAlertsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforHumidityAlertsText");	
	}
	public  boolean isEmailNotificationsforHumidityAlertsToggleVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforHumidityAlertsToggle");
	}
	public  String getEmailNotificationsforHumidityAlertsToggleValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforHumidityAlertsToggle");	
	}
	public  boolean isAlertforthisRangeHumidityTextVissible() {
		
		
		
		
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		TouchAction action = new TouchAction(testCase.getMobileDriver());		
		int i = 0;
		while (!(MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeHumidityText")) && i < 10)
		{
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, (int) (dimension.getHeight() * -.7)).release().perform();
		i++;					
		}
	
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeHumidityText");
		
		
		
	}
	public  String getAlertforthisRangeHumidityTextValue() {
		
//		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
//		TouchAction action = new TouchAction(testCase.getMobileDriver());		
//		int i = 0;
//		while ((MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeHumidityText")) && i < 10)
//		{
//			action.press(10, (int) (dimension.getHeight() * .5)).moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
//		i++;					
//		}

		return MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeHumidityText");	
		

		
	}
	public  boolean isAlertforthisRangeHumidityPercentageButtonVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlertforthisRangeHumidityPercentageButton");
	}
	public  String getAlertforthisRangeHumidityPercentageButtonValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeHumidityPercentageButton");	
	}
	public  boolean isBelowTextinHumidityVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BelowTextinHumidity");
	}
	public  String getBelowTextinHumidityValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BelowTextinHumidity");	
	}
	public  boolean isBelowHumidityDropDownVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BelowHumidityDropDown");
	}
	public  String getBelowHumidityDropDownValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BelowHumidityDropDown");	
	}
	public  boolean isAAboveTextinHumidityVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboveTextinHumidity");
	}
	public  String getAboveTextinHumidityValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AboveTextinHumidity");	
	}
	public  boolean isAboveHumidityDropDownVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AboveHumidityDropDown");
	}
	public  String getAboveHumidityDropDownValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "AboveHumidityDropDown");	
	}
	public  boolean isEmailContactsTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailContactsText");
	}
	public  String getEmailContactsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailContactsText");	
	}

	
	
	
	
	
	
	
	
	
	
	public static boolean navigateFromDashboardScreenToWLDManageAlerts(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				flag=flag & set.clickonManagealerts();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	
}