package com.honeywell.keywords.jasper.scheduling.Create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.utils.InputVariables;

public class CreateDefaultScheduleWithSpecificPeriodEditTime extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;

	public CreateDefaultScheduleWithSpecificPeriodEditTime(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user creates \"(.+)\" schedule following specific \"(.+)\" time$")
	public boolean keywordSteps() throws KeywordException {
		try
		{
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Create Schedule : Cannot create schedule because thermostat is offline");
				return true;
			}
			if (exampleData.get(0).equalsIgnoreCase("Geofence based")) {
				if(exampleData.get(1).equalsIgnoreCase("With")){
						inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
						inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "Yes");
				}else
				{
					inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
					inputs.setInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER, "No");
				}
			} else if (exampleData.get(0).equalsIgnoreCase("Same Every Day")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
			} else if (exampleData.get(0).equalsIgnoreCase("Different On Weekdays")) {
				inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
				inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
			}

			if (statInfo.getThermostatType().equals("Jasper")) {
				HashMap<String, String> defaultValues;
				inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				
				String startTime = "";
				String endTime = "";
				String WWstartTime = "";
				String WWendTime = "";
	
				if(statInfo.getJasperDeviceType().equals("EMEA")){
					startTime = JasperSetPoint.CalculatePeriodStartEMEA(testCase);
					endTime = JasperSetPoint.CalculatePeriodEndEMEA(testCase, 2);
				}else {
					startTime = JasperSetPoint.CalculatePeriodStartNAHB(testCase);
					endTime = JasperSetPoint.CalculatePeriodEndNAHB(testCase, 2);
				}
				
				if (!startTime.contains("M") && !startTime.contains("m")) {
					Date tempStartTime, tempEndTime;
					SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
					SimpleDateFormat df12 = new SimpleDateFormat("hh:mm aa");
					String dateStringStartTime = startTime.replaceAll("\\.", "");
					String dateStringEndTime = endTime.replaceAll("\\.", "");
					try {
						tempStartTime = df24.parse(dateStringStartTime);
						startTime = df12.format(tempStartTime);
						tempEndTime = df24.parse(dateStringEndTime);
						endTime = df12.format(tempEndTime);
					} catch (ParseException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[ParseException] Error: " + e.getMessage());
					} catch (NumberFormatException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[NumberFormatException] Error: " + e.getMessage());
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[Exception] Error: " + e.getMessage());
					}
				}
				
				if(statInfo.getJasperDeviceType().equals("EMEA")){
					WWstartTime = JasperSetPoint.CalculateNextPeriodStartEMEA(testCase);
					WWendTime = JasperSetPoint.CalculateNextPeriodENDEMEA(testCase);
				}else {
					WWstartTime = JasperSetPoint.CalculateNextPeriodStartNAHB(testCase);
					WWendTime = JasperSetPoint.CalculatePeriodEndNAHB(testCase, 2);
				}
				if (!WWstartTime.contains("M") && !WWstartTime.contains("m")) {
					Date tempStartTime, tempEndTime;
					SimpleDateFormat df24 = new SimpleDateFormat("hh:mm");
					SimpleDateFormat df12 = new SimpleDateFormat("hh:mm aa");
					String dateStringStartTime = startTime.replaceAll("\\.", "");
					String dateStringEndTime = endTime.replaceAll("\\.", "");
					try {
						tempStartTime = df24.parse(dateStringStartTime);
						WWstartTime = df12.format(tempStartTime);
						tempEndTime = df24.parse(dateStringEndTime);
						WWendTime = df12.format(tempEndTime);
					} catch (ParseException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[ParseException] Error: " + e.getMessage());
					} catch (NumberFormatException e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[NumberFormatException] Error: " + e.getMessage());
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[Exception] Error: " + e.getMessage());
					}
				}
				
				if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
						.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Geofence");
					
			
					flag = flag & JasperSchedulingUtils.createGeofenceBasedwithsleepspeicfictime(testCase, inputs,startTime, endTime, true);
					
				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.EVERYDAY_SCHEDULE)) {
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");

					if (jasperStatType.toUpperCase().contains("EMEA")) {
					
						flag = flag & JasperSchedulingUtils.createSpecificPeriodtimebaseschedule(testCase, inputs, exampleData.get(1).toUpperCase(), startTime, endTime);
					} else if (jasperStatType.toUpperCase().contains("NA")) {
			
						flag = flag & JasperSchedulingUtils.createSpecificPeriodtimebaseschedule(testCase, inputs, exampleData.get(1).toUpperCase(), startTime, endTime);

				} else if (inputs.getInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE)
						.equalsIgnoreCase(InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE)) {
					defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, "Time");
					

					if (jasperStatType.toUpperCase().contains("EMEA")) {
					
						flag = flag & JasperSchedulingUtils.createSpecificPeriodtimebaseschedule(testCase, inputs, exampleData.get(1).toUpperCase(),  WWstartTime, WWendTime);
					} else if (jasperStatType.toUpperCase().contains("NA")) {

						flag = flag & JasperSchedulingUtils.createSpecificPeriodtimebaseschedule(testCase, inputs, exampleData.get(1).toUpperCase(), WWstartTime, WWendTime);
						}
					}
			
					}}
		}catch (Exception e) {
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
