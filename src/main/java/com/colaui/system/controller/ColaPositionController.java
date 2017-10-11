package com.colaui.system.controller;

import com.colaui.system.model.ColaPosition;
import com.colaui.helper.Page;
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

    @GetMapping
    public Page<ColaPosition> paging(@RequestParam int pageSize,
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
        return positionService.getPage(pageSize, pageNo, containDecode);
    }

    @GetMapping("/grouppositions")
    public Page<ColaPosition> groupPositions(@RequestParam int pageSize,
                                             @RequestParam int pageNo,
                                             @RequestParam(required = false) String groupId) {
        return positionService.groupPositions(pageSize, pageNo, groupId);
    }

    @GetMapping("/rolepositions")
    public Page<ColaPosition> rolePositions(@RequestParam int pageSize,
                                            @RequestParam int pageNo,
                                            @RequestParam(required = false) String roleId) {
        return positionService.rolePositions(pageSize, pageNo, roleId);
    }

    @PostMapping
    public void save(@RequestBody ColaPosition position) {
        positionService.save(position);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        positionService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaPosition position) {
        positionService.update(position);
    }

    @GetMapping("/{id}")
    public ColaPosition find(@PathVariable("id") long id) {
        return positionService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaPosition> find(@PathVariable("from") int from,
                                   @PathVariable("limit") int limit) {
        return positionService.find(from, limit);
    }
}