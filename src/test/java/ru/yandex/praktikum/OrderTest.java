package ru.yandex.praktikum;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.pageobjects.MainPage;
import ru.yandex.praktikum.pageobjects.OrderPage;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private final int startButton;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderTest(int startButton,
                     String name,
                     String surname,
                     String address,
                     String metro,
                     String phone,
                     String date,
                     String rentalPeriod,
                     String color,
                     String comment) {
        this.startButton = startButton;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Кнопка {0}, {1} {2}")
    public static Object[][] getData() {
        return new Object[][]{
                {1, "Иван", "Иванов", "Москва, Толстого 1", "Спортивная", "89998887766", "03.11.2025", "сутки", "black", "позвонить"},
                {2, "Пётр", "Петров", "Москва, Арбат 10", "Черкизовская", "89997776655", "04.11.2025", "двое суток", "grey", "без звонка"}
        };
    }

    @Test
    public void orderScooterPositiveFlow() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        if (startButton == 1) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstStep(name, surname, address, metro, phone);
        orderPage.fillSecondStep(date, rentalPeriod, color, comment);
        orderPage.confirmOrder();

        Assert.assertTrue("Модалка об успешном заказе не появилась", orderPage.isOrderCreated());
    }
}
// В Chrome известный баг: окно "Заказ оформлен" не появляется — тест падает ожидаемо.