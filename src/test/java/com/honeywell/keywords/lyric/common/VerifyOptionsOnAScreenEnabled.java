package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.CameraUtils;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.Dashboard;

import com.honeywell.screens.PrimaryCard;


import io.appium.java_client.TouchAction;

public class VerifyOptionsOnAScreenEnabled extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyOptionsOnAScreenEnabled(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^the following \"(.*)\" options should be enabled:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "CAMERA SETTINGS": {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
//			Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
//			TouchAction action = new TouchAction(testCase.getMobileDriver());
//			CameraUtils.waitForProgressBarToComplete(testCase, "RETRY IN LOADING SNAPSHOT SPINNER", 5);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				if (fieldToBeVerified.equalsIgnoreCase("Camera Mode")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Camera Mode section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Camera Mode section is disabled");

					}
			}else if (fieldToBeVerified.equalsIgnoreCase("Manage Alerts")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Manage Alerts section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts section is disabled");
					}
			} else if (fieldToBeVerified.equalsIgnoreCase("Motion Detection")) {
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Motion Detection section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Motion Detection section is disabled");
				}
		}else if (fieldToBeVerified.equalsIgnoreCase("Sound Detection")) {
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Sound Detection section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sound Detection section is disabled");
				}
		}else if (fieldToBeVerified.equalsIgnoreCase("Night Vision")) {
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Night Vision section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Night Vision section is disabled");
				}
		}else if (fieldToBeVerified.equalsIgnoreCase("Video Quality")) {
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Video Quality  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality  section is disabled");
					}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera LED")) {
						if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
							Keyword.ReportStep_Pass(testCase, "Camera LED  section is enabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera LED  section is disabled");
						}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera Microphone")) {
							if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
								Keyword.ReportStep_Pass(testCase, "Camera Microphone  section is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Camera Microphone  section is disabled");
							}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera Configuration")) {
		    Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			int startx = (dimension.width * 20) / 100;
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
	        action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
			}else {
				int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			}
								if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
									Keyword.ReportStep_Pass(testCase, "Camera Configuration  section is enabled");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Camera Configuration  section is disabled");
								}
		}
			}
			break;
		}

		

		case "THERMOSTAT ICONS":{

			PrimaryCard thermo = new PrimaryCard(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				System.out.println(fieldToBeVerified);
				if (fieldToBeVerified.equalsIgnoreCase("mode")) {
					if(thermo.isModeElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "Mode Element is enabled");
					}
					else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule Element is not enabled");
					}
					
				}
				else if (fieldToBeVerified.equalsIgnoreCase("schedule")) {
					if(thermo.isScheduleElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "Schedule Element is enabled");
					}
					else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Schedule Element is not enabled");

					}
			}else if (fieldToBeVerified.equalsIgnoreCase("Manage Alerts")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Manage Alerts section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Manage Alerts section is disabled");
					}
			} else if (fieldToBeVerified.equalsIgnoreCase("Motion Detection")) {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
			
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Motion Detection section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Motion Detection section is disabled");
				}

			}
			break;
			
		}
		}
		
		case "THERMOSTAT":{

			Dashboard thermo = new Dashboard(testCase);

			for (int i = 0; i < data.getSize(); i++) {
				String fieldToBeVerified = data.getData(i, "Options");
				System.out.println(fieldToBeVerified);
				if (fieldToBeVerified.equalsIgnoreCase("UP STEPPER")) {
					if(thermo.isUPStepperElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "UP Stepper Element is enabled");
					}
					else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"UP Stepper Element is not enabled");
					}
					
				}
				else if (fieldToBeVerified.equalsIgnoreCase("DOWN STEPPER")) {
					if(thermo.isDownStepperElementEnabled()) {

						Keyword.ReportStep_Pass(testCase, "Down Stepper Element is enabled");
					}
					else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Down Stepper Element is not enabled");

		}
				}else if (fieldToBeVerified.equalsIgnoreCase("Sound Detection")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Sound Detection section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Sound Detection section is disabled");
				}
		}else if (fieldToBeVerified.equalsIgnoreCase("Night Vision")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
					Keyword.ReportStep_Pass(testCase, "Night Vision section is enabled");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Night Vision section is disabled");
				}
		}else if (fieldToBeVerified.equalsIgnoreCase("Video Quality")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
					if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
						Keyword.ReportStep_Pass(testCase, "Video Quality  section is enabled");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Video Quality  section is disabled");

					}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera LED")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
						if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
							Keyword.ReportStep_Pass(testCase, "Camera LED  section is enabled");
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Camera LED  section is disabled");
						}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera Microphone")) {
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
							if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
								Keyword.ReportStep_Pass(testCase, "Camera Microphone  section is enabled");
							} else {
								flag = false;
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Camera Microphone  section is disabled");
							}
		}else if (fieldToBeVerified.equalsIgnoreCase("Camera Configuration")) {
		    Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
			TouchAction action = new TouchAction(testCase.getMobileDriver());
			if (testCase.getPlatform().toUpperCase().contains("IOS")) {
			int startx = (dimension.width * 20) / 100;
			action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
	        action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6)).release().perform();
			}else {
				int startx = (dimension.width * 20) / 100;
			int starty = (dimension.height * 62) / 100;
			int endx = (dimension.width * 22) / 100;
			int endy = (dimension.height * 35) / 100;
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
			}
			CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
								if (cs.isCameraSetingsOptionVisible(fieldToBeVerified)) {
									Keyword.ReportStep_Pass(testCase, "Camera Configuration  section is enabled");
								} else {
									flag = false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
											"Camera Configuration  section is disabled");
								}
		}
			}
			break;
			
		}

		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
