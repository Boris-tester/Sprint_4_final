package ru.yandex.praktikum.pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ===== ШАГ 1 =====
    private final By firstNameField = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // ===== ШАГ 2 =====
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalDropdown = By.xpath("//div[contains(@class,'Dropdown-placeholder')]");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Заказать' and contains(@class,'Button_Middle')]");

    // ===== МОДАЛКИ =====
    // сама модалка "Хотите оформить заказ?"
    private final By confirmModal = By.xpath("//div[contains(@class,'Order_Modal')][.//div[contains(text(),'Хотите оформить заказ?')]]");
    // кнопка "Да"
    private final By yesButton = By.xpath("//button[text()='Да']");
    // модалка об успехе
    private final By successModal = By.xpath("//*[contains(text(),'Заказ оформлен')]");

    // ===== Ошибки на первом шаге =====
    private final By firstNameError = By.xpath("//div[contains(@class,'Input_ErrorMessage') and text()='Введите корректное имя']");
    private final By lastNameError = By.xpath("//div[contains(@class,'Input_ErrorMessage') and text()='Введите корректную фамилию']");
    private final By metroError = By.xpath("//div[contains(@class,'Order_MetroError') and text()='Выберите станцию']");
    private final By phoneError = By.xpath("//div[contains(@class,'Input_ErrorMessage') and text()='Введите корректный номер']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // заполнить первый шаг
    public void fillFirstStep(String name, String surname, String address,
                              String metro, String phone) {

        // ждём, что мы на странице /order
        wait.until(ExpectedConditions.urlContains("/order"));

        driver.findElement(firstNameField).sendKeys(name);
        driver.findElement(lastNameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        // метро: кликнули, ввели, выбрали
        WebElement metroInput = driver.findElement(metroField);
        metroInput.click();
        metroInput.sendKeys(metro);
        metroInput.sendKeys(Keys.ARROW_DOWN);
        metroInput.sendKeys(Keys.ENTER);

        // телефон — дождались и ввели
        WebElement phoneInput = wait.until(
                ExpectedConditions.elementToBeClickable(phoneField));
        phoneInput.click();
        phoneInput.sendKeys(phone);

        // кнопка "Далее"
        driver.findElement(nextButton).click();
    }

    // метод для проверки валидации - жмём "Далее" без заполнения полей
    public void clickNextOnFirstStep() {
        wait.until(ExpectedConditions.urlContains("/order"));
        driver.findElement(nextButton).click();
    }

    // заполнить второй шаг
    public void fillSecondStep(String date, String rentalPeriod, String color, String comment) {
        // дата
        WebElement dateInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(dateField));
        dateInput.click();
        dateInput.clear();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        // срок аренды
        driver.findElement(rentalDropdown).click();
        By option = By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + rentalPeriod + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();

        // цвет
        if (color != null && !color.isEmpty()) {
            driver.findElement(By.id(color)).click();
        }

        // комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentField).sendKeys(comment);
        }

        // "Заказать" на втором шаге
        driver.findElement(orderButton).click();
    }

    // геттеры ошибок
    public boolean isFirstNameErrorVisible() {
        return driver.findElements(firstNameError).size() > 0;
    }

    public boolean isLastNameErrorVisible() {
        return driver.findElements(lastNameError).size() > 0;
    }

    public boolean isMetroErrorVisible() {
        return driver.findElements(metroError).size() > 0;
    }

    public boolean isPhoneErrorVisible() {
        return driver.findElements(phoneError).size() > 0;
    }

    // нажать "Да" в модалке подтверждения
    public void confirmOrder() {
        // сначала ждём, что модалка вообще появилась
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmModal));
        // потом уже жмём "Да"
        wait.until(ExpectedConditions.elementToBeClickable(yesButton)).click();
    }

    // проверить, что заказ оформлен
    public boolean isOrderCreated() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successModal));
        return true;
    }
}
