package ru.shasoft.votingsystem.menu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shasoft.votingsystem.common.HasId;
import ru.shasoft.votingsystem.common.model.BaseEntity;
import ru.shasoft.votingsystem.menu.Dish;
import ru.shasoft.votingsystem.menu.DishesAttributeConverter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity implements HasId {
// No session, no needs Serializable

    @Setter(AccessLevel.NONE)
    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Convert(converter = DishesAttributeConverter.class)
    @Column(name = "dishes", length = 1024)
    private List<Dish> dishes;

    public Menu(Integer id, Integer restaurantId, LocalDate createAt, List<Dish> dishes) {
        super(id);
        this.restaurantId = restaurantId;
        this.createAt = createAt;
        this.dishes = dishes;
    }

    public Menu(Menu r) {
        this(r.id, r.restaurantId, r.createAt, r.dishes);
    }

    @Override
    public String toString() {
        return "Menu:" + id + ", " + restaurantId + '[' + dishes.toString() + ']';
    }
}