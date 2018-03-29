package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.account.information.DeviceInformation;

public class PerformInBackground extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	private TestCaseInputs inputs;
	private ArrayList<String> states;

	public PerformInBackground(TestCases testCase, TestCaseInputs inputs, ArrayList<String> states) {
		this.testCase = testCase;
		this.states = states;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^\"(.+)\" is triggered in the background$")
	public boolean keywordSteps() throws KeywordException {
		Runnable bgnd = new Runnable() {
			public void run() {
				MobileUtils.minimizeApp(testCase, 20);
			}
		};
		Thread t1 = new Thread(bgnd);
		t1.start();
		CHILUtil chUtil = null;
		try {
			chUtil = new CHILUtil(inputs);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
		try {
			if (chUtil.getConnection()) {
				if (states.get(0).equalsIgnoreCase("app")) {
					try {
						MobileUtils.minimizeApp(testCase, 20);
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Exception" + e.getMessage());
					}
				} else if (states.get(0).equalsIgnoreCase("DISMISS ALARM")) {
					try {
						if (chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase) == 202) {
							Keyword.ReportStep_Pass(testCase, "dismissed alarm executed");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not dismiss alarm ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (states.get(0).equalsIgnoreCase("invited user dismiss alarm")) {
					String actualUser = inputs.getInputValue("USERID");
					inputs.setInputValue("USERID", inputs.getInputValue("INVITEDUSER"));
					String actualLocName = inputs.getInputValue("LOCATION1_NAME");
					inputs.setInputValue("LOCATION1_NAME", "Home");
					try {
						chUtil = new CHILUtil(inputs);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						if (chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase) == 202) {
							Keyword.ReportStep_Pass(testCase, "invited user  - dismissed alarm executed");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not dismiss alarm ");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					inputs.setInputValue("USERID", actualUser);
					inputs.setInputValue("LOCATION1_NAME", actualLocName);
				} else if (states.get(0).equalsIgnoreCase("invited user changes mode to HOME")) {
					String actualUser = inputs.getInputValue("USERID");
					inputs.setInputValue("USERID", inputs.getInputValue("INVITEDUSER"));
					String actualLocName = inputs.getInputValue("LOCATION1_NAME");
					inputs.setInputValue("LOCATION1_NAME", "Home");
					try {
						chUtil = new CHILUtil(inputs);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					locInfo = new LocationInformation(testCase, inputs);
					deviceInfo = new DeviceInformation(testCase, inputs);
					int result = 0;
					try {
						result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(), "Home",
								testCase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to home");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in home : " + result);
					}
					inputs.setInputValue("USERID", actualUser);
					inputs.setInputValue("LOCATION1_NAME", actualLocName);
				} else if (states.get(0).equalsIgnoreCase("SWITCHED TO HOME")
						|| states.get(0).equalsIgnoreCase("HOME MODE")) {
					int result = 0;
					try {
						result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(), "Home",
								testCase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to home");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in home : " + result);
					}
				} else if (states.get(0).equalsIgnoreCase("AWAY MODE")) {
					int result = 0;
					try {
						result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(), "AWAY",
								testCase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to AWAY MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in AWAY MODE : " + result);
					}
				} else if (states.get(0).equalsIgnoreCase("NIGHT MODE")
						|| states.get(0).equalsIgnoreCase("SWITCHED TO NIGHT")) {
					int result = 0;
					try {
						result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(), "NIGHT",
								testCase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to NIGHT MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in NIGHT MODE : " + result);
					}
				} else if (states.get(0).equalsIgnoreCase("OFF MODE")) {
					int result = 0;
					try {
						result = chUtil.switchToOff(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (result == 202) {
						Keyword.ReportStep_Pass(testCase, "Base station is set to NIGHT MODE");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not set base station in NIGHT MODE : " + result);
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Input not handled");
				}
				while (t1.isAlive()) {
					Keyword.ReportStep_Pass(testCase, "App in backgrond");
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
