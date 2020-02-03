package com.honeywell.keywords.lumina.wld;

import java.util.ArrayList;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.resideo.lumina.utils.LuminaUtils;

import pro.truongsinh.appium_flutter.FlutterFinder;

public class HumidityTimeStamp extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public DataTable dataTable;
	public boolean flag = true;
	public String[][] eventsList;
	public ArrayList<String> screen;



	public HumidityTimeStamp(TestCases testCase, TestCaseInputs inputs, DataTable dataTable) {
		this.testCase = testCase;
		this.screen = screen;
		this.dataTable = dataTable;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should see the following in WLD main screen:$")
	public boolean keywordSteps() throws KeywordException {
		System.out.println("Hello world");
		LuminaUtils lumina = new LuminaUtils(inputs, testCase);
		for (int i = 0; i < dataTable.getSize(); i++) {
			switch (dataTable.getData(i, "Device Home").trim().toUpperCase()) {
			
			case "HUMIDITY VALUE":{
				System.out.println("HUMIDITY");
				lumina.VerifyScreen(dataTable.getData(i, "Device Home").toUpperCase());
				break;
			}
			case "LAST UPDATED":{
				System.out.println("Last Updated");
				lumina.VerifyScreen(dataTable.getData(i, "Device Home").toUpperCase());
				break;
			}
			case "NEXT UPDATED":{
				System.out.println("Next Updated");
				lumina.VerifyScreen(dataTable.getData(i, "Device Home").toUpperCase());
				break;
			}
			case "TEMPERATURE VALUE":{
				System.out.println("TEMPERATURE");
				lumina.VerifyScreen(dataTable.getData(i, "Device Home").toUpperCase());
				break;
			}
			default:{
				flag = false;
			}

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
