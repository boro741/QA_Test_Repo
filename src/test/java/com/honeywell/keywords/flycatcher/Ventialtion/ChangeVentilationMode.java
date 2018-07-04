package com.honeywell.keywords.flycatcher.Ventialtion;

import java.util.ArrayList;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class ChangeVentilationMode extends Keyword {



	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public ChangeVentilationMode(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		super("Change Ventilation Mode");
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
	@KeywordStep(gherkins = "^user changes Vantilation mode to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			String mode = exampleData.get(0);
			String expectedMode = " ";
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String allowedModes = statInfo.getThermoStatVentilationMode();
			if (mode.equalsIgnoreCase("On")) {
				if (!allowedModes.contains("On")) {
					expectedMode = "On";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change Ventilation Mode : Mode is already in On");
					return true;
				}
			} else if (mode.equalsIgnoreCase("Auto")) {
				if (!allowedModes.equalsIgnoreCase("Auto")) {
					expectedMode = "Auto";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change Ventilation Mode : Mode is already in Auto");
					return true;
				}
			} else if (mode.equalsIgnoreCase("off")) {
				if (!allowedModes.contains("off")) {
					expectedMode = "off";
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Change Ventilation Mode : Mode is already in Off");
					return true;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Invalid input : " + mode);
			}
			flag = flag & FlyCatcherVentialtion.changeVentilationMode(testCase, inputs, expectedMode);
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
