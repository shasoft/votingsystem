package ru.shasoft.votingsystem.menu.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shasoft.votingsystem.common.HasId;
import ru.shasoft.votingsystem.common.model.BaseEntity;
import ru.shasoft.votingsystem.menu.DishesAttributeConverter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "cooking_at"}, name = "uk_menu_restaurant_id_create_at")},
        indexes = @Index(name = "ik_menu_create_at_restaurant_id", columnList = "cooking_at, restaurant_id")
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity implements HasId {


    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;

    @Column(name = "cooking_at", nullable = false)
    private LocalDate cookingAt;

    @Convert(converter = DishesAttributeConverter.class)
    @Column(name = "dishes", length = 256 * 256)
    private List<Dish> dishes;

    public Menu(Integer id, int restaurantId, LocalDate createAt, List<Dish> dishes) {
        super(id);
        this.restaurantId = restaurantId;
        this.cookingAt = createAt;
        this.dishes = dishes;
    }

    public Menu(Menu r) {
        this(r.id, r.restaurantId, r.cookingAt, r.dishes);
    }

    @Override
    public String toString() {
        return "Menu(" + id + "): " + restaurantId + ", " + cookingAt.toString() + ", [" + dishes.toString() + "]";
    }
}