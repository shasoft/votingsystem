package ru.shasoft.votingsystem.restaurant.web;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shasoft.votingsystem.restaurant.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api";

    @Override
    @GetMapping("/restaurant/{id}")
    @Cacheable(value = "restaurant", key = "#id")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/restaurants")
    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return super.getAll();
    }
}