package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;
import java.util.List;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperAdhocOverride;

public class ChangeSystemMode extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	private boolean flag = true;

	public ChangeSystemMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes system mode to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			String mode = exampleData.get(0);
			String expectedMode = " ";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			if (mode.equalsIgnoreCase("Heat")) {
				if (allowedModes.contains("Heat")) {
					expectedMode = "Heat";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change System Mode : Cannot change system mode to heat because theremostat does not support heat mode");
					return true;
				}
			} else if (mode.equalsIgnoreCase("Cool")) {
				if (allowedModes.contains("Cool")) {
					expectedMode = "Cool";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change System Mode : Cannot change system mode to cool because theremostat does not support cool mode");
					return true;
				}
			} else if (mode.equalsIgnoreCase("Auto")) {
				if (allowedModes.contains("Auto")) {
					expectedMode = "Auto";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change System Mode : Cannot change system mode to auto because theremostat does not support auto mode");
					return true;
				}
			} else if (mode.equalsIgnoreCase("Off")) {
				if (allowedModes.contains("Off")) {
					expectedMode = "Off";
				} else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Change System Mode : Allowed modes does not have Off");
					return true;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input : " + mode);
			}
			flag = flag & JasperAdhocOverride.changeSystemMode(testCase, inputs, expectedMode);
		} catch (Exception e){

		}
		return flag;
	}

	/*
	 * ========================================After
	 * method============================================================= Check
	 * if the Keyword has been executed successfully.
	 * =========================================================================
	 * ==========================================
	 */
	@Override
	@AfterKeyword
	public boolean postCondition() {

		try {
			if (flag) {
				ReportStep_Pass(testCase, "Change System Mode : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Change System Mode : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}
}
