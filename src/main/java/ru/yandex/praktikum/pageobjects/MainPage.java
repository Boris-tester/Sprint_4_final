package ru.yandex.praktikum.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ===== баннер с куками =====
    private final By cookieButton = By.id("rcc-confirm-button");

    // ===== логотипы =====
    // логотип Самоката
    private final By scooterLogo = By.cssSelector("a.Header_LogoScooter__3lsAR");
    // логотип Яндекса
    private final By yandexLogo = By.cssSelector("a.Header_LogoYandex__3TSOI");

    // ===== Кнопки "Заказать" =====
    // Кнопка 1 (Верхняя)
    private final By topOrderButton = By.xpath("(//button[text()='Заказать'])[1]");
    // Кнопка 2 (Нижняя)
    private final By bottomOrderButton = By.xpath("(//button[text()='Заказать'])[2]");

    // ===== Статус заказа (хедер) =====
    private final By statusButton = By.xpath("//button[contains(@class,'Header_Link') and text()='Статус заказа']");
    private final By orderNumberInput = By.xpath("//input[contains(@class,'Header_Input') and @placeholder='Введите номер заказа']");
    private final By goButton = By.xpath("//button[contains(@class,'Header_Button') and (text()='Go!' or text()='Посмотреть')]");

    // ===== Блок "Такого заказа нет" на странице трека =====
    private final By orderNotFoundBlock = By.xpath("//div[contains(@class,'Track_NotFound')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // открыть главную
    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    // принять куки, если баннер есть
    public void acceptCookies() {
        if (driver.findElements(cookieButton).size() > 0) {
            driver.findElement(cookieButton).click();
        }
    }

    // ===== FAQ =====
    public void clickQuestion(int index) {
        By question = By.id("accordion__heading-" + index);
        driver.findElement(question).click();
    }

    public String getAnswerText(int index) {
        By answer = By.id("accordion__panel-" + index);
        return driver.findElement(answer).getText();
    }

    // ===== заказ самоката =====
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
        wait.until(ExpectedConditions.urlContains("/order"));
    }

    public void clickBottomOrderButton() {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();",
                driver.findElement(bottomOrderButton)
        );
        driver.findElement(bottomOrderButton).click();
        wait.until(ExpectedConditions.urlContains("/order"));
    }

    // ===== доп. сценарии =====
    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
        wait.until(ExpectedConditions.urlToBe("https://qa-scooter.praktikum-services.ru/"));
    }

    public void clickYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    // открыть форму статуса заказа
    public void openStatusForm() {
        driver.findElement(statusButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberInput));
    }

    // ввести номер заказа
    public void enterOrderNumber(String number) {
        driver.findElement(orderNumberInput).sendKeys(number);
    }

    // нажать Go / Посмотреть
    public void submitOrderNumber() {
        driver.findElement(goButton).click();
    }

    // проверить, что показан блок "Такого заказа нет"
    public boolean isOrderNotFoundBlockVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderNotFoundBlock));
        return driver.findElements(orderNotFoundBlock).size() > 0;
    }
}
