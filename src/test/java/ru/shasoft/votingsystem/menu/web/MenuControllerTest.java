package ru.shasoft.votingsystem.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.shasoft.votingsystem.AbstractControllerTest;
import ru.shasoft.votingsystem.menu.model.Menu;
import ru.shasoft.votingsystem.menu.repository.MenuRepository;

import java.util.List;

import static ru.shasoft.votingsystem.menu.MenuTestData.date2024_11_04;
import static ru.shasoft.votingsystem.menu.MenuTestData.dateToday;
import static ru.shasoft.votingsystem.menu.web.MenuController.REST_URL;

class MenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MenuRepository repository;

    @Test
    void today() {
        List<Menu> menus = repository.getByDate(date2024_11_04, dateToday);
        int ff = 0;
    }
}