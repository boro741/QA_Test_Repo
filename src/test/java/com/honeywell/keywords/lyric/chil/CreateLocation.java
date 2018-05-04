package com.honeywell.keywords.lyric.chil;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;

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
		
		if(!FRUtils.checkLocationPermittedForFR(testCase, inputs,false,result)){
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
