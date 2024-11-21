package ru.shasoft.votingsystem.menu.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shasoft.votingsystem.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping(value = MenuController.REST_URL)
public class MenuController extends AbstractMenuController {

    static final String REST_URL = "/api/menus";

    @GetMapping("/by-date")
    public List<Menu> byDate(@RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return repository.getByDate(date);
    }

    @GetMapping("/by-restaurant")
    public List<Menu> byRestaurant(@RequestParam int restaurantId) {
        return repository.getByRestaurant(restaurantId);
    }
}