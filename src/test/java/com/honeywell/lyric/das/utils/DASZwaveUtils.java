package com.honeywell.lyric.das.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.ZwavePrimardCardScreen;
import com.honeywell.screens.ZwaveScreen;

public class DASZwaveUtils {

	// Activate device screen actions
	public static boolean timeOutForNoActivatedDevice(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		AddNewDeviceScreen addDeviceScreen = new AddNewDeviceScreen(testCase);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(2,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(2));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (addDeviceScreen.isAddNewDeviceHeaderDisplayed(1)
								|| zScreen.isRetryOnDeviceNotFoundPopUpDisplayed(1)
								|| zScreen.isNoDeviceToExcludePopupHeaderDisplayed(1)) {
							return true;
						} else {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Switching to completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Switching to complete did not complete after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForEnteringInclusionToComplete(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(1,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(1));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (zScreen.isEnteringInclusionModeOverlayDisplayed()) {
							System.out.println("Waiting for Entering inclusion to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Entering inclusion diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Entering inclusion did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifyDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		if (zScreen.isDeviceNotFoundPopupDisplayed()) {
			Keyword.ReportStep_Pass(testCase, "Device Not Found Pop Up Title is correctly displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"No ZWAVE device Pop Up not displayed or mispelled");
		}
		return flag;
	}

	// Primary card actions
	public static boolean waitForSwitchingToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			ZwavePrimardCardScreen zPrimaryScreen = new ZwavePrimardCardScreen(testCase);
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(1,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(1));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (zPrimaryScreen.isSwitchingToOverlayDisplayed()) {
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Switching to completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Switching to complete did not complete after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean waitForToggleActionToComplete(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);

		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(1,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(1));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (zScreen.isToggleStatusProgressDisplayed()) {
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Switching to completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Switching to complete did not complete after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean clickCancelFromNavigation(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickCancelFromNavigation();
	}

	public static boolean clickConfirmOnCancelFromNavigation(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickConfirmOnCancelFromNavigation();
	}

	public static boolean clickNavigateUp(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		zScreen.clickNavigateBack();
		return flag;
	}

	public static boolean verifyZWaveUtilitiesScreen(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.isZwaveUtitiesScreenDisplayed();
	}

	public static boolean verifyZWaveDevicesScreen(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.isZwaveDevicesScreenDisplayed();
	}

	public static boolean navigateToZwaveUtilitiesFromDashboard(TestCases testCase) {
		boolean flag = true;
		navigateToZwaveDevicesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag = flag & zwaveScreen.clickZwaveUtilitiesMenu();
		return flag;
	}

	public static boolean navigateToGeneralInclusionFromDashboard(TestCases testCase) {
		boolean flag = true;
		navigateToZwaveUtilitiesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag = flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
		return flag;
	}

	public static boolean navigateToGeneralExclusionFromDashboard(TestCases testCase) {
		boolean flag = true;
		navigateToZwaveUtilitiesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag = flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
		return flag;
	}

	public static boolean navigateToDashboardFromZwaveIndividualDeviceSettings(TestCases testCase,
			TestCaseInputs inputs) throws Exception {
		boolean flag = true;

		flag = flag & navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(testCase, inputs);
		flag = flag & navigateToSecuritySettingsFromZwaveDevices(testCase, inputs);
		flag = flag & navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
		flag = flag & navigateToDashboardFromPrimaryCard(testCase, inputs);
		return flag;
	}

	public static boolean navigateToDashboardFromPrimaryCard(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToPrimaryCardFromSecuritySettings(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToSecuritySettingsFromZwaveDevices(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToZwaveDevicesFromZwaveUtilitiesSettings(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToZwaveDevicesFromZwaveIndividualDeviceSettings(TestCases testCase,
			TestCaseInputs inputs) throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToPrimaryCardFromZwaveIndividualDeviceSettings(TestCases testCase,
			TestCaseInputs inputs) throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean navigateToDashboardFromZwaveUtilitiesSettings(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & navigateToZwaveDevicesFromZwaveUtilitiesSettings(testCase, inputs);
		flag = flag & navigateToSecuritySettingsFromZwaveDevices(testCase, inputs);
		flag = flag & navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
		flag = flag & navigateToDashboardFromPrimaryCard(testCase, inputs);
		return flag;
	}

	public static boolean navigateToDashboardFromZwaveDevicesSettings(TestCases testCase, TestCaseInputs inputs)
			throws Exception {
		boolean flag = true;
		flag = flag & navigateToSecuritySettingsFromZwaveDevices(testCase, inputs);
		flag = flag & navigateToPrimaryCardFromSecuritySettings(testCase, inputs);
		flag = flag & navigateToDashboardFromPrimaryCard(testCase, inputs);
		return flag;
	}

	public static boolean navigateToZwaveDevicesFromSettings(TestCases testCase) throws Exception {
		boolean flag = true;
		flag = flag & DASZwaveUtils.clickNavigateUp(testCase);
		return flag;
	}

	public static boolean waitForNamingScreen(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		zScreen.isNameTheDeviceTitleDisplayed();
		zScreen.isNameTheDeviceTitleDisplayed();
		return flag;
	}

	// Remove popup
	public static boolean clickCancelOnRemoveDevicePopUp(TestCases testCase, TestCaseInputs inputs) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickCancelOnRemoveDevicePopUp();
	}

	public static boolean clickOkOnRemoveDevicePopUp(TestCases testCase, TestCaseInputs inputs) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickOkOnRemoveDevicePopUp();
	}

	// Add new device inclusion - device not found popup
	public static boolean clickTryExcludeOnDeviceNotFoundPopUp(TestCases testCase, TestCaseInputs inputs) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickTryExcludeOnDeviceNotFoundPopUp();
	}

	public static boolean clickRetryOnDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickRetryOnDeviceNotFoundPopUp();
	}

	public static boolean clickCancelOnDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickCancelOnDeviceNotFoundPopUp();
	}

	// General inclusion - device not found popup

	public static boolean clickOKOnDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickOKOnDeviceNotFoundPopUp();
	}

	// General exclusion - device excluded popup
	public static boolean clickOKOnDeviceExcludedPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickOKOnDeviceExcludedPopUp();
	}

	public static boolean clickCancelFurtherExclusionOnExcludedPopup(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickCancelFurtherExclusionOnExcludedPopup();
	}

	public static boolean clickConfirmFurtherExclusionOnExcludedPopup(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickConfirmFurtherExclusionOnExcludedPopup();
	}
	// FixAllProgress

	public static boolean waitForActionToComplete(TestCases testCase, String actionName) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(3,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(3));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (actionName.toUpperCase()) {
						case "SAVING NAME": {
							ZwaveScreen zScreen = new ZwaveScreen(testCase);
							if (zScreen.isFixAllProgressDisplayed()) {
								return false;
							} else {
								return true;
							}
						}
						case "FIX ALL": {
							ZwaveScreen zScreen = new ZwaveScreen(testCase);
							if (zScreen.isFixAllProgressDisplayed()) {
								return false;
							} else {
								return true;
							}
						}
						case "REPLACE PROGRESS": {
							ZwaveScreen zScreen = new ZwaveScreen(testCase);
							if (zScreen.isReplaceProgressDisplayed()) {
								return false;
							} else {
								return true;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FALSE_POSITIVE,
									actionName + " input not handled");
						}
						}
					} catch (Exception e) {
						return false;
					}
					return false;
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Screen diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					actionName + " did not disapper after waiting for 3 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	// Exclude screen elements

	public static boolean waitForEnteringExclusionToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			ZwaveScreen zScreen = new ZwaveScreen(testCase);
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(1,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(1));
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (zScreen.isEnteringExclusionModeOverlayDisplayed()) {
							System.out.println("Waiting for Entering exclusion to disappear");
							return false;
						} else {
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Entering inclusion diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Entering inclusion did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean verifyDeviceExcludedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		if (zScreen.isExcludedSuccessPopupTitleDisplayed()) {
			Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up Title is correctly displayed");
			if (zScreen.isExcludedSuccessPopupMessageDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Device Excluded Pop Up message displayed incorrectly");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device Excluded Pop Up not displayed");
		}
		return flag;
	}

	public static boolean clickCancelOnExcludeDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickNoDeviceToExcludePopupCancel();
	}

	public static boolean activateZwaveSwitch(TestCases testCase, TestCaseInputs inputs) throws Exception {
		ZWaveRelayUtils.powerOnZwaveSwitch(inputs);
		ZWaveRelayUtils.enrollZwaveSwitch1();
		TimeUnit.SECONDS.sleep(2);
		ZWaveRelayUtils.pressButtonOnSwitch1();
		return true;
	}

	public static boolean activateZwaveDimmer(TestCases testCase, TestCaseInputs inputs) throws Exception {
		ZWaveRelayUtils.powerOnZwaveDimmer(inputs);
		ZWaveRelayUtils.enrollZwaveDimmer1();
		TimeUnit.SECONDS.sleep(2);
		ZWaveRelayUtils.pressButtonOnDimmer1();
		return true;
	}

	public static boolean clickRetryOnExcludeDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickNoDeviceToExcludePopupRetry();
	}

	public static boolean verifyDeviceDeletedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			if (zScreen.isExcludedSuccessPopupTitleDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up Title is correctly displayed");
				if (zScreen.isExcludedSuccessPopupMessageDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up message correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device Excluded Pop Up message displayed incorrectly");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device Excluded Pop Up not displayed");
			}
		} else {
			if (zScreen.isDeletedSuccessPopupTitleDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Device removed Pop Up Title is correctly displayed");
				if (zScreen.isDeletedSuccessPopupMessageDisplayed()) {
					Keyword.ReportStep_Pass(testCase, "Device removed Pop Up message correctly displayed");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device Excluded Pop Up message displayed incorrectly");
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device Excluded Pop Up not displayed");
			}
		}
		return flag;
	}

	public static void increaseDimmerIntensity(TestCases testCase, int intensityToBeSet) {
		ZwavePrimardCardScreen zps = new ZwavePrimardCardScreen(testCase);
		WebElement slider = zps.getDimmerSeekBar();
		int xAxisStartPoint = slider.getLocation().getX() + 90;
		int yAxis = slider.getLocation().getY() + (slider.getSize().getHeight() / 2);
		double sliderWidth = slider.getSize().getWidth() - 150;
		double pixelsPerPercent = sliderWidth / 100;
		double pixelsToBeMoved = (intensityToBeSet * pixelsPerPercent);
		Keyword.ReportStep_Pass(testCase, "Setting the dimmer intensity to: " + intensityToBeSet);
		MobileUtils.clickOnCoordinate(testCase, (int) (xAxisStartPoint + pixelsToBeMoved), yAxis);
		if (zps.isDimmerSeekBarVisible()) {
			Keyword.ReportStep_Pass(testCase, "Successfully changed dimmer intensity");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to change dimmer intensity");
		}

	}

	public static boolean navigateToZwaveDevicesFromDashboard(TestCases testCase) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ZWAVEDEVICES);
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Navigated to Zwave DEVICES");
				return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not navigate to Zwave DEVICES menu from dashboard");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean navigateToSwitchSettingsFromDashboard(TestCases testCase) throws Exception {
		boolean flag = true;
		if (navigateToSwitchPrimaryCardFromDashboard(testCase)) {
			PrimaryCard pc = new PrimaryCard(testCase);
			flag = flag & pc.clickOnCogIcon();
			return flag;
		}
		return false;
	}

	public static boolean navigateToSwitchSettingsFromDashboardViaZwaveDevices(TestCases testCase) throws Exception {
		boolean flag = true;
		flag = flag & navigateToZwaveDevicesFromDashboard(testCase);
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		flag = flag & zScreen.ClickSwitchSettingFromZwaveDevices();
		return flag;
	}

	public static boolean navigateToDimmerSettingsFromDashboardViaZwaveDevices(TestCases testCase) throws Exception {
		boolean flag = true;
		flag = flag & navigateToZwaveDevicesFromDashboard(testCase);
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		flag = flag & zScreen.ClickDimmerSettingFromZwaveDevices();
		return flag;
	}

	public static boolean navigateToSwitchPrimaryCardFromDashboard(TestCases testCase) throws Exception {
		boolean flag = true;
		String switchNames[] = { "Switch1", "Switch 001", "Switch2" };
		Dashboard d = new Dashboard(testCase);
		for (String switchName : switchNames) {
			if (d.isDevicePresentOnDashboard(switchName)) {
				flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, switchName);
				return true;
			}
		}
		return false;
	}

	public static boolean navigateToDimmerPrimaryCardFromDashboard(TestCases testCase) throws Exception {
		boolean flag = true;
		String switchNames[] = { "Dimmer1", "Switch 001", "Dimmer2" };
		for (String switchName : switchNames) {
			if (LyricUtils.verifyDeviceDisplayedOnDashboard(testCase, switchName)) {
				flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, switchName);
				return true;
			}
		}
		return false;
	}

	public static boolean navigateToDimmerSettingsFromDashboard(TestCases testCase) throws Exception {
		boolean flag = true;
		if (navigateToDimmerPrimaryCardFromDashboard(testCase)) {
			PrimaryCard pc = new PrimaryCard(testCase);
			flag = flag & pc.clickOnCogIcon();
			return flag;
		}
		return false;
	}

	public static boolean navigateToDimmerPrimaryCardFromDimmerSettings(TestCases testCase) {
		boolean flag = true;
		ZwaveScreen zScreen = new ZwaveScreen(testCase);
		flag = flag & zScreen.clickNavigateBack();
		return flag;
	}

	public static boolean navigateToControllerDetailsFromDashboard(TestCases testCase) {
		navigateToZwaveUtilitiesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickModelandFirmwareDetailsMenu();
	}

	public static boolean isControllerDetailsDisplayed(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.isZwaveUtitiesScreenDisplayed();

	}

	public static boolean navigateToAddDeviceScreenFromDashboardThroughIcon(TestCases testCase) {
		Dashboard ds = new Dashboard(testCase);
		if (ds.clickOnAddNewDeviceIcon()) {
			AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
			ads.clickOnZwaveFromAddNewDevice();
			return true;
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Could not click on Global drawer menu from dashboard");
		}
		return false;
	}

}
