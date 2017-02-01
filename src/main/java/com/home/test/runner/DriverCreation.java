package com.home.test.runner;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverCreation implements Callable<WebDriver> {

	private WebDriver webDriver;
	private String className;

	public DriverCreation(String className) {
		this.className = className;
	}

	/*
	 * @Override public void run() {
	 * System.setProperty("webdriver.gecko.driver",
	 * "D:\\eclipse\\geckodriver-v0.13.0-win64\\geckodriver.exe"); webDriver=new
	 * FirefoxDriver(); }
	 * 
	 * public WebDriver getDriver(){ return webDriver; }
	 */
	@Override
	public WebDriver call() throws Exception {
		if (className.equalsIgnoreCase("FireFoxBaseTest")) {
			System.setProperty("webdriver.gecko.driver", "D:\\eclipse\\geckodriver-v0.13.0-win64\\geckodriver.exe");
			webDriver = new FirefoxDriver();
		}
		return webDriver;
	}

}
