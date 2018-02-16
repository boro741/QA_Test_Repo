package com.honeywell.lyric.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.honeywell.CHIL.CHILUtil;
import com.honeywell.account.information.LocationInformation;
import com.honeywell.commons.coreframework.Keyword;
import com.honeywell.commons.coreframework.SuiteConstants;
import com.honeywell.commons.coreframework.SuiteConstants.SuiteConstantTypes;
import com.honeywell.commons.coreframework.TestCaseInputs;
import com.honeywell.commons.coreframework.TestCases;
import com.honeywell.commons.deviceCloudProviders.PCloudyExecutionDesiredCapability.PCloudyDeviceInformation;
import com.honeywell.commons.mobile.CustomDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.perfecto.PerfectoLabUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.Dashboard;
import com.honeywell.screens.LoginScreen;
import com.honeywell.screens.OSPopUps;
import com.honeywell.screens.SecretMenu;

import io.appium.java_client.MobileBy;

public class LyricUtils {
	/**
	 * <h1>Take Screenshot</h1>
	 * <p>
	 * The takeScreenShot method takes a screen shot on the device and stores it in
	 * the path given through the the parameter
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param path
	 *            Path to where the screen shot has to be saved
	 * @param driver
	 *            The driver instantiated to run the test case
	 * @return String File name of the screen shot
	 */
	public static String takeScreenShot(String path, WebDriver driver) {
		String scrName = "#";
		if (driver == null) {
			return scrName;
		} else {
			try {
				File scrSht = ((TakesScreenshot) new Augmenter().augment((RemoteWebDriver) driver))
						.getScreenshotAs(OutputType.FILE);
				String temp = scrSht.getName();

				File scrFolder = new File(path);

				FileUtils.copyFileToDirectory(scrSht, scrFolder);
				scrName = temp;
			} catch (Exception e) {
				scrName = scrName + "Error : " + e.getCause();
			}
		}
		return scrName;
	}

	/**
	 * <h1>Get Location Information</h1>
	 * <p>
	 * The getLocationInformation method gets location details stored in CHIL of the
	 * location name provided to the test case.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return JSONObject Location details of the location name
	 */
	public static JSONObject getLocationInformation(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {
				long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));

				if (locationID == -1) {
					return jsonObject;
				}

				if (chUtil.isConnected()) {
					String chilURL = getCHILURL(testCase, inputs);
					String url = chilURL + "api/v3/locations/" + locationID;
					HttpURLConnection connection = chUtil.doGetRequest(url);

					try {

						if (connection != null) {

							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

							String inputLine;
							StringBuffer html = new StringBuffer();

							while (!in.ready()) {
							}

							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}

							in.close();

							jsonObject = new JSONObject(html.toString().trim());

						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"Get Location Information : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}

					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get StatLocation Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}
				}

			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get Location Information  : Unable to connect to CHIL.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Location Information  : Unable to get location. Error occured - " + e.getMessage());
			jsonObject = null;
		}

		return jsonObject;
	}

	/**
	 * <h1>Get Device Information</h1>
	 * <p>
	 * The getDeviceInformation method gets device details stored in CHIL of the
	 * device name and location name provided to the test case.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return JSONObject Device details of the device name and location name
	 */
	public static JSONObject getDeviceInformation(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;

		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {
				long locationID = chUtil.getLocationID(inputs.getInputValue("LOCATION1_NAME"));

				if (locationID == -1) {
					return jsonObject;
				}

				if (chUtil.isConnected()) {
					String chilURL = getCHILURL(testCase, inputs);
					String url = chilURL + "api/v3/locations/" + locationID;

					HttpURLConnection connection = chUtil.doGetRequest(url);

					try {

						if (connection != null) {

							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

							String inputLine;
							StringBuffer html = new StringBuffer();

							while (!in.ready()) {
							}

							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}

							in.close();

							JSONObject jsonObj = new JSONObject(html.toString().trim());

							JSONArray array = (JSONArray) jsonObj.get("devices");

							JSONObject tempJSONObject = null;

							boolean elementFound = false;

							for (int counter = 0; counter < array.length(); counter++) {
								tempJSONObject = array.getJSONObject(counter);

								if (inputs.getInputValue("LOCATION1_DEVICE1_NAME")
										.equalsIgnoreCase(tempJSONObject.getString("userDefinedDeviceName"))) {

									jsonObject = array.getJSONObject(counter);
									elementFound = true;
									break;
								}
							}

							if (elementFound) {
							} else {
								Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
										"Get Stat Information : Stat not found by name - "
												+ inputs.getInputValue("LOCATION1_DEVICE1_NAME"));
							}
						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
									"Get Stat Information : Location not found by name - "
											+ inputs.getInputValue("LOCATION1_NAME"));
						}

					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FRAMEWORK_CONFIGURATION,
								"Get Stat Information  : Error occured - " + e.getMessage());
						jsonObject = null;
					}

				} else {
					Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
							"Get Stat Information  : Unable to connect to CHIL.");
				}
			}
		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Stat Information  : Unable to get information for Stat - "
							+ inputs.getInputValue("LOCATION1_DEVICE1_NAME") + " at location - "
							+ inputs.getInputValue("LOCATION1_NAME") + " : Error occured - " + e.getMessage());
			jsonObject = null;
		}

		return jsonObject;
	}

	/**
	 * <h1>Get CHIL Url</h1>
	 * <p>
	 * The getCHILURL method returns the url of the environment provided to the test
	 * case
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return String URL of the environment provided to the test case
	 */
	public static String getCHILURL(TestCases testCase, TestCaseInputs inputs) throws Exception {
		String chilURL = " ";
		try {
			String environment = inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT);
			environment = environment.replaceAll("\\s", "");
			if (environment.equalsIgnoreCase("Production")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_PRODUCTION");
			} else if (environment.equalsIgnoreCase("CHILInt(Azure)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_INT");
			} else if (environment.equalsIgnoreCase("ChilDev(Dev2)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DEV2");
			} else if (environment.equalsIgnoreCase("CHILStage(Azure)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_URL_STAGING");
			} else if (environment.equalsIgnoreCase("LoadTesting")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_LOAD_TESTING");
			} else if (environment.equalsIgnoreCase("ChilDas(QA)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_QA");
			} else if (environment.equalsIgnoreCase("ChilDas(Test)")) {
				chilURL = SuiteConstants.getConstantValue(SuiteConstantTypes.PROJECT_SPECIFIC, "CHIL_DAS_TEST");
			} else {
				throw new Exception("Invalid URL");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return chilURL;
	}

	/**
	 * <h1>Login in to the Lyric Application</h1>
	 * <p>
	 * The loginToLyricApp method click on the login button post launch/Application
	 * Environment Setup, inputs the email ID and password, and taps on the login
	 * button
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if all the button clicks and values were set
	 *         properly. Returns 'false' if there was an error on clicking any
	 *         buttons or setting any values
	 */
	public static boolean loginToLyricApp(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		LoginScreen ls = new LoginScreen(testCase);
		if (ls.isLoginButtonVisible() && !ls.isEmailAddressTextFieldVisible()) {
			flag = flag & ls.clickOnLoginButton();
		}
		if (ls.setEmailAddressValue(inputs.getInputValue("USERID").toString())) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileUtils.hideKeyboard(testCase.getMobileDriver());
			}
			Keyword.ReportStep_Pass(testCase,
					"Login To Lyric : Email Address set to - " + inputs.getInputValue("USERID"));
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Login To Lyric : Not able to set Email Address.");
			flag = false;
		}
		if (ls.setPasswordValue(inputs.getInputValue("PASSWORD").toString())) {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				MobileUtils.hideKeyboard(testCase.getMobileDriver());
			}
			Keyword.ReportStep_Pass(testCase, "Login To Lyric : Password set to - " + inputs.getInputValue("PASSWORD"));
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Login To Lyric : Not able to set Password.");
			flag = false;
		}
		if (ls.isLoginButtonVisible()) {
			flag = flag & ls.clickOnLoginButton();
		}
		return flag;
	}

	/**
	 * <h1>Verify If Login Is Successful</h1>
	 * <p>
	 * The verifyLoginSuccessful method verifies the if the user has successfully
	 * logged in by checking the presence of the weather icon on the dash board.
	 * Timeout of locating the weather icon is 2 minutes
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return boolean Returns 'true' if weather icon is found. Returns 'false' if
	 *         weather icon is not located.
	 */
	public static boolean verifyLoginSuccessful(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		OSPopUps os = new OSPopUps(testCase);
		Dashboard d = new Dashboard(testCase);
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);

		try {
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						int counter = 0;
						while (os.isAllowButtonVisible(1) && counter < 3) {
							os.clickOnAllowButton();
							counter++;
						}
						if (os.isNotNowButtonVisible(1)) {
							os.clickOnNotNowButton();
							return false;
						}
					} else {
						if (os.isCloseButtonVisible(1)) {
							os.clickOnCloseButton();
							return false;
						}
					}
					if (d.isWeatherIconVisible(1)) {
						return true;
					} else {
						return false;
					}

				}
			});
			if (isEventReceived) {
				Keyword.ReportStep_Pass(testCase, "Login to Lyric : Successfully navigated to HomeScreen");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Login To Lyric : Unable to navigate to homepage. Could not find notification icon on homepage");
			}

		} catch (TimeoutException e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Timed out while loading. Wait time : 2 minutes");
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	/**
	 * <h1>Close Application Launch Pop Ups</h1>
	 * <p>
	 * The closeAppLaunchPopups method closes all the application pop ups displayed on the device post the application launch.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @return boolean	Returns 'true' if all the pop ups have been closed successfully. Returns 'false' if any of the pop up fails to close
	 */
	public static boolean closeAppLaunchPopups(TestCases testCase) {
		boolean flag = true;
		OSPopUps os = new OSPopUps(testCase);
		LoginScreen ls = new LoginScreen(testCase);
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			if (os.isAllowButtonVisible(5)) {
				flag = flag & os.clickOnAllowButton();
			}
		} else {
			int counter = 0;
			while (!ls.isLyricLogoVisible() && counter < 5) {
				if (os.isAllowButtonVisible()) {
					flag = flag & os.clickOnAllowButton();
				}
				if (os.isOkButtonVisible()) {
					flag = flag & os.clickOnOkButton();
				}

				if (os.isCancelButtonVisible()) {
					flag = flag & os.clickOnCancelButton();
				}
				counter++;
			}
		}
		return flag;
	}

	/**
	 * <h1>Set Application Environment</h1>
	 * <p>
	 * The set application environment method navigates to the secret menu from the login screen, set the application environment provided to the test case, and navigates back to the login screen
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return	boolean	Returns 'true' if the environment is successfully set. Returns 'false' if the environment is not set successfully
	 */
	public static boolean setAppEnvironment(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			String environmentToSelect = inputs.getInputValue(TestCaseInputs.APP_ENVIRONMENT);
			LoginScreen ls = new LoginScreen(testCase);
			SecretMenu sm = new SecretMenu(testCase);
			flag = flag & ls.longPressOnSecretMenuImage();

			if (sm.isWebServerURLVisible()) {
				flag = flag & sm.clickOnWebServerURL();
				// Keeping this explicit wait because sometimes the environment selection fails
				// on ANDROID
				Thread.sleep(1000);
			}
			environmentToSelect = environmentToSelect.replaceAll("\\s", "");
			if (environmentToSelect.equalsIgnoreCase("ChilDas(QA)")) {
				flag = flag & sm.clickOnCHILDASQAOption();
			} else if (environmentToSelect.equalsIgnoreCase("Production")) {
				flag = flag & sm.clickOnProductionOption();
			} else if (environmentToSelect.equalsIgnoreCase("CHILStage(Azure)")) {
				flag = flag & sm.clickOnCHILStageAzureOption();
			} else if (environmentToSelect.equalsIgnoreCase("CHILInt(Azure)")) {
				flag = flag & sm.clickOnCHILIntAzureOption();
			} else if (environmentToSelect.equalsIgnoreCase("ChilDev(Dev2)")) {
				flag = flag & sm.clickOnCHILDevDev2Option();
			} else if (environmentToSelect.equalsIgnoreCase("LoadTesting")) {
				flag = flag & sm.clickOnCHILLoadTestingOption();
			} else if (environmentToSelect.equalsIgnoreCase("ChilDas(Test)")) {
				flag = flag & sm.clickOnCHILDASTestOption();
			} else {
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Invalid Environment");
				return false;
			}
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				flag = flag & MobileUtils.pressBackButton(testCase);
			} else {
				flag = flag & sm.clickOnDoneButton();
			}
			flag = flag & ls.isLyricLogoVisible();
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Set App Environment :  Error Occured - " + e.getMessage());
			flag = false;
		}

		return flag;
	}

	public static boolean launchAndLoginToApplication(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		flag = MobileUtils.launchApplication(inputs, testCase, true);
		flag = flag & LyricUtils.closeAppLaunchPopups(testCase);
		flag = flag & LyricUtils.setAppEnvironment(testCase, inputs);
		flag = flag & LyricUtils.loginToLyricApp(testCase, inputs);
		flag = flag & LyricUtils.verifyLoginSuccessful(testCase, inputs);
		return flag;
	}

	public static TimeZone getDeviceTimeZone(TestCases testCase, TestCaseInputs inputs) throws Exception {
		TimeZone timeZone = null;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			String zone = new String();
			try {
				if (inputs.isRunningOn("Local")) {
					if (inputs.isInputAvailable("ANDROID_UDID")) {
						zone = ADBUtils.getAndroidMobileDeviceTimeZone(inputs.getInputValue("ANDROID_UDID"));
					} else {
						zone = ADBUtils.getAndroidMobileDeviceTimeZone();
					}
				} else if (inputs.isRunningOn("Perfecto")) {
					zone = PerfectoLabUtils.getTimeZoneAndroidOnly(testCase.getMobileDriver());
				} else if (inputs.isRunningOn("pCloudy")) {
					PCloudyDeviceInformation deviceInfo = testCase.getPcloudyDeviceInformation();
					zone = deviceInfo.getpCloudySession().getConnector().executeAdbCommand(deviceInfo.getAuthToken(),
							deviceInfo.getBookingDtoDevice(), "adb shell getprop persist.sys.timezone");
				} else if (inputs.isRunningOn("TestObject")) {
					zone = "CET";
				} else if (inputs.isRunningOn("Saucelabs")) {
					zone = "CET";
				}
				zone = zone.trim();
				timeZone = TimeZone.getTimeZone(zone);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			try {
				if (inputs.isRunningOn("Local")) {
					timeZone = TimeZone.getDefault();
				} else if (inputs.isRunningOn("Perfecto")) {
					timeZone = TimeZone.getTimeZone("US/Eastern");
				} else if (inputs.isRunningOn("Saucelabs")) {
					timeZone = TimeZone.getTimeZone("US/Pacific");
				} else if (inputs.isRunningOn("TestObject")) {
					timeZone = TimeZone.getTimeZone("CET");
				}
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return timeZone;
	}

	public static String getDeviceTime(TestCases testCase, TestCaseInputs inputs) {
		String time = " ";
		try {
			Calendar date = Calendar.getInstance(LyricUtils.getDeviceTimeZone(testCase, inputs));
			String ampm;
			if (date.get(Calendar.AM_PM) == Calendar.AM) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			String hour;
			if (date.get(Calendar.HOUR) == 0) {
				hour = "12";
			} else {
				hour = String.valueOf(date.get(Calendar.HOUR));
			}
			String minute;
			if (date.get(Calendar.MINUTE) < 10) {
				minute = "0" + date.get(Calendar.MINUTE);
			} else {
				minute = String.valueOf(date.get(Calendar.MINUTE));
			}
			int month = date.get(Calendar.MONTH) + 1;
			time = String.valueOf(date.get(Calendar.YEAR) + "-" + month + "-" + date.get(Calendar.DAY_OF_MONTH) + "T"
					+ hour + ":" + minute + " " + ampm);
		} catch (Exception e) {
			time = "";
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Android Device Time : Error Occured : " + e.getMessage());
		}

		return time;
	}

	public static JSONObject getAllAlertsThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
		JSONObject jsonObject = null;
		try (CHILUtil chUtil = new CHILUtil(inputs)) {

			if (chUtil.getConnection()) {
				if (chUtil.isConnected()) {
					LocationInformation locInfo = new LocationInformation(testCase, inputs);
					String chapiURL = getCHILURL(testCase, inputs);
					String url = chapiURL + "api/v2/users/" + locInfo.getUserID() + "/Alerts";
					HttpURLConnection connection = chUtil.doGetRequest(url);

					try {

						if (connection != null) {

							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

							String inputLine;
							StringBuffer html = new StringBuffer();

							while (!in.ready()) {
							}

							while ((inputLine = in.readLine()) != null) {
								html.append(inputLine);
							}

							in.close();

							jsonObject = new JSONObject(html.toString().trim());

						} else {
							Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
									"Get All Alerts : Failed to get all Alerts");
						}

					} catch (IOException e) {
						Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
								"Get All Alerts : Failed to get all Alerts. Error occured - " + e.getMessage());
						jsonObject = null;
					}
				}

			} else {
				Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
						"Get All Alerts  : Unable to connect to CHAPI.");
			}

		} catch (Exception e) {

			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get All Alerts : Unable to get alerts. Error occured - " + e.getMessage());
			jsonObject = null;
		}
		return jsonObject;
	}

	public static List<Long> getAllAlertIDS(TestCases testCase, TestCaseInputs inputs) {
		List<Long> alertIDS = new ArrayList<Long>();
		try {
			JSONObject jsonObj = getAllAlertsThroughCHIL(testCase, inputs);
			JSONArray jsonArray = jsonObj.getJSONArray("userAlerts");
			for (int i = 0; i < jsonArray.length(); i++) {
				alertIDS.add(jsonArray.getJSONObject(i).getLong("id"));
			}
		} catch (JSONException e) {
			System.out.println("No Alerts found");
		} catch (Exception e) {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return alertIDS;
	}

	public static boolean dismissAllAlerts(TestCases testCase, TestCaseInputs inputs) {
		boolean flag = true;
		try {
			@SuppressWarnings("resource")
			CHILUtil chUtil = new CHILUtil(inputs);
			if (chUtil.getConnection()) {
				if (chUtil.isConnected()) {
					List<Long> alertIDS = LyricUtils.getAllAlertIDS(testCase, inputs);
					int result = chUtil.dismissAllAlerts(alertIDS);
					if (result == 200) {
						Keyword.ReportStep_Pass(testCase, "Successfully dismissed alerts with ids : " + alertIDS);
					} else {
						flag = false;
						Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
								"Failed to dismiss alerts with ids : " + alertIDS);
					}
				}
			} else {
				flag = false;
				throw new Exception("Failed to connect to CHIL");
			}
		} catch (Exception e) {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Error Occured : " + e.getMessage());
		}
		return flag;
	}

	public static boolean scrollToElementUsingExactAttributeValue(TestCases testCase, String attribute, String value)
			throws Exception {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (testCase.getMobileDriver()
						.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
								+ "new UiSelector()." + attribute + "(\"" + value + "\"));")) != null) {
					return true;
				} else {
					return false;
				}
			} else {
				JavascriptExecutor js = (JavascriptExecutor) testCase.getMobileDriver();
				HashMap<Object, Object> scrollObject = new HashMap<>();
				scrollObject.put("predicateString", attribute + " == '" + value + "'");
				js.executeScript("mobile:scroll", scrollObject);
				WebElement element = testCase.getMobileDriver()
						.findElement(MobileBy.iOSNsPredicateString(attribute + " == '" + value + "'"));
				if (element.getAttribute(attribute).equals(value)) {
					return true;
				} else {
					return false;
				}
			}

		} catch (NoSuchElementException e) {
			throw new Exception("Element with text : '" + value + "' not found");
		} catch (Exception e) {
			throw new Exception("Error Occured: " + e.getMessage());
		}
	}

	public static boolean scrollToElementUsingAttributeSubStringValue(TestCases testCase, String attribute,
			String value) throws Exception {
		try {
			if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
				if (testCase.getMobileDriver()
						.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("
								+ "new UiSelector().textContains(\"" + value + "\"));")) != null) {
					return true;
				} else {
					return false;
				}
			} else {
				JavascriptExecutor js = (JavascriptExecutor) testCase.getMobileDriver();
				HashMap<Object, Object> scrollObject = new HashMap<>();
				scrollObject.put("predicateString", attribute + " CONTAINS '" + value + "'");
				js.executeScript("mobile: scroll", scrollObject);
				WebElement element = testCase.getMobileDriver()
						.findElement(MobileBy.iOSNsPredicateString(attribute + " CONTAINS '" + value + "'"));
				if (element.getAttribute(attribute).contains(value)) {
					return true;
				} else {
					return false;
				}

			}

		} catch (NoSuchElementException e) {
			throw new Exception("Element with text : '" + value + "' not found");
		} catch (Exception e) {
			throw new Exception("Error Occured: " + e.getMessage());
		}
	}

	public static boolean clickOnAddDeviceIcon(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AddNewDeviceIcon")) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "AddNewDeviceIcon")) {
				flag = false;
			}
		}

		return flag;
	}

	public static boolean clickOnCancelButtonOfAddDeviceScreen(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "AddNewDevice");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "CancelButton")) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "CancelButton")) {
				flag = false;
			}
		}

		return flag;
	}

	public static boolean clickOnGlobalButtonOfDashboard(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton")) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "GlobalDrawerButton")) {
				flag = false;
			}
		}

		return flag;
	}

	public static boolean clickOnAddNewDeviceMenu(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "AddNewDeviceMenu")) {
			if (!MobileUtils.clickOnElement(fieldObjects, testCase, "AddNewDeviceMenu")) {
				flag = false;
			}
		}

		return flag;
	}

	public static boolean clickOnZwaveFromAddNewDevice(TestCases testCase) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "AddNewDevice");
		fieldObjects = MobileUtils.loadObjectFile(testCase, "AddNewDevice");
		try {
			LyricUtils.scrollToElementUsingExactAttributeValue(testCase, "value", "Z-Wave Device");
		} catch (Exception e) {
			System.out.println("Not able to locate");
		}
		flag = flag & MobileUtils.clickOnElement(fieldObjects, testCase, "ZwaveList");
		return flag;
	}

	public static boolean verifyDeviceDisplayedOnDashboard(TestCases testCase, String expectedDevice) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "Dashboard");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardIconText", 30)) {
			List<WebElement> dashboardIconText = MobileUtils.getMobElements(fieldObjects, testCase,
					"DashboardIconText");
			boolean f = false;
			for (WebElement e : dashboardIconText) {
				String displayedText = "";
				if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
					displayedText = e.getText();
				} else {
					try {
						displayedText = e.getAttribute("value");
					} catch (Exception e1) {
					}
				}
				if (displayedText.equalsIgnoreCase(expectedDevice)) {
					f = true;
					break;
				}
			}
			if (f) {
				Keyword.ReportStep_Pass(testCase, "Device : " + expectedDevice + " is present on the dashboard.");
			} else {
				flag = false;
				Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
						"Device : " + expectedDevice + " is not present on the dashboard.");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "Dashboard Icons not found");
		}
		return flag;
	}
}
