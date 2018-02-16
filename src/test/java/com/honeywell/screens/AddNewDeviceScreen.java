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
		try {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Z-Wave Device");
		} catch (Exception e) {
			System.out.println("Not able to locate");
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ZwaveList");
	}
	

	public boolean clickOnCancelButtonOfAddDeviceScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelButton");
	}
}
