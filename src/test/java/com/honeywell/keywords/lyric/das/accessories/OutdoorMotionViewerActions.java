package com.honeywell.keywords.lyric.das.accessories;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSensorUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class OutdoorMotionViewerActions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> states;
	public boolean flag = true;

	public OutdoorMotionViewerActions(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
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
	@KeywordStep(gherkins = "^user outdoor motion viewer \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			if (states.get(0).equalsIgnoreCase("Tampered") || states.get(0).equalsIgnoreCase("Cover Tampered")) {
				DASSensorUtils.tamperOSMV(testCase, inputs);
			} else if (states.get(0).equalsIgnoreCase("Tamper cleared")) {
				DASSensorUtils.tamperClearOSMV(testCase, inputs);
			} else if (states.get(0).equalsIgnoreCase("Tamper Restored")) {
				DASSensorUtils.tamperClearOSMV(testCase, inputs);
				DASSensorUtils sensorUtils = new DASSensorUtils();
				sensorUtils.verifySensorState(testCase, inputs, "OSMV", "tamper cleared");
			} else if (states.get(0).equalsIgnoreCase("enrolled")) {
				// DASSensorUtils.enrollMotionSensor(testCase, inputs);
			} else if (states.get(0).equalsIgnoreCase("Motion detected")) {
				inputs.setInputValue("MOTION_DETECTED_TIME",
						LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
				System.out.println("Move the object in front of motion sensor");
			} else if (states.get(0).equalsIgnoreCase("Motion not detected")) {
				System.out.println("Do not move any object in front of OSMV sensor");
			} else if (states.get(1).equalsIgnoreCase("opened") || states.get(1).equalsIgnoreCase("open")) {
				DASSensorUtils.openOSMV(testCase, inputs);
			} else if (states.get(1).equalsIgnoreCase("closed")) {
				DASSensorUtils.closeOSMV(testCase, inputs);
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Input not handled");
			}
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