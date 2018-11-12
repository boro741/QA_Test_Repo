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

public class VerifyIfMsgIsDeletedInActivityHistoryList extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedInput;
	public boolean flag = true;

	public VerifyIfMsgIsDeletedInActivityHistoryList(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^\"(.*)\" should not be displayed in the \"(.+)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (expectedInput.get(1).equalsIgnoreCase("ACTIVITY HISTORY")) {
				switch (expectedInput.get(0).toUpperCase()) {
				case "DELETED MESSAGE": {
					ActivityHistoryScreen ah = new ActivityHistoryScreen(testCase);
					if (ah.isNoMessagesLabelVisible(10)) {
						Keyword.ReportStep_Pass(testCase,
								"After deleting a message, Activity History List is empty. No Messages label is displayed");
					} else {
						Keyword.ReportStep_Pass(testCase, "Messages dispalyed in Activity History screen");
						ah.firstMsgFromActivityHistoryListAfterDelete(testCase, inputs);
						System.out.println(
								"#######1: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE"));
						System.out.println(
								"#######2: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE"));
						System.out.println(
								"#######3: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE"));
						System.out.println(
								"#######4: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE"));
						System.out.println(
								"#######5: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE"));
						System.out.println(
								"#######6: " + inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE"));

						if ((inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
								.equalsIgnoreCase(inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE")))
								&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
										.equalsIgnoreCase(
												inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE")))
								&& (inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE")
										.equalsIgnoreCase(inputs
												.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE")))) {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Message Time before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Time after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE")
											+ ". Message Title before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Title after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE")
											+ ". Message Detail before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE")
											+ ". Message Detail after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE"));
						} else {
							Keyword.ReportStep_Pass(testCase,
									"Message Time before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_BEFORE_DELETE")
											+ ". Message Time after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TIME_AFTER_DELETE")
											+ ". Message Title before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_BEFORE_DELETE")
											+ ". Message Title after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_TITLE_AFTER_DELETE")
											+ ". Message Detail before delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_BEFORE_DELETE")
											+ ". Message Detail after delete is: "
											+ inputs.getInputValue("FIRST_ACTIVITY_HISTORY_MSG_DETAIL_AFTER_DELETE"));
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
