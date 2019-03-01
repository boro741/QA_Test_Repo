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
import com.honeywell.lyric.das.utils.CameraUtils;

import io.appium.java_client.TouchAction;
import static io.appium.java_client.touch.offset.PointOption.point;

public class CameraSettingsScreen extends MobileScreens {

	private static final String screenName = "CameraSettings";

	public CameraSettingsScreen(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isCameraSettingsHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraSettingsScreenHeaderTitle", timeOut);
	}

	public boolean isManageAlertsLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ManageAlertsLabel", timeOut);
	}

	public boolean clickOnManageAlertsLabel() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "ManageAlertsLabel");
	}

	public boolean isMotionDetectionLabelVisible(TestCases testCase, int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionLabel", timeOut);
	}

	public boolean clickOnMotionDetectionLabel() {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionDetectionLabel");
		} else {
			testCase.getMobileDriver().findElementByName("Camera_Settings_1_0_cell").click();
			return true;
		}
	}

	public boolean isBackButtonVisibleInMotionDetectionSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
	}

	public boolean clickOnBackButtonInMotionDetectionSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
	}

	public boolean isSoundDetectionLabelVisible(TestCases testCase, int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionLabel", timeOut);
	}

	public boolean clickOnSoundDetectionLabel() {
		boolean flag = true;
		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SoundDetectionLabel");
		return flag;
	}

	public boolean clickOnCameraConfiguration() {
		boolean flag = true;
		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "CameraConfiguration");
		return flag;
	}

	public boolean isSoundDetectionScreenHeaderTitleVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionScreenHeaderTitle", timeOut);
	}

	public boolean isBackButtonVisibleInSoundDetectionSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInSoundDetectionScreen");
	}

	public boolean clickOnBackButtonInSoundDetectionSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInSoundDetectionScreen");
	}

	public boolean isNightVisionLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionLabel", timeOut);
	}

	public boolean clickOnNightVisionLabel() {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "NightVisionLabel");
		} else {
			testCase.getMobileDriver().findElementByName("Camera_Settings_1_2_cell").click();
			return true;
		}
	}

	public boolean isBackButtonVisibleInNightVisionSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInNigthVisionScreen");
	}

	public boolean clickOnBackButtonVisibleInNightVisionSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInNigthVisionScreen");
	}

	public boolean isVideoQualityLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityLabel", timeOut);
	}

	public boolean clickOnVideoQualityLabel() {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "VideoQualityLabel");
		} else {
			testCase.getMobileDriver().findElementByName("Camera_Settings_1_3_cell").click();
			return true;
		}
	}

	public boolean isBackButtonVisibleInVideoQualitySettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInVideoQualityScreen");
	}

	public boolean clickOnBackButtonVisibleInVideoQualitySettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInVideoQualityScreen");
	}

	public boolean isLoadingSnapshotSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSnapshotSpinner");
	}

	public boolean isRetryInLoadingSpanshotVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RetryInLoadingSnapshotSpinner")) {
			WebElement retryLoadingSpinnerVisibilityStatus = MobileUtils.getMobElement(objectDefinition, testCase,
					"RetryInLoadingSnapshotSpinner");
			if (retryLoadingSpinnerVisibilityStatus.getAttribute("visible").equals("false")) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
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
		Thread.sleep(5000);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEventAlertsSwitch").getText()
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (testCase.getMobileDriver().findElementByName("MOTIONDETECTED_toggle").getAttribute("value")
					.equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean toggleMotionEventAlertsSwitch(TestCases testCase) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionEventAlertsSwitch");
		} else {
			testCase.getMobileDriver().findElementByName("MOTIONDETECTED_toggle").click();
			return true;
		}
	}

	public boolean isCameraFaceDectiontAlertsSwitchEnabled(TestCases testCase) throws Exception {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraFaceDetectionAlertsSwitch").getText()
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (testCase.getMobileDriver().findElementById("FaceRecognization_toggle").getAttribute("value")
					.equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}

		}
	}

	public boolean toggleCameraFaceDetectionAlertsSwitch(TestCases testCase) {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraFaceDetectionAlertsSwitch");
		} else {
			testCase.getMobileDriver().findElementById("FaceRecognization_toggle").click();
			return true;
		}
	}

	public boolean isCameraFaceDetectionAlertSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "ThermostatIndoorTempAlertSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Thermostat Indoor Temperature Alert Switch");
		}
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

	public boolean isCameraMotionDetectionSwitchEnabled(TestCases testCase) throws Exception {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionSwitch").getText()
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (testCase.getMobileDriver().findElementByName("Motion Detection_toggle").getAttribute("value")
					.equalsIgnoreCase("1")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean toggleCameraMotionDetectionSwitch(TestCases testCase) {
		if (testCase.getMobileDriver().getPlatformName().contains("Android")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionDetectionSwitch");
		} else {
			testCase.getMobileDriver().findElementByName("Motion Detection_toggle").click();
			return true;
		}
	}

	public boolean isMotionDetectionZoneEnabled() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionZoneVisible")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionZoneVisible")
						.getAttribute("enabled").equalsIgnoreCase("true")) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			flag = flag & MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionZoneVisible");
		}
		return flag;
	}

	public boolean isCameraSetingsOptionVisible(String settingOptions) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.isMobElementExists("XPath",
					"//android.widget.TextView[@text='" + settingOptions + "']", testCase);
		} else {
			flag = flag & MobileUtils.isMobElementExists("XPath",
					"//XCUIElementTypeStaticText[contains(@name, '_subTitle') and @value='" + settingOptions + "']",
					testCase);
		}
		return flag;
	}

	/* Method to click on any element from camera settings screen */
	public boolean clickONCameraSetingsOption(String settingOptions) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			flag = flag & MobileUtils.clickOnElement(testCase, "XPath",
					"//android.widget.TextView[@text='" + settingOptions + "']");

		} else {
			flag = flag & MobileUtils.clickOnElement(testCase, "XPath",
					"//XCUIElementTypeStaticText[contains(@name, '_subTitle') and @value='" + settingOptions + "']");

		}
		return flag;
	}

	/* Method to check the camera Configuration options */
	public boolean isCameraConfigurationsOptionVisible(String settingName) throws Exception {
		String attribute;
		WebElement element = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			attribute = "text";
			if (settingName.equals("NAME")) {
				settingName = "Name";
			}
			element = MobileUtils.getMobElement(testCase, "XPATH",
					"//android.widget.TextView[@" + attribute + "='" + settingName + "']");
		} else {
			attribute = "value";
			element = MobileUtils.getMobElement(testCase, "XPATH",
					"//XCUIElementTypeStaticText[@" + attribute + "='" + settingName + "']");
		}
		if (element.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	/* Switch under Camera Manage Alerts screen */
	public boolean isCameraEmailNotificationsTextVisible(String emailValue) {
		System.out.println(MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStatusEmailNotification")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "CameraStatusEmailNotification")
						.equalsIgnoreCase(emailValue));
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStatusEmailNotification")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "CameraStatusEmailNotification")
						.equalsIgnoreCase(emailValue)) {
			return true;
		} else {
			return false;
		}
	}

	/* Switch under Camera Manage Alerts screen */
	public boolean isSoundEmailNotificationTextVisible(String emailValue) {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundEventEmailNotification")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "SoundEventEmailNotification")
						.equalsIgnoreCase(emailValue)) {
			return true;
		} else {
			return false;
		}
	}

	/* Switch under Camera Manage Alerts screen */
	public boolean isMotionEmailNotificationTextVisible(String emailValue) {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			// TouchAction action = new TouchAction(testCase.getMobileDriver());
			int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionEventEmailNotification")
					&& MobileUtils.getFieldValue(objectDefinition, testCase, "MotionEventEmailNotification")
							.equalsIgnoreCase(emailValue)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/*
	 * Checking for the Email Switch enabled for camera event under Camera Manage
	 * Alerts screen
	 */
	public boolean isCameraEmailNotificationSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraStatusEmailNotificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatusEmailNotificationSwitch")
						.getText().equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraStatusEmailNotificationSwitch")
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

	/*
	 * Method to toggle the Email Switch enabled for camera event under Camera
	 * Manage Alerts screen
	 */
	public boolean toggleCameraEmailNotificationSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraStatusEmailNotificationSwitch");
	}

	/*
	 * Checking for the Email Switch enabled for sound event under Camera Manage
	 * Alerts screen
	 */
	public boolean isSoundEmailNotificationSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundEventEmailNotificationSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundEventEmailNotificationSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundEventEmailNotificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Sound Event Switch");
		}
	}

	/*
	 * Method to toggle the Email Switch enabled for sound event under Camera Manage
	 * Alerts screen
	 */
	public boolean toggleSoundEmailNotificationSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SoundEventEmailNotificationSwitch");
	}

	/*
	 * Checking for the Email Switch enabled for motion event under Camera Manage
	 * Alerts screen
	 */
	public boolean isMotionEmailNotificationSwitchEnabled(TestCases testCase) throws Exception {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionEventEmailNotificationSwitch", 20)) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEventEmailNotificationSwitch")
						.getText().equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionEventEmailNotificationSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Motion Event Switch");
		}
	}

	/*
	 * Method to toggle the Email Switch enabled for motion event under Camera
	 * Manage Alerts screen
	 */
	public boolean toggleMotionEmailNotificationSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionEventEmailNotificationSwitch");
	}

	public boolean isMotionEmailNotificationVisible(TestCases testCase) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionEventEmailNotificationDAS");
	}

	public boolean isMotionSensitivityEnabled(TestCases testCase) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensitivityVisible")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionSensitivityVisible")
						.getAttribute("enabled").equalsIgnoreCase("true")) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionSensitivityVisible")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionSensitivityVisible")
						.getAttribute("value").equalsIgnoreCase("enabled")) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isCameraMotionDetectionStatusYES(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.getFieldValue(objectDefinition, testCase, "MotionDetectionStatus").equalsIgnoreCase("ON")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isAreasOutsideZonesWarningPopupHeaderTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AreasOutsideZonesWarningPopupHeaderTitle");
	}

	public boolean isAreasOutsideZonesWarningPopupMsgDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AreasOutsideZonesWarningPopupMsg");
	}

	public boolean clickOnNOButtonInAreasOutsideZonesWarningPopup() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NOButton");
	}

	public boolean clickOnYESButtonInAreasOutsideZonesWarningPopup() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "YESButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "YESButton");
		} else {
			return false;
		}
	}

	public boolean isMotionDetectionZonesWarningPopupHeaderTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"MotionDetectionZonesWarningPopupHeaderTitle");
	}

	public boolean isMotionDetectionZonesWarningPopupMsgDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionZonesWarningPopupMsg");
	}

	public boolean clickOnOKButtonInMotionDetectionZonesWarningPopup() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButton");
		} else {
			return false;
		}
	}

	public boolean navigateBackAndForthInManageAlertsScreen(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInManageAlertsScreen")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInManageAlertsScreen");
			if (isCameraSettingsHeaderTitleVisible(20) && isManageAlertsLabelVisible(10)) {
				flag = flag & clickOnManageAlertsLabel();
			}
		}
		return flag;
	}

	public boolean navigateBackAndForthInCameraSettingsScreen(TestCases testCase) {
		PrimaryCard pc = new PrimaryCard(testCase);
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackCameraSettings")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackCameraSettings");
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
		}
		return flag;
	}

	public boolean navigateBackAndForthInMotionDetectionScreen(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInMotionDetectionScreen")) {
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
			if (isAreasOutsideZonesWarningPopupHeaderTitleDisplayed()
					&& isAreasOutsideZonesWarningPopupMsgDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Area Outside Zone Warning Popup is displayed");
				clickOnNOButtonInAreasOutsideZonesWarningPopup();
			}
			if (isCameraSettingsHeaderTitleVisible(20) && isMotionDetectionLabelVisible(testCase, 10)) {
				flag = flag & clickOnMotionDetectionLabel();
			}
		}
		return flag;
	}

	public boolean isEmailNotificationCellVisibleAfterTurningONAlerts(TestCases testCase) {
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

	public boolean selectZone(String zoneToBeSelected) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH", "//android.widget.TextView[@text= '" + zoneToBeSelected + "']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//android.widget.TextView[@text= '" + zoneToBeSelected + "']")
							.getAttribute("checked").equalsIgnoreCase("true")) {
				Keyword.ReportStep_Pass(testCase, zoneToBeSelected + " is already selected");
			} else {
				Keyword.ReportStep_Pass(testCase, zoneToBeSelected + " is unselected");
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//android.widget.TextView[@text= '" + zoneToBeSelected + "']");
				Keyword.ReportStep_Pass(testCase, "Clicked on the zone: " + zoneToBeSelected);
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableOrDisableDetectionZone")) {
					Keyword.ReportStep_Pass(testCase, "Enable Detection Zone button is displayed");
					if (MobileUtils.getMobElement(objectDefinition, testCase, "EnableOrDisableDetectionZone")
							.getAttribute("checked").equalsIgnoreCase("false")) {
						Keyword.ReportStep_Pass(testCase,
								"Enable Detection Zone button is: " + MobileUtils
										.getMobElement(objectDefinition, testCase, "EnableOrDisableDetectionZone")
										.getAttribute("checked"));
						MobileUtils.clickOnElement(objectDefinition, testCase, "EnableOrDisableDetectionZone");
						if (MobileUtils.getMobElement(objectDefinition, testCase, "EnableOrDisableDetectionZone")
								.getAttribute("checked").equalsIgnoreCase("true")) {
							Keyword.ReportStep_Pass(testCase,
									"Enable Detection Zone button is changed to: " + MobileUtils
											.getMobElement(objectDefinition, testCase, "EnableOrDisableDetectionZone")
											.getAttribute("checked"));
						}
					}
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeButton[@name = '" + zoneToBeSelected + "']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeButton[@name= '" + zoneToBeSelected + "']")
							.getAttribute("value") != null) {
				Keyword.ReportStep_Pass(testCase, zoneToBeSelected + " is already selected");
			} else {
				Keyword.ReportStep_Pass(testCase, zoneToBeSelected + " is unselected");
				// MobileUtils.clickOnElement(testCase, "XPATH",
				// "//XCUIElementTypeButton[@name = '" + zoneToBeSelected + "']");
				testCase.getMobileDriver()
						.findElementByXPath(
								"//XCUIElementTypeButton[@name = '" + zoneToBeSelected.replaceAll("\\s", "") + "']")
						.click();
				Keyword.ReportStep_Pass(testCase, "Clicked on the zone: " + zoneToBeSelected);
				if (MobileUtils.isMobElementExists(objectDefinition, testCase, "EnableOrDisableDetectionZone")) {
					Keyword.ReportStep_Pass(testCase, "Enable Detection Zone button is displayed");
					if (MobileUtils.getFieldValue(objectDefinition, testCase, "EnableOrDisableDetectionZone")
							.equalsIgnoreCase("disable")) {
						Keyword.ReportStep_Pass(testCase, "Enable Detection Zone button is: " + MobileUtils
								.getFieldValue(objectDefinition, testCase, "EnableOrDisableDetectionZone"));
						MobileUtils.clickOnElement(objectDefinition, testCase, "EnableOrDisableDetectionZone");
						if (MobileUtils.getFieldValue(objectDefinition, testCase, "EnableOrDisableDetectionZone")
								.equalsIgnoreCase("enable")) {
							Keyword.ReportStep_Pass(testCase,
									"Enable Detection Zone button is changed to: " + MobileUtils
											.getFieldValue(objectDefinition, testCase, "EnableOrDisableDetectionZone"));
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Enable Detection Zone button is not displayed");
				}
			}
		}
		return flag;
	}

	public boolean isMotionSensitivityOptionVisible(String motionSensitivityOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("ID", motionSensitivityOption.toLowerCase(), testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='" + motionSensitivityOption.toLowerCase() + "_subTitle" + "']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@name='" + motionSensitivityOption.toLowerCase()
											+ "_subTitle" + "']")
							.getAttribute("value").equalsIgnoreCase(motionSensitivityOption.toLowerCase())) {
				return flag;
			} else {
				if (motionSensitivityOption.toLowerCase().equals("normal")) {
					motionSensitivityOption = "medium";
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeStaticText[@name='" + motionSensitivityOption.toLowerCase() + "_subTitle"
									+ "']",
							testCase)
							&& MobileUtils
									.getMobElement(testCase, "XPATH",
											"//XCUIElementTypeStaticText[@name='"
													+ motionSensitivityOption.toLowerCase() + "_subTitle" + "']")
									.getAttribute("value").equalsIgnoreCase("normal")) {
						return flag;
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isMotionSensitivityStatusSetToExpected(TestCases testCase, String motionSensitivityStatus) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (motionSensitivityStatus.equalsIgnoreCase("medium")) {
				motionSensitivityStatus = "normal";
			}
			if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
					+ motionSensitivityStatus.toLowerCase()
					+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				int startx = (dimension.width * 20) / 100;
				int starty = (dimension.height * 62) / 100;
				int endx = (dimension.width * 22) / 100;
				int endy = (dimension.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
						+ motionSensitivityStatus.toLowerCase()
						+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
						testCase, 5)) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
					testCase)) {
				return flag;
			} else {
				/*
				 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
				 * (dimension.getHeight() * .6)) .release().perform(); action.press(10, (int)
				 * (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
				 * .release().perform();
				 */
				action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
						testCase)) {
					return flag;
				} else {
					if (motionSensitivityStatus.toLowerCase().equals("normal")) {
						motionSensitivityStatus = "medium";
						if (MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
										+ "']/XCUIElementTypeImage[contains(@name, '"
										+ motionSensitivityStatus.toLowerCase() + "_Image" + "')]",
								testCase)) {
							return flag;
						} else {
							flag = false;
						}
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	public boolean setSoundSensitivityStatusToExpected(TestCases testCase, String motionSensitivityStatus) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int counter = 0;
			if (motionSensitivityStatus.equalsIgnoreCase("medium")) {
				motionSensitivityStatus = "normal";
			}
			if (MobileUtils.isMobElementExists("XPATH", "//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/"
					+ motionSensitivityStatus.toLowerCase() + "\'" + "]", testCase)) {
				while ((MobileUtils
						.isMobElementExists("XPATH",
								"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/"
										+ motionSensitivityStatus.toLowerCase() + "\'" + "]",
								testCase))
						&& counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", "//*[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/" + motionSensitivityStatus.toLowerCase() + "\'" + "]");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
							+ motionSensitivityStatus.toLowerCase()
							+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
							testCase)) {
						break;
					} else {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					}
					counter++;
				}
			} else {
				flag = false;
			}
		} else {
			int counter = 0;
			if ((MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
					testCase))) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
						testCase)) && counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
									+ "']/XCUIElementTypeImage[contains(@name, '"
									+ motionSensitivityStatus.toLowerCase() + "_Image" + "')]",
							testCase)) {
						break;
					} else {
						System.out.println("%%%%%Scoll down again");
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimension.getHeight() * .9)) .moveTo(0, -(int) (dimension.getHeight() *
						 * .6)).release().perform();
						 */
					}
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isSoundDetectionOptionVisible(String indoorSoundOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//android.widget.TextView[@resource-id='com.honeywell.android.lyric:id/list_item_lyric_horizontal_text_view_primary_text' and @text='"
							+ indoorSoundOption + "']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("NAME", indoorSoundOption + "_subTitle", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isSoundDetectionSectionIsEnabled(TestCases testCase) {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionSection", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundDetectionSection")
						.getAttribute("enabled").equalsIgnoreCase("TRUE")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundDetectionSection").getAttribute("value")
						.equalsIgnoreCase("disabled")) {
					return true;
				} else {
					return false;
				}

			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[contains(@name, '_subTitle') and @value='Sound Detection']/parent::XCUIElementTypeCell",
					testCase)
					&& MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name, '_subTitle') and @value='Sound Detection']/parent::XCUIElementTypeCell")
							.getAttribute("value").equalsIgnoreCase("enabled")) {
				return true;
			} else {
				return false;
			}
		}
	}

	public boolean isMotionDetectionSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionLabel")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionLabel").getAttribute("enabled")
						.equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isSoundDetectionSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionLabel")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "SoundDetectionLabel").getAttribute("enabled")
						.equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isNightVisionSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionLabel") && MobileUtils
				.getMobElement(objectDefinition, testCase, "NightVisionLabel").getAttribute("enabled").equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isVideoQualitySectionEnabled(TestCases testCase) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		int startx = (dimension.width * 20) / 100;
		int starty = (dimension.height * 62) / 100;
		int endx = (dimension.width * 22) / 100;
		int endy = (dimension.height * 35) / 100;
		testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityLabel")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "VideoQualityLabel").getAttribute("enabled")
						.equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isCameraLEDSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraLEDLabel") && MobileUtils
				.getMobElement(objectDefinition, testCase, "CameraLEDLabel").getAttribute("enabled").equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isCameraMicrophoneSectionEnabled(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionLabel")
				&& MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionLabel").getAttribute("enabled")
						.equals("true")) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean isCameraSoundDetectionSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundDetectionSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundDetectionSwitch").getAttribute("value")
						.equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Camera Sound Detection Switch");
		}
	}

	public boolean toggleCameraSoundDetectionSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SoundDetectionSwitch");
	}

	public boolean isCameraSoundDetectionStatusYES(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionStatus")) {
			if (!MobileUtils.getFieldValue(objectDefinition, testCase, "SoundDetectionStatus")
					.equalsIgnoreCase("OFF")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//*[contains(@name,'_subTitle') and @value='Sound Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]",
						testCase)) {
					if (MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Sound Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
							.getAttribute("value").equalsIgnoreCase("ON")) {
						return flag;
					} else {
						flag = false;
					}
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isSoundSensitivityEnabled() {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundSensitivityVisible")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundSensitivityVisible")
						.getAttribute("clickable").equalsIgnoreCase("true")) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundSensitivityVisible")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "SoundSensitivityVisible")
						.getAttribute("value").equalsIgnoreCase("enabled")) {
					return flag;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	public boolean isSoundSensitivityStatusSetToExpected(TestCases testCase, String motionSensitivityStatus) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (motionSensitivityStatus.equalsIgnoreCase("medium")) {
				motionSensitivityStatus = "normal";
			}
			if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
					+ motionSensitivityStatus.toLowerCase()
					+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				int startx = (dimension.width * 20) / 100;
				int starty = (dimension.height * 62) / 100;
				int endx = (dimension.width * 22) / 100;
				int endy = (dimension.height * 35) / 100;
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
						+ motionSensitivityStatus.toLowerCase()
						+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
						testCase, 5)) {
					return flag;
				} else {
					flag = false;
				}
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
							+ "']/XCUIElementTypeImage[contains(@name, '" + motionSensitivityStatus.toLowerCase()
							+ "_Image" + "')]",
					testCase)) {
				return flag;
			} else {
				/*
				 * action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int)
				 * (dimension.getHeight() * .6)) .release().perform(); action.press(10, (int)
				 * (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
				 * .release().perform();
				 */
				action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				action.press(point(10, (int) (dimension.getHeight() * .9)))
						.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
				if (MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
								+ "']/XCUIElementTypeImage[contains(@name, '" + motionSensitivityStatus.toLowerCase()
								+ "_Image" + "')]",
						testCase)) {
					return flag;
				} else {
					if (motionSensitivityStatus.toLowerCase().equals("normal")) {
						motionSensitivityStatus = "medium";
						if (MobileUtils.isMobElementExists("XPATH",
								"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
										+ "']/XCUIElementTypeImage[contains(@name, '"
										+ motionSensitivityStatus.toLowerCase() + "_Image" + "')]",
								testCase)) {
							return flag;
						} else {
							flag = false;
						}
					} else {
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	public boolean setMotionSensitivityStatusToExpected(TestCases testCase, String motionSensitivityStatus) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
		@SuppressWarnings("rawtypes")
		TouchAction action = new TouchAction(testCase.getMobileDriver());
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int counter = 0;
			if (motionSensitivityStatus.equalsIgnoreCase("medium")) {
				motionSensitivityStatus = "normal";
			}
			if (MobileUtils.isMobElementExists("XPATH", "//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/"
					+ motionSensitivityStatus.toLowerCase() + "\'" + "]", testCase)) {
				while ((MobileUtils
						.isMobElementExists("XPATH",
								"//*[@resource-id=" + "\'" + "com.honeywell.android.lyric:id/"
										+ motionSensitivityStatus.toLowerCase() + "\'" + "]",
								testCase))
						&& counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH", "//*[@resource-id=" + "\'"
							+ "com.honeywell.android.lyric:id/" + motionSensitivityStatus.toLowerCase() + "\'" + "]");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH", "//*[contains(@resource-id,'"
							+ motionSensitivityStatus.toLowerCase()
							+ "')]/android.widget.LinearLayout[1]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
							testCase)) {
						break;
					} else {
						int startx = (dimension.width * 20) / 100;
						int starty = (dimension.height * 62) / 100;
						int endx = (dimension.width * 22) / 100;
						int endy = (dimension.height * 35) / 100;
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
						testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
					}
					counter++;
				}
			} else {
				flag = false;
			}
		} else {
			int counter = 0;
			if ((MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
					testCase))) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']",
						testCase)) && counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell[@name= '" + motionSensitivityStatus.toLowerCase() + "_cell" + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeCell[@name='" + motionSensitivityStatus.toLowerCase() + "_cell"
									+ "']/XCUIElementTypeImage[contains(@name, '"
									+ motionSensitivityStatus.toLowerCase() + "_Image" + "')]",
							testCase)) {
						break;
					} else {
						System.out.println("%%%%%Scroll down again");
						/*
						 * action.press(10, (int) (dimension.getHeight() * .9)) .moveTo(0, -(int)
						 * (dimension.getHeight() * .6)).release().perform(); action.press(10, (int)
						 * (dimension.getHeight() * .9)) .moveTo(0, -(int) (dimension.getHeight() *
						 * .6)).release().perform();
						 */
						action.press(point(10, (int) (dimension.getHeight() * .9)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
						action.press(point(10, (int) (dimension.getHeight() * .9)))
								.moveTo(point(0, -(int) (dimension.getHeight() * .6))).release().perform();
					}
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isNightVisionOptionVisible(String nightVisionOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc,'" + nightVisionOption
							+ "')]/android.widget.RelativeLayout/android.widget.TextView",
					testCase)
					&& MobileUtils
							.getFieldValue(testCase, "XPATH",
									"//*[starts-with(@content-desc,'" + nightVisionOption
											+ "')]/android.widget.RelativeLayout/android.widget.TextView")
							.equalsIgnoreCase(nightVisionOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='" + nightVisionOption.toLowerCase() + "_subTitle" + "']",
					testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@name='" + nightVisionOption.toLowerCase()
											+ "_subTitle" + "']")
							.getAttribute("value").equalsIgnoreCase(nightVisionOption.toLowerCase())) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isNightVisionStatusSetToExpectedInCameraSettingsScreen(TestCases testCase,
			String nightVisionStatus) {
		boolean flag = true;

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc, 'Night Vision')]/android.widget.LinearLayout/android.widget.TextView",
					testCase)
					&& MobileUtils.getFieldValue(testCase, "XPATH",
							"//*[starts-with(@content-desc, 'Night Vision')]/android.widget.LinearLayout/android.widget.TextView")
							.equalsIgnoreCase(nightVisionStatus)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[contains(@name,'_subTitle') and @value='Night Vision']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]",
					testCase)
					&& MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Night Vision']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
							.getAttribute("value").equalsIgnoreCase(nightVisionStatus)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isNightVisionStatusSetToExpectedInNightVisionScreen(TestCases testCase, String nightVisionStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc,'" + nightVisionStatus
							+ "')]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			// if (MobileUtils.isMobElementExists("XPATH",
			// "//XCUIElementTypeCell[@name='" + nightVisionStatus.toLowerCase() + "_cell"
			// + "']/XCUIElementTypeImage[contains(@name, '" +
			// nightVisionStatus.toLowerCase() + "_Image"
			// + "')]",
			// testCase))
			if (testCase.getMobileDriver()
					.findElementByXPath("//XCUIElementTypeCell[@name='" + nightVisionStatus.toLowerCase() + "_cell"
							+ "']/XCUIElementTypeImage[@name='" + nightVisionStatus.toLowerCase() + "_Image"
							+ "']") != null) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean setNightVisionStatusToExpected(TestCases testCase, String nightVisionStatus) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int counter = 0;
			if (MobileUtils.isMobElementExists("XPATH", "//*[starts-with(@content-desc, '" + nightVisionStatus
					+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '" + nightVisionStatus + "']",
					testCase)) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//*[starts-with(@content-desc, '" + nightVisionStatus
								+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '"
								+ nightVisionStatus + "']",
						testCase)) && counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//*[starts-with(@content-desc, '" + nightVisionStatus
									+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '"
									+ nightVisionStatus + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH", "//*[starts-with(@content-desc,'" + nightVisionStatus
							+ "')]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
							testCase)) {
						break;
					}
					counter++;
				}
			} else {
				flag = false;
			}
		} else {
			int counter = 0;
			if ((MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name= '" + nightVisionStatus.toLowerCase() + "_cell" + "']", testCase))) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name= '" + nightVisionStatus.toLowerCase() + "_cell" + "']", testCase))
						&& counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell[@name= '" + nightVisionStatus.toLowerCase() + "_cell" + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeCell[@name='" + nightVisionStatus.toLowerCase() + "_cell"
									+ "']/XCUIElementTypeImage[contains(@name, '" + nightVisionStatus.toLowerCase()
									+ "_Image" + "')]",
							testCase)) {
						break;
					}
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isVideoQualityOptionVisible(String videoQualityOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc,'" + videoQualityOption
							+ "')]/android.widget.RelativeLayout/android.widget.TextView",
					testCase)
					&& MobileUtils
							.getFieldValue(testCase, "XPATH",
									"//*[starts-with(@content-desc,'" + videoQualityOption
											+ "')]/android.widget.RelativeLayout/android.widget.TextView")
							.equalsIgnoreCase(videoQualityOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeStaticText[@name='" + videoQualityOption + "_subTitle" + "']", testCase)
					&& MobileUtils
							.getMobElement(testCase, "XPATH",
									"//XCUIElementTypeStaticText[@name='" + videoQualityOption + "_subTitle" + "']")
							.getAttribute("value").equalsIgnoreCase(videoQualityOption)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isVideoQualityStatusSetToExpectedInCameraSettingsScreen(TestCases testCase,
			String videoQualityOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc, 'Video Quality')]/android.widget.LinearLayout/android.widget.TextView",
					testCase)
					&& MobileUtils.getFieldValue(testCase, "XPATH",
							"//*[starts-with(@content-desc, 'Video Quality')]/android.widget.LinearLayout/android.widget.TextView")
							.equalsIgnoreCase(videoQualityOption)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[contains(@name,'_subTitle') and @value='Video Quality']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]",
					testCase)
					&& MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Video Quality']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
							.getAttribute("value").equalsIgnoreCase(videoQualityOption)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isVideoQualityStatusSetToExpectedInVideoQualityScreen(TestCases testCase,
			String videoQualityOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.isMobElementExists("XPATH",
					"//*[starts-with(@content-desc,'" + videoQualityOption
							+ "')]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
					testCase)) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (MobileUtils.isMobElementExists("XPATH", "//XCUIElementTypeCell[@name='" + videoQualityOption + "_cell"
					+ "']/XCUIElementTypeImage[@name='" + videoQualityOption + "_Image" + "']", testCase)) {
				return flag;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean setVideoQualityStatusToExpected(TestCases testCase, String videoQualityOption) {
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			int counter = 0;
			if (MobileUtils.isMobElementExists("XPATH", "//*[starts-with(@content-desc, '" + videoQualityOption
					+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '" + videoQualityOption + "']",
					testCase)) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//*[starts-with(@content-desc, '" + videoQualityOption
								+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '"
								+ videoQualityOption + "']",
						testCase)) && counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//*[starts-with(@content-desc, '" + videoQualityOption
									+ "')]/android.widget.RelativeLayout/android.widget.TextView[@text= '"
									+ videoQualityOption + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH", "//*[starts-with(@content-desc,'" + videoQualityOption
							+ "')]/android.widget.RelativeLayout/android.widget.ImageView[@content-desc='Select']",
							testCase)) {
						break;
					}
					counter++;
				}
			} else {
				flag = false;
			}
		} else {
			int counter = 0;
			if ((MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell[@name= '" + videoQualityOption + "_cell" + "']", testCase))) {
				while ((MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell[@name= '" + videoQualityOption + "_cell" + "']", testCase))
						&& counter < 5) {
					flag = flag & MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell[@name= '" + videoQualityOption + "_cell" + "']");
					System.out.println("########Click No: " + counter);
					CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 2);
					if (MobileUtils.isMobElementExists("XPATH",
							"//XCUIElementTypeCell[@name='" + videoQualityOption + "_cell"
									+ "']/XCUIElementTypeImage[contains(@name, '" + videoQualityOption + "_Image"
									+ "')]",
							testCase)) {
						break;
					}
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

	public boolean isCameraMicrophoneSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraMicrophoneSwitch", 10)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraMicrophoneSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraMicrophoneSwitch")
						.getAttribute("value").equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}

			}
		} else {
			throw new Exception("Could not find Camera Microphone Switch");
		}
	}

	public boolean toggleCameraMicroPhoneSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraMicrophoneSwitch");
	}

	public boolean isTurnOffCameraMicrophonePopupHeaderTitleVisible(TestCases testCase) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TurnOffCameraMicrophonePopupHeaderTitle");
	}

	public boolean isTurnOffCameraMicrophonePopupMsgVisible(TestCases testCase) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "TurnOffCameraMicrophonePopupMsg");
	}

	public boolean isCANCELButtonInTurnOffCameraMicrophonePopupVisible(TestCases testCase) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CANCELButtonInTurnOffCameraMicrophonePopup");
	}

	public boolean clickOnCANCELButtonInTurnOffCameraMicrophonePopup(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CANCELButtonInTurnOffCameraMicrophonePopup");
	}

	public boolean isOKButtonInTurnOffCameraMicrophonePopupVisible(TestCases testCase) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "OKButtonInTurnOffCameraMicrophonePopup");
	}

	public boolean clickOnOKButtonInTurnOffCameraMicrophonePopup(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "OKButtonInTurnOffCameraMicrophonePopup");
	}

	public boolean verifySoundSSensitivitytatusInSettingsScreen(TestCases testCase, String soundStatus) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraSoundSensitivityValueInSettingsScreen")
				&& MobileUtils.getFieldValue(objectDefinition, testCase, "CameraSoundSensitivityValueInSettingsScreen")
						.equalsIgnoreCase(soundStatus)) {
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean verifyCameraTurnOffMicrophonePopUp(TestCases testCase) {
		return (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraMicrophoneTurnOffPopUp"));
	}

	public boolean clickOnMicrophonePopupOkbutton(TestCases testCase) {
		return (MobileUtils.isMobElementExists(objectDefinition, testCase, "CameraMicrophoneTurnOffOKButton"));
	}

	public boolean isManangeAlertsEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "ManageAlertsLabel").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "ManageAlertsLabel")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isMotionDetectionEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionLabel").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "MotionDetectionLabel")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isPeopleDetectionEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "PeopleDetectionLable").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "PeopleDetectionLable")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isNightVisionEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "NightVisionLabel").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "NightVisionLabel")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isVideoQualityEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "VideoQualityLabel").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "VideoQualityLabel")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isCameraOnInHomeModeEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CameraOnInHomeMode").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "CameraOnInHomeMode")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isCameraOnInNigtModeEnabled() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.getMobElement(objectDefinition, testCase, "CameraOnInNightMode").isEnabled();
		} else {
			return MobileUtils.getFieldValue(objectDefinition, testCase, "CameraOnInHomeMode")
					.equalsIgnoreCase("enabled");
		}
	}

	public boolean isCameraMicrophoneTextEnabled() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "CameraMicrophoneText").isEnabled();
	}

	public boolean isVideoQualityAutoVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityAuto");
	}

	public boolean isVideoQualityAutoTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityAutoText");
	}

	public boolean ClickOnVideoQualityAutoOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VideoQualityAuto");
	}

	public boolean isVideoQualityLowVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityLow");
	}

	public boolean isVideoQualityLowTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityLowText");
	}

	public boolean ClickOnVideoQualityLowOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VideoQualityLow");
	}

	public boolean isVideoQualityHighVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityHigh");
	}

	public boolean isVideoQualityHighTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityHighText");
	}

	public boolean ClickOnVideoQualityHighOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "VideoQualityHigh");
	}

	public boolean isVideoQualityAutoSelectedVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityAutoSelected");
	}

	public boolean isVideoQualityLowSelectedVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityLowSelected");
	}

	public boolean isVideoQualityHighSelectedVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "VideoQualityHighSelected");
	}

	public boolean isNightVisionAutoVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionAuto");
	}

	public boolean isNightVisionAutoTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionAutoText");
	}

	public boolean ClickOnNightVisionAutoOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NightVisionAuto");
	}

	public boolean isNightVisionONVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionON");
	}

	public boolean isNightVisionONTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionONText");
	}

	public boolean ClickOnNightVisionONOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NightVisionON");
	}

	public boolean isNightVisionOFFVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionOFF");
	}

	public boolean isNightVisionOFFTextVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NightVisionOFFText");
	}

	public boolean ClickOnNightVisionOFFOption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NightVisionOFF");
	}

	public boolean isCameraOnInHomeModeSwitchEnabled(TestCases testCase) throws Exception {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraOnInHomeModeSwitch").getText()
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			return (testCase.getMobileDriver()
					.findElementByXPath("//XCUIElementTypeSwitch[@name='Camera_Settings_2_0_toggle']")
					.getAttribute("value").equals("1"));
		}
	}

	public boolean toggleCameraOnInHomeModeSwitch() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOnInHomeModeSwitch");
		} else {
			testCase.getMobileDriver().findElementByXPath("//XCUIElementTypeSwitch[@name='Camera_Settings_2_0_toggle']")
					.click();
			return true;
		}
	}

	public boolean isCameraOnInNightModeSwitchEnabled(TestCases testCase) throws Exception {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (MobileUtils.getMobElement(objectDefinition, testCase, "CameraOnInNightModeSwitch").getText()
					.equalsIgnoreCase("ON")) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println(testCase.getMobileDriver()
					.findElementByXPath("//XCUIElementTypeSwitch[@name='Camera_Settings_2_1_toggle']")
					.getAttribute("value"));
			return (testCase.getMobileDriver()
					.findElementByXPath("//XCUIElementTypeSwitch[@name='Camera_Settings_2_1_toggle']")
					.getAttribute("value").equalsIgnoreCase(String.valueOf(1)));
		}
	}

	public boolean toggleCameraOnInNightModeSwitch() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CameraOnInNightModeSwitch");
		} else {
			testCase.getMobileDriver().findElementByXPath("//XCUIElementTypeSwitch[@name='Camera_Settings_2_1_toggle']")
					.click();
			return true;
		}
	}

	public boolean clickonbackoption() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackCameraSettings");
	}

	public boolean isEnsureTheCameraisturnedonandtheprivacyringisopenvisible(int timeout) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase,
				"EnsureTheCameraisturnedonandtheprivacyringisopen", timeout);
	}

	public boolean clickonEnsureTheCameraisOKoption() {
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "EnsureTheCameraisOKoption");
		} else {
			testCase.getMobileDriver().findElementById("Ok").click();
			return true;
		}
	}
}
