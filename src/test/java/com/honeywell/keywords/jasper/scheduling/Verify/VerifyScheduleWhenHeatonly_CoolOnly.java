package com.honeywell.keywords.jasper.scheduling.Verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

public class VerifyScheduleWhenHeatonly_CoolOnly {

	public static boolean VerifyScheduleWhenHeatonly_CoolOnly(TestCases testCase, TestCaseInputs inputs,
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
							if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("FlyCatcher")) {
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
													&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period WAKE's expected time and cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_TIME)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period WAKE's expected time and cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " "
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
													&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period AWAY's expected time and  cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_TIME)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period AWAY's expected time and  cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_TIME)
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime + " " + ss.getCoolSetPointsOfGivenEverydayPeriod(
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
													&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period HOME's expected time and  cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_TIME)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period HOME's expected time and  cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime 
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
													&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
													.equalsIgnoreCase(ss.getCoolSetPointsOfGivenEverydayPeriod(
															schedule_everydaytitle.get(i).getText()).replaceAll("°",""))) {
												Keyword.ReportStep_Pass(testCase,
														"Period SLEEP's expected time and  cool set points are shown correctly: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_TIME)
																+ " " + inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Period SLEEP's expected time and  cool set points: "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_TIME)
																+ " "
																+ inputs.getInputValue(
																		InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
																+ " are not shown correctly: " + tempTime 
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

					if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("FLYCATCHER")) {
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
											&& inputs.getInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Wake's expected weekday time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Wake's expected weekday time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_WAKE_TIME) 
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Away's expected weekday time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME)
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Away's expected weekday time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_AWAY_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Home's expected weekday time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Home's expected weekday time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_HOME_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekdayPeriod(
													weekdayschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekday-Sleep's expected weekday time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekday-Sleep's expected weekday time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKDAY_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Wake's expected Weekend time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Wake's expected Weekend time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_WAKE_TIME) 
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_WAKE_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Away's expected Weekend time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Away's expected Weekend time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_AWAY_TIME) 
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_AWAY_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
											&& inputs.getInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Home's expected Weekend time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Home's expected Weekend time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_HOME_TIME) 
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_HOME_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime
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
											.equalsIgnoreCase(tempTime)
											&& inputs.getInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
											.equalsIgnoreCase(ss.getCoolSetPointsOfGivenWeekendPeriod(
													weekendschedule_periodtitle.get(i).getText()).replaceAll("°",""))) {
										Keyword.ReportStep_Pass(testCase,
												"Period Weekend-Sleep's expected Weekend time and  cool set points are shown correctly: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) 
														+ " " + inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT));
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Period Weekend-Sleep's expected Weekend time and  cool set points: "
														+ inputs.getInputValue(InputVariables.WEEKEND_SLEEP_TIME) + " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT)
														+ " "
														+ inputs.getInputValue(
																InputVariables.WEEKEND_SLEEP_COOL_SETPOINT)
														+ " are not shown correctly: " + tempTime 
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
					 if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("NA") || inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("FLYCATCHER")) {
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
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Wake");
										if (inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period WAKE's expected time and  cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period WAKE's expected time and  cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME)
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
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Away");
										if (inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period AWAY's expected time and  cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period AWAY's expected time and  cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME)
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
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Home");
										if (inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period HOME's expected time and  cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period HOME's expected time and  cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME)
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
										tempCoolSetPointApp = ss.getCoolSetPointsOfGivenEverydayPeriod("Sleep");
										if (inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
												.equalsIgnoreCase(tempTime)
												&& inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT)
												.equalsIgnoreCase(tempCoolSetPointApp.replaceAll("˚",""))) {
											Keyword.ReportStep_Pass(testCase,
													"Period SLEEP's expected time and  cool set points are shown correctly: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
															+ " " + inputs.getInputValue(
																	InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Period SLEEP's expected time and  cool set points: "
															+ inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME)
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

}
