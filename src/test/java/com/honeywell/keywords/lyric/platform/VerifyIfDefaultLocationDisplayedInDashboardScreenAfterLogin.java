package com.honeywell.keywords.lyric.platform;

import java.util.Set;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;

public class VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public static boolean FLAG = true;
	public static String DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN = null;
	public static String DEFAULTLOCATIONFROMCHIL = null;
	public static Set<String> LISTOFLOCATIONSFORLOGGEDINUSERFROMCHIL = null;

	public VerifyIfDefaultLocationDisplayedInDashboardScreenAfterLogin(TestCases testCase, TestCaseInputs inputs) {
		this.inputs = inputs;
		this.testCase = testCase;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		FLAG = true;
		return FLAG;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with the default location in Dashboard screen$")
	public boolean keywordSteps() {
		Dashboard d = new Dashboard(testCase);
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtils = new CHILUtil(inputs);
			if (chUtils.getConnection()) {
				DEFAULTLOCATIONFROMCHIL = chUtils.getDefaultSelectedLocationName();
				Keyword.ReportStep_Pass(testCase, "Default Location Name from CHIL: " + DEFAULTLOCATIONFROMCHIL);
				LISTOFLOCATIONSFORLOGGEDINUSERFROMCHIL = chUtils.getLocationNames();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (d.isLocationNameDisplayedInDashboardScreen()) {
			Keyword.ReportStep_Pass(testCase, "Dashboard screen has a location.");
			DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN = d.getLocationNameDisplayedInDashboardScreen();
			if (DEFAULTLOCATIONFROMCHIL.equalsIgnoreCase(DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN)) {
				Keyword.ReportStep_Pass(testCase,
						"Default Location Name from CHIL: " + DEFAULTLOCATIONFROMCHIL
								+ " and default Location Selected in Dashbooard Screen: "
								+ DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN + " are the same.");
			} else {
				FLAG = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Default Location Name from CHIL: " + DEFAULTLOCATIONFROMCHIL
								+ " and default Location Selected in Dashbooard Screen: "
								+ DEFAULTLOCATIONDISPLAYEDINDASHBOARDSCREEN + " are not the same.");
			}
		}
		return FLAG;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() {
		boolean flag = true;
		return flag;
	}
}
