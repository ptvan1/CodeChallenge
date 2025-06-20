package objects;

public interface CommonObjects {
	String continueOnThisBrowserElement = "//h3[text()='Continue on this browser']";
	String auditoElement = "//div[contains(@class,'audio-preview')]//div[@class='toolbox-button']";
	String videoElement = "//div[contains(@class,'video-preview')]//div[@class='toolbox-button']";
	String joinNowButtonElement = "//*[@aria-label='Join Now']";
	String iframeElement = "//div[@id='jitsi']/iframe";
	String viewChatElement = "//form[@class='input-area webchat-input-form']/input[1]";
	String endVisitButtonElement = "//a[text()='End Visit']";
}
