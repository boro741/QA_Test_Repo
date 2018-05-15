package com.honeywell.keywords.lyric.das.activityHistory;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;

import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class VerifyAlerts extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyAlerts(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user receives a (.*) alert$")
	public boolean keywordSteps() throws KeywordException {
		String expectedAlert = "";
		String actualUser="";
		if(!inputs.getInputValue("VERIFY_ACTIVITYLOGS").equalsIgnoreCase("NO")){
			if(exampleData.get(0).contains("by invited user")){
				actualUser=inputs.getInputValue("USERID");
				inputs.setInputValue("USERID",inputs.getInputValue("INVITEDUSER"));
			}
			try {
				HashMap<String, MobileObject> fieldObjects = MobileUtils
						.loadObjectFile(testCase, "HomeScreen");

				switch (exampleData.get(0).toUpperCase()) {

				// opened activities
				case "DOOR OPENED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Opened";
					break;
				}
				case "WINDOW OPENED AT HOME MODE": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Opened";
					break;
				}

				// closed activities
				case "DOOR CLOSED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Closed";
					break;
				}

				case "WINDOW CLOSED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Closed";
					break;
				}

				// troubled activities
				case "DOOR SENSOR TROUBLE": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " is malfunctioning";
					break;
				}
				case "WINDOW SENSOR TROUBLE": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " is malfunctioning";
					break;
				}

				// trouble cleared activities
				case "DOOR SENSOR TROUBLE CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " is functioning normally again";
					break;
				}
				case "WINDOW SENSOR TROUBLE CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " is functioning normally again";
					break;
				}

				// Alarm activities
				case "ALARM": {
					expectedAlert = "Alarm";
					break;
				}

				case "DOOR SENSOR ALARM": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Alarm";
					break;
				}

				case "WINDOW SENSOR ALARM": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Alarm";
					break;
				}
				case "ALARM DISMISSED BY INVITED USER":
				case "ALARM DISMISSED": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedAlert = "Alarm Dismissed by "
							+ locInfo.getUserFirstName();
					break;
				}
				/* Feature removed
			case "DOOR SENSOR ALARM DISMISSED": {
				expectedAlert = inputs
						.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
						+ " Alarm dismissed";
				break;
			}
			case "WINDOW SENSOR ALARM DISMISSED": {
				expectedAlert = inputs
						.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
						+ " Alarm dismissed";
				break;
			}
				 */

				// bypass activities
				case "DOOR SENSOR BYPASS": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Bypass";
					break;
				}
				case "WINDOW SENSOR BYPASS": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Bypass";
					break;
				}

				// bypass cleared activities
				case "DOOR SENSOR BYPASS CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Bypass Cleared";
					break;
				}
				case "WINDOW SENSOR BYPASS CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Bypass Cleared";
					break;
				}

				// tampered activities
				case "DOOR SENSOR TAMPER": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Tampered";
					break;
				}
				case "WINDOW SENSOR TAMPER": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Tampered";
					break;
				}

				// tamper cleared activities
				case "DOOR SENSOR TAMPER CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
							+ " Tamper Cleared";
					break;
				}
				case "WINDOW SENSOR TAMPER CLEARED": {
					expectedAlert = inputs
							.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
							+ " Tamper Cleared";
					break;
				}

				// mode activities
				case "SWITCHED TO HOME BY INVITED USER":
				case "SWITCHED TO HOME": {
					LocationInformation locInfo = new LocationInformation(testCase,
							inputs);
					expectedAlert = "Switched to Home by " + locInfo.getUserFirstName();
					break;
				}
				case "SWITCHED TO AWAY BY INVITED USER":
				case "SWITCHED TO AWAY": {
					LocationInformation locInfo = new LocationInformation(testCase,
							inputs);
					expectedAlert = "Switched to Away by " + locInfo.getUserFirstName();
					break;
				}
				case "SWITCHED TO NIGHT BY INVITED USER":
				case "SWITCHED TO NIGHT": {
					LocationInformation locInfo = new LocationInformation(testCase,
							inputs);
					expectedAlert ="Switched to Night by " + locInfo.getUserFirstName();
					break;
				}
				case "SWITCHED TO OFF BY INVITED USER":
				case "SWITCHED TO OFF": {
					LocationInformation locInfo = new LocationInformation(testCase,
							inputs);
					expectedAlert = "Switched to Off by " + locInfo.getUserFirstName();
					break;
				}


				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + exampleData.get(0));
					return flag;
				}
				}

				if (MobileUtils.isMobElementExists(fieldObjects, testCase,
						"AlertsTitle", 5)) {
					Keyword.ReportStep_Pass(testCase,
							"User is already on the alerts screen");
				} else {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase,
							"AlertsIcon", 3)) {
						flag = flag
								& MobileUtils.clickOnElement(fieldObjects,
										testCase, "AlertsIcon");
					}
				}
				String alerts[][] = LyricUtils.getAllAlerts(testCase);
				// System.out.println(Arrays.deepToString(alerts));
				boolean alertFound = false;
				for (int i = 0; i < alerts.length; i++) {
					try {
						if (expectedAlert.equalsIgnoreCase(alerts[i][0])) {
							alertFound = true;
							break;
						}
					} catch (NoSuchElementException e) {
						break;
					} catch (NullPointerException e) {
						break;
					} catch (Exception e) {
						break;
					}
				}
				if (alertFound) {
					Keyword.ReportStep_Pass(testCase, expectedAlert
							+ " found on the alerts screen");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(
							testCase,
							FailType.FUNCTIONAL_FAILURE,
							expectedAlert
							+ " not found on the alerts screen. Expected Alert: "
							+ expectedAlert);
				}
			} catch (Exception e) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured : " + e.getMessage());
			}
			if(exampleData.get(0).contains("by invited user")){
				inputs.setInputValue("USERID",actualUser);
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
