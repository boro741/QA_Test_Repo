package com.honeywell.keywords.lyric.das.chil;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class SetDASPanelEntryExitTimerThroughCHIL extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;

	public SetDASPanelEntryExitTimerThroughCHIL(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@SuppressWarnings("resource")
	@Override
	@KeywordStep(gherkins = "^user sets the entry/exit timer to (.*) seconds$")
	public boolean keywordSteps() throws KeywordException {
		try {
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if (chUtil.getConnection()) {
				try {
					int result = chUtil.putEntryExitTimer(locInfo.getLocationID(), deviceInfo.getDeviceID(),
							parameters.get(0));
					if (result == HttpURLConnection.HTTP_ACCEPTED) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully set entry exit timer to " + parameters.get(0) + " seconds");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to set entry exit timer to " + parameters.get(0) + " seconds. Response code : "
										+ result);
					}

				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
				}

			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
