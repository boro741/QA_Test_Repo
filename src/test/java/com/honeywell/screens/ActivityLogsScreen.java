package com.honeywell.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

import io.appium.java_client.TouchAction;

public class ActivityLogsScreen extends MobileScreens {

	private static final String screenName = "ActivityLogs";

	// Locator values used in the methods
	public static final String ACTIVITYLOGSCROLLUPICON = "icon_arch";
	public static final String ACTIVITYLOGSCROLLDOWNICON = "icon_arrow_up";

	public ActivityLogsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isOpenActivityLogsIconVisible() throws Exception {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollUp")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollUp");
		} else {
			try {
				// MobileElement activityLogUpElement = null;
				CustomDriver driver = testCase.getMobileDriver();
				if (driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).isEnabled()) {
					return flag;
				}
			} catch (NoSuchElementException e) {
				flag = false;
				throw new Exception(e + "Activity log scroll up icon is not displayed");
			}
		}
		return flag;
	}

	public boolean clickOnOpenActivityLogsIcon() throws Exception {
		boolean flag = true;
		WebElement activityArrow = null;
		WebElement panelState = null;
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			activityArrow = MobileUtils.getMobElement(objectDefinition, testCase, "AcitvityLogScrollUp");
			panelState = MobileUtils.getMobElement(objectDefinition, testCase, "PanelState");
			action = action.press(activityArrow).moveTo(panelState).release().perform();
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollUp")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AcitvityLogScrollUp");
			} else {
				try {
					// MobileElement activityLogUpElement = null;
					CustomDriver driver = testCase.getMobileDriver();
					if (driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).isEnabled()) {
						//driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).click();
						action.tap(driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON))).release().perform();
					}
				} catch (NoSuchElementException e) {

					throw new Exception(e + "Activity log scroll up icon is not displayed");
				}
			}
		}
		return flag;
	}

	public boolean isCloseActivityLogsIconVisible() throws Exception {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollDown")) {
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollDown");
		} else {
			try {
				// MobileElement activityLogDownElement = null;
				CustomDriver driver = testCase.getMobileDriver();
				if (driver.findElement(By.name(ACTIVITYLOGSCROLLDOWNICON)).isEnabled()) {
					return flag;
				}
			} catch (NoSuchElementException e) {
				flag = false;
				throw new Exception(e + "Activity log scroll down icon is not displayed");
			}
		}
		return flag;
	}

	public boolean clickOnCloseActivityLogsIcon() throws Exception {
		boolean flag = true;
		WebElement activityDay = null;
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			activityDay = MobileUtils.getMobElement(objectDefinition, testCase, "AcitvityLogScrollDown");
			action = action.press(activityDay).moveTo(activityDay.getLocation().getX(), 300).release().perform();
			if (action != null) {
				Keyword.ReportStep_Pass(testCase, "Successfully closed activity logs");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to close activity logs");
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollDown")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AcitvityLogScrollDown");
			} else {
				try {
					// MobileElement activityLogDownElement = null;
					CustomDriver driver = testCase.getMobileDriver();
					if (driver.findElement(By.name(ACTIVITYLOGSCROLLDOWNICON)).isEnabled()) {
						activityDay = driver.findElement(By.name(ACTIVITYLOGSCROLLDOWNICON));
						action.press(activityDay).moveTo(activityDay.getLocation().getX(), 300).release().perform();
						
					}
				} catch (NoSuchElementException e) {
					flag = false;
					throw new Exception(e + "Activity log scroll down icon is not displayed");
				}
			}
		}
		return flag;
	}
}
