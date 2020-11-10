import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*
import static io.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*

public class UserGenerator {
    private UserGenerator() {
    }

    @Data
    @AllArgsConstructor
    public static class RegistrationDto {
        String name;
        String password;
        String status;
    }

    private static getRegistrationDto RegistrationDto() {
         return new RegistrationDto();
    }
}

// java -jar app-ibank.jar -P:profile=test