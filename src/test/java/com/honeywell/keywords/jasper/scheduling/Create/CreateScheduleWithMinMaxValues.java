package com.honeywell.keywords.jasper.scheduling.Create;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.utils.InputVariables;

public class CreateScheduleWithMinMaxValues extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;

	public CreateScheduleWithMinMaxValues(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user creates \"(.+)\" schedule by setting temperature value to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String jasperStatType = statInfo.getJasperDeviceType();
		try
		{
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Cannot create schedule because thermostat is offline");
				return true;
			}

			if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
			} else if (exampleData.get(0).equalsIgnoreCase("Same Every Day")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
			} else if (exampleData.get(0).equalsIgnoreCase("Different On Weekdays")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
			}

			if (exampleData.get(1).equalsIgnoreCase("Above Maximum")) {
				inputs.setInputValue(InputVariables.SETPOINT_RANGE, InputVariables.ABOVE_MAXIMUM);
			} else if (exampleData.get(1).equalsIgnoreCase("Below Minimum")) {
				inputs.setInputValue(InputVariables.SETPOINT_RANGE, InputVariables.BELOW_MINIMUM);
			} else if (exampleData.get(1).equalsIgnoreCase("At Maximum")) {
				inputs.setInputValue(InputVariables.SETPOINT_RANGE, InputVariables.AT_MAXIMUM);
			} else if (exampleData.get(1).equalsIgnoreCase("At Minimum")) {
				inputs.setInputValue(InputVariables.SETPOINT_RANGE, InputVariables.AT_MINIMUM);
			} else if (exampleData.get(1).equalsIgnoreCase("Within range")) {
				inputs.setInputValue(InputVariables.SETPOINT_RANGE, InputVariables.IN_RANGE);
			}

			if (statInfo.getThermostatType().equals("Jasper")) {
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					HashMap<String, String> defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs,
							"Geofence");
					if (jasperStatType.equalsIgnoreCase("EMEA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
								defaultValues.get("GeofenceSleepStartTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
								defaultValues.get("GeofenceSleepEndTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					} else if (jasperStatType.equalsIgnoreCase("NA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
								defaultValues.get("GeofenceSleepStartTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
								defaultValues.get("GeofenceSleepEndTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					}
					flag = flag & JasperSchedulingUtils.createGeofenceBasedScheduleWithMinMaxSetPoints(testCase, inputs, true);
				} else {
					HashMap<String, String> defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
					inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, defaultValues.get("EverydaySleepTime").toLowerCase().replaceAll("^0*", ""));

					inputs.setInputValue(InputVariables.WEEKDAY_WAKE_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.WEEKDAY_AWAY_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.WEEKDAY_HOME_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_TIME, defaultValues.get("EverydaySleepTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.WEEKEND_WAKE_TIME, "8:00 AM");
					inputs.setInputValue(InputVariables.WEEKEND_AWAY_TIME, "10:00 AM");
					inputs.setInputValue(InputVariables.WEEKEND_HOME_TIME, "5:00 PM");
					inputs.setInputValue(InputVariables.WEEKEND_SLEEP_TIME, "11:00 PM");

					flag = flag & JasperSchedulingUtils.createTimeBasedScheduleWithMinMaxSetPoints(testCase, inputs);
				}
			}
		} 
		catch (Exception e) {
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
