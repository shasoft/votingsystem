package ru.shasoft.votingsystem.restaurant;

import ru.shasoft.votingsystem.MatcherFactory;
import ru.shasoft.votingsystem.restaurant.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "registered", "password");

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;
    public static final int NOT_FOUND = 100;
    public static final String RESTAURANT_NAME_1 = "Ил Фаро";
    public static final String RESTAURANT_NAME_2 = "Чайка";
    public static final String RESTAURANT_NAME_3 = "Волга-Волга";

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID_1, RESTAURANT_NAME_1);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID_2, RESTAURANT_NAME_2);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID_3, RESTAURANT_NAME_3);

    public static Restaurant getNew() {
        return new Restaurant(null, "Хотдоги");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID_1, "Шаурмячная");
    }
}
