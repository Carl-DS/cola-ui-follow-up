package com.colaui.system.controller;

import com.colaui.example.model.ColaCompany;
import com.colaui.system.service.ColaCompanyService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/company")
public class ColaCompanyController {
    @Autowired
    private ColaCompanyService companyService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<ColaCompany> paging(@RequestParam int pageSize,
                                    @RequestParam int pageNo, @RequestParam(required=false) String contain) {
        return companyService.getPage(pageSize, pageNo, contain);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public void save(@RequestBody ColaCompany company) {
        companyService.save(company);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id) {
        companyService.delete(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public void update(@RequestBody ColaCompany company) {
        companyService.update(company);
    }

    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    public ColaCompany find(@PathVariable("id") long id) {
        return companyService.find(id);
    }

    @RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
    public List<ColaCompany> find(@PathVariable("from") int from,
                                  @PathVariable("limit") int limit) {
        return companyService.find(from, limit);
    }
}