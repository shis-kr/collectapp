package Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import Base.Base;
import PageObjects.AppConstants;
import PageObjects.AppData;

public class Test1 extends Base{

	@Test(enabled=false)
	public void adminloginTest()
	{
		try
		{
			adminlogin();
			Assert.assertTrue(driver.findElement(By.id(AppConstants.profileTabs)).isDisplayed());
			logout();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test(enabled=false)
	public void changeLanguage()
	{
		try
		{
			String loginBtnPlaceholderText = driver.findElement(By.id(AppConstants.loginBtn)).getText();
			driver.findElement(By.id(AppConstants.changeLanguage)).click();
			List<WebElement> languages = driver.findElements(By.id(AppConstants.changeLanguageId));
			languages.get(2).click();
			String loginBtnPlaceholderText1 = driver.findElement(By.id(AppConstants.loginBtn)).getText();
			Assert.assertFalse(loginBtnPlaceholderText.contains(loginBtnPlaceholderText1));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test(enabled = true)
	public void filingData() throws InterruptedException, IOException
	{
//		Thread.sleep(5000);
//		Runtime.getRuntime().exec("adb shell am start -n io.appium.settings/.Settings -e wifi off");
//		Runtime.getRuntime().exec("adb -s shell am start -n io.appium.settings/.Settings -e wifi off");
//		Thread.sleep(8000);
		auditorlogin();
		List<WebElement> questionnaire = driver.findElements(By.id(AppConstants.ListSurveyItemId));
		questionnaire.get(0).click();
		for(int i=0;i<3;i++)
		{
			List<WebElement> questions = driver.findElements(By.id(AppConstants.QuestionItemId));
			System.out.println(questions.size());
			for(WebElement question:questions)
			{
				question.click();
				fillField();
			}
			swipeTopVertically();
		}
	}
	
	private void fillField() throws InterruptedException
	{
		//subjective field
		if(driver.findElements(By.id(AppConstants.speakButtonId)).size()>0)
		{
			driver.findElement(By.id(AppConstants.answerTextId)).sendKeys(AppData.textvalue);
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		//multiple choice field
		else if(driver.findElements(By.id(AppConstants.multipleChoiceId)).size()>0)
		{
			List<WebElement> choices = driver.findElements(By.id(AppConstants.multipleChoiceOptionId));
			choices.get(0).click();
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		//phone number input
		else if(driver.findElements(By.id(AppConstants.questionDescriptionId)).size()>0)
		{
			if(driver.findElement(By.id(AppConstants.questionDescriptionId)).getText().contains("Phone Number Input"))
			{
				driver.findElement(By.id(AppConstants.phoneanswerTextId)).sendKeys(AppData.numericalvalue);
				driver.findElement(By.id(AppConstants.questionDoneId)).click();
				return;
			}
		}
		
		//date picker
		else if(driver.findElements(By.id(AppConstants.datePicker)).size()>0)
		{
			driver.findElement(By.id(AppConstants.saveDateId)).click();
			return;
		}
		
		//numerical input
		else if(driver.findElements(By.id(AppConstants.questionDescriptionId)).size()>0)
		{
			if(driver.findElement(By.id(AppConstants.questionDescriptionId)).getText().contains("Numerical Input"))
			{
				driver.findElement(By.id(AppConstants.answerTextId)).sendKeys(AppData.numericalvalue);
				driver.findElement(By.id(AppConstants.questionDoneId)).click();
				return;
			}
		}
		
		//time picker
		else if(driver.findElements(By.id(AppConstants.timePicker)).size()>0)
		{
			driver.findElement(By.id(AppConstants.saveDateId)).click();
			return;
		}
		
		//scrolling
		else if(driver.findElements(By.id(AppConstants.scrollerId)).size()>0)
		{
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		
		//picture
		else if(driver.findElements(By.id(AppConstants.ImagePickFrom)).size()==2)
		{
			List<WebElement> picker = driver.findElements(By.id(AppConstants.ImagePickFrom));
			picker.get(0).click();
			driver.findElement(By.id(AppConstants.takeImageId)).click();
			driver.findElement(By.id(AppConstants.saveImage)).click();
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		
		//camera
		else if(driver.findElements(By.id(AppConstants.alertTitleImageId)).size()>0)
		{
			driver.findElement(By.id(AppConstants.ImagePickFrom)).click();;
			driver.findElement(By.id(AppConstants.takeImageId)).click();
			driver.findElement(By.id(AppConstants.saveImage)).click();
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		
		//audio
		else if(driver.findElements(By.id(AppConstants.recordAudio)).size()>0)
		{
			driver.findElement(By.id(AppConstants.startAudio)).click();
			wait.wait(200);
			driver.findElement(By.id(AppConstants.startAudio)).click();
			driver.findElement(By.id(AppConstants.questionDoneId)).click();
			return;
		}
		
		//signature
		else if(driver.findElements(By.id(AppConstants.signaurePad)).size()>0)
		{
			WebElement element = driver.findElement(By.id(AppConstants.signaurePad));

		    Actions builder = new Actions(driver);
		    Action drawAction = builder.moveToElement(element,135,15) //start points x axis and y axis. 
		              .click()
		              .moveByOffset(200, 60) // 2nd points (x1,y1)
		              .click()
		              .moveByOffset(100, 70)// 3rd points (x2,y2)
		              .doubleClick()
		              .build();
		    drawAction.perform();
		    driver.findElement(By.id(AppConstants.saveSignatureId)).click();
		    driver.findElement(By.id(AppConstants.questionDoneId)).click();
		    return;
		}
	}

	private void adminlogin() 
	{
		driver.findElement(By.id(AppConstants.userName)).sendKeys(AppData.adminEemail);
		driver.findElement(By.id(AppConstants.password)).sendKeys(AppData.adminPassword);
		driver.findElement(By.id(AppConstants.showPwd)).click();
		driver.findElement(By.id(AppConstants.loginBtn)).click();
	}
	
	private void logout()
	{
		driver.findElement(By.id(AppConstants.settingsTabId)).click();
		driver.findElement(By.id(AppConstants.logoutId)).click();
		Assert.assertTrue(driver.findElement(By.id(AppConstants.logoutFrameId)).isDisplayed());
		driver.findElement(By.id(AppConstants.logoutYesId)).click();
		Assert.assertTrue(driver.findElement(By.id(AppConstants.loginBtn)).isDisplayed());
	}

	private void auditorlogin() 
	{
		driver.findElement(By.id(AppConstants.userName)).sendKeys(AppData.auditorusername);
		driver.findElement(By.id(AppConstants.password)).sendKeys(AppData.auditorPassword);
		driver.findElement(By.id(AppConstants.showPwd)).click();
		driver.findElement(By.id(AppConstants.loginBtn)).click();
	}

	@Override
	public void executeTestCase() throws MalformedURLException {
		
	}

	
	
}
