package test;

import data.UserGenerator;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static data.UserGenerator.*;

public class UserTest {

    @Test
    void successCase() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getCorrectAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $("[class=App_appContainer__3jRx1]").shouldHave(text("Личный кабинет"));
    }

    @Test
    void invalidLogin() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getInvalidLoginAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void invalidPassword() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getInvalidPasswordAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void blockedUser() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getBlockedUserAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
        // Проверка на выполнение перезаписи данных пользователя(пароль) при повторной передаче пользователя в запросе с тем же логином:
    void rewritedUser() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getRewritedUser();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $("[class=App_appContainer__3jRx1]").shouldHave(text("Личный кабинет"));
    }
}