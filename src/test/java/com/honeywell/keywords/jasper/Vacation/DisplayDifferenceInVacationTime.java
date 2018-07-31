package com.honeywell.keywords.jasper.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.jasper.utils.JasperSetPoint;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.VacationHoldScreen;

public class DisplayDifferenceInVacationTime extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplayDifferenceInVacationTime(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs=inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user is displayed with (.*) date as (.*) nearest to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		String deviceTime="";
		if(exampleData.get(0).equalsIgnoreCase("From")&&exampleData.get(1).equalsIgnoreCase("Current Time")) {
			switch(exampleData.get(2)) {
			case "15":{
				
				deviceTime = "";
				final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						String time = vacationDateFormat
								.format(androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
					} catch (Exception e) {
						flag=false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured:"+e.getMessage());
					}

				} else {
					deviceTime = JasperVacation.convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
				}
				Date startTime;
				Date currentTime;
				try {
					startTime = vacationDateFormat.parse(CHILUtil.startTime);
					 currentTime = vacationDateFormat.parse(deviceTime);
					 long dif = startTime.getTime() - currentTime.getTime();
						int diffInMinutes = (int) ((dif / 1000) / 60);
						if (diffInMinutes > Integer.parseInt(exampleData.get(2))) {
							flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 15 mins");
							
						}
						else if(diffInMinutes<=Integer.parseInt(exampleData.get(2))) {
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 15 minutes");
							flag=true;
						}
						else if (diffInMinutes < 0) {
							Keyword.ReportStep_Pass(testCase,
									"Wait for Vacation Start : Vacation start time is past current device time");
							flag=true;
						}
				} catch (ParseException e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}
				
				break;
			}
			case "10":{
				deviceTime = "";
				final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						String time = vacationDateFormat
								.format(androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
					} catch (Exception e) {
						flag=false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured:"+e.getMessage());
					}

				} else {
					deviceTime = JasperVacation.convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
				}
				Date startTime;
				Date currentTime;
				try {
					startTime = vacationDateFormat.parse(CHILUtil.startTime);
					 currentTime = vacationDateFormat.parse(deviceTime);
					 long dif = startTime.getTime() - currentTime.getTime();
						int diffInMinutes = (int) ((dif / 1000) / 60);
						if (diffInMinutes > Integer.parseInt(exampleData.get(2))) {
							flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 10 mins");
							
						}
						else if(diffInMinutes<=Integer.parseInt(exampleData.get(2))) {
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 10 minutes");
							flag=true;
						}
						else if (diffInMinutes < 0) {
							Keyword.ReportStep_Pass(testCase,
									"Wait for Vacation Start : Vacation start time is past current device time");
							flag=true;
						}
				} catch (ParseException e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}

				break;
			}
			}
			
		}
		else if(exampleData.get(0).equalsIgnoreCase("To")&&exampleData.get(1).equalsIgnoreCase("Week from Current Time")) {
			switch(exampleData.get(2)) {
			case "15":{
				
				deviceTime = "";
				final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						String time = vacationDateFormat
								.format(androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
					} catch (Exception e) {
						flag=false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured:"+e.getMessage());
					}

				} else {
					deviceTime = JasperVacation.convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
				}
				Date startTime;
				Date currentTime;
				try {
					startTime = vacationDateFormat.parse(CHILUtil.endTime);
					 currentTime = vacationDateFormat.parse(deviceTime);
					 long dif = startTime.getTime() - currentTime.getTime();
						int diffInMinutes = (int) ((dif / 1000) / 60);
						if (diffInMinutes > Integer.parseInt(exampleData.get(2))) {
							flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 15 mins");
							
						}
						else if(diffInMinutes<=Integer.parseInt(exampleData.get(2))) {
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 15 minutes");
							flag=true;
						}
						else if (diffInMinutes < 0) {
							Keyword.ReportStep_Pass(testCase,
									"Wait for Vacation Start : Vacation start time is past current device time");
							flag=true;
						}
				} catch (ParseException e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}
				
				break;
			}
			case "10":{
				deviceTime = "";
				final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					try {
						String time = vacationDateFormat
								.format(androidDateFormat.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
					} catch (Exception e) {
						flag=false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured:"+e.getMessage());
					}

				} else {
					deviceTime = JasperVacation.convertTimetoUTCTime(testCase, JasperAdhocOverride.getIOSSimulatorTime(testCase));
				}
				Date startTime;
				Date currentTime;
				try {
					startTime = vacationDateFormat.parse(CHILUtil.endTime);
					 currentTime = vacationDateFormat.parse(deviceTime);
					 long dif = startTime.getTime() - currentTime.getTime();
						int diffInMinutes = (int) ((dif / 1000) / 60);
						if (diffInMinutes > Integer.parseInt(exampleData.get(2))) {
							flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 10 mins");
							
						}
						else if(diffInMinutes<=Integer.parseInt(exampleData.get(2))) {
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 10 minutes");
							flag=true;
						}
						else if (diffInMinutes < 0) {
							Keyword.ReportStep_Pass(testCase,
									"Wait for Vacation Start : Vacation start time is past current device time");
							flag=true;
						}
				} catch (ParseException e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}

				break;
			}
			}
			
		} 
			
			 return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}


}
