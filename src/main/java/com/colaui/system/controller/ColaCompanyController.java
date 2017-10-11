package com.colaui.system.controller;

import com.colaui.system.model.ColaCompany;
import com.colaui.system.service.ColaCompanyService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frame/company")
public class ColaCompanyController {

    @Autowired
    private ColaCompanyService companyService;

    @GetMapping
    public Page<ColaCompany> paging(@RequestParam int pageSize,
                                    @RequestParam int pageNo,
                                    @RequestParam(required = false) String contain) {
        return companyService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaCompany company) {
        companyService.save(company);
    }

    @DeleteMapping("/{id}/")
    public void delete(@PathVariable("id") long id) {
        companyService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaCompany company) {
        companyService.update(company);
    }

    @GetMapping("/{id}/")
    public ColaCompany find(@PathVariable("id") long id) {
        return companyService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaCompany> find(@PathVariable("from") int from,
                                  @PathVariable("limit") int limit) {
        return companyService.find(from, limit);
    }
}