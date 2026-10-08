package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;


    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");
    private final By metroOptionFirst = By.className("select-search__select");

    private final By deliveryDateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    private final By rentalPeriodOptionDay = By.xpath(".//div[text()='сутки']");
    private final By datepickerCalendar = By.className("react-datepicker__calendar-container");
    private final By blackPearlCheckbox = By.id("black");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath(".//div[contains(@class,'Order_Buttons')]/button[text()='Заказать']");
    private final By confirmButton = By.xpath(".//button[text()='Да']");

    // Окно подтверждения (до нажатия "Да")
    private final By confirmationModal = By.cssSelector("div[class*='Order_ModalHeader']");

    // Финальное сообщение об успехе - появляется ПОСЛЕ клика "Да"
    private final By successMessage = By.xpath(".//div[contains(@class,'Order_ModalHeader')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillFirstStep(String firstName, String lastName, String address,
                              String metroStation, String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ждём, пока поле "Имя" появится на странице
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);

        WebElement metroInput = driver.findElement(metroStationField);
        metroInput.sendKeys(metroStation);

        wait.until(ExpectedConditions.presenceOfElementLocated(metroOptionFirst));
        driver.findElement(metroOptionFirst).findElement(By.tagName("li")).click();

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    public void fillSecondStep(String deliveryDate, String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ждём, пока поле даты появится
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateField));

        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.ENTER);

        // Ждём закрытия календаря
        wait.until(ExpectedConditions.invisibilityOfElementLocated(datepickerCalendar));

        // Срок аренды
        driver.findElement(rentalPeriodDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodOptionDay));
        driver.findElement(rentalPeriodOptionDay).click();

        // Цвет
        driver.findElement(blackPearlCheckbox).click();

        // Комментарий
        driver.findElement(commentField).sendKeys(comment);

        // Нажать "Заказать"
        driver.findElement(orderButton).click();

        // Ждём окно подтверждения
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));

        // Нажать «Да»
        driver.findElement(confirmButton).click();

        // Ждём смену текста заголовка на "Заказ оформлен"
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                successMessage, "Заказ оформлен"));
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText();
    }
}
