package com.home.test.runner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverCreation implements Runnable {

	private WebDriver webDriver;
	
	@Override
	public void run() {
		System.setProperty("webdriver.gecko.driver","D:\\eclipse\\geckodriver-v0.13.0-win64\\geckodriver.exe");
		webDriver=new FirefoxDriver();
	}

	public WebDriver getDriver(){
		return webDriver;
	}
	
	
}
