package com.home.test.runner;
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
    
    	
    }
    public static void setDriver(WebDriver webDriver){
    	if(null==webDriver){
    		System.out.println("Inside Test1");
    	}
    }
}
