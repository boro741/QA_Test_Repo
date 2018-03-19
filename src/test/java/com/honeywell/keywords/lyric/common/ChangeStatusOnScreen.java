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
import com.honeywell.screens.ZwaveScreen;

public class ChangeStatusOnScreen extends Keyword {

	private TestCases testCase;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;
	private String currentStatus;
	private TestCaseInputs inputs;

	public ChangeStatusOnScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.expectedScreen = expectedScreen;
		this.inputs= inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user turns (.*) the (.*) through the (.*)$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(2).toUpperCase()) {
		case "DIMMER PRIMARY CARD": 
		case "SWITCH PRIMARY CARD": {
			switch (expectedScreen.get(1).toUpperCase()) {
			case "DIMMER": 
			case "SWITCH": {
				ZwavePrimardCardScreen zp = new ZwavePrimardCardScreen(testCase);
				if(zp.verifyPresenceOfSwitchStatus()){
					currentStatus=zp.getSwitchStatus();
					switch (expectedScreen.get(0).toUpperCase()) {
					case "ON": {
						if(zp.switchOn()){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							flag=false;
							Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,  expectedScreen.get(0) +" status is not in "+expectedScreen.get(1)+" instead found to be "+currentStatus);
						}
						break;
					}
					case "OFF": {
						if(zp.switchOff()){
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
				/*
				ZwavePrimardCardScreen zp = new ZwavePrimardCardScreen(testCase);
				if(zp.verifySwitchStatus()){
					currentStatus=zp.fetchSwitchStatus();
					switch (expectedScreen.get(1).toUpperCase()) {
					case "ON": {
						if(zp.fetchSwitchStatus().equalsIgnoreCase("On")){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							flag=false;
							Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,  expectedScreen.get(0) +" status is not in "+expectedScreen.get(1)+" instead found to be "+currentStatus);
						}
						break;
					}
					case "OFF": {
						if(zp.fetchSwitchStatus().equalsIgnoreCase("Off")){
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
				 */}
			default:{
				flag=false;
				Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE, "Input 1 not handled");
			}
			}
			break;
		}
		case "Z-WAVE DEVICE FUNCTION KEY":{
			switch (expectedScreen.get(1).toUpperCase()) {
			case "SWITCH": {
				switch (expectedScreen.get(0).toUpperCase()) {
				case "ON": {
					try {
						ZWaveRelayUtils.powerOnZwaveSwitch(inputs);
						if(ZWaveRelayUtils.isSwitch1ON()){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							ZWaveRelayUtils.pressButtonOnSwitch1();
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is made to "+expectedScreen.get(1) );
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "OFF": {
					try {
						ZWaveRelayUtils.powerOnZwaveSwitch(inputs);
						if(ZWaveRelayUtils.isSwitch1ON()){
							ZWaveRelayUtils.pressButtonOnSwitch1();
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is made to "+expectedScreen.get(1) );
						}else{
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is already in to "+expectedScreen.get(1) );
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "OFFLINE": {
					try {
						ZWaveRelayUtils.powerOffZwaveSwitch(inputs);
						ZwaveScreen zs=new ZwaveScreen(testCase);
						flag = flag & zs.ClickSwitchSettingFromZwaveDevices();
						int i=0;
						while(i<3 && zs.verifyPresenceOfSwitchStatus()){
							flag = flag & zs.clickOffStatus();
							flag = flag & DASZwaveUtils.waitForToggleActionToComplete(testCase);
							i++;
						}
						flag = flag & DASZwaveUtils.navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is made to "+expectedScreen.get(1) );
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				}
				break;
			}
			case "DIMMER": {
				switch (expectedScreen.get(0).toUpperCase()) {
				case "ON": {
					try {
						ZWaveRelayUtils.powerOnZwaveDimmer(inputs);
						int iIntensity=Integer.parseInt(ZWaveRelayUtils.getDimmerIntensityRange().split("-")[0]);
						if(iIntensity>0){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							ZWaveRelayUtils.pressButtonOnDimmer1();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "OFF": {
					try {
						ZWaveRelayUtils.powerOnZwaveDimmer(inputs);
						int iIntensity=Integer.parseInt(ZWaveRelayUtils.getDimmerIntensityRange().split("-")[0]);
						if(iIntensity==0){
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							ZWaveRelayUtils.pressButtonOnDimmer1();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "OFFLINE": {
					try {
						ZWaveRelayUtils.powerOffZwaveDimmer(inputs);
						ZwaveScreen zs=new ZwaveScreen(testCase);
						flag = flag & zs.ClickDimmerSettingFromZwaveDevices();
						int i=0;
						while(i<3 && zs.verifyPresenceOfSwitchStatus()){
							flag = flag & zs.clickOffStatus();
							flag = flag & DASZwaveUtils.waitForToggleActionToComplete(testCase);
							i++;
						}
						flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is made to "+expectedScreen.get(1) );
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
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
		case "DIMMER SETTINGS":
		case "SWITCH SETTINGS":{
			switch (expectedScreen.get(1).toUpperCase()) {
			case "DIMMER":
			case "SWITCH": {
				ZwaveScreen zs = new ZwaveScreen(testCase);
				if(zs.verifyPresenceOfSwitchStatus()){
					currentStatus=zs.getSwitchStatus();
					switch (expectedScreen.get(0).toUpperCase()) {
					case "ON": {
						if(zs.switchOn()){
							DASZwaveUtils.waitForToggleActionToComplete(testCase); 
							Keyword.ReportStep_Pass(testCase, expectedScreen.get(0) +" status is "+expectedScreen.get(1) );
						}else{
							flag=false;
							Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,  expectedScreen.get(0) +" status is not in "+expectedScreen.get(1)+" instead found to be "+currentStatus);
						}
						break;
					}
					case "OFF": {
						if(zs.switchOff()){
							DASZwaveUtils.waitForToggleActionToComplete(testCase); 
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
