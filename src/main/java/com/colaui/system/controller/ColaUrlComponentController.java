package com.colaui.system.controller;

import com.colaui.example.model.ColaUrlComponent;
import com.colaui.system.service.ColaUrlComponentService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/urlcomponent")
public class ColaUrlComponentController {
    @Autowired
    private ColaUrlComponentService urlcomponentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaUrlComponent> paging(@RequestParam int pageSize,
                                         @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return urlcomponentService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaUrlComponent urlcomponent) {
        urlcomponentService.save(urlcomponent);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        urlcomponentService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaUrlComponent urlcomponent) {
        urlcomponentService.update(urlcomponent);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaUrlComponent find(@PathVariable("id") long id) {
        return urlcomponentService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaUrlComponent> find(@PathVariable("from") int from,
                                       @PathVariable("limit") int limit) {
        return urlcomponentService.find(from, limit);
    }
}