package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.lyric.utils.LyricUtils;

public class SecretMenu extends MobileScreens {

	private static final String screenName = "SecretMenu";

	public SecretMenu(TestCases testCase) {
		super(testCase, screenName);
	}

	public boolean isWebServerURLVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "WebServerURL", 3);
	}

	public boolean clickOnWebServerURL() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "WebServerURL");
	}

	public boolean isCHILDASQAOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChilDasQAOption", 3);
	}

	public boolean isProductionOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ProductionOption", 3);
	}

	public boolean isCHILStageAzureOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CHILStageAzureOption", 3);
	}

	public boolean isCHILIntAzureOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CHILIntAzureOption", 3);
	}

	public boolean isCHILDevDev2OptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChilDevDev2Option", 3);
	}

	public boolean isCHILLoadTestingOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "LoadTestingOption", 3);
	}

	public boolean isCHILDASTestOptionVisible() {
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "ChilDasTestOption", 3);
	}

	public boolean clickOnCHILDASQAOption() throws Exception {
		if (this.isCHILDASQAOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDasQAOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Chil Das(QA)")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDasQAOption");
			}
			return false;
		}

	}

	public boolean clickOnProductionOption() throws Exception {
		if (this.isProductionOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ProductionOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Production")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "ProductionOption");
			}
			return false;
		}

	}

	public boolean clickOnCHILStageAzureOption() throws Exception {
		if (this.isCHILStageAzureOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CHILStageAzureOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "CHIL Stage (Azure)")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "CHILStageAzureOption");
			}
			return false;
		}
	}

	public boolean clickOnCHILIntAzureOption() throws Exception {
		if (this.isCHILIntAzureOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "CHILIntAzureOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "DEV - CHIL INT")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "CHILIntAzureOption");
			}
			return false;
		}
	}

	public boolean clickOnCHILDevDev2Option() throws Exception {
		if (this.isCHILDevDev2OptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDevDev2Option");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Chil Das(DEV2)")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDevDev2Option");
			}
			return false;
		}
	}

	public boolean clickOnCHILLoadTestingOption() throws Exception {
		if (this.isCHILLoadTestingOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "LoadTestingOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Chil Load(Test)")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "LoadTestingOption");
			}
			return false;
		}
	}

	public boolean clickOnCHILDASTestOption() throws Exception {
		if (this.isCHILDASTestOptionVisible()) {
			return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDasTestOption");
		} else {
			if (LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Chil Das(Test)")) {
				return MobileUtils.clickOnElement(objectDefinition, testCase, "ChilDasTestOption");
			}
			return false;
		}
	}

	public boolean clickOnDoneButton() {
		return MobileUtils.clickOnElement(objectDefinition, testCase, "DoneButton");
	}
	
}