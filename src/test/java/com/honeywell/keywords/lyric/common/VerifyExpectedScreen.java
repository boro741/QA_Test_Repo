package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.perfecto.WindTunnelUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSolutionCardScreen;

public class VerifyExpectedScreen extends Keyword {

	private TestCases testCase;
	private HashMap<String, MobileObject> fieldObjects;
	public boolean flag = true;
	private ArrayList<String> expectedNavigation;
	private TestCaseInputs inputs;

	public VerifyExpectedScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedNavigation) {
		super("Select Device From Dashboard");
		this.testCase = testCase;
		this.expectedNavigation = expectedNavigation;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		flag = true;
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify user is in \"(.+)\"$")
	public boolean keywordSteps() {
			try {
				switch (expectedNavigation.get(0).toUpperCase()) {
				case "LOGIN SCREEN": {
					// LAunch the app
					flag = MobileUtils.launchApplication(inputs, testCase, false);
					// verify it is in Login screen
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "LoginButton")) {
						Keyword.ReportStep_Pass(testCase, "In Login screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Dashboard not displayed");
					}
					// flag= flag && CameraUtils.clickOnHamburgerMenu(fieldObjects,testCase);
					// CameraUtils.selectAddNewDeviceFromHamburgerMenu(testCase, inputs,
					// fieldObjects);
					break;
				}
				case "CAMERA CONFIGURATION": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// Navigate to Hamburger menu
					// flag= flag && CameraUtils.clickOnHamburgerMenu(fieldObjects,testCase);
					// Choose the device provided in inputs
					// flag= flag && CameraUtils.selectDeviceFromHamburgerMenu(testCase, inputs);
					// flag= flag && CameraUtils.clickOnCameraConfigurationSettings(testCase);
					break;
				}
				case "CAMERA DASHBOARD": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// check whether already in solution card/select the device from dashboard to
					// navigate to solution card
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						// TODO
					} else {
						if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardDevices")) {
							List<WebElement> devices = MobileUtils.getMobElements(fieldObjects, testCase,
									"DashboardDevices");
							if (devices.size() > 0) {
								Keyword.ReportStep_Pass(testCase, "Camera Dashboard is displayed");
							} else {
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Camera Dashboard not displayed");
							}
						}
					}

					break;
				}
				case "CAMERA SOLUTION CARD": {
					// Choose the location provided in inputs
					CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
					if (cs.isCameraOnButtonVisible(15)) {
						Keyword.ReportStep_Pass(testCase, "Camera Solution card is displayed");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Solution card is not displayed");
						flag = false;
					}
					break;
				}
				case "CLIP CARD LIST": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// check whether already in solution card/select the device from dashboard to
					// navigate to solution card
					// flag=flag & CameraUtils.navigateToSolutionCard(testCase, inputs);
					// brings the clip card into view
					// flag=flag & CameraUtils.navigateToClipCard(testCase);
					break;
				}
				case "CLIP PLAY": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// check whether already in solution card/select the device from dashboard to
					// navigate to solution card
					// flag=flag & CameraUtils.navigateToSolutionCard(testCase, inputs);
					// brings the clip card into view
					// flag=flag & CameraUtils.navigateToClipCard(testCase);

					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ClipCardFrames")) {
						Keyword.ReportStep_Pass(testCase, "Clip card frames displayed");
						List<WebElement> clipFrames = MobileUtils.getMobElements(fieldObjects, testCase,
								"ClipCardFrames");
						clipFrames = MobileUtils.getMobElements(fieldObjects, testCase, "ClipCardFrames");
						if (clipFrames.size() > 0) {
							try {
								clipFrames.get(0).click();
								// Keyword.ReportStep_Pass(testCase,"Tap on clip at "+randomNum +" position
								// "+randomNum+"was successfull");
								Keyword.ReportStep_Pass(testCase, "Tap on clip was successfull");
							} catch (Exception e) {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to tap");
							}
						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "No clip displayed");
					}
					break;

				}
				case "HAMBURGER MENU": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// Navigate to Hamburger menu
					// flag= flag && CameraUtils.clickOnHamburgerMenu(fieldObjects,testCase);
					break;
				}
				case "CAMERA DEVICE SETTINGS": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// Navigate to Hamburger menu
					// flag= flag && CameraUtils.clickOnHamburgerMenu(fieldObjects,testCase);
					// Choose the device provided in inputs
					// CameraUtils.selectDeviceFromHamburgerMenu(testCase, inputs);
					break;
				}
				case "CAMERA MODE SETTINGS": {
					// Choose the location provided in inputs
					// flag=CameraUtils.chooseLocation(testCase,inputs);
					// Navigate to Hamburger menu
					// flag= flag && CameraUtils.clickOnHamburgerMenu(fieldObjects,testCase);
					// Choose the device provided in inputs
					// CameraUtils.selectDeviceFromHamburgerMenu(testCase, inputs);
					// Clicks on Camera mode from camera settings
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CameraModeMenu")) {
						MobileUtils.clickOnElement(fieldObjects, testCase, "CameraModeMenu");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								" Camera Mode Menu is not displayed");
						flag = false;
					}
					break;
				}
				case "NO NETWORK": {
					// Makes the wifi disconnects by turning ON Airplane mode
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						Dimension dimensions = testCase.getMobileDriver().manage().window().getSize();
						int width = (dimensions.width);
						int height = (dimensions.height);
						try {
							testCase.getMobileDriver().swipe(width - 100, height, width - 100, height - 200, 500);
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Not able to pull up the settings screen");
						}
						WebElement Wifi = MobileUtils.getMobElement(testCase, "name", "Wi-Fi");
						if (Wifi.getAttribute("value").equals("1")) {
							MobileUtils.clickOnElement(testCase, "name", "Wi-Fi");
							Keyword.ReportStep_Pass(testCase, "Disconnected WIFI");
						} else {
							Keyword.ReportStep_Pass(testCase, "WIFI is not connected");
						}

						WebElement downArrow = testCase.getMobileDriver().findElementByXPath(
								"//*[@name='Wi-Fi']/parent::UIAScrollView/preceding-sibling::UIAStaticText");
						int downArrowHeight = downArrow.getSize().getHeight();
						try {
							testCase.getMobileDriver().swipe(width - 100, downArrowHeight, width - 100, height, 500);
						} catch (Exception e) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Not able to close the settings screen");
						}
					} else {
						MobileUtils.setAirPlaneMode(testCase, true);
					}

					break;
				}
				case "NETWORK": {
					// Connects back wifi disconnects by turning OFF Airplane mode
					MobileUtils.setAirPlaneMode(testCase, false);
					break;
				}
				case "POOR NETWORK BANDWIDTH": {
					Keyword.ReportStep_Pass(testCase, "2g_gprs_average is set");
					WindTunnelUtils.performNetworkSimulation(testCase,
							WindTunnelUtils.NetworkSimulationOperations.UPDATE, "2g_gprs_average");
					break;
				}
				case "GOOD NETWORK BANDWIDTH": {
					Keyword.ReportStep_Pass(testCase, "4g_lte_advanced_good is set");
					WindTunnelUtils.performNetworkSimulation(testCase,
							WindTunnelUtils.NetworkSimulationOperations.START, "4g_lte_advanced_good");
					break;
				}
				default: {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Navigation input not handled");
				}
				}
			} catch (Exception e) {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "exception occured " + e.getMessage());
				flag = false;
			}
		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {

		return flag;
	}

}
