package com.colaui.example.controller;

import com.colaui.example.model.ColaProduct;
import com.colaui.example.service.ColaProductService;
import com.colaui.helper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ColaProductController {

	@Autowired
	private ColaProductService productService;


	@GetMapping
	public Page<ColaProduct> queryOfCategoryId(@RequestParam int pageSize, @RequestParam int pageNo,
											   @RequestParam(required = false) Long categoryId,
											   @RequestParam(required = false) String sort) {
		return productService.queryOfCategoryId(pageSize, pageNo, categoryId, sort);
	}

	@PostMapping
	public void save(@RequestBody ColaProduct product) {
		productService.save(product);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") long id) {
		 productService.delete(id);
	}

	@PutMapping
	public void update(@RequestBody ColaProduct product) {
		productService.update(product);
	}

	@GetMapping("/{id}")
	public ColaProduct find(@PathVariable("id") long id) {
		return productService.find(id);
	}

	@GetMapping("/{from}/{limit}")
	public List<ColaProduct> find(@PathVariable("from") int from, @PathVariable("limit") int limit) {
		return productService.find(from, limit);
	}
}
