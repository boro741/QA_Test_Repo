package com.honeywell.jasper.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;

import io.appium.java_client.TouchAction;


public class JasperSchedulingEditUtils {
	public static boolean editGeofenceSchedule(TestCases testCase, TestCaseInputs inputs, String geofencePeriod) {
		boolean flag = true;
		 SchedulingScreen schl = new SchedulingScreen(testCase);
		try 
		{
			flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);

			Keyword.ReportStep_Pass(testCase,
					"*********************** Verifying geofence based schedule on solution card **************************");

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Home']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Home text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Home text not displayed on schedule screen");
				}

				if (schl.isUseMyHomeSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (schl.isCreateSleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (schl.isUseMySleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule :Use My Sleep Settings option not displayed on schedule screen");
					}
				}

				if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Away']", testCase, 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : When I'm Away text displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : When I'm Away text not displayed on schedule screen");
				}

				if (schl.isUseMyAwaySettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
				}
			} else {
				if (schl.isGeofenceHomeElementVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}

				if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
					if (schl.isCreateSleepSettingsTextVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Create Sleep Settings option not displayed on schedule screen");
					}
				} else {
					if (schl.isGeofenceSleepElementVisible(5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Displayed Schedule : Use My Sleep Settings option not displayed on schedule screen");
					}
				}
				if (schl.isGeofenceAwayElementVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
				}
			}

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();
			HashMap<String, String> targetSetPoints = new HashMap<String, String>();
			if (geofencePeriod.equalsIgnoreCase("Home")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (!schl.clickOnUseMyHomeSettingsButton()) {
						flag = false;
					}
				} else {
					if (!schl.clickOnGeofenceHomeButton()) {
						flag = false;
					}
				}
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Home period ***************");
				inputs.setInputValue(InputVariables.GEOFENCE_PERIOD,InputVariables.GEOFENCE_HOME);
				/*
				 * if (allowedModes.contains("Heat") &&
				 * allowedModes.contains("Cool")) { if
				 * (MobileUtils.isMobElementExists(fieldObjects, testCase,
				 * "HomeTemperatureHeaderMultiTemperature", 5)) {
				 * Keyword.ReportStep_Pass(testCase,
				 * "Edit Schedule : Successfully navigated to home set points page"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Edit Schedule : Failed to navigate to home set points page"); }
				 * } else if (allowedModes.contains("Heat") &&
				 * !allowedModes.contains("Cool") || !allowedModes.contains("Heat")
				 * && allowedModes.contains("Cool")) { if
				 * (MobileUtils.isMobElementExists(fieldObjects, testCase,
				 * "HomeTemperatureHeaderSingleTemperature", 5)) {
				 * Keyword.ReportStep_Pass(testCase,
				 * "Edit Schedule : Successfully navigated to home set points page"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Edit Schedule : Failed to navigate to home set points page"); }
				 * }
				 */
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_HOME_COOL_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Home cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
				}
				flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Home", targetSetPoints);
				flag = flag & schl.clickOnSaveButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting set points for Home period ***************");
			} else if (geofencePeriod.equalsIgnoreCase("Sleep")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (!schl.clickOnUseMySleepSettingsButton()) {
						flag = false;
					}
				} else {
					if (!schl.clickOnGeofenceSleepButton()) {
						flag = false;
					}
				}
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase,
						"*************** Setting time and set points for Sleep period ***************");
				inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase,
						inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_START_TIME), "GeofenceSleepStartTime", true, true);
				flag = flag & JasperSchedulingUtils.setPeriodTime(testCase, inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_END_TIME),
						"GeofenceSleepEndTime", true, true);
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_SLEEP_COOL_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Sleep cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
				}
				flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Sleep", targetSetPoints);
				flag = flag & schl.clickOnSaveButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting time and set points for Sleep period ***************");
			} else if (geofencePeriod.equalsIgnoreCase("Away")) {
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					if (!schl.clickOnUseMyAwaySettingsButton()) {
						flag = false;
					}
				} else {
					if (!schl.clickOnGeofenceAwayButton()) {
						flag = false;
					}
				}
				Keyword.ReportStep_Pass(testCase, " ");
				Keyword.ReportStep_Pass(testCase, "*************** Setting set points for Away period ***************");
				inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_AWAY);
				/*
				 * if (allowedModes.contains("Heat") &&
				 * allowedModes.contains("Cool")) { if
				 * (MobileUtils.isMobElementExists(fieldObjects, testCase,
				 * "AwayTemperatureHeaderMultiTemperature", 5)) {
				 * Keyword.ReportStep_Pass(testCase,
				 * "Edit Schedule : Successfully navigated to away set points page"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Edit Schedule : Failed to navigate to away set points page"); }
				 * } else if (allowedModes.contains("Heat") &&
				 * !allowedModes.contains("Cool") || !allowedModes.contains("Heat")
				 * && allowedModes.contains("Cool")) { if
				 * (MobileUtils.isMobElementExists(fieldObjects, testCase,
				 * "AwayTemperatureHeaderSingleTemperature", 5)) {
				 * Keyword.ReportStep_Pass(testCase,
				 * "Edit Schedule : Successfully navigated to away set points page"
				 * ); } else { flag = false; Keyword.ReportStep_Fail(testCase,
				 * FailType.FUNCTIONAL_FAILURE,
				 * "Edit Schedule : Failed to navigate to away set points page"); }
				 * }
				 */
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					targetSetPoints.put("targetHeatTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_HEAT_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away heat set points to "
							+ targetSetPoints.get("targetHeatTemp"));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					targetSetPoints.put("targetCoolTemp", inputs.getInputValue(InputVariables.GEOFENCE_AWAY_COOL_SETPOINT));
					Keyword.ReportStep_Pass(testCase, "Set Period Set Points : Setting Away cool set points to "
							+ targetSetPoints.get("targetCoolTemp"));
				}
				flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, "Away", targetSetPoints);
				flag = flag & schl.clickOnSaveButton();
				Keyword.ReportStep_Pass(testCase,
						"*************** Completed setting set points for Away period ***************");
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (schl.isBackButtonVisible(5)) {
					if (!schl.clickOnBackButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
			} else {
				if (schl.isCloseButtonVisible(5)) {
					if (!schl.clickOnCloseButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
				}
			}
		}catch (Exception e){
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean editGeofenceScheduleWithMinMaxValues(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		SchedulingScreen schl = new SchedulingScreen(testCase);
		flag = flag & JasperSchedulingUtils.viewScheduleOnPrimaryCard(testCase);

		Keyword.ReportStep_Pass(testCase,
				"*********************** Verifying geofence based schedule on solution card **************************");

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Home']", testCase, 5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : When I'm Home text displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule : When I'm Home text not displayed on schedule screen");
			}

			if (schl.isUseMyHomeSettingsTextVisible(5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
			}

			if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
				if (schl.isCreateSleepSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Create Sleep Settings option not displayed on schedule screen");
				}
			} else {
				if (schl.isUseMySleepSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule :Use My Sleep Settings option not displayed on schedule screen");
				}
			}

			if (MobileUtils.isMobElementExists("xpath", "//*[@text='When I" + "\u2019" + "m Away']", testCase, 5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : When I'm Away text displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule : When I'm Away text not displayed on schedule screen");
			}

			if (schl.isUseMyAwaySettingsTextVisible(5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule :Use My Home Settings option not displayed on schedule screen");
			}
		} else {
			if (schl.isGeofenceHomeElementVisible(5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : Use My Home Settings option displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
			}

			if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("No")) {
				if (schl.isCreateSleepSettingsTextVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Create Sleep Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Create Sleep Settings option not displayed on schedule screen");
				}
			} else {
				if (schl.isGeofenceSleepElementVisible(5)) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Displayed Schedule : Use My Sleep Settings option displayed on schedule screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Displayed Schedule : Use My Sleep Settings option not displayed on schedule screen");
				}
			}
			if (schl.isGeofenceAwayElementVisible(5)) {
				Keyword.ReportStep_Pass(testCase,
						"Verify Displayed Schedule : Use My Away Settings option displayed on schedule screen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Verify Displayed Schedule : Use My Home Settings option not displayed on schedule screen");
			}
		}

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (schl.isUseMyHomeSettingsTextVisible(5)) {
				if (!schl.clickOnUseMyHomeSettingsButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Use My Home Settings option not displayed on schedule screen");
			}
		} else {
			if (schl.isGeofenceHomeElementVisible(5)) {
				if (!schl.clickOnGeofenceHomeButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Use My Home Settings option not displayed on schedule screen");
			}
		}

		inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_HOME);
		Keyword.ReportStep_Pass(testCase, " ");
		Keyword.ReportStep_Pass(testCase,
				"*************** Setting maximum and minimum set points for Home ***************");
		flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
		flag = flag & schl.clickOnSaveButton();
		Keyword.ReportStep_Pass(testCase,
				"*************** Completed setting maximum and minimum set points for Home ***************");

		if (inputs.getInputValue(InputVariables.SET_GEOFENCE_SLEEP_TIMER).equalsIgnoreCase("Yes")) {
			inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_SLEEP);
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (schl.isUseMySleepSettingsTextVisible(5)) {
					if (!schl.clickOnUseMySleepSettingsButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Use My Sleep Settings option not displayed on schedule screen");
				}
			} else {
				if (schl.isGeofenceSleepElementVisible(5)) {
					if (!schl.clickOnGeofenceSleepButton()) {
						flag = false;
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Use My Sleep Settings option not displayed on schedule screen");
				}
			}
			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting maximum and minimum set points for Sleep ***************");
			flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
			flag = flag & schl.clickOnSaveButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting maximum and minimum set points for Sleep ***************");
		}

		inputs.setInputValue(InputVariables.GEOFENCE_PERIOD, InputVariables.GEOFENCE_AWAY);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (schl.isUseMyAwaySettingsTextVisible(5)) {
				if (!MobileUtils.clickOnElement(testCase, "XPATH", "//*[@text='Use My Away Settings']")) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Use My Away Settings option not displayed on schedule screen");
			}
		} else {
			if (schl.isGeofenceAwayElementVisible(5)) {
				if (!schl.clickOnGeofenceAwayButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Use My Away Settings option not displayed on schedule screen");
			}
		}
		Keyword.ReportStep_Pass(testCase, " ");
		Keyword.ReportStep_Pass(testCase,
				"*************** Setting maximum and minimum set points for Away ***************");
		flag = flag & JasperSchedulingUtils.setGeofenceSchedulePeriodSetPoints(testCase, inputs, null, true);
		flag = flag & schl.clickOnSaveButton();
		Keyword.ReportStep_Pass(testCase,
				"*************** Completed setting maximum and minimum set points for Away ***************");

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (schl.isBackButtonVisible(5)) {
				if (!schl.clickOnBackButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
			}
		} else {
			if (schl.isCloseButtonVisible(5)) {
				if (!schl.clickOnCloseButton()) {
					flag = false;
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate Back button");
			}
		}
		return flag;
	}

	public static boolean editTimeBasedScheduleWithMinMaxValues(TestCases testCase, TestCaseInputs inputs,
			String periodName) {
		boolean flag = true;
		String[] scheduleDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		List<WebElement> scheduleDayHeaders = null;
		 SchedulingScreen schl = new SchedulingScreen(testCase);
		int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		CustomDriver driver = testCase.getMobileDriver();
		Dimension dimension = driver.manage().window().getSize();
		int height = dimension.getHeight();
		int width = dimension.getWidth();
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

			if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
				testCase.getMobileDriver().scrollToExact(periodName.split("_")[1]);
			}

			while (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + periodName + "']", testCase, 5)) {
				touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
				touchAction.perform();
			}
			if (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + periodName + "']", testCase, 5)) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to locate the period: " + periodName);
			} else {
				WebElement period = MobileUtils.getMobElement(testCase, "XPATH",
						"//*[@content-desc='" + periodName + "']");
				if (period != null) {
					if (!MobileUtils.clickOnElement(testCase, "XPATH", "//*[@content-desc='" + periodName + "']")) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected period: " + periodName);

						if (periodName.contains("Wake")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_WAKE);
						} else if (periodName.contains("Away")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_AWAY);
						} else if (periodName.contains("Home")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_HOME);
						} else if (periodName.contains("Sleep")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_SLEEP);
						}

						if (periodName.contains("1")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_1);
						} else if (periodName.contains("2")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_2);
						} else if (periodName.contains("3")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_3);
						} else if (periodName.contains("4")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_4);
						}

						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase,
								"*************** Setting maximum and minimum set points ***************");
						flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & schl.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points ***************");
						inputs.setInputValue(InputVariables.SCHEDULE_PERIOD_EDITED, periodName);
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA + 1, periodName);
					}
				}
			}
		} else {
			desiredDayIndex = Arrays.asList(scheduleDays).indexOf(periodName.split("_")[0]);
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
				scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
				lesserDayIndex = Arrays.asList(scheduleDays).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
				greaterDayIndex = Arrays.asList(scheduleDays)
						.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
			}
			int i = 0;
			while ((!MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + periodName + "']", testCase, 5))
					&& i < 10) {
				if (desiredDayIndex > greaterDayIndex) {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					i++;
				} else if (desiredDayIndex < lesserDayIndex) {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
					i++;
				} else {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					i++;
				}
			}
			if (!MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + periodName + "']", testCase, 5)) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to locate the period: " + periodName);
			} else {
				WebElement period = testCase.getMobileDriver().findElement(By.name(periodName));
				if (period != null) {
					if (!MobileUtils.clickOnElement(testCase, "XPATH",
							"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + periodName + "']")) {
						flag = false;
					} else {
						Keyword.ReportStep_Pass(testCase, "Selected period: " + periodName);

						if (periodName.contains("Wake")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_WAKE);
						} else if (periodName.contains("Away")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_AWAY);
						} else if (periodName.contains("Home")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_HOME);
						} else if (periodName.contains("Sleep")) {
							inputs.setInputValue(InputVariables.PERIOD_NAME_NA, InputVariables.EVERYDAY_SLEEP);
						}

						if (periodName.contains("1")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_1);
						} else if (periodName.contains("2")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_2);
						} else if (periodName.contains("3")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_3);
						} else if (periodName.contains("4")) {
							inputs.setInputValue(InputVariables.PERIOD_NUMBER_EMEA, InputVariables.EVERYDAY_4);
						}

						Keyword.ReportStep_Pass(testCase, " ");
						Keyword.ReportStep_Pass(testCase,
								"*************** Setting maximum and minimum set points ***************");
						flag = flag & JasperSchedulingUtils.setTimeSchedulePeriodSetPoints(testCase, inputs, null, true);
						flag = flag & schl.clickOnSaveButton();
						Keyword.ReportStep_Pass(testCase,
								"*************** Completed setting maximum and minimum set points ***************");
						inputs.setInputValue(InputVariables.SCHEDULE_PERIOD_EDITED, periodName);
						inputs.setInputValue(InputVariables.PERIOD_NAME_NA + 1, periodName);
					}
				}
			}
		}

		return flag;
	}

	public static boolean editTimeBasedScheduleByDeletingPeriods(TestCases testCase, TestCaseInputs inputs,
			String schedulePeriod, int periodCounterToBeDeleted) {
		boolean flag = true;
		WebElement period = null;
		String[] scheduleDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		List<WebElement> scheduleDayHeaders = null;
		int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

		CustomDriver driver = testCase.getMobileDriver();
		Dimension dimension = driver.manage().window().getSize();
		int height = dimension.getHeight();
		int width = dimension.getWidth();
		TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriod + "']", testCase, 5)) {
				testCase.getMobileDriver().scrollToExact(schedulePeriod.split("_")[1]);
				while (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriod + "']", testCase,
						5)) {
					touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
					touchAction.perform();
				}
			}
			period = testCase.getMobileDriver().findElement(By.xpath("//*[@content-desc='" + schedulePeriod + "']"));
		} else {
			desiredDayIndex = Arrays.asList(scheduleDays).indexOf(schedulePeriod.split("_")[0]);
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
				scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
				lesserDayIndex = Arrays.asList(scheduleDays).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
				greaterDayIndex = Arrays.asList(scheduleDays)
						.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
			}
			int i = 0;
			while ((!MobileUtils.isMobElementExists("XPATH",
					"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + schedulePeriod + "']", testCase, 5))
					&& i < 10) {
				if (desiredDayIndex > greaterDayIndex) {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					i++;
				} else if (desiredDayIndex < lesserDayIndex) {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
					i++;
				} else {
					touchAction.press(10, (int) (dimension.getHeight() * .5))
							.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
					i++;
				}
			}
			period = testCase.getMobileDriver().findElement(By.name(schedulePeriod));
		}
		inputs.setInputValue(InputVariables.PERIOD_NAME_NA + periodCounterToBeDeleted, schedulePeriod);

		Keyword.ReportStep_Pass(testCase, " ");
		Keyword.ReportStep_Pass(testCase, "*************** Deleting period- " + schedulePeriod + " ***************");
		try {
			period.click();
			Keyword.ReportStep_Pass(testCase, "Selected period-" + schedulePeriod);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to select the period-" + period);
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "PeriodDeleteIcon", 5)) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "PeriodDeleteIcon")) {
				flag = false;
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Failed to locate the Period delete icon");
		}
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ConfirmDeleteButton", 5)) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "ConfirmDeleteButton")) {
				flag = false;
			} else {
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleOptionsButton", 5)
						|| MobileUtils.isMobElementExists(fieldObjects, testCase, "CreateScheduleButton", 5)) {
					Keyword.ReportStep_Pass(testCase,
							"Schedule period is deleted when clicked on Delete during Delete operation");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Schedule period is not deleted when clicked on Delete during Delete operation");
				}
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Period Confirm Delete button not found");
		}

		Keyword.ReportStep_Pass(testCase,
				"*************** Completed deleting period- " + schedulePeriod + " ***************");
		if (schedulePeriod.contains("Wake")) {
			inputs.setInputValue(InputVariables.EVERYDAY_WAKE_TIME, "Tap to set");
		} else if (schedulePeriod.contains("Away")) {
			inputs.setInputValue(InputVariables.EVERYDAY_AWAY_TIME, "Tap to set");
		} else if (schedulePeriod.contains("Home")) {
			inputs.setInputValue(InputVariables.EVERYDAY_HOME_TIME, "Tap to set");
		} else if (schedulePeriod.contains("Sleep")) {
			inputs.setInputValue(InputVariables.EVERYDAY_SLEEP_TIME, "Tap to set");
		}

		return flag;
	}

	public static boolean editTimeBasedSchedule(TestCases testCase, TestCaseInputs inputs, String schedulePeriod,
			int periodCounterToBeDeleted) {
		boolean flag = true;
		try {
			 SchedulingScreen schl = new SchedulingScreen(testCase);
			WebElement period = null;
			String[] scheduleDays = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			List<WebElement> scheduleDayHeaders = null;
			int desiredDayIndex = 0, lesserDayIndex = 0, greaterDayIndex = 0;
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");

			CustomDriver driver = testCase.getMobileDriver();
			Dimension dimension = driver.manage().window().getSize();
			int height = dimension.getHeight();
			int width = dimension.getWidth();
			TouchAction touchAction = new TouchAction(testCase.getMobileDriver());

			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			List<String> allowedModes = statInfo.getAllowedModes();

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriod + "']", testCase, 5)) {
					testCase.getMobileDriver().scrollToExact(schedulePeriod.split("_")[1]);
					while (!MobileUtils.isMobElementExists("XPATH", "//*[@content-desc='" + schedulePeriod + "']", testCase,
							5)) {
						touchAction.press(width / 2, height / 2).waitAction(MobileUtils.getDuration(2000)).moveTo(width / 2, 82).release();
						touchAction.perform();
					}
				}
				period = testCase.getMobileDriver().findElement(By.xpath("//*[@content-desc='" + schedulePeriod + "']"));
			} else {
				desiredDayIndex = Arrays.asList(scheduleDays).indexOf(schedulePeriod.split("_")[0]);
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ScheduleDayHeader", 5)) {
					scheduleDayHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ScheduleDayHeader");
					lesserDayIndex = Arrays.asList(scheduleDays).indexOf(scheduleDayHeaders.get(0).getAttribute("value"));
					greaterDayIndex = Arrays.asList(scheduleDays)
							.indexOf(scheduleDayHeaders.get(scheduleDayHeaders.size() - 1).getAttribute("value"));
				}
				int i = 0;
				while ((!MobileUtils.isMobElementExists("XPATH",
						"//XCUIElementTypeCell/XCUIElementTypeStaticText[@name='" + schedulePeriod + "']", testCase, 5))
						&& i < 10) {
					if (desiredDayIndex > greaterDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					} else if (desiredDayIndex < lesserDayIndex) {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * .4)).release().perform();
						i++;
					} else {
						touchAction.press(10, (int) (dimension.getHeight() * .5))
						.moveTo(0, (int) (dimension.getHeight() * -.4)).release().perform();
						i++;
					}
				}
				period = testCase.getMobileDriver().findElement(By.name(schedulePeriod));
				String cp = schedulePeriod + "_CoolTemperature";
				String hp = schedulePeriod + "_HeatTemperature";
				WebElement elemTime = testCase.getMobileDriver().findElement(By.name(schedulePeriod + "_Time"));
				System.out.println(elemTime.getAttribute("value"));
				if (allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					WebElement elemCP = testCase.getMobileDriver().findElement(By.name(cp));
					WebElement elemHP = testCase.getMobileDriver().findElement(By.name(hp));
					System.out.println(elemCP.getAttribute("value"));
					System.out.println(elemHP.getAttribute("value"));
				} else if (allowedModes.contains("Cool") && !allowedModes.contains("Heat")) {
					WebElement elemCP = testCase.getMobileDriver().findElement(By.name(cp));
					System.out.println(elemCP.getAttribute("value"));
				} else if (!allowedModes.contains("Cool") && allowedModes.contains("Heat")) {
					WebElement elemHP = testCase.getMobileDriver().findElement(By.name(hp));
					System.out.println(elemHP.getAttribute("value"));
				}
			}
			String periodName = "";
			HashMap<String, String> periodTimeandSetPoint = new HashMap<String, String>();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				periodTimeandSetPoint.put("periodName", schedulePeriod.split("_")[0]);
				periodName = schedulePeriod.split("_")[0];
			} else {
				periodTimeandSetPoint.put("periodName", schedulePeriod.split("_")[1]);
				periodName = schedulePeriod.split("_")[1];
			}

			if (periodName.equalsIgnoreCase("Wake") || periodName.equalsIgnoreCase("1")) {
				if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")
						|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
					periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_WAKE_TIME));
				} else {
					periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
					periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
				}
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_WAKE_HEAT_SETPOINT));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_WAKE_COOL_SETPOINT));
				}
			} else if (periodName.equalsIgnoreCase("Away") || periodName.equalsIgnoreCase("2")) {
				if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")
						|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
					periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_AWAY_TIME));
				} else {
					periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_2_TIME));
					periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
				}
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT));
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_AWAY_HEAT_SETPOINT));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_AWAY_COOL_SETPOINT));
				}
			} else if (periodName.equalsIgnoreCase("Home") || periodName.equalsIgnoreCase("3")) {
				if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")
						|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
					periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_HOME_TIME));
				} else {
					periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_3_TIME));
					periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
				}
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT));
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_HOME_HEAT_SETPOINT));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_HOME_COOL_SETPOINT));
				}
			} else if (periodName.equalsIgnoreCase("Sleep") || periodName.equalsIgnoreCase("4")) {
				if (statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")
						|| statInfo.getJasperDeviceType().equalsIgnoreCase("NA")) {
					periodTimeandSetPoint.put("Time", inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_TIME));
				} else {
					periodTimeandSetPoint.put("StartTime", inputs.getInputValue(InputVariables.EVERYDAY_4_TIME));
					periodTimeandSetPoint.put("EndTime", inputs.getInputValue(InputVariables.EVERYDAY_1_TIME));
				}
				if (allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT));
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
				} else if (allowedModes.contains("Heat") && !allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("HeatSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_HEAT_SETPOINT));
				} else if (!allowedModes.contains("Heat") && allowedModes.contains("Cool")) {
					periodTimeandSetPoint.put("CoolSetPoint",
							inputs.getInputValue(InputVariables.EVERYDAY_SLEEP_COOL_SETPOINT));
				}
			}
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA + periodCounterToBeDeleted, schedulePeriod);
			inputs.setInputValue(InputVariables.SCHEDULE_PERIOD_EDITED, schedulePeriod);

			Keyword.ReportStep_Pass(testCase, " ");
			Keyword.ReportStep_Pass(testCase,
					"*************** Setting time and set points for " + schedulePeriod + " ***************");
			flag = flag
					& JasperSchedulingUtils.setTimeSchedulePeriodTimeAndSetPoints(testCase, inputs, periodTimeandSetPoint, period);
			flag = flag & schl.clickOnSaveButton();
			Keyword.ReportStep_Pass(testCase,
					"*************** Completed setting time and set points for " + schedulePeriod + " ***************");

		}catch (Exception e){
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

}
