package com.honeywell.screens;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;

public class WLDSolutionCard extends MobileScreens {

	private static final String screenName = "WLD_SolutionCard";
	public WLDSolutionCard(TestCases testCase) {
		super(testCase, screenName);
	}
	public boolean isCurrentTemperatureTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CurrentTemperature");
	}
	public String getCurrentTemperatureTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CurrentTemperature");
	}	
	public boolean isCurrentHumidityTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CurrentHumidity");
	}
	public String getCurrentHumidityTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "CurrentHumidity");
	}	
	public boolean isLastUpdatedTimeTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LastUpdate");
	}
	public String getLastUpdateTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LastUpdate");
	}
	public boolean isTemperatureGraphTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TemperatureGraph");
	}
	public String getTemperatureGraphTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TemperatureGraph");
	}
	public boolean isHumidityGraphTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "HumidityGraph");
	}
	public String getHumidityGraphTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "HumidityGraph");
	}
	public boolean isBatterypercentageTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "RemainingBattery");
	}
	public String getRemainingBatteryTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "RemainingBattery");
	}
	public boolean isNextUpdateTimeTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextUpdateTime");
	}
	public String getNextUpdateTimeTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");
	}
	public boolean clickOnHumidityGraphTitle()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumidityTab");
	}

	public static boolean navigateFromDashboardScreenToWLDSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();			
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: Navigation to Settings Failed" + e.getMessage());
		}
		return flag;
	}

	public boolean navigateFromPrimaryCardToWLDSettingsScreen() {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();			
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
}