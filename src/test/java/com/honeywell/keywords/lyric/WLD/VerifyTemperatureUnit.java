package com.honeywell.keywords.lyric.WLD;

import java.util.ArrayList;
import java.util.HashMap;
import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.WLDLeakDetectorSettings;
import com.honeywell.screens.WLDSolutionCard;

public class VerifyTemperatureUnit extends Keyword {
	/**
	 * @author Amresh Pradhan (H297378)
	 * @since 2018-11-12
	 */
	private TestCases testCase;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;
	public DataTable data;
	public String temp_unit1;
	public String temp_unit2;
	public VerifyTemperatureUnit(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
			DataTable data) {
		this.testCase = testCase;
		this.expectedScreen = expectedScreen;
		this.data = data;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^verify Temperature Unit is changed as per the (.+) selected below:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "TEMPERATURE UNIT":{
			WLDSolutionCard wld = new WLDSolutionCard(testCase);
			WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "FAHRENHEIT": {
					flag = flag & wld.checkAndDismissControlState();
					flag = flag & wld.isCurrentTemperatureTitleVisible();
					String temp_unit_before = wld.getCurrentTemperatureTitleText();
					if(temp_unit_before.contains(".")) 
					{ temp_unit1 = "Celsius";}
					else 
					{ temp_unit1 = "Fahrenheit";}
					Keyword.ReportStep_Pass(testCase, "Temperature Unit Before: "+temp_unit1);
					flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
					set.clickonTemperatureUnit();
					flag = flag & set.navigateFromWLDSettingsScreenToPrimaryCard();
					flag = flag & wld.isCurrentTemperatureTitleVisible();
					String temp_unit_after = wld.getCurrentTemperatureTitleText();
					if(temp_unit_after.contains(".")) 
					{ temp_unit2 = "Celsius";}
					else 
					{ temp_unit2 = "Fahrenheit";}
					Keyword.ReportStep_Pass(testCase, "Temperature Unit After: "+temp_unit2);
					if (!temp_unit1.equals(temp_unit2)) {
						Keyword.ReportStep_Pass(testCase, "The " + parameter + " unit was sucessfully changed");	
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The " + parameter + " unit was not sucessfully changed");
					}
					break;
				}
				case "CELSIUS": {
					flag = flag & wld.checkAndDismissControlState();
					flag = flag & wld.isCurrentTemperatureTitleVisible();
					String temp_unit_before = wld.getCurrentTemperatureTitleText();
					if(temp_unit_before.contains(".")) 
					{ temp_unit1 = "Celsius";}
					else 
					{ temp_unit1 = "Fahrenheit";
					}
					Keyword.ReportStep_Pass(testCase, "Temperature Unit Before: "+temp_unit1);
					flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
					set.clickonTemperatureUnit();
					flag = flag & set.navigateFromWLDSettingsScreenToPrimaryCard();
					flag = flag & wld.isCurrentTemperatureTitleVisible();
					String temp_unit_after = wld.getCurrentTemperatureTitleText();
					if(temp_unit_after.contains(".")) 
					{ temp_unit2 = "Celsius";}
					else 
					{ temp_unit2 = "Fahrenheit";}
					Keyword.ReportStep_Pass(testCase, "Temperature Unit After: "+temp_unit2);

					if (!temp_unit1.equals(temp_unit2)) {
						Keyword.ReportStep_Pass(testCase, "The " + parameter + " unit was sucessfully changed");	
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"The " + parameter + " unit was not sucessfully changed");
					}
					break;
				}
				}
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