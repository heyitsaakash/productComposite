package com.bbb.composite.product.details.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponseDTO implements Serializable {

	private static final long serialVersionUID = -5363242644049180308L;


	private String productId;
	

	private String[] siteId;

	private String collectionFlag;

	
	private String scene7Url;

	
	private String display_name;

	
	private String intl_restricted;

	
	private String incart_flag;

	
	private String[] attributes_json;

	
	private String seo_url;

	
	private String mx_pricing_label_code;
	// private String CHILD_PRODUCT;

	//private String EMAIL_OUT_OF_STOCK;

	
	private String shop_guide_id;

	public String getScene7Url() {
		return scene7Url;
	}

	public void setScene7Url(String scene7Url) {
		this.scene7Url = scene7Url;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getIntl_restricted() {
		return intl_restricted;
	}

	public void setIntl_restricted(String intl_restricted) {
		this.intl_restricted = intl_restricted;
	}

	public String getIncart_flag() {
		return incart_flag;
	}

	public void setIncart_flag(String incart_flag) {
		this.incart_flag = incart_flag;
	}

	public String[] getAttributes_json() {
		return attributes_json;
	}

	public void setAttributes_json(String[] attributes_json) {
		this.attributes_json = attributes_json;
	}

	public String getSeo_url() {
		return seo_url;
	}

	public void setSeo_url(String seo_url) {
		this.seo_url = seo_url;
	}

	public String getMx_pricing_label_code() {
		return mx_pricing_label_code;
	}

	public void setMx_pricing_label_code(String mx_pricing_label_code) {
		this.mx_pricing_label_code = mx_pricing_label_code;
	}

	public String getShop_guide_id() {
		return shop_guide_id;
	}

	public void setShop_guide_id(String shop_guide_id) {
		this.shop_guide_id = shop_guide_id;
	}

	public String getPrice_range_string() {
		return price_range_string;
	}

	public void setPrice_range_string(String price_range_string) {
		this.price_range_string = price_range_string;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSwatch_flag() {
		return swatch_flag;
	}

	public void setSwatch_flag(String swatch_flag) {
		this.swatch_flag = swatch_flag;
	}

	public String getHigh_price() {
		return high_price;
	}

	public void setHigh_price(String high_price) {
		this.high_price = high_price;
	}

	public String getPrice_range_descrip() {
		return price_range_descrip;
	}

	public void setPrice_range_descrip(String price_range_descrip) {
		this.price_range_descrip = price_range_descrip;
	}

	public String getLong_description() {
		return long_description;
	}

	public void setLong_description(String long_description) {
		this.long_description = long_description;
	}

	public String getMx_incart_flag() {
		return mx_incart_flag;
	}

	public void setMx_incart_flag(String mx_incart_flag) {
		this.mx_incart_flag = mx_incart_flag;
	}

	public String getReviews() {
		return reviews;
	}

	public void setReviews(String reviews) {
		this.reviews = reviews;
	}

	public String[] getAlt_images() {
		return alt_images;
	}

	public void setAlt_images(String[] alt_images) {
		this.alt_images = alt_images;
	}

	public String getLow_price() {
		return low_price;
	}

	public void setLow_price(String low_price) {
		this.low_price = low_price;
	}

	// private String[] PROD_ATTRIBUTES;
	@JsonProperty("PRICE_RANGE_STRING")
	private String price_range_string;

	//private String[] CATEGORY_HIERARCHY;


	@JsonProperty("DESCRIPTION")
	private String description;

	//private String BRAND_DESCRIP;

	// private String SITE_NUM;

	//  private String[] RECORD_IDENTIFIER;

	@JsonProperty("SWATCH_FLAG")
	private String swatch_flag;

	@JsonProperty("HIGH_PRICE")
	private String high_price;

	@JsonProperty("PRICE_RANGE_DESCRIP")
	private String price_range_descrip;

	@JsonProperty("LONG_DESCRIPTION")
	private String long_description;

	@JsonProperty("MX_INCART_FLAG")
	private String mx_incart_flag;

	@JsonProperty("REVIEWS")
	private String reviews;

	@JsonProperty("ALT_IMAGES")
	private String[] alt_images;

	@JsonProperty("LOW_PRICE")
	private String low_price;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String[] getSiteId() {
		return siteId;
	}

	public void setSiteId(String[] siteId) {
		this.siteId = siteId;
	}

	public String getCollectionFlag() {
		return collectionFlag;
	}

	public void setCollectionFlag(String collectionFlag) {
		this.collectionFlag = collectionFlag;
	}
}