package ru.shasoft.votingsystem.menu.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.shasoft.votingsystem.common.BaseRepository;
import ru.shasoft.votingsystem.menu.model.Menu;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

}