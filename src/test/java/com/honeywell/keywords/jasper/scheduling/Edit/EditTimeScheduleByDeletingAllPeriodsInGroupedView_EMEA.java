package com.honeywell.keywords.jasper.scheduling.Edit;

import java.util.ArrayList;
import java.util.Random;

import com.honeywell.account.information.DeviceInformation;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;

import com.honeywell.jasper.utils.JasperSchedulingEditUtils;
import com.honeywell.lyric.das.utils.DashboardUtils;
import com.honeywell.lyric.utils.InputVariables;
import com.honeywell.screens.SchedulingScreen;


public class EditTimeScheduleByDeletingAllPeriodsInGroupedView_EMEA extends Keyword {

	public TestCases testCase;
	public TestCaseInputs inputs;
	public ArrayList<String> exampleData;
	public boolean flag = true;
	String schedulePeriodToSelect = "", temp = "";
	int i = 0;
	ArrayList<String> arrlist = new ArrayList<String>(8);

	public EditTimeScheduleByDeletingAllPeriodsInGroupedView_EMEA(TestCases testCase, TestCaseInputs inputs, ArrayList<String> exampleData) {
		this.testCase = testCase;
		this.inputs = inputs;
		this.exampleData = exampleData;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user tries to delete \"(.+)\" in EMEA schedule screen$")
	public boolean keywordSteps() throws KeywordException {
		try {
			int NoOfPeriods = 0;
			SchedulingScreen s = new SchedulingScreen(testCase);
			if (exampleData.get(0).equalsIgnoreCase("All Periods")) {
				NoOfPeriods = s.getSchedulePeriodTimeElement().size();
			}
			DeviceInformation statInfo = new DeviceInformation(testCase, inputs);
			String jasperStatType = statInfo.getJasperDeviceType();
			if (!statInfo.isOnline()) {
				Keyword.ReportStep_Pass(testCase, "Thermostat is offline");
				return true;
			}
			if (jasperStatType.equals("EMEA")) {
				inputs.setInputValue(InputVariables.JASPER_STAT_TYPE, jasperStatType);
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					int deletioncount = NoOfPeriods;
					for (int j = 1; j <= NoOfPeriods-1; j++) {
						Random rn = new Random();
						if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
							schedulePeriodToSelect = rn.nextInt((deletioncount - 1)+1) + 1 + "_Everyday";
							flag = flag
									& JasperSchedulingEditUtils.editTimeBasedScheduleByDeletingPeriods(testCase, inputs, schedulePeriodToSelect,j);
							if(flag==true){
								deletioncount--;
							}
						}
					} 
				} else{
					int deletioncount = NoOfPeriods;
					for (int j = 1; j <= NoOfPeriods-1; j++) {
						Random rn = new Random();
						if (inputs.getInputValue(InputVariables.SHOW_VIEW_TYPE).equalsIgnoreCase("Grouped Days")) {
							int periodsuffix = rn.nextInt((deletioncount - 1)+1) + 1;
							schedulePeriodToSelect = "Everyday_"+ periodsuffix;
							flag = flag
									& JasperSchedulingEditUtils.editTimeBasedScheduleByDeletingPeriods(testCase, inputs, schedulePeriodToSelect,j);
							if(flag==true){
								deletioncount--;
							}
						}
					}	
				}
				flag = flag & DashboardUtils.navigateToDashboardFromAnyScreen(testCase);
			}
		}catch (Exception e){

		}

		return flag;
	}

	@Override
	@AfterKeyword
	public boolean postCondition() throws KeywordException {
		return true;
	}
}