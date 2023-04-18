package com.capgemini.farmers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyQuoteViewer extends CallCenterTest{
	private static String str="PolicyQuoteViewer";
	private static Logger log = LoggerFactory.getLogger(PolicyQuoteViewer.class);
	@Test
	public void validate()
	{
		Map<String,String> map=getProperties("validatePolicyQuoteViewer");
		startTime=System.nanoTime();
		initChrome();
		webDriver.navigate().to(map.get("urlPolicyQuoteViewer"));
		boolean isretrived=searchpolicy(map);
		boolean isPolicyTabsOk=false;
		boolean isBillingTabsOk=false;
		if(isretrived)
		{
			isPolicyTabsOk=checkPolicyTab();
			isBillingTabsOk=checkbillingTab();
			log.info("Policy Tab is validated: {}",isPolicyTabsOk);
			log.info("Billing Tab is validated: {}",isBillingTabsOk);
		webDriver.close();
		webDriver.quit();
		}
		else
		{
			log.info("Unable to check the policy provided {} \nKindly check the policyNo field in zurichconnect.ini file for the same.",map.get("policyno"));
		webDriver.close();
		webDriver.quit();
		}
	}
	private static boolean searchpolicy(Map<String, String> map) {
		element=webDriver.findElement(By.cssSelector("#SearchInput_SearchType[value=Number]"));
		element.click();
		for(int i=0;i<7;i++)
		{
			if(i==1)
			{
				continue;
			}
			element=webDriver.findElement(By.id("SystemTypes_"+i+"__isSelected"));
			if(element.isSelected())
			{
				element.click();
			}
		}
		screenshot(str);	
		element=webDriver.findElement(By.id("SearchInput_Number"));
		element.sendKeys(map.get("policyno"));
		element=webDriver.findElement(By.id("RequestSearch"));
		element.click();
		log.info("Searching the data ");
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		screenshot(str);
		//  
		List<WebElement> elements=webDriver.findElements(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(1) > a"));
		if(!elements.isEmpty())
		{
			log.info("Policy Found: {}",elements.get(0).getAttribute("target").substring(11));
			element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(1) > a"));
			element.click();
		}
		else
		{
			return false;
		}
		return true;
	}
	private static boolean checkbillingTab() {
		try
		{
		element=webDriver.findElement(By.cssSelector("#tabMaster > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(4) > div > div.jqx-tabs-titleContentWrapper.jqx-disableselect"));
		element.click();
		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
	    /////////////////    Billing Statements tab ////////////
	    element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(4)"));
	    value=element.getAttribute("outerText");
	    log.info("Description value of 1st Biling item is {}",value);
	    element=webDriver.findElement(By.cssSelector("#BillingStatement > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Billing Statements tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Ledger tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabBilling > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(2)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(4)"));
	    value=element.getAttribute("outerText");
	    log.info("Ledger's 1st Description detail :{}",value);
	    element=webDriver.findElement(By.cssSelector("#LedgerStatement > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Ledger tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Payments tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabBilling > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(3)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(3)"));
	    value=element.getAttribute("outerText");
	    log.info("First Source detail is :{}",value);
	    element=webDriver.findElement(By.cssSelector("#PaymentHistory > span"));
	    value=element.getAttribute("outerText");
		//System.out.print("test0");
	    log.info("Time taken on Payments tab is {}",value);
	    screenshot(str);
	    //System.out.print("test1");
	    /////////////////  Billing History tab ////////////
		element=webDriver.findElement(By.cssSelector("#tabBilling > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(4)"));
	    element.click(); 
		//System.out.print("test2");
		screenshot(str);                           
	    webDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	    //element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(4)"));
	    //value=element.getAttribute("outerText");
	    //logger.info("Ledger's 1st Description detail :"+value);
	    element=webDriver.findElement(By.cssSelector("#BillingHistory > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Billing history tab is {}",value);
	    screenshot(str);
	    sleep(5);
	    /////////////////  Refund History tab ////////////
	    //element=webDriver.findElement(By.xpath("//*[@id="tabBilling"]/div[1]/ul/li[5]"));
		webDriver.findElement(By.cssSelector("#tabBilling > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(5)")).click();
	    //element.click();
	    webDriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	    //element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(4)"));
	    //value=element.getAttribute("outerText");
	    //logger.info("Ledger's 1st Description detail :"+value);
	    element=webDriver.findElement(By.cssSelector("#RefundHistory > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Refund History tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Comission History tab ////////////
	    //element=webDriver.findElement(By.xpath("//*[@id="tabBilling"]/div[1]/ul/li[6]"));
		webDriver.findElement(By.cssSelector("#tabBilling > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(6)")).click();
	    //element.click();
	    webDriver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(1) > td:nth-child(4)"));
	    value=element.getAttribute("outerText");
	    log.info("First Source detail is :{}",value);
	    element=webDriver.findElement(By.cssSelector("#CommissionHistory > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on comisiion history tab is {}",value);
	    screenshot(str);	    
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}
		return true;
		
	}
	private static boolean checkPolicyTab() {
		
		try{
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		ArrayList<String> tabs2 = new ArrayList<> (webDriver.getWindowHandles());
	    webDriver.switchTo().window(tabs2.get(1));
	    
	    /////////////////    Transactions tab ////////////
	    element=webDriver.findElement(By.cssSelector("#transactions > tbody > tr:first-child > td:nth-child(8)"));
	    value=element.getAttribute("outerText");
	    log.info("Provided value of 1st Transcation is {}",value);
	    element=webDriver.findElement(By.cssSelector("#TransactionHistory > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on transaction is {}",value);
	    screenshot(str);
	    /////////////////    Basic Policy tab ////////////
	    
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(2)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#wide > tbody > tr:nth-child(23) > td:nth-child(2)"));
	    value=element.getAttribute("outerText");
	    log.info("UnderWriting company is :{}",value);
	    element=webDriver.findElement(By.cssSelector("#BasicPolicy > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Basic Policy tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Extended Policy tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(3)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#ExtendedPolicy > table:nth-child(4) > tbody > tr:nth-child(2) > td:nth-child(2)"));
	    value=element.getAttribute("outerText");
	    log.info("Producer Number {}",value);
	    element=webDriver.findElement(By.cssSelector("#ExtendedPolicy > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Extended policy tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Operator Policy tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(4)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#Operators > table:nth-child(3) > tbody > tr > td:nth-child(1)"));
	    value=element.getAttribute("outerText");
	    log.info("Operator Name: {}",value);
	    element=webDriver.findElement(By.cssSelector("#Operators > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Operator tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Claim & Violation tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(5)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#ClmsAndVios > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Claims tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Vehicles tab ////////////
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(6)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#Vehicles > table:nth-child(3) > tbody > tr > td:nth-child(1)"));
	    value=element.getAttribute("outerText");
	    log.info("1st Vin is  {}",value);
	    element=webDriver.findElement(By.cssSelector("#Vehicles > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on vehicle tab is {}",value);
	    screenshot(str);
	    
	    /////////////////    Coverage tab ////////////
	    //#Coverages > table > tbody > tr:nth-child(1) > td:nth-child(1)
	    element=webDriver.findElement(By.cssSelector("#tabPolicy > div.jqx-tabs-headerWrapper.jqx-tabs-header.jqx-widget-header.jqx-rc-t > ul > li:nth-child(7)"));
	    element.click();
	    webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
	    element=webDriver.findElement(By.cssSelector("#Coverages > h3:nth-child(6)"));
	    value=element.getAttribute("outerText");
	    log.info("Coverage Amount : {}",value);
	    element=webDriver.findElement(By.cssSelector("#Coverages > span"));
	    value=element.getAttribute("outerText");
	    log.info("Time taken on Coverage is {}",value);
	    screenshot(str);
		}
		catch(Exception e)
		{
			e.getMessage();
			return false;
		}
		return true;
	}
}
