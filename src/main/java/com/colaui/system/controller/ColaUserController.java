package com.colaui.system.controller;

import com.colaui.example.model.ColaUser;
import com.colaui.provider.Page;
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
@RequestMapping("frame/user")
public class ColaUserController {
    @Autowired
    private ColaUserService colaUserService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
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

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaUser colaUser) {
        colaUserService.save(colaUser);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        colaUserService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaUser colaUser) {
        colaUserService.update(colaUser);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaUser find(@PathVariable("id") String id) {
        return colaUserService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaUser> find(@PathVariable("from") int from,
                               @PathVariable("limit") int limit) {
        return colaUserService.find(from, limit);
    }

    @RequestMapping(value = "/groupusers/", method = RequestMethod.GET)
    public Page<ColaUser> groupUsers(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam String groupId) {
        return colaUserService.groupUsers(pageSize, pageNo, groupId);
    }

    @RequestMapping(value="/check", method = RequestMethod.GET)
    public boolean check(@RequestParam String username) {
        return colaUserService.check(username);
    }
}
