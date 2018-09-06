package com.honeywell.jasper.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.honeywell.screens.SchedulingScreen;

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

	public static boolean createGeofenceBasedSchedule(TestCases testCase, TestCaseInputs inputs,
			boolean createScheduleUsingUseGeofenceButton) {
		boolean flag = true;
		try {
			SchedulingScreen ss = new SchedulingScreen(testCase);
			//flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
			if (ss.isTimeScheduleButtonVisible(10)) {
				flag = flag & ss.clickOnTimeScheduleButton();
			} 
			if (ss.isCreateScheduleButtonVisible(5)) {
				flag = flag & ss.clickOnCreateScheduleButton();
			} else {
				if (ss.isScheduleOffOverlayVisible(5)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();
				if (ss.isSwitchToGeofenceButtonVisible(5)) {
					flag = flag & ss.clickOnSwitchToGeofenceButton();
				}
			}
			if (createScheduleUsingUseGeofenceButton) {
				flag = flag & ss.clickOnUseGeofencingText();
			} else {
				flag = flag & ss.clickOnLearnMoreButton();
				flag = flag & ss.clickOnGetStartedButton();
				flag = flag & ss.clickOnSaveButton();
			}
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Home period ***************");
			inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_HOME);
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isHomeTemperatureHeaderMultiTemperatureVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				} else {
				}
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")
					|| !allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isHomeTemperatureHeaderSingleTemperatureVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				} else {

				}
			}
			HashMap<String, String> targetSetPoints = new HashMap<String, String>();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
			}
			flag = flag & setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Home", targetSetPoints);
			String homeSetpoint=null;
			if(inputs.getInputValue("NaviagateBackAtSleep").equalsIgnoreCase("true")){
				homeSetpoint=ss.getHeatSetPointChooserSetPointsValue();
			}
			if (ss.isNextButtonVisible(5)) {
				flag = flag & ss.clickOnNextButton();
			}
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting set points for Home period ***************");
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Away period ***************");
			inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_AWAY);
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isAwayTemperatureHeaderMultiTemperatureVisble(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				} else {
				}
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")
					|| !allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isAwayTemperatureHeaderSingleTemperatureVisble(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				} else {
				}
			}
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
			}
			flag = flag & setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Away", targetSetPoints);
			String awaySetPoint = null;
			if(inputs.getInputValue("NaviagateBackAtSleep").equalsIgnoreCase("true")){
				awaySetPoint=ss.getHeatSetPointChooserSetPointsValue();
			}
			if (ss.isNextButtonVisible(5)) {
				flag = flag & ss.clickOnNextButton();
			}
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting set points for Away period ***************");
			if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & ss.clickOnSkipSleepButton();
				} else {
					flag = flag & ss.clickOnNoButton();
				}

			} else {
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Setting time and set points for Sleep period ***************");
				inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					//flag = flag & ss.clickOnYesButton();

				}
				flag = flag & setPeriodTime(testCase, inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME),
						"GeofenceSleepStartTime", true, true);
				flag = flag & setPeriodTime(testCase, inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME),
						"GeofenceSleepEndTime", true, true);
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp",
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
					targetSetPoints.put("targetHeatTemp",
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					targetSetPoints.put("targetHeatTemp",
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp",
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
				}
				flag = flag & setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Sleep", targetSetPoints);
				if(inputs.getInputValue("NaviagateBackAtSleep").equalsIgnoreCase("true")){
					flag = flag & ss.clickOnCloseButton();
					verifyHeatStepperValue(testCase, inputs, awaySetPoint, "");
					ss.clickOnBackButton();
					verifyHeatStepperValue(testCase, inputs, homeSetpoint, "");
					flag = flag & ss.clickOnNextButton();
					flag = flag & ss.clickOnNextButton();
				}
				flag = flag & ss.clickOnNextButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting time and set points for Sleep period ***************");
			}
			// flag = flag & InputVariables.verifyCreatedSchedule(testCase, inputs,
			// "Geofence");
			if (ss.IsSaveButtonVisible(10)) {
				flag = flag & ss.clickOnSaveButton();
			}

			if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
				System.out.println("Copy all");
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
				if (ss.isCopyButtonVisible(5)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
				if (ss.isCheckBoxVisible(5)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
					System.out.println(checkBoxes.size());
					String SelectStatPosition = getRandomSetPointValueBetweenMinandMax(testCase, inputs,
							Double.parseDouble("0"), Double.parseDouble(String.valueOf(checkBoxes.size())));

					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						// if
						// (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value").equals(""))
						// {
						Keyword.ReportStep_Pass(testCase,
								"Selecting stat at Position " + SelectStatPosition + ", copying to "
										+ checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
						checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
						inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
								checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
						// }
					} else {
						Keyword.ReportStep_Pass(testCase, "Selecting stat at Position " + SelectStatPosition
								+ ", copying to " + checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked")
								.equals("false")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
						}
					}

				}
				if (ss.isCopyButtonVisible(5)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else {
				if (ss.isSkipButtonVisible(5)) {
					flag = flag & ss.clickOnSkipButton();
				}
			}
			if (ss.isTimeScheduleButtonVisible(10)) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Create Schedule : Failed to navigate to Primary Card");
			}
		} catch (Exception e) {
		}
		return flag;
	}


	public static boolean createGeofenceBasedwithsleepspeicfictime(TestCases testCase, TestCaseInputs inputs, String startTime, String endTime,
			boolean createScheduleUsingUseGeofenceButton) {
		boolean flag = true;
	try {
		SchedulingScreen ss = new SchedulingScreen(testCase);
		//flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
		if (ss.isTimeScheduleButtonVisible(2)) {
			flag = flag & ss.clickOnTimeScheduleButton();
		} 
		if (ss.isCreateScheduleButtonVisible(2)) {
			flag = flag & ss.clickOnCreateScheduleButton();
		} else {
			if (ss.isScheduleOffOverlayVisible(2)) {
				if (!ss.clickOnScheduleOffOverlay()) {
					flag = false;
				} else {
					Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
				}
			}
			flag = flag & ss.clickOnScheduleOptionsButton();
			if (ss.isSwitchToGeofenceButtonVisible(5)) {
				flag = flag & ss.clickOnSwitchToGeofenceButton();
			}
		}
		if (createScheduleUsingUseGeofenceButton) {
			flag = flag & ss.clickOnUseGeofencingText();
		} else {
			flag = flag & ss.clickOnLearnMoreButton();
			flag = flag & ss.clickOnGetStartedButton();
			flag = flag & ss.clickOnSaveButton();
		}
		
		
		Keyword.ReportStep_Pass(testCase, " ");
		Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Home period ***************");
		inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_HOME);
						if (ss.isHomeTemperatureHeaderSingleTemperatureVisible(5)) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule : Successfully navigated to home set points page");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule : Failed to navigate to home set points page");
						}
				if (ss.isNextButtonVisible(5)) {
				flag = flag & ss.clickOnNextButton();
				}				
		Keyword.ReportStep_Pass(testCase,
				"*************** Completed setting set points for Home period ***************");
		Keyword.ReportStep_Pass(testCase, " ");
		Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Away period ***************");
		inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_AWAY);
						if (ss.isAwayTemperatureHeaderMultiTemperatureVisble(5)) {
							Keyword.ReportStep_Pass(testCase,
									"Create Schedule : Successfully navigated to away set points page");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule : Failed to navigate to away set points page");
						}
						if (ss.isNextButtonVisible(5)) {
							flag = flag & ss.clickOnNextButton();
						}
		Keyword.ReportStep_Pass(testCase,
				"*************** Completed setting set points for Away period ***************");
		if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								flag = flag & ss.clickOnSkipSleepButton();
							} else {
								flag = flag & ss.clickOnNoButton();
							}
				
						} else {
							Keyword.ReportStep_Pass(testCase, " ");
							Keyword.ReportStep_Pass(testCase,
									"*************** Setting time and set points for Sleep period ***************");
							inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
							
							flag = flag & setPeriodTime(testCase, startTime,"GeofenceSleepStartTime", true, true);
							flag = flag & setPeriodTime(testCase, endTime,"GeofenceSleepEndTime", true, true);
							flag = flag & ss.clickOnNextButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting time and set points for Sleep period ***************");
		}
		// flag = flag & InputVariables.verifyCreatedSchedule(testCase, inputs,"Geofence");
		 
		if (ss.IsSaveButtonVisible(5)) {
			flag = flag & ss.clickOnSaveButton();
		}

		if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
			System.out.println("Copy all");
			if (ss.isCheckBoxVisible(3)) {
				List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
			if (ss.isCopyButtonVisible(5)) {
				flag = flag & ss.clickOnCopyButton();
			}
		} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
			if (ss.isCheckBoxVisible(5)) {
				List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
				System.out.println(checkBoxes.size());
				String SelectStatPosition = getRandomSetPointValueBetweenMinandMax(testCase, inputs,
						Double.parseDouble("0"), Double.parseDouble(String.valueOf(checkBoxes.size())));

				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					// if
					// (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value").equals(""))
					// {
					Keyword.ReportStep_Pass(testCase,
							"Selecting stat at Position " + SelectStatPosition + ", copying to "
									+ checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
					checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
					inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
					// }
				} else {
					Keyword.ReportStep_Pass(testCase, "Selecting stat at Position " + SelectStatPosition
							+ ", copying to " + checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
					if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked")
							.equals("false")) {
						checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
						inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
								checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
					}
				}

			}
			if (ss.isCopyButtonVisible(5)) {
				flag = flag & ss.clickOnCopyButton();
			}
		} else {
			if (ss.isSkipButtonVisible(5)) {
				flag = flag & ss.clickOnSkipButton();
			}
		}
		if (ss.isTimeScheduleButtonVisible(10)) {
			Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Create Schedule : Failed to navigate to Primary Card");
		}
	} catch (Exception e) {
	}
	return flag;
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
							if (jasperStatType.equalsIgnoreCase("NA") || jasperStatType.equalsIgnoreCase("FLYCATCHER")) {
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
							if (jasperStatType.equalsIgnoreCase("NA") || jasperStatType.equalsIgnoreCase("FLYCATCHER")) {
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
				if (jasperStatType.toUpperCase().contains("NA") || jasperStatType.equalsIgnoreCase("FLYCATCHER")) {
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
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (ss.isTimeScheduleButtonVisible(10)) {
			flag = flag & ss.clickOnTimeScheduleButton();
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Schedule button not found on Primary Card");
		}
		return flag;
	}

	public static boolean createTimeBasedScheduleWithDefaultValues(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			WebElement element = null;
			SchedulingScreen ss = new SchedulingScreen(testCase);
			if (ss.isCreateScheduleButtonVisible(5)) {
				flag = flag & ss.clickOnCreateScheduleButton();

				if (ss.isTimeOptionVisible(5)) {
					flag = flag & ss.clickOnTimeOption();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Unable to navigate to create schedule page.");
					return false;
				}
			} else {
				if (ss.isScheduleOffOverlayVisible(5)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();

				DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
				String currentScheduleType = devInfo.getThermoStatScheduleType();

				if (currentScheduleType.equalsIgnoreCase("Timed")) {
					flag = flag & ss.clickOnCreateNewTimeScheduleButton();
				} else {
					flag = flag & ss.clickOnSwitchToTimeScheduleButton();
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				flag = flag & ss.clickOnEverydayScheduleButton();
				System.out.println(inputs.getInputValue(InputVariables.JASPER_STAT_TYPE));
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					String[] modes = { "Wake", "Away", "Home", "Sleep" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = devInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("Wake")) {
							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
							element = ss.getEverydayWakeElement();
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
							element = ss.getEverydayAwayElement();
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
							element = ss.getEverydayHomeElement();
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
							element = ss.getEverydaySleepElement();
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
						flag = flag & JasperSchedulingUtils.verifySetPeriodTime(testCase,
								periodTimeandSetPoint.get("Time"), "TimeChooser");
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
						flag = flag & ss.clickOnSaveButton();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
							element = ss.getEveryday1Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
							element = ss.getEveryday2Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
							element = ss.getEveryday3Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
							element = ss.getEveryday4Element();
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
						if (ss.isTimeChooserHeaderVisible(5)) {
							Keyword.ReportStep_Pass(testCase,
									"Time chooser header is shown correctly");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Time chooser header is not shown correctly");
						}
						if (ss.isTempChooserHeaderVisible(5)) {
							Keyword.ReportStep_Pass(testCase,
									"Temp chooser header is shown correctly");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Temp chooser header is not shown correctly");
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
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed verifying time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
					}
				}
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				flag = flag & ss.clickOnWeekdayandWeekendScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					String[] modes = { "Wake_Weekday", "Away_Weekday", "Home_Weekday", "Sleep_Weekday", "Wake_Weekend",
							"Away_Weekend", "Home_Weekend", "Sleep_Weekend" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = devInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("Wake_Weekday")) {
							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
							element = ss.getWeekdayWakeElement();
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
							element = ss.getWeekdayAwayElement();
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
							element = ss.getWeekdayHomeElement();
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
							element = ss.getWeekdaySleepElement();
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
									element = ss.getWeekendWakeElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendWakeElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e3) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Wake_Saturday-Sunday");
										}
									}
									element = ss.getWeekendWakeElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendWakeElement();
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
							//							try {
							if (!testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								//									element = ss.getWeekendAwayElement();
								//								} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								if (!ss.isWeekendAwayElementVisible(5)) {
									try {
										action.press(10, (int) (dimension.getHeight() * .5))
										.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Away_Saturday-Sunday");
									}
								}
								element = ss.getWeekendAwayElement();
								//								}
								//							} catch (NoSuchElementException e) {
							} else {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendAwayElement();
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
									element = ss.getWeekendHomeElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendHomeElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Home_Saturday-Sunday");
										}
									}
									element = ss.getWeekendHomeElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendHomeElement();
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
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendSleepElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendSleepElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Sleep_Saturday-Sunday");
										}
									}
									element = ss.getWeekendSleepElement();
								}
							} catch (NoSuchElementException e) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Create Schedule : Could not find element Sleep_Saturday-Sunday");
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
						flag = flag & ss.clickOnSaveButton();
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
							element = ss.getWeekday1Element();
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
							element = ss.getWeekday2Element();
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
							element = ss.getWeekday3Element();
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
							element = ss.getWeekday4Element();
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
									scrollForAndroidScreen(testCase);
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend1Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend1ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e3) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Saturday-Sunday");
										}
									}
									element = ss.getWeekend1Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend1Element();
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
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = ss.getWeekend2Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend2ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Away_Saturday-Sunday");
										}
									}
									element = ss.getWeekend2Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend2Element();
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
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = ss.getWeekend3Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend3ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Home_Saturday-Sunday");
										}
									}
									element = ss.getWeekend3Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend3Element();
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
									Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
									int startx = (dimensions.width * 20) / 100;
									int starty = (dimensions.height * 62) / 100;
									int endx = (dimensions.width * 22) / 100;
									int endy = (dimensions.height * 35) / 100;
									testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
									element = ss.getWeekend4Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend4ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Sleep_Saturday-Sunday");
										}
									}
									element = ss.getWeekend4Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend4Element();
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
						flag = flag & ss.clickOnSaveButton();
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

				if (ss.isEverydayScheduleButtonVisible(5)) {
					Keyword.ReportStep_Pass(testCase, "Successfully deleted all the periods");
					return flag;
				}

				if (ss.isNoScheduleTextVisible(10)){
					flag = flag && ss.clickOnBackButton();
					Keyword.ReportStep_Pass(testCase, "Successfully deleted all the periods");
					return flag;
				}
			}
			if (inputs.getInputValue(InputVariables.ADD_PERIOD).equalsIgnoreCase("Yes")) {
				JasperSchedulingUtils.addPeriodEMEADefaultCase(testCase, inputs);
			}

			if (ss.IsSaveButtonVisible(10)) {
				flag = flag & ss.clickOnSaveButton();
			}
			if (!inputs.getInputValue("ConfirmTimeShedule").isEmpty()) {
				if (inputs.getInputValue("ConfirmTimeShedule").equalsIgnoreCase("true")) {
					if (ss.isConfirmChangeButtonVisible(5)) {
						Keyword.ReportStep_Pass(testCase, "Create Schedule : Confirm change button shown");
						if (!ss.clickOnConfirmChangeButton()) {
							flag = false;
						}
					}
				} else {
					if (ss.isCancelChangeButtonVisible(5)) {
						Keyword.ReportStep_Pass(testCase, "Create Schedule : Cancel change button shown");
						if (!ss.clickOnCancelChangeButton()) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel button not shown");
					}
					return flag;
				}

			} else {
				if (ss.isConfirmChangeButtonVisible(10)) {

					if (!ss.clickOnConfirmChangeButton()) {
						flag = false;
					}
				}
			}
			if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
				System.out.println("Copy all");
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
				if (ss.isCopyButtonVisible(3)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value")
								.equals("Disabled")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
						}
					} else {
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked")
								.equals("false")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
						}
					}

				}
				if (ss.isCopyButtonVisible(10)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else if (ss.isSkipButtonVisible(10)) {
				flag = flag & ss.clickOnSkipButton();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifySetPeriodTime(TestCases testCase, String time, String locatorValueinObjectDefinition) {
		boolean flag = true;
		try {
			String timeChar=String.valueOf(time.charAt(0));
			if(timeChar.equals("0")){
				time=time.replaceFirst("0", "");
			}
			SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
			String time12hours = time.toUpperCase();
			String time24hours = date24Format.format(date12Format.parse(time)).toUpperCase();
			SchedulingScreen ss = new SchedulingScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ss.getElementFromObjectDefinitions(locatorValueinObjectDefinition).getText().replaceAll("\\.", "")
						.toUpperCase().contains(time12hours)
						|| ss.getElementFromObjectDefinitions(locatorValueinObjectDefinition).getText()
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
					setTime = ss.getTimeChooserValue();
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
					setTime = ss.getTimeChooserEndTimeValue();
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("GeofenceSleepStartTime")) {
					setTime = ss.getElementFromObjectDefinitions(locatorValueinObjectDefinition).getAttribute("value");
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("GeofenceSleepEndTime")) {
					setTime = ss.getElementFromObjectDefinitions(locatorValueinObjectDefinition).getAttribute("value");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Incorrect time chooser identifier");
				}
				if (setTime.equalsIgnoreCase(time.replace(" AM", "")) || setTime.equalsIgnoreCase(time.replace(" PM", "")) ) {
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
		try {
			HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
			String tempPeriod = " ", tempPeriodStartTime = " ", tempPeriodEndTime = "";
			WebElement element = null;
			List<WebElement> schedule_period_time = null, schedule_period_setpoint = null;
			List<WebElement> weekdaySchedule_period_time = null;
			List<WebElement> weekendSchedule_period_time = null;
			int initialPeriodSize = 0, finalPeriodSize = 0;
			SchedulingScreen ss = new SchedulingScreen(testCase);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = devInfo.getAllowedModes();
			SchedulingScreen sScreen = new SchedulingScreen(testCase);

			if (sScreen.getSchedulePeriodbuttons() != null) {
				element = sScreen.getSchedulePeriodbutton();
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Setting time and set points for new period ***************");
				Random rn = new Random();
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					tempPeriod = String.valueOf(rn.nextInt((3 - 1) + 1) + 1);
					if (ss.isEverydayTimeVisible(5)) {
						schedule_period_time = ss.getEverydayTimeElements();
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
					if (Integer.parseInt(tempPeriod) <= 4) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							while (!ss.isWeekday1ElementVisible(5)) {
								scrollUpForAndroidScreen(testCase);
							}

						} else {
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
						}
						if (ss.isWeekDayTimeListVisible(5)) {
							weekdaySchedule_period_time = ss.getWeekdayTimeListElements();
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekday Time list");
						}
						element = sScreen.getSchedulePeriodbutton();
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
						if (ss.isWeekendTimeListVisible(5)) {
							weekendSchedule_period_time = ss.getWeekdendTimeListElements();
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekend Time list");
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							int size = ss.getAddImageElements().size();
							if (size > 1) {
								element = ss.getAddImageElements().get(1);
							} else {
								element = sScreen.getSchedulePeriodbutton();
							}
						} else {
							element = sScreen.getSchedulePeriodbuttons().get(1);
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
							tempPeriodEndTime = weekendSchedule_period_time.get(Integer.parseInt(tempPeriod) - 4)
									.getText();

						}
					}
				} else {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
							.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						tempPeriodStartTime = schedule_period_time.get(Integer.parseInt(tempPeriod) - 1)
								.getAttribute("value");
						tempPeriodEndTime = schedule_period_time.get(Integer.parseInt(tempPeriod))
								.getAttribute("value");
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

				tempPeriodStartTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, tempPeriodStartTime,
						true, 0, 30);
				tempPeriodEndTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, tempPeriodEndTime, false,
						0, 30);
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

				flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs,
						periodTimeandSetPoint, element);
				flag = flag & ss.clickOnSaveButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting time and set points for new period ***************");

				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					schedule_period_time = ss.getEverydayTimeElements();
					finalPeriodSize = schedule_period_time.size();
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					if (Integer.parseInt(tempPeriod) <= 4) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							scrollUpForAndroidScreen(testCase);
						} else {
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
						}
						if (ss.isWeekDayTimeListVisible(5)) {
							schedule_period_time = ss.getWeekdayTimeListElements();
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
						if (ss.isWeekendTimeListVisible(5)) {
							schedule_period_time = ss.getWeekdendTimeListElements();
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
						schedule_period_time = ss.getEverydayTimeElements();
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_period_setpoint = ss.getSchedulePeriodHeatSetPointElement();
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
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								} else if (i == 2) {
									inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, tempTime);
									inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								} else if (i == 3) {
									inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, tempTime);
									inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								} else if (i == 4) {
									inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, tempTime);
									inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT,
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								} else if (i == 5) {
									inputs.setInputValue(InputVariables.EVERYDAY_5_TIME, tempTime);
									inputs.setInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT,
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								} else if (i == 6) {
									inputs.setInputValue(InputVariables.EVERYDAY_6_TIME, tempTime);
									inputs.setInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT,
											ss.getValueOfEverydayHeatTemperatureElementAtIndex(i));
								}
							}
						}
					} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
							.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
						if (Integer.parseInt(tempPeriod) <= 4) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								scrollUpForAndroidScreen(testCase);

							} else {
								Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
								TouchAction action = new TouchAction(testCase.getMobileDriver());
								action.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .2)).release().perform();
							}
							if (ss.isWeekDayTimeListVisible(5)) {
								schedule_period_time = ss.getWeekdayTimeListElements();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekday Time list");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								schedule_period_setpoint = ss.getWeekdayHeatSetpointListEMEAElements();
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
												&& !schedule_period_time.get(i - 1).getAttribute("value")
												.contains("M")) {
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
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
									} else if (i == 2) {
										inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
									} else if (i == 3) {
										inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
									} else if (i == 4) {
										inputs.setInputValue(InputVariables.WEEKDAY_4_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT,
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
									} else if (i == 5) {
										inputs.setInputValue(InputVariables.WEEKDAY_5_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT,
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
									} else if (i == 6) {
										inputs.setInputValue(InputVariables.WEEKDAY_6_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT,
												ss.getValueOfWeekdayHeatTemperatureElementAtIndex(String.valueOf(i)));
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
							if (ss.isWeekendTimeListVisible(5)) {
								schedule_period_time = ss.getWeekdendTimeListElements();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekend Time list");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								schedule_period_setpoint = ss.getWeekdendHeatSetpointListEMEAElements();
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
												&& !schedule_period_time.get(i - 1).getAttribute("value")
												.contains("M")) {
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
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									} else if (i == 2) {
										inputs.setInputValue(InputVariables.WEEKEND_2_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									} else if (i == 3) {
										inputs.setInputValue(InputVariables.WEEKEND_3_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									} else if (i == 4) {
										inputs.setInputValue(InputVariables.WEEKEND_4_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT,
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									} else if (i == 5) {
										inputs.setInputValue(InputVariables.WEEKEND_5_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT,
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									} else if (i == 6) {
										inputs.setInputValue(InputVariables.WEEKEND_6_TIME, tempTime);
										inputs.setInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT,
												ss.getValueOfWeekendHeatTemperatureElementAtIndex(i));
									}
								}
							}

						}
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to add a new period: Initial period count-" + initialPeriodSize
							+ " Final period count-" + finalPeriodSize);
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"[AddImage] Add image/+ icon is not shown");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public static boolean setPeriodTime(TestCases testCase, String time, String locatorValueinObjectDefinition,
			boolean isValidTime, boolean verifySetPeriodTime) {
		SchedulingScreen ss = new SchedulingScreen(testCase);
		boolean flag = true;
		try {

			String timeToSet = " ";
			String time24hours = " ";
			String hours = time.split(":")[0];
			String minutes = time.split(":")[1].split(" ")[0];
			String ampm = time.split(":")[1].split(" ")[1];
			String invalidTime = " ";
			if (ss.isElementFromObjectDefinitionsVisible(locatorValueinObjectDefinition, 5)) {
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooser")) {
						if (!ss.clickOnTimeScheduleStartTime()) {
							flag = false;
						}
					} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
						if (ss.isTimeScheduleEndTimeVisible(5)) {
							if (!ss.getTimeScheduleEndTimeValue().equalsIgnoreCase("false")) {
								if (!ss.clickOnTimeScheduleEndTime()) {
									flag = false;
								}
							} else {
								if (!ss.clickOnStartButton()) {
									flag = false;
								}
								return flag;
							}
						} else {
							if (ss.isTimeScheduleEndTimeCellVisible(5)) {
								if (!ss.clickOnTimeScheduleEndTimeCell()) {
									flag = false;
								}
							}
						}
					} else {
						flag = flag & ss.clickOnElementFromObjectDefinitions(locatorValueinObjectDefinition);
					}
				} else {
					if (ss.getElementFromObjectDefinitions(locatorValueinObjectDefinition).getAttribute("enabled")
							.equalsIgnoreCase("true")) {
						flag = flag & ss.clickOnElementFromObjectDefinitions(locatorValueinObjectDefinition);
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
				if (ss.isAMPMLabelVisible(3) || ss.isAMLabelVisible(3) || ss.isAMPMLabelVisible(3)) {
					if (ampm.equalsIgnoreCase("AM")) {
						ampm = "AM";
					} else {
						ampm = "PM";
					}
					timeToSet = hours + ":" + minutes + ampm;
					invalidTime = hours + ":" + "25" + ampm;
				} else {
					timeToSet = time24hours;
					invalidTime = time24hours.split(":")[0] + ":25";
				}
				if (isValidTime) {
					if (ss.isTimePickerVisible(15)) {
						if (ss.setValueToTimePicker(timeToSet)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						} else
							if (ss.setHoursValueToTimePickerAndroid(hours) && ss.setMinsValueToTimePickerAndroid(minutes) && ss.setAMPMTimeFormatValueToTimePickerAndroid(ampm)) {
								Keyword.ReportStep_Pass(testCase,
										"Set Period Time : Successfully set time " + timeToSet + " to time picker");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Set Period Time : Failed to set time " + timeToSet + " to time picker");
							}
						flag = flag & ss.clickOnOkButton();
					}
				} else {
					if (ss.isTimePickerVisible(5)) {
						if (!ss.setValueToTimePicker(invalidTime)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Failed to set time " + invalidTime + " to time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						}
						if (ss.setValueToTimePicker(timeToSet)) {
							Keyword.ReportStep_Pass(testCase,
									"Set Period Time : Successfully set time " + timeToSet + " to time picker");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time : Failed to set time " + timeToSet + " to time picker");
						}
						flag = flag & ss.clickOnOkButton();
					}
				}
				if (verifySetPeriodTime) {
					flag = flag & verifySetPeriodTime(testCase, time, locatorValueinObjectDefinition);
				}

			} else {
				int i = Integer.parseInt(hours);
				hours = Integer.toString(i);
				if (ss.isTimeHoursVisible(5)) {
					//String hoursspit = time.split(":")[0];
					flag = flag & ss.setValueToTimeHoursPicker(hours);
				}
				if (ss.isTimeMinutesVisible(5)) {
					flag = flag & ss.setValueToTimeMinutesPicker(minutes);
				}
				if (ss.isTimeAMPMVisible(5)) {
					flag = flag & ss.setValueToTimeAMPMPicker(ampm);
				}
				if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooser")) {
					if (!ss.clickOnTimeScheduleStartTime()) {
						flag = false;
					}
				} else if (locatorValueinObjectDefinition.equalsIgnoreCase("TimeChooserEndTime")) {
					//	if (!ss.getTimeScheduleEndTimeValue().equalsIgnoreCase("false")) {
					if (!ss.clickOnTimeScheduleEndTime()) {
						flag = false;
					}
					/*	} else {
						if (!ss.clickOnStartButton()) {
							flag = false;
						}
					}*/
				} else {
					flag = flag & ss.clickOnElementFromObjectDefinitions(locatorValueinObjectDefinition);
				}
				if (verifySetPeriodTime) {
					flag = flag & verifySetPeriodTime(testCase, i+":"+minutes+" "+ampm, locatorValueinObjectDefinition);
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
				element.click();
				Keyword.ReportStep_Pass(testCase, "Successfully click on : Add period");
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set Period Time and Set Points : Failed to select " + periodTimeandSetPoint.get("periodName"));
				return false;
			}
			if (jasperStatType.equalsIgnoreCase("NA")|| jasperStatType.equalsIgnoreCase("FLYCATCHER") ) {
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

	public static boolean setCoolStepper(TestCases testCase, TestCaseInputs inputs, String targetCoolTemp, int index) {
		boolean flag = true;
		String coolSetPoint = "";
		WebElement coolUp = null;
		WebElement coolDown = null;
		int coolScroller = 0;
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			try {
				coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
				coolUp = ss.getCoolSetPointUpButton();
				coolDown = ss.getCoolSetPointDownButton();
			} catch (NoSuchElementException e) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int startx = (dimensions.width * 20) / 100;
						int starty = (dimensions.height * 62) / 100;
						int endx = (dimensions.width * 22) / 100;
						int endy = (dimensions.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
						coolUp = ss.getCoolSetPointUpButton();
						coolDown = ss.getCoolSetPointDownButton();
					}
				} catch (NoSuchElementException e1) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to find the cool stepper");
				}
			}

		} else {
			int size = ss.getCoolSetPointsElements().size();

			if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
					&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
				coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
				coolUp = ss.getCoolSetPointUpButton();
				coolDown = ss.getCoolSetPointDownButton();
			} else {
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					coolSetPoint = ss.getCoolSetPointsElements().get(1).getAttribute("value");
					coolUp = ss.getCoolIncrementElements().get(1);
					coolDown = ss.getCoolDecrementElements().get(1);
				} else {
					coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
					coolUp = ss.getCoolSetPointUpButton();
					coolDown = ss.getCoolSetPointDownButton();
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
		try{
			String heatSetPoint = " ";
			WebElement heatUp = null;
			WebElement heatDown = null;
			int heatScroller = 0;
			SchedulingScreen ss = new SchedulingScreen(testCase);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				try {
					heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
					heatUp = ss.getHeatSetPointUpButton();
					heatDown = ss.getHeatSetPointDownButton();
				} catch (NoSuchElementException e) {
					try {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 62) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
							heatUp = ss.getHeatSetPointUpButton();
							heatDown = ss.getHeatSetPointDownButton();
						}
					} catch (NoSuchElementException e1) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to find the heat stepper");
					}
				}
			} else {
				int size = ss.getHeatSetPointsElements().size();

				if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
						&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
					heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
					heatUp = ss.getHeatSetPointUpButton();
					heatDown = ss.getHeatSetPointDownButton();


				} else {
					if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
							&& (size > 1)) {
						heatSetPoint = ss.getHeatSetPointsElements().get(1).getAttribute("value");
						heatUp = ss.getHeatIncrementElements().get(1);
						heatDown = ss.getHeatDecrementElements().get(1);
					} else {
						heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();

						heatUp = ss.getHeatSetPointUpButton(index);
						heatDown = ss.getHeatSetPointDownButton();

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
		}catch(Exception e){
			System.out.println("Exception occured"+e.getMessage());
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
	public static String convertiontime(TestCases testCase, String time) {
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
		Dimension dimensions;
		int startx, starty, endx, endy;
		SchedulingScreen ss = new SchedulingScreen(testCase);

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			schedule_period_title = ss.getSchedulePeriodTitleElements();
			schedule_period_time = ss.getSchedulePeriodTimeElements();
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {

				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
					scrollUpForAndroidScreen(testCase);
					scrollUpForAndroidScreen(testCase);
					List<WebElement> weekdayschedule_period_title = ss.getWeekdaySchedulePeriodTitleElements();

					for (int i = 1; i <= (weekdayschedule_period_title.size()); i++) {
						if (String.valueOf(i)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = ss.getWeekdayPeroidTimeValueAtIndex(i);
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
					List<WebElement> weekendschedule_period_title = ss.getWeekendSchedulePeriodTitleElements();

					for (int i = 5; i <= (weekendschedule_period_title.size() * 2); i++) {
						if (String.valueOf(i)
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							if (ss.isWeekendPeriodTimeElementAtIndexVisible(i, 5)) {
								expectedPeriodTime = ss.getWeekendPeroidTimeValueAtIndex(i);
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
					if (String.valueOf(i)
							.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
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

					if (ss.isWeekdayPeriodTimeVisible(5)) {
						schedule_period_time = ss.getWeekdayPeriodTimeElements();
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
								Keyword.ReportStep_Pass(testCase, "Selected period-"
										+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-"
												+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
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

					if (ss.isWeekendPeriodTimeVisible(5)) {
						schedule_period_time = ss.getWeekendPeriodTimeElements();
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
								Keyword.ReportStep_Pass(testCase, "Selected period-"
										+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE));
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-"
												+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
												+ " Error message: " + e.getMessage());
							}
						}
					}
				}
			} else {
				if (ss.isEverydayPeriodTimeVisible(5)) {
					schedule_period_time = ss.getEverydayPeriodTimeElements();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate Everyday schedule period times");
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
									"Failed to click on Period-"
											+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
											+ " Error message: " + e.getMessage());
						}
					}
				}
			}
		}

		if (ss.isPeriodDeleteIconVisible(5)) {
			if (!ss.clickOnPeriodDeleteIcon()) {
				flag = false;
			} else {
				if (ss.isConfirmDeleteButtonVisible(5)) {
					if (!ss.clickOnConfirmDeleteButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to find Confirm Delete button");
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
				if (ss.isSchedulePeriodTimeVisible(5)) {
					schedule_period_time = ss.getSchedulePeriodTimeElements();
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
				if (ss.isEverydayTimeVisible(5)) {
					schedule_period_time = ss.getEverydayTimeElements();
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
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
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
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			schedule_period_title = ss.getSchedulePeriodTitleElements();
			schedule_period_time = ss.getSchedulePeriodTimeElements();
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
					scrollUpForAndroidScreen(testCase);
					scrollUpForAndroidScreen(testCase);
					List<WebElement> weekdayschedule_period_title = ss.getWeekdaySchedulePeriodTitleElements();
					for (int i = 1; i <= (weekdayschedule_period_title.size()); i++) {
						if (weekdayschedule_period_title.get(i - 1).getText()
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = ss.getWeekdayPeroidTimeValueAtIndex(i);
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
					List<WebElement> weekendschedule_period_title = ss.getWeekendSchedulePeriodTitleElements();

					for (int i = 1; i <= weekendschedule_period_title.size(); i++) {
						if (String.valueOf((Integer.parseInt(weekendschedule_period_title.get(i - 1).getText()) + 4))
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = ss.getWeekendPeroidTimeValueAtIndex(i);
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

					if (ss.isWeekdaySchedulePeriodTitleVisible(5)) {
						weekdayschedule_period_title = ss.getWeekdaySchedulePeriodTitleElements();
					}

					action.press(10, (int) (dimension.getHeight() * .5)).moveTo(0, (int) (dimension.getHeight() * -.3))
					.release().perform();

					if (ss.isWeekendSchedulePeriodTitleVisible(5)) {
						weekendschedule_period_title = ss.getWeekendSchedulePeriodTitleElements();
					}

					String expectedPeriod;
					if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {

						action.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * .3)).release().perform();

						schedule_period_time = weekdayschedule_period_title;
						expectedPeriod = "Monday - Friday_";
						for (int i = 1; i <= schedule_period_time.size(); i++) {
							String temp = ss.getPeriodName(expectedPeriod + i);
							temp = temp.split("_")[1];
							if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
								expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
								try {
									schedule_period_time.get(i - 1).click();
									break;
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to click on Period-"
													+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
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
							String temp = ss.getPeriodName(expectedPeriod + i);
							temp = temp.split("_")[1];
							if (String.valueOf((Integer.parseInt(temp) + 4))
									.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
								expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
								try {
									schedule_period_time.get(i - 1).click();
									break;
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to click on Period-"
													+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
													+ " Error message: " + e.getMessage());
								}
							}
						}
					}

				} else {
					schedule_period_time = ss.getEverydayTimeElements();
					for (int i = 1; i <= schedule_period_time.size(); i++) {
						String temp = ss.getPeriodName("Everyday_" + i);
						temp = temp.split("_")[1];
						if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
							expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
							try {
								schedule_period_time.get(i - 1).click();
								break;
							} catch (Exception e) {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to click on Period-"
												+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
												+ " Error message: " + e.getMessage());
							}
						}
					}
				}
			} else {
				schedule_period_time = ss.getEverydayTimeElements();
				for (int i = 1; i <= schedule_period_time.size(); i++) {
					String temp = ss.getPeriodName("Everyday_" + i);
					temp = temp.split("_")[1];
					if (temp.equalsIgnoreCase(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE))) {
						expectedPeriodTime = schedule_period_time.get(i - 1).getAttribute("value");
						try {
							schedule_period_time.get(i - 1).click();
							break;
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to click on Period-"
											+ inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)
											+ " Error message: " + e.getMessage());
						}
					}
				}
			}
		}

		if (ss.isPeriodEditScreenTitleVisible(5)) {
			Keyword.ReportStep_Pass(testCase,
					"Period edit screen is shown");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Period edit screen is not shown");
		}

		if (ss.isPeriodDeleteIconVisible(5)) {
			if (!ss.clickOnPeriodDeleteIcon()) {
				flag = false;
			} else {
				if (ss.isConfirmDeleteButtonVisible(5)) {
					if (!ss.clickOnConfirmDeleteButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to find Confirm Delete button");
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to find Delete icon");
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				scrollUpForAndroidScreen(testCase);
				if (Integer.parseInt(inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE)) <= 4) {
				} else {
					scrollForAndroidScreen(testCase);
				}
			} else {
				schedule_period_time = ss.getSchedulePeriodTimeElements();
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
				schedule_period_time = ss.getEverydayTimeElements();
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
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, InputVariables.EVERYDAY_2_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, InputVariables.EVERYDAY_3_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, InputVariables.EVERYDAY_3_TIME);
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, InputVariables.EVERYDAY_4_TIME);
				}
			} else {

				if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("1")) {
					// 1, 2,3, 4
					inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_1_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_2_TIME));
					System.out.println("1 " + inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_1_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
					inputs.setInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));

				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("2")) {
					inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("3")) {
					inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKDAY_3_TIME,
							inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("5")) {
					// 1, 2,3, 4
					inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_1_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_1_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_2_TIME));
					System.out.println("1 " + inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT) + " "
							+ InputVariables.WEEKEND_2_TIME);
					inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("6")) {
					inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					System.out.println("2 " + inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_2_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
				} else if (inputs.getInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE).equalsIgnoreCase("7")) {
					System.out.println("3 " + inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT,
							inputs.getInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT));
					inputs.setInputValue(InputVariables.WEEKEND_3_TIME,
							inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
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
		SchedulingScreen ss = new SchedulingScreen(testCase);

		if (ss.isTimeScheduleButtonVisible(10)){
			ss.clickOnTimeScheduleButton();
		}

		if (scheduleType.equalsIgnoreCase("time")||scheduleType.equalsIgnoreCase("time based")) {
			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying time based schedule on Primary Card **************************");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					if (ss.isEverydayTextVisible(5)) {
						try {
							ss.getEverydayText();
							Keyword.ReportStep_Pass(testCase,
									"Verify Displayed Schedule : Everyday text displayed on schedule screen");
							schedule_periodtimes = ss.getSchedulePeriodTimeElements();
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT);
										if (inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT)
												.contains(".0")) {
											tempHeatSetPointFromInputs = inputs
													.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT)
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
										if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
							} else if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("FlyCatcher")) {
								List<WebElement> schedule_everydaytitle = ss.getEverydayTitleListElements();
								for (int i = 0; i < schedule_periodtimes.size(); i++) {
									Keyword.ReportStep_Pass(testCase,
											"*********************** Verifying schedule period time and schedule period heat set points against set values **************************");
									dateString = ss.getTimeOfEverydayScheduleOfGivenPeriod(
											schedule_everydaytitle.get(i).getText()).replaceAll("\\.", "");
									tempTime = dateString;
									if (i == 0) {
										if (!ss.getTimeOfEverydayScheduleOfGivenPeriod(
												schedule_everydaytitle.get(i).getText())
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
											 * getInputValue(InputVariables.EVERYDAY_WAKE_TIME) );
											 * System.out.println(tempTime); System.out.println(inputs. getInputValue(
											 * InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
											 */

											if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
													.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
													.equalsIgnoreCase(ss.getHeatSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period WAKE's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period WAKE's expected time and heat and cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ ss.getHeatSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText())
																+ " " + ss.getCoolSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME).toUpperCase()
													.equalsIgnoreCase(ss.getTimeOfEverydayScheduleOfGivenPeriod(
															schedule_everydaytitle.get(i).getText()))) {
												Keyword.ReportStep_Pass(testCase,
														"Period WAKE's expected time is shown correctly: " + inputs
														.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period WAKE's expected time: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_TIME)
																+ " is not shown correctly: "
																+ ss.getTimeOfEverydayScheduleOfGivenPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										}
									} else if (i == 1) {
										if (!ss.getTimeOfEverydayScheduleOfGivenPeriod(
												schedule_everydaytitle.get(i).getText())
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
											if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
													.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
													.equalsIgnoreCase(ss.getHeatSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period AWAY's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period AWAY's expected time and heat and cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ ss.getHeatSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText())
																+ " " + ss.getCoolSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										} else {
											// schedule_everydaytitle.get(i).getText()
											if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
													.equalsIgnoreCase(ss.getTimeOfEverydayScheduleOfGivenPeriod(
															schedule_everydaytitle.get(i).getText()))) {
												Keyword.ReportStep_Pass(testCase,
														"Period AWAY's expected time is shown correctly: " + inputs
														.getInputValue(InputVariables.EVERYDAY_AWAY_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period AWAY's expected time: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_TIME)
																+ " is not shown correctly: "
																+ ss.getTimeOfEverydayScheduleOfGivenPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										}
									} else if (i == 2) {
										if (!ss.getTimeOfEverydayScheduleOfGivenPeriod(
												schedule_everydaytitle.get(i).getText())
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
											if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
													.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
													.equalsIgnoreCase(ss.getHeatSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period HOME's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period HOME's expected time and heat and cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ ss.getHeatSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText())
																+ " " + ss.getCoolSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
													.equalsIgnoreCase(ss.getTimeOfEverydayScheduleOfGivenPeriod(
															schedule_everydaytitle.get(i).getText()))) {
												Keyword.ReportStep_Pass(testCase,
														"Period HOME's expected time is shown correctly: " + inputs
														.getInputValue(InputVariables.EVERYDAY_HOME_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period HOME's expected time: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_TIME)
																+ " is not shown correctly: "
																+ ss.getTimeOfEverydayScheduleOfGivenPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										}
									} else if (i == 3) {
										if (!ss.getTimeOfEverydayScheduleOfGivenPeriod(
												schedule_everydaytitle.get(i).getText())
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
											if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
													.equalsIgnoreCase(tempTime)
													&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
													.equalsIgnoreCase(ss.getHeatSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))
													&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period SLEEP's expected time and heat and cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period SLEEP's expected time and heat and cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
																+ ss.getHeatSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText())
																+ " " + ss.getCoolSetPointsOfGivenEverydayPeriod(
																		schedule_everydaytitle.get(i).getText()));
											}
										} else {
											if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
													.equalsIgnoreCase(ss.getTimeOfEverydayScheduleOfGivenPeriod(
															schedule_everydaytitle.get(i).getText()))) {
												Keyword.ReportStep_Pass(testCase,
														"Period SLEEP's expected time is shown correctly: " + inputs
														.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period SLEEP's expected time: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_TIME)
																+ " is not shown correctly: "
																+ ss.getTimeOfEverydayScheduleOfGivenPeriod(
																		schedule_everydaytitle.get(i).getText()));
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
					if (ss.isWeekdayTextVisible(5)) {
						try {
							ss.getWeekdayText();
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



					// start//////////////////////////
					if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
						List<WebElement> weekdayschedule_periodtimes = ss.getWeekdayTimeListElements();
						List<WebElement> weekdayschedule_periodheatsetPoint = ss
								.getWeekdayHeatSetpointListEMEAElements();
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_5_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_6_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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

						scrollForAndroidScreen(testCase);
						scrollForAndroidScreen(testCase);
						if (ss.isWeekendTextVisible(5)) {
							try {
								ss.getWeekendText();
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

						List<WebElement> weekendschedule_periodtimes = ss.getWeekendSchedulePeriodTimesElements();
						List<WebElement> weekendschedule_periodsetPoint = ss
								.getWeekendSchedulePeriodSetPointsElements();
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_5_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_6_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
						List<WebElement> weekdayschedule_periodtitle = ss.getWeekdayTitleListElements();
						Keyword.ReportStep_Pass(testCase,
								"*********************** Verifying Weekday-Weekend schedule period time and schedule period heat set points against set values **************************");

						for (int i = 0; i < weekdayschedule_periodtitle.size(); i++) {
							dateString = ss
									.getTimeOfWeekdayScheduleOfGivenPeriod(weekdayschedule_periodtitle.get(i).getText())
									.replaceAll("\\.", "");
							tempTime = dateString;
							if (i == 0) {
								if (!ss.getTimeOfWeekdayScheduleOfGivenPeriod(
										weekdayschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekdayScheduleOfGivenPeriod(
													weekdayschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekdayScheduleOfGivenPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 1) {
								if (!ss.getTimeOfWeekdayScheduleOfGivenPeriod(
										weekdayschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekdayScheduleOfGivenPeriod(
													weekdayschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekdayScheduleOfGivenPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 2) {
								if (!ss.getTimeOfWeekdayScheduleOfGivenPeriod(
										weekdayschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekdayScheduleOfGivenPeriod(
													weekdayschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekdayScheduleOfGivenPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 3) {
								if (!ss.getTimeOfWeekdayScheduleOfGivenPeriod(
										weekdayschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekdayPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekdayScheduleOfGivenPeriod(
													weekdayschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekdayScheduleOfGivenPeriod(
																weekdayschedule_periodtitle.get(i).getText()));
									}
								}
							}
						}
						scrollForAndroidScreen(testCase);
						scrollForAndroidScreen(testCase);
						List<WebElement> weekendschedule_periodtitle = ss.getWeekendTitleListElements();
						for (int i = 0; i < weekendschedule_periodtitle.size(); i++) {
							dateString = ss
									.getTimeOfWeekendScheduleOfGivenPeriod(weekendschedule_periodtitle.get(i).getText())
									.replaceAll("\\.", "");
							tempTime = dateString;
							if (i == 0) {
								if (!ss.getTimeOfWeekendScheduleOfGivenPeriod(
										weekendschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekendScheduleOfGivenPeriod(
													weekendschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekendScheduleOfGivenPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 1) {
								if (!ss.getTimeOfWeekendScheduleOfGivenPeriod(
										weekendschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekendScheduleOfGivenPeriod(
													weekendschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekendScheduleOfGivenPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 2) {
								if (!ss.getTimeOfWeekendScheduleOfGivenPeriod(
										weekendschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText())
														+ " " + ss.getCoolSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekendScheduleOfGivenPeriod(
													weekendschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekendScheduleOfGivenPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								}
							} else if (i == 3) {
								if (!ss.getTimeOfWeekendScheduleOfGivenPeriod(
										weekendschedule_periodtitle.get(i).getText()).equalsIgnoreCase("Tap to set")) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
											.equalsIgnoreCase(tempTime.replaceFirst("\\s+",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
											.equalsIgnoreCase(ss.getHeatSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ ss.getHeatSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText())
														+ " " + ss.getHeatSetPointsOfGivenWeekendPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
											.equalsIgnoreCase(ss.getTimeOfWeekendScheduleOfGivenPeriod(
													weekendschedule_periodtitle.get(i).getText()))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected time is shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected time: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
														+ " is not shown correctly: "
														+ ss.getTimeOfWeekendScheduleOfGivenPeriod(
																weekendschedule_periodtitle.get(i).getText()));
									}
								}
							}
						}
					}
				}
			} else {
				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					try {
						if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
							if (ss.isEverydayScheduleTitleAndPeriodTimeVisble(6)) {
								schedule_periodtime = ss.getEverydayScheduleTitleAndPeriodTimeElements();
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("1");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("2");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("3");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("4");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("5");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_5_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
									tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("6");
									if (tempHeatSetPointApp.contains(".0")) {
										tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
									}
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT);
									if (inputs.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT).contains(".0")) {
										tempHeatSetPointFromInputs = inputs
												.getInputValue(InputVariables.EVERYDAY_6_HEAT_SETPOINT).split("\\.")[0];
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
									if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
							if (ss.isEverydaySchedulePeriodTimeVisible(6)) {
								schedule_periodtime = ss.getEverydaySchedulePeriodTimesElements();
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
										tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("Wake");
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Wake");
										if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
												.equalsIgnoreCase(tempHeatSetPointApp.replaceAll("˚",""))
												&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period WAKE's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period WAKE's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
												.equalsIgnoreCase(tempTime)) {
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
										tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("Away");
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Away");
										if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
												.equalsIgnoreCase(tempHeatSetPointApp.replaceAll("˚",""))
												&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period AWAY's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period AWAY's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime + " "
															+ tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
												.equalsIgnoreCase(tempTime)) {
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
										tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("Home");
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Home");
										if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
												.equalsIgnoreCase(tempHeatSetPointApp.replaceAll("˚",""))
												&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period HOME's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period HOME's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_HOME_HEAT_SETPOINT)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime

															+ " " + tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
												.equalsIgnoreCase(tempTime)) {
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
										tempHeatSetPointApp = ss.getHeatSetPointsOfGivenEverydayPeriod("Sleep");
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Sleep");
										if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
												.equalsIgnoreCase(tempHeatSetPointApp.replaceAll("˚",""))
												&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period SLEEP's expected time and heat and cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period SLEEP's expected time and heat and cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT)
															+ " "
															+ inputs.getInputValue(
																	InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
															+ " are not shown correctly: " + tempTime

															+ " " + tempHeatSetPointApp + " " + tempCoolSetPointApp);
										}
									} else {
										if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
												.equalsIgnoreCase(tempTime)) {
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
						if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
							schedule_periodtimes_weekday = ss.getWeekdayPeriodTimeElements();
							schedule_periodtimes_weekend = ss.getWeekendPeriodTimeElements();
							schedule_weekday_heatsetpoints = ss.getWeekdayHeatSetpoints();
							schedule_weekend_heatsetpoints = ss.getWeekendHeatSetpoints();
							schedule_weekday_coolsetpoints = ss.getWeekdayCoolSetpoints();
							schedule_weekend_coolsetpoints = ss.getWeekendCoolSetpoints();
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekday_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekday_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekday_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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

									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekday_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekday_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected weekday time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekday_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekday_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekday.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
						scrollForAndroidScreen(testCase);
						scrollForAndroidScreen(testCase);
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
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekend_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekend_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekend_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME)
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
											.equalsIgnoreCase(
													schedule_weekend_heatsetpoints.get(j).getAttribute("value").replaceAll("˚",""))
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(schedule_weekend_coolsetpoints.get(j)
													.getAttribute("value").replaceAll("˚",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected Weekend time and heat and cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime + " "
														+ schedule_weekend_heatsetpoints.get(j).getAttribute("value")
														+ " "
														+ schedule_weekend_coolsetpoints.get(j).getAttribute("value"));
									}
									j++;
								} else {
									if (inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME).equalsIgnoreCase(
											schedule_periodtimes_weekend.get(i - 1).getAttribute("value").replaceAll("˚",""))) {
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
						if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(5)) {
							schedule_periodtimes_weekday = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
							schedule_weekday_heatsetpoints = ss.getWeekdayHeatSetpoints();
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate Weekday schedule period times");
						}
						if (ss.isWeekendScheduleTitleAndPeriodTimeVisble(5)) {
							schedule_periodtimes_weekend = ss.getWeekendScheduleTitleAndPeriodTimeElements();

							schedule_weekend_heatsetpoints = ss.getWeekendHeatSetpoints();
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
								tempHeatSetPointFromInputs = inputs
										.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT);
								if (inputs.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).contains(".0")) {
									tempHeatSetPointFromInputs = inputs
											.getInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT).split("\\.")[0];
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
								if (tempTimeInputs.trim().equalsIgnoreCase(tempTime.trim())
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
		} else if (scheduleType.equalsIgnoreCase("Geofence based")||scheduleType.equalsIgnoreCase("Geofence")) {
			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying goefence based schedule on Primary Card **************************");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ss.isWhenImHomeTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Home text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Home text not displayed on schedule screen");
				}

				if (ss.isUseMyHomeSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (ss.isCreateSleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (ss.isUseMySleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Use My Sleep Settings option not displayed on schedule screen");
					}
				}

				if (ss.isWhenImAwayTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Away text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Away text not displayed on schedule screen");
				}

				if (ss.isUseMyAwaySettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}
			} else {
				if (ss.isUseMyHomeSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (ss.isCreateSleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (ss.isUseMySleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Use My Sleep Settings option not displayed on schedule screen");
					}
				}
				if (ss.isUseMyAwaySettingsTextVisible(5)) {
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
				if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
					schedule_setpoints = ss.getSchedulePeriodHeatSetPointElement();
					tempHeatSetPointApp = schedule_setpoints.get(0).getText();
					if (schedule_setpoints.get(0).getText().contains(".0")||schedule_setpoints.get(0).getText().contains(".5")) {
						tempHeatSetPointApp = schedule_setpoints.get(0).getText().split("\\.")[0];
					}
					tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
					if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
						tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
					}
					if (tempHeatSetPointApp.replace("\u00B0", "").equalsIgnoreCase(tempHeatSetPointFromInputs)) {
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
						if (schedule_setpoints.get(1).getText().contains(".0")||schedule_setpoints.get(1).getText().contains(".5")) {
							tempHeatSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}

						if (tempHeatSetPointApp.replace("\u00B0", "").equalsIgnoreCase(tempHeatSetPointFromInputs)) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep set point is shown correctly in solution card: "
											+ tempHeatSetPointApp);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep set point: " + tempHeatSetPointFromInputs
									+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
						}

						SleepStartEndTime = ss.getSleepStartEndTime();

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
						if (SleepEndTime
								.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME).replaceAll("^0*", ""))) {
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
						if (schedule_setpoints.get(2).getText().contains(".0")||schedule_setpoints.get(2).getText().contains(".5")) {
							tempHeatSetPointApp = schedule_setpoints.get(2).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.replace("\u00B0", "").equalsIgnoreCase(tempHeatSetPointFromInputs)) {
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
						if (schedule_setpoints.get(1).getText().contains(".0")||schedule_setpoints.get(1).getText().contains(".5")) {
							tempHeatSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
							tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
						}
						if (tempHeatSetPointApp.replace("\u00B0", "").equalsIgnoreCase(tempHeatSetPointFromInputs)) {
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
				if (ss.isGeofenceHomeHeatElementVisible(5)) {
					homeHeatSetPointIOS = ss.getGeofenceHomeHeatElement();
					tempHeatSetPointApp = homeHeatSetPointIOS.getAttribute("value");
					if (tempHeatSetPointApp.contains(".0")||tempHeatSetPointApp.contains(".5")) {
						tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
					}
					tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
					if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
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
						sleepHeatSetPointIOS = ss.getGeofenceSleepHeatElement();
						tempHeatSetPointApp = sleepHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")||tempHeatSetPointApp.contains(".5")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
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

						SleepStartEndTime = ss.getGeofenceSleepSubTitleTimeText();

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

						if (inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME).contains(SleepStartTime)) {
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
						if (inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME).contains(SleepEndTime)) {
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

						awayHeatSetPointIOS = ss.getGeofenceAwayHeatElement();
						tempHeatSetPointApp = awayHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")||tempHeatSetPointApp.contains(".5")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
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
						awayHeatSetPointIOS = ss.getGeofenceAwayHeatElement();
						tempHeatSetPointApp = awayHeatSetPointIOS.getAttribute("value");
						if (tempHeatSetPointApp.contains(".0")||tempHeatSetPointApp.contains(".5")) {
							tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
						}
						tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
						if (tempHeatSetPointFromInputs.contains(".0")||tempHeatSetPointFromInputs.contains(".5")) {
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
			if (ss.isNoScheduleTextVisible(5)) {
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
				ss.clickOnBackButton();
			} else {
				if (ss.isCloseButtonVisible(5)) {
					if (!ss.clickOnCloseButton()) {
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

	private static void scrollForAndroidScreen(TestCases testCase) {
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		dimensions = testCase.getMobileDriver().manage().window().getSize();
		int startx = (dimensions.width * 20) / 100;
		int starty = (dimensions.height * 62) / 100;
		int endx = (dimensions.width * 22) / 100;
		int endy = (dimensions.height * 35) / 100;
		testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
	}

	private static void scrollUpForAndroidScreen(TestCases testCase) {
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
		int startx = (dimensions.width * 20) / 100;
		int starty = (dimensions.height * 62) / 100;
		int endx = (dimensions.width * 22) / 100;
		int endy = (dimensions.height * 35) / 100;
		testCase.getMobileDriver().swipe(endx, endy, startx, starty, 1000);
	}
	
	public static boolean verifyScheduleRetained(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true, scheduleRetainedFlag = true;
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (ss.isBackButtonVisible(5)) {
				if (!ss.clickOnBackButton()) {
					flag = false;
				}
			}
			if (ss.isBackButtonVisible(5)) {
				if (!ss.clickOnBackButton()) {
					flag = false;
				}
			}
		} else {
			int i = 0;
			while ((!ss.isTimeScheduleButtonVisible(5)) && i < 2) {
				if (ss.isCloseButtonVisible(3)) {
					flag = flag & ss.clickOnCloseButton();
				}
			}
			i++;
		}
		if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED)
				.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
			if (ss.isTimeScheduleButtonVisible(10)) {
				flag = flag & ss.clickOnTimeScheduleButton();
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ss.isUseMyHomeSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					scheduleRetainedFlag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}
				if (ss.isUseMyAwaySettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					scheduleRetainedFlag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}
			} else {
				if (ss.isUseMyHomeSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else if (ss.isUseMyAwaySettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else if (scheduleRetainedFlag = false) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}
			}
		} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED)
				.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {

		} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED)
				.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {

		}

		if (scheduleRetainedFlag) {
			Keyword.ReportStep_Pass(testCase,
					inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED) + " is retained in Solution card");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to retain "
					+ inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE_RETAINED) + " in Solution card");
		}

		return flag;
	}

	public static boolean createGeofenceBasedScheduleWithMinMaxSetPoints(TestCases testCase, TestCaseInputs inputs,
			boolean createScheduleUsingUseGeofenceButton) {
		boolean flag = true;
		try {
			SchedulingScreen ss = new SchedulingScreen(testCase);
			//			flag = flag & viewScheduleOnPrimaryCard(testCase);

			if (ss.isCreateScheduleButtonVisible(5)) {
				flag = flag & ss.clickOnCreateScheduleButton();
			} else {
				if (ss.isScheduleOffOverlayVisible(5)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();
				if (ss.isSwitchToGeofenceButtonVisible(5)) {
					flag = flag & ss.clickOnSwitchToGeofenceButton();
				}
			}
			if (createScheduleUsingUseGeofenceButton) {
				flag = flag & ss.clickOnUseGeofencingText();
			} else {
				flag = flag & ss.clickOnLearnMoreButton();
			}

			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Home period ***************");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isHomeTemperatureHeaderMultiTemperatureVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				}
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isHomeTemperatureHeaderSingleTemperatureVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				}
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isHomeTemperatureHeaderSingleTemperatureVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				}
			}

			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting maximum and minimum set points for Home ***************");
			inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_HOME);
			flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
			flag = flag & ss.clickOnNextButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting maximum and minimum set points for Home ***************");
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Away period ***************");
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isAwayTemperatureHeaderMultiTemperatureVisble(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				}
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isAwayTemperatureHeaderSingleTemperatureVisble(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				}
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isAwayTemperatureHeaderSingleTemperatureVisble(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				}
			}
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting maximum and minimum set points for Away ***************");
			inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_AWAY);
			flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
			flag = flag & ss.clickOnNextButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting maximum and minimum set points for Away ***************");
			if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & ss.clickOnSkipSleepButton();
				} else {
					flag = flag & ss.clickOnNoButton();
				}

			} else {
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Setting time and set points for Sleep period ***************");
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					//flag = flag & ss.clickOnYesButton();
				}
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,
						inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME), "GeofenceSleepStartTime", true,
						true);
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,
						inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME), "GeofenceSleepEndTime", true,
						true);
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Setting maximum and minimum set points for Sleep ***************");
				inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
				flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
				flag = flag & ss.clickOnNextButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting maximum and minimum set points for Sleep ***************");
			}
			// flag = flag & InputVariables.verifyCreatedSchedule(testCase, inputs,
			// "Geofence");
			if (ss.IsSaveButtonVisible(10)) {
				flag = flag & ss.clickOnSaveButton();
			}
			if (ss.isSkipButtonVisible(5)) {
				flag = flag & ss.clickOnSkipButton();
			}
			if (ss.isTimeScheduleButtonVisible(10)) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Create Schedule : Failed to navigate to Primary Card");
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean setGeofenceSchedulePeriodSetPoints(TestCases testCase, TestCaseInputs inputs,
			String periodName, HashMap<String, String> targetSetPoints) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag
					& JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, targetSetPoints, false);
		} else {
			SchedulingScreen ss = new SchedulingScreen(testCase);
			try {
				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
				List<String> allowedModes = statInfo.getAllowedModes();
				String coolSetPoint = " ";
				String heatSetPoint = " ";
				WebElement coolUp = null;
				WebElement heatUp = null;
				WebElement coolDown = null;
				WebElement heatDown = null;
				int coolScroller = 0;
				int heatScroller = 0;
				int size = 0;

				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					String coolTemp = " ";
					String heatTemp = " ";
					if (periodName.equals("Home") || periodName.equals("Sleep")) {
						flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
								targetSetPoints, false);
						return flag;
					} else if (periodName.equals("Away")) {
						size = ss.getCoolSetPointsElements().size();
						if (size > 1) {
							coolSetPoint = ss.getCoolSetPointsElements().get(1).getAttribute("value");
							heatSetPoint = ss.getHeatSetPointsElements().get(1).getAttribute("value");
							coolUp = ss.getCoolIncrementElements().get(1);
							coolDown = ss.getCoolDecrementElements().get(1);
							heatUp = ss.getHeatIncrementElements().get(1);
							heatDown = ss.getHeatDecrementElements().get(1);

						} else {
							coolSetPoint = ss.getCoolSetPointsElements().get(0).getAttribute("value");
							heatSetPoint = ss.getHeatSetPointsElements().get(0).getAttribute("value");
							if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
									&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
								coolUp = ss.getCoolIncrementElements().get(0);
								coolDown = ss.getCoolDecrementElements().get(0);
								heatUp = ss.getHeatIncrementElements().get(0);
								heatDown = ss.getHeatDecrementElements().get(0);
							} else {
								coolUp = ss.getCoolIncrementElements().get(1);
								coolDown = ss.getCoolDecrementElements().get(0);
								heatUp = ss.getHeatIncrementElements().get(1);
								heatDown = ss.getHeatDecrementElements().get(0);
							}
						}
					}

					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetCoolTemp = Integer.parseInt(targetSetPoints.get("targetCoolTemp"));
						int targetHeatTemp = Integer.parseInt(targetSetPoints.get("targetHeatTemp"));

						Double temp = Double.parseDouble(coolSetPoint);
						coolScroller = temp.intValue() - targetCoolTemp;
						temp = Double.parseDouble(heatSetPoint);
						heatScroller = temp.intValue() - targetHeatTemp;
						coolTemp = String.valueOf(targetCoolTemp);
						heatTemp = String.valueOf(targetHeatTemp);
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						Double scroller = (Double.parseDouble(coolSetPoint) - targetCoolTemp) * 2;
						coolScroller = scroller.intValue();
						scroller = (Double.parseDouble(heatSetPoint) - targetHeatTemp) * 2;
						heatScroller = scroller.intValue();
						coolTemp = String.valueOf(targetCoolTemp);
						heatTemp = String.valueOf(targetHeatTemp);
					}

					if (coolScroller < 0) {
						coolScroller *= -1;
						for (int j = 0; j < coolScroller; j++) {
							TouchAction t1 = new TouchAction(testCase.getMobileDriver());
							t1.tap(coolUp.getLocation().getX() + 20, heatUp.getLocation().getY() + 20).perform();
						}
					} else if (coolScroller > 0) {
						for (int j = 0; j < coolScroller; j++) {
							coolDown.click();
						}
					}
					size = ss.getCoolSetPointsElements().size();
					if (periodName.equals("Away") && (size > 1)) {
						coolSetPoint = ss.getCoolSetPointsElements().get(1).getAttribute("value");
					} else {
						coolSetPoint = ss.getCoolSetPointsElements().get(0).getAttribute("value");
					}
					flag = flag & verifyCoolStepperValue(testCase, inputs, coolTemp, "");
					if (heatScroller < 0) {
						heatScroller *= -1;
						for (int j = 0; j < heatScroller; j++) {
							TouchAction t1 = new TouchAction(testCase.getMobileDriver());
							t1.tap(heatUp.getLocation().getX() + 20, heatUp.getLocation().getY() + 20).perform();
						}
					} else if (heatScroller > 0) {
						for (int j = 0; j < heatScroller; j++) {
							heatDown.click();
						}
					}
					size = ss.getHeatSetPointsElements().size();
					if (periodName.equals("Away") && (size > 1)) {
						heatSetPoint = ss.getHeatSetPointsElements().get(1).getAttribute("value");
					} else {
						heatSetPoint = ss.getHeatSetPointsElements().get(0).getAttribute("value");
					}
					flag = flag & verifyHeatStepperValue(testCase, inputs, heatTemp, "");
				}

				else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					String heatTemp = " ";
					size = ss.getHeatSetPointsElements().size();

					if (periodName.equalsIgnoreCase("Away")) {
						heatSetPoint = ss.getHeatSetPointsElements().get(0).getAttribute("value");
						if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
								&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
							heatUp = ss.getHeatIncrementElements().get(0);
						} else if(ss.getHeatIncrementElements().size()>1){
							heatUp = ss.getHeatIncrementElements().get(1);
						}else{
							heatUp = ss.getHeatIncrementElements().get(0);
						}
					} else {
						heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
						heatUp = ss.getHeatIncrementElements().get(0);
					}
					heatDown = ss.getHeatSetPointDownButton();

					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetHeatTemp = Integer.parseInt(targetSetPoints.get("targetHeatTemp"));
						Double temp = Double.parseDouble(heatSetPoint);
						heatScroller = temp.intValue() - targetHeatTemp;
						heatTemp = String.valueOf(targetHeatTemp);
					} else {
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						Double scroller = (Double.parseDouble(heatSetPoint) - targetHeatTemp) * 2;
						heatScroller = scroller.intValue();
						heatTemp = String.valueOf(targetHeatTemp);
					}

					if (heatScroller < 0) {
						heatScroller *= -1;
						for (int j = 0; j < heatScroller; j++) {

							TouchAction t1 = new TouchAction(testCase.getMobileDriver());
							t1.tap(heatUp.getLocation().getX() + 20, heatUp.getLocation().getY() + 20).perform();

						}
					} else if (heatScroller > 0) {
						for (int j = 0; j < heatScroller; j++) {
							heatDown.click();
						}
					}

					if (periodName.equalsIgnoreCase("Away") && (size > 1)) {
						heatSetPoint = ss.getHeatSetPointsElements().get(1).getAttribute("value");
					} else {
						heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
					}

					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double temp = Double.parseDouble(heatSetPoint);
						heatSetPoint = String.valueOf(temp.intValue());
					}
					if (heatSetPoint.equals(heatTemp)) {
						Keyword.ReportStep_Pass(testCase,
								"Set Schedule Period Set Points : Heat Set Point Successfully set to : "
										+ heatSetPoint);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Schedule Period Set Points : Failed to set heat set point to : " + heatTemp);
					}
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					String coolTemp = " ";
					size = ss.getCoolSetPointsElements().size();
					if (periodName.equalsIgnoreCase("Away")) {
						coolSetPoint = ss.getCoolSetPointsElements().get(0).getAttribute("value");
						if (!inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE).isEmpty()
								&& inputs.getInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE) != null) {
							coolUp = ss.getCoolIncrementElements().get(0);
						} else {
							coolUp = ss.getCoolIncrementElements().get(1);
						}
					} else {
						coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
						coolUp = ss.getCoolIncrementElements().get(0);
					}
					coolDown = ss.getCoolSetPointDownButton();

					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetCoolTemp = Integer.parseInt(targetSetPoints.get("targetCoolTemp"));
						Double temp = Double.parseDouble(coolSetPoint);
						coolScroller = temp.intValue() - targetCoolTemp;
						coolTemp = String.valueOf(targetCoolTemp);
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						Double scroller = (Double.parseDouble(heatSetPoint) - targetCoolTemp) * 2;
						coolScroller = scroller.intValue();
						coolTemp = String.valueOf(targetCoolTemp);
					}
					if (coolScroller < 0) {
						coolScroller *= -1;
						for (int j = 0; j < coolScroller; j++) {

							TouchAction t1 = new TouchAction(testCase.getMobileDriver());
							t1.tap(coolUp.getLocation().getX() + 20, coolUp.getLocation().getY() + 20).perform();

						}
					} else if (coolScroller > 0) {
						for (int j = 0; j < coolScroller; j++) {
							coolDown.click();
						}
					}

					if (periodName.equalsIgnoreCase("Away") && (size > 1)) {
						coolSetPoint = ss.getCoolSetPointsElements().get(1).getAttribute("value");
					} else {
						coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
					}

					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double temp = Double.parseDouble(coolSetPoint);
						coolSetPoint = String.valueOf(temp.intValue());
					}
					if (coolSetPoint.equals(coolTemp)) {
						Keyword.ReportStep_Pass(testCase,
								"Set Schedule Period Set Points : Cool Set Point Successfully set to : "
										+ coolSetPoint);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Set Schedule Period Set Points : Failed to set cool set point to : " + coolTemp);
					}
				}

			}

			catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set Schedule Period Set Points : Error Occured : " + e.getMessage());
			}
		}
		return flag;
	}

	public static boolean setGeofenceSchedulePeriodSetPoints(TestCases testCase, TestCaseInputs inputs,
			HashMap<String, String> targetSetPoints, boolean validateMinMaxSetPoints) {
		boolean flag = true;

		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			String coolTemp = " ";
			String heatTemp = " ";

			if (validateMinMaxSetPoints) {
				HashMap<String, String> minMaxSetPoints = statInfo.getDeviceMaxMinSetPoints();
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 1));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
										String.valueOf(targetCoolTemp - 1));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 1));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
										String.valueOf(targetCoolTemp - 1));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 1));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
										String.valueOf(targetCoolTemp - 1));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 1));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
										String.valueOf(targetCoolTemp + 1));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 1));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
										String.valueOf(targetCoolTemp + 1));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 1));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
										String.valueOf(targetCoolTemp + 1));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
										String.valueOf(targetCoolTemp));
							}
						} else {
							HashMap<String, String> targetHeatCoolPoints = new HashMap<String, String>();
							// Double maxHeat =
							// Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									minCool, minHeat);
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
							}
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetHeatCoolPoints, false);
						}
					} else {
						Double targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("MaxHeat"));
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
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MaxCool"));
							targetHeatTemp = Double.parseDouble(targetSetPoints.get("MaxHeat"));
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
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetHeatTemp = Double.parseDouble(targetSetPoints.get("MinHeat"));
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
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							targetCoolTemp = Double.parseDouble(minMaxSetPoints.get("MinCool"));
							targetHeatTemp = Double.parseDouble(targetSetPoints.get("MinHeat"));
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
						} else {
							HashMap<String, String> targetHeatCoolPoints = new HashMap<String, String>();
							Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
							Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
							Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxHeat, minHeat);
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, heatTemp);
								targetHeatCoolPoints.put("targetHeatTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, coolTemp);
								targetHeatCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
							}
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetHeatCoolPoints, false);
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
						} else {
							Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							HashMap<String, String> targetCoolPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
							}
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetCoolPoints, false);
						}
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("MaxCool"));
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
						} else {
							Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
							Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
							coolTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
									maxCool, minCool);
							HashMap<String, String> targetCoolPoints = new HashMap<String, String>();
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, coolTemp);
								targetCoolPoints.put("targetCoolTemp",
										inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
							}
							flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs,
									targetCoolPoints, false);
						}
					}
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						Double temp = Double.parseDouble(minMaxSetPoints.get("MaxHeat"));
						int targetHeatTemp = temp.intValue();
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

					} else {
						Double targetHeatTemp = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 0.5));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 0.5));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp - 0.5));
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MAXIMUM)) {
							targetHeatTemp = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, heatTemp);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, heatTemp);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, heatTemp);
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.BELOW_MINIMUM)) {
							targetHeatTemp = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 0.5));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 0.5));
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
										String.valueOf(targetHeatTemp + 0.5));
							}
						} else if (inputs.getInputValue(InputVariables.SETPOINT_RANGE)
								.equalsIgnoreCase(InputVariables.AT_MINIMUM)) {
							targetHeatTemp = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
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
							if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
								inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, heatTemp);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
								inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, heatTemp);
							} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
									.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
								inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, heatTemp);
							}
						} else {
							if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
									.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
								HashMap<String, String> targetHeatPoints = new HashMap<String, String>();
								Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
								Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
								heatTemp = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase,
										inputs, maxHeat, minHeat);
								String tempGeofencePeriod = "";
								if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
										.equalsIgnoreCase(InputVariables.GEOFENCE_HOME)) {
									inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, heatTemp);
									targetHeatPoints.put("targetHeatTemp",
											inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
									tempGeofencePeriod = "Home";
								} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
										.equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)) {
									inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, heatTemp);
									targetHeatPoints.put("targetHeatTemp",
											inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
									tempGeofencePeriod = "Away";
								} else if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD)
										.equalsIgnoreCase(InputVariables.GEOFENCE_SLEEP)) {
									inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, heatTemp);
									targetHeatPoints.put("targetHeatTemp",
											inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
									tempGeofencePeriod = "Sleep";
								}
								flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs,
										tempGeofencePeriod, targetHeatPoints);
							}
						}
					}
				}
			} else {
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					if (inputs.getInputValue(InputVariables.UNITS).equals(GlobalVariables.FAHRENHEIT)) {
						int targetCoolTemp = Integer.parseInt(targetSetPoints.get("targetCoolTemp"));
						int targetHeatTemp = Integer.parseInt(targetSetPoints.get("targetHeatTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
						heatTemp = String.valueOf(targetHeatTemp);
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
						heatTemp = String.valueOf(targetHeatTemp);
					}
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
						int targetHeatTemp = Integer.parseInt(targetSetPoints.get("targetHeatTemp"));
						heatTemp = String.valueOf(targetHeatTemp);
					} else {
						Double targetHeatTemp = Double.parseDouble(targetSetPoints.get("targetHeatTemp"));
						heatTemp = String.valueOf(targetHeatTemp);
					}
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
						int targetCoolTemp = Integer.parseInt(targetSetPoints.get("targetCoolTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
					} else {
						Double targetCoolTemp = Double.parseDouble(targetSetPoints.get("targetCoolTemp"));
						coolTemp = String.valueOf(targetCoolTemp);
					}
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

	public static boolean verifyCoolStepperValue(TestCases testCase, TestCaseInputs inputs, String coolTemp,
			String verifyMinimumOrMaximum) {
		boolean flag = true;
		try {
			String coolSetPoint = "";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			SchedulingScreen ss = new SchedulingScreen(testCase);
			HashMap<String, String> minMaxSetPoints = statInfo.getDeviceMaxMinSetPoints();
			String jasperStatType = statInfo.getJasperDeviceType();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
			} else {
				int size = ss.getCoolSetPointsElements().size();
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					coolSetPoint = ss.getCoolSetPointsElements().get(1).getAttribute("value");
				} else {
					coolSetPoint = ss.getCoolSetPointChooserSetPointsValue();
				}
			}
			if (verifyMinimumOrMaximum.equalsIgnoreCase("AboveMaximum")) {
				String setPoints = minMaxSetPoints.get("MaxCool");
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}
				if (coolSetPoint.equals(coolTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Cool Stepper Value : Cool Set Point set to max set point after trying to set it to a value above maximum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Cool Stepper Value : Cool Set Point not set to max set point after trying to set it to a value above maximum set points");
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected cool Stepper Value:"+coolTemp+" displayed value is "+coolSetPoint );
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Maximum")) {
				String setPoints = minMaxSetPoints.get("MaxCool");
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
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
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
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
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					coolTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						coolTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						coolTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
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
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
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
		}
		return flag;
	}

	public static boolean verifyHeatStepperValue(TestCases testCase, TestCaseInputs inputs, String heatTemp,
			String verifyMinimumOrMaximum) {
		boolean flag = true;
		try {
			String heatSetPoint = " ";
			SchedulingScreen ss = new SchedulingScreen(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			HashMap<String, String> minMaxSetPoints = statInfo.getDeviceMaxMinSetPoints();
			String jasperStatType = statInfo.getJasperDeviceType();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
				Keyword.ReportStep_Pass(testCase,"Displayed value "+heatSetPoint);
			} else {
				int size = ss.getHeatSetPointsElements().size();
				if (inputs.getInputValue(InputVariables.GEOFENCE_PERIOD).equalsIgnoreCase(InputVariables.GEOFENCE_AWAY)
						&& (size > 1)) {
					heatSetPoint = ss.getHeatSetPointsElements().get(1).getAttribute("value");
				} else {
					heatSetPoint = ss.getHeatSetPointChooserSetPointsValue();
				}
			}

			if (verifyMinimumOrMaximum.equalsIgnoreCase("AboveMaximum")) {
				 heatTemp = minMaxSetPoints.get("MaxHeat");
				
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT) && !jasperStatType.equalsIgnoreCase("EMEA")) {
					Double temp = Double.parseDouble(heatTemp);
					heatTemp = String.valueOf(temp.intValue());
					Keyword.ReportStep_Pass(testCase,
							"Stat is NA and unit is fahrenheit, hence converted to integer"+heatTemp);
				}else{
					Keyword.ReportStep_Pass(testCase,
							"Stat is either not NA or  not unit is fahrenheit, hence converted to integer"+heatTemp);
				}
					/*else {
				}
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = roundOffCelsiusData(testCase, heatTemp);
					} else {
						heatTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, heatTemp));
					}
				}*/
				
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to max set point after trying to set it to a value above maximum set points");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to max set point after trying to set it to a value above maximum set points");
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Expected Heat Stepper Value:"+heatTemp+" displayed value is "+heatSetPoint );
				}
			} else if (verifyMinimumOrMaximum.equalsIgnoreCase("Maximum")) {
				String setPoints = minMaxSetPoints.get("MaxHeat");
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
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
				if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
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
				/*if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(setPoints);
					heatTemp = String.valueOf(temp.intValue());
				} else {
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						heatTemp = roundOffCelsiusData(testCase, setPoints);
					} else {
						heatTemp = roundOffCelsiusData(testCase, convertFromFahrenhietToCelsius(testCase, setPoints));
					}
				}*/
				if (heatSetPoint.equals(heatTemp)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point set to min set point");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat Set Point not set to min set point");
				}
			} else {
				/*if (statInfo.getThermostatUnits().equals(GlobalVariables.FAHRENHEIT)) {
					Double temp = Double.parseDouble(heatSetPoint);
					heatSetPoint = String.valueOf(temp.intValue());
				}*/
				Keyword.ReportStep_Pass(testCase,
						"Expected : "+heatTemp);
				Keyword.ReportStep_Pass(testCase,
						"Displayed :"+heatSetPoint);
				System.out.println("Displayed value "+heatSetPoint+ " and Expected : "+heatTemp);
				if (heatSetPoint.trim().equals(heatTemp.trim())) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Heat Stepper Value : Heat Set Point Successfully set to : " + heatSetPoint);
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Heat Stepper Value : Heat set point is not set to : " + heatTemp);
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Exception occurred : " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	public static boolean createTimeBasedSchedule(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			WebElement element = null;
			SchedulingScreen ss = new SchedulingScreen(testCase);
			if (ss.isTimeScheduleButtonVisible(10)) {
				flag = flag & viewScheduleOnPrimaryCard(testCase);
			}

			if (ss.isCreateScheduleButtonVisible(5)) {
				flag = flag & ss.clickOnCreateScheduleButton();

				if (ss.isTimeOptionVisible(5)) {
					flag = flag & ss.clickOnTimeOption();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Unable to navigate to create schedule page.");
					return false;
				}
			} else {
				if (ss.isScheduleOffOverlayVisible(5)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();

				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
				String currentScheduleType = statInfo.getThermoStatScheduleType();
                Keyword.ReportStep_Pass(testCase, "Current schedule is :"+currentScheduleType);
				if (currentScheduleType.equalsIgnoreCase("Timed")) {
					flag = flag & ss.clickOnCreateNewTimeScheduleButton();
				} else {
					flag = flag & ss.clickOnSwitchToTimeScheduleButton();
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				flag = flag & ss.clickOnEverydayScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					String[] modes = { "Wake", "Away", "Home", "Sleep" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = statInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("Wake")) {
							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
							element = ss.getEverydayWakeElement();
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
							element = ss.getEverydayAwayElement();
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
							element = ss.getEverydayHomeElement();
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
							element = ss.getEverydaySleepElement();
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
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
						flag = flag & setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint, element);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
					}
				} else {
					String[] modes = { "1", "2", "3", "4" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = statInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("1")) {
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
							element = ss.getEveryday1Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
							element = ss.getEveryday2Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
							element = ss.getEveryday3Element();
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
							periodTimeandSetPoint.put("StartTime",
									inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
							element = ss.getEveryday4Element();
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
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
						flag = flag & setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint,
								element);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
					}
				}
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				flag = flag & ss.clickOnWeekdayandWeekendScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher") ) {
					String[] modes = { "Wake_Weekday", "Away_Weekday", "Home_Weekday", "Sleep_Weekday", "Wake_Weekend",
							"Away_Weekend", "Home_Weekend", "Sleep_Weekend" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = statInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("Wake_Weekday")) {
							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
							element = ss.getWeekdayWakeElement();
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
							element = ss.getWeekdayAwayElement();
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
							element = ss.getWeekdayHomeElement();
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
							element = ss.getWeekdaySleepElement();
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
									element = ss.getWeekendWakeElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendWakeElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e3) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Wake_Saturday-Sunday");
										}
									}
									element = ss.getWeekendWakeElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendWakeElement();
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
									element = ss.getWeekendAwayElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendAwayElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Away_Saturday - Sunday");
										}
									}
									element = ss.getWeekendAwayElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendAwayElement();
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
									element = ss.getWeekendHomeElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendHomeElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Home_Saturday-Sunday");
										}
									}
									element = ss.getWeekendHomeElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendHomeElement();
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
									element = ss.getWeekendSleepElement();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekendSleepElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Sleep_Saturday-Sunday");
										}
									}
									element = ss.getWeekendSleepElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendSleepElement();
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
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
						flag = flag & setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint,
								element);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
					}
				} else {
					String[] modes = { "1_Weekday", "2_Weekday", "3_Weekday", "4_Weekday", "1_Weekend", "2_Weekend",
							"3_Weekend", "4_Weekend" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = statInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("1_Weekday")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKDAY_1_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKDAY_2_TIME));
							element = ss.getWeekday1Element();
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
						} else if (mode.equals("2_Weekday")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKDAY_2_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
							element = ss.getWeekday2Element();
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
						} else if (mode.equals("3_Weekday")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKDAY_3_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
							element = ss.getWeekday3Element();
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
						} else if (mode.equals("4_Weekday")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKDAY_4_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKDAY_1_TIME));
							element = ss.getWeekday4Element();
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
						} else if (mode.equals("1_Weekend")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKEND_1_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKEND_2_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend1Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend1ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e3) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Saturday-Sunday_1");
										}
									}
									element = ss.getWeekend1Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend1Element();
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
						} else if (mode.equals("2_Weekend")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKEND_2_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekend2Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend2ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element 2_Saturday-Sunday");
										}
									}
									element = ss.getWeekend2Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend2Element();
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
						} else if (mode.equals("3_Weekend")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKEND_3_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekend3Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend3ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element 3_Saturday-Sunday");
										}
									}
									element = ss.getWeekend3Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend3Element();
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
						} else if (mode.equals("4_Weekend")) {
							periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.WEEKEND_4_TIME));
							periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.WEEKEND_1_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekend4Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
									if (!ss.isWeekend4ElementVisible(5)) {
										try {
											action.press(10, (int) (dimension.getHeight() * .5))
											.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element 4_Saturday-Sunday");
										}
									}
									element = ss.getWeekend4Element();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekend4Element();
									}

								} catch (NoSuchElementException e1) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Create Schedule : Could not find element 4_Saturday-Sunday");
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
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
						flag = flag & setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint,
								element);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ periodTimeandSetPoint.get("periodName") + " period ***************");
					}
				}
			}
			// flag = flag & InputVariables.verifyCreatedSchedule(testCase, inputs,
			// "Time");
			flag = flag & ss.clickOnDoneButton();
			if (ss.isConfirmChangeButtonVisible(10)) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isConfirmChangeButtonVisible(5)) {
						ss.clickOnConfirmChangeButton();
					}
				} else {
					if (!ss.clickOnConfirmChangeButton()) {
						flag = false;
					}
				}
			}
			if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
				System.out.println("Copy all");
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
				if (ss.isCopyButtonVisible(3)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
					System.out.println(checkBoxes.size());
					String SelectStatPosition = getRandomSetPointValueBetweenMinandMax(testCase, inputs,
							Double.parseDouble("0"), Double.parseDouble(String.valueOf(checkBoxes.size())));
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Pass(testCase, "Selecting stat at Position " + SelectStatPosition
								+ ", copying to " + checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Selecting stat at Position " + SelectStatPosition + ", copying to "
										+ checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
					}

					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value")
								.equals("Disabled")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
						}
					} else {
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked")
								.equals("false")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
						}
					}

				}
				if (ss.isCopyButtonVisible(3)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else {
				if (ss.isSkipButtonVisible(3)) {
					flag = flag & ss.clickOnSkipButton();
				}
			}
			if (ss.isTimeScheduleButtonVisible(10)) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Create Schedule : Failed to navigate to Primary Card");
			}
		} catch (Exception e) {

		}
		return flag;
	}
	public static boolean createSpecificPeriodtimebaseschedule(TestCases testCase, TestCaseInputs inputs, String Period, String startTime,String EndTime) {
		boolean flag = true;
		try {
			WebElement element = null;
			SchedulingScreen ss = new SchedulingScreen(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String currentScheduleType = statInfo.getThermoStatScheduleType();
			if (ss.isTimeScheduleButtonVisible(2)) {
				flag = flag & viewScheduleOnPrimaryCard(testCase);
			}

			if (ss.isCreateScheduleButtonVisible(2)) {
				flag = flag & ss.clickOnCreateScheduleButton();

				if (ss.isTimeOptionVisible(2)) {
					flag = flag & ss.clickOnTimeOption();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Unable to navigate to create schedule page.");
					return false;
				}
			} else {
				if (ss.isScheduleOffOverlayVisible(2)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();

				if (currentScheduleType.equalsIgnoreCase("Timed")) {
					flag = flag & ss.clickOnCreateNewTimeScheduleButton();
				} else {
					flag = flag & ss.clickOnSwitchToTimeScheduleButton();
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				flag = flag & ss.clickOnEverydayScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					
					
						if (Period.equalsIgnoreCase("WAKE")) {
							element = ss.getEverydayWakeElement();
							
						} else if (Period.equalsIgnoreCase("AWAY")) {
							element = ss.getEverydayAwayElement();
							
						} else if (Period.equalsIgnoreCase("HOME")) {
							element = ss.getEverydayHomeElement();
						
						} else if (Period.equals("SLEEP")) {
							element = ss.getEverydaySleepElement();
						}
						if(element.isDisplayed()){
								element.click();
								Keyword.ReportStep_Pass(testCase, "Successfully click on : Add period");
							}else{
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Set Period Time and Set Points : Failed to select " + Period );	
							}
						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ Period + " period ***************");
						
						/********time*******/
						
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, startTime,"TimeChooser", true, true);
						
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ Period + " period ***************");
						/*******time******/
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ Period + " period ***************");
				
				}else {
					
						if (Period.equals("P1")) {
							element = ss.getEveryday1Element();
							
						} else if (Period.equals("P2")) {
							element = ss.getEveryday2Element();
							
						} else if (Period.equals("P3")) {
								inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT);
							}
						 else if (Period.equals("P4")) {
							element = ss.getEveryday4Element();
							
						}
						if(element.isDisplayed()){
							element.click();
							Keyword.ReportStep_Pass(testCase, "Successfully click on : Add period");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Set Period Time and Set Points : Failed to select " + Period );	
						}
						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting time and set points for "
								+ Period + " period ***************");


						/********time*******/
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,Period,
									"TimeChooser", true, true);
						flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, Period,
									"TimeChooserEndTime", true, true);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ Period + " period ***************");
						/*******time******/
						
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase, "*************** Completed setting time and set points for "
								+ Period + " period ***************");
			
					
					}
			} 
			
			if (ss.isConfirmChangeButtonVisible(10)) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isConfirmChangeButtonVisible(5)) {
						ss.clickOnConfirmChangeButton();
					}
				} else {
					if (!ss.clickOnConfirmChangeButton()) {
						flag = false;
					}
				}
			}
			if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
				System.out.println("Copy all");
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
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
				if (ss.isCopyButtonVisible(3)) {
					flag = flag & ss.clickOnCopyButton();
				}
			} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
				if (ss.isCheckBoxVisible(3)) {
					List<WebElement> checkBoxes = ss.getAllCheckBoxElements();
					System.out.println(checkBoxes.size());
					String SelectStatPosition = getRandomSetPointValueBetweenMinandMax(testCase, inputs,
							Double.parseDouble("0"), Double.parseDouble(String.valueOf(checkBoxes.size())));
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						Keyword.ReportStep_Pass(testCase, "Selecting stat at Position " + SelectStatPosition
								+ ", copying to " + checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Selecting stat at Position " + SelectStatPosition + ", copying to "
										+ checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
					}

					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("value")
								.equals("Disabled")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("label"));
						}
					} else {
						if (checkBoxes.get(Integer.parseInt(SelectStatPosition)).getAttribute("checked")
								.equals("false")) {
							checkBoxes.get(Integer.parseInt(SelectStatPosition)).click();
							inputs.setInputValue(InputVariables.STAT_TO_COPY_SCHEDULE,
									checkBoxes.get(Integer.parseInt(SelectStatPosition)).getText());
						}
					}

				}
				if (ss.isCopyButtonVisible(3)) {
					flag = flag & ss.clickOnCopyButton();
				}
				} else {
				if (ss.isSkipButtonVisible(3)) {
					flag = flag & ss.clickOnSkipButton();
				}
			}
			if (ss.isConfirmChangeButtonVisible(4)){
				ss.clickOnConfirmChangeButton();
			}
			if (ss.isTimeScheduleButtonVisible(10)) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Create Schedule : Failed to navigate to Primary Card");
			}
		} catch (Exception e) {

		}
		return flag;
	}

	public static boolean createTimeBasedScheduleWithMinMaxSetPoints(TestCases testCase, TestCaseInputs inputs) {

		boolean flag = true;
		SchedulingScreen ss = new SchedulingScreen(testCase);
		try {
			WebElement element = null;

			if (ss.isCreateScheduleButtonVisible(5)) {
				flag = flag & ss.clickOnCreateScheduleButton();

				if (ss.isTimeOptionVisible(5)) {
					flag = flag & ss.clickOnTimeOption();
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Unable to navigate to create schedule page.");
					return false;
				}
			} else {
				if (ss.isScheduleOffOverlayVisible(5)) {
					if (!ss.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & ss.clickOnScheduleOptionsButton();

				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
				String currentScheduleType = statInfo.getThermoStatScheduleType();

				if (currentScheduleType.equalsIgnoreCase("Timed")) {
					flag = flag & ss.clickOnCreateNewTimeScheduleButton();
				} else {
					flag = flag & ss.clickOnSwitchToTimeScheduleButton();
				}
			}

			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				flag = flag & ss.clickOnEverydayScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					String[] modes = { "Wake", "Away", "Home", "Sleep" };
					for (String mode : modes) {
						if (mode.equals("Wake")) {
							element = ss.getEverydayWakeElement();
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_WAKE);
						} else if (mode.equals("Away")) {
							element = ss.getEverydayAwayElement();
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_AWAY);
						} else if (mode.equals("Home")) {
							element = ss.getEverydayHomeElement();
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_HOME);
						} else if (mode.equals("Sleep")) {
							element = ss.getEverydaySleepElement();
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_SLEEP);
						}
						try {
							element.click();
							Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully clicked on " + mode);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule : Failed to click on " + mode);
						}

						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting maximum and minimum set points for "
								+ mode + " period ***************");
						flag = flag
								& JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points for " + mode
								+ " period ***************");
					}
				}
				// ================================================EMEA===========================================================
				else {
					String[] modes = { "1", "2", "3", "4" };
					for (String mode : modes) {
						if (mode.equals("1")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getEveryday1Element();
								if (ss.getEverydayPeriodTimeElements().get(0) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_1_TIME,
											ss.getEverydayPeriodTimeElements().get(0).getText());
								}
							} else {
								element = ss.getEveryday1Element();
								if (ss.getValueOfEverydayTimeElementAtIndex(1).get(0) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_1_TIME,
											ss.getValueOfEverydayTimeElementAtIndex(1).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_1);
						} else if (mode.equals("2")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getEveryday2Element();
								if (ss.getEverydayPeriodTimeElements().get(1) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_2_TIME,
											ss.getEverydayPeriodTimeElements().get(1).getText());
								}
							} else {
								element = ss.getEveryday2Element();
								if (ss.getValueOfEverydayTimeElementAtIndex(2).get(0) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_2_TIME,
											ss.getValueOfEverydayTimeElementAtIndex(2).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_2);
						} else if (mode.equals("3")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getEveryday3Element();
								if (ss.getEverydayPeriodTimeElements().get(2) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_3_TIME,
											ss.getEverydayPeriodTimeElements().get(2).getText());
								}
							} else {
								element = ss.getEveryday3Element();
								if (ss.getValueOfEverydayTimeElementAtIndex(3).get(0) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_3_TIME,
											ss.getValueOfEverydayTimeElementAtIndex(3).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_3);
						} else if (mode.equals("4")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getEveryday4Element();
								if (ss.getEverydayPeriodTimeElements().get(3) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_4_TIME,
											ss.getEverydayPeriodTimeElements().get(3).getText());
								}
							} else {
								element = ss.getEveryday4Element();
								if (ss.getValueOfEverydayTimeElementAtIndex(4).get(0) != null) {
									inputs.setInputValue(InputVariables.EVERYDAY_4_TIME,
											ss.getValueOfEverydayTimeElementAtIndex(4).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_4);
						}
						try {
							String elementDesc = element.getAttribute("name");
							element.click();
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on : " + elementDesc);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time and Set Points : Failed to select Period-"
											+ InputVariables.PERIOD_NUMBER_EMEA);
							return false;
						}
						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting maximum and minimum set points for "
								+ mode + " period ***************");
						flag = flag
								& JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points for " + mode
								+ " period ***************");
					}
				}
				if (ss.IsSaveButtonVisible(10)) {
					flag = flag & ss.clickOnSaveButton();
				}
				if (ss.isConfirmChangeButtonVisible(10)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ss.isConfirmChangeButtonVisible(5)) {
							ss.clickOnConfirmChangeButton();
						}
					} else {
						if (!ss.clickOnConfirmChangeButton()) {
							flag = false;
						}
					}
				}
				if (ss.isSkipButtonVisible(10)) {
					flag = flag & ss.clickOnSkipButton();
				}
				if (ss.isTimeScheduleButtonVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Failed to navigate to Primary Card");
				}

			} else {
				flag = flag & ss.clickOnWeekdayandWeekendScheduleButton();
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equals("FlyCatcher")) {
					String[] modes = { "Wake_Weekday", "Away_Weekday", "Home_Weekday", "Sleep_Weekday", "Wake_Weekend",
							"Away_Weekend", "Home_Weekend", "Sleep_Weekend" };
					for (String mode : modes) {
						HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
						DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
						List<String> allowedModes = statInfo.getAllowedModes();
						periodTimeandSetPoint.put("periodName", mode);
						if (mode.equals("Wake_Weekday")) {
							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME));
							element = ss.getWeekdayWakeElement();
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
							element = ss.getWeekdayAwayElement();
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
							element = ss.getWeekdayHomeElement();
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
							element = ss.getWeekdaySleepElement();
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
							//							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							//							TouchAction action = new TouchAction(testCase.getMobileDriver());
							//							action.press(10, (int) (dimension.getHeight() * .5))
							//							.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();

							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME));
							//							try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendWakeElement();
									}
								} catch (NoSuchElementException e1) {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Create Schedule : Could not find element Wake_Saturday-Sunday");
								}
								//									element = ss.getWeekendWakeElement();
							} else {
								if (!ss.isWeekendWakeElementVisible(5)) {
									try {
										testCase.getMobileDriver().scrollTo("Saturday - Sunday_Wake");
									} catch (Exception e3) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Create Schedule : Could not find element Wake_Saturday-Sunday");
									}
								}
								element = ss.getWeekendWakeElement();
							}
							//							} catch (NoSuchElementException e) {
							//								try {
							//									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							//										Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							//										int startx = (dimensions.width * 20) / 100;
							//										int starty = (dimensions.height * 62) / 100;
							//										int endx = (dimensions.width * 22) / 100;
							//										int endy = (dimensions.height * 35) / 100;
							//										testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
							//										element = ss.getWeekendWakeElement();
							//									}
							//								} catch (NoSuchElementException e1) {
							//									flag = false;
							//									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							//											"Create Schedule : Could not find element Wake_Saturday-Sunday");
							//								}
							//							}

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
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();

							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekendAwayElement();
								} else {
									if (!ss.isWeekendAwayElementVisible(5)) {
										try {
											testCase.getMobileDriver().scrollTo("Saturday - Sunday_Away");
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Away_Saturday-Sunday");
										}
									}
									element = ss.getWeekendAwayElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendAwayElement();
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
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();

							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekendHomeElement();
								} else {
									if (!ss.isWeekendHomeElementVisible(5)) {
										try {
											testCase.getMobileDriver().scrollTo("Saturday - Sunday_Home");
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Home_Saturday-Sunday");
										}
									}
									element = ss.getWeekendHomeElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendHomeElement();
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
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							action.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();

							periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME));
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									element = ss.getWeekendSleepElement();
								} else {
									if (!ss.isWeekendSleepElementVisible(5)) {
										try {
											testCase.getMobileDriver().scrollTo("Saturday - Sunday_Sleep");
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Create Schedule : Could not find element Sleep_Saturday-Sunday");
										}
									}
									element = ss.getWeekendSleepElement();
								}
							} catch (NoSuchElementException e) {
								try {
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										scrollForAndroidScreen(testCase);
										element = ss.getWeekendSleepElement();
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
						try {
							element.click();
							Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully clicked on " + mode);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Create Schedule : Failed to click on " + mode);
						}

						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting maximum and minimum set points for "
								+ mode + " period ***************");
						flag = flag
								& JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points for " + mode
								+ " period ***************");
					}
				}
				// ================================================EMEA===========================================================
				else {
					String[] modes = { "1", "2", "3", "4", "5", "6", "7", "8" };
					for (String mode : modes) {
						if (mode.equals("1")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekday1Element();
								if (ss.getEverydayTimeElements().get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_1_TIME,
											ss.getEverydayTimeElements().get(0).getText());
								}
							} else {
								element = ss.getWeekday1Element();
								if (ss.getValueOfWeekdayTimeElementAtIndex(1).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_1_TIME,
											ss.getValueOfWeekdayTimeElementAtIndex(1).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "1");
						} else if (mode.equals("2")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekday2Element();
								if (ss.getEverydayTimeElements().get(1) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_2_TIME,
											ss.getEverydayTimeElements().get(1).getText());
								}
							} else {
								element = ss.getWeekday2Element();
								if (ss.getValueOfWeekdayTimeElementAtIndex(2).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_2_TIME,
											ss.getValueOfWeekdayTimeElementAtIndex(2).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "2");
						} else if (mode.equals("3")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekday3Element();
								if (ss.getEverydayTimeElements().get(2) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_3_TIME,
											ss.getEverydayTimeElements().get(2).getText());
								}
							} else {
								element = ss.getWeekday3Element();
								if (ss.getValueOfWeekdayTimeElementAtIndex(3).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_3_TIME,
											ss.getValueOfWeekdayTimeElementAtIndex(3).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "3");
						} else if (mode.equals("4")) {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekday4Element();
								if (ss.getEverydayTimeElements().get(3) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_4_TIME,
											ss.getEverydayTimeElements().get(3).getText());
								}
							} else {
								element = ss.getWeekday4Element();
								if (ss.getValueOfWeekdayTimeElementAtIndex(4).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKDAY_4_TIME,
											ss.getValueOfWeekdayTimeElementAtIndex(4).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "4");
						} else if (mode.equals("5")) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend1Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 1_Saturday-Sunday");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekend1Element();
								if (ss.getValueOfWeekendTimeElementAtIndex(1) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_1_TIME,
											ss.getValueOfWeekendTimeElementAtIndex(1).getText());
								}
							} else {
								element = ss.getWeekend1Element();
								if (ss.getValueOfWeekendTimesElementAtIndex(1).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_1_TIME,
											ss.getValueOfWeekendTimesElementAtIndex(1).get(0).getAttribute("value"));
								}
							}

							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "5");
						} else if (mode.equals("6")) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend2Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 2_Saturday - Sunday");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekend2Element();
								if (ss.getValueOfWeekendTimeElementAtIndex(2) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_2_TIME,
											ss.getValueOfWeekendTimeElementAtIndex(2).getText());
								}
							} else {
								element = ss.getWeekend2Element();
								if (ss.getValueOfWeekendTimesElementAtIndex(2).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_2_TIME,
											ss.getValueOfWeekendTimesElementAtIndex(2).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "6");
						} else if (mode.equals("7")) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend3Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 3_Saturday-Sunday");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekend3Element();
								if (ss.getValueOfWeekendTimeElementAtIndex(3) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_3_TIME,
											ss.getValueOfWeekendTimeElementAtIndex(3).getText());
								}
							} else {
								element = ss.getWeekend3Element();
								if (ss.getValueOfWeekendTimesElementAtIndex(3).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_3_TIME,
											ss.getValueOfWeekendTimesElementAtIndex(1).get(3).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "7");
						} else if (mode.equals("8")) {
							try {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									scrollForAndroidScreen(testCase);
									element = ss.getWeekend4Element();
								} else {
									Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
									TouchAction action = new TouchAction(testCase.getMobileDriver());
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.2)).release().perform();
								}
							} catch (NoSuchElementException e1) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule : Could not find element 4_Saturday - Sunday");
							}
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								element = ss.getWeekend4Element();
								if (ss.getValueOfWeekendTimeElementAtIndex(4) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_4_TIME,
											ss.getValueOfWeekendTimeElementAtIndex(4).getText());
								}
							} else {
								element = ss.getWeekend4Element();
								if (ss.getValueOfWeekendTimesElementAtIndex(4).get(0) != null) {
									inputs.setInputValue(InputVariables.WEEKEND_4_TIME,
											ss.getValueOfWeekendTimesElementAtIndex(4).get(0).getAttribute("value"));
								}
							}
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, "8");
						}
						try {
							String elementDesc = element.getAttribute("name");
							element.click();
							Keyword.ReportStep_Pass(testCase, "Successfully clicked on : " + elementDesc);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Period Time and Set Points : Failed to select Period-"
											+ InputVariables.PERIOD_NUMBER_EMEA);
							return false;
						}
						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase, "*************** Setting maximum and minimum set points for "
								+ mode + " period ***************");
						flag = flag
								& JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & ss.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points for " + mode
								+ " period ***************");
					}
				}
				if (ss.IsSaveButtonVisible(10)) {
					flag = flag & ss.clickOnSaveButton();
				}
				if (ss.isConfirmChangeButtonVisible(10)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ss.isConfirmChangeButtonVisible(5)) {
							ss.clickOnConfirmChangeButton();
						}
					} else {
						if (!ss.clickOnConfirmChangeButton()) {
							flag = false;
						}
					}
				}
				if (ss.isSkipButtonVisible(10)) {
					flag = flag & ss.clickOnSkipButton();
				}
				if (ss.isTimeScheduleButtonVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to Primary Card");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Failed to navigate to Primary Card");
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean selectIndividualDaysViewOrGroupedDaysView(TestCases testCase, String viewDays) {
		boolean flag = true;
		SchedulingScreen ss = new SchedulingScreen(testCase);
		try {
			if (!ss.isViewByIndividualDaysVisible(10)) {
				flag = flag & viewScheduleOnPrimaryCard(testCase);
				if (!ss.isViewByIndividualDaysVisible(5)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "App not in schedule view screen");
				}
			}

			if ("Individual Days".equalsIgnoreCase(viewDays)) {
				if (ss.isViewByIndividualDaysVisible(5)) {
					if (!ss.clickOnViewByIndividualDays()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Individual days");
					}
				}
			} else {
				if (ss.isViewByGroupedDaysVisible(5)) {
					if (!ss.clickOnViewByGroupedDays()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected View by Grouped days");
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}

		return flag;
	}
	public static boolean addOrDeleteSleepSettings(TestCases testCase, TestCaseInputs inputs,
			boolean addSleepSettings) {
		boolean flag = true;
		flag = flag & viewScheduleOnPrimaryCard(testCase);
		SchedulingScreen schl = new SchedulingScreen(testCase);
		inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
		if (addSleepSettings) {
			inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
			if (schl.isCreateSleepSettingsVisible(5)) {
				if (!schl.clickOnCreateSleepSettings()) {
					flag = false;
				} else {
					if (schl.IsSaveButtonVisible(5)) {
						if (!schl.clickOnSaveButton()) {
							flag = false;
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate SAVE button");
					}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to locate Create Sleep Settings button");
			}
		} else {
			inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "No");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("xpath", "//*[@text='Use My Sleep Settings']", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "xpath", "//*[@text='Use My Sleep Settings']")) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Use My Sleep Settings option not displayed on schedule screen");
				}
			} else {
				if (MobileUtils.isMobElementExists("name", "Geofence_Sleep", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "Geofence_Sleep")) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Use My Sleep Settings option not displayed on schedule screen");
				}
			}
			if (schl.isPeriodDeleteIconVisible(5)) {
				if (!schl.clickOnPeriodDeleteIcon()) {
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
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Delete icon");
			}
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (schl.isBackButtonVisible(5)) {
				if (!schl.clickOnBackButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
			}
		} else {
			if (MobileUtils.isMobElementExists("name", "btn close normal", testCase, 5)) {
				if (!MobileUtils.clickOnElement(testCase, "name", "btn close normal")) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
			}
		}

		return flag;
	}

	public static boolean setGeofenceSleepSettings(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			SchedulingScreen schl = new SchedulingScreen(testCase);
			HashMap<String, String> targetSetPoints = new HashMap<String, String>();
			if(schl.isTimeScheduleButtonVisible(10)){
				flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
			}

			if(schl.isGeofenceSleepElementVisible(5)) {
				Keyword.ReportStep_Pass(testCase, "Use My Sleep Settings option displayed on schedule screen");
				if (!schl.clickOnGeofenceSleepButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Use My Sleep Settings option not displayed on schedule screen");
			}
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting time and set points for Sleep period ***************");
			flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME),
					"GeofenceSleepStartTime", true, true);
			flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME),
					"GeofenceSleepEndTime", true, true);
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
						+ targetSetPoints.get("targetHeatTemp"));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
				Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
						+ targetSetPoints.get("targetCoolTemp"));
			}
			flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Sleep", targetSetPoints);
			if (schl.IsSaveButtonVisible(5)) {
				if (!schl.clickOnSaveButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate SAVE button");
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (schl.isBackButtonVisible(5)) {
					if (!schl.clickOnBackButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
			} else {
				if (MobileUtils.isMobElementExists("name", "btn close normal", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "btn close normal")) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public static boolean clickOnDeleteIconForSelectedPeriodNA(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		String periodToSelect = null;
		Random rn = new Random();
		SchedulingScreen schl = new SchedulingScreen(testCase);
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")
				|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA") || statInfo.getJasperDeviceType().equalsIgnoreCase("FLYCATCHER")) {
			String[] schedulePeriods = { "Wake", "Away", "Home", "Sleep" };
			periodToSelect = schedulePeriods[rn.nextInt((3 - 0) + 1) + 0];
		} else if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
			String[] schedulePeriods = { "1", "2", "3", "4" };
			periodToSelect = schedulePeriods[rn.nextInt((3 - 0) + 1) + 0];
		}
		List<WebElement> scheduleDayHeaders = null;
		int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//*[@text='" + periodToSelect + "']", testCase, 5)) {
				if (!MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='" + periodToSelect + "']")) {
					flag = false;
				} else {
					Keyword.ReportStep_Pass(testCase, "Selected period-" + periodToSelect);
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to locate the period-" + periodToSelect);
			}
		} else {
			String[] scheduleDay = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			String DayToSelect = scheduleDay[rn.nextInt((6 - 0) + 1) + 0];
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
				desiredDayIndex = Arrays.asList(scheduleDay).indexOf(DayToSelect);
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
					scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
					lesserDayIndex = Arrays.asList(scheduleDay)
							.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
					greaterDayIndex = Arrays.asList(scheduleDay)
							.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
				}
				int i = 0;
				while ((!MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + DayToSelect + "_" + periodToSelect
						+ "']",
						testCase, 5)) && i < 10) {
					if (desiredDayIndex > greaterDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					} else if (desiredDayIndex < lesserDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
						i++;
					} else {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					}
				}
				WebElement period = testCase.getMobileDriver().findElement(By.name(DayToSelect + "_" + periodToSelect));
				if (period == null) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate the period-" + DayToSelect + "_" + periodToSelect);
				} else {
					period.click();
					Keyword.ReportStep_Pass(testCase, "Selected period-" + DayToSelect + "_" + periodToSelect);
				}
			} else {
				if (MobileUtils.isMobElementExists("name", "Everyday_" + periodToSelect, testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "Everyday_" + periodToSelect)) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected period-" + periodToSelect);
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Failed to locate the period-" + periodToSelect);
				}
			}
		}
		if (schl.isPeriodDeleteIconVisible(5)) {
			if (!schl.clickOnPeriodDeleteIcon()) {
				flag = false;
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate the Period delete icon");
		}

		return flag;
	}

	public static boolean createGeofenceBasedScheduleWithDefaultValues(TestCases testCase, TestCaseInputs inputs,
			boolean createScheduleUsingUseGeofenceButton) {
		boolean flag = true;
		try {
			SchedulingScreen schl = new SchedulingScreen(testCase);
			if (schl.isCreateScheduleButtonVisible(10)) {
				flag = flag & schl.clickOnCreateScheduleButton();
			} else {
				if (schl.isScheduleOffOverlayVisible(10)) {
					if (!schl.clickOnScheduleOffOverlay()) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Existing schedule is resumed");
					}
				}
				flag = flag & schl.clickOnScheduleOptionsButton();
				if (schl.isSwitchToGeofenceButtonVisible(10)) {
					flag = flag & schl.clickOnSwitchToGeofenceButton();
				}
			}

			if (createScheduleUsingUseGeofenceButton) {
				flag = flag && schl.clickOnUseGeofencingText();
			} else {
				flag = flag && schl.clickOnLearnMoreButton();
			}
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Verifying set points for Home period ***************");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (schl.isHomeTemperatureHeaderMultiTemperatureVisible(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				} else {
					/*
					 * if (MobileUtils.isMobElementExists("name",
					 * "What temperatures do you prefer when you're at home" +
					 * "\u003F", testCase, 5)) { Keyword.ReportStep_Pass(testCase,
					 * "Create Schedule : Successfully navigated to home set points page"
					 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "Create Schedule : Failed to navigate to home set points page"
					 * ); }
					 */
				}
				flag = flag & verifyCoolStepperValue(testCase, inputs,
						inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT), "");
				flag = flag & verifyHeatStepperValue(testCase, inputs,
						inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT), "");

			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				if(inputs.getInputValue("NaviagateBackAtAway").equalsIgnoreCase("true")){

				}
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (schl.isHomeTemperatureHeaderSingleTemperatureVisible(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to home set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to home set points page");
					}
				} else {
					/*
					 * if (MobileUtils.isMobElementExists("name",
					 * "What temperature do you prefer when you're at home" +
					 * "\u003F", testCase, 5)) { Keyword.ReportStep_Pass(testCase,
					 * "Create Schedule : Successfully navigated to home set points page"
					 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "Create Schedule : Failed to navigate to home set points page"
					 * ); }
					 */
				}
				flag = flag & verifyHeatStepperValue(testCase, inputs,
						inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT), "");
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (schl.isHomeTemperatureHeaderSingleTemperatureVisible(10)) {
					Keyword.ReportStep_Pass(testCase, "Create Schedule : Successfully navigated to home set points page");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Create Schedule : Failed to navigate to home set points page");
				}
				flag = flag & verifyCoolStepperValue(testCase, inputs,
						inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT), "");
			}
			flag = flag & schl.clickOnNextButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed verifying set points for Home period ***************");
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase, "*************** Verifying set points for Away period ***************");
			String coolSetPoint = "";
			String heatSetPoint = "";
			if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (schl.isAwayTemperatureHeaderMultiTemperatureVisble(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				}
				coolSetPoint = schl.getCoolSetPointChooserSetPointsValue();
				flag = flag & verifyCoolStepperValue(testCase, inputs, coolSetPoint,
						inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
				heatSetPoint = schl.getHeatSetPointChooserSetPointsValue();
				flag = flag & verifyHeatStepperValue(testCase, inputs, heatSetPoint,
						inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
			} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (schl.isAwayTemperatureHeaderSingleTemperatureVisble(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				} else {
					/*
					 * if (MobileUtils.isMobElementExists("name",
					 * "What temperatures do you prefer when you're away from home"
					 * + "\u003F", testCase, 5)) { Keyword.ReportStep_Pass(testCase,
					 * "Create Schedule : Successfully navigated to away set points page"
					 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "Create Schedule : Failed to navigate to away set points page"
					 * ); }
					 */
				}
				heatSetPoint = schl.getHeatSetPointChooserSetPointsValue();
				flag = flag & verifyHeatStepperValue(testCase, inputs, heatSetPoint,
						inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
			} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
				if (testCase.getPlatform().contains("ANDROID")) {
					if (schl.isAwayTemperatureHeaderSingleTemperatureVisble(10)) {
						Keyword.ReportStep_Pass(testCase,
								"Create Schedule : Successfully navigated to away set points page");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create Schedule : Failed to navigate to away set points page");
					}
				} else {
					/*
					 * if (MobileUtils.isMobElementExists("name",
					 * "What temperature do you prefer when you're away from home" +
					 * "\u003F", testCase, 5)) { Keyword.ReportStep_Pass(testCase,
					 * "Create Schedule : Successfully navigated to away set points page"
					 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
					 * FailType.FUNCTIONAL_FAILURE,
					 * "Create Schedule : Failed to navigate to away set points page"
					 * ); }
					 */
				}
				coolSetPoint = schl.getCoolSetPointChooserSetPointsValue();
				flag = flag & verifyCoolStepperValue(testCase, inputs, coolSetPoint,
						inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
			}
			flag = flag & schl.clickOnNextButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed verifying set points for Away period ***************");
			if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
				flag = flag & schl.clickOnSkipButton();

			} else {
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Verifying time and set points for Sleep period ***************");
				if (testCase.getPlatform().toUpperCase().contains("IOS")) {
					//flag = flag & schl.clickOnYesButton();
				}
				flag = flag & verifySetPeriodTime(testCase,
						inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME), "GeofenceSleepStartTime");
				flag = flag & verifySetPeriodTime(testCase,
						inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME), "GeofenceSleepEndTime");
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					flag = flag & verifyCoolStepperValue(testCase, inputs,
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT), "");
					flag = flag & verifyHeatStepperValue(testCase, inputs,
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT), "");
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					flag = flag & verifyHeatStepperValue(testCase, inputs,
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT), "");
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					flag = flag & verifyCoolStepperValue(testCase, inputs,
							inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT), "");
				}
				flag = flag & schl.clickOnNextButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed verifying time and set points for Sleep period ***************");
			}
			// flag = flag & JasperUtils.verifyCreatedSchedule(testCase, inputs,
			// "Geofence");
			if (schl.IsSaveButtonVisible(10)) {
				flag = flag &  schl.clickOnSaveButton();
			}
			if (schl.isSkipButtonVisible(10)) {
				flag = flag & schl.clickOnSkipButton();
			}
		} catch (Exception e) {

		}
		return flag;
	}

}
