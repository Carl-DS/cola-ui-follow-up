package com.colaui.example.controller;

import com.colaui.example.model.ColaCategory;
import com.colaui.example.service.ColaCategoryService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class ColaCategoryController {
	@Autowired
	ColaCategoryService categoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Page<ColaCategory> paging(@RequestParam int pageSize,
									 @RequestParam int pageNo,
									 @RequestParam(required=false) String contain) {
		return categoryService.getPage(pageSize, pageNo, contain);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void save(@RequestBody ColaCategory category) {
		System.out.println(category.getCategoryName());
		categoryService.save(category);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting ColaEmployee with id " + id);
		 categoryService.delete(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public void update(@RequestBody ColaCategory category) {
		System.out.println(category.getCategoryName());
		categoryService.update(category);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public ColaCategory find(@PathVariable("id") long id) {
		return categoryService.find(id);
	}

	@RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
	public List<ColaCategory> find(@PathVariable("from") int from, @PathVariable("limit") int limit) {
		return categoryService.find(from, limit);
	}
}
