package com.honeywell.suiteExecutor;

import com.honeywell.commons.coreframework.SuiteUtils;

public class SuiteExecutor {

	public static void main(String[] commandLineArguments) throws Exception {

		commandLineArguments = new String[] {
				// "--publishresult",
				// "--jira_credentials",
				// "aterbuild:aterbuild@123",
				"--useXCUITest", "true", 
				//"--deviceCloudProviderCredentials", "PCloudy::pratik.lalseta@honeywell.com:b5rjy3trvc2992yxzbzbtns9,Perfecto::pratik.lalseta@honeywell.com:Password1,TestObject_IOS::pratik.lalseta@honeywell.com:C2EA3CFC50A14D309F37661CDD60003C",
				"--appToInstall", "Android:400000054,IOS:57", 
				"--groups", 
				
				//"DIYCancelSetUp",
				//"DIYCancelSetUpInRegisterBaseStation",
				//"DIYWhenQRCodeIsNotScanned",
				//"DIYCancelSetUpInConnectToNetworkScreen",
				//"DIYInvalidWiFiPassword",
				//"DIYWhenInvalidQRCodeIsScanned",
				
				//"DIYRegistrationWhenSingleBaseStationIsAvailable",
				//"DIYRegistrationWithNewLocationAndBaseStationName",
				//"DIYAddAWiFiNetworkWithInvalidPwdAndTryReconnectingWithAvailableNetwork",
				//"DIYRegistrationByNavigatingToOtherApps",
				//"DIYTryToReRegisterDAS",
				//"DIYDeleteExistingDASAndRegisterIt",
				
				//Add sensor and enabling geofencing scenario
				"DIYRegistrationWithAddSensorAndEnableGeoFencing",
				
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

		//commandLineArguments = new String[] {"--help"};
		//commandLineArguments = new String[] {"--updateKeywordCatalogue"};
		
		// ============= Executes the Suite created from the Suite Configuration
		// JSON file =============
		try {
			SuiteUtils suiteUtils = SuiteUtils.getTestSuite(commandLineArguments);
			suiteUtils.executeSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
