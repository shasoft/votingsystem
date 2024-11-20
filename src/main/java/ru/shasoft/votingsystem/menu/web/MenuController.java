package ru.shasoft.votingsystem.menu.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = MenuController.REST_URL)
public class MenuController extends AbstractMenuController {

    static final String REST_URL = "/api/menu";
/*
    @GetMapping("today")
    public List<Menu> today() {
        final LocalDate now = LocalDate.now();
        return repository.getByDate(now, now);
    }

    @GetMapping("weekly")
    public List<Menu> weekly() {
        final LocalDate now = LocalDate.now();
        return repository.getByDate(now, now.plusDays(6));
    }

 */
}