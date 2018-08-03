package com.honeywell.keywords.jasper.Setpoint;

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

public class VerifyScheduleSetpointFollowedAfterChangingMode extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyScheduleSetpointFollowedAfterChangingMode(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user should be displayed with respective period setpoint value in solution card$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		Double getPeriodSetpoint, currentStepperSetpoint ;
		getPeriodSetpoint = Double.parseDouble(statInfo.getCurrentSetPoints());
		currentStepperSetpoint = JasperSetPoint.getCurrentSetPointInDialer(testCase);
		if(getPeriodSetpoint==currentStepperSetpoint){
			Keyword.ReportStep_Pass(testCase,
					"Stepper stepoint is following current schedule setpoint:" +getPeriodSetpoint);
		}else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Stepper stepoint is not following current schedule setpoint"+ "ScheduleSetpoint:"+getPeriodSetpoint + "StepperSetpoint:"+currentStepperSetpoint);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
