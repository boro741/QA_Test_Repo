package com.honeywell.keywords.lyric.das.diyregistration;

import com.honeywell.commons.coreframework.*;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

import java.util.ArrayList;

public class EnableAmazonAlexa extends Keyword {

	private TestCases testCase;
	private ArrayList<String> amazonUserCredentials;
	private boolean flag = true;
	private TestCaseInputs inputs;

	public EnableAmazonAlexa(TestCases testCase, TestCaseInputs inputs, ArrayList<String> amazonUserCredentials) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.amazonUserCredentials = amazonUserCredentials;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return true;
	}

	@Override
	@KeywordStep(gherkins = "^user enables Amazon Alexa with \"(.*)\" and \"(.*)\"$")
	public boolean keywordSteps() throws KeywordException {
		DIYRegistrationUtils.enableAmazonAlexa(testCase, inputs, amazonUserCredentials.get(0),
				amazonUserCredentials.get(1));
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
