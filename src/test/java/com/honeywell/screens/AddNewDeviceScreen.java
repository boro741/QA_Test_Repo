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
		if (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList",10)) {
			int counter = 0;
			while (!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList",10) && counter < 4) {
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

	public boolean isAddNewDeviceHeaderDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AddNewDeviceHeader");
	}

	public boolean clickOnCancelButtonOfAddDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
}
