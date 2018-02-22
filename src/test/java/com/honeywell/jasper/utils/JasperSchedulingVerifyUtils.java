package com.honeywell.jasper.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;

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


}
