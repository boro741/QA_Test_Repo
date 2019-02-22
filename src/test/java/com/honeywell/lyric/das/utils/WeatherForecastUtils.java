package com.honeywell.lyric.das.utils;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSetPoint;

public class WeatherForecastUtils {
	static boolean flag = true;

	public static boolean compareWeatherForecastInDashboardWithCHIL(TestCases testCase, TestCaseInputs inputs,
			String WeatherTempValue) {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			long locationID;
			double weatherTemperatureFromCHILInCelsius, weatherTemperatureFromCHILInFarenheit,
					weatherTemperatureDisplayedInTheApp;
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				System.out.println(locationID);
				weatherTemperatureFromCHILInCelsius = Double.parseDouble(chUtil.getWeather(locationID));
				System.out.println("########weatherTemperatureFromCHIL: " + weatherTemperatureFromCHILInCelsius);
				weatherTemperatureDisplayedInTheApp = Double.parseDouble(WeatherTempValue);
				if (!WeatherTempValue.contains(".")) {
					weatherTemperatureFromCHILInFarenheit = Double
							.parseDouble(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase,
									String.valueOf(weatherTemperatureFromCHILInCelsius)));
					if ((Double.compare(weatherTemperatureFromCHILInFarenheit,
							weatherTemperatureDisplayedInTheApp) == 0)
							|| (Double.compare(weatherTemperatureFromCHILInFarenheit,
									weatherTemperatureDisplayedInTheApp) >= 2)
							|| (Double.compare(weatherTemperatureFromCHILInFarenheit,
									weatherTemperatureDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase,
								"Weather displayed in app is in Farenheit Unit" + weatherTemperatureDisplayedInTheApp);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Weather displayed in app: "
								+ weatherTemperatureDisplayedInTheApp + "is not in Farenheit Unit");
					}
				} else {
					if ((Double.compare(weatherTemperatureFromCHILInCelsius, weatherTemperatureDisplayedInTheApp) == 0)
							|| (Double.compare(weatherTemperatureFromCHILInCelsius,
									weatherTemperatureDisplayedInTheApp) >= 2)
							|| (Double.compare(weatherTemperatureFromCHILInCelsius,
									weatherTemperatureDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase,
								"Weather displayed in app is same as the Weather Temperature in CHIL");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Weather displayed in app: " + weatherTemperatureDisplayedInTheApp
										+ "is not same as the weather displayed in CHIL"
										+ weatherTemperatureFromCHILInCelsius);
					}
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to connect to CHIL");
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean compareMaxTempWithCHIL(TestCases testCase, TestCaseInputs inputs, String getMaxWeather) {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			long locationID;
			double maxTempFromCHILInCelsius, maxTemperatureFromCHILInFarenheit, maxTempDisplayedInTheApp;
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				System.out.println(locationID);
				maxTempFromCHILInCelsius = Double.parseDouble(chUtil.getMaxTemperature(locationID));
				System.out.println("########MaxTemperatureFromCHILInCelsius: " + maxTempFromCHILInCelsius);
				maxTempDisplayedInTheApp = Double.parseDouble(getMaxWeather);
				if (!getMaxWeather.contains(".")) {
					maxTemperatureFromCHILInFarenheit = Double.parseDouble(JasperSetPoint
							.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(maxTempFromCHILInCelsius)));
					if ((Double.compare(maxTemperatureFromCHILInFarenheit, maxTempDisplayedInTheApp) == 0)
							|| (Double.compare(maxTemperatureFromCHILInFarenheit, maxTempDisplayedInTheApp) >= 2)
							|| (Double.compare(maxTemperatureFromCHILInFarenheit, maxTempDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase, "Max Temperature displayed in app is in Farenheit Unit "
								+ maxTemperatureFromCHILInFarenheit);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Max Temperature displayed in app: " + maxTempDisplayedInTheApp
										+ "is not in Farenheit Unit");
					}
				} else {
					if ((Double.compare(maxTempFromCHILInCelsius, maxTempDisplayedInTheApp) == 0)
							|| (Double.compare(maxTempFromCHILInCelsius, maxTempDisplayedInTheApp) >= 2)
							|| (Double.compare(maxTempFromCHILInCelsius, maxTempDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase, "Max Temprature displayed in app" + maxTempDisplayedInTheApp
								+ "is same as the Max Temperature in CHIL in Celsius" + maxTempFromCHILInCelsius);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Max Temperature displayed in app: " + maxTempDisplayedInTheApp
										+ "is not same as the Max Temperature displayed in CHIL"
										+ maxTempFromCHILInCelsius);
					}
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to connect to CHIL");
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean compareMinTempWithCHIL(TestCases testCase, TestCaseInputs inputs, String getMinWeather) {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			long locationID;
			double minTempFromCHILInCelsius, minTemperatureFromCHILInFarenheit, minTempDisplayedInTheApp;
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				System.out.println(locationID);
				minTempFromCHILInCelsius = Double.parseDouble(chUtil.getMinTemperature(locationID));
				System.out.println("########MinTemperatureFromCHILInCelsius: " + minTempFromCHILInCelsius);
				minTempDisplayedInTheApp = Double.parseDouble(getMinWeather);
				if (!getMinWeather.contains(".")) {
					minTemperatureFromCHILInFarenheit = Double.parseDouble(JasperSetPoint
							.convertFromCelsiusToFahrenhiet(testCase, String.valueOf(minTempFromCHILInCelsius)));
					if ((Double.compare(minTemperatureFromCHILInFarenheit, minTempDisplayedInTheApp) == 0)
							|| (Double.compare(minTemperatureFromCHILInFarenheit, minTempDisplayedInTheApp) >= 2)
							|| (Double.compare(minTemperatureFromCHILInFarenheit, minTempDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase, "Min Temperature displayed in app is in Farenheit Unit "
								+ minTemperatureFromCHILInFarenheit);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Min Temperature displayed in app: " + minTempDisplayedInTheApp
										+ "is not in Farenheit Unit");
					}
				} else {
					if ((Double.compare(minTempFromCHILInCelsius, minTempDisplayedInTheApp) == 0)
							|| (Double.compare(minTempFromCHILInCelsius, minTempDisplayedInTheApp) >= 2)
							|| (Double.compare(minTempFromCHILInCelsius, minTempDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase, "Min Temprature displayed in app " + minTempDisplayedInTheApp
								+ "is same as the Max Temperature in CHILin Celsius " + minTempFromCHILInCelsius);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Min Temperature displayed in app: " + minTempDisplayedInTheApp
										+ "is not same as the Min Temperature displayed in CHIL in Celsius"
										+ minTempFromCHILInCelsius);
					}
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to connect to CHIL");
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean compareWeatherForecastTempWithCHIL(TestCases testCase, TestCaseInputs inputs,
			String getWeatherForecastTemp) {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			long locationID;
			double weatherForecastFromCHILInCelsius, weatherForecastFromCHILInFarenheit,
					weatherForecastDisplayedInTheApp;
			if (chUtil.getConnection()) {
				locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				System.out.println(locationID);
				weatherForecastFromCHILInCelsius = Double.parseDouble(chUtil.getWeather(locationID));
				System.out.println(
						"########WeatherForecastTemperatureFromCHILInCelsius: " + weatherForecastFromCHILInCelsius);
				weatherForecastDisplayedInTheApp = Double.parseDouble(getWeatherForecastTemp);
				if (!getWeatherForecastTemp.contains(".")) {
					weatherForecastFromCHILInFarenheit = Double
							.parseDouble(JasperSetPoint.convertFromCelsiusToFahrenhiet(testCase,
									String.valueOf(weatherForecastFromCHILInCelsius)));
					if ((Double.compare(weatherForecastFromCHILInFarenheit, weatherForecastDisplayedInTheApp) == 0)
							|| (Double.compare(weatherForecastFromCHILInFarenheit,
									weatherForecastDisplayedInTheApp) >= 2)
							|| (Double.compare(weatherForecastFromCHILInFarenheit,
									weatherForecastDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase,
								"Weather Forecast Temperature displayed in app is in Farenheit Unit "
										+ weatherForecastFromCHILInFarenheit);
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Weather Forecast Temperature displayed in app: " + weatherForecastDisplayedInTheApp
										+ "is not in Farenheit Unit");
					}
				} else {
					if ((Double.compare(weatherForecastFromCHILInCelsius, weatherForecastDisplayedInTheApp) == 0)
							|| (Double.compare(weatherForecastFromCHILInCelsius, weatherForecastDisplayedInTheApp) >= 2)
							|| (Double.compare(weatherForecastFromCHILInCelsius,
									weatherForecastDisplayedInTheApp) <= 2)) {
						Keyword.ReportStep_Pass(testCase,
								"Weather Forecast Temprature displayed in app is same as the Max Temperature in CHIL");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Weather Forecast Temperature displayed in app: " + weatherForecastDisplayedInTheApp
										+ "is not same as the Max Temperature displayed in CHIL"
										+ weatherForecastFromCHILInCelsius);
					}
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to connect to CHIL");
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}
}