package com.colaui.system.controller;

import com.colaui.example.model.ColaPosition;
import com.colaui.provider.Page;
import com.colaui.system.service.ColaPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/frame/position")
public class ColaPositionController {
    @Autowired
    private ColaPositionService positionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaPosition> paging(@RequestParam int pageSize,
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
        return positionService.getPage(pageSize, pageNo, containDecode);
    }

    @RequestMapping(value = "/grouppositions", method = RequestMethod.GET)
    public Page<ColaPosition> groupPositions(@RequestParam int pageSize,
                                             @RequestParam int pageNo,
                                             @RequestParam(required=false) String groupId) {
        return positionService.groupPositions(pageSize, pageNo, groupId);
    }

    @RequestMapping(value = "/rolepositions", method = RequestMethod.GET)
    public Page<ColaPosition> rolePositions(@RequestParam int pageSize,
                                             @RequestParam int pageNo,
                                             @RequestParam(required=false) String roleId) {
        return positionService.rolePositions(pageSize, pageNo, roleId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaPosition position) {
        positionService.save(position);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        positionService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaPosition position) {
        positionService.update(position);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaPosition find(@PathVariable("id") long id) {
        return positionService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaPosition> find(@PathVariable("from") int from,
                                   @PathVariable("limit") int limit) {
        return positionService.find(from, limit);
    }
}