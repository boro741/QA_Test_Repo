package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;

public class OverrideSetTemperature extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	
	public OverrideSetTemperature(TestCases testCase, TestCaseInputs inputs,ArrayList<String> exampleData) {
		this.testCase=testCase;
		this.inputs=inputs;
		this.exampleData=exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user overrides the \"(.+)\" set temperature$")
	public boolean keywordSteps() throws KeywordException {
		ArrayList<String> data=new ArrayList<String>();
		Keyword.execute(SetRotateDialer.class, testCase, inputs);
		Keyword.execute(VerifySetPointsOverriddenOnDialer.class, testCase, inputs);
		if(exampleData.get(0).equalsIgnoreCase("geofence scheduling"))
		{
			data.add("while current schedule period");
		}
		else if(exampleData.get(0).equalsIgnoreCase("time scheduling"))
		{
			data.add("till next schedule period");
		}
		Keyword.execute(VerifySchedulingAdHocStatus.class, testCase, inputs,data);
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

