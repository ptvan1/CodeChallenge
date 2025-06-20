package objects;

public interface RoomPageObjects {
	String patientTocallElement = "//a[@title = 'Call' and @data-visit-id='%s']";
	String yourNameInputElement = "//input[@name='first_name']";
	String consentCheckboxElement = "//input[@name='consent']";
	String enterWaitingRoomButtonElement = "//input[@type='submit']";
	String forProviderLoginButtonElement = "(//a[@href='/providers/login'])[1]";
}
