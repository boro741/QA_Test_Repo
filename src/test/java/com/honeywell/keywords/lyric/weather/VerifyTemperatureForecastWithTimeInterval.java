
package com.honeywell.keywords.lyric.weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.honeywell.screens.WeatherForecastScreen;

public class VerifyTemperatureForecastWithTimeInterval extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyTemperatureForecastWithTimeInterval(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user should be displayed with three forecast with interval of \"(.+)\" hrs in \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		if (parameters.get(1).equalsIgnoreCase("WEATHER FORECAST")) {
			switch (parameters.get(0).toUpperCase()) {
			case "6": {
				WeatherForecastScreen w = new WeatherForecastScreen(testCase);
				if (w.isForecastLeftTimeDisplayed() && w.isForecastCenterTimeDisplayed()
						&& w.isForecastRightTimeDisplayed()) {
					String forecastLeftTimeValue = w.getForecastLeftTimeValue();
					String forecastCenterTimeValue = w.getForecastCenterTimeValue();
					String forecastRightTimeValue = w.getForecastRightTimeValue();
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
					try {
						Date date1 = format.parse(forecastCenterTimeValue);
						Date date2 = format.parse(forecastLeftTimeValue);
						Date date3 = format.parse(forecastRightTimeValue);
						long timeDifference1 = date2.getTime() - date1.getTime();
						long timeDifference2 = date3.getTime() - date2.getTime();
						if (timeDifference1 == 21600000 && timeDifference2 == 21600000) {
							Keyword.ReportStep_Pass(testCase, "has a difference of 6 hrs");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									" does not have a difference of 6 hrs");
						}

					} catch (ParseException e) {
						e.printStackTrace();
					}
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
