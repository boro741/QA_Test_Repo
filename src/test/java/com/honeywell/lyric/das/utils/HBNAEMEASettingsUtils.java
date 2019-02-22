package com.honeywell.lyric.das.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.GlobalVariables;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.ThermostatSettingsScreen;

public class HBNAEMEASettingsUtils {

	public static final int d = 176;
	public static final char degree = (char) d;

	/**
	 * <h1>Wait for until progress bar to complete</h1>
	 * <p>
	 * The waitForProgressBarToComplete method waits until the progress bar closes.
	 * </p>
	 *
	 * Instance of the TestCases class used to create the testCase. testCase
	 * instance.
	 * 
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
			CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
			CameraSettingsScreen css = new CameraSettingsScreen(testCase);
			Boolean isEventReceived = fWait.until(new Function<String, Boolean>() {
				public Boolean apply(String a) {
					try {
						switch (elementProgressBar) {
						case "LOADING SPINNER BAR": {
							if (cs.isLoadingSpinnerVisible()) {
								System.out.println("Waiting for Verifying loading spinner text to disappear");
								return false;
							} else {
								return true;
							}
						}
						case "RETRY IN LOADING SNAPSHOT SPINNER": {
							if (!css.isRetryInLoadingSpanshotVisible()) {
								System.out.println("Waiting for Verifying loading snapshot spinner to disappear");
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

	/**
	 * <h1>Minimize and Maximize the app</h1>
	 * <p>
	 * The minimizeAndMaximizeTheApp method is to minimize and maximize the app
	 * </p>
	 *
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
	 * <h1>Select temperature range value</h1>
	 * <p>
	 * The selectAboveTemperatureRangeValue method is to select the temperature
	 * range value
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app sets temperature range value below
	 *         current thermostat temperature. Returns 'false' if unsuccessful.
	 */
	public static boolean selectBelowTemperatureRangeValue(TestCases testCase, TestCaseInputs inputs,
			String alertBelowTempRangeOption) throws Exception {
		boolean flag = true;
		WebElement element = null;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		int thermostatCurrentTempValue = Integer.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE"));
		Random r = new Random();

		if (statInfo.getThermostatUnits().equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
			int belowValue = 0, belowValueApp = 0, aboveValueApp = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
					belowValueApp = Integer.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
							.split(String.valueOf(degree))[0]);
					if (belowValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to " + thermostatCurrentTempValue);
						ts.clickOnBelowAboveTempAlertRangeOption(alertBelowTempRangeOption);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Below Temperature value: " + thermostatCurrentTempValue);
							element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentTempValue));
							if (element != null) {
								element.click();
								Keyword.ReportStep_Pass(testCase,
										"Below Temperature value is set to: " + thermostatCurrentTempValue);
								belowValueApp = Integer
										.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
												.split(String.valueOf(degree))[0]);
								if ((belowValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
												belowValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
																.split(String.valueOf(degree))[0]);
												if ((belowValueApp == thermostatCurrentTempValue)) {
													do {
														belowValue = r.nextInt((98 - 39) + 1) + 39;
													} while (belowValue < thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertBelowTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value in the app: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Displayed Below Temperature value: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value: "
																			+ aboveValueApp);
														}
														ts.clickOnBelowAboveTempAlertRangeOption(
																alertBelowTempRangeOption);
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Below Temperature value: " + belowValue);
															element = testCase.getMobileDriver()
																	.scrollTo(String.valueOf(belowValue));
															if (element != null) {
																element.click();
																inputs.setInputValue("INDOORTEMP_BELOW_VALUE",
																		belowValue);
																Keyword.ReportStep_Pass(testCase,
																		"Below Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_BELOW_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange]Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and Displayed Above Temperature value is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to a new value greater than " + thermostatCurrentTempValue);
						try {
							do {
								belowValue = r.nextInt((98 - 39) + 1) + 39;
							} while (belowValue <= thermostatCurrentTempValue);
							ts.clickOnBelowAboveTempAlertRangeOption(alertBelowTempRangeOption);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Below Temperature value: " + belowValue);
								element = testCase.getMobileDriver().scrollTo(String.valueOf(belowValue));
								if (element != null) {
									element.click();
									inputs.setInputValue("INDOORTEMP_BELOW_VALUE", belowValue);
									Keyword.ReportStep_Pass(testCase, "Below Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase, "[AfterChange]Displayed Below Temperature value is: "
										+ belowValueApp + " and Above value is shown: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			} else {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
					belowValueApp = Integer
							.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption).split(",")[0]
									.replaceAll("\u00B0", ""));
					if (belowValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to " + thermostatCurrentTempValue);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Below Temperature value: " + thermostatCurrentTempValue);
							flag = ts.setTempValueForTemperatureRange(alertBelowTempRangeOption,
									thermostatCurrentTempValue);
							if (flag) {
								// element.click();
								Keyword.ReportStep_Pass(testCase,
										"Below Humidity value is set to: " + thermostatCurrentTempValue);
								belowValueApp = Integer.parseInt(
										ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption).split(",")[0]
												.replaceAll("\u00B0", ""));
								if ((belowValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
										}
										if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
											belowValueApp = Integer.parseInt(
													ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
															.split(",")[0].replaceAll("\u00B0", ""));
											if ((belowValueApp == thermostatCurrentTempValue)
													&& (ts.isBackButtonVisible(1))) {
												if ((belowValueApp == thermostatCurrentTempValue)) {
													do {
														belowValue = r.nextInt((98 - 39) + 1) + 39;
													} while (belowValue < thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertBelowTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);

														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value in the app: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Displayed Below Temperature value: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value: "
																			+ aboveValueApp);
														}
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Below Temperature value: " + belowValue);
															flag = ts.setTempValueForTemperatureRange(
																	alertBelowTempRangeOption, belowValue);
															if (flag) {
																// element.click();
																inputs.setInputValue("INDOORTEMP_BELOW_VALUE",
																		belowValue);
																Keyword.ReportStep_Pass(testCase,
																		"Below Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_BELOW_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer
																	.parseInt(ts.getBelowTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															aboveValueApp = Integer
																	.parseInt(ts.getAboveTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange]Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and Displayed Above Temperature value is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to a new value greater than " + thermostatCurrentTempValue);
						try {
							do {
								belowValue = r.nextInt((98 - 39) + 1) + 39;
							} while (belowValue <= thermostatCurrentTempValue);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Below Temperature value: " + belowValue);
								flag = ts.setTempValueForTemperatureRange(alertBelowTempRangeOption, belowValue);
								if (flag) {
									// element.click();
									inputs.setInputValue("INDOORTEMP_BELOW_VALUE", belowValue);
									Keyword.ReportStep_Pass(testCase, "Below Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase, "[AfterChange]Displayed Below Temperature value is: "
										+ belowValueApp + " and Above value is shown: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			}
		} else {
			double belowValue = 0, belowValueApp = 0, aboveValueApp = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
					belowValueApp = Integer.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
							.split(String.valueOf(degree))[0]);
					if (belowValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to " + thermostatCurrentTempValue);
						ts.clickOnBelowAboveTempAlertRangeOption(alertBelowTempRangeOption);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Below Temperature value: " + thermostatCurrentTempValue);
							element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentTempValue));
							if (element != null) {
								element.click();
								Keyword.ReportStep_Pass(testCase,
										"Below Temperature value is set to: " + thermostatCurrentTempValue);
								belowValueApp = Integer
										.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
												.split(String.valueOf(degree))[0]);
								if ((belowValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
												belowValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
																.split(String.valueOf(degree))[0]);
												if ((belowValueApp == thermostatCurrentTempValue)) {
													do {
														belowValue = (r.nextInt((365 - 40) + 1) + 40) / 10.0;
														belowValue = Math.round(belowValue * 2) / 2.0;
													} while (belowValue < thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertBelowTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in app is: "
																			+ belowValueApp
																			+ " Displayed Below Temperature value in app is "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange] Below Temperature value is: "
																			+ belowValueApp
																			+ " and Above Temperature value is: "
																			+ aboveValueApp);
														}
														ts.clickOnBelowAboveTempAlertRangeOption(
																alertBelowTempRangeOption);
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Below Temperature value: " + belowValue);
															element = testCase.getMobileDriver()
																	.scrollTo(String.valueOf(belowValue));
															if (element != null) {
																element.click();
																inputs.setInputValue("INDOORTEMP_BELOW_VALUE",
																		belowValue);
																Keyword.ReportStep_Pass(testCase,
																		"Below Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_BELOW_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange] Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and Above Temperature value is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to a new value greater than " + thermostatCurrentTempValue);
						try {
							do {
								belowValue = (r.nextInt((365 - 40) + 1) + 40) / 10.0;
								belowValue = Math.round(belowValue * 2) / 2.0;
							} while (belowValue <= thermostatCurrentTempValue);
							ts.clickOnBelowAboveTempAlertRangeOption(alertBelowTempRangeOption);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Below Temperature value: " + belowValue);
								element = testCase.getMobileDriver().scrollTo(String.valueOf(belowValue));
								if (element != null) {
									element.click();
									inputs.setInputValue("INDOORTEMP_BELOW_VALUE", belowValue);
									Keyword.ReportStep_Pass(testCase, "Below Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange]Displayed Below Temperature value is: " + belowValueApp
												+ " and Displayed Above Temperature value is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			} else {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
					belowValueApp = Integer
							.parseInt(ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption).split(",")[0]
									.replaceAll("\u00B0", ""));
					if (belowValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to " + thermostatCurrentTempValue);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Below Temperature value: " + thermostatCurrentTempValue);
							flag = ts.setTempValueForTemperatureRange(alertBelowTempRangeOption,
									thermostatCurrentTempValue);
							if (flag) {
								// element.click();
								Keyword.ReportStep_Pass(testCase,
										"Below Temperature value is set to: " + thermostatCurrentTempValue);
								belowValueApp = Integer.parseInt(
										ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption).split(",")[0]
												.replaceAll("\u00B0", ""));
								if ((belowValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertBelowTempRangeOption)) {
												belowValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertBelowTempRangeOption)
																.split(",")[0].replaceAll("\u00B0", ""));
												if ((belowValueApp == thermostatCurrentTempValue)) {
													do {
														belowValue = (r.nextInt((365 - 40) + 1) + 40) / 10.0;
														belowValue = Math.round(belowValue * 2) / 2.0;
													} while (belowValue < thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertBelowTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in app is: "
																			+ belowValueApp
																			+ " Displayed Below Temperature value in app is "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange] Below Temperature value is: "
																			+ belowValueApp
																			+ " and Above Temperature value is: "
																			+ aboveValueApp);
														}
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Below Temperature value: " + belowValue);
															flag = ts.setTempValueForTemperatureRange(
																	alertBelowTempRangeOption, belowValue);
															if (flag) {
																// element.click();
																inputs.setInputValue("INDOORTEMP_BELOW_VALUE",
																		belowValue);
																Keyword.ReportStep_Pass(testCase,
																		"Below Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_BELOW_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer
																	.parseInt(ts.getBelowTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															aboveValueApp = Integer
																	.parseInt(ts.getAboveTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange] Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and Above Temperature value is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Below Temperature value: " + belowValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Below Temperature value: "
										+ belowValueApp + " to a new value greater than " + thermostatCurrentTempValue);
						try {
							do {
								belowValue = (r.nextInt((365 - 40) + 1) + 40) / 10.0;
								belowValue = Math.round(belowValue * 2) / 2.0;
							} while (belowValue <= thermostatCurrentTempValue);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Below Temperature value: " + belowValue);
								flag = ts.setTempValueForTemperatureRange(alertBelowTempRangeOption, belowValue);
								if (flag) {
									// element.click();
									inputs.setInputValue("INDOORTEMP_BELOW_VALUE", belowValue);
									Keyword.ReportStep_Pass(testCase, "Below Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(",")[0].replaceAll("\u00B0", ""));
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(",")[0].replaceAll("\u00B0", ""));
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange]Displayed Below Temperature value is: " + belowValueApp
												+ " and Displayed Above Temperature value is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * <h1>Select temperature range value</h1>
	 * <p>
	 * The selectAboveTemperatureRangeValue method is to select the temperature
	 * range value
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app sets temperature range value above
	 *         current thermostat temperature. Returns 'false' if unsuccessful.
	 */
	public static boolean selectAboveTemperatureRangeValue(TestCases testCase, TestCaseInputs inputs,
			String alertAboveTempRangeOption) throws Exception {
		boolean flag = true;
		WebElement element = null;
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		int thermostatCurrentTempValue = Integer.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE"));
		Random r = new Random();

		if (statInfo.getThermostatUnits().equalsIgnoreCase(GlobalVariables.FAHRENHEIT)) {
			int aboveValue = 0, belowValueApp = 0, aboveValueApp = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
					aboveValueApp = Integer.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
							.split(String.valueOf(degree))[0]);
					if (aboveValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to " + thermostatCurrentTempValue);
						ts.clickOnBelowAboveTempAlertRangeOption(alertAboveTempRangeOption);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Above Temperature value: " + thermostatCurrentTempValue);
							element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentTempValue));
							if (element != null) {
								element.click();
								Keyword.ReportStep_Pass(testCase,
										"Above Temperature value is set to: " + thermostatCurrentTempValue);
								aboveValueApp = Integer
										.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
												.split(String.valueOf(degree))[0]);
								if ((aboveValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
												aboveValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
																.split(String.valueOf(degree))[0]);
												if ((aboveValueApp == thermostatCurrentTempValue)) {
													do {
														aboveValue = r.nextInt((99 - 40) + 1) + 40;
													} while (aboveValue > thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertAboveTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app is: "
																			+ belowValueApp
																			+ " and Displayed Above Temperature value in the app is: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Below Temperature value displayed is: "
																			+ belowValueApp
																			+ " and Above Temperature value displayed is: "
																			+ aboveValueApp);
														}
														ts.clickOnBelowAboveTempAlertRangeOption(
																alertAboveTempRangeOption);
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Above Temperature value: " + aboveValue);
															element = testCase.getMobileDriver()
																	.scrollTo(String.valueOf(aboveValue));
															if (element != null) {
																element.click();
																inputs.setInputValue("INDOORTEMP_ABOVE_VALUE",
																		aboveValue);
																Keyword.ReportStep_Pass(testCase,
																		"Above Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_ABOVE_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange]Below Temperature value displayed is: "
																			+ belowValueApp
																			+ " and Above Temperature value displayed is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to a new value lower than " + thermostatCurrentTempValue);
						try {
							do {
								aboveValue = r.nextInt((99 - 40) + 1) + 40;
							} while (aboveValue > thermostatCurrentTempValue);
							ts.clickOnBelowAboveTempAlertRangeOption(alertAboveTempRangeOption);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Above Temperature value: " + aboveValue);
								element = testCase.getMobileDriver().scrollTo(String.valueOf(aboveValue));
								if (element != null) {
									element.click();
									inputs.setInputValue("INDOORTEMP_ABOVE_VALUE", aboveValue);
									Keyword.ReportStep_Pass(testCase, "Above Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange]Below Temperature value displayed is: " + belowValueApp
												+ " and Above Temperature value displayed is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			} else {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
					aboveValueApp = Integer
							.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption).split(",")[0]
									.replaceAll("\u00B0", ""));
					if (aboveValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to " + thermostatCurrentTempValue);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Above Temperature value: " + thermostatCurrentTempValue);
							flag = ts.setTempValueForTemperatureRange(alertAboveTempRangeOption,
									thermostatCurrentTempValue);
							if (flag) {
								// element.click();
								Keyword.ReportStep_Pass(testCase,
										"Above Temperature value is set to: " + thermostatCurrentTempValue);
								aboveValueApp = Integer.parseInt(
										ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption).split(",")[0]
												.replaceAll("\u00B0", ""));
								if ((aboveValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
												aboveValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
																.split(",")[0].replaceAll("\u00B0", ""));
												if ((aboveValueApp == thermostatCurrentTempValue)) {
													do {
														aboveValue = r.nextInt((99 - 40) + 1) + 40;
													} while (aboveValue > thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertAboveTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app is: "
																			+ belowValueApp
																			+ " and Displayed Above Temperature value in the app is: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Below Temperature value displayed is: "
																			+ belowValueApp
																			+ " and Above Temperature value displayed is: "
																			+ aboveValueApp);
														}
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Above Temperature value: " + aboveValue);
															flag = ts.setTempValueForTemperatureRange(
																	alertAboveTempRangeOption, aboveValue);
															if (flag) {
																// element.click();
																inputs.setInputValue("INDOORTEMP_ABOVE_VALUE",
																		aboveValue);
																Keyword.ReportStep_Pass(testCase,
																		"Above Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_ABOVE_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange]Below Temperature value displayed is: "
																			+ belowValueApp
																			+ " and Above Temperature value displayed is: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to a new value lower than " + thermostatCurrentTempValue);
						try {
							do {
								aboveValue = r.nextInt((99 - 40) + 1) + 40;
							} while (aboveValue > thermostatCurrentTempValue);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Above Temperature value: " + aboveValue);
								flag = ts.setTempValueForTemperatureRange(alertAboveTempRangeOption, aboveValue);
								if (flag) {
									// element.click();
									inputs.setInputValue("INDOORTEMP_ABOVE_VALUE", aboveValue);
									Keyword.ReportStep_Pass(testCase, "Above Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange] Below Temperature value displayed is: " + belowValueApp
												+ " and Above Temperature value displayed is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			}
		} else {
			double aboveValue = 0, belowValueApp = 0, aboveValueApp = 0;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
					aboveValueApp = Integer.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
							.split(String.valueOf(degree))[0]);
					if (aboveValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to " + thermostatCurrentTempValue);
						ts.clickOnBelowAboveTempAlertRangeOption(alertAboveTempRangeOption);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Above Temperature value: " + thermostatCurrentTempValue);
							element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentTempValue));
							if (element != null) {
								element.click();
								aboveValueApp = Integer
										.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
												.split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase,
										"Above Temperature value is set to: " + thermostatCurrentTempValue);
								if ((aboveValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
												aboveValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
																.split(String.valueOf(degree))[0]);
												if ((aboveValueApp == thermostatCurrentTempValue)) {
													do {
														aboveValue = (r.nextInt((370 - 45) + 1) + 45) / 10.0;
														aboveValue = Math.round(aboveValue * 2) / 2.0;
													} while (aboveValue > thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertAboveTempRangeOption)) {
														belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																.split(String.valueOf(degree))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																.split(String.valueOf(degree))[0]);
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app is: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value in the app is: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value is: "
																			+ aboveValueApp);
														}
														ts.clickOnBelowAboveTempAlertRangeOption(
																alertAboveTempRangeOption);
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Above Temperature value: " + aboveValue);
															element = testCase.getMobileDriver()
																	.scrollTo(String.valueOf(aboveValue));
															if (element != null) {
																element.click();
																inputs.setInputValue("INDOORTEMP_ABOVE_VALUE",
																		aboveValue);
																Keyword.ReportStep_Pass(testCase,
																		"Above Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_ABOVE_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer.parseInt(ts.getBelowTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															aboveValueApp = Integer.parseInt(ts.getAboveTempRangeValue()
																	.split(String.valueOf(degree))[0]);
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange] Below value is shown: "
																			+ belowValueApp
																			+ " and Above value is shown: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to a new value lower than " + thermostatCurrentTempValue);
						try {
							do {
								aboveValue = (r.nextInt((370 - 45) + 1) + 45) / 10.0;
								aboveValue = Math.round(aboveValue * 2) / 2.0;
							} while (aboveValue > thermostatCurrentTempValue);
							ts.clickOnBelowAboveTempAlertRangeOption(alertAboveTempRangeOption);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Above Temperature value: " + aboveValue);
								element = testCase.getMobileDriver().scrollTo(String.valueOf(aboveValue));
								if (element != null) {
									element.click();
									inputs.setInputValue("INDOORTEMP_ABOVE_VALUE", aboveValue);
									Keyword.ReportStep_Pass(testCase, "Above Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(String.valueOf(degree))[0]);
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(String.valueOf(degree))[0]);
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange]Displayed Below Temperature value is: " + belowValueApp
												+ " and displayed Above Temperature value is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			} else {
				if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
					aboveValueApp = Integer
							.parseInt(ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption).split(",")[0]
									.replaceAll("\u00B0", ""));
					if (aboveValueApp != thermostatCurrentTempValue) {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is not equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to " + thermostatCurrentTempValue);
						try {
							Keyword.ReportStep_Pass(testCase,
									"Scroll to Above Temperature value: " + thermostatCurrentTempValue);
							flag = ts.setTempValueForTemperatureRange(alertAboveTempRangeOption,
									thermostatCurrentTempValue);
							if (flag) {
								// element.click();
								aboveValueApp = Integer.parseInt(
										ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption).split(",")[0]
												.replaceAll("\u00B0", ""));
								Keyword.ReportStep_Pass(testCase,
										"Above Temperature value is set to: " + thermostatCurrentTempValue);
								if ((aboveValueApp == thermostatCurrentTempValue) && (ts.isBackButtonVisible(1))) {
									ts.clickOnBackButton();
									waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									if (ts.isManageAlertsLabelVisible(10)) {
										ts.clickOnManageAlertsLabel();
										if (ts.isThermostatTempAlertRangeVisible()) {
											ts.clickOnThermostatTempAlertRange();
											if (ts.isBelowAboveTempAlertRangeOptionVisible(alertAboveTempRangeOption)) {
												aboveValueApp = Integer.parseInt(
														ts.getBelowAboveTempAlertRangeValue(alertAboveTempRangeOption)
																.split(",")[0].replaceAll("\u00B0", ""));
												if ((aboveValueApp == thermostatCurrentTempValue)) {
													do {
														aboveValue = (r.nextInt((370 - 45) + 1) + 45) / 10.0;
														aboveValue = Math.round(aboveValue * 2) / 2.0;
													} while (aboveValue > thermostatCurrentTempValue);
													if (ts.isBelowAboveTempAlertRangeOptionVisible(
															alertAboveTempRangeOption)) {
														belowValueApp = Integer
																.parseInt(ts.getBelowTempRangeValue().split(",")[0]
																		.replaceAll("\u00B0", ""));
														aboveValueApp = Integer
																.parseInt(ts.getAboveTempRangeValue().split(",")[0]
																		.replaceAll("\u00B0", ""));
														if (belowValueApp >= aboveValueApp) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Displayed Below Temperature value in the app is: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value in the app is: "
																			+ aboveValueApp);
														} else {
															Keyword.ReportStep_Pass(testCase,
																	"[BeforeChange]Displayed Below Temperature value is: "
																			+ belowValueApp
																			+ " and displayed Above Temperature value is: "
																			+ aboveValueApp);
														}
														try {
															Keyword.ReportStep_Pass(testCase,
																	"Scroll to Above Temperature value: " + aboveValue);
															flag = ts.setTempValueForTemperatureRange(
																	alertAboveTempRangeOption, aboveValue);
															if (flag) {
																// element.click();
																inputs.setInputValue("INDOORTEMP_ABOVE_VALUE",
																		aboveValue);
																Keyword.ReportStep_Pass(testCase,
																		"Above Temperature value is set to: "
																				+ inputs.getInputValue(
																						"INDOORTEMP_ABOVE_VALUE"));
															}
														} catch (Exception e) {
															flag = false;
															Keyword.ReportStep_Fail(testCase,
																	FailType.FUNCTIONAL_FAILURE,
																	"Error message: " + e.getMessage());
														}
														if (ts.isIndoorTempRangeValueVisible()) {
															belowValueApp = Integer
																	.parseInt(ts.getBelowTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															aboveValueApp = Integer
																	.parseInt(ts.getAboveTempRangeValue().split(",")[0]
																			.replaceAll("\u00B0", ""));
															Keyword.ReportStep_Pass(testCase,
																	"[AfterChange] Below value is shown: "
																			+ belowValueApp
																			+ " and Above value is shown: "
																			+ aboveValueApp);
														}
														if (ts.isBackButtonVisible(1)) {
															ts.clickOnBackButton();
															waitForProgressBarToComplete(testCase,
																	"LOADING SPINNER BAR", 3);
														}

													}
												}
											}
										}
									}
								}
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					} else {
						Keyword.ReportStep_Pass(testCase,
								"Above Temperature value: " + aboveValueApp
										+ " is equal to current thermostat temperature value: "
										+ thermostatCurrentTempValue + ". Updating Above Temperature value: "
										+ aboveValueApp + " to a new value lower than " + thermostatCurrentTempValue);
						try {
							do {
								aboveValue = (r.nextInt((370 - 45) + 1) + 45) / 10.0;
								aboveValue = Math.round(aboveValue * 2) / 2.0;
							} while (aboveValue > thermostatCurrentTempValue);
							try {
								Keyword.ReportStep_Pass(testCase, "Scroll to Above Temperature value: " + aboveValue);
								flag = ts.setTempValueForTemperatureRange(alertAboveTempRangeOption, aboveValue);
								if (flag) {
									// element.click();
									inputs.setInputValue("INDOORTEMP_ABOVE_VALUE", aboveValue);
									Keyword.ReportStep_Pass(testCase, "Above Temperature value is set to: "
											+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE"));
								}
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Error message: " + e.getMessage());
							}
							if (ts.isIndoorTempRangeValueVisible()) {
								belowValueApp = Integer
										.parseInt(ts.getBelowTempRangeValue().split(",")[0].replaceAll("\u00B0", ""));
								aboveValueApp = Integer
										.parseInt(ts.getAboveTempRangeValue().split(",")[0].replaceAll("\u00B0", ""));
								Keyword.ReportStep_Pass(testCase,
										"[AfterChange]Displayed Below Temperature value is: " + belowValueApp
												+ " and displayed Above Temperature value is: " + aboveValueApp);
							}
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
					}
				}
			}
		}
		return flag;
	}

	public static boolean verifyIndoorTempRangeAlertInActivityHistoryScreen(TestCases testCase, TestCaseInputs inputs,
			String indoorTempAlertRangeOption) throws Exception {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		List<WebElement> listAlertTitles = new ArrayList<>();
		List<WebElement> listAlertMsgs = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (ts.isActivityHistoryAlertTitleVisible() && ts.isActivityHistoryAlertMsgVisible()) {
				listAlertTitles = ts.getActivityHistoryAlertTitlesList();
				listAlertMsgs = ts.getActivityHistoryAlertMsgsList();
				for (int i = 0; i < listAlertTitles.size(); i++) {
					for (int j = 0; j < listAlertMsgs.size(); j++) {
						if (indoorTempAlertRangeOption.contains("Below") && ts.isActivityHistoryAlertTimeVisible()) {
							if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
								String expectedBelowTempAlert = "Alert: The temperature in your home is lower than "
										+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE") + ".0"
										+ String.valueOf(degree);
								if (listAlertTitles.get(i).getText().contains(expectedBelowTempAlert)
										&& listAlertMsgs.get(j).getText().contains(expectedBelowTempAlert)) {
									Keyword.ReportStep_Pass(testCase, "Below Temperature Alert Message is displayed: "
											+ listAlertMsgs.get(j).getText());
									break;
								}
							} else {
								flag = false;
							}
						} else if (indoorTempAlertRangeOption.contains("Above")
								&& ts.isActivityHistoryAlertTimeVisible()) {
							if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")) {
								String expectedAboveTempAlert = "Alert: The temperature in your home is higher than "
										+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE") + ".0"
										+ String.valueOf(degree);
								if (listAlertTitles.get(i).getText().contains(expectedAboveTempAlert)
										&& listAlertMsgs.get(j).getText().contains(expectedAboveTempAlert)) {
									Keyword.ReportStep_Pass(testCase, "Above Temperature Alert Message is displayed: "
											+ listAlertMsgs.get(j).getText());
									break;
								}
							} else {
								flag = false;
							}
						}
					}
				}
			}
		} else {
			if (ts.isActivityHistoryAlertTitleVisible() && ts.isActivityHistoryAlertMsgVisible()) {
				listAlertTitles = ts.getActivityHistoryAlertTitlesList();
				listAlertMsgs = ts.getActivityHistoryAlertMsgsList();
				for (int i = 0; i < listAlertTitles.size(); i++) {
					for (int j = 0; j < listAlertMsgs.size(); j++) {
						if (indoorTempAlertRangeOption.contains("Below") && ts.isActivityHistoryAlertTimeVisible()) {
							if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
								String expectedBelowTempAlert = "Alert: The temperature in your home is lower than "
										+ inputs.getInputValue("INDOORTEMP_BELOW_VALUE") + ".0"
										+ String.valueOf(degree);
								if (listAlertTitles.get(i).getAttribute("value").contains(expectedBelowTempAlert)
										&& listAlertMsgs.get(j).getAttribute("value")
												.contains(expectedBelowTempAlert)) {
									Keyword.ReportStep_Pass(testCase, "Below Temperature Alert Message is displayed: "
											+ listAlertMsgs.get(j).getAttribute("value"));
									break;
								}
							} else {
								flag = false;
							}
						} else if (indoorTempAlertRangeOption.contains("Above")
								&& ts.isActivityHistoryAlertTimeVisible()) {
							if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
									|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")) {
								String expectedAboveTempAlert = "Alert: The temperature in your home is higher than "
										+ inputs.getInputValue("INDOORTEMP_ABOVE_VALUE") + ".0"
										+ String.valueOf(degree);
								if (listAlertTitles.get(i).getAttribute("value").contains(expectedAboveTempAlert)
										&& listAlertMsgs.get(j).getAttribute("value")
												.contains(expectedAboveTempAlert)) {
									Keyword.ReportStep_Pass(testCase, "Above Temperature Alert Message is displayed: "
											+ listAlertMsgs.get(j).getAttribute("value"));
									break;
								}
							} else {
								flag = false;
							}
						}
					}
				}
			}
		}
		return flag;

	}

	/**
	 * <h1>Select Humidity range value</h1>
	 * <p>
	 * The selectBelowHumidityRangeValue method is to select the humidity range
	 * value
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app sets humidity range value below
	 *         current thermostat humidity. Returns 'false' if unsuccessful.
	 */
	public static boolean selectBelowHumidityRangeValue(TestCases testCase, TestCaseInputs inputs,
			String alertBelowHumidityRangeOption) throws Exception {
		boolean flag = true;
		WebElement element = null;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		int thermostatCurrentHumidityValue = Integer
				.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDITY_VALUE"));
		// thermostatCurrentHumidityValue = Math.round((thermostatCurrentHumidityValue +
		// 9) / 10 * 10);
		System.out.println("###########thermostatCurrentHumidityValue1: " + thermostatCurrentHumidityValue);
		thermostatCurrentHumidityValue = Math.round((thermostatCurrentHumidityValue - 5) / 10 * 10);
		System.out.println("###########thermostatCurrentHumidityValue2: " + thermostatCurrentHumidityValue);
		Random r = new Random();

		int belowValue = 0, belowValueApp = 0, aboveValueApp = 0;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertBelowHumidityRangeOption)) {
				belowValueApp = Integer
						.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption).split("%")[0]);
				if (belowValueApp != thermostatCurrentHumidityValue) {
					Keyword.ReportStep_Pass(testCase,
							"Below Humidity value: " + belowValueApp
									+ " is not equal to current thermostat Humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Below Humidity value: "
									+ belowValueApp + " to " + thermostatCurrentHumidityValue);
					ts.clickOnBelowAboveHumidityAlertRangeOption(alertBelowHumidityRangeOption);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Below Humidity value: " + thermostatCurrentHumidityValue);
						element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentHumidityValue));
						if (element != null) {
							element.click();
							Keyword.ReportStep_Pass(testCase,
									"Below Humidity value is set to: " + thermostatCurrentHumidityValue);
							belowValueApp = Integer.parseInt(ts
									.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption).split("%")[0]);
							if ((belowValueApp == thermostatCurrentHumidityValue) && (ts.isBackButtonVisible(1))) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
								if (ts.isManageAlertsLabelVisible(10)) {
									ts.clickOnManageAlertsLabel();
									if (ts.isThermostatHumidityAlertRangeVisible()) {
										ts.clickOnThermostatHumidityAlertRange();
										if (ts.isBelowAboveHumidityAlertRangeOptionVisible(
												alertBelowHumidityRangeOption)) {
											belowValueApp = Integer.parseInt(ts
													.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption)
													.split("%")[0]);
											if ((belowValueApp == thermostatCurrentHumidityValue)) {
												do {
													belowValue = r.nextInt((90 - 5) + 1) + 5;
												} while (belowValue < thermostatCurrentHumidityValue);
												belowValue = Math.round((belowValue + 9) / 10 * 10);
												if (ts.isBelowAboveHumidityAlertRangeOptionVisible(
														alertBelowHumidityRangeOption)) {
													belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue()
															.split(String.valueOf("%"))[0]);
													aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue()
															.split(String.valueOf("%"))[0]);
													if (belowValueApp >= aboveValueApp) {
														flag = false;
														Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
																"Displayed Below Humidity value in the app: "
																		+ belowValueApp
																		+ " and displayed Above Humidity value in the app: "
																		+ aboveValueApp);
													} else {
														Keyword.ReportStep_Pass(testCase,
																"[BeforeChange]Displayed Below Humidity value: "
																		+ belowValueApp
																		+ " and displayed Above Humidity value: "
																		+ aboveValueApp);
													}
													ts.clickOnBelowAboveHumidityAlertRangeOption(
															alertBelowHumidityRangeOption);
													try {
														Keyword.ReportStep_Pass(testCase,
																"Scroll to Below Humidity value: " + belowValue);
														element = testCase.getMobileDriver()
																.scrollTo(String.valueOf(belowValue));
														if (element != null) {
															element.click();
															inputs.setInputValue("INDOORHUMIDITY_BELOW_VALUE",
																	belowValue);
															Keyword.ReportStep_Pass(testCase,
																	"Below Humidity value is set to: "
																			+ inputs.getInputValue(
																					"INDOORHUMIDITY_BELOW_VALUE"));
														}
													} catch (Exception e) {
														flag = false;
														Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
																"Error message: " + e.getMessage());
													}

													if (ts.isIndoorHumidityRangeValueVisible()) {
														belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue()
																.split(String.valueOf("%"))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue()
																.split(String.valueOf("%"))[0]);
														Keyword.ReportStep_Pass(testCase,
																"[AfterChange]Displayed Below Humidity value is: "
																		+ belowValueApp
																		+ " and Displayed Above Humidity value is: "
																		+ aboveValueApp);
													}
													if (ts.isBackButtonVisible(1)) {
														ts.clickOnBackButton();
														waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR",
																3);
													}

												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Below Humidity value: " + belowValueApp
									+ " is equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Below humidity value: "
									+ belowValueApp + " to a new value greater than " + thermostatCurrentHumidityValue);
					try {
						do {
							belowValue = r.nextInt((90 - 5) + 1) + 5;
						} while (belowValue < thermostatCurrentHumidityValue);
						belowValue = Math.round((belowValue + 9) / 10 * 10);
						ts.clickOnBelowAboveHumidityAlertRangeOption(alertBelowHumidityRangeOption);
						try {
							Keyword.ReportStep_Pass(testCase, "Scroll to Below Humidity value: " + belowValue);
							element = testCase.getMobileDriver().scrollTo(String.valueOf(belowValue));
							if (element != null) {
								element.click();
								inputs.setInputValue("INDOORHUMIDITY_BELOW_VALUE", belowValue);
								Keyword.ReportStep_Pass(testCase, "Below Humidity value is set to: "
										+ inputs.getInputValue("INDOORHUMIDITY_BELOW_VALUE"));
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
						if (ts.isIndoorHumidityRangeValueVisible()) {
							belowValueApp = Integer
									.parseInt(ts.getBelowHumidityRangeValue().split(String.valueOf("%"))[0]);
							aboveValueApp = Integer
									.parseInt(ts.getAboveHumidityRangeValue().split(String.valueOf("%"))[0]);
							Keyword.ReportStep_Pass(testCase, "[AfterChange]Displayed Below Humidity value is: "
									+ belowValueApp + " and Above Humidity value is shown: " + aboveValueApp);
						}
						if (ts.isBackButtonVisible(1)) {
							ts.clickOnBackButton();
							waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
						}

					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				}
			}
		} else {
			if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertBelowHumidityRangeOption)) {
				belowValueApp = Integer
						.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption).split("%")[0]
								.replaceAll("\u00B0", ""));
				System.out.println("#######belowValueApp: " + belowValueApp);
				if (belowValueApp != thermostatCurrentHumidityValue) {
					Keyword.ReportStep_Pass(testCase,
							"Below Humidity value: " + belowValueApp
									+ " is not equal to current thermostat Humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Below Humidity value: "
									+ belowValueApp + " to " + thermostatCurrentHumidityValue);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Below Humidity value: " + thermostatCurrentHumidityValue);
						flag = ts.setHumidiityValueForHumidityRange(alertBelowHumidityRangeOption,
								thermostatCurrentHumidityValue);
						if (flag) {
							// element.click();
							Keyword.ReportStep_Pass(testCase,
									"Below Humidity value is set to: " + thermostatCurrentHumidityValue);
							belowValueApp = Integer.parseInt(ts
									.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption).split("%")[0]);
							if ((belowValueApp == thermostatCurrentHumidityValue) && (ts.isBackButtonVisible(1))) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
								if (ts.isManageAlertsLabelVisible(10)) {
									ts.clickOnManageAlertsLabel();
									if (ts.isThermostatHumidityAlertRangeVisible()) {
										ts.clickOnThermostatHumidityAlertRange();
										if (ts.isBelowAboveHumidityAlertRangeOptionVisible(
												alertBelowHumidityRangeOption)) {
											belowValueApp = Integer.parseInt(ts
													.getBelowAboveHumidityAlertRangeValue(alertBelowHumidityRangeOption)
													.split("%")[0]);
											if ((belowValueApp == thermostatCurrentHumidityValue)) {
												do {
													belowValue = r.nextInt((90 - 5) + 1) + 5;
													System.out.println("@@@@@@@@belowValue: " + belowValue);
												} while (belowValue < thermostatCurrentHumidityValue);
												belowValue = Math.round((belowValue + 9) / 10 * 10);
												if (ts.isBelowAboveHumidityAlertRangeOptionVisible(
														alertBelowHumidityRangeOption)) {
													belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue()
															.split(String.valueOf("%"))[0]);
													aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue()
															.split(String.valueOf("%"))[0]);
													if (belowValueApp >= aboveValueApp) {
														flag = false;
														Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
																"Displayed Below Humidity value in the app: "
																		+ belowValueApp
																		+ " and displayed Above Humidity value in the app: "
																		+ aboveValueApp);
													} else {
														Keyword.ReportStep_Pass(testCase,
																"[BeforeChange]Displayed Below Humidity value: "
																		+ belowValueApp
																		+ " and displayed Above Humidity value: "
																		+ aboveValueApp);
													}
													try {
														Keyword.ReportStep_Pass(testCase,
																"Scroll to Below Humidity value: " + belowValue);
														flag = ts.setHumidiityValueForHumidityRange(
																alertBelowHumidityRangeOption, belowValue);
														if (flag) {
															// element.click();
															inputs.setInputValue("INDOORHUMIDITY_BELOW_VALUE",
																	belowValue);
															Keyword.ReportStep_Pass(testCase,
																	"Below Humidity value is set to: "
																			+ inputs.getInputValue(
																					"INDOORHUMIDITY_BELOW_VALUE"));
														}
													} catch (Exception e) {
														flag = false;
														Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
																"Error message: " + e.getMessage());
													}
													if (ts.isIndoorHumidityRangeValueVisible()) {
														belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue()
																.split(String.valueOf("%"))[0]);
														aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue()
																.split(String.valueOf("%"))[0]);
														Keyword.ReportStep_Pass(testCase,
																"[AfterChange]Displayed Below Humidity value is: "
																		+ belowValueApp
																		+ " and Displayed Above Humidity value is: "
																		+ aboveValueApp);
													}
													if (ts.isBackButtonVisible(1)) {
														ts.clickOnBackButton();
														waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR",
																3);
													}

												}
											}
										}
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Below Humidity value: " + belowValueApp
									+ " is equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Below humidity value: "
									+ belowValueApp + " to a new value greater than " + thermostatCurrentHumidityValue);
					try {
						do {
							belowValue = r.nextInt((90 - 5) + 1) + 5;
							System.out.println("@@@@@@@@belowValue: " + belowValue);
						} while (belowValue < thermostatCurrentHumidityValue);
						belowValue = Math.round((belowValue + 9) / 10 * 10);
						try {
							Keyword.ReportStep_Pass(testCase, "Scroll to Below Humidity value: " + belowValue);
							flag = ts.setHumidiityValueForHumidityRange(alertBelowHumidityRangeOption, belowValue);
							if (flag) {
								// element.click();
								inputs.setInputValue("INDOORHUMIDITY_BELOW_VALUE", belowValue);
								Keyword.ReportStep_Pass(testCase, "Below Humidity value is set to: "
										+ inputs.getInputValue("INDOORHUMIDITY_BELOW_VALUE"));
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error message: " + e.getMessage());
						}
						if (ts.isIndoorHumidityRangeValueVisible()) {
							belowValueApp = Integer
									.parseInt(ts.getBelowHumidityRangeValue().split(String.valueOf("%"))[0]);
							aboveValueApp = Integer
									.parseInt(ts.getAboveHumidityRangeValue().split(String.valueOf("%"))[0]);
							Keyword.ReportStep_Pass(testCase, "[AfterChange]Displayed Below Humidity value is: "
									+ belowValueApp + " and Above Humidity value is shown: " + aboveValueApp);
						}
						if (ts.isBackButtonVisible(1)) {
							ts.clickOnBackButton();
							waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
						}

					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				}
			}
		}
		if ((belowValueApp >= 5 && belowValueApp <= 90) && (aboveValueApp <= 95 && aboveValueApp >= 10)) {
			Keyword.ReportStep_Pass(testCase,
					"Below value is set within the range 5% and 90% AND Above value is set within the range 10% and 95%");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Below and Above values are not set within the range: Below(5%->90%) and Above(10%->95%)");
		}
		return flag;
	}

	/**
	 * <h1>Select Humidity range value</h1>
	 * <p>
	 * The selectAboveHumidityRangeValue method is to select the humidity range
	 * value
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @return boolean Returns 'true' if the app sets humidity range value above
	 *         current thermostat humidity. Returns 'false' if unsuccessful.
	 */
	public static boolean selectAboveHumidityRangeValue(TestCases testCase, TestCaseInputs inputs,
			String alertAboveHumidityRangeOption) throws Exception {
		boolean flag = true;
		WebElement element = null;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		int thermostatCurrentHumidityValue = Integer
				.parseInt(inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDITY_VALUE"));
		thermostatCurrentHumidityValue = Math.round((thermostatCurrentHumidityValue - 5) / 10 * 10);
		int updatedThermostatCurrentHumidityValue = (thermostatCurrentHumidityValue + 10);

		int belowValueApp = 0, aboveValueApp = 0;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertAboveHumidityRangeOption)) {
				aboveValueApp = Integer
						.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption).split("%")[0]);
				if (aboveValueApp != thermostatCurrentHumidityValue) {
					Keyword.ReportStep_Pass(testCase,
							"Above Humidity value: " + aboveValueApp
									+ " is not equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Above humidity value: "
									+ aboveValueApp + " to " + thermostatCurrentHumidityValue);
					ts.clickOnBelowAboveHumidityAlertRangeOption(alertAboveHumidityRangeOption);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Above Humidity value: " + thermostatCurrentHumidityValue);
						element = testCase.getMobileDriver().scrollTo(String.valueOf(thermostatCurrentHumidityValue));
						if (element != null) {
							element.click();
							Keyword.ReportStep_Pass(testCase,
									"Above Humidity value is set to: " + thermostatCurrentHumidityValue);
							aboveValueApp = Integer
									.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption)
											.split(String.valueOf("%"))[0]);
							inputs.setInputValue("INDOORHUMIDITY_ABOVE_VALUE", aboveValueApp);
							Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: "
									+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE"));
							if ((aboveValueApp == thermostatCurrentHumidityValue) && (ts.isBackButtonVisible(1))) {
								if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertAboveHumidityRangeOption)) {
									belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue().split("%")[0]);
									aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue().split("%")[0]);
									if (belowValueApp >= aboveValueApp) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Displayed Below Humidity value in the app is: " + belowValueApp
														+ " and Displayed Above Humidity value in the app is: "
														+ aboveValueApp);
									} else {
										Keyword.ReportStep_Pass(testCase,
												"[BeforeChange]Below Humidity value displayed is: " + belowValueApp
														+ " and Above Humidity value displayed is: " + aboveValueApp);
									}
									if (ts.isBackButtonVisible(1)) {
										ts.clickOnBackButton();
										waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Above Humidity value: " + aboveValueApp
									+ " is equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Above Humidity value: "
									+ aboveValueApp + " to a new value lower than " + thermostatCurrentHumidityValue);
					ts.clickOnBelowAboveHumidityAlertRangeOption(alertAboveHumidityRangeOption);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Above Humidity value: " + updatedThermostatCurrentHumidityValue);
						element = testCase.getMobileDriver()
								.scrollTo(String.valueOf(updatedThermostatCurrentHumidityValue));
						if (element != null) {
							element.click();
							aboveValueApp = Integer
									.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption)
											.split(String.valueOf("%"))[0]);
							Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: " + aboveValueApp);
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
								if (ts.isManageAlertsLabelVisible(10)) {
									ts.clickOnManageAlertsLabel();
									if (ts.isThermostatHumidityAlertRangeVisible()) {
										ts.clickOnThermostatHumidityAlertRange();
										ts.clickOnBelowAboveHumidityAlertRangeOption(alertAboveHumidityRangeOption);
										try {
											Keyword.ReportStep_Pass(testCase, "Scroll to Above Humidity value: "
													+ thermostatCurrentHumidityValue);
											element = testCase.getMobileDriver()
													.scrollTo(String.valueOf(thermostatCurrentHumidityValue));
											if (element != null) {
												element.click();
												aboveValueApp = Integer.parseInt(ts
														.getBelowAboveHumidityAlertRangeValue(
																alertAboveHumidityRangeOption)
														.split(String.valueOf("%"))[0]);
												Keyword.ReportStep_Pass(testCase,
														"Above Humidity value is set to: " + aboveValueApp);
												inputs.setInputValue("INDOORHUMIDITY_ABOVE_VALUE", aboveValueApp);
												Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: "
														+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE"));
											}
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Error message: " + e.getMessage());
										}
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				}
			}
		} else {
			if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertAboveHumidityRangeOption)) {
				aboveValueApp = Integer
						.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption).split("%")[0]);
				if (aboveValueApp != thermostatCurrentHumidityValue) {
					Keyword.ReportStep_Pass(testCase,
							"Above Humidity value: " + aboveValueApp
									+ " is not equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Above humidity value: "
									+ aboveValueApp + " to " + thermostatCurrentHumidityValue);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Above Humidity value: " + thermostatCurrentHumidityValue);
						flag = ts.setHumidiityValueForHumidityRange(alertAboveHumidityRangeOption,
								thermostatCurrentHumidityValue);
						if (flag) {
							// element.click();
							Keyword.ReportStep_Pass(testCase,
									"Above Humidity value is set to: " + thermostatCurrentHumidityValue);
							aboveValueApp = Integer
									.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption)
											.split(String.valueOf("%"))[0]);
							inputs.setInputValue("INDOORHUMIDITY_ABOVE_VALUE", aboveValueApp);
							Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: "
									+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE"));
							if ((aboveValueApp == thermostatCurrentHumidityValue) && (ts.isBackButtonVisible(1))) {
								if (ts.isBelowAboveHumidityAlertRangeOptionVisible(alertAboveHumidityRangeOption)) {
									belowValueApp = Integer.parseInt(ts.getBelowHumidityRangeValue().split("%")[0]);
									aboveValueApp = Integer.parseInt(ts.getAboveHumidityRangeValue().split("%")[0]);
									if (belowValueApp >= aboveValueApp) {
										flag = false;
										Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Displayed Below Humidity value in the app is: " + belowValueApp
														+ " and Displayed Above Humidity value in the app is: "
														+ aboveValueApp);
									} else {
										Keyword.ReportStep_Pass(testCase,
												"[BeforeChange]Below Humidity value displayed is: " + belowValueApp
														+ " and Above Humidity value displayed is: " + aboveValueApp);
									}
									if (ts.isBackButtonVisible(1)) {
										ts.clickOnBackButton();
										waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Above Humidity value: " + aboveValueApp
									+ " is equal to current thermostat humidity value: "
									+ thermostatCurrentHumidityValue + ". Updating Above Humidity value: "
									+ aboveValueApp + " to a new value lower than " + thermostatCurrentHumidityValue);
					try {
						Keyword.ReportStep_Pass(testCase,
								"Scroll to Above Humidity value: " + updatedThermostatCurrentHumidityValue);
						flag = ts.setHumidiityValueForHumidityRange(alertAboveHumidityRangeOption,
								updatedThermostatCurrentHumidityValue);
						if (flag) {
							// element.click();
							aboveValueApp = Integer
									.parseInt(ts.getBelowAboveHumidityAlertRangeValue(alertAboveHumidityRangeOption)
											.split(String.valueOf("%"))[0]);
							Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: " + aboveValueApp);
							if (ts.isBackButtonVisible(1)) {
								ts.clickOnBackButton();
								waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
								if (ts.isManageAlertsLabelVisible(10)) {
									ts.clickOnManageAlertsLabel();
									if (ts.isThermostatHumidityAlertRangeVisible()) {
										ts.clickOnThermostatHumidityAlertRange();
										ts.clickOnBelowAboveHumidityAlertRangeOption(alertAboveHumidityRangeOption);
										try {
											Keyword.ReportStep_Pass(testCase, "Scroll to Above Humidity value: "
													+ thermostatCurrentHumidityValue);
											flag = ts.setHumidiityValueForHumidityRange(alertAboveHumidityRangeOption,
													thermostatCurrentHumidityValue);
											if (flag) {
												// element.click();
												aboveValueApp = Integer.parseInt(ts
														.getBelowAboveHumidityAlertRangeValue(
																alertAboveHumidityRangeOption)
														.split(String.valueOf("%"))[0]);
												Keyword.ReportStep_Pass(testCase,
														"Above Humidity value is set to: " + aboveValueApp);
												inputs.setInputValue("INDOORHUMIDITY_ABOVE_VALUE", aboveValueApp);
												Keyword.ReportStep_Pass(testCase, "Above Humidity value is set to: "
														+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE"));
											}
										} catch (Exception e) {
											flag = false;
											Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
													"Error message: " + e.getMessage());
										}
									}
								}
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error message: " + e.getMessage());
					}
				}
			}
		}
		if ((belowValueApp >= 5 && belowValueApp <= 90) && (aboveValueApp <= 95 && aboveValueApp >= 10)) {
			Keyword.ReportStep_Pass(testCase,
					"Below value is set within the range 5% and 90% AND Above value is set within the range 10% and 95%");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Below and Above values are not set within the range: Below(5%->90%) and Above(10%->95%)");
		}
		return flag;
	}

	public static boolean verifyIndoorHumidityRangeAlertInActivityHistoryScreen(TestCases testCase,
			TestCaseInputs inputs, String indoorHumidityAlertRangeOption) throws Exception {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		List<WebElement> listAlertMsgs = new ArrayList<>();
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (ts.isActivityHistoryAlertTitleVisible() && ts.isActivityHistoryAlertMsgVisible()) {
				listAlertMsgs = ts.getActivityHistoryAlertMsgsList();
				for (int i = 0; i < listAlertMsgs.size(); i++) {
					if (indoorHumidityAlertRangeOption.contains("Below") && ts.isActivityHistoryAlertTimeVisible()) {
						if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
							String expectedBelowHumidityAlert = "Low humidity! Your home’s humidity level is less than "
									+ inputs.getInputValue("INDOORHUMIDITY_BELOW_VALUE") + "%.";
							if (listAlertMsgs.get(i).getText().equalsIgnoreCase(expectedBelowHumidityAlert)) {
								Keyword.ReportStep_Pass(testCase,
										"Below Humidity Alert Message is displayed: " + listAlertMsgs.get(i).getText());
								break;
							}
						} else {
							flag = false;
						}
					} else if (indoorHumidityAlertRangeOption.contains("Above")
							&& ts.isActivityHistoryAlertTimeVisible()) {
						if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
							String expectedAboveHumidityAlert = "High Humidity! Your home’s humidity level is over "
									+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE") + "%.";
							if (listAlertMsgs.get(i).getText().equalsIgnoreCase(expectedAboveHumidityAlert)) {
								Keyword.ReportStep_Pass(testCase,
										"Above Humidity Alert Message is displayed: " + listAlertMsgs.get(i).getText());
								break;
							}
						} else {
							flag = false;
						}
					}
				}
			}
		} else {
			if (ts.isActivityHistoryAlertTitleVisible() && ts.isActivityHistoryAlertMsgVisible()) {
				listAlertMsgs = ts.getActivityHistoryAlertMsgsList();
				for (int i = 0; i < listAlertMsgs.size(); i++) {
					if (indoorHumidityAlertRangeOption.contains("Below") && ts.isActivityHistoryAlertTimeVisible()) {
						if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
							String expectedBelowHumidityAlert = "Low humidity! Your home’s humidity level is less than "
									+ inputs.getInputValue("INDOORHUMIDITY_BELOW_VALUE") + "%.";
							if (listAlertMsgs.get(i).getAttribute("value")
									.equalsIgnoreCase(expectedBelowHumidityAlert)) {
								Keyword.ReportStep_Pass(testCase, "Below Humidity Alert Message is displayed: "
										+ listAlertMsgs.get(i).getAttribute("value"));
								break;
							}
						} else {
							flag = false;
						}
					} else if (indoorHumidityAlertRangeOption.contains("Above")
							&& ts.isActivityHistoryAlertTimeVisible()) {
						if (ts.getActivityHistoryAlertTime().equalsIgnoreCase("A moment ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("one minute ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("2 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("3 minutes ago")
								|| ts.getActivityHistoryAlertTime().equalsIgnoreCase("4 minutes ago")) {
							String expectedAboveHumidityAlert = "High Humidity! Your home’s humidity level is over "
									+ inputs.getInputValue("INDOORHUMIDITY_ABOVE_VALUE") + "%.";
							if (listAlertMsgs.get(i).getAttribute("value")
									.equalsIgnoreCase(expectedAboveHumidityAlert)) {
								Keyword.ReportStep_Pass(testCase, "Above Humidity Alert Message is displayed: "
										+ listAlertMsgs.get(i).getAttribute("value"));
								break;
							}
						} else {
							flag = false;
						}
					}
				}
			}
		}
		return flag;
	}

	public static boolean verifyDeleteThermostatDeviceConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		if (ts.isDeleteThermostatDevicePopupTitleVisible()) {
			Keyword.ReportStep_Pass(testCase,
					"Delete Thermostat Device Confirmation Pop Up Title is correctly displayed");
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (MobileUtils.isMobElementExists("XPATH",
						"//android.widget.TextView[contains(@text, 'Are you sure you want to delete')]", testCase)) {
					return flag;
				} else {
					flag = false;
				}
			} else {

				// iOS
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Thermostat Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteThermostatDeviceConfirmationPopUpIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		if (ts.isDeleteThermostatDevicePopupTitleVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Thermostat Device Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete Thermostat Device Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyThermostatHumidificationValueInHumidificationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		String currentThermostatHumdificationValue = ts
				.getThermostatHumidificationValueInHumidificationScreen(testCase);
		inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE",
				currentThermostatHumdificationValue);
		if (inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE") != null) {
			Keyword.ReportStep_Pass(testCase, "Current Thermostat Humidification value is: "
					+ inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDIFICATION_VALUE"));
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}

	public static boolean verifyThermostatDehumidificationValueInDehumidificationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		String currentThermostatDehumdificationValue = ts
				.getThermostatDehumidificationValueInHumidificationScreen(testCase);
		inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE",
				currentThermostatDehumdificationValue);
		if (inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE") != null) {
			Keyword.ReportStep_Pass(testCase, "Current Thermostat Dehumidification value is: "
					+ inputs.getInputValue("CURRENT_THERMOSTAT_DEHUMIDIFICATION_VALUE"));
			return flag;
		} else {
			flag = false;
		}
		return flag;
	}
}