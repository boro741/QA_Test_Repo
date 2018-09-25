package com.honeywell.keywords.lyric.das.activitylogs;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.NoSuchElementException;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASActivityLogsUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.AlarmScreen;
import com.honeywell.account.information.LocationInformation;

public class VerifyActivityList extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public DataTable dataTable;
	public boolean flag = true;
	public String[][] eventsList;

	public VerifyActivityList(TestCases testCase, TestCaseInputs inputs,  DataTable dataTable) {
		this.testCase = testCase;
		this.dataTable = dataTable;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		eventsList =DASActivityLogsUtils.fetchActivityList(testCase);
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify the following activity log:$")
	public boolean keywordSteps() throws KeywordException {
/*
		if(true){
			return true;
		}*/
		// if(!inputs.getInputValue("VERIFY_ACTIVITYLOGS").equalsIgnoreCase("NO")){
		try {
			String expectedActivityHeader = "";
			String expectedActivitySubHeader = "";
			String deviceLocationTime = "";
			Date expectedTime = new Date();
			Date displayedTime = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
			SimpleDateFormat hour12Format = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat hour24Format = new SimpleDateFormat("HH:mm");
			String actualUser = "";
			for (int i = 0; i < dataTable.getSize(); i++) {
				if (dataTable.getData(i, "Elements").contains("by invited user")) {
					actualUser = inputs.getInputValue("USERID");
					inputs.setInputValue("USERID", inputs.getInputValue("INVITEDUSER"));
				}
				switch (dataTable.getData(i, "Elements").trim().toUpperCase()) {
				case "INDOOR MOTION VIEWER TAMPERED AT AWAY MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " tamper";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("ISMV_TAMPERED_TIME");
					break;
				}
				case "INDOOR MOTION VIEWER TAMPER CLEARED AT AWAY MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " tamper cleared";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("ISMV_TAMPER_CLEARED_TIME");
					break;
				}
				case "INDOOR MOTION VIEWER TAMPERED AT NIGHT MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " tamper";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("ISMV_TAMPERED_TIME");
					break;
				}
				case "INDOOR MOTION VIEWER TAMPER CLEARED AT NIGHT MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1") + " tamper cleared";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("ISMV_TAMPER_CLEARED_TIME");
					break;
				}
				case "INDOOR MOTION VIEWER CLIP":{
					expectedActivityHeader = "Motion detected";
					expectedActivitySubHeader = inputs.getInputValue("LOCATION1_DEVICE1_INDOORMOTIONVIEWER1");
					deviceLocationTime = inputs.getInputValue("INDOORMOTION_DETECTED_TIME");
					AlarmScreen alarmScreen = new AlarmScreen(testCase);
					flag= alarmScreen.isMotionDetectedVideoClipDisplayed();
					break;
				}
				case "MOTION SENSOR TAMPERED AT AWAY MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " tamper";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("MOTION_SENSOR_TAMPERED_TIME");
					break;
				}
				case "MOTION SENSOR TAMPER CLEARED AT AWAY MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " tamper cleared";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("MOTION_SENSOR_TAMPER_CLEARED_TIME");
					break;
				}
				case "SENSOR MOTION DETECTED AT AWAY MODE":{
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_MOTIONSENSOR1") + " detected motion";
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
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")+" Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
					break;
				}

				case "WINDOW ALARM AT NIGHT MODE":{
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1")+" Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("WINDOW_OPENED_TIME");
					break;
				}
				case "ALARM AT AWAY MODE": {
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "SIREN SOUNDED BY INVITED USER":
				case "SIREN SOUNDED BY ACTUAL USER": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					//expectedActivityHeader = "Attention Siren sounded by " + locInfo.getUserFirstName();
					expectedActivityHeader = "Siren sounded by " + locInfo.getUserFirstName();
					expectedActivitySubHeader = "BY APP";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "ALARM AT NIGHT MODE": {
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "DOOR SENSOR ALARM AT AWAY MODE": {
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "DOOR SENSOR ALARM AT NIGHT MODE": {
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "WINDOW SENSOR ALARM AT NIGHT MODE": {
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "WINDOW SENSOR ALARM AT AWAY MODE": {
					//expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " Alarm";
					expectedActivityHeader = "Security Alarm";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("ALARM_TIME");
					break;
				}
				case "ALARM DISMISSED BY INVITED USER":
				case "ALARM DISMISSED": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedActivityHeader = "Alarm cancelled by "+locInfo.getUserFirstName();
					expectedActivitySubHeader = "BY APP";
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
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " tamper";
					expectedActivitySubHeader = "HOME MODE";
					deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
					break;
				}
				case "WINDOW SENSOR TAMPERED AT HOME MODE": {
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " tamper";
					expectedActivitySubHeader = "HOME MODE";
					deviceLocationTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
					break;
				}
				case "DOOR SENSOR TAMPERED AT AWAY MODE": {
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " tamper";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
					break;
				}
				case "WINDOW SENSOR TAMPERED AT AWAY MODE": {
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " tamper";
					expectedActivitySubHeader = "AWAY MODE";
					deviceLocationTime = inputs.getInputValue("WINDOW_TAMPERED_TIME");
					break;
				}
				case "DOOR SENSOR TAMPERED AT NIGHT MODE": {
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_DOORSENSOR1") + " tamper";
					expectedActivitySubHeader = "NIGHT MODE";
					deviceLocationTime = inputs.getInputValue("DOOR_TAMPERED_TIME");
					break;
				}
				case "WINDOW SENSOR TAMPERED AT NIGHT MODE": {
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_WINDOWSENSOR1") + " tamper";
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
					expectedActivitySubHeader = "BY KEYFOB";
					deviceLocationTime = inputs.getInputValue("HOME_TIME");
					break;
				}

				case "SWITCHED TO AWAY BY INVITED USER BY APP":
				case "SWITCHED TO AWAY BY APP": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
							+ locInfo.getUserFirstName();
					System.out.println("#########expectedActivityHeader: " + expectedActivityHeader);
					expectedActivitySubHeader = "BY APP";
					deviceLocationTime = inputs.getInputValue("AWAY_TIME");
					System.out.println("#########deviceLocationTime: " + deviceLocationTime);
					break;
				}

				case "SWITCHED TO AWAY BY KEYFOB": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Away by "
							+ locInfo.getUserFirstName();
					expectedActivitySubHeader = "BY KEYFOB";
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
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Night by "
							+ locInfo.getUserFirstName();
					expectedActivitySubHeader = "BY KEYFOB";
					deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
					break;
				}

				case "SWITCHED TO OFF BY INVITED USER BY APP":
				case "SWITCHED TO OFF BY APP": {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					expectedActivityHeader = inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " set to Off by "
							+ locInfo.getUserFirstName();
					expectedActivitySubHeader = "BY APP";
					deviceLocationTime = inputs.getInputValue("NIGHT_TIME");
					break;
				}

				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input : " + dataTable.getData(i, "Elements").trim().toUpperCase());
					return flag;
				}
				}
				if (dataTable.getData(i, "Elements").trim().contains("by invited user")) {
					inputs.setInputValue("USERID", actualUser);
				}
				if (deviceLocationTime.equals("")) {
					deviceLocationTime = LyricUtils.getLocationTime(testCase, inputs, "TIMEINYYMMHHMMFORMAT");
					System.out.println("########deviceLocationTime: " + deviceLocationTime);
					System.out.println("Not found");
				}
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					expectedTime = hour12Format.parse(hour12Format.format(timeFormat.parse(deviceLocationTime)));
					System.out.println("#########1. expectedTime: " + expectedTime);
				} else {
					if (eventsList[0][2].contains("PM") || eventsList[0][2].contains("AM")) {
						expectedTime = timeFormat.parse(deviceLocationTime);
						System.out.println("########expectedTime: " + expectedTime);
					} else {
						expectedTime = hour24Format.parse(hour24Format.format(timeFormat.parse(deviceLocationTime)));
						System.out.println("########expectedTime: " + expectedTime);
					}
				}
				boolean activityLogFound = false;
				for (int index = 0; index < eventsList.length; index++) {
					try {
						if (eventsList[index][2].contains("PM") || eventsList[index][2].contains("AM")) {
							displayedTime = hour12Format.parse(eventsList[index][2]);
							System.out.println("########1. displayedTime: " + displayedTime);
							System.out.println("########2. expectedTime: " + expectedTime);
						} else {
							System.out.println(eventsList[index][2]);
							expectedTime = hour24Format.parse(eventsList[index][2]);// timeFormat.parse(eventsList[i][2]);
							System.out.println("########1. displayedTime: " + displayedTime);
							System.out.println("########2. expectedTime: " + expectedTime);

						}
						long diff = displayedTime.getTime() - expectedTime.getTime();
						System.out.println("########1. diff: " + diff);
						diff = diff / (60 * 1000) % 60;
						System.out.println("########2. diff: " + diff);
						if ((diff <= 59 && diff >= 0) || (diff >= -59 && diff < 0)) {
							if (eventsList[index][0].equalsIgnoreCase(expectedActivityHeader)) {
								if (eventsList[index][1].equalsIgnoreCase(expectedActivitySubHeader)) {
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
			}
			
			
			// flag = flag & DASNotificationUtils.closeActivityLogs(testCase);
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		// }
		eventsList = null;
		return flag;

	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
