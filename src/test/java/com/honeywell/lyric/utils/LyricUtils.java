package com.honeywell.lyric.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
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
import com.honeywell.commons.mobile.CustomIOSDriver;
import com.honeywell.commons.mobile.MobileObject;
import com.honeywell.commons.mobile.MobileUtils;
import com.honeywell.commons.perfecto.PerfectoLabUtils;
import com.honeywell.commons.report.FailType;
import com.honeywell.screens.CoachMarks;
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
	 * 
	 *         Modified On: 21/02/2018 by Surendar
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
			else
			{
				ls.clickOnLyricLogo();
			}
			Keyword.ReportStep_Pass(testCase, "Login To Lyric : Password set to - " + inputs.getInputValue("PASSWORD"));
		} else {
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Login To Lyric : Not able to set Password.");
			flag = false;
		}
		if (ls.isLoginButtonVisible()) {
			flag = flag & ls.clickOnLoginButton();
		} else {
			MobileUtils.hideKeyboardIOS(testCase.getMobileDriver(), "Go");
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
	public static boolean verifyLoginSuccessful(TestCases testCase, TestCaseInputs inputs, boolean... closeCoachMarks) {
		boolean flag = true;
		OSPopUps os = new OSPopUps(testCase);
		CoachMarks cm = new CoachMarks(testCase);
		Dashboard d = new Dashboard(testCase);
		FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
		fWait.pollingEvery(5, TimeUnit.SECONDS);
		fWait.withTimeout(3, TimeUnit.MINUTES);

		try {
			Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
				public Boolean apply(CustomDriver driver) {
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						try {
							((CustomIOSDriver) testCase.getMobileDriver()).switchTo().alert().accept();
							return false;
						} catch (Exception e) {
							if (os.isNotNowButtonVisible(1)) {
								os.clickOnNotNowButton();
								return false;
							} else if (cm.isGotitButtonVisible(1)) {
								if (closeCoachMarks.length > 0 && !closeCoachMarks[0]) {
									return true;
								} else {

									return LyricUtils.closeCoachMarks(testCase);
								}

							}
						}
					} else {
						if (os.isCloseButtonVisible(1)) {
							os.clickOnCloseButton();
							return false;
						}
					}
					if (testCase.getPlatform().toUpperCase().contains("IOS")) {
						return d.isWeatherIconVisible(1);
					} else {
						if (!d.isSplashScreenVisible(2) && !d.isProgressBarVisible(2)) {
							if (closeCoachMarks.length > 0 && !closeCoachMarks[0]) {
								return true;
							} else {
								return CoachMarkUtils.closeCoachMarks(testCase);
		}

						} else {
							return false;
						}
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
	 * The closeAppLaunchPopups method closes all the application pop ups displayed
	 * on the device post the application launch.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @return boolean Returns 'true' if all the pop ups have been closed
	 *         successfully. Returns 'false' if any of the pop up fails to close
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
			try {
				FluentWait<CustomDriver> fWait = new FluentWait<CustomDriver>(testCase.getMobileDriver());
				fWait.pollingEvery(2, TimeUnit.SECONDS);
				fWait.withTimeout(1, TimeUnit.MINUTES);
				Boolean isEventReceived = fWait.until(new Function<CustomDriver, Boolean>() {
					public Boolean apply(CustomDriver driver) {
						try {
							if (ls.isLyricLogoVisible()) {
								return true;
							}
							if(os.isIgnoreButtonVisible(3))
							{
								os.clickOnIgnoreButton();
								return false;
							}
							/*if (os.isCancelButtonVisible()) {
								os.clickOnCancelButton();
								return false;
							}*/ else {
								((CustomIOSDriver) testCase.getMobileDriver()).switchTo().alert().accept();
								return false;
							}
						} catch (Exception e) {
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
		}
		return flag;
	}

	/**
	 * <h1>Set Application Environment</h1>
	 * <p>
	 * The set application environment method navigates to the secret menu from the
	 * login screen, set the application environment provided to the test case, and
	 * navigates back to the login screen
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
	 * @return boolean Returns 'true' if the environment is successfully set.
	 *         Returns 'false' if the environment is not set successfully
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
				TimeUnit.SECONDS.sleep(1);
				// Thread.sleep(1000);
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

	/**
	 * <h1>Launch And Login to Lyric Application</h1>
	 * <p>
	 * The launchAndLoginToApplication method launches the lyric application, closes
	 * all pop ups post application launch, sets the application environment, logs
	 * in the the application, and verifies whether the user has successfully logged
	 * in or not.
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
	 * @return boolean Returns 'true' if all the operations mentioned in the
	 *         description have been performed successfully. Returns 'false' if any
	 *         of the operations mentioned in the description fails.
	 */
	public static boolean launchAndLoginToApplication(TestCases testCase, TestCaseInputs inputs,
			boolean... closeCoachMarks) {
		boolean flag = true;
		flag = MobileUtils.launchApplication(inputs, testCase, true);
		flag = flag & LyricUtils.closeAppLaunchPopups(testCase);
		flag = flag & LyricUtils.setAppEnvironment(testCase, inputs);
		flag = flag & LyricUtils.loginToLyricApp(testCase, inputs);
		flag = flag & LyricUtils.verifyLoginSuccessful(testCase, inputs);
		return flag;
	}

	/**
	 * <h1>Get Device Time zone</h1>
	 * <p>
	 * The getDeviceTimeZone method returns the TimeZone the device is configured
	 * to.
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
	 * @return TimeZone Returns the timezone of the device
	 */
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

	/**
	 * <h1>Get Device Time</h1>
	 * <p>
	 * The getDeviceTime method gets the time on the device
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
	 * @return String Device time in the format 'yyyymmddThh:mm:a'
	 */
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

	/**
	 * <h1>Get Device Time</h1>
	 * <p>
	 * The addMinutesToDate method gets the time on the device with added minutes
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-03-17
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return String Device time in the format 'yyyymmddThh:mm:a'
	 */
	public static String addMinutesToDate(TestCases testCase, String date, int noOfMins) {
		String dateAfterAddition = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'h:mm a");
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(date));
			c.add(Calendar.MINUTE, noOfMins);
			dateAfterAddition = dateFormat.format(c.getTime());
		} catch (Exception e) {
			dateAfterAddition = " ";
			Keyword.ReportStep_Fail_WithOut_ScreenShot(testCase, FailType.FUNCTIONAL_FAILURE,
					"Add days to date : Error Occured : " + e.getMessage());
		}
		return dateAfterAddition;
	}

	/**
	 * <h1>Get All Alerts through CHIL</h1>
	 * <p>
	 * The getAllAlertsThroughCHIL method gets all the alerts of the location name
	 * provided to the test case through CHIL.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance.
	 * @return JSONObject Alert details of the location name provided to the test
	 *         case.
	 */
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

	/**
	 * <h1>Get All Alert IDs</h1>
	 * <p>
	 * The getAllAlertIDS method gets all the alerts through CHIL, extracts the IDS
	 * for each alert from the JSONObject, and stores the ids in a list
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance.
	 * @return List<Long> List of all alert IDS
	 */
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

	/**
	 * <h1>Dismiss All Alerts through CHIL</h1>
	 * <p>
	 * The dismissesAllAlertsThroughCHIL method dismisses all the alerts of the
	 * location name provided to the test case through CHIL.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance.
	 * @return boolean Returns 'true' if all the alerts have been dismissed
	 *         successfully. Returns 'false' if error occurs while dismissing
	 *         alerts.
	 */
	public static boolean dismissAllAlertsThroughCHIL(TestCases testCase, TestCaseInputs inputs) {
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

	/**
	 * <h1>Scroll To The List</h1>
	 * <p>
	 * The scrollUpAList method scrolls to an element
	 * using swipe gestures.
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @param value
	 *            Value of the attribute used to locate the element
	 * @return boolean Returns 'true' if the element is found. Returns 'false' if
	 *         the element is not found.
	 */
	public static boolean scrollUpAList(TestCases testCase, WebElement devieListWebEle)
			throws Exception {
		Dimension d1;
		Point p1;
		int startx = -1;
		int starty = -1;
		int endy = -1;
		if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
			d1 = devieListWebEle.getSize();
			p1 = devieListWebEle.getLocation();
			startx = p1.getX();
			starty = (int) (d1.height * 0.90) + p1.getY();
			endy = (int) (d1.height * 0.60) + p1.getY();
		} else {
			d1 = devieListWebEle.getSize();
			p1 = devieListWebEle.getLocation();
			starty = (int) (d1.height * 0.80);
			endy = (int) -((d1.height * 0.50) + p1.getY());
			startx = d1.width / 2;
		}
		return MobileUtils.swipe(testCase, startx, starty, startx, endy);
	}

	/**
	 * <h1>Scroll To Element Using Exact Attribute Value</h1>
	 * <p>
	 * The scrollToElementUsingExactAttributeValue method scrolls to an element
	 * using the attribute and exact value passed to the method in the parameters.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @param value
	 *            Value of the attribute used to locate the element
	 * @return boolean Returns 'true' if the element is found. Returns 'false' if
	 *         the element is not found.
	 */
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
				try {
					js.executeScript("mobile:scroll", scrollObject);
				} catch (Exception e) {
					if (e.getMessage().contains("Failed to find scrollable visible")) {
						js.executeScript("mobile:scroll", scrollObject);
					}
				}
				WebElement element = testCase.getMobileDriver()
						.findElement(MobileBy.iOSNsPredicateString(attribute + " == '" + value + "'"));
				if (element.getAttribute(attribute).equals(value)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (NoSuchElementException e) {
			throw new Exception(
					"Element with text : '" + value + "' not found. Exception Type: No Such Element Exception");
		} catch (Exception e) {
			throw new Exception("Element with text : '" + value + "' not found. Exception Message: " + e.getMessage());
		}
	}

	/**
	 * <h1>Scroll To Element Using Attribute Sub String Value</h1>
	 * <p>
	 * The scrollToElementUsingAttributeSubStringValue method scrolls to an element
	 * using the attribute and attribute substring value passed to the method in the
	 * parameters.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param attribute
	 *            Attribute of the value used to locate the element
	 * @param value
	 *            Substring value of the attribute used to locate the element
	 * @return boolean Returns 'true' if the element is found. Returns 'false' if
	 *         the element is not found.
	 */
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
				try {
					scrollObject.put("predicateString", attribute + " CONTAINS '" + value + "'");
					js.executeScript("mobile:scroll", scrollObject);
				} catch (Exception e) {
					scrollObject.clear();
					scrollObject.put("direction", "down");
					js.executeScript("mobile:scroll", scrollObject);
				}
				WebElement element = MobileUtils.getMobElement(testCase, "xpath",
						"//*[contains(@" + attribute + ",'" + value + "')]");
				// WebElement element = testCase.getMobileDriver()
				// .findElement(MobileBy.iOSNsPredicateString(attribute + " CONTAINS '" + value
				// + "'"));
				if (element.getAttribute(attribute).contains(value)) {
					return true;
				} else {
					return false;
				}

			}

		} catch (NoSuchElementException e) {
			throw new Exception("Element with text/value containing : '" + value
					+ "' not found. Exception Type : No Such Element Exception");
		} catch (Exception e) {
			throw new Exception("Element with text/value containing : '" + value + "' not found. Exception Message: "
					+ e.getMessage());
		}
	}

	/**
	 * <h1>Verify Device Displayed On Dashboard</h1>
	 * <p>
	 * The verifyDeviceDisplayedOnDashboard method verifies the device name passed
	 * in the parameters is present on the Dashboard.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 *            testCase instance.
	 * @param deviceName
	 *            Device name whose presence has to be verified on the dash board
	 * @return boolean Returns 'true' if the device is present on the dash board.
	 *         Returns 'false' if the device is not present on the dash board.
	 */
	public static boolean verifyDeviceDisplayedOnDashboard(TestCases testCase, String deviceName) {
		boolean flag = true;
		Dashboard d = new Dashboard(testCase);
		if (d.isDevicePresentOnDashboard(deviceName)) {
			Keyword.ReportStep_Pass(testCase, "Device : " + deviceName + " is present on the dashboard.");
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Device : " + deviceName + " is not present on the dashboard.");
		}
		return flag;
	}

	/**
	 * <h1>Verify if Device is still displayed in dashboard after deleting it</h1>
	 * <p>
	 * The verifyDeviceNotDisplayedOnDashboard method verifes if device is still
	 * displayed in dashboard after deleting it.
	 * </p>
	 *
	 * @author Pratik P. Lalseta (H119237)
	 * @version 1.0
	 * @since 2018-02-19
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase.
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance.
	 * @return boolean Returns 'true' if device is not displayed in dashboard
	 *         screen. Returns 'false' if device is still displayed in dashboard
	 *         screen.
	 */
	public static boolean verifyDeviceNotDisplayedOnDashboard(TestCases testCase, TestCaseInputs inputs,
			String expectedDevice) {
		boolean flag = true;
		HashMap<String, MobileObject> fieldObjects = MobileUtils.loadObjectFile(testCase, "DAS_InstallationScreen");
		if (MobileUtils.isMobElementExists(fieldObjects, testCase, "GlobalDrawerButton", 30, false)) {
			if (MobileUtils.isMobElementExists(fieldObjects, testCase, "DashboardIconText", 5)) {
				List<WebElement> dashboardIconText = MobileUtils.getMobElements(fieldObjects, testCase,
						"DashboardIconText");
				if (MobileUtils.isMobElementExists("id", "name", testCase, 3, false)) {
					dashboardIconText.addAll(MobileUtils.getMobElements(testCase, "id", "name"));
				}
				boolean f = false;
				String deviceName = "";
				for (WebElement e : dashboardIconText) {
					String displayedText;
					if (testCase.getPlatform().toUpperCase().contains("ANDROID")) {
						displayedText = e.getText();
					} else {
						displayedText = e.getAttribute("value");
					}
					if (expectedDevice.equalsIgnoreCase("Switch")) {
						deviceName = inputs.getInputValue("LOCATION1_SWITCH1_NAME");
					} else if (expectedDevice.equalsIgnoreCase("Dimmer")) {
						deviceName = inputs.getInputValue("LOCATION1_DIMMER1_NAME");
					}
					if (displayedText.equals(deviceName)) {
						f = true;
						break;
					}
				}
				if (f) {
					flag = false;
					Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
							"Device : " + deviceName + " is present on the dashboard.");
				} else {
					Keyword.ReportStep_Pass(testCase, "Device : " + deviceName + " is not present on the dashboard.");
				}
			} else {
				Keyword.ReportStep_Pass(testCase, "No devices found on the dashboard");
			}
		} else {
			flag = false;
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE, "User is not on the dashboard");
		}
		return flag;
	}
	
	public static boolean closeCoachMarks(TestCases testCase) {
		boolean flag = true;
		CoachMarks cm = new CoachMarks(testCase);
		int counter = 0;
		if (cm.isGotitButtonVisible(1)) {
			while (cm.isGotitButtonVisible(1) && counter < 5) {
				flag = flag & cm.clickOnGotitButton();
				counter++;
			}
		}
		return flag;
	}
	
	/**
	 * <h1>Get Location Time</h1>
	 * <p>
	 * The getLocationTime method returns the location time
	 * </p>
	 *
	 * @author Midhun Gollapalli (H179225)
	 * @version 1.0
	 * @since 2018-02-15
	 * @param testCase
	 *            Instance of the TestCases class used to create the testCase
	 * @param inputs
	 *            Instance of the TestCaseInputs class used to pass inputs to the
	 *            testCase instance
	 * @return TimeZone Returns the location time
	 */
	public static String getLocationTime(TestCases testCase, TestCaseInputs inputs, String timeFormat) {
		LocationInformation locInfo = new LocationInformation(testCase, inputs);
		String time = " ";
		try {
			TimeZone timeZone = TimeZone.getTimeZone(locInfo.getIANATimeZone());

			Calendar date = Calendar.getInstance(timeZone);
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
			switch (timeFormat) {
			case "TIMEINYYMMHHMMFORMAT": {
				time = String.valueOf(date.get(Calendar.YEAR) + "-" + month + "-" + date.get(Calendar.DAY_OF_MONTH)
						+ "T" + hour + ":" + minute + " " + ampm);
				break;
			}
			case "TIMEINHHMMFORMAT": {
				time = String.valueOf(hour + ":" + minute + " " + ampm);
				break;
			}
			}
		} catch (Exception e) {
			time = "";
			Keyword.ReportStep_Fail(testCase, FailType.FUNCTIONAL_FAILURE,
					"Get Android Device Time : Error Occured : " + e.getMessage());
		}
		return time;
	}

}
