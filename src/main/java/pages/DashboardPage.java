package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Helper;
import objects.DashboardPageObjects;
import objects.CommonObjects;

public class DashboardPage extends Helper{
	public DashboardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void callPatient(String visitId, boolean isMute, boolean stopCamera) throws Exception {
		WebElement patientToCallElement = getPresentElement(String.format(DashboardPageObjects.patientTocallElement, visitId));
		patientToCallElement.click();
		WebElement continueOnThisBrowser = getPresentElement(CommonObjects.continueOnThisBrowserElement);
		continueOnThisBrowser.click();
		switchToIframe();
		WebElement unmuteElement = getPresentElement(CommonObjects.auditoElement);
		unmuteElement.click();
		WebElement stopVideoElement = getPresentElement(CommonObjects.videoElement);
		stopVideoElement.click();
		try {
			WebElement joinNowBtn = driver.findElement(By.xpath(CommonObjects.joinNowButtonElement));
			joinNowBtn.click();
		}catch(Exception e) {
			Thread.sleep(3000);
		}
		driver.switchTo().defaultContent();
	}
	
	public void switchToIframe() throws Exception {
		WebElement iframeElement = getPresentElement(CommonObjects.iframeElement);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
		Thread.sleep(300);
	}
}