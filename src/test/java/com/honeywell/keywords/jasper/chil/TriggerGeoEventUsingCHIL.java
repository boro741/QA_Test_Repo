package com.honeywell.keywords.jasper.chil;

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
import com.honeywell.jasper.utils.JasperSetPoint;


public class TriggerGeoEventUsingCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public TriggerGeoEventUsingCHIL(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user thermostat set \"(.+)\" with \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase,inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			long geofenceID = locInfo.getGeofenceID();
			long userID = locInfo.getUserID();
			String deviceID = statInfo.getDeviceID();

			if (exampleData.get(1).equalsIgnoreCase("UserArrived")) {
				switch (exampleData.get(0).toUpperCase()) {
				case "HOME": {
					if (chUtil.getConnection()) {
						int result = chUtil.TriggerGeoEvent(locationID, geofenceID, userID, exampleData.get(1));
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase, "geo event triggered" + exampleData.get(1));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "geo event not triggered" + exampleData.get(0));
						}
					}break;
				}
				
				case "SLEEP": {
					if (chUtil.getConnection()) {
					String startTime = "";
					String endTime = "";
					int result = chUtil.TriggerGeoEvent(locationID, geofenceID,userID, exampleData.get(1));
					if (result == 200) {
							Keyword.ReportStep_Pass(testCase, "geo event triggered" + exampleData.get(1));
					} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "geo event not triggered" + exampleData.get(0));
					}
					if(statInfo.getJasperDeviceType().equals("EMEA")){
							startTime = JasperSetPoint.TriggerGeoEventSleepStartEMEA(testCase);
							endTime = JasperSetPoint.TriggerGeoEventSleepEndEMEA(testCase, 2);
					}else {
							startTime = JasperSetPoint.TriggerGeoEventSleepStartNAHB(testCase);
				 			endTime = JasperSetPoint.TriggerGeoEventSleepEndNAHB(testCase, 2);
					}
					if (chUtil.getConnection()) {
					result = chUtil.TriggerGeoEventSleep(locationID, deviceID, startTime, endTime );
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,"Set Sleep Using CHIL : Successfully Set Sleep using CHIL");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"Set Sleep Using CHIL : Failed to Set Sleep using CHIL");							}
					}
					}break;
					}
					default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + exampleData.get(0));
					}
					}
				}else if (exampleData.get(1).equalsIgnoreCase("UserDeparted")) {
				switch (exampleData.get(0).toUpperCase()) {
				case "AWAY" : {
					if (chUtil.getConnection()) {
					int result = chUtil.TriggerGeoEvent(locationID, geofenceID, userID, exampleData.get(1));
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "geo event triggered" + exampleData.get(1));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "geo event not triggered" + exampleData.get(0));
					}
					}	break;
					}
					default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + exampleData.get(0));
					}
					}
					}
				}catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
				FailType.FUNCTIONAL_FAILURE,"Error Occured : " + e.getMessage());
				}
return flag;
			
}
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
