package com.honeywell.lyric.das.utils;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.CoachMarkUtils;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.GeofenceSettings;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SecuritySolutionCardScreen;
import com.honeywell.screens.ThermostatSettingsScreen;

public class DASSettingsUtils {

	public static boolean verifyDeleteDASConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteDASDevicePopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				/*
				 * message =
				 * "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
				 * + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
				 */
				message = "//android.widget.TextView[@text='This will delete " + "\""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"" + " and all related accessories']";
			} else {
				locator = "name";
				/*
				 * message =
				 * "  This will delete your Smart Home Security and all the connected accessories.     Are you sure you want to delete \""
				 * + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?";
				 */
				message = "  This will delete " + "\"" + inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\""
						+ " and all related accessories.";
			}

			// message =" This will delete your Smart Home Security and all the connected
			// accessories. Are you sure you want to delete &quot;Living Room&quot;?";

			if (MobileUtils.isMobElementExists(locator, message, testCase, 5)) {
				Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete DAS Confirmation Pop Up message not correctly displayed. Expected: '" + message
								+ "'. Displayed : (Refer Image)");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteAccessSensorConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up Title is displayed");
			System.out.println(
					"###########ACCESSSENSORTYPE: " + inputs.getInputValue(DASInputVariables.ACCESSSENSORTYPE));
			System.out.println("###########ACCESSSENSOR: " + DASInputVariables.ACCESSSENSOR);
			if (inputs.getInputValue(DASInputVariables.ACCESSSENSORTYPE).equals(DASInputVariables.ACCESSSENSOR)) {
				flag = flag & bs.isAccessSensorDeletePopUpMessageVisible();
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Access Sensor Confirmation Pop Up is incorrect");
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,
						"Delete Access Sensor Confirmation Pop Up message is correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Access Sensor Confirmation Pop Up message is incorrect");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up is not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteMotionSensorConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up Title is displayed");
			System.out.println(
					"###########MOTIONSENSORTYPE: " + inputs.getInputValue(DASInputVariables.MOTIONSENSORTYPE));
			System.out.println("###########ACCESSSENSOR: " + DASInputVariables.MOTIONSENSOR);
			if (inputs.getInputValue(DASInputVariables.MOTIONSENSORTYPE).equals(DASInputVariables.MOTIONSENSOR)) {
				flag = flag & bs.isMotionSensorDeletePopUpMessageVisible();
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Motion Sensor Confirmation Pop Up is incorrect");
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,
						"Delete Motion Sensor Confirmation Pop Up message is correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Motion Sensor Confirmation Pop Up message is incorrect");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up is not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteKeyfobConfirmationPopUp(TestCases testCase) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isKeyfobPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up Title is correctly displayed");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Keyfob Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteDASConfirmationPopUpIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteDASPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Delete DAS Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteKeyfobConfirmationPopUpIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteKeyfobPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Keyfob Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteSensorConfirmationPopUpIsNotDisplayed(TestCases testCase) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpVisible()) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up displayed");
		} else {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up not displayed");
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Security Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToSecuritySettingsScreen method navigates from
	 * the dashboard to the security screen by clicking on the Global Drawer option
	 * and clicking on the camera name on the secondary card settings
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToSecuritySettingsScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			Thread.sleep(2000);

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardScreenToSecuritySolutionCardScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			Thread.sleep(2000);

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromSecuritySettingsToSecuritySolutionCard(TestCases testCase) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);

		try {
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromSecuritySolutionCardToCameraSolutionCard(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		SecuritySolutionCardScreen securityScreen = new SecuritySolutionCardScreen(testCase);

		try {

			if (securityScreen.isBackButtonVisible()) {
				flag = flag & securityScreen.clickOnBackButton();

				if (inputs.isInputAvailable("LOCATION1_CAMERA1_NAME")) {
					String cameraName = inputs.getInputValue("LOCATION1_CAMERA1_NAME");
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, cameraName);
				} else {
					flag = false;
				}

			} else {
				flag = false;
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean EnableGlobalGeofence(TestCases testCase) {
		boolean flag = true;
		Dashboard d = new Dashboard(testCase);
		SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
		GeofenceSettings geoScreen = new GeofenceSettings(testCase);
		try {
			if (d.isGlobalDrawerButtonVisible()) {
				flag = flag & d.clickOnGlobalDrawerButton();
				flag = flag & sc.selectOptionFromSecondarySettings("Geofence");
			}

			if (!geoScreen.selectOptionFromGeofenceSettings(GeofenceSettings.ENABLEGEOFENCETHISLOCATION)) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not click on global geofence toggle");
			}
			Thread.sleep(3000);

			if (!geoScreen.selectOptionFromGeofenceSettings(GeofenceSettings.ENABLEGEOFENCEALERT)) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not click on global geofence alert toggle");
			}

			Thread.sleep(2000);

			if (geoScreen.isclickOnBackButtonVisible()) {
				flag = flag & geoScreen.clickOnBackButton();
				Thread.sleep(2000);
				if (sc.isclickOnBackButtonVisible()) {
					flag = flag & sc.clickOnBackButton();
				}

			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean DisableGlobalGeofence(TestCases testCase) {
		boolean flag = true;
		Dashboard d = new Dashboard(testCase);
		SecondaryCardSettings sc = new SecondaryCardSettings(testCase);

		GeofenceSettings geoScreen = new GeofenceSettings(testCase);
		try {
			if (d.isGlobalDrawerButtonVisible()) {
				flag = flag & d.clickOnGlobalDrawerButton();
				flag = flag & sc.selectOptionFromSecondarySettings("Geofence");
			}
			Thread.sleep(3000);

			if (!geoScreen.selectOptionFromGeofenceSettings(GeofenceSettings.DISABLEGEOFENCETHISLOCATION)) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Could not click on global geofence toggle");
			}

			Thread.sleep(3000);
			if (geoScreen.isclickOnBackButtonVisible()) {
				flag = flag & geoScreen.clickOnBackButton();

				if (sc.isclickOnBackButtonVisible()) {
					flag = flag & sc.clickOnBackButton();
				}

			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Camera Settings Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToCameraSettingsScreen method navigates from
	 * the dashboard to the Camera Settings screen by clicking on the Global Drawer
	 * option and clicking on the camera name on the secondary card settings
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToCameraSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardScreenToCameraConfigurationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen ac = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();

				// scroll to bottom starts
				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						"Camera Configuration");
				// ends
				//// flag = flag & ac.clickONCameraSetingsOption("Camera Configuration");
				flag &= ac.clickOnCameraConfiguration();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardScreenToCameraConfigurationScreen(TestCases testCase,
			TestCaseInputs inputs, String CameraName) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen ac = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, CameraName);
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
				// scroll to bottom starts
				flag = LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						"Camera Configuration");
				// ends
				//// flag = flag & ac.clickONCameraSetingsOption("Camera Configuration");
				flag &= ac.clickOnCameraConfiguration();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Camera Settings Screen</h1>
	 * 
	 * The navigateFromDashboardScreenToThermostatSettingsScreen method navigates
	 * from the dashboard to the Thermostat Settings screen by clicking on the
	 * camera name in the dashboard
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToThermostatSettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);

		DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
		inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_TEMP_VALUE", deviceInfo.getIndoorTemperature());
		if (inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE") != null) {
			Keyword.ReportStep_Pass(testCase, "Current Thermostat Temperature value is: "
					+ inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE"));
		}
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Manage Alerts Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToManageAlertsScreen method navigates from the
	 * dashboard to the Manage alerts screen by clicking on the device name on the
	 * secondary card settings and tap on Manage Alerts option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToManageAlertsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);

		DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
		inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_TEMP_VALUE", deviceInfo.getIndoorTemperature());
		if (inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE") != null) {
			Keyword.ReportStep_Pass(testCase, "Current Thermostat Temperature value is: "
					+ inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE"));
		}
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isThermostatCurrentHumidityValueVisible(inputs, 20)) {
				Keyword.ReportStep_Pass(testCase, "Humidity Value displayed in Primary card screen is: "
						+ inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDITY_VALUE"));
			}

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isManageAlertsLabelVisible(5)) {
				cs.clickOnManageAlertsLabel();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	
	/**
	 * <h1>Navigate from Dashboard to Camera Manage Alerts Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToCameraManageAlertsScreen method navigates
	 * from the dashboard to the Camera Manage Alerts screen by clicking on the
	 * device name on the secondary card settings and tap on Manage Alerts
	 * option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToCameraManageAlertsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isManageAlertsLabelVisible(5)) {
				cs.clickOnManageAlertsLabel();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Set Filter Reminder Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToSetFilterReminderScreen method navigates
	 * from the dashboard to the Set Filter Reminder screen by clicking on the
	 * device name on the secondary card settings and tap on Set Filter Reminder
	 * option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToSetFilterReminderScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);

		DeviceInformation deviceInfo = new DeviceInformation(testCase, inputs);
		inputs.setInputValueWithoutTarget("CURRENT_THERMOSTAT_TEMP_VALUE", deviceInfo.getIndoorTemperature());
		if (inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE") != null) {
			Keyword.ReportStep_Pass(testCase, "Current Thermostat Temperature value is: "
					+ inputs.getInputValue("CURRENT_THERMOSTAT_TEMP_VALUE"));
		}
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isThermostatCurrentHumidityValueVisible(inputs, 20)) {
				Keyword.ReportStep_Pass(testCase, "Humidity Value displayed in Primary card screen is: "
						+ inputs.getInputValue("CURRENT_THERMOSTAT_HUMIDITY_VALUE"));
			}

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (ts.isSetFilterReminderLabelVisible(5)) {
				ts.clickOnSetFilterReminderLabel();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Humidification Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToThermostatHumidificationScreen method
	 * navigates from the dashboard to the Humidification screen by clicking on the
	 * device name on the secondary card settings and tap on Humidification option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToThermostatHumidificationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.HUMIDIFICATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Dehumidification Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToThermostatDehumidificationScreen method
	 * navigates from the dashboard to the Dehumidification screen by clicking on
	 * the device name on the secondary card settings and tap on Dehumidification
	 * option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToThermostatDehumidificationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.DEHUMIDIFICATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Sleep Brightness Mode Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToSleepBrigthnessModeScreen method navigates
	 * from the dashboard to the Sleep Brightness Mode screen by clicking on the
	 * device name on the secondary card settings and tap on Sleep Brightness Mode
	 * option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToSleepBrigthnessModeScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.SLEEPBRIGHTNESSMODE);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Sound Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToSoundScreen method navigates from the
	 * dashboard to the Sound screen by clicking on the device name on the secondary
	 * card settings and tap on Sound option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToSoundScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.SOUND);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * clicking on the camera name in the dashboard
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToThermostatConfigurationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		try {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			PrimaryCard pc = new PrimaryCard(testCase);
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.THERMOSTATCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Ventilation Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToVentilationScreen method navigates from the
	 * dashboard to the Sound screen by clicking on the device name on the secondary
	 * card settings and tap on Ventilation option
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToVentilationScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);

			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.VENTILATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Camera Motion Detection Settings Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToCameraMotionDetectionSettingsScreen method
	 * navigates from the dashboard to the Camera Motion Detection screen by
	 * clicking on the Global Drawer option and clicking on the camera name on the
	 * secondary card settings
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToCameraMotionDetectionSettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isMotionDetectionLabelVisible(testCase, 5)) {
				cs.clickOnMotionDetectionLabel();
				CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean navigateFromDashboardScreenToCameraSoundDetectionSettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isSoundDetectionLabelVisible(testCase, 5)) {
				cs.clickOnSoundDetectionLabel();
				CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Camera Night Vision Settings Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToCameraNightVisionSettingsScreen method
	 * navigates from the dashboard to the Camera Night Vision screen by clicking on
	 * the Global Drawer option and clicking on the camera name on the secondary
	 * card settings
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToCameraNightVisionSettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isNightVisionLabelVisible(5)) {
				cs.clickOnNightVisionLabel();
				CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Camera Video Quality Settings Screen</h1>
	 * <p>
	 * The navigateFromDashboardScreenToCameraVideoQualitySettingsScreen method
	 * navigates from the dashboard to the Camera Video Quality screen by clicking
	 * on the Global Drawer option and clicking on the camera name on the secondary
	 * card settings
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToCameraVideoQualitySettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
					inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
			;
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			if (cs.isVideoQualityLabelVisible(5)) {
				cs.clickOnVideoQualityLabel();
				CameraUtils.waitForProgressBarToComplete(testCase, "LOADING SPINNER BAR", 3);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToBaseStationConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {

			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToEntryExitDelayScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToKeyfobScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToSensorsScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToSensorSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
			String sensorName = "";
			if (!inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")
					&& !inputs.isInputAvailable("LOCATION1_MOTIONENSOR1_NAME")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"No sensor names were provided in the Requirement file");
				return flag;
			}
			if (inputs.isInputAvailable("LOCATION1_ACCESSSENSOR1_NAME")) {
				sensorName = inputs.getInputValue("LOCATION1_ACCESSSENSOR1_NAME");
				inputs.setInputValue(DASInputVariables.ACCESSSENSORTYPE, DASInputVariables.ACCESSSENSOR);
			} else {
				sensorName = inputs.getInputValue("LOCATION1_MOTIONSENSOR1_NAME");
				inputs.setInputValue(DASInputVariables.MOTIONSENSORTYPE, DASInputVariables.MOTIONSENSOR);
			}
			flag = flag & bs.selectSensorFromSensorList(sensorName);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			inputs.setInputValue(DASInputVariables.SENSORNAME, sensorName);
			inputs.setInputValue(DASInputVariables.SENSORID, devInfo.getDASSensorID(sensorName));
			inputs.setInputValue(DASInputVariables.SENSORRESPONSETYPE, devInfo.getDASSensorResponseType(sensorName));
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToKeyfobSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			String keyfobName = "";
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
			if (flag) {
				System.out.println("Successfully navigated to Keyfob List Settings Screen");
				Keyword.ReportStep_Pass(testCase, "Successfully navigated to Keyfob List Settings Screen");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Did not navigated to Keyfob List Settings Screen");
			}
			if (!inputs.isInputAvailable("LOCATION1_DEVICE1_KEYFOB1")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"No keyfob names were provided in the Requirement file");
				return flag;
			}
			keyfobName = inputs.getInputValue("LOCATION1_DEVICE1_KEYFOB1");
			flag = flag & bs.selectSensorFromSensorList(keyfobName);
			DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
			inputs.setInputValue(DASInputVariables.KEYFOBNAME, keyfobName);
			inputs.setInputValue(DASInputVariables.KEYFOBID, devInfo.getDASKeyfobID(keyfobName));
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToAmazonAlexaScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.AMAZONALEXA);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToVideoSettingsScreen(TestCases testCase) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.VIDEOSETTINGS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromSensorScreenToBaseStationConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromSensoListScreenToBaseStationConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromKeyFobListScreenToBaseStationConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromAmazonAlexaScreenToBaseStationConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromAmazonAlexaScreenToSensorListScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			} else if (bs.isNavBackButtonVisible()) {
				flag = flag & bs.clickOnNavBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromAmazonAlexaScreenToKeyFobListScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			} else if (bs.isNavBackButtonVisible()) {
				flag = flag & bs.clickOnNavBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromKeyFobListScreenToSensorListScreen(TestCases testCase) {
		boolean flag = true;
		try {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isBackButtonVisible()) {
				flag = flag & bs.clickOnBackButton();
			} else if (bs.isNavBackButtonVisible()) {
				flag = flag & bs.clickOnNavBackButton();
			}
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.SENSORS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Activity History to Manage Alerts Screen</h1>
	 * <p>
	 * The navigateFromActivityHistoryScreenToManageAlertsScreen method navigates
	 * from the Activity History to the Manage alerts screen
	 * </p>
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromActivityHistoryScreenToManageAlertsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		if (pc.isBackButtonVisible()) {
			pc.clickOnBackButton();
			if (pc.isBackButtonVisible()) {
				pc.clickOnBackButton();
				try {
					flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
							inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					;
					flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
					if (pc.isCogIconVisible()) {
						flag = flag & pc.clickOnCogIcon();
					}
					if (cs.isManageAlertsLabelVisible(5)) {
						cs.clickOnManageAlertsLabel();
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
			}
		}
		return flag;
	}

	/**
	 * <h1>Navigate from Dashboard to Thermostat Configuration Screen</h1>
	 * 
	 * The navigateFromDashboardScreenToThermostatConfigurationScreen method
	 * navigates from the dashboard to the Thermostat Configuration screen by
	 * clicking on the camera name in the dashboard
	 *
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if navigation is successful. Returns 'false'
	 *         if navigation is not successful.
	 */
	public static boolean navigateFromDashboardScreenToThermostatConfigurationScreen(TestCases testCase) {
		boolean flag = true;
		try {
			ThermostatSettingsScreen ts = new ThermostatSettingsScreen(testCase);
			PrimaryCard pc = new PrimaryCard(testCase);
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Thermostathu");
			flag = flag & CoachMarkUtils.closeCoachMarks(testCase);
			if (pc.isCogIconVisible()) {
				flag = flag & pc.clickOnCogIcon();
			}
			flag = flag & ts.selectOptionFromThermostatSettings(BaseStationSettingsScreen.THERMOSTATCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
}