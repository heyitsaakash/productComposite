package com.bbb.composite.product.details.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.bbb.composite.product.details.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * ProductDTO attributes specified here
 * 
 * @author ash262
 *
 */

public class ProductResponse implements Serializable{

	private static final long serialVersionUID = -6041000584001833480L;

	private String start;

	@JsonProperty("docs")
	private List<Product> productsSolr;

	private String numFound;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getNumFound() {
		return numFound;
	}

	public void setNumFound(String numFound) {
		this.numFound = numFound;
	}

	public List<Product> getProducts() {
		return productsSolr;
	}

	public void setProducts(List<Product> products) {
		this.productsSolr = products;
	}
}
