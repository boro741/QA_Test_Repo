package com.honeywell.keywords.lyric.das.activitylogs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

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
import com.honeywell.account.information.LocationInformation;

public class VerifyActivityLogs extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	private ArrayList<String> exampleData;
	public boolean flag = true;

	public VerifyActivityLogs(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user receives a \"(.*)\" activity log$")
	public boolean keywordSteps() throws KeywordException {
		// if(!inputs.getInputValue("VERIFY_ACTIVITYLOGS").equalsIgnoreCase("NO")){
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ActivityLogs");
			String expectedActivityHeader = "";
			String expectedActivitySubHeader = "";
			List<WebElement> activityLogHeaders = null;
			List<WebElement> activityLogSubHeaders = null;
			List<WebElement> activityLogTime = null;
			String deviceTime = "";
			Date expectedTime = new Date();
			Date displayedTime = new Date();
			String attribute = "";
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
			SimpleDateFormat androidFormat = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat iosFormat = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat iosFormat2 = new SimpleDateFormat("HH:mm");
			String actualUser = "";
			if (exampleData.get(0).contains("by invited user")) {
				actualUser = inputs.getInputValue("USERID");
				inputs.setInputValue("USERID", inputs.getInputValue("INVITEDUSER"));
			}

			// flag = flag & DASNotificationUtils.openActivityLogs(testCase);
			switch (exampleData.get(0).toUpperCase()) {

			// opened activities
			case "DOOR OPENED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Opened";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "DOOR OPENED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Opened";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "DOOR OPENED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Opened";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Opened";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Opened";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Opened";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}

			// closed activities
			case "DOOR CLOSED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Closed";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "DOOR CLOSED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Closed";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "DOOR CLOSED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Closed";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Closed";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Closed";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Closed";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
				break;
			}

			// troubled activities
			case "DOOR SENSOR TROUBLE AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "HOME MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "HOME MODE";
				break;
			}
			case "DOOR SENSOR TROUBLE AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "AWAY MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "AWAY MODE";
				break;
			}
			case "DOOR SENSOR TROUBLE AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "NIGHT MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " is malfunctioning";
				expectedActivitySubHeader = "NIGHT MODE";
				break;
			}

			// trouble cleared activities
			case "DOOR SENSOR TROUBLE CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "HOME MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "HOME MODE";
				break;
			}
			case "DOOR SENSOR TROUBLE CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "AWAY MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "AWAY MODE";
				break;
			}
			case "DOOR SENSOR TROUBLE CLEARED AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "NIGHT MODE";
				break;
			}
			case "WINDOW SENSOR TROUBLE CLEARED AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")
						+ " is functioning normally again";
				expectedActivitySubHeader = "NIGHT MODE";
				break;
			}

			// Alarm activities
			case "ALARM AT AWAY MODE": {
				expectedActivityHeader = "Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ATTENTION SIREN SOUNDED BY INVITED USER":
			case "ATTENTION SIREN SOUNDED BY ACTUAL USER": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = "Attention Siren sounded by " + locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ALARM AT NIGHT MODE": {
				expectedActivityHeader = "Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "DOOR SENSOR ALARM AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "DOOR SENSOR ALARM AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "WINDOW SENSOR ALARM AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "WINDOW SENSOR ALARM AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ALARM DISMISSED BY INVITED USER":
			case "ALARM DISMISSED": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = "Alarm Dismissed";
				expectedActivitySubHeader = locInfo.getUserFirstName();
				deviceTime = inputs.getInputValue("ALARM_DISMISSED_TIME");
				break;
			}

			/*
			 * Feature removed case "DOOR SENSOR ALARM DISMISSED AT HOME MODE":{
			 * expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "HOME MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; } case
			 * "DOOR SENSOR ALARM DISMISSED AT AWAY MODE":{ expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "AWAY MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; } case
			 * "DOOR SENSOR ALARM DISMISSED AT NIGHT MODE":{ expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "NIGHT MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; } case
			 * "WINDOW SENSOR ALARM DISMISSED AT HOME MODE":{ expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "HOME MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; } case
			 * "WINDOW SENSOR ALARM DISMISSED AT AWAY MODE":{ expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "AWAY MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; } case
			 * "WINDOW SENSOR ALARM DISMISSED AT NIGHT MODE":{ expectedActivityHeader =
			 * inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm dismissed";
			 * expectedActivitySubHeader = "NIGHT MODE"; deviceTime =
			 * inputs.getInputValue("ALARM_DISMISSED_TIME"); break; }
			 */

			// bypass activities
			case "DOOR SENSOR BYBASS": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Bypassed";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_BYPASSED_TIME");
				break;
			}
			case "WINDOW SENSOR BYPASS": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Bypassed";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_BYPASSED_TIME");
				break;
			}

			// bypass cleared activities
			case "DOOR SENSOR BYBASS CLEARED": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Bypass cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_BYPASS_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR BYPASS CLEARED": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Bypass cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_BYPASS_CLEARED_TIME");
				break;
			}

			// tampered activities
			case "DOOR SENSOR TAMPERED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tampered";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tampered";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPERED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tampered";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tampered";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPERED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tampered";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tampered";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}

			// tamper cleared activities
			case "DOOR SENSOR TAMPER CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPER CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "AWAY MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPER CLEARED AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT NIGHT": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}

			// mode activities
			case "SWITCHED TO HOME BY INVITED USER VIA THE APP":
			case "SWITCHED TO HOME VIA THE APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Home by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceTime = inputs.getInputValue("HOME_TIME");
				break;
			}

			case "SWITCHED TO HOME VIA KEYFOB": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Home by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "VIA KEYFOB";
				deviceTime = inputs.getInputValue("HOME_TIME");
				break;
			}

			case "SWITCHED TO AWAY BY INVITED USER VIA THE APP":
			case "SWITCHED TO AWAY VIA THE APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Away by "
						+ locInfo.getUserFirstName();
				System.out.println("#########expectedActivityHeader: " + expectedActivityHeader);
				expectedActivitySubHeader = "BY APP";
				deviceTime = inputs.getInputValue("AWAY_TIME");
				System.out.println("#########deviceTime: " + deviceTime);
				break;
			}

			case "SWITCHED TO AWAY VIA KEYFOB": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Away by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "VIA KEYFOB";
				deviceTime = inputs.getInputValue("AWAY_TIME");
				break;
			}
			case "SWITCHED TO NIGHT BY INVITED USER VIA THE APP":
			case "SWITCHED TO NIGHT VIA THE APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Night by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}

			case "SWITCHED TO NIGHT VIA KEYFOB": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Night by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "VIA KEYFOB";
				deviceTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}
			
			case "SWITCHED TO OFF BY INVITED USER VIA THE APP":
			case "SWITCHED TO OFF VIA THE APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " Switched to Off by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}
			
			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + exampleData.get(0));
				return flag;
			}
			}
			if (deviceTime.equals("")) {
				deviceTime = LyricUtils.getDeviceTime(testCase, inputs);
				System.out.println("########deviceTime: " + deviceTime);
				System.out.println("Not found");
			}

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				attribute = "text";
				activityLogHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ActivityLogHeader");

				activityLogSubHeaders = null;
				activityLogSubHeaders = MobileUtils.getMobElements(fieldObjects, testCase, "ActivityLogSubHeader");

				activityLogTime = null;
				activityLogTime = MobileUtils.getMobElements(fieldObjects, testCase, "ActivityLogTime");

				expectedTime = androidFormat.parse(androidFormat.format(timeFormat.parse(deviceTime)));
			} else {
				attribute = "value";
				activityLogHeaders = new ArrayList<WebElement>();
				activityLogSubHeaders = new ArrayList<WebElement>();
				activityLogTime = new ArrayList<WebElement>();

				int events = 0;
				if (MobileUtils.isMobElementExists("xpath", "//*[contains(@name,'_Cell')]", testCase)) {
					events = MobileUtils.getMobElements(testCase, "xpath", "//*[contains(@name,'_Cell')]").size();
					System.out.println("#########events: " + events);
				}

				for (int i = 0; i < events; i++) {
					String locatorValue = "Events_1_" + i + "_Title";
					System.out.println("#########locatorValue: " + locatorValue);
					if (MobileUtils.isMobElementExists("NAME", locatorValue, testCase)) {
						activityLogHeaders.add(MobileUtils.getMobElement(testCase, "NAME", locatorValue));
					}
					locatorValue = "Events_1_" + i + "_Subtitle";
					System.out.println("#########locatorValue: " + locatorValue);
					if (MobileUtils.isMobElementExists("NAME", locatorValue, testCase)) {
						activityLogSubHeaders.add(MobileUtils.getMobElement(testCase, "NAME", locatorValue));
					}
					locatorValue = "Events_1_" + i + "_Time";
					System.out.println("#########locatorValue: " + locatorValue);
					if (MobileUtils.isMobElementExists("NAME", locatorValue, testCase)) {
						activityLogTime.add(MobileUtils.getMobElement(testCase, "NAME", locatorValue));
					}
				}
				if (activityLogTime.get(0).getAttribute("value").contains("PM")
						|| activityLogTime.get(0).getAttribute("value").contains("AM")) {
					expectedTime = timeFormat.parse(deviceTime);
					System.out.println("########expectedTime: " + expectedTime);
				} else {
					expectedTime = iosFormat2.parse(iosFormat2.format(timeFormat.parse(deviceTime)));
					System.out.println("########expectedTime: " + expectedTime);
				}
			}
			boolean activityLogFound = false;
			for (int i = 0; i < activityLogHeaders.size(); i++) {
				try {
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						displayedTime = androidFormat.parse(activityLogTime.get(i).getAttribute(attribute));
					} else {
						if (activityLogTime.get(i).getAttribute("value").contains("PM")
								|| activityLogTime.get(0).getAttribute("value").contains("AM")) {
							displayedTime = iosFormat.parse(activityLogTime.get(i).getAttribute(attribute));
							System.out.println("########displayedTime: " + displayedTime);
						} else {
							expectedTime = timeFormat.parse(activityLogTime.get(i).getAttribute(attribute));
							System.out.println("########expectedTime: " + expectedTime);
						}
					}
					long diff = displayedTime.getTime() - expectedTime.getTime();
					System.out.println("########diff: " + diff);
					diff = diff / (60 * 1000) % 60;
					System.out.println("########diff: " + diff);
					if ((diff <= 59 && diff >= 0) || (diff >= -59 && diff < 0)) {
						if (activityLogHeaders.get(i).getAttribute(attribute)
								.equalsIgnoreCase(expectedActivityHeader)) {
							if (activityLogSubHeaders.get(i).getAttribute(attribute)
									.equalsIgnoreCase(expectedActivitySubHeader)) {
								activityLogFound = true;
								break;
							}
						}
					}
				} catch (NoSuchElementException e) {
					break;
				} catch (NullPointerException e) {
					break;
				} catch (Exception e) {
					break;
				}
			}
			if (activityLogFound) {
				Keyword.ReportStep_Pass(testCase,
						expectedActivityHeader + " found on the activity log with correct time stamp : "
								+ androidFormat.format(displayedTime) + " and correct sub header : "
								+ expectedActivitySubHeader);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedActivityHeader
						+ " not found on the activity log with correct time stamp and correct sub header. Expected Time: "
						+ androidFormat.format(expectedTime) + " ExepectedHeader: " + expectedActivityHeader
						+ " Expected Sub Header: " + expectedActivitySubHeader);
			}
			if (exampleData.get(0).contains("by invited user")) {
				inputs.setInputValue("USERID", actualUser);
			}
			// flag = flag & DASNotificationUtils.closeActivityLogs(testCase);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		// }

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
