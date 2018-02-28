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
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.DASDIYRegistrationScreens;
import com.honeywell.screens.ZwaveScreen;

public class ClickOnButton extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> expectedButton;
	public boolean flag = true;

	public ClickOnButton(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedButton) {
		this.testCase = testCase;
		// this.inputs = inputs;
		this.expectedButton = expectedButton;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) by clicking on (.*) button$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if (expectedButton.get(0).equalsIgnoreCase("deletes DAS device")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnDeleteButton();
					break;
				}
				}
			}else if(expectedButton.get(0).equalsIgnoreCase("fixes all zwave devices")){
				switch (expectedButton.get(1).toUpperCase()) {
				case "FIX ALL": {
					ZwaveScreen zs= new ZwaveScreen(testCase);
					if(zs.isFixAllEnabled()){
						zs.clickOnFixAll();
						zs.clickOnFixAllPopupCancel();
						zs.clickOnFixAll();
						zs.clickOnFixAllPopupConfirm();
						DASZwaveUtils.waitForActionToComplete(testCase);   
						zs.clickOnFixAllPopupAccept();
					}else{
						Keyword.ReportStep_Pass(testCase, "No device found to be offline");
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("cancels the set up")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "CANCEL": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isCancelButtonInChooseLocationScreenVisible()) {
						dasDIY.clickOnCancelButtonInChooseLocationScreen();
					}
					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("views cancel setup")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "BACK ARROW": {
					DASDIYRegistrationScreens dasDIY = new DASDIYRegistrationScreens(testCase);
					if (dasDIY.isBackArrowInRegisterBaseStationVisible()) {
						dasDIY.clickOnBackArrowInRegisterBaseStationScreen();
						if (dasDIY.isCancelPopupVisible()) {
							return flag;
						}
					}

					break;
				}
				}
			} else if (expectedButton.get(0).equalsIgnoreCase("deletes sensor")) {
				switch (expectedButton.get(1).toUpperCase()) {
				case "DELETE": {
					BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
					flag = flag & bs.clickOnDeleteButton();
					break;
				}
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
