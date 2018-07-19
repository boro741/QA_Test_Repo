package com.honeywell.keywords.flycatcher.Ventialtion;

import java.util.ArrayList;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.FlyCatcherPrimaryCard;

public class VerfiyRunVentilationTimerStatus extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	ArrayList<String> exampleData;
	public boolean flag = true;

	public VerfiyRunVentilationTimerStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Verify run ventilation is \"(.+)\" in \"(.+)$")
	public boolean keywordSteps() {
		try
		{
			String Runtimer = exampleData.get(0);
			String ventilationMode = exampleData.get(1);
			FlyCatcherPrimaryCard fly = new FlyCatcherPrimaryCard(testCase); 
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String ventilationstatus = statInfo.getThermoStatVentilationMode();
			if (ventilationstatus != null){
				if (fly.isVentilationIconVisible()){
					flag = flag && fly.ClickOnVentilationButton();
				} else{
					flag = flag && fly.ClickOnMoreButton();
					flag = flag && fly.ClickOnVentilationButton();
				}
				if (ventilationMode.equalsIgnoreCase("Off")) {
					fly.changeVentilationModeToOff();
					if (fly.VentilationTimerOnModes() == true){
						Keyword.ReportStep_Pass(testCase,
								"Ventilation timer is " + Runtimer + " when ventilation mode is " + ventilationMode);
						return true;
					} else{
						flag = false;
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Ventilation timer is disabled");
					}
				}
				else if (ventilationMode.equalsIgnoreCase("On")) {
					fly.changeVentilationModeToOn();
					if (fly.VentilationTimerOnModes() != true){
						Keyword.ReportStep_Pass(testCase,
								"Ventilation timer is " + Runtimer + " when ventilation mode is " + ventilationMode);
						return true;
					} else{
						flag = false;
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Ventilation timer is enabled");
					}
				} 
				else if (ventilationMode.equalsIgnoreCase("Auto")) {
					if (fly.VentilationTimerOnModes() == true){
						Keyword.ReportStep_Pass(testCase,
								"Ventilation timer is " + Runtimer + " when ventilation mode is " + ventilationMode);
						return true;
					} else{
						flag = false;
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Ventilation timer is disabled");
					}
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						" Ventilation Mode is disabled in thermostat ");
			}
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
		}catch (Exception e){

		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		if (flag) 
			ReportStep_Pass(testCase, "VerifyVentilationMode : Keyword successfully executed");			
		else
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "VerifyVentilationMode : Keyword failed during execution");
		return flag;
	}

}
