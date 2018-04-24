package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {    
    /**
     * The Starting point of Automation Framework.
     *
     * @param groups
     *            String[] array type, represents the groups to execute passed
     *            from Command line Arguments.
     */
	public static void main(String[] commandLineArguments) throws Exception {
			
		commandLineArguments = new String[] {
				
				//"--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
				"--appToInstall", "Android:400000172,IOS:4000190", 
				"--groups", 
				
//				"CommandControlviewSecuritysolutioncard",
//				"CommandControlfromHomemode",
//				"CommandControlfromAwaymode",
//				"CommandControlfromNightmodemode",
//				"CommandControlfromOffmode",
//				"CommandAndControlSecurityStatesWhenNavigatedBackToDashboard",
//				"CommandAndControlSecurityStatesUpdateInDashboard",
//				"PushNotificationWhenSwitchedToDiffSecurityStates",
//				"UserpressesbackbuttonWhileSwitchingModes",
//				"CommandControlWhenDoorSensorIsInFault",
//				"CommandControlWhenWindowSensorIsInFault",
				"CommandControlWhenMotionSensorIsInFault",
				
//				"DIYCancelSetUp",
//				"DIYCancelSetUpInRegisterBaseStation",
//				"DIYWhenQRCodeIsNotScanned",
//				"DIYCancelSetUpInConnectToNetworkScreen",
//				"DIYInvalidWiFiPassword",
//				"DIYWhenInvalidQRCodeIsScanned",
//				"DIYRegistrationWhenSingleBaseStationIsAvailable",
//				"DIYRegistrationWithNewLocationAndBaseStationName",
//				"DIYAddAWiFiNetworkWithInvalidPwdAndTryReconnectingWithAvailableNetwork",
//				"DIYRegistrationByNavigatingToOtherApps",
//				"DIYTryToReRegisterDAS",
//				"DIYDeleteExistingDASAndRegisterIt",
				
				//Add sensor and enabling geofencing scenario
				//"DIYRegistrationWithAddSensorAndEnableGeoFencing",
				
				//No base station should be available for the following scenario
				//"DIYWhenNoBaseStationsAreAvailable",
				
				//Following scenarios require Multiple base stations
				//"DIYRefreshBaseStationsList",
				//"DIYMultipleDASRegistrationsForTheSameAccount",
				
				//Invalid scenario, as UI is changed
				//"DIYSmartHomeSecurityOptionInAddNewDevice",
				
				//Following scenarios require SetUp
				//"DIYAddAWiFiNetwork",
				//"DIYConnectingToOpenWiFiNetwork",
				
				//Following scenarios are not automatable
				//"DIYDenyAppAccessToLocationServices",
				//"DIYDisconnectDASDevice",
				//"DIYTimeoutInDASDevice",
				//"DIYTurnOffMobileDeviceBluetooth",
				//"DIYMoveAwayFromDASDeviceAfterScanningQRCode",
				//"DIYTryToReRegisterDASAfterPerformingFactorySettingsOnDAS",
				//"DIYTryToReRegisterDASWhenDASIsOffline",
				//"DIYRegistrationByReceivingCallsAndMsgs",
				};
			try {
				SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
				suiteUtils.executeSuite();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
