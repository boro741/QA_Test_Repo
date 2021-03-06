package com.honeywell.keywords.lyric.common;

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
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSensorUtils;

public class PerformInBackground extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public PerformInBackground(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
		this.testCase = testCase;
		this.states = states;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}
	@Override
	@KeywordStep(gherkins = "^user \"(.+)\" in background$")
	public boolean keywordSteps() throws KeywordException {
		try {
			Runnable bgnd = new Runnable() {
				public void run() {
					MobileUtils.minimizeApp(testCase, -1);
				}
			};
			Thread t1= new Thread(bgnd);
			t1.start();
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if (chUtil.getConnection()) {
				switch (states.get(0)){
				case "opens window with app": {
					try{
						DASSensorUtils.openWindow(testCase, inputs,"WINDOW");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
						break;
					}
				} case "opens RF window with app": {
					try{
						DASSensorUtils.openWindow(testCase, inputs,"RF WINDOW SENSOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
						break;
					}
				}case "opens door with app":{
					try{
						DASSensorUtils.openDoor(testCase, inputs,"DOOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				} case "opens RF door with app":{
					try{
						DASSensorUtils.openDoor(testCase, inputs,"RF DOOR SENSOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				}
				case "door tampered with app": {
					try{
						DASSensorUtils.tamperDoor(testCase, inputs,"DOOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				} case "window tampered with app": {
					try{
						DASSensorUtils.tamperWindow(testCase, inputs,"WINDOW");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				}
				case "RF door tampered with app": {
					try{
						DASSensorUtils.tamperDoor(testCase, inputs,"RF DOOR SENSOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				} case "RF window tampered with app": {
					try{
						DASSensorUtils.tamperWindow(testCase, inputs,"RF WINDOW SENSOR");
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				}case "put app": {
					try{
						MobileUtils.minimizeApp(testCase, 20);
					}catch(Exception e){
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Exception"+e.getMessage());
					}
					break;
				} case "DISMISS ALARM": {
					if(chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase)==202){
						Keyword.ReportStep_Pass(testCase, "dismissed alarm executed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not dismiss alarm " );
					} 
					break;
				} case "invited user dismiss alarm": {
					String actualUser=inputs.getInputValue("USERID");
					inputs.setInputValue("USERID",inputs.getInputValue("INVITEDUSER"));
					String actualLocName=inputs.getInputValue("LOCATION1_NAME");
					inputs.setInputValue("LOCATION1_NAME","Home");
					chUtil = new CHILUtil(inputs);
					if(chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase)==202){
						Keyword.ReportStep_Pass(testCase, "invited user  - dismissed alarm executed");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not dismiss alarm " );
					} 
					inputs.setInputValue("USERID",actualUser);
					inputs.setInputValue("LOCATION1_NAME",actualLocName);
					break;
				} case "invited user changes mode to HOME": {
					String actualUser=inputs.getInputValue("USERID");
					inputs.setInputValue("USERID",inputs.getInputValue("INVITEDUSER"));
					String actualLocName=inputs.getInputValue("LOCATION1_NAME");
					inputs.setInputValue("LOCATION1_NAME","Home");
					chUtil = new CHILUtil(inputs);
					locInfo = new LocationInformation(testCase, inputs);
					deviceInfo = new DeviceInformation(testCase, inputs);
					int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"Home",testCase);
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to home");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in home : " + result);
					}
					inputs.setInputValue("USERID",actualUser);
					inputs.setInputValue("LOCATION1_NAME",actualLocName);
					break;
				} 
				case "SWITCHED TO HOME":
				case "TRIGERS HOME MODE":{
					int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"Home",testCase);
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to home");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in home : " + result);
					}
					break;
				} case "TRIGERS AWAY MODE":{
					int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"AWAY",testCase);
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to AWAY MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in AWAY MODE : " + result);
					}
					break;
				} 
				case "TRIGERS NIGHT MODE":
				case "SWITCHED TO NIGHT":{
					int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"NIGHT",testCase);
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to NIGHT MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in NIGHT MODE : " + result);
					}
					break;
				} case "TRIGERS OFF MODE":{
					int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),"OFF", testCase);
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to NIGHT MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in NIGHT MODE : " + result);
					}
					break;
				} default:{
					Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Input not handled");
					break;
				}
				/*while(t1.isAlive()){
					Keyword.ReportStep_Pass(testCase, "App in backgrond");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
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
