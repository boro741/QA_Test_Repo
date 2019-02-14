package com.honeywell.keywords.CameraMembership;

import java.net.HttpURLConnection;
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

public class MembershipUtility extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public String plan;

	public MembershipUtility(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^(.*) camera from CHIL$")
	public boolean keywordSteps() throws KeywordException {

		CHILUtil util;
		int locationId = 46099;
		String emxDeviceId = "31338C0AB6A5D94C07EB1B0A669EAA94DA5611A5CCD25BF3AB5A4BD1BCB1D90F";
		try {
			if (parameters.get(0).equalsIgnoreCase("add")) {

				try {
					util = new CHILUtil(inputs);
					int result = util.RegisterAndActivateCamera(locationId, emxDeviceId, testCase);

					if (result == HttpURLConnection.HTTP_CREATED || result == HttpURLConnection.HTTP_OK) {
						flag = true;
						Keyword.ReportStep_Pass(testCase, "Device Registered Sucessfully");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (parameters.get(0).equalsIgnoreCase("delete")) {
				util = new CHILUtil(inputs);
				int resultDelete = util.deleteDeviceHub(locationId);

				if (resultDelete == HttpURLConnection.HTTP_CREATED || resultDelete == HttpURLConnection.HTTP_OK) {
					flag = true;
					Keyword.ReportStep_Pass(testCase, "Device Deleted Sucessfully");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid input  ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
