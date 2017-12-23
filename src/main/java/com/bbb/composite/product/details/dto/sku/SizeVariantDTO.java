package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;

/**
 * SizeVariantDTO attributes specified here.
 *
 * @author psh111
 */
public class SizeVariantDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2022713816064698929L;
	private String sizeCode;
	private String displayName;
	private String description;
	
	public String getSizeCode() {
		return sizeCode;
	}
	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((description == null) ? 0 : description.hashCode());
		result = PRIME * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = PRIME * result + ((sizeCode == null) ? 0 : sizeCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SizeVariantDTO other = (SizeVariantDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (sizeCode == null) {
			if (other.sizeCode != null)
				return false;
		} else if (!sizeCode.equals(other.sizeCode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SizeVariantDTO [sizeCode=" + sizeCode + ", displayName=" + displayName + ", description=" + description
				+ "]";
	}

}
