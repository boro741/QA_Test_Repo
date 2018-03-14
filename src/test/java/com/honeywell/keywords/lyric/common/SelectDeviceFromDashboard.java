package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;

public class SelectDeviceFromDashboard extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> deviceType;

	public SelectDeviceFromDashboard(TestCases testCase, TestCaseInputs inputs,ArrayList<String> deviceType) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.deviceType=deviceType;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects (.*) from the dashboard$")
	public boolean keywordSteps() {
		try
		{
		String deviceToBeSelected = "";
		if(deviceType.get(0).equalsIgnoreCase("DAS Device"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Dimmer"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DIMMER1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Switch"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_SWITCH1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Jasper device"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Thermostat device"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		}
		if (DashboardUtils.selectDeviceFromDashboard(testCase, deviceToBeSelected)) {
			Keyword.ReportStep_Pass(testCase, "Successfully selected device : " + deviceToBeSelected);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device : " + deviceToBeSelected
					+ " is not present on the dashboard");
		}
		}
		catch(Exception e)
		{
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
