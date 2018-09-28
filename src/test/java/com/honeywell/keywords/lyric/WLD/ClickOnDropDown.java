
package com.honeywell.keywords.lyric.WLD;

import java.util.ArrayList;
import java.util.HashMap;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.WLDManageAlerts;

public class ClickOnDropDown extends Keyword {
	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;
	public ClickOnDropDown(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user clicks on \"(.+)\" in \"(.+)\" Alerts$")
	public boolean keywordSteps() throws KeywordException {
		WLDManageAlerts ale = new WLDManageAlerts(testCase);
		try {
			if (parameters.get(1).equalsIgnoreCase("TEMPERATURE")) 
			{
				if(parameters.get(0).equalsIgnoreCase("BELOW RANGE"))
				{
					flag = flag && ale.clickBelowTemperatureDropDown();
				}
				else if(parameters.get(0).equalsIgnoreCase("ABOVE RANGE")) {
					flag = flag && ale.clickAboveTemperatureDropDown();
				}
			}
			else if (parameters.get(1).equalsIgnoreCase("HUMIDITY")) 
			{
				if(parameters.get(0).equalsIgnoreCase("BELOW RANGE"))
				{
					flag = flag && ale.clickBelowHumidityDropDown();
				}
				else if(parameters.get(0).equalsIgnoreCase("ABOVE RANGE")) {
					flag = flag && ale.clickAboveHumidityDropDown();
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
		return flag;
	}
}