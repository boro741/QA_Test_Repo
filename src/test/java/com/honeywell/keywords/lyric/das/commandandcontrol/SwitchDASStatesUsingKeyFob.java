package com.honeywell.keywords.lyric.das.commandandcontrol;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.das.utils.DASCommandControlUtils;
import com.honeywell.lyric.relayutils.RelayUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class SwitchDASStatesUsingKeyFob extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public SwitchDASStatesUsingKeyFob(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user switches from \"(.*)\" to \"(.*)\" using keyfob$")
	public boolean keywordSteps() {
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		String currentStatus = sc.getCurrentSecurityState();
		System.out.println("#############currentStatus: " + currentStatus);
		if (states.get(0).equalsIgnoreCase("Home")) {
			if (currentStatus.equalsIgnoreCase("Home")) {
				Keyword.ReportStep_Pass(testCase, "User status is already set to Home");
			} else {
				flag = flag & DASCommandControlUtils.changeStatus(testCase, "Home", inputs);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
			}
		} else if (states.get(0).equalsIgnoreCase("Night")) {
			if (currentStatus.equalsIgnoreCase("Sleep") || currentStatus.equalsIgnoreCase("Night")) {
				Keyword.ReportStep_Pass(testCase, "User status is already set to Night");
			} else {
				flag = flag & DASCommandControlUtils.changeStatus(testCase, "Night", inputs);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR" , 2);
				flag = flag & DASCommandControlUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
			}
		} else if (states.get(0).equalsIgnoreCase("Away")) {
			if (currentStatus.equalsIgnoreCase("Away")) {
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
		try{
		if (states.get(1).equalsIgnoreCase("Home")) {
			RelayUtils.Keyfob_Home();
			inputs.setInputValue("HOME_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "HOME_TIME " + inputs.getInputValue("HOME_TIME"));
		} else if (states.get(1).equalsIgnoreCase("Night")) {
			RelayUtils.Keyfob_Night();
			inputs.setInputValue("NIGHT_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "NIGHT_TIME " + inputs.getInputValue("NIGHT_TIME"));
		} else if (states.get(1).equalsIgnoreCase("Away")) {
			RelayUtils.Keyfob_Away();
			inputs.setInputValue("AWAY_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			System.out.println("#########AWAY_TIME: " + inputs.getInputValue("AWAY_TIME"));
			Keyword.ReportStep_Pass(testCase, "AWAY_TIME " + inputs.getInputValue("AWAY_TIME"));
		} else if (states.get(1).equalsIgnoreCase("Off")) {
			RelayUtils.Keyfob_Off();
			inputs.setInputValue("OFF_TIME", LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
			Keyword.ReportStep_Pass(testCase, "OFF_TIME " + inputs.getInputValue("OFF_TIME"));
		}
		}catch(Exception e){
			System.out.println("Excpetion occured");
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
