package com.honeywell.screens;

import java.util.List;

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
			Keyword.ReportStep_Pass(testCase, "Successfully opened activity logs");
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
						Keyword.ReportStep_Pass(testCase, "Successfully opened activity logs");
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
						Keyword.ReportStep_Pass(testCase, "Successfully closed activity logs");
					}
				} catch (NoSuchElementException e) {
					flag = false;
					throw new Exception(e + "Activity log scroll down icon is not displayed");
				}
			}
		}
		return flag;
	}


	public boolean isClipPlayButtonExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ClipPlayButton");
	}
	public boolean clickClipPlayButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ClipPlayButton");
	}
	public boolean isDowloadIconExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DowloadIcon");
	}
	public boolean clickDowloadIcon() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DowloadIcon");
	}
	public boolean isDowloadCloseExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DownloadClose");
	}
	public boolean clickDowloadClose() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DownloadClose");
	}
	public boolean isDownloadBufferExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DownloadBuffer",timeout);
	}
	public boolean isClipDownloadScreenExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ClipDownloadScreen");
	}
	public boolean isCanceDowloadContentExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CanceDowloadContent");
	}
	public boolean isCancelYesExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelYes");
	}
	public boolean clickCancelYes() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelYes");
	}
	public boolean isCancelNoExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelNo");
	}
	public boolean clickCancelNo() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelNo");
	}
	public boolean isEventTimesExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EventTimes");
	}
	public List<WebElement> getEventTimes()
	{
		return MobileUtils.getMobElements(objectDefinition, testCase, "EventTimes");
	}
	public boolean isClipStatusExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ClipStatus");
	}
	public List<WebElement> getClipStatus()
	{
		return MobileUtils.getMobElements(objectDefinition, testCase, "ClipStatus");
	}
	public boolean isDeletePopupExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeletPopup");
	}
	public boolean isDeleteSuccessExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteSuccess");
	}
	public boolean clickDeleteSuccess() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteSuccess");
	}
	public boolean isCancelDownloadVideoClipExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelDownloadVideoClip");
	}
	public boolean isDeleteOkExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteOk");
	}
	
	public boolean clickDeleteOk() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteOk");
	}
	public boolean isDeleteCancelExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteCancel");
	}
	public boolean clickDeleteCancel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteCancel");
	}
	public boolean isBackToViewListExists(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackToViewList",timeout);
	}
	public boolean clickBackToViewList() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackToViewList");
	}
	///
	public boolean isCancelDowloadHeaderExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelDowloadHeader");
	}
	public boolean isDeleteClipsExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteClips");
	}
	public boolean clickDeleteClips() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DeleteClips");
	}
	public boolean isDeleteConfirmationHeaderExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DeleteConfirmationHeader");
	}
	public boolean isOkButtonExists() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OkButton");
	}
	public boolean clickOkButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OkButton");
	}

}
