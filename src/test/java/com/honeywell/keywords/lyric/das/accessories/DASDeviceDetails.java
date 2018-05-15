package com.honeywell.keywords.lyric.das.accessories;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class DASDeviceDetails extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public DASDeviceDetails(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has a DAS device configured$")
	public boolean keywordSteps() throws KeywordException {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
			if(!FRUtils.IsDASDeviceAvailableAndRegistered(testCase, inputs,true,locationID)){
				flag=false;
			}
			if(!flag){
				ReportStep_Fail_WithOut_ScreenShot(testCase,FailType.FUNCTIONAL_FAILURE,"DAS Device is not available in the location but it should be available");
			}
		
		
		return flag;
	}
	
	
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}

}
