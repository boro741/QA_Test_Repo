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

	public boolean isMotionDetectionLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionLabel", timeOut);
	}

	public boolean clickOnMotionDetectionLabel() {
		boolean flag = true;
		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "MotionDetectionLabel");
		return flag;
	}

	public boolean isBackButtonVisibleInMotionDetectionSettingsScreen() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
	}

	public boolean clickOnBackButtonInMotionDetectionSettingsScreen() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
	}

	public boolean isSoundDetectionLabelVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionLabel", timeOut);
	}

	public boolean clickOnSoundDetectionLabel() {
		boolean flag = true;
		flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "SoundDetectionLabel");
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

	public boolean isLoadingSnapshotSpinnerVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadingSnapshotSpinner");
	}

	public boolean isRetryInLoadingSpanshotVisible() {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "RetryInLoadingSnapshotSpinner")) {
			WebElement retryLoadingSpinnerVisibilityStatus = MobileUtils.getMobElement(objectDefinition, testCase,
					"RetryInLoadingSnapshotSpinner");
			System.out.println("########" + retryLoadingSpinnerVisibilityStatus.getAttribute("visible"));
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
	
	public boolean isCameraMotionDetectionSwitchEnabled(TestCases testCase) throws Exception {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionSwitch", 20)) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionSwitch").getText()
						.equalsIgnoreCase("ON")) {
					return true;
				} else {
					return false;
				}
			} else {
				if (MobileUtils.getMobElement(objectDefinition, testCase, "MotionDetectionSwitch").getAttribute("value")
						.equalsIgnoreCase("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new Exception("Could not find Camera Motion Detection Switch");
		}
	}

	public boolean toggleCameraMotionDetectionSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "MotionDetectionSwitch");
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

	public boolean isMotionSensitivityEnabled() {
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
				System.out.println("########value: " + MobileUtils
						.getMobElement(objectDefinition, testCase, "MotionSensitivityVisible").getAttribute("value"));
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
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "MotionDetectionStatus")) {
			System.out
					.println("#####" + MobileUtils.getFieldValue(objectDefinition, testCase, "MotionDetectionStatus"));
			if (MobileUtils.getFieldValue(objectDefinition, testCase, "MotionDetectionStatus").equalsIgnoreCase("ON")) {
				return flag;
			} else {
				flag = false;
			}
		} else {
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//*[contains(@name,'_subTitle') and @value='Motion Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]",
						testCase)) {
					System.out.println("#####" + MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Motion Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
							.getAttribute("value"));
					if (MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Motion Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
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

	public boolean isAreasOutsideZonesWarningPopupHeaderTitleDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AreasOutsideZonesWarningPopupHeaderTitle");
	}

	public boolean isAreasOutsideZonesWarningPopupMsgDisplayed() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "AreasOutsideZonesWarningPopupMsg");
	}

	public boolean clickOnNOButtonInAreasOutsideZonesWarningPopup() {
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "NOButton")) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "NOButton");
		} else {
			return false;
		}
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

	public boolean navigateBackAndForthInMotionDetectionScreen(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "BackButtonInMotionDetectionScreen")) {
			System.out.println("#########Back button in Motion Detection screen is displayed");
			flag = flag & MobileUtils.clickOnElement(objectDefinition, testCase, "BackButtonInMotionDetectionScreen");
			System.out.println("#########Clicked on Back button in Motion Detection screen");
			if (isAreasOutsideZonesWarningPopupHeaderTitleDisplayed()
					&& isAreasOutsideZonesWarningPopupMsgDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Area Outside Zone Warning Popup is displayed");
				clickOnNOButtonInAreasOutsideZonesWarningPopup();
			}
			if (isCameraSettingsHeaderTitleVisible(20) && isMotionDetectionLabelVisible(10)) {
				flag = flag & clickOnMotionDetectionLabel();
				System.out.println("#########Motion Detection screen is displayed");

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
				MobileUtils.clickOnElement(testCase, "XPATH",
						"//XCUIElementTypeButton[@name = '" + zoneToBeSelected + "']");
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
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
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

	public boolean setSoundSensitivityStatusToExpected(TestCases testCase, String motionSensitivityStatus) {
		boolean flag = true;
		Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
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
						System.out.println("%%%%%Scoll down again");
						action.press(10, (int) (dimension.getHeight() * .9))
								.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						action.press(10, (int) (dimension.getHeight() * .9))
								.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					}
					counter++;
				}
			} else {
				flag = false;
			}
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
			throw new Exception("Could not find Camera Motion Detection Switch");
		}
	}

	public boolean toggleCameraSoundDetectionSwitch(TestCases testCase) {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "SoundDetectionSwitch");
	}

	public boolean isCameraSoundDetectionStatusYES(TestCases testCase) {
		boolean flag = true;
		if (MobileUtils.isMobElementExists(objectDefinition, testCase, "SoundDetectionStatus")) {
			System.out.println("#####" + MobileUtils.getFieldValue(objectDefinition, testCase, "SoundDetectionStatus"));
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
					System.out.println("#####" + MobileUtils.getMobElement(testCase, "XPATH",
							"//*[contains(@name,'_subTitle') and @value='Sound Detection']/following-sibling::XCUIElementTypeStaticText[contains(@name,'_value')]")
							.getAttribute("value"));
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
				System.out.println("########value: " + MobileUtils
						.getMobElement(objectDefinition, testCase, "SoundSensitivityVisible").getAttribute("value"));
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
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
				action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
						.release().perform();
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
						System.out.println("%%%%%Scoll down again");
						action.press(10, (int) (dimension.getHeight() * .9))
								.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
						action.press(10, (int) (dimension.getHeight() * .9))
								.moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
					}
					counter++;
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}
}
