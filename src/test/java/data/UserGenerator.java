package data;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.github.javafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class UserGenerator {

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

    public static void createUser(AuthInfo authInfo) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body() // передаём в теле объект, который будет преобразован в JSON
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

    public static AuthInfo getCorrectAuthInfo() {
        Faker faker = new Faker(new Locale("en-GB"));
        return new AuthInfo(
                faker.name().firstName().toLowerCase(),
                generatePassword(),
                "active"
        );
    }

    public static AuthInfo getInvalidLoginAuthInfo() {
        return new AuthInfo(
                "badLogin",
                generatePassword(),
                "active"
        );
    }

    public static AuthInfo getInvalidPasswordAuthInfo() {
        Faker faker = new Faker(new Locale("en-GB"));
        return new AuthInfo(
                faker.name().firstName().toLowerCase(),
                "badPassword",
                "active"
        );
    }

    public static AuthInfo getBlockedUserAuthInfo() {
        Faker faker = new Faker(new Locale("en-GB"));
        return new AuthInfo(
                faker.name().firstName().toLowerCase(),
                generatePassword(),
                "blocked"
        );
    }

//    public static AuthInfo getUnregisteredUserAuthInfo() {
//        Faker faker = new Faker(new Locale("en-GB"));
//        return new AuthInfo(
//                faker.name().firstName().toLowerCase(),
//                generatePassword(),
//                "active"
//        );
    }
}



