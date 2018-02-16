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
import com.honeywell.screens.ZwaveScreen;

public class EditDeviceName extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public EditDeviceName(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user edits the (.*) name to (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(0).equalsIgnoreCase("DAS Panel")) {
				fieldObjects = MobileUtils.loadObjectFile(testCase, "DASSettings");
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DASNameTextbox", 5)) {
					//if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						flag = flag & MobileUtils.clearTextField(fieldObjects, testCase, "DASNameTextbox");
					//} else {
						//MobileUtils.getMobElement(fieldObjects, testCase, "DASNameTextbox").clear();
					//}
					flag = flag & MobileUtils.setValueToElement(fieldObjects, testCase, "DASNameTextbox",
							parameters.get(1));
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						flag = flag & MobileUtils.clickOnElement(testCase, "name", "Done");
					}
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Could not find DAS Name Text Box");
				}
			}
			else if(parameters.get(0).equalsIgnoreCase("Switch")){
				ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
				if(zwaveScreen.isNamingFieldDisplayed()){
					zwaveScreen.setNameToSwitch(parameters.get(1));
					zwaveScreen.saveEditedNameToSwitch();
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
