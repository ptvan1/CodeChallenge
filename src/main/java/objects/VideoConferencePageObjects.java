package objects;

public interface VideoConferencePageObjects {
	String visitChatButtonElementID= "group-chat";
	String sendMessageButtonElementID= "sendBtn";
	String lastMessageElement = "(//div[@class='webchat-message-bubble'])[last()]";
	String leaveTheMeetingIconElement = "//*[@aria-label='Leave the meeting']";
	String iframeElement = "//iframe[@name='jitsiConferenceFrame0']";
	String endVisitButtonElement = "//a[text()='End Visit']";
}
