package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.jasper.utils.JasperVacation;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.VacationHoldScreen;

public class VerifyVacationStatus extends Keyword {
	public ArrayList<String> exampleData;
	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyVacationStatus(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.exampleData = exampleData;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user verifies vacation is \"(.+)\" in \"(.+)\"$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if (exampleData.get(0).equalsIgnoreCase("on")) {
			if ((exampleData.get(1).trim().equals("vacation settings card"))
					|| (exampleData.get(1).trim().equals("Vacation"))) {
				try {
					if (vhs.isVacationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Vacation Switch is enabled in Vacation Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Vacation Switch is disabled in Vacation Settings Screen");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (exampleData.get(1).trim().equals("solution card")) {
				PrimaryCard pc = new PrimaryCard(testCase);
				if (!pc.isCogIconVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"User is not in Solution Card screen. Navigate back to Dashboard screen and tap on device");
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					try {
						flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
								inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "User is in the Solution Card screen.");
				}
				flag = flag & JasperVacation.waitForVacationStart(testCase, inputs);
				flag = flag & JasperVacation.verifyVacationStatusOnSolutionsCard(testCase, inputs, true);
				
			} else if (exampleData.get(1).trim().equals("dashboard")) {
				flag = flag & JasperVacation.waitForVacationStart(testCase, inputs);
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);
			}
		} else if (exampleData.get(0).equalsIgnoreCase("off")) {
			if ((exampleData.get(1).trim().equals("vacation settings card"))
					|| (exampleData.get(1).trim().equals("Vacation"))) {
				try {
					if (!vhs.isVacationSwitchEnabled(testCase)) {
						Keyword.ReportStep_Pass(testCase, "Vacation Switch is disabled in Vacation Settings Screen");
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Vacation Switch is enabled in Vacation Settings Screen");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (exampleData.get(1).trim().equals("solution card")) {
				PrimaryCard pc = new PrimaryCard(testCase);
				if (!pc.isCogIconVisible()) {
					Keyword.ReportStep_Pass(testCase,
							"User is not in Solution Card screen. Navigate back to Dashboard screen and tap on device");
					flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
					try {
						flag = flag & DashboardUtils.selectDeviceFromDashboard(testCase,
								inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "User is in Solution Card screen.");
				}
				flag = flag & JasperVacation.verifyVacationStatusOnSolutionsCard(testCase, inputs, false);
			} else if (exampleData.get(1).trim().equals("dashboard")) {
				flag = flag & JasperVacation.verifyVacationStatusOnPrimaryCard(testCase, inputs, true);
			}
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}
