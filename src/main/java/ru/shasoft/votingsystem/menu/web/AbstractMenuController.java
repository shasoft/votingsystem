package ru.shasoft.votingsystem.menu.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.shasoft.votingsystem.menu.model.Menu;
import ru.shasoft.votingsystem.menu.repository.MenuRepository;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractMenuController {
    protected final Logger log = getLogger(getClass());

    @Autowired
    protected MenuRepository repository;

    public Menu get(int id) {
        log.info("get {}", id);
        return repository.getExisted(id);
    }

    public List<Menu> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
}