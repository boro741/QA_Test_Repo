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


import io.appium.java_client.TouchAction;

public class JasperSchedulingVerifyUtils {

	public static boolean verifyTemperatureWithInRange(TestCases testCase, TestCaseInputs inputs) {

		boolean flag = true;
		try
		{	
			List<WebElement> schedule_heatsetpoints, schedule_coolsetpoints, schedule_period_time = null;
			WebElement heatSetPoint, coolSetPoint;
			String[] schedulePeriods = { "Wake", "Away", "Home", "Sleep" };

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();

			flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);

			if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
				Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
				Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
				Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
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
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_coolsetpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
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
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_CoolTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_CoolTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_CoolTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_CoolTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_CoolTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_CoolTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
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
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
								schedule_coolsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_cooling_point");
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
							if (MobileUtils.isMobElementExists("xpath",
									"//XCUIElementTypeStaticText[contains(@name,'_Time')]", testCase, 6)) {
								schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'_Time')]");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Everyday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double
										.parseDouble(MobileUtils
												.getMobElement(testCase, "name",
														"Everyday_" + schedulePeriods[i] + "_CoolTemperature")
												.getAttribute("value")) <= maxCool
												&& Double
												.parseDouble(
														MobileUtils
														.getMobElement(testCase, "name",
																"Everyday_" + schedulePeriods[i]
																		+ "_CoolTemperature")
														.getAttribute("value")) >= minCool) {
									Keyword.ReportStep_Pass(testCase,
											"Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[TemperatureInMaxMinRange] Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
								if (Double
										.parseDouble(MobileUtils
												.getMobElement(testCase, "name",
														"Everyday_" + schedulePeriods[i] + "_HeatTemperature")
												.getAttribute("value")) <= maxHeat
												&& Double
												.parseDouble(
														MobileUtils
														.getMobElement(testCase, "name",
																"Everyday_" + schedulePeriods[i]
																		+ "_HeatTemperature")
														.getAttribute("value")) >= minHeat) {
									Keyword.ReportStep_Pass(testCase,
											"Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[TemperatureInMaxMinRange] Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_heatsetpoints = MobileUtils.getMobElements(
									MobileUtils.loadObjectFile(testCase, "ScheduleScreen"), testCase,
									"SchedulePeriodHeatSetPoint");
							schedule_coolsetpoints = MobileUtils.getMobElements(
									MobileUtils.loadObjectFile(testCase, "ScheduleScreen"), testCase,
									"SchedulePeriodCoolSetPoint");
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_cooling_point");
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
							if (MobileUtils.isMobElementExists("xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
									testCase, 6)) {
								schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double
										.parseDouble(
												MobileUtils
												.getMobElement(testCase, "name",
														"Monday - Friday_" + schedulePeriods[i]
																+ "_HeatTemperature")
												.getAttribute("value")) <= maxHeat
												&& Double
												.parseDouble(MobileUtils
														.getMobElement(testCase, "name",
																"Monday - Friday_" + schedulePeriods[i]
																		+ "_HeatTemperature")
														.getAttribute("value")) >= minHeat) {
									Keyword.ReportStep_Pass(testCase,
											"[Monday - Friday] Heat Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Monday - Friday] Heat Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
								if (Double
										.parseDouble(
												MobileUtils
												.getMobElement(testCase, "name",
														"Monday - Friday_" + schedulePeriods[i]
																+ "_CoolTemperature")
												.getAttribute("value")) <= maxHeat
												&& Double
												.parseDouble(MobileUtils
														.getMobElement(testCase, "name",
																"Monday - Friday_" + schedulePeriods[i]
																		+ "_CoolTemperature")
														.getAttribute("value")) >= minHeat) {
									Keyword.ReportStep_Pass(testCase,
											"[Monday - Friday]Cool Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Monday - Friday]Cool Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
							}
							if (MobileUtils.isMobElementExists("xpath",
									"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
									testCase, 6)) {
								schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate Weekday schedule period times");
								return flag;
							}
							for (int i = 0; i < schedule_period_time.size(); i++) {
								if (Double
										.parseDouble(
												MobileUtils
												.getMobElement(testCase, "name",
														"Saturday - Sunday_" + schedulePeriods[i]
																+ "_HeatTemperature")
												.getAttribute("value")) <= maxHeat
												&& Double
												.parseDouble(MobileUtils
														.getMobElement(testCase, "name",
																"Saturday - Sunday_" + schedulePeriods[i]
																		+ "_HeatTemperature")
														.getAttribute("value")) >= minHeat) {
									Keyword.ReportStep_Pass(testCase,
											"[Saturday - Sunday]Heat Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Saturday - Sunday]Heat Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
								if (Double
										.parseDouble(
												MobileUtils
												.getMobElement(testCase, "name",
														"Saturday - Sunday_" + schedulePeriods[i]
																+ "_CoolTemperature")
												.getAttribute("value")) <= maxHeat
												&& Double
												.parseDouble(MobileUtils
														.getMobElement(testCase, "name",
																"Saturday - Sunday_" + schedulePeriods[i]
																		+ "_CoolTemperature")
														.getAttribute("value")) >= minHeat) {
									Keyword.ReportStep_Pass(testCase,
											"[Saturday - Sunday]Cool Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is set within or at the maximum and minimum range");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"[Saturday - Sunday]Cool Set Point value: "
													+ Double.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value"))
													+ " is not set within or at the maximum and minimum range");
								}
							}
						}
					}
				}
			} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
				Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
				Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
							schedule_coolsetpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_cooling_point");
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
						if (MobileUtils.isMobElementExists("name", "Geofence_Cool_HeatTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Cool_HeatTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Sleeep_CoolTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleeep_CoolTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(coolSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_CoolTemperature", testCase, 5)) {
							coolSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_CoolTemperature");
							if (Double.parseDouble(coolSetPoint.getAttribute("value")) <= maxCool
									&& Double.parseDouble(coolSetPoint.getAttribute("value")) >= minCool) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(coolSetPoint.getAttribute("value"))
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
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_cooling_point", testCase, 5)) {
								schedule_coolsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_cooling_point");
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
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Everyday_" + i + 1 + "_CoolTemperature")
													.getAttribute("value")) <= maxCool
													&& Double.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + i + 1 + "_CoolTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + i + 1 + "_CoolTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + i + 1 + "_CoolTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Everyday_" + schedulePeriods[i] + "_CoolTemperature")
													.getAttribute("value")) <= maxCool
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_CoolTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + schedulePeriods[i]
																				+ "_CoolTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + schedulePeriods[i]
																				+ "_CoolTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							schedule_heatsetpoints = MobileUtils.getMobElements(
									MobileUtils.loadObjectFile(testCase, "ScheduleScreen"), testCase,
									"SchedulePeriodHeatSetPoint");
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + (i + 1) + "_HeatTemperature")
													.getAttribute("value")) <= maxCool
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + (i + 1)
																	+ "_HeatTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + (i + 1) + "_HeatTemperature")
													.getAttribute("value")) <= maxCool
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + (i + 1)
																	+ "_HeatTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + schedulePeriods[i]
																	+ "_HeatTemperature")
													.getAttribute("value")) <= maxCool
													&& Double
													.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + schedulePeriods[i]
																	+ "_HeatTemperature")
													.getAttribute("value")) <= maxCool
													&& Double
													.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value")) >= minCool) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
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
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
							schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
									"scheduling_period_heating_point");
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
						if (MobileUtils.isMobElementExists("name", "Geofence_Home_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Home_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Home Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Sleeep_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleeep_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Sleep Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
										+ " is set within or at the maximum and minimum range");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[TemperatureInMaxMinRange] Sleep Set Point value: "
												+ Double.parseDouble(heatSetPoint.getAttribute("value"))
												+ " is not set within or at the maximum and minimum range");
							}
						}
						if (MobileUtils.isMobElementExists("name", "Geofence_Away_HeatTemperature", testCase, 5)) {
							heatSetPoint = MobileUtils.getMobElement(testCase, "name", "Geofence_Away_HeatTemperature");
							if (Double.parseDouble(heatSetPoint.getAttribute("value")) <= maxHeat
									&& Double.parseDouble(heatSetPoint.getAttribute("value")) >= minHeat) {
								Keyword.ReportStep_Pass(testCase,
										"Home Set Point value: " + Double.parseDouble(heatSetPoint.getAttribute("value"))
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
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.TIME_BASED_SCHEDULE)) {
					if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Everyday_" + (i + 1) + "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + (i + 1) + "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + (i + 1) + "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + (i + 1) + "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Everyday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Everyday_" + schedulePeriods[i] + "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Everyday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Everyday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					} else {

						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							// schedule_periodtimes_weekday =
							// MobileUtils.getMobElements(MobileUtils.loadObjectFile(testCase,
							// "ScheduleScreen"), testCase, "SchedulePeriodTime");
							schedule_heatsetpoints = MobileUtils.getMobElements(
									MobileUtils.loadObjectFile(testCase, "ScheduleScreen"), testCase,
									"SchedulePeriodHeatSetPoint");
							if (MobileUtils.isMobElementExists("ID", "scheduling_period_heating_point", testCase, 5)) {
								schedule_heatsetpoints = MobileUtils.getMobElements(testCase, "ID",
										"scheduling_period_heating_point");
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
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + (i + 1) + "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + (i + 1)
																	+ "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + (i + 1) + "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double
													.parseDouble(
															MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + (i + 1)
																	+ "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + (i + 1)
																		+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							} else {
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Monday - Friday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Monday - Friday_" + schedulePeriods[i]
																	+ "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double
													.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Monday - Friday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Monday - Friday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
								if (MobileUtils.isMobElementExists("xpath",
										"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]",
										testCase, 6)) {
									schedule_period_time = MobileUtils.getMobElements(testCase, "xpath",
											"//XCUIElementTypeStaticText[contains(@name,'Saturday - Sunday') and contains(@name,'_Time')]");
								}
								for (int i = 0; i < schedule_period_time.size(); i++) {
									if (Double
											.parseDouble(
													MobileUtils
													.getMobElement(testCase, "name",
															"Saturday - Sunday_" + schedulePeriods[i]
																	+ "_HeatTemperature")
													.getAttribute("value")) <= maxHeat
													&& Double
													.parseDouble(MobileUtils
															.getMobElement(testCase, "name",
																	"Saturday - Sunday_" + schedulePeriods[i]
																			+ "_HeatTemperature")
															.getAttribute("value")) >= minHeat) {
										Keyword.ReportStep_Pass(testCase,
												"Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is set within or at the maximum and minimum range");
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"[TemperatureInMaxMinRange] Set Point value: "
														+ Double.parseDouble(
																MobileUtils
																.getMobElement(testCase, "name",
																		"Saturday - Sunday_" + schedulePeriods[i]
																				+ "_HeatTemperature")
																.getAttribute("value"))
														+ " is not set within or at the maximum and minimum range");
									}
								}
							}
						}
					}
				}
			}

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				testCase.getMobileDriver().findElement(By.xpath("//*[@content-desc='Navigate Up']")).click();
			} else {
				if (MobileUtils.isMobElementExists("name", "btn close normal", testCase, 5)) {
					if (!MobileUtils.clickOnElement(testCase, "name", "btn close normal")) {
						flag = false;
					}
				}
			}
		}
		catch(Exception e)
		{
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

		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String jasperStatType = statInfo.getJasperDeviceType();

		if (timeInterval.equalsIgnoreCase("10")) {
			i = 10;
		} else if (timeInterval.equalsIgnoreCase("15")) {
			i = 15;
		}

		if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE).equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
			String geofenceStartTime = "", geofenceEndTime = "";
			Double temp;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("ID", "scheduling_period_startEnd_time", testCase, 5)) {
					if (MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time").getText()
							.contains("M")
							|| MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time").getText()
									.contains("m")) {
						geofenceStartTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText().split("\\s+")[0];
					} else {
						geofenceStartTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText().split("\\s+")[0];
					}
					if (MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time").getText()
							.contains("M")
							|| MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time").getText()
									.contains("m")) {
						geofenceEndTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText().split("\\s+")[3];
					} else {
						geofenceEndTime = MobileUtils.getMobElement(testCase, "ID", "scheduling_period_startEnd_time")
								.getText().split("\\s+")[2];
					}
				}
			} else {
				if (MobileUtils.isMobElementExists("name", "Geofence_Sleep_subTitle", testCase, 5)) {
					if (MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle").getAttribute("value")
							.contains("M")
							|| MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
									.getAttribute("value").contains("m")) {
						geofenceStartTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value").split("\\s+")[0];
					} else {
						geofenceStartTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value").split("\\s+")[0];
					}
					if (MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle").getAttribute("value")
							.contains("M")
							|| MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
									.getAttribute("value").contains("m")) {
						geofenceEndTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value").split("\\s+")[3];
					} else {
						geofenceEndTime = MobileUtils.getMobElement(testCase, "name", "Geofence_Sleep_subTitle")
								.getAttribute("value").split("\\s+")[2];
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
			if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
				String everydayStartTime = "", everydayEndTime = "";
				Double temp;
				List<WebElement> everydayPeriodTime = null;

				if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE) != null && !inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).isEmpty()) {
					if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
						flag = flag & JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Grouped Days");
					} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
						flag = flag
								& JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase, "Individual Days");
					}

					WebElement period = null;
					CustomDriver driver = testCase.getMobileDriver();
					Dimension dimension = driver.manage().window().getSize();
					int height = dimension.getHeight();
					int width = dimension.getWidth();
					TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						if (!MobileUtils.isMobElementExists("XPATH",
								"//*[@content-desc='" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']", testCase,
								5)) {
							testCase.getMobileDriver()
									.scrollToExact(inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[1]);
							while (!MobileUtils.isMobElementExists("XPATH",
									"//*[@content-desc='" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']",
									testCase, 5)) {
								touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82)
										.release();
								touchAction.perform();
							}
						}
						period = testCase.getMobileDriver().findElement(
								By.xpath("//*[@content-desc='" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "']"));
						period.findElement(By.id("scheduling_period_time")).click();

						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
							everydayStartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
									.getText().split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
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
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]Start time: "
											+ everydayStartTime + " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]Start time: "
											+ everydayStartTime + " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(everydayEndTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]End time: "
												+ everydayEndTime + " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]End time: "
												+ everydayEndTime + " is not set in intervals of " + i + " minutes");
							}
						}
					} else {
						desiredDayIndex = Arrays.asList(scheduleDays)
								.indexOf(inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED).split("_")[0]);
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
							scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase,
									"ScheduleDayHeader");
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
								Keyword.ReportStep_Pass(testCase,
										"Selected the period: " + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED));
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to select the period: " + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED));

							}
						}

						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
							everydayStartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
									.getAttribute("value").split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
								everydayEndTime = MobileUtils
										.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
										.getAttribute("value").split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the End time");
							}
						}
						temp = Double.parseDouble(everydayStartTime.split(":")[1]);
						if (temp.intValue() % i == 0) {
							Keyword.ReportStep_Pass(testCase,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]Start time: "
											+ everydayStartTime + " is set in intervals of " + i + " minutes");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]Start time: "
											+ everydayStartTime + " is not set in intervals of " + i + " minutes");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							temp = Double.parseDouble(everydayEndTime.split(":")[1]);
							if (temp.intValue() % i == 0) {
								Keyword.ReportStep_Pass(testCase,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]End time: "
												+ everydayEndTime + " is set in intervals of " + i + " minutes");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"[Period-" + inputs.getInputValue(InputVariables.SCHEDULE_PERIOD_EDITED) + "]End time: "
												+ everydayEndTime + " is not set in intervals of " + i + " minutes");
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
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EverydayTime", 5)) {
						everydayPeriodTime = MobileUtils.getMobElements(fieldObjects, testCase, "EverydayTime");
					}
					for (int e = 0; e < everydayPeriodTime.size(); e++) {
						if (everydayPeriodTime.get(e) != null) {
							everydayPeriodTime.get(e).click();
						}
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
								everydayStartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
										.getText().split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Start time");
							}
							if (jasperStatType.equalsIgnoreCase("EMEA")) {
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
									everydayEndTime = MobileUtils
											.getMobElement(fieldObjects, testCase, "TimeChooserEndTime").getText()
											.split("\\s+")[0];
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Failed to locate the End time");
								}
							}
						} else {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
								everydayStartTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
										.getAttribute("value").split("\\s+")[0];
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Start time");
							}
							if (jasperStatType.equalsIgnoreCase("EMEA")) {
								if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
									everydayEndTime = MobileUtils
											.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
											.getAttribute("value").split("\\s+")[0];
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
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
								if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
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
					}
				}
			} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE).equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
				String startTime = "", endTime = "";
				Double temp;
				List<WebElement> periodTime = null;
				// =================================================ANDROID====================================================================
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SchedulePeriodTime", 5)) {
						periodTime = MobileUtils.getMobElements(fieldObjects, testCase, "SchedulePeriodTime");
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
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
							startTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser").getText()
									.split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
								endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
										.getText().split("\\s+")[0];
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
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
								if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
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
					}
				}
				// =======================================================IOS===========================================================
				else {
					// ========================================Weekday=====================================================
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekdayPeriodTime", 5)) {
						periodTime = MobileUtils.getMobElements(fieldObjects, testCase, "WeekdayPeriodTime");
					}
					for (int e = 0; e < periodTime.size(); e++) {
						if (periodTime.get(e) != null) {
							periodTime.get(e).click();
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
							startTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
									.getAttribute("value").split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
								endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
										.getAttribute("value").split("\\s+")[0];
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
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
								if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
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
					}
					// ================================================Weekend====================================================
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeekendPeriodTime", 5)) {
						periodTime = MobileUtils.getMobElements(fieldObjects, testCase, "WeekendPeriodTime");
					}
					for (int e = 0; e < periodTime.size(); e++) {
						if (periodTime.get(e) != null) {
							periodTime.get(e).click();
						}
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooser", 5)) {
							startTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooser")
									.getAttribute("value").split("\\s+")[0];
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to locate the Start time");
						}
						if (jasperStatType.equalsIgnoreCase("EMEA")) {
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeChooserEndTime", 5)) {
								endTime = MobileUtils.getMobElement(fieldObjects, testCase, "TimeChooserEndTime")
										.getAttribute("value").split("\\s+")[0];
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
							if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BackButton", 5)) {
								if (!MobileUtils.clickOnElement(fieldObjects, testCase, "BackButton")) {
									flag = false;
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Failed to locate the Back button in Edit Period screen");
							}
						} else {
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
					}
				}
			}
		}

		return flag;
	}

	

}
