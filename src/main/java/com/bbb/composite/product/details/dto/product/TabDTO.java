package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;

/**
 * TabDTO attributes specified here
 * 
 * @author psh111
 *
 */
public class TabDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6699530210163080681L;

	private String tabName;
	
	private String tabContent;

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabContent() {
		return tabContent;
	}

	public void setTabContent(String tabContent) {
		this.tabContent = tabContent;
	}
	
	
}
