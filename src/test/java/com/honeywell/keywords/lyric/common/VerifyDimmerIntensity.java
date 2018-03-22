package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.screens.ZwavePrimardCardScreen;

public class VerifyDimmerIntensity extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	private ArrayList<String> parameters;
	public boolean flag = true;

	public VerifyDimmerIntensity(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
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
	@KeywordStep(gherkins = "^user should be displayed with (.*) intensity on the (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (parameters.get(1).equalsIgnoreCase("ZWAVE device")) {
				
				int intensityToBeVerified = Integer.parseInt(parameters.get(0).split("%")[0].split("~")[1]);
				String range =verifyRange(intensityToBeVerified); 
				if (!range.equals("Not In Range")) {
					Keyword.ReportStep_Pass(testCase,
							"Dimmer Intensity is between the range : " + range + " on ZWAVE device");
				} else {
					if (!range.equals("Not In Range")) {
						Keyword.ReportStep_Pass(testCase,
								"Dimmer Intensity is between the range : " + range + " on ZWAVE device");
					}
					else
					{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Dimmer Intensity is not between the expected range");
					}
				}
			}
			else if(parameters.get(1).equalsIgnoreCase("application"))
			{
				ZwavePrimardCardScreen zps = new ZwavePrimardCardScreen(testCase);
				int intensityToBeVerified = Integer.parseInt(parameters.get(0).split("%")[0].split("~")[1]);
				int displayedIntensity = Integer.parseInt(zps.getDimmerStatus().split("%")[0]);
				if((intensityToBeVerified == displayedIntensity) || (intensityToBeVerified >= displayedIntensity-5 && intensityToBeVerified <= displayedIntensity + 5))
				{
					Keyword.ReportStep_Pass(testCase, "Dimmer Intensity is correctly displayed on the application");
				}
				else
				{
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"Dimmer Intensity is not correctly displayed on the application");
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
	
	public static String verifyRange(int intensityToBeVerified) throws Exception
	{
		String range = ZWaveRelayUtils.getDimmerIntensityRange();
		System.out.println("Range : " + range);
		System.out.println("Intensity To Verify " + intensityToBeVerified);
		int rangeMin = Integer.parseInt(range.split("-")[0]);
		System.out.println("Range Min " + rangeMin);
		int rangeMax = Integer.parseInt(range.split("-")[1]);
		System.out.println("Range Max " + rangeMax);
		if (intensityToBeVerified <= rangeMax && intensityToBeVerified >= rangeMin) {
			return range;
		}
		else
		{
			return "Not In Range";
		}
	}
}
