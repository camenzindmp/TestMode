package Data;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.github.javafaker.Faker;

import java.util.Locale;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

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

    static void createUser() {
        // сам запрос
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new AuthInfo(getAuthInfo().login, getAuthInfo().password, getAuthInfo().status) // передаём в теле объект, который будет преобразован в JSON
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

    public static AuthInfo getAuthInfo() {
        Faker faker = new Faker(new Locale("en-GB"));
        return new AuthInfo(
                faker.name().firstName(),
                generatePassword(),
                "active"
        );
    }
}

