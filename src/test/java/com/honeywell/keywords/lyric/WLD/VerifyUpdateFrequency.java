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
import com.honeywell.screens.WLDUpdateFrequency;

public class VerifyUpdateFrequency extends Keyword {
	/**
	 * @author Amresh Pradhan (H297378)
	 * @since 2018-11-10
	 */
	private TestCases testCase;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;
	public DataTable data;
	public VerifyUpdateFrequency(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
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
	@KeywordStep(gherkins = "^verify Next Update Time in Solution Card after selecting (.+) options:$")
	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "UPDATE FREQUENCY":{
			WLDSolutionCard wld = new WLDSolutionCard(testCase);
			WLDLeakDetectorSettings set = new WLDLeakDetectorSettings(testCase);
			WLDUpdateFrequency freq = new WLDUpdateFrequency(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Options");
				switch (parameter.toUpperCase()) {
				case "DAILY": {
					String before_update = wld.getNextUpdateTimeTitleText();
					System.out.println("Before Update Time : "+before_update);
					flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
					System.out.println("Selecting  Daily");
					flag = flag & set.clickonUpdateFrequencyTitleText();
					flag = flag & freq.clickOnDailyRadioButton();
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					String after_update = wld.getNextUpdateTimeTitleText();
					System.out.println("After Update Time : "+after_update);
					break;
				}
				case "TWICE DAILY": {
					String before_update = wld.getNextUpdateTimeTitleText();
					System.out.println("Before Update Time : "+before_update);
					flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
					System.out.println("Selecting Twice Daily");
					flag = flag & set.clickonUpdateFrequencyTitleText();
					flag = flag & freq.clickOnTwiceDailyRadioButton();
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					String after_update = wld.getNextUpdateTimeTitleText();
					System.out.println("After Update Time : "+after_update);
					break;
				}
				case "THRICE DAILY": {
					String before_update = wld.getNextUpdateTimeTitleText();
					System.out.println("Before Update Time : "+before_update);
					flag = flag & wld.navigateFromPrimaryCardToWLDSettingsScreen();
					System.out.println("Selecting Thrice Daily");
					flag = flag & set.clickonUpdateFrequencyTitleText();
					flag = flag & freq.clickOnThriceDailyRadioButton();
					flag = flag & set.navigateFromUpdateFrequencyCardToPrimaryCard();
					String after_update = wld.getNextUpdateTimeTitleText();
					System.out.println("After Update Time : "+after_update);
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, "The " + parameter + "has found");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The " + parameter + " was not found");
				}
				flag = true;
			}
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