package com.honeywell.jasper.utils;

import java.util.Arrays;
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

}
