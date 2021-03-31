package com.honeywell.keywords.jasper.scheduling.Edit;

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


public class EditGeofenceScheduleByAddingOrDeletingSleep extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public EditGeofenceScheduleByAddingOrDeletingSleep(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user edits (.*) schedule by (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try 
		{
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}
			String jasperStatType = statInfo.getJasperDeviceType();
			List<String> allowedModes = statInfo.getAllowedModes();
			HashMap<String, String> defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
			inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
			inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
			String temp = " ";
			Double d;
			if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				d = Double.parseDouble(defaultValues.get("GeofenceHomeHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceHomeCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceSleepHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceSleepCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceAwayHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceAwayCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);

			} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
				d = Double.parseDouble(defaultValues.get("GeofenceHomeCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceSleepCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceAwayCoolTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);

			} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
				d = Double.parseDouble(defaultValues.get("GeofenceHomeHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceSleepHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);
				d = Double.parseDouble(defaultValues.get("GeofenceAwayHeatTemp"));
				temp = String.valueOf(d);
				inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
			}
			inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, defaultValues.get("GeofenceSleepStartTime"));
			inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, defaultValues.get("GeofenceSleepEndTime"));

			if (exampleData.get(0).equalsIgnoreCase("geofence")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
			}
			inputs.setInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE, "true");

			if (exampleData.get(1).equalsIgnoreCase("Deleting")) {
				flag = flag & JasperSchedulingUtils.addOrDeleteSleepSettings(testCase, inputs, false);
			} else if (exampleData.get(1).equalsIgnoreCase("Adding")) {
				flag = flag & JasperSchedulingUtils.addOrDeleteSleepSettings(testCase, inputs, true);
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Inputs not handled");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}