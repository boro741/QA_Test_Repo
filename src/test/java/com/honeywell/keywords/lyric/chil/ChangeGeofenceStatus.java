package com.honeywell.keywords.lyric.chil;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class ChangeGeofenceStatus extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> parameters;
	public boolean flag = true;

	public ChangeGeofenceStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^(.*) geofencing is (.*) on the user account through CHIL$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("location")) {
				boolean status = false;
				@SuppressWarnings("resource")
				CHILUtil chUtil = new CHILUtil(inputs);
				if (parameters.get(1).equalsIgnoreCase("enabled")) {
					status = true;
				} else if (parameters.get(1).equalsIgnoreCase("enabled")) {
					status = true;
				} else {
					flag = false;
					throw new Exception("Invalid Input: " + parameters.get(0));
				}
				if (chUtil.getConnection()) {
					int result = chUtil.changeLocationGeofenceStatus(status, chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")));
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "Successfully changed geofence status");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to change geofence status");
					}
				} else {
					flag = false;
					throw new Exception("Failed to connect to CHIL");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + parameters.get(0));
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
