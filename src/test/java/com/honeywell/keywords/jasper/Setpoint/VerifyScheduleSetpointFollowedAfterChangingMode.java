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
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.screens.SensorSettingScreen;

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
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" setpoint value in solution card$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "RESPECTIVE PERIOD": 
				{
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					Double getPeriodSetpoint, currentStepperSetpoint ;
					currentStepperSetpoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
					getPeriodSetpoint = Double.parseDouble(statInfo.getCurrentSetPoints());
					if(getPeriodSetpoint.compareTo(currentStepperSetpoint) == 0 ){
						Keyword.ReportStep_Pass(testCase,
								"Stepper stepoint is following current schedule setpoint:" +getPeriodSetpoint);
					}else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Stepper stepoint is not following current schedule setpoint"+ "ScheduleSetpoint: "+getPeriodSetpoint + " StepperSetpoint: "+currentStepperSetpoint);
					}
					break;
				}
			case"DR":
			{
				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
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
