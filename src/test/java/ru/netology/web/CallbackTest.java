package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import com.codeborne.selenide.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CallbackTest {

    @Test
    public void shouldSuccessSend() {
        open("http://localhost:9999/");
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $("[data-test-id=\"order-success\"]").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim()));

    }

    @Test
    public void shouldInvalidNameValidPhone() {
        open("http://localhost:9999/");
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("!Дмитрий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id=\"name\"]")).shouldHave(cssClass("input_invalid"));
        $(By.cssSelector("[data-test-id=\"phone\"]")).shouldNotHave(cssClass("input_invalid"));

    }

    @Test
    public void shouldInvalidNameInvalidPhone() {
        open("http://localhost:9999/");
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("!Дмитрий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155Д377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id=\"name\"]")).shouldHave(cssClass("input_invalid"));
        $(By.cssSelector("[data-test-id=\"phone\"]")).shouldNotHave(cssClass("input_invalid"));

    }

    @Test
    public void shouldValidNameInvalidPhone() {
        open("http://localhost:9999/");
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155Д377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id=\"name\"]")).shouldNotHave(cssClass("input_invalid"));
        $(By.cssSelector("[data-test-id=\"phone\"]")).shouldHave(cssClass("input_invalid"));

    }
}
