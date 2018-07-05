package com.honeywell.screens;


import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;


import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;


public class AlarmScreen extends MobileScreens {
	private TestCases testCase;
	private static final String screenName = "AlarmScreen";


	public AlarmScreen(TestCases testCase) {
		super(testCase, screenName);
		this.testCase=testCase;
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
		if(b) {
			Keyword.ReportStep_Pass(testCase, "Call Screen is displayed");
		}
		return b;
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

	public boolean openAlarmHistory() throws Exception {
		boolean flag = true;
		WebElement History_BottomArrow = null;
		WebElement Alarm = null;
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			History_BottomArrow = MobileUtils.getMobElement(objectDefinition, testCase, "History_BottomArrow");
			Alarm = MobileUtils.getMobElement(objectDefinition, testCase, "Alarm_Title");
			action = action.press(History_BottomArrow).moveTo(Alarm).release().perform();
		}
		// For IOS device
		else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AcitvityLogScrollUp")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "AcitvityLogScrollUp");
			} else {
				try {
					// MobileElement activityLogUpElement = null;
					CustomDriver driver = testCase.getMobileDriver();
					if (driver.findElement(By.name("ACTIVITYLOGSCROLLUPICON")).isEnabled()) {
						driver.findElement(By.name("ACTIVITYLOGSCROLLUPICON")).click();
					}
				}

				catch (NoSuchElementException e) {

					throw new Exception(e + "Activity log scroll up icon is not displayed");
				}
			}
		}
		return flag;

	}


	public boolean clickLiveStreamingArea() {

		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag=flag & MobileUtils.clickOnElement(objectDefinition, testCase, "LiveStreamingArea");

		}
		else {
			flag=flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Live_icon");

		}

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
					MobileUtils.clickOnElement(objectDefinition, testCase, "pauseStreaming");
					return true;
				}else return false;

			}


		});

		if(isEventReceived) {
			// flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "pauseStreaming");
			flag=true;	
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
			Keyword.ReportStep_Pass(testCase, "Paused Streaming is displayed");
		}
		else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Paused Streaming is not displayed");
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


	public void swipe(String locatorValue) {

		MobileElement notificationLocation = MobileUtils.getMobElement(testCase,"xpath", locatorValue);

		int NotificationStartX=notificationLocation.getLocation().getX();
		int NotificationStartY=notificationLocation.getLocation().getY();
		int leftsidePoint=150;
		int rightsidePoint=600;

		try {	
			TouchAction action1 = new TouchAction(testCase.getMobileDriver());
			TouchAction action2 = new TouchAction(testCase.getMobileDriver());
			MultiTouchAction action = new MultiTouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				action1 = action1.press(NotificationStartX,NotificationStartY).moveTo(-leftsidePoint,0).release();
				action2 = action2.press(NotificationStartX+rightsidePoint,NotificationStartY).moveTo(rightsidePoint,0).release();
				action.add(action1).add(action2).perform();
				Keyword.ReportStep_Pass(testCase, "Swiped to view the options");
			} else {
				NotificationStartX= notificationLocation.getSize().getWidth()/2;
				action1 = action1.press(NotificationStartX,NotificationStartY).moveTo(-80,0).release();
				action1.perform();
				action1.tap(NotificationStartX,NotificationStartY).release().perform();
				Keyword.ReportStep_Pass(testCase, "Swiped to view the options");
			
			}
		}
		catch(Exception e) {
			System.out.println("Fix it");
			Keyword.ReportStep_Pass(testCase, "Failed to Swipe to view the options");
		}
	}



	public boolean switchToHomeOnNotificationExists() {

		boolean flag=false;
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchHomeNotificationButton",2)) {
			flag= true;
		}
		return flag;


	}



	public boolean switchToNightOnNotificationExists() {
		boolean flag=false;
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchNightNotificationButton",2)) {
			flag= true;
		}
		return flag;
	}

	public boolean clickswitchToHomeOnNotification() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchHomeNotificationButton");

	}
	public boolean clickswitchToNightOnNotification() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchNightNotificationButton");

	}

	public boolean isSwitchToHomeExists() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton")) {
			return true;
		}
		return false;
	}



	public boolean clickLiveStreamingMaximize() {
		boolean flag = true;
		clickLiveStreamingArea();

		flag=flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Maximize_button");
		return flag;
	}



	public boolean clickLiveStreamingMinimize() {
		boolean flag = true;
		clickLiveStreamingArea();


		flag=flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Minimize_button");
		return flag;
	}




	public boolean isAlarmTitleVisible() {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Title",10)) {
			Keyword.ReportStep_Pass(testCase, "ALarm Title is found");
			return true;
		}
		return false;
	}

	public boolean isAlarmLocationVisible(TestCaseInputs inputs) {
		String expectedLocationName = inputs.getInputValue("LOCATION1_NAME");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Alarm_Subtitle");
			if(element!=null) {
				String actualLocationName = element.getText();
				if(expectedLocationName.equalsIgnoreCase(actualLocationName)) {
					System.out.println("AlarmLocation is found");
					return true;
				}
			}
			else {
				System.out.println("element is null");
			}
		}
		else {
			String locatorValue ="//XCUIElementTypeStaticText[@value=\""+expectedLocationName+"\"]";
			if (MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 10)) {
				System.out.println("AlarmLocation is found");

				Keyword.ReportStep_Pass(testCase, "'" + locatorValue + "' is Present");
				return true;
			}
			else {
				System.out.println("AlarmLocation is not found");
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"'" + locatorValue + "'It is  not present");
			}
		}
		return false;
	}

	public boolean verifyLiveStreamingProgress() {
		boolean flag = true;
		System.out.println("In Progress func");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MobileElement element= MobileUtils.getMobElement(objectDefinition, testCase, "ProgressBar_timer");
		if(element!=null) {
			System.out.println("Element is not null");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				String value1 = MobileUtils.getAttribute(testCase, element, "name");
				String[] percent1=value1.split("_");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				String value2 = MobileUtils.getAttribute(testCase, element, "name");
				String[] percent2=value2.split("_");
				if(Math.abs(Integer.parseInt(percent2[1])-Integer.parseInt(percent1[1]))>0) {
					flag=true;

					Keyword.ReportStep_Pass(testCase, "Camera is Live Streaming and progressing");
				}
				else {
					flag=false;
					Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Camera Live streaming is not progressing");
				}

			}
			else {
				System.out.println("In ios else verify live");

				String value1 = MobileUtils.getAttribute(testCase, element, "value");
				System.out.println(value1);
				String percent1=value1.substring(0,value1.length()-1);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				String value2 = MobileUtils.getAttribute(testCase, element, "value");
				System.out.println(value2);
				String percent2=value2.substring(0,value1.length()-1);


				if(Math.abs(Integer.parseInt(percent2)-Integer.parseInt(percent1))>0) {
					flag=true;
					System.out.println("Live Streaming Progress is found");
					Keyword.ReportStep_Pass(testCase, "Camera is Live Streaming and progressing");

				}
			}

		}
		else {
			flag=false;
			System.out.println("element is null");
			Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Element can't be located!");
		}                             

		return flag;
	}

	public boolean isAlarm_NavigatebackVisible() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_NaviigateBack",5)) {
			System.out.println("ALarm Back is found");
			return true;
		}
		return false;
	}

	public boolean isCallButtonVisible() {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "CallButton")) {
			System.out.println("Call is found");
			return true;
		}

		return false;
	}

	public boolean isEntryDelayTitleVisible() {
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryDelayDoorOpenTitle")) {
			System.out.println("EntryDelay title found");
			return true;
		}
		return false;
	}

	public boolean isEntryDelaySubTitleVisible(TestCaseInputs inputs) {
		String expectedSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "EntryDelayDoorOpenSensorNameTitle");
		if(element!=null) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				String actualSensorName = element.getText();
				if(expectedSensorName.equalsIgnoreCase(actualSensorName)) {
					System.out.println("EntryDelay subtitle found");
					return true;
				}
			}
			else {
				String actualSensorName = MobileUtils.getAttribute(testCase, element, "name");
				System.out.println(actualSensorName);
				actualSensorName.toUpperCase();
				expectedSensorName.toUpperCase();
				if(actualSensorName.contains(expectedSensorName)) {
					System.out.println("EntryDelay subtitle found");
					return true;
				}
			}
		}
		else {
			System.out.println("element is null");
		}
		return false;
	}
	public boolean isEntryDelayLocationVisible(TestCaseInputs inputs) {

		String expectedLocationName = inputs.getInputValue("LOCATION1_NAME");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "EntryDelayLocationText");
			if(element!=null) {
				String actualLocationName = element.getText();
				if(expectedLocationName.equalsIgnoreCase(actualLocationName)) {
					Keyword.ReportStep_Pass(testCase,"Location is found");
					return true;
				}
			}
			else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"element is null");
			}
		}
		else {

			String locatorValue ="//XCUIElementTypeStaticText[@name=\""+expectedLocationName+"\"]";
			if (MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "'" + locatorValue + "' is Present");
				return true;
			}
			else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"'" + locatorValue + "'It is  not present");
			}
		}
		return false;
	}

	public boolean isAlarmWillSoundInTextVisible() {

		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmWillSoundInText")) {
			System.out.println("secs found");
			return true;
		}
		return false;
	}
	public boolean AlarmInSecsCounter(){
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "AlarmWillSoundInSeconds");
			if(element!=null) {
				String value1=element.getText();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				String value2=element.getText();

				if(Math.abs(Integer.parseInt(value2)-Integer.parseInt(value1))>0) {

					Keyword.ReportStep_Pass(testCase, "Entry Delay is happening");
					return true;
				}
				else {

					Keyword.ReportStep_Fail(testCase,FailType.FUNCTIONAL_FAILURE,"Entry Delay is not happening");
				}
			}
		}
		else {
			System.out.println("Id needed for inspecting Entry Delay Counter");
		}

		return false;
	}
	public boolean isSwitchToNightExists() {
  		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton")){
			return true;
		}
		return false;
	}

	public boolean isAttentionButtonExists() {
	
		if(MobileUtils.isMobElementExists(objectDefinition, testCase, "AttentionButton")) {
		return true;
	}
		return false;
	}
}
