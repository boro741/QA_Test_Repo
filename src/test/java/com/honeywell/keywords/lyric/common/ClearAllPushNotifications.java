package com.honeywell.keywords.lyric.common;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.das.utils.DASNotificationUtils;

public class ClearAllPushNotifications extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public boolean flag = true;
	public boolean pressBack = true;
	
	public ClearAllPushNotifications(TestCases testCase, TestCaseInputs inputs) {
		this.testCase = testCase;
		// this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user clears all push notifications$")
	public boolean keywordSteps() throws KeywordException {
		DASNotificationUtils.openNotifications(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			DASNotificationUtils.openNotifications(testCase);
			if (MobileUtils.isMobElementExists("id",
					"com.android.systemui:id/clear_all", testCase, 5)) {
				if (MobileUtils
						.getMobElement(testCase, "id",
								"com.android.systemui:id/clear_all")
						.getAttribute("enabled").equalsIgnoreCase("true")) {
					flag = flag
							& MobileUtils.clickOnElement(testCase, "id",
									"com.android.systemui:id/clear_all");
				} 
			}else if (MobileUtils.isMobElementExists("id",
					"com.android.systemui:id/clear_text", testCase, 5)) {
				flag = flag
						& MobileUtils.clickOnElement(testCase, "id",
								"com.android.systemui:id/clear_text");
				pressBack=false;
			} 
		} else {
			int i = 0;
			while (i < 5) {
				if (MobileUtils.isMobElementExists("name",
						"Clear notifications", testCase, 2) && i < 5) {
					flag = flag
							& MobileUtils.clickOnElement(testCase, "name",
									"Clear notifications");
					flag = flag
							& MobileUtils.clickOnElement(testCase, "name",
									"Confirm clear notifications");
				}
				else
				{
					break;
				}
				i++;
			}
		}
		if(pressBack){
		DASNotificationUtils.closeNotifications(testCase);
		}
		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
