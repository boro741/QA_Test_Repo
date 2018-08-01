package com.honeywell.keywords.jasper.scheduling.Create;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.Dashboard;


public class CreateDefaultScheduleMultiStat extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	String locationName, deviceName;

	public CreateDefaultScheduleMultiStat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user creates default \"(.+)\" schedule value \"(.+)\" stats$")
	public boolean keywordSteps() throws KeywordException {
		try{
			locationName = inputs.getInputValue("LOCATION2_NAME");
			deviceName = inputs.getInputValue("LOCATION2_DEVICE1_NAME");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			Dashboard dashBoardScreen=  new Dashboard(testCase);
			dashBoardScreen.selectLocationFromDashBoard(testCase, inputs.getInputValue("LOCATION2_NAME"));
			inputs.setInputValue("LOCATION1_NAME", inputs.getInputValue("LOCATION2_NAME"));
			inputs.setInputValue("LOCATION1_DEVICE1_NAME", inputs.getInputValue("LOCATION2_DEVICE1_NAME"));
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			System.out.println(inputs.getInputValue("LOCATION1_NAME"));
			System.out.println(inputs.getInputValue("LOCATION2_DEVICE1_NAME"));
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			for (int i = 1; i <=locInfo.getDeviceCountOfLocation(); i++) {
				String str = inputs.getInputValue("LOCATION2_DEVICE" + i + "_NAME");
				if (str != null && !str.isEmpty()) {
					System.out.println(" deleting schedule for " + str);
					inputs.setInputValue("LOCATION1_DEVICE1_NAME", str);
					statInfo = new DeviceInformation(testCase, inputs);
					String deviceID = statInfo.getDeviceID();
					try {
						if (chUtil.getConnection()) {

							if (chUtil.deleteSchedule(chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")),
									deviceID) == 200) {
								Keyword.ReportStep_Pass(testCase,
										"Create Schedule Using CHIL : Successfully deleted schedule through CHIL");
							} else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule Using CHIL : Failed to delete schedule using CHIL");
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
				} else {
					break;
				}

			}
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, inputs.getInputValue("LOCATION2_DEVICE1_NAME"));
			String jasperStatType = statInfo.getJasperDeviceType();

			if (exampleData.get(0).equalsIgnoreCase("Geofence")||exampleData.get(0).equalsIgnoreCase("Geofence based")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
			} else if (exampleData.get(0).equalsIgnoreCase("Same Every Day")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
			} else if (exampleData.get(0).equalsIgnoreCase("Different On Weekdays")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
			}

			if (exampleData.get(1).equalsIgnoreCase("without copying schedule to other")) {
				inputs.setInputValue(InputVariables.SKIP_COPYING, "Yes");
			} else if (exampleData.get(1).equalsIgnoreCase("by copying schedule to all")) {
				inputs.setInputValue(InputVariables.ALL_STAT_COPYING, "Yes");
			} else if (exampleData.get(1).equalsIgnoreCase("by copying schedule to selected")) {
				inputs.setInputValue(InputVariables.SPECIFIC_STAT_COPYING, "Yes");
			}
			if (statInfo.getJasperDeviceType().equals("Jasper") || statInfo.getJasperDeviceType().equals("EMEA")) {
				List<String> allowedModes = statInfo.getAllowedModes();
				HashMap<String, String> defaultValues;
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				String temp = " ";

				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
					if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("GeofenceHomeCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceHomeHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceAwayCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceAwayHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceSleepCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);

					} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("GeofenceHomeHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceAwayHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceSleepHeatTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT, temp);
					} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
						temp = parseValue(defaultValues.get("GeofenceHomeCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceAwayCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT, temp);
						temp = parseValue(defaultValues.get("GeofenceSleepCoolTemp"));
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT, temp);
					}

					if (jasperStatType.toUpperCase().contains("EMEA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("GeofenceSleepStartTime"), true, 1,
								10);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime);
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("GeofenceSleepEndTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime);
					} else if (jasperStatType.toUpperCase().contains("NA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("GeofenceSleepStartTime"), true, 1,
								15);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME, changedTime);
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("GeofenceSleepEndTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME, changedTime);
					}
					flag = flag & JasperSchedulingUtils.createGeofenceBasedSchedule(testCase, inputs,true);
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
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
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayWakeTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.EVERYDAY_1_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayAwayTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.EVERYDAY_2_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayHomeTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.EVERYDAY_3_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydaySleepTime"), true, 1, 10);
						inputs.setInputValue(InputVariables.EVERYDAY_4_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					} else if (jasperStatType.toUpperCase().contains("NA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayWakeTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayAwayTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydayHomeTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("EverydaySleepTime"), true, 1, 15);
						inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					}
					flag = flag & JasperSchedulingUtils.createTimeBasedSchedule(testCase, inputs);

				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
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

					if (jasperStatType.toUpperCase().contains("EMEA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayWakeTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKDAY_1_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayAwayTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKDAY_2_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayHomeTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKDAY_3_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdaySleepTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKDAY_4_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendWakeTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKEND_1_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendAwayTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKEND_2_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendHomeTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKEND_3_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendSleepTime"), true, 0, 10);
						inputs.setInputValue(InputVariables.WEEKEND_4_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					} else if (jasperStatType.toUpperCase().contains("NA")) {
						String changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayWakeTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKDAY_WAKE_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayAwayTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKDAY_AWAY_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdayHomeTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKDAY_HOME_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekdaySleepTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKDAY_SLEEP_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendWakeTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKEND_WAKE_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendAwayTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKEND_AWAY_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendHomeTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKEND_HOME_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
						changedTime = JasperSchedulingUtils.addHoursAndMinutesToTime(testCase,defaultValues.get("WeekendSleepTime"), true, 0, 15);
						inputs.setInputValue(InputVariables.WEEKEND_SLEEP_TIME, changedTime.toLowerCase().replaceAll("^0*", ""));
					}
					flag = flag & JasperSchedulingUtils.createTimeBasedSchedule(testCase, inputs);
				}
			}
		} 
		catch (Exception e) {
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
		inputs.setInputValue("LOCATION1_NAME", locationName);
		inputs.setInputValue("LOCATION1_DEVICE1_NAME", deviceName);
		return flag;
	}
}
