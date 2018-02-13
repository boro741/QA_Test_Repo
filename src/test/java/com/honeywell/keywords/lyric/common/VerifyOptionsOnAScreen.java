package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;

public class VerifyOptionsOnAScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyOptionsOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the following (.*) options:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "SECURITY SETTINGS": {
			fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
			for (int i = 0; i < data.getSize(); i++) {
				String attribute = "";
				String fieldTobeVerified = data.getData(i, "Settings");
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					attribute = "text";
				} else {
					attribute = "value";
					if (data.getData(i, "Settings").equalsIgnoreCase("Geofencing")) {
						fieldTobeVerified = "Geofence";
					}
				}
				try {
					if (fieldTobeVerified.equalsIgnoreCase("Key Fob")
							|| fieldTobeVerified.equalsIgnoreCase("Sensors")) {
						flag = flag & LyricUtils.scrollToElementUsingAttributeSubStringValue(testCase, attribute,
								fieldTobeVerified);
					} else {
						flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute,
								fieldTobeVerified);
					}
					if (flag) {
						Keyword.ReportStep_Pass(testCase,
								"Settings: '" + fieldTobeVerified + "' is present on the DAS Settings screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
			break;
		}
		case "ENTRY-EXIT DELAY": {
			fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
			String attribute = "";
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				attribute = "text";
			} else {
				attribute = "value";
			}
			for (int i = 0; i < data.getSize(); i++) {
				try {
					flag = flag & LyricUtils.scrollToElementUsingExactAttributeValue(testCase, attribute,
							data.getData(i, "Delays"));
					if (flag) {
						Keyword.ReportStep_Pass(testCase, "Option: '" + data.getData(i, "Delays")
								+ "' is present on the Entry/Exit Delay screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}

			}
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
