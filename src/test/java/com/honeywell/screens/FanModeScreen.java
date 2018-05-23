package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class FanModeScreen extends MobileScreens {

	private static final String screenName = "FanMode";

	public FanModeScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isFanScreenTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanScreenTitle", 5, false);
	}

	public String getFanScreenTitle() 
	{
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanModeScreenTitle").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanModeScreenTitle").getAttribute("value");
	}

	public String getDeviceNameOnFanScreen() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "DeviceNameOnFanScreen").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "DeviceNameOnFanScreen").getAttribute("value");
	}

	public String getFanDescription() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanDescriptionId").getText();
		else {
			String str = getFanRunningSts();

			if (str != null) {
				if (str.equalsIgnoreCase("AUTO"))
					return MobileUtils.getMobElement(objectDefinition, testCase, "FanAutoDesc").getAttribute("name");
				else if (str.equalsIgnoreCase("ON"))
					return MobileUtils.getMobElement(objectDefinition, testCase, "FanOnDesc").getAttribute("name");
				else if (str.equalsIgnoreCase("CIRCULATE"))
					return MobileUtils.getMobElement(objectDefinition, testCase, "FanCirculateDesc")
							.getAttribute("name");
				else
					return null;
			} else
				return null;
		}
	}

	public String getFanInfoToolbarTitle() {
		
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanInfoToolbarTitle").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanInfoToolbarTitle").getAttribute("value");
	}

	public String getFanInfoToolbarDeviceName() {
		if(testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanInfoToolbarDeviceName").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "FanInfoToolbarDeviceName").getAttribute("value");			
	}

	public String getListMode1Name() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode1Auto").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode1Auto").getAttribute("name");
	}

	public String getListMode1Desc() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode1AutoDesc").getText();
	}

	public String getListMode2Name() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode2Circulate").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode2Circulate").getAttribute("name");
	}

	public String getListMode2Desc() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode2CirculateDesc").getText();
	}

	public String getListMode3Name() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID"))
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode3On").getText();
		else
			return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode3On").getAttribute("name");
	}

	public String getListMode3Desc() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "ListMode3OnDesc").getText();
	}

	public String getFanRunningSts() {
		String fanRunSts = null;

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", "action_sheet_item_title", testCase, 5)) {
				List<WebElement> allFanModes = MobileUtils.getMobElements(testCase, "ID", "action_sheet_item_title");

				for (WebElement fanmode : allFanModes) {
					if (fanmode.getAttribute("selected").equals("true")) {
						System.out.println(fanmode.getText());
						fanRunSts = fanmode.getText();
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FanAutoSelected", 10))
				fanRunSts = "AUTO";
			else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FanOnSelected", 10))
				fanRunSts = "ON";
			else if (MobileUtils.isMobElementExists(objectDefinition, testCase, "FanCirculateSelected", 10))
				fanRunSts = "CIRCULATE";
		}

		return fanRunSts;
	}

	public boolean isFanAutoModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanAutoModeOption", timeOut);
	}

	public Boolean changeFanModeToAutoMode() {
		if (isFanAutoModeButtonVisible(5))
			return MobileUtils.clickOnElement(objectDefinition, testCase, "FanAutoModeOption");
		else
			return false;
	}

	public boolean isFanOnModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanOnModeOption", timeOut);
	}

	public Boolean changeFanModeToOnMode() {
		if (isFanOnModeButtonVisible(5))
			return MobileUtils.clickOnElement(objectDefinition, testCase, "FanOnModeOption");
		else
			return false;
	}

	public boolean isFanCircModeButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanCirculateModeOption", timeOut);
	}

	public Boolean changeFanModeToCircMode() {
		if (isFanCircModeButtonVisible(5))
			return MobileUtils.clickOnElement(objectDefinition, testCase, "FanCirculateModeOption");
		else
			return false;
	}

	public boolean isSaveButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SaveAction", timeOut);
	}

	public boolean clickSaveButton() {
		if (isSaveButtonVisible(5))
			return MobileUtils.clickOnElement(objectDefinition, testCase, "SaveAction");
		else
			return false;
	}

	public boolean isFanButtonVisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanModeButton", timeout);
	}

	public boolean clickFanButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "FanModeButton");
	}

	public boolean isFanInfoButtonVisible(int timeout) 
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FanScreenInfoButton", timeout);
	}

	public boolean clickFanScreenInfoButton() {
		if (isFanInfoButtonVisible(5))
			return MobileUtils.clickOnElement(objectDefinition, testCase, "FanScreenInfoButton");
		else
			return false;
	}

}
