package com.honeywell.keywords.jasper.Vacation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.VacationHoldScreen;

public class DisplayDifferenceInVacationTime extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public DisplayDifferenceInVacationTime(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
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
		try {
			String deviceTime = "";
			if (exampleData.get(0).equalsIgnoreCase("From") && exampleData.get(1).equalsIgnoreCase("Current Time")) {
				switch (exampleData.get(2)) {
				case "15": {
					String startDate = vhs.getStartDate() + " " + vhs.getStartTime();
					String startDateTime = "", startDateTimeUTC = "";
					deviceTime = "";
					final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
							Locale.ENGLISH);
					final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm aaa");
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						try {
							startDateTime = vacationDateFormat.format(dateFormat.parse(startDate.trim()));
							startDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, startDateTime);
							if (inputs.isRunningOn("Saucelabs")) {
								String time = LyricUtils.getDeviceTime(testCase, inputs);
								SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
								time = vacationDateFormat.format(androidDateFormat1.parse(time));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							} else {
								String time = vacationDateFormat.format(androidDateFormat
										.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured:" + e.getMessage());
						}
					} else {
						startDateTime = vacationDateFormat.format(dateFormat.parse(startDate.trim()));
						startDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, startDateTime);
						if (inputs.isRunningOn("Saucelabs")) {
							String time = LyricUtils.getDeviceTime(testCase, inputs);
							SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
							time = vacationDateFormat.format(androidDateFormat1.parse(time));
							deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
						} else {
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase,
								JasperAdhocOverride.getIOSSimulatorTime(testCase));
						}
					}

					String currentTimeUTC = "";
					currentTimeUTC = JasperSetPoint.roundOffTimeToUpcomingNearest15minutes(testCase, deviceTime);
					if (!currentTimeUTC.equalsIgnoreCase(startDateTimeUTC)) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Vacation start time: " + currentTimeUTC
										+ " is not nearest 15 mins to : " + startDateTimeUTC);
					} else {
						Keyword.ReportStep_Pass(testCase, "Vacation Start time: " + currentTimeUTC
								+ " is nearest 15 minutes to: " + startDateTimeUTC);
						flag = true;
					}
					break;
				}
				case "10": {
					String startDate = vhs.getStartDate() + " " + vhs.getStartTime();
					String startDateTime = "", startDateTimeUTC = "";
					deviceTime = "";
					final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
							Locale.ENGLISH);
					final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm aaa");
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						try {
							startDateTime = vacationDateFormat.format(dateFormat.parse(startDate.trim()));
							startDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, startDateTime);

							if (inputs.isRunningOn("Saucelabs")) {
								String time = LyricUtils.getDeviceTime(testCase, inputs);
								SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
								time = vacationDateFormat.format(androidDateFormat1.parse(time));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							} else {
								String time = vacationDateFormat.format(androidDateFormat
										.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							}

						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured:" + e.getMessage());
						}

					} else {
						startDateTime = vacationDateFormat.format(dateFormat.parse(startDate.trim()));
						startDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, startDateTime);
						if (inputs.isRunningOn("Saucelabs")) {
							String time = LyricUtils.getDeviceTime(testCase, inputs);
							SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
							time = vacationDateFormat.format(androidDateFormat1.parse(time));
							deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
						} else {
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase,
								JasperAdhocOverride.getIOSSimulatorTime(testCase));
						}
					}
					String currentTimeUTC = "";
					currentTimeUTC = JasperSetPoint.roundOffTimeToUpcomingNearest10minutes(testCase, deviceTime);
					Keyword.ReportStep_Pass(testCase, currentTimeUTC);
					Keyword.ReportStep_Pass(testCase, startDateTimeUTC);
					if (currentTimeUTC.equalsIgnoreCase(startDateTimeUTC)) {
						Keyword.ReportStep_Pass(testCase, "Vacation Start and Current Time is 10 minutes");
						flag = true;
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Vacation start time: " + currentTimeUTC
										+ " is not nearest 10 mins to: " + startDateTimeUTC);

					}
					break;
				}
				}

			} else if (exampleData.get(0).equalsIgnoreCase("To")
					&& exampleData.get(1).equalsIgnoreCase("Week from Current Time")) {
				switch (exampleData.get(2)) {
				case "15": {
					String endDate = vhs.getEndDate() + " " + vhs.getEndTime();
					System.out.println(endDate);
					String endDateTime = "", endDateTimeUTC = "";
					deviceTime = "";
					final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
							Locale.ENGLISH);
					final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm aaa");
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						try {
							endDateTime = vacationDateFormat.format(dateFormat.parse(endDate.trim()));
							endDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, endDateTime);
							if (inputs.isRunningOn("Saucelabs")) {
								String time = LyricUtils.getDeviceTime(testCase, inputs);
								SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
								time = vacationDateFormat.format(androidDateFormat1.parse(time));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							} else {
								String time = vacationDateFormat.format(androidDateFormat
										.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured:" + e.getMessage());
						}

					} else {
						endDateTime = vacationDateFormat.format(dateFormat.parse(endDate.trim()));
						endDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, endDateTime);
						if (inputs.isRunningOn("Saucelabs")) {
							String time = LyricUtils.getDeviceTime(testCase, inputs);
							SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
							time = vacationDateFormat.format(androidDateFormat1.parse(time));
							deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
						} else {
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase,
								JasperAdhocOverride.getIOSSimulatorTime(testCase));
						}
					}
					String currentTimeUTC = "";
					currentTimeUTC = JasperSetPoint.roundOffTimeToTheNearest1week(testCase, deviceTime);
					currentTimeUTC = currentTimeUTC.split("T")[0];
					endDateTimeUTC = endDateTimeUTC.split("T")[0];
					if (!currentTimeUTC.equalsIgnoreCase(endDateTimeUTC)) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Vacation start time: " + currentTimeUTC
										+ " is not nearest 15 mins to: " + endDateTimeUTC);
					} else {
						Keyword.ReportStep_Pass(testCase, "Vacation Start and Current Time is 15 minutes");
						flag = true;
					}

					break;
				}
				case "10": {
					String endDate = vhs.getEndDate() + " " + vhs.getEndTime();
					String endDateTime = "", endDateTimeUTC = "";
					deviceTime = "";
					final SimpleDateFormat vacationDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					final SimpleDateFormat androidDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",
							Locale.ENGLISH);
					final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm aaa");
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						try {
							endDateTime = vacationDateFormat.format(dateFormat.parse(endDate.trim()));
							endDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, endDateTime);
							if (inputs.isRunningOn("Saucelabs")) {
								String time = LyricUtils.getDeviceTime(testCase, inputs);
								SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
								time = vacationDateFormat.format(androidDateFormat1.parse(time));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							} else {
								String time = vacationDateFormat.format(androidDateFormat
										.parse(JasperAdhocOverride.getAndroidDeviceTime(testCase).trim()));
								deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
							}
						} catch (Exception e) {
							flag = false;
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Error Occured:" + e.getMessage());
						}
					} else {
						endDateTime = vacationDateFormat.format(dateFormat.parse(endDate.trim()));
						endDateTimeUTC = JasperVacation.convertTimetoUTCTime(testCase, endDateTime);
						if (inputs.isRunningOn("Saucelabs")) {
							String time = LyricUtils.getDeviceTime(testCase, inputs);
							SimpleDateFormat androidDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
							time = vacationDateFormat.format(androidDateFormat1.parse(time));
							deviceTime = JasperVacation.convertTimetoUTCTime(testCase, time);
						} else {
						deviceTime = JasperVacation.convertTimetoUTCTime(testCase,
								JasperAdhocOverride.getIOSSimulatorTime(testCase));
						}
					}
					String currentTimeUTC = "";
					currentTimeUTC = JasperSetPoint.roundOffTimeToTheNearest1week(testCase, deviceTime);
					currentTimeUTC = currentTimeUTC.split("T")[0];
					endDateTimeUTC = endDateTimeUTC.split("T")[0];
					Keyword.ReportStep_Pass(testCase, currentTimeUTC);
					Keyword.ReportStep_Pass(testCase, endDateTimeUTC);
					if (currentTimeUTC.equalsIgnoreCase(endDateTimeUTC)) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation end and Current Time is 10 minutes " + endDateTimeUTC);
						flag = true;

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Vacation start time: " + currentTimeUTC
										+ " is not nearest 10 mins to: " + endDateTimeUTC);

					}

					break;
				}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured:" + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}

}
