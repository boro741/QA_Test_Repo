package com.honeywell.screens;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class CameraSettingsScreen extends MobileScreens {

	private static final String screenName = "CameraSettings";

	public CameraSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCameraSettingsHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraSettingsScreenHeaderTitle", timeOut);
	}

	public boolean isManageAlertsVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ManageAlertsLabel", timeOut);
	}

	public boolean clickOnManageAlerts() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ManageAlertsLabel");
	}

	public boolean isCameraStatusONOFFAlertsSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStatusOnOffAlertsSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatusOnOffAlertsSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatusOnOffAlertsSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Camera Status ON/OFF Alert Switch");
		}
	}

	public boolean toggleCameraStatusONOFFAlertsSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraStatusOnOffAlertsSwitch");
	}

	public boolean isMotionEventAlertsSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionEventAlertsSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEventAlertsSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEventAlertsSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}

			}
		} else {
			throw new Exception("Could not find Motion Event Alert Switch");
		}
	}

	public boolean toggleMotionEventAlertsSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionEventAlertsSwitch");
	}

	public boolean isSoundEventAlertsSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundEventAlertsSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundEventAlertsSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundEventAlertsSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}

			}
		} else {
			throw new Exception("Could not find Sound Event Alert Switch");
		}
	}

	public boolean toggleSoundEventsAlertsSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SoundEventAlertsSwitch");
	}

	public boolean navigateBackAndForth(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInManageAlertsScreen")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInManageAlertsScreen");
			if (isCameraSettingsHeaderTitleVisible(5) && isManageAlertsVisible(5)) {
				flag = flag & clickOnManageAlerts();
			}
		}
		return flag;
	}

	public boolean isEmailNotificationCellVisibleAfterTurningOffAlerts(TestCases testCase) {
		boolean flag = true;
		List<WebElement> emailNotificationsCells = new ArrayList<WebElement>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationCell")) {
				emailNotificationsCells = MobileUtils.getMobElements(objectDefinition, testCase,
						"EmailNotificationCell");
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EmailNotificationCell")) {
				emailNotificationsCells = MobileUtils.getMobElements(objectDefinition, testCase,
						"EmailNotificationCell");
			}
		}
		if (emailNotificationsCells.size() < 3) {
			Keyword.ReportStep_Pass(testCase, "Email Notification Cells displayed: " + emailNotificationsCells.size());
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Incorrect Email Notifications Cells displayed: " + emailNotificationsCells.size());
		}
		return flag;
	}
}
