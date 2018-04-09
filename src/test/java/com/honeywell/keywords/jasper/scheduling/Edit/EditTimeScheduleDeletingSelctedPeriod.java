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

public class EditTimeScheduleDeletingSelctedPeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public EditTimeScheduleDeletingSelctedPeriod(TestCases testCase, TestCaseInputs inputs,
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
	@KeywordStep(gherkins = "^user edit Time schedule by deleting \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {

		if (exampleData.get(0).equalsIgnoreCase("Atleast 1 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "ONE");
		} else if (exampleData.get(0).equalsIgnoreCase("Atleast 2 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "TWO");
		} else if (exampleData.get(0).equalsIgnoreCase("Atleast 3 period")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "THREE");
		} else if (exampleData.get(0).equalsIgnoreCase("All 4 periods")) {
			inputs.setInputValue(InputVariables.NUMBER_OF_PERIODS_TO_DELETE, "FOUR");
		}

		flag = flag & JasperSchedulingUtils.clickOnDeleteIconForSelectedPeriodNA(testCase, inputs);

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
