 package com.honeywell.keywords.jasper.AdhocOverride.TempPerm;

import java.util.ArrayList;


import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.screens.AdhocScreen;


public class TemporaryPermanentHold extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public TemporaryPermanentHold(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user has \"(.+)\" status$")
	public boolean keywordSteps() throws KeywordException{
		try {
			AdhocScreen adhocScreen = new AdhocScreen(testCase);
			if (exampleData.get(0).equalsIgnoreCase("TEMPORARY"))
			{
				switch (exampleData.get(0).toUpperCase()) 
				{
				case "TEMPORARY" :
				{
					flag = flag & JasperAdhocOverride.GetTemporaryHold(testCase, inputs);
					/*if(adhocScreen.getAdhocStatusElement().toUpperCase().contains("HOLD UNTIL")){
						ReportStep_Pass(testCase, "In "+ exampleData.get(0));
					}else{
						flag = false;
						Keyword.ReportStep_Fail(testCase,
						FailType.FUNCTIONAL_FAILURE,"Not in " +  exampleData.get(0));
					}*/
					
				}break;
				}
			}else if (exampleData.get(0).equalsIgnoreCase("Permanent")) 
			{
				switch (exampleData.get(0).toUpperCase()) {
				case "PERMANENT" : 
				{
					flag = flag & JasperAdhocOverride.GetTemporaryHold(testCase, inputs);
					flag = flag & JasperAdhocOverride.holdSetPointsPermanentlyFromAdHoc(testCase);
					/*if(adhocScreen.getAdhocStatusElement().toUpperCase().contains(exampleData.get(0).toUpperCase())){
						ReportStep_Pass(testCase, "In "+ exampleData.get(0));
					}else{
						flag = false;
						Keyword.ReportStep_Fail(testCase,
						FailType.FUNCTIONAL_FAILURE,"Not in " +  exampleData.get(0));
					}*/
				}break;
				}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase,
			FailType.FUNCTIONAL_FAILURE,"Error Occured : " + e.getMessage());
			}
		return flag;
	}
	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}

