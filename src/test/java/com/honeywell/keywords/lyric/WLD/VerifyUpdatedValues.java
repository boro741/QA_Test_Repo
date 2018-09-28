
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

public class VerifyUpdatedValues extends Keyword {
	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyUpdatedValues(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be able to see updated values in \"(.+)\" when user changes \"(.+)\" values in Alert Range$")
	public boolean keywordSteps() throws KeywordException {
		WLDManageAlerts alert = new WLDManageAlerts(testCase);
		try {
			if (parameters.get(0).equalsIgnoreCase("Alert for this Temperature Range")) 
			{
				switch (parameters.get(1).toUpperCase()) {
				case "BELOW": {
					String BelowTemp = alert.selectRandomValue();
					flag = flag && alert.verifyAlertforThisBelowTemperatureRangeUpdated(BelowTemp);
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Pass");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fail" );
					}
					break;
				}
				case "ABOVE":{
					String AboveTemp = alert.selectRandomValue();
					flag = flag && alert.verifyAlertforThisAboveTemperatureRangeUpdated(AboveTemp);
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Pass");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fail" );
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
				} 
			}
			else if (parameters.get(0).equalsIgnoreCase("Alert for this Humidity Range")) 
			{
				switch (parameters.get(1).toUpperCase()) {
				case "BELOW": {
					String BelowTemp = alert.selectRandomValue();
					flag = flag && alert.verifyAlertforThisBelowHumidityRangeUpdated(BelowTemp);
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Pass");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fail" );
					}
					break;
				}
				case "ABOVE":{
					String AboveTemp = alert.selectRandomValue();
					flag = flag && alert.verifyAlertforThisAboveHumidityRangeUpdated(AboveTemp);
					if(flag) {
						Keyword.ReportStep_Pass(testCase, "Pass");
					}
					else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Fail" );
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " - Input not handled in " + parameters.get(1));
				}
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