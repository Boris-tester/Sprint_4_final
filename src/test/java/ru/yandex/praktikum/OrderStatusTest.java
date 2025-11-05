package ru.yandex.praktikum;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.pageobjects.MainPage;

public class OrderStatusTest extends BaseTest {

    @Test
    public void shouldShowMessageWhenOrderNotFound() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        // открываем форму статуса в хедере
        mainPage.openStatusForm();
        // вводим заведомо левый номер
        mainPage.enterOrderNumber("9999999");
        // жмём кнопку
        mainPage.submitOrderNumber();

        // на /track должен появиться блок «Такого заказа нет»
        Assert.assertTrue("Не показали блок 'Такого заказа нет'", mainPage.isOrderNotFoundBlockVisible());
    }
}
