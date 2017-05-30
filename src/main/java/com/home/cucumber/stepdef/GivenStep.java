package com.home.cucumber.stepdef;

import cucumber.api.java.en.Given;

public class GivenStep {

	@Given("^User is trying to add \"([^\"]*)\" and \"([^\"]*)\"$")
	public void user_is_trying_to_add_and(String arg1, String arg2) throws Throwable {
		System.out.println("Adding Two Number"+arg1+" and "+arg2);
	}
}
