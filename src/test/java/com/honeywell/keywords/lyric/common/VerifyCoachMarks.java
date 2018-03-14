package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.screens.CoachMarks;

public class VerifyCoachMarks extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyCoachMarks(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies the (.*) coach marks$")
	public boolean keywordSteps() throws KeywordException {
		try {
			CoachMarks cm = new CoachMarks(testCase);
			if (cm.isGotitButtonVisible(15)) {
				switch (parameters.get(0).toUpperCase()) {
				case "DAS DASHBOARD": {
					flag = flag & CoachMarkUtils.verifyDashboardCoachMarks(testCase,CoachMarkUtils.DAS);
					break;
				}
				case "DAS SOLUTION CARD": {
					flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase,CoachMarkUtils.DAS);
					break;
				}
				case "DAS CAMERA SOLUTION CARD": {
					flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase,CoachMarkUtils.DASCAMERA);
					break;
				}
				case "THERMOSTAT DASHBOARD": {
					flag = flag & CoachMarkUtils.verifyDashboardCoachMarks(testCase,CoachMarkUtils.THERMOSTAT);
					break;
				}
				case "NA THERMOSTAT SOLUTION CARD": {
					flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase,CoachMarkUtils.THERMOSTATNA);
					break;
				}
				case "EMEA THERMOSTAT SOLUTION CARD": {
					flag = flag & CoachMarkUtils.verifySolutionCardCoachMarks(testCase,CoachMarkUtils.THERMOSTATEMEA);
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + parameters.get(0));
				}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Coach Marks are not visible");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
