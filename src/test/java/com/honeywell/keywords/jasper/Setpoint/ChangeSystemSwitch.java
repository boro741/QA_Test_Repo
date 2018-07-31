package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.VacationHoldScreen;

public class ChangeSystemSwitch extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public ChangeSystemSwitch(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user change the (.*) from (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		 if(vhs.ClickOnModeText()) {
			 Keyword.ReportStep_Pass(testCase,
						"Mode is been clicked");
			
			 switch(exampleData.get(0).toUpperCase()) {
			 case "OFF":{
				flag&=vhs.ClickOnSystemModes("OffMode");
				 break;
			 }
			 case "AUTO":{
					flag&=vhs.ClickOnSystemModes("AutoMode");
					 break;
				 }
			 case "HEAT":{
					flag&=vhs.ClickOnSystemModes("HeatMode");
					 break;
				 }
			 case "COOL":{
					flag&=vhs.ClickOnSystemModes("CoolMode");
					 break;
				 }
			 }
			if(flag) {
				 Keyword.ReportStep_Pass(testCase,
							"User Change Mode is been clicked");
			}
			else {
				flag=false;
				 Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Unable to change the Mode");
			}
		 }
		 else {
			 flag=false;
			 Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Unable to click the Mode");
		 }
		 return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}


}
