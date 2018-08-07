package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.ArrayList;

import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SchedulingScreen;

public class VerifyScheduleCreatedMultiStat extends Keyword {
	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	String locationName, deviceName;

	public VerifyScheduleCreatedMultiStat(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^verify \"(.+)\" schedule is \"(.+)\" stats$")
	public boolean keywordSteps() throws KeywordException {
		if (exampleData.get(0).equalsIgnoreCase("Geofence")) {
			inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.GEOFENCE_BASED_SCHEDULE);
		} else if (exampleData.get(0).equalsIgnoreCase("Everyday")) {
			inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
			inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
		} else if (exampleData.get(0).equalsIgnoreCase("Weekday and Weekend")) {
			inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
			inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.WEEKDAY_AND_WEEKEND_SCHEDULE);
		}

		if (exampleData.get(1).equalsIgnoreCase("not copied to other")) {
			inputs.setInputValue(InputVariables.SKIP_COPYING, "Yes");
		} else if (exampleData.get(1).equalsIgnoreCase("copied to all other")) {
			inputs.setInputValue(InputVariables.ALL_STAT_COPYING, "Yes");
		} else if (exampleData.get(1).equalsIgnoreCase("copied to selected")) {
			inputs.setInputValue(InputVariables.SPECIFIC_STAT_COPYING, "Yes");
		} else {
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE, "Input not handled");
			flag = false;
		}
		SchedulingScreen ss = new SchedulingScreen(testCase);
		if (ss.isCloseButtonVisible(5)) {
			if (!ss.clickOnCloseButton()) {
				flag = false;
			}
		}
		try {
			if (ss.isBackButtonVisible(10)){
				ss.clickOnBackButton();
			}
			locationName = inputs.getInputValue("LOCATION1_NAME");
			deviceName = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
			inputs.setInputValue("LOCATION1_NAME", inputs.getInputValue("LOCATION2_NAME"));
			String device1Name = inputs.getInputValue("LOCATION2_DEVICE1_NAME");
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			System.out.println(locInfo.getDeviceCountOfLocation());
			for (int i = 1; i <= locInfo.getDeviceCountOfLocation(); i++) {
				String str = inputs.getInputValue("LOCATION2_DEVICE" + i + "_NAME");
				if (str != null && !str.isEmpty()) {
					inputs.setInputValue("LOCATION1_DEVICE1_NAME", str);
					if (!inputs.getInputValue("LOCATION1_DEVICE1_NAME").equals(device1Name)) {
						Dashboard dashBoardScreen=  new Dashboard(testCase);
						flag = flag && dashBoardScreen.selectLocationFromDashBoard(testCase, inputs.getInputValue("LOCATION2_NAME"));
						
						flag = flag && DashboardUtils.selectDeviceFromDashboard(testCase,
								inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
						if (inputs.getInputValue(InputVariables.ALL_STAT_COPYING).equals("Yes")) {
							if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
									.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
								flag = flag & JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs,
										"geofence");
							} else {
								flag = flag
										& JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs, "time");
							}
						} else if (inputs.getInputValue(InputVariables.SKIP_COPYING).equals("Yes")) {
							if (inputs.getInputValue("LOCATION1_DEVICE1_NAME").equals(device1Name)) {
								if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
										.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
									flag = flag & JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs,
											"geofence");
								} else {
									flag = flag & JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs,
											"time");
								}
							} else {
								flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
								if (ss.isNoScheduleTextVisible(5)) {
									Keyword.ReportStep_Pass(testCase, "No schedule screen is displayed on "
											+ inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											" 'No schedule' screen not displayed");
								}

									if (ss.isBackButtonVisible(5)) {
										if (!ss.clickOnBackButton()) {
											flag = false;
										}
									}
								
							}
						} else if (inputs.getInputValue(InputVariables.SPECIFIC_STAT_COPYING).equals("Yes")) {
							if (inputs.getInputValue(InputVariables.STAT_TO_COPY_SCHEDULE)
									.equals(inputs.getInputValue("LOCATION1_DEVICE1_NAME"))
									|| inputs.getInputValue("LOCATION1_DEVICE1_NAME").equals(device1Name)) {
								if (inputs.getInputValue(InputVariables.TYPE_OF_SCHEDULE)
										.equalsIgnoreCase(InputVariables.GEOFENCE_BASED_SCHEDULE)) {
									flag = flag & JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs,
											"geofence");
								} else {
									flag = flag & JasperSchedulingUtils.verifyDisplayedScheduleOnPrimaryCard(testCase, inputs,
											"time");
								}
							} else {
								flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);
								if (ss.isNoScheduleTextVisible(5)) {
									Keyword.ReportStep_Pass(testCase, "No schedule screen is displayed on "
											+ inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
								} else {
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											" 'No schedule' screen not displayed");
								}
								if (!ss.clickOnBackButton()) {
									flag = false;
								}
							}
						}
						if (ss.isCloseButtonVisible(5)) {
							if (!ss.clickOnCloseButton()) {
								flag = false;
							}
						}
					}
				} else {
					break;
				}
			}
			inputs.setInputValue("LOCATION1_DEVICE1_NAME", device1Name);
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
		inputs.setInputValue("LOCATION1_NAME", locationName);
		inputs.setInputValue("LOCATION1_DEVICE1_NAME", deviceName);
		return flag;
	}
}
