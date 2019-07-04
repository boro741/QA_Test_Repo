package com.honeywell.keywords.lyric.das.chil;

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
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class SetDASUserModeThroughCHIL extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	private TestCaseInputs inputs;

	public SetDASUserModeThroughCHIL(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user is set to (.*) mode through CHIL$")
	public boolean keywordSteps() throws KeywordException {
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			Dashboard d = new Dashboard(testCase);
			SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
			if (deviceInfo.isOnline()) {

				if (chUtil.getConnection()) {
					switch (parameters.get(0).toUpperCase()) {
					case "HOME": {
						if (chUtil.clearAlarm(locInfo.getLocationID(), deviceInfo.getDeviceID(), testCase) == 202) {
							Keyword.ReportStep_Pass(testCase, "Clearing alarm(if exists) before setting panel mode");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to clear alarm through CHIL");
						}
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"Home", testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to home");
							try {
								if (testCase.getMobileDriver() == null) {
									return flag;
								} else {
									if (d.isSecurityStatusLabelVisible()) {
										String currentStatus = d.getSecurityStatusLabel();
										System.out.println(
												"#############currentStatus in dashbaord screen: " + currentStatus);
										if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
												parameters.get(0).toUpperCase(), 2)) {
											flag = true;
										}
									} else {
										if (sc.isAppSettingsIconVisible(10)) {
											flag = true;
										}
									}
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error Occured: " + e.getMessage());
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in home : " + result);
						}
						break;
					}
					case "AWAY": {
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"Away", testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to Away Mode");
                            
							try {
								Thread.sleep(15000);
								if (testCase.getMobileDriver() == null) {
									return flag;
								} else {
									if (d.isSecurityStatusLabelVisible()) {
										String currentStatus = d.getSecurityStatusLabel();
										System.out.println(
												"#############currentStatus in dashbaord screen: " + currentStatus);
										if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
												parameters.get(0).toUpperCase(), 2)) {
											flag = true;
										}
									} else {
										if (sc.isAppSettingsIconVisible(10)) {
											flag = true;
										}
									}
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error Occured: " + e.getMessage());
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in AWAY MODE : " + result);
						}
						
						break;
					}
					case "NIGHT": {
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),
								"Night", testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to Night Mode");
							try {
								Thread.sleep(15000);
								if (testCase.getMobileDriver() == null) {
									return flag;
								} else {
									if (d.isSecurityStatusLabelVisible()) {
										String currentStatus = d.getSecurityStatusLabel();
										System.out.println(
												"#############currentStatus in dashbaord screen: " + currentStatus);
										if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
												parameters.get(0).toUpperCase(), 2)) {
											flag = true;
										}
									} else {
										if (sc.isAppSettingsIconVisible(10)) {
											flag = true;
										}
									}
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error Occured: " + e.getMessage());
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in NIGHT MODE : " + result);
						}
						
						break;
					}
					case "OFF": {
						int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(), "Off",
								testCase);
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Base station is set to OFF Mode");
							try {
								if (testCase.getMobileDriver() == null) {
									return flag;
								} else {
									if (d.isSecurityStatusLabelVisible()) {
										String currentStatus = d.getSecurityStatusLabel();
										System.out.println(
												"#############currentStatus in dashbaord screen: " + currentStatus);
										if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
												parameters.get(0).toUpperCase(), 2)) {
											flag = true;
										}
									} else {
										if (sc.isAppSettingsIconVisible(10)) {
											flag = true;
										}
									}
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error Occured: " + e.getMessage());
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set base station in NIGHT MODE : " + result);
						}
						break;
					}case "SENSOR ALERT ENABLED": {
						String isActive = "Enabled";
						int result = chUtil.putAlertStatusForSensor(locInfo.getLocationID(), deviceInfo.getDeviceID(), isActive);
						try {
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase, "Sensor Alert is Enabled");
							}
						else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set sensor alert to enabled state: " + result);
						}
					}
						catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured: " + e.getMessage());
						}
						break;
					} case "SENSOR ALERT DISABLED":{

						String isActive = "Disabled";
						int result = chUtil.putAlertStatusForSensor(locInfo.getLocationID(), deviceInfo.getDeviceID(), isActive);
						try {
						if (result == 200) {
							Keyword.ReportStep_Pass(testCase, "Sensor Alert is Disabled");
							}
						else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not set sensor alert to disabled state: " + result);
						}
					}
						catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured: " + e.getMessage());
						}
						break;
					}
					case "SENSOR ENROLLMENT ENABLED":{
						boolean isEnabled = true;
						int result = chUtil.postSensorDiscovery(locInfo.getLocationID(), deviceInfo.getDeviceID(), isEnabled);
						try {
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Sensor Discovery mode is Enabled");
							}
						else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not enable sensor discovery mode: " + result);
						}
					}
						catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured: " + e.getMessage());
						}
						break;
					}
					case "SENSOR ENROLLMENT DISABLED":{
						boolean isEnabled = false;
						int result = chUtil.postSensorDiscovery(locInfo.getLocationID(), deviceInfo.getDeviceID(), isEnabled);
						try {
						if (result == 202) {
							Keyword.ReportStep_Pass(testCase, "Sensor Discovery mode is Disabled");
							}
						else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not disable sensor discovery mode: " + result);
						}
					}
						catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured: " + e.getMessage());
						}
						break;
					}
					default: {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Input not handled " + parameters.get(0).toUpperCase());
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
