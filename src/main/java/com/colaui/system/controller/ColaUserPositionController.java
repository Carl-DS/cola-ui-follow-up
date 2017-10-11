package com.colaui.system.controller;

import com.colaui.system.model.ColaUserPosition;
import com.colaui.system.service.ColaUserPositionService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/userposition")
public class ColaUserPositionController {

    @Autowired
    private ColaUserPositionService userpositionService;

    @GetMapping
    public Page<ColaUserPosition> paging(@RequestParam int pageSize,
                                         @RequestParam int pageNo,
                                         @RequestParam(required = false) String contain) {
        return userpositionService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaUserPosition userposition) {
        userpositionService.save(userposition);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        userpositionService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaUserPosition userposition) {
        userpositionService.update(userposition);
    }

    @GetMapping("/{id}")
    public ColaUserPosition find(@PathVariable("id") long id) {
        return userpositionService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaUserPosition> find(@PathVariable("from") int from,
                                       @PathVariable("limit") int limit) {
        return userpositionService.find(from, limit);
    }
}