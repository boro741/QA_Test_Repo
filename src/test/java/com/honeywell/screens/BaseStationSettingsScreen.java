package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class BaseStationSettingsScreen extends MobileScreens {

	private static final String screenName = "DASSettings";
	public static final String BASESTATIONCONFIGURATION = "Base Station Configuration";
	public static final String ENTRYEXITDELAYSETTINGS = "Entry/Exit Delay";

	public BaseStationSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean selectOptionFromBaseStationSettings(String option) throws Exception {
		switch (option) {
		case BaseStationSettingsScreen.BASESTATIONCONFIGURATION: {
			boolean flag = true;
			if (this.isBaseStationConfigurationsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			} else {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "text",
							BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				} else {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value",
							BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
				}
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			if (this.isBaseStationConfigurationsOptionVisible()) {
				flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BaseStationConfigurationsOption");
			}
			return flag;
		}

		case BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS: {
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
			if (this.isBaseStationConfigurationsOptionVisible()) {
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

	public boolean isBaseStationConfigurationsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BaseStationConfigurationsOption", 3);
	}

	public boolean isEntryExitDelaySettingsOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryExitDelayOption", 3);
	}

	public boolean clickOnNoButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NoButton");
	}

	public boolean isDeleteDASPopUpVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteDASPopUpConfirmationTitle", 3);
	}

	public boolean verifyParticularBaseStationSettingsVisible(String settingName) throws Exception {
		String attribute;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
		} else {
			attribute = "value";
		}
		if (settingName.equalsIgnoreCase("Key Fob") || settingName.equalsIgnoreCase("Sensors")) {
			return LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute, settingName);
		} else {
			return LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute, settingName);
		}
	}

	public boolean verifyParticularEntryExitDelayOptionVisible(String option) throws Exception {
		String attribute = "";
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
		} else {
			attribute = "value";
		}
		return LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute, option);
	}

	public boolean clickOn15SecondsEntryExitDelayOption()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "15SecondsOption");
	}
	
	public boolean is15SecondsEntryExitDelayOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "15SecondsOption", 3);
	}
	
	public boolean clickOn30SecondsEntryExitDelayOption()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "30SecondsOption");
	}
	
	public boolean is30SecondsEntryExitDelayOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "30SecondsOption", 3);
	}
	
	public boolean clickOn45SecondsEntryExitDelayOption()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "45SecondsOption");
	}
	
	public boolean is45SecondsEntryExitDelayOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "45SecondsOption", 3);
	}
	
	public boolean clickOn60SecondsEntryExitDelayOption()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "60SecondsOption");
	}
	
	public boolean is60SecondsEntryExitDelayOptionVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "60SecondsOption", 3);
	}
}
