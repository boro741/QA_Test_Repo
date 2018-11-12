package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASManageUsersUtils;

public class EnterTextInATextField extends Keyword {

	private TestCases testCase;
	private ArrayList<String> inputName;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public EnterTextInATextField(TestCases testCase, TestCaseInputs inputs, ArrayList<String> inputName) {
		this.testCase = testCase;
		this.inputName = inputName;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user inputs \"(.+)\" in \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (inputName.get(2).equalsIgnoreCase("INVITE USER")) {
			switch (inputName.get(1).toUpperCase()) {
			case "EMAIL TEXT FIELD": {
				if (inputName.get(0).equalsIgnoreCase("LOGGED IN USERS EMAIL ADDRESS")) {
					flag &= DASManageUsersUtils.inputEmailAddressInInviteUserScreen(testCase,
							inputs.getInputValue("USERID"));
				} else {
					flag &= DASManageUsersUtils.inputEmailAddressInInviteUserScreen(testCase, inputName.get(0));
				}
				break;
			}
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}