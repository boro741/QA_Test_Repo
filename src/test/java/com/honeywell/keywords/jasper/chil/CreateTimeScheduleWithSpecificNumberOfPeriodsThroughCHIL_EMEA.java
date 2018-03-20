package com.honeywell.keywords.jasper.chil;

import java.util.ArrayList;

import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.InputVariables;

public class CreateTimeScheduleWithSpecificNumberOfPeriodsThroughCHIL_EMEA extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public CreateTimeScheduleWithSpecificNumberOfPeriodsThroughCHIL_EMEA(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user has \"(.+)\" with \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (exampleData.get(1).equalsIgnoreCase("1 period")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "One");

			} else if (exampleData.get(1).equalsIgnoreCase("2 periods")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "Two");

			} else if (exampleData.get(1).equalsIgnoreCase("3 periods")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "Three");

			} else if (exampleData.get(1).equalsIgnoreCase("4 periods")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "Four");

			} else if (exampleData.get(1).equalsIgnoreCase("5 periods")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "Five");

			} else if (exampleData.get(1).equalsIgnoreCase("6 periods")) {
				inputs.setInputValue(InputVariables.EMEA_TIME_SCHEDULE_NUMBER_OF_PERIODS_IN_A_DAY, "Six");

			}

			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String deviceID = statInfo.getDeviceID();
			String jasperStatType = statInfo.getJasperDeviceType();

			if (!statInfo.getThermostatType().equalsIgnoreCase("HoneyBadger")) {

				if (!jasperStatType.equalsIgnoreCase("NA")) {

					if (exampleData.get(0).equalsIgnoreCase("time schedule")) {
						inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
						inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);
						inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
					}

					try {
						if (chUtil.getConnection()) {
							if (chUtil.createTimeScheduleWithSpecificNumberOfPeriods_EMEA(inputs,
									chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME")), deviceID) == 200) {
								Keyword.ReportStep_Pass(testCase,
										"Create Schedule Using CHIL : Successfully created time schedule through CHIL for "
												+ exampleData.get(1) + " per day");
							} else {
								flag = false;
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
										"Create Schedule Using CHIL : Failed to create time schedule using CHIL for "
												+ exampleData.get(1) + " per day");
							}
						}
					} catch (Exception e) {
						flag = false;
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Error Occured : " + e.getMessage());
					}
				} else {
					ReportStep_Pass(testCase, "Expected is EMEA account type but using: " + jasperStatType);
				}
			} else {
				ReportStep_Pass(testCase, "Expected is EMEA account type but using: " + statInfo.getThermostatType());
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}