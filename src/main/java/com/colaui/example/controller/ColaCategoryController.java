package com.colaui.example.controller;

import com.colaui.example.model.ColaCategory;
import com.colaui.example.service.ColaCategoryService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class ColaCategoryController {

    @Autowired
    private ColaCategoryService categoryService;

    @GetMapping
    public Page<ColaCategory> paging(@RequestParam int pageSize,
                                     @RequestParam int pageNo,
                                     @RequestParam(required = false) String contain) {
        return categoryService.getPage(pageSize, pageNo, contain);
    }

    @PostMapping
    public void save(@RequestBody ColaCategory category) {
        System.out.println(category.getCategoryName());
        categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting ColaEmployee with id " + id);
        categoryService.delete(id);
    }

    @PutMapping
    public void update(@RequestBody ColaCategory category) {
        System.out.println(category.getCategoryName());
        categoryService.update(category);
    }

    @GetMapping("/{id}")
    public ColaCategory find(@PathVariable("id") long id) {
        return categoryService.find(id);
    }

    @GetMapping("/{from}/{limit}")
    public List<ColaCategory> find(@PathVariable("from") int from, @PathVariable("limit") int limit) {
        return categoryService.find(from, limit);
    }
}
