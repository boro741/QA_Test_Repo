package com.honeywell.keywords.jasper.scheduling.Edit;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingEditUtils;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;

public class EditTimeBasedScheduleForSelectedNumberOfDays extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;
	String schedulePeriodToSelect = "", tempDays = "";
	int i = 0, tempHours = 0;;
	ArrayList<String> arrlistPeriod = new ArrayList<String>(8);
	ArrayList<String> arrlistDays = new ArrayList<String>(8);
	ArrayList<Integer> templist = new ArrayList<Integer>(8);

	public EditTimeBasedScheduleForSelectedNumberOfDays(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user edits schedule periods of \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {

		if (exampleData.get(0).equalsIgnoreCase("One Day")) {
			i = 1;
		} else if (exampleData.get(0).equalsIgnoreCase("Two Days")) {
			i = 2;
		} else if (exampleData.get(0).equalsIgnoreCase("Three Days")) {
			i = 3;
		} else if (exampleData.get(0).equalsIgnoreCase("Four Days")) {
			i = 4;
		} else if (exampleData.get(0).equalsIgnoreCase("Five Days")) {
			i = 5;
		} else if (exampleData.get(0).equalsIgnoreCase("Six Days")) {
			i = 6;
		}
		try {

			String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
			String[] schedulePeriods_EMEA = { "1", "2", "3", "4", "5", "6" };
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			Random rn = new Random();

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}
			inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
			inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);

			if (statInfo.getThermostatType().equals("Jasper")) {
				List<String> allowedModes = statInfo.getAllowedModes();
				HashMap<String, String> defaultValues;
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				String temp = " ";

				if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {	
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("EverydayWakeCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayWakeHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayAwayCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayAwayHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayHomeCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayHomeHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydaySleepCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydaySleepHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT, temp);
					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("EverydayWakeHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayAwayHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayHomeHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydaySleepHeatTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT, temp);
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("EverydayWakeCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayAwayCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydayHomeCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("EverydaySleepCoolTemp"));
						inputs.setInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT, temp);
						inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT, temp);
					}

					for (int j = 1; j <= i; j++) {
						if (jasperStatType.toUpperCase().contains("EMEA")) {
							do {
								tempHours = rn.nextInt((12 - 1) + 1) + 1;
							} while (templist.contains(tempHours));
							templist.add(tempHours);

							String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayWakeTime"), true, 1, 00);
							inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayAwayTime"), true, 1, 00);
							inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayHomeTime"), true, 1, 00);
							inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydaySleepTime"), true, 1, 00);
							inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, changedTime);

						} else if (jasperStatType.toUpperCase().contains("NA")) {
							do {
								tempHours = rn.nextInt((12 - 1) + 1) + 1;
							} while (templist.contains(tempHours));
							templist.add(tempHours);

							String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayWakeTime"), true, tempHours, 15);
							inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayAwayTime"), true, tempHours, 15);
							inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydayHomeTime"), true, tempHours, 15);
							inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, changedTime);
							changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
									defaultValues.get("EverydaySleepTime"), true, tempHours, 15);
							inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, changedTime);
						}

						do {
							if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
								if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
									if (jasperStatType.equalsIgnoreCase("NA")) {
										schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0]
												+ "_Everyday";
									} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
										schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0]
												+ "_Everyday";
									}
								} else {
									if (jasperStatType.equalsIgnoreCase("NA")) {
										schedulePeriodToSelect = "Everyday_"
												+ schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0];
									} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
										schedulePeriodToSelect = "Everyday_"
												+ schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0];
									}
								}

							} else
								if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("SINGLE DAY")) {
									tempDays = days[rn.nextInt((6 - 0) + 1) + 0];
									if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
										if (jasperStatType.equalsIgnoreCase("NA")) {
											schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0] + "_"
													+ tempDays;
										} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
											schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0] + "_"
													+ tempDays;
										}
									} else {
										if (jasperStatType.equalsIgnoreCase("NA")) {
											schedulePeriodToSelect = tempDays + "_"
													+ schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0];
										} else {
											schedulePeriodToSelect = tempDays + "_"
													+ schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0];
										}
									}
								}
						} while (arrlistPeriod.contains(schedulePeriodToSelect) || arrlistDays.contains(tempDays));
						arrlistPeriod.add(schedulePeriodToSelect);
						arrlistDays.add(tempDays);

						flag = flag & JasperSchedulingEditUtils.editTimeBasedSchedule(testCase, inputs, schedulePeriodToSelect, j);
					}
				}
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
			}
		}catch (Exception e){
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public String parseValue(String value) {
		try {
			Double temp;
			if (inputs.getInputValue(InputVariables.UNITS).equals("Fahrenheit")) {
				temp = Double.parseDouble(value);
				return String.valueOf(temp.intValue());
			} else {
				temp = Double.parseDouble(value);
				return String.valueOf(temp);
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Parse Value : Error Occured : " + e.getMessage());
			return "";
		}
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
