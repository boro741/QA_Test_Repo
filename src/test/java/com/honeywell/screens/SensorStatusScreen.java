package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class SensorStatusScreen extends MobileScreens {

	private static final String screenName = "SensorStatus";
	
	public SensorStatusScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	
	public List<WebElement> getSensorList() {
		List<WebElement> list;
		if(!testCase.getPlatform().contains("IOS")){
			list = MobileUtils.getMobElements(testCase,"xpath", "//*[contains(@content-desc,'sensor_status_item_')]");
		}else{
			list = MobileUtils.getMobElements(objectDefinition,testCase,"SensorList");
		}
		return list;
	}
	
    
	public boolean clickOnSensorStatusScreenBack(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SensorListBack");
	}
}
