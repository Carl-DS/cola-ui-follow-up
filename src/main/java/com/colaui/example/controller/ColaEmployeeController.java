package com.colaui.example.controller;

import com.colaui.example.model.ColaEmployee;
import com.colaui.example.service.ColaEmployeeService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class ColaEmployeeController {

    @Autowired
    private ColaEmployeeService employeeService;

    @GetMapping
    public Page<ColaEmployee> paging(@RequestParam int pageSize,
                                     @RequestParam int pageNo, 
                                     @RequestParam(required = false) String contain) {
        return employeeService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaEmployee employee) {
        employeeService.save(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        employeeService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaEmployee employee) {
        employeeService.update(employee);
    }

    @GetMapping("/{id}")
    public ColaEmployee find(@PathVariable("id") long id) {
        return employeeService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaEmployee> find(@PathVariable("from") int from,
                                   @PathVariable("limit") int limit) {
        return employeeService.find(from, limit);
    }
}
