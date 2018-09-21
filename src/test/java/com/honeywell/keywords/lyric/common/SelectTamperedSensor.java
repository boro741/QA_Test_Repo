
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
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASSensorUtils;
import com.honeywell.screens.SensorStatusScreen;

public class SelectTamperedSensor extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;

	public SelectTamperedSensor(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.parameters = parameters;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects \"(.+)\" that is \"(.+)\" from \"(.+)\" screen$")

	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(2).equalsIgnoreCase("Sensors List") || parameters.get(2).equalsIgnoreCase("Sensors Status")) {
				
				switch (parameters.get(0).toUpperCase()) {
				case "DOOR SENSOR": 
				case "Window SENSOR": 
				case "ISMV SENSOR": 
				case "OSMV SENSOR": 	 	
				case "MOTION SENSOR": {
					SensorStatusScreen sc = new SensorStatusScreen(testCase);
					DASSensorUtils dss = new DASSensorUtils();
					if (parameters.get(1).equalsIgnoreCase("Tampered")) {
					flag = flag & sc.selectTamperedClear(testCase, inputs, parameters.get(0));
					}else if(parameters.get(1).equalsIgnoreCase("Low Battery")){
						flag = flag & dss.verifySensorState(testCase, inputs, "ISMV", "Low Battery");
					}
					break;
			}
		} 
			}
		}catch (Exception e) {
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
