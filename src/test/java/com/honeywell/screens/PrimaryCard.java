package com.honeywell.screens;

import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.mobile.MobileScreens;
import com.honeywell.commons.mobile.MobileUtils;

public class PrimaryCard extends MobileScreens{

	private static final String screenName = "PrimaryCard";		

	public PrimaryCard(TestCases testCase) {
		super(testCase,screenName);
	}
	
	public boolean isCogIconVisible()
	{
		return MobileUtils.isMobElementExists(objectDefinition, testCase, "CogIcon",3);
	}
	
	public boolean clickOnCogIcon()
	{
		return MobileUtils.clickOnElement(objectDefinition, testCase, "CogIcon");
	}

	}
