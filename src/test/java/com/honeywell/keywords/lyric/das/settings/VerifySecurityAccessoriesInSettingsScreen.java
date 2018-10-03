package com.honeywell.keywords.lyric.das.settings;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.BaseStationSettingsScreen;

public class VerifySecurityAccessoriesInSettingsScreen extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;

	public VerifySecurityAccessoriesInSettingsScreen(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should see (.*) with count as (.*) and (.*)$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedOption.get(0).toUpperCase()) {
		case "KEY FOB": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			String totalKeyFobsEnrolled = null;
			try {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bs.isKeyFobOptionVisible()) {
				totalKeyFobsEnrolled = bs.getTotalKeyFobsEnrolledCount();
			}
			if (expectedOption.get(1).equals(totalKeyFobsEnrolled)) {
				Keyword.ReportStep_Pass(testCase, "Total enrolled Key Fob devices are: " + totalKeyFobsEnrolled
						+ " which is same as expected: " + expectedOption.get(1));
				if (expectedOption.get(2).equalsIgnoreCase("NO ISSUES")) {
					if (!bs.isKeyFobIssuesLabelVisibleInSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase,
								"Key Fob No Issues label present is: " + expectedOption.get(2));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Key Fob No Issues label present is: " + expectedOption.get(2));
					}
				} else if (expectedOption.get(2).matches(".*\\\\d+.*")) {
					if (bs.isKeyFobIssuesLabelVisibleInSettingsScreen()
							&& bs.getKeyFobIssuesCount().equals(expectedOption.get(2))) {
						Keyword.ReportStep_Pass(testCase,
								"Key fob issues count displayed is same as expected: " + expectedOption.get(2));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Key fob issues count displayed is: " + bs.getKeyFobIssuesCount()
										+ ". It is not same as expected: " + expectedOption.get(2));
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid input: " + expectedOption.get(2));
				}
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Total enrolled Key Fob devices are: "
						+ totalKeyFobsEnrolled + " which is not same as expected: " + expectedOption.get(1));
			}
			break;
		}
		case "SENSORS": {
			BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
			String totalSensorsEnrolled = null;
			try {
				flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase,
						testCase.getPlatform().toUpperCase().contains("ANDROID") ? "text" : "value",
						BaseStationSettingsScreen.BASESTATIONCONFIGURATION);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (bs.isSensorsOptionVisible()) {
				totalSensorsEnrolled = bs.getTotalSensorsEnrolledCount();
			}
			if (expectedOption.get(1).equals(totalSensorsEnrolled)) {
				Keyword.ReportStep_Pass(testCase, "Total enrolled Sensors devices are: " + totalSensorsEnrolled
						+ " which is same as expected: " + expectedOption.get(1));
				if (expectedOption.get(2).equalsIgnoreCase("NO ISSUES")) {
					if (!bs.isSensorsIssuesLabelVisibleInSettingsScreen()) {
						Keyword.ReportStep_Pass(testCase, "Sensors Issues label is present: " + expectedOption.get(2));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sensors Issues label is present: " + expectedOption.get(2));
					}
				} //else if (expectedOption.get(2).matches(".*\\\\d+.*")) {
				else if (bs.isSensorsIssuesLabelVisibleInSettingsScreen()
							&& bs.getSensorsIssuesCount().equals(expectedOption.get(2) + " issue(s)")) {
						Keyword.ReportStep_Pass(testCase,
								"Sensor issues count displayed is same as expected: " + expectedOption.get(2));
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Sensor issues count displayed is: " + bs.getKeyFobIssuesCount()
										+ ". It is not same as expected: " + expectedOption.get(2));
					}
				/*} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Invalid input: " + expectedOption.get(2));
				}*/
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Total enrolled Sensors devices are: "
						+ totalSensorsEnrolled + " which is not same as expected: " + expectedOption.get(1));
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedOption.get(0));
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
