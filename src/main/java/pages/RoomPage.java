package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import objects.CommonObjects;
import objects.ProviderLoginPage;
import objects.RoomPageObjects;
import objects.DashboardPageObjects;
import utils.Helper;

public class RoomPage extends Helper{
	public RoomPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void loginAsProvider(String email, String password) throws InterruptedException {
		getPresentElement(RoomPageObjects.forProviderLoginButtonElement).click();
		getPresentElementById(ProviderLoginPage.emailElementID).sendKeys(email);
		getPresentElementById(ProviderLoginPage.passwordElementID).sendKeys(password);
		getPresentElementById(ProviderLoginPage.loginButtonElementID).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DashboardPageObjects.dashboardMenuElement)));
	}

	public String enterWaitingRoom(String fullName, boolean isMute, boolean stopCamera) {
		String visitID = "";
		try {
			// Navigate to VSee room
			WebElement nameInput = getPresentElement(RoomPageObjects.yourNameInputElement);
			nameInput.clear();
			nameInput.sendKeys(fullName);

			WebElement agreeCheckbox = getPresentElement(RoomPageObjects.consentCheckboxElement);
			agreeCheckbox.click();

			// Click on Join or Enter button
			WebElement joinBtn = getPresentElement(RoomPageObjects.enterWaitingRoomButtonElement);
			joinBtn.click();

			// Wait for confirmation or new page
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(CommonObjects.continueOnThisBrowserElement)));

			WebElement continueOnThisBrowser = driver.findElement(By.xpath(CommonObjects.continueOnThisBrowserElement));
			continueOnThisBrowser.click();

			switchToIframe();
			WebElement unmuteElement = getPresentElement(CommonObjects.auditoElement);
			unmuteElement.click();
			
			WebElement stopVideoElement = getPresentElement(CommonObjects.videoElement);
			stopVideoElement.click();

			String currentUrl = driver.getCurrentUrl();
			String[] splitUrl = currentUrl.split("/");
			visitID = splitUrl[splitUrl.length - 1];
			try {
				WebElement joinNowBtn = driver.findElement(By.xpath(CommonObjects.joinNowButtonElement));
				joinNowBtn.click();
			}catch(Exception e) {
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.switchTo().defaultContent();
		return visitID;
	}

	public void switchToIframe() throws Exception {
		WebElement iframeElement = getPresentElement(CommonObjects.iframeElement);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeElement));
		Thread.sleep(300);
	}
}