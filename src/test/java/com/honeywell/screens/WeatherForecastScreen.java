package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class WeatherForecastScreen extends MobileScreens {

	private static final String screenName = "Weather";

	public WeatherForecastScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public String whichWeatherTempUnitIsEnabled() {
		String tempUnit = null;
		if (MobileUtils.getMobElement(objectDefinition, testCase, "CelsiusUnit").getAttribute("enabled")
				.equalsIgnoreCase("true")) {
			tempUnit = MobileUtils.getFieldValue(objectDefinition, testCase, "CelsiusUnit");
		} else if (MobileUtils.getMobElement(objectDefinition, testCase, "FarenheitUnit").getAttribute("enabled")
				.equalsIgnoreCase("true")) {
			tempUnit = MobileUtils.getFieldValue(objectDefinition, testCase, "FarenheitUnit");
		}
		return tempUnit;
	}

	public String whichWeatherTempUnitIsDisabled() {
		String tempUnit = null;
		if (MobileUtils.getMobElement(objectDefinition, testCase, "CelsiusUnit").getAttribute("enabled")
				.equalsIgnoreCase("false")) {
			tempUnit = MobileUtils.getFieldValue(objectDefinition, testCase, "CelsiusUnit");
		} else if (MobileUtils.getMobElement(objectDefinition, testCase, "FarenheitUnit").getAttribute("enabled")
				.equalsIgnoreCase("false")) {
			tempUnit = MobileUtils.getFieldValue(objectDefinition, testCase, "FarenheitUnit");
		}
		return tempUnit;
	}

	public boolean isWeatherScreenTitleDisplayed(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherScreenTitle", timeOut);
	}

	public boolean clickOnBackIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackIcon");
	}

	public boolean clickOnCelsiusUnit() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CelsiusUnit");
	}

	public boolean isCelsiusUnitDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CelsiusUnit");
	}

	public boolean isFarenheitUnitDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FarenheitUnit");
	}

	public boolean clickOnFarenheitUnit() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FarenheitUnit");
	}

	public boolean isCurrentUnitOfFarenheitDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CurrentFarenheitUnit");
	}

	public boolean isCurrentUnitOfCelsiusDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CurrentCelsiusUnit");
	}

	public boolean isForecastLeftTimeDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ForecastLeftTime");
	}

	public boolean isForecastCenterTimeDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ForecastCenterTime");
	}

	public boolean isForecastRightTimeDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ForecastRightTime");
	}

	public String getForecastLeftTimeValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ForecastLeftTime");
	}

	public String getForecastCenterTimeValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ForecastCenterTime");
	}

	public String getForecastRightTimeValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "ForecastRightTime");
	}

	public boolean isHumidityDisplayed(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Humidity", timeOut);
	}

	public String getHumidity() {
		String humidityValue = null;
		String humidityPercentage = MobileUtils.getFieldValue(objectDefinition, testCase, "Humidity");
		System.out.println("Humidity in app" + humidityPercentage);
		String humidityPerc = humidityPercentage.split("%")[0];
		if (humidityPerc.contains("HUMIDITY")) {
			humidityValue = humidityPerc.split("HUMIDITY")[1].trim();
			return humidityValue;
		} else {
			return humidityPerc;
		}
	}

	public boolean isWeatherMaxTempDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherMaxTemp");
	}

	public boolean isWeatherMinTempDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherMinTemp");
	}

	public String getWeatherMaxTemp() {
		String maxWeather = MobileUtils.getFieldValue(objectDefinition, testCase, "WeatherMaxTemp");
		System.out.println("Max temp in app: " + maxWeather);
		return maxWeather.split("˚")[0];

	}

	public String getWeatherMinTemp() {
		String minWeather = MobileUtils.getFieldValue(objectDefinition, testCase, "WeatherMinTemp");
		System.out.println("Min temp in app: " + minWeather);
		return minWeather.split("˚")[0];
	}

	public boolean isWeatherForecastValueDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WeatherForecastValue");
	}

	public String getWeatherForecastValue() {
		return MobileUtils.getFieldValue(objectDefinition, testCase, "WeatherForecastValue");
	}

	public String getWeatherForecastStatus() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "WeatherForecastStatus").getAttribute("text");
		} else {
			// ios
			return MobileUtils.getMobElement(objectDefinition, testCase, "WeatherForecastStatus").getAttribute("value")
					.toLowerCase();
		}
	}
}