package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.bbb.composite.product.details.dto.sku.ColorVariantDTO;
import com.bbb.composite.product.details.dto.sku.SizeVariantDTO;

/**
 * VariantOptionsDTO attributes specified here.
 * 
 * @author psh111
 *
 */
public class VariantOptionsDTO implements Serializable{
	
	private static final long serialVersionUID = 332254005038200731L;
	
	private Set<SizeVariantDTO> sizes;
	private Set<ColorVariantDTO> colors;
	public Set<SizeVariantDTO> getSizes() {
		return sizes;
	}
	
	/**
	 * Method to create Set of Sizes
	 * 
	 * @param sizes sizes to be passed
	 */
	public void addSizes(SizeVariantDTO sizes) {
		if (this.sizes == null) {
			this.sizes = new HashSet<>();
		}
		this.sizes.add(sizes);
	}
	public Set<ColorVariantDTO> getColors() {
		return colors;
	}
	
	/**
	 * method to create Set of Colors.
	 * 
	 * @param colors colors to be passed
	 */
	public void addColors(ColorVariantDTO colors) {
		if (this.colors == null) {
			this.colors = new HashSet<>();
		}
		this.colors.add(colors);
	}
	
	
	
}