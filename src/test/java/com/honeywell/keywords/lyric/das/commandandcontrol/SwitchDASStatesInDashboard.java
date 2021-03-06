package com.honeywell.keywords.lyric.das.commandandcontrol;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.Dashboard;

public class SwitchDASStatesInDashboard extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public SwitchDASStatesInDashboard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user switches from \"(.*)\" to \"(.*)\" in the dashboard screen$")
	public boolean keywordSteps() {
		Dashboard d = new Dashboard(testCase);
		String currentStatus = d.getSecurityStatusLabel();
		System.out.println("#############currentStatus: " + currentStatus);
		if (states.get(0).equalsIgnoreCase("Home")) {
			if (currentStatus.contains("HOME")
					|| currentStatus.contains("Home")) {
				Keyword.ReportStep_Pass(testCase, "User status is already set to Home");
			} else {
				flag = flag & DASCommandControlUtils.changeStatus(testCase, "Home", inputs);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
			}
		} else if (states.get(0).equalsIgnoreCase("Night")) {
			if (currentStatus.contains("SLEEP") || currentStatus.contains("NIGHT")
					|| (currentStatus.contains("Night"))) {
				Keyword.ReportStep_Pass(testCase, "User status is already set to Night");
			} else {
				flag = flag & DASCommandControlUtils.changeStatus(testCase, "Night", inputs);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR" , 2);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
			}
		} else if (states.get(0).equalsIgnoreCase("Away")) {
			if (currentStatus.contains("AWAY")
					|| currentStatus.contains("Away")) {
				Keyword.ReportStep_Pass(testCase, "User status is already set to Away");
			} else {
				flag = flag & DASCommandControlUtils.changeStatus(testCase, "Away", inputs);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR" , 2);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
			}
		}
		if (!flag) {
			return flag;
		}
		if (states.get(1).equalsIgnoreCase("Home")) {
			flag = flag & DASCommandControlUtils.changeStatus(testCase, "Home", inputs);
			inputs.setInputValue("HOME_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));
		} else if (states.get(1).equalsIgnoreCase("Night")) {
			flag = flag & DASCommandControlUtils.changeStatus(testCase, "Night", inputs);
			inputs.setInputValue("NIGHT_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "NIGHT_TIME " + inputs.getInputValue("NIGHT_TIME"));
			// flag = flag &
			// DASCommandControlUtils.waitForTimerToComplete(testCase);
		} else if (states.get(1).equalsIgnoreCase("Away")) {
			flag = flag & DASCommandControlUtils.changeStatus(testCase, "Away", inputs);
			inputs.setInputValue("AWAY_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "AWAY_TIME " + inputs.getInputValue("AWAY_TIME"));
		} else if (states.get(1).equalsIgnoreCase("Off")) {
			flag = flag & DASCommandControlUtils.changeStatus(testCase, "Off", inputs);
			inputs.setInputValue("OFF_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "OFF_TIME " + inputs.getInputValue("OFF_TIME"));
			// flag = flag &
			// DASCommandControlUtils.waitForTimerToComplete(testCase);
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
