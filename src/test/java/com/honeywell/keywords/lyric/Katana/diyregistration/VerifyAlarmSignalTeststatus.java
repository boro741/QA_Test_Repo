package com.honeywell.keywords.lyric.Katana.diyregistration;

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
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class VerifyAlarmSignalTeststatus extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> status;
	public boolean flag = true;

	public VerifyAlarmSignalTeststatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> status) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.status = status;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify if alarm signal test from dealer is \"(.*)\"$")
	public boolean keywordSteps() {
		try {
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase); 
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if (dasDIY.isWaitingForAlarmSignalTextVisible()){
				Keyword.ReportStep_Pass(testCase, "Waiting for Alamr Signal Test from Dealer");
				if (chUtil.getConnection()){
					switch (status.get(0).toUpperCase()) {
					case "PARTIALLY CONFIRMED" :{
						int result = chUtil.putAlarmSignalStatus(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"PartiallyCompleted");
						if (result == 202) {
							flag = flag && MobileUtils.isMobElementExists("name", "Alarm Signal " + status.get(0), testCase,450);
//							flag = flag && dasDIY.clickOnDoneButtonInFeatureSetUpCompletedScreen();
							flag = flag && MobileUtils.clickOnElement(testCase, "xpath","//*[contains(@name,'RightButton')]");
							Keyword.ReportStep_Pass(testCase, "Alarm Signal Status chnaged to " + status.get(0));
						}
						break;
					}
					case "CONFIRMED" :{
						int result = chUtil.putAlarmSignalStatus(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"Completed");
						if (result == 202) {
							flag = flag && MobileUtils.isMobElementExists("name", "Alarm Signal " + status.get(0), testCase,45);
//							flag = flag && dasDIY.clickOnDoneButtonInFeatureSetUpCompletedScreen();
							flag = flag && MobileUtils.clickOnElement(testCase, "xpath","//*[contains(@name,'RightButton')]");
							Keyword.ReportStep_Pass(testCase, "Alarm Signal Status chnaged to " + status.get(0));
						}
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Invalid Input: " + status.get(0));
					}
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}


