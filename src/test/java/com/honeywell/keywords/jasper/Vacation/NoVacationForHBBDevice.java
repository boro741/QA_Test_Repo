package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import org.json.JSONObject;

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
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.VacationHoldScreen;

public class NoVacationForHBBDevice extends Keyword {
	
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public NoVacationForHBBDevice(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user with HBB is not listed under the review vacation settings in the location$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
			try {
				CHILUtil chUtil = new CHILUtil(inputs);
				JSONObject json= LyricUtils.getDeviceInformation(testCase, inputs,inputs.getInputValue("HBDeviceId"));
				String hbDeviceId=json.getString("deviceID");
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				long locationID = locInfo.getLocationID();
				String toastMessageText=LyricUtils.getToastMessage(testCase);
				if(!toastMessageText.contains(hbDeviceId)) {
					flag=true;
					Keyword.ReportStep_Pass(testCase, "HBB is not listed under review");
				}
				else {
					flag=false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "HBB Listed in review for vacation");
				}
				
			}
			catch(Exception ex) {
				flag=false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured : " + ex.getMessage());
			}
	   return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}

