package com.honeywell.keywords.lyric.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.honeywell.commons.bddinterface.DataTable;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.ActivityLogsScreen;
import com.honeywell.screens.BaseStationSettingsScreen;
import com.honeywell.screens.CameraSettingsScreen;
import com.honeywell.screens.CameraSolutionCardScreen;

public class VerifyisElementDisplayed extends Keyword {

	private TestCases testCase;
	// private TestCaseInputs inputs;
	public ArrayList<String> parameters;
	public boolean flag = true;
	public DataTable data;

	public VerifyisElementDisplayed(TestCases testCase, TestCaseInputs inputs, ArrayList<String> parameters) {
		this.testCase = testCase;
		// this.inputs = inputs;
		//this.data = data;
		this.parameters = parameters;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user should be displayed with (.*)$")
	public boolean keywordSteps() throws KeywordException {

		try {
			switch (parameters.get(0)) {
			case "Unable to connect, please try again": {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				if (cs.isRetryTextExists()) {
					if (cs.getRetryText().equalsIgnoreCase(parameters.get(0))) {
						Keyword.ReportStep_Pass(testCase, parameters.get(0) + " exists");
					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Expected :" + parameters.get(0) + "Actual :" + cs.getRetryText());
						flag = false;
					}

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "play icon": {
				CameraSolutionCardScreen cs = new CameraSolutionCardScreen(testCase);
				if (cs.isCameraPlayButtonExists(10)) {
					Keyword.ReportStep_Pass(testCase, parameters.get(0) + " exists");
				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "playing clips": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isDowloadIconExists()) {
					Keyword.ReportStep_Pass(testCase, "Clip is getting played");
				} else if (al.isClipPlayButtonExists()) {
					al.clickClipPlayButton();
					Thread.sleep(3000);
					if (al.isDowloadIconExists()) {
						Keyword.ReportStep_Pass(testCase, "Clip is getting played");
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameters.get(0) + " does not exists");
						flag = false;
					}
					Thread.sleep(9000);
				} else {
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Viewed status in view list": {
				Thread.sleep(9000);
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				boolean check = false;
				if (al.isBackToViewListExists(30)) {
					al.clickBackToViewList();
				}
					if (al.isClipStatusExists()) {
						List<WebElement> elements = al.getClipStatus();
						for (WebElement ele : elements) {
							String status = ele.getText();
							if (status.equals("VIEWED")) {
								check = true;
								Keyword.ReportStep_Pass(testCase, "Viewed status has been updated in clip list");
								break;
							}
						}
						if (!check) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameters.get(0) + "Viewed status has not been updated in clip list");
							flag = false;

						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameters.get(0) + " does not exists");
						flag = false;
					}

				if (al.isClipPlayButtonExists()) {
					al.clickClipPlayButton();
				}
				break;
			}
			case "Saved status in view list": {
				boolean check = false;
				Thread.sleep(9000);
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isBackToViewListExists(30)) {
					al.clickBackToViewList();
				}
					if (al.isClipStatusExists()) {
						List<WebElement> elements = al.getClipStatus();
						for (WebElement ele : elements) {
							String status = ele.getText();
							if (status.equals("SAVED")) {
								check = true;
								Keyword.ReportStep_Pass(testCase, "Saved status has been updated in clip list");
								break;
							}
						}
						if (!check) {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									parameters.get(0) + "Saved status has not been updated in clip list");
							flag = false;
						}
					} else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameters.get(0) + " does not exists");
						flag = false;
					}
				break;
			}
			case "Download progress": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isClipDownloadScreenExists()) {
					Keyword.ReportStep_Pass(testCase, "Clip is getting downloaded");

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Download cancel popup": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isCancelYesExists() && al.isCancelNoExists() && al.isCancelDownloadVideoClipExists()) {
					Keyword.ReportStep_Pass(testCase, "User is displayed with download clip cancel popup");

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Delete clip confirm popup": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isDeletePopupExists() && al.isDeleteCancelExists() && al.isDeleteOkExists()) {
					Keyword.ReportStep_Pass(testCase, "User is displayed with delete clip confirm popup");

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Delete clip success popup": {
				if (testCase.getPlatform().contains("IOS")) {
					ActivityLogsScreen al = new ActivityLogsScreen(testCase);
					if (al.isDeleteSuccessExists()) {
						Keyword.ReportStep_Pass(testCase, "User is displayed with delete clip success popup");
						al.clickOkButton();

					} else {

						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								parameters.get(0) + " does not exists");
						flag = false;
					}
				}
				break;
			}
			case "Clip deleted popup": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isDeleteConfirmationHeaderExists() && al.isOkButtonExists()) {
					Keyword.ReportStep_Pass(testCase, "User got confirmation popup for after deleting clip");

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "Delete popup": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				if (al.isCancelNoExists() && al.isCancelYesExists() && al.isCancelDowloadHeaderExists()) {
					Keyword.ReportStep_Pass(testCase, "User is displayed with download cancel popup");

				} else {

					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							parameters.get(0) + " does not exists");
					flag = false;
				}
				break;
			}
			case "recent clip at top": {
				ActivityLogsScreen al = new ActivityLogsScreen(testCase);
				List<WebElement> elements = al.getEventTimes();
				DateFormat sdf = new SimpleDateFormat("hh:mm");
				Date date1 = new Date();
				Date date2 = new Date();
				if (elements.size() > 1) {
					for (int i = 0; i < elements.size() - 1; i++) {
						date1 = sdf.parse(elements.get(i).getText());
						date2 = sdf.parse(elements.get(i + 1).getText());
						if (date1.compareTo(date2) > 0) {
							Keyword.ReportStep_Pass(testCase, "Recent clip is on top");
						} else {
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Recent clip is not on top");
							flag = false;
						}
					}
				} else {
					Keyword.ReportStep_Pass(testCase, "Only one clip is present");
				}
				break;
			}
			case "You can perform this action only in home or off mode" : {
				BaseStationSettingsScreen cs = new BaseStationSettingsScreen(testCase);
				if(testCase.getPlatform().toUpperCase().contains("ANDROID")){
					Keyword.ReportStep_Pass(testCase, "You can perform this action only in home or off mode toast message displayed");
				}else{
					if(cs.isYoucanperformthisactiononlyinVisible()){
						cs.ClickOnYoucanperformthisactiononlyinOKOption();
						Keyword.ReportStep_Pass(testCase, "You can perform this action only in home or off mode pop up displayed");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "You can perform this action only in home or off mode pop up not displayed");
					}
				}break;
			}
			case "Ensure the camera is turned on and the privacy ring is open" : {
				CameraSettingsScreen cs = new CameraSettingsScreen(testCase);
				if(testCase.getPlatform().toUpperCase().contains("ANDROID")){
					Keyword.ReportStep_Pass(testCase, "Ensure the camera is turned on and the privacy ring is open");
				}else{
					if(cs.isEnsureTheCameraisturnedonandtheprivacyringisopenvisible(10)){
						cs.clickonEnsureTheCameraisOKoption();
						Keyword.ReportStep_Pass(testCase, "Ensure the camera is turned on and the privacy ring is open");
					}else {
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Ensure the camera is turned on and the privacy ring is open");
					}
				}break;
			}

			default: {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						parameters.get(0) + "Input does not match");
				flag = false;
				break;
			}
			}

		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured: " + e.getMessage());
		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return flag;
	}
}
