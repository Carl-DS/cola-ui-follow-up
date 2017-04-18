package com.colaui.system.controller;

import com.colaui.example.model.ColaMessageTemplate;
import com.colaui.provider.Page;
import com.colaui.system.service.ColaMessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/frame/messageTemplate")
public class ColaMessageTemplateController {
    @Autowired
    private ColaMessageTemplateService messageTemplateService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaMessageTemplate> paging(@RequestParam int pageSize,
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
        return messageTemplateService.getPage(pageSize, pageNo, containDecode);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaMessageTemplate messagetemplate) {
        messageTemplateService.save(messagetemplate);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
        messageTemplateService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaMessageTemplate messagetemplate) {
        messageTemplateService.update(messagetemplate);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaMessageTemplate find(@PathVariable("id") String id) {
        return messageTemplateService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaMessageTemplate> find(@PathVariable("from") int from,
                                          @PathVariable("limit") int limit) {
        return messageTemplateService.find(from, limit);
    }
}