package ru.yandex.praktikum;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.praktikum.pageobjects.MainPage;

import java.util.ArrayList;

public class LogoTest extends BaseTest {

    @Test
    public void scooterLogoShouldOpenHome() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        mainPage.clickScooterLogo();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(
                "После клика по логотипу Самоката должна открыться главная Самоката",
                "https://qa-scooter.praktikum-services.ru/",
                currentUrl
        );
    }

    @Test
    public void yandexLogoShouldOpenYandexHome() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        // кликаем по логотипу Яндекса (он открывает новую вкладку)
        mainPage.clickYandexLogo();

        // переключаемся на новую вкладку
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        Assert.assertTrue("После клика по логотипу не открылось новое окно", tabs.size() > 1);
        driver.switchTo().window(tabs.get(1));

        String actualUrl = driver.getCurrentUrl();
        System.out.println("Открылось: " + actualUrl);

        // после клика на логотип Яндекса открывается не yandex.ru, а dzen.ru
        boolean openedYandex =
                actualUrl.contains("yandex.ru") ||
                        actualUrl.contains("ya.ru") ||
                        actualUrl.contains("dzen.ru") ||
                        actualUrl.contains("passport.yandex.ru");

        Assert.assertTrue("После клика по логотипу Яндекса не открылась страница Яндекса. Открылось: " + actualUrl, openedYandex);
    }
}
