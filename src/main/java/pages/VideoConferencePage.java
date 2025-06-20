package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import objects.CommonObjects;
import objects.VideoConferencePageObjects;
import utils.Helper;

public class VideoConferencePage extends Helper{
	public VideoConferencePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void sendMessage(String message) throws InterruptedException {
		getPresentElementById(VideoConferencePageObjects.visitChatButtonElementID).click();
		WebElement chatInputElement = getPresentElement(CommonObjects.viewChatElement);
		chatInputElement.click();
		chatInputElement.sendKeys(message + Keys.ENTER);
		Thread.sleep(2000);
	}

	public boolean verifyMessageReceived(String expected) throws InterruptedException {
		Thread.sleep(2000);
		return getPresentElement(VideoConferencePageObjects.lastMessageElement).getText().equals(expected);
	}

	public void providerEndCall() throws InterruptedException {
		try {
			switchToIframe();
			getPresentElement(VideoConferencePageObjects.leaveTheMeetingIconElement).click();
			driver.switchTo().defaultContent();
			Thread.sleep(300);
			WebElement leaveTheCallElement = getPresentElement("//a[@class='btn btn-leaveCall']");
			leaveTheCallElement.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
	}

	public void patientEndCall() throws InterruptedException {
		try {
			switchToIframe();
			WebElement leaveTheMeetingElement = getPresentElement(VideoConferencePageObjects.leaveTheMeetingIconElement);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", leaveTheMeetingElement);
			leaveTheMeetingElement.click();
			driver.switchTo().defaultContent();
			Thread.sleep(1000);
			WebElement endVisitButtonElement = getPresentElement(VideoConferencePageObjects.endVisitButtonElement);
			endVisitButtonElement.click();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
	}

	public void switchToIframe() throws Exception {
		WebElement iframeElement = getPresentElement(CommonObjects.iframeElement);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
		Thread.sleep(300);
	}
}
