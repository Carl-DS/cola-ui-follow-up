package com.colaui.example.controller;

import com.colaui.example.model.Product;
import com.colaui.example.service.ProductService;
import com.colaui.provider.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
	@Autowired
	ProductService productService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Page<Product> queryOfCategoryId(@RequestParam int pageSize, @RequestParam int pageNo,
										   @RequestParam(required = false) Long categoryId,
										   @RequestParam(required = false) String sort) {
		return productService.queryOfCategoryId(pageSize, pageNo, categoryId, sort);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting Employee with id " + id);
		// productService.delete(id);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void save(@RequestBody Product product) {
		productService.save(product);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
	public void update(@RequestBody Product product) {
		productService.update(product);
	}

	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public Product find(long id) {
		return productService.find(id);
	}

	@RequestMapping(value = "/{from}/{limit}", method = RequestMethod.GET)
	public List<Product> find(@PathVariable("from") int from,
			@PathVariable("limit") int limit) {
		return productService.find(from, limit);
	}
}
