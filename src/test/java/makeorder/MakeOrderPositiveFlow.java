package makeorder;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.OrderPageForWhom;
import pageobject.OrderPageAboutRent;
import pageobject.MainPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.MainPage;

import java.time.Duration;

import static pageobject.MainPage.orderButtonMiddle;
import static pageobject.MainPage.orderButtonTop;


@RunWith(Parameterized.class)
public class MakeOrderPositiveFlow {

    private WebDriver driver;
    private static By orderButton;
    private static String name;
    private static String surname;
    private static String address;
    private static String phoneNumber;
    private static String comment;

    public MakeOrderPositiveFlow(By orderButton, String name, String surname, String address, String phoneNumber, String comment) {
        this.orderButton = orderButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object [][] getCredentials(){
        return new Object[][] {
                {orderButtonTop, "Елена", "Бойко", "Варшавка", "+79169077854", "Я старалась" },
                {orderButtonMiddle, "Анна Мария", "Ян", "Туман, 666", "3570001112233", ""}
        };
    }
    @Before
    public void setUp(){
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickCookie();
    }
    @Test
    public void mainFlowWithValidDataIsSuccessfully() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOrderButton(orderButton);
        OrderPageForWhom orderPageForWhom = new OrderPageForWhom(driver);
        //раздел для кого самокат
        orderPageForWhom.enterName(name);
        orderPageForWhom.enterSurname(surname);
        orderPageForWhom.enterAddress(address);
        orderPageForWhom.clickMetroStation();
        orderPageForWhom.enterPhoneNumber(phoneNumber);
        orderPageForWhom.clickNextButton();
        //раздел Про аренду
        OrderPageAboutRent orderPageAboutRent = new OrderPageAboutRent(driver);
        orderPageAboutRent.chooseDate();
        orderPageAboutRent.chooseRentalPeriod();
        orderPageAboutRent.chooseColor();
        orderPageAboutRent.writeComment(comment);
        orderPageAboutRent.clickFinalOrderButton();
        orderPageAboutRent.confirmOrder();
        orderPageAboutRent.checkOrderComplete();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

}
