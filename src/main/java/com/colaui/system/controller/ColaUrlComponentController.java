package com.colaui.system.controller;

import com.colaui.system.model.ColaUrlComponent;
import com.colaui.helper.Page;
import com.colaui.system.service.ColaUrlComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public void saveRoleUrlComponents(@RequestBody List<Map<String, Object>> urlComponents) {
        if (urlComponents == null || urlComponents.size() < 1) {
            return;
        } else {
            urlcomponentService.saveRoleUrlComponents(urlComponents);
        }
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