package com.home.test.runner;
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class LibraryTest2 {
	private static WebDriver webDriver;
	@Test public void testSomeLibraryMethod() {
		webDriver.get("https://facebook.com");
    }
    public static void setDriver(WebDriver webDriver){
    	LibraryTest2.webDriver=webDriver;
    	if(null==webDriver){
    		System.out.println("Inside Test2");
    	}
    }
}
