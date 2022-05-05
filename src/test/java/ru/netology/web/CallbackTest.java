package ru.netology.web;

import static com.codeborne.selenide.Condition.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import com.codeborne.selenide.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.function.BooleanSupplier;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CallbackTest {

    @BeforeEach
    public void startBrowser(){
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSuccessSend() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $("[data-test-id=\"order-success\"]").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.".trim()));

    }

    @Test
    public void shouldInvalidNameValidPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий!");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"]")).shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }

    @Test
    public void shouldInvalidNameInvalidPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий!");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+791553A77834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }

    @Test
    public void shouldValidNameInvalidPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+791553A77834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'] .input__sub")).shouldHave(text("Укажите точно как в паспорте".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }

    @Test
    public void shouldValidNameValidPhoneCheckBoxFalse() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'] .input__sub")).shouldHave(text("Укажите точно как в паспорте".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"].input_invalid .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(255, 92, 92, 1)"));

    }

    @Test
    public void shouldNullNameInvalidPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("+79155377834");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).shouldHave(text("Поле обязательно для заполнения".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }

    @Test
    public void shouldValidNameNullPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("Дмитрий Великий-Невеликий");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'] .input__sub")).shouldHave(text("Укажите точно как в паспорте".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"].input_invalid .input__sub")).shouldHave(text("Поле обязательно для заполнения".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }

    @Test
    public void shouldNullNameNullPhoneCheckBoxTrue() {
        $(By.cssSelector(".input__control[type=\"text\"]")).setValue("");
        $(By.cssSelector(".input__control[type=\"tel\"]")).setValue("");
        $(By.cssSelector("[data-test-id=\"agreement\"]")).click();
        $(By.cssSelector("button")).click();
        $(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).shouldHave(text("Поле обязательно для заполнения".trim()));
        $(By.cssSelector("[data-test-id=\"phone\"] .input__sub")).shouldHave(text("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно.".trim()));
        $(By.cssSelector("[data-test-id=\"agreement\"] .checkbox__text")).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй".trim()));
        Assertions.assertTrue($(By.cssSelector("[data-test-id=\"agreement\"]")).getCssValue("color").equals("rgba(11, 31, 53, 0.95)"));

    }
}
