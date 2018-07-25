package com.honeywell.keywords.jasper.scheduling.Verify;

import java.util.HashMap;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperSchedulingVerifyUtils;


public class VerifyPrimaryCardOptions extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public DataTable dataTable;
	public HashMap<String, MobileObject> fieldObjects;

	public VerifyPrimaryCardOptions(TestCases testCase, TestCaseInputs inputs, DataTable dataTable) {
		this.inputs = inputs;
		this.testCase = testCase;
		this.dataTable = dataTable;
		this.fieldObjects = MobileUtils.loadObjectFile(testCase, "PrimaryCard");
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies the following on the primary card:$")
	public boolean keywordSteps() {
		DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
		for (int i = 0; i < dataTable.getSize(); i++) {
			switch (dataTable.getData(i, "Elements")) {
			case "Indoor Temperature":
				flag = flag & JasperSchedulingVerifyUtils.verifyIndoorTemperature(testCase, inputs);
				break;
			case "Indoor Humidity":
				break;
			case "System Mode":
				flag = flag & JasperSchedulingVerifyUtils.verifySystemMode(testCase, inputs);
				break;
			case "Set Points":
				flag = flag & JasperSchedulingVerifyUtils.verifyDisplayedSetPoints(testCase, inputs);
				break;
			case "Available system modes":
				flag = flag & JasperSchedulingVerifyUtils.verifyAvailableSystemModes(testCase, inputs);
				break;
			case "Following schedule": {

				String systemMode = statInfo.getThermoStatMode();
				if (systemMode.equals("Off")) {
					Keyword.ReportStep_Pass(testCase, "Scheduling status will not be shown when system mode if OFF");
				} else {
					this.fieldObjects = MobileUtils.loadObjectFile(testCase, "AdHocOverride");
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "ThermostatSchedule", 30)) {
						Keyword.ReportStep_Pass(testCase, "Scheduling status displayed");
						String currentText=null;
						if(testCase.getPlatform().toUpperCase().equals("IOS")){
							currentText = MobileUtils.getMobElement(fieldObjects, testCase, "ThermostatSchedule")
									.getAttribute("label");
						}else{
							currentText = MobileUtils.getMobElement(fieldObjects, testCase, "ThermostatSchedule")
									.getText();
						}
						System.out.println(currentText);
						if (currentText.equals("Following Schedule")) {
							Keyword.ReportStep_Pass(testCase, "Scheduling status displayed as " + currentText);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Scheduling status expected Following schedule but displayed as " + currentText);

						}
					} else if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AdHocStatus", 30)) {
						Keyword.ReportStep_Pass(testCase, "Scheduling status displayed");
						String currentText = MobileUtils.getMobElement(fieldObjects, testCase, "AdHocStatus")
								.getAttribute("label");
						System.out.println(currentText);
						if (currentText.contains("Hold")) {
							Keyword.ReportStep_Pass(testCase, "Scheduling status displayed as " + currentText);
						} else {
							flag = false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Scheduling status expected Hold Until but displayed as " + currentText);

						}

					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Scheduling status not displayed");
					}
				}

				break;
			}
			case "Scheduling Icon":
				this.fieldObjects = MobileUtils.loadObjectFile(testCase, "ScheduleScreen");
				if (statInfo.getThermostatType().equalsIgnoreCase("Jasper")) {
					if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TimeScheduleButton", 5)) {
						Keyword.ReportStep_Pass(testCase,
								"Verify Primary Card Elements : Scheduling Icon present on Primary Card");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Verify Primary Card Elements : Scheduling Icon not present on the Primary Card");
					}
				} else {
					Keyword.ReportStep_Pass(testCase,
							"Verify Primary Card Elements : Device type is Honeybadger, hence not checking for scheduling icon");
				}
				break;
			case "Weather Icon":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WeatherButton")) {
					Keyword.ReportStep_Pass(testCase,
							"Verify Primary Card Elements : Weather Icon present on Primary Card");
				} else {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Verify Primary Card Elements : Weather Icon not present on the Primary Card");
				}
				break;

			case "Temperature":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WLDDismissIcon", 5)) {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "WLDDismissIcon")) {
						flag = false;
					}
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TemperatureTab", 5)) {
					ReportStep_Pass(testCase, "TEMPERATURE tab is shown");
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "TemperatureTab")) {
						flag = false;
					}
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "TEMPERATURE tab is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TemperatureHumidityValue", 5)) {
					ReportStep_Pass(testCase, "Temperature value is shown: "
							+ MobileUtils.getMobElement(fieldObjects, testCase, "TemperatureHumidityValue").getText());
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Temperature value is not shown");
				}

				break;

			case "Humidity":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "WLDDismissIcon", 5)) {
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "WLDDismissIcon")) {
						flag = false;
					}
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "HumidityTab", 5)) {
					ReportStep_Pass(testCase, "HUMIDITY tab is shown");
					if (!MobileUtils.clickOnElement(fieldObjects, testCase, "HumidityTab")) {
						flag = false;
					}
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "HUMIDITY tab is not shown");
				}
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "TemperatureHumidityValue", 5)) {
					ReportStep_Pass(testCase, "Humidity value is shown: "
							+ MobileUtils.getMobElement(fieldObjects, testCase, "TemperatureHumidityValue").getText());
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Humidity value is not shown");
				}

				break;

			case "Timestamp":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DeviceLastUpdateTime", 5)) {
					ReportStep_Pass(testCase, "Device last update time is shown: "
							+ MobileUtils.getMobElement(fieldObjects, testCase, "DeviceLastUpdateTime").getText());
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Device last update time is not shown");
				}

				break;

			case "Battery percentage":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "BatteryRemainingText", 5)) {
					ReportStep_Pass(testCase, "Battery Remaining text is shown: "
							+ MobileUtils.getMobElement(fieldObjects, testCase, "BatteryRemainingText").getText());
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Battery Remaining text is not shown");
				}

				break;

			case "Next update time":
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "NextUpdateText", 5)) {
					ReportStep_Pass(testCase, "Next update text is shown: "
							+ MobileUtils.getMobElement(fieldObjects, testCase, "NextUpdateText").getText());
				} else {
					flag = false;
					ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Next update text is not shown");
				}

				break;
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}
}