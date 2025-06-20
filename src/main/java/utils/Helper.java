package utils;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public Helper(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(30));
	}
	public void clickElement(By by) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elmToClick = wait.until(ExpectedConditions.elementToBeClickable(by));
		elmToClick.click();
	}
	
	public void clickElement(String xpath) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elmToClick = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		elmToClick.click();
	}
	
	public WebElement getPresentElement(String xpath) {
		WebElement elm = null;
		try {
			this.driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			elm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
			return elm;
		}catch(Exception e) {
			return null;
		}
	}
	
	public WebElement getPresentElementById(String id) {
		WebElement elm = null;
		try {
			elm = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
			return elm;
		}catch(Exception e) {
			return null;
		}
	}
	
	public static void switchWindow(WebDriver maximizedWindow, WebDriver minimizedWindow) {
		minimizedWindow.manage().window().minimize();
		maximizedWindow.manage().window().maximize();
	}
	
}
