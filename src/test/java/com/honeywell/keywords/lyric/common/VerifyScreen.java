package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;

public class VerifyScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the (.*) screen$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "ACTIVATE Z-WAVE DEVICE": {
			fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ActivateTheDeviceHeader") && MobileUtils.isMobElementExists(fieldObjects, testCase, "ActivateTheDeviceTitle")){
				Keyword.ReportStep_Pass(testCase, "In " +expectedScreen.get(0).toUpperCase() + " screen");
			}else{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not in excpected screen: "+expectedScreen.get(0).toUpperCase());
			}
			break;
		}
		case "EXCLUSION MODE ACTIVE":{
			fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludeModeScreenHeader") && MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludeModeTitle")){
				Keyword.ReportStep_Pass(testCase, "In " +expectedScreen.get(0).toUpperCase() + " screen");
			}else{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not in excpected screen: "+expectedScreen.get(0).toUpperCase());
			}
			break;
		}
		case "ADD NEW DEVICE":{
			fieldObjects = MobileUtils.loadObjectFile(testCase, "AddNewDevice");
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "AddNewDeviceHeader")){
				Keyword.ReportStep_Pass(testCase, "In " +expectedScreen.get(0).toUpperCase() + " screen");
			}else{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not in excpected screen: "+expectedScreen.get(0).toUpperCase());
			}
			break;
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
