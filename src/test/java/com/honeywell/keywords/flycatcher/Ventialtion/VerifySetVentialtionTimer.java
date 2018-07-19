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
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class VerifySetVentialtionTimer extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifySetVentialtionTimer(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^Ventilation Timer is Set to \"(.+)\"$")
	public boolean keywordSteps() {
		try {
			int ExpectedtimerValue = Integer.parseInt(exampleData.get(0));
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int ventilationsTimer = statInfo.getVentilationTimerValue();
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase); 
			if (fly.isVentilationTimerText()){
				int AppValue = fly.getVentilationTimeValue();
				if ( AppValue == ventilationsTimer && ExpectedtimerValue == AppValue){
					Keyword.ReportStep_Pass(testCase,
							"Ventialtion timer Value is changed to " + ExpectedtimerValue);
					return true;
				}
			} else{
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						" Ventilation timer is not displayed ");
			}
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

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
				ReportStep_Pass(testCase, "Verify Vewntilation Timer value : Keyword successfully executed");
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Vewntilation Timer value : Keyword failed during execution");
			}
		} catch (Exception e) {
			flag = false;
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Change System Mode : Error Occured while executing post-condition : " + e.getMessage());
		}
		return flag;
	}

}
