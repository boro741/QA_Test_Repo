package com.honeywell.screens;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class WLDSolutionCard extends MobileScreens {
	private static final String screenName = "WLD_SolutionCard";
	public boolean flag = true;
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
	public boolean isSetupCompleteTitleVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetupComplete");
	}
	public String getSetupCompleteTitleText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "SetupComplete");
	}
	public boolean isSetupCompleteDescriptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SetupCompleteDescription");
	}
	public String getSetupCompleteDescriptionText()
	{
		return MobileUtils.getFieldValue(objectDefinition, testCase, "SetupCompleteDescription");
	}
	public String getNextUpdateTimeTitleText()
	{
		if(testCase.getPlatform().toUpperCase().contains("ANDROID")) 
		{
			return MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");
		}
	else 
		{
			String Before_String =  MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");
			try {
				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
				fWait.pollingEvery(5, TimeUnit.SECONDS);
				fWait.withTimeout(30, TimeUnit.SECONDS);
				boolean isEventReceived =  fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						if(!MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime").equals(Before_String)) {
							return  true;
						}
						else 
							return  false;		
					}
				});
				if(isEventReceived) {
					return MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");
				}
				}
			catch(Exception e)
			{
				flag = true;
				//System.out.println("Exception : " + e.getMessage());
			}
	
			}
		return MobileUtils.getFieldValue(objectDefinition, testCase, "NextUpdateTime");	
	}
	public boolean clickOnHumidityGraphTitle()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "HumidityTab");
	}
	public boolean isDismissButtonVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DismissButton");
	}
	public boolean clickOnDismissButton()
	{
		boolean flag = true;
		flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "DismissButton");
		if(isDismissButtonVisible())
		{
			flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "DismissButton");
		}
		return flag;
	}
	public static boolean navigateFromDashboardScreenToWLDSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		WLDSolutionCard wld = new WLDSolutionCard(testCase);
		PrimaryCard pc = new PrimaryCard(testCase);

		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();			
			}else if(wld.isDismissButtonVisible()) {
				wld.clickOnDismissButton();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: Navigation to Settings Failed" + e.getMessage());
		}
		return flag;
	}
	
	public boolean navigateFromPrimaryCardToWLDSettingsScreen() {
		boolean flag = true;
		WLDSolutionCard wld = new WLDSolutionCard(testCase);
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();			
			}else if(wld.isDismissButtonVisible()) {
				wld.clickOnDismissButton();
			}
			
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	public boolean navigateFromPrimaryCardToLeakDetectorConfigurationScreen() {
		boolean flag = true;
		WLDSolutionCard wld = new WLDSolutionCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				set.clickonLeakDetectorConfiguration();
			}else if(wld.isDismissButtonVisible()) {
				wld.clickOnDismissButton();
			}
			
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	public boolean selectDefaultDaily(TestCases testCase) {
		WLDSolutionCard wld = new WLDSolutionCard(testCase);
		WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
		WLDUpdateFrequency freq = new WLDUpdateFrequency(testCase);
		flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
		System.out.println("Selected Default Daily");
		flag = flag & set.clickonUpdateFrequencyTitleText();
		flag = flag & freq.clickOnDailyRadioButton();
		flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
		return flag;
	}
	public String convertToTimeFormat(String str) {
        String[] arrOfStr = str.split("NEXT UPDATE ");     
		return arrOfStr[1];
	}
	
	
	
	public boolean isNextUpdateTimeUpdated(String before_time, String after_time, int byxhrs) {
		   long hrs = 3600*1000;
	     DateFormat sdf = new SimpleDateFormat("hh:mm a");
			try {
				Date beforedate = sdf.parse(before_time);
				System.out.println("Before Date: "+beforedate);
				Date afterDate=sdf.parse(after_time);
				System.out.println("After Date: "+afterDate);
				
				Date addedDate = new Date(beforedate.getTime() + byxhrs * hrs);
				System.out.println("Added Date: "+addedDate);

				 if(addedDate.after(afterDate)) {
					 Keyword.ReportStep_Pass(testCase, "The Next Update Time was updated successfully by "+byxhrs+" Hours");
					 flag = true;
				 }else if(addedDate.after(afterDate)) {
					 Keyword.ReportStep_Pass(testCase, "The Next Update Time was updated successfully by "+byxhrs+" Hours");
					 flag = true;
				 }else if(addedDate.equals(afterDate)) {
					 Keyword.ReportStep_Pass(testCase, "The Next Update Time was updated successfully by "+byxhrs+" Hours");
					 flag = true;
				 }			 
				 else {
					 Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The Next Update Time was Not Updated Successfully by "+byxhrs+" Hours");
					 flag = false;
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return flag;
	}
}