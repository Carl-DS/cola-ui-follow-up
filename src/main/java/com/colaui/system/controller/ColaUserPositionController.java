package com.colaui.system.controller;

import com.colaui.example.model.ColaUserPosition;
import com.colaui.system.service.ColaUserPositionService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/userposition")
public class ColaUserPositionController {
    @Autowired
    private ColaUserPositionService userpositionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaUserPosition> paging(@RequestParam int pageSize,
                                         @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return userpositionService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaUserPosition userposition) {
        userpositionService.save(userposition);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        userpositionService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaUserPosition userposition) {
        userpositionService.update(userposition);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaUserPosition find(@PathVariable("id") long id) {
        return userpositionService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaUserPosition> find(@PathVariable("from") int from,
                                       @PathVariable("limit") int limit) {
        return userpositionService.find(from, limit);
    }
}