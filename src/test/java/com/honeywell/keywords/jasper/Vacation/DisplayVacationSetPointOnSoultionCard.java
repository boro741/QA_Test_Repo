package com.honeywell.keywords.jasper.Vacation;

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

public class DisplayVacationSetPointOnSoultionCard extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplayVacationSetPointOnSoultionCard(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user should be displayed with (.*) setpoint value$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		CHILUtil chUtil;
		 DeviceInformation statInfo;
		 
	    switch(exampleData.get(0).toUpperCase()) {
			case "VACATION":{
				try {
					flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					 chUtil = new CHILUtil(inputs);
					 statInfo = new DeviceInformation(testCase, inputs);
					 CHILUtil.setPointInVacationCard=Integer.parseInt(statInfo.getVacationHeatSetPoint());
					if(Integer.parseInt(vhs.GetPrimaryCardValue())==CHILUtil.setPointInVacationCard) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Setpoint is present in Solution card");
						
					}
					else {
						 flag=false;
						 Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Vacation Setpoint is not present in Solution card");
					 }
					
				} catch (Exception e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}
				break;
			}
		}
	 return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}


}
