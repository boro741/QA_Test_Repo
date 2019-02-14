package com.honeywell.keywords.jasper.scheduling.View;

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
import com.honeywell.lyric.utils.InputVariables;

public class SetTimeSchedulePeriodToSpecificTime extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;
	String schedulePeriodToSelect;

	public SetTimeSchedulePeriodToSpecificTime(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {

		// if
		// (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped
		// Days")) {
		// flag = flag &
		// JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase,
		// "Grouped Days");
		// } else if
		// (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("SINGLE
		// DAY")) {
		// flag = flag &
		// JasperSchedulingUtils.selectIndividualDaysViewOrGroupedDaysView(testCase,
		// "SINGLE DAY");
		// }
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user edit \"(.+)\" period by changing time value to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		String tempDays = "";
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		Random rn = new Random();
		try {

			if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					schedulePeriodToSelect = exampleData.get(0) + "_Everyday";
				} else {
					schedulePeriodToSelect = "Everyday_" + exampleData.get(0);
				}
				inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Everyday");

			} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("SINGLE DAY")) {
				if (inputs.getInputValue(InputVariables.SCHEDULE_DAY_EDITED).isEmpty()
						|| inputs.getInputValue(InputVariables.SCHEDULE_DAY_EDITED) == null) {
					tempDays = days[rn.nextInt((6 - 0) + 1) + 0];
					inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, tempDays);
				} else {
					tempDays = inputs.getInputValue(InputVariables.SCHEDULE_DAY_EDITED);
				}

				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					schedulePeriodToSelect = exampleData.get(0) + "_" + tempDays;
				} else {
					schedulePeriodToSelect = tempDays + "_" + exampleData.get(0);
				}
			}

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}
			inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
			inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);

			if (statInfo.getThermostatType().equals("Jasper") || statInfo.getThermostatType().equals("HoneyBadger")) {
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

					if (jasperStatType.toUpperCase().contains("EMEA")) {
						if (exampleData.get(0).equalsIgnoreCase("1")) {
							inputs.setInputValue(InputVariables.EVERYDAY_1_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("2")) {
							inputs.setInputValue(InputVariables.EVERYDAY_2_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("3")) {
							inputs.setInputValue(InputVariables.EVERYDAY_3_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("4")) {
							inputs.setInputValue(InputVariables.EVERYDAY_4_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
					} else if (jasperStatType.toUpperCase().contains("NA")) {
						if (exampleData.get(0).equalsIgnoreCase("Wake")) {
							inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("Away")) {
							inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("Home")) {
							inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
						if (exampleData.get(0).equalsIgnoreCase("Sleep")) {
							inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME,
									exampleData.get(1).split("\\s+")[0] + ":" + exampleData.get(1).split("\\s+")[1]
											+ " " + exampleData.get(1).split("\\s+")[2]);
						}
					}
					flag = flag & JasperSchedulingEditUtils.editTimeBasedSchedule(testCase, inputs,
							schedulePeriodToSelect, 1);
				}
			}
		} catch (Exception e) {

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
		return flag;
	}
}
