
package com.honeywell.keywords.lyric.chil;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.DeviceInformation;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;

public class EnrollThermostatToDR extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;

	public boolean flag = true;

	public EnrollThermostatToDR(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
			try {
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			if(!statInfo.isOnline())
			{
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Thermostat - "+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " is Offline.");
				testCase.getMobileDriver().quit();
				testCase.setMobileDriver(null);
				return flag;
			}
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			long locationID = locInfo.getLocationID();
			String deviceID = statInfo.getDeviceID();
		if (deviceID.contains("LCC") && chUtil.getConnection()) {
			int result = chUtil.disableVacation(locationID, deviceID);
		if (result == 200) {
			Keyword.ReportStep_Pass(testCase,
					"Activate Vacation Using CHIL : Successfully disabled vacation using CHIL");
			} else {
				flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Activate Vacation Using CHIL : Failed to disable vacation using CHIL");
				}
			}
			if (statInfo.getThermoStatMode().equalsIgnoreCase("Off")) {
				Keyword.ReportStep_Pass(testCase,
						"Enroll Thermostat To DR : Thermostat is in Off mode. Changing system mode to cool");
				if (chUtil.getConnection()) {
					int result = chUtil.changeSystemMode(locationID, deviceID, "Cool");
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase,
								"Change System Mode Using CHIL : Successfully changed system mode to Cool");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Change System Mode Using CHIL : Failed to change system mode to Cool");
					}
				}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user thermostat is enrolled with DR$")
	public boolean keywordSteps() {
		try {
			LocationInformation locInfo = new LocationInformation(testCase, inputs);
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String result = CHILUtil.putDREnrollement(inputs, statInfo.getDeviceID(), statInfo.getDeviceMacID(),
					String.valueOf(locInfo.getLocationID()));
		//	String result = CHILUtil.putDREnrollement(inputs, "TCC-1105351", "00D02D4DBC15","569086");
			if (result.contains("Failed")) {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Enroll Thermostat To DR : " + result);
			} else {
				Keyword.ReportStep_Pass(testCase, "Enroll Thermostat To DR : " + result);
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Enroll Thermostat To DR : Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {

		return flag;
	}

}
