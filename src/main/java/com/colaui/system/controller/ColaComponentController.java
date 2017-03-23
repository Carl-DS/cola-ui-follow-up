package com.colaui.system.controller;

import com.colaui.example.model.ColaComponent;
import com.colaui.system.service.ColaComponentService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/component")
public class ColaComponentController {
    @Autowired
    private ColaComponentService componentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaComponent> paging(@RequestParam int pageSize,
                                      @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return componentService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaComponent component) {
        componentService.save(component);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        componentService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaComponent component) {
        componentService.update(component);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaComponent find(@PathVariable("id") long id) {
        return componentService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaComponent> find(@PathVariable("from") int from,
                                    @PathVariable("limit") int limit) {
        return componentService.find(from, limit);
    }
}