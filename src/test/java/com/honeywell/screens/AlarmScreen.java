package com.honeywell.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

import io.appium.java_client.TouchAction;

public class AlarmScreen extends MobileScreens {

	private static final String screenName = "AlarmScreen";

	public AlarmScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isAlarmScreenDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Title") 
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Subtitle")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton");
	}

	public boolean isPleaseWaitDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DismissRequestProcessing");
	}

	public boolean isAlarmDismissButtonDisplayed()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton");
	}

	public boolean clickOnDismissAlarm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlarmDismissButton") &&
				MobileUtils.clickOnElement(objectDefinition, testCase, "DismissAlarmPopupOk");

	}

	public boolean clickOnCall() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CallButton");
	}

	//Entry delay screen

	public boolean isEntryDelayScreenDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton") 
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton");
	}

	public boolean clickOnSwitchToHome(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToHomeButton");
	}

	public boolean clickOnSwitchToNight(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToNightButton");
	}

	public boolean clickOnAlarm_NavigateBack(){
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Alarm_NaviigateBack");
	}


	public boolean clickOnAttention(){
		if(isEntryDelayScreenDisplayed()){
			System.out.println("In Entry delay screen");
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionButton");
	}

	public boolean isWaitingToCloseScreenDisplayed(){
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WaitingToCloseDoor") ;
	}

	public boolean isCallScreenDisplayed() {
		Boolean b = MobileUtils.isMobElementExists(objectDefinition, testCase, "CallPolice") &&  
				MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelCall");
		return (b);
	}

	public boolean clickCancelButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CancelCall");
	}

	public boolean clickCallPoliceButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CallPolice");
	}
	public boolean clickalarmHistoryButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "History_BottomArrow");
	}
	public boolean isAlarmHistoryDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_History");
	}

	public boolean openAlarmHistory() {
		boolean flag = true;
		WebElement History_BottomArrow = null;
		WebElement Alarm_History = null;
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			History_BottomArrow = MobileUtils.getMobElement(objectDefinition, testCase, "History_BottomArrow");
			Alarm_History = MobileUtils.getMobElement(objectDefinition, testCase, "Alarm_History");
			action = action.press(History_BottomArrow).moveTo(Alarm_History).release().perform();
		}
		// For IOS device
		//		else {
		//			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollUp")) {
		//				return MobileUtils.clickOnElement(objectDefinition, testCase, "AcitvityLogScrollUp");
		//			} else {
		//				try {
		//					// MobileElement activityLogUpElement = null;
		//					CustomDriver driver = testCase.getMobileDriver();
		//			//		if (driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).isEnabled()) {
		//			//			driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).click();
		//					}
		//				} catch (NoSuchElementException e) {
		//
		//					throw new Exception(e + "Activity log scroll up icon is not displayed");
		//				}
		//			}

		return flag;
	}
	public boolean clickLiveStreamingArea() {
		boolean flag = true;

		MobileUtils.clickOnElement(objectDefinition, testCase, "LiveStreamingArea");

		return flag;
	}

	public boolean clickPauseStreaming() {
		boolean flag = true;

		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				clickLiveStreamingArea();
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "pauseStreaming",3)) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "pauseStreaming");					
		}



		return flag;
	}

	public boolean isPlayStreamingVisible() {
		boolean flag = false;

		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "playStreaming")) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			flag = true;					
		}

		return flag;
	}

	public boolean clickPlayStreaming() {
		boolean flag = true;

		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if(MobileUtils.isMobElementExists(objectDefinition, testCase, "playStreaming")) {
					return true;
				}else return false;

			}

		});

		if(isEventReceived) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "playStreaming");					
		}



		return flag;
	}
}
