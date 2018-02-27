package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASZwaveUtils;

public class ChangeDimmerIntensity extends Keyword {

	private TestCases testCase;
	private ArrayList<String> intensity;
	public boolean flag = true;

	public ChangeDimmerIntensity(TestCases testCase, TestCaseInputs inputs,ArrayList<String> intensity) {
		this.testCase = testCase;
		this.intensity=intensity;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the intensity of the dimmer to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		int intensityToBeSet = Integer.parseInt(intensity.get(0).split("%")[0].split("~")[1]);
		DASZwaveUtils.increaseDimmerIntensity(testCase,intensityToBeSet);		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
