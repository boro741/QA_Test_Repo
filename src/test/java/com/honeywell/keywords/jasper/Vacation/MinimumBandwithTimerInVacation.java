package com.honeywell.keywords.jasper.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.VacationHoldScreen;

public class MinimumBandwithTimerInVacation extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public boolean flag = true;
	ArrayList<String> exampleData;

	public MinimumBandwithTimerInVacation(TestCases testCase, TestCaseInputs inputs,ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs=inputs;
		this.exampleData=exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^Minimum bandwidth timer between from and to is (.*) hour$")
	public boolean keywordSteps() throws KeywordException {
		VacationHoldScreen vhs = new VacationHoldScreen(testCase);
		if(exampleData.get(0).equalsIgnoreCase("1")) {
			try {
				String vacationStart=vhs.getStartTime();
				String vacationEnd=vhs.getEndTime();
				SimpleDateFormat vacationTimeFormatStart = new SimpleDateFormat("HH:mm a");
				Date startTime;
				Date endTime;
				try {
					startTime = vacationTimeFormatStart.parse(vacationStart);
					endTime = vacationTimeFormatStart.parse(vacationEnd);
					long dif = endTime.getTime()-startTime.getTime();
					if(vacationStart.contains("12:") && (vacationStart.contains("AM")||vacationStart.contains("PM"))){
						if(vacationEnd.contains("01:")){
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 1 hour");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 1 hour");
							flag=false;
						}
					}else if(vacationStart.contains("24:") && !(vacationStart.contains("AM")||vacationStart.contains("PM"))){
						if(vacationEnd.contains("00:")){
							Keyword.ReportStep_Pass(testCase,
									"Vacation Start and Current Time is 1 hour");
						}else{
							Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
									"Wait for Vacation start : Differnece between vacation start time and current time is greater than 1 hour");
							flag=false;
						}
					}
					
					else{
					int diffInHours = (int) ((dif / 1000) / 60/60);
					if (diffInHours > Integer.parseInt(exampleData.get(0))) {
						flag=false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Wait for Vacation start : Differnece between vacation start time and current time is greater than 1 hour");

					}
					else if(diffInHours<=Integer.parseInt(exampleData.get(0))) {
						Keyword.ReportStep_Pass(testCase,
								"Vacation Start and Current Time is 1 hour");
						flag=true;
					}
					else if (diffInHours < 0) {
						Keyword.ReportStep_Pass(testCase,
								"Wait for Vacation Start : Vacation start time is past current device time");
						flag=true;
					}
					}
				} catch (ParseException e) {
					flag=false;
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Error Occured:"+e.getMessage());
				}

			} catch (Exception e) {
				flag=false;
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Error Occured:"+e.getMessage());
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

