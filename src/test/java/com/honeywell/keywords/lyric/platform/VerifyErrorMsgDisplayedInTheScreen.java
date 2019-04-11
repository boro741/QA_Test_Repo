package com.honeywell.keywords.lyric.platform;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASManageUsersUtils;
import com.honeywell.lyric.das.utils.EditAccountUtils;

public class VerifyErrorMsgDisplayedInTheScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> errorMsgText;
	public boolean flag = true;
	@SuppressWarnings("unused")
	private TestCaseInputs inputs;

	public VerifyErrorMsgDisplayedInTheScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> errorMsgText) {
		this.testCase = testCase;
		this.errorMsgText = errorMsgText;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with \"(.+)\" error message in the \"(.+)\" in the \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (errorMsgText.get(2).equalsIgnoreCase("CHANGE PASSWORD")) {
			switch (errorMsgText.get(1).toUpperCase()) {
			case "OLD PASSWORD TEXT FIELD": {
				if (errorMsgText.get(0).equalsIgnoreCase("YOU MUST ENTER YOUR PASSWORD")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInOldPwdTextField(testCase, errorMsgText.get(0));
				} else if (errorMsgText.get(0).equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInOldPwdTextField(testCase, errorMsgText.get(0));
				}
				break;
			}
			case "NEW PASSWORD TEXT FIELD": {
				if (errorMsgText.get(0).equalsIgnoreCase("YOU MUST ENTER YOUR NEW PASSWORD")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInNewPwdTextField(testCase, errorMsgText.get(0));
				} else if (errorMsgText.get(0).equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInNewPwdTextField(testCase, errorMsgText.get(0));
				} else if (errorMsgText.get(0).equalsIgnoreCase("PASSWORDS DO NOT MATCH")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInNewPwdTextField(testCase, errorMsgText.get(0));
				}
				break;
			}
			case "VERIFY NEW PASSWORD TEXT FIELD": {
				if (errorMsgText.get(0).equalsIgnoreCase("YOU MUST ENTER YOUR VERIFY PASSWORD")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInVerifyNewPwdTextField(testCase, errorMsgText.get(0));
				} else if (errorMsgText.get(0).equalsIgnoreCase("INVALID PASSWORD FORMAT")) {
					flag &= EditAccountUtils.verifyErrorMsgTextInVerifyNewPwdTextField(testCase, errorMsgText.get(0));
				}
				break;
			}
			}
		} else if (errorMsgText.get(2).equalsIgnoreCase("INVITE NEW USER")) {
			switch (errorMsgText.get(1).toUpperCase()) {
			case "INVITE NEW USER EMAIL TEXT FIELD": {
				if (errorMsgText.get(0).equalsIgnoreCase("USER ALREADY ADDED TO THIS ACCOUNT")) {
					flag &= DASManageUsersUtils.verifyUserAlreadyAddedErrorMsgBelowInviteNewUserEmailTextField(testCase,
							errorMsgText.get(0));
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