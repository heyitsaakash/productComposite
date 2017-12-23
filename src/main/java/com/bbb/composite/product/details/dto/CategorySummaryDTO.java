/**
 * 
 */
package com.bbb.composite.product.details.dto;

import java.io.Serializable;

/**
 * CategorySummaryDTO attributes specified here.
 * 
 * @author nbisht
 *
 */
public class CategorySummaryDTO implements Serializable,Comparable<CategorySummaryDTO> {
	
	private static final long serialVersionUID = 206313173586591347L;

	private String categoryId;
	
	private String displayName;
	
	private Integer seqNum;
	
	
	/**
	 * @return the categoryId
	 */
	public final String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public final void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the seqNum
	 */
	public final Integer getSeqNum() {
		return seqNum;
	}
	/**
	 * @param seqNum the seqNum to set
	 */
	public final void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	/**
	 * @return the displayName
	 */
	public final String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public final void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public int compareTo(CategorySummaryDTO o) {
		
		return this.getSeqNum().compareTo(o.getSeqNum());
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategorySummaryDTO other = (CategorySummaryDTO) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		return true;
	}
	
	

}
