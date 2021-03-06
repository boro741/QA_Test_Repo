package com.honeywell.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.platform.VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

public class Dashboard extends MobileScreens {

	private static final String screenName = "Dashboard";

	public Dashboard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
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

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
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
			flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceIcon", timeOut, false);
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceNewIcon")) {
			flag &= MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceNewIcon");
		} else {
			flag = false;
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

	public boolean clickOnDeviceOnDashbaord() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DashboardIconText");
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

	public boolean isIncreaseSecurityPopupVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "IncreaseSecurityPopup");
	}

	public boolean clickOnDontUseButtonInIncreaseSecurityPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DontUseButtonInIncreaseSecurityPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "DontUseButtonInIncreaseSecurityPopup");
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean clickOnAddButtonInIncreaseSecurityPopup() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AddButtonInIncreaseSecurityPopup")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "AddButtonInIncreaseSecurityPopup");
		} else {
			flag = false;
		}
		return flag;
	}

	// Method to check the camera ON text in dashboard screen
	public boolean isCameraOnTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOnText");
	}

	// Amresh
	public boolean isDashboardIndoorTempWldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIndoorTempWld");
	}

	public String getdashboardIndoorTempWldLabel() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardIndoorTempWld");
	}

	public boolean isDashboardHumidityWldVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardHumidityWld");
	}

	public String getDashboardHumidityWldLabel() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardHumidityWld");
	}

	public String getDashboardDeviceNameLabel() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardIconText");
	}

	// Method to check the camera Off text in dashboard screen
	public boolean isCameraOffTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOffText");
	}

	public boolean isCameraOfflineTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraOfflineText");
	}

	public boolean isCameraRingClosedVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraRingClosedText");
	}

	public String getCameraStatus(int timeOut) {
		String cameraStatus = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardIconStatus", timeOut)) {
			cameraStatus = MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardIconStatus");
			System.out.println("######Current Camera Status is: " + cameraStatus);
		}
		return cameraStatus;
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

	public WebElement getDashboardDeviceNameElement(String deviceName) {
		WebElement element = null;
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				element = testCase.getMobileDriver()
						.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
								+ "new UiSelector()." + "text" + "(\"" + deviceName + "\"));"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Keyword.ReportStep_Fail(testCase, FailType.COSMETIC_FAILURE, "Error Occured:" + e.getMessage());
		}
		return element;
	}

	public boolean selectLocationFromDashBoard(TestCases testCase, String locationToBeSelected) {
		boolean flag = true;
		WebElement element = null;
		System.out.println("");
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationDropDownButton")) {
			element = MobileUtils.getMobElement(objectDefinition, testCase, "LocationName");
		}
		if (element != null) {
			/*
			 * if (testCase.getPlatform().toUpperCase().contains("IOS")) { fieldObjects =
			 * MobileUtils.loadObjectFile(testCase, "PrimaryCard"); if
			 * (MobileUtils.isMobElementExists(fieldObjects, testCase, "LocationNameIOS",
			 * 5)) { if (MobileUtils.getMobElement(fieldObjects, testCase,
			 * "LocationNameIOS").getAttribute("value") .contains(locationToBeSelected)) {
			 * Keyword.ReportStep_Pass(testCase,
			 * "Select Location From DashBoard : User is already in location : " +
			 * locationToBeSelected); } else { fieldObjects =
			 * MobileUtils.loadObjectFile(testCase, "HomeScreen"); flag = flag &
			 * MobileUtils.clickOnElement(fieldObjects, testCase, "LocationSpinner");
			 * 
			 * if (MobileUtils.clickOnElement(testCase, "name", locationToBeSelected)) {
			 * Keyword.ReportStep_Pass(testCase,
			 * "Select Location From DashBoard : Successfully selected location : " +
			 * locationToBeSelected); } else { flag = false;
			 * Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
			 * "Select Location From DashBoard : Failed to select location : " +
			 * locationToBeSelected); } } } } else {
			 */
			if (element.getText().equalsIgnoreCase(locationToBeSelected)) {
				Keyword.ReportStep_Pass(testCase,
						"Select Location From DashBoard : User is already in location : " + locationToBeSelected);
			} else {
				boolean f = false;
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDropDownButton");
				List<WebElement> locNames = MobileUtils.getMobElements(objectDefinition, testCase,
						"LocationDropDownList");
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
			// }
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
					Keyword.ReportStep_Pass(testCase, "Device name matches on Dashboard");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device name was not matches in Dashboard");
				}
			}
		}

		if (this.areDeviceTempUpVisibleOnDashboard(10)) {
			flag = true;
			Keyword.ReportStep_Pass(testCase, "Stat Temp Stepper Up icon is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp Stepper Up icon is not displayed on Dashboard");
		}

		if (this.areDeviceTempDownVisibleOnDashboard(10)) {
			flag = true;
			Keyword.ReportStep_Pass(testCase, "Stat Temp Stepper Down icon is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp Stepper Down icon is not displayed on Dashboard");
		}

		if (this.areDeviceTempVisibleOnDashboard(10)) {
			flag = true;
			Keyword.ReportStep_Pass(testCase, "Stat Temp values is displayed on Dashboard");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stat Temp values icon is not displayed on Dashboard");
		}

		return flag;
	}

	public boolean isThermostatNameCorrectlyDisplayed(String expectedDeviceName, TestCaseInputs inputs) {
		String actualDeviceName = null;
		boolean flag = true;
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatName");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			actualDeviceName = ele.getText();
		} else {
			actualDeviceName = ele.getAttribute("value");
		}
		if (expectedDeviceName.equalsIgnoreCase(actualDeviceName)) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isTemperatureNotDisplayed() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "TemperatureInOffModeForIOS");
		if (status.equals("--")) {
			return true;
		}
		return false;
	}

	public boolean isOffStatusVisible(TestCaseInputs inputs) {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			String status = MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatTemperature");
			if (status.toUpperCase().contains("OFF")) {
				return true;
			} else {
				return false;
			}
		} else {
			String thermostatname = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
			WebElement ele = MobileUtils.getMobElement(testCase, "xpath",
					"//XCUIElementTypeStaticText[@name='subTitle_" + thermostatname + "']");
			String status = ele.getText();
			if (status.toUpperCase().contains("OFF")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isThermostatTemperatureDisplayed(TestCaseInputs inputs) {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String chilDeviceIndoorTemperature = statInfo.getIndoorTemperature();
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			String actualValue = MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatTemperature");
			if (actualValue.contains("--") == false) {
				if (actualValue.contains(chilDeviceIndoorTemperature) && actualValue.toUpperCase().contains("INSIDE")) {
					System.out.println("Temperature is  given ");
					return flag;
				} else {
					flag = false;
				}
			} else {
				String thermostatname = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
				WebElement ele = MobileUtils.getMobElement(testCase, "xpath",
						"//XCUIElementTypeStaticText[@name='subTitle_" + thermostatname + "']");
				actualValue = ele.getText();
				if (actualValue.contains(chilDeviceIndoorTemperature) && actualValue.toUpperCase().contains("INSIDE")) {
					System.out.println("Temperature is  given ");
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isUserExpectedTemperatureDisplayed() {

		String actualValue = MobileUtils.getFieldValue(objectDefinition, testCase, "UserExpectedTemperature");

		if (actualValue.contains("--") == false) {
			System.out.println("Temperature is  given ");
			return true;
		} else {
			return false;
		}
	}

	public boolean NavigatetoThermostatDashboard() {
		if (MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatName")) {
			CoachMarkUtils.closeCoachMarks(testCase);
			return true;
		}
		return false;
	}

	public boolean isUpStepperDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpStepper", 8);
	}

	public boolean isDownStepperDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DownStepper", 8);
	}

	public boolean isUPStepperElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "UpStepper");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isDownStepperElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "DownStepper");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isMaxTemperatureVisibleOnDashBoard(TestCaseInputs inputs) {
		String currentSetPoint = "";
		int maxSetPointInt = 0;
		float maxSetPointFloat = 0;
		String maxSetPoint = "";
		boolean systemIsCelsius = false;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String currentSystemMode = statInfo.getThermoStatMode();
		HashMap<String, String> setPoints = new HashMap<String, String>();
		try {
			setPoints = statInfo.getDeviceMaxMinSetPoints();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (currentSystemMode.toUpperCase().contains("HEAT")) {
			maxSetPoint = setPoints.get("MaxHeat");
		} else if (currentSystemMode.toUpperCase().contains("COOL")) {
			maxSetPoint = setPoints.get("MaxCool");
		}
		Keyword.ReportStep_Pass(testCase, "Max temp from stat before conversion is " + maxSetPoint);
		if (maxSetPoint.contains(".")) {
			systemIsCelsius = true;
			maxSetPointFloat = Float.parseFloat(maxSetPoint);
		} else {
			maxSetPointInt = (int) Float.parseFloat(maxSetPoint);
		}
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "UserExpectedTemperature");
		currentSetPoint = ele.getText();
		Keyword.ReportStep_Pass(testCase, "Max temp from display before conversion is " + currentSetPoint);
		if (systemIsCelsius == false) {
			if (maxSetPointInt == (Integer.parseInt(currentSetPoint))) {
				return true;
			}
		} else {
			if (maxSetPointFloat == (Float.parseFloat(currentSetPoint))) {
				return true;
			}
		}
		return false;
	}

	public boolean isMinTemperatureVisibleOnDashBoard(TestCaseInputs inputs) {
		String currentSetPoint = "";
		int minSetPointInt = 0;
		float minSetPointFloat = 0;
		String minSetPoint = "";
		boolean systemIsCelsius = false;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String currentSystemMode = statInfo.getThermoStatMode();
		HashMap<String, String> setPoints = new HashMap<String, String>();
		try {
			setPoints = statInfo.getDeviceMaxMinSetPoints();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (currentSystemMode.toUpperCase().contains("HEAT")) {
			minSetPoint = setPoints.get("MinHeat");
		} else if (currentSystemMode.toUpperCase().contains("COOL")) {
			minSetPoint = setPoints.get("MinCool");
		}
		if (minSetPoint.contains(".")) {
			systemIsCelsius = true;
			minSetPointFloat = Float.parseFloat(minSetPoint);
		} else {
			minSetPointInt = (int) Float.parseFloat(minSetPoint);
		}
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "UserExpectedTemperature");
		currentSetPoint = ele.getText();

		if (systemIsCelsius == false) {
			if (minSetPointInt == (Integer.parseInt(currentSetPoint))) {
				return true;
			}
		} else {
			if (minSetPointFloat == (Float.parseFloat(currentSetPoint))) {
				return true;
			}
		}
		return false;
	}

	public boolean isDrEventLabelVisibleOnDashboard() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardDrAlert", 3, false);
	}

	public boolean NavigatetoThermostatPrimarycard() {
		if (MobileUtils.clickOnElement(objectDefinition, testCase, "ThermostatName")) {
			return true;
		}
		return false;
	}

	public boolean clickOnUpStepper() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UpStepper");
	}

	public boolean clickOnDownStepper() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DownStepper");
	}

	public boolean isHeatingTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatingText", 3, false);
	}

	public boolean isCoolingTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolingText", 3, false);
	}

	public boolean isHeatingCoolingTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatingCoolingText", 3, false);
	}

	public String getSetPoint() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "DeviceCurrentTempValue").getText();
		} else {
			return MobileUtils.getMobElement(objectDefinition, testCase, "DeviceCurrentTempValue")
					.getAttribute("value");
		}
	}

	public static double getCurrentSetPointInDashboard(TestCases testCase) {
		try {
			// =========================================Get current SetPoint
			// value in Dialer============================================
			String setpoint = "";
			Dashboard TPC = new Dashboard(testCase);
			if (TPC.areDeviceTempVisibleOnDashboard(10)) {
				setpoint = TPC.getSetPoint();
			}
			String currentThermostatTemp = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				currentThermostatTemp = setpoint;
				currentThermostatTemp = currentThermostatTemp.split("\\,")[0];
			} else {
				currentThermostatTemp = setpoint;
			}
			return Double.parseDouble(currentThermostatTemp);
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
			return -1;
		}
	}

	public boolean isWeatherIconDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardWeatherForecast");
	}
    
    public MobileElement getWeatherIconMobileElement() {
        return MobileUtils.getMobElement(objectDefinition, testCase, "DashboardWeatherForecast");
    }

	public boolean isDashboardWeatherForecastDisplayed(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardWeatherForecast", timeOut);
	}

	public String getWeatherTempValue() {
		String weatherValue = MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardWeatherForecastValue");
		return weatherValue.split("˚")[0];
	}
    
    public String getWeatherDetail() {
        String weatherValue = MobileUtils.getFieldValue(objectDefinition, testCase, "DashboardWeatherForecastValue");
        return weatherValue.split("˚")[1];
    }

	public boolean clickOnWeatherTempValue() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DashboardWeatherForecast");
	}

	public boolean isDashboardMenuVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DashboardMenu");
	}

	public boolean clickOnDashboardMenu() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DashboardMenu");
	}

	public boolean clickOnLocationDropdown() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDropdown");
	}

	public boolean isLocationNameDisplayedInDashboardScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationName");
	}

	public String getLocationNameDisplayedInDashboardScreen() {
		String locationName = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationName")) {
			locationName = MobileUtils.getFieldValue(objectDefinition, testCase, "LocationName");
		}
		return locationName;
	}

	public String getWeatherTempStatus() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String weatherValue = MobileUtils.getFieldValue(objectDefinition, testCase,
					"DashboardWeatherForecastValue");
			return weatherValue.split("˚")[1];
		} else {
			// ios
			String weatherValue = MobileUtils.getMobElement(objectDefinition, testCase, "DashboardWeatherForecastValue")
					.getAttribute("label");
			return weatherValue.split("˚")[1];
		}
	}
	
	public boolean closeLocationDropDownList() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			touchAction.tap(tapOptions().withPosition(point(width / 2, height / 2))).perform();
			
		} else {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationDropDownButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDropDownButton");
		}
		}
		return flag;
	
	}

	public boolean selectLocationDropDownArrow() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationDropDownButton")) {
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDropDownButton");
		}
		return flag;
	}

	public boolean isLocationListDisplayedWhenClickedOnLocationDropDownArrow() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase,
					"LocationListWhenClickedOnLocationDropDownArrow");
		} else {
			return MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name='Navigation_Drop_Down_Title_cell']", testCase);
		}
	}
	
	public WebElement getLocationListViewInDashboardScreenScrollableEleInAndroid() {
		WebElement locationListViewInDashboardScreenInAndroid = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase,
				"LocationListViewInDashboardScreenScrollableInAndroid")) {
			locationListViewInDashboardScreenInAndroid = MobileUtils.getMobElement(objectDefinition, testCase,
					"LocationListViewInDashboardScreenScrollableInAndroid");
		}
		return locationListViewInDashboardScreenInAndroid;
	}
	
	public boolean isLocationListViewInDashboardScreenScrollableInAndroid() {
		return MobileUtils
				.getMobElement(objectDefinition, testCase, "LocationListViewInDashboardScreenScrollableInAndroid")
				.getAttribute("scrollable").equalsIgnoreCase("true");
	}

	public List<String> getLocationNamesDisplayedWhenClickedOnLocationDropDownArrow(int expectedLocationsCount) {
		CustomDriver driver = testCase.getMobileDriver();
		List<String> locationNames = new ArrayList<String>();
		String locationName = null;
		List<WebElement> locationNameCellsElementsInAndroid = new ArrayList<WebElement>();
		List<MobileElement> locationNameCellsElementsIniOS = new ArrayList<MobileElement>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			locationNameCellsElementsInAndroid = MobileUtils.getMobElements(objectDefinition, testCase,
					"LocationListWhenClickedOnLocationDropDownArrow");
			
			if (locationNameCellsElementsInAndroid.size() != expectedLocationsCount
					&& this.isLocationListViewInDashboardScreenScrollableInAndroid()) {
				Keyword.ReportStep_Pass(testCase,
						"Fetching the locations in Locations dropdown list before Scrolling the list");

				for (WebElement ele : locationNameCellsElementsInAndroid) {
					System.out.println("####ele.getText(): " + ele.getText());
					locationName = ele.getText();
					if (!locationNames.contains(locationName) && locationName != null) {
						locationNames.add(locationName);
					}
				}

				Keyword.ReportStep_Pass(testCase, "Need to scroll down the list and fetch all the locations");

				Dimension dimensions = MobileUtils.getMobElement(objectDefinition, testCase,
						"LocationListViewInDashboardScreenScrollableInAndroid").getSize();
				int startx = dimensions.width;
				int starty = (dimensions.height * 75) / 100;
				int endx = dimensions.width;
				int endy = (dimensions.height * 25) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);

				locationNameCellsElementsInAndroid = MobileUtils.getMobElements(objectDefinition, testCase,
						"LocationListWhenClickedOnLocationDropDownArrow");

				for (WebElement ele : locationNameCellsElementsInAndroid) {
					System.out.println("####ele.getText(): " + ele.getText());
					locationName = ele.getText();
					if (!locationNames.contains(locationName) && locationName != null) {
						locationNames.add(locationName);
					}
				}

				if (locationNames.size() == (expectedLocationsCount - 1)) {
					Keyword.ReportStep_Pass(testCase, "Fetched all the locations from Locations dropdown list");
					Keyword.ReportStep_Pass(testCase, "Scroll top in the Locations dropdown list");
					testCase.getMobileDriver().swipe(startx, endy, endx, starty, 1000);
				}

			}
		} else {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			locationNameCellsElementsIniOS = driver
					.findElements(By.xpath("//XCUIElementTypeCell[@name='Navigation_Drop_Down_Title_cell']"));
			
			for (int i = 0; i < locationNameCellsElementsIniOS.size(); i++) {
				locationName = driver
						.findElement(By.xpath("//XCUIElementTypeCell[" + (i + 1)
								+ "]/XCUIElementTypeStaticText[@name='Navigation_Drop_Down_Title_title'][1]"))
						.getAttribute("value");
				if (!locationNames.contains(locationName) && locationName != null) {
					locationNames.add(locationName);
				} else {
					
					locationName = driver
							.findElement(By.xpath("//XCUIElementTypeCell[" + (i + 1)
									+ "]/XCUIElementTypeStaticText[@name='Navigation_Drop_Down_Title_title'][1]"))
							.getAttribute("value");
					if (!locationNames.contains(locationName)) {
						locationNames.add(locationName);
					} else {
						
						locationName = driver
								.findElement(By.xpath("//XCUIElementTypeCell[" + (i + 1)
										+ "]/XCUIElementTypeStaticText[@name='Navigation_Drop_Down_Title_title'][1]"))
								.getAttribute("value");
						if (!locationNames.contains(locationName)) {
							locationNames.add(locationName);
						}
					}
				}
			}
		}
		return locationNames;
	}
	
	public boolean selectLocationFromDropDownListInDashBoardScreen(TestCases testCase, String locationToBeSelected) {
		boolean flag = true;
		WebElement element = null;
		String defaultLocationDisplayed = VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin.DEFAULTLOCATIONFROMCHIL;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[@resource-id='com.honeywell.android.lyric:id/toolbar_spinner_item_dropdown_title' and @text='"
							+ locationToBeSelected + "']",
					testCase)) {
				element = MobileUtils.getMobElement(testCase, "XPATH",
						"//*[@resource-id='com.honeywell.android.lyric:id/toolbar_spinner_item_dropdown_title' and @text='"
								+ locationToBeSelected + "']");
				if (element != null) {
					if (element.getText().equalsIgnoreCase(defaultLocationDisplayed)) {
						Keyword.ReportStep_Pass(testCase,
								"Select Location From DashBoard : User is already in location : "
										+ locationToBeSelected);
					} else {
						element.click();
						Keyword.ReportStep_Pass(testCase,
								"Select Location From DashBoard : Successfully selected location : "
										+ locationToBeSelected);
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "LocationDropDownButton")) {
				element = MobileUtils.getMobElement(objectDefinition, testCase, "LocationName");
				if (element != null) {
					if (element.getText().equalsIgnoreCase(locationToBeSelected)) {
						Keyword.ReportStep_Pass(testCase,
								"Select Location From DashBoard : User is already in location : "
										+ locationToBeSelected);
					} else {
						boolean f = false;
						flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "LocationDropDownButton");
						List<WebElement> locNames = MobileUtils.getMobElements(objectDefinition, testCase,
								"LocationDropDownList");
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
									"Select Location From DashBoard : Failed to select location : "
											+ locationToBeSelected);
						}
					}
				}
			}
		}
		return flag;
	}
	
	public MobileElement getDeviceEleInDashboardScreen() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "DeviceEleInDashboard");
	}
	
	public List<String> getDevicesListDisplayedInDashboardScreen() {
		String deviceName = null;
		List<WebElement> devicesListWebElements = new ArrayList<WebElement>();
		List<String> deviceNames = new ArrayList<String>();
		devicesListWebElements = MobileUtils.getMobElements(objectDefinition, testCase, "DevicesList");
		for (int i = 0; i < devicesListWebElements.size(); i ++) {
			deviceName = devicesListWebElements.get(i).getText();
			if (!deviceNames.contains(deviceName) && deviceName != null) {
				deviceNames.add(deviceName);
			}
		}
		int counter = 0;
		while (!this.isAddDeviceIconVisible(1) && counter < 4) {
			// Scroll down to view the remaining list of devices
			LyricUtils.scrollUpAList(testCase, this.getDeviceEleInDashboardScreen());
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			devicesListWebElements = MobileUtils.getMobElements(objectDefinition, testCase, "DevicesList");
			for (int i = 0; i < devicesListWebElements.size(); i ++) {
				deviceName = devicesListWebElements.get(i).getText();
				if (!deviceNames.contains(deviceName) && deviceName != null) {
					deviceNames.add(deviceName);
				}
			}
			counter++;
		}
		return deviceNames;
	}
    
    public String getDashboardMaxTemp() {
        String maxWeather = MobileUtils.getFieldValue(objectDefinition, testCase, "WeatherMaxTemp");
        return maxWeather.split("˚")[0];
    }
    
    public String getDashboardMinTemp() {
        String minWeather = MobileUtils.getFieldValue(objectDefinition, testCase, "WeatherMinTemp");
        return minWeather.split("˚")[0];
    }
    
    public boolean isWeatherMaxTempDisplayed() {
        return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherMaxTemp");
    }
    
    public boolean isWeatherMinTempDisplayed() {
        return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherMinTemp");
    }
    
    public boolean isTodaysForecastDisplayed() {
        return MobileUtils.isMobElementExists(objectDefinition, testCase, "TodaysForecastLabel");
    }
    
    public boolean isPageIndicatorDisplayed() {
        return MobileUtils.isMobElementExists(objectDefinition, testCase, "PageIndicator");
    }
    
    public WebElement getDashboardWeatherStatusArea() {
        WebElement weatherStatusArea=MobileUtils.getMobElement(objectDefinition, testCase, "DashboardWeatherStatusArea");
        return weatherStatusArea;
    }
    public boolean isDashboardUpdateInProgressVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateInProgress");
	}
    public boolean verifystate(String stateToVerify) {
		String currentPanelState = MobileUtils.getFieldValue(objectDefinition, testCase, "SecurityTitleInDashboardScreen");
		switch (stateToVerify.toUpperCase()) {
		case "HOME": {
			if (currentPanelState.contains(stateToVerify)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}
		}
		case "AWAY": {
			if  (currentPanelState.contains(stateToVerify)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		case "NIGHT": {
			if  (currentPanelState.contains(stateToVerify)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		case "OFF": {
			if  (currentPanelState.contains(stateToVerify)) {
				Keyword.ReportStep_Pass(testCase, "Panel is in Expected state" + currentPanelState);
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Panel is not in expected state");
			}

		}
		default: {
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "input not handled");
		}

		}
		return false;
	}
}
