package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecuritySolutionCardScreen;

public class ChangeSystemModesBasedOnDelay extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public DataTable  data;
	private TestCaseInputs inputs;

	public ChangeSystemModesBasedOnDelay(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters,DataTable data) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs=inputs;
		this.data=data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the (.*) should be delay with (.*)$")
	public boolean keywordSteps() throws KeywordException {
		
		@SuppressWarnings("unused")
		BaseStationSettingsScreen bs=new BaseStationSettingsScreen(testCase);
		Dashboard d = new Dashboard(testCase);
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		try {
			if (parameters.get(0).equalsIgnoreCase("System Modes"))
			{
				for (int i = 0; i < data.getSize(); i++) {
					try {
						@SuppressWarnings("resource")
						CHILUtil chUtil = new CHILUtil(inputs);
						LocationInformation locInfo = new LocationInformation(testCase, inputs);
						DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
						if (deviceInfo.isOnline()) {

							if (chUtil.getConnection()) {
								switch (data.getData(i, "ChangedModes").toUpperCase()) {
								case "AWAY": {
									int result = chUtil.setBaseStationMode(locInfo.getLocationID(), deviceInfo.getDeviceID(),
											"Away", testCase);
									if (result == 202) {
										Keyword.ReportStep_Pass(testCase, "Base station is set to Away Mode");
										String toastMessageText=LyricUtils.getToastMessage(testCase);
										if(toastMessageText.contains(parameters.get(1))) {
											flag=true;
											Keyword.ReportStep_Pass(testCase, "Away mode shows the exit delay"+parameters.get(1)+"as expected.");
										}
										else {
											flag=false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Expected exit delay is not shown in Away mode:"+parameters.get(1));
										}
										try {
											
											if (testCase.getMobileDriver() == null) {
												return flag;
											} else {
												if (d.isSecurityStatusLabelVisible()) {
													String currentStatus = d.getSecurityStatusLabel();
													System.out.println(
															"#############currentStatus in dashbaord screen: " + currentStatus);
													if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
															parameters.get(1).toUpperCase(), 2)) {
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
										String toastMessageText=LyricUtils.getToastMessage(testCase);
										if(toastMessageText.contains(parameters.get(1))) {
											flag=true;
											Keyword.ReportStep_Pass(testCase, "Night mode shows the exit delay"+parameters.get(1)+"as expected.");
										}
										else {
											flag=false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Expected exit delay is not shown in Night mode:"+parameters.get(1));
										}
										
										try {
											
											if (testCase.getMobileDriver() == null) {
												return flag;
											} else {
												if (d.isSecurityStatusLabelVisible()) {
													String currentStatus = d.getSecurityStatusLabel();
													System.out.println(
															"#############currentStatus in dashbaord screen: " + currentStatus);
													if (DashboardUtils.waitForSecurityStatusToUpdate(testCase,
															parameters.get(1).toUpperCase(), 2)) {
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
					
			}
							}
						}
					}
				catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
		}
		}
		catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		
			
					return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
