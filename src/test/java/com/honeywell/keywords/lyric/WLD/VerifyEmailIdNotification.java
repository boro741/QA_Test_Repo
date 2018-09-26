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
import com.honeywell.screens.WLDEmailNotificationsScreen;

public class VerifyEmailIdNotification extends Keyword {
	/**
	 * @author Amresh Pradhan (H297378)
	 * @since 2018-09-25
	 */
	private TestCases testCase;
	public ArrayList<String> expectedScreen;
	public boolean flag = true;
	public ArrayList<String> parameters;
	public HashMap<String, MobileObject> fieldObjects;
	public DataTable data;
	public VerifyEmailIdNotification(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedScreen,
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
	@KeywordStep(gherkins = "^verify if user is able to see enterred (.+) in the Email display part:$")

	public boolean keywordSteps() throws KeywordException {
		switch (expectedScreen.get(0).toUpperCase()) {
		case "EMAIL ID":{
			WLDEmailNotificationsScreen notify = new WLDEmailNotificationsScreen(testCase);
			for (int i = 0; i < data.getSize(); i++) {
				String parameter = data.getData(i, "Email");
				switch (parameter.toUpperCase()) {
				case "VALID EMAIL ID": 
				{
					flag = flag && notify.isEnterEmailEditTextBoxVissible();
					if(testCase.getPlatform().toUpperCase().contains("ANDROID") && flag) 
					{
						notify.clearEnterEmailEditTextBox();
						String randomEmail = notify.generateRandomEmailID();
						System.out.println("Random id Generated was: "+randomEmail);
						notify.setValueToEmailTextBox(randomEmail);
						System.out.println("Entered Submit button");
						flag = flag && notify.verifyEmailPresentinList(randomEmail);
					}else {
						//notify.clearEnterEmailEditTextBox();
						String randomEmail = notify.generateRandomEmailID();
						System.out.println("Random id Generated was: "+randomEmail);
						notify.setValueToEmailTextBox(randomEmail);
						System.out.println("Entered Submit button");
						flag = flag && notify.verifyEmailPresentinList(randomEmail);
					}
					break;
				}
				case "INVALID EMAIL ID": {
					flag = flag && notify.isEnterEmailEditTextBoxVissible();
					String randomEmail = "IncorrectEmailID";
					if(testCase.getPlatform().toUpperCase().contains("ANDROID") && flag) 
					{
						notify.clearEnterEmailEditTextBox();
						notify.setValueToEmailTextBox(randomEmail);
						System.out.println("Entered Submit button");
						flag = flag && notify.incorrectEmailPopup();
					}
					else {
						notify.setValueToEmailTextBox(randomEmail);
						System.out.println("Entered Submit button");
						flag = flag && notify.incorrectEmailPopup();
					}
					break;
				}
				}
				if (flag) {
					Keyword.ReportStep_Pass(testCase, " " + parameter + " was selected");
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"The Update Frequency: " + parameter + " was not selected");
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