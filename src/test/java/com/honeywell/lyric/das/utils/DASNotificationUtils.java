package com.honeywell.lyric.das.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;

public class DASNotificationUtils {

	@SuppressWarnings("unchecked")
	public static void openNotifications(TestCases testCase) {
		MobileUtils.minimizeApp(testCase, -1);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) testCase.getMobileDriver();
			driver.openNotifications();
		} else {
			testCase.getMobileDriver().swipe(0, 0, 0, 500, 1);
		}
	}

	@SuppressWarnings("unchecked")
	public static void closeNotifications(TestCases testCase) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).pressKeyCode(AndroidKeyCode.BACK);
		} else {
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			int startX = dimensions.width / 2;
			int startY = (dimensions.height - 20);
			touchAction.press(startX, startY).moveTo(0, -500).release().perform();
		}
	}
}