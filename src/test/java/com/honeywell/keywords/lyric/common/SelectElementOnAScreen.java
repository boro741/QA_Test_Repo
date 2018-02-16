package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.ZwaveScreen;

public class SelectElementOnAScreen extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectElementOnAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects \"(.*)\" from \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("install device")) {
				switch (parameters.get(0).toUpperCase()) {
				case "Z-WAVE DEVICE": {
					AddNewDeviceScreen ads= new AddNewDeviceScreen(testCase);
					ads.clickOnZwaveFromAddNewDevice();
					break;
				}
				}
			}
			else
				if(parameters.get(1).equalsIgnoreCase("Switch Settings")){
					switch (parameters.get(0).toUpperCase()) {
					case "DELETE": {
						ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
						zwaveScreen.ClickDeleteFromSettings();
						break;
					}
					}
				}
			else
			if (parameters.get(1).equalsIgnoreCase("Entry-Exit Delay")) {
				switch (parameters.get(0).toUpperCase()) {
				case "15": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "15SecondsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "15SecondsOption");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"15 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "30": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "30SecondsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "30SecondsOption");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"30 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "45": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "45SecondsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "45SecondsOption");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"45 seconds Entry/Exit Delay Option not found");
					}
					break;
				}
				case "60": {
					fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "60SecondsOption", 3)) {
						flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "60SecondsOption");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"60 seconds Entry/Exit Delay Option not found");
					}
					break;
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
