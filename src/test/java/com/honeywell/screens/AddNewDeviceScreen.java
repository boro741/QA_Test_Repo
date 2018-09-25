package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class AddNewDeviceScreen extends MobileScreens {

	private static final String screenName = "AddNewDevice";

	public AddNewDeviceScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean clickOnZwaveFromAddNewDevice() {
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList", 3)) {
			int counter = 0;
			while (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList", 3) && counter < 8) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						LyricUtils.scrollUpAList(testCase, MobileUtils.getMobElement(testCase, "ID",
								"fragment_add_new_device_list"));
						//LyricUtils.scrollToElementUsingExactAttributeValue();
					} else {
						LyricUtils.scrollUpAList(testCase, MobileUtils.getMobElement(objectDefinition,testCase, "DevicesList"));
						//LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "Name", "Z-Wave Device");
					}
					// LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
				} catch (Exception e) {
					e.printStackTrace();
				}
				counter++;
			}
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveList");
	}

	public boolean isFetchingDevicesListProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "FetchingDevicesLoadingSpinnerMsg", 3);
	}

	public boolean isAddNewDeviceHeaderDisplayed(int lookFor) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader", lookFor);
	}

	public boolean clickOnCancelButtonOfAddDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
	
	public boolean isPageTitleVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PageTitle", timeOut);
	}
	
	public boolean isSearchVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Search",timeOut);
	}
	
	public boolean clickOnBackButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButton");
	}
	
	public boolean isLogoutVisible(int timeOut)
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Logout",timeOut);
	}
	
	public boolean clickOnLogoutButton()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Logout");
	}
}
