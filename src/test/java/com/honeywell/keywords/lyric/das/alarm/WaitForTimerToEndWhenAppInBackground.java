package com.honeywell.keywords.lyric.das.alarm;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASAlarmUtils;

public class WaitForTimerToEndWhenAppInBackground extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> timer;
	public boolean flag = true;

	public WaitForTimerToEndWhenAppInBackground(TestCases testCase, TestCaseInputs inputs, ArrayList<String> timer) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.timer = timer;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^timer lapse \"(.+)\" seconds$")
	public boolean keywordSteps() {
		try {
			DASAlarmUtils.timeForExitDelayToEndWhenAppInBackground(testCase, inputs, Integer.parseInt(timer.get(0)));
		} catch (Throwable e) {
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
