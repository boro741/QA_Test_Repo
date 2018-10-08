package com.honeywell.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.FluentWait;
import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;

public class WLDLeakDetectorSettings extends MobileScreens {
	public boolean flag = true;
	private static final String screenName = "WLD_Settings";
	public WLDLeakDetectorSettings(TestCases testCase) {
		super(testCase, screenName);
	}
	//Manage Alerts
	public boolean isManageAlertsTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Managealerts");
	}
	public String displayManageAlertsTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "Managealerts");
	}
	public boolean clickonManagealerts()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Managealerts");
	}
	//Battery Status
	public boolean isBatteryStatusTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BatteryStatus");
	}
	public String displayBatteryStatusTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BatteryStatus");
	}	
	public boolean isBatteryStatusPercentageVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BatteryStatusPercentage");
	}
	public String displayBatteryStatusPercentageText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "BatteryStatusPercentage");
	}
	//Temperature Unit
	public boolean isTemperatureUnitTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TemperatureUnitTitle");
	}
	public String dislplayTemperatureUnitTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TemperatureUnitTitle");
	}
	public boolean isTemperatureUnitVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TemperatureUnit");
	}
	public String dislplayTemperatureUnitValue()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "TemperatureUnit");
	}
	public boolean clickonTemperatureUnit()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "TemperatureUnit");
	}
	//Update Frequency
	public boolean isUpdateFrequencyTitleTextVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateFrequencyText");
	}
	public String displayUpdateFrequencyTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "UpdateFrequencyText");
	}
	public boolean isUpdateFrequencyValueVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "UpdateFrequencyValue");
	}
	public String displayUpdateFrequencyValue()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "UpdateFrequencyValue");
	}
	public boolean clickonUpdateFrequencyTitleText()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "UpdateFrequencyText");
	}
	//Leak Detector Configuration
	public boolean isLeakDetectorConfigurationTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LeakDetectorConfiguration");
	}
	public String displayLeakDetectorConfigurationTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "LeakDetectorConfiguration");
	}
	public boolean clickonLeakDetectorConfiguration()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "LeakDetectorConfiguration");
	}
	//Frequency Updated pop up
	public boolean isFrequencyUpdatedPopupVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Frequency_Updated_Popup");
	}
	public boolean clickonFrequencyUpdatedPopup()
	{
		try {
			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(10, TimeUnit.SECONDS);
			boolean status = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if(isFrequencyUpdatedPopupVisible()) {
						return  MobileUtils.clickOnElement(objectDefinition, testCase, "Frequency_Updated_Popup");
					}
					else 
						return  false;		
				}
			});
			if(!status)
			{
				flag = false;
			}
		}catch(Exception e)
		{
			flag = false;
		}
		return flag;
	}
	//WLD settings to Primary card
	public boolean navigateFromWLDSettingsScreenToPrimaryCard() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.pressEnterButton(testCase);
			return MobileUtils.pressBackButton(testCase, "Navigated back");
		}
		else {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "Settings_NavigateBack");
		}
	}
	public boolean navigateFromUpdateFrequencyCardToPrimaryCard(){
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")){
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Navigate_Back");
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Navigate_Back");
			return flag;
		}
		else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Settings_NavigateBack");
			set.clickonFrequencyUpdatedPopup();
			return MobileUtils.clickOnElement(objectDefinition, testCase, "Settings_NavigateBack");				
		}
	}	

	public static boolean navigateFromDashboardScreenToWLDSettingsUpdateFrequencyScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		WLDSolutionCard sol = new WLDSolutionCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag && sol.checkAndDismissControlState();
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				flag=flag & set.clickonUpdateFrequencyTitleText();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	public boolean navigateFromWLDSettingsScreenToUpdateFrequencyCard() {
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		flag = flag && set.clickonUpdateFrequencyTitleText();
		return flag;
	}
}