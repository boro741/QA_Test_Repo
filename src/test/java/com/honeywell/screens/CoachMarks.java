package com.honeywell.screens;


import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;

public class CoachMarks extends MobileScreens {

	private static final String screenName = "CoachMark";

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
	
	public static final String[] NASolutionCardCoachMarkHeaders = new String[] { "Indoor Temperature Reading", "Temp Stepper",
			"Mode", "Fan","Schedule" };
	public static final String[] NASolutionCardCoachMarkDescriptions = new String[] {
			"Displays current temperature in your home",
			"Change the temperature that your thermostat is set to.",
			"Change thermostat mode between Heat, Cool, Auto and Off",
			"Change the fan mode your system is using",
			"View, edit or create a new schedule for your thermostat"};
	
	public static final String[] EMEASolutionCardCoachMarkHeaders = new String[] { "Indoor Temperature Reading", "Temp Stepper",
			"Mode","Schedule" };
	public static final String[] EMEASolutionCardCoachMarkDescriptions = new String[] {
			"Displays current temperature in your home",
			"Change the temperature that your thermostat is set to.",
			"Change thermostat mode between Heat, Cool, Auto and Off",
			"View, edit or create a new schedule for your thermostat"};

	

	public static final String DAS = "das";
	public static final String DASCAMERA = "dascamera";
	public static final String WLD = "wld";
	public static final String THERMOSTAT = "thermostat";
	public static final String THERMOSTATNA = "thermostatna";
	public static final String THERMOSTATEMEA = "thermostatemea";

	public CoachMarks(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isGotitButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "GotItButton", timeOut, false);
	}

	public boolean clickOnGotitButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "GotItButton");
	}
	
	public boolean isNextButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "NextButton", timeOut, false);
	}

	public boolean clickOnNextButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "NextButton");
	}
	
	public boolean isDoneButtonVisible(int timeOut) {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "DoneButton", timeOut, false);
	}

	public boolean clickOnDoneButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
	}

	public String getCoachMarkHeaderText() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "CoachMarkHeader").getText();
	}

	public String getCoachMarkDescription() {
		return MobileUtils.getMobElement(objectDefinition, testCase, "CoachMarkDescription").getText();
	}
	public boolean verifyDashboardCoachMarks(String deviceType) {
		boolean flag = true;
		switch (deviceType) {
		case CoachMarks.DAS: {
			for (int i = 0; i < CoachMarks.DASDashboardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText().equalsIgnoreCase(CoachMarks.DASDashboardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase,
							"DAS Dashboard Coach Mark " + (i + 1) + " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "DAS Dashboard Coach Mark Header  "
							+ (i + 1) + " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription().equalsIgnoreCase(CoachMarks.DASDashboardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Dashboard Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Dashboard Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
			}

			break;
		}
		case CoachMarks.THERMOSTAT: {
			for (int i = 0; i < CoachMarks.ThermostatDashboardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText().equalsIgnoreCase(CoachMarks.ThermostatDashboardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase,
							"Thermostat Dashboard Coach Mark " + (i + 1) + " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Thermostat Dashboard Coach Mark Header  "
							+ (i + 1) + " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription().equalsIgnoreCase(CoachMarks.ThermostatDashboardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "Thermostat Dashboard Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Thermostat Dashboard Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
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

//	@SuppressWarnings("unused")
	public boolean verifySolutionCardCoachMarks(String deviceType) {
		boolean flag = true;
		switch (deviceType) {
		case CoachMarks.DAS: {
			for (int i = 0; i < CoachMarks.DASSecuritySolutionCardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarks.DASSecuritySolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarks.DASSecuritySolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
			}

			break;
		}
		case CoachMarks.DASCAMERA: {
			for (int i = 0; i < CoachMarks.DASCameraSolutionCardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarks.DASCameraSolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Camera Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Camera Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarks.DASCameraSolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "DAS Camera Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"DAS Camera Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
			}
			break;
		}
		case CoachMarks.THERMOSTATNA: {
			for (int i = 0; i < CoachMarks.NASolutionCardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarks.NASolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "NA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NA thermostat Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarks.NASolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "NA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"NA thermostat Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
			}
			break;
		}
		
		case CoachMarks.THERMOSTATEMEA: {
			for (int i = 0; i < CoachMarks.EMEASolutionCardCoachMarkHeaders.length; i++) {
				if (this.getCoachMarkHeaderText()
						.equalsIgnoreCase(CoachMarks.EMEASolutionCardCoachMarkHeaders[i])) {
					Keyword.ReportStep_Pass(testCase, "EMEA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Header is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"EMEA thermostat Solution Card Coach Mark Header  " + (i + 1)
									+ " is not correctly displayed on the dashboard");
				}
				if (this.getCoachMarkDescription()
						.equalsIgnoreCase(CoachMarks.EMEASolutionCardCoachMarkDescriptions[i])) {
					Keyword.ReportStep_Pass(testCase, "EMEA thermostat Solution Card Coach Mark " + (i + 1)
							+ " Description is correctly displayed on the dashboard");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"EMEA thermostat Solution Card Coach Mark Description " + (i + 1)
									+ "  is not correctly displayed on the dashboard");
				}
				flag = flag & this.clickOnGotitButton();
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
	
}
