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


public class TriggerTimebasedScheduleCurrentNextPeriodUsingCHIL extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> exampleData;

	public TriggerTimebasedScheduleCurrentNextPeriodUsingCHIL(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user thermostat has \"(.+)\" and \"(.+)\" current and next period \"(.+)\" schedule$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase,inputs);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = devInfo.getDeviceID();
			String startTime = "";
			String CurrentPeriod = exampleData.get(0);
			String Period = exampleData.get(1);
			String NextPeriodStartTime = "";
			String jasperStatType = devInfo.getJasperDeviceType();
			if(exampleData.get(2).equalsIgnoreCase("Time Based")) {
				if(devInfo.getJasperDeviceType().equals("EMEA")){
					startTime = JasperSetPoint.CalculatePeriodStartEMEA(testCase);
					NextPeriodStartTime = JasperSetPoint.CalculateNextPeriodStartEMEA(testCase);
					
				}else{
					startTime = JasperSetPoint.CalculatePeriodStartNAHB(testCase);
					NextPeriodStartTime = JasperSetPoint.CalculateNextPeriodStartNAHB(testCase);
				}	
				if (chUtil.getConnection()) {
					if (chUtil.TriggerTimNextPeriod(locationID, deviceID, CurrentPeriod, Period, startTime, NextPeriodStartTime,jasperStatType )
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
