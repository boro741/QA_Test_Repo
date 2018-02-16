package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class Dashboard extends MobileScreens {

	private static final String screenName = "Dashboard";

	public Dashboard(TestCases testCase) {
		super(testCase, screenName);
	}
	
	public boolean isWeatherIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon",3,false);
	}

	public boolean isWeatherIconVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon",timeOut,false);
	}
	
	public boolean isAddDeviceIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIcon");
		
	}
	
	public boolean clickOnAddNewDeviceIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceIcon");
	}
	
	public boolean clickOnGlobalButtonOfDashboard() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerButton");
	}
	
	public boolean areDevicesPresentOnDashboard(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", timeOut); 
	}
	
	public boolean areDevicesPresentOnDashboard()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", 3); 
	}
	
	public boolean isDevicePresentOnDashboard(String deviceName)
	{
		boolean flag=true;
		if (this.areDevicesPresentOnDashboard(10)) {
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconText");
			boolean f = false;
			for (WebElement e : dashboardIconText) {
				String displayedText = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					displayedText = e.getText();
				} else {
					try {
						displayedText = e.getAttribute("value");
					} catch (Exception e1) {
					}
				}
				if (displayedText.equalsIgnoreCase(deviceName)) {
					f = true;
					break;
				}
			}
				flag = f;
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dashboard Icons not found");
		}
		return flag;
	}
}
