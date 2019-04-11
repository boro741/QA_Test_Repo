package com.honeywell.keywords.lyric.das.pushnotifications;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.PointOption.point;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASNotificationUtils;

import io.appium.java_client.TouchAction;

public class VerifyNoPushNotificationShouldBeDisplayed extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyNoPushNotificationShouldBeDisplayed(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should not be displayed with Honeywell Home push notifications$")
	public boolean keywordSteps() throws KeywordException {
		DASNotificationUtils.openNotifications(testCase);

		@SuppressWarnings("unused")
		List<WebElement> pushNotificationList = new ArrayList<>();
		@SuppressWarnings("rawtypes")
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
		Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int i = 0;
			search: while (i < 5) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//android.widget.LinearLayout[@resource-id='android:id/notification_main_column']/android.widget.LinearLayout[@resource-id='android:id/line1']/android.widget.TextView[@resource-id='android:id/title']",
						testCase)) {
					List<WebElement> listOfNotifications = MobileUtils.getMobElements(testCase, "XPATH",
							"//android.widget.LinearLayout[@resource-id='android:id/notification_main_column']/android.widget.LinearLayout[@resource-id='android:id/line1']/android.widget.TextView[@resource-id='android:id/title']");
					for (WebElement pushNotification : listOfNotifications) {
						if (!inputs.isRunningOn("Perfecto") || !inputs.isRunningOn("SauceLabs")) {
							if (!pushNotification.getText().contains("Honeywell")) {
								Keyword.ReportStep_Pass(testCase, "No Honeywell push notification is displayed");
								break search;

							}
						} else if (pushNotification.getText().contains("Honeywell")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Honeywell push notification is displayed");
							break search;
						} else {
							Point notificationToBeCleared = pushNotification.getLocation();
							int startX = notificationToBeCleared.getX();
							int endX = notificationToBeCleared.getX() / 2;
							int startY = notificationToBeCleared.getY();
							touchAction.press(point((startX + 150), (startY + 20)))
									.waitAction(waitOptions(MobileUtils.getDuration(2000)))
									.moveTo(point((endX - 100), 0)).release().perform();
						}
					}
				}
			}
			i++;
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell", testCase)) {
				int i = 0;
				while (i < 5) {
					List<WebElement> listOfNotifications = MobileUtils.getMobElements(testCase, "XPATH",
							"//XCUIElementTypeCell");
					for (WebElement pushNotification : listOfNotifications) {
						if (pushNotification.getText().contains("Honeywell")) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Honeywell push notification is displayed");
							break;
						} else {
							Point notificationToBeCleared = pushNotification.getLocation();
							int startX = notificationToBeCleared.getX();
							int endX = notificationToBeCleared.getX() / 2;
							int startY = notificationToBeCleared.getY();
							/*
							 * touchAction.press((startX + 150), (startY + 20)).moveTo((endX - 100),
							 * 0).release() .perform(); touchAction.tap((dimensions.width - 20), (startY +
							 * 30)).perform();
							 */
							touchAction.press(point((startX + 150), (startY + 20))).moveTo(point((endX - 100), 0))
									.release().perform();
							touchAction.tap(tapOptions().withPosition(point((dimensions.width - 20), (startY + 30))))
									.perform();
						}

					}
				}
				i++;
			}
		}
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "No Honeywell push notification is displayed");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Honeywell push notification is displayed");
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
