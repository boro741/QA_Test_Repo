package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class BaseStationSettingsScreen extends MobileScreens {

	private static final String screenName = "DASSettings";
	public static final String BASESTATIONSETTINGS = "Base Station Settings";
	public static final String ENTRYEXITDELAYSETTINGS = "Entry/Exit Delay";

	public BaseStationSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean selectOptionFromBaseStationSettings(String option) throws Exception {
		switch (option) {
		case BaseStationSettingsScreen.BASESTATIONSETTINGS:
		{
			boolean flag = true;
			if (this.isBaseStationSettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationSettingsOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.BASESTATIONSETTINGS);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
							BaseStationSettingsScreen.BASESTATIONSETTINGS);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationSettingsOption");
			}
			if (this.isBaseStationSettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationSettingsOption");
			}
			return flag;
		}
		
		case BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS:
		{
			boolean flag = true;
			if (this.isEntryExitDelaySettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
							BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			}
			if (this.isBaseStationSettingsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "EntryExitDelayOption");
			}
			return flag;
		}
			
		default: {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				return MobileUtils.clickOnElement(testCase, "xpath",
						"//android.widget.TextView[@text='" + option + "']");
			} else {
				return MobileUtils.clickOnElement(testCase, "value", option);
			}
		}

		}
	}
	
	public boolean isBaseStationSettingsOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationSettingsOption",3);
	}

	public boolean isEntryExitDelaySettingsOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption",3);
	}
}
