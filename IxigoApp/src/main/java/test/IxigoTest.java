package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.reporters.FailedReporter;

import pageFactory.IxigoHomePage;

public class IxigoTest {

	WebDriver driver;
	IxigoHomePage homePage;
	
	@BeforeTest
	public void launchApp() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.ixigo.com/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
	
	@Test(priority=0)
	public void verifyPage() {
		String expPageTitle = "ixigo - Flights, IRCTC Train Booking, Bus Booking, Air Tickets & Hotels";
		if(expPageTitle.equals(driver.getTitle())) {
			Reporter.log("Page title verified and matching");
		}else {
			Reporter.log("Page title not matching with expected page title");
		}
	}
	
	@Test(priority=1)
	public void enterTravelDetails() throws InterruptedException {
		homePage = PageFactory.initElements(driver, IxigoHomePage.class);
		homePage.enterTravelDetails("New Delhi", "Bengaluru", "27 April 2021", "24 June 2021", 2);
	}
	
	@Test(priority=2)
	public void verifySearchResultsAndFilterOptions() {
		homePage.validateResultsAndFilterOptions("Non stop");
	}
	
	
	@Test(priority=3)
	public void printAirlineDetails() {
		homePage.printDepartingAirlineDetailsWhereFareIsLessThan(7000);
		homePage.printReturnAirlineDetailsWhereFareIsLessThan(7000);
	}
	
	@AfterTest
	public void closeResources() {
		driver.close();
	}
}
