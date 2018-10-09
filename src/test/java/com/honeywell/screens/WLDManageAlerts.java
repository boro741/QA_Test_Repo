package com.honeywell.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;

import io.appium.java_client.TouchAction;

public class WLDManageAlerts extends MobileScreens {

	private static final String screenName = "WLD_ManageAlerts";
	boolean flag=true;
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
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "IndoorTemperatureAlertsToggle");
		}else {
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[1]"));
			toggleSwitch.click();
			flag = true;
		}
		return flag;
	}
	public boolean clickIndoorHumidityAlertsToggle() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "IndoorHumidityAlertsToggle");
		}else {
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[1]"));
			toggleSwitch.click();
			flag = true;
		}
		return flag;
	}
	public boolean isIndoorTemperatureAlertToggleEnabled() 
	{
		try {
			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(2, TimeUnit.SECONDS);
			fWait.withTimeout(10, TimeUnit.SECONDS);
			boolean isEventReceived =  fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if(!MobileUtils.isMobElementExists(objectDefinition, testCase, "IndoorTemperatureAlertsToggle")) {
						return  true;
					}
					else 
						return  false;		
				}
			});
			if(isEventReceived) {
				try{
					WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[1]"));
					if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
						flag = true;
					}
					else {
						flag =false;
					}
				}
				catch (Exception e){
					flag=false;
				}			}
			}
		catch(Exception e)
		{
			flag = true;
		}
		return flag;
	}
	public boolean isIndoorHumidityAlertToggleEnabled() 
	{
		try{
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[1]"));
			if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
				flag = true;
			}
			else {
				flag = false;
			}
		}
		catch (Exception e){
			flag = false;
		}
		return flag;
	}
	public  boolean isEmailNotificationsforTemperatureAlertsTextVissible() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsText");
		}
		else {			
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsText");
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
	public  boolean isAboveTextinHumidityVissible() {
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
	//Email Contacts
	public  boolean isEmailContactsTextVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailContactsText");
	}
	public  String getEmailContactsTextValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "EmailContactsText");	
	}
	public static boolean navigateFromDashboardScreenToWLDManageAlerts(TestCases testCase, TestCaseInputs inputs) {
		boolean flag=true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDSolutionCard sol = new WLDSolutionCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & sol.checkAndDismissControlState();
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
	public static boolean navigateFromDashboardScreenToEmailNotificationsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		WLDManageAlerts ale = new WLDManageAlerts(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				flag = flag & set.clickonManagealerts();
				flag = flag & ale.clickEmailContacts();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	public boolean clickEmailContacts() {
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());	
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		int i = 0;
		while ((!isEmailContactsTextVissible()) && i < 10)
		{
			touchAction.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, (int) (dimension.getHeight() * -.7)).release().perform();
			i++;					
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "EmailContactsText");
	}
	public boolean clickAlertforthisRangeTemperaturePercentageButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlertforthisRangeTemperaturePercentageButton");
	}
	public  boolean clickAlertforthisRangeHumidityPercentageButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlertforthisRangeHumidityPercentageButton");
	}
	public boolean clickBelowTemperatureDropDown() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BelowTemperatureDropDown");
	}
	public boolean clickAboveTemperatureDropDown() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AboveTemperatureDropDown");
	}
	public boolean clickBelowHumidityDropDown() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BelowHumidityDropDown");

	}
	public boolean clickAboveHumidityDropDown() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AboveHumidityDropDown");
	}
	public boolean verifyAlertforThisBelowTemperatureRangeUpdated(String belowTemp) {
		String str = MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeTemperaturePercentageButton");	
		String newstr[] = str.split(" /");
		if(newstr[0].equals(belowTemp)) {
			flag = true;
		}
		else {
			flag = false;
		}
		return flag;
	}
	public boolean verifyAlertforThisAboveTemperatureRangeUpdated(String aboveTemp) {
		String str = MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeTemperaturePercentageButton");	
		String newstr[] = str.split(" /");
		if(newstr[1].equals(aboveTemp)) {
			flag = true;
		}
		else {
			flag = false;
		}
		return flag;
	}
	public String selectRandomValue() {
		List<WebElement> element = MobileUtils.getMobElements(testCase, "id","list_item_lyric_spinner_drop_down_text");
		String Selected = element.get(2).getText();
		element.get(2).click();
		return Selected;	
	}
	public boolean verifyAlertforThisAboveHumidityRangeUpdated(String aboveTemp) {
		String str = MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeHumidityPercentageButton");	
		String newstr[] = str.split(" /");
		if(newstr[1].equals(aboveTemp)) {
			flag = true;
		}
		else {
			flag = false;
		}
		return flag;
	}
	public boolean verifyAlertforThisBelowHumidityRangeUpdated(String belowTemp) {
		String str = MobileUtils.getFieldValue(objectDefinition, testCase, "AlertforthisRangeHumidityPercentageButton");	
		String newstr[] = str.split(" /");
		if(newstr[0].equals(belowTemp)) {
			flag = true;
		}
		else {
			flag = false;
		}
		return flag;
	}
	public boolean isEmailNotificationsforTemperatureAlertsToggleEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforTemperatureAlertsToggle").equals("ON")){
				flag = true;
			}
			else {
				flag = false;
			}
		}
		else {
			try{
				WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalTemperature_toggle'])[2]"));
				if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
					flag = true;
				}
				else {
					flag = false;
				}
			}
			catch (Exception e){
				flag = false;
			}
		}
		return flag;
	}
	public boolean isEmailNotificationsforHumidityAlertsToggleEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if(MobileUtils.getFieldValue(objectDefinition, testCase, "EmailNotificationsforHumidityAlertsToggle").equals("ON")){
				flag = true;
			}
			else {
				flag=false;
			}
		}
		else {
		try{
			WebElement toggleSwitch = testCase.getMobileDriver().findElement(By.xpath("(//*[@name='AbnormalHumidity_toggle'])[2]"));
			if (toggleSwitch.getAttribute("value").equalsIgnoreCase("1")) {
				flag = true;
			}
			else {
				flag = false;
			}
		}
		catch (Exception e){
			flag = false;
		}
		}
		return flag;
	}
}