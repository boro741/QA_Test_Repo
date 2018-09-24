package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;

public class VerifyThermostatNameandTemperature extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;


	public VerifyThermostatNameandTemperature(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
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
	@KeywordStep(gherkins = "^user should be displayed with \"(.*)\" with \"(.*)\" temperature$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(1).toUpperCase()) {
		case "XX INSIDE": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "THERMOSTAT NAME": {
			Dashboard thermo = new Dashboard(testCase);
				flag = flag & thermo.isThermostatNameCorrectlyDisplayed(inputs.getInputValue("LOCATION1_DEVICE1_NAME"),inputs);
				flag = flag & thermo.isThermostatTemperatureDisplayed(inputs);
				if(flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(0)+"is "+expectedScreen.get(1));
				}
				else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							 expectedScreen.get(0)+"is not "+expectedScreen.get(1));
				}
				break;
			}
			}
			
			
			
			
			break;
		}
		
		}
		return flag;
	}
}