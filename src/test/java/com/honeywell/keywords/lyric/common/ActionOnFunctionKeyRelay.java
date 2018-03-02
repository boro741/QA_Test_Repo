package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;

public class ActionOnFunctionKeyRelay extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> deviceType;

	public ActionOnFunctionKeyRelay(TestCases testCase, TestCaseInputs inputs, ArrayList<String> deviceType) {
		this.testCase = testCase;
		this.deviceType = deviceType;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) the (.*) function key$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if(deviceType.get(0).equalsIgnoreCase("activates")){
				if (deviceType.get(1).equalsIgnoreCase("Switch")) {
					Keyword.ReportStep_Pass(testCase, "Activating function key on Switch");
					ZWaveRelayUtils.enrollZwaveSwitch1();
					TimeUnit.SECONDS.sleep(2);
					ZWaveRelayUtils.pressButtonOnSwitch1();
				}
				else if (deviceType.get(1).equalsIgnoreCase("Dimmer")) {
					Keyword.ReportStep_Pass(testCase, "Activating function key on Dimmer");
					ZWaveRelayUtils.enrollZwaveDimmer1();
					TimeUnit.SECONDS.sleep(2);
					ZWaveRelayUtils.pressButtonOnDimmer1();
				}
			}else if(deviceType.get(0).equalsIgnoreCase("does not activate")){
				DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
			}else if(deviceType.get(0).equalsIgnoreCase("disconnects")){
				if (deviceType.get(1).equalsIgnoreCase("Switch power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Switch");
					ZWaveRelayUtils.powerOffZwaveSwitch(inputs);
				}
				else if (deviceType.get(1).equalsIgnoreCase("Dimmer power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Dimmer");
					ZWaveRelayUtils.powerOffZwaveDimmer(inputs);
				}
			}else if(deviceType.get(0).equalsIgnoreCase("connects")){
				if (deviceType.get(1).equalsIgnoreCase("Switch power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Switch");
					ZWaveRelayUtils.powerOnZwaveSwitch(inputs);
				}
				else if (deviceType.get(1).equalsIgnoreCase("Dimmer power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Dimmer");
					ZWaveRelayUtils.powerOnZwaveDimmer(inputs);
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
