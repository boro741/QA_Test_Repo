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
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.screens.ZwavePrimardCardScreen;

public class VerifyStatusOnScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;
	private String currentStatus;

	public VerifyStatusOnScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
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
	@KeywordStep(gherkins = "^user should see the (.*) status as (.*) on the (.*)$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(2).toUpperCase()) {
		case "SWITCH PRIMARY CARD": {
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {
				ZwavePrimardCardScreen zp = new ZwavePrimardCardScreen(testCase);
				DASZwaveUtils.waitForSwitchingToComplete(testCase);
				if(zp.verifyPresenceOfSwitchStatus()){
					currentStatus=zp.getSwitchStatus();
					switch (expectedScreen.get(1).toUpperCase()) {
					case "ON": {
						if(zp.getSwitchStatus().equalsIgnoreCase("On")){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							flag=false;
							Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,  expectedScreen.get(0) +" status is not in "+expectedScreen.get(1)+" instead found to be "+currentStatus);
						}
						break;
					}
					case "OFF": {
						if(zp.getSwitchStatus().equalsIgnoreCase("Off")){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							flag=false;
							Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,  expectedScreen.get(0) +" status is not in "+expectedScreen.get(1)+" instead found to be "+currentStatus);
						}
						break;
					}
					}
				}else{
					flag=false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "switch status not found");
				}
				break;
			}
			default:{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "DASHBOARD":{
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {

				//Dashboard ds = new Dashboard(testCase);
				//if(ds..verifySwitchStatus()){
				//	currentStatus=zp.fetchSwitchStatus();
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {

					break;
				}
				case "OFF": {

					break;
				}
				}
				/*}else{
					flag=false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "switch status not found");
				}*/
				break;
			}
			default:{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "Z-WAVE DEVICE":{
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SWITCH": {
				switch (expectedScreen.get(1).toUpperCase()) {
				case "ON": {
					try {
						if (ZWaveRelayUtils.isSwitch1ON()) {
							Keyword.ReportStep_Pass(testCase,
									"Switch is in on state in z-wave device");
						} else {
							Keyword.ReportStep_Fail(testCase,
									FailType.FUNCTIONAL_FAILURE,
									"Switch is not in on state on the z-wave device");
						}
					} catch (Exception e) {
						ReportStep_Fail(testCase, FailType.FRAMEWORK_CONFIGURATION, "Relay issue"+e.getMessage());
					}
					break;
				} case "OFF":  {
					try {
						if (!ZWaveRelayUtils.isSwitch1ON()) {
							Keyword.ReportStep_Pass(testCase,
									"Switch is in off state in z-wave device");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase,
									FailType.FUNCTIONAL_FAILURE,
									"Switch is not in off state on the z-wave device");
						}
					} catch (Exception e) {
						ReportStep_Fail(testCase, FailType.FRAMEWORK_CONFIGURATION, "Relay issue"+e.getMessage());
					}
					break;
				}
				}
				break;
			}
			}
			break;
		}
		default:{
			flag=false;
			Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 3 not handled");
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