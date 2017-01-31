package com.home.test.runner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.WebDriver;

public class DriverInitializer{

	public List<WebDriver> getDriverList() throws InterruptedException {
		int size=0;
		ExecutorService executorService=Executors.newFixedThreadPool(2);
		List<WebDriver> driverObject=new ArrayList();
		
		while(size<2){
			Runnable driverCreation=new DriverCreation();
			executorService.execute(driverCreation);
			size++;
		}
			
		List<Runnable> runnables=executorService.shutdownNow();
		
		for(Runnable driverCreation:runnables){
			driverObject.add(((DriverCreation)driverCreation).getDriver());
		}
		
		return driverObject;
	}
}
