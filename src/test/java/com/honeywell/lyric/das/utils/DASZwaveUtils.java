package com.honeywell.lyric.das.utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.ZwavePrimardCardScreen;
import com.honeywell.screens.ZwaveScreen;

public class DASZwaveUtils {

	//Activate device screen actions
	public static boolean timeOutForNoActivatedDevice(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "RetryOption",1)||MobileUtils.isMobElementExists(fieldObjects, testCase, "NoDeviceToExcludePopupHeader",1)) {
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
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EnteringInclusionModeOverlay", 5)) {
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
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DeviceNotFoundPopup", 3)) {
			Keyword.ReportStep_Pass(testCase, "Device Not Found Pop Up Title is correctly displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"No Z-Wave device Pop Up not displayed or mispelled");
		}
		return flag;
	}

	// Primary card actions
	public static boolean waitForSwitchingToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwavePrimaryScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "SwitchingToOverlay", 5)) {
							System.out.println("Waiting for Switching to complete");
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
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ToggleStatusProgress", 5)) {
							System.out.println("Waiting for Switching to complete");
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
	
	
	public static boolean clickNavigateUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		flag = MobileUtils.clickOnElement(fieldObjects, testCase, "NavigateBack");
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
	public static boolean navigateToZwaveUtilitiesFromDashboard(TestCases testCase){
		boolean flag = true;
		navigateToZwaveDevicesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag= flag & zwaveScreen.clickZwaveUtilitiesMenu();  
		return flag;
	}
	public static boolean navigateToGeneralInclusionFromDashboard(TestCases testCase){
		boolean flag = true;
		navigateToZwaveUtilitiesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag= flag & zwaveScreen.clickGeneralDeviceInclusionMenu();
		return flag;
	}

	public static boolean navigateToGeneralExclusionFromDashboard(TestCases testCase){
		boolean flag = true;
		navigateToZwaveUtilitiesFromDashboard(testCase);
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		flag= flag & zwaveScreen.clickGeneralDeviceExclusionMenu();
		return flag;
	}

	public static boolean waitForNamingScreen(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
		MobileUtils.isMobElementExists(fieldObjects, testCase, "NameTheDeviceTitle");
		MobileUtils.isMobElementExists(fieldObjects, testCase, "NameTheDeviceTitle");
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
//FixAllProgress
	
	public static boolean waitForActionToComplete(TestCases testCase,HashMap<String, MobileObject> fieldObjects,String locator) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, locator, 5)) {
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
				Keyword.ReportStep_Pass(testCase, "Screen diasppeared");
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
	
	
	//Exclude screen elements

	public static boolean waitForEnteringExclusionToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "EnteringExclusionModeOverlay", 5)) {
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
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupTitle")) {
			//MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage");
			Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up Title is correctly displayed");
			/*String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "This device will be permanently removed. Are you sure?";
			}
			 */
			//if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage")){
				Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Device Excluded Pop Up message displayed incorrectly");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device Excluded Pop Up not displayed");
		}
		return flag;
	} 
	public static boolean clickCancelOnExcludeDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickNoDeviceToExcludePopupCancel();
	}

	public static boolean clickRetryOnExcludeDeviceNotFoundPopUp(TestCases testCase) {
		ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
		return zwaveScreen.clickNoDeviceToExcludePopupRetry();
	}

	public static boolean verifyDeviceDeletedPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");

		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupTitle")) {
			//MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage");
			Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up Title is correctly displayed");
			/*String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "This device will be permanently removed. Are you sure?";
			}
			 */
			//if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
			if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludedSuccessPopupMessage")){
				Keyword.ReportStep_Pass(testCase, "Device Excluded Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Device Excluded Pop Up message displayed incorrectly");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device Excluded Pop Up not displayed");
		}
		return flag;
	}
	public static void increaseDimmerIntensity(TestCases testCase,
			int intensityToBeSet) {
		ZwavePrimardCardScreen zps = new ZwavePrimardCardScreen(testCase);
		WebElement slider = zps.getDimmerSeekBar();
		int xAxisStartPoint = slider.getLocation().getX() + 90;
		int yAxis = slider.getLocation().getY()
				+ (slider.getSize().getHeight() / 2);
		double sliderWidth = slider.getSize().getWidth() - 150;
		double pixelsPerPercent = sliderWidth / 100;
		double pixelsToBeMoved = (intensityToBeSet * pixelsPerPercent);
		Keyword.ReportStep_Pass(testCase, "Setting the dimmer intensity to: "
				+ intensityToBeSet);
		MobileUtils.clickOnCoordinate(testCase,
				(int) (xAxisStartPoint + pixelsToBeMoved), yAxis);
		if (zps.isDimmerSeekBarVisible()) {
			Keyword.ReportStep_Pass(testCase,
					"Successfully changed dimmer intensity");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to change dimmer intensity");
		}

	}
	public static boolean navigateToZwaveDevicesFromDashboard(TestCases testCase) {
		Dashboard ds = new Dashboard(testCase);
		if (ds.clickOnGlobalDrawerButton()) {
			SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
			if (sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ZWAVEDEVICES)) {
					Keyword.ReportStep_Pass(testCase,
							"Navigated to  Zwave DEVICES");
					return true;
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not click on Zwave DEVICES menu from Global drawer");
			}
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Could not click on Global drawer menu from dashboard");
		}
		return false;
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
	

}
