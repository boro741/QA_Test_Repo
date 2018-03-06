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
import com.honeywell.screens.DASDIYRegistrationScreens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class DIYRegistrationUtils {

	/**
	 * <h1>Turn off device bluetooth</h1>
	 * <p>
	 * The turnOffDeviceBluetooth method turns off device bluetooth.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the bluetooth is turned off. Returns
	 *         'false' if the bluetooth is not turned off.
	 */
	@SuppressWarnings("unchecked")
	public static boolean turnOffDeviceBluetooth(TestCases testCase) throws Exception{
		boolean flag = true;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			//((AndroidDriver<MobileElement>) testCase.getMobileDriver()).setConnection();
			//startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).openNotifications();
			((AndroidDriver<MobileElement>) testCase.getMobileDriver()).getSettings();
			MobileUtils.swipe(testCase, 530, 210, 0, 200);
			MobileElement el3 = ((AndroidDriver<MobileElement>) testCase.getMobileDriver()).findElementByAccessibilityId("title");
			int width = el3.getLocation().getX();
			int height = el3.getLocation().getY();
			System.out.println("####width: " + width);
			System.out.println("#######height: " + height);
			MobileElement el1 = ((AndroidDriver<MobileElement>) testCase.getMobileDriver()).findElementByAccessibilityId("Bluetooth on.");
			el1.click();
			MobileElement el2 = ((AndroidDriver<MobileElement>) testCase.getMobileDriver()).findElementByXPath("//android.view.View[@content-desc=\"Bluetooth off.\"]");
			el2.click();
			
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
			fWait.withTimeout(3, TimeUnit.MINUTES);
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
					"Connecting Smart Home Security loading spinner did not disapper after waiting for 3 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Scan Invalid QR code</h1>
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
	 * @return boolean Returns 'true' if the QR code scanning failure popup gets
	 *         displayed. Returns 'false' if the QR code scanning failure popup does
	 *         not display.
	 */
	public static boolean scanInvalidQRCode(TestCases testCase) {

		JFrame frame = new JFrame();
		boolean flag = true;
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		try {
			String screenShotPath = System.getProperty("user.dir") + File.separator + "QRCodes" + File.separator
					+ "InvalidQRCode.png";

			BufferedImage picture = ImageIO.read(new File(screenShotPath));
			ImageIcon imageIcon = new ImageIcon(
					new ImageIcon(picture).getImage().getScaledInstance(500, 700, Image.SCALE_DEFAULT));
			JLabel label = new JLabel(imageIcon);
			frame.add(label);
			frame.pack();
			frame.setAlwaysOnTop(true);
			frame.setVisible(true);

			FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
			fWait.pollingEvery(5, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					try {
						if (dasDIY.isQRCodeScanningFailurePopupVisible()) {
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
				Keyword.ReportStep_Pass(testCase, "Scanning Failure QR code popup is displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Scanning Failure QR code popup is not displayed");
			}
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
	
	/**
	 * <h1>Wait until sensor set up button displays</h1>
	 * <p>
	 * The waitForSetUpButtonToDisplay method waits until the
	 * set up button displays.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the sensor set up button displays. Returns
	 *         'false' if the sensor set up button does not display.
	 */
	public static boolean waitForSensorSetUpButtonToDisplay(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (!dasDIY.isSensorSetUpButtonVisible()) {
							System.out.println("Waiting for Sensor Set Up button to be displayed");
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
				Keyword.ReportStep_Pass(testCase, "Sensor Set Up button is displayed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Sensor Set Up button does not display after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	/**
	 * <h1>Wait until In Progress loading spinner disappears</h1>
	 * <p>
	 * The waitUntilInProgressLoadingSpinnerDisappears method waits until the
	 * loading spinner disappears.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the in progress loading spinner disappears. Returns
	 *         'false' if the in progress loading spinner is still displayed.
	 */
	public static boolean waitUntilInProgressLoadingSpinnerDisappears(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isInProgressLoadingSpinnerTextVisible()) {
							System.out.println("Waiting for In progress loading spinner text to disappear");
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
				Keyword.ReportStep_Pass(testCase, "In progress loading spinner disappeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"In Progress loading spinner does not disappear even after 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	/**
	 * <h1>Wait until Saving Sensor loading spinner disappears</h1>
	 * <p>
	 * The waitUntilInProgressLoadingSpinnerDisappears method waits until the
	 * loading spinner disappears.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the Saving Sensor loading spinner disappears. Returns
	 *         'false' if the Saving Sensor loading spinner is still displayed.
	 */
	public static boolean waitUntilSavingSensorLoadingSpinnerDisappears(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isSavingSensorLoadingSpinnerTextVisible()) {
							System.out.println("Waiting for Saving Sensor loading spinner text to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Saving Sensor loading spinner disappeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Saving Sensor loading spinner does not disappear even after 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	/**
	 * <h1>Wait until Verifying loading spinner disappears</h1>
	 * <p>
	 * The waitUntilVerifyingLoadingSpinnerDisappears method waits until the
	 * loading spinner disappears.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the Verifying loading spinner disappears. Returns
	 *         'false' if the Verifying loading spinner is still displayed.
	 */
	public static boolean waitUntilVerifyingLoadingSpinnerDisappears(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isVerifyingLoadingSpinnerTextVisible()) {
							System.out.println("Waiting for Verifying loading spinner text to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Verifyig loading spinner disappeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Verifying loading spinner does not disappear even after 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	/**
	 * <h1>Wait until Finishing up loading spinner disappears</h1>
	 * <p>
	 * The waitUntilFinishingUpLoadingSpinnerDisappears method waits until the
	 * loading spinner disappears.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-5
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the Finishing up loading spinner disappears. Returns
	 *         'false' if the Finishing up loading spinner is still displayed.
	 */
	public static boolean waitUntilFinishingUpLoadingSpinnerDisappears(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isFinishingUpLoadingSpinnerTextVisible()) {
							System.out.println("Waiting for Finishing up loading spinner text to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Finishing up loading spinner disappeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Finishing up loading spinner does not disappear even after 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
	
	/**
	 * <h1>Wait for Saving GeoFencing progress bar to close</h1>
	 * <p>
	 * The waitForSavingGeoFencingProgressBarToComplete method waits until the
	 * progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-2
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForSavingGeoFencingProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isEnablingGeoFencingLoadingProgressBarVisible()) {
							System.out.println("Waiting for Saving Geofencing loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Saving Geofencing loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Saving Geofencing loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Minimize and Maximize the app</h1>
	 * <p>
	 * The minimizeAndMaximizeTheApp method is to minimize and maximize the app
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-01
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app is minimized and maximized. Returns
	 *         'false' if unsuccessful.
	 */
	public static boolean minimizeAndMaximizeTheApp(TestCases testCase) {
		boolean flag = true;
		MobileUtils.minimizeApp(testCase, 5);
		return flag;
	}

	/**
	 * <h1>Wait for Deleting progress bar to close</h1>
	 * <p>
	 * The waitForDeletingLocationProgressBarToComplete method waits until the
	 * progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-2
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForDeletingLocationProgressBarToComplete(TestCases testCase) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			fWait.pollingEvery(3, TimeUnit.SECONDS);
			fWait.withTimeout(1, TimeUnit.MINUTES);
			DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						if (dasDIY.isDeletingLocationLoadingProgressBarVisible()) {
							System.out.println("Waiting for Deleting Location loading spinner to disappear");
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
				Keyword.ReportStep_Pass(testCase, "Deleting Location loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Deleting Location loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}
}
