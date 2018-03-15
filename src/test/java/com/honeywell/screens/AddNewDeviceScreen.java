package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class AddNewDeviceScreen extends MobileScreens{

	private static final String screenName = "AddNewDevice";		

	public AddNewDeviceScreen(TestCases testCase) {
		super(testCase,screenName);
	}

	public boolean clickOnZwaveFromAddNewDevice() {
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList",3)) {
			int counter = 0;
			while (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList",3) && counter < 8) {
				try {
					LyricUtils.scrollUpAList(testCase, objectDefinition, "DevicesList");
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

    public boolean isAddNewDeviceHeaderDisplayed(int lookFor){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader",lookFor);
	}

	public boolean clickOnCancelButtonOfAddDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
}
