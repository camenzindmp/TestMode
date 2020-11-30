package data;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class UserGenerator {
    private static Faker faker = new Faker(new Locale("en-GB"));

    private UserGenerator() {
    }

    public static String generatePassword() { // метод генерации пароля (случайная последовательность символов);
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String randomPassword = fakeValuesService.bothify("???????");
        return randomPassword;
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void registerUser(AuthInfo authInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(authInfo) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Data
    @AllArgsConstructor
    public static class AuthInfo {
        private String login;
        private String password;
        private String status;
    }

    public static AuthInfo getCorrectAuthInfo() { //OK!
        AuthInfo user = new AuthInfo(faker.name().firstName().toLowerCase(), generatePassword(), "active");
        registerUser(user);
        return user;
    }

    public static AuthInfo getInvalidLoginAuthInfo() { //OK!
        return new AuthInfo(
                "badLogin",
                generatePassword(),
                "active"
        );
    }

    public static AuthInfo getInvalidPasswordAuthInfo() {
        String login = faker.name().firstName().toLowerCase();
        registerUser(new AuthInfo(login, generatePassword(), "active"));
        return new AuthInfo(login, generatePassword(), "active");
    }

    public static AuthInfo getBlockedUserAuthInfo() {
        return new AuthInfo(
                faker.name().firstName().toLowerCase(),
                generatePassword(),
                "blocked"
        );
    }

//    public static AuthInfo getUnregisteredUserAuthInfo() {
//        return new AuthInfo(
//                faker.name().firstName().toLowerCase(),
//                generatePassword(),
//                "active"
//        );
}




