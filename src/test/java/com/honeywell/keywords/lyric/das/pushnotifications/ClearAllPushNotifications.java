package com.honeywell.keywords.lyric.das.pushnotifications;

import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.das.utils.DASNotificationUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.SecuritySolutionCardScreen;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.appium.java_client.touch.WaitOptions.waitOptions;


public class ClearAllPushNotifications extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public boolean pressBack = true;

	public ClearAllPushNotifications(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user clears all push notifications$")
	public boolean keywordSteps() throws KeywordException {
		SecuritySolutionCardScreen sc = new SecuritySolutionCardScreen(testCase);
		DASNotificationUtils.openNotifications(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (sc.isClearNotificationsIconVisible(5)) {
				if (sc.isClearNotificationsIconEnabled()) {
					flag = flag & sc.clickOnClearNotificationsIcon();
				}
			} else if (sc.isClearNotificationsTextVisible(5)) {
				flag = flag & sc.clickOnClearNotificationsText();
				pressBack = false;
			}
		} else {
			int i = 0;
			while (i < 5) {
				if (sc.isClearNotificationsIconVisible(2) && i < 5) {
					flag = flag & sc.clickOnClearNotificationsIcon();
					flag = flag & sc.clickOnConfirmButtonInClearNotifications();
				} else {
					if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell", testCase)) {
						List<WebElement> listOfNotifications = MobileUtils.getMobElements(testCase, "XPATH",
								"//XCUIElementTypeCell");
						for (WebElement ele : listOfNotifications) {
							@SuppressWarnings("rawtypes")
							TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
							Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
							Point notificationToBeCleared = ele.getLocation();
							int startX = notificationToBeCleared.getX();
							int endX = notificationToBeCleared.getX() / 2;
							int startY = notificationToBeCleared.getY();
							/*
							 * touchAction.press((startX + 150), (startY + 20)).moveTo((endX - 100),
							 * 0).release() .perform(); touchAction.tap((dimensions.width - 20), (startY +
							 * 30)).perform();
							 */
							touchAction.press(point((startX + 150), (startY + 20))).waitAction(waitOptions(MobileUtils.getDuration(5000))).moveTo(point((endX - 100), 0))
									.release().perform();
							touchAction.tap(tapOptions().withPosition(point((dimensions.width - 20), (startY + 30))))
									.perform();							
						}
					} else {
						break;
					}
				}
				i++;
			}
		}
		if (pressBack) {
			DASNotificationUtils.closeNotifications(testCase);
			testCase.getMobileDriver().launchApp();
			LyricUtils.verifyLoginSuccessful(testCase, inputs, false);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
