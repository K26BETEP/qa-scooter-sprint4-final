package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final String baseUrl = "https://qa-scooter.praktikum-services.ru";

    // Кнопка "Заказать" в шапке
    private final By headerOrderButton = By.className("Button_Button__ra12g");

    // Кнопка "Заказать" внизу страницы
    private final By bottomOrderButtonLocator = By.className("Button_Middle__1CSJM");

    // Вопросы FAQ - заголовки
    private By getQuestionHeading(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Вопросы FAQ - панели с ответами
    private By getAnswerPanel(int index) {
        return By.id("accordion__panel-" + index);
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(baseUrl);
    }

    // Клик по кнопке "Заказать" в шапке
    public void clickHeaderOrderButton() {
        driver.findElement(headerOrderButton).click();
    }

    // Клик по кнопке "Заказать" внизу страницы
    public void clickBottomOrderButton() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(bottomOrderButtonLocator));
        driver.findElement(bottomOrderButtonLocator).click();
    }

    // Открыть вопрос по индексу и получить текст ответа
    public String openQuestionAndGetAnswer(int questionIndex) {
        By questionLocator = getQuestionHeading(questionIndex);
        By answerLocator = getAnswerPanel(questionIndex);

        WebElement questionElement = driver.findElement(questionLocator);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", questionElement);

        questionElement.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        return driver.findElement(answerLocator).getText();
    }
}
