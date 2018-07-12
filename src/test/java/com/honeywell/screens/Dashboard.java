package com.honeywell.screens;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class Dashboard extends MobileScreens {

	private static final String screenName = "Dashboard";

	public Dashboard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isWeatherIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon", 3, false);
	}

	public boolean isWeatherIconVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherIcon", timeOut, false);
	}

	public boolean isSplashScreenVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SplashScreen", timeOut, false);
	}

	public boolean isProgressBarVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar", timeOut, false);
	}
	
	public boolean isCloseButtonInHoneywellRatingPopupVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CloseButtonInHoneywellRatingPopup", timeOut);
	}
	
	public boolean clickOnCloseButtonInHoneywellRatingPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CloseButtonInHoneywellRatingPopup");
	}
	
	public boolean isDoneButtonInWeatherForecastIsVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButtonInWeatherForecast", timeOut);
	}
	
	public boolean clickOnDoneButtonInWeatherForecast() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonInWeatherForecast");
	}
	
	public boolean isGotItButtonInWeatherForecastIsVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotItButtonInWeatherForecast");
	}
	
	public boolean clickOnGotItButtonInWeatherForecast() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotItButtonInWeatherForecast");
	}

	public boolean isAddDeviceIconVisible(int timeOut) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIcon", timeOut)) {
			flag = flag
					& MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIcon", timeOut, false);
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceNewIcon")) {
				flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceNewIcon");
			}
		}
		return flag;
	}

	public boolean isAddDeviceIconBelowExistingDevicesVisible(int timeOut) {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIconBelowExistingDevice",
				timeOut)) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIconBelowExistingDevice",
					timeOut, false);
		} else {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceNewIcon");
		}
	}

	public boolean clickOnAddNewDeviceIcon() {
		if (isAddDeviceIconVisible(10)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceIcon");
		} else if (isAddDeviceIconBelowExistingDevicesVisible(10)) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceIconBelowExistingDevice");
		}
		return false;
	}

	public boolean isAddDeviceIconBelowExistingDASDeviceVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIconBelowExistingDevice",
				timeOut, false);

	}

	public boolean clickOnAddDeviceIconBelowExistingDASDevice() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AddNewDeviceIconBelowExistingDevice");
	}

	public boolean clickOnGlobalDrawerButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GlobalDrawerButton");
	}

	public boolean areDevicesVisibleOnDashboard(int timeOut) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", timeOut);
	}

	public boolean areDevicesVisibleOnDashboard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconText", 3);
	}
	
	public boolean areDeviceTempVisibleOnDashboard(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeviceCurrentTempValue", timeOut);
	}

	public boolean areDeviceTempVisibleOnDashboard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeviceCurrentTempValue", 3);
	}
	
	public boolean areDeviceTempUpVisibleOnDashboard(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperUp", timeOut);
	}

	public boolean areDeviceTempUpVisibleOnDashboard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperUp", 3);
	}
	
	public boolean areDeviceTempDownVisibleOnDashboard(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperDown", timeOut);
	}

	public boolean areDeviceTempDownVisibleOnDashboard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperDown", 3);
	}

	public boolean isDevicePresentOnDashboard(String deviceName) {
		boolean flag = true;
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
						if ((e.getAttribute("visible").equals("true")) && (e.getAttribute("value").trim() != null)
								&& (!e.getAttribute("value").trim().isEmpty()))
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
			flag = false;
		}
		return flag;
	}

	public boolean isGlobalDrawerButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", 3);
	}

	public boolean isGlobalDrawerButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GlobalDrawerButton", timeOut);
	}

	public boolean isSecurityStatusLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconStatus");
	}

	public String getSecurityStatusLabel() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardIconStatus");
	}

	public String getZwaveDeviceStatus(String expectedDevice) {
		if (this.areDevicesVisibleOnDashboard(10)) {
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconText");
			List<WebElement> dashboardIconStatus = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconStatus");
			for (int i = 0; i <= dashboardIconText.size(); i++) {
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
					if (dashboardIconStatus.get(i).getText().toUpperCase().contains("ON")) {
						return "ON";
					} else {
						return "OFF";
					}
				}
			}
		} else {
		}
		return null;
	}

	public List<WebElement> getDashboardDeviceNameElements() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "DashboardIconText");
	}

	public static boolean selectLocationFromDashBoard(TestCases testCase, String locationToBeSelected) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "HomeScreen");
		WebElement element = null;
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "LocationSpinner", 5)) {
			element = MobileUtils.getMobElement(fieldObjects, testCase, "LocationSpinner");
		}
		if (element != null) {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "LocationNameIOS", 5)) {
					if (MobileUtils.getMobElement(fieldObjects, testCase, "LocationNameIOS").getAttribute("value")
							.contains(locationToBeSelected)) {
						Keyword.ReportStep_Pass(testCase,
								"Select Location From DashBoard : User is already in location : "
										+ locationToBeSelected);
					} else {
						fieldObjects = MobileUtils.loadObjectFile(testCase, "HomeScreen");
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "LocationSpinner");

						if (MobileUtils.clickOnElement(testCase, "name", locationToBeSelected)) {
							Keyword.ReportStep_Pass(testCase,
									"Select Location From DashBoard : Successfully selected location : "
											+ locationToBeSelected);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Select Location From DashBoard : Failed to select location : "
											+ locationToBeSelected);
						}
					}
				}
			} else {
				if (element.getText().equalsIgnoreCase(locationToBeSelected)) {
					Keyword.ReportStep_Pass(testCase,
							"Select Location From DashBoard : User is already in location : " + locationToBeSelected);
				} else {
					boolean f = false;
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "LocationSpinner");
					List<WebElement> locNames = MobileUtils.getMobElements(fieldObjects, testCase, "LocationDropDown");
					for (WebElement ele : locNames) {
						if (ele.getText().equalsIgnoreCase(locationToBeSelected)) {
							ele.click();
							f = true;
							break;
						}
					}
					if (f) {
						Keyword.ReportStep_Pass(testCase,
								"Select Location From DashBoard : Successfully selected location : "
										+ locationToBeSelected);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Select Location From DashBoard : Failed to select location : " + locationToBeSelected);
					}
				}
			}
		}
		return flag;
	}
	
	public boolean VerifyComfortDeviceStatusInDashBoard(String expectedDevice) {
		boolean flag = true;
		if (this.areDevicesVisibleOnDashboard(10) && this.areDeviceTempVisibleOnDashboard(10)) {
			
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(objectDefinition, testCase,
					"DashboardIconText");
			for (int i = 0; i < dashboardIconText.size(); i++) {
				String displayedText = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					displayedText = dashboardIconText.get(i).getText();
				} else {
					try {
						displayedText = dashboardIconText.get(i).getAttribute("value");
					} catch (Exception e1) {
					}
				}
				
				flag = flag & displayedText.contains(expectedDevice);
				if (flag) {
					Keyword.ReportStep_Pass(testCase,
							"Device name matches on Dashboard");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device name was not matches in Dashboard");
				}				
			}		
		}
		
		if(this.areDeviceTempUpVisibleOnDashboard(10))
		{
			flag = true;
			Keyword.ReportStep_Pass(testCase,
					"Stat Temp Stepper Up icon is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp Stepper Up icon is not displayed on Dashboard");
		}	
		
		if(this.areDeviceTempDownVisibleOnDashboard(10))
		{
			flag = true;
			Keyword.ReportStep_Pass(testCase,
					"Stat Temp Stepper Down icon is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp Stepper Down icon is not displayed on Dashboard");
		}	
		
		if(this.areDeviceTempVisibleOnDashboard(10))
		{
			flag = true;
			Keyword.ReportStep_Pass(testCase,
					"Stat Temp values is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp values icon is not displayed on Dashboard");
		}	
		
		return flag;
	}
}
