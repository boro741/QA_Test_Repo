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
			if(states.get(0).equalsIgnoreCase("door")||states.get(0).equalsIgnoreCase("door sensor")){
				if(states.get(1).equalsIgnoreCase("opened")){
					DASSensorUtils.openDoor(testCase, inputs);
				} else if(states.get(1).equalsIgnoreCase("closed")){
					DASSensorUtils.closeDoor(testCase, inputs);
				} else if(states.get(1).equalsIgnoreCase("is not closed")){
					System.out.println("No action on door sensor");
					DASAlarmUtils.timeOutForNoSensorAction(testCase,inputs);
				} else if (states.get(1).equalsIgnoreCase("Tampered")){
					DASSensorUtils.tamperDoor(testCase, inputs);
				}else if (states.get(1).equalsIgnoreCase("Tamper Restored")){
					DASSensorUtils.tamperClearDoor(testCase, inputs);
//					DASSensorUtils sensorUtils = new DASSensorUtils();
//					sensorUtils.verifySensorState(testCase, inputs, "door", "tamper cleared");
				}
				else if (states.get(1).equalsIgnoreCase("Tamper CLEARED")){
					DASSensorUtils.tamperClearDoor(testCase, inputs);
				}
				else if(states.get(1).equalsIgnoreCase("enrolled")){
					DASSensorUtils.enrollDoor(testCase, inputs);
				}
				else if(states.get(1).equalsIgnoreCase("offline")){
					System.out.println("Make door sensor offline"); 
				} else if(states.get(1).equalsIgnoreCase("Low Battery")){
					System.out.println("Make door sensor Low Battery"); 
				}
				
				else{
					Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Input not handled");
				}	
			}else if(states.get(0).equalsIgnoreCase("window") || states.get(0).equalsIgnoreCase("window sensor")){
				if(states.get(1).equalsIgnoreCase("opened")){
					DASSensorUtils.openWindow(testCase, inputs);
				} else if(states.get(1).equalsIgnoreCase("closed")){
					DASSensorUtils.closeWindow(testCase, inputs);
				} else if(states.get(1).equalsIgnoreCase("does not close")){
					DASAlarmUtils.timeOutForNoSensorAction(testCase,inputs);
				}else if (states.get(1).equalsIgnoreCase("Tampered")){
					DASSensorUtils.tamperWindow(testCase, inputs);
				}else if (states.get(1).equalsIgnoreCase("Tamper Restored")){
					DASSensorUtils.tamperClearWindow(testCase, inputs);
					DASSensorUtils sensorUtils = new DASSensorUtils();
					sensorUtils.verifySensorState(testCase, inputs, "window", "tamper cleared");
				}
				else if (states.get(1).equalsIgnoreCase("Tamper CLEARED")){
					DASSensorUtils.tamperClearWindow(testCase, inputs);
				}
				else if(states.get(1).equalsIgnoreCase("enrolled")){
					DASSensorUtils.enrollWindow(testCase, inputs);
				}
				else if(states.get(1).equalsIgnoreCase("offline")){
					System.out.println("Make window sensor offline");
				}else if(states.get(1).equalsIgnoreCase("Low Battery")){
					System.out.println("Make window sensor Low Battery");
				}
				else{
					Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Input not handled");
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
