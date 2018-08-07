package com.honeywell.lyric.DR.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.lyric.chil.TriggerDREvent;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.DRScreens;

public class DRUtils extends MobileScreens {

	private static final String screenName = "DR";

	public DRUtils(TestCases testCase) {
		super(testCase, screenName);
	}

	public static Boolean VerifyDRPopUp(TestCases testCase, TestCaseInputs inputs) {
boolean flag=true;	

		String UTCStartTime = inputs.getInputValue(TriggerDREvent.DREVENTSTARTTIME);
		String deviceDRStartTime = LyricUtils.getDeviceEquivalentUTCTime(testCase, inputs, UTCStartTime);
		String currentDeviceTime = LyricUtils.getDeviceTime(testCase, inputs);
		String deviceDay;
		
		if (deviceDRStartTime.split("T")[0].equals(currentDeviceTime.split("T")[0])) {
			deviceDay = "today";
		} else {
			deviceDay = deviceDRStartTime.split("T")[0];
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			DRScreens dr = new DRScreens(testCase);
			if(dr.isSavingEventTitleDisplayed()){
					Keyword.ReportStep_Pass(testCase,
							"Verifying Savings Event Pop Up Message : Savings Event alert title correctly displayed");
					String message = "A Savings Event is scheduled for " + deviceDay + " at "
							+ deviceDRStartTime.split("T")[1]
						+ ". During this time, your thermostats will be optimized by your utility provider.";
					String message2 = "A Savings Event is scheduled for " + deviceDay + " at "
							+ deviceDRStartTime.split("T")[1].replace("PM", "p.m.").replace("AM", "a.m.")
							+ ". During this time, your thermostats will be optimized by your utility provider.";
						
						if (dr.VerifyDRMessageContent().equalsIgnoreCase(message) || dr.VerifyDRMessageContent().equalsIgnoreCase(message2)) {
							Keyword.ReportStep_Pass(testCase,
									"Verifying Savings Event Pop Up Message : Savings Event alert message is correctly displayed");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Verifying Savings Event Pop Up Message : Savings Event alert message is not correctly displayed. Actual : "
											+ dr.VerifyDRMessageContent()
											+ ". Expected : '" + message + "' or '" + message2 +"'");
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verifying Savings Event Pop Up Message : Savings Event alert title is not correctly displayed");
					}
					flag = flag & dr.ClickOnOkPopup();
				} 
				
			
		// IOS
		else {
			if (MobileUtils.isMobElementExists("name", "Savings Event Scheduled", testCase, 10, false)) {
				Keyword.ReportStep_Pass(testCase,
						"Verifying Savings Event Pop Up Message : Savings Event Alert title correctly displayed");
				SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
				String time=null;
				try {
					time = format.format(format.parse(deviceDRStartTime.split("T")[1]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String message = "A Savings Event is scheduled for " + deviceDay + " at "
						+ time
						+ ". During this time, your thermostats will be optimized by your utility provider.";
				if (MobileUtils.isMobElementExists("name", message, testCase, 5, false)) {
					Keyword.ReportStep_Pass(testCase,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verifying Savings Event Pop Up Message : Savings Event alert message is not correctly displayed. Expected : " + message);
				}
				flag = flag & MobileUtils.clickOnElement(testCase, "name", "OK", false);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verifying Savings Event Pop Up Message : Savings Event alert title is not displayed");
			}
		}
	
	return flag;	
		
	}
}