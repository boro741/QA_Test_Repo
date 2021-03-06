package com.honeywell.screens;

import java.time.Duration;
import java.util.NoSuchElementException;

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
import com.honeywell.lyric.das.utils.DASAlarmUtils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.appium.java_client.touch.WaitOptions.waitOptions;

public class AlarmScreen extends MobileScreens {
	private TestCases testCase;
	private static final String screenName = "AlarmScreen";
	// Locator values used in the methods
	public static final String ACTIVITYLOGSCROLLUPICON = "icon_arch";
	public static final String ACTIVITYLOGSCROLLDOWNICON = "icon_arrow_up";

	public AlarmScreen(TestCases testCase) {
		super(testCase, screenName);
		this.testCase = testCase;
	}

	public boolean isAlarmScreenDisplayed(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Title", timeout)
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Subtitle", timeout)
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton", timeout);
	}

	public boolean isPleaseWaitDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DismissRequestProcessing");
	}

	public boolean isAlarmDismissButtonDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmDismissButton");
	}

	public boolean clickOnDismissAlarm() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AlarmDismissButton")
				&& MobileUtils.clickOnElement(objectDefinition, testCase, "DismissAlarmPopupOk");
	}

	public boolean clickOnCall() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CallButton");
	}

	// Entry delay screen

	public boolean isEntryDelayScreenDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton");
	}

	public boolean clickOnSwitchToHome() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToHomeButton");
	}

	public boolean clickOnSwitchToHome(int timeout) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton", timeout)) {
			;
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToHomeButton");
		}
		return flag;
	}

	public boolean clickOnSwitchToNight() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToNightButton");
	}

	public boolean clickOnAlarm_NavigateBack() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "Alarm_NaviigateBack");
	}

	public boolean clickOnAttention() {
		if (isEntryDelayScreenDisplayed()) {
			System.out.println("In Entry delay screen");
		}
		return MobileUtils.clickOnElement(objectDefinition, testCase, "AttentionButton");
	}

	public boolean isLoadingProgressBarDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingProgressBar");
	}

	public boolean isWaitingToCloseScreenDisplayed(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WaitingToCloseDoor", timeout);
	}

	public boolean isCallScreenDisplayed() {
		Boolean b = MobileUtils.isMobElementExists(objectDefinition, testCase, "CallPolice")
				&& MobileUtils.isMobElementExists(objectDefinition, testCase, "CancelCall");
		if (b) {
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
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			History_BottomArrow = MobileUtils.getMobElement(objectDefinition, testCase, "History_BottomArrow");
			Alarm = MobileUtils.getMobElement(objectDefinition, testCase, "Alarm_Title");
			// action = action.press(History_BottomArrow).moveTo(Alarm).release().perform();
			action = action.press(element(History_BottomArrow)).waitAction(waitOptions(MobileUtils.getDuration(2000))).moveTo(element(Alarm)).release().perform();
		}
		// For IOS device
		else {
			try {
				// MobileElement activityLogUpElement = null;
				CustomDriver driver = testCase.getMobileDriver();
				History_BottomArrow = driver.findElement(By.name("icon_arch"));
				Alarm = driver.findElement(By.name("Alarm_Navigation_Title"));// MobileUtils.getMobElement(objectDefinition,
																				// testCase, "Alarm_Title");
				if (driver.findElement(By.name(ACTIVITYLOGSCROLLUPICON)).isEnabled()) {
					/*
					 * action.press(History_BottomArrow.getLocation().getX(),
					 * History_BottomArrow.getLocation().getY()) .moveTo(0,
					 * -History_BottomArrow.getLocation().getY() +
					 * Alarm.getLocation().getY()).release() .perform();
					 */
					action.press(
							point(History_BottomArrow.getLocation().getX(), History_BottomArrow.getLocation().getY())).waitAction(waitOptions(MobileUtils.getDuration(2000)))
							.moveTo(point(0, -History_BottomArrow.getLocation().getY() + Alarm.getLocation().getY()))
							.release().perform();
				}
			}

			catch (NoSuchElementException e) {

				throw new Exception(e + "Activity log scroll up icon is not displayed");
			}
		}
		return flag;

	}

	public boolean isMotionDetectedVideoClipDisplayed() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectedClip")) {
			Keyword.ReportStep_Pass(testCase, "Successfully LOCATED LOCATE MOTION VIDEO CLIP");
			return true;
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to LOCATE MOTION VIDEO CLIP");
		}
		return false;
	}

	public boolean closeAlarmHistory() throws Exception {
		boolean flag = true;
		WebElement activityDay = null;
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			activityDay = MobileUtils.getMobElement(objectDefinition, testCase, "AcitvityLogScrollDown");
			// action = action.press(activityDay).moveTo(activityDay.getLocation().getX(),
			// 300).release().perform();
			action = action.press(element(activityDay)).waitAction(waitOptions(MobileUtils.getDuration(2000))).moveTo(point(activityDay.getLocation().getX(), 300)).release()
					.perform();
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
						// action.press(activityDay).moveTo(activityDay.getLocation().getX(),
						// 300).release().perform();
						action.press(element(activityDay)).waitAction(waitOptions(MobileUtils.getDuration(2000))).moveTo(point(activityDay.getLocation().getX(), 300))
								.release().perform();
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

	public boolean clickLiveStreamingArea() {

		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "LiveStreamingArea");

		} else {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Live_icon");

		}

		return flag;
	}

	public boolean clickPauseStreaming() {
		boolean flag = true;
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(3, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(3));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				clickLiveStreamingArea();
				if (MobileUtils.isMobElementExists("name", "Pause Livestream", testCase)) {
					MobileUtils.clickOnElement(testCase, "name", "Pause Livestream");
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			// flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase,
			// "pauseStreaming");
			flag = true;
		}

		return flag;
	}

	public boolean isPlayStreamingVisible() {
		boolean flag = false;

		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(3, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(3));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "playStreaming")) {
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			flag = true;
			Keyword.ReportStep_Pass(testCase, "Paused Streaming is displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Paused Streaming is not displayed");
		}

		return flag;
	}

	public boolean clickPlayStreaming() {
		boolean flag = true;

		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		// fWait.pollingEvery(5, TimeUnit.SECONDS);
		// fWait.withTimeout(3, TimeUnit.MINUTES);
		fWait.pollingEvery(Duration.ofSeconds(5));
		fWait.withTimeout(Duration.ofMinutes(3));
		Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
			public Boolean apply(CustomDriver driver) {
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "playStreaming")) {
					return true;
				} else
					return false;

			}

		});

		if (isEventReceived) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "playStreaming");
		}

		return flag;
	}

	public void swipe(String locatorValue) {

		MobileElement notificationLocation;
		notificationLocation = MobileUtils.getMobElement(testCase, "xpath", locatorValue);
		int NotificationStartX = notificationLocation.getLocation().getX();
		int NotificationStartY = notificationLocation.getLocation().getY();
		int leftsidePoint = 150;
		int rightsidePoint = 600;

		try {
			@SuppressWarnings("rawtypes")
			TouchAction action1 = new TouchAction(testCase.getMobileDriver());
			@SuppressWarnings("rawtypes")
			TouchAction action2 = new TouchAction(testCase.getMobileDriver());
			MultiTouchAction action = new MultiTouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				/*
				 * action1 = action1.press(NotificationStartX,
				 * NotificationStartY).moveTo(-leftsidePoint, 0).release(); action2 =
				 * action2.press(NotificationStartX + rightsidePoint, NotificationStartY)
				 * .moveTo(rightsidePoint, 0).release();
				 */
				action1 = action1.press(point(NotificationStartX, NotificationStartY)).moveTo(point(-leftsidePoint, 0))
						.release();
				action2 = action2.press(point(NotificationStartX + rightsidePoint, NotificationStartY)).waitAction(waitOptions(MobileUtils.getDuration(2000)))
						.moveTo(point(rightsidePoint, 0)).release();
				action.add(action1).add(action2).perform();
				Keyword.ReportStep_Pass(testCase, "Swiped to view the options");
			} else {
				NotificationStartX = notificationLocation.getSize().getWidth() / 2;
				// action1 = action1.press(NotificationStartX, NotificationStartY).moveTo(-80,
				// 0).release();
				action1 = action1.press(point(NotificationStartX, NotificationStartY)).waitAction(waitOptions(MobileUtils.getDuration(2000))).moveTo(point(-80, 0)).release();
				action1.perform();
				// action1.tap(NotificationStartX, NotificationStartY).release().perform();
				action1.tap(point(NotificationStartX, NotificationStartY)).release().perform();
				Keyword.ReportStep_Pass(testCase, "Swiped to view the options");

			}
		} catch (Exception e) {
			System.out.println("Fix it");
			Keyword.ReportStep_Pass(testCase, "Failed to Swipe to view the options");
		}
	}

	public boolean switchToHomeOnNotificationExists() {
		boolean flag = false;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchHomeNotificationButton", 2)) {
			flag = true;
		}
		return flag;
	}

	public boolean switchToNightOnNotificationExists() {
		boolean flag = false;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchNightNotificationButton", 2)) {
			flag = true;
		}
		return flag;
	}

	public boolean clickswitchToHomeOnNotification() {
		boolean flag = true;
		CustomDriver driver = testCase.getMobileDriver();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			driver.findElement(By.xpath(
					"//android.widget.Button[@resource-id='android:id/action0' and @content-desc='SWITCH TO HOME']"))
					.click();
		} else {
			// flag &= MobileUtils.clickOnElement(objectDefinition, testCase,
			// "SwitchHomeNotificationButton");
			testCase.getMobileDriver().findElementByName("Switch to Home").click();
			flag = true;
		}
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton")) {
			Keyword.ReportStep_Pass(testCase,
					"Failed to click on SWITCH TO HOME button in PUSH NOTIFICATION. Hence clicking on SWITCH TO HOME button in Entry Delay screen");
			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToHomeButton");
		}
		// flag &= DASAlarmUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR",
		// 2);
		return flag;
	}

	public boolean clickswitchToNightOnNotification() {
		boolean flag = true;
		CustomDriver driver = testCase.getMobileDriver();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			driver.findElement(By.xpath(
					"//android.widget.Button[@resource-id='android:id/action0' and @content-desc='SWITCH TO NIGHT']"))
					.click();
		} else {
			// flag &= MobileUtils.clickOnElement(objectDefinition, testCase,
			// "SwitchNightNotificationButton");
			testCase.getMobileDriver().findElementByName("Switch to Night").click();
			flag = true;
		}
//		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton")) {
//			Keyword.ReportStep_Pass(testCase,
//					"Failed to click on SWITCH TO NIGHT button in PUSH NOTIFICATION. Hence clicking on SWITCH TO NIGHT button in Entry Delay screen");
//			flag &= MobileUtils.clickOnElement(objectDefinition, testCase, "SwitchToNightButton");
//		}
		flag &= DASAlarmUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 2);
		return flag;
	}

	public boolean isSwitchToHomeExists() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToHomeButton")) {
			return true;
		}
		return false;
	}

	public boolean clickLiveStreamingMaximize() {
		boolean flag = true;
		clickLiveStreamingArea();

		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Maximize_button");
		return flag;
	}

	public boolean clickLiveStreamingMinimize() {
		boolean flag = true;
		clickLiveStreamingArea();

		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "Minimize_button");
		return flag;
	}

	public boolean isAlarmTitleVisible() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_Title", 10)) {
			Keyword.ReportStep_Pass(testCase, "ALarm Title is found");
			return true;
		}
		return false;
	}

	public boolean isAlarmLocationVisible(TestCaseInputs inputs) {
		String expectedLocationName = inputs.getInputValue("LOCATION1_NAME");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "Alarm_Subtitle");
			if (element != null) {
				String actualLocationName = element.getText();
				if (expectedLocationName.equalsIgnoreCase(actualLocationName)) {
					System.out.println("AlarmLocation is found");
					return true;
				}
			} else {
				System.out.println("element is null");
			}
		} else {
			String locatorValue = "//XCUIElementTypeStaticText[@value=\"" + expectedLocationName + "\"]";
			if (MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 10)) {
				System.out.println("AlarmLocation is found");

				Keyword.ReportStep_Pass(testCase, "'" + locatorValue + "' is Present");
				return true;
			} else {
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
		MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase, "ProgressBar_timer");
		if (element != null) {
			System.out.println("Element is not null");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				String value1 = MobileUtils.getAttribute(testCase, element, "name");
				String[] percent1 = value1.split("_");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}
				String value2 = MobileUtils.getAttribute(testCase, element, "name");
				String[] percent2 = value2.split("_");
				if (Math.abs(Integer.parseInt(percent2[1]) - Integer.parseInt(percent1[1])) > 0) {
					flag = true;

					Keyword.ReportStep_Pass(testCase, "Camera is Live Streaming and progressing");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera Live streaming is not progressing");
				}

			} else {
				System.out.println("In ios else verify live");

				String value1 = MobileUtils.getAttribute(testCase, element, "value");
				System.out.println(value1);
				String percent1 = value1.substring(0, value1.length() - 1);

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				String value2 = MobileUtils.getAttribute(testCase, element, "value");
				System.out.println(value2);
				String percent2 = value2.substring(0, value1.length() - 1);

				if (Math.abs(Integer.parseInt(percent2) - Integer.parseInt(percent1)) > 0) {
					flag = true;
					System.out.println("Live Streaming Progress is found");
					Keyword.ReportStep_Pass(testCase, "Camera is Live Streaming and progressing");

				}
			}

		} else {
			flag = false;
			System.out.println("element is null");
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Element can't be located!");
		}

		return flag;
	}

	public boolean isAlarm_NavigatebackVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "Alarm_NaviigateBack", 5)) {
			System.out.println("ALarm Back is found");
			return true;
		}
		return false;
	}

	public boolean isCallButtonVisible() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CallButton")) {
			System.out.println("Call is found");
			return true;
		}

		return false;
	}

	public boolean isEntryDelayTitleVisible() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EntryDelayDoorOpenTitle")) {
			System.out.println("EntryDelay title found");
			return true;
		}
		return false;
	}

	public boolean isEntryDelaySubTitleVisible(TestCaseInputs inputs) {
		String expectedSensorName = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1");
		MobileElement element = MobileUtils.getMobElement(objectDefinition, testCase,
				"EntryDelayDoorOpenSensorNameTitle");
		if (element != null) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				String actualSensorName = element.getText();
				if (actualSensorName.toUpperCase().contains(expectedSensorName.toUpperCase())) {
					System.out.println("EntryDelay subtitle found");
					return true;
				}
			} else {
				String actualSensorName = MobileUtils.getAttribute(testCase, element, "name");
				System.out.println(actualSensorName);
				;
				;
				if (actualSensorName.toUpperCase().contains(expectedSensorName.toUpperCase())) {
					System.out.println("EntryDelay subtitle found");
					return true;
				}
			}
		} else {
			System.out.println("element is null");
		}
		return false;
	}

	public boolean isEntryDelayLocationVisible(TestCaseInputs inputs) {

		String expectedLocationName = inputs.getInputValue("LOCATION1_NAME");
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "EntryDelayLocationText");
			if (element != null) {
				String actualLocationName = element.getText();
				if (expectedLocationName.equalsIgnoreCase(actualLocationName)) {
					Keyword.ReportStep_Pass(testCase, "Location is found");
					return true;
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "element is null");
			}
		} else {

			String locatorValue = "//XCUIElementTypeStaticText[@value='" + expectedLocationName + "']";
			if (MobileUtils.isMobElementExists("xpath", locatorValue, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "'" + locatorValue + "' is Present");
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"'" + locatorValue + "'It is  not present");
			}
		}
		return false;
	}

	public boolean isAlarmWillSoundInTextVisible() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AlarmWillSoundInText")) {
			System.out.println("secs found");
			return true;
		}
		return false;
	}

	public boolean AlarmInSecsCounter() {
		WebElement element = MobileUtils.getMobElement(objectDefinition, testCase, "AlarmWillSoundInSeconds");
		if (element != null) {
			String value1 = element.getText();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String value2 = element.getText();

			if (Math.abs(Integer.parseInt(value2) - Integer.parseInt(value1)) > 0) {

				Keyword.ReportStep_Pass(testCase, "Entry Delay is happening");
				return true;
			} else {

				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Entry Delay is not happening");
			}
		}
		return false;
	}

	public boolean isSwitchToNightExists() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SwitchToNightButton")) {
			return true;
		}
		return false;
	}

	public boolean isAttentionButtonExists() {

		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "AttentionButton")) {
			return true;
		}
		return false;
	}
}
