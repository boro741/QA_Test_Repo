package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DIYRegistrationUtils;

public class TurnOffBlueToothInAndroidDevice extends Keyword {

	private TestCases testCase;
	public ArrayList<String> expectedCondition;

	public boolean flag = true;

	public TurnOffBlueToothInAndroidDevice(TestCases testCase, TestCaseInputs inputs,
			ArrayList<String> expectedCondition) {
		this.testCase = testCase;
		this.expectedCondition = expectedCondition;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user turns \"(.*)\" mobile device Bluetooth$")
	public boolean keywordSteps() {
		switch (expectedCondition.get(0).toUpperCase()) {
		case "OFF": {
			try {
				DIYRegistrationUtils.turnOffDeviceBluetooth(testCase);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "ON": {
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Invalid condition " + expectedCondition.get(0));
			return flag;

		}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
