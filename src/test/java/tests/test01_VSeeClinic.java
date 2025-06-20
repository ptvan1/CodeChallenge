package tests;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.DashboardPage;
import pages.RoomPage;
import pages.VideoConferencePage;
import utils.Helper;

public class test01_VSeeClinic {
    WebDriver userADriver;
    WebDriver userBDriver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        Map<String, Object> prefs = new HashMap<>();

        // 1 = Allow, 2 = Block
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        options.setExperimentalOption("prefs", prefs);
        userADriver = new ChromeDriver(options);
        userBDriver = new ChromeDriver(options);
    }

    @Test
    public void testChatBetweenUsers() throws Exception {
        String roomUrl = "https://team.vsee.me/u/robin2";
        String providerUser = "phungtrongvan3004@gmail.com";
        String providerPass = "Van3004@test";
        String patientName = "Nguyen Van A";
        String messageToSend = "Hello from B";
        
        // UserA - waiting room
        Helper.switchWindow(userADriver, userBDriver);
        userADriver.get(roomUrl);
        userADriver.manage().window().maximize();
        RoomPage roomPageUserA = new RoomPage(userADriver);
        String visitIdOfUserA = roomPageUserA.enterWaitingRoom(patientName, true, true);

        // UserB - login and join
        Helper.switchWindow(userBDriver, userADriver);
        userBDriver.get(roomUrl);
        RoomPage roomPageUserB = new RoomPage(userBDriver);
        DashboardPage userBDashboardPage = new DashboardPage(userBDriver);
        roomPageUserB.loginAsProvider(providerUser, providerPass);
        
        // UserB - call patient
        VideoConferencePage userBVideoConferencePage = new VideoConferencePage(userBDriver);
        userBDashboardPage.callPatient(visitIdOfUserA, true, true);
        userBVideoConferencePage.sendMessage(messageToSend);

        // UserA verifies message
        Helper.switchWindow(userADriver, userBDriver);
        VideoConferencePage userAVideoConferencePage = new VideoConferencePage(userADriver);
        Assert.assertTrue(userAVideoConferencePage.verifyMessageReceived(messageToSend));

        // End call
        Helper.switchWindow(userBDriver, userADriver);
        userBVideoConferencePage.providerEndCall();
        Helper.switchWindow(userADriver, userBDriver);
        userADriver.switchTo().window(userADriver.getWindowHandle());
        userAVideoConferencePage.patientEndCall();
    }
    
    @AfterClass
    public void tearDown() {
    	userADriver.manage().deleteAllCookies();
    	userBDriver.manage().deleteAllCookies();
        userADriver.quit();
        userBDriver.quit();
    }
}