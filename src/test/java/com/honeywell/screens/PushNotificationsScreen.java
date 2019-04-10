package com.honeywell.screens;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class PushNotificationsScreen extends MobileScreens {

	private static final String screenName = "AboutTheAppScreen";

	public PushNotificationsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isLoadingSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSpinner");
	}

	public boolean isProgressBarVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProgressBar");
	}

	public boolean isHoneywellPushNotificationDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "PushNotificationHoneywell");
	}

	public List<WebElement> getHoneywellPushNotifications() {
		return MobileUtils.getMobElements(objectDefinition, testCase, "pushNotification");
	}
}