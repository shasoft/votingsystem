package ru.shasoft.votingsystem.menu;

public class Dish {
    public String name;
    public int price;

    public Dish() {
        name = "";
        price = 0;
    }

    public Dish(String name, int price) {
        this.name = name;
        this.price = price;
    }
}