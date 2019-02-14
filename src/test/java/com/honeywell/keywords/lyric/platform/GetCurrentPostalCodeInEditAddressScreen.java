
package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;
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
import com.honeywell.screens.AddressScreen;

public class GetCurrentPostalCodeInEditAddressScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public GetCurrentPostalCodeInEditAddressScreen(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user gets the current postal code from \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		if (parameters.get(0).equalsIgnoreCase("EDIT ADDRESS")) {
			AddressScreen ads = new AddressScreen(testCase);
			inputs.setInputValue("POSTAL_CODE_VALUE_IN_EDIT_ADDRESS_SCREEN",
					ads.getPostalCodeValueInEditAddressScreen());
		}

		return flag;

	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
