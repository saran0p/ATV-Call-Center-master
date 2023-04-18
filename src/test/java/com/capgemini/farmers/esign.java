package com.capgemini.farmers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class esign extends CallCenterTest {
	private static String str="esign";
	private static Logger logger = LoggerFactory.getLogger(esign.class);
	
	@Test
	public void validate() {
		Map<String,String> map=getProperties("validateDSA");
		startTime=System.nanoTime();
		initChrome();
		webDriver.navigate().to(map.get("urlDsa"));
		screenshot(str);
        webDriver.findElement(By.xpath("/html/body/div/div[2]/form/div/div/div/div/fieldset/div[3]/div[1]/input")).sendKeys(map.get("policyno"));
        screenshot(str);
        webDriver.findElement(By.xpath("/html/body/div/div[2]/form/div/div/div/div/fieldset/div[5]/button[1]")).click();
        screenshot(str);
        logger.info("eSign Assistant validated successfully");
		webDriver.close();
		webDriver.quit();
	}
}
