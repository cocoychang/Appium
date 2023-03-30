package com.appiumref.Appium;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;



public class Base {
	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	
	@BeforeClass
	public void ConfigureAppium() throws MalformedURLException {
		
		Map<String , String> env = new HashMap<String , String>(System.getenv());
		env.put("ANDROID_HOME", "C:\\Users\\63998\\AppData\\Local\\Android\\Sdk");
		env.put("JAVA_HOME", "C:\\Program Files\\Java\\jdk-20");

		//run appium server automatically
		service=new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\63998\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
		.withIPAddress("127.0.0.1").usingPort(4723).withTimeout(Duration.ofSeconds(300)).build();
		service.start();
		//create capabilities
		
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName("testAndroid");
		//options.setApp(System.getProperty("user.dir")+"\\Appium\\src\\test\\java\\resources\\ApiDemos-debug.apk");
		options.setCapability("platformVersion","11.0");
		//options.setAutomationName("UiAutomator2");
		options.setCapability(MobileCapabilityType.APP, "C:\\Users\\63998\\Documents\\resources\\ApiDemos-debug.apk");
		
		
		
		//create object for android
		
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.quit();
		service.stop();
	}
	 public void scrollToEnd() {
    	 boolean canScrollMore;
 		do {
 		 canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
 			    "left", 100, "top", 100, "width", 200, "height", 200,
 			    "direction", "down",
 			    "percent", 3.0
 			));

 		} while(canScrollMore);
     }

     public void scrollToElement(String ele) {
    	driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"ele\"));"));
     }

   //perform swipe action
     public void swipeAction(WebElement ele, String swipeDirection) {

 		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
 				"elementId", ((RemoteWebElement) ele).getId(),
 			    "direction", swipeDirection,
 			    "percent", 0.75
 			));	
     }


     @AfterClass
     public void tearDown() {

 		driver.quit();
 		service.stop();
     }


}
