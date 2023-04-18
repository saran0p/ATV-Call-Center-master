package com.capgemini.farmers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetroTranscations extends CallCenterTest{
	private static Logger log = LoggerFactory.getLogger(RetroTranscations.class);
	private static String str="RetroTranscations";
	private boolean isPolicyValidated=false;
	private boolean isModulevalidated=false;
	private boolean isDifferencevalidated=false;

	@Test
	public void validate() {
		Map<String,String> map=getProperties("validateRetroTranscations");
		startTime=System.nanoTime();
		initChrome();
		webDriver.navigate().to(map.get("urlRetroTranscations"));
		screenshot(str);
		retrive(map);
	}
	public boolean retrive(Map <String,String> map)
	{
		try
		{
			isPolicyValidated=validatePolicy(map);
		if(isPolicyValidated)
		{
			log.debug("Policy Screen Passed");
			isModulevalidated=validateModule(map);
		}
		else
			return false;
		if(isModulevalidated)
		{
			log.debug("Module Screen Passed");
			isDifferencevalidated=validateDifference();
		}
		else 
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		if(isPolicyValidated && isModulevalidated && isDifferencevalidated)
		{
			log.debug("Difference Screen Passed");
			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean validateDifference() {
		try {
		webDriver.findElement(By.id("RequestSearch")).click();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str);
		log.debug(webDriver.findElement(By.xpath("//*[@id='eDocument-form']/h4")).getText());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Validating Term for Difference failed");
			return false;
		}
		return true;
	}
	private boolean validateModule(Map<String, String> map) {
		try {
		Select select=new Select(webDriver.findElement(By.id("DocCore_qpMod")));
		select.selectByVisibleText(map.get("module"));
		webDriver.findElement(By.id("RequestSearch")).click();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Validationg Term for Policy failed");
			return false;
		}
		return true;
	}
	private boolean validatePolicy(Map<String, String> map) {
		try {
		sleep(10);	
		webDriver.findElement(By.id("DocCore_qpNumber")).sendKeys(map.get("pointpolicy"));
		screenshot(str);
		webDriver.findElement(By.id("RequestSearch")).click();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Validating Policy failed");
			return false;
		}
		return true;
	}
}