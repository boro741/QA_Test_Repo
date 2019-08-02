package com.honeywell.keywords.lyric.das.accessories;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASAlarmUtils;
import com.honeywell.lyric.das.utils.DASSensorUtils;

public class AccessSensorActions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public AccessSensorActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.states = states;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"(.+)\" access sensor \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			switch (states.get(0).toUpperCase()) {
			case "DOOR": 
			case "DOOR SENSOR" :
			case "RF DOOR SENSOR" :{
				switch (states.get(1)) {
				case "opened":
				case "open":{
					DASSensorUtils.openDoor(testCase, inputs ,states.get(0));
					break;
				}case "closed":{
					DASSensorUtils.closeDoor(testCase, inputs,states.get(0));
					break;
				} case "is not closed":{
					System.out.println("No action on door sensor");
					DASAlarmUtils.timeOutForNoSensorAction(testCase, inputs);
					break;
				} 
				case "Tampered":
				case "Cover Tampered":{
					DASSensorUtils.tamperDoor(testCase, inputs,states.get(0).toUpperCase());
				} case "Tamper Restored" : {
					DASSensorUtils.tamperClearDoor(testCase, inputs,states.get(0).toUpperCase());
					// DASSensorUtils sensorUtils = new DASSensorUtils();
					// sensorUtils.verifySensorState(testCase, inputs, "door", "tamper cleared");
					break;
				}case "Tamper CLEARED" : {
					DASSensorUtils.tamperClearDoor(testCase, inputs,states.get(0).toUpperCase());
					break;
				} case "enrolled" : {
					DASSensorUtils.enrollDoor(testCase, inputs,states.get(0));
					break;
				} case "offline" : {
					System.out.println("Make door sensor offline");
					break;
				} case "Low Battery" : {
					System.out.println("Make door sensor Low Battery");
				}default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Input not handled");
				}
				}
				break;
			}
			case "WINDOW":
			case "RF WINDOW SENSOR" :
			case "WINDOW SENSOR": {
				switch (states.get(1)) {
				case "opened":
				case "open":{
					DASSensorUtils.openWindow(testCase, inputs ,states.get(0));
					break;
				}
				case "closed":{
					DASSensorUtils.closeWindow(testCase, inputs,states.get(0));
					break;
				}
				case "does not close":{
					DASAlarmUtils.timeOutForNoSensorAction(testCase, inputs);
					break;
				}
				case "Tampered":
				case "Cover Tampered":{
					DASSensorUtils.tamperWindow(testCase, inputs,states.get(0).toUpperCase());
					break;
				}
				case "Tamper Restored":{
					DASSensorUtils.tamperClearWindow(testCase, inputs,states.get(0).toUpperCase());
					DASSensorUtils sensorUtils = new DASSensorUtils();
					sensorUtils.verifySensorState(testCase, inputs, "window", "tamper cleared");
					break;
				}case "Tamper CLEARED":{
					DASSensorUtils.tamperClearWindow(testCase, inputs,states.get(0).toUpperCase());
					break;
				} case "enrolled":{
					DASSensorUtils.enrollWindow(testCase, inputs,states.get(0));
					break;
				} case "offline":{
					System.out.println("Make window sensor offline");
					break;
				} case "Low Battery":{
					System.out.println("Make window sensor Low Battery");
					break;
				}default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + states.get(0) + " for " + states.get(1));
					break;
				}
				}
				break;
			}
			case "MOTION":
			case "MOTION SENSOR":{
				if (states.get(1).equalsIgnoreCase("opened") || states.get(1).equalsIgnoreCase("open")) {
					DASSensorUtils.openMotionSensor(testCase, inputs);
				} else if (states.get(1).equalsIgnoreCase("closed")) {
					DASSensorUtils.closeMotionSensor(testCase, inputs);
				} else if (states.get(1).equalsIgnoreCase("Tampered")
						|| states.get(1).equalsIgnoreCase("Cover Tampered")) {
					DASSensorUtils.tamperMotionSensor(testCase, inputs);
				} else if (states.get(1).equalsIgnoreCase("Tamper CLEARED")) {
					DASSensorUtils.tamperClearMotionSensor(testCase, inputs);
				}
				break;
			}case "COMBO":
			case "COMBO SENSOR":{
				if (states.get(1).equalsIgnoreCase("Triger")) {
					DASSensorUtils.enrollSmoke(testCase, inputs);
					Thread.sleep(10000);
				} else if (states.get(1).equalsIgnoreCase("Smoke Test")) {
					DASSensorUtils.enrollSmoke(testCase, inputs);
					Thread.sleep(10000);
				} else if (states.get(1).equalsIgnoreCase("Co Test")){
					DASSensorUtils.enrollCo(testCase, inputs);
					Thread.sleep(10000);
				}
				break;
			}
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
