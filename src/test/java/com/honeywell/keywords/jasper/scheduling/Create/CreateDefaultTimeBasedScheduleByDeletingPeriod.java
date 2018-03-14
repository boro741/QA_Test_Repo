package com.honeywell.keywords.jasper.scheduling.Create;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.utils.InputVariables;

public class CreateDefaultTimeBasedScheduleByDeletingPeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public CreateDefaultTimeBasedScheduleByDeletingPeriod(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user creates \"(.+)\" schedule by deleting period from the default schedule values$")
	public boolean keywordSteps() throws KeywordException {
		try 
		{
			HashMap<String, String> defaultValues = new HashMap<String, String>();
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			String temp = " ";
			if (exampleData.get(0).equalsIgnoreCase("Everyday")) {
				defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("EverydayWakeCoolTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_1_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("EverydayWakeHeatTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_1_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("EverydayAwayCoolTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_2_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("EverydayAwayHeatTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_2_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("EverydayHomeCoolTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_3_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("EverydayHomeHeatTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_3_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, defaultValues.get("EverydaySleepTime"));
				inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, defaultValues.get("EverydaySleepTime"));
				temp = parseValue(defaultValues.get("EverydaySleepCoolTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_4_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("EverydaySleepHeatTemp"));
				inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.EVERYDAY_4_HEAT_SETPOINT, temp);
				if (jasperStatType.equalsIgnoreCase("EMEA")) {
					Random rn = new Random();
					String periodToDelete = String.valueOf(rn.nextInt((4 - 1) + 1) + 1);
					inputs.setInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE, periodToDelete);
					inputs.setInputValue(InputVariables.DELETE_PERIOD, "Yes");
				}
				flag = flag & JasperSchedulingUtils.createTimeBasedScheduleWithDefaultValues(testCase, inputs);
			} else if (exampleData.get(0).equalsIgnoreCase("Weekday and Weekend")) {
				defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				inputs.setInputValue(InputVariables.WEEKDAY_1_TIME, defaultValues.get("WeekdayWakeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKDAY_WAKE_TIME, defaultValues.get("WeekdayWakeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekdayWakeCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_1_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_WAKE_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekdayWakeHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_1_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_WAKE_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, defaultValues.get("WeekdayAwayTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKDAY_AWAY_TIME, defaultValues.get("WeekdayAwayTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekdayAwayCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_2_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_AWAY_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekdayAwayHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_2_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_AWAY_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, defaultValues.get("WeekdayHomeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKDAY_HOME_TIME, defaultValues.get("WeekdayHomeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekdayHomeCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_3_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_HOME_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekdayHomeHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_3_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_HOME_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_4_TIME, defaultValues.get("WeekdaySleepTime"));
				inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_TIME, defaultValues.get("WeekdaySleepTime"));
				temp = parseValue(defaultValues.get("WeekdaySleepCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_4_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekdaySleepHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKDAY_4_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_HEAT_SETPOINT, temp);

				inputs.setInputValue(InputVariables.WEEKEND_1_TIME, defaultValues.get("WeekendWakeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKEND_WAKE_TIME, defaultValues.get("WeekendWakeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekendWakeCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_1_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_WAKE_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekendWakeHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_1_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_WAKE_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_2_TIME, defaultValues.get("WeekendAwayTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKEND_AWAY_TIME, defaultValues.get("WeekendAwayTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekendAwayCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_2_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_AWAY_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekendAwayHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_2_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_AWAY_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_3_TIME, defaultValues.get("WeekendHomeTime").toLowerCase().replaceAll("^0*", ""));
				inputs.setInputValue(InputVariables.WEEKEND_HOME_TIME, defaultValues.get("WeekendHomeTime").toLowerCase().replaceAll("^0*", ""));
				temp = parseValue(defaultValues.get("WeekendHomeCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_3_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_HOME_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekendHomeHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_3_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_HOME_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_4_TIME, defaultValues.get("WeekendSleepTime"));
				inputs.setInputValue(InputVariables.WEEKEND_SLEEP_TIME, defaultValues.get("WeekendSleepTime"));
				temp = parseValue(defaultValues.get("WeekendSleepCoolTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_4_COOL_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_SLEEP_COOL_SETPOINT, temp);
				temp = parseValue(defaultValues.get("WeekendSleepHeatTemp"));
				inputs.setInputValue(InputVariables.WEEKEND_4_HEAT_SETPOINT, temp);
				inputs.setInputValue(InputVariables.WEEKEND_SLEEP_HEAT_SETPOINT, temp);
				if (jasperStatType.equalsIgnoreCase("EMEA")) {
					Random rn = new Random();
					System.out.println("Random" + rn);
					System.out.println("Random number " + rn.nextInt((8 - 1) + 1) + 1);
					String periodToDelete = String.valueOf(rn.nextInt((8 - 1) + 1) + 1);
					System.out.println("Period to delete " + periodToDelete);
					inputs.setInputValue(InputVariables.PERIOD_NUMBER_TO_DELETE, periodToDelete);
					inputs.setInputValue(InputVariables.DELETE_PERIOD, "Yes");
				}
				flag = flag & JasperSchedulingUtils.createTimeBasedScheduleWithDefaultValues(testCase, inputs);
			}
		}
		catch(Exception e)
		{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
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
}
