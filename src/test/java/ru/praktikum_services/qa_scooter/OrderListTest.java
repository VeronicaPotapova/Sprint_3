package ru.praktikum_services.qa_scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;


public class OrderListTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }


    @Test
    @DisplayName("Check orders list can be returned")
    public void ordersListCanBeReturned() {
        ValidatableResponse response = orderClient.returnOrdersList();
        int statusCode = response.extract().statusCode();
        String ordersList = response.extract().body().asString();

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Orders list is empty or contains incorrect data", ordersList.contains("id"));

    }

}