package com.honeywell.screens;

import org.openqa.selenium.Dimension;

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
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			int startx = (dimensions.width * 20) / 100;
			int starty = (dimensions.height * 62) / 100;
			int endx = (dimensions.width * 22) / 100;
			int endy = (dimensions.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			while(!MobileUtils.isMobElementExists(objectDefinition, testCase, "ZwaveList",10)){
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			}
		} catch (Exception e) {
			System.out.println("Not able to locate");
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
