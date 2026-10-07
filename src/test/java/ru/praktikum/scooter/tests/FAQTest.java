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

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FAQTest {

    private WebDriver driver;
    private final int questionIndex;
    private final String expectedAnswerContains;

    public FAQTest(int questionIndex, String expectedAnswerContains) {
        this.questionIndex = questionIndex;
        this.expectedAnswerContains = expectedAnswerContains;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, "400 рублей"},
                {1, "один самокат"},
                {2, "времени аренды"},
                {3, "завтрашнего дня"},
                {4, "Пока что нет"},
                {5, "полной зарядкой"},
                {6, "Штрафа не будет"},
                {7, "обязательно"}
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void checkFaqAnswerOpens() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        String actualAnswer = mainPage.openQuestionAndGetAnswer(questionIndex);

        Assert.assertTrue(
                "Ответ на вопрос №" + questionIndex + " не содержит ожидаемого текста",
                actualAnswer.contains(expectedAnswerContains));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

