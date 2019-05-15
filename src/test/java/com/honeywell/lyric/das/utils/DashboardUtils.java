package com.honeywell.lyric.das.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CoachMarks;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SchedulingScreen;
import com.honeywell.screens.CameraScreen;

public class DashboardUtils {

	public static boolean selectDeviceFromDashboard(TestCases testCase, String deviceToBeSelected,
			boolean... closeCoachMarks) throws Exception {
		List<WebElement> dashboardIconText = null;
		Dashboard d = new Dashboard(testCase);
		CoachMarks cm = new CoachMarks(testCase);
		boolean flag = false;
		List<String> availableDevices = new ArrayList<String>();
		if (d.areDevicesVisibleOnDashboard(25)) {
			dashboardIconText = d.getDashboardDeviceNameElements();
		}
		for (WebElement e : dashboardIconText) {
			String displayedText = null;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (e != null) {
					displayedText = e.getText();
				}
			} else {
				try {
					if ((e.getAttribute("visible").equals("true")) && (e.getAttribute("value").trim() != null)
							&& (!e.getAttribute("value").trim().isEmpty()))
						displayedText = e.getAttribute("value");
				} catch (Exception e1) {
				}
			}
			availableDevices.add(displayedText);
			if (displayedText.equals(deviceToBeSelected)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (cm.isCoachMarkVisible(5)) {
			if (closeCoachMarks.length > 0 && !closeCoachMarks[0]) {
				return true;
			} else {
				return LyricUtils.closeCoachMarks(testCase);
			}
		}
		if (flag) {
			Keyword.ReportStep_Pass(testCase, "Successfully selected " + deviceToBeSelected + " device from Dashboard");
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Failed to select " + deviceToBeSelected + " device from Dashboard");
		}
		return flag;
	}

	public static boolean navigateToDashboardFromAnyScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			CameraScreen camScr = new CameraScreen(testCase);
			SchedulingScreen sch = new SchedulingScreen(testCase);
			Dashboard d = new Dashboard(testCase);
			if (d.isGlobalDrawerButtonVisible()) {
				Keyword.ReportStep_Pass(testCase,
						"Navigate To Primary Card : User is already on the Primary Card or Dashboard");
				return flag;
			} else {
				int i = 0;
				while ((!d.isGlobalDrawerButtonVisible()) && i < 10) {
					if (sch.isCloseButtonVisible(3)) {
						flag = flag & sch.clickOnCloseButton();
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					} else if (MobileUtils.isMobElementExists("id", "BACK", testCase)) {
						flag = flag & MobileUtils.clickOnElement(testCase, "id", "BACK");
					} else if (bs.isBackButtonVisible(2)) {
						flag = flag & bs.clickOnBackButton();
					} else if (camScr.isBackButtonVisible(2)) {
						flag = flag & camScr.clickOnBackButton();
					} else if (sch.IsSaveButtonVisible(5)) {
						flag = flag & sch.clickOnSaveButton();
					}
					i++;
				}
				if (d.isGlobalDrawerButtonVisible() || (d.isAddDeviceIconVisible(5))) {
					Keyword.ReportStep_Pass(testCase,
							"Navigate To Primary Card : Successfully navigated to Primary card or Dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Navigate To Primary Card : Failed to navigate to Primary card or Dashboard");
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Navigate To Primary Card : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForSecurityStatusToUpdate method waits until the progress bar closes.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-03-08
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForSecurityStatusToUpdate(TestCases testCase, String securityState, int duration) {
		boolean flag = true;
		System.out.println("%%%%%%%%%%securityState: " + securityState);
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(duration));
			Dashboard d = new Dashboard(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (securityState) {
						case "HOME": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "AWAY": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "NIGHT": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						case "OFF": {
							if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
								if (d.getSecurityStatusLabel().toLowerCase().contains(securityState.toLowerCase())) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}

							} else {
								if (d.getSecurityStatusLabel().contains(securityState)) {
									System.out.println(securityState + " State is displayed");
									return true;
								} else {
									return false;
								}
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + securityState);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Progress bar loading spinner did not disapper after waiting for 1 minute");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForProgressBarToComplete method waits until the progress bar closes.
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @return boolean Returns 'true' if the progress bar disappears. Returns
	 *         'false' if the progress bar is still displayed.
	 */
	public static boolean waitForProgressBarToComplete(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(duration));
			Dashboard d = new Dashboard(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "LOADING SPINNER BAR": {
							if (d.isLoadingSpinnerVisible()) {
								System.out.println("Waiting for Verifying loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "PROGRESS BAR": {
							if (d.isProgressBarVisible()) {
								System.out.println("Waiting for Verifying loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + elementProgressBar);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Progress bar loading spinner diasppeared.");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Progress bar loading spinner did not disapper after waiting for: " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}

		return flag;
	}

	public static boolean waitForOptionOnScreen(TestCases testCase, String elementProgressBar, int duration) {
		boolean flag = true;
		try {
			FluentWait<String> fWait = new FluentWait<String>(" ");
			/*
			 * fWait.pollingEvery(3, TimeUnit.SECONDS); fWait.withTimeout(duration,
			 * TimeUnit.MINUTES);
			 */
			fWait.pollingEvery(Duration.ofSeconds(3));
			fWait.withTimeout(Duration.ofMinutes(duration));
			Dashboard d = new Dashboard(testCase);
			PrimaryCard pc = new PrimaryCard(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "HEATING TEXT TO APPEAR": {
							if (d.isHeatingTextVisible()) {
								return true;
							} else {
								System.out.println("Waiting for Heating Text to appear");
								return false;
							}
						}
						case "HEATING TEXT TO DISAPPEAR": {
							if (d.isHeatingTextVisible()) {
								System.out.println("Waiting for Heating Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "HEATINGORCOOLING TEXT TO DISAPPEAR": {
							if (d.isHeatingCoolingTextVisible()) {
								System.out.println("Waiting for Heating or Cooling Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "HEATING TEXT TO APPEAR IN PRIMARY CARD": {
							if (pc.isHeatingTextVisible()) {
								return true;
							} else {
								System.out.println("Waiting for Heating Text to appear");
								return false;
							}
						}
						case "HEATING TEXT TO DISSAPEAR IN PRIMARY CARD": {
							if (pc.isHeatingTextVisible()) {
								System.out.println("Waiting for Heating Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "COOLING TEXT TO APPEAR": {
							if (d.isCoolingTextVisible()) {
								return true;
							} else {
								System.out.println("Waiting for Cooling Text to appear");
								return false;
							}
						}
						case "COOLING TEXT TO DISAPPEAR": {
							if (d.isCoolingTextVisible()) {
								System.out.println("Waiting for Cooling Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "COOLING TEXT TO APPEAR IN PRIMARY CARD": {
							if (pc.isCoolingTextVisible()) {
								return true;
							} else {
								System.out.println("Waiting for Cooling Text to appear");
								return false;
							}
						}
						case "COOLING TEXT TO DISSAPEAR IN PRIMARY CARD": {
							if (pc.isCoolingTextVisible()) {
								System.out.println("Waiting for Cooling Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "HEATINGORCOOLING TEXT TO DISSAPEAR IN PRIMARY CARD": {
							if (pc.isHeatingCoolingTextVisible()) {
								System.out.println("Waiting for Cooling Text to disappear");
								return false;
							} else {
								return true;
							}
						}
						default: {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Invalid argument passed : " + elementProgressBar);
							return true;
						}
						}
					} catch (Exception e) {
						return false;
					}
				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Wait for:" + elementProgressBar + "completed");
			}
		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Wait for:" + elementProgressBar + "Failed after waiting for: " + duration + " minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean enterCountryNameAndSelectItInConfirmYourCountryScreenAndAcceptNewAgreement(TestCases testCase,
			TestCaseInputs inputs, String countryName) {
		boolean flag = true;
		AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
		flag &= adn.enterCountryNameInCountryTextFieldAndAcceptNewAgreement(inputs, countryName);
		return flag;
	}

	public static boolean enterCountryNameAndSelectItInConfirmYourCountryScreen(TestCases testCase,
			TestCaseInputs inputs, String countryName) {
		boolean flag = true;
		AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
		flag &= adn.enterCountryNameInCountryTextField(inputs, countryName);
		return flag;
	}

	public static boolean verifyDefaultCountryDisplayedInConfirmYourCountryScreen(TestCases testCase,
			TestCaseInputs inputs, String countryName) {
		boolean flag = true;
		AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
		flag &= adn.verifyDefaultCountryDisplayedInConfirmYourCountryScreen(inputs, countryName);
		return flag;
	}

	public static boolean verifyCountryDisplayedInAddNewDeviceScreen(TestCases testCase, String countryName) {
		boolean flag = true;
		System.out.println("##########COUNTRY NAME IS: " + countryName);
		AddNewDeviceScreen adn = new AddNewDeviceScreen(testCase);
		flag &= adn.isShowingDevicesForCountryLabelInAddNewDeviceScreenVisible(countryName);
		return flag;
	}

	public static boolean createANewLocation(TestCases testCase, TestCaseInputs inputs, String locationName) {
		boolean flag = true;
		Dashboard d = new Dashboard(testCase);
		DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
		AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
		if (d.isAddDeviceIconVisible(20)) {
			flag &= d.clickOnAddNewDeviceIcon();
			flag &= dasDIY.isAddNewDeviceScreenVisible(20);
			inputs.setInputValue("SELECTED_COUNTRY", ads.getCountryLabelInAddNewDeviceScreenVisible().substring(20));
			try {
				flag &= dasDIY.selectDeviceToInstall("Smart Home Security");
				if (dasDIY.isNextButtonVisible()) {
					flag &= dasDIY.clickOnNextButton();
					if (dasDIY.isCreateNewLocationLinkVisible()) {
						flag &= dasDIY.clickOnCreateNewLocationLink();
						inputs.setInputValue("LOCATION1_NAME", locationName);
						flag = flag & DIYRegistrationUtils.inputNewLocationName(testCase, inputs, locationName);
						if (dasDIY.isZipCodeTextFieldVisible()) {
							flag = flag & dasDIY.clearEnteredTextInZipCodeTextField();
							flag = flag & dasDIY.enterZipCode("90002");
						} else if (inputs.isRunningOn("Perfecto") && inputs.getInputValue("SELECTED_COUNTRY") != null
								&& !inputs.getInputValue("SELECTED_COUNTRY").isEmpty()
								&& inputs.getInputValue("SELECTED_COUNTRY").equalsIgnoreCase("United States")) {
							Keyword.ReportStep_Pass(testCase,
									"Country Selected is: " + inputs.getInputValue("SELECTED_COUNTRY")
											+ ". Hence Confirm Your Zip Code screen will not be displayed.");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Confirm Your Zip Code screen");
						}
						if (dasDIY.isNameYourBaseStationHeaderTitleVisible()) {
							Keyword.ReportStep_Pass(testCase,
									"Successfully navigated to Name Your Base Station screen");
							DIYRegistrationUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 1);
							if (dasDIY.isCancelButtonVisible()) {
								flag = flag & dasDIY.clickOnCancelButton();
								if (dasDIY.isYesButtonInCancelPopupVisible()) {
									flag = flag & dasDIY.clickOnYesButtonInCancelPopup();
									flag &= DashboardUtils.waitForProgressBarToComplete(testCase, "PROGRESS BAR", 1);
									if (ads.isAddNewDeviceHeaderDisplayed(60)
											&& ads.isSelectADeviceToInstallHeaderInAddNewDeviceScreenVisible()) {
										Keyword.ReportStep_Pass(testCase,
												"Successfully navigated to Add New Device Screen");
										if (ads.isCancelButtonInAddNewDeviceScreenVisible()) {
											Keyword.ReportStep_Pass(testCase,
													"Cancel button is displayed in the Add New Device Screen");
											flag &= ads.clickOnCancelButtonOfAddDeviceScreen();
											if (d.isGlobalDrawerButtonVisible(20) && (d.isAddDeviceIconVisible(10)
													|| d.isAddDeviceIconBelowExistingDASDeviceVisible(10))) {
												Keyword.ReportStep_Pass(testCase,
														"Successfully navigated to Dashboared screen");
											} else {
												flag = false;
												Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
														"Failed to navigate to Dashboared screen");
											}
										} else {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Cancel button is not displayed in the Add New Device Screen");
										}
									} else {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Failed to navigate to Add New Device Screen");
									}
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"YES button is not displayed in Cancel popup.");
								}
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Cancel button not displayed.");
							}
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Failed to navigate to Name Your Base Station screen");
						}
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Create New Location Link is not displayed in Choose Location Screen");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Next Button is not displayed in What To Expect Screen");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Add Device Button is not displayed in the Dashboard Screen");
		}
		return flag;
	}
}