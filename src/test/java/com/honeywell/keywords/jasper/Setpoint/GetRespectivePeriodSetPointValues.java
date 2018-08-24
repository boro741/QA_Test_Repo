package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import java.util.HashMap;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.jasper.utils.JasperSetPoint;

public class GetRespectivePeriodSetPointValues extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public GetRespectivePeriodSetPointValues(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Change System Mode");
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify respective \"(.+)\" period setpoint values$")
	public boolean keywordSteps() {
		try {
			
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			
			String Mode = devInfo.getThermoStatMode();
			String currentScheduleType = devInfo.getThermoStatScheduleType();
			if(currentScheduleType.equalsIgnoreCase("Timed")){
				currentScheduleType="Time";
			}
			
			HashMap<String, String> defaultValues = new HashMap<String, String>();
			defaultValues = JasperSchedulingUtils.getDefaultScheduleValues(testCase, inputs, currentScheduleType);
			
			Double Setpointvalue = JasperSetPoint.getCurrentSetPointInDialer(testCase);
			String currentsetpoint = Setpointvalue.toString();
			String currentStepperSetpoint = "";
			
			if (currentScheduleType.equalsIgnoreCase("Time")) {
				switch (exampleData.get(0).toUpperCase()) {
				case "WAKE": 	{
						if(Mode.equalsIgnoreCase("Cool")){
							String WakeCoolSetpoint = "";
							String WakeCoolSetpoint1 = defaultValues.get("EverydayWakeCoolTemp");
								if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
								 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								 WakeCoolSetpoint = WakeCoolSetpoint1;
								}else{
								 WakeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, WakeCoolSetpoint1));
								 currentStepperSetpoint = currentsetpoint; 
								}
								flag = flag & currentStepperSetpoint.equals(WakeCoolSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																currentStepperSetpoint+"Period setpoint:+"+WakeCoolSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+WakeCoolSetpoint +
										" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}else {
							String WakeHeatSetpoint ="";
							String WakeHeatSetpoint1 = defaultValues.get("EverydayWakeHeatTemp");
							if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
								 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								 WakeHeatSetpoint = WakeHeatSetpoint1;
								}else{
									WakeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, WakeHeatSetpoint1));
									currentStepperSetpoint = currentsetpoint; 
								}
							flag = flag & currentStepperSetpoint.equals(WakeHeatSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
										currentStepperSetpoint+"Period setpoint:+"+WakeHeatSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+WakeHeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}
				}break;
				case "AWAY": 	{
							if(Mode.equalsIgnoreCase("Cool")){
								String AwayCoolSetpoint ="";
								 String AwayCoolSetpoint1 = defaultValues.get("EverydayAwayCoolTemp");
								 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
									 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
									 AwayCoolSetpoint = AwayCoolSetpoint1;
									}else{
										AwayCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayCoolSetpoint1));
										currentStepperSetpoint = currentsetpoint; 
									}
								flag = flag & currentStepperSetpoint.equals(AwayCoolSetpoint);
								if(flag)
									Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																	currentStepperSetpoint+"Period setpoint:+"+AwayCoolSetpoint );
								else 
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayCoolSetpoint +
											" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
							}else {
								String AwayHeatSetpoint ="";
								String AwayHeatSetpoint1 = defaultValues.get("EverydayAwayHeatTemp");
								if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
									 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
									 AwayHeatSetpoint = AwayHeatSetpoint1;
									}else{
										AwayHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayHeatSetpoint1));
										currentStepperSetpoint = currentsetpoint; 
									}
								flag = flag & currentStepperSetpoint.equals(AwayHeatSetpoint);
								if(flag)
									Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
											currentStepperSetpoint+"Period setpoint:+"+AwayHeatSetpoint );
								else 
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayHeatSetpoint +""
										+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
							}
					}break;
				case "SLEEP": 	{
							if(Mode.equalsIgnoreCase("Cool")){
								String SleepCoolSetpoint ="";
								 String SleepCoolSetpoint1 = defaultValues.get("EverydaySleepCoolTemp");
								 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
									 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
									 SleepCoolSetpoint =  SleepCoolSetpoint1;
									}else{
										SleepCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, SleepCoolSetpoint1));
										currentStepperSetpoint = currentsetpoint; 
									}
								flag = flag & currentStepperSetpoint.equals(SleepCoolSetpoint);
								if(flag)
									Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																	currentStepperSetpoint+"Period setpoint:+"+SleepCoolSetpoint );
								else 
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+SleepCoolSetpoint +
											" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
							}else {
								String SleepHeatSetpoint= "";
								String SleepHeatSetpoint1 = defaultValues.get("EverydaySleepHeatTemp");
								if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
									 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
									 SleepHeatSetpoint = SleepHeatSetpoint1;
									}else{
										SleepHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, SleepHeatSetpoint1));
										currentStepperSetpoint = currentsetpoint; 
									}
								flag = flag & currentStepperSetpoint.equals(SleepHeatSetpoint);
								if(flag)
									Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
											currentStepperSetpoint+"Period setpoint:+"+SleepHeatSetpoint );
								else 
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+SleepHeatSetpoint +""
											+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
							}
						}break;
				case "HOME": 	{
								if(Mode.equalsIgnoreCase("Cool")){
									String HomeCoolSetpoint ="";
									 String HomeCoolSetpoint1 = defaultValues.get("EverydaySleepCoolTemp");
									 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
										 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
										 HomeCoolSetpoint =  HomeCoolSetpoint1;
										}else{
											HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
											currentStepperSetpoint = currentsetpoint; 
										}
									flag = flag & currentStepperSetpoint.equals(HomeCoolSetpoint);
									if(flag)
										Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																		currentStepperSetpoint+"Period setpoint:+"+HomeCoolSetpoint );
									else 
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeCoolSetpoint +
												" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
								}else {
									String HomeHeatSetpoint = "";
									String HomeHeatSetpoint1 = defaultValues.get("EverydaySleepHeatTemp");
									if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
										 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
										 HomeHeatSetpoint = HomeHeatSetpoint1;
										}else{
											HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
											currentStepperSetpoint = currentsetpoint; 
										}
									flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
									if(flag)
										Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
												currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
									else 
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
												+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
								}
							}break;
				}
			}else if (currentScheduleType.equalsIgnoreCase("Geofence"))	{
				switch (exampleData.get(0).toUpperCase()) {
				case "AWAY":{
									if(Mode.equalsIgnoreCase("Cool")){
										String AwayCoolSetpoint ="";
										 String AwayCoolSetpoint1 = defaultValues.get("GeofenceAwayCoolTemp");
										 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
											 AwayCoolSetpoint = AwayCoolSetpoint1;
											}else{
												AwayCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayCoolSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(AwayCoolSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																			currentStepperSetpoint+"Period setpoint:+"+AwayCoolSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayCoolSetpoint +
													" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}else {
										String AwayHeatSetpoint ="";
										String AwayHeatSetpoint1 = defaultValues.get("GeofenceAwayHeatTemp");
										if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
											 AwayHeatSetpoint = AwayHeatSetpoint1; 
											}else{
												AwayHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayHeatSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(AwayHeatSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
													currentStepperSetpoint+"Period setpoint:+"+AwayHeatSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayHeatSetpoint +""
												+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}	
								}break;
				case "HOME": 	{
									if(Mode.equalsIgnoreCase("Cool")){
										String HomeCoolSetpoint = "";
										 String HomeCoolSetpoint1 = defaultValues.get("GeofenceHomeCoolTemp");
										 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", "");
											 HomeCoolSetpoint = HomeCoolSetpoint1;
											}else{
												HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(HomeCoolSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																			currentStepperSetpoint+"Period setpoint:+"+HomeCoolSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeCoolSetpoint +
													" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}else {
										String HomeHeatSetpoint = "";
										String HomeHeatSetpoint1 = defaultValues.get("GeofenceHomeHeatTemp");
										if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
											 HomeHeatSetpoint = HomeHeatSetpoint1;
											}else{
												HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
													currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
													+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}
								}break;
				case "SLEEP": 	{
									if(Mode.equalsIgnoreCase("Cool")){
										String HomeCoolSetpoint = "";
										 String HomeCoolSetpoint1 = defaultValues.get("GeofenceSleepCoolTemp");
										 if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
											 HomeCoolSetpoint = HomeCoolSetpoint1;
											}else{
												HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(HomeCoolSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
																			currentStepperSetpoint+"Period setpoint:+"+HomeCoolSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeCoolSetpoint +
													" and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}else {
										String HomeHeatSetpoint = "";
										String HomeHeatSetpoint1 = defaultValues.get("GeofenceSleepHeatTemp");
										if (devInfo.getThermostatUnits().contains("Fahrenheit")) {
											 currentStepperSetpoint = currentsetpoint.replace(".0", "");
											 HomeHeatSetpoint = HomeHeatSetpoint1;
											}else{
												HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
												currentStepperSetpoint = currentsetpoint; 
											}
										flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
										if(flag)
											Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
													currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
										else 
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
													+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
									}
								}break;
				}
				}
			} catch (Exception e){
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
						FailType.FUNCTIONAL_FAILURE,"Error Occured : " + e.getMessage());
			}
			return flag;
	}

	private String parseValue(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}

