package com.honeywell.lyric.Katana.diyregistration;


import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.keywords.lyric.Katana.utils.ProMonitoringUtils;

public class VerifyProMonitoringInfo extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyProMonitoringInfo(TestCases testCase, TestCaseInputs inputs) {
		 this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Verify Pro Monitoring Information From AN60$")
	public boolean keywordSteps() {
		try {
			String dealer = ProMonitoringUtils.getHomeOwnerInfo(testCase, inputs);
			System.out.println(dealer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}