package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.FlyCatcherPrimaryCard;
import com.honeywell.screens.SchedulingScreen;

public class VerifyNextSchedulePeriodActivate extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public VerifyNextSchedulePeriodActivate(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify next schedule period activated$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			if (devInfo.getThermostatType().equalsIgnoreCase("NA")) {
						boolean flag = true;
						FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
								testCase.getMobileDriver());
						fWait.pollingEvery(10, TimeUnit.SECONDS);
						fWait.withTimeout(15, TimeUnit.SECONDS);
						SchedulingScreen FollowingS = new SchedulingScreen(testCase);
						Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
							public Boolean apply(CustomDriver driver) {
								if (FollowingS.isFollowingSchedulesVisible(2)) {
									return true;
								} else {
									return false;
								}
							}
						});
		
						if (isEventReceived) {
							Keyword.ReportStep_Pass(testCase,
									"Temprarory hold disappeared and displayed foolowing schedule");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed : foolowing schedule not displayed");
						}
						return flag;
			} else if (devInfo.getThermostatType().equalsIgnoreCase("EMEA")) {
						boolean flag = true;
						FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
								testCase.getMobileDriver());
						fWait.pollingEvery(10, TimeUnit.SECONDS);
						fWait.withTimeout(10, TimeUnit.SECONDS);
						SchedulingScreen FollowingS = new SchedulingScreen(testCase);
						Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
							public Boolean apply(CustomDriver driver) {
								if (FollowingS.isFollowingSchedulesVisible(2)) {
									return true;
								} else {
									return false;
								}
							}
						});
		
						if (isEventReceived) {
							Keyword.ReportStep_Pass(testCase,
									"Temprarory hold disappeared and displayed foolowing schedule");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed : foolowing schedule not displayed");
						}
						return flag;	
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}