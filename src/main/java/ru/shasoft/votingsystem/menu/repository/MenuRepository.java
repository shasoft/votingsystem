package ru.shasoft.votingsystem.menu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.shasoft.votingsystem.common.BaseRepository;
import ru.shasoft.votingsystem.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {
    @Query("SELECT m FROM Menu m WHERE m.cookingAt = :cookingAt")
    List<Menu> getByDate(LocalDate cookingAt);

    @Query("SELECT m FROM Menu m WHERE m.restaurantId = :restaurantId")
    List<Menu> getByRestaurant(int restaurantId);
}