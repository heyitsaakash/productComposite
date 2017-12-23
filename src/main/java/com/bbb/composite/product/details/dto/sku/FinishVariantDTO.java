package com.bbb.composite.product.details.dto.sku;

import java.io.Serializable;

/**
 * FinishVariantDTO attributes specified here.
 *
 * @author psh111
 */
public class FinishVariantDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1241926432071050942L;
	private String displayName;
	private String colorCode;
	private String swatchImagePath;
	private String smallImagePath;
	private String thumbnailImagePath;
	private String largeImagePath;
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getSwatchImagePath() {
		return swatchImagePath;
	}
	public void setSwatchImagePath(String swatchImagePath) {
		this.swatchImagePath = swatchImagePath;
	}
	public String getSmallImagePath() {
		return smallImagePath;
	}
	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}
	public String getThumbnailImagePath() {
		return thumbnailImagePath;
	}
	public void setThumbnailImagePath(String thumbnailImagePath) {
		this.thumbnailImagePath = thumbnailImagePath;
	}
	public String getLargeImagePath() {
		return largeImagePath;
	}
	public void setLargeImagePath(String largeImagePath) {
		this.largeImagePath = largeImagePath;
	}
	@Override
	public String toString() {
		return "FinishVariantDTO [displayName=" + displayName + ", colorCode=" + colorCode + ", swatchImagePath="
				+ swatchImagePath + ", smallImagePath=" + smallImagePath + ", thumbnailImagePath=" + thumbnailImagePath
				+ ", largeImagePath=" + largeImagePath + "]";
	}
}
