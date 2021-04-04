package pageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class IxigoHomePage {

	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(xpath="//img[@title='ixigo.com']")
	WebElement ixigoLogo;
	
	@FindBy(xpath="//div[normalize-space(text())='From']/following-sibling::input[@placeholder='Enter city or airport']")
	WebElement textboxFromCity;
	
	@FindBy(xpath="//div[normalize-space(text())='To']/following-sibling::input[@placeholder='Enter city or airport']")
	WebElement textboxToCity;
	
	@FindBy(xpath="//input[@placeholder='Depart']")
	WebElement textboxDepartDate;
	
	@FindBy(xpath="//input[@placeholder='Return']")
	WebElement textboxReturnDate;
	
	@FindBy(xpath="//div[text()='Travellers | Class']/following-sibling::input")
	WebElement textboxTravellers;
	
	@FindBy(xpath="//div[@class='c-flight-listing-row-v2 selected show']")
	WebElement searchResults;
	
	@FindBy(xpath="//div[text()='Stops']/following-sibling::div/descendant::div[@class='stop-info']")
	List<WebElement> stopsList;
	
	@FindBy(xpath="(//div[starts-with(text(),'Departure from')])[1]/following-sibling::div/descendant::button")
	List<WebElement> departuresList;
	
	@FindBy(xpath="//div[text()='Airlines']/following-sibling::div/descendant::div[@class='arln-nm']")
	List<WebElement> airlinesList;
	
	WebElement stopFilterOption(String stopType) {
		return driver.findElement(By.xpath("//div[text()='Stops']/following-sibling::div/descendant::div[@class='stop-info' and text()='"+stopType+"']/parent::span/preceding-sibling::span"));
	}
	
	WebElement departureFilterOption(String deptTime) {
		return driver.findElement(By.xpath("(//div[starts-with(text(),'Departure from')]/following-sibling::div/descendant::button[text()='"+deptTime+"'])[1]"));
	}
	
	WebElement noOfAdults(int noOfAdults) {
		return driver.findElement(By.xpath("//div[text()='Adult']/parent::div/following-sibling::div/span[text()='"+noOfAdults+"']"));
	}
	
	WebElement city(String cityName) {
		return driver.findElement(By.xpath("//div[@class='city-row ']/div[@class='city' and contains(text(),'"+cityName+"')]"));
	}
	
	@FindBy(xpath="//button[normalize-space(text())='Search']")
	WebElement buttonSearch;
	
	WebElement flightResults(String city) {
		return driver.findElement(By.xpath("//span[@class='trp-city' and starts-with(text(),'"+city+"')]"));
	}
	
	List<WebElement> departingFlights(String cityName){
		return driver.findElements(By.xpath("//span[@class='trp-city' and starts-with(text(),'"+cityName+"')]/parent::div/parent::div/parent::div/following-sibling::div/div[1]/descendant::div[contains(@class,'c-flight-listing-split-row')]"));
	}
	
	List<WebElement> returnFlights(String cityName){
		return driver.findElements(By.xpath("//span[@class='trp-city' and starts-with(text(),'"+cityName+"')]/parent::div/parent::div/parent::div/following-sibling::div/div[2]/descendant::div[contains(@class,'c-flight-listing-split-row')]"));
	}
	
	@FindBy(xpath="//div[@class='result-col outr']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[contains(@class,'price-display')]/span[2]")
	List<WebElement> departingFlightPrice;
	
	@FindBy(xpath="//div[@class='result-col']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[contains(@class,'price-display')]/span[2]")
	List<WebElement> returnFlightPrice;
	
	@FindBy(xpath="//div[@class='result-col outr']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[@class='airline-text']/div")
	List<WebElement> departingFlightNumber; 
	
	@FindBy(xpath="//div[@class='result-col']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[@class='airline-text']/div")
	List<WebElement> returnFlightNumber;
	
	@FindBy(xpath="//div[@class='result-col outr']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[@class='time'][1]")
	List<WebElement> departingFlightTime; 
	
	@FindBy(xpath="//div[@class='result-col']/descendant::div[contains(@class,'c-flight-listing-split-row')]/descendant::div[@class='time'][1]")
	List<WebElement> returnFlightTime;
	
	List<WebElement> selDate(String date, String month, String year) {
		return driver.findElements(By.xpath("//div[@class='rd-month-label' and text()='"+month+" "+year+"']/following-sibling::table/descendant::td[not(contains(@class,'prev-month')) and not(contains(@class,'next-month'))]/div[@class='day has-info' and text()='"+date+"']"));
	}
	
	List<WebElement> returnDate(String date, String month, String year) {
		return driver.findElements(By.xpath("(//div[@class='rd-month-label' and text()='"+month+" "+year+"']/following-sibling::table/descendant::td[not(contains(@class,'prev-month')) and not(contains(@class,'next-month'))]/div[@class='day has-info' and text()='"+date+"'])[2]"));
	}
	
	@FindBy(xpath="//button[contains(@class,'next')]")
	WebElement nextDepartButton;
	
	@FindBy(xpath="(//button[contains(@class,'next')])[2]")
	WebElement nextReturnButton;
	
	public IxigoHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterTravelDetails(String fromCity, String toCity, String deptDate, String returnDate, int noOfTravellers) {
		wait = new WebDriverWait(driver, 50);
		checkPageIsReday();
		
		//Entring From City
		textboxFromCity.click();
		textboxFromCity.clear();
		textboxFromCity.sendKeys(fromCity);
		wait.until(ExpectedConditions.elementToBeClickable(city(fromCity)));
		city(fromCity).click();
		
		//Entering to city
		textboxToCity.clear();
		textboxToCity.sendKeys(toCity);
		wait.until(ExpectedConditions.elementToBeClickable(city(toCity)));
		city(toCity).click();
		
		//Entering Departure & Return Dates
		selectDepartDate(deptDate);
		selectReturnDate(returnDate);
		
		//Selecting no of travellers
		textboxTravellers.click();
		noOfAdults(noOfTravellers).click();
		
		//Clicking on search button
		buttonSearch.click();		
	}
	
	public void validateResultsAndFilterOptions(String stopType) {
		wait.until(ExpectedConditions.visibilityOf(searchResults));
		Assert.assertEquals(searchResults.isDisplayed(), true);
		
		//Verifying Stops filter options
		for(WebElement stop: stopsList) {
			Reporter.log("\""+stop.getText()+"\" option is present in Stops Filter");
			System.out.println("\""+stop.getText()+"\" option is present in Stops Filter");
		}
		
		//Verifying Departures filter Options
		for(WebElement departure: departuresList) {
			Reporter.log("\""+departure.getText()+"\" option is present in Departure Filter");
			System.out.println("\""+departure.getText()+"\" option is present in Departure Filter");
		}
		
		//Verifying Airlines filter Options
		for(WebElement airline: airlinesList) {
			Reporter.log("\""+airline.getText()+"\" option is present in Airline Filter");
			System.out.println("\""+airline.getText()+"\" option is present in Airline Filter");
		}
		
		//Selecting Stop type in Stops filter option
		stopFilterOption(stopType).click();
		Reporter.log("\""+stopType+"\" stop has been selected from stops filter");
	}
	
	public void printDepartingAirlineDetailsWhereFareIsLessThan(int fare) {

		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for(int i = 0; i<departingFlightPrice.size(); i++) {
			
			ArrayList<String> list = new ArrayList<String>();
			
			if(Integer.parseInt(departingFlightPrice.get(i).getText()) < fare) {
				list.add(departingFlightTime.get(i).getText());
				list.add(departingFlightPrice.get(i).getText());
				map.put(departingFlightNumber.get(i).getText(), list);
			}
		}
		Reporter.log("--------------------------------------------------------");
		System.out.println("Departing flight details where fare is less than "+fare);
		Reporter.log("Departing flight details where fare is less than "+fare);
		System.out.println("--------------------------------------------------------");
		Reporter.log("--------------------------------------------------------");
		for(Map.Entry<String, List<String>> entry: map.entrySet()) {
			Reporter.log("Flight Number: "+entry.getKey() + ", Flight Time: "+entry.getValue().get(0) + ", Flight Fare: "+entry.getValue().get(1));
			System.out.println("Flight Number: "+entry.getKey() + ", Flight Time: "+entry.getValue().get(0) + ", Flight Fare: "+entry.getValue().get(1));
		}
	}
	
	public void printReturnAirlineDetailsWhereFareIsLessThan(int fare) {

		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for(int i = 0; i<returnFlightPrice.size(); i++) {
			
			ArrayList<String> list = new ArrayList<String>();
			
			if(Integer.parseInt(returnFlightPrice.get(i).getText()) < fare) {
				list.add(returnFlightTime.get(i).getText());
				list.add(returnFlightPrice.get(i).getText());
				map.put(returnFlightNumber.get(i).getText(), list);
			}
		}
		System.out.println("--------------------------------------------------------");
		Reporter.log("--------------------------------------------------------");
		System.out.println("Return flight details where fare is less than "+fare);
		Reporter.log("Return flight details where fare is less than "+fare);
		System.out.println("--------------------------------------------------------");
		Reporter.log("--------------------------------------------------------");
		
		for(Map.Entry<String, List<String>> entry: map.entrySet()) {
			Reporter.log("Flight Number: "+entry.getKey() + ", Flight Time: "+entry.getValue().get(0) + ", Flight Fare: "+entry.getValue().get(1));
			System.out.println("Flight Number: "+entry.getKey() + ", Flight Time: "+entry.getValue().get(0) + ", Flight Fare: "+entry.getValue().get(1));
		}
		
	}
	
	public void selectDepartDate(String date) {
		String[] dateVal = date.split(" ");
		String d = dateVal[0];
		String m = dateVal[1];
		String y = dateVal[2];
		
		textboxDepartDate.click();
		
		while(selDate(d, m, y).size() != 1) {
			nextDepartButton.click();
		}
		selDate(d, m, y).get(0).click();
	}
	
	public void selectReturnDate(String date) {
		String[] dateVal = date.split(" ");
		String d = dateVal[0];
		String m = dateVal[1];
		String y = dateVal[2];
		
		textboxReturnDate.click();
		
		while(selDate(d, m, y).size() != 1) {
			nextReturnButton.click();
		}
		selDate(d, m, y).get(0).click();
	}
	
	public void checkPageIsReday(){
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		if(js.executeScript("return document.readyState").toString().equals("complete")) {
			try {
				Thread.sleep(1000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Page is loaded");
			return;
		}
		
		for(int i = 0; i<=25; i++) {
			try {
				Thread.sleep(1000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			if(js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}
	
}
