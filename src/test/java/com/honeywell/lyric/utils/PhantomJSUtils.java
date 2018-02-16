package com.honeywell.lyric.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJSUtils {

	public static WebDriver getPhantomJSDriver() throws Exception {
		try {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setJavascriptEnabled(true);
			caps.setCapability("takesScreenshot", true);
			if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						"src/test/resources/PhantomJS/mac/bin/phantomjs");
			} else {
				caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
						"src/test/resources/PhantomJS/win/bin/phantomjs");
			}
			return (new PhantomJSDriver(caps));
		} catch (Exception e) {
			throw new Exception("Unable to create PhantomJS driver. Error Occured: " + e.getMessage());
		}
	}
}
