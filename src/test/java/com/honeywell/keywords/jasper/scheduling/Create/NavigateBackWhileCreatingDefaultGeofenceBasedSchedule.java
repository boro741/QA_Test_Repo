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


public class NavigateBackWhileCreatingDefaultGeofenceBasedSchedule extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public NavigateBackWhileCreatingDefaultGeofenceBasedSchedule(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user navigates back while creating \"(.+)\" scheduling with default values \"(.+)\" sleep settings$")
	public boolean keywordSteps() throws KeywordException {
		try
		{
			HashMap<String, String> defaultValues = new HashMap<String, String>();
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Cannot create schedule because thermostat is offline");
				return true;
			}
			String temp = " ";
			if (exampleData.get(0).equalsIgnoreCase("Geofence based")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
			}

			if (exampleData.get(1).equalsIgnoreCase("With")) {
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
			} else if (exampleData.get(1).equalsIgnoreCase("Without")) {
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "No");
			}
			String sleepHeatSetPoints = " ";
			
			if (statInfo.getThermostatType().equals("Jasper")) {
				Double minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
				Double maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
				String JasperStatType = statInfo.getJasperDeviceType();
				defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, JasperStatType);
				if (inputs.getInputValue(InputVariables.JASPER_STAT_TYPE).equalsIgnoreCase("EMEA")) {
					sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, sleepHeatSetPoints);
					sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, sleepHeatSetPoints);
					sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, sleepHeatSetPoints);
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						temp = parseValue(defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME,
								defaultValues.get("GeofenceSleepStartTime"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME,
								defaultValues.get("GeofenceSleepEndTime"));
					}
				} else {
					temp = parseValue(defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);
					temp = parseValue(defaultValues.get("GeofenceAwayHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						temp = parseValue(defaultValues.get("GeofenceSleepCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME,
								defaultValues.get("GeofenceSleepStartTime"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME,
								defaultValues.get("GeofenceSleepEndTime"));
					}
				}
				inputs.setInputValueWithoutTarget("NaviagateBackAtSleep", "true");
				flag = flag & JasperSchedulingUtils.createGeofenceBasedSchedule(testCase, inputs,true);
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

