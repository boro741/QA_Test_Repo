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
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.WeatherForecastScreen;

public class VerifyTemperatureScaleUnitOnAddNewDeviceDashboardScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyTemperatureScaleUnitOnAddNewDeviceDashboardScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" temperature scale in \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		if (parameters.get(1).equalsIgnoreCase("WEATHER FORECAST")) {
			WeatherForecastScreen w = new WeatherForecastScreen(testCase);
			switch (parameters.get(0).toUpperCase()) {
			case "CELSIUS UNIT": {
				if (w.whichWeatherTempUnitIsEnabled().contains("C")) {
					Keyword.ReportStep_Pass(testCase, "Celsius unit value is enabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Celsius unit is not enabled");
				}
				break;
			}
			case "FARENHEIT UNIT": {
				if (w.whichWeatherTempUnitIsEnabled().contains("F")) {
					Keyword.ReportStep_Pass(testCase, "Farenheit unit value is enabled");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Farenheit unit is not enabled");
				}
				break;
			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid input " + parameters.get(0));
			}
			}
        }else if (parameters.get(1).equalsIgnoreCase("ADD NEW DEVICE DASHBOARD")) {
            Dashboard d = new Dashboard(testCase);
            switch (parameters.get(0).toUpperCase()) {
                case "CELSIUS": {
                    if (d.getWeatherTempValue().contains(".")) {
                        Keyword.ReportStep_Pass(testCase, "Celsius value is displayed");
                    } else {
                        Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Celsius value is not displayed");
                    }
                    break;
                }
                case "FARENHEIT": {
                    if (!d.getWeatherTempValue().contains(".")) {
                        Keyword.ReportStep_Pass(testCase, "Farenheit value is displayed");
                    } else {
                        Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Farenheit value is not displayed");
                    }
                    break;
                }
                    
                default: {
                    flag = false;
                    Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid input " + parameters.get(0));
                }
            }
        }
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
