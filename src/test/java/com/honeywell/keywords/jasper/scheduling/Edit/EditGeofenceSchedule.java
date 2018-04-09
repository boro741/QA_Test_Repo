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
import com.honeywell.jasper.utils.JasperSchedulingEditUtils;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.utils.InputVariables;

public class EditGeofenceSchedule extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public EditGeofenceSchedule(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user edit \"(.+)\" schedule by editing \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
			List<String> allowedModes = statInfo.getAllowedModes();
			String homeHeatSetPoints = " ";
			String homeCoolSetPoints = " ";
			String awayHeatSetPoints = " ";
			String awayCoolSetPoints = " ";
			String sleepHeatSetPoints = " ";
			String sleepCoolSetPoints = " ";
			Double maxHeat = 0D, minHeat = 0D, maxCool = 0D, minCool = 0D;
			HashMap<String, String> defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
			maxHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxHeat"));
			minHeat = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinHeat"));
			if (jasperStatType.toUpperCase().contains("NA")) {
				maxCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MaxCool"));
				minCool = Double.parseDouble(statInfo.getDeviceMaxMinSetPoints().get("MinCool"));
			}

			if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
			}
			
			inputs.setInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE, "true");
			
			if (exampleData.get(1).equalsIgnoreCase("Home settings")) {
				if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					homeHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					homeCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, homeCoolSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
							defaultValues.get("GeofenceAwayCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, homeHeatSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
							defaultValues.get("GeofenceAwayHeatTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
								defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
								defaultValues.get("GeofenceSleepCoolTemp"));
					}
				} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					homeHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, homeHeatSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
							defaultValues.get("GeofenceAwayHeatTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
								defaultValues.get("GeofenceSleepHeatTemp"));
					}
				} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
					homeCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, homeCoolSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
							defaultValues.get("GeofenceAwayCoolTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
								defaultValues.get("GeofenceSleepCoolTemp"));
					}
				}
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, defaultValues.get("GeofenceSleepStartTime"));
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, defaultValues.get("GeofenceSleepEndTime"));
				
				flag = flag & JasperSchedulingEditUtils.editGeofenceSchedule(testCase, inputs, "Home");

			} else if (exampleData.get(1).equalsIgnoreCase("Sleep settings")) {
				if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
							defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
							defaultValues.get("GeofenceAwayCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
							defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
							defaultValues.get("GeofenceAwayHeatTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
								maxHeat, minHeat);
						sleepCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
								maxCool, minCool);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, sleepHeatSetPoints);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, sleepCoolSetPoints);
					}
				} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
							defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT,
							defaultValues.get("GeofenceAwayHeatTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						sleepHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
								maxHeat, minHeat);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, sleepHeatSetPoints);
					}
				} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
							defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT,
							defaultValues.get("GeofenceAwayCoolTemp"));
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						sleepCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs,
								maxCool, minCool);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, sleepCoolSetPoints);
					}
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
				flag = flag & JasperSchedulingEditUtils.editGeofenceSchedule(testCase, inputs, "Sleep");

			} else if (exampleData.get(1).equalsIgnoreCase("Away settings")) {
				if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					awayHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					awayCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
							defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, awayCoolSetPoints);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
							defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, awayHeatSetPoints);
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
								defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
								defaultValues.get("GeofenceSleepCoolTemp"));
					}
				} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					awayHeatSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxHeat,
							minHeat);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT,
							defaultValues.get("GeofenceHomeHeatTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, awayHeatSetPoints);
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT,
								defaultValues.get("GeofenceSleepHeatTemp"));
					}
				} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
					awayCoolSetPoints = JasperSchedulingUtils.getRandomSetPointValueBetweenMinandMax(testCase, inputs, maxCool,
							minCool);
					inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT,
							defaultValues.get("GeofenceHomeCoolTemp"));
					inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, awayCoolSetPoints);
					if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT,
								defaultValues.get("GeofenceSleepCoolTemp"));
					}
				}
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, defaultValues.get("GeofenceSleepStartTime"));
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, defaultValues.get("GeofenceSleepEndTime"));
				
				flag = flag & JasperSchedulingEditUtils.editGeofenceSchedule(testCase, inputs, "Away");

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