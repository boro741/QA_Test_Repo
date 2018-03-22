package com.honeywell.keywords.jasper.scheduling.Edit;

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
import com.honeywell.jasper.utils.JasperSchedulingEditUtils;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;


public class EditScheduleByChangingTemperatureToMinMaxValues extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;
	ArrayList<String> arrlist = new ArrayList<String>(8);
	String[] schedulePeriods_NA = { "Wake", "Away", "Home", "Sleep" };
	String[] schedulePeriods_EMEA = { "1", "2", "3", "4" };
	String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	String schedulePeriodToSelect = "", jasperStatType = "";
	Random rn = new Random();

	public EditScheduleByChangingTemperatureToMinMaxValues(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user edit \"(.+)\" schedule by changing temperature to \"(.+)\" value$")
	public boolean keywordSteps() throws KeywordException {
		try {
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

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
				jasperStatType = statInfo.getJasperDeviceType();
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
			}
			HashMap<String, String> defaultValues = new HashMap<String, String>();
			inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());

			if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
				defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME,
						defaultValues.get("GeofenceSleepStartTime"));
				inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, defaultValues.get("GeofenceSleepEndTime"));
				inputs.setInputValue(InputVariables.EDIT_GEOFENCE_SCHEDULE, "true");
				flag = flag & JasperSchedulingEditUtils.editGeofenceScheduleWithMinMaxValues(testCase, inputs);

			} else if (exampleData.get(0).equalsIgnoreCase("Time")) {
				defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);

				if (jasperStatType.equalsIgnoreCase("NA")) {
					inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, defaultValues.get("EverydaySleepTime"));
				} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
					inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, defaultValues.get("EverydayWakeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, defaultValues.get("EverydayAwayTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, defaultValues.get("EverydayHomeTime").toLowerCase().replaceAll("^0*", ""));
					inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, defaultValues.get("EverydaySleepTime"));
				}

				do {
					if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0] + "_Everyday";
							} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
								schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0]
										+ "_Everyday";
							}
						} else {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								schedulePeriodToSelect = "Everyday_" + schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0];
							} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
								schedulePeriodToSelect = "Everyday_"
										+ schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0];
							}
						}

					} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								schedulePeriodToSelect = schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0] + "_"
										+ days[rn.nextInt((6 - 0) + 1) + 0];
							} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
								schedulePeriodToSelect = schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0] + "_"
										+ days[rn.nextInt((6 - 0) + 1) + 0];
							}
						} else {
							if (jasperStatType.equalsIgnoreCase("NA")) {
								schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 1) + 0] + "_"
										+ schedulePeriods_NA[rn.nextInt((3 - 0) + 1) + 0];
							} else if (jasperStatType.equalsIgnoreCase("EMEA")) {
								schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 1) + 0] + "_"
										+ schedulePeriods_EMEA[rn.nextInt((3 - 0) + 1) + 0];
							}
						}
					}
				} while (arrlist.contains(schedulePeriodToSelect));
				arrlist.add(schedulePeriodToSelect);

				flag = flag
						& JasperSchedulingEditUtils.editTimeBasedScheduleWithMinMaxValues(testCase, inputs, schedulePeriodToSelect);

				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
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