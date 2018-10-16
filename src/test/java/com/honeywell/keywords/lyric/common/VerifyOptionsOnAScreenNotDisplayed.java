package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifyOptionsOnAScreenNotDisplayed extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenNotDisplayed(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedScreen, DataTable data) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with the following \"(.*)\" options:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {

		case "DETERRENCE SETTINGS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Settings");
				switch (parameter.toUpperCase()) {
				case "SELECT CHIME": {
					flag &= !bs.isSelectChimeVisible();
					break;
				}
				case "PLAY DOG BARK SOUND": {
					flag &= !bs.isPlayDogBarkSoundVisible();
					break;
				}
				case "PARTY IS ON": {
					flag &= !bs.isPartyIsOnVisible();
					break;
				}
				case "VACUUM": {
					flag &= !bs.isVacuumVisible();
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + " is not displayed");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " is displayed");
				}
				flag = true;
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
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
