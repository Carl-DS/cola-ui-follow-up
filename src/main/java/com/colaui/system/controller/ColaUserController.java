package com.colaui.system.controller;

import com.colaui.system.model.ColaUser;
import com.colaui.helper.Page;
import com.colaui.system.service.ColaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by carl.li on 2017/3/3.
 */
@RestController
@RequestMapping("/frame/user")
public class ColaUserController {
    @Autowired
    private ColaUserService colaUserService;

    @GetMapping
    public Page<ColaUser> paging(@RequestParam int pageSize,
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
        return colaUserService.getPage(pageSize, pageNo, containDecode);
    }

    @PostMapping
    public void save(@RequestBody ColaUser colaUser) {
        colaUserService.save(colaUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        colaUserService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaUser colaUser) {
        colaUserService.update(colaUser);
    }

    @GetMapping("/{id}")
    public ColaUser find(@PathVariable("id") String id) {
        return colaUserService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaUser> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return colaUserService.find(from, limit);
    }

    @GetMapping("/groupusers")
    public Page<ColaUser> groupUsers(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam String groupId) {
        return colaUserService.groupUsers(pageSize, pageNo, groupId);
    }

    @GetMapping("/roleusers")
    public Page<ColaUser> roleUsers(@RequestParam int pageSize,
                                    @RequestParam int pageNo,
                                    @RequestParam String roleId) {
        return colaUserService.roleUsers(pageSize, pageNo, roleId);
    }

    @GetMapping("/check")
    public boolean check(@RequestParam String username) {
        return colaUserService.check(username);
    }
}
