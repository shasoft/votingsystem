package ru.shasoft.votingsystem.menu;

import ru.shasoft.votingsystem.MatcherFactory;
import ru.shasoft.votingsystem.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static ru.shasoft.votingsystem.restaurant.RestaurantTestData.RESTAURANT_ID_2;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class);

    public static final int MENU_ID_1 = 1;
    public static final int MENU_ID_2 = 2;
    public static final int MENU_ID_3 = 3;
    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish("суп", 1000);
    public static final Dish dish2 = new Dish("компот", 100);
    public static final Dish dish3 = new Dish("хлеб", 15);

    public static final LocalDate date2024_11_05 = LocalDate.of(2024, 11, 5);
    public static final LocalDate date2024_11_04 = LocalDate.of(2024, 11, 4);

    public static final Menu menu1 = new Menu(MENU_ID_1, RESTAURANT_ID_1, date2024_11_05, List.of(dish1, dish2));
    public static final Menu menu2 = new Menu(MENU_ID_2, RESTAURANT_ID_1, date2024_11_04, List.of(dish2, dish3));
    public static final Menu menu3 = new Menu(MENU_ID_3, RESTAURANT_ID_2, date2024_11_05, List.of(dish3, dish1));

    public static Menu getNew() {
        return new Menu(null, RESTAURANT_ID_1, LocalDate.now(), List.of(dish1));
    }

    public static Menu getUpdated() {
        return new Menu(MENU_ID_1, RESTAURANT_ID_1, LocalDate.now(), List.of(dish1, dish2));
    }
}
