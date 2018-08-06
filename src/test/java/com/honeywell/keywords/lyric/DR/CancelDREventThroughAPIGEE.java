package com.honeywell.keywords.lyric.DR;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class CancelDREventThroughAPIGEE extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;

	public CancelDREventThroughAPIGEE(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^DR event is cancelled by the utility provider$")
	public boolean keywordSteps() throws KeywordException {
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int eventID = statInfo.getDREventID();
			if (eventID != -1) {
				try {
					String result = CHILUtil.cancelDREvent(inputs, eventID, statInfo.getDeviceID());
					if (result.contains("Failed")) {
						result = CHILUtil.cancelDREvent(inputs, eventID, statInfo.getDeviceID());
						if (result.contains("Failed")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Cancel DR Event : " + result);
							return flag;
						} else {
							Keyword.ReportStep_Pass(testCase, "Cancel DR Event : " + result);
						}
					} else {
						Keyword.ReportStep_Pass(testCase, "Cancel DR Event : " + result);
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Cancel DR Event : " + e.getMessage());
					return flag;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Cancel DR Event Through APIGEE : Could not cancel DR Event since no DR event is running");
				return false;
			}
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(10, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				int i = 0;

				public Boolean apply(String a) {
					DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
					try {
						if (!statInfo.isDREventStarted()) {
							System.out.println("DR Event Cancelled");
							return true;
						} else {
							System.out.println("Cancelling DR Event. " + i + " seconds completed");
							statInfo = new DeviceInformation(testCase, inputs);
							int eventID = statInfo.getDREventID();
							if (eventID != -1) {
								try {
									CHILUtil.cancelDREvent(inputs, eventID, statInfo.getDeviceID());
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Error Occured : " + e.getMessage());
									return false;
								}
							}
							i = i + 10;
							try {
								if (testCase.getMobileDriver() != null) {
									MobileUtils.isMobElementExists("id", "actionbar_activity_log_image", testCase,3);
								} else {
									return false;
								}
							} catch (NullPointerException e) {
								return false;
							} catch (Exception e) {
								return false;
							}
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Trigger DR To Start : DR Event has been cancelled");
			}

		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"DR event is not cancelled in CHIL after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
