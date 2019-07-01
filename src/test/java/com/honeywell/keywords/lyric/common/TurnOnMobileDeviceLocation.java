package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASNotificationUtils;
import com.honeywell.screens.MobileDeviceSettingsScreen;
import com.honeywell.screens.OSPopUps;

public class TurnOnMobileDeviceLocation extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public TurnOnMobileDeviceLocation(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user turns on the mobile device location$")
	public boolean keywordSteps() {
		OSPopUps osp = new OSPopUps(testCase);
		MobileDeviceSettingsScreen mds= new MobileDeviceSettingsScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (mds.isMobileDeviceLocationDisabled()) {
				flag &= mds.clickOnMobileDeviceLocationToEnable();
				if (flag) {
					if(osp.isAgreeButtonInMobileDeviceSettingsPopupVisible(10)) {
					osp.clickOnAgreeButtonInMobileDeviceSettingsPopup();
					}
					testCase.getMobileDriver().launchApp();
					Keyword.ReportStep_Pass(testCase, "Launching app to continue testing");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Launching app failed");
				}

			} else if (mds.isMobileDeviceLocationEnabled()) {
				testCase.getMobileDriver().launchApp();
				Keyword.ReportStep_Pass(testCase, "Launching app to continue testing");
			}
		} else {
			// iOS
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}
