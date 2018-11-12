package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASManageUsersUtils;

public class DeleteInviteUserInAddUsersScreen extends Keyword {

	private TestCases testCase;
	private ArrayList<String> deleteInvite;
	public boolean flag = true;
	private TestCaseInputs inputs;

	public DeleteInviteUserInAddUsersScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> deleteInvite) {
		this.testCase = testCase;
		this.deleteInvite = deleteInvite;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user deletes the \"(.+)\" from \"(.+)\" screen$")
	public boolean keywordSteps() {
		if (deleteInvite.get(1).equalsIgnoreCase("ADD USERS")) {
			flag &= DASManageUsersUtils.deleteInvitedUserInAddUsersScreen(testCase, inputs, deleteInvite.get(0));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}