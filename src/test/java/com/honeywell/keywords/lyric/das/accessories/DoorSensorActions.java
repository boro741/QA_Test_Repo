package com.honeywell.keywords.lyric.das.accessories;
import java.util.ArrayList;
import java.util.HashMap;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.RelayUtils;

public class DoorSensorActions extends Keyword {

	private TestCases testCase;
	//private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public DoorSensorActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
		//this.inputs = inputs;
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
				System.out.println("in exit delay / Open door");
			} else if(states.get(0).contains("closes") && states.get(1).equalsIgnoreCase("in exit")){
				System.out.println("close door");
			} else if(states.get(0).contains("opens") && states.get(1).equalsIgnoreCase("after exit")){
				System.out.println("after exit delay / Open door");
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
