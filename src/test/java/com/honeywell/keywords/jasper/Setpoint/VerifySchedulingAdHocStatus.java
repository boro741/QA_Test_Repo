package com.honeywell.keywords.jasper.Setpoint;

import java.util.ArrayList;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.jasper.utils.JasperAdhocOverride;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;
import com.honeywell.lyric.utils.InputVariables;


public class VerifySchedulingAdHocStatus extends Keyword {

	public boolean flag = true;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;

	public VerifySchedulingAdHocStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^verify the override status on the solution card is overridden with set temperature \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		String setPoints = "";
		String systemMode = statInfo.getThermoStatMode();
		if (systemMode.equals("Auto")) {
			systemMode = statInfo.getThermostatModeWhenAutoChangeOverActive();
		}
		if (systemMode.equals("Heat")) {
			setPoints = inputs.getInputValue(InputVariables.OVERRIDE_HEAT_SETPOINTS);
		} else if (systemMode.equals("Cool")) {
			setPoints = inputs.getInputValue(InputVariables.OVERRIDE_COOL_SETPOINTS);
		}else if (systemMode.equalsIgnoreCase("Off")) {
			Keyword.ReportStep_Pass(testCase, "System is in OFF mode and hence Adhoc status is not displayed");
			return flag;
		}
		if (exampleData.get(0).equalsIgnoreCase("till next schedule period")) {
			flag = flag & JasperSchedulingVerifyUtils.verifySchedulingStatusOnPrimaryCard(testCase, inputs, "Time", setPoints);
		} else if (exampleData.get(0).equalsIgnoreCase("while current schedule period")) {
			flag = flag & JasperSchedulingVerifyUtils.verifySchedulingStatusOnPrimaryCard(testCase, inputs, "Geofence",setPoints);
		}
		else if(exampleData.get(0).equalsIgnoreCase("permanently")) {
			flag = flag & JasperAdhocOverride.verifyAdHocHoldPermanentlyStatus(testCase, inputs, setPoints);
		}
		else if(exampleData.get(0).equalsIgnoreCase("till defined hold time")) {
			flag = flag & JasperSchedulingVerifyUtils.verifyHoldUntilStatusOnPrimaryCard(testCase, inputs, inputs.getInputValue(InputVariables.HOLD_UNTIL_TIME));
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
