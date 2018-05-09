package com.honeywell.keywords.lyric.das.accessories;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.EditLocationScreen;
import com.honeywell.screens.SecondaryCardSettings;

public class DisableFacialRecognition extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> screen;
	public boolean flag = true;

	public DisableFacialRecognition(TestCases testCase, TestCaseInputs inputs, ArrayList<String> screen) {
		this.testCase = testCase;
		this.screen = screen;
		this.inputs = inputs;

	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user changes the location from \"([^\"]*)\" to \"([^\"]*)\" for facial recognition$")
	public boolean keywordSteps() throws KeywordException {
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
		switch (screen.get(0).toUpperCase()) {
		case "PERMITTED": {
			if(screen.get(1).toUpperCase().equals("NOT PERMITTED")){
				//Edit the location by clicking on Edit Address screen
				Dashboard dScreen = new Dashboard(testCase);
				if (dScreen.clickOnGlobalDrawerButton()) {
					SecondaryCardSettings sc = new SecondaryCardSettings(testCase);
					if (!sc.selectOptionFromSecondarySettings(SecondaryCardSettings.ADDRESSDETAILS)) {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Could not click on Address menu from Global drawer");
					}
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Could not click on Global drawer menu from dashboard");
				}
				//Click Edit Button
				EditLocationScreen els = new EditLocationScreen(testCase);
				if(!els.isEditButtonExist(3)){
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Could not find Edit Address Button in Home Adress Screen");
				}
				els.clickEditButton();
				flag=flag & els.setState(inputs.getInputValue("IL_STATE"));
				flag=flag & els.setZipCode(inputs.getInputValue("IL_ZIPCODE"));
				flag=MobileUtils.pressBackButton(testCase);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(els.isSaveButtonAvailable()){
				  flag=flag & els.clickOnSaveButton();
				  //Go To DashBoard Screen
				  DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
				  
				}
				else{
					flag=false;
					ReportStep_Fail_WithOut_ScreenShot(testCase,FailType.FUNCTIONAL_FAILURE,"Save Button is not available");
				}
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			flag=FRUtils.checkLocationPermittedForFR(testCase, inputs,false,locationID);
			if(!flag){
				ReportStep_Fail_WithOut_ScreenShot(testCase,FailType.FUNCTIONAL_FAILURE,"User Dont have permission for FR in the location but he should have permission");
			}
			else{
				ReportStep_Pass(testCase,"Location is edited successfully with FR disabled state and validated FR is disabled");
				
			}
			}
			break;
		}
		case "NOT PERMITTED":{
			
			break;
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
