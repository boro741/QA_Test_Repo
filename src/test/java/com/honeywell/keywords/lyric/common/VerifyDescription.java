package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;

public class VerifyDescription extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyDescription(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the (.*) description$")
	public boolean keywordSteps() throws KeywordException {
		if (expectedScreen.get(0).equalsIgnoreCase("AUTO FAN")) {
			PrimaryCard thermo = new PrimaryCard(testCase);
			flag = flag & thermo.isAutoFanDefinitionVisibleOnChangeFanScreen();

		} else if (expectedScreen.get(0).equalsIgnoreCase("CIRCULATE")) {
			PrimaryCard thermo = new PrimaryCard(testCase);
			flag = flag & thermo.isCirculateFanDefinitionVisibleOnChangeFanScreen();

		} else if (expectedScreen.get(0).equalsIgnoreCase("ON")) {
			PrimaryCard thermo = new PrimaryCard(testCase);
			flag = flag & thermo.isOnFanDefinitionVisibleOnChangeFanScreen();
		} else if (expectedScreen.get(0).equalsIgnoreCase("AUTOMODE")) {
			PrimaryCard thermo1 = new PrimaryCard(testCase);
			flag = flag & thermo1.isAutoDefinitionVisible();

		} else if (expectedScreen.get(0).equalsIgnoreCase("HEAT")) {
			PrimaryCard thermo1 = new PrimaryCard(testCase);
			flag = flag & thermo1.isHeatDefinitionVisibleOnChangeModeScreen();

		} else if (expectedScreen.get(0).equalsIgnoreCase("OFF")) {
			PrimaryCard thermo1 = new PrimaryCard(testCase);
			flag = flag & thermo1.isOffDefinitionVisibleOnChangeModeScreen();

		} else if (expectedScreen.get(0).equalsIgnoreCase("COOL")) {
			PrimaryCard thermo1 = new PrimaryCard(testCase);
			flag = flag & thermo1.isCoolDefinitionVisibleOnChangeModeScreen();
		
		} else if (expectedScreen.get(0).equalsIgnoreCase("NO CLIPS AVAILABLE")) {
			CameraSolutionCardScreen soutionCard = new CameraSolutionCardScreen(testCase);
			flag = flag & soutionCard.isNoClipsTextAvailable();
		
		} else if (expectedScreen.get(0).equalsIgnoreCase("CAMERA IS ON")) {
			Dashboard dash = new Dashboard(testCase);
			flag = flag & dash.isCameraOnTextVisible();
			
		} else if (expectedScreen.get(0).equalsIgnoreCase("CAMERA IS OFF")) {
		    Dashboard dash = new Dashboard(testCase);
		    flag = flag & dash.isCameraOffTextVisible();
		    
		}else if (expectedScreen.get(0).equalsIgnoreCase("SAVED SNAPSHOT MESSAGE")) {
		    CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
		    flag = flag & cs.isSanpShotSavedTextExists();
		}
	
		
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Mode: " + expectedScreen.get(0) + " description is present");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Mode: " + expectedScreen.get(0) + " description is not present");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}