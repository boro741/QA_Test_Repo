package com.honeywell.keywords.jasper.Setpoint;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.utils.InputVariables;


public class VerifySetPointsOverriddenOnDialer extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	
	public VerifySetPointsOverriddenOnDialer(TestCases testCase, TestCaseInputs inputs) {
		this.testCase=testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify the schedule temperature is overridden with set temperature$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo=new DeviceInformation(testCase, inputs);
		String expectedTemp="";
		String systemMode=statInfo.getThermoStatMode();
		if(systemMode.equals("Auto"))
		{
			systemMode=statInfo.getThermostatModeWhenAutoChangeOverActive();
		}
		if(systemMode.equals("Heat"))
		{
			expectedTemp=inputs.getInputValue(InputVariables.OVERRIDE_HEAT_SETPOINTS);
		}
		else if(systemMode.equals("Cool"))
		{
			expectedTemp=inputs.getInputValue(InputVariables.OVERRIDE_COOL_SETPOINTS);
		}
		flag = flag & JasperSetPoint.verifyDialerTemperature(testCase, inputs, Double.parseDouble(expectedTemp));
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
