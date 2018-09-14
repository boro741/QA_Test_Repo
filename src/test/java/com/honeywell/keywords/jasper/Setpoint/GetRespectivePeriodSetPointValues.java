package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.concurrent.TimeUnit;

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

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public GetRespectivePeriodSetPointValues(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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

			Double Setpointvalue = null ;
			String currentsetpoint = "";
			String currentStepperSetpoint = "";
			String jasperStatType = devInfo.getJasperDeviceType();

			flag = flag & devInfo.SyncDeviceInfo(testCase, inputs);
			if (flag) {
				Setpointvalue = JasperSetPoint.getCurrentSetPointInDialer(testCase);

			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"geofence Temporary Hold status not displayed" + Setpointvalue);
			}
			currentsetpoint = Setpointvalue.toString();
			TimeUnit.SECONDS.sleep(5);

			if (currentScheduleType.equalsIgnoreCase("Time")) {
				if (jasperStatType.equalsIgnoreCase("NA")) {
					switch (exampleData.get(0).toUpperCase()) {
					case "WAKE": 	{
						if(Mode.equalsIgnoreCase("Cool")){
							String WakeCoolSetpoint = "";
							String WakeCoolSetpoint1 = defaultValues.get("EverydayWakeCoolTemp");
							String statUnit=devInfo.getThermostatUnits();
							if (statUnit.equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								WakeCoolSetpoint = WakeCoolSetpoint1.replace(".0", "");
							}else if (statUnit.equalsIgnoreCase("celsius")) {
								WakeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, WakeCoolSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+statUnit);
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
							String statUnit=devInfo.getThermostatUnits();
							if (statUnit.equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								WakeHeatSetpoint = WakeHeatSetpoint1.replace(".0", ""); 
							}else if (statUnit.equalsIgnoreCase("celsius")) {
								WakeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, WakeHeatSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}
							else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+statUnit);
							}
							flag = flag & currentStepperSetpoint.equals(WakeHeatSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
										currentStepperSetpoint+"Period setpoint:+"+WakeHeatSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+WakeHeatSetpoint +""
										+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}
						break;
					}
					case "AWAY": 	{
						if(Mode.equalsIgnoreCase("Cool")){
							String AwayCoolSetpoint ="";
							String AwayCoolSetpoint1 = defaultValues.get("EverydayAwayCoolTemp");
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", "");
								AwayCoolSetpoint = AwayCoolSetpoint1.replace(".0", ""); 
							}else if (devInfo.getThermostatUnits().equalsIgnoreCase("celsius")) {
								AwayCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayCoolSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
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
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								AwayHeatSetpoint = AwayHeatSetpoint1.replace(".0", "");
							}else if (devInfo.getThermostatUnits().equalsIgnoreCase("celsius")) {
								AwayHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayHeatSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
							}
							flag = flag & currentStepperSetpoint.equals(AwayHeatSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
										currentStepperSetpoint+"Period setpoint:+"+AwayHeatSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayHeatSetpoint +""
										+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}
						break;
					}
					case "SLEEP": 	{
						if(Mode.equalsIgnoreCase("Cool")){
							String SleepCoolSetpoint ="";
							String SleepCoolSetpoint1 = defaultValues.get("EverydaySleepCoolTemp");
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								SleepCoolSetpoint =  SleepCoolSetpoint1.replace(".0", "");
							}else if (devInfo.getThermostatUnits().equalsIgnoreCase("celsius")) {
								SleepCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, SleepCoolSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
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
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								SleepHeatSetpoint = SleepHeatSetpoint1.replace(".0", "");
							}else if (devInfo.getThermostatUnits().equalsIgnoreCase("celsius")) {
								SleepHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, SleepHeatSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
							}
							flag = flag & currentStepperSetpoint.equals(SleepHeatSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
										currentStepperSetpoint+"Period setpoint:+"+SleepHeatSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+SleepHeatSetpoint +""
										+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}
						break;
					}
					case "HOME": 	{
						if(Mode.equalsIgnoreCase("Cool")){
							String HomeCoolSetpoint ="";
							String HomeCoolSetpoint1 = defaultValues.get("EverydayHomeCoolTemp");
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								HomeCoolSetpoint =  HomeCoolSetpoint1.replace(".0", "");
							}else if(devInfo.getThermostatUnits().equalsIgnoreCase("Celsius")){
								HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
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
							String HomeHeatSetpoint1 = defaultValues.get("EverydayHomeHeatTemp");
							if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
								currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
								HomeHeatSetpoint = HomeHeatSetpoint1.replace(".0", "");
							} else if(devInfo.getThermostatUnits().equalsIgnoreCase("celsius")){
								HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
								currentStepperSetpoint = currentsetpoint; 
							}else{
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
							}
							flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
							if(flag)
								Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
										currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
							else 
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
										+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						}
						break;
					}
					}
				}
				else{

					switch (exampleData.get(0).toUpperCase()) {
					case "P1":     {
						String P1HeatSetpoint ="";
						String P1HeatSetpoint1 = defaultValues.get("EverydayWakeHeatTemp");
						if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							P1HeatSetpoint = P1HeatSetpoint1.replace(".0", "");
						}else if(devInfo.getThermostatUnits().equalsIgnoreCase("celsius")){
							P1HeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,P1HeatSetpoint1);
							currentStepperSetpoint = currentsetpoint;
						}else{
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
						}
						flag = flag & currentStepperSetpoint.equals(P1HeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+P1HeatSetpoint );
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+P1HeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						break;
					}
					case "P2":     {
						String P2HeatSetpoint ="";
						String P2HeatSetpoint1 = defaultValues.get("EverydayAwayHeatTemp");
						if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							P2HeatSetpoint = P2HeatSetpoint1.replace(".0", "");
						}else if(devInfo.getThermostatUnits().equalsIgnoreCase("celsius")){
							P2HeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,P2HeatSetpoint1);
							currentStepperSetpoint = currentsetpoint;
						}else{
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
						}
						flag = flag & currentStepperSetpoint.equals(P2HeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+P2HeatSetpoint );
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+P2HeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						break;
					}
					case "P4":     {
						String P4HeatSetpoint= "";
						String P4HeatSetpoint1 = defaultValues.get("EverydaySleepHeatTemp");
						if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							P4HeatSetpoint = P4HeatSetpoint1.replace(".0", "");
						}else if(devInfo.getThermostatUnits().equalsIgnoreCase("celsius")){
							P4HeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,P4HeatSetpoint1);
							currentStepperSetpoint = currentsetpoint;
						}else{
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
						}
						flag = flag & currentStepperSetpoint.equals(P4HeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+P4HeatSetpoint );
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+P4HeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						break;
					}
					case "P3":     {
						String P3HeatSetpoint = "";
						String P3HeatSetpoint1 = defaultValues.get("EverydayHomeHeatTemp");
						if (devInfo.getThermostatUnits().equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							P3HeatSetpoint = P3HeatSetpoint1.replace(".0", "");
						}else if(devInfo.getThermostatUnits().equalsIgnoreCase("celsius")){
							P3HeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,P3HeatSetpoint1);
							currentStepperSetpoint = currentsetpoint;
						}else{
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+devInfo.getThermostatUnits());
						}
						flag = flag & currentStepperSetpoint.equals(P3HeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+P3HeatSetpoint );
						else
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+P3HeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
						break;
					}
					}
				}	

			}

			else if (currentScheduleType.equalsIgnoreCase("Geofence"))	{
				String statUnit=devInfo.getThermostatUnits();
				switch (exampleData.get(0).toUpperCase()) {
				case "AWAY":{
					if(Mode.equalsIgnoreCase("Cool")){
						String AwayCoolSetpoint ="";
						String AwayCoolSetpoint1 = defaultValues.get("GeofenceAwayCoolTemp");
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
							AwayCoolSetpoint = AwayCoolSetpoint1.replace(".0", "");
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							AwayCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayCoolSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
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
						
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
							AwayHeatSetpoint = AwayHeatSetpoint1.replace(".0", ""); 
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							AwayHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, AwayHeatSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
						}
						flag = flag & currentStepperSetpoint.equals(AwayHeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+AwayHeatSetpoint );
						else 
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+AwayHeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
					}
					break;
				}
				case "HOME": 	{
					if(Mode.equalsIgnoreCase("Cool")){
						String HomeCoolSetpoint = "";
						String HomeCoolSetpoint1 = defaultValues.get("GeofenceHomeCoolTemp");
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							HomeCoolSetpoint = HomeCoolSetpoint1.replace(".0", "");
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
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
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
							HomeHeatSetpoint = HomeHeatSetpoint1.replace(".0", "");
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
						}
						flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
						else 
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
					}
					break;
				}
				case "SLEEP": 	{
					if(Mode.equalsIgnoreCase("Cool")){
						String HomeCoolSetpoint = "";
						String HomeCoolSetpoint1 = defaultValues.get("GeofenceSleepCoolTemp");
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", ""); 
							HomeCoolSetpoint = HomeCoolSetpoint1.replace(".0", "");
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							HomeCoolSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeCoolSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
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
						if (statUnit.equalsIgnoreCase("Fahrenheit")) {
							currentStepperSetpoint = currentsetpoint.replace(".0", "");
							HomeHeatSetpoint = HomeHeatSetpoint1.replace(".0", "");
						}else if (statUnit.equalsIgnoreCase("Celsius")){
							HomeHeatSetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, HomeHeatSetpoint1));
							currentStepperSetpoint = currentsetpoint; 
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat is not in expected unit:"+ statUnit);
						}
						flag = flag & currentStepperSetpoint.equals(HomeHeatSetpoint);
						if(flag)
							Keyword.ReportStep_Pass(testCase, "Displayed current period setpint, Current period setpoint:"+
									currentStepperSetpoint+"Period setpoint:+"+HomeHeatSetpoint );
						else 
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Setpoint of current period:"+HomeHeatSetpoint +""
									+ " and primary card setpoint value:" +currentStepperSetpoint+ " not matched");
					}
					break;
				}
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