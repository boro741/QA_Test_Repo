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
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.screens.SchedulingScreen;

public class DeleteParticularTimePeriod extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;

	public DeleteParticularTimePeriod(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
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
	@KeywordStep(gherkins = "^user deletes the \"(.+)\" time period$")
	public boolean keywordSteps() throws KeywordException {
		DeviceInformation devInfo = new DeviceInformation(testCase, inputs);
		String jasperStatType = devInfo.getJasperDeviceType();
		String prelocator = null;
		String postlocator = null;
		String locator=null;
		if(jasperStatType.toUpperCase().contains("NA")){
			if(testCase.getPlatform().contains("IOS"))
			{
				if (exampleData.get(0).equalsIgnoreCase("Home")||exampleData.get(0).equalsIgnoreCase("P1")) {
					locator="Everyday_Home_subTitle";
				}else if (exampleData.get(0).equalsIgnoreCase("Wake")||exampleData.get(0).equalsIgnoreCase("P2")) {
					locator="Everyday_Wake_subTitle";
				}else if (exampleData.get(0).equalsIgnoreCase("Sleep")||exampleData.get(0).equalsIgnoreCase("P3")) {
					locator="Everyday_Sleep_subTitle";
				}else if (exampleData.get(0).equalsIgnoreCase("Away")||exampleData.get(0).equalsIgnoreCase("P4")) {
					locator="Everyday_Away_subTitle";
				}
					prelocator="//*[contains(@name,'";
					postlocator = "')]";
				
			}else {
				if (exampleData.get(0).equalsIgnoreCase("Home")||exampleData.get(0).equalsIgnoreCase("P1")) {
					locator="Home_Everyday";
				}else if (exampleData.get(0).equalsIgnoreCase("Wake")||exampleData.get(0).equalsIgnoreCase("P2")) {
					locator="Wake_Everyday";
				}else if (exampleData.get(0).equalsIgnoreCase("Sleep")||exampleData.get(0).equalsIgnoreCase("P3")) {
					locator="Sleep_Everyday";
				}else if (exampleData.get(0).equalsIgnoreCase("Away")||exampleData.get(0).equalsIgnoreCase("P4")) {
					locator="Away_Everyday";
				}
					prelocator="//*[contains(@content-desc,'";
					postlocator = "')]";
			}
			
		}else{
			if(jasperStatType.toUpperCase().contains("EMEA")){
				if (exampleData.get(0).equalsIgnoreCase("Home")||exampleData.get(0).equalsIgnoreCase("P1")) {
					locator="1";
				}else if (exampleData.get(0).equalsIgnoreCase("Wake")||exampleData.get(0).equalsIgnoreCase("P2")) {
					locator="2";
				}else if (exampleData.get(0).equalsIgnoreCase("Sleep")||exampleData.get(0).equalsIgnoreCase("P3")) {
					locator="3";
				}else if (exampleData.get(0).equalsIgnoreCase("Away")||exampleData.get(0).equalsIgnoreCase("P4")) {
					locator="4";
				}
				if(!testCase.getPlatform().contains("IOS")){
					prelocator="//*[contains(@content-desc,'";
					postlocator = "')]";
				}else{
					prelocator="//*[contains(@name,'";
					postlocator = "')]";
				}
			}
		}

		SchedulingScreen ss = new SchedulingScreen(testCase);

		if (ss.isTimeScheduleButtonVisible()) {
			flag = flag && ss.clickOnTimeScheduleButton();
			flag = flag && MobileUtils.clickOnElement(testCase, "Xpath",prelocator+locator+postlocator );
			if (ss.isPeriodDeleteIconVisible(10)) {
				flag = flag &&ss.clickOnPeriodDeleteIcon();
				if (ss.isDeletePopupVisible() && ss.isDeletePopupVisible()
						&& ss.isConfirmDeleteButtonVisible(1)) {
					flag = flag && ss.clickOnConfirmDeleteButton();
					flag = flag && ss.clickOnCloseButton();
				}
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
