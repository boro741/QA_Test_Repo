package com.honeywell.keywords.jasper.Vacation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.utils.LyricUtils;
import com.honeywell.screens.VacationHoldScreen;

public class VerifyIfHBBDeviceIsDiplayedInVacationScreen extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;

	public VerifyIfHBBDeviceIsDiplayedInVacationScreen(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^HBB device should not be listed under the review vacation settings section in Vacation screen$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		List<WebElement> devicesList = new ArrayList<>();
		System.out.println(inputs.getInputValue("HBDeviceId"));
		JSONObject json = LyricUtils.getDeviceInformation(testCase, inputs, inputs.getInputValue("HBDeviceId"));
		String hbDeviceId = json.getString("deviceID");
		if (vhs.isDevicesListInVacationScreenVisible()) {
			devicesList = vhs.getDevicesListInVacationScreen();
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				for (WebElement ele : devicesList) {
					if (ele.getText().equals(hbDeviceId)) {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"HBB device listed under the review vacation settings section in Vacation screen is: "
										+ ele.getText());
						break;
					} else {
						Keyword.ReportStep_Pass(testCase,
								"HBB device is not listed under the review vacation settings section in Vacation screen");
					}
				}
			} else {
				for (int i = 1; i <= devicesList.size(); i++) {
					if (MobileUtils.getMobElement(testCase, "XPATH",
							"//XCUIElementTypeOther[contains(@name,'Review your vacation settings')]/following-sibling::XCUIElementTypeCell["
									+ i + "]/XCUIElementTypeStaticText[contains(@name,'_subTitle')]")
							.getAttribute("value").equals(hbDeviceId)) {
						flag = false;
						break;
					} else {
						// do nothing
					}
				}
			}
			if (flag) {
				Keyword.ReportStep_Pass(testCase,
						"HBB device is not listed under the review vacation settings section in Vacation screen");
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"HBB device listed under the review vacation settings section in Vacation screen is: "
								+ hbDeviceId);
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
