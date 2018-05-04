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
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class FacialRecognitionLocation extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> screen;
	public boolean flag = true;

	public FacialRecognitionLocation(TestCases testCase, TestCaseInputs inputs, ArrayList<String> screen) {
		this.testCase = testCase;
		this.screen = screen;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has location \"([^\"]*)\" for facial recognition$")
	public boolean keywordSteps() throws KeywordException {
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
		switch (screen.get(0).toUpperCase()) {
		case "PERMITTED": {
			flag=FRUtils.checkLocationPermittedForFR(testCase, inputs,true,locationID);
			break;
		}
		case "NOT PERMITTED":{
			
			//Update to Illinois location through chil so FR will not be shown
			FRUtils.updateLocationThroughCHIL(testCase, inputs,"{\"city\":\"Chicago Ridge\",\"state\":\"IL\",\"country\":\"US\",\"zipcode\":\"60415\"}",locationID);
			flag=FRUtils.checkLocationPermittedForFR(testCase, inputs,false,locationID);
			break;
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
