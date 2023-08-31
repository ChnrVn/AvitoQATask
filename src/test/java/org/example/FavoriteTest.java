package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


class FavoriteTest {
    public static String TEST_URL = "https://www.avito.ru/nikel/knigi_i_zhurnaly/domain-driven_design_distilled_vaughn_vernon_2639542363";

    @Test
    public void addToFavListTest(){
        WebDriver driver = new ChromeDriver();

        driver.get(TEST_URL);

        //Поиск кнопки "Избранное"
        WebElement webElement = driver.findElements(
                        By.xpath("//button[@data-marker='item-view/favorite-button']"))
                .stream()
                .filter(el -> "desktop-usq1f1".equals(el.getAttribute("class")))
                .findFirst()
                .orElseThrow();


        webElement.click();

        //Дождаться пока кнопка сменит состояние
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.attributeToBe(
                        webElement,
                        "data-is-favorite",
                        "true"));

        driver.get("https://www.avito.ru/favorites");

        // Найти в списке "избранное" элемент с URL объявления, добавленного в ходе теста
        List<WebElement> references = driver.findElements(By.xpath(
                        "//div[@class='item-snippet-root-d2wFO']//div[@class='item-snippet-column-2-md2mY']//a"))
                .stream()
                .filter(webElement1-> TEST_URL.equals(webElement1.getAttribute("href")))
                .toList();

        driver.quit();

        Assertions.assertEquals(1, references.size());
        //Такое объявления должно быть ровно одно
    }
}