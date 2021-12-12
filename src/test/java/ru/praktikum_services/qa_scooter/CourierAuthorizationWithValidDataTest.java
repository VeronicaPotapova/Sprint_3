package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierAuthorizationWithValidDataTest {

    private CourierClient courierClient;
    Courier courier = Courier.getRandom();
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        ValidatableResponse courierCreated = courierClient.create(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check courier can be authorization with valid data")
    public void courierCanBeAuthorizationWithValidData() {
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));
        int statusCode = response.extract().statusCode();
        courierId = response.extract().path("id");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

}