package com.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.basic.Main;

public class TestCase extends Main {

	@BeforeClass(groups= {"Smoke"} )
	public static void beforeclass() throws Exception
	{
		
			Main.beforetest(); //smoke
			Main.main();  //smoke
		
	}
	
	
	@Test( groups= {"Regression"})
	public static void testone() throws Exception 
	{
		Main.cab();
		Main.selectCity();
		Main.dateTime();
		Main.search();
		Main.filter();
	}
	
	@Test(priority=1, groups= {"Smoke"})
	public void testwo() throws InterruptedException
	{
		
		Main.giftCard(); 
		Main.hotel(); 
		Main.hotelCity();
		Main.dates();
		Main.rooms(); 
	}
	
	
	@AfterClass (groups="Smoke")
	public static void afterTest()
	{
		Main.aftertest();  
	}
	
	
}
