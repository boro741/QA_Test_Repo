package com.honeywell.screens;

import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;

public class WLDEmailNotificationsScreen extends MobileScreens {
	private static final String screenName = "WLD_EmailNotifications";
	boolean flag = true;
	public HashMap<String, MobileObject> fieldObjects;

	public WLDEmailNotificationsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isEnterEmailEditTextBoxVissible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnterEmailEditTextBox");
	}

	public boolean clearEnterEmailEditTextBox() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			MobileUtils.clickOnElement(objectDefinition, testCase, "EnterEmailEditTextBox");
			return MobileUtils.clearTextField(objectDefinition, testCase, "EnterEmailEditTextBox");
		} else {
			testCase.getMobileDriver().findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeTextField"))
					.clear();
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "EnterEmailEditTextBox");
		}
	}

	public boolean setValueToEmailTextBox(String Email) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.setValueToElement(objectDefinition, testCase, "EnterEmailEditTextBox", Email);
			@SuppressWarnings("rawtypes")
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());
			Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
			// System.out.println("######dimensions.width:- " + dimensions.width);
			// System.out.println("######dimensions.height:- " + dimensions.height);
			// System.out.println("######(dimensions.width - 100):- " + (dimensions.width -
			// 100));
			// System.out.println("######(dimensions.height - 100):- " + (dimensions.height
			// - 100));
			// touchAction.tap((dimensions.width - 100), (dimensions.height -
			// 100)).perform();
			touchAction.tap(point((dimensions.width - 100), (dimensions.height - 100))).perform();
		} else {
			flag = flag & MobileUtils.setValueToElement(testCase, "XPATH", "//XCUIElementTypeTextField", Email);
			MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButtonOnKeyboard");
		}
		return flag;

	}

	public String generateRandomEmailID() {
		double randomNumber = Math.floor((1 + Math.random()) * 0x10000);
		String newId = "email" + randomNumber;
		return newId + "@grr.la";
	}

	public boolean verifyEmailPresentinList(String randomEmail) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			@SuppressWarnings("rawtypes")
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			String foundString = "";
			List<WebElement> toggleSwitch = null;
			boolean found = false;
			while (!found) {
				toggleSwitch = MobileUtils.getMobElements(testCase, "id",
						"list_item_lyric_horizontal_text_view_primary_text");
				for (int k = 0; k < toggleSwitch.size(); k++) {
					foundString = toggleSwitch.get(k).getText();
					if (foundString.equalsIgnoreCase(randomEmail)) {
						found = true;
						break;
					}
				}
				if (!found) {
					/*
					 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, (int)
					 * (dimension.getHeight() * -.7)) .release().perform();
					 */
					action.press(point(10, (int) (dimension.getHeight() * .9)))
							.moveTo(point(0, (int) (dimension.getHeight() * -.7))).release().perform();
				}
			}
			if (found) {
				Keyword.ReportStep_Pass(testCase, "Email Added to the Pending Accept  List");
			} else {
				flag = false;
			}
		} else {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			@SuppressWarnings("rawtypes")
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			String foundString = "";
			List<WebElement> toggleSwitch = null;
			boolean found = false;
			while (!found) {
				toggleSwitch = MobileUtils.getMobElements(testCase, "name",
						"EmailNotificationSettingsViewController_Email_Notification_subTitle");
				for (int k = 0; k < toggleSwitch.size(); k++) {
					foundString = toggleSwitch.get(k).getText();
					if (foundString.equalsIgnoreCase(randomEmail)) {
						found = true;
						break;
					}
				}
				if (!found) {
					/*
					 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, (int)
					 * (dimension.getHeight() * -.7)) .release().perform();
					 */
					action.press(point(10, (int) (dimension.getHeight() * .9)))
							.moveTo(point(0, (int) (dimension.getHeight() * -.7))).release().perform();
				}
			}
			if (found) {
				Keyword.ReportStep_Pass(testCase, "Email Added to the Pending Accept  List");
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean incorrectEmailPopup() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = true;
			return MobileUtils.isMobElementExists(objectDefinition, testCase, "validEmailText");
		} else {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "validEmailText")
					.equals("Please enter a valid email.")) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean navigateBackAndForth() {
		WLDManageAlerts wld = new WLDManageAlerts(testCase);
		flag = flag && MobileUtils.clickOnElement(objectDefinition, testCase, "NavigateBackButton");
		flag = flag && wld.clickEmailContacts();
		return flag;

	}
}