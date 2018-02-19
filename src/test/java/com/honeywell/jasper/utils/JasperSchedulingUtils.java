package com.honeywell.jasper.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.GlobalVariables;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.lyric.utils.LyricUtils;

import io.appium.java_client.TouchAction;

public class JasperSchedulingUtils {

	public static JSONArray getDefaultScheduleTempelate(TestCases testCase, TestCaseInputs inputs) throws Exception {
		JSONArray tempelate = new JSONArray();
		try (CHILUtil chUtil = new CHILUtil(inputs)) {
			if (chUtil.getConnection()) {
				long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));
				if (locationID == -1) {
					throw new Exception("Invalid locationID: -1");
				}
				if (chUtil.isConnected()) {
					if (chUtil.isConnected()) {
						String chilURL = LyricUtils.getCHILURL(testCase, inputs);
						String url = chilURL + "api/v2/locations/" + locationID + "/DefaultScheduleTemplates";
						HttpURLConnection connection = chUtil.doGetRequest(url);
						if (connection != null) {
							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							String inputLine;
							StringBuffer html = new StringBuffer();
							while (!in.ready()) {
							}
							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}
							in.close();
							tempelate = new JSONArray(html.toString().trim());
						} else {
							throw new Exception("Unable to connect to CHIL");
						}
					}
				} else {
					throw new Exception("Not connected to CHIL");
				}
			}
		} catch (Exception e) {
			throw new Exception("Error occured - " + e.getMessage());
		}
		return tempelate;
	}

	public static HashMap<String, String> getDefaultScheduleValues(TestCases testCase, TestCaseInputs inputs,
			String typeOfSchedule) {
		HashMap<String, String> scheduleValues = new HashMap<String, String>();
		try {
			JSONArray tempelate = getDefaultScheduleTempelate(testCase, inputs);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = devInfo.getJasperDeviceType();
			JSONObject timeTempelate = tempelate.getJSONObject(0);
			JSONObject geofenceTempelate = tempelate.getJSONObject(1);
			String time24Hours = " ";
			SimpleDateFormat hours24 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat hours12 = new SimpleDateFormat("hh:mm a");
			String time = " ";
			Date date = null;
			if (typeOfSchedule.equalsIgnoreCase("Time")) {
				JSONArray days = timeTempelate.getJSONObject("timedSchedule").getJSONArray("days");
				for (int i = 0; i < days.length(); i++) {
					JSONObject temp = null;
					String coolTemp = "";
					String heatTemp = "";
					String period = "";
					if (days.getJSONObject(i).getString("day").equals("Monday")) {
						temp = days.getJSONObject(i);
						JSONArray periods = temp.getJSONArray("periods");
						for (int j = 0; j < periods.length(); j++) {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								if (periods.getJSONObject(j).getString("periodType").equals("Wake")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Wake";

								} else if (periods.getJSONObject(j).getString("periodType").equals("Away")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Away";
								} else if (periods.getJSONObject(j).getString("periodType").equals("Home")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Home";
								} else if (periods.getJSONObject(j).getString("periodType").equals("Sleep")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Sleep";
								}
								if ((devInfo.getThermostatUnits()).equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
									if (coolTemp.contains(".0")) {
										coolTemp = coolTemp.split("\\.")[0];
									}
									if (heatTemp.contains(".0")) {
										heatTemp = heatTemp.split("\\.")[0];
									}
								}

								scheduleValues.put("Everyday" + period + "Time", time);
								scheduleValues.put("Everyday" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Everyday" + period + "HeatTemp", heatTemp);
								scheduleValues.put("Weekday" + period + "Time", time);
								scheduleValues.put("Weekday" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Weekday" + period + "HeatTemp", heatTemp);
							} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
								if (j == 0) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Wake";

								} else if (j == 1) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Away";
								} else if (j == 2) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Home";
								} else if (j == 3) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Sleep";
								}

								if (jasperStatType.toUpperCase().contains("EMEA")) {
									coolTemp = roundOffCelsiusData(testCase,
											JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, coolTemp));
									heatTemp = roundOffCelsiusData(testCase,
											JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, heatTemp));
								}
								scheduleValues.put("Everyday" + period + "Time", time);
								scheduleValues.put("Everyday" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Everyday" + period + "HeatTemp", heatTemp);
								scheduleValues.put("Weekday" + period + "Time", time);
								scheduleValues.put("Weekday" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Weekday" + period + "HeatTemp", heatTemp);
							}
						}
					} else if (days.getJSONObject(i).getString("day").equals("Saturday")) {
						temp = days.getJSONObject(i);
						JSONArray periods = temp.getJSONArray("periods");
						for (int j = 0; j < periods.length(); j++) {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								if (periods.getJSONObject(j).getString("periodType").equals("Wake")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Wake";

								} else if (periods.getJSONObject(j).getString("periodType").equals("Away")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Away";
								} else if (periods.getJSONObject(j).getString("periodType").equals("Home")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Home";
								} else if (periods.getJSONObject(j).getString("periodType").equals("Sleep")) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Sleep";
								}

								if ((devInfo.getThermostatUnits()).equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
									if (coolTemp.contains(".0")) {
										coolTemp = coolTemp.split("\\.")[0];
									}
									if (heatTemp.contains(".0")) {
										heatTemp = heatTemp.split("\\.")[0];
									}
								}

								scheduleValues.put("Weekend" + period + "Time", time);
								scheduleValues.put("Weekend" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Weekend" + period + "HeatTemp", heatTemp);
							} else {
								if (j == 0) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Wake";

								} else if (j == 1) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Away";
								} else if (j == 2) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Home";
								} else if (j == 3) {
									try {
										time24Hours = periods.getJSONObject(j).getString("startTime");
										date = hours24.parse(time24Hours);
										time = hours12.format(date);
									} catch (Exception e) {
										Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
												FailType.FUNCTIONAL_FAILURE,
												"Get Default Schedule Values : Error Occured : " + e.getMessage());
									}
									coolTemp = String.valueOf(periods.getJSONObject(j).get("coolSetPoint"));
									heatTemp = String.valueOf(periods.getJSONObject(j).get("heatSetPoint"));
									period = "Sleep";
								}
								if (jasperStatType.toUpperCase().contains("EMEA")) {
									coolTemp = roundOffCelsiusData(testCase,
											JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, coolTemp));
									heatTemp = roundOffCelsiusData(testCase,
											JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, heatTemp));
								}
								scheduleValues.put("Weekend" + period + "Time", time);
								scheduleValues.put("Weekend" + period + "CoolTemp", coolTemp);
								scheduleValues.put("Weekend" + period + "HeatTemp", heatTemp);
							}
						}
					}
				}
			} else if (typeOfSchedule.equalsIgnoreCase("Geofence")) {
				JSONObject geofenceSchedule = geofenceTempelate.getJSONObject("geoFenceSchedule");
				if (jasperStatType.toUpperCase().contains("NA")) {
					if ((devInfo.getThermostatUnits()).equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
						String temp = String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("coolSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceHomeCoolTemp", temp);

						temp = String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("heatSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceHomeHeatTemp", temp);

						temp = String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("coolSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceAwayCoolTemp", temp);

						temp = String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("heatSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceAwayHeatTemp", temp);

						temp = String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("coolSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceSleepCoolTemp", temp);

						temp = String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("heatSetPoint"));
						if (temp.contains(".0")) {
							temp = temp.split("\\.")[0];
						}
						scheduleValues.put("GeofenceSleepHeatTemp", temp);
					} else {
						scheduleValues.put("GeofenceHomeCoolTemp",
								String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("coolSetPoint")));
						scheduleValues.put("GeofenceHomeHeatTemp",
								String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("heatSetPoint")));
						scheduleValues.put("GeofenceAwayCoolTemp",
								String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("coolSetPoint")));
						scheduleValues.put("GeofenceAwayHeatTemp",
								String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("heatSetPoint")));
						scheduleValues.put("GeofenceSleepCoolTemp",
								String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("coolSetPoint")));
						scheduleValues.put("GeofenceSleepHeatTemp",
								String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("heatSetPoint")));
					}

				} else {
					scheduleValues.put("GeofenceHomeCoolTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("coolSetPoint")))));
					scheduleValues.put("GeofenceHomeHeatTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("homePeriod").get("heatSetPoint")))));
					scheduleValues.put("GeofenceAwayCoolTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("coolSetPoint")))));
					scheduleValues.put("GeofenceAwayHeatTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("awayPeriod").get("heatSetPoint")))));
					scheduleValues.put("GeofenceSleepCoolTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("coolSetPoint")))));
					scheduleValues.put("GeofenceSleepHeatTemp",
							roundOffCelsiusData(testCase, JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase,
									String.valueOf(geofenceSchedule.getJSONObject("sleepMode").get("heatSetPoint")))));
				}

				try {
					time24Hours = geofenceSchedule.getJSONObject("sleepMode").getString("startTime");
					date = hours24.parse(time24Hours);
					time = hours12.format(date);
					scheduleValues.put("GeofenceSleepStartTime", time);
					time24Hours = geofenceSchedule.getJSONObject("sleepMode").getString("endTime");
					date = hours24.parse(time24Hours);
					time = hours12.format(date);
					scheduleValues.put("GeofenceSleepEndTime", time);
				} catch (Exception e) {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Get Default Schedule Values : Error Occured : " + e.getMessage());
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return scheduleValues;
	}

	public static String convertFromFahrenhietToCelsius(TestCases testCase, String fahrenhietTemp) {
		try {
			Double temp = Double.parseDouble(fahrenhietTemp);
			temp = ((temp - 32) * 5) / 9;
			temp = Math.round(temp * 10.0) / 10.0;
			return temp.toString();
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : e.getMessage");
			return " ";
		}
	}

	public static String roundOffCelsiusData(TestCases testCase, String celsiusTemp) {
		Double temp = Double.parseDouble(celsiusTemp);
		return String.valueOf((Math.round(temp * 2) / 2.0));
	}

	public static boolean viewScheduleOnPrimaryCard(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeScheduleButton", 10)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "TimeScheduleButton");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule button not found on Primary Card");
		}
		return flag;
	}

	public static boolean createTimeBasedScheduleWithDefaultValues(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try
		{
		WebElement element = null;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		flag = flag & viewScheduleOnPrimaryCard(testCase);

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CreateScheduleButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CreateScheduleButton");

			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeOption")) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "TimeOption");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Create Schedule : Unable to navigate to create schedule page.");
				return false;
			}
		} else {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleOffOverlay", 5)) {
				if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ScheduleOffOverlay")) {
					flag = false;
				} else {
					Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
				}
			}
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "ScheduleOptionsButton");

			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			String currentScheduleType = devInfo.getThermoStatScheduleType();

			if (currentScheduleType.equalsIgnoreCase("Timed")) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CreateNewTimeScheduleButton");
			} else {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SwitchToTimeScheduleButton");
			}
		}

		if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
				.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "EverydayScheduleButton");
			if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA")) {
				String[] modes = { "Wake", "Away", "Home", "Sleep" };
				for (String mode : modes) {
					HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					List<String> allowedModes = devInfo.getAllowedModes();
					periodTimeandSetPoint.put("periodName", mode);
					if (mode.equals("Wake")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Wake_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "EverydayWake");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_WAKE);
					} else if (mode.equals("Away")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Away_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "EverydayAway");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_AWAY);
					} else if (mode.equals("Home")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Home_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "EverydayHome");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_HOME);
					} else if (mode.equals("Sleep")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Sleep_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "EverydaySleep");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_SLEEP);
					}
					Keyword.ReportStep_Pass(testCase, " ");
					Keyword.ReportStep_Pass(testCase, "*************** Verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
					try {
						String elementDesc = element.getAttribute("name");
						element.click();
						Keyword.ReportStep_Pass(testCase, "Successfully click on : " + elementDesc);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Period Time and Set Points : Failed to select "
										+ periodTimeandSetPoint.get("periodName"));
						return false;
					}
					flag = flag & JasperSchedulingUtils.verifySetPeriodTime(testCase, periodTimeandSetPoint.get("Time"),
							"TimeChooser");
					String coolTemp = " ";
					String heatTemp = " ";
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						}

						flag = flag & JasperSchedulingUtils.verifyHeatStepperValue(testCase, inputs, heatTemp, "");
						flag = flag & JasperSchedulingUtils.verifyCoolStepperValue(testCase, inputs, coolTemp, "");

					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & JasperSchedulingUtils.verifyHeatStepperValue(testCase, inputs, heatTemp, "");
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
						}
						flag = flag & JasperSchedulingUtils.verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					}
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton");
					Keyword.ReportStep_Pass(testCase, "*************** Completed verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
				}
			}
			// ================================================EMEA===========================================================
			else {
				String[] modes = { "1", "2", "3", "4" };
				for (String mode : modes) {
					HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					List<String> allowedModes = devInfo.getAllowedModes();
					periodTimeandSetPoint.put("periodName", mode);
					if (mode.equals("1")) {
						periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
						periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='1_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(testCase, "name", "Everyday_1");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT));
						}
					} else if (mode.equals("2")) {
						periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
						periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='2_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(testCase, "name", "Everyday_2");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT));
						}
					} else if (mode.equals("3")) {
						periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
						periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='3_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(testCase, "name", "Everyday_3");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
						}
					} else if (mode.equals("4")) {
						periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
						periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='4_Everyday']"));
						} else {
							element = MobileUtils.getMobElement(testCase, "name", "Everyday_4");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
						}
					}
					Keyword.ReportStep_Pass(testCase, " ");
					Keyword.ReportStep_Pass(testCase, "*************** Verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
					try {
						String elementDesc = element.getAttribute("name");
						element.click();
						Keyword.ReportStep_Pass(testCase, "Successfully click on : " + elementDesc);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Period Time and Set Points : Failed to select "
										+ periodTimeandSetPoint.get("periodName"));
						return false;
					}
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (MobileUtils.isMobElementExists("XPATH",
								"//*[@text='When do you want the temperature to change?']", testCase, 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Time chooser header is shown correctly: When do you want the temperature to change?");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Time chooser header is not shown correctly");
						}
						if (MobileUtils.isMobElementExists("XPATH",
								"//*[@text='What temperature is preferred during this time?']", testCase, 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Temp chooser header is shown correctly: What temperature is preferred during this time?");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Temp chooser header is not shown correctly");
						}
					} else {
						if (MobileUtils.isMobElementExists("XPATH",
								"//*[@value='When do you want the temperature to change?']", testCase, 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Time chooser header is shown correctly: When do you want the temperature to change?");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Time chooser header is not shown correctly");
						}
						if (MobileUtils.isMobElementExists("XPATH",
								"//*[@value='What temperature is preferred at this time?']", testCase, 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Temp chooser header is shown correctly: What temperature is preferred at this time?");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Temp chooser header is not shown correctly");
						}
					}
					flag = flag & JasperSchedulingUtils.verifySetPeriodTime(testCase,
							periodTimeandSetPoint.get("StartTime"), "TimeChooser");
					flag = flag & JasperSchedulingUtils.verifySetPeriodTime(testCase,
							periodTimeandSetPoint.get("EndTime"), "TimeChooserEndTime");
					String coolTemp = " ";
					String heatTemp = " ";
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						}

						flag = flag & JasperSchedulingUtils.verifyHeatStepperValue(testCase, inputs, heatTemp, "");
						flag = flag & JasperSchedulingUtils.verifyCoolStepperValue(testCase, inputs, coolTemp, "");

					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & JasperSchedulingUtils.verifyHeatStepperValue(testCase, inputs, heatTemp, "");
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
						}
						flag = flag & JasperSchedulingUtils.verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					}
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton");
					Keyword.ReportStep_Pass(testCase, "*************** Completed verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
				}
			}
		} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
				.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "WeekdayandWeekendScheduleButton");
			if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA")) {
				String[] modes = { "Wake_Weekday", "Away_Weekday", "Home_Weekday", "Sleep_Weekday", "Wake_Weekend",
						"Away_Weekend", "Home_Weekend", "Sleep_Weekend" };
				for (String mode : modes) {
					HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					List<String> allowedModes = devInfo.getAllowedModes();
					periodTimeandSetPoint.put("periodName", mode);
					if (mode.equals("Wake_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Wake_Monday - Friday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekdayWake");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKDAY_WAKE);
					} else if (mode.equals("Away_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Away_Monday - Friday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekdayAway");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKDAY_AWAY);
					} else if (mode.equals("Home_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Home_Monday - Friday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekdayHome");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKDAY_HOME);
					} else if (mode.equals("Sleep_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='Sleep_Monday - Friday']"));
						} else {
							element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekdaySleep");
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKDAY_SLEEP);
					} else if (mode.equals("Wake_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='Wake_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendWake", 5)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e3) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Wake_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekendWake");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='Wake_Saturday - Sunday']"));
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element Wake_Saturday-Sunday");
							}
						}

						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKEND_WAKE);
					} else if (mode.equals("Away_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='Away_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendAway", 5)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Away_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekendAway");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='Away_Saturday - Sunday']"));
								}

							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element Away_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKEND_AWAY);
					} else if (mode.equals("Home_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='Home_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendHome", 5)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Home_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekendHome");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='Home_Saturday - Sunday']"));
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element Home_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKEND_HOME);
					} else if (mode.equals("Sleep_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='Sleep_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendSleep", 5)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Sleep_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(fieldObjects, testCase, "WeekendSleep");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='Sleep_Saturday - Sunday']"));
								}

							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element Sleep_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
						}
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.WEEKEND_SLEEP);
					}
					Keyword.ReportStep_Pass(testCase, " ");
					Keyword.ReportStep_Pass(testCase, "*************** Verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
					try {
						String elementDesc = element.getAttribute("name");
						element.click();
						Keyword.ReportStep_Pass(testCase, "Successfully click on : " + elementDesc);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Period Time and Set Points : Failed to select "
										+ periodTimeandSetPoint.get("periodName"));
						return false;
					}
					flag = flag & verifySetPeriodTime(testCase, periodTimeandSetPoint.get("Time"), "TimeChooser");
					String coolTemp = " ";
					String heatTemp = " ";
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
						flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
						}
						flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					}
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton");
					Keyword.ReportStep_Pass(testCase, "*************** Completed verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
				}
			} else {

				String[] modes = { "Wake_Weekday", "Away_Weekday", "Home_Weekday", "Sleep_Weekday", "Wake_Weekend",
						"Away_Weekend", "Home_Weekend", "Sleep_Weekend" };
				for (String mode : modes) {
					HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
					DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
					List<String> allowedModes = devInfo.getAllowedModes();
					periodTimeandSetPoint.put("periodName", mode);
					if (mode.equals("Wake_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_1_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='1_Monday - Friday']"));
						} else {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@name='Monday - Friday_1']"));
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_1_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_1_COOL_SETPOINT));
						}
					} else if (mode.equals("Away_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_2_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='2_Monday - Friday']"));
						} else {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@name='Monday - Friday_2']"));
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT));
						}
					} else if (mode.equals("Home_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='3_Monday - Friday']"));
						} else {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@name='Monday - Friday_3']"));
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
						}
					} else if (mode.equals("Sleep_Weekday")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@content-desc='4_Monday - Friday']"));
						} else {
							element = testCase.getMobileDriver()
									.findElement(By.xpath("//*[@name='Monday - Friday_4']"));
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
						}
					} else if (mode.equals("Wake_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_1_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='1_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists("xpath", "//*[@name='Saturday - Sunday_1']",
										testCase)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e3) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(testCase, "xpath",
										"//*[@name='Saturday - Sunday_1']");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='1_Saturday - Sunday']"));
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 1_Saturday-Sunday");
							}
						}

						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_1_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_1_COOL_SETPOINT));
						}
					} else if (mode.equals("Away_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_2_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='2_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists("xpath", "//*[@name='Saturday - Sunday_2']",
										testCase)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Away_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(testCase, "xpath",
										"//*[@name='Saturday - Sunday_2']");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='2_Saturday - Sunday']"));
								}

							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 2_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT));
						}
					} else if (mode.equals("Home_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='3_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists("xpath", "//*[@name='Saturday - Sunday_3']",
										testCase)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Home_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(testCase, "xpath",
										"//*[@name='Saturday - Sunday_3']");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='3_Saturday - Sunday']"));
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 3_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
						}
					} else if (mode.equals("Sleep_Weekend")) {
						periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = testCase.getMobileDriver()
										.findElement(By.xpath("//*[@content-desc='4_Saturday - Sunday']"));
							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!MobileUtils.isMobElementExists("xpath", "//*[@name='Saturday - Sunday_4']",
										testCase)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
												.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Sleep_Saturday-Sunday");
									}
								}
								element = MobileUtils.getMobElement(testCase, "xpath",
										"//*[@name='Saturday - Sunday_4']");
							}
						} catch (NoSuchElementException e) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = testCase.getMobileDriver()
											.findElement(By.xpath("//*[@content-desc='4_Saturday - Sunday']"));
								}

							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element Sleep_Saturday-Sunday");
							}
						}
						if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
						} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("HeatSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
						} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
							periodTimeandSetPoint.put("CoolSetPoint",
									inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
						}
					}
					Keyword.ReportStep_Pass(testCase, " ");
					Keyword.ReportStep_Pass(testCase, "*************** Verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
					try {
						String elementDesc = element.getAttribute("name");
						element.click();
						Keyword.ReportStep_Pass(testCase, "Successfully click on : " + elementDesc);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Period Time and Set Points : Failed to select "
										+ periodTimeandSetPoint.get("periodName"));
						return false;
					}
					flag = flag & verifySetPeriodTime(testCase, periodTimeandSetPoint.get("Time"), "TimeChooser");
					String coolTemp = " ";
					String heatTemp = " ";
					if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
						flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							int targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
						} else {
							Double targetHeatTemp = Double.parseDouble(periodTimeandSetPoint.get("HeatSetPoint"));
							heatTemp = String.valueOf(targetHeatTemp);
						}
						flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
					} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
						if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
							Double temp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							int targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
						} else {
							Double targetCoolTemp = Double.parseDouble(periodTimeandSetPoint.get("CoolSetPoint"));
							coolTemp = String.valueOf(targetCoolTemp);
						}
						flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					}
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton");
					Keyword.ReportStep_Pass(testCase, "*************** Completed verifying time and set points for "
							+ periodTimeandSetPoint.get("periodName") + " period ***************");
				}

			}
		}
		// flag = flag & InputVariables.verifyCreatedSchedule(testCase, inputs,
		// "Time");
		if (inputs.getInputValue(InputVariables.DELETE_PERIOD).equalsIgnoreCase("Yes")) {
			JasperSchedulingUtils.deletePeriodEMEA(testCase, inputs);
		}
		if (inputs.getInputValue(InputVariables.DELETE_MULTIPLE_PERIODS).equalsIgnoreCase("Yes")) {
			int count = 0, periodToDelete = 0;
			ArrayList<Integer> arrlist = new ArrayList<Integer>(8);
			Random rn = new Random();
			if (inputs.getInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE).equalsIgnoreCase("ONE")) {
				count = 1;
			} else if (inputs.getInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE).equalsIgnoreCase("TWO")) {
				count = 2;
			} else if (inputs.getInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE).equalsIgnoreCase("THREE")) {
				count = 3;
			} else if (inputs.getInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE).equalsIgnoreCase("FOUR")) {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					count = 8;
				} else {
					count = 4;
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				for (int i = 0; i < count; i++) {
					do {
						periodToDelete = rn.nextInt(4 - 1 + 1) + 1;
					} while (arrlist.contains(periodToDelete));
					inputs.setInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE, String.valueOf(periodToDelete));
					arrlist.add(periodToDelete);
					JasperSchedulingUtils.deletePeriodNA(testCase, inputs);
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				periodToDelete = 1;
				for (int i = 0; i < count; i++) {
					do {
						periodToDelete = rn.nextInt(8 - 1 + 1) + 1;
					} while (arrlist.contains(periodToDelete));
					inputs.setInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE, String.valueOf(periodToDelete));
					arrlist.add(periodToDelete);
					deletePeriodNA(testCase, inputs);
				}
			}

			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EverydayScheduleButton", 5)) {
				Keyword.ReportStep_Pass(testCase, "Successfully deleted all the periods");
				return flag;
			}
		}
		if (inputs.getInputValue(InputVariables.ADD_PERIOD).equalsIgnoreCase("Yes")) {
			JasperSchedulingUtils.addPeriodEMEADefaultCase(testCase, inputs);
		}

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DoneButton", 5)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "DoneButton");
		}
		if (!inputs.getInputValue("ConfirmTimeShedule").isEmpty()) {
			System.out.println(inputs.getInputValue("ConfirmTimeShedule"));
			if (inputs.getInputValue("ConfirmTimeShedule").equalsIgnoreCase("true")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmChangeButton", 5, false)) {
					Keyword.ReportStep_Pass(testCase, "Create Schedule : Confirm change button shown");
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmChangeButton")) {
						flag = false;
					}
				}
			} else {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (MobileUtils.isMobElementExists("name", "CANCEL", testCase, 5, false)) {
						Keyword.ReportStep_Pass(testCase, "Create Schedule : Cancel change button shown");
						if (!MobileUtils.clickOnElement(testCase, "name", "CANCEL")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel button not shown");
					}
				} else {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CancelChangeButton", 5, false)) {
						Keyword.ReportStep_Pass(testCase, "Create Schedule : Cancel change button shown");
						if (!MobileUtils.clickOnElement(fieldObjects, testCase, "CancelChangeButton")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel button not shown");
					}
				}
				return flag;
			}

		} else {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmChangeButton", 10, false)) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (testCase.getMobileDriver().findElement(By.id("android:id/button1")).getAttribute("text")
							.equals("Confirm Change")) {
						testCase.getMobileDriver().findElement(By.id("android:id/button1")).click();
					}
				} else {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmChangeButton")) {
						flag = false;
					}
				}
			}
		}
		if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
			System.out.println("Copy all");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CheckBox", 3, false)) {
				List<WebElement> checkBoxes = MobileUtils.getMobElements(fieldObjects, testCase, "CheckBox");
				for (WebElement cbox : checkBoxes) {
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (cbox.getAttribute("value").equals("Disabled")) {
							cbox.click();
						}
					} else {
						if (cbox.getAttribute("checked").equals("false")) {
							cbox.click();
						}
					}
				}

			}
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CopyButton", 3, false)) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CopyButton");
			}
		} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CheckBox", 3, false)) {
				List<WebElement> checkBoxes = MobileUtils.getMobElements(fieldObjects, testCase, "CheckBox");
				System.out.println(checkBoxes.size());
				String SelectStatPosition = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase,
						inputs, Double.parseDouble("0"), Double.parseDouble(String.valueOf(checkBoxes.size())));
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Keyword.ReportStep_Pass(testCase, "Selecting stat at Position " + SelectStatPosition
							+ ", copying to " + checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Selecting stat at Position " + SelectStatPosition + ", copying to "
									+ checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
				}

				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value").equals("Disabled")) {
						checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
						inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
								checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
					}
				} else {
					if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked").equals("false")) {
						checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
						inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
								checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
					}
				}

			}
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CopyButton", 10, false)) {
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CopyButton");
			}
		} else if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SkipButton", 10, false)) {
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SkipButton");
		}
		/*
		 * if (MobileUtils.isMobElementExists(fieldObjects, testCase,
		 * "GeofenceScheduleButton", 10)) { Keyword.ReportStep_Pass(testCase,
		 * "Create Schedule : Successfully navigated to Primary Card"); } else if
		 * (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeScheduleButton",
		 * 10)) { Keyword.ReportStep_Pass(testCase,
		 * "Create Schedule : Successfully navigated to Primary Card"); } else { flag =
		 * false; Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
		 * "Create Schedule : Failed to navigate to Primary Card"); }
		 */
		}
		catch(Exception e)
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifySetPeriodTime(TestCases testCase, String time, String locatorValueinObjectDefinition) {
		boolean flag = true;
		try {
			SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
			String time12hours = time;
			String time24hours = date24Format.format(date12Format.parse(time));
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(fieldObjects, testCase, locatorValueinObjectDefinition).getText()
						.replaceAll("\\.", "").toUpperCase().contains(time12hours)
						|| MobileUtils.getMobElement(fieldObjects, testCase, locatorValueinObjectDefinition).getText()
								.replaceAll("\\.", "").toUpperCase().contains(time24hours)) {
					Keyword.ReportStep_Pass(testCase, "Verify Set Period Time : Time is set to " + time);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Set Period Time : Time is not set to " + time);
				}
			} else {
				String setTime = "";
				if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooser")) {
					setTime = MobileUtils.getMobElement(testCase, "name", "TimeSchedule_StartTime_value")
							.getAttribute("value");
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
					setTime = MobileUtils.getMobElement(testCase, "name", "TimeSchedule_EndTime_value")
							.getAttribute("value");
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("GeofenceSleepStartTime")) {
					setTime = MobileUtils.getMobElement(fieldObjects, testCase, locatorValueinObjectDefinition)
							.getAttribute("value");
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("GeofenceSleepEndTime")) {
					setTime = MobileUtils.getMobElement(fieldObjects, testCase, locatorValueinObjectDefinition)
							.getAttribute("value");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Incorrect time chooser identifier");
				}
				if (setTime.toUpperCase().contains(time.toUpperCase())) {
					Keyword.ReportStep_Pass(testCase, "Verify Set Period Time : Time is set to " + time);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Set Period Time : Time is not set to " + time);
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify Set Period Time : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static String getRandomSetPointValueBetweenMinandMax(TestCases testCase, TestCaseInputs inputs, Double max,
			Double min) {
		Random rn = new Random();
		Double setPoint = min + (max - min) * rn.nextDouble();
		return String.valueOf(setPoint.intValue());
	}

	public static boolean addPeriodEMEADefaultCase(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try
		{
		HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
		String tempPeriod = " ", tempPeriodStartTime = " ", tempPeriodEndTime = "";
		WebElement element = null;
		List<WebElement> schedule_period_time = null, schedule_period_setpoint = null;
		List<WebElement> weekdaySchedule_period_time = null;
		List<WebElement> weekendSchedule_period_time = null;
		int initialPeriodSize = 0, finalPeriodSize = 0;

		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
		List<String> allowedModes = devInfo.getAllowedModes();

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AddImage", 5)) {
			element = MobileUtils.getMobElement(fieldObjects, testCase, "AddImage");
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting time and set points for new period ***************");
			Random rn = new Random();
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				tempPeriod = String.valueOf(rn.nextInt((3 - 1) + 1) + 1);
				System.out.println(tempPeriod);
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EverydayTime", 5)) {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate Everyday Time list");
				}
				initialPeriodSize = schedule_period_time.size();
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				do {
					tempPeriod = String.valueOf(rn.nextInt((7 - 1) + 1) + 1);
				} while (tempPeriod.equalsIgnoreCase("4"));
				System.out.println(tempPeriod);
				if (Integer.parseInt(tempPeriod) <= 4) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						while (!MobileUtils.isMobElementExists("XPATH",
								"//*[contains(@content-desc,'1_Monday - Friday')]", testCase, 5)) {
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
						}

					} else {
						Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekdayTimeList", 5)) {
						weekdaySchedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase,
								"WeekdayTimeList");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekday Time list");
					}
					element = MobileUtils.getMobElements(fieldObjects, testCase, "AddImage").get(0);
					initialPeriodSize = weekdaySchedule_period_time.size();
				} else {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);

					} else {
						Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendTimeList", 5)) {
						weekendSchedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase,
								"WeekendTimeList");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekend Time list");
					}
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						int size = MobileUtils.getMobElements(fieldObjects, testCase, "AddImage").size();
						if (size > 1) {
							element = MobileUtils.getMobElements(fieldObjects, testCase, "AddImage").get(1);
						} else {
							element = MobileUtils.getMobElements(fieldObjects, testCase, "AddImage").get(0);
						}
					} else {
						element = MobileUtils.getMobElements(fieldObjects, testCase, "AddImage").get(1);
					}

					initialPeriodSize = weekendSchedule_period_time.size();
				}
			}

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					tempPeriodStartTime = schedule_period_time.get(Integer.parseInt(tempPeriod) - 1).getText();
					tempPeriodEndTime = schedule_period_time.get(Integer.parseInt(tempPeriod)).getText();
				} else {
					if (Integer.parseInt(tempPeriod) <= 4) {
						tempPeriodStartTime = weekdaySchedule_period_time.get(Integer.parseInt(tempPeriod) - 1)
								.getText();
						tempPeriodEndTime = weekdaySchedule_period_time.get(Integer.parseInt(tempPeriod)).getText();
					} else {
						tempPeriodStartTime = weekendSchedule_period_time.get(Integer.parseInt(tempPeriod) - 4 - 1)
								.getText();
						tempPeriodEndTime = weekendSchedule_period_time.get(Integer.parseInt(tempPeriod) - 4).getText();

					}
				}
			} else {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					tempPeriodStartTime = schedule_period_time.get(Integer.parseInt(tempPeriod) - 1)
							.getAttribute("value");
					tempPeriodEndTime = schedule_period_time.get(Integer.parseInt(tempPeriod)).getAttribute("value");
				} else {
					if (Integer.parseInt(tempPeriod) <= 4) {
						tempPeriodStartTime = weekdaySchedule_period_time.get(Integer.parseInt(tempPeriod) - 1)
								.getAttribute("value");
						tempPeriodEndTime = weekdaySchedule_period_time.get(Integer.parseInt(tempPeriod))
								.getAttribute("value");
					} else {
						tempPeriodStartTime = weekendSchedule_period_time.get(Integer.parseInt(tempPeriod) - 4 - 1)
								.getAttribute("value");
						tempPeriodEndTime = weekendSchedule_period_time.get(Integer.parseInt(tempPeriod) - 4)
								.getAttribute("value");

					}
				}
			}
			try {
				if (!tempPeriodStartTime.contains("m") && !tempPeriodStartTime.contains("M")) {
					final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
					final Date dateObj1 = sdf.parse(tempPeriodStartTime.split("\\s+")[0]);
					tempPeriodStartTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
				}
				if (!tempPeriodEndTime.contains("m") && !tempPeriodEndTime.contains("M")) {
					final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
					final Date dateObj1 = sdf.parse(tempPeriodEndTime.split("\\s+")[0]);
					tempPeriodEndTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
				}
			} catch (NumberFormatException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"[NumberFormatException] Error message: " + e.getMessage());
			} catch (ParseException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"[ParseException] Error message: " + e.getMessage());
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"[Exception] Error message: " + e.getMessage());
			}

			tempPeriodStartTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, tempPeriodStartTime, true, 0, 30);
			tempPeriodEndTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, tempPeriodEndTime, false, 0, 30);
			inputs.setInputValue(InputVariables.ADD_PERIOD_START_TIME, tempPeriodStartTime);
			inputs.setInputValue(InputVariables.ADD_PERIOD_END_TIME, tempPeriodEndTime);

			periodTimeandSetPoint.put("periodName", "AddImage");
			periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.ADD_PERIOD_START_TIME));
			periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.ADD_PERIOD_END_TIME));
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				periodTimeandSetPoint.put("HeatSetPoint",
						inputs.getInputValue(InputVariables.ADD_PERIOD_HEAT_SETPOINT));
				periodTimeandSetPoint.put("CoolSetPoint",
						inputs.getInputValue(InputVariables.ADD_PERIOD_COOL_SETPOINT));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				periodTimeandSetPoint.put("HeatSetPoint",
						inputs.getInputValue(InputVariables.ADD_PERIOD_HEAT_SETPOINT));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				periodTimeandSetPoint.put("CoolSetPoint",
						inputs.getInputValue(InputVariables.ADD_PERIOD_COOL_SETPOINT));
			}

			flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint,
					element);
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SaveButton");
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting time and set points for new period ***************");

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
				finalPeriodSize = schedule_period_time.size();
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				if (Integer.parseInt(tempPeriod) <= 4) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);

					} else {
						Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekdayTimeList", 5)) {
						schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "WeekdayTimeList");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekday Time list");
					}
					finalPeriodSize = schedule_period_time.size();
				} else {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);

					} else {
						Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
						TouchAction action = new TouchAction(testCase.getMobileDriver());
						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
					}
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendTimeList", 5)) {
						schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "WeekendTimeList");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekend Time list");
					}
					finalPeriodSize = schedule_period_time.size();
				}
			}

			String tempTime = "";
			if (initialPeriodSize < finalPeriodSize) {
				Keyword.ReportStep_Pass(testCase, "A new period is added successfully: Initial period count-"
						+ initialPeriodSize + " Final period count-" + finalPeriodSize);
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						schedule_period_setpoint = MobileUtils.getMobElements(fieldObjects, testCase,
								"SchedulePeriodHeatSetPoint");

						for (int i = 1; i <= schedule_period_time.size(); i++) {
							try {
								tempTime = schedule_period_time.get(i - 1).getText();
								if (!schedule_period_time.get(i - 1).getText().contains("m")
										&& !schedule_period_time.get(i - 1).getText().contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf
											.parse(schedule_period_time.get(i - 1).getText().split("\\s+")[0]);
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 1) {
								inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							} else if (i == 2) {
								inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							} else if (i == 3) {
								inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							} else if (i == 4) {
								inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							} else if (i == 5) {
								inputs.setInputValue(InputVariables.EVERYDAY_5_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							} else if (i == 6) {
								inputs.setInputValue(InputVariables.EVERYDAY_6_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT,
										schedule_period_setpoint.get(i - 1).getText());
							}
						}
					} else {
						for (int i = 1; i <= schedule_period_time.size(); i++) {
							try {
								tempTime = schedule_period_time.get(i - 1).getAttribute("value");
								if (!schedule_period_time.get(i - 1).getAttribute("value").contains("m")
										&& !schedule_period_time.get(i - 1).getAttribute("value").contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf
											.parse(schedule_period_time.get(i - 1).getAttribute("value"));
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 1) {
								inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							} else if (i == 2) {
								inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							} else if (i == 3) {
								inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							} else if (i == 4) {
								inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							} else if (i == 5) {
								inputs.setInputValue(InputVariables.EVERYDAY_5_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							} else if (i == 6) {
								inputs.setInputValue(InputVariables.EVERYDAY_6_TIME, tempTime);
								inputs.setInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT,
										MobileUtils
												.getMobElement(testCase, "name", "Everyday_" + i + "_HeatTemperature")
												.getAttribute("value"));
							}
						}
					}
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					if (Integer.parseInt(tempPeriod) <= 4) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);

						} else {
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekdayTimeList", 5)) {
							schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase,
									"WeekdayTimeList");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekday Time list");
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_period_setpoint = MobileUtils.getMobElements(fieldObjects, testCase,
									"WeekdayHeatSetpointListEMEA");
							for (int i = 1; i <= schedule_period_time.size(); i++) {
								try {
									tempTime = schedule_period_time.get(i - 1).getText();
									if (!schedule_period_time.get(i - 1).getText().contains("m")
											&& !schedule_period_time.get(i - 1).getText().contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(schedule_period_time.get(i - 1).getText());
										tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (i == 1) {
									inputs.setInputValue(InputVariables.WEEKDAY_1_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 2) {
									inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 3) {
									inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 4) {
									inputs.setInputValue(InputVariables.WEEKDAY_4_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 5) {
									inputs.setInputValue(InputVariables.WEEKDAY_5_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 6) {
									inputs.setInputValue(InputVariables.WEEKDAY_6_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								}
							}
						} else {
							for (int i = 1; i <= schedule_period_time.size(); i++) {
								try {
									tempTime = schedule_period_time.get(i - 1).getAttribute("value");
									if (!schedule_period_time.get(i - 1).getAttribute("value").contains("m")
											&& !schedule_period_time.get(i - 1).getAttribute("value").contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf
												.parse(schedule_period_time.get(i - 1).getAttribute("value"));
										tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (i == 1) {
									inputs.setInputValue(InputVariables.WEEKDAY_1_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 2) {
									inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 3) {
									inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 4) {
									inputs.setInputValue(InputVariables.WEEKDAY_4_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 5) {
									inputs.setInputValue(InputVariables.WEEKDAY_5_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 6) {
									inputs.setInputValue(InputVariables.WEEKDAY_6_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);

						} else {
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendTimeList", 5)) {
							schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase,
									"WeekendTimeList");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekend Time list");
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_period_setpoint = MobileUtils.getMobElements(fieldObjects, testCase,
									"WeekendHeatSetpointListEMEA");
							for (int i = 1; i <= schedule_period_time.size(); i++) {
								try {
									tempTime = schedule_period_time.get(i - 1).getText();
									if (!schedule_period_time.get(i - 1).getText().contains("m")
											&& !schedule_period_time.get(i - 1).getText().contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(schedule_period_time.get(i - 1).getText());
										tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (i == 1) {
									inputs.setInputValue(InputVariables.WEEKEND_1_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 2) {
									inputs.setInputValue(InputVariables.WEEKEND_2_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 3) {
									inputs.setInputValue(InputVariables.WEEKEND_3_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 4) {
									inputs.setInputValue(InputVariables.WEEKEND_4_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 5) {
									inputs.setInputValue(InputVariables.WEEKEND_5_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								} else if (i == 6) {
									inputs.setInputValue(InputVariables.WEEKEND_6_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT,
											schedule_period_setpoint.get(i - 1).getText());
								}
							}
						} else {
							for (int i = 1; i <= schedule_period_time.size(); i++) {
								try {
									tempTime = schedule_period_time.get(i - 1).getAttribute("value");
									if (!schedule_period_time.get(i - 1).getAttribute("value").contains("m")
											&& !schedule_period_time.get(i - 1).getAttribute("value").contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf
												.parse(schedule_period_time.get(i - 1).getAttribute("value"));
										tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (i == 1) {
									inputs.setInputValue(InputVariables.WEEKEND_1_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 2) {
									inputs.setInputValue(InputVariables.WEEKEND_2_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 3) {
									inputs.setInputValue(InputVariables.WEEKEND_3_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 4) {
									inputs.setInputValue(InputVariables.WEEKEND_4_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 5) {
									inputs.setInputValue(InputVariables.WEEKEND_5_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								} else if (i == 6) {
									inputs.setInputValue(InputVariables.WEEKEND_6_TIME, tempTime);
									inputs.setInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT,
											MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + i + "_HeatTemperature")
													.getAttribute("value"));
								}
							}
						}

					}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to add a new period: Initial period count-" + initialPeriodSize + " Final period count-"
								+ finalPeriodSize);
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[AddImage] Add image/+ icon is not shown");
		}
		}
		catch(Exception e)
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public static boolean setPeriodTime(TestCases testCase, String time, String locatorValueinObjectDefinition,
			boolean isValidTime, boolean verifySetPeriodTime) {
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		boolean flag = true;
		try {
			String timeToSet = " ";
			String time24hours = " ";
			String hours = time.split(":")[0];
			String minutes = time.split(":")[1].split(" ")[0];
			String ampm = time.split(":")[1].split(" ")[1];
			String invalidTime = " ";
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, locatorValueinObjectDefinition)) {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooser")) {
						if (!MobileUtils.clickOnElement(testCase, "name", "TimeSchedule_StartTime")) {
							flag = false;
						}
					} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
						if (MobileUtils.isMobElementExists("xpath", "//XCUIElementTypeCell[@name='End']", testCase,
								5)) {
							if (!MobileUtils.getMobElement(testCase, "xpath", "//XCUIElementTypeCell[@name='End']")
									.getAttribute("value").equalsIgnoreCase("false")) {
								if (!MobileUtils.clickOnElement(testCase, "xpath",
										"//XCUIElementTypeCell[@name='End']")) {
									flag = false;
								}
							} else {
								if (!MobileUtils.clickOnElement(testCase, "name", "Start")) {
									flag = false;
								}
								return flag;
							}
						} else {
							if (MobileUtils.isMobElementExists("name", "TimeSchedule_EndTime_cell", testCase, 5)) {
								if (!MobileUtils.clickOnElement(testCase, "name", "TimeSchedule_EndTime_cell")) {
									flag = false;
								}
							}
						}
					} else {
						flag = flag
								& MobileUtils.clickOnElement(fieldObjects, testCase, locatorValueinObjectDefinition);
					}
				} else {
					if (MobileUtils.getMobElement(fieldObjects, testCase, locatorValueinObjectDefinition)
							.getAttribute("enabled").equalsIgnoreCase("true")) {
						flag = flag
								& MobileUtils.clickOnElement(fieldObjects, testCase, locatorValueinObjectDefinition);
					} else {
						Keyword.ReportStep_Pass(testCase, locatorValueinObjectDefinition + " is disabled");
						return flag;
					}
				}
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
				SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
				time24hours = date24Format.format(date12Format.parse(time));
				if (MobileUtils.isMobElementExists("id", "ampm_label", testCase, 3, false)
						|| MobileUtils.isMobElementExists("id", "am_label", testCase, 3, false)
						|| MobileUtils.isMobElementExists("id", "pm_label", testCase, 3, false)) {
					if (ampm.equalsIgnoreCase("AM")) {
						ampm = "A.M.";
					} else {
						ampm = "P.M.";
					}
					timeToSet = hours + ":" + minutes + ampm;
					invalidTime = hours + ":" + "25" + ampm;
				} else {
					timeToSet = time24hours;
					invalidTime = time24hours.split(":")[0] + ":25";
				}
				if (isValidTime) {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimePicker", 5)) {
						if (MobileUtils.setValueToElement(fieldObjects, testCase, "TimePicker", timeToSet)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time : Failed to set time " + timeToSet + " to time picker");
						}
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "OkButton");
					}
				} else {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimePicker", 5)) {
						if (!MobileUtils.setValueToElement(fieldObjects, testCase, "TimePicker", invalidTime)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Failed to set time " + invalidTime + " to time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						}
						if (MobileUtils.setValueToElement(fieldObjects, testCase, "TimePicker", timeToSet)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time : Failed to set time " + timeToSet + " to time picker");
						}
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "OkButton");
					}
				}
				if (verifySetPeriodTime) {
					flag = flag & verifySetPeriodTime(testCase, time, locatorValueinObjectDefinition);
				}

			} else {
				int i = Integer.parseInt(hours);
				hours = Integer.toString(i);
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeHours", 5)) {
					flag = flag & MobileUtils.setValueInPicker(testCase, fieldObjects, "TimeHours", hours);
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeMinutes", 5)) {
					flag = flag & MobileUtils.setValueInPicker(testCase, fieldObjects, "TimeMinutes", minutes);
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeAMPM", 5)) {
					flag = flag & MobileUtils.setValueInPicker(testCase, fieldObjects, "TimeAMPM", ampm);
				}
				if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooser")) {
					if (!MobileUtils.clickOnElement(testCase, "name", "TimeSchedule_StartTime")) {
						flag = false;
					}
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
					if (!MobileUtils.getMobElement(testCase, "NAME", "End").getAttribute("value")
							.equalsIgnoreCase("false")) {
						if (!MobileUtils.clickOnElement(testCase, "name", "End")) {
							flag = false;
						}
					} else {
						if (!MobileUtils.clickOnElement(testCase, "name", "Start")) {
							flag = false;
						}
					}
				} else {
					flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, locatorValueinObjectDefinition);
				}
				if (verifySetPeriodTime) {
					flag = flag & verifySetPeriodTime(testCase, time, locatorValueinObjectDefinition);
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Set Period Time : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean setTimeSchedulePeriodTimeAndSetPoints(TestCases testCase, TestCaseInputs inputs,
			HashMap<String, String> periodTimeandSetPoint, WebElement element) {
		boolean flag = true;
		try {
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = devInfo.getJasperDeviceType();
			try {
				String elementDesc = element.getAttribute("name");
				element.click();
				Keyword.ReportStep_Pass(testCase, "Successfully click on : " + elementDesc);
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set Period Time and Set Points : Failed to select " + periodTimeandSetPoint.get("periodName"));
				return false;
			}
			if (jasperStatType.equalsIgnoreCase("NA")) {
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, periodTimeandSetPoint.get("Time"),
						"TimeChooser", true, true);
			} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, periodTimeandSetPoint.get("StartTime"),
						"TimeChooser", true, true);
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, periodTimeandSetPoint.get("EndTime"),
						"TimeChooserEndTime", true, true);
			}
			if (inputs.getInputValue(InputVariables.OVERRIDE_PERIOD).equalsIgnoreCase("Yes")) {

			} else {
				HashMap<String, String> targetSetPoints = new HashMap<String, String>();
				List<String> allowedModes = devInfo.getAllowedModes();
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					Keyword.ReportStep_Pass(testCase,
							"Set Period Set Points : Setting " + periodTimeandSetPoint.get("periodName")
									+ " cool set points to " + periodTimeandSetPoint.get("CoolSetPoint"));
					Keyword.ReportStep_Pass(testCase,
							"Set Period Set Points : Setting " + periodTimeandSetPoint.get("periodName")
									+ " heat set points to " + periodTimeandSetPoint.get("HeatSetPoint"));
					targetSetPoints.put("targetCoolTemp", periodTimeandSetPoint.get("CoolSetPoint"));
					targetSetPoints.put("targetHeatTemp", periodTimeandSetPoint.get("HeatSetPoint"));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					Keyword.ReportStep_Pass(testCase, "Setting " + periodTimeandSetPoint.get("periodName")
							+ " heat set points to " + periodTimeandSetPoint.get("HeatSetPoint"));
					targetSetPoints.put("targetHeatTemp", periodTimeandSetPoint.get("HeatSetPoint"));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					Keyword.ReportStep_Pass(testCase, "Setting " + periodTimeandSetPoint.get("periodName")
							+ " cool set points to " + periodTimeandSetPoint.get("CoolSetPoint"));
					targetSetPoints.put("targetCoolTemp", periodTimeandSetPoint.get("CoolSetPoint"));
				}
				flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, targetSetPoints,
						false);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean setTimeSchedulePeriodSetPoints(TestCases testCase, TestCaseInputs inputs,
			HashMap<String, String> targetSetPoints, boolean validateMinMaxSetPoints) {
		boolean flag = true;
		try {
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = devInfo.getAllowedModes();
			String coolTemp = " ";
			String heatTemp = " ";
			if (validateMinMaxSetPoints) {
				HashMap<String, String> minMaxSetPoints = devInfo.getDeviceMaxMinSetPoints();
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetCoolTemp, targetHeatTemp;
						Double temp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
							targetHeatTemp = temp.intValue();
							targetCoolTemp += 1;
							targetHeatTemp += 1;
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "AboveMaximum");

							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
							targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
							targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Maximum");

							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetHeatTemp = temp.intValue();
							targetCoolTemp -= 1;
							targetHeatTemp -= 1;
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "BelowMinimum");

							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetCoolTemp = temp.intValue();
							temp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetHeatTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Minimum");

							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else {
							HashMap<String, String> setPoints = new HashMap<String, String>();
							Double maxHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							Double maxCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxHeat, minHeat);
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							}
							setPoints.put("targetHeatTemp", heatTemp);
							setPoints.put("targetCoolTemp", coolTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									setPoints, false);
						}
					} else {
						Double targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
						Double targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetCoolTemp += 0.5;
							targetHeatTemp += 0.5;
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 0.5));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetCoolTemp -= 0.5;
							targetHeatTemp -= 0.5;
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 0.5));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							coolTemp = String.valueOf(targetCoolTemp);
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else {
							Double maxHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							Double maxCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxHeat, minHeat);
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							}
							targetSetPoints.put("targetHeatTemp", heatTemp);
							targetSetPoints.put("targetCoolTemp", coolTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetSetPoints, false);
						}
					}
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetCoolTemp;
						Double temp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetCoolTemp = temp.intValue();
							targetCoolTemp += 1;
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
							targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetCoolTemp = temp.intValue();
							targetCoolTemp -= 1;
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetCoolTemp = temp.intValue();
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							}
						} else {
							Double maxCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							HashMap<String, String> targetCoolPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
							}
							targetCoolPoints.put("targetCoolTemp", coolTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetCoolPoints, false);
						}
					} else {
						Double targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetCoolTemp += 0.5;
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp - 0.5));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							targetCoolTemp = Double.parseDouble(targetSetPoints.get("MaxCool"));
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(targetSetPoints.get("MinCool"));
							targetCoolTemp -= 0.5;
							heatTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp + 0.5));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(targetSetPoints.get("MinCool"));
							coolTemp = String.valueOf(targetCoolTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
							} else {
								flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
							}
							flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(targetCoolTemp));
								}
							}
						} else {
							Double maxCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							HashMap<String, String> targetCoolPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT,
											String.valueOf(coolTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT,
											String.valueOf(coolTemp));
								}
							}
							targetCoolPoints.put("targetCoolTemp", coolTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetCoolPoints, false);
						}
					}
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetHeatTemp;
						Double temp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetHeatTemp = temp.intValue();
							targetHeatTemp += 1;
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp - 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
							targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetHeatTemp = temp.intValue();
							targetHeatTemp -= 1;
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp + 1));
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							temp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetHeatTemp = temp.intValue();
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(targetHeatTemp));
								}
							}
						} else {
							Double maxHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxHeat, minHeat);
							HashMap<String, String> targetHeatPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
									inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
									inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
									inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
									inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
								if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
									inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
									inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
									inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
										.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
									inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
											String.valueOf(heatTemp));
								}
							}
							targetHeatPoints.put("targetHeatTemp", heatTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetHeatPoints, false);
						}
					} else {

						Double targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
						if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.ABOVE_MAXIMUM)) {
							targetHeatTemp += 0.5;
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "AboveMaximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_1)) {
										inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_2)) {
										inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_3)) {
										inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_4)) {
										inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
										inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
										inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
										inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
								}
							} else {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("1")) {
										inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("2")) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("3")) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("4")) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("5")) {
										inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("6")) {
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("7")) {
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("8")) {
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
										inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
										inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp - 0.5));
									}
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Maximum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_1)) {
										inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_2)) {
										inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_3)) {
										inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_4)) {
										inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
										inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
										inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
										inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								}
							} else {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("1")) {
										inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("2")) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("3")) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("4")) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("5")) {
										inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("6")) {
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("7")) {
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("8")) {
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
										inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
										inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							targetHeatTemp -= 0.5;
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "BelowMinimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_1)) {
										inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_2)) {
										inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_3)) {
										inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_4)) {
										inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
										inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
										inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
										inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
								}
							} else {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("1")) {
										inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("2")) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("3")) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("4")) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("5")) {
										inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("6")) {
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("7")) {
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("8")) {
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
										inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
										inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp + 0.5));
									}
								}
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							targetHeatTemp = Double.parseDouble(minMaxSetPoints.get("MinHeat"));
							heatTemp = String.valueOf(targetHeatTemp);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
									|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
											.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
							} else {
								flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
							}
							flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "Minimum");
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_1)) {
										inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_2)) {
										inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_3)) {
										inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_4)) {
										inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
										inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
										inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
										inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								}
							} else {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("1")) {
										inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("2")) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("3")) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("4")) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("5")) {
										inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("6")) {
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("7")) {
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("8")) {
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
										inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
										inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
												String.valueOf(targetHeatTemp));
									}
								}
							}
						} else {
							Double maxHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(devInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxHeat, minHeat);
							HashMap<String, String> targetHeatPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
									.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_1)) {
										inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_2)) {
										inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_3)) {
										inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_4)) {
										inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_WAKE)) {
										inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_AWAY)) {
										inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_HOME)) {
										inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.EVERYDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
								}
							} else {
								if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("1")) {
										inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("2")) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("3")) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("4")) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA).equalsIgnoreCase("5")) {
										inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("6")) {
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("7")) {
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_EMEA)
											.equalsIgnoreCase("8")) {
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
								} else {
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_HOME)) {
										inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKDAY_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
									if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_WAKE)) {
										inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_AWAY)) {
										inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_HOME)) {
										inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA)
											.equalsIgnoreCase(InputVariables.WEEKEND_SLEEP)) {
										inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT,
												String.valueOf(heatTemp));
									}
								}
							}
							targetHeatPoints.put("targetHeatTemp", heatTemp);
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetHeatPoints, false);
						}

					}
				}
			} else {
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						double tempCool = targetCoolTemp.doubleValue();
						int tempCoolValue = (int) tempCool;
						double tempHeat = targetHeatTemp.doubleValue();
						int tempHeatValue = (int) tempHeat;
						coolTemp = String.valueOf(Integer.valueOf(tempCoolValue));
						heatTemp = String.valueOf(Integer.valueOf(tempHeatValue));
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
						heatTemp = String.valueOf(targetHeatTemp);
					}

					/*
					 * if(statInfo.getThermostatUnits().equalsIgnoreCase(
					 * GlobalVariables.FAHRENHEIT)){ if(coolTemp.contains(".0")){
					 * coolTemp=coolTemp.split("\\.")[0]; } if(heatTemp.contains(".0")){
					 * heatTemp=heatTemp.split("\\.")[0]; } }
					 */

					if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
							|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
					} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
					} else {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
					}
					flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
							|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
					} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
					} else {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
					}
					flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						double tempHeat = targetHeatTemp.doubleValue();
						int tempHeatValue = (int) tempHeat;
						heatTemp = String.valueOf(Integer.valueOf(tempHeatValue));
					} else {
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						heatTemp = String.valueOf(targetHeatTemp);
					}
					/*
					 * if(heatTemp.contains(".0")){ heatTemp=heatTemp.split("\\.")[0]; }
					 */
					if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
							|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
					} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 1);
					} else {
						flag = flag & setHeatStepper(testCase, inputs, heatTemp, 0);
					}
					flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						double tempCool = targetCoolTemp.doubleValue();
						int tempCoolValue = (int) tempCool;
						coolTemp = String.valueOf(Integer.valueOf(tempCoolValue));
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
					}
					/*
					 * if(coolTemp.contains(".0")){ coolTemp=coolTemp.split("\\.")[0]; }
					 */
					if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)
							|| inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
					} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
							.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 1);
					} else {
						flag = flag & setCoolStepper(testCase, inputs, coolTemp, 0);
					}
					flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Set Schedule Period Set Points : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifyHeatStepperValue(TestCases testCase, TestCaseInputs inputs, String heatTemp,
			String verifyMinimumOrMaximum) {
		boolean flag = true;
		try {
			String heatSetPoint = " ";
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, String> minMaxSetPoints = devInfo.getDeviceMaxMinSetPoints();
			String jasperStatType = devInfo.getJasperDeviceType();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
						.findElement(By.id("scheduling_period_temp_point")).getText();
			} else {
				int size = MobileUtils.getMobElements(fieldObjects, testCase, "HeatSetPoints").size();
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					heatSetPoint = MobileUtils.getMobElements(fieldObjects, testCase, "HeatSetPoints").get(1)
							.getAttribute("value");
				} else {
					heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPoints")
							.getAttribute("value");
				}
			}

			if (verifyMinimumOrMaximum.equalsIgnoreCase("AboveMaximum")) {
				String setPoints = minMaxSetPoints.get("MaxHeat");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to max set point after trying to set it to a value above maximum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to max set point after trying to set it to a value above maximum set points");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Maximum")) {
				String setPoints = minMaxSetPoints.get("MaxHeat");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to max set point");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to max set point");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("BelowMinimum")) {
				String setPoints = minMaxSetPoints.get("MinHeat");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to min set point after trying to set it to a value below minimum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to min set point after trying to set it to a value below minimum set points");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Minimum")) {
				String setPoints = minMaxSetPoints.get("MinHeat");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to min set point");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to min set point");
				}
			} else {
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(heatSetPoint);
					heatSetPoint = String.valueOf(temp.intValue());
				}
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point Successfully set to : " + heatSetPoint);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat set point is not set to : " + heatTemp);
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean setCoolStepper(TestCases testCase, TestCaseInputs inputs, String targetCoolTemp, int index) {
		boolean flag = true;
		String coolSetPoint = "";
		WebElement coolUp = null;
		WebElement coolDown = null;
		int coolScroller = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			try {
				coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
						.findElement(By.id("scheduling_period_temp_point")).getText();
				coolUp = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
						.findElement(By.id("scheduling_temp_chooser_up"));
				coolDown = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
						.findElement(By.id("scheduling_temp_chooser_down"));
			} catch (NoSuchElementException e) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
								.findElement(By.id("scheduling_period_temp_point")).getText();
						coolUp = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
								.findElement(By.id("scheduling_temp_chooser_up"));
						coolDown = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
								.findElement(By.id("scheduling_temp_chooser_down"));
					}
				} catch (NoSuchElementException e1) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to find the cool stepper");
				}
			}

		} else {
			int size = MobileUtils.getMobElements(fieldObjects, testCase, "CoolSetPoints").size();

			if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
					&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
				coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPoints").getAttribute("value");
				coolUp = testCase.getMobileDriver().findElements(By.name("coolTemparatureUpperButton")).get(0);
				coolDown = MobileUtils.getMobElement(fieldObjects, testCase, "CoolDecrement");
			} else {
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					coolSetPoint = MobileUtils.getMobElements(fieldObjects, testCase, "CoolSetPoints").get(1)
							.getAttribute("value");
					coolUp = MobileUtils.getMobElements(fieldObjects, testCase, "CoolIncrement").get(1);
					coolDown = MobileUtils.getMobElements(fieldObjects, testCase, "CoolDecrement").get(1);
				} else {
					coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPoints")
							.getAttribute("value");
					coolUp = testCase.getMobileDriver().findElements(By.name("coolTemparatureUpperButton")).get(index);
					coolDown = MobileUtils.getMobElement(fieldObjects, testCase, "CoolDecrement");
				}
			}
		}
		if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
			Double temp = Double.parseDouble(coolSetPoint);
			coolScroller = temp.intValue() - Integer.parseInt(targetCoolTemp);
		} else {
			Double scroller = (Double.parseDouble(coolSetPoint) - Double.parseDouble(targetCoolTemp)) * 2;
			coolScroller = scroller.intValue();
		}
		flag = flag & setValueToScroller(testCase, coolScroller, coolUp, coolDown);
		return flag;
	}

	public static boolean setValueToScroller(TestCases testCase, int scroller, WebElement upButton,
			WebElement downButton) {
		boolean flag = true;
		if (scroller < 0) {
			scroller *= -1;
			for (int j = 0; j < scroller; j++) {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					TouchAction t1 = new TouchAction(testCase.getMobileDriver());
					t1.tap(upButton.getLocation().getX() + 20, upButton.getLocation().getY() + 20).perform();
				} else {
					upButton.click();
				}
			}
		} else if (scroller > 0) {
			for (int j = 0; j < scroller; j++) {
				downButton.click();
			}
		}
		return flag;
	}

	public static boolean setHeatStepper(TestCases testCase, TestCaseInputs inputs, String targetHeatTemp, int index) {
		boolean flag = true;
		String heatSetPoint = " ";
		WebElement heatUp = null;
		WebElement heatDown = null;
		int heatScroller = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			try {
				heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
						.findElement(By.id("scheduling_period_temp_point")).getText();
				heatUp = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
						.findElement(By.id("scheduling_temp_chooser_up"));
				heatDown = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
						.findElement(By.id("scheduling_temp_chooser_down"));
			} catch (NoSuchElementException e) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
								.findElement(By.id("scheduling_period_temp_point")).getText();
						heatUp = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
								.findElement(By.id("scheduling_temp_chooser_up"));
						heatDown = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPointChooser")
								.findElement(By.id("scheduling_temp_chooser_down"));
					}
				} catch (NoSuchElementException e1) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to find the cool stepper");
				}
			}
		} else {
			int size = MobileUtils.getMobElements(fieldObjects, testCase, "HeatSetPoints").size();

			if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
					&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
				heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPoints").getAttribute("value");
				heatUp = testCase.getMobileDriver().findElements(By.name("heatTemparatureUpperButton")).get(0);
				heatDown = MobileUtils.getMobElement(fieldObjects, testCase, "HeatDecrement");
			} else {
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					heatSetPoint = MobileUtils.getMobElements(fieldObjects, testCase, "HeatSetPoints").get(1)
							.getAttribute("value");
					heatUp = MobileUtils.getMobElements(fieldObjects, testCase, "HeatIncrement").get(1);
					heatDown = MobileUtils.getMobElements(fieldObjects, testCase, "HeatDecrement").get(1);
				} else {
					heatSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "HeatSetPoints")
							.getAttribute("value");
					// heatUp =
					// getMobElementWithoutVisibilityCheck(fieldObjects,
					// testCase, "HeatIncrement",false,true);
					heatUp = testCase.getMobileDriver().findElements(By.name("heatTemparatureUpperButton")).get(index);
					heatDown = MobileUtils.getMobElement(fieldObjects, testCase, "HeatDecrement");
				}
			}
		}
		if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
			Double temp = Double.parseDouble(heatSetPoint);
			heatScroller = temp.intValue() - Integer.parseInt(targetHeatTemp);
		} else {
			Double scroller = (Double.parseDouble(heatSetPoint) - Double.parseDouble(targetHeatTemp)) * 2;
			heatScroller = scroller.intValue();
		}
		flag = flag & setValueToScroller(testCase, heatScroller, heatUp, heatDown);
		return flag;
	}

	public static boolean verifyCoolStepperValue(TestCases testCase, TestCaseInputs inputs, String coolTemp,
			String verifyMinimumOrMaximum) {
		boolean flag = true;
		try {
			String coolSetPoint = "";
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			String jasperStatType = devInfo.getJasperDeviceType();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPointChooser")
						.findElement(By.id("scheduling_period_temp_point")).getText();
			} else {
				int size = MobileUtils.getMobElements(fieldObjects, testCase, "CoolSetPoints").size();
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					coolSetPoint = MobileUtils.getMobElements(fieldObjects, testCase, "CoolSetPoints").get(1)
							.getAttribute("value");
				} else {
					coolSetPoint = MobileUtils.getMobElement(fieldObjects, testCase, "CoolSetPoints")
							.getAttribute("value");
				}
			}
			HashMap<String, String> minMaxSetPoints = devInfo.getDeviceMaxMinSetPoints();
			if (verifyMinimumOrMaximum.equalsIgnoreCase("AboveMaximum")) {
				String setPoints = minMaxSetPoints.get("MaxCool");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point set to max set point after trying to set it to a value above maximum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool Set Point not set to max set point after trying to set it to a value above maximum set points");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Maximum")) {
				String setPoints = minMaxSetPoints.get("MaxCool");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point set to max set point");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool Set Point not set to max set point");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("BelowMinimum")) {
				String setPoints = minMaxSetPoints.get("MinCool");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point set to min set point after trying to set it to a value below minimum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool Set Point not set to min set point after trying to set it to a value below minimum set points");
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Minimum")) {
				String setPoints = minMaxSetPoints.get("MinCool");
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = JasperSchedulingUtils.roundOffCelsiusData(testCase,
								JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point set to min set point");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool Set Point not set to min set point");
				}
			} else {
				if (devInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(coolSetPoint);
					coolSetPoint = String.valueOf(temp.intValue());
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point is set to : " + coolSetPoint);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool set point is not set to : " + coolTemp);
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static String addHoursAndMinutesToTime(TestCases testCase, String time, boolean incrementTime, int diffHour,
			int diffMin) {
		Date returnTime = null;
		String changedTime = "";
		boolean flag = false;

		try {
			SimpleDateFormat df12 = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
			SimpleDateFormat dfHour = new SimpleDateFormat("h a");
			String dateString = time.replaceAll("\\.", "");
			if (dateString.contains("m") || dateString.contains("M") && dateString.contains(":")) {
				returnTime = df12.parse(dateString);
			} else if (dateString.contains("m") || dateString.contains("M") && !dateString.contains(":")) {
				returnTime = dfHour.parse(dateString);
				flag = true;
			} else {
				returnTime = df24.parse(dateString);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(returnTime);
			if (incrementTime) {
				calendar.add(Calendar.HOUR, diffHour);
				calendar.add(Calendar.MINUTE, diffMin);
			} else {
				calendar.add(Calendar.HOUR, -diffHour);
				calendar.add(Calendar.MINUTE, -diffMin);
			}
			returnTime = calendar.getTime();
			if (dateString.contains("m") || dateString.contains("M") && dateString.contains(":")) {
				changedTime = df12.format(returnTime);
			} else if (flag) {
				changedTime = dfHour.format(returnTime);
			} else {
				changedTime = df24.format(returnTime);
			}
		} catch (NumberFormatException e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[NumberFormatException] " + e.getMessage());
		} catch (ParseException e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[ParseException] " + e.getMessage());
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Exception] " + e.getMessage());
		}
		return changedTime;
	}

	public static boolean deletePeriodNA(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true, deletedPeriodFlag = true;
		List<WebElement> schedule_period_title, schedule_period_time = null;
		String expectedPeriodTime = "";
		Dimension dimensions ;
		int startx, starty, endx, endy;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			schedule_period_title = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTitle");
			schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTime");
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
					dimensions = testCase.getMobileDriver().manage().window().getSize();
					 startx = (dimensions.width * 20) / 100;
					 starty = (dimensions.height * 62) / 100;
					 endx = (dimensions.width * 22) / 100;
					 endy = (dimensions.height * 35) / 100;
					testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
					testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
					List<WebElement> weekdayschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
							"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout[1]/android.widget.TextView");
					
					for (int i = 1; i <= (weekdayschedule_period_title.size()); i++) {
						if (String.valueOf(i)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = MobileUtils
									.getMobElement(testCase, "xpath",
											"//android.widget.FrameLayout[" + i
													+ "]//*[contains(@content-desc,'_Monday - Friday')]/android.widget.TextView[1]")
									.getText();
							try {
								weekdayschedule_period_title.get(i - 1).click();
								Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i));
								break;
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[SelectPeriodToDelete] Failed to select Period-" + (i));
							}
						}
					}
				} else {
					dimensions = testCase.getMobileDriver().manage().window().getSize();
					startx = (dimensions.width * 20) / 100;
					starty = (dimensions.height * 62) / 100;
					endx = (dimensions.width * 22) / 100;
					endy = (dimensions.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					List<WebElement> weekendschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
							"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout[1]/android.widget.TextView");

					for (int i = 5; i <= (weekendschedule_period_title.size() * 2); i++) {
						if (String.valueOf(i)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							if (MobileUtils.isMobElementExists("Xpath",
									"//android.widget.FrameLayout[" + (i)
											+ "]//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]",
									testCase, 5)) {
								expectedPeriodTime = MobileUtils
										.getMobElement(testCase, "xpath",
												"//android.widget.FrameLayout[" + (i)
														+ "]//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]")
										.getText();
							}
							try {
								weekendschedule_period_title.get(i - 5).click();
								Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i));
								break;
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[SelectPeriodToDelete] Failed to select Period-" + (i));
							}
						}
					}
				}

			} else {
				for (int i = 1; i <= schedule_period_title.size(); i++) {
					if (String.valueOf(i).equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
						expectedPeriodTime = schedule_period_time.get(i - 1).getText();
						try {
							schedule_period_title.get(i - 1).click();
							Keyword.ReportStep_Pass(testCase,
									"Selected Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
							break;
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SelectPeriodToDelete] Failed to select Period-"
											+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
						}
					}
				}
			}
		} else {
			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {

					try {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
						TimeUnit.SECONDS.sleep(3);
					} catch (Exception e1) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error while/after scrolling up: " + e1.getMessage());
					}

					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekdayPeriodTime", 5)) {
						schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "WeekdayPeriodTime");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekday schedule period times");
					}
					for (int i = 1; i <= schedule_period_time.size(); i++) {
						if (String.valueOf(i)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
							try {
								schedule_period_time.get(i - 1).click();
								Keyword.ReportStep_Pass(testCase,
										"Selected period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
												+ " Error message: " + e.getMessage());
							}
						}
					}
				} else {
					try {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
						TimeUnit.SECONDS.sleep(3);
					} catch (Exception e1) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error while/after scrolling down: " + e1.getMessage());
					}

					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendPeriodTime", 5)) {
						schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "WeekendPeriodTime");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to locate Weekend schedule period times");
					}
					for (int i = 1; i <= schedule_period_time.size(); i++) {
						if (String.valueOf(i + 4)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
							try {
								schedule_period_time.get(i - 1).click();
								Keyword.ReportStep_Pass(testCase,
										"Selected period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
												+ " Error message: " + e.getMessage());
							}
						}
					}
				}
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EverydayTime", 5)) {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate Everyday schedule period times");
				}
				for (int i = 1; i <= schedule_period_time.size(); i++) {
					if (String.valueOf(i).equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
						expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
						try {
							schedule_period_time.get(i - 1).click();
							Keyword.ReportStep_Pass(testCase,
									"Selected period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
							break;
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
											+ " Error message: " + e.getMessage());
						}
					}
				}
			}
		}

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "PeriodDeleteIcon")) {
				flag = false;
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.isMobElementExists("XPATH", "//*[@text='Delete']", testCase, 5)) {
						if (!MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='Delete']")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to find Confirm Delete button");
					}
				} else {
					if (MobileUtils.isMobElementExists("name", "DELETE", testCase, 5)) {
						if (!MobileUtils.clickOnElement(testCase, "name", "DELETE")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to find Confirm Delete button");
					}
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to find Delete icon");
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				// TODO

			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SchedulePeriodTime", 5)) {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTime");
					for (WebElement elem : schedule_period_time) {
						if (elem.getText().equalsIgnoreCase(expectedPeriodTime)) {
							flag = false;
							deletedPeriodFlag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Deleted period still shown after Deleting");
							break;
						}
					}
				}
			}
		} else {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				// TODO
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EverydayTime", 5)) {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
					for (WebElement elem : schedule_period_time) {
						if (elem.getAttribute("value").equalsIgnoreCase(expectedPeriodTime)) {
							flag = false;
							deletedPeriodFlag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Deleted period still shown after Deleting");
							break;
						}
					}
				}
			}
		}
		if (deletedPeriodFlag) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully deleted the Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("4")) {
					inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, "Tap to set");
				}
			} else {
				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					inputs.setInputValue(InputVariables.WEEKDAY_WAKE_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.WEEKDAY_AWAY_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.WEEKDAY_HOME_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("4")) {
					inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_TIME, "Tap to set");
				}
				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("5")) {
					inputs.setInputValue(InputVariables.WEEKEND_WAKE_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("6")) {
					inputs.setInputValue(InputVariables.WEEKEND_AWAY_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("7")) {
					inputs.setInputValue(InputVariables.WEEKEND_HOME_TIME, "Tap to set");
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("8")) {
					inputs.setInputValue(InputVariables.WEEKEND_SLEEP_TIME, "Tap to set");
				}

			}
		}
		return flag;
	}

	public static boolean deletePeriodEMEA(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true, deletedPeriodFlag = true;
		List<WebElement> schedule_period_title, schedule_period_time;
		String expectedPeriodTime = "";
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			schedule_period_title = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTitle");
			schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTime");
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
					Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
					int startx = (dimensions.width * 20) / 100;
					int starty = (dimensions.height * 62) / 100;
					int endx = (dimensions.width * 22) / 100;
					int endy = (dimensions.height * 35) / 100;
					testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
					List<WebElement> weekdayschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
							"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout[1]/android.widget.TextView");
					for (int i = 1; i <= (weekdayschedule_period_title.size()); i++) {
						if (weekdayschedule_period_title.get(i - 1).getText()
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = MobileUtils
									.getMobElement(testCase, "xpath",
											"//android.widget.FrameLayout[" + i
													+ "]//*[contains(@content-desc,'_Monday - Friday')]/android.widget.TextView[1]")
									.getText();
							try {
								weekdayschedule_period_title.get(i - 1).click();
								Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i));
								break;
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[SelectPeriodToDelete] Failed to select Period-" + (i));
							}
						}
					}
				} else {
					Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
					int startx = (dimensions.width * 20) / 100;
					int starty = (dimensions.height * 62) / 100;
					int endx = (dimensions.width * 22) / 100;
					int endy = (dimensions.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					List<WebElement> weekendschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
							"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout[1]/android.widget.TextView");

					for (int i = 1; i <= weekendschedule_period_title.size(); i++) {
						if (String.valueOf((Integer.parseInt(weekendschedule_period_title.get(i - 1).getText()) + 4))
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = MobileUtils
									.getMobElement(testCase, "xpath",
											"//android.widget.FrameLayout[" + i
													+ "]//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]")
									.getText();
							try {
								weekendschedule_period_title.get(i - 1).click();
								Keyword.ReportStep_Pass(testCase, "Selected Period-"
										+ (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)));
								break;
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[SelectPeriodToDelete] Failed to select Period-"
												+ (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)));
							}
						}
					}
				}
			} else {

				for (int i = 0; i < schedule_period_title.size(); i++) {
					if (schedule_period_title.get(i).getText()
							.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
						expectedPeriodTime = schedule_period_time.get(i).getText();
						try {
							schedule_period_title.get(i).click();
							Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i + 1));
							break;
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SelectPeriodToDelete] Failed to select Period-" + (i + 1));
						}
					}
				}
			}
		} else {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					List<WebElement> weekdayschedule_period_title = null, weekendschedule_period_title = null;

					Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
					TouchAction action = new TouchAction(testCase.getMobileDriver());
					action.press(10, (int) (dimension.getHeight() * .5)).moveTo(0, (int) (dimension.getHeight() * .3))
							.release().perform();

					if (MobileUtils.isMobElementExists("xpath",
							"//*[contains(@name,'Monday - Friday_') and contains(@name,'_Time')]", testCase, 5)) {
						weekdayschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@name,'Monday - Friday_') and contains(@name,'_Time')]");
					}

					action.press(10, (int) (dimension.getHeight() * .5)).moveTo(0, (int) (dimension.getHeight() * -.3))
							.release().perform();

					if (MobileUtils.isMobElementExists("xpath",
							"//*[contains(@name,'Saturday - Sunday_') and contains(@name,'_Time')]", testCase, 5)) {
						weekendschedule_period_title = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@name,'Saturday - Sunday_') and contains(@name,'_Time')]");
					}

					String expectedPeriod;
					if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {

						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .3)).release().perform();

						schedule_period_time = weekdayschedule_period_title;
						expectedPeriod = "Monday - Friday_";
						for (int i = 1; i <= schedule_period_time.size(); i++) {
							String temp = MobileUtils.getMobElement(testCase, "name", expectedPeriod + i)
									.getAttribute("name");
							temp = temp.split("_")[1];
							if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
								expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
								try {
									schedule_period_time.get(i - 1).click();
									break;
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
													+ " Error message: " + e.getMessage());
								}
							}
						}
					} else {
						action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.3)).release().perform();

						schedule_period_time = weekendschedule_period_title;
						expectedPeriod = "Saturday - Sunday_";
						for (int i = 1; i <= schedule_period_time.size(); i++) {
							String temp = MobileUtils.getMobElement(testCase, "name", expectedPeriod + i)
									.getAttribute("name");
							temp = temp.split("_")[1];
							if (String.valueOf((Integer.parseInt(temp) + 4))
									.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
								expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
								try {
									schedule_period_time.get(i - 1).click();
									break;
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
													+ " Error message: " + e.getMessage());
								}
							}
						}
					}

				} else {
					schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
					for (int i = 1; i <= schedule_period_time.size(); i++) {
						String temp = MobileUtils.getMobElement(testCase, "name", "Everyday_" + i).getAttribute("name");
						temp = temp.split("_")[1];
						if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
							try {
								schedule_period_time.get(i - 1).click();
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
												+ " Error message: " + e.getMessage());
							}
						}
					}
				}
			} else {
				schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
				for (int i = 1; i <= schedule_period_time.size(); i++) {
					String temp = MobileUtils.getMobElement(testCase, "name", "Everyday_" + i).getAttribute("name");
					temp = temp.split("_")[1];
					if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
						expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
						try {
							schedule_period_time.get(i - 1).click();
							break;
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
											+ " Error message: " + e.getMessage());
						}
					}
				}
			}
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='When do you want the temperature to change?']",
					testCase, 5)) {
				Keyword.ReportStep_Pass(testCase,
						"Period edit screen is shown: When do you want the temperature to change?");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Period edit screen is not shown");
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//UIAStaticText[@value='When do you want the temperature to change?']", testCase, 5)) {
				Keyword.ReportStep_Pass(testCase,
						"Period edit screen is shown: When do you want the temperature to change?");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Period edit screen is not shown");
			}
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "PeriodDeleteIcon")) {
				flag = false;
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.isMobElementExists("XPATH", "//*[@text='Delete']", testCase, 5)) {
						if (!MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='Delete']")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to find Confirm Delete button");
					}
				} else {
					if (MobileUtils.isMobElementExists("xpath",
							"//XCUIElementTypeAlert[@name='Confirm Delete']//XCUIElementTypeButton[@name='DELETE']",
							testCase, 5)) {
						if (!MobileUtils.clickOnElement(testCase, "xpath",
								"//XCUIElementTypeAlert[@name='Confirm Delete']//XCUIElementTypeButton[@name='DELETE']")) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to find Confirm Delete button");
					}
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to find Delete icon");
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
				int startx = (dimensions.width * 20) / 100;
				int starty = (dimensions.height * 62) / 100;
				int endx = (dimensions.width * 22) / 100;
				int endy = (dimensions.height * 35) / 100;
				testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
				// List<WebElement> weekdayschedule_period_title =
				// MobileUtils.getMobElements(testCase,"xpath","//*[contains(@content-desc,'_Monday-Friday')]//android.widget.RelativeLayout/android.widget.TextView");
				// List<WebElement> weekdayschedule_period_time =
				// MobileUtils.getMobElements(testCase,"xpath","//*[contains(@content-desc,'_Monday-Friday')]/android.widget.TextView[1]");
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
					/*
					 * for (int i = 0; i <weekdayschedule_period_title.size();
					 * i++) { if (weekdayschedule_period_title.get(i).getText()
					 * .equalsIgnoreCase(inputs.getInputValue(
					 * PERIOD_NUMBER_EMEA_TO_DELETE))) { expectedPeriodTime =
					 * weekdayschedule_period_time.get(i).getText(); try {
					 * weekdayschedule_period_title.get(i).click();
					 * Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i
					 * + 1)); break; } catch (Exception e) { flag = false;
					 * Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "[SelectPeriodToDelete] Failed to select Period-" + (i +
					 * 1)); } } }
					 */
				} else {
					dimensions = testCase.getMobileDriver().manage().window().getSize();
					startx = (dimensions.width * 20) / 100;
					starty = (dimensions.height * 62) / 100;
					endx = (dimensions.width * 22) / 100;
					endy = (dimensions.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					// List<WebElement> weekendschedule_period_title =
					// MobileUtils.getMobElements(testCase,"xpath","//*[contains(@content-desc,'_Saturday-Sunday')]//android.widget.RelativeLayout/android.widget.TextView");
					// List<WebElement> weekendschedule_period_time =
					// MobileUtils.getMobElements(testCase,"xpath","//*[contains(@content-desc,'_Saturday-Sunday')]/android.widget.TextView[1]");

					/*
					 * for (int i = 0; i <weekendschedule_period_title.size();
					 * i++) { if (weekendschedule_period_title.get(i).getText()
					 * .equalsIgnoreCase(inputs.getInputValue(
					 * PERIOD_NUMBER_EMEA_TO_DELETE))) { expectedPeriodTime =
					 * weekendschedule_period_time.get(i).getText(); try {
					 * weekendschedule_period_title.get(i).click();
					 * Keyword.ReportStep_Pass(testCase, "Selected Period-" + (i
					 * + 1)); break; } catch (Exception e) { flag = false;
					 * Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "[SelectPeriodToDelete] Failed to select Period-" + (i +
					 * 1)); } } }
					 */
				}
			} else {
				schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTime");
				for (WebElement elem : schedule_period_time) {
					if (elem.getText().equalsIgnoreCase(expectedPeriodTime)) {
						flag = false;
						deletedPeriodFlag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Deleted period still shown after Deleting");
						break;
					}
				}
			}
		} else {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {

			} else {
				schedule_period_time = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
				for (WebElement elem : schedule_period_time) {
					if (elem.getAttribute("value").equalsIgnoreCase(expectedPeriodTime)) {
						flag = false;
						deletedPeriodFlag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Deleted period still shown after Deleting");
						break;
					}
				}
			}
		}
		if (deletedPeriodFlag) {
			Keyword.ReportStep_Pass(testCase,
					"Succesfully deleted the Period-" + inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, InputVariables.EVERYDAY_2_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, InputVariables.EVERYDAY_3_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, InputVariables.EVERYDAY_3_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				}
			} else {

				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					// 1, 2,3, 4
					inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_1_TIME, inputs.getInputValue(InputVariables.WEEKDAY_2_TIME));
					System.out.println("1 " + inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_1_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
					inputs.setInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));

				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("5")) {
					// 1, 2,3, 4
					inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_1_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_1_TIME, inputs.getInputValue(InputVariables.WEEKEND_2_TIME));
					System.out.println("1 " + inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT) + " " + InputVariables.WEEKEND_2_TIME);
					inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_TIME, inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME, inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("6")) {
					inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_TIME, inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME, inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("7")) {
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT, inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME, inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
				}

			}
		}
		return flag;
	}
	
	public static boolean verifyDisplayedScheduleOnPrimaryCard(TestCases testCase, TestCaseInputs inputs,
			String scheduleType) {
		List<WebElement> schedule_setpoints, schedule_heatsetpoints, schedule_periodtimes = null,
				schedule_periodtimes_weekday = null, schedule_periodtimes_weekend = null,
				schedule_weekday_heatsetpoints = null, schedule_weekend_heatsetpoints = null,
				schedule_weekday_coolsetpoints = null, schedule_weekend_coolsetpoints = null,
				schedule_periodtime = null;
		WebElement homeHeatSetPointIOS, sleepHeatSetPointIOS, awayHeatSetPointIOS;
		String tempHeatSetPointApp = "", tempCoolSetPointApp = "", tempHeatSetPointFromInputs = "",
				SleepStartEndTime = "", SleepStartTime = "", SleepEndTime = "";
		String dateString = "", tempTime = "", tempTimeInputs = "";
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		flag = flag & viewScheduleOnPrimaryCard(testCase);

		if (scheduleType.equalsIgnoreCase("time")) {
			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying time based schedule on Primary Card **************************");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Every Day']", testCase, 5)) {
						try {
							testCase.getMobileDriver().findElement(By.xpath("//*[@text='Every Day']"));
							Keyword.ReportStep_Pass(testCase,
									"Verify Displayed Schedule : Everyday text displayed on schedule screen");
							schedule_periodtimes = MobileUtils.getMobElements(fieldObjects, testCase,
									"SchedulePeriodTime");
							schedule_heatsetpoints = MobileUtils.getMobElements(fieldObjects, testCase,
									"SchedulePeriodHeatSetPoint");
							if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
								for (int i = 1; i <= schedule_periodtimes.size(); i++) {
									Keyword.ReportStep_Pass(testCase,
											"*********************** Verifying schedule period time and schedule period heat set points against set values **************************");
									dateString = schedule_periodtimes.get(i - 1).getText().replaceAll("\\.", "");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (i == 1) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 1's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 1's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									} else if (i == 2) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 2's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 2's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									} else if (i == 3) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 3's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 3's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									} else if (i == 4) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 4's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 4's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									} else if (i == 5) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 5's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 5's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									} else if (i == 6) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT).contains(".0")) {
											tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT)
													.split("\\.")[0];
										}
										tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText();
										if (schedule_heatsetpoints.get(i - 1).getText().contains(".0")) {
											tempHeatSetPointApp = schedule_heatsetpoints.get(i - 1).getText()
													.split("\\.")[0];
										}
										tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_TIME);
										try {
											if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
												tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										if (tempTimeInputs.equalsIgnoreCase(tempTime)
												&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period 6's expected time and heat set point are shown correctly: "
															+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period 6's expected time and heat set point: " + tempTimeInputs
															+ " " + tempHeatSetPointFromInputs
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp);
										}
									}
								}
							} else if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA")) {
								/*
								 * schedule_coolsetpoints =
								 * MobileUtils.getMobElements(fieldObjects,
								 * testCase, "SchedulePeriodCoolSetPoint");
								 */
								List<WebElement> schedule_everydaytitle = MobileUtils.getMobElements(fieldObjects,
										testCase, "EverydayTitleList");
								for (int i = 0; i < schedule_periodtimes.size(); i++) {
									Keyword.ReportStep_Pass(testCase,
											"*********************** Verifying schedule period time and schedule period heat set points against set values **************************");
									dateString = MobileUtils
											.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ schedule_everydaytitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
											.replaceAll("\\.", "");
									tempTime = dateString;
									if (i == 0) {
										if (!MobileUtils
												.getFieldValue(testCase, "xpath",
														"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																+ schedule_everydaytitle.get(i).getText()
																+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
												.equalsIgnoreCase("Tap to set")) {

											try {
												if (!dateString.contains("m") && !dateString.contains("M")) {
													final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
													final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
													tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
												}
											} catch (NumberFormatException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[NumberFormatException] Error message: " + e.getMessage());
											} catch (ParseException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[ParseException] Error message: " + e.getMessage());
											} catch (Exception e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[Exception] Error message: " + e.getMessage());
											}

											/*
											 * System.out.println(inputs.
											 * getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
											 * ); System.out.println(tempTime);
											 * System.out.println(inputs.
											 * getInputValue(
											 * InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
											 */
											System.out.println(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'"
															+ schedule_everydaytitle.get(i).getText()
															+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"));
											if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME).equalsIgnoreCase(tempTime)
													&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
													&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period WAKE's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period WAKE's expected time and heat and cool set points: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
																+ " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																	+ schedule_everydaytitle.get(i).getText()
																	+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period WAKE's expected time is shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period WAKE's expected time: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
																+ " is not shown correctly: "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
											}
										}
									} else if (i == 1) {
										if (!MobileUtils
												.getFieldValue(testCase, "xpath",
														"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																+ schedule_everydaytitle.get(i).getText()
																+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
												.equalsIgnoreCase("Tap to set")) {
											try {
												if (!dateString.contains("m") && !dateString.contains("M")) {
													final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
													final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
													tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
												}
											} catch (NumberFormatException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[NumberFormatException] Error message: " + e.getMessage());
											} catch (ParseException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[ParseException] Error message: " + e.getMessage());
											} catch (Exception e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[Exception] Error message: " + e.getMessage());
											}
											if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME).equalsIgnoreCase(tempTime)
													&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
													&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period AWAY's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period AWAY's expected time and heat and cool set points: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
																+ " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));

											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																	+ schedule_everydaytitle.get(i).getText()
																	+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period AWAY's expected time is shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period AWAY's expected time: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
																+ " is not shown correctly: "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
											}
										}
									} else if (i == 2) {
										if (!MobileUtils
												.getFieldValue(testCase, "xpath",
														"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																+ schedule_everydaytitle.get(i).getText()
																+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
												.equalsIgnoreCase("Tap to set")) {
											try {
												if (!dateString.contains("m") && !dateString.contains("M")) {
													final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
													final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
													tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
												}
											} catch (NumberFormatException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[NumberFormatException] Error message: " + e.getMessage());
											} catch (ParseException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[ParseException] Error message: " + e.getMessage());
											} catch (Exception e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[Exception] Error message: " + e.getMessage());
											}
											if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME).equalsIgnoreCase(tempTime)
													&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
													&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period HOME's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period HOME's expected time and heat and cool set points: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
																+ " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));

											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																	+ schedule_everydaytitle.get(i).getText()
																	+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period HOME's expected time is shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period HOME's expected time: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
																+ " is not shown correctly: "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
											}
										}
									} else if (i == 3) {
										if (!MobileUtils
												.getFieldValue(testCase, "xpath",
														"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																+ schedule_everydaytitle.get(i).getText()
																+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
												.equalsIgnoreCase("Tap to set")) {
											try {
												if (!dateString.contains("m") && !dateString.contains("M")) {
													final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
													final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
													tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
												}
											} catch (NumberFormatException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[NumberFormatException] Error message: " + e.getMessage());
											} catch (ParseException e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[ParseException] Error message: " + e.getMessage());
											} catch (Exception e) {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"[Exception] Error message: " + e.getMessage());
											}
											if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME).equalsIgnoreCase(tempTime)
													&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
													&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
															.equalsIgnoreCase(MobileUtils.getFieldValue(testCase,
																	"xpath", "//*[contains(@content-desc,'"
																			+ schedule_everydaytitle.get(i).getText()
																			+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period SLEEP's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period SLEEP's expected time and heat and cool set points: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME) + " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
																+ " "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "_Everyday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));

											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																	+ schedule_everydaytitle.get(i).getText()
																	+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
												Keyword.ReportStep_Pass(testCase,
														"Period SLEEP's expected time is shown correctly: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period SLEEP's expected time: "
																+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
																+ " is not shown correctly: "
																+ MobileUtils.getFieldValue(testCase, "xpath",
																		"//*[contains(@content-desc,'_Everyday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																				+ schedule_everydaytitle.get(i)
																						.getText()
																				+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
											}
										}

									}
								}
							}

						} catch (NoSuchElementException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Displayed Schedule : Everyday text not displayed on schedule screen");
						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Everyday text not displayed on schedule screen");
					}
				} else {
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Monday - Friday']", testCase, 5)) {
						try {
							testCase.getMobileDriver().findElement(By.xpath("//*[@text='Monday - Friday']"));
							Keyword.ReportStep_Pass(testCase,
									"Verify Displayed Schedule : Monday-Friday text displayed on schedule screen");
						} catch (NoSuchElementException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Displayed Schedule : Monday-Friday text not displayed on schedule screen");
						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Monday-Friday text not displayed on schedule screen");
					}
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Saturday - Sunday']", testCase, 5)) {
						try {
							testCase.getMobileDriver().findElement(By.xpath("//*[@text='Saturday - Sunday']"));
							Keyword.ReportStep_Pass(testCase,
									"Verify Displayed Schedule : Saturday-Sunday text displayed on schedule screen");
						} catch (NoSuchElementException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify Displayed Schedule : Saturday-Sunday text not displayed on schedule screen");
						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Saturday-Sunday text not displayed on schedule screen");
					}

					// start//////////////////////////
					if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
						List<WebElement> weekdayschedule_periodtimes = MobileUtils.getMobElements(fieldObjects,
								testCase, "WeekdayTimeList");
						List<WebElement> weekdayschedule_periodheatsetPoint = MobileUtils.getMobElements(fieldObjects,
								testCase, "WeekdayHeatSetpointListEMEA");
						Keyword.ReportStep_Pass(testCase,
								"*********************** Verifying Weekday-Weekend schedule period time and schedule period heat set points against set values **************************");

						for (int i = 0; i < weekdayschedule_periodtimes.size(); i++) {
							dateString = weekdayschedule_periodtimes.get(i).getText().replaceAll("\\.", "");
							tempTime = dateString;
							try {
								if (!dateString.contains("m") && !dateString.contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 0) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(0).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 1's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 1's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 1) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(1).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 2's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 2's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 2) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(2).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 3's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 3's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 3) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(3).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 4's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 4's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 4) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(4).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_5_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 5's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 5's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 5) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekdayschedule_periodheatsetPoint.get(5).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_6_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekday]Period 6's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekday]Period 6's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							}

						}
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);

						List<WebElement> weekendschedule_periodtimes = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.TextView[1]");
						List<WebElement> weekendschedule_periodsetPoint = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout[2]/android.widget.TextView");
						for (int i = 0; i < weekendschedule_periodtimes.size(); i++) {
							dateString = weekendschedule_periodtimes.get(i).getText().replaceAll("\\.", "");
							tempTime = dateString;
							try {
								if (!dateString.contains("m") && !dateString.contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 0) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(0).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_1_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 1's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 1's expected Weekend time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 1) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(1).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_2_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 2's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 2's expected Weekend time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 2) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(2).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_3_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 3's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 3's expected Weekend time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 3) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(3).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_4_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 4's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 4's expected Weekend time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							}

							else if (i == 4) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(4).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_5_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 5's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 5's expected weekday time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							} else if (i == 5) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = weekendschedule_periodsetPoint.get(5).getText();
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_6_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"[Weekend]Period 6's expected weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Weekend]Period 6's expected weekend time and heat set point: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs
													+ " are not shown correctly: " + tempTime + " "
													+ tempHeatSetPointApp);
								}
							}
						}
					}
					// end/////////////////////////
					else if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA")) {
						List<WebElement> weekdayschedule_periodtitle = MobileUtils.getMobElements(fieldObjects,
								testCase, "WeekdayTitleList");
						List<WebElement> weekendschedule_periodtitle = MobileUtils.getMobElements(fieldObjects,
								testCase, "WeekendTitleList");
						Keyword.ReportStep_Pass(testCase,
								"*********************** Verifying Weekday-Weekend schedule period time and schedule period heat set points against set values **************************");

						for (int i = 0; i < weekdayschedule_periodtitle.size(); i++) {
							dateString = MobileUtils
									.getFieldValue(testCase, "xpath",
											"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
													+ weekdayschedule_periodtitle.get(i).getText()
													+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
									.replaceAll("\\.", "");
							tempTime = dateString;
							if (i == 0) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekdayschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekdayschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 1) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekdayschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekdayschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 2) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekdayschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekdayschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 3) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekdayschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekdayschedule_periodtitle.get(i).getText()
																	+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "_Monday - Friday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekdayschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Monday - Friday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekdayschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							}
						}

						for (int i = 0; i < weekendschedule_periodtitle.size(); i++) {
							dateString = MobileUtils
									.getFieldValue(testCase, "xpath",
											"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
													+ weekendschedule_periodtitle.get(i).getText()
													+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
									.replaceAll("\\.", "");
							tempTime = dateString;
							if (i == 0) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekendschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekendschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 1) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekendschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekendschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 2) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekendschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekendschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							} else if (i == 3) {
								if (!MobileUtils
										.getFieldValue(testCase, "xpath",
												"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
														+ weekendschedule_periodtitle.get(i).getText()
														+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView")
										.equalsIgnoreCase("Tap to set")) {
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]"))
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
													.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
															"//*[contains(@content-desc,'"
																	+ weekendschedule_periodtitle.get(i).getText()
																	+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[2]")
														+ " "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "_Saturday - Sunday')]//android.widget.LinearLayout[2]/android.widget.TextView[1]"));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
											.equalsIgnoreCase(MobileUtils.getFieldValue(testCase, "xpath",
													"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
															+ weekendschedule_periodtitle.get(i).getText()
															+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
														+ " is not shown correctly: "
														+ MobileUtils.getFieldValue(testCase, "xpath",
																"//*[contains(@content-desc,'_Saturday - Sunday')]/android.widget.LinearLayout/android.widget.TextView[@text='"
																		+ weekendschedule_periodtitle.get(i).getText()
																		+ "']/parent::android.widget.LinearLayout/following-sibling::android.widget.TextView"));
									}
								}
							}
						}
					}
				}
			} else {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					try {
						if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists("xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
									testCase, 6)) {
								schedule_periodtime = MobileUtils.getMobElements(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Everyday schedule period times");
								return flag;
							}
							for (int i = 1; i <= schedule_periodtime.size(); i++) {
								Keyword.ReportStep_Pass(testCase,
										"*********************** Verifying schedule period time and schedule period heat set points against set values **************************");
								dateString = schedule_periodtime.get(i - 1).getAttribute("value").replaceAll("\\.", "");
								tempTime = dateString;
								try {
									if (!dateString.contains("m") && !dateString.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
										tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (i == 1) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_1_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 1's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 1's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								} else if (i == 2) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_2_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 2's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 2's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								} else if (i == 3) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_3_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 3's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 3's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								} else if (i == 4) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_4_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 4's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 4's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								} else if (i == 5) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_5_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_5_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 5's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 5's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								} else if (i == 6) {
									tempHeatSetPointApp = MobileUtils
											.getMobElement(testCase, "name", "Everyday_6_HeatTemperature")
											.getAttribute("value");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT)
												.split("\\.")[0];
									}
									tempTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_6_TIME);
									try {
										if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
											tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (tempTimeInputs.equalsIgnoreCase(tempTime)
											&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
										Keyword.ReportStep_Pass(testCase,
												"Period 6's expected time and heat set point are shown correctly: "
														+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period 6's expected time and heat set point: " + tempTimeInputs + " "
														+ tempHeatSetPointFromInputs + " are not shown correctly: "
														+ tempTime + " " + tempHeatSetPointApp);
									}
								}
							}
						} else if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA")) {
							if (MobileUtils.isMobElementExists("xpath",
									"//XCUIElementTypeStaticText[contains(@name,'_Time')]", testCase, 6)) {
								schedule_periodtime = MobileUtils.getMobElements(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'_Time')]");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Everyday schedule period times");
								return flag;
							}

							for (int i = 0; i < schedule_periodtime.size(); i++) {
								Keyword.ReportStep_Pass(testCase,
										"*********************** Verifying schedule period time and schedule period heat set points against set values **************************");
								dateString = schedule_periodtime.get(i).getAttribute("value");
								tempTime = dateString;
								if (i == 0) {
									if (!tempTime.equalsIgnoreCase("Tap to set")) {
										try {
											if (!dateString.contains("m") && !dateString.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
												tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										tempHeatSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Wake_HeatTemperature")
												.getAttribute("value");
										tempCoolSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Wake_CoolTemperature")
												.getAttribute("value");
										if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME).equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
														.equalsIgnoreCase(tempHeatSetPointApp)
												&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
														.equalsIgnoreCase(tempCoolSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period WAKE's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period WAKE's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME).equalsIgnoreCase(tempTime)) {
											Keyword.ReportStep_Pass(testCase,
													"Period WAKE's expected time is shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period WAKE's expected time: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
															+ " is not shown correctly: " + tempTime);
										}
									}
								} else if (i == 1) {
									if (!tempTime.equalsIgnoreCase("Tap to set")) {
										try {
											if (!dateString.contains("m") && !dateString.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
												tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										tempHeatSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Away_HeatTemperature")
												.getAttribute("value");
										tempCoolSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Away_CoolTemperature")
												.getAttribute("value");
										if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME).equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
														.equalsIgnoreCase(tempHeatSetPointApp)
												&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
														.equalsIgnoreCase(tempCoolSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period AWAY's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period AWAY's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME).equalsIgnoreCase(tempTime)) {
											Keyword.ReportStep_Pass(testCase,
													"Period AWAY's expected time is shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period AWAY's expected time: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
															+ " is not shown correctly: " + tempTime);
										}
									}
								} else if (i == 2) {
									if (!tempTime.equalsIgnoreCase("Tap to set")) {
										try {
											if (!dateString.contains("m") && !dateString.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
												tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										tempHeatSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Home_HeatTemperature")
												.getAttribute("value");
										tempCoolSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Home_CoolTemperature")
												.getAttribute("value");
										if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME).equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
														.equalsIgnoreCase(tempHeatSetPointApp)
												&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
														.equalsIgnoreCase(tempCoolSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period HOME's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period HOME's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime

															+ " " + tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME).equalsIgnoreCase(tempTime)) {
											Keyword.ReportStep_Pass(testCase,
													"Period HOME's expected time is shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period HOME's expected time: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
															+ " is not shown correctly: " + tempTime);
										}
									}
								} else if (i == 3) {
									if (!tempTime.equalsIgnoreCase("Tap to set")) {
										try {
											if (!dateString.contains("m") && !dateString.contains("M")) {
												final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
												final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
												tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
											}
										} catch (NumberFormatException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[NumberFormatException] Error message: " + e.getMessage());
										} catch (ParseException e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[ParseException] Error message: " + e.getMessage());
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[Exception] Error message: " + e.getMessage());
										}
										tempHeatSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Sleep_HeatTemperature")
												.getAttribute("value");
										tempCoolSetPointApp = MobileUtils
												.getMobElement(testCase, "name", "Everyday_Sleep_CoolTemperature")
												.getAttribute("value");
										if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME).equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
														.equalsIgnoreCase(tempHeatSetPointApp)
												&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
														.equalsIgnoreCase(tempCoolSetPointApp)) {
											Keyword.ReportStep_Pass(testCase,
													"Period SLEEP's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period SLEEP's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT) + " "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime

															+ " " + tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME).equalsIgnoreCase(tempTime)) {
											Keyword.ReportStep_Pass(testCase,
													"Period SLEEP's expected time is shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period SLEEP's expected time: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
															+ " is not shown correctly: " + tempTime);
										}
									}
								}
							}
						}
					} catch (NoSuchElementException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Everyday text not displayed on schedule screen");
					}
				} else {
					if (!inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
						if (MobileUtils.isMobElementExists("xpath",
								"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
								testCase, 5)) {
							schedule_periodtimes_weekday = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
							schedule_periodtimes_weekend = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
							schedule_weekday_heatsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_HeatTemperature')]");
							schedule_weekend_heatsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_HeatTemperature')]");
							schedule_weekday_coolsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_CoolTemperature')]");
							schedule_weekend_coolsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_CoolTemperature')]");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekday and Weekend schedule period times");
							return flag;
						}
						Keyword.ReportStep_Pass(testCase,
								"*********************** Verifying Weekday-Weekend schedule period time and schedule period heat set points against set values **************************");

						int j = 0;
						for (int i = 1; i <= schedule_periodtimes_weekday.size(); i++) {
							if (i == 1) {
								if (!schedule_periodtimes_weekday.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekday.get(0).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekday_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekday
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 2) {
								if (!schedule_periodtimes_weekday.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekday.get(1).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekday_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekday
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 3) {
								if (!schedule_periodtimes_weekday.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekday.get(2).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekday_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekday
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 4) {
								if (!schedule_periodtimes_weekday.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekday.get(3).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekday_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekday
																.get(i - 1).getAttribute("value"));
									}
								}
							}

						}
						j = 0;
						for (int i = 1; i <= schedule_periodtimes_weekend.size(); i++) {
							if (i == 1) {
								if (!schedule_periodtimes_weekend.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekend.get(0).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekend_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekend
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 2) {
								if (!schedule_periodtimes_weekend.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekend.get(1).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekend_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekend
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 3) {
								if (!schedule_periodtimes_weekend.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekend.get(2).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekend_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekend
																.get(i - 1).getAttribute("value"));
									}
								}
							} else if (i == 4) {
								if (!schedule_periodtimes_weekend.get(i - 1).getAttribute("value")
										.equalsIgnoreCase("Tap to set")) {
									dateString = schedule_periodtimes_weekend.get(3).getAttribute("value");
									tempTime = dateString;
									try {
										if (!dateString.contains("m") && !dateString.contains("M")) {
											final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
											final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
											tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
										}
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error message: " + e.getMessage());
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error message: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error message: " + e.getMessage());
									}
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME).equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT).equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value"))
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT).equalsIgnoreCase(
													schedule_weekend_coolsetpoints.get(j).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT) + " "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value"))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
														+ " is not shown correctly: " + schedule_periodtimes_weekend
																.get(i - 1).getAttribute("value"));
									}
								}
							}
						}
					} else {
						if (MobileUtils.isMobElementExists("xpath",
								"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
								testCase, 5)) {
							schedule_periodtimes_weekday = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
							schedule_weekday_heatsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_HeatTemperature')]");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekday schedule period times");
						}
						if (MobileUtils.isMobElementExists("xpath",
								"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
								testCase, 5)) {
							schedule_periodtimes_weekend = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");

							schedule_weekend_heatsetpoints = MobileUtils.getMobElements(testCase, "xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_HeatTemperature')]");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekend schedule period times");
						}

						Keyword.ReportStep_Pass(testCase,
								"*********************** Verifying Weekday-Weekend schedule period time and schedule period heat set points against set values **************************");

						for (int i = 1; i <= schedule_periodtimes_weekday.size(); i++) {
							dateString = schedule_periodtimes_weekday.get(i - 1).getAttribute("value");
							tempTime = dateString;
							try {
								if (!dateString.contains("m") && !dateString.contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 1) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekday_heatsetpoints.get(0).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_1_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 1's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 1's expected weekday time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 2) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekday_heatsetpoints.get(1).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_2_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 2's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 2's expected weekday time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 3) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekday_heatsetpoints.get(2).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_3_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 3's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 3's expected weekday time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 4) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekday_heatsetpoints.get(3).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKDAY_4_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 4's expected weekday time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 4's expected weekday time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							}

						}
						for (int i = 1; i <= schedule_periodtimes_weekend.size(); i++) {
							dateString = schedule_periodtimes_weekend.get(i - 1).getAttribute("value");
							tempTime = dateString;
							try {
								if (!dateString.contains("m") && !dateString.contains("M")) {
									final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									final Date dateObj1 = sdf.parse(dateString.split("\\s+")[0]);
									tempTime = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								}
							} catch (NumberFormatException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[NumberFormatException] Error message: " + e.getMessage());
							} catch (ParseException e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[ParseException] Error message: " + e.getMessage());
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Exception] Error message: " + e.getMessage());
							}
							if (i == 1) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekend_heatsetpoints.get(0).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_1_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 1's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 1's expected Weekend time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 2) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekend_heatsetpoints.get(1).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_2_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 2's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 2's expected Weekend time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 3) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekend_heatsetpoints.get(2).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_3_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 3's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 3's expected Weekend time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							} else if (i == 4) {
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT)
											.split("\\.")[0];
								}
								tempHeatSetPointApp = schedule_weekend_heatsetpoints.get(3).getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempTimeInputs = inputs.getInputValue(InputVariables.WEEKEND_4_TIME);
								try {
									if (!tempTimeInputs.contains("m") && !tempTimeInputs.contains("M")) {
										final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
										final Date dateObj1 = sdf.parse(tempTimeInputs.split("\\s+")[0]);
										tempTimeInputs = new SimpleDateFormat("hh:mm aa").format(dateObj1);
									}
								} catch (NumberFormatException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[NumberFormatException] Error message: " + e.getMessage());
								} catch (ParseException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[ParseException] Error message: " + e.getMessage());
								} catch (Exception e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Exception] Error message: " + e.getMessage());
								}
								if (tempTimeInputs.equalsIgnoreCase(tempTime)
										&& tempHeatSetPointFromInputs.equalsIgnoreCase(tempHeatSetPointApp)) {
									Keyword.ReportStep_Pass(testCase,
											"Period 4's expected Weekend time and heat set point are shown correctly: "
													+ tempTimeInputs + " " + tempHeatSetPointFromInputs);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Period 4's expected Weekend time and heat set point: " + tempTimeInputs
													+ " " + tempHeatSetPointFromInputs + " are not shown correctly: "
													+ tempTime + " " + tempHeatSetPointApp);
								}
							}

						}
						// end/////////////////////////
					}
				}
			}

			Keyword.ReportStep_Pass(testCase,
					"*********************** Completed verifying time based schedule on Primary Card **************************");
		} else if (scheduleType.equalsIgnoreCase("geofence")) {
			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying goefence based schedule on Primary Card **************************");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Home']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Home text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Home text not displayed on schedule screen");
				}

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='Use My Home Settings']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Create Sleep Settings']", testCase, 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (MobileUtils.isMobElementExists("xpath", "//*[@text='Use My Sleep Settings']", testCase, 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Use My Sleep Settings option not displayed on schedule screen");
					}
				}

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Away']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Away text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Away text not displayed on schedule screen");
				}

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='Use My Away Settings']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}
			} else {
				/*
				 * if (MobileUtils.isMobElementExists("name", "When I'm Home",
				 * testCase, 5)) { Keyword.ReportStep_Pass(testCase,
				 * "Verify Displayed Schedule : When I'm Home text displayed on schedule screen"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Verify Displayed Schedule : When I'm Home text not displayed on schedule screen"
				 * ); }
				 */

				if (MobileUtils.isMobElementExists("xpath", "//*[@value='Use My Home Settings']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (MobileUtils.isMobElementExists("xpath", "//*[@value='Create Sleep Settings']", testCase, 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (MobileUtils.isMobElementExists("xpath", "//*[@value='Use My Sleep Settings']", testCase, 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Use My Sleep Settings option not displayed on schedule screen");
					}
				}

				/*
				 * if (MobileUtils.isMobElementExists("name", "When I'm Away",
				 * testCase, 5)) { Keyword.ReportStep_Pass(testCase,
				 * "Verify Displayed Schedule : When I'm Away text displayed on schedule screen"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Verify Displayed Schedule : When I'm Away text not displayed on schedule screen"
				 * ); }
				 */

				if (MobileUtils.isMobElementExists("xpath", "//*[@value='Use My Away Settings']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}
			}
			// ============================================ANDROID============================================================
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
					schedule_setpoints = MobileUtils.getMobElements(testCase, "ID", "scheduling_period_heating_point");
					tempHeatSetPointApp = schedule_setpoints.get(0).getText();
					if (schedule_setpoints.get(0).getText().contains(".0")) {
						tempHeatSetPointApp = schedule_setpoints.get(0).getText().split("\\.")[0];
					}
					tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
					if (tempHeatSetPointFromInputs.contains(".0")) {
						tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
					}
					if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
						Keyword.ReportStep_Pass(testCase,
								"[HomeSettings] Home set point is shown correctly in solution card: "
										+ tempHeatSetPointApp);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[HomeSettings] Home set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
					}
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						tempHeatSetPointApp = schedule_setpoints.get(1).getText();
						if (schedule_setpoints.get(1).getText().contains(".0")) {
							tempHeatSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}

						SleepStartEndTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText();

						dateString = SleepStartEndTime.replaceAll("\\.", "");
						String startTimeTemp, endTimeTemp;
						try {
							if (!dateString.contains("m") && !dateString.contains("M")) {
								final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
								final Date dateObj1 = sdf.parse(SleepStartEndTime.split("\\s+")[0]);
								startTimeTemp = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								final Date dateObj2 = sdf.parse(SleepStartEndTime.split("\\s+")[2]);
								endTimeTemp = new SimpleDateFormat("hh:mm aa").format(dateObj2);
								SleepStartEndTime = startTimeTemp + " - " + endTimeTemp;
							}
							SleepStartTime = SleepStartEndTime.split("\\s+")[0] + " "
									+ SleepStartEndTime.split("\\s+")[1];
							SleepEndTime = SleepStartEndTime.split("\\s+")[3] + " "
									+ SleepStartEndTime.split("\\s+")[4];

						} catch (NumberFormatException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[NumberFormatException] Error message: " + e.getMessage());
						} catch (ParseException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[ParseException] Error message: " + e.getMessage());
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Exception] Error message: " + e.getMessage());
						}

						if (SleepStartTime
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep Start time is shown correctly in solution card: "
											+ SleepStartTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep Start time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME)
											+ " is not shown correctly in solution card: " + SleepStartTime);
						}
						if (SleepEndTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep End time is shown correctly in solution card: "
											+ SleepEndTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep End time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME)
											+ " is not shown correctly in solution card: " + SleepEndTime);
						}

						tempHeatSetPointApp = schedule_setpoints.get(2).getText();
						if (schedule_setpoints.get(2).getText().contains(".0")) {
							tempHeatSetPointApp = schedule_setpoints.get(2).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Away set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Away set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}
					} else {
						tempHeatSetPointApp = schedule_setpoints.get(1).getText();
						if (schedule_setpoints.get(1).getText().contains(".0")) {
							tempHeatSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Away set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Away set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate Geofence schedule set points in solution card");
				}
			}
			// ================================================IOS================================================================
			else {
				if (MobileUtils.isMobElementExists("name", "Geofence_Home_HeatTemperature", testCase, 5)) {
					homeHeatSetPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_HeatTemperature");
					tempHeatSetPointApp = homeHeatSetPointIOS.getAttribute("value");
					if (tempHeatSetPointApp.contains(".0")) {
						tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
					}
					tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
					if (tempHeatSetPointFromInputs.contains(".0")) {
						tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
					}
					if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
						Keyword.ReportStep_Pass(testCase,
								"[HomeSettings] Home set point is shown correctly in solution card: "
										+ tempHeatSetPointApp);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[HomeSettings] Home set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
					}
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						sleepHeatSetPointIOS = MobileUtils.getMobElement(testCase, "name",
								"Geofence_Sleep_HeatTemperature");
						tempHeatSetPointApp = sleepHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}

						SleepStartEndTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value");

						dateString = SleepStartEndTime.replaceAll("\\.", "");
						String startTimeTemp, endTimeTemp;
						try {
							if (!dateString.contains("m") && !dateString.contains("M")) {
								final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
								final Date dateObj1 = sdf.parse(SleepStartEndTime.split("\\s+")[0]);
								startTimeTemp = new SimpleDateFormat("hh:mm aa").format(dateObj1);
								final Date dateObj2 = sdf.parse(SleepStartEndTime.split("\\s+")[2]);
								endTimeTemp = new SimpleDateFormat("hh:mm aa").format(dateObj2);
								SleepStartEndTime = startTimeTemp + " - " + endTimeTemp;
							}
							SleepStartTime = SleepStartEndTime.split("\\s+")[0] + " "
									+ SleepStartEndTime.split("\\s+")[1];
							SleepEndTime = SleepStartEndTime.split("\\s+")[3] + " "
									+ SleepStartEndTime.split("\\s+")[4];

						} catch (NumberFormatException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[NumberFormatException] Error message: " + e.getMessage());
						} catch (ParseException e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[ParseException] Error message: " + e.getMessage());
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Exception] Error message: " + e.getMessage());
						}

						if (SleepStartTime
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep Start time is shown correctly in solution card: "
											+ SleepStartTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep Start time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME)
											+ " is not shown correctly in solution card: " + SleepStartTime);
						}
						if (SleepEndTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep End time is shown correctly in solution card: "
											+ SleepEndTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep End time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME)
											+ " is not shown correctly in solution card: " + SleepEndTime);
						}

						awayHeatSetPointIOS = MobileUtils.getMobElement(testCase, "name",
								"Geofence_Away_HeatTemperature");
						tempHeatSetPointApp = awayHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[AwaySettings] Away set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[AwaySettings] Away set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}
					} else {
						awayHeatSetPointIOS = MobileUtils.getMobElement(testCase, "name",
								"Geofence_Away_HeatTemperature");
						tempHeatSetPointApp = awayHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Away set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Away set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate Geofence schedule set points in solution card");
				}
			}
			Keyword.ReportStep_Pass(testCase,
					"*********************** Completed verifying geofence based schedule on Primary Card **************************");
		} else if (scheduleType.equalsIgnoreCase("no schedule")) {
			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying no schedule on Primary Card **************************");
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "NoScheduleText", 5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : No schedule text displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule : No schedule not text displayed on schedule screen");
			}
			Keyword.ReportStep_Pass(testCase,
					"*********************** Completed verifying no schedule on Primary Card **************************");
		}
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@content-desc='Navigate Up']")).click();
			} else {
				if (MobileUtils.isMobElementExists("name", "btn close normal", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "btn close normal")) {
						flag = false;
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to navigate back to Primary Card: Error message-" + e.getMessage());
		}

		return flag;
	}

	
	
}
