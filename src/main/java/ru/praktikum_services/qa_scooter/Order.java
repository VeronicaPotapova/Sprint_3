package ru.praktikum_services.qa_scooter;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Order {

    public String orderFirstName;
    public String orderLastName;
    public String orderAddress;
    public int orderMetroStation;
    public String orderPhone;
    public int orderRentTime;
    public String orderDeliveryDate;
    public String orderComment;
    public String[] orderColor;

    public Order(String orderFirstName, String orderLastName, String orderAddress, int orderMetroStation, String orderPhone, int orderRentTime, String orderDeliveryDate, String orderComment, String[] orderColor) {
        this.orderFirstName = orderFirstName;
        this.orderLastName = orderLastName;
        this.orderAddress = orderAddress;
        this.orderMetroStation = orderMetroStation;
        this.orderPhone = orderPhone;
        this.orderRentTime = orderRentTime;
        this.orderDeliveryDate = orderDeliveryDate;
        this.orderComment = orderComment;
        this.orderColor = orderColor;
    }

    public static Order getRandomData(String[] orderColor) {
        Faker faker = new Faker(new Locale("ru"));
        final String orderFirstName = faker.name().firstName();
        final String orderLastName = faker.name().firstName();
        final String orderAddress = faker.address().streetAddress();
        final int orderMetroStation = faker.number().numberBetween(1, 101);
        final String orderPhone = faker.phoneNumber().phoneNumber();
        final int orderRentTime = faker.number().numberBetween(1, 7);
        final String orderDeliveryDate = faker.date().future(11, TimeUnit.DAYS).toString();
        final String orderComment = faker.lorem().sentence();
        return new Order(orderFirstName, orderLastName, orderAddress, orderMetroStation, orderPhone, orderRentTime, orderDeliveryDate, orderComment, orderColor);
    }


}
