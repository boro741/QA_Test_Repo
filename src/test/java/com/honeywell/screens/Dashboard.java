package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

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

	public boolean isSplashScreenVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SplashScreen",timeOut,false);
	}
	
	public boolean isProgressBarVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar",timeOut,false);
	}
	
	public boolean isAddDeviceIconVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIcon",timeOut,false);

	}

	public boolean clickOnAddNewDeviceIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceIcon");
	}

	public boolean clickOnGlobalDrawerButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerButton");
	}

	public boolean areDevicesVisibleOnDashboard(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", timeOut); 
	}

	public boolean areDevicesVisibleOnDashboard()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", 3); 
	}

	public boolean isDevicePresentOnDashboard(String deviceName)
	{
		boolean flag=true;
		if (this.areDevicesVisibleOnDashboard(10)) {
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
			Keyword.ReportStep_Pass(testCase, "Dashboard Icons not found");
		}
		return flag;
	}

	public boolean isGlobalDrawerButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", 3);
	}

	public boolean isGlobalDrawerButtonVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", timeOut);
	}

	public String getZwaveDeviceStatus(String expectedDevice) {
		if (this.areDevicesVisibleOnDashboard(10)) {
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconText");
			List<WebElement> dashboardIconStatus = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconStatus");
			for (int i=0; i<=dashboardIconText.size(); i++) {
				String displayedText = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					displayedText = dashboardIconText.get(i).getText();
				} else {
					try {
						displayedText = dashboardIconText.get(i).getAttribute("value");
					} catch (Exception e1) {
					}
				}
				if (displayedText.toUpperCase().contains(expectedDevice)) {
					if(dashboardIconStatus.get(i).getText().toUpperCase().contains("ON")){
						return "ON";
					}else {
						return "OFF";
					}
				}
			}
		} else {
		}
		return null;
	}

	public List<WebElement> getDashboardDeviceNameElements()
	{
		return MobileUtils.getMobElements(objectDefinition, testCase, "DashboardIconText");
	}
	
}
