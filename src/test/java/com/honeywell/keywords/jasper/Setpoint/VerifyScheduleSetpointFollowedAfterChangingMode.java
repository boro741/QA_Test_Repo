package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

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
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.PrimaryCard;

public class VerifyScheduleSetpointFollowedAfterChangingMode extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;

	public VerifyScheduleSetpointFollowedAfterChangingMode(TestCases testCase, TestCaseInputs inputs,ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^the user should be displayed with (.*) setpoint value$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		flag = flag & statInfo.SyncDeviceInfo(testCase, inputs);
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "RESPECTIVE PERIOD": 
			{
				flag = flag & DashboardUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
				
				
				String getPeriodSetpointString;
				Double getPeriodSetpoint = 0.0, currentStepperSetpoint = 0.0 ;
				if (flag){
					currentStepperSetpoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					getPeriodSetpoint = Double.parseDouble(statInfo.getCurrentSetPoints());
					getPeriodSetpointString=getPeriodSetpoint.toString();
					/*String statUnit=statInfo.getThermostatUnits();
					if (statUnit.equalsIgnoreCase("Fahrenheit")) {

					}else if (statUnit.equalsIgnoreCase("celsius")) {
						ReportStep_Pass(testCase, "setpoint value from chil is "+getPeriodSetpointString);
					}else{
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+statUnit);
					}*/

					if(getPeriodSetpointString.equals(currentStepperSetpoint.toString())){
						Keyword.ReportStep_Pass(testCase,
								"Stepper stepoint is following current schedule setpoint:" +getPeriodSetpoint);
					}else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Stepper stepoint is not following current schedule setpoint"+ "ScheduleSetpoint: "+getPeriodSetpoint + " StepperSetpoint: "+currentStepperSetpoint);
					}
				}else
				{
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to fetch the stat info");
				}
				break;
			}
			case"DR":
			{
				Double getDRSetpoint, currentStepperSetpoint ;
				getDRSetpoint = Double.parseDouble(statInfo.getCurrentSetPoints());
				currentStepperSetpoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
				if(getDRSetpoint-currentStepperSetpoint==0.0){
					Keyword.ReportStep_Pass(testCase,
							"Stepper stepoint is following DR setpoint:" +getDRSetpoint);
				}else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Stepper stepoint is not following DR Setpoint "+ getDRSetpoint + "StepperSetpoint:"+currentStepperSetpoint);
				}
				break;
			}
			case "OVERRIDE SETPOINT":
			{
				if (flag){
					String Overridesetpoint = "", currentStepperSetpoint = "";
					Double currentStepperSetpoint1 = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					String Overridesetpointvalue1 = statInfo.getOverrrideSetpoint();
					String statUnit=statInfo.getThermostatUnits();
					if(statUnit.equalsIgnoreCase("Fahrenheit")) {
						currentStepperSetpoint = currentStepperSetpoint1.toString().replace(".0", ""); 
						Overridesetpoint = Overridesetpointvalue1.replace(".0", "");
					}else if(statUnit.equalsIgnoreCase("Celsius")) {
						Overridesetpoint = JasperSchedulingUtils.roundOffCelsiusData(testCase,JasperSchedulingUtils.convertFromFahrenhietToCelsius(testCase, Overridesetpointvalue1));
						currentStepperSetpoint = currentStepperSetpoint1.toString(); 
					}else{
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Stat unit not received"+statInfo.getThermostatUnits());
					}
					if(Overridesetpoint.equals(currentStepperSetpoint)){
						Keyword.ReportStep_Pass(testCase,
								"Stepper stepoint is following current schedule setpoint:" +Overridesetpoint);
					}else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Stepper stepoint is not following current schedule setpoint"+ "ScheduleSetpoint: "+Overridesetpoint + " StepperSetpoint: "+currentStepperSetpoint);
					}
				}
				else
				{
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Unable to fetch the stat info");
				}

				break;
			}
			case"HEATING TO":
			{
				String HeatingToValue = "";
				Double currentStepperSetpoint1 = JasperSetPoint.getCurrentSetPointInDialer(testCase);
				String currentStepperSetpoint = currentStepperSetpoint1.toString();
				PrimaryCard PCS = new PrimaryCard(testCase);
				String HeatingToValue1 = PCS.getCurrentHeatingOrCoolingSetpointValue();
				String Value = HeatingToValue1.replace("HEATING TO ", "");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					HeatingToValue = Value.replace("°", "");}
				else{
					HeatingToValue = Value.replace("˚", "");
				}
				if(HeatingToValue.equals(currentStepperSetpoint)){
					Keyword.ReportStep_Pass(testCase,
							"Stepper stepoint is following current Heating to setpoint:" +HeatingToValue);
				}else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Stepper stepoint is not following current heating to setpoint" +HeatingToValue + " StepperSetpoint: "+currentStepperSetpoint);
				}
				break;
			}
			case"COOLING TO":
			{
				String CoolingToValue = "";
				Double currentStepperSetpoint1 = JasperSetPoint.getCurrentSetPointInDialer(testCase);
				String currentStepperSetpoint = currentStepperSetpoint1.toString();
				PrimaryCard PCS = new PrimaryCard(testCase);
				String CoolingToValue1 = PCS.getCurrentHeatingOrCoolingSetpointValue();
				String Value = CoolingToValue1.replace("COOLING TO ", "");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					CoolingToValue = Value.replace("°", "");
				}
				else{
					CoolingToValue = Value.replace("˚", "");
				}
				if(CoolingToValue.equals(currentStepperSetpoint)){
					Keyword.ReportStep_Pass(testCase,
							"Stepper stepoint is following current cooling to setpoint:" +CoolingToValue);
				}else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Stepper stepoint is not following current cooling to setpoint" +CoolingToValue + " StepperSetpoint: "+currentStepperSetpoint);
				}
				break;
			}

			}
		}
		catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}



	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
