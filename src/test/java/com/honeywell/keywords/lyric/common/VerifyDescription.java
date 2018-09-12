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
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.SensorSettingScreen;
import com.honeywell.screens.SensorStatusScreen;

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
		}else if (expectedScreen.get(0).equalsIgnoreCase("SENSORSNOISSUE")) {
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		    flag = flag & sc.isSensorNoIssueVisible();
		}else if (expectedScreen.get(0).equalsIgnoreCase("LIVING ROOM COVER TAMPERED")) {
		    SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		    flag = flag & sc.isCoverTamperedTextVisibleinSecuritySolutions();   
		}else if (expectedScreen.get(0).equalsIgnoreCase("COVER TAMPERED")) {
		    SensorStatusScreen ssc = new SensorStatusScreen(testCase);
		    flag = flag & ssc.isCoverTamperedTextVisibleinSensorStatusScreen(testCase, inputs);
		}else if (expectedScreen.get(0).equalsIgnoreCase("SOLUTION CARD OFFLINE STATUS")) {
			SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
		    flag = flag & ssc.isSensorOffline();
		}else if (expectedScreen.get(0).equalsIgnoreCase("OFFLINE STATUS")) {
			SensorStatusScreen ssc = new SensorStatusScreen(testCase);
		    flag = flag & ssc.isSensorOfflineInStatus();
		}else if (expectedScreen.get(0).equalsIgnoreCase("FRONT DOOR OPEN")) {
			SecuritySolutionCardScreen ssc = new SecuritySolutionCardScreen(testCase);
		    flag = flag & ssc.isDoorOpenTextVisible();
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