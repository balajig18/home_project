package com.home.test.runner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.openqa.selenium.WebDriver;

public class DriverInitializer {

	
	private String className;

	public DriverInitializer(String className) {
		this.className = className;
	}

	public Queue<WebDriver> getDriverList() throws InterruptedException, ExecutionException {
		int size = 0;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		Queue<WebDriver> driverObject = new LinkedList<>();
		Queue<Future<WebDriver>> driverTask = new LinkedList<>();

		while (size < 2) {
			Callable<WebDriver> driverCreation = new DriverCreation(className);
			Future<WebDriver> webDriverTask = executorService.submit(driverCreation);
			driverTask.add(webDriverTask);
			size++;
		}

		for (Future<WebDriver> driverCreationTest : driverTask) {
			driverObject.add(driverCreationTest.get());
		}

		executorService.shutdown();

		return driverObject;
	}
}
