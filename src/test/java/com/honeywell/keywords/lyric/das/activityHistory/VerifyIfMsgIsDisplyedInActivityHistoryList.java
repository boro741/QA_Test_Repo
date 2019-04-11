package com.honeywell.keywords.lyric.das.activityHistory;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivityHistoryScreen;

public class VerifyIfMsgIsDisplyedInActivityHistoryList extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedInput;
	public boolean flag = true;

	public VerifyIfMsgIsDisplyedInActivityHistoryList(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedInput) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedInput = expectedInput;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^\"(.*)\" should be displayed in the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (expectedInput.get(1).equalsIgnoreCase("ACTIVITY HISTORY")) {
				switch (expectedInput.get(0).toUpperCase()) {
				case "GEOFENCE HOME EVENT - SUCCESS": {
					ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
					if (ah.isNoMessagesLabelVisible(10)) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No messages are displayed in the Activity History screen");
					} else {
						// Keyword.ReportStep_Pass(testCase, "Messages dispalyed in Activity History
						// screen");
						ah.firstMsgFromActivityHistoryListAfterGeofenceCross(testCase, inputs);
						System.out.println(
								"#######1: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE"));
						System.out.println(
								"#######2: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE"));
						System.out.println(
								"#######3: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE"));

						if ((inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
								.equalsIgnoreCase("A MOMENT AGO"))
								|| (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
										.equalsIgnoreCase("ONE MINUTE AGO"))
								|| (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
										.equalsIgnoreCase("TWO MINUTE AGO")
										|| inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
												.equalsIgnoreCase("22 Mar 2019, 7:00 p.m.")
										|| inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
												.equalsIgnoreCase("2 HOURS AGO"))
										&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
												.equalsIgnoreCase("Geofence home event - success"))
										&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE")
												.equalsIgnoreCase("Geofence home event - success"))) {
							flag = true;
							Keyword.ReportStep_Pass(testCase,
									"Message Time after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Sub Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE"));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Message Time after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Title Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Sub Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE"));

						}
					}
					break;
				}

				case "GEOFENCE AWAY EVENT - SUCCESS": {
					ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
					if (ah.isNoMessagesLabelVisible(10)) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"No messages are displayed in the Activity History screen");
					} else {
						// Keyword.ReportStep_Pass(testCase, "Messages dispalyed in Activity History
						// screen");
						ah.firstMsgFromActivityHistoryListAfterGeofenceCross(testCase, inputs);
						System.out.println(
								"#######1: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE"));
						System.out.println(
								"#######2: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE"));
						System.out.println(
								"#######3: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE"));

						if ((inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
								.equalsIgnoreCase("A MOMENT AGO"))
								|| (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
										.equalsIgnoreCase("ONE MINUTE AGO"))
								|| (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
										.equalsIgnoreCase("TWO MINUTE AGO")
										|| (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
												.equalsIgnoreCase("22 Mar 2019, 7:06 p.m.")))
										&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
												.equalsIgnoreCase("Geofence away event - success"))
										&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE")
												.equalsIgnoreCase("Geofence away event - success"))) {
							flag = true;
							Keyword.ReportStep_Pass(testCase,
									"Message Time after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Sub Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE"));
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Message Time after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Title Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Sub Title after Geofence cross is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE"));

						}
					}
					break;
				}

				default: {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid Input : " + expectedInput.get(0));
				}
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
