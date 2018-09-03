package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSolutionCardScreen;

public class VerifyisElementExists extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;

	public VerifyisElementExists(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.data = data;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) displayed with options:$")
	public boolean keywordSteps() throws KeywordException {
		CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
		for (int i = 0; i < data.getSize(); i++) {
			String fieldTobeVerified = data.getData(i, "Icons");
			try {
				if(this.parameters.get(0).equalsIgnoreCase("should be"))
				{
					if (fieldTobeVerified.equalsIgnoreCase("Snapshot")) {
						if (!cs.isSanpShotIconExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Snapshot Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Snapshot Option is not displayed");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Talk")) {
						if (!cs.isPushIconExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Talk Option is dispayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Talk Option is not displayed");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Attention")) {
						if (!cs.isAttentionIconExists()) {
//							flag = false;
//							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Attention Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Attention  Option is not displayed");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Live Stream Progress Bar")) {
						if (!cs.isLiveStreamProgressBarExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Live Stream Progress Bar Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Live Stream Progress Bar  Option is not displayed");
						}
					}
					
				}else if(this.parameters.get(0).equalsIgnoreCase("should not be"))
				{
					if (fieldTobeVerified.equalsIgnoreCase("Snapshot")) {
						if (cs.isSanpShotIconExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Snapshot Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Snapshot Option is not displayed");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Talk")) {
						if (cs.isPushIconExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Talk Option is dispayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Talk Option is not displayed");
						}
					} else if (fieldTobeVerified.equalsIgnoreCase("Attention")) {
						if (cs.isAttentionIconExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Attention Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Attention  Option is not displayed");
						}
					}else if (fieldTobeVerified.equalsIgnoreCase("Live Stream Progress Bar")) {
						if (cs.isLiveStreamProgressBarExists()) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Live Stream Progress Bar Option is displayed");
						} else {
							Keyword.ReportStep_Pass(testCase, "Live Stream Progress Bar  Option is not displayed");
						}
					}
					
				}
				
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
			}

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
