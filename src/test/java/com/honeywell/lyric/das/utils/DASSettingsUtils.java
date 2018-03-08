package com.honeywell.lyric.das.utils;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.DASInputVariables;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.SecondaryCardSettings;

public class DASSettingsUtils {

	public static boolean verifyDeleteDASConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteDASDevicePopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete DAS Confirmation Pop Up Title is correctly displayed");
			String message, locator = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				locator = "xpath";
				message = "//android.widget.TextView[@text='This will delete your Smart Home Security and all the connected accessories. Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?']";
			} else {
				locator = "name";
				message = "  This will delete your Smart Home Security and all the connected accessories.     Are you sure you want to delete \""
						+ inputs.getInputValue("LOCATION1_CAMERA1_NAME") + "\"?";
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

	public static boolean verifyDeleteSensorConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isDeleteSensorPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up Title is correctly displayed");
			if (inputs.getInputValue(DASInputVariables.SENSORTYPE).equals(DASInputVariables.MOTIONSENSOR)) {
				flag = flag & bs.isMotionSensorDeletePopUpMessageVisible();
			} else {
				flag = flag & bs.isAccessSensorDeletePopUpMessageVisible();
			}

			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Delete Sensor Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Sensor Confirmation Pop Up message not correctly displayed.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Sensor Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteKeyfobConfirmationPopUp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		if (bs.isKeyfobPopUpTitleVisible()) {
			Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up Title is correctly displayed");
			if (bs.isKeyfobDeletePopUpMessageVisible()) {
				Keyword.ReportStep_Pass(testCase, "Delete Keyfob Confirmation Pop Up message correctly displayed");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Delete Keyfob Confirmation Pop Up message not correctly displayed.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Delete Keyfob Confirmation Pop Up not displayed");
		}
		return flag;
	}

	public static boolean verifyDeleteDASConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
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

	public static boolean verifyDeleteKeyfobConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
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

	public static boolean verifyDeleteSensorConfirmationPopUpIsNotDisplayed(TestCases testCase, TestCaseInputs inputs) {
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
	public static boolean navigateFromDashboardScreenToSecuritySettingsScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		PrimaryCard pc = new PrimaryCard(testCase);
		try {
			flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase, "Security");
			flag = flag & LyricUtils.closeCoachMarks(testCase);
			if(pc.isCogIconVisible())
			{
				flag = flag & pc.clickOnCogIcon();
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToBaseStationConfigurationScreen(TestCases testCase,
			TestCaseInputs inputs) {
		boolean flag = true;
		try {

			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToEntryExitDelayScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			if (bs.isEntryExitDelaySettingsOptionVisible()) {
				flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.ENTRYEXITDELAYSETTINGS);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Unable to find Entry/Exit Delay option on DAS Panel Settings");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToKeyfobScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	public static boolean navigateFromDashboardToSensorsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
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
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
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
				inputs.setInputValue(DASInputVariables.SENSORTYPE, DASInputVariables.ACCESSSENSOR);
			} else {
				sensorName = inputs.getInputValue("LOCATION1_MOTIONSENSOR1_NAME");
				inputs.setInputValue(DASInputVariables.SENSORTYPE, DASInputVariables.MOTIONSENSOR);
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
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			String keyfobName = "";
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.KEYFOB);
			if (!inputs.isInputAvailable("LOCATION1_KEYFOB1_NAME")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"No keyfob names were provided in the Requirement file");
				return flag;
			}
			keyfobName = inputs.getInputValue("LOCATION1_KEYFOB1_NAME");
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

	public static boolean navigateFromDashboardToAmazonAlexaScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.AMAZONALEXA);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}
	
	public static boolean navigateFromDashboardToVideoSettingsScreen(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			flag = flag & DASSettingsUtils.navigateFromDashboardScreenToSecuritySettingsScreen(testCase, inputs);
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			flag = flag & bs.selectOptionFromBaseStationSettings(BaseStationSettingsScreen.VIDEOSETTINGS);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

}
