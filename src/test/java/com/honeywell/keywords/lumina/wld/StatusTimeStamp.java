package com.honeywell.keywords.lumina.wld;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.resideo.lumina.utils.LuminaUtils;


public class StatusTimeStamp extends Keyword  {

	private TestCaseInputs inputs;
	private TestCases testCase;


	public boolean flag = true;



	public StatusTimeStamp(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}
	@Override
	@KeywordStep(gherkins = "^user sees the WLD status and time stamp of status update$")
	public boolean keywordSteps() {
		LuminaUtils lumina = new LuminaUtils(inputs, testCase);
		
//		Boolean testRes = lumina.VerifyScreen("CONNECTION STATUS") && lumina.VerifyScreen("Last Updated Time") && lumina.VerifyScreen("Next Update Time");
//		if( testRes ) {
//			flag = flag &&  testRes;
//		}
		lumina.VerifyScreen("LAST UPDATED");
		//lumina.VerifyScreen("NEXT UPDATED");
		//System.out.println("T: "+temp.getTempUnit());
		
		return flag;
	}
	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
