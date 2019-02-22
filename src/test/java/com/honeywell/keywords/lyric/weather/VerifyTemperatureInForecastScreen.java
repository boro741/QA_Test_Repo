
package com.honeywell.keywords.lyric.weather;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.WeatherForecastScreen;

public class VerifyTemperatureInForecastScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyTemperatureInForecastScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the current unit of \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		String deviceTime = LyricUtils.getDeviceTime(testCase, inputs);
		System.out.println(deviceTime);
		if (parameters.get(1).equalsIgnoreCase("WEATHER FORECAST")) {
			switch (parameters.get(0).toUpperCase()) {
			case "FARENHEIT": {
				WeatherForecastScreen w = new WeatherForecastScreen(testCase);
				if (w.isCurrentUnitOfFarenheitDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Farenheit unit is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Farenheit unit is not displayed");
				}
				break;
			}
			case "CELSIUS": {
				WeatherForecastScreen w = new WeatherForecastScreen(testCase);
				if (w.isCurrentUnitOfCelsiusDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Celsius unit is displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Celsius unit is not displayed");
				}
				break;
			}
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid input " + parameters.get(0));
			}
			}
			/*
			 * if (flag) { Keyword.ReportStep_Pass(testCase, parameters.get(0) +
			 * " displayed"); } else { Keyword.ReportStep_Fail(testCase,
			 * FailType.FUNCTIONAL_FAILURE, parameters.get(0) + " not displayed"); }
			 */
		}
		return flag;

	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
