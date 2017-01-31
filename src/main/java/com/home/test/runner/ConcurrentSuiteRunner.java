	
package com.home.test.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ConcurrentSuiteRunner extends Suite {
    public ConcurrentSuiteRunner(final Class<?> klass) throws InitializationError, InterruptedException {        super(klass, new AllDefaultPossibilitiesBuilder(true) {
            @Override
            public Runner runnerForClass(Class<?> testClass) throws Throwable {
                List<RunnerBuilder> builders = Arrays.asList(
                        new RunnerBuilder() {
                            @Override
                            public Runner runnerForClass(Class<?> testClass) throws Throwable {
                                Concurrent annotation = testClass.getAnnotation(Concurrent.class);
                                if (annotation != null)
                                    return  null;//new ConcurrentJunitRunner(testClass);
                                return null;
                            }
                        },
                        ignoredBuilder(),
                        annotatedBuilder(),
                        suiteMethodBuilder(),
                        junit3Builder(),
                        junit4Builder());
                for (RunnerBuilder each : builders) {
                    Runner runner = each.safeRunnerForClass(testClass);
                    if (runner != null)
                        return runner;
                }
                return null;
            }
        });
        setScheduler(new RunnerScheduler() {
           	List<WebDriver> webDriverList=new DriverInitializer().getDriverList();
            ExecutorService executorService = Executors.newFixedThreadPool(
                    klass.isAnnotationPresent(Concurrent.class) ?
                            klass.getAnnotation(Concurrent.class).threads()==webDriverList.size()?webDriverList.size():klass.getAnnotation(Concurrent.class).threads():
                            (int) (Runtime.getRuntime().availableProcessors() * 1.5),
                    new NamedThreadFactory(klass.getSimpleName()));
            CompletionService<Void> completionService = new ExecutorCompletionService<Void>(executorService);
            Queue<Future<Void>> tasks = new LinkedList<Future<Void>>();
            
            @Override
            public void schedule(Runnable childStatement) {
            	tasks.offer(completionService.submit(childStatement, null));
            }

            @Override
            public void finished() {
                try {
                    while (!tasks.isEmpty())
                        tasks.remove(completionService.take());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    while (!tasks.isEmpty())
                        tasks.poll().cancel(true);
                    executorService.shutdownNow();
                    for(WebDriver webDriver:webDriverList){
                    	try{
                    	webDriver.quit();
                    	}catch(Exception exception){
                    		
                    	}
                    }
                }
            }
        });
    }

    @Override
    public void run(RunNotifier notifier) {
    
    	super.run(notifier);
    }
    
    @Override
    protected void runChild(Runner runner, RunNotifier notifier) {
    	try {
    		
			List<Method> methodList=Arrays.asList(runner.getDescription().getTestClass().getMethods());
			for(Method method:methodList){
				System.out.println(method.getName());
				if("setDriver".contains(method.getName())){
					method.invoke(null);
				}
			}
			
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	super.runChild(runner, notifier);
    }
    @Override
    protected Statement withBeforeClasses(Statement statement) {
     	return super.withBeforeClasses(statement);
    }
    static final class NamedThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final ThreadGroup group;

        NamedThreadFactory(String poolName) {
            group = new ThreadGroup(poolName + "-" + poolNumber.getAndIncrement());
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(group, r, group.getName() + "-thread-" + threadNumber.getAndIncrement(), 0);
        }
    }

}
