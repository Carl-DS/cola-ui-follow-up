package com.colaui.system.controller;

import com.colaui.example.model.ColaRole;
import com.colaui.provider.Page;
import com.colaui.system.service.ColaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("frame/role")
public class ColaRoleController {
    @Autowired
    private ColaRoleService roleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaRole> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        String containDecode = null;
        if (null != contain) {
            try {
                // 对前台使用的encodeURI() 进行解码
                containDecode = URLDecoder.decode(contain, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return roleService.getPage(pageSize, pageNo, containDecode);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaRole role) {
        roleService.save(role);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        roleService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaRole role) {
        roleService.update(role);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaRole find(@PathVariable("id") String id) {
        return roleService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaRole> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return roleService.find(from, limit);
    }
}