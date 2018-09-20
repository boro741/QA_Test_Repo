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
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CameraSolutionCardScreen;

public class VerifyCameraLiveStreaming extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedCondition;
	public boolean flag = true;
	public DataTable data;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyCameraLiveStreaming(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedCondition) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedCondition = expectedCondition;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user Camera \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
		switch (expectedCondition.get(0)) {
		case "streaming for 90 sec": {
			Instant starts = Instant.now();
			Instant ends = Instant.now();
			
			if (cs.isCameraPlayButtonExists(3)) {
				cs.clickOnCameraPlayButton();
			}
			if (cs.isLiveStreamProgressBarExists(50)) {
				starts = Instant.now();
				if (cs.isCameraPlayButtonExists(90)) {
					ends = Instant.now();
				}

			} else {
				if (cs.isCameraPlayButtonExists(3)) {
					cs.clickOnCameraPlayButton();
				}
				if (cs.isLiveStreamProgressBarExists(50)) {
					starts = Instant.now();
					if (cs.isCameraPlayButtonExists(90)) {
						ends = Instant.now();
					}

				}
			}
			// Example : PT1M29.681S = 1 minute 29 seconds

			Duration duration = Duration.between(starts, ends);
			System.out.println(Duration.between(starts, ends));
			if (duration.toString().contains("1M") && duration.toString().contains(".")) {
				// Example : PT1M29.681S = 1 minute 29 seconds
				String[] dur1 = duration.toString().split("M");
				if ((dur1[0].contains("1") && (int) (Double.parseDouble(dur1[1].replace("S", ""))) > 20)
						|| dur1[0].contains("1")) {
					Keyword.ReportStep_Pass(testCase, "Camera is live streamed for 90 seconds");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Camera is not live streamed for 90 seconds");
					flag = false;
				}
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera live stream is not happening");
				flag = false;
			}
			break;
		}
		case "not streaming": {
			if (cs.isCameraPlayButtonExists(90)) {

				Keyword.ReportStep_Pass(testCase, "Camera is not strereaming");
			} else {
				if (cs.isLiveStreamProgressBarExists(40)) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera is Streaming");
					flag = false;
				} else {
					Keyword.ReportStep_Pass(testCase, "Camera is not strereaming");
				}

			}
			break;
		}
		case "should be streaming": {
				if (cs.isLiveStreamProgressBarExists(40)) {
					Keyword.ReportStep_Pass(testCase, "Camera is not strereaming");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Camera is Streaming");
					flag = false;
				}

			break;
		}
		default: {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Input data data does not match");
			flag = false;
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