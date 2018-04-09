package com.honeywell.keywords.jasper.scheduling.Edit;

import java.util.ArrayList;
import java.util.Random;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSchedulingEditUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;

public class EditTimeScheduleByDeletingPeriods extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;
	String schedulePeriodToSelect = "", temp = "";
	int i = 0;
	ArrayList<String> arrlist = new ArrayList<String>(8);

	public EditTimeScheduleByDeletingPeriods(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user edit Time schedule by deleting \"(.+)\" on confirming the period deletion$")
	public boolean keywordSteps() throws KeywordException {
		try {
		if (exampleData.get(0).equalsIgnoreCase("Atleast 1 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "ONE");
			i = 1;
		} else if (exampleData.get(0).equalsIgnoreCase("Atleast 2 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "TWO");
			i = 2;
		} else if (exampleData.get(0).equalsIgnoreCase("Atleast 3 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "THREE");
			i = 3;
		} else if (exampleData.get(0).equalsIgnoreCase("All 4 periods")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "FOUR");
			i = 4;
		}

		String[] schedulePeriods = { "Wake", "Away", "Home", "Sleep" };
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		Random rn = new Random();

		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String jasperStatType = statInfo.getJasperDeviceType();
		if (!statInfo.isOnline()) {
			Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
			return true;
		}
		inputs.setInputValue(InputVariables.TYPE_OF_SCHEDULE, InputVariables.TIME_BASED_SCHEDULE);
		inputs.setInputValue(InputVariables.TYPE_OF_TIME_SCHEDULE, InputVariables.EVERYDAY_SCHEDULE);

		if (statInfo.getThermostatType().equals("Jasper")) {
			inputs.setInputValue(InputVariables.UNITS, statInfo.getThermostatUnits());
			inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);

			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				for (int j = 1; j <= i; j++) {
					do {
						if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
							schedulePeriodToSelect = schedulePeriods[rn.nextInt((3 - 0) + 1) + 0] + "_Everyday";

						} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
							schedulePeriodToSelect = schedulePeriods[rn.nextInt((3 - 0) + 1) + 0] + "_"
									+ days[rn.nextInt((6 - 0) + 1) + 0];
						}
					} while (arrlist.contains(schedulePeriodToSelect));
					arrlist.add(schedulePeriodToSelect);

					flag = flag
							& JasperSchedulingEditUtils.editTimeBasedScheduleByDeletingPeriods(testCase, inputs, schedulePeriodToSelect,j);
				}

			} else{
				for (int j = 1; j <= i; j++) {
					do {
						if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
							schedulePeriodToSelect = "Everyday_"+schedulePeriods[rn.nextInt((3 - 0) + 1) + 0];

						} else if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Individual Days")) {
							schedulePeriodToSelect = days[rn.nextInt((6 - 0) + 1) + 0]+"_"+schedulePeriods[rn.nextInt((3 - 0) + 1) + 0];
						}
					} while (arrlist.contains(schedulePeriodToSelect));
					arrlist.add(schedulePeriodToSelect);

					flag = flag
							& JasperSchedulingEditUtils.editTimeBasedScheduleByDeletingPeriods(testCase, inputs, schedulePeriodToSelect,j);
				}
			}
			flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
		}
		} catch (Exception e){
			
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
