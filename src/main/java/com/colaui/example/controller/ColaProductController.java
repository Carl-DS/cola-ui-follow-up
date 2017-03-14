package com.colaui.example.controller;

import com.colaui.example.model.ColaProduct;
import com.colaui.example.service.ColaProductService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ColaProductController {
	@Autowired
	ColaProductService productService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Page<ColaProduct> queryOfCategoryId(@RequestParam int pageSize, @RequestParam int pageNo,
											   @RequestParam(required = false) Long categoryId,
											   @RequestParam(required = false) String sort) {
		return productService.queryOfCategoryId(pageSize, pageNo, categoryId, sort);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void save(@RequestBody ColaProduct product) {
		productService.save(product);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting ColaEmployee with id " + id);
		 productService.delete(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public void update(@RequestBody ColaProduct product) {
		productService.update(product);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public ColaProduct find(@PathVariable("id") long id) {
		return productService.find(id);
	}

	@RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
	public List<ColaProduct> find(@PathVariable("from") int from, @PathVariable("limit") int limit) {
		return productService.find(from, limit);
	}
}
