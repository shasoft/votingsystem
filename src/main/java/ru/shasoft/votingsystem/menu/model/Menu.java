package ru.shasoft.votingsystem.menu.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.shasoft.votingsystem.common.HasId;
import ru.shasoft.votingsystem.common.model.BaseEntity;
import ru.shasoft.votingsystem.menu.Dish;
import ru.shasoft.votingsystem.menu.DishesAttributeConverter;
import ru.shasoft.votingsystem.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "create_at"}, name = "uk_menu_restaurant_id_create_at")},
        indexes = @Index(name = "ik_menu_create_at_restaurant_id", columnList = "create_at, restaurant_id")
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity implements HasId {
// No session, no needs Serializable

    /*
    @Setter(AccessLevel.NONE)
    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;
     */

    @JoinColumn(name = "restaurant_id")
    @ManyToOne//(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "create_at", nullable = false)
    private LocalDate createAt;

    @Convert(converter = DishesAttributeConverter.class)
    @Column(name = "dishes", length = 1024)
    private List<Dish> dishes;

    @Column(name = "votes", nullable = false)
    private int votes;

    public Menu(Integer id, Restaurant restaurant, LocalDate createAt, List<Dish> dishes, int votes) {
        super(id);
        this.restaurant = restaurant;
        this.createAt = createAt;
        this.dishes = dishes;
        this.votes = votes;
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate createAt, List<Dish> dishes) {
        this(id, restaurant, createAt, dishes, 0);
    }

    public Menu(Menu r) {
        this(r.id, r.restaurant, r.createAt, r.dishes, r.votes);
    }

    @Override
    public String toString() {
        return "Menu(" + id + "): " + restaurant.id() + ", " + createAt.toString() + " [" + dishes.toString() + "]";
    }
}