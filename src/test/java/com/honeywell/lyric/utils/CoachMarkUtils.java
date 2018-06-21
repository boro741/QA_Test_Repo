package com.honeywell.lyric.utils;

import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CoachMarks;

public class CoachMarkUtils {

	public static final String[] DASDashboardCoachMarkHeaders = new String[] { "Access More Information",
			"Quick Controls" };
	public static final String[] DASDashboardCoachMarkDescriptions = new String[] {
			"Access controls and options for any device by tapping on the device name.",
			"Quickly change the security mode by tapping one of the options here." };

	public static final String[] DASSecuritySolutionCardCoachMarkHeaders = new String[] { "Home Mode", "Away Mode",
			"Night Mode", "Off Mode" };
	public static final String[] DASSecuritySolutionCardCoachMarkDescriptions = new String[] {
			"In Home mode, the system will not trigger an alarm, but you will hear chimes when door and window sensors open.",
			"In Away mode, the system will be monitoring your home and will trigger an alarm.",
			"In Night mode, only door and window sensors will trigger an alarm.",
			"In Off mode, all security and chime features are disabled. However you can still access the camera live stream and features like Alexa." };

	public static final String[] DASCameraSolutionCardCoachMarkHeaders = new String[] { "Attention" };
	public static final String[] DASCameraSolutionCardCoachMarkDescriptions = new String[] {
			"Use Attention when you see a intruder or something suspicious in your home. This will sound in home siren to deter intruders" };

	public static final String[] ThermostatDashboardCoachMarkHeaders = new String[] { "Access More Information",
			"Quick Controls" };
	public static final String[] ThermostatDashboardCoachMarkDescriptions = new String[] {
			"Access controls and options for any device by tapping on the device name.",
			"Quickly change the temperature that your thermostat is set to" };

	public static final String[] NASolutionCardCoachMarkHeaders = new String[] { "Indoor Temperature Reading",
			"Temp Stepper", "Mode", "Fan", "Schedule" };
	public static final String[] NASolutionCardCoachMarkDescriptions = new String[] {
			"Displays current temperature in your home", "Change the temperature that your thermostat is set to.",
			"Change thermostat mode between Heat, Cool, Auto and Off", "Change the fan mode your system is using",
			"View, edit or create a new schedule for your thermostat" };

	public static final String[] EMEASolutionCardCoachMarkHeaders = new String[] { "Indoor Temperature Reading",
			"Temp Stepper", "Mode", "Schedule" };
	public static final String[] EMEASolutionCardCoachMarkDescriptions = new String[] {
			"Displays current temperature in your home", "Change the temperature that your thermostat is set to.",
			"Change thermostat mode between Heat, Cool, Auto and Off",
			"View, edit or create a new schedule for your thermostat" };

	public static final String DAS = "das";
	public static final String DASCAMERA = "dascamera";
	public static final String WLD = "wld";
	public static final String THERMOSTAT = "thermostat";
	public static final String THERMOSTATNA = "thermostatna";
	public static final String THERMOSTATEMEA = "thermostatemea";

	public static boolean verifyDashboardCoachMarks(TestCases testCase, String deviceType) {
		boolean flag = true;
		CoachMarks cm = new CoachMarks(testCase);
		switch (deviceType) {
		case CoachMarkUtils.DAS: {
			for (int i = 0; i < CoachMarkUtils.DASDashboardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText().equalsIgnoreCase(CoachMarkUtils.DASDashboardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase,
							"DAS Dashboard Coach Mark " + (i + 1) + " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "DAS Dashboard Coach Mark Header  "
							+ (i + 1) + " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.DASDashboardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Dashboard Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Dashboard Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}

			break;
		}
		case CoachMarkUtils.THERMOSTAT: {
			for (int i = 0; i < CoachMarkUtils.ThermostatDashboardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarkUtils.ThermostatDashboardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "Thermostat Dashboard Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Dashboard Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.ThermostatDashboardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "Thermostat Dashboard Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Dashboard Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}

			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Invalid device type passed in the argument");
		}
		}

		return flag;
	}
	
	public static boolean verifySolutionCardCoachMarks(TestCases testCase, String deviceType) {
		boolean flag = true;
		CoachMarks cm = new CoachMarks(testCase);
		switch (deviceType) {
		case CoachMarkUtils.DAS: {
			for (int i = 0; i < CoachMarkUtils.DASSecuritySolutionCardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarkUtils.DASSecuritySolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.DASSecuritySolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}

			break;
		}
		case CoachMarkUtils.DASCAMERA: {
			for (int i = 0; i < CoachMarkUtils.DASCameraSolutionCardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarkUtils.DASCameraSolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Camera Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Camera Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.DASCameraSolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Camera Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Camera Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}
			break;
		}
		case CoachMarkUtils.THERMOSTATNA: {
			for (int i = 0; i < CoachMarkUtils.NASolutionCardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText().equalsIgnoreCase(CoachMarkUtils.NASolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "NA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NA thermostat Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.NASolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "NA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NA thermostat Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}
			break;
		}

		case CoachMarkUtils.THERMOSTATEMEA: {
			for (int i = 0; i < CoachMarkUtils.EMEASolutionCardCoachMarkHeaders.length; i++) {
				if (cm.getCoachMarkHeaderText().equalsIgnoreCase(CoachMarkUtils.EMEASolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "EMEA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"EMEA thermostat Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (cm.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarkUtils.EMEASolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "EMEA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"EMEA thermostat Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & cm.clickOnGotitButton();
			}
			break;
		}

		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Invalid device type passed in the argument");
		}
		}

		return flag;
	}

	public static boolean closeCoachMarks(TestCases testCase) {
		boolean flag = true;
		CoachMarks cm = new CoachMarks(testCase);
		int counter = 0;
		/*if (cm.isGotitButtonVisible(5)) {
			while (cm.isGotitButtonVisible(1) && counter < 5) {
				flag = flag & cm.clickOnGotitButton();
				counter++;
			}
		}*/
		if (cm.isNextButtonVisible(1)) {
			while (cm.isNextButtonVisible(1) && counter < 5) {
				flag = flag & cm.clickOnNextButton();
				if (cm.isDoneButtonVisible(1)) {
					flag = flag & cm.clickOnDoneButton();
				}
				counter++;
			}
		}
		return flag;
	}
	
}
