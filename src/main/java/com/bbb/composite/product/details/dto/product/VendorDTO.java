package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;

/**
 * VendorDTO attributes specified here
 * 
 * @author psh111
 *
 */
public class VendorDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831289975809331997L;
	private String vendorName;
	private String vendorJS;
	private String vendorCSS;
	private String vendorMobileJS;
	private String vendorMobileCSS;
	private String apiURL;
	private String clientId;
	private String apiVersion;
	private String apiKey;
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorJS() {
		return vendorJS;
	}
	public void setVendorJS(String vendorJS) {
		this.vendorJS = vendorJS;
	}
	public String getVendorCSS() {
		return vendorCSS;
	}
	public void setVendorCSS(String vendorCSS) {
		this.vendorCSS = vendorCSS;
	}
	public String getVendorMobileJS() {
		return vendorMobileJS;
	}
	public void setVendorMobileJS(String vendorMobileJS) {
		this.vendorMobileJS = vendorMobileJS;
	}
	public String getVendorMobileCSS() {
		return vendorMobileCSS;
	}
	public void setVendorMobileCSS(String vendorMobileCSS) {
		this.vendorMobileCSS = vendorMobileCSS;
	}
	public String getApiURL() {
		return apiURL;
	}
	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}	
	
	
}
