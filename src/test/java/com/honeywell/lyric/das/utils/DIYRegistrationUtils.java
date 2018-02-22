package com.honeywell.lyric.das.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;

public class DIYRegistrationUtils {

	/**
	 * <h1>Wait for device list progress bar to complete</h1>
	 * <p>
	 * The waitForFetchingDeviceListProgressBarToComplete method waits until the
	 * progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForFetchingDeviceListProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			AddNewDeviceScreen addNewDevice = new AddNewDeviceScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (addNewDevice.isFetchingDevicesListProgressBarVisible()) {
							System.out.println("Waiting for Fetching Devices List loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Waiting for Fetching Devices List loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Fetching Devices List loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Wait for progress bar to close</h1>
	 * <p>
	 * The waitForLookingForBaseStationProgressBarToComplete method waits until the
	 * progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForLookingForBaseStationProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isLookingForBaseStationProgressBarVisible()) {
							System.out.println("Waiting for Looking for Base station loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Looking for Base station loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for base station loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Select a base station</h1>
	 * <p>
	 * The selectABaseStation method selects a base station from the list of
	 * stations displayed.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @param value
	 *            Value of the attribute used to locate the element
	 * @return boolean Returns 'true' if the element is found. Returns 'false' if
	 *         the element is not found.
	 */
	public static boolean selectABaseStation(TestCases testCase, TestCaseInputs inputs, String macID) throws Exception {
		boolean flag = true;
		String locatorType, locatorValue;
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String displayedText = dasDIY.getToolBarTitleInRegisterBaseStationScreen();
			if (displayedText.equals("Register Base Station")) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "xpath";
			locatorValue = "//android.widget.TextView[@text='" + macID + "']";
		} else {
			if (dasDIY.isSingleBaseStationDisplayed()) {
				Keyword.ReportStep_Pass(testCase, "Single DAS device detected, hence skipping this step");
				return flag;
			}
			locatorType = "name";
			locatorValue = macID;
		}

		if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
			flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
		} else {
			int i = 0;
			while (!MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				String locType;
				String locVal;
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					locType = "id";
					locVal = "diy_wld_list";
				} else {
					locType = "xpath";
					locVal = "//XCUIElementTypeCollectionView";
				}
				LyricUtils.scrollUpAList(testCase, locType, locVal);
				i++;
				if (i > 5) {
					break;
				}
			}

			if (MobileUtils.isMobElementExists(locatorType, locatorValue, testCase, 2)) {
				flag = flag & MobileUtils.clickOnElement(testCase, locatorType, locatorValue);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "MAC ID : " + macID + " not present");
			}
		}
		return flag;
	}

	/**
	 * <h1>Wait for QR code scanning failure popup to display</h1>
	 * <p>
	 * The waitForQRCodeScanningFailurePopupToDisplay method waits until the QR code
	 * scanning failure popup displays.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the QR code scanning failure popup appears.
	 *         Returns 'false' if QR code scanning failure popup does not display.
	 */
	public static boolean waitForQRCodeScanningFailurePopupToDisplay(TestCases testCase, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(duration, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
							System.out.println("Waiting for QR Code Scanning Failure popup to display");
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
				Keyword.ReportStep_Pass(testCase, "QR Code Scanning Failure popup displayed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"QR Code Scanning Failure popup did not appear after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Wait for progress bar to close</h1>
	 * <p>
	 * The waitForLookingForNetworkConnectionProgressBarToComplete method waits
	 * until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForLookingForNetworkConnectionProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isLookingForNetworkConnectionProgressBarVisible()) {
							System.out.println("Waiting for Looking for Wi-Fi list loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Looking for Wi-Fi list loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Looking for Wi-Fi list loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Wait for progress bar to close</h1>
	 * <p>
	 * The waitForConnectingSmartHomeSecurityProgressBarToComplete method waits
	 * until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-21
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForConnectingSmartHomeSecurityProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(2, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isConnectingSmartHomeSecurityLoadingSpinnerVisible()) {
							System.out
									.println("Waiting for Connecting Smart Home Security loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Connecting Smart Home Security loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Connecting Smart Home Security loading spinner did not disapper after waiting for 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Scan QR code</h1>
	 * <p>
	 * The scanQRCode method is to scan QR code
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-22
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the QR code is scanned. Returns 'false' if
	 *         the QR code scan is unsuccessful.
	 */
	public static boolean scanQRCode(TestCases testCase) {
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		boolean flag = true;
		if (testCase.isTestSuccessful()) {
			JFrame frame = new JFrame();
			try {
				String screenShotPath = LyricUtils.takeScreenShot(
						System.getProperty("user.dir") + File.separator + "QRCodes", testCase.getMobileDriver());
				screenShotPath = System.getProperty("user.dir") + File.separator + "QRCodes" + File.separator
						+ screenShotPath;

				BufferedImage picture = ImageIO.read(new File(screenShotPath));
				ImageIcon imageIcon = new ImageIcon(
						new ImageIcon(picture).getImage().getScaledInstance(500, 700, Image.SCALE_DEFAULT));
				JLabel label = new JLabel(imageIcon);
				frame.add(label);
				frame.pack();
				frame.setAlwaysOnTop(true);
				frame.setVisible(true);
				testCase.startTimer("QRCodeScanningTimer");

				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
				fWait.pollingEvery(5, TimeUnit.SECONDS);
				fWait.withTimeout(1, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						try {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (dasDIY.isConnectToNetworkHeaderDescVisible()) {
									String displayedText = dasDIY.getToolBarTitleInConnectToNetworkScreen();
									if (displayedText.equals("Connect to Network")) {
										return true;
									} else {
										return false;
									}
								} else if (dasDIY.isLookingForNetworkConnectionProgressBarVisible()) {
									return true;
								} else {
									return false;
								}
							} else {
								if (dasDIY.isConnectToNetworkHeaderDescVisible()) {
									return true;
								} else {
									return false;
								}
							}

						} catch (Exception e) {
							return false;
						}
					}
				});
				if (isEventReceived) {
					Keyword.ReportStep_Pass(testCase, "Successfully scanned QR code");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "QR code not scanned");
				}
				testCase.stopTimer("QRCodeScanningTimer", "", "", "");
				frame.setVisible(false);
				frame.dispose();
			} catch (TimeoutException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to scan QR code");
				frame.setVisible(false);
				frame.dispose();
			} catch (IOException e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				frame.setVisible(false);
				frame.dispose();
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				frame.setVisible(false);
				frame.dispose();
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Scenario steps failed already, hence skipping the verification");
		}
		return flag;
	}
}
