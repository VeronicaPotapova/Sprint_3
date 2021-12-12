package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CourierNotAuthorizationWithErrorDataTest {

    private final CourierCredentials credentials;
    private final int expectedStatus;
    private final String expectedMessage;

    public CourierNotAuthorizationWithErrorDataTest(CourierCredentials credentials, int expectedStatus, String expectedMessage) {
        this.credentials = credentials;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {CourierCredentials.getWithLoginOnly(Courier.getWithLoginOnly()), 400, "Недостаточно данных для входа"},
                {CourierCredentials.getWithPasswordOnly(Courier.getWithPasswordOnly()), 400, "Недостаточно данных для входа"},
                {CourierCredentials.from(Courier.getWithLoginAndPasswordOnly()), 404, "Учетная запись не найдена"},
                {CourierCredentials.getWithoutData(), 404, "Учетная запись не найдена"},
        };
    }

    @Test
    @DisplayName("Check courier can not be authorization without data")
    public void courierCanNotBeAuthorizationWithErrorData() {
        ValidatableResponse response = new CourierClient().login(credentials);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
    }


}