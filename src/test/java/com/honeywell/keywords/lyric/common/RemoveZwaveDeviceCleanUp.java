package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

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
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.ZwaveScreen;

public class RemoveZwaveDeviceCleanUp extends Keyword {

	private TestCases testCase;
	public boolean flag = true;
	public ArrayList<String> parameters;
	private TestCaseInputs inputs;
	public HashMap<String, MobileObject> fieldObjects;

	public RemoveZwaveDeviceCleanUp(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user has no (.*)$")
	public boolean keywordSteps() throws KeywordException {
		try {
			switch (parameters.get(0).toUpperCase()) {
			case "SWITCH1": {
				boolean f = false;
				HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
				if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardIconText", 30)) {
					List<WebElement> dashboardIconText = MobileUtils.getMobElements(fieldObjects, testCase,
							"DashboardIconText");
					for (WebElement e : dashboardIconText) {
						String displayedText = "";
						if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
							displayedText = e.getText();
						} else {
							try {
								displayedText = e.getAttribute("value");
							} catch (Exception e1) {
							}
						}
						if (displayedText.equalsIgnoreCase(parameters.get(0))) {
							f = true;
							break;
						}
					}
				}
				if(f){
					Dashboard ds = new Dashboard(testCase);
					if(ds.clickOnGlobalButtonOfDashboard()){         
						SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
						if(sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ZWAVEDEVICES)){
							ZwaveScreen zwaveScreen = new ZwaveScreen(testCase);
							if(zwaveScreen.ClickSwitchSettingFromZwaveUtilities()){
								zwaveScreen.ClickDeleteFromSettings();
								zwaveScreen.isRemoveDevicePopUpDisplayed();
								DASZwaveUtils.clickOKOnDeviceExcludedPopUp(testCase);
								fieldObjects = MobileUtils.loadObjectFile(testCase, "ZwaveScreen");
								DASZwaveUtils.waitForEnteringInclusionToComplete(testCase);
								if(MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludeModeScreenHeader") && MobileUtils.isMobElementExists(fieldObjects, testCase, "ExcludeModeTitle")){
									Keyword.ReportStep_Pass(testCase, "In Exclude screen");
								}else{
									flag=false;
									Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Not in excpected screen: Exclude");
								}
								ZWaveRelayUtils.enrollZwaveSwitch1();
								Thread.sleep(2000);
								ZWaveRelayUtils.pressButtonOnSwitch1();
								zwaveScreen.clickOKOnDeviceExcludedPopUp();
								DASZwaveUtils.clickNavigateUp(testCase, inputs);
								DASZwaveUtils.clickNavigateUp(testCase, inputs);
							}else{
								Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
										"Could not click on "+parameters.get(0));
							}
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Could not click on Add new device menu from Global drawer");
						}
					}else{
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Global drawer menu from dashboard");
					}
				}
				break;
			}
			case "DIMMER": {
				break;
			}

			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Input not handled " + parameters.get(0).toUpperCase());
			}
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
