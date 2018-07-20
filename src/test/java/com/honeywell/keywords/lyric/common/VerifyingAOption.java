package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;

import org.json.JSONObject;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.FRUtils;
import com.honeywell.lyric.utils.LyricUtils;

import com.honeywell.screens.Dashboard;


import com.honeywell.screens.PrimaryCard;


import com.honeywell.screens.PrimaryCard;



import com.honeywell.screens.PrimaryCard;

import com.honeywell.screens.SecondaryCardSettings;
import com.honeywell.screens.SensorSettingScreen;



public class VerifyingAOption extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public DataTable data;

	public VerifyingAOption(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedScreen = expectedScreen;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user \"([^\"]*)\" with the \"([^\"]*)\" option$")
	public boolean keywordSteps() throws KeywordException {
		JSONObject tempJSON= (JSONObject)LyricUtils.getLocationInformation(testCase, inputs);
		long locationID=tempJSON.getLong("locationID");
		if(expectedScreen.get(1).toUpperCase().equals("FR")){
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD BE DISPLAYED": {
				SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
				try {
					if (scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is present on the Secondary Cards screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is not present on the Secondary Cards screen");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				break;
			}
			case "SHOULD NOT BE DISPLAYED":{
				SecondaryCardSettings  scs = new SecondaryCardSettings(testCase);
				try {
					if (!scs.isFROptionAvailable(3)) {
						Keyword.ReportStep_Pass(testCase,
								"Facial Recognition Option is not present on the Secondary Cards screen");


					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Facial Recognition Option is  present on the Secondary Cards screen but should not");
					}
				} catch (Exception e) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
				}
				finally{
					//Update to the location where FR will permit
					FRUtils.updateLocationThroughCHIL(testCase, inputs,"{\"city\":\"Chicago Ridge\",\"state\":\"NY\",\"country\":\"US\",\"zipcode\":\"11747\"}",locationID);
				}
				break;

			}



			default: {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Input: " + expectedScreen.get(0));
			}
			}
		}
		else if(expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON SELECTED MODE"))
		{
			PrimaryCard card=new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			
			case "SHOULD BE DISPLAYED":
				if(card.isSetModeDisplayed(inputs)){
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is  displayed");
					
				}
				else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is not displayed");
				}
				break;
			}
			}
		else if(expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON NEW SELECTED MODE"))
		{
			PrimaryCard card=new PrimaryCard(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			
			case "SHOULD BE DISPLAYED":
				if(card.isNewSetModeDisplayed(inputs)){
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is  displayed");
					
				}
				else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is not displayed");
				}
				break;
			}
			}
			
			
		
		
		else if(expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN CANCEL")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if(sensor.isCancelButtonDisplayed()){
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is displayed");
				}
				else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
				}
			}
			}

		}
		else if(expectedScreen.get(1).toUpperCase().equals("TEST SENSOR SCREEN BACK")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {

			case "SHOULD NOT BE DISPLAYED": {
				if(sensor.isBackButtonDisplayed()) {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is displayed");
				}
				else {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
				}
			}
			}

		}
		else if(expectedScreen.get(1).toUpperCase().equals("CANCEL SENSOR SETUP POPUP")){
			SensorSettingScreen sensor = new SensorSettingScreen(testCase);
			switch (expectedScreen.get(0).toUpperCase()) {
			case "SHOULD NOT BE DISPLAYED": {
			
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(sensor.isCancelSetUpPopUpVisible()) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Cancel sensor popup is displayed");
					return flag;

				} else {
					 Keyword.ReportStep_Pass(testCase, "Cancel Sensor popup is not displayed");
				
				}
				
				break;
			
			}
			}
			
			}else if(expectedScreen.get(1).toUpperCase().equals("DONE")){
				SensorSettingScreen sensor = new SensorSettingScreen(testCase);
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE DISPLAYED": {
				
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(sensor.isDoneButtonVisible()==false) {
						flag = true;
						 Keyword.ReportStep_Pass(testCase, "Done button is not displayed");
						return flag;

					} else {
						flag=false;
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Done button is displayed");
					}
					
					break;
				
				}
				}
				
				}
			else if(expectedScreen.get(1).toUpperCase().equals("RESPECTIVE SETPOINT VALUE")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD BE DISPLAYED": {
			Dashboard thermo  = new Dashboard(testCase);
				if(thermo.isUserExpectedTemperatureDisplayed()) {
					System.out.println("User Expected Temperature is Displayed");
					Keyword.ReportStep_Pass(testCase,"User Expected Temperature is Displayed" );
				}
				else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,"User Expected Temperature is not Displayed");
				}
					
					break;
				
				}
				}	
			}
			else if(expectedScreen.get(1).toUpperCase().equals("--")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD BE DISPLAYED": {
					Dashboard dash = new Dashboard(testCase);
					if(dash.isTemperatureNotDisplayed()){
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
					}
					else {
					
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, expectedScreen.get(1)+" is displayed");
					}
					break;
				}
				}
			}

			

			else if(expectedScreen.get(1).toUpperCase().equals("BLUE TICK MARK ON SELECTED FAN")){
		
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD BE DISPLAYED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					String currentlySelectedFanMode = inputs.getInputValue("SelectedFanMode");
					System.out.println("Currently Selected fan mode "+currentlySelectedFanMode);
			    if(currentlySelectedFanMode.equalsIgnoreCase("AUTO")) {
			
			    	flag = flag & thermo.isAutoFanElementSelected();
				}
			    else if(currentlySelectedFanMode.equalsIgnoreCase("CIRCULATE")) {
					
			    	flag = flag & thermo.isCirculateFanElementSelected();
				}
			    else if(currentlySelectedFanMode.equalsIgnoreCase("ON")) {
					
			    	flag = flag & thermo.isONFanElementSelected();
				}
				}
				break;
				}
				if(flag) {
					Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is present for "+inputs.getInputValue("SelectedFanMode"));
				}
			}
			else if(expectedScreen.get(1).toUpperCase().equals("AUTO FAN")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
					}
					break;
				}
				}
			

			}
			else if(expectedScreen.get(1).toUpperCase().equals("CIRCULATE")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
					}
					break;
				}
				}
			
			}

			
			else if(expectedScreen.get(1).toUpperCase().equals("CIRCULATE")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
					}
					break;
				}
				}
			
			}


			else if(expectedScreen.get(1).toUpperCase().equals("ON")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED":{
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentFanMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
					}
					break;
				}
				}

			
			}
			else if(expectedScreen.get(1).toUpperCase().equals("FAN")){
				
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE DISPLAYED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.isFanButtonVisible();
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+" is displayed");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
						
					}
					break;
				}
				}
			}
			else if(expectedScreen.get(1).toUpperCase().equals("AUTOMODE")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					System.out.println("Inside Should not be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED": {
					System.out.println("Inside Should be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
						
					}
					break;
				}

				}
			
			}
			else if(expectedScreen.get(1).toUpperCase().equals("COOL")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					System.out.println("Inside Should not be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED": {
					System.out.println("Inside Should be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
						
					}
					break;
				}
				}
			
			}
			else if(expectedScreen.get(1).toUpperCase().equals("HEAT")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					System.out.println("Inside Should not be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED": {
					System.out.println("Inside Should be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
						
					}
					break;
				}
				}
			
			}
			else if(expectedScreen.get(1).toUpperCase().equals("OFF")){
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE UPDATED": {
					System.out.println("Inside Should not be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is updated");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not updated");
						
					}
					break;
				}
				case "SHOULD BE UPDATED": {
					System.out.println("Inside Should be updated");
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.checkCurrentMode(expectedScreen.get(1).toUpperCase());
					if(flag) {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is updated");
						
					}
					else {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+"Value is not updated");
						
					}
					break;
				}
				}
			
			}
				
				

			else if(expectedScreen.get(1).toUpperCase().equals("FAN")){
				
				switch (expectedScreen.get(0).toUpperCase()) {
				case "SHOULD NOT BE DISPLAYED": {
					PrimaryCard thermo = new PrimaryCard(testCase);
					flag=thermo.isFanButtonVisible();
					if(flag) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,expectedScreen.get(1)+" is displayed");
					}
					else {
						flag=true;
						Keyword.ReportStep_Pass(testCase, expectedScreen.get(1)+" is not displayed");
						
					}
					break;
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
