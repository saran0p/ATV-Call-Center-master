package com.capgemini.farmers;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyForm extends CallCenterTest {
	private static final String STATE = "state";
	private static String str = "PolicyForm";
	private static Logger log = LoggerFactory.getLogger(PolicyForm.class);

	@Test
	public void validate() {
		try {
			Map<String, String> map = getProperties("validatePolicyForm");
			startTime = System.nanoTime();
			initChrome();
			webDriver.navigate().to(map.get("urlpolicyForm"));
			screenshot(str);
			boolean isretrived = filterState(map);
			if (!isretrived) 
			{
				fail("Unable to check the State provided "+map.get(STATE)+" \nKindly check the state & sortBy field in zurichconnect.ini file for the same.");
			}
			isretrived = checkDocs();
			log.debug("Validataion completed for {}", map.get(STATE));
		} finally {
			long endTime = System.nanoTime();
			long totaltime = (endTime - startTime);
			log.debug("Total Time to run in Seconds {}", TimeUnit.NANOSECONDS.toSeconds(totaltime));
			webDriver.quit();
		}
	}

	public static boolean filterState(Map<String, String> map) {
		Select select;

		element = webDriver.findElement(By.id("State"));
		select = new Select(element);
		select.selectByValue(map.get(STATE));

		element = webDriver.findElement(By.id("SortBy"));
		select = new Select(element);
		select.selectByValue(map.get("sortBy"));
		screenshot(str);
		element = webDriver.findElement(By.id("RequestSearch"));
		element.click();
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str);
		if (!webDriver.findElements(By.id("newSelection")).isEmpty()) {
			log.debug("State Filter passed");
			return true;
		} else
			return false;
	}

	public static boolean checkDocs() {
		ArrayList<String> tabs;
		try {
			element = webDriver.findElement(By.cssSelector(
					"#newSelection > div:nth-child(2) > #wide > tbody > tr:nth-child(1) > td:nth-child(2) > a:first-child"));
			element.click();
			tabs = new ArrayList<>(webDriver.getWindowHandles());
			webDriver.switchTo().window(tabs.get(1));
			sleep(10);
			screenshot(str);
			webDriver.switchTo().window(tabs.get(0));
			webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			element = webDriver.findElement(By.cssSelector(
					"#newEndorsement > div:nth-child(2) > #wide > tbody > tr:nth-child(1) > td:nth-child(2) > a:first-child"));
			element.click();
			tabs = new ArrayList<>(webDriver.getWindowHandles());
			webDriver.switchTo().window(tabs.get(1));
			sleep(10);
			screenshot(str);
			webDriver.switchTo().window(tabs.get(0));
			webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			element = webDriver.findElement(By.cssSelector(
					"#newCountrywide > div:nth-child(2) > #wide > tbody > tr:nth-child(1) > td:nth-child(2) > a:first-child"));
			element.click();
			tabs = new ArrayList<>(webDriver.getWindowHandles());
			webDriver.switchTo().window(tabs.get(1));
			sleep(10);
			screenshot(str);
			webDriver.switchTo().window(tabs.get(0));
		} catch (Exception e) {
			log.debug("Some error to retrieve the Docs kindly check last screenshots");
			throw e;
		}
		log.debug("Docs are viewable no issues");
		return true;
	}
}
