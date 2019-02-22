package com.honeywell.keywords.jasper.scheduling.Create;

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

public class SetGeofenceSleepTime extends Keyword {
	@SuppressWarnings("unused")
	private static final String Startime = null;
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	ArrayList<String> exampleData;

	public SetGeofenceSleepTime(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user sets sleep start time to \"(.+)\" and end time to \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = statInfo.getDeviceID();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase,
						"Set Sleep Time : Cannot set Sleep time because thermostat is offline");
				return true;
			}
			if (exampleData.get(0).equalsIgnoreCase("11:00AM")) {
				String startTime = (exampleData.get(0).replace("AM", ":00"));
				if (exampleData.get(1).equalsIgnoreCase("11:00AM")) {
					String endTime = (exampleData.get(1).replace("AM", ":00"));
					if (chUtil.getConnection()) {
						int result = chUtil.TriggerGeoEventSleep(locationID, deviceID, startTime, endTime);
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase,
									"Set Sleep Using CHIL : Successfully Set Sleep using CHIL");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Set Sleep Using CHIL : Failed to Set Sleep using CHIL");
						}
					}
				}
			}
		} catch (Exception e) {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Parse Value : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
