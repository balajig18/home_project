package com.home.testng.runner;


import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestNgTest {

	@Test
	public void test1(ITestContext iTestContext){
		iTestContext.getCurrentXmlTest().getAllParameters();
	}
}
