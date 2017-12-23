package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;
import java.util.Date;

/**
 * RebateDTO attributes specified here.
 *
 * @author psh111
 */
public class RebateDTO implements Serializable {

	private static final long serialVersionUID = -4326296184225869925L;

	private String description;
	private String rebateUrl;
	private Date startDate;
	private Date endDate;

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRebateUrl() {
		return rebateUrl;
	}


	public void setRebateUrl(String rebateUrl) {
		this.rebateUrl = rebateUrl;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
