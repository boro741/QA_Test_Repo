package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.OSPopUps;

public class SelectDeviceFromDashboard extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> deviceType;

	public SelectDeviceFromDashboard(TestCases testCase, TestCaseInputs inputs,ArrayList<String> deviceType) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.deviceType=deviceType;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() {

		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user selects (.*) from the dashboard$")
	public boolean keywordSteps() {
		OSPopUps os = new OSPopUps(testCase);
		String deviceToBeSelected = "";
		if(deviceType.get(0).equalsIgnoreCase("DAS Device"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Dimmer"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DIMMER1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Switch"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_SWITCH1_NAME");
		}
		else if(deviceType.get(0).equalsIgnoreCase("Jasper device"))
		{
			deviceToBeSelected = inputs.getInputValue("LOCATION1_DEVICE1_NAME");
		}

		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		List<WebElement> dashboardIconText = null;
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardIconText", 5)) {
			dashboardIconText = MobileUtils.getMobElements(fieldObjects, testCase, "DashboardIconText");
		}
		boolean f = false;
		List<String> availableDevices = new ArrayList<String>();
		for (WebElement e : dashboardIconText) {
			String displayedText;
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				displayedText = e.getText();
			} else {
				displayedText = e.getAttribute("value");
			}
			availableDevices.add(displayedText);
			if (displayedText.equals(deviceToBeSelected)) {
				e.click();
				f = true;
				break;
			}
		}
		if (f) {
			Keyword.ReportStep_Pass(testCase, "Successfully selected device : " + deviceToBeSelected);
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device : " + deviceToBeSelected + " is not present on the dashboard. Available Devices: " + availableDevices);
		}
		int counter = 0;
		while (os.isGotitButtonVisible(5) && counter < 5) {
			os.clickOnGotitButton();
			counter++;
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		return flag;
	}

}
