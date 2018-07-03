package com.honeywell.keywords.lyric.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.LoginScreen;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;

public class LogoutOfLyric extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	boolean success = false;

	public LogoutOfLyric(TestCases testCase, TestCaseInputs inputs) {
		super("Logout of Lyric");
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@SuppressWarnings("unchecked")
	@Override
	@BeforeKeyword
	public boolean preCondition() {
		boolean flag = true;
		inputs.setInputValue("LOCATION1_NAME", inputs.getInputValue(InputVariables.LOCATION_NAME));
		inputs.setInputValue("LOCATION1_DEVICE1_NAME", inputs.getInputValue(InputVariables.DEVICE_NAME));

		if (inputs.isRunningOn("Perfecto")) {
			if (inputs.getInputValue(InputVariables.MOBILE_LOCATION_OFF).equalsIgnoreCase("true")) {
				flag = flag & LyricUtils.changeLocationSettings(testCase, inputs, "ON");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					Activity activity = new Activity("com.honeywell.android.lyric",
							"com.honeywell.granite.graniteui.presentation.activity.dashboard.DashBoardActivity");
					((AndroidDriver<MobileElement>) testCase.getMobileDriver()).startActivity(activity);
				} else {
					testCase.getMobileDriver().launchApp();
				}
			}
			/*	if (inputs.getInputValue(InputVariables.MOBILE_LOCATIONPERMISSION_OFF).equalsIgnoreCase("true")) {
				InputVariables.changeLocationPermission(testCase, inputs);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					((AndroidDriver<MobileElement>) testCase.getMobileDriver()).startActivity("com.honeywell.android.lyric",
							"com.honeywell.granite.graniteui.presentation.activity.dashboard.DashBoardActivity");
				} else {
						testCase.getMobileDriver().launchApp();
				}
			}*/
		}

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user logs out of the app$")
	public boolean keywordSteps() {
		boolean flag = true;
		try {
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);

			Dashboard db = new Dashboard(testCase);
			if (db.isGlobalDrawerButtonVisible(10)) {
				WebElement element = null;
				if (!db.clickOnGlobalDrawerButton()) {
					flag = false;
				} else {
					try {
						if (testCase.getPlatform().toUpperCase().contains("IOS")) {
							Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
							TouchAction action = new TouchAction(testCase.getMobileDriver());
							for (int i = 0; i < 3; ++i) {
								if (success) {
									break;
								}
								try {
									action.press(10, (int) (dimension.getHeight() * .5))
									.moveTo(0, (int) (dimension.getHeight() * -.5)).release().perform();
								} catch (Exception e) {
								}

								FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(
										testCase.getMobileDriver());
								fWait.withTimeout(5, TimeUnit.SECONDS);
								fWait.pollingEvery(500, TimeUnit.MILLISECONDS);
								try {
									WebElement logoutElement = fWait.until(ExpectedConditions
											.visibilityOfElementLocated(By.xpath("//*[@label='logout_subTitle']")));
									if (logoutElement != null) {
										logoutElement.click();
										success = true;
									} else {
										flag = false;
										ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
												"Logout of Lyric : Logout option not found");
									}
								} catch (TimeoutException e) {
									// Retry again
								}
							}
						} else {
							element = testCase.getMobileDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
									+ "new UiSelector().text(\"Logout\"));"));
							if (element != null) {
								element.click();
								ReportStep_Pass(testCase, "Clicked on Logout option");
							} else {
								flag = false;
								ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Logout of Lyric : Logout option not found");
							}
						}
						LoginScreen ls = new LoginScreen(testCase);
						if (ls.isLoginButtonVisible()) {
							ReportStep_Pass(testCase, "Logout of Lyric : Logout operation Successful.");
						} else {
							ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Logout of Lyric : Not able to logout of the App after click on Logout option.");
							flag = false;
						}
					} catch (Exception e) {
						flag = false;
						ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"[ScrollToLogout] Error message: " + e.getMessage());
					}
				}
			} else {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Global Drawer icon");
			}

			if (!inputs.isRunningOn("Perfecto")) {
				testCase.getMobileDriver().closeApp();
			}
			/*
			 * if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			 * testCase.getMobileDriver().removeApp(
			 * SuiteConstants.getConstantValue(SuiteConstantTypes.MOBILE_APP,
			 * "Android_Package_Name")); }
			 */

			//testCase.getMobileDriver().quit();
			//testCase.setMobileDriver(null);
		} catch (Exception e) {
			ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Logout of Lyric : Error occured - " + e.getMessage());
			flag = false;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		boolean flag = true;
		return flag;
	}
}
