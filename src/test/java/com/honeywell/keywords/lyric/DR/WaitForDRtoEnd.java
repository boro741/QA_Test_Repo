// Goal : Login into the application and verify the next screen after tapping into login button

package com.honeywell.keywords.lyric.DR;

import java.time.Duration;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class WaitForDRtoEnd extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public WaitForDRtoEnd(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^DR event has ended on user device$")
	public boolean keywordSteps() {
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(10, TimeUnit.SECONDS); fWait.withTimeout(15,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(10));
			fWait.withTimeout(Duration.ofMinutes(15));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				int i = 0;

				public Boolean apply(String a) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					try {
						if (statInfo.isDREventStarted()) {
							System.out.println("Waiting for DR to end. " + i + " seconds completed");
							i = i + 10;
							try {
								if (testCase.getMobileDriver() != null) {
									MobileUtils.isMobElementExists("id", "actionbar_activity_log_image", testCase);
								} else {
									return false;
								}
							} catch (NullPointerException e) {
								return false;
							}
							return false;
						} else {
							System.out.println("DR event has ended");
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Wait For DR To End : DR Event ended");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Wait for DR Event To End : Waited for 15 minutes but DR event did not end");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
