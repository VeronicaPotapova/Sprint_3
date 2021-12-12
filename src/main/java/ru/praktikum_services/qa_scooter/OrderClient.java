package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders";

    @Step("get orders list")
    public ValidatableResponse returnOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("create order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();

    }

    @Step("cancel order")
    public ValidatableResponse cancel(int trackOrder) {
        return given()
                .spec(getBaseSpec())
                .body(trackOrder)
                .when()
                .put(ORDER_PATH + "/cancel")
                .then();
    }

}
