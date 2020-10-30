import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*
import static io.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*

public class UserGenerator {
 private static RequestSpecification requestSpec = new RequestSpecBuilder()
         .setBaseUri("http://localhost")
         .setPort(9999)
         .setAccept(ContentType.JSON)
         .setContentType(ContentType.JSON)
         .log(LogDetail.ALL)
         .build();

 @BeforeAll
 static void setUpAll() {
  // сам запрос
  given()
          .spec(requestSpec) // указываем, какую спецификацию используем
          .body(new RegistrationDto("vasya", "password", "active")) // передаём в теле объект, который будет преобразован в JSON
          .when() // "когда"
          .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
          .then() // "тогда ожидаем"
          .statusCode(200); // код 200 OK
 }
    ...
}

// java -jar app-ibank.jar -P:profile=test