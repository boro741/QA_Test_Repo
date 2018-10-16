package com.honeywell.keywords.lyric.camerasettings;

import io.appium.java_client.TouchAction;

import java.util.ArrayList;

import org.openqa.selenium.Dimension;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.PrimaryCard;
import com.honeywell.screens.VacationHoldScreen;

public class NavigateBackAndForthInAScreen extends Keyword {
	private TestCases testCase;
	private TestCaseInputs inputs;
	public ArrayList<String> expectedOption;
	public boolean flag = true;
	public DataTable data;

	public NavigateBackAndForthInAScreen(TestCases testCase, TestCaseInputs inputs, ArrayList<String> expectedOption) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.expectedOption = expectedOption;

	}

	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user navigates back and forth in \"(.*)\" screen$")
	public boolean keywordSteps() throws KeywordException {
		CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
		BaseStationSettingsScreen bs = new BaseStationSettingsScreen(testCase);
		PrimaryCard pc = new PrimaryCard(testCase);
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		switch (expectedOption.get(0).toUpperCase()) {
		case "MANAGE ALERTS": {
			cs.navigateBackAndForthInManageAlertsScreen(testCase);
			break;
		}
		case "CAMERA SETTINGS": {
			cs.navigateBackAndForthInCameraSettingsScreen(testCase);
			break;
		}
		case "MOTION DETECTION SETTINGS": {
			cs.navigateBackAndForthInMotionDetectionScreen(testCase);
			break;
		}
		case "VACATION": {
			vhs.navigateBackAndForthInVacationsScreen(testCase);
			break;
		}
		case "VACATION STAT": {
			vhs.navigateBackAndForthInVacationsStatScreen(testCase, inputs);
			break;
		}
		case "ENHANCED DETERRENCE": {
			flag &= bs.clickOnBackButton();
			flag &= bs.clickOnEnhancedDeterrenceOption(20);
			if (bs.isEnhancedDeterrenceOptionVisible(20)) {
				ReportStep_Pass(testCase,
						"User is still in Security Settings screen. Tapping on Enhanced Deterrence option");
				flag &= bs.clickOnEnhancedDeterrenceOption(20);
			}
			break;
		}
		case "DAS SECURITY SETTINGS UP": {
			flag &= bs.clickOnBackButton();
			flag &= pc.clickOnCogIcon();
			if (flag) {
				Dimension dimension = testCase.getMobileDriver().manage().window().getSize();
				TouchAction action = new TouchAction(testCase.getMobileDriver());
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int startx = (dimension.width * 20) / 100;
					int starty = (dimension.height * 62) / 100;
					int endx = (dimension.width * 22) / 100;
					int endy = (dimension.height * 35) / 100;
					testCase.getMobileDriver().swipe(startx, starty, endx, endy, 1000);
				} else {
					action.press(10, (int) (dimension.getHeight() * .9)).moveTo(0, -(int) (dimension.getHeight() * .6))
							.release().perform();
				}
			}
			break;
		}
		default: {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid option: " + expectedOption.get(0));
		}
			if (flag) {
				Keyword.ReportStep_Pass(testCase, "Navigated back to " + expectedOption.get(0));
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Failed to navigate bake to " + expectedOption.get(0));
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
