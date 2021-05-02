package com.java.momentum.dailypoints.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Document (collection = "MomentumProduct")
public class MomentumProduct {

	@Transient
    public static final String SEQUENCE_NAME = "product_sequence";

	@Id
	private long id;

	@NotBlank
    @Size(max=100)
    @Indexed(unique=true)
	private String productDescription;

	@Positive
	private int productPointCost;

	public MomentumProduct() {

	}

	public MomentumProduct(String firstName, String productDescription, int productPointCost) {
		this.productDescription = productDescription;
		this.productPointCost = productPointCost;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public int getProductPointCost() {
		return productPointCost;
	}

	public void setProductPointCost(int productPointCost) {
		this.productPointCost = productPointCost;
	}

	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productDescription=" + productDescription + ", productCost=" + productPointCost
				+ "]";
	}	
}
