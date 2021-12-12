package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    OrderClient orderClient = new OrderClient();

    private final String[] orderColor;
    private final int expectedStatus;

    private int trackOrder;

    public OrderCreateTest(String[] orderColor, int expectedStatus) {
        this.orderColor = orderColor;
        this.expectedStatus = expectedStatus;
    }

    @After
    public void tearDown() {
        orderClient.cancel(trackOrder);
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}, 201},
                {new String[]{"BLACK", "GREY"}, 201},
                {new String[]{"GREY"}, 201},
                {new String[]{}, 201},
        };
    }

    @Test
    @DisplayName("Check create order with color")
    public void shouldBeOrderCreatedWithColor() {
        Order order = Order.getRandomData(orderColor);

        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        trackOrder = response.extract().path("track");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Track order is incorrect", trackOrder, is(not(0)));
    }
}

