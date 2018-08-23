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


public class TriggerTimebasedSchedule3PeriodsUsingCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public TriggerTimebasedSchedule3PeriodsUsingCHIL(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user thermostat has \"(.+)\" as previous period \"(.+)\" as current period and \"(.+)\" as next period of \"(.+)\" schedule$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase,inputs);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = devInfo.getDeviceID();
			String startTime = "";
			String previousPeriodTime="";
			String PreviousPeriod = exampleData.get(0);
			String CurrentPeriod = exampleData.get(1);
			String NextPeriod = exampleData.get(2);
			String NextPeriodStartTime = "";
			if(exampleData.get(3).equalsIgnoreCase("Time Based")) {
				if(devInfo.getJasperDeviceType().equals("EMEA")){
					startTime = JasperSetPoint.CalculatePeriodStartEMEA(testCase);
					NextPeriodStartTime = JasperSetPoint.CalculateNextPeriodStartEMEA(testCase);
					
				}else{
					previousPeriodTime=JasperSetPoint.CalculatePreviosPeriodStartNAHB(testCase);
					startTime = JasperSetPoint.CalculatePeriodStartNAHB(testCase);
					NextPeriodStartTime = JasperSetPoint.CalculateNextPeriodStartNAHB(testCase);
				}	
				if (chUtil.getConnection()) {
					if (chUtil.TriggerTimNextPeriod(locationID, deviceID,PreviousPeriod, CurrentPeriod, NextPeriod, previousPeriodTime, startTime, NextPeriodStartTime)
							== 200) {
						Keyword.ReportStep_Pass(testCase,
								"Successfully Activated the period: "+exampleData.get(0).toUpperCase());
					} else {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Unable to activate the period: "+exampleData.get(0).toUpperCase());
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
