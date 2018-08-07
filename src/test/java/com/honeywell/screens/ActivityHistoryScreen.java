package com.honeywell.screens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.das.utils.DASZwaveUtils;

import io.appium.java_client.MobileElement;

public class ActivityHistoryScreen extends MobileScreens {

	private static final String screenName = "ActivityHistoryScreen";

	public ActivityHistoryScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isMessagesDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Message");

	}

	public HashMap<String, String> getAllMessages(TestCases testCase) throws Exception {
		HashMap<String, String> combinedMessage = new HashMap<String, String>();
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

				List<WebElement> messagesGroup = MobileUtils.getMobElements(objectDefinition, testCase, "Message",
						false);
				int i = 1;
				for (int j = 1; j < messagesGroup.size(); j++) {
					WebElement ele = testCase.getMobileDriver().findElement(By.xpath("//android.widget.LinearLayout["
							+ i
							+ "]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView"));
					WebElement ele1 = testCase.getMobileDriver().findElement(By.xpath("//android.widget.LinearLayout["
							+ i
							+ "]/android.widget.LinearLayout/android.widget.RelativeLayout//following-sibling::android.widget.TextView"));
					if (!combinedMessage.containsKey(ele.getAttribute("text"))) {
						combinedMessage.put(ele.getAttribute("text"), ele1.getAttribute("text"));
					}
					System.out.println(ele.getAttribute("text"));
					System.out.println(ele1.getAttribute("text"));
					i++;
				}

			} else {// IOS
				List<WebElement> messagesGroup = MobileUtils.getMobElements(objectDefinition, testCase, "Message",
						false);
				int i = 1;
				// for (WebElement message : messagesGroup) {
				for (int j = 1; j < messagesGroup.size(); j++) {
					//// XCUIElementTypeCell/XCUIElementTypeStaticText[1]
					WebElement ele = testCase.getMobileDriver()
							.findElement(By.xpath("//XCUIElementTypeCell[" + i + "][contains(@name,'Selected')]"));
					WebElement ele1 = testCase.getMobileDriver()
							.findElement(By.xpath("//XCUIElementTypeCell[" + i + "][contains(@name,'Selected')]//XCUIElementTypeStaticText[contains(@name,'Saving')][1]"));
					if (!combinedMessage.containsKey(ele.getAttribute("value"))) {
						combinedMessage.put(ele.getAttribute("label"), ele1.getAttribute("value"));
					}
					System.out.println(ele.getAttribute("label"));
					System.out.println(ele1.getAttribute("value"));
					i++;
				}

			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return combinedMessage;
	}

}
