package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CourierCreateRequestValidationTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedMessage;

    private CourierClient courierClient;
    private int statusCode;

    public CourierCreateRequestValidationTest(Courier courier, int expectedStatus, String expectedMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithFirstNameOnly(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithLoginAndPasswordOnly(), 201, null},
        };
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (statusCode == 201) {
            courierClient.delete(courierClient.login(CourierCredentials.from(courier)).extract().path("id"));
        }
    }

    @Test
    @DisplayName("Check courier can not be created without login")
    public void courierCanNotBeCreatedWithoutLogin() {

        ValidatableResponse response = courierClient.create(courier);
        statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
    }
}