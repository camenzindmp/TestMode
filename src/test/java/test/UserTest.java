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
//        createUser(UserGenerator.getCorrectAuthInfo());
        UserGenerator.createUser(UserGenerator.getCorrectAuthInfo());
        UserGenerator.AuthInfo authInfo = UserGenerator.getCorrectAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $("[class=App_appContainer__3jRx1]").shouldHave(text("Личный кабинет"));
    }

    @Test
    void incorrectLogin() {
//        createUser(UserGenerator.getCorrectAuthInfo());
        UserGenerator.AuthInfo authInfo = UserGenerator.getInvalidLoginAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void incorrectPassword() {
//        createUser(UserGenerator.getCorrectAuthInfo());
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
//        createUser(UserGenerator.getBlockedUserAuthInfo());
        UserGenerator.AuthInfo authInfo = UserGenerator.getBlockedUserAuthInfo();
        open("http://localhost:9999");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка")).shouldBe(Condition.visible);
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

//    @Test
//    void unregisteredUser() {
//        UserGenerator.AuthInfo authInfo = UserGenerator.getUnregisteredUserAuthInfo();
//        open("http://localhost:9999");
//        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
//        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
//        $("[type=button]").click();
//        $(byText("Ошибка")).shouldBe(Condition.visible);
//        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
//    }
}