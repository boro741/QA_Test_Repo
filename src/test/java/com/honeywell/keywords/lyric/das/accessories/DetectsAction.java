package com.honeywell.keywords.lyric.das.accessories;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class DetectsAction extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;

	public DetectsAction(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) detects (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("DAS camera")) {
				if (parameters.get(1).equalsIgnoreCase("Motion")) {
					System.out.println("Move the object in front of das camera");
				}else {
					flag=false;
				}
			} else if (parameters.get(0).equalsIgnoreCase("Sensor")) {
				if (parameters.get(1).equalsIgnoreCase("Motion")) {
					inputs.setInputValue("MOTION_DETECTED_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					System.out.println("Move the object in front of motion sensor");
				}else {
					flag=false;
				}
			}else if (parameters.get(0).equalsIgnoreCase("indoor motion viewer")) {
				if (parameters.get(1).equalsIgnoreCase("Motion")) {
					inputs.setInputValue("INDOORMOTION_DETECTED_TIME",LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT"));
					System.out.println("Move the object in front of indoor motion viewer");
				}else {
					flag=false;
				}
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,"Detected motion");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase,
						FailType.FUNCTIONAL_FAILURE,
						"Failed to detect "+parameters.get(1)+" through "+parameters.get(0));
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
