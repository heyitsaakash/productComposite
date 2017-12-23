/**
 * 
 */
package com.bbb.composite.product.details.dto;

import java.io.Serializable;

/**
 * The Class SeoDTO.
 *
 * @author nbisht
 */
public class SeoDTO implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9081388050471396044L;

	/** The tag id. */
	private String tagId;
	
	/** The display name. */
	private String displayName;
	
	/** The title. */
	private String title;
	
	/** The description. */
	private String description;
	
	/** The keywords. */
	private String keywords;
	
	/** The contentkeys. */
	private String contentkeys;
	
	/** The footer content. */
	private String footerContent;
	

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the keywords.
	 *
	 * @return the keywords
	 */
	public final String getKeywords() {
		return keywords;
	}
	
	/**
	 * Sets the keywords.
	 *
	 * @param keywords the keywords to set
	 */
	public final void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * Gets the tag id.
	 *
	 * @return the tag id
	 */
	public String getTagId() {
		return tagId;
	}
	
	/**
	 * Sets the tag id.
	 *
	 * @param tagId the new tag id
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Sets the display name.
	 *
	 * @param displayName the new display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * Gets the contentkeys.
	 *
	 * @return the contentkeys
	 */
	public String getContentkeys() {
		return contentkeys;
	}
	
	/**
	 * Sets the contentkeys.
	 *
	 * @param contentkeys the new contentkeys
	 */
	public void setContentkeys(String contentkeys) {
		this.contentkeys = contentkeys;
	}
	
	/**
	 * Gets the footer content.
	 *
	 * @return the footer content
	 */
	public String getFooterContent() {
		return footerContent;
	}
	
	/**
	 * Sets the footer content.
	 *
	 * @param footerContent the new footer content
	 */
	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}
	
}
