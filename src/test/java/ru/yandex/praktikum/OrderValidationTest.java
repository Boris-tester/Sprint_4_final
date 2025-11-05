package ru.yandex.praktikum;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.pageobjects.MainPage;
import ru.yandex.praktikum.pageobjects.OrderPage;

public class OrderValidationTest extends BaseTest {

    @Test
    public void shouldShowErrorsOnEmptyFirstStep() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();
        mainPage.clickTopOrderButton(); // открыли форму

        OrderPage orderPage = new OrderPage(driver);
        orderPage.clickNextOnFirstStep(); // жмём Далее без ввода данных

        Assert.assertTrue("Нет ошибки имени", orderPage.isFirstNameErrorVisible());
        Assert.assertTrue("Нет ошибки фамилии", orderPage.isLastNameErrorVisible());
        Assert.assertTrue("Нет ошибки станции", orderPage.isMetroErrorVisible());
        Assert.assertTrue("Нет ошибки телефона", orderPage.isPhoneErrorVisible());
    }
}
