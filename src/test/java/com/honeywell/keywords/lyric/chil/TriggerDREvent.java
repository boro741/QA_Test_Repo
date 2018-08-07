// Goal : Login into the application and verify the next screen after tapping into login button


package com.honeywell.keywords.lyric.chil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.lyric.utils.LyricUtils;


public class TriggerDREvent extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;

	public static String EVENTID = "eventID";
	public static String DREVENTSTARTTIME = "dreventstarttime";
	public boolean flag = true;

	public TriggerDREvent(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			int eventID = statInfo.getDREventID();
			if (eventID != -1) {
				try {
					String result = CHILUtil.cancelDREvent(inputs,eventID, statInfo.getDeviceID());
					if (result.contains("Failed")) {
						result = CHILUtil.cancelDREvent(inputs,eventID, statInfo.getDeviceID());
						if (result.contains("Failed")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Trigger DR Event : " + result);
							return flag;
						} else {
							Keyword.ReportStep_Pass(testCase, "Trigger DR Event : " + result);
						}
					} else {
						Keyword.ReportStep_Pass(testCase, "Trigger DR Event : " + result);
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Trigger DR Event : " + e.getMessage());
				}
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
									CHILUtil.cancelDREvent(inputs,eventID, statInfo.getDeviceID());
								} catch (Exception e) {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Error Occured : " + e.getMessage());
									return false;
								}
							}
							i = i + 10;
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
					"DR event is not cancelled in CHIL even after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has triggered DR event with \"(.+)\" and \"(.+)\" opt-out able for \"(.+)\" minutes and \"(.+)\" minutes from now$")
	public boolean keywordSteps() {
		try {
			int minsFromNow = Integer.parseInt(exampleData.get(3));
			String duration = exampleData.get(2);
			HashMap<String, String> headerValues = new HashMap<String, String>();
			String startTime = LyricUtils.addMinutesToDate(testCase, JasperSetPoint.getCurrentUTCTime(testCase),
					minsFromNow)+ ".000Z";
			headerValues.put("startTime", startTime);
			headerValues.put("randomizationInterval", "0");
			if (exampleData.get(1).equals("is")) {
				headerValues.put("isOptOutable", "true");
				headerValues.put("isLocallyOptOutable", "true");
			} else {
				headerValues.put("isOptOutable", "false");
				headerValues.put("isLocallyOptOutable", "false");
			}
			if (exampleData.get(0).equals("duty cycle")) {
				headerValues.put("phase1_dutyCycle", "1.0");
			} else if (exampleData.get(0).equals("offset")) {
				headerValues.put("phase1_dutyCycle", "null");
				headerValues.put("phase1_heatDelta", "1");
				headerValues.put("phase1_coolDelta", "1");
			}
			if (Integer.parseInt(duration) > 0 && Integer.parseInt(duration) < 10) {
				duration = "0" + duration;
			}
			headerValues.put("phase1_duration", "00:" + duration + ":00");
			headerValues.put("dutyCyclePeriod", "Min10");
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			headerValues.put("devices", statInfo.getDeviceID());
			String eventID = CHILUtil.postDREvent(inputs,headerValues);
			if (eventID.equals("")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Trigger DR Event : Failed to trigger DR Event");
			} else {
				inputs.setInputValue(TriggerDREvent.EVENTID, eventID);
				inputs.setInputValue(TriggerDREvent.DREVENTSTARTTIME, startTime);
				Keyword.ReportStep_Pass(testCase, "Trigger DR Event : Successfully triggered DR Event with ID : "
						+ eventID + " starting at " + startTime);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Trigger DR Event : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
