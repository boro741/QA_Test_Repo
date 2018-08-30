package com.honeywell.keywords.flycatcher.Dehumidifier;

	import java.util.ArrayList;

	import com.honeywell.account.information.DeviceInformation;
	import com.honeywell.commons.coreframework.AfterKeyword;
	import com.honeywell.commons.coreframework.BeforeKeyword;
	import com.honeywell.commons.coreframework.Keyword;
	import com.honeywell.commons.coreframework.KeywordStep;
	import com.honeywell.commons.coreframework.TestCaseInputs;
	import com.honeywell.commons.coreframework.TestCases;
	import com.honeywell.commons.report.FailType;
import com.honeywell.keywords.flycatcher.Humidifier.FlyHumidifier;

	public class SetDeHumidification extends Keyword {

		private TestCases testCase;
		private TestCaseInputs inputs;
		ArrayList<String> exampleData;
		public boolean flag = true;

		public SetDeHumidification(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
			super("Edit Stop Ventilation Mode");
			this.inputs = inputs;
			this.testCase = testCase;
			this.exampleData = exampleData;
		}

		@Override
		@BeforeKeyword
		public boolean preCondition() {
			return flag;
		}

		@Override
		@KeywordStep(gherkins = "^user Sets the Dehumidification \"(.+)\"$")
		public boolean keywordSteps() {
			try {
				String DehumdificationValue = exampleData.get(0);
				DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
				String DeHumidificationtatus = statInfo.getThermoStatDeHumidificationSettings();
				if (DeHumidificationtatus != null){
					flag = flag && FlyHumidifier.setDeHumdifier(testCase,inputs,DehumdificationValue);
				}else {
					flag = false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"DeHumidifier is disabled in thermostat ");
				}
			} catch (Exception e){
				flag = false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured : " + e.getMessage());
			}
			return flag;
		}

		/*
		 * ========================================After
		 * method============================================================= Check
		 * if the Keyword has been executed successfully.
		 * =========================================================================
		 * ==========================================
		 */
		@Override
		@AfterKeyword
		public boolean postCondition() {

			try {
				if (flag) {
					ReportStep_Pass(testCase, "Set Humidifier : Keyword successfully executed");
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Set DeHumidifier : Keyword failed during execution");
				}
			} catch (Exception e) {
				flag = false;
				ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Set DeHumidifier : Error Occured while executing post-condition : " + e.getMessage());
			}
			return flag;
		}

	}


