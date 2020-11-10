import lombok.AllArgsConstructor;
import lombok.Data;

import static io.restassured.RestAssured.*
import static io.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*

public class UserGenerator {
    private UserGenerator() {
    }

    @Data
    @AllArgsConstructor
    public static class RegistrationDto {
        private String name;
        private String password;
        private String status;
    }

    private static RegistrationDto getRegistrationDto() {
         return new RegistrationDto("vasya","password","active");
    }
}

// java -jar app-ibank.jar -P:profile=test