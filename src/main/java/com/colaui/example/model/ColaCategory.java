package com.colaui.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "COLA_CATEGORY")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class ColaCategory implements Serializable {
	private static final long serialVersionUID = 6076304611179489256L;

	private long id;
	private ColaCategory parent;
	private String categoryName;
	private String description;
	private Collection<ColaCategory> categories;
	private Collection<ColaProduct> products;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public ColaCategory getParent() {
		return parent;
	}

	public void setParent(ColaCategory parent) {
		this.parent = parent;
	}

	@Column(name = "CATEGORY_NAME")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
	@JsonIgnore
	public Collection<ColaCategory> getCategories() {
		return categories;
	}

	public void setCategories(Collection<ColaCategory> categories) {
		this.categories = categories;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
	@JsonIgnore
	public Collection<ColaProduct> getProducts() {
		return products;
	}

	public void setProducts(Collection<ColaProduct> products) {
		this.products = products;
	}
}
