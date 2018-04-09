package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;

public class VerifyThermostatStatus extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyThermostatStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^verify the \"(.+)\" status on the solution card$")
	public boolean keywordSteps() throws KeywordException {
		if (exampleData.get(0).equalsIgnoreCase("geofence scheduling")) {
			flag = flag & JasperSchedulingVerifyUtils.verifyThermostatStatus(testCase, inputs, "geofence");
		}
		else if (exampleData.get(0).equalsIgnoreCase("time scheduling")) {
			flag = flag & JasperSchedulingVerifyUtils.verifyThermostatStatus(testCase, inputs, "time");
		}
		else if (exampleData.get(0).equalsIgnoreCase("schedule off")) {
			flag = flag & JasperSchedulingVerifyUtils.verifyThermostatStatus(testCase, inputs, "off");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}

