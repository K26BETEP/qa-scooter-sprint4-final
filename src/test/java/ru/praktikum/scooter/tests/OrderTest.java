package ru.praktikum.scooter.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.praktikum.scooter.pages.MainPage;
import ru.praktikum.scooter.pages.OrderPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest {

    private WebDriver driver;
    private final String entryPoint;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String deliveryDate;
    private final String comment;

    public OrderTest(String entryPoint, String firstName, String lastName, String address,
                     String metroStation, String phone, String deliveryDate, String comment) {
        this.entryPoint = entryPoint;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"header", "Иван", "Иванов", "ул. Тверская, 1", "Чистые пруды", "89991234567", "10.03.2016", "Позвонить за час"},
                {"bottom", "Анна", "Смирнова", "пр. Мира, 10", "Проспект Мира", "89997654321", "12.11.1998", "Без комментария"}
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void orderScooterTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        if ("header".equals(entryPoint)) {
            mainPage.clickHeaderOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstStep(firstName, lastName, address, metroStation, phone);
        orderPage.fillSecondStep(deliveryDate, comment);

        String successMessage = orderPage.getSuccessMessage();

        System.out.println("=== ТЕКСТ СООБЩЕНИЯ: [" + successMessage + "] ===");

        Assert.assertTrue(
                "Не появилось сообщение об успешном заказе. Текст: " + successMessage,
                successMessage != null && !successMessage.isEmpty());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
