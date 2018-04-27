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

public class WindowSensorActions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public WindowSensorActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user window \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			if(states.get(0).equalsIgnoreCase("opened")){
				DASSensorUtils.openWindow(testCase, inputs);
			} else if(states.get(0).equalsIgnoreCase("closed")){
				DASSensorUtils.closeWindow(testCase, inputs);
			} else if(states.get(0).equalsIgnoreCase("does not close")){
				DASAlarmUtils.timeOutForNoSensorAction(testCase,inputs);
			}else if (states.get(0).equalsIgnoreCase("Tampered")){
				DASSensorUtils.tamperWindow(testCase, inputs);
			}else if (states.get(0).equalsIgnoreCase("Tamper Restored")){
				DASSensorUtils.tamperClearWindow(testCase, inputs);
				DASSensorUtils sensorUtils = new DASSensorUtils();
				sensorUtils.verifySensorState(testCase, inputs, "window", "tamper cleared");
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
