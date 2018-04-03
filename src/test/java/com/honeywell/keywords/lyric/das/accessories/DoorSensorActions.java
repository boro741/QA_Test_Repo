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

public class DoorSensorActions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public DoorSensorActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user \"(.+)\" the door \"(.+)\" delay$")
	public boolean keywordSteps() {
		try {
			if(states.get(0).contains("opens") && states.get(1).equalsIgnoreCase("in exit")){
				DASSensorUtils.openDoor(testCase, inputs);
			} else if(states.get(0).contains("closes") && states.get(1).equalsIgnoreCase("in exit")){
				DASSensorUtils.closeDoor(testCase, inputs);
			} else if(states.get(0).contains("opens") && states.get(1).equalsIgnoreCase("after exit")){
				DASSensorUtils.openDoor(testCase, inputs);
			} else if(states.get(0).contains("closes") && states.get(1).equalsIgnoreCase("after exit")){
				DASSensorUtils.closeDoor(testCase, inputs);
			} else if(states.get(0).contains("does not close") && states.get(1).equalsIgnoreCase("after exit")){
				DASAlarmUtils.timeOutForNoSensorAction(testCase,inputs);
			}
			else{
				Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Input not handled");
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
