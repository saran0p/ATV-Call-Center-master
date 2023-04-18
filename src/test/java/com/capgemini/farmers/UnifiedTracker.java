package com.capgemini.farmers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class UnifiedTracker extends CallCenterTest {
	private static String str="UnifiedTracker";
	
	@Test
	public void validate() {
		Map<String,String> map=getProperties("validateTracking");
		startTime=System.nanoTime();
		initChrome();
		webDriver.navigate().to(map.get("urltracking"));
		screenshot(str);
		webDriver.findElement(By.xpath("//*[@id=\"eDocument-form\"]/ul/li[11]/a")).click();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str); 
	}

}
