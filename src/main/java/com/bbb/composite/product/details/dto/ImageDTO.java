package com.bbb.composite.product.details.dto;

import java.io.Serializable;

/**
 * ImageDTO attributes specified here.
 *
 * @author psh111
 */
public class ImageDTO implements Serializable{

	private static final long serialVersionUID = 5511827445179617665L;
	private String thumbnailImage;// 
	private String swatchImage;//
	private String smallImage;//scene7//
	private String largeImage;//scene7
	private String regularImage;//scene7//
	private int zoomImageIndex;//
	private String zoomImage;//
	private String mediumImage;//scene7//
	private String collectionThumbnailImage;//scene7//
	private boolean anywhereZoomAvailable;// 
	private String basicImage; // actual scene7url//

	
	public String getThumbnailImage() {
		return thumbnailImage;
	}

	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}

	public String getSwatchImage() {
		return swatchImage;
	}

	public void setSwatchImage(String swatchImage) {
		this.swatchImage = swatchImage;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public String getRegularImage() {
		return regularImage;
	}

	public void setRegularImage(String regularImage) {
		this.regularImage = regularImage;
	}

	public int getZoomImageIndex() {
		return zoomImageIndex;
	}

	public void setZoomImageIndex(int zoomImageIndex) {
		this.zoomImageIndex = zoomImageIndex;
	}

	public String getZoomImage() {
		return zoomImage;
	}

	public void setZoomImage(String zoomImage) {
		this.zoomImage = zoomImage;
	}

	public String getMediumImage() {
		return mediumImage;
	}

	public void setMediumImage(String mediumImage) {
		this.mediumImage = mediumImage;
	}

	public String getCollectionThumbnailImage() {
		return collectionThumbnailImage;
	}

	public void setCollectionThumbnailImage(String collectionThumbnailImage) {
		this.collectionThumbnailImage = collectionThumbnailImage;
	}

	public boolean isAnywhereZoomAvailable() {
		return anywhereZoomAvailable;
	}

	public void setAnywhereZoomAvailable(boolean anywhereZoomAvailable) {
		this.anywhereZoomAvailable = anywhereZoomAvailable;
	}

	public String getBasicImage() {
		return basicImage;
	}

	public void setBasicImage(String basicImage) {
		this.basicImage = basicImage;
	}
	
	
}
