package com.honeywell.keywords.jasper.scheduling.Create;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class CreateDefaultGeofenceBasedScheduleWithEditSleep extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;

	public CreateDefaultGeofenceBasedScheduleWithEditSleep(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user creates \"(.+)\" schedule using \"(.+)\" option by editing sleep setting values and default home and away values$")
	public boolean keywordSteps() throws KeywordException {
		boolean selectUseGeofence = true;
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Cannot create schedule because thermostat is offline");
				return true;
			}
			if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
			}

			if (exampleData.get(1).equalsIgnoreCase("Learn more")) {
				selectUseGeofence = false;
			} else if (exampleData.get(1).equalsIgnoreCase("Use Geofence")) {
				selectUseGeofence = true;
			}

			if (statInfo.getThermostatType().equals("Jasper")) {
				List<String> allowedModes = statInfo.getAllowedModes();
				String sleepHeatSetPoints = " ";
				String sleepCoolSetPoints = " ";
				HashMap<String, String> defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				String temp = " ";

				if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
					Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
					Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
					Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
					temp = parseValue(defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
					sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					sleepCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, sleepCoolSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, sleepHeatSetPoints);

				} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
					Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));

					temp = parseValue(defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
					sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, sleepHeatSetPoints);
				} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
					Double maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
					Double minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
					temp = parseValue(defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);
					sleepCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, sleepCoolSetPoints);
				}

				if (jasperStatType.equalsIgnoreCase("EMEA")) {
					String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
							defaultValues.get("GeofenceSleepStartTime"), true, 1, 10);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime);
					changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, defaultValues.get("GeofenceSleepEndTime"),
							true, 1, 10);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime);
				} else if (jasperStatType.equalsIgnoreCase("NA")) {
					String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,
							defaultValues.get("GeofenceSleepStartTime"), true, 1, 15);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime);
					changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase, defaultValues.get("GeofenceSleepEndTime"),
							true, 1, 15);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime);
				}
				flag = flag & JasperSchedulingUtils.createGeofenceBasedSchedule(testCase, inputs, selectUseGeofence);
			}
		}
		catch(Exception e)
		{
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
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

