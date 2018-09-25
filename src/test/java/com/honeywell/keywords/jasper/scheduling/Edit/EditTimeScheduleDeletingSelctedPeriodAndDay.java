package com.honeywell.keywords.jasper.scheduling.Edit;


import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperSchedulingUtils;
import com.honeywell.lyric.utils.InputVariables;

public class EditTimeScheduleDeletingSelctedPeriodAndDay extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public EditTimeScheduleDeletingSelctedPeriodAndDay(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user edit Time schedule by deleting \"(.+)\" of \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		if (exampleData.get(1).equalsIgnoreCase("Wake")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "Wake");
		}
		else if (exampleData.get(1).equalsIgnoreCase("Away")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "Away");
		} else if (exampleData.get(1).equalsIgnoreCase("Home")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "Home");
		} else if (exampleData.get(1).equalsIgnoreCase("Sleep")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "Sleep");
		}
		else if (exampleData.get(1).equalsIgnoreCase("P1")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "1");
		}
		else if (exampleData.get(1).equalsIgnoreCase("P2")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "2");
		} else if (exampleData.get(1).equalsIgnoreCase("P3")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "3");
		} else if (exampleData.get(1).equalsIgnoreCase("P4")) {
			inputs.setInputValue(InputVariables.PERIOD_NAME_NA, "4");
		}

		
		
		if (exampleData.get(0).equalsIgnoreCase("Monday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Monday");
			}
		else if (exampleData.get(0).equalsIgnoreCase("Tuesday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Tuesday");
		} else if (exampleData.get(0).equalsIgnoreCase("Wednesday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Wednesday");
		} else if (exampleData.get(0).equalsIgnoreCase("Thursday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Thursday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Friday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Friday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Saturday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Saturday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Sunday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Sunday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Monday - Friday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Monday - Friday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Saturday - Sunday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Saturday - Sunday");
		}
		else if (exampleData.get(0).equalsIgnoreCase("Everyday")) {
			inputs.setInputValue(InputVariables.SCHEDULE_DAY_EDITED, "Everyday");
		}
		
		
		flag = flag & JasperSchedulingUtils.clickOnDeleteIconForSelectedPeriodAndDayNA(testCase, inputs);
		

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
