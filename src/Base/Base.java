package Base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public abstract class Base {

	public static AndroidDriver driver = null;
	protected static String packName = null;
	DesiredCapabilities cap;
	File app;
	protected static WebDriverWait wait = null;
	
	protected String Con_Package_Name = "com.socialcops.collect";
	protected String Con_Activity_Name = "com.socialcops.collect.view.activity.MainActivity";
	
	
	// before method function runs before each test case function runs
	@BeforeMethod
	public AppiumDriver initializeDrivers()
	{
		try
		{
			//path of android app
			File appDir = new File("app");
			File app = new File(appDir,"Collect_com.socialcops.collect.apk");
			// setting desired capabilities of device
			cap = new DesiredCapabilities();
			cap.setCapability("platformName","Android");
			cap.setCapability("deviceName", "Android Device");
			cap.setCapability("app", app.getAbsolutePath());
			// setting package name and activity name to desired capabilities
			cap.setCapability("app-package", Con_Package_Name);
			cap.setCapability("app-activity", Con_Activity_Name);
			// default server to connect to appium
			driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"),cap);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		
		return driver;
	}
	
	@Test
	public abstract void executeTestCase() throws MalformedURLException;
	
	
	//after mothod runs after each test case function is run
	@AfterMethod
	public void tearDown(ITestResult result)
	{
		driver.quit();
	}
	
	public static void swipeTopVertically()
	{
		  try
		  {
			  Dimension size = driver.manage().window().getSize();
			  int starty = (int) (size.height * 0.90 );
			  int endy = (int) (size.height * 0.05);
			  int startx = size.width / 2;
			  driver.swipe(startx, starty, startx, endy, 3000);
		  }
		  catch(Exception e)
		  {
			  System.out.println(e.getMessage());
		  }
	}
	
}
