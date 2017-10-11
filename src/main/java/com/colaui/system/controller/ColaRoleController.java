package com.colaui.system.controller;

import com.colaui.system.model.ColaRole;
import com.colaui.helper.Page;
import com.colaui.system.service.ColaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/frame/role")
public class ColaRoleController {

    @Autowired
    private ColaRoleService roleService;

    @GetMapping
    public Page<ColaRole> paging(@RequestParam int pageSize,
                                 @RequestParam int pageNo,
                                 @RequestParam(required = false) String contain) {
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

    @PostMapping
    public void save(@RequestBody ColaRole role) {
        roleService.save(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        roleService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaRole role) {
        roleService.update(role);
    }

    @GetMapping("/{id}")
    public ColaRole find(@PathVariable("id") String id) {
        return roleService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaRole> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return roleService.find(from, limit);
    }
}