package ru.shasoft.votingsystem.menu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shasoft.votingsystem.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL)
// TODO: cache only most requested, seldom changed data!
public class MenuController extends AbstractMenuController {

    static final String REST_URL = "/api/menu";

    @GetMapping
    public List<Menu> today() {
        final LocalDate now = LocalDate.now();
        return repository.getByDate(now, now);
    }
}