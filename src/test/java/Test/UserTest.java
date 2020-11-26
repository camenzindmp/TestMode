package Test;

import Data.UserGenerator;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class UserTest {

    @Test
    void successCase() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getAuthInfo();
        open("http://localhost:9999/api/system/users");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Success Text")).shouldBe(Condition.visible);
    }

    @Test
    void incorrectLogin() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getAuthInfo();
        open("http://localhost:9999/api/system/users");
        $("[class=input__control][name=login]").setValue("wrongLogin");
        $("[class=input__control][name=password]").setValue(authInfo.getPassword());
        $("[type=button]").click();
        $(byText("Ошибка ")).shouldBe(Condition.visible);
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void incorrectPassword() {
        UserGenerator.AuthInfo authInfo = UserGenerator.getAuthInfo();
        open("http://localhost:9999/api/system/users");
        $("[class=input__control][name=login]").setValue(authInfo.getLogin());
        $("[class=input__control][name=password]").setValue("wrongPassword");
        $("[type=button]").click();
        $(byText("Ошибка ")).shouldBe(Condition.visible);
        $(byText("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
//
//    @Test
//    void disabledUser() {
//        UserGenerator.AuthInfo authInfo = UserGenerator.getAuthInfo();
//        open("http://localhost:9999/api/system/users");
//    }
//
//    @Test
//    void userNotExists() {
//        UserGenerator.AuthInfo authInfo = UserGenerator.getAuthInfo();
//        open("http://localhost:9999/api/system/users");
//    }
}