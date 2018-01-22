package com.honeywell.keywords.lyric.chil;

import java.util.ArrayList;
import java.util.List;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.lyric.utils.LyricUtils;

public class ClearAllAlertsThroughCHIL extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public ClearAllAlertsThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user dismisses all alerts and notification through CHIL$")
	public boolean keywordSteps() throws KeywordException {
		for (int i = 0; i < 100; i++) {
			try {
				List<Long> alertIDS = new ArrayList<Long>();
				alertIDS = LyricUtils.getAllAlertIDS(testCase, inputs);
				if (alertIDS.isEmpty()) {
					System.out.println("Alerts Cleared");
					break;
				} else {
					System.out.println("Clearing alerts with the IDS: "
							+ alertIDS);
					LyricUtils.dismissAllAlerts(testCase, inputs);
				}
			} catch (Exception e) {
				break;
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
