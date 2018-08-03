package com.honeywell.jasper.utils;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.deviceCloudProviders.PCloudyExecutionDesiredCapability.PCloudyDeviceInformation;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.perfecto.PerfectoLabUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class JasperSetPoint {

	public static boolean rotateDialer(TestCases testCase,
			TestCaseInputs inputs, Double targetTemp) {
		HashMap<String, String> setPoints = new HashMap<String, String>();
		boolean flag = true;
		double currentTemp = 0, currentNewTemp = 0;
		String systemMode = "";
		Double maxHeat = 0.0;
		Double minHeat = 0.0;
		Double maxCool = 0.0;
		Double minCool = 0.0;
		Double maxSetPoint = 0.0;
		Double minSetPoint = 0.0;
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			setPoints = statInfo.getDeviceMaxMinSetPoints();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
				minHeat = Double.parseDouble(setPoints.get("MinHeat"));
				maxCool = Double.parseDouble(setPoints.get("MaxCool"));
				minCool = Double.parseDouble(setPoints.get("MinCool"));
			} else if (allowedModes.contains("Heat")
					&& !allowedModes.contains("Cool")) {
				maxHeat = Double.parseDouble(setPoints.get("MaxHeat"));
				minHeat = Double.parseDouble(setPoints.get("MinHeat"));
			} else if (!allowedModes.contains("Heat")
					&& allowedModes.contains("Cool")) {
				maxCool = Double.parseDouble(setPoints.get("MaxCool"));
				minCool = Double.parseDouble(setPoints.get("MinCool"));
			}
			systemMode = statInfo.getThermoStatMode();
			if (systemMode.equals("Auto")) {
				systemMode = statInfo
						.getThermostatModeWhenAutoChangeOverActive();
			}
			if (systemMode.equals("Cool")) {
				maxSetPoint = maxCool;
				minSetPoint = minCool;
			} else if (systemMode.equals("Heat")) {
				maxSetPoint = maxHeat;
				minSetPoint = minHeat;
			}
			if (targetTemp < minSetPoint || targetTemp > maxSetPoint) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Setpoint Stepper : Target Temp : " + targetTemp
								+ " is out of range" + maxSetPoint + "-"
								+ minSetPoint);
				return false;
			} else {
				FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
				currentTemp = getCurrentSetPointInDialer(testCase);
				if (currentTemp != targetTemp) {
					if (targetTemp > currentTemp) {
						double get = targetTemp - currentTemp;
						for (int i = 0; i <= get - 1; i++) {
							fly.ClickOnSetPointStepperUPButton();

						}
					} else if (targetTemp < currentTemp) {
						double get = (currentTemp - targetTemp);
						for (int i = 0; i <= get - 1; i++) {
							fly.clickOnSetPointSteppeDownButton();
						}
					}
					currentNewTemp = getCurrentSetPointInDialer(testCase);
					if (currentNewTemp == targetTemp) {
						Keyword.ReportStep_Pass(testCase,
								"Rotate Dialer : Successfully set dialer set points to : "
										+ currentTemp);
						return flag;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase,
								FailType.FUNCTIONAL_FAILURE,
								"Rotate Dialer : Failed to set dialer set points to: "
										+ targetTemp);
					}
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Rotate Dialer : Error Occured : " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	public static double getCurrentSetPointInDialer(TestCases testCase) {
		try {
			// =========================================Get current SetPoint
			// value in Dialer============================================
			String dialer = "";
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			if (fly.IsDailerButtonVisible(10)) {
				dialer = fly.getDailerValue();
			}
			String currentThermostatTemp = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				currentThermostatTemp = dialer;
				currentThermostatTemp = currentThermostatTemp.split(",")[1];
			} else {
				currentThermostatTemp = dialer;
			}
			return Double.parseDouble(currentThermostatTemp);
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
			return -1;
		}

	}

	public static boolean changeSystemMode(TestCases testCase,
			TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Create Schedule : Cannot change system mode because thermostat is offline");
				return true;
			}

			if (expectedMode.equals("Heat")) {
				fly.changeSystemModeToHeatMode();
			}

			else if (expectedMode.equals("Cool")) {
				fly.changeSystemModeToCoolMode();
			}

			else if (expectedMode.equals("Off")) {
				fly.changeSystemModeToOffMode();
			} else if (expectedMode.equals("Auto")) {
				fly.changeSystemModeToAutoMode();
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured : " + e.getMessage());

		}
		return flag;
	}

	public static boolean verifyDialerTemperature(TestCases testCase,
			TestCaseInputs inputs, Double expectedTemp) {
		boolean flag = true;
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
				testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(30, TimeUnit.SECONDS);
		try {
			Boolean isEventReceived = fWait
					.until(new Function<CustomDriver, Boolean>() {
						public Boolean apply(CustomDriver driver) {
							Double currentSetPoint = getCurrentSetPointInDialer(testCase);
							if (currentSetPoint - expectedTemp == 0.0) {
								Keyword.ReportStep_Pass(testCase,
										"Verify Dialer Set Points : Dialer set points is set to : "
												+ expectedTemp);
								return true;
							} else {
								return false;
							}
						}
					});
			flag = isEventReceived;
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Dialer Set Points : Dialer set points did not change to : "
							+ expectedTemp + " after waiting for 30 seconds");
		}
		return flag;
	}

	/**
	 * <p>
	 * The convertFromCelsiusToFahrenhiet method converts a temperature value
	 * from Celsius to Fahrenheit.
	 * </p>
	 * 
	 * @param TestCases
	 * @param String
	 *            - This is the Celsius temperature that has to be converted to
	 *            Fahrenheit.
	 * @return String - This is temperature value in Fahrenheit.
	 * 
	 * @author h119237 - Pratik Lalseta.
	 */
	public static String convertFromCelsiusToFahrenhiet(TestCases testCase,
			String celsiusTemp) {
		try {
			Double temp = Double.parseDouble(celsiusTemp);
			temp = (9.0 / 5.0) * temp + 32;
			return String.valueOf(temp.intValue());
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : e.getMessage");
			return " ";
		}
	}

	public static String getCurrentUTCTime(TestCases testCase) {
		String UTCTime = " ";
		try {
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			ZonedDateTime zone = ZonedDateTime.now(ZoneOffset.UTC);
			UTCTime = vacationDateFormat.format(vacationDateFormat.parse(zone
					.toString()));
		} catch (Exception e) {
			UTCTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Get Current UTC Time : Error Occured : " + e.getMessage());
		}
		return UTCTime;
	}

	public static String roundOffTimeToTheNearest15minutes(TestCases testCase,
			String time) {
		String roundOffTime = " ";
		try {
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(vacationDateFormat.parse(time));
			int minutes = c.get(Calendar.MINUTE);
			int mod = minutes % 15;
			c.add(Calendar.MINUTE, -mod);
			c.set(Calendar.SECOND, 0);
			roundOffTime = vacationDateFormat.format(c.getTime());
		} catch (Exception e) {
			roundOffTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return roundOffTime;
	}

	public static String roundOffTimeToTheNearest10minutes(TestCases testCase,
			String time) {
		String roundOffTime = " ";
		try {
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(vacationDateFormat.parse(time));
			int minutes = c.get(Calendar.MINUTE);
			int mod = minutes % 10;
			c.add(Calendar.MINUTE, -mod);
			c.set(Calendar.SECOND, 0);
			roundOffTime = vacationDateFormat.format(c.getTime());
		} catch (Exception e) {
			roundOffTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return roundOffTime;
	}

	public static String CalculatePeriodStartEMEA(TestCases testCase) {
		String formattedTime = " ";
		try {
			Calendar startTime = Calendar.getInstance();
			int minutes = startTime.get(Calendar.MINUTE);
			int mod = minutes % 10;
			startTime.add(Calendar.MINUTE, -mod);
			startTime.set(Calendar.SECOND, 0);
			Date date = startTime.getTime();
			DateFormat SleepStart = new SimpleDateFormat("HH:mm:ss");
			formattedTime = SleepStart.format(date);
		} catch (Exception e) {
			formattedTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add start time : Error Occured : " + e.getMessage());
		}
		return formattedTime;
	}

	public static String CalculatePeriodEndEMEA(TestCases testCase,
			int hour) {
		String formattedTime = "";
		try {
			Calendar EndTime = Calendar.getInstance();
			int minutes = EndTime.get(Calendar.MINUTE);
			int mod = minutes % 10;
			EndTime.add(Calendar.MINUTE, -mod);
			EndTime.set(Calendar.SECOND, 0);
			EndTime.add(Calendar.HOUR, hour);
			Date date = EndTime.getTime();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			formattedTime = dateFormat.format(date);
		} catch (Exception e) {
			formattedTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return formattedTime;
	}

	public static String CalculatePeriodStartNAHB(TestCases testCase) {
		String formattedTime = " ";
		try {
			Calendar startTime = Calendar.getInstance();
			int minutes = startTime.get(Calendar.MINUTE);
			int mod = minutes % 15;
			startTime.add(Calendar.MINUTE, -mod);
			startTime.set(Calendar.SECOND, 0);
			Date date = startTime.getTime();
			DateFormat SleepStart = new SimpleDateFormat("HH:mm:ss");
			formattedTime = SleepStart.format(date);
		} catch (Exception e) {
			formattedTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add start time : Error Occured : " + e.getMessage());
		}
		return formattedTime;
	}
	

	public static String CalculatePeriodEndNAHB(TestCases testCase,
			int hour) {
		String formattedTime = "";
		try {
			Calendar EndTime = Calendar.getInstance();
			int minutes = EndTime.get(Calendar.MINUTE);
			int mod = minutes % 15;
			EndTime.add(Calendar.MINUTE, -mod);
			EndTime.set(Calendar.SECOND, 0);
			EndTime.add(Calendar.HOUR, hour);
			Date date = EndTime.getTime();
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			formattedTime = dateFormat.format(date);
		} catch (Exception e) {
			formattedTime = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return formattedTime;
	}

	/**
	 * <p>
	 * The addDaysToDate method adds days a date.
	 * </p>
	 * 
	 * @param TestCases
	 * @param String
	 *            - This is a date in the format 'yyyy-MM-dd'T'HH:mm:ss' to
	 *            which days have to be added.
	 * @param Integer
	 *            - This is the number of days to be added to the data passed in
	 *            the above parameter.
	 * @return String in the format 'yyyy-MM-dd'T'HH:mm:ss'
	 * 
	 * @author h119237 - Pratik Lalseta.
	 */
	public static String addDaysToDate(TestCases testCase, String date,
			int noOfDays) {
		String dateAfterAddition = "";
		try {
			SimpleDateFormat vacationDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss");
			Calendar c = Calendar.getInstance();
			c.setTime(vacationDateFormat.parse(date));
			c.add(Calendar.DATE, noOfDays);
			dateAfterAddition = vacationDateFormat.format(c.getTime());
		} catch (Exception e) {
			dateAfterAddition = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
					FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return dateAfterAddition;
	}

	public static String getDeviceEquivalentUTCTime(TestCases testCase,
			TestCaseInputs inputs, String UTCTime) {
		String deviceTime = "";
		try {
			TimeZone deviceTimeZone = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z' Z");
			Calendar deviceEqTime = Calendar.getInstance(TimeZone
					.getTimeZone("UTC"));
			deviceEqTime.setTime(dateFormat.parse(UTCTime + " " + "UTC"));
			deviceTimeZone = getDeviceTimeZone(testCase, inputs);
			deviceEqTime.setTimeZone(deviceTimeZone);
			String ampm = "";
			if (deviceEqTime.get(Calendar.AM_PM) == Calendar.AM) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			String hour;
			if (deviceEqTime.get(Calendar.HOUR) == 0) {
				hour = "12";
			} else {
				hour = String.valueOf(deviceEqTime.get(Calendar.HOUR));
			}
			if (Integer.parseInt(hour) < 10) {
				hour = "0" + hour;
			}
			String minute;
			if (deviceEqTime.get(Calendar.MINUTE) < 10) {
				minute = "0" + deviceEqTime.get(Calendar.MINUTE);
			} else {
				minute = String.valueOf(deviceEqTime.get(Calendar.MINUTE));
			}
			int month = deviceEqTime.get(Calendar.MONTH) + 1;
			deviceTime = String.valueOf(deviceEqTime.get(Calendar.YEAR) + "-"
					+ month + "-" + deviceEqTime.get(Calendar.DAY_OF_MONTH)
					+ "T" + hour + ":" + minute + " " + ampm);
		} catch (Exception e) {
			deviceTime = "";
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return deviceTime;
	}

	public static TimeZone getDeviceTimeZone(TestCases testCase,
			TestCaseInputs inputs) throws Exception {
		TimeZone timeZone = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String zone = new String();
			try {
				if (inputs.isRunningOn("Local")) {
					String cmd = "adb shell getprop persist.sys.timezone";
					zone = JasperAdhocOverride.executeADBCommand(testCase, cmd);
				} else if (inputs.isRunningOn("Perfecto")) {
					zone = PerfectoLabUtils.getTimeZoneAndroidOnly(testCase
							.getMobileDriver());
				} else if (inputs.isRunningOn("pCloudy")) {
					PCloudyDeviceInformation deviceInfo = testCase
							.getPcloudyDeviceInformation();
					zone = deviceInfo
							.getpCloudySession()
							.getConnector()
							.executeAdbCommand(deviceInfo.getAuthToken(),
									deviceInfo.getBookingDtoDevice(),
									"adb shell getprop persist.sys.timezone");
				} else if (inputs.isRunningOn("TestObject")) {
					zone = "CET";
				} else if (inputs.isRunningOn("Saucelabs")) {
					zone = "CET";
				}
				zone = zone.trim();
				timeZone = TimeZone.getTimeZone(zone);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			try {
				if (inputs.isRunningOn("Local")) {
					timeZone = TimeZone.getDefault();
				} else if (inputs.isRunningOn("Perfecto")) {
					timeZone = TimeZone.getTimeZone("US/Eastern");
				} else if (inputs.isRunningOn("Saucelabs")) {
					timeZone = TimeZone.getTimeZone("US/Pacific");
				} else if (inputs.isRunningOn("TestObject")) {
					timeZone = TimeZone.getTimeZone("CET");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return timeZone;
	}

}