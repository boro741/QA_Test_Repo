package com.honeywell.screens;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

import io.appium.java_client.MobileElement;

public class PrimaryCard extends MobileScreens {

	private static final String screenName = "PrimaryCard";

	public PrimaryCard(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCogIconVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CogIcon", 5);
	}

	public boolean clickOnCogIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CogIcon");
	}

	public boolean isBackButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButton", 3);
	}

	public boolean clickOnBackButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
		}
	
	public boolean isThermostatCurrentTemperatureVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}

	public String getThermostatCurrentTemperatureValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ThermostatCurrentTemperature");
	}

	public boolean isThermostatCurrentHumidityValueVisible(TestCaseInputs inputs, int timeOut) {
		boolean flag = true;
		String humidityPercentageInPrimaryCardScreen = null;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidityValueInPrimaryCardScreen", 30)) {
			humidityPercentageInPrimaryCardScreen = MobileUtils
					.getMobElement(objectDefinition, testCase, "HumidityValueInPrimaryCardScreen").getText();
			if (humidityPercentageInPrimaryCardScreen.contains("%")) {
				humidityPercentageInPrimaryCardScreen = humidityPercentageInPrimaryCardScreen.split(" ")[1]
						.split("%")[0];
				if (!humidityPercentageInPrimaryCardScreen.equalsIgnoreCase("TO")) {
					inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_HUMIDITY_VALUE",
							humidityPercentageInPrimaryCardScreen);
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Humidity value is not present in Primary Card screen");
			}
		} else if (MobileUtils.isMobElementExists(objectDefinition, testCase,
				"HumidityValueFromTheScrollListInPrimaryCardScreen", 30)) {
			humidityPercentageInPrimaryCardScreen = MobileUtils
					.getMobElement(objectDefinition, testCase, "HumidityValueFromTheScrollListInPrimaryCardScreen")
					.getText();
			if (humidityPercentageInPrimaryCardScreen.contains("%")) {
				humidityPercentageInPrimaryCardScreen = humidityPercentageInPrimaryCardScreen.split(" ")[1]
						.split("%")[0];
				if (!humidityPercentageInPrimaryCardScreen.equalsIgnoreCase("TO")) {
					inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_HUMIDITY_VALUE",
							humidityPercentageInPrimaryCardScreen);
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Humidity value is not present in Primary Card screen");
			}
		}
		return flag;
	}

	public boolean isChangeFanScreenDisplayed() {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle");
		} else {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ChangeFanTitle");
			String value = element.getAttribute("value");
			System.out.println(value);
			if (value.toUpperCase().contains("CHANGE FAN")) {
				return true;
			}
		}
		return false;
	}

	public boolean clickOnInfoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "InfoButton");
	}

	public boolean isThermostatSolutionCardDisplayed() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton")
				|| MobileUtils.isMobElementExists(objectDefinition, testCase, "FanButton")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFanModeElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Fanmode");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean clickOnModeButton() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ModeButton");
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ModeIcon");
		}
	}

	public boolean clickOnFanButton() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "FanButton");
		} else {
			if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeButton[contains(@name,'Fan_Mode')]", testCase,
					10)) {
				if (MobileUtils.clickOnElement(testCase, "xpath",
						"//XCUIElementTypeButton[contains(@name,'Fan_Mode')]")) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isChangeModeScreenDisplayed() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
		} else {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ChangeModeTitle");
			String value = element.getAttribute("value");
			if (value.toUpperCase().contains("CHANGE MODE")) {
				return true;
			}
		}
		return false;
	}

	public boolean isModeInfoScreenDisplayed() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle")
					&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeModeTitle");
		} else {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "InformationTitle");
			String value = element.getAttribute("value");
			if (value.equals("Information_CHANGE MODE")) {
				return true;
			}
		}
		return false;
	}

	public boolean isFanInfoScreenDisplayed() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "InformationTitle")
					&& MobileUtils.isMobElementExists(objectDefinition, testCase, "ChangeFanTitle");
		} else {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "InformationTitle");
			String value = element.getAttribute("value");
			if (value.equals("Information_CHANGE FAN")) {
				return true;
			}
		}
		return false;
	}

	public boolean isAutoModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeDefinition", 5);
	}

	public boolean isHeatModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeDefinition", 5);
	}

	public boolean isCoolModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeDefinition", 5);
	}

	public boolean isHeatModeVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatMode")) {
			return flag;
		} else if (testCase.getMobileDriver().findElement(By.id("HEAT")).isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isCoolModeVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolMode")) {
			return flag;
		} else if (testCase.getMobileDriver().findElement(By.id("COOL")).isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;

	}

	public boolean isOffModeVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OffMode")) {
			return flag;
		} else if (testCase.getMobileDriver().findElement(By.id("OFF")).isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;

	}

	public boolean isAutoModeVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoMode")) {
			return flag;
		} else if (testCase.getMobileDriver().findElement(By.id("AUTO")).isEnabled()) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isSetModeDisplayed(TestCaseInputs inputs) {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String mode = statInfo.getThermoStatMode();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
		if (mode.equals("Cool")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Heat")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Off")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
			if (element.isSelected()) {
				return true;
			}
		}
		if (mode.equals("Auto")) {
			MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "AutoMode");
			if (element.isSelected()) {
				return true;
			}
		}
		} else {
			if (mode.equals("Cool")) {
				
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='COOL Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
			if (mode.equals("Heat")) {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='HEAT Selected']", testCase, false)){
					return true;
				}else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
			if (mode.equals("Off")) {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='OFF Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
			if (mode.equals("Auto")) {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='AUTO Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
			
		}
		return false;
	}

	public Boolean clickOnHeatButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatMode")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "HeatMode");
		} else {
			if (testCase.getMobileDriver().findElement(By.id("HEAT")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.name("HEAT")).click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public Boolean clickOnCoolButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolMode")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CoolMode");
		} else {
			if (testCase.getMobileDriver().findElement(By.id("COOL")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.name("COOL")).click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public Boolean clickOnOffButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OffMode")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "OffMode");
		} else {
			if (testCase.getMobileDriver().findElement(By.id("OFF")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.name("OFF")).click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public Boolean clickOnAutoButton() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoMode")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "AutoMode");
		} else {
			if (testCase.getMobileDriver().findElement(By.id("AUTO")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.name("AUTO")).click();
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isNewSetModeDisplayed(TestCaseInputs inputs) {
		String mode = inputs.getInputValue("SystemMode");
		if (mode.equals("COOL")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "CoolMode");
				if (element.isSelected()) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + mode + " is selected");
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			} else {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='COOL Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
		}
		if (mode.equals("HEAT")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "HeatMode");
				if (element.isSelected()) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + mode + " is selected");
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			} else {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='HEAT Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
		}
		if (mode.equals("OFF")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "OffMode");
				if (element.isSelected()) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + mode + " is selected");
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			} else {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='OFF Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
		}
		if (mode.equals("AUTO")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Auto");
				if (element.isSelected()) {
					Keyword.ReportStep_Pass(testCase, "Mode: " + mode + " is selected");
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			} else {
				if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='AUTO Selected']", testCase, false)){
					return true;
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Mode: " + mode + " is unselected");
					return false;
				}
			}
		}
		return false;
	}

	public boolean isAutoDefinitionVisible() {
		boolean flag = true;

				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoModeDescription")) {
					return flag;
				} else {
					flag = false;
				}
		return flag;
	}

	public boolean isHeatDefinitionVisibleOnChangeModeScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "HeatModeDescription")) {
			return flag;
		} else {
			if (testCase.getMobileDriver().findElement(By.name("HEAT TO REACH TARGET TEMPERATURE")).isEnabled()) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isOffDefinitionVisibleOnChangeModeScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OffModeDescription")) {
			return flag;
		} else {
			if (testCase.getMobileDriver().findElement(By.name("COOL TO REACH TARGET TEMPERATURE")).isEnabled()) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isCoolDefinitionVisibleOnChangeModeScreen() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CoolModeDescription")) {
			return flag;
		} else {
			if (testCase.getMobileDriver().findElement(By.name("TURN SYSTEM OFF")).isEnabled()) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean clickOnAutoFanButton() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoFanButton");
		} else {
			if (testCase.getMobileDriver().findElement(By.xpath("//*[@name='AUTO']")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@name='AUTO']")).click();
				return true;
			}
//			WebElement element = testCase.getMobileDriver().findElement(By.xpath("//*[@name='AUTO']"));
//			if (element != null) {
//				element.click();
//				return true;
//			}
			return false;
		}
	}

	public boolean isSystemOffModeDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SystemOffModeDefinition", 5);
	}

	public boolean isAutoFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinition", 5);
	}

	public boolean isCirculateFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinition", 5);
	}

	public boolean isOnFanDefinitionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinition", 5);
	}

	public boolean isModeElementEnabled() {
		boolean flag = true;
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton");
		if (element.isEnabled()) {
			return flag;
		} else {
			return false;
		}
	}

	public boolean isAutoFanDefinitionVisibleOnInfoScreen() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinitionOnInfoScreen")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinitionOnInfoScreen", 4);
		} else {
			return false;
		}
	}

	public boolean isCirculateFanDefinitionVisibleOnInfoScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinitionOnInfoScreen", 5);
	}

	public boolean isOnFanDefinitionVisibleOnInfoScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinitionOnInfoScreen", 5);
	}

	public boolean isScheduleElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ScheduleButton");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isOffStatusVisibleOnSolutionCard() {
		String status = MobileUtils.getFieldValue(objectDefinition, testCase, "ModeStatus");
		if (status.toUpperCase().contains("OFF")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isVacationStatusVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationStatus",timeout);

	}

	public boolean clickOnVacationStatus() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VacationStatus");

	}

	public boolean clickOnXButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "XButton");
	}

	public boolean isAutoFanElementSelected() {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "AutoFanButton");
			if (element.isSelected()) {
				return true;

			}
		} else {
			if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='AUTO Selected']", testCase, false)){
				return true;
			}
		}
		return false;
	}

	public boolean clickOnCirculateFanButton() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CirculateFanButton");
		} else {
			if (testCase.getMobileDriver().findElement(By.xpath("//*[@name='CIRCULATE']")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@name='CIRCULATE']")).click();
				return true;
			}
			return false;
		}
	}

	public boolean clickOnONFanButton() {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OnFanButton");
		} else {
			if (testCase.getMobileDriver().findElement(By.xpath("//*[@name='ON']")).isEnabled()) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@name='ON']")).click();
				return true;
			}
			return false;
		}
	}

	public boolean isCirculateFanElementSelected() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "CirculateFanButton");
			if (element.isSelected()) {
				return true;

			}
		} else {
			if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='CIRCULATE Selected']", testCase, false)){
				return true;
			}
		}
		return false;
	}

	public boolean isONFanElementSelected() {
		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "OnFanButton");
			if (element.isSelected()) {
				return true;

			}
		} else {
			if(MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='ON Selected']", testCase, false)){
				return true;
			}
		}
		return false;
	}

	public boolean isAutoFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoFanDefinitionOnChangeFanScreen", 5);
	}

	public boolean isCirculateFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CirculateFanDefinitionOnChangeFanScreen", 5);
	}

	public boolean isOnFanDefinitionVisibleOnChangeFanScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OnFanDefinitionOnChangeFanScreen", 5);

	}

	public boolean checkCurrentFanMode(String expectedValue) {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "FanButton");
			String value = element.getAttribute("name");
			if (expectedValue.contains(value.toUpperCase())) {
				return true;
			}
		} else {
			WebElement element = MobileUtils.getMobElement(testCase, "xpath",
					"//XCUIElementTypeButton[contains(@name,'Fan_Mode')]");
			String value = element.getText();
			if (value.toUpperCase().contains(expectedValue.toUpperCase())) {
				return true;
			}
		}
		return false;
	}

	public void clickOnEmergencyHeatButton() {
		WebElement element1 = MobileUtils.getMobElement(objectDefinition, testCase, "EmergencyHeatToggle");
		if (element1.getText().equalsIgnoreCase("off")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "EmergencyHeatToggle");
		} else {
			MobileUtils.clickOnElement(objectDefinition, testCase, "EmergencyHeatToggle");
		}

	}

	public boolean checkCurrentMode(String expectedValue) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton")) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton").getAttribute("name")
						.toUpperCase().contains(expectedValue)) {
					return flag;
				} else {
					flag = false;
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "Current displayed mode is "+MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton").getAttribute("value"));
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ModeButton").getAttribute("value")
						.toUpperCase().contains(expectedValue)) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean setMaxTemperatureByTappingUpStepper(TestCaseInputs inputs) {
		boolean flag = true;
		String maxSetPoint = "";
		float maxSetPointFloat = 0;
		int maxSetPointInt = 0;
		boolean systemIsCelsius = false;
		String currentSetPoint = "";
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
		Keyword.ReportStep_Pass(testCase, "Max temp from stat got is "+maxSetPoint);
		if (maxSetPoint.contains(".")) {
			systemIsCelsius = true;
			maxSetPointFloat = Float.parseFloat(maxSetPoint);
		} else {
			maxSetPointInt = (int) Float.parseFloat(maxSetPoint);
		}

		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "CurrentSetPoint");
		currentSetPoint = ele.getText();
		if (systemIsCelsius == false) {
			while (Integer.parseInt(currentSetPoint) < maxSetPointInt) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperUp");
				currentSetPoint = ele.getText();
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton") && MobileUtils
						.getFieldValue(objectDefinition, testCase, "ModeButton").equalsIgnoreCase(currentSetPoint)) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			while (Float.parseFloat(currentSetPoint) < maxSetPointFloat) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperUp");
				currentSetPoint = ele.getText();
			}
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean clickOnSaveButton() {

		if (testCase.getPlatform().toUpperCase().contains("IOS") == false) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveButton");
		} else {
			WebElement element = testCase.getMobileDriver().findElement(By.xpath("//*[@name='Save']"));
			if (element != null) {
				element.click();
				return true;
			}
			return false;
		}
	}

	public boolean isMaxTemperatureVisibleOnSolutionCard(TestCaseInputs inputs) {
		boolean flag = true;
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
		Keyword.ReportStep_Pass(testCase, "Max temp from stat before conversion is "+maxSetPoint);
		if (maxSetPoint.contains(".")) {
			systemIsCelsius = true;
			maxSetPointFloat = Float.parseFloat(maxSetPoint);
		} else {
			maxSetPointInt = (int) Float.parseFloat(maxSetPoint);
		}
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "CurrentSetPoint");
		currentSetPoint = ele.getText();
		Keyword.ReportStep_Pass(testCase, "Max temp from display before conversion is "+currentSetPoint);
		if (systemIsCelsius == false) {
			if (maxSetPointInt == (Integer.parseInt(currentSetPoint))) {
				return flag;
			}
		} else {
			if (maxSetPointFloat == (Float.parseFloat(currentSetPoint))) {
				return flag;
			}
		}
		Keyword.ReportStep_Pass(testCase, "Value in solution card is "+currentSetPoint);
		return flag;
	}

	public boolean setMinTemperatureByTappingDownStepper(TestCaseInputs inputs) {
		boolean flag = true;
		String minSetPoint = "";
		int minSetPointInt = 0;
		float minSetPointFloat = 0;
		boolean systemIsCelsius = false;
		String currentSetPoint = "";
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
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "CurrentSetPoint");
		currentSetPoint = ele.getText();
		if (systemIsCelsius == false) {
			while (Integer.parseInt(currentSetPoint) > minSetPointInt) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperDown");
				currentSetPoint = ele.getText();
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeButton") && MobileUtils
						.getFieldValue(objectDefinition, testCase, "ModeButton").equalsIgnoreCase(currentSetPoint)) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			while (Float.parseFloat(currentSetPoint) > minSetPointFloat) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperDown");
				currentSetPoint = ele.getText();
			}
		}
		//	else {
		//	WebElement element = testCase.getMobileDriver()
		//			.findElement(By.xpath("//*[@name='autoChangeOver_toggle']"));
		//	if (element != null) {
		//		String elementisOnorOff = element.getAttribute("value");
		//		if (elementisOnorOff.equals("0")) {
		//			return flag;
		//		} else {
		//			element.click();
		//			return flag;
		//		}
		//	}
		//}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean isMinTemperatureVisibleOnSolutionCard(TestCaseInputs inputs) {
		String currentSetPoint = "";
		int minSetPointInt = 0;
		String minSetPoint = "";
		boolean systemIsCelsius = false;
		float minSetPointFloat = 0;
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
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "CurrentSetPoint");
		currentSetPoint = ele.getText();
		if (systemIsCelsius == false) {
			if (minSetPointInt == (Integer.parseInt(currentSetPoint))) {
				return true;
			}
		} else {
			if (minSetPointFloat == (Float.parseFloat(currentSetPoint))) {
				return true;
			} else {
				WebElement element = testCase.getMobileDriver()
						.findElement(By.xpath("//*[@name='autoChangeOver_toggle']"));
				if (element != null) {
					String elementisOnorOff = element.getAttribute("value");
					if (elementisOnorOff.equals("1")) {
						return true;
					} else {
						element.click();
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean autoModeButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AutoMode");
		
	}

	public boolean disableAutoChangeOver(TestCaseInputs inputs) {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "AutoChangeOverToggle");
		String status = ele.getText();
		System.out.println(status);
		if (status.equals("OFF") || status.equals("0")) {
			return true;
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoChangeOverToggle");
		}
	}

	public boolean enableAutoChangeOver(TestCaseInputs inputs) {
		WebElement ele = MobileUtils.getMobElement(objectDefinition, testCase, "AutoChangeOverToggle");
		String status = ele.getText();
		System.out.println(status);
		if (status.equals("ON") || status.equals("1")) {
			return true;
		} else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "AutoChangeOverToggle");
		}
	}

	public boolean isFanButtonVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanButton", 8);
	}

	public boolean isEmergencyHeatOptionDisabled() {
		WebElement element1 = MobileUtils.getMobElement(objectDefinition, testCase, "EmergencyHeatToggle");
		if (element1.getText().equalsIgnoreCase("off")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmergencyHeatOptionNotDisplayed() {
		return (!MobileUtils.isMobElementExists(objectDefinition, testCase, "EmergencyHeatToggle", 5));
		
	}

	public boolean isUPStepperElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "StatTempStepperUp");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isDownStepperElementEnabled() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "StatTempStepperDown");
		if (element.isEnabled()) {
			return true;
		}
		return false;
	}

	public boolean isDownStepperVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperDown");
	}

	public boolean isUpStepperVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "StatTempStepperUp");
	}

	public boolean clickOnDownStepper(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperDown");
	}

	public boolean clickOnUpStepper() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "StatTempStepperUp");
	}

	public boolean isUserExpectedTemperatureDisplayed() {

		WebElement expectedTemp = MobileUtils.getMobElement(objectDefinition, testCase, "UserExpectedTemperature");

		String actualValue = expectedTemp.getText();
		if (actualValue.contains("--") == false) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSystemIsOffVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ModeStatus", timeOut);
	}

	public boolean isDrGreenLabelVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DrEventLabel");
	}

	public boolean clickOnDrEventLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DrEventLabel");
	}

	public boolean isNoScheduleTextAvailable() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NoSchedule");
	}
	public String getCurrentHeatingOrCoolingSetpointValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HeatingCoolingToValue");
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
	
	public boolean isEmergencyHeatStatusVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EmergencyHeatStatus");
	}
	
}
