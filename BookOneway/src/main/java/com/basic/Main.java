package com.basic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.remote.*;
import com.utils.DateUtils;
import com.utils.ExtentReportManager;


public class Main {

	public static String propertyFilePath = "C:\\Users\\910655\\Documents\\BookOneway\\BookOneway\\Config.property";
	public static WebDriver driver;
	public static Properties prop = new Properties();
	public static ExtentReports report = ExtentReportManager.GetReportInstance();
	public static ExtentTest logger;

	// @Test    
	public static void main() throws Exception {

		try {
			
			DesiredCapabilities capabilities=null;
			
			logger = report.createTest("OUT STATION CAB");
			logger.log(Status.INFO, "Initializing the Browser");
			FileReader reader = new FileReader(propertyFilePath);
			prop.load(reader);
			String browser = prop.getProperty("browser");
			String baseUrl = prop.getProperty("url");
			// set the system property for Chrome driver
			if (browser.equalsIgnoreCase("chrome"))
			{
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(baseUrl); 
				/*capabilities=DesiredCapabilities.chrome();
				driver=new RemoteWebDriver(new URL("http://192.168.1.100:4444/wd/hub"), capabilities);
				driver.manage().window().maximize();*/
				
			} 
			else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "Drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.get(baseUrl);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void cab() throws InterruptedException {
		logger.log(Status.INFO, "Booking One way outsation cab");
		driver.navigate().refresh();
		driver.navigate().to(prop.getProperty("Cabsurl"));
		Thread.sleep(5000);
		System.out.println("Cabs Page Title: " + driver.getTitle());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("/html/body")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}


	public static void selectCity() throws InterruptedException {
		logger.log(Status.INFO, "Selecting From and To places");
		driver.findElement(By.xpath("//*[@id='fromCity']"));
		WebDriverWait wait1 = new WebDriverWait(driver, 100000);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='fromCity']"))).click();
		WebElement fromInput = driver.findElement(By.xpath("//div[@role='combobox']//child::input[@type='text']"));
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		fromInput.sendKeys("del");
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		List<WebElement> fromDrop = driver.findElements(By.xpath("//ul[@role='listbox']//li"));
		for (WebElement down : fromDrop) {
			String city = down.getText();
			if (city.contains("Delhi, India")) {
				down.click();
				break;
			}
		}
		

		// Selecting To city
		WebElement toInput = driver.findElement(By.xpath("//div[@role='combobox']//child::input[@type='text']"));
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		toInput.sendKeys("man");
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		List<WebElement> toDrop = driver.findElements(By.xpath("//ul[@role='listbox']//li"));
		for (WebElement down : toDrop) {
			String city = down.getText();
			if (city.contains("Manali, Himachal Pradesh, India")) {
				down.click();
				break;
			}
		}
	}

	
	// @Test(priority = 3)
	public static void dateTime() throws InterruptedException {
		logger.log(Status.INFO, "Selecting date and time");
		// Selecting date
		driver.findElement(By.xpath("//span[contains(text(),'DEPARTURE')]")).click();
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		String flag = "False";

		while (flag == "False") {

			if (driver.findElements(By.xpath("//div[@class='DayPicker-Day']")).size() > 0) {

				driver.findElement(By.xpath("//div[@class='DayPicker-Day']")).click();
				flag = "True";
				Thread.sleep(5000);
				//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			}

			else {
				Thread.sleep(5000);
				//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
			}
		}

		// Selecting time
		WebElement pickupTime = driver.findElement(By.xpath("//span[normalize-space()='PICKUP-TIME']"));
		pickupTime.click();
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//li[normalize-space()='06:30 AM']")).click();
		Thread.sleep(2000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
      }

	
	
	public static void search()
	{
		logger.log(Status.INFO, "Searching for results");
		driver.findElement(By.xpath("//a[contains(text(), 'Search')]")).click();
	}

	
	
	public static void filter() throws InterruptedException {
		logger.log(Status.INFO, "Filter the results");
		driver.findElement(By.xpath("//label[normalize-space()='SUV']")).click();
		Thread.sleep(4000);
		//driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//span[@class = 'cursorPointer dodgerBlueColor']")).click();
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class = 'sortOptionUnit'][1]/p")).click();
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

		for (WebElement printData : driver
				.findElements(By.xpath("//*[@id=\"List\"]/div[1]/div/div[2]/div[1]/div[1]/span[1]")))

		{
			System.out.println("The Car available with lowest price:" + printData.getText());
		}

		for (WebElement printData : driver
				.findElements(By.xpath("//*[@id=\"List\"]/div[1]/div/div[3]/div/div[2]/div/p[1]")))

		{
			System.out.println("Price: " + printData.getText());
		}
	}

	
	public static void giftCard() throws InterruptedException {
		logger.log(Status.INFO, "Group Gifting");
		driver.navigate().to(prop.getProperty("giftcardsurl"));
		System.out.println("Giftcards Page Title: " + driver.getTitle());
		driver.manage().timeouts().implicitlyWait(05, TimeUnit.SECONDS);

		// clicking Group Gifting
		System.out.println("Group Gifting Page Title: " + driver.getTitle());
		driver.findElement(By.xpath("/html/body/header/div[1]/div/ul/li[5]/a")).click();
		 Thread.sleep(5000);
		 //driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		// take screenshot of the page
		String imagename = DateUtils.getTimeStamp() + ".png";
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// File src= takeScreenShot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "/Screenshot/" + imagename);
		try {

			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/Screenshot/" + imagename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		System.out.println("Error Message: The Group Gifting Site can't be reached");

	}

	
	public static void hotel() 
	{
		logger.log(Status.INFO, "Hotel Booking");
		driver.navigate().to(prop.getProperty("hotelPageUrl"));
		System.out.println("Hotel Page Title: " + driver.getTitle());
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	
	public static void hotelCity() throws InterruptedException {
		logger.log(Status.INFO, "Selecting hotel city");
		driver.findElement(By.cssSelector("#city")).click();
		WebElement toInput1 = driver.findElement(By.xpath("//div[@role='combobox']//child::input[@type='text']"));
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		toInput1.sendKeys("man");
		Thread.sleep(3000);    
		List<WebElement> toDrop1 = driver.findElements(By.xpath("//ul[@role='listbox']//li"));
		for (WebElement down : toDrop1) {
			String city = down.getText();
			if (city.contains("Manali, Himachal Pradesh, India")) {
				down.click();
				break;
			}
		}
	}

	
	public static void dates() throws InterruptedException {
		logger.log(Status.INFO, "Selecting dates");
		WebElement dates = driver.findElement(By.id("checkin"));
		dates.click();
		Thread.sleep(2000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		String flag = "false";

		while (flag == "false") {
			List<WebElement> calendar = driver.findElements(By.xpath("//div[@class='DayPicker-Day']"));
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if (calendar.size() > 0) {
				WebElement in = driver.findElement(By.xpath("//div[@class='DayPicker-Day']"));
				in.click();
				Thread.sleep(2000);
				//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				WebElement out = driver.findElement(By.xpath("//div[@class='DayPicker-Day']"));
				out.click();
				//Thread.sleep(2000);
				driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				flag = "True";
				Thread.sleep(3000);
				//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			} else {
				Thread.sleep(5000);
				//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
				break;
			}
		}
	}

	
	public static void rooms() throws InterruptedException {
		logger.log(Status.INFO, "Dispalying Adult count");
		driver.findElement(By.cssSelector("#guest")).click();
		Thread.sleep(2000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class = 'addRooomDetails']/ul/li[12]")).click();
		List<WebElement> adultcount = driver
				.findElements(By.xpath("//div[@class = 'addRooomDetails']/ul[1]/li[@data-cy]"));
		System.out.println("Total Adult count is: " + adultcount.size());
		// Clicking on Apply button
		driver.findElement(By.xpath("//*[@class = 'primaryBtn btnApply']")).click();
		Thread.sleep(1000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		// Clicking on search button
		driver.findElement(By.xpath("//*[@id = 'hsw_search_button']")).click();
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1500)");
		Thread.sleep(5000); 
		
		driver.navigate().to(prop.getProperty("url"));

		// WebDriverWait Wait=new WebDriverWait(driver,5000);
/*		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,5000)");
		Thread.sleep(5000);  */
		//driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
	}

	
	public static void beforetest() {

		System.out.println("Test Automation Started Successfully");
	}

	public static void aftertest() {
		logger.log(Status.INFO, "Test Executed successfully");
		report.flush();
		driver.quit();
		System.out.println("Test Automation Successfully Executed");
	}

}

