package com.honeywell.keywords.lyric.common;

import java.util.ArrayList;
import com.honeywell.commons.coreframework.AfterKeyword;
import com.honeywell.commons.coreframework.BeforeKeyword;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.KeywordException;
import com.honeywell.commons.coreframework.KeywordStep;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.report.FailType;
import com.honeywell.lyric.das.utils.DASZwaveUtils;
import com.honeywell.lyric.relayutils.ZWaveRelayUtils;
import com.honeywell.screens.AddNewDeviceScreen;
import com.honeywell.screens.ZwaveScreen;

public class ActionOnFunctionKeyRelay extends Keyword {

	private TestCases testCase;
	private TestCaseInputs inputs;
	public boolean flag = true;
	public ArrayList<String> deviceType;

	public ActionOnFunctionKeyRelay(TestCases testCase, TestCaseInputs inputs, ArrayList<String> deviceType) {
		this.testCase = testCase;
		this.deviceType = deviceType;
		this.inputs = inputs;
	}

	@Override
	@BeforeKeyword
	public boolean preCondition() throws KeywordException {
		return flag;
	}

	@Override
	@KeywordStep(gherkins = "^user (.*) the (.*) function key$")
	public boolean keywordSteps() throws KeywordException {
		try {
			if(deviceType.get(0).equalsIgnoreCase("activates for discovery")){
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				int i=0;
				if (deviceType.get(1).equalsIgnoreCase("Switch")) {
					do{
						Keyword.ReportStep_Pass(testCase, "Activating function key on Switch");
						DASZwaveUtils.activateZwaveSwitch(testCase, inputs);
						i++;
						if(zScreen.isDeviceNotFoundPopupDisplayed(10)){
							flag = flag & zScreen.clickRetryOnDeviceNotFoundPopUp();
							flag = flag & zScreen.isActivateZwaveScreenDisplayed();
						}
					}
					while(i<3 && !zScreen.isNamingFieldDisplayed(10) && zScreen.isActivateZwaveScreenDisplayed() );

				}	else if (deviceType.get(1).equalsIgnoreCase("Dimmer")) {
					do{
						Keyword.ReportStep_Pass(testCase, "Activating function key on Dimmer");
						DASZwaveUtils.activateZwaveDimmer(testCase, inputs);
						i++;
						if(zScreen.isDeviceNotFoundPopupDisplayed(10)){
							flag = flag & zScreen.clickRetryOnDeviceNotFoundPopUp();
							flag = flag & zScreen.isActivateZwaveScreenDisplayed();
						}
					}while(i<3 && !zScreen.isNamingFieldDisplayed(10) && zScreen.isActivateZwaveScreenDisplayed());
				}
			}
			else if(deviceType.get(0).equalsIgnoreCase("activates for exclusion")){
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				AddNewDeviceScreen ads = new AddNewDeviceScreen(testCase);
				int i=0;
				if (deviceType.get(1).equalsIgnoreCase("Switch")) {
					do{
						
						Keyword.ReportStep_Pass(testCase, "Activating function key on Switch");
						DASZwaveUtils.activateZwaveSwitch(testCase, inputs);
						if(zScreen.isDeviceNotFoundPopupDisplayed(5)){
							//retry on device not found popup
							if(zScreen.isRetryOnDeviceNotFoundPopUpDisplayed(5)){
								flag = flag & zScreen.clickRetryOnDeviceNotFoundPopUp();
							} else if(zScreen.isOKOnDeviceExcludedPopUpDisplayed(5)){
								flag = flag & zScreen.clickOKOnDeviceExcludedPopUp();
								if(ads.isAddNewDeviceHeaderDisplayed(5)){
									//select zdevice from add device screen
									flag = flag &ads.clickOnZwaveFromAddNewDevice();
									DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
									flag = flag & zScreen.clickTryExcludeOnDeviceNotFoundPopUp();
								}else if(zScreen.isGeneralDeviceExclusionMenuDisplayed()){
									//select general exclusion from zdevice
									flag = flag & zScreen.clickGeneralDeviceExclusionMenu();
								}
							}else if(ads.isAddNewDeviceHeaderDisplayed(5)){
								//select zdevice from add device screen
								flag = flag &ads.clickOnZwaveFromAddNewDevice();
								DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
								flag = flag & zScreen.clickTryExcludeOnDeviceNotFoundPopUp();
							}
						}

						i++;
					}
					while(i<3 && !zScreen.isExcludedSuccessPopupMessageDisplayed(10) && zScreen.isExcludeZwaveScreenDisplayed());

				}	else if (deviceType.get(1).equalsIgnoreCase("Dimmer")) {
					do{
						Keyword.ReportStep_Pass(testCase, "Activating function key on Dimmer");
						DASZwaveUtils.activateZwaveDimmer(testCase, inputs);
						if(zScreen.isDeviceNotFoundPopupDisplayed(5)){//retry on device not found popup
							if(zScreen.isRetryOnDeviceNotFoundPopUpDisplayed(5)){
								flag = flag & zScreen.clickRetryOnDeviceNotFoundPopUp();
							} else if(zScreen.isOKOnDeviceExcludedPopUpDisplayed(5)){
								flag = flag & zScreen.clickOKOnDeviceExcludedPopUp();
								if(ads.isAddNewDeviceHeaderDisplayed(5)){
									//select zdevice from add device screen
									flag = flag &ads.clickOnZwaveFromAddNewDevice();
									DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
									flag = flag & zScreen.clickTryExcludeOnDeviceNotFoundPopUp();
								}else if(zScreen.isGeneralDeviceExclusionMenuDisplayed()){
									//select general exclusion from zdevice
									flag = flag & zScreen.clickGeneralDeviceExclusionMenu();
								}
							}else if(ads.isAddNewDeviceHeaderDisplayed(5)){
								//select zdevice from add device screen
								flag = flag &ads.clickOnZwaveFromAddNewDevice();
								DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
								flag = flag & zScreen.clickTryExcludeOnDeviceNotFoundPopUp();
							}
						}
						i++;
					}while(i<3 && !zScreen.isExcludedSuccessPopupMessageDisplayed(10) && zScreen.isExcludeZwaveScreenDisplayed());
				}
			}
			else if(deviceType.get(0).equalsIgnoreCase("activates for replacement")){
				ZwaveScreen zScreen = new ZwaveScreen(testCase);
				int i=0;
				if (deviceType.get(1).equalsIgnoreCase("Switch")) {
					do{
						Keyword.ReportStep_Pass(testCase, "Activating function key on Switch");
						DASZwaveUtils.activateZwaveSwitch(testCase, inputs);
						i++;
					}
					while(i<3 && !zScreen.isReplaceScreenDisplayed() && zScreen.isReplaceScreenMessageDisplayed());

				}	else if (deviceType.get(1).equalsIgnoreCase("Dimmer")) {
					do{
						Keyword.ReportStep_Pass(testCase, "Activating function key on Dimmer");
						DASZwaveUtils.activateZwaveDimmer(testCase, inputs);
						i++;
					}while(i<3 && !zScreen.isReplaceScreenDisplayed() && zScreen.isReplaceScreenMessageDisplayed());
				}
			}
			else if(deviceType.get(0).equalsIgnoreCase("does not activate")){
				DASZwaveUtils.timeOutForNoActivatedDevice(testCase);
			}else if(deviceType.get(0).equalsIgnoreCase("disconnects")){
				if (deviceType.get(1).equalsIgnoreCase("Switch power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Switch");
					ZWaveRelayUtils.powerOffZwaveSwitch(inputs);
				}
				else if (deviceType.get(1).equalsIgnoreCase("Dimmer power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Dimmer");
					ZWaveRelayUtils.powerOffZwaveDimmer(inputs);
				}
			}else if(deviceType.get(0).equalsIgnoreCase("connects")){
				if (deviceType.get(1).equalsIgnoreCase("Switch power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Switch");
					ZWaveRelayUtils.powerOnZwaveSwitch(inputs);
				}
				else if (deviceType.get(1).equalsIgnoreCase("Dimmer power")) {
					Keyword.ReportStep_Pass(testCase, "Disconnecting function key on Dimmer");
					ZWaveRelayUtils.powerOnZwaveDimmer(inputs);
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
