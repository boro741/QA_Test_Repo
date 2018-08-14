package com.honeywell.keywords.lyric.DR;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openqa.selenium.By;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.chil.TriggerDREvent;
import com.honeywell.lyric.DR.utils.DRUtils;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.ActivityHistoryScreen;

import io.appium.java_client.MobileElement;

public class VerifyDRMessage extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;

	public boolean flag = true;

	public VerifyDRMessage(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user receives and views a \"(.*)\" message on the \"(.*)\" screen$")
	public boolean keywordSteps() {
		try {
			flag = flag & DRUtils.waitForProgressBarToComplete(testCase,"Messages", 1);
			ActivityHistoryScreen ahs = new ActivityHistoryScreen(testCase);
			if (ahs.isMessagesDisplayed()) {

			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No Messages found");
			}

			boolean isMessagePresent = false;
			HashMap<String, String> combinedMessage = ahs.getAllMessages(testCase);
			String UTCStartTime = inputs.getInputValue(TriggerDREvent.DREVENTSTARTTIME);
			String deviceDRStartTime = LyricUtils.getDeviceEquivalentUTCTime(testCase, inputs, UTCStartTime);
			String currentDeviceTime = LyricUtils.getDeviceTime(testCase, inputs);
			String UTCEndTime = "";
			String deviceDREndTime = "";
			String message = "";
			String message2 = "";
			String locationAndStatName = "";
			if (!inputs.getInputValue(WaitForDRtoStart.DREVENTENDTIME).equals("")) {
				UTCEndTime = inputs.getInputValue(WaitForDRtoStart.DREVENTENDTIME) + ".000Z";
				deviceDREndTime = LyricUtils.getDeviceEquivalentUTCTime(testCase, inputs, UTCEndTime);
			}
			if (exampleData.get(0).equalsIgnoreCase("saving event schedule")) {
				String deviceDay;
				String deviceDay1;
				if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
					deviceDay = "today";
					deviceDay1 = "Today";
				} else {
					deviceDay = deviceDRStartTime.split("T")[0];
					deviceDay1 = deviceDRStartTime.split("T")[0];
				}
				message = "A Savings Event is scheduled for " + deviceDay + " at " + deviceDRStartTime.split("T")[1]
						+ ". During this time, your thermostats will be optimized by your utility provider.";
				message2 = "A Savings Event is scheduled for " + deviceDay1 + " at "
						+ deviceDRStartTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.")
						+ ". During this time, your thermostats will be optimized by your utility provider.";

				for (Entry<String, String> s : combinedMessage.entrySet()) {
					System.out.println(s.getValue());
					if (s.getKey().toUpperCase().contains("SCHEDULED")) {
						if (s.getValue().contains(message) || (s.getValue().contains(message2))) {
							isMessagePresent = true;
							Keyword.ReportStep_Pass(testCase,
									"Verify DR Message : Savings Event Scheduled DR Message displayed with correct values");
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event Scheduled title not displayed correctly on message details screen");
						}
					}
					else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify DR Message : Savings Event Scheduled message is not displayed");
					}
					
				}

			} 
			else if (exampleData.get(0).equalsIgnoreCase("saving event started")) {
				String deviceDay;
				String deviceDay1;
				if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
					deviceDay = "today";
					deviceDay1 = "Today";
				} else {
					deviceDay = deviceDRStartTime.split("T")[0];
					deviceDay1 = deviceDRStartTime.split("T")[0];
				}
				message = "Event started " + deviceDay + " at " + deviceDRStartTime.split("T")[1];
				message2 = "Event started " + deviceDay + " at "
						+ deviceDRStartTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.");

				for (Entry<String, String> s : combinedMessage.entrySet()) {
					System.out.println(s.getValue());
					if (s.getKey().toUpperCase().contains("STARTED")) {
						if (s.getValue().contains(message) || (s.getValue().contains(message2))) {
							isMessagePresent = true;
							Keyword.ReportStep_Pass(testCase,
									"Verify DR Message : Savings Event Started DR Message displayed with correct values");
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event Started title not displayed correctly on message details screen");
						}
					}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event Started message is not displayed");
					}
				}

			}
			else if (exampleData.get(0).equalsIgnoreCase("saving event ended")) {
				String deviceDay;
				String deviceDay1;
				if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
					deviceDay = "today";
					deviceDay1 = "Today";
				} else {
					deviceDay = deviceDRStartTime.split("T")[0];
					deviceDay1 = deviceDRStartTime.split("T")[0];
				}
				message = "Event ended today at " + deviceDREndTime.split("T")[1];
				message2 = "Event ended today at "
						+ deviceDREndTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.");

				for (Entry<String, String> s : combinedMessage.entrySet()) {
					System.out.println(s.getValue());
					if (s.getKey().toUpperCase().contains("ENDED")) {
						if (s.getValue().contains(message) || (s.getValue().contains(message2))) {
							isMessagePresent = true;
							Keyword.ReportStep_Pass(testCase,
									"Verify DR Message : Savings Event Ended DR Message displayed with correct values");
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event Ended title not displayed correctly on message details screen");
						}
					}
					else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify DR Message : Savings Event Ended message is not displayed");
					}
				}

			}
			else if (exampleData.get(0).equalsIgnoreCase("saving event canceled by utility")) {
				String deviceDay;
				String deviceDay1;
				if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
					deviceDay = "today";
					deviceDay1 = "Today";
				} else {
					deviceDay = deviceDRStartTime.split("T")[0];
					deviceDay1 = deviceDRStartTime.split("T")[0];
				}
				message = "Your utility provider has canceled the Savings Event";

				for (Entry<String, String> s : combinedMessage.entrySet()) {
					System.out.println(s.getValue());
					if (s.getKey().toUpperCase().contains("CANCELED")) {
						if (s.getValue().contains(message)) {
							isMessagePresent = true;
							Keyword.ReportStep_Pass(testCase,
									"Verify DR Message : Savings Event cancelled DR Message displayed with correct values");
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event cancelled title not displayed correctly on message details screen");
						}
					}
					else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify DR Message : Savings Event cancelled message is not displayed");
					}
				}

			}
			else if (exampleData.get(0).equalsIgnoreCase("saving event canceled by user")) {
				String deviceDay;
				String deviceDay1;
				if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
					deviceDay = "today";
					deviceDay1 = "Today";
				} else {
					deviceDay = deviceDRStartTime.split("T")[0];
					deviceDay1 = deviceDRStartTime.split("T")[0];
				}
				message = "A user has canceled the Savings Event.";

				for (Entry<String, String> s : combinedMessage.entrySet()) {
					System.out.println(s.getValue());
					if (s.getKey().toUpperCase().contains("CANCELED BY USER")) {
						if (s.getValue().contains(message)) {
							isMessagePresent = true;
							Keyword.ReportStep_Pass(testCase,
									"Verify DR Message : Savings Event Canceled by User DR Message displayed with correct values");
						}
						else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verify DR Message : Savings Event Canceled by User DR Message is not displayed with correct values");
						}
					}
					else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify DR Message : Savings Event Canceled by User message is not displayed");
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
	public boolean postCondition() {
		return flag;
	}

}
