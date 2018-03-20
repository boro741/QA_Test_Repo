package com.honeywell.jasper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;


import io.appium.java_client.TouchAction;

public class JasperSchedulingVerifyUtils {

	public static boolean verifyTemperatureWithInRange(TestCases testCase, TestCaseInputs inputs) {

		boolean flag = true;
		try {
			List<WebElement> schedule_heatsetpoints, schedule_coolsetpoints, schedule_period_time = null;
			WebElement heatSetPoint, coolSetPoint;
			String[] schedulePeriods = { "Wake", "Away", "Home", "Sleep" };
			SchedulingScreen ss = new SchedulingScreen(testCase);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();

			flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);

			if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
				Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
				Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
				Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
							for (WebElement setpoints : schedule_heatsetpoints) {
								if (setpoints != null) {
									if (Double.parseDouble(setpoints.getText()) <= maxHeat
											&& Double.parseDouble(setpoints.getText()) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: " + Double.parseDouble(setpoints.getText())
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(setpoints.getText())
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
						if (ss.isSchedulePeriodCoolSetPointVisible(5)) {
							schedule_coolsetpoints = ss.getSchedulePeriodCoolSetPointElement();
							for (WebElement setpoints : schedule_coolsetpoints) {
								if (setpoints != null) {
									if (Double.parseDouble(setpoints.getText()) <= maxCool
											&& Double.parseDouble(setpoints.getText()) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: " + Double.parseDouble(setpoints.getText())
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(setpoints.getText())
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (ss.isGeofenceHomeCoolElementVisible(5)) {
							coolSetPoint = ss.getGeofenceHomeCoolElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceHomeHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceHomeHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceSleepCoolElementVisible(5)) {
							coolSetPoint = ss.getGeofenceSleepCoolElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceSleepHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceSleepHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceAwayCoolElementVisible(5)) {
							coolSetPoint = ss.getGeofenceAwayCoolElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceAwayHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceAwayHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
					}
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
							.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
								for (WebElement setpoints : schedule_heatsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxHeat
												&& Double.parseDouble(setpoints.getText()) >= minHeat) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
							if (ss.isSchedulePeriodCoolSetPointVisible(5)) {
								schedule_coolsetpoints = ss.getSchedulePeriodCoolSetPointElement();
								for (WebElement setpoints : schedule_coolsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxCool
												&& Double.parseDouble(setpoints.getText()) >= minCool) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (ss.isEverydaySchedulePeriodTimeVisible(6)) {
								schedule_period_time = ss.getEverydaySchedulePeriodTimesElements();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Everyday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double.parseDouble(
										ss.getCoolSetPointsOfGivenEverydayPeriod(schedulePeriods[i])) <= maxCool
										&& Double.parseDouble(ss.getCoolSetPointsOfGivenEverydayPeriod(
												schedulePeriods[i])) >= minCool) {
									Keyword.ReportStep_Pass(testCase, "Set Point value: "
											+ Double.parseDouble(
													ss.getCoolSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[TemperatureInMaxMinRange] Set Point value: "
													+ Double.parseDouble(ss
															.getCoolSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
													+ " is not set within or at the maximum and minimum range");
								}
								if (Double.parseDouble(
										ss.getHeatSetPointsOfGivenEverydayPeriod(schedulePeriods[i])) <= maxHeat
										&& Double.parseDouble(ss.getHeatSetPointsOfGivenEverydayPeriod(
												schedulePeriods[i])) >= minHeat) {
									Keyword.ReportStep_Pass(testCase, "Set Point value: "
											+ Double.parseDouble(
													ss.getHeatSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[TemperatureInMaxMinRange] Set Point value: "
													+ Double.parseDouble(ss
															.getHeatSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
													+ " is not set within or at the maximum and minimum range");
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
							schedule_coolsetpoints = ss.getSchedulePeriodCoolSetPointElement();
							if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
								for (WebElement setpoints : schedule_heatsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxHeat
												&& Double.parseDouble(setpoints.getText()) >= minHeat) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
							if (ss.isSchedulePeriodCoolSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodCoolSetPointElement();
								for (WebElement setpoints : schedule_coolsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxCool
												&& Double.parseDouble(setpoints.getText()) >= minCool) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (ss.isWeekdayPeriodTimeVisible(6)) {
								schedule_period_time = ss.getWeekdayPeriodTimeElements();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double
										.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
												schedulePeriods[i])) <= maxHeat
										&& Double.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
												schedulePeriods[i])) >= minHeat) {
									Keyword.ReportStep_Pass(testCase, "[Monday - Friday] Heat Set Point value: "
											+ Double.parseDouble(ss
													.getValueOfWeekdayHeatTemperatureElementAtIndex(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Monday - Friday] Heat Set Point value: "
													+ Double.parseDouble(
															ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
																	schedulePeriods[i]))

													+ " is not set within or at the maximum and minimum range");
								}
								if (Double
										.parseDouble(ss.getValueOfWeekdayCoolTemperatureElementAtIndex(
												schedulePeriods[i])) <= maxHeat
										&& Double.parseDouble(ss.getValueOfWeekdayCoolTemperatureElementAtIndex(
												schedulePeriods[i])) >= minHeat) {
									Keyword.ReportStep_Pass(testCase, "[Monday - Friday]Cool Set Point value: "
											+ Double.parseDouble(ss
													.getValueOfWeekdayCoolTemperatureElementAtIndex(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Monday - Friday]Cool Set Point value: "
													+ Double.parseDouble(
															ss.getValueOfWeekdayCoolTemperatureElementAtIndex(
																	schedulePeriods[i]))
													+ " is not set within or at the maximum and minimum range");
								}
							}
							if (ss.isWeekendScheduleTitleAndPeriodTimeVisble(6)) {
								schedule_period_time = ss.getWeekendScheduleTitleAndPeriodTimeElements();
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double
										.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
												schedulePeriods[i])) <= maxHeat
										&& Double.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
												schedulePeriods[i])) >= minHeat) {
									Keyword.ReportStep_Pass(testCase, "[Saturday - Sunday]Heat Set Point value: "
											+ Double.parseDouble(ss
													.getValueOfWeekendHeatTemperatureElementAtIndex(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Saturday - Sunday]Heat Set Point value: "
													+ Double.parseDouble(
															ss.getValueOfWeekendHeatTemperatureElementAtIndex(
																	schedulePeriods[i]))
													+ " is not set within or at the maximum and minimum range");
								}
								if (Double
										.parseDouble(ss.getValueOfWeekendCoolTemperatureElementAtIndex(
												schedulePeriods[i])) <= maxHeat
										&& Double.parseDouble(ss.getValueOfWeekendCoolTemperatureElementAtIndex(
												schedulePeriods[i])) >= minHeat) {
									Keyword.ReportStep_Pass(testCase, "[Saturday - Sunday]Cool Set Point value: "
											+ Double.parseDouble(ss
													.getValueOfWeekendCoolTemperatureElementAtIndex(schedulePeriods[i]))
											+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Saturday - Sunday]Cool Set Point value: "
													+ Double.parseDouble(
															ss.getValueOfWeekendCoolTemperatureElementAtIndex(
																	schedulePeriods[i]))
													+ " is not set within or at the maximum and minimum range");
								}
							}
						}
					}
				}
			} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
				Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
				Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ss.isSchedulePeriodCoolSetPointVisible(5)) {
							schedule_coolsetpoints = ss.getSchedulePeriodCoolSetPointElement();
							for (WebElement setpoints : schedule_coolsetpoints) {
								if (setpoints != null) {
									if (Double.parseDouble(setpoints.getText()) <= maxCool
											&& Double.parseDouble(setpoints.getText()) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: " + Double.parseDouble(setpoints.getText())
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(setpoints.getText())
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (ss.isGeofenceCoolHeatTemperatureVisible(5)) {
							coolSetPoint = ss.getGeofenceCoolHeatTemperatureElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceSleepCoolElementVisible(5)) {
							coolSetPoint = ss.getGeofenceSleepCoolElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceAwayCoolElementVisible(5)) {
							coolSetPoint = ss.getGeofenceAwayCoolElement();
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
					}
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
							.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isSchedulePeriodCoolSetPointVisible(5)) {
								schedule_coolsetpoints = ss.getSchedulePeriodCoolSetPointElement();
								for (WebElement setpoints : schedule_coolsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxCool
												&& Double.parseDouble(setpoints.getText()) >= minCool) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
								if (ss.isEverydayScheduleTitleAndPeriodTimeVisble(5)) {
									schedule_period_time = ss.getEverydayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getCoolSetPointsOfGivenEverydayPeriod(String.valueOf(i + 1))) <= maxCool
											&& Double.parseDouble(ss.getCoolSetPointsOfGivenEverydayPeriod(
													String.valueOf(i + 1))) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getCoolSetPointsOfGivenEverydayPeriod(String.valueOf(i + 1)))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getCoolSetPointsOfGivenEverydayPeriod(
																String.valueOf(i + 1)))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (ss.isEverydayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getEverydayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getCoolSetPointsOfGivenEverydayPeriod(schedulePeriods[i])) <= maxCool
											&& Double.parseDouble(ss.getCoolSetPointsOfGivenEverydayPeriod(
													schedulePeriods[i])) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getCoolSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getCoolSetPointsOfGivenEverydayPeriod(
																schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
							if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
								for (WebElement setpoints : schedule_heatsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxCool
												&& Double.parseDouble(setpoints.getText()) >= minCool) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(5)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenWeekdayPeriod(String.valueOf(i + 1))) <= maxCool
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													String.valueOf(i + 1))) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenWeekdayPeriod(String.valueOf(i + 1)))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenWeekdayPeriod(
																String.valueOf(i + 1)))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenWeekendPeriod(String.valueOf(i + 1))) <= maxCool
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenWeekendPeriod(
													String.valueOf(i + 1))) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenWeekendPeriod(String.valueOf(i + 1)))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenWeekendPeriod(
																String.valueOf(i + 1)))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenWeekdayPeriod(schedulePeriods[i])) <= maxCool
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenWeekdayPeriod(
													schedulePeriods[i])) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenWeekdayPeriod(schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenWeekdayPeriod(
																schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (ss.isWeekendScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekendScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenWeekendPeriod(schedulePeriods[i])) <= maxCool
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenWeekendPeriod(
													schedulePeriods[i])) >= minCool) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenWeekendPeriod(schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenWeekendPeriod(
																schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					}
				}
			} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
				Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
							for (WebElement setpoints : schedule_heatsetpoints) {
								if (setpoints != null) {
									if (Double.parseDouble(setpoints.getText()) <= maxHeat
											&& Double.parseDouble(setpoints.getText()) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: " + Double.parseDouble(setpoints.getText())
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(setpoints.getText())
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (ss.isGeofenceHomeHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceHomeHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceSleepHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceSleepHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (ss.isGeofenceAwayHeatElementVisible(5)) {
							heatSetPoint = ss.getGeofenceAwayHeatElement();
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
					}
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
							.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
								for (WebElement setpoints : schedule_heatsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxHeat
												&& Double.parseDouble(setpoints.getText()) >= minHeat) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
								if (ss.isEverydayScheduleTitleAndPeriodTimeVisble(5)) {
									schedule_period_time = ss.getEverydayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenEverydayPeriod(String.valueOf(i + 1))) <= maxHeat
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenEverydayPeriod(
													String.valueOf(i + 1))) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenEverydayPeriod(String.valueOf(i + 1)))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenEverydayPeriod(
																String.valueOf(i + 1)))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (ss.isEverydayScheduleTitleAndPeriodTimeVisble(5)) {
									schedule_period_time = ss.getEverydayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getHeatSetPointsOfGivenEverydayPeriod(schedulePeriods[i])) <= maxHeat
											&& Double.parseDouble(ss.getHeatSetPointsOfGivenEverydayPeriod(
													schedulePeriods[i])) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getHeatSetPointsOfGivenEverydayPeriod(schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss.getHeatSetPointsOfGivenEverydayPeriod(
																schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
							if (ss.isSchedulePeriodHeatSetPointVisible(5)) {
								schedule_heatsetpoints = ss.getSchedulePeriodHeatSetPointElement();
								for (WebElement setpoints : schedule_heatsetpoints) {
									if (setpoints != null) {
										if (Double.parseDouble(setpoints.getText()) <= maxHeat
												&& Double.parseDouble(setpoints.getText()) >= minHeat) {
											Keyword.ReportStep_Pass(testCase,
													"Set Point value: " + Double.parseDouble(setpoints.getText())
															+ " is set within or at the maximum and minimum range");
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"[TemperatureInMaxMinRange] Set Point value: "
															+ Double.parseDouble(setpoints.getText())
															+ " is not set within or at the maximum and minimum range");
										}
									}
								}
							}
						} else {
							if (statInfo.getJasperDeviceType().equalsIgnoreCase("EMEA")) {
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
													String.valueOf(i + 1))) <= maxHeat
											&& Double.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
													String.valueOf(i + 1))) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
														String.valueOf(i + 1)))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
																		String.valueOf(i + 1)))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (ss.isWeekendScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekendScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double.parseDouble(
											ss.getValueOfWeekendHeatTemperatureElementAtIndex(i + 1)) <= maxHeat
											&& Double.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
													i + 1)) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(
														ss.getValueOfWeekendHeatTemperatureElementAtIndex(i + 1))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(ss
																.getValueOfWeekendHeatTemperatureElementAtIndex(i + 1))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
													schedulePeriods[i])) <= maxHeat
											&& Double.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
													schedulePeriods[i])) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
														schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																ss.getValueOfWeekdayHeatTemperatureElementAtIndex(
																		schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (ss.isWeekdayScheduleTitleAndPeriodTimeVisble(6)) {
									schedule_period_time = ss.getWeekdayScheduleTitleAndPeriodTimeElements();
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
													schedulePeriods[i])) <= maxHeat
											&& Double.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
													schedulePeriods[i])) >= minHeat) {
										Keyword.ReportStep_Pass(testCase, "Set Point value: "
												+ Double.parseDouble(ss.getValueOfWeekendHeatTemperatureElementAtIndex(
														schedulePeriods[i]))
												+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																ss.getValueOfWeekendHeatTemperatureElementAtIndex(
																		schedulePeriods[i]))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					}
				}
			}

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
		}
		return flag;
	}

	public static boolean verifyTimeFieldIncrements(TestCases testCase, TestCaseInputs inputs, String timeInterval) {
		boolean flag = true;
		int i = 0;
		String[] scheduleDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		List<WebElement> scheduleDayHeaders = null;
		int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
		SchedulingScreen ss = new SchedulingScreen(testCase);
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String jasperStatType = statInfo.getJasperDeviceType();

		if (timeInterval.equalsIgnoreCase("10")) {
			i = 10;
		} else if (timeInterval.equalsIgnoreCase("15")) {
			i = 15;
		}

		if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
				.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
			String geofenceStartTime = "", geofenceEndTime = "";
			Double temp;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ss.isSchedulingPeriodSleepStartTimeVisible(5)) {
					if (ss.getSchedulingPeriodSleepStartTimeText().contains("M")
							|| ss.getSchedulingPeriodSleepStartTimeText().contains("m")) {
						geofenceStartTime = ss.getSchedulingPeriodSleepStartTimeText().split("\\s+")[0];
					} else {
						geofenceStartTime = ss.getSchedulingPeriodSleepStartTimeText().split("\\s+")[0];
					}
					if (ss.getSchedulingPeriodSleepStartTimeText().contains("M")
							|| ss.getSchedulingPeriodSleepStartTimeText().contains("m")) {
						geofenceEndTime = ss.getSchedulingPeriodSleepStartTimeText().split("\\s+")[3];
	} else {
						geofenceEndTime = ss.getSchedulingPeriodSleepStartTimeText().split("\\s+")[2];
					}
				}
			} else {
				if (ss.isGeofenceSleepSubTitleVisible(5)) {
					if (ss.getGeofenceSleepSubTitleText().contains("M")
							|| ss.getGeofenceSleepSubTitleText().contains("m")) {
						geofenceStartTime = ss.getGeofenceSleepSubTitleText().split("\\s+")[0];
					} else {
						geofenceStartTime = ss.getGeofenceSleepSubTitleText().split("\\s+")[0];
					}
					if (ss.getGeofenceSleepSubTitleText().contains("M")
							|| ss.getGeofenceSleepSubTitleText().contains("m")) {
						geofenceEndTime = ss.getGeofenceSleepSubTitleText().split("\\s+")[3];
					} else {
						geofenceEndTime = ss.getGeofenceSleepSubTitleText().split("\\s+")[2];
					}
				}
			}
			temp = Double.parseDouble(geofenceStartTime.split(":")[1]);
			if (temp.intValue() % i == 0) {
				Keyword.ReportStep_Pass(testCase, "Start time is set in intervals of " + i + " minutes");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Start time is not set in intervals of " + i + " minutes");
			}
			temp = Double.parseDouble(geofenceEndTime.split(":")[1]);
			if (temp.intValue() % i == 0) {
				Keyword.ReportStep_Pass(testCase, "End time is set in intervals of " + i + " minutes");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"End time is not set in intervals of " + i + " minutes");
			}

		} else {
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				String everydayStartTime = "", everydayEndTime = "";
				Double temp;
				List<WebElement> everydayPeriodTime = null;

				if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE) != null
						&& !inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).isEmpty()) {
					if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
						flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase,
								"Grouped Days");
					} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE)
							.equalsIgnoreCase("Individual Days")) {
						flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase,
								"Individual Days");
					}

					WebElement period = null;
					CustomDriver driver = testCase.getMobileDriver();
					Dimension dimension = driver.manage().window().getSize();
					int height = dimension.getHeight();
					int width = dimension.getWidth();
					TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (!MobileUtils.isMobElementExists(
								"XPATH", "//*[@content-desc='"
										+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']",
								testCase, 5)) {
							testCase.getMobileDriver().scrollToExact(
									inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[1]);
							while (!MobileUtils.isMobElementExists(
									"XPATH", "//*[@content-desc='"
											+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']",
									testCase, 5)) {
								touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000))
										.moveTo(width / 2, 82).release();

								touchAction.perform();
							}
						}
						period = testCase.getMobileDriver().findElement(By.xpath("//*[@content-desc='"
								+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']"));
						period.findElement(By.id("scheduling_period_time")).click();

						if (ss.isTimeChooserHeaderVisible(5)) {
							everydayStartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
									.getText().split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (ss.isTimeChooserEndTimeVisible(5)) {
								everydayEndTime = MobileUtils
										.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getText()
										.split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(everydayStartTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
											+ "]Start time: " + everydayStartTime + " is set in intervals of " + i
											+ " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
											+ "]Start time: " + everydayStartTime + " is not set in intervals of " + i
											+ " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(everydayEndTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
												+ "]End time: " + everydayEndTime + " is set in intervals of " + i
												+ " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
												+ "]End time: " + everydayEndTime + " is not set in intervals of " + i
												+ " minutes");
							}
						}
					} else {
						desiredDayIndex = Arrays.asList(scheduleDays)
								.indexOf(inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[0]);
						if (ss.isScheduleDayHeaderVisible(5)) {
							scheduleDayHeaders = ss.getScheduleDayHeaderElements();
							lesserDayIndex = Arrays.asList(scheduleDays)
									.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
							greaterDayIndex = Arrays.asList(scheduleDays).indexOf(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
						}
						int m = 0;
						while ((!MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
										+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']",
										testCase, 5)) && m < 10) {
							if (desiredDayIndex > greaterDayIndex) {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
								m++;
							} else if (desiredDayIndex < lesserDayIndex) {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
								m++;
							} else {
								touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
								m++;
							}
						}
						period = testCase.getMobileDriver()
								.findElement(By.name(inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)));

						if (period != null) {
							try {
								period.click();
								Keyword.ReportStep_Pass(testCase, "Selected the period: "
										+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED));
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to select the period: "
												+ inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED));

							}
						}

						if (ss.isTimeChooserHeaderVisible(5)) {
							everydayStartTime = ss.getTimeChooserValue().split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (ss.isTimeChooserEndTimeVisible(5)) {
								everydayEndTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(everydayStartTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
											+ "]Start time: " + everydayStartTime + " is set in intervals of " + i
											+ " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
											+ "]Start time: " + everydayStartTime + " is not set in intervals of " + i
											+ " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(everydayEndTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
												+ "]End time: " + everydayEndTime + " is set in intervals of " + i
												+ " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED)
												+ "]End time: " + everydayEndTime + " is not set in intervals of " + i
												+ " minutes");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Navigation_Left_Bar_Item", testCase, 5)) {
							if (!MobileUtils.clickOnElement(testCase, "name", "Navigation_Left_Bar_Item")) {
								flag = false;
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the CANCEL button in Edit Period screen");
						}
					}
				} else {
					if (ss.isEverydayTimeVisible(5)) {
						everydayPeriodTime = ss.getEverydayTimeElements();
					}
					for (int e = 0; e < everydayPeriodTime.size(); e++) {
						if (everydayPeriodTime.get(e) != null) {
							everydayPeriodTime.get(e).click();
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isTimeChooserHeaderVisible(5)) {
								everydayStartTime = ss.getTimeChooserValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Start time");
							}
							if (jasperStatType.equalsIgnoreCase("EMEA")) {
								if (ss.isTimeChooserEndTimeVisible(5)) {
									everydayEndTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate the End time");
								}
							}
						} else {
							if (ss.isTimeChooserHeaderVisible(5)) {
								everydayStartTime = ss.getTimeChooserValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Start time");
							}
							if (jasperStatType.equalsIgnoreCase("EMEA")) {
								if (ss.isTimeChooserEndTimeVisible(5)) {
									everydayEndTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate the End time");
								}
							}
						}
						temp = Double.parseDouble(everydayStartTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]Start time: " + everydayStartTime
									+ " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Period-" + (e + 1) + "]Start time: " + everydayStartTime
									+ " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(everydayEndTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]End time: " + everydayEndTime
										+ " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Period-" + (e + 1) + "]End time: " + everydayEndTime
										+ " is not set in intervals of " + i + " minutes");
							}
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isBackButtonVisible(5)) {
								if (!ss.clickOnBackButton()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
							if (ss.isNavigationLeftBarItemVisible(5)) {
								if (!ss.clickOnNavigationLeftBarItem()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the CANCEL button in Edit Period screen");
							}
						}
					}
				}
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
					.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				String startTime = "", endTime = "";
				Double temp;
				List<WebElement> periodTime = null;
				// =================================================ANDROID====================================================================
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (ss.isSchedulePeriodTimeVisible(5)) {
						periodTime = ss.getSchedulePeriodTimeElements();
					}
					for (int e = 0; e < periodTime.size(); e++) {
						if (e == 4) {
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							dimensions = testCase.getMobileDriver().manage().window().getSize();
							int startx = (dimensions.width * 20) / 100;
							int starty = (dimensions.height * 20) / 100;
							int endx = (dimensions.width * 22) / 100;
							int endy = (dimensions.height * 35) / 100;
							testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						}

						if (periodTime.get(e) != null) {
							periodTime.get(e).click();
						}
						if (ss.isTimeChooserHeaderVisible(5)) {
							startTime = ss.getTimeChooserValue()
									.split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (ss.isTimeChooserEndTimeVisible(5)) {
								endTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(startTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]Start time: " + startTime
									+ " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
									+ "]Start time: " + startTime + " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(endTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]End time: " + endTime
										+ " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
										+ "]End time: " + endTime + " is not set in intervals of " + i + " minutes");
							}
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isBackButtonVisible(5)) {
								if (!ss.clickOnBackButton()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
							if (ss.isNavigationLeftBarItemVisible(5)) {
								if (!ss.clickOnNavigationLeftBarItem()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the CANCEL button in Edit Period screen");
							}
						}
					}
				}
				// =======================================================IOS===========================================================
				else {
					// ========================================Weekday=====================================================
					if (ss.isWeekdayPeriodTimeVisible(5)) {
						periodTime = ss.getWeekdayPeriodTimeElements();
					}
					for (int e = 0; e < periodTime.size(); e++) {
						if (periodTime.get(e) != null) {
							periodTime.get(e).click();
						}
						if (ss.isTimeChooserHeaderVisible(5)) {
							startTime = ss.getTimeChooserValue().split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (ss.isTimeChooserEndTimeVisible(5)) {
								endTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(startTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]Start time: " + startTime
									+ " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
									+ "]Start time: " + startTime + " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(endTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]End time: " + endTime
										+ " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
										+ "]End time: " + endTime + " is not set in intervals of " + i + " minutes");
							}
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isBackButtonVisible(5)) {
								if (!ss.clickOnBackButton()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
							if (ss.isNavigationLeftBarItemVisible(5)) {
								if (!ss.clickOnNavigationLeftBarItem()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the CANCEL button in Edit Period screen");
							}
						}
					}
					// ================================================Weekend====================================================
					if (ss.isWeekendPeriodTimeVisible(5)) {
						periodTime = ss.getWeekendPeriodTimeElements();
					}
					for (int e = 0; e < periodTime.size(); e++) {
						if (periodTime.get(e) != null) {
							periodTime.get(e).click();
						}
						if (ss.isTimeChooserHeaderVisible(5)) {
							startTime = ss.getTimeChooserValue().split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (ss.isTimeChooserEndTimeVisible(5)) {
								endTime = ss.getTimeChooserEndTimeValue().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(startTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]Start time: " + startTime
									+ " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
									+ "]Start time: " + startTime + " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(endTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase, "[Period-" + (e + 1) + "]End time: " + endTime
										+ " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "[Period-" + (e + 1)
										+ "]End time: " + endTime + " is not set in intervals of " + i + " minutes");
							}
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (ss.isBackButtonVisible(5)) {
								if (!ss.clickOnBackButton()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
							if (ss.isNavigationLeftBarItemVisible(5)) {
								if (!ss.clickOnNavigationLeftBarItem()) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the CANCEL button in Edit Period screen");
							}
						}
					}
				}
			}
		}

		return flag;
	}

	public static boolean verifyAppRedirectedToScheduleScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (ss.isEverydayScheduleButtonVisible(5)) {
			Keyword.ReportStep_Pass(testCase, "App is successfuly redirected to schedule selection screen");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ss.isBackButtonVisible(5)) {
					if (!ss.clickOnBackButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
				if (ss.isBackButtonVisible(5)) {
					if (!ss.clickOnBackButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
			} else {
				if (ss.isCloseButtonVisible(5)) {
					if (!ss.clickOnCloseButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Close button");
				}
				if (ss.isCloseButtonVisible(5)) {
					if (!ss.clickOnCloseButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Close button");
				}

			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"App is not redirected to schedule selection screen");
		}
		return flag;
	}

	public static boolean verifyScheduleEdited(TestCases testCase, TestCaseInputs inputs, String scheduleType) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
			List<WebElement> schedule_setpoints = null;
			WebElement setPointIOS = null;
			String tempHeatSetPointApp = "", tempCoolSetPointApp = "", tempHeatSetPointFromInputs = "",
					tempCoolSetPointFromInputs = "", SleepStartEndTime = "", SleepStartTime = "", SleepEndTime = "",
					periodStartTimeApp = "", periodStartTimeInputs = "";
			String[] scheduleDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			List<WebElement> scheduleDayHeaders = null;
			int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;

			flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			if (scheduleType.equalsIgnoreCase("geofence")) {
				// ============================================ANDROID============================================================
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
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
										"[HomeSettings] Home heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
							tempCoolSetPointApp = schedule_setpoints.get(0).getText();
							if (schedule_setpoints.get(0).getText().contains(".0")) {
								tempCoolSetPointApp = schedule_setpoints.get(0).getText().split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
							tempCoolSetPointApp = schedule_setpoints.get(0).getText();
							if (schedule_setpoints.get(0).getText().contains(".0")) {
								tempCoolSetPointApp = schedule_setpoints.get(0).getText().split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
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
										"[HomeSettings] Home heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
					}
				} else {
					// ==================================================IOS========================================================
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_HeatTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_HeatTemperature");
							tempHeatSetPointApp = setPointIOS.getAttribute("value");
							if (tempHeatSetPointApp.contains(".0")) {
								tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_CoolTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_CoolTemperature");
							tempCoolSetPointApp = setPointIOS.getAttribute("value");
							if (tempCoolSetPointApp.contains(".0")) {
								tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_CoolTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_CoolTemperature");
							tempCoolSetPointApp = setPointIOS.getAttribute("value");
							if (tempCoolSetPointApp.contains(".0")) {
								tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_HeatTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_HeatTemperature");
							tempHeatSetPointApp = setPointIOS.getAttribute("value");
							if (tempHeatSetPointApp.contains(".0")) {
								tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[HomeSettings] Home heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[HomeSettings] Home heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
					}
				}
				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CreateSleepSettings", 5)) {
						Keyword.ReportStep_Pass(testCase, "Create Sleep Settings is shown on solution card");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to find Create Sleep Settings option on solution card");
					}
				} else {
					// ============================================ANDROID============================================================
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
											"[SleepSettings] Sleep heat set point is shown correctly in solution card: "
													+ tempHeatSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep heat set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule heat set points");
							}
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
								schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_cooling_point");
								tempCoolSetPointApp = schedule_setpoints.get(1).getText();
								if (schedule_setpoints.get(1).getText().contains(".0")) {
									tempCoolSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
								}
								tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT);
								if (tempCoolSetPointFromInputs.contains(".0")) {
									tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
								}
								if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep cool set point is shown correctly in solution card: "
													+ tempCoolSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep cool set point: " + tempCoolSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule cool set points");
							}
						} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
								schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_cooling_point");
								tempCoolSetPointApp = schedule_setpoints.get(1).getText();
								if (schedule_setpoints.get(1).getText().contains(".0")) {
									tempCoolSetPointApp = schedule_setpoints.get(1).getText().split("\\.")[0];
								}
								tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT);
								if (tempCoolSetPointFromInputs.contains(".0")) {
									tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
								}
								if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep cool set point is shown correctly in solution card: "
													+ tempCoolSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep cool set point: " + tempCoolSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule cool set points");
							}
						} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
											"[SleepSettings] Sleep heat set point is shown correctly in solution card: "
													+ tempHeatSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep heat set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule heat set points");
							}
						}
						SleepStartEndTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText();

						String dateString = SleepStartEndTime.replaceAll("\\.", "");
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

						SleepStartTime = SleepStartEndTime.split("\\s+")[0] + " " + SleepStartEndTime.split("\\s+")[1];
						SleepEndTime = SleepStartEndTime.split("\\s+")[3] + " " + SleepStartEndTime.split("\\s+")[4];
						if (SleepStartTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME).toLowerCase().replaceAll("^0*", ""))) {
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
						if (SleepEndTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME).toLowerCase().replaceAll("^0*", ""))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep End time is shown correctly in solution card: " + SleepEndTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep End time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME)
											+ " is not shown correctly in solution card: " + SleepEndTime);
						}
					} else {
						// ==================================================IOS========================================================
						if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_HeatTemperature", testCase, 5)) {
								setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_HeatTemperature");
								tempHeatSetPointApp = setPointIOS.getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
								if (tempHeatSetPointFromInputs.contains(".0")) {
									tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
								}
								if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep heat set point is shown correctly in solution card: "
													+ tempHeatSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep heat set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule heat set points");
							}
							if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_CoolTemperature", testCase, 5)) {
								setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_CoolTemperature");
								tempCoolSetPointApp = setPointIOS.getAttribute("value");
								if (tempCoolSetPointApp.contains(".0")) {
									tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
								}
								tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT);
								if (tempCoolSetPointFromInputs.contains(".0")) {
									tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
								}
								if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep cool set point is shown correctly in solution card: "
													+ tempCoolSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep cool set point: " + tempCoolSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule cool set points");
							}
						} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_CoolTemperature", testCase, 5)) {
								setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_CoolTemperature");
								tempCoolSetPointApp = setPointIOS.getAttribute("value");
								if (tempCoolSetPointApp.contains(".0")) {
									tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
								}
								tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT);
								if (tempCoolSetPointFromInputs.contains(".0")) {
									tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
								}
								if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep cool set point is shown correctly in solution card: "
													+ tempCoolSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep cool set point: " + tempCoolSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule cool set points");
							}
						} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
							if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_HeatTemperature", testCase, 5)) {
								setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_HeatTemperature");
								tempHeatSetPointApp = setPointIOS.getAttribute("value");
								if (tempHeatSetPointApp.contains(".0")) {
									tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
								}
								tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT);
								if (tempHeatSetPointFromInputs.contains(".0")) {
									tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
								}
								if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
									Keyword.ReportStep_Pass(testCase,
											"[SleepSettings] Sleep heat set point is shown correctly in solution card: "
													+ tempHeatSetPointApp);
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[SleepSettings] Sleep heat set point: " + tempHeatSetPointFromInputs
											+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate schedule heat set points");
							}
						}
						SleepStartEndTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value");
						SleepStartTime = SleepStartEndTime.split("\\s+")[0] + " " + SleepStartEndTime.split("\\s+")[1];
						SleepEndTime = SleepStartEndTime.split("\\s+")[3] + " " + SleepStartEndTime.split("\\s+")[4];
						if (SleepStartTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME).toLowerCase().replaceAll("^0*", ""))) {
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
						if (SleepEndTime.equalsIgnoreCase(inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME).toLowerCase().replaceAll("^0*", ""))) {
							Keyword.ReportStep_Pass(testCase,
									"[SleepSettings] Sleep End time is shown correctly in solution card: " + SleepEndTime);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[SleepSettings] Sleep End time: "
											+ inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME)
											+ " is not shown correctly in solution card: " + SleepEndTime);
						}
					}
				}
				// ============================================ANDROID============================================================
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int i;
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						i = 2;
					} else {
						i = 1;
					}
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
							tempHeatSetPointApp = schedule_setpoints.get(i).getText();
							if (schedule_setpoints.get(i).getText().contains(".0")) {
								tempHeatSetPointApp = schedule_setpoints.get(i).getText().split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
							tempCoolSetPointApp = schedule_setpoints.get(i).getText();
							if (schedule_setpoints.get(i).getText().contains(".0")) {
								tempCoolSetPointApp = schedule_setpoints.get(i).getText().split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
							tempCoolSetPointApp = schedule_setpoints.get(i).getText();
							if (schedule_setpoints.get(i).getText().contains(".0")) {
								tempCoolSetPointApp = schedule_setpoints.get(i).getText().split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_setpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
							tempHeatSetPointApp = schedule_setpoints.get(i).getText();
							if (schedule_setpoints.get(i).getText().contains(".0")) {
								tempHeatSetPointApp = schedule_setpoints.get(i).getText().split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
					}
				} else {
					// ==================================================IOS========================================================
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_HeatTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_HeatTemperature");
							tempHeatSetPointApp = setPointIOS.getAttribute("value");
							if (tempHeatSetPointApp.contains(".0")) {
								tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_CoolTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_CoolTemperature");
							tempCoolSetPointApp = setPointIOS.getAttribute("value");
							if (tempCoolSetPointApp.contains(".0")) {
								tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_CoolTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_CoolTemperature");
							tempCoolSetPointApp = setPointIOS.getAttribute("value");
							if (tempCoolSetPointApp.contains(".0")) {
								tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
							}
							tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT);
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away cool set point is shown correctly in solution card: "
												+ tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away cool set point: " + tempCoolSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempCoolSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule cool set points");
						}
					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_HeatTemperature", testCase, 5)) {
							setPointIOS = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_HeatTemperature");
							tempHeatSetPointApp = setPointIOS.getAttribute("value");
							if (tempHeatSetPointApp.contains(".0")) {
								tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
							}
							tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT);
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"[AwaySettings] Away heat set point is shown correctly in solution card: "
												+ tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[AwaySettings] Away heat set point: " + tempHeatSetPointFromInputs
										+ " is not shown correctly in solution card: " + tempHeatSetPointApp);
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate schedule heat set points");
						}
					}
				}
			} else if (scheduleType.equalsIgnoreCase("Everyday")) {
				if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
					flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Grouped Days");
				} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
					flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Individual Days");
				}

				WebElement period = null;
				CustomDriver driver = testCase.getMobileDriver();
				Dimension dimension = driver.manage().window().getSize();
				int height = dimension.getHeight();
				int width = dimension.getWidth();
				TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

				for (int i = 1; i <= 4; i++) {
					if (!inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).isEmpty()
							&& inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) != null) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (!MobileUtils.isMobElementExists("XPATH",
									"//*[@content-desc='" + inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "']", testCase, 5)) {
								testCase.getMobileDriver()
								.scrollToExact(inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).split("_")[1]);
								while (!MobileUtils.isMobElementExists("XPATH",
										"//*[@content-desc='" + inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "']", testCase,
										5)) {
									touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82)
									.release();
									touchAction.perform();
								}
							}
							period = testCase.getMobileDriver().findElement(
									By.xpath("//*[@content-desc='" + inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "']"));

							if (period.findElement(By.id("scheduling_period_time")) != null) {
								periodStartTimeApp = period.findElement(By.id("scheduling_period_time")).getText();
								System.out.println(periodStartTimeApp);
							}
							if (!periodStartTimeApp.equalsIgnoreCase("Tap to set")) {
								if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
									tempHeatSetPointApp = period.findElement(By.id("scheduling_period_heating_point"))
											.getText();
									tempCoolSetPointApp = period.findElement(By.id("scheduling_period_cooling_point"))
											.getText();
									System.out.println(tempHeatSetPointApp);
									System.out.println(tempCoolSetPointApp);
								} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
									tempCoolSetPointApp = period.findElement(By.id("scheduling_period_cooling_point"))
											.getText();
									System.out.println(tempCoolSetPointApp);
								} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
									tempHeatSetPointApp = period.findElement(By.id("scheduling_period_heating_point"))
											.getText();
									System.out.println(tempHeatSetPointApp);
								}
							}

							if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Wake")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("1")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Away")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("2")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Home")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("3")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Sleep")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("4")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT);
								}
							}

							if (!periodStartTimeApp.equalsIgnoreCase("Tap to set")) {
								if (!periodStartTimeApp.contains("M") && !periodStartTimeApp.contains("m")) {
									Date returnTimeApp, returnTimeInputs;
									SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
									String dateStringApp = periodStartTimeApp.replaceAll("\\.", "");
									String dateStringInputs = periodStartTimeInputs.replaceAll("\\.", "");
									try {
										returnTimeApp = df24.parse(dateStringApp);
										periodStartTimeApp = df24.format(returnTimeApp);
										returnTimeInputs = df24.parse(dateStringInputs);
										periodStartTimeInputs = df24.format(returnTimeInputs);
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error: " + e.getMessage());
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error: " + e.getMessage());
									}
								}
							}
							System.out.println(periodStartTimeApp);
							System.out.println(periodStartTimeInputs);
						} else {
							desiredDayIndex = Arrays.asList(scheduleDays)
									.indexOf(inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).split("_")[0]);
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
								scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase,
										"ScheduleDayHeader");
								lesserDayIndex = Arrays.asList(scheduleDays)
										.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
								greaterDayIndex = Arrays.asList(scheduleDays).indexOf(
										scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
							}
							int m = 0;
							while ((!MobileUtils
									.isMobElementExists("XPATH",
											"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
													+ inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "']",
													testCase, 5))
									&& m < 10) {
								if (desiredDayIndex > greaterDayIndex) {
									touchAction.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
									m++;
								} else if (desiredDayIndex < lesserDayIndex) {
									touchAction.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
									m++;
								} else {
									touchAction.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
									m++;
								}
							}
							if (!MobileUtils.isMobElementExists("XPATH",
									"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='"
											+ inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "']",
											testCase, 5)) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the period: " + inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i));
							} else {
								period = testCase.getMobileDriver()
										.findElement(By.name(inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i)));
							}

							String cp = inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "_CoolTemperature";
							String hp = inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "_HeatTemperature";
							WebElement elemTime = testCase.getMobileDriver()
									.findElement(By.name(inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i) + "_Time"));
							System.out.println(elemTime.getAttribute("value"));
							if (elemTime != null) {
								periodStartTimeApp = elemTime.getAttribute("value");
							}
							if (!periodStartTimeApp.equalsIgnoreCase("Tap to set")) {
								if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
									WebElement elemCP = testCase.getMobileDriver().findElement(By.name(cp));
									WebElement elemHP = testCase.getMobileDriver().findElement(By.name(hp));
									System.out.println(elemCP.getAttribute("value"));
									System.out.println(elemHP.getAttribute("value"));
									tempHeatSetPointApp = elemHP.getAttribute("value");
									tempCoolSetPointApp = elemCP.getAttribute("value");
								} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
									WebElement elemCP = testCase.getMobileDriver().findElement(By.name(cp));
									System.out.println(elemCP.getAttribute("value"));
									tempCoolSetPointApp = elemCP.getAttribute("value");

								} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
									WebElement elemHP = testCase.getMobileDriver().findElement(By.name(hp));
									System.out.println(elemHP.getAttribute("value"));
								}
							}

							if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Wake")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("1")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Away")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("2")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Home")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("3")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT);
								}
							} else if (inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("Sleep")
									|| inputs.getInputValue(InputVariables.PERIOD_NAME_NA + i).contains("4")) {
								if (statInfo.getJasperDeviceType().equalsIgnoreCase("HoneyBadger")
										|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT);
								} else {
									periodStartTimeInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_TIME);
									tempHeatSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT);
									tempCoolSetPointFromInputs = inputs.getInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT);
								}
							}

							if (!periodStartTimeApp.equalsIgnoreCase("Tap to set")) {
								if (!periodStartTimeApp.contains("M") && !periodStartTimeApp.contains("m")) {
									Date returnTimeApp, returnTimeInputs;
									SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
									String dateStringApp = periodStartTimeApp.replaceAll("\\.", "");
									String dateStringInputs = periodStartTimeInputs.replaceAll("\\.", "");
									try {
										returnTimeApp = df24.parse(dateStringApp);
										periodStartTimeApp = df24.format(returnTimeApp);
										returnTimeInputs = df24.parse(dateStringInputs);
										periodStartTimeInputs = df24.format(returnTimeInputs);
									} catch (ParseException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[ParseException] Error: " + e.getMessage());
									} catch (NumberFormatException e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[NumberFormatException] Error: " + e.getMessage());
									} catch (Exception e) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[Exception] Error: " + e.getMessage());
									}
								}
							}
						}
						if (!periodStartTimeApp.isEmpty()) {
							if (periodStartTimeApp.equalsIgnoreCase(periodStartTimeInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"Successfully edited period time to " + periodStartTimeApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Period time in app: " + periodStartTimeApp + " is not changed to expected value: "
												+ periodStartTimeInputs);
							}
						}
						if (!tempCoolSetPointApp.isEmpty()) {
							if (tempCoolSetPointApp.contains(".0")) {
								tempCoolSetPointApp = tempCoolSetPointApp.split("\\.")[0];
							}
							if (tempCoolSetPointFromInputs.contains(".0")) {
								tempCoolSetPointFromInputs = tempCoolSetPointFromInputs.split("\\.")[0];
							}
							if (tempCoolSetPointApp.equalsIgnoreCase(tempCoolSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"Successfully edited period cool point to " + tempCoolSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Period cool point in app: " + tempCoolSetPointApp
										+ " is not changed to expected value: " + tempCoolSetPointFromInputs);
							}
						}
						if (!tempHeatSetPointApp.isEmpty()) {
							if (tempHeatSetPointApp.contains(".0")) {
								tempHeatSetPointApp = tempHeatSetPointApp.split("\\.")[0];
							}
							if (tempHeatSetPointFromInputs.contains(".0")) {
								tempHeatSetPointFromInputs = tempHeatSetPointFromInputs.split("\\.")[0];
							}
							if (tempHeatSetPointApp.equalsIgnoreCase(tempHeatSetPointFromInputs)) {
								Keyword.ReportStep_Pass(testCase,
										"Successfully edited period heat point to " + tempHeatSetPointApp);
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Period heat point in app: " + tempHeatSetPointApp
										+ " is not changed to expected value: " + tempHeatSetPointFromInputs);
							}
						}
					}
				}

			} else if (scheduleType.equalsIgnoreCase("Weekday and Weekend")) {

			}
		} catch (Exception e) {

		}
		return flag;
	}

	public static boolean verifyIndoorTemperature(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String chilDeviceIndoorTemperature = statInfo.getIndoorTemperature();
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(fieldObjects, testCase, "IndoorTemperature").getText()
					.equals(chilDeviceIndoorTemperature)) {
				Keyword.ReportStep_Pass(testCase, "Verify Indoor Temperature : Indoor temperature is : "
						+ chilDeviceIndoorTemperature + " in both app and CHIL");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Primary Card Elements : Indoor temperature is not the same in CHIL and app. App : "
								+ MobileUtils.getMobElement(fieldObjects, testCase, "IndoorTemperature").getText()
								+ " ,CHIL : " + chilDeviceIndoorTemperature);
			}
		} else {
			if (MobileUtils.getMobElement(fieldObjects, testCase, "IndoorTemperature").getAttribute("value")
					.equals(chilDeviceIndoorTemperature)) {
				Keyword.ReportStep_Pass(testCase, "Verify Indoor Temperature : Indoor temperature is : "
						+ chilDeviceIndoorTemperature + " in both app and CHIL");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Primary Card Elements : Indoor temperature is not the same in CHIL and app. App : "
								+ MobileUtils.getMobElement(fieldObjects, testCase, "IndoorTemperature")
								.getAttribute("value")
								+ " ,CHIL : " + chilDeviceIndoorTemperature);
			}
		}
		return flag;
	}

	public static boolean verifySystemMode(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String chilDeviceSystemMode = statInfo.getThermoStatMode();
		String chilDeviceID = statInfo.getDeviceID();
		String appDeviceSystemMode;
		String appDeviceDialerSystemMode;
		WebElement dialer;
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			dialer = MobileUtils.getMobElement(testCase, "name", "Dialer_" + chilDeviceID);
			appDeviceSystemMode = MobileUtils.getMobElement(fieldObjects, testCase, "SystemModeButton")
					.getAttribute("value");
			appDeviceDialerSystemMode = dialer.getAttribute("label");
		} else {
			dialer = MobileUtils.getMobElement(fieldObjects, testCase, "Dialer");
			appDeviceSystemMode = MobileUtils.getMobElement(fieldObjects, testCase, "SystemModeButton")
					.getAttribute("name");
			appDeviceDialerSystemMode = MobileUtils.getMobElement(fieldObjects, testCase, "Dialer").getAttribute("name")
					.split(",")[0];
		}
		if (chilDeviceSystemMode.equals(appDeviceSystemMode)) {
			Keyword.ReportStep_Pass(testCase,
					"Verify System Mode : System mode icon is in " + appDeviceSystemMode + " in both app and CHIL");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Verify System Mode : System mode is "
					+ chilDeviceSystemMode + " in CHIL but system mode icon is in " + appDeviceSystemMode + " mode");
		}

		if (chilDeviceSystemMode.equalsIgnoreCase("Off")) {
			if (chilDeviceSystemMode.equalsIgnoreCase(appDeviceDialerSystemMode)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify System Mode : Dialer is in " + chilDeviceSystemMode + " mode in both app and CHIL");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify System Mode : Dialer mode in app is different from CHIL. Dialer mode in app : "
								+ appDeviceDialerSystemMode + " , CHIL : " + chilDeviceSystemMode);
			}
		} else {
			if (chilDeviceSystemMode.equalsIgnoreCase("Auto")) {
				chilDeviceSystemMode = statInfo.getThermostatModeWhenAutoChangeOverActive();
			}
			if (chilDeviceSystemMode.equalsIgnoreCase(appDeviceDialerSystemMode)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify System Mode : Dialer is in " + chilDeviceSystemMode + " mode in both app and CHIL");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify System Mode : Dialer mode in app is different from CHIL. Dialer mode in app : "
								+ appDeviceDialerSystemMode + " , CHIL : " + chilDeviceSystemMode);
			}
		}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifySystemMode(TestCases testCase, TestCaseInputs inputs, String expectedMode) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
				testCase.getMobileDriver());
		fWait.pollingEvery(1, TimeUnit.SECONDS);
		fWait.withTimeout(60, TimeUnit.SECONDS);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				String changedMode = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					changedMode = MobileUtils.getMobElement(fieldObjects, testCase, "SystemModeButton")
							.getAttribute("name");
				} else {
					changedMode = MobileUtils.getMobElement(fieldObjects, testCase, "SystemModeButton")
							.getAttribute("value");
				}
				if (changedMode.equals(expectedMode)) {
					return true;
				} else {
					return false;
				}
			}
		});

		if (isEventReceived) {
			Keyword.ReportStep_Pass(testCase,
					"Verify System Mode : Thermostat is in " + expectedMode + " mode in the app");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verify System Mode : Thermostat is not in " + expectedMode + " mode in the app");
		}

		/*
		 * String isEventReceived1 = fWait.until(new
		 * Function<CustomDriver, String>() { public String
		 * apply(CustomDriver driver) { String changedMode = "";
		 * DeviceInformation statInfo = new DeviceInformation(testCase,
		 * inputs); if (statInfo.getThermoStatMode().equals(expectedMode)) {
		 * changedMode = statInfo.getThermoStatMode(); return changedMode; }
		 * else { return null; } } });
		 * 
		 * if (isEventReceived1.equals(expectedMode)) {
		 * Keyword.ReportStep_Pass(testCase,
		 * "Verify System Mode : Thermostat Mode is " + expectedMode +
		 * "ing in CHIL"); } else { flag = false;
		 * Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
		 * "Verify System Mode : Thermostat Mode is not in " + expectedMode +
		 * "ing in CHIL"); }
		 */
		return flag;
	}

	public static boolean verifyDisplayedSetPoints(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String chilDeviceSystemMode = statInfo.getThermoStatMode();
		String chilDeviceID = statInfo.getDeviceID();
		String appDeviceSetPoints = " ";
		WebElement dialer;

		if (chilDeviceSystemMode.equalsIgnoreCase("Off")) {
			Keyword.ReportStep_Pass(testCase,
					"Verify Displayed Set Points : System mode is off. Hence, not verifying set points");
			return true;
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				dialer = MobileUtils.getMobElement(testCase, "name", "Dialer_" + chilDeviceID);
				appDeviceSetPoints = dialer.getAttribute("value");
			} else {
				dialer = MobileUtils.getMobElement(fieldObjects, testCase, "Dialer");
				appDeviceSetPoints = MobileUtils.getMobElement(fieldObjects, testCase, "Dialer").getAttribute("name")
						.split(",")[1];
			}
			if (chilDeviceSystemMode.equalsIgnoreCase("Auto")) {
				chilDeviceSystemMode = statInfo.getThermostatModeWhenAutoChangeOverActive();
			}
			String deviceSetPoints = statInfo.getCurrentSetPoints();
			if (Double.parseDouble(deviceSetPoints) - Double.parseDouble(appDeviceSetPoints) == 0) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Set Points : Displayed dialer setpoint value is same as value in CHIL");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Set Points : Displayed dialer set point value is not same as value in CHIL. Dialer value : "
								+ appDeviceSetPoints + ", CHIL value : " + deviceSetPoints);
			}
		}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifyAvailableSystemModes(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "SystemModeButton");
			if (allowedModes.contains("Auto") && allowedModes.contains("Cool") && allowedModes.contains("Heat")
					&& allowedModes.contains("Off")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AutoModeOption", 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Auto mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Auto mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Cool mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Cool mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Heat mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Heat mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OffModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Off mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Off mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
			} else if (!allowedModes.contains("Auto") && allowedModes.contains("Cool") && allowedModes.contains("Heat")
					&& allowedModes.contains("Off")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AutoModeOption", 3)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Auto mode displayed in UI allowed modes but not available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Cool mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Cool mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Heat mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Heat mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OffModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Off mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Off mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
			} else if (!allowedModes.contains("Auto") && !allowedModes.contains("Cool") && allowedModes.contains("Heat")
					&& allowedModes.contains("Off")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AutoModeOption", 3)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Auto mode displayed in UI allowed modes but not available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolModeOption", 3)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Cool mode displayed in UI allowed modes but not available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Heat mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Heat mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OffModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Off mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Off mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
			} else if (!allowedModes.contains("Auto") && allowedModes.contains("Cool") && !allowedModes.contains("Heat")
					&& allowedModes.contains("Off")) {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AutoModeOption", 3)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Auto mode displayed in UI allowed modes but not available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CoolModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Cool mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Cool mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HeatModeOption", 3)) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Heat mode displayed in UI allowed modes but not available in CHIL allowed modes");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "OffModeOption", 3)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Available System Modes : Off mode displayed in allowed modes in both UI and CHIL");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Available System Modes : Off mode not displayed in UI allowed modes but available in CHIL allowed modes");
				}
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Available System Modes : Invalid allowed modes on CHIL : " + allowedModes);
				flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "CancelOption");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean verifyNumberOfSchedulePeriodsInTimeSchedule(TestCases testCase, TestCaseInputs inputs,
			int numberOfPeriodsToCheck) {
		boolean flag = true;
		List<WebElement> period = null;
		String temp = "";
		List<WebElement> scheduleDayHeaders = null;
		int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {

			flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Grouped Days");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@content-desc,'_Everyday')]", testCase, 5)) {
					period = MobileUtils.getMobElements(testCase, "xpath", "//*[contains(@content-desc,'_Everyday')]");
					temp = period.get(0).getAttribute("name");
					if (!(period.size() > 1)) {
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Period Delete icon is not shown-not able to delete the period");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Period Delete icon is shown");
						}

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Number of periods present is more than 1: " + period.size());
					}
				}
			} else {
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]", testCase,
						5)) {
					period = MobileUtils.getMobElements(testCase, "xpath",
							"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
					temp = period.get(0).getAttribute("name");
					if (!(period.size() > 1)) {
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (!MobileUtils.isMobElementExists("XPATH", "//*[contains(@name,'DELETE')]", testCase)) {
							Keyword.ReportStep_Pass(testCase,
									"Period Delete icon is not shown-not able to delete the period");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Period Delete icon is shown");
						}

					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Number of periods present is more than 1: " + period.size());
					}
				}
				if (MobileUtils.isMobElementExists("NAME", "Navigation_Left_Bar_Item", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "NAME", "Navigation_Left_Bar_Item")) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate CANCEL button");
				}
			}
		} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {

			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			int count = 0, tries;

			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

			flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Individual Days");

			for (int i = 0; i < days.length; i++) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					testCase.getMobileDriver().scrollToExact(days[i]);
					tries = 0;
					while (MobileUtils.isMobElementExists("XPATH",
							"//*[contains(@content-desc,'" + "_" + days[i] + "')]", testCase, 5) && tries < 1) {
						period = MobileUtils.getMobElements(testCase, "xpath",
								"//*[contains(@content-desc,'" + "_" + days[i] + "')]");
						temp = period.get(0).getAttribute("name");
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Period Delete icon is not shown-not able to delete the period");
							count++;
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Period Delete icon is shown");
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
							if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
								flag = false;
							}
						}
						touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
						touchAction.perform();
						tries++;
					}
				} else {
					if (i == 5 || i == 6) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
								.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					}
					desiredDayIndex = Arrays.asList(days).indexOf(days[i]);
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
						scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
						lesserDayIndex = Arrays.asList(days).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
						greaterDayIndex = Arrays.asList(days)
								.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
					}
					int m = 0;
					while ((!MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='Add Period']/preceding-sibling::XCUIElementTypeStaticText[@value='"
									+ days[i] + "']",
							testCase, 5)) && m < 10) {
						if (desiredDayIndex > greaterDayIndex) {
							touchAction.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
							m++;
							scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase,
									"ScheduleDayHeader");
							lesserDayIndex = Arrays.asList(days)
									.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
							System.out.println(scheduleDayHeaders.get(0).getAttribute("value"));
							greaterDayIndex = Arrays.asList(days).indexOf(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
							System.out.println(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
						} else if (desiredDayIndex < lesserDayIndex) {
							touchAction.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
							m++;
							scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase,
									"ScheduleDayHeader");
							lesserDayIndex = Arrays.asList(days)
									.indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
							System.out.println(scheduleDayHeaders.get(0).getAttribute("value"));
							greaterDayIndex = Arrays.asList(days).indexOf(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
							System.out.println(
									scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
							// System.out.println(testCase.getMobileDriver().getPageSource());
						} else {
							break;
						}
					}
					WebElement tempDay = testCase.getMobileDriver().findElement(
							By.xpath("//*[@name='Add Period']/preceding-sibling::XCUIElementTypeStaticText[@value='"
									+ days[i] + "']"));
					if (tempDay != null) {
						Keyword.ReportStep_Pass(testCase, "Located - " + days[i]);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate - " + days[i]);
						return flag;
					}
					tries = 0;
					while (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[contains(@name,'" + days[i] + "_Time')]", testCase, 5)
							&& tries < 1) {
						period = MobileUtils.getMobElements(testCase, "xpath",
								"//XCUIElementTypeStaticText[contains(@name,'" + days[i] + "_Time')]");
						temp = period.get(0).getAttribute("name");
						try {
							period.get(0).click();
							Keyword.ReportStep_Pass(testCase, "Selected period-" + temp);
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to select the period-" + temp);
						}
						if (!MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
							Keyword.ReportStep_Pass(testCase,
									"Period Delete icon is not shown-not able to delete the period");
							count++;
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Period Delete icon is shown");
						}
						if (MobileUtils.isMobElementExists("NAME", "Navigation_Left_Bar_Item", testCase, 5)) {
							if (!MobileUtils.clickOnElement(testCase, "NAME", "Navigation_Left_Bar_Item")) {
								flag = false;
							}
						}
						tries++;
					}
				}
			}
			if (count > 2) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Number of periods present: " + count
						+ " are more than expected number " + numberOfPeriodsToCheck);
			} else {
				Keyword.ReportStep_Pass(testCase,
						"Number of periods present: " + count + " is as expected number: " + numberOfPeriodsToCheck);
			}
		}

		return flag;
	}

}
