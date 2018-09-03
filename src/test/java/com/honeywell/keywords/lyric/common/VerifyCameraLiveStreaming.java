package com.honeywell.keywords.lyric.common;

import java.time.Duration;
import java.time.Instant;
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
import com.honeywell.screens.CameraSolutionCardScreen;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.PrimaryCard;

public class VerifyCameraLiveStreaming extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyCameraLiveStreaming(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be shown with live streaming for 90 seconds and stopped$")
	public boolean keywordSteps() throws KeywordException {
		Instant starts=Instant.now();
		Instant ends=Instant.now();
		CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
		if (cs.isCameraPlayButtonExists(90)) {
			cs.clickOnCameraPlayButton();
		if (cs.isLiveStreamProgressBarExists()) {
		 starts = Instant.now();
			if (cs.isCameraPlayButtonExists(90)) {
			 ends = Instant.now();
			}
			
		}
		System.out.println(Duration.between(starts, ends));
		
		}
		
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}