package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.Dashboard;

public class VerifyCoachMarks extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyCoachMarks(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies the (.*) coach marks$")
	public boolean keywordSteps() throws KeywordException {
		try {
			CoachMarks cm = new CoachMarks(testCase);
			Dashboard dB = new Dashboard(testCase);
			//if (cm.isGotitButtonVisible(15)) {
				switch (parameters.get(0).toUpperCase()) {
				case "DAS DASHBOARD": {
					flag = flag & cm.verifyDashboardCoachMarks(CoachMarks.DAS);
					break;
				}
				case "DAS SOLUTION CARD": {
					flag = flag & cm.verifySolutionCardCoachMarks(CoachMarks.DAS);
					break;
				}
				case "DAS CAMERA SOLUTION CARD": {
					flag = flag & cm.verifySolutionCardCoachMarks(CoachMarks.DASCAMERA);
					break;
				}
				case "THERMOSTAT DASHBOARD": {
					flag = flag & cm.verifyDashboardCoachMarks(CoachMarks.THERMOSTAT);
					break;
				}
				case "NA THERMOSTAT SOLUTION CARD": {
					dB.NavigatetoThermostatPrimarycard();
					flag = flag & cm.verifySolutionCardCoachMarks(CoachMarks.THERMOSTATNA);
					break;
				}
				case "EMEA THERMOSTAT SOLUTION CARD": {
					flag = flag & dB.NavigatetoThermostatPrimarycard();
					flag = flag & cm.verifySolutionCardCoachMarks(CoachMarks.THERMOSTATEMEA);
					break;
				}
				case "ACCESS MORE INFORMATION" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Access More Information")){
						Keyword.ReportStep_Pass(testCase, "Access More Information Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				case "QUICK CONTROLS" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Quick Controls")){
						Keyword.ReportStep_Pass(testCase, "Quick Controls Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				case "HOME MODE" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Home Mode")){
						Keyword.ReportStep_Pass(testCase, "Home Mode Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				case "AWAY MODE" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Away Mode")){
						Keyword.ReportStep_Pass(testCase, "Away Mode Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				case "NIGHT MODE" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Night Mode")){
						Keyword.ReportStep_Pass(testCase, "Night Mode Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				case "OFF MODE" :{
					String getHeader = cm.getCoachMarkHeaderText();
					if (getHeader.equalsIgnoreCase("Off Mode")){
						Keyword.ReportStep_Pass(testCase, "Off Mode Header is displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input");
					}
					break;
				}
				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + parameters.get(0));
				}
				}
//			} else {
//				flag = false;
//				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Coach Marks are not visible");
//			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
