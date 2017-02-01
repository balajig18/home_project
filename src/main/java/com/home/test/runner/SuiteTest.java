package com.home.test.runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(ConcurrentSuiteRunner.class)
@Concurrent(threads=4)
@SuiteClasses({LibraryTest.class,LibraryTest2.class,LibraryTest3.class})
public class SuiteTest extends FireFoxBaseTest {

}
