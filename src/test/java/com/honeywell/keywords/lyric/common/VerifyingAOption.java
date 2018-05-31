package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SecondaryCardSettings;


public class VerifyingAOption extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyingAOption(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		 this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		
	}
	
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"([^\"]*)\" with the \"([^\"]*)\" option$")
	public boolean keywordSteps() throws KeywordException {
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
		if(expectedScreen.get(1).toUpperCase().equals("FR")){
		switch (expectedScreen.get(0).toUpperCase()) {
		
		case "SHOULD BE DISPLAYED": {
			SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
				try {
					if (scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is present on the Secondary Cards screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is not present on the Secondary Cards screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
		case "SHOULD NOT BE DISPLAYED":{
			SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
			try {
				if (!scs.isFROptionAvailable(3)) {
					Keyword.ReportStep_Pass(testCase,
							"Facial Recognition Option is not present on the Secondary Cards screen");
					
					
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Facial Recognition Option is  present on the Secondary Cards screen but should not");
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}
			finally{
				//Update to the location where FR will permit
				FRUtils.updateLocationThroughCHIL(testCase, inputs,"{\"city\":\"Chicago Ridge\",\"state\":\"NY\",\"country\":\"US\",\"zipcode\":\"11747\"}",locationID);
			}
			break;
			
		}
			
		
		
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
		}
		}
		}
		else
		{
			flag=false;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}

}