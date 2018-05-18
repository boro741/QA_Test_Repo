package com.honeywell.keywords.lyric.chil;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.FRUtils;

public class CreateLocation extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> screen;
	public boolean flag = true;

	public CreateLocation(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has location without DAS device$")
	public boolean keywordSteps() throws KeywordException {
		long result=FRUtils.createLocationThroughCHIL(testCase, inputs,"{\"name\":\"Extended Stay123\",\"streetAddress\":\"100 Spagnoli Road\",\"city\":\"Melville\",\"state\":\"NY\",\"country\":\"US\",\"zipcode\":\"11747\",\"TimeZone\":\"Eastern\",\"DaylightSavingTimeEnabled\":\"false\",\"GeoFenceEnabled\":\"false\"}");
		if(result==0){
			flag=false;
		}
		if(!flag)
		{
			ReportStep_Fail_WithOut_ScreenShot(testCase,FailType.FUNCTIONAL_FAILURE,"Location Creation Failed");
		}
		
		if(!FRUtils.checkLocationPermittedForFR(testCase, inputs,false,result)){
			flag=false;
		}
		if(flag){
			ReportStep_Pass(testCase,"user has a location without DAS Device as expected");
		}
		else{
			ReportStep_Fail_WithOut_ScreenShot(testCase,FailType.FUNCTIONAL_FAILURE,"user has a location with DAS Device but he should not have DAS device in this location");
		}
		
		return flag;
	}
	
	
	
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
