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

public class KeyfobActions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public KeyfobActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user press \"(.+)\" key from keyfob$")
	public boolean keywordSteps() {
		try {
			if(states.get(0).equalsIgnoreCase("home")){
				System.out.println("home");
			} else if(states.get(0).equalsIgnoreCase("away")){
				System.out.println("away");
			} else if(states.get(0).equalsIgnoreCase("off")){
				System.out.println("off");
			} else if (states.get(0).equalsIgnoreCase("night")){
				System.out.println("night");
			}
			else if(states.get(0).equalsIgnoreCase("enrollment")){
				System.out.println("home and away");
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
