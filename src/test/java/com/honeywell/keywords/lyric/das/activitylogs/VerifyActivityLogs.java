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
		/*if(true){
			return true;
		}*/
		// if(!inputs.getInputValue("VERIFY_ACTIVITYLOGS").equalsIgnoreCase("NO")){
		try {
			HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "ActivityLogs");
			String expectedActivityHeader = "";
			String expectedActivitySubHeader = "";
			String[][] eventsList;
			String deviceLocationTime = "";
			Date expectedTime = new Date();
			Date displayedTime = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
			SimpleDateFormat hour12Format = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat hour24Format = new SimpleDateFormat("HH:mm");
			String actualUser = "";
			if (exampleData.get(0).contains("by invited user")) {
				actualUser = inputs.getInputValue("USERID");
				inputs.setInputValue("USERID", inputs.getInputValue("INVITEDUSER"));
			}

			// flag = flag & DASNotificationUtils.openActivityLogs(testCase);
			switch (exampleData.get(0).toUpperCase()) {
			case "SENSOR MOTION DETECTED AT AWAY MODE":{
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " Motion Detected";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("MOTION_DETECTED_TIME");
				break;
			}

			case "CAMERA MOTION DETECTED AT AWAY MODE":{
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " Detected";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("MOTION_DETECTED_TIME");
				break;
			}
			// opened activities
			case "DOOR OPENED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " opened";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "DOOR OPENED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " opened";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "DOOR OPENED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " opened";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " opened";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " opened";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			case "WINDOW OPENED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " opened";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			// closed activities
			case "DOOR CLOSED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " closed";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "DOOR CLOSED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " closed";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "DOOR CLOSED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " closed";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " closed";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " closed";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
				break;
			}
			case "WINDOW CLOSED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Closed";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_CLOSED_TIME");
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

			case "WINDOW ALARM AT AWAY MODE":{
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")+" Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			
			case "WINDOW ALARM AT NIGHT MODE":{
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")+" Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
				break;
			}
			case "ALARM AT AWAY MODE": {
				expectedActivityHeader = "Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ATTENTION SIREN SOUNDED BY INVITED USER":
			case "ATTENTION SIREN SOUNDED BY ACTUAL USER": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = "Attention Siren sounded by " + locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ALARM AT NIGHT MODE": {
				expectedActivityHeader = "Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "DOOR SENSOR ALARM AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "DOOR SENSOR ALARM AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "WINDOW SENSOR ALARM AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "WINDOW SENSOR ALARM AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("ALARM_TIME");
				break;
			}
			case "ALARM DISMISSED BY INVITED USER":
			case "ALARM DISMISSED": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = "Alarm cancelled ";
				expectedActivitySubHeader = locInfo.getUserFirstName();
				deviceLocationTime = inputs.getInputValue("ALARM_DISMISSED_TIME");
				break;
			}

			// bypass activities
			case "DOOR SENSOR BYBASS": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Bypassed";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_BYPASSED_TIME");
				break;
			}
			case "WINDOW SENSOR BYPASS": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Bypassed";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_BYPASSED_TIME");
				break;
			}

			// bypass cleared activities
			case "DOOR SENSOR BYBASS CLEARED": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Bypass cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_BYPASS_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR BYPASS CLEARED": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Bypass cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_BYPASS_CLEARED_TIME");
				break;
			}

			// tampered activities
			case "DOOR SENSOR TAMPERED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPERED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPERED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPERED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
				break;
			}

			// tamper cleared activities
			case "DOOR SENSOR TAMPER CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT HOME MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "HOME MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPER CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT AWAY MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "AWAY MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}
			case "DOOR SENSOR TAMPER CLEARED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("DOOR_TAMPER_CLEARED_TIME");
				break;
			}
			case "WINDOW SENSOR TAMPER CLEARED AT NIGHT MODE": {
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Tamper cleared";
				expectedActivitySubHeader = "NIGHT MODE";
				deviceLocationTime = inputs.getInputValue("WINDOW_TAMPER_CLEARED_TIME");
				break;
			}

			// mode activities
			case "SWITCHED TO HOME BY INVITED USER BY APP":
			case "SWITCHED TO HOME BY APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceLocationTime = inputs.getInputValue("HOME_TIME");
				break;
			}

			case "SWITCHED TO HOME BY KEYFOB": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Home by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY KEY FOB";
				deviceLocationTime = inputs.getInputValue("HOME_TIME");
				break;
			}

			case "SWITCHED TO AWAY BY INVITED USER BY APP":
			case "SWITCHED TO AWAY BY APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceLocationTime = inputs.getInputValue("AWAY_TIME");
				break;
			}

			case "SWITCHED TO AWAY BY KEYFOB": {
				//LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
						+ inputs.getInputValue("KEY_FOB_NAME");
				expectedActivitySubHeader = "BY KEY FOB";
				deviceLocationTime = inputs.getInputValue("AWAY_TIME");
				break;
			}
			case "SWITCHED TO NIGHT BY INVITED USER BY APP":
			case "SWITCHED TO NIGHT BY APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}

			case "SWITCHED TO NIGHT BY KEYFOB": {
				//LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
						+ inputs.getInputValue("KEY_FOB_NAME");
				expectedActivitySubHeader = "BY KEY FOB";
				deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}

			case "SWITCHED TO OFF BY INVITED USER BY APP":
			case "SWITCHED TO OFF BY APP": {
				LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned Off by "
						+ locInfo.getUserFirstName();
				expectedActivitySubHeader = "BY APP";
				deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}
			
			case "SWITCHED TO OFF BY KEYFOB": {
				//LocationInformation locInfo = new LocationInformation(testCase, inputs);
				expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " turned Off by "
						+ inputs.getInputValue("KEY_FOB_NAME");
				expectedActivitySubHeader = "BY KEY FOB";
				deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
				break;
			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + exampleData.get(0));
				return flag;
			}
			}
			if (deviceLocationTime.equals("")) {
				deviceLocationTime = LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT");
				System.out.println("########deviceLocationTime: " + deviceLocationTime);
				System.out.println("Not found");
			}

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {

				List<WebElement> activityLogTitleElements = MobileUtils.getMobElements(fieldObjects, testCase,
						"ActivityLogHeader");
				List<WebElement> activityLogSubTitleElements = MobileUtils.getMobElements(fieldObjects, testCase,
						"ActivityLogSubHeader");
				List<WebElement> activityLogTimeElements = MobileUtils.getMobElements(fieldObjects, testCase,
						"ActivityLogTime");
				System.out.println("#########activityLogTitleElements.size(): " + activityLogTitleElements.size());
				System.out
						.println("#########activityLogSubTitleElements.size(): " + activityLogSubTitleElements.size());
				System.out.println("#########activityLogTimeElements.size(): " + activityLogTimeElements.size());
				eventsList = new String[activityLogTitleElements.size()][3];
				int i = 0;
				for (WebElement ele : activityLogTitleElements) {
					eventsList[i][0] = ele.getAttribute("text");
					i++;
					// activityLogHeaders.add(ele.getAttribute("text"));
				}
				i = 0;
				for (WebElement ele : activityLogSubTitleElements) {
					eventsList[i][1] = ele.getAttribute("text");
					i++;
					// activityLogSubHeaders.add(ele.getAttribute("text"));
				}
				i = 0;
				for (WebElement ele : activityLogTimeElements) {
					eventsList[i][2] = ele.getAttribute("text");
					i++;
					// activityLogTime.add(ele.getAttribute("text"));
				}
				expectedTime = hour12Format.parse(hour12Format.format(timeFormat.parse(deviceLocationTime)));
				System.out.println("#########1. expectedTime: " + expectedTime);
			} else {
				int events = 0;
				if (MobileUtils.isMobElementExists("xpath", "//*[contains(@name,'_Cell')]", testCase)) {
					events = MobileUtils.getMobElements(testCase, "xpath", "//*[contains(@name,'_Cell')]").size();
					System.out.println("#########events: " + events);
				}
				eventsList = new String[events][3];
				for (int i = 0; i < events; i++) {
					String eventTitleLocatorValue = "Events_1_" + i + "_Title";
					String eventSubTitleLocatorValue = "Events_1_" + i + "_Subtitle";
					String eventTimeLocatorValue = "Events_1_" + i + "_Time";
					System.out.println("#########eventTitleLocatorValue: " + eventTitleLocatorValue);
					System.out.println("#########eventSubTitleLocatorValue: " + eventSubTitleLocatorValue);
					System.out.println("#########eventTimeLocatorValue: " + eventTimeLocatorValue);
					try {
						eventsList[i][0] = MobileUtils.getFieldValue(testCase, "NAME", eventTitleLocatorValue);
					} catch (NoSuchElementException e) {
						eventsList[i][0] = "";
					} catch (NullPointerException e) {
						eventsList[i][0] = "";
					} catch (Exception e) {
						eventsList[i][0] = "";
					}
					try {
						if (MobileUtils.isMobElementExists("NAME", eventSubTitleLocatorValue, testCase, 5)) {
							eventsList[i][1] = MobileUtils.getFieldValue(testCase, "NAME", eventSubTitleLocatorValue);
						} else {
							eventsList[i][1] = "";
						}

					} catch (NoSuchElementException e) {
						eventsList[i][1] = "";
					} catch (NullPointerException e) {
						eventsList[i][1] = "";
					} catch (Exception e) {
						eventsList[i][0] = "";
					}
					try {
						eventsList[i][2] = MobileUtils.getFieldValue(testCase, "NAME", eventTimeLocatorValue);
					} catch (NoSuchElementException e) {
						eventsList[i][2] = "";
					} catch (NullPointerException e) {
						eventsList[i][2] = "";
					} catch (Exception e) {
						eventsList[i][0] = "";
					}
				}
				System.out.println("##########eventsList.length: " + eventsList.length);
				for (int i = 0; i < eventsList.length; i++) {
					for (int j = 0; j < 3; j++) {
						System.out.println("#######evnetList[" + i + "]" + "[" + j + "]" + eventsList[i][j]);
					}
				}
				if (eventsList[0][2].contains("PM") || eventsList[0][2].contains("AM")) {
					expectedTime = timeFormat.parse(deviceLocationTime);
					System.out.println("########expectedTime: " + expectedTime);
				} else {
					expectedTime = hour24Format.parse(hour24Format.format(timeFormat.parse(deviceLocationTime)));
					System.out.println("########expectedTime: " + expectedTime);
				}
			}
			boolean activityLogFound = false;
			for (int i = 0; i < eventsList.length; i++) {
				try {
					if (eventsList[i][2].contains("PM") || eventsList[i][2].contains("AM")) {
						displayedTime = hour12Format.parse(eventsList[i][2]);
						System.out.println("########1. displayedTime: " + displayedTime);
						System.out.println("########2. expectedTime: " + expectedTime);
					} else {
						System.out.println(eventsList[i][2]);
						expectedTime = hour24Format.parse(eventsList[i][2]);// timeFormat.parse(eventsList[i][2]);
						System.out.println("########1. displayedTime: " + displayedTime);
						System.out.println("########2. expectedTime: " + expectedTime);

					}
					long diff = displayedTime.getTime() - expectedTime.getTime();
					System.out.println("########1. diff: " + diff);
					diff = diff / (60 * 1000) % 60;
					System.out.println("########2. diff: " + diff);
					if ((diff <= 59 && diff >= 0) || (diff >= -59 && diff < 0)) {
						if (eventsList[i][0].equalsIgnoreCase(expectedActivityHeader)) {
							if (eventsList[i][1].equalsIgnoreCase(expectedActivitySubHeader)) {
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
								+ hour12Format.format(displayedTime) + " and correct sub header : "
								+ expectedActivitySubHeader);
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedActivityHeader
						+ " not found on the activity log with correct time stamp and correct sub header. Expected Time: "
						+ hour12Format.format(expectedTime) + " ExepectedHeader: " + expectedActivityHeader
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
