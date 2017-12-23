package com.bbb.composite.product.details.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;

import com.bbb.core.dto.ErrorMessage;
import com.bbb.core.dto.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Wrapper for the product  providing one top level nesting
 * @author Aakash Sharma
 * @param <T> the generic type
 */
public class ProductWrapper<T> implements Serializable {
	

	/** The data. */
	@SuppressWarnings("squid:S1948")
	@JsonProperty(value="response")
	private T data;


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

/**
	 * Sets the data.
	 * 	 *
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
	}

@Override
public String toString() {
	return "ProductWrapperSolrDTO [data=" + data + "]";
}
	


}
