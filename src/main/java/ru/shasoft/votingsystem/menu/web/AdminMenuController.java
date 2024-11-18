package ru.shasoft.votingsystem.menu.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.shasoft.votingsystem.menu.model.Menu;

import java.net.URI;
import java.util.List;

import static ru.shasoft.votingsystem.common.validation.ValidationUtil.assureIdConsistent;
import static ru.shasoft.votingsystem.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController extends AbstractMenuController {

    static final String REST_URL = "/api/admin/menu";

    @Override
    @GetMapping("/{id}")
    public Menu get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<Menu> getAll() {
        return super.getAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu) {
        log.info("create {}", menu);
        checkNew(menu);
        Menu created = repository.save(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int id) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        repository.save(menu);
    }
}