package com.home.cucumber.stepdef;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	String arg1;
	String arg2;
	@When("^Adding Two Positive Number \"([^\"]*)\" and \"([^\"]*)\"$")
	public void adding_Two_Positive_Number_and(String arg1, String arg2) throws Throwable {
	    this.arg1=arg1;
	    this.arg2=arg2;
	}
	@Then("^Result should be equal To \"([^\"]*)\"$")
	public void result_should_be_equal_To(String result1) throws Throwable {
	    int result=Integer.parseInt(arg1)+Integer.parseInt(arg2);
	    boolean flag=Integer.parseInt(result1)==result;
	    if(!flag){
	    	Assert.fail();
	    }
	}
}
