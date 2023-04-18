package com.capgemini.farmers;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.ini4j.Ini;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class CallCenterTest {
	private static Logger log = LoggerFactory.getLogger(CallCenterTest.class);
	static WebDriver webDriver;
	static WebElement element;
	static int scrNo=0;
	static String value;
	protected long startTime;
	static DateTimeFormatter timeformat = DateTimeFormatter.ofPattern("HHmmss");
	static DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("uuuuMMddHHmm");
	static ZonedDateTime zdt = new Date().toInstant().atZone(ZoneId.systemDefault());

	public static void sleep(int sec)
	{
		try {
			Thread.sleep(sec*1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	protected static Map<String,String> getProperties(String str)
	{
		try{
			Ini iniFile=new Ini(new File("zurichconnect.ini"));
			return iniFile.get(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new HashMap<>();
		}
	}
	protected static void initChrome() {
		ChromeOptions chromeoption=new ChromeOptions();
		chromeoption.addArguments("--disable-gpu");
		chromeoption.addArguments("start-maximized");
		WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver(chromeoption);
		webDriver.manage().window().maximize();
	}
	protected static void screenshot(String str) {
		TakesScreenshot screenshot=(TakesScreenshot) webDriver;
		File dest = new File(String.format("Output%s/21CCallCenterValidate%s_%s.png", zdt.format(dateformat),
				zdt.format(timeformat), scrNo));
		scrNo++;
		File scr=screenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scr, dest);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	public abstract void validate();
}
