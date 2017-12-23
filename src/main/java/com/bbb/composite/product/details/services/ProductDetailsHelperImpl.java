package com.bbb.composite.product.details.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bbb.composite.product.details.dto.AttributeDTO;
import com.bbb.composite.product.details.dto.BrandCompositeDTO;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.ProductPriceCompositeDTO;
import com.bbb.composite.product.details.dto.SKUCompositeVariantValuesDTO;
import com.bbb.composite.product.details.dto.SkuCompositeDTO;
import com.bbb.composite.product.details.dto.SkuPriceCompositeDTO;
import com.bbb.composite.product.details.dto.VariantOptionsDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.product.details.dto.product.BrandDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.SKUVariantValuesDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;

/**
 * ProductDetailsHelper Implementation class
 * 
 * @author psh111
 *
 */
@Component("com.bbb.composite.product.details.services.ProductDetailsHelperImpl")
public class ProductDetailsHelperImpl implements ProductDetailsHelper {
	
	@Value("${composite.productdetails.higherShipThreshhold}")
	private String higherShipThreshholdStr;
	
	@Value("${composite.productdetails.freeshippingCollectionsProduct}")
	private String freeshippingCollectionsProduct;
	
	@Value("${composite.productdetails.freeshippingProduct}")
	private String freeshippingProduct;
	
	@Value("${composite.productdetails.ShipMsgDisplayFlag}")
	private String shipMsgDisplayFlag;
	
	@Override
	public ProductCompositeDTO getProductDetails(ProductDTO productDTO) {
		if(productDTO != null) {
			ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
			productCompositeDTO.setProductId(productDTO.getProductId());
			productCompositeDTO.setName(productDTO.getName());
			productCompositeDTO.setShortDescription(productDTO.getDescription());
			productCompositeDTO.setLongDescription(productDTO.getLongDescription());
			productCompositeDTO.setProductImages(productDTO.getProductImage());
			productCompositeDTO.setProductTabs(productDTO.getProductTabs());
			productCompositeDTO.setProductMedia(productDTO.getProductMedias());
			productCompositeDTO.setAttributesList(createMapFromAttributes(productDTO.getProductAttributes()));
			productCompositeDTO.setSeoURL(productDTO.getSeoUrl());
			productCompositeDTO.setShopGuideId(productDTO.getShopGuideId());
			productCompositeDTO.setIntlRestricted(productDTO.getIntlRestricted());
			productCompositeDTO.setReviews(productDTO.getBazaarVoiceProductDTO());
			productCompositeDTO.setChildSKUs(productDTO.getChildSKUs());
			productCompositeDTO.setSiteId(productDTO.getSiteId());
			productCompositeDTO.setAltImagesList(productDTO.getAltImagesList());
			productCompositeDTO.setCategoryList(productDTO.getRelatedCategories());
			populateBrand(productDTO.getBrandDTO(), productCompositeDTO);
			return productCompositeDTO;
		} else {
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("squid:CommentedOutCodeLine")
	public List<SkuCompositeDTO> convertSkuData(List<SkuDTO> skuDTOList) {
		List<SkuCompositeDTO> skuCompositeDTOList = null;
		if(CollectionUtils.isNotEmpty(skuDTOList)) {
			skuCompositeDTOList = new ArrayList<>();
			for (SkuDTO skuDTO : skuDTOList) {
				SkuCompositeDTO skuCompositeDTO = new SkuCompositeDTO();
				skuCompositeDTO.setId(skuDTO.getSkuId());
				skuCompositeDTO.setProductId(skuDTO.getProductId());
				skuCompositeDTO.setShortDescription(skuDTO.getDescription());
				skuCompositeDTO.setLongDescription(skuDTO.getLongDescription());
				skuCompositeDTO.setActiveFlag(skuDTO.getIsSkuActive());
				skuCompositeDTO.setIntlRestricted(skuDTO.getIsIntlRestricted());
				skuCompositeDTO.setShippingRestricted(skuDTO.getIsShippingRestricted());
				//skuCompositeDTO.setDisplayShipMsg(skuDTO.getisdi);
				skuCompositeDTO.setSkuImages(skuDTO.getSkuImages());
				skuCompositeDTO.setVariantValues(convertVariantDTO(skuDTO.getSkuVariantValues()));
				skuCompositeDTO.setSkuAttributes(createMapFromAttributes(skuDTO.getSkuAttributes()));
				skuCompositeDTOList.add(skuCompositeDTO);
			}
		}
		return skuCompositeDTOList;
	}
	
	@Override
	public void addVariantOptionDetailsToProduct(List<SkuDTO> skuDTOList, ProductCompositeDTO productCompositeDTO) {
		if(CollectionUtils.isNotEmpty(skuDTOList)) {
			for (SkuDTO skuDTO : skuDTOList) {
				SKUVariantValuesDTO skuVariantValues = skuDTO.getSkuVariantValues();
				if (null != skuVariantValues) {
					setSKUVariantDTO(productCompositeDTO,skuVariantValues);
				}
			}
		}
	}

	/**
	 * Helper method to set the SKUVariantDTO obj
	 * 
	 * @param productCompositeDTO
	 * @param skuVariantValues
	 */
	private void setSKUVariantDTO (ProductCompositeDTO productCompositeDTO,SKUVariantValuesDTO skuVariantValues){
					VariantOptionsDTO variantOptionsDTO = productCompositeDTO.getVariantOptions();
					if (null == variantOptionsDTO) {
						variantOptionsDTO = new VariantOptionsDTO();
						productCompositeDTO.setVariantOptions(variantOptionsDTO);
					}
					if (null != skuVariantValues.getColorVariant()) {
						variantOptionsDTO.addColors(skuVariantValues.getColorVariant());
					}
					if (null != skuVariantValues.getSizeVariant()) {
						variantOptionsDTO.addSizes(skuVariantValues.getSizeVariant());
					}
				}
	@Override
	public void populateProductPriceData(ProductPriceDTO productPriceDTO, ProductCompositeDTO productCompositeDto) {
		if(null != productPriceDTO) {
			ProductPriceCompositeDTO producCompositetPriceDTO = new ProductPriceCompositeDTO();
			producCompositetPriceDTO.setPriceRangeDesc(productPriceDTO.getPriceRangeDescription());
			producCompositetPriceDTO.setLabelCode(productPriceDTO.getProductLabelCode());
			producCompositetPriceDTO.setInCartFlag(productPriceDTO.getProductInCartFlag());
			producCompositetPriceDTO.setInCartPrice(productPriceDTO.getProductInCartPrice());
			productCompositeDto.setPrice(producCompositetPriceDTO);
			populatePriceRangeDesc(producCompositetPriceDTO, productPriceDTO);
			updateShippingMsg(productPriceDTO, productCompositeDto);
		}
	}
	
	@Override
	public void populateSkuPriceData(List<SkuPriceDTO> skuPriceEntities, ProductCompositeDTO productCompositeDto) {
		List<SkuCompositeDTO> skuDtos = productCompositeDto.getSkus();
		if(CollectionUtils.isNotEmpty(skuPriceEntities) && CollectionUtils.isNotEmpty(skuDtos)) {
			for (SkuCompositeDTO skuDto : skuDtos) {
				SkuPriceDTO skuPriceDto = getSkuPriceDtoOnId(skuPriceEntities, skuDto.getId());
				if (null != skuPriceDto) {
					SkuPriceCompositeDTO skuPriceDTO = new SkuPriceCompositeDTO();
					skuPriceDTO.setListPrice(skuPriceDto.getSkuListPrice());
					skuPriceDTO.setSalePrice(skuPriceDto.getSkuSalePrice());
					skuPriceDTO.setLabelCode(skuPriceDto.getSkuLabelCode());
					skuPriceDTO.setInCartFlag(skuPriceDto.getSkuInCartFlag());
					skuPriceDTO.setInCartPrice(skuPriceDto.getSkuInCartPrice());
					skuDto.setPrice(skuPriceDTO);
				}
			}
		}
	}
	
	private void populateBrand(BrandDTO brandDto, ProductCompositeDTO productCompositeDTO) {
		if (null != brandDto) {
			BrandCompositeDTO brandCompositeDTO = new BrandCompositeDTO();
			brandCompositeDTO.setBrandId(brandDto.getBrandId());
			brandCompositeDTO.setBrandDescription(brandDto.getBrandDesc());
			brandCompositeDTO.setBrandImage(brandDto.getBrandImage());
			brandCompositeDTO.setBrandName(brandDto.getBrandName());
			brandCompositeDTO.setDisplayFlag(brandDto.getDisplayFlag());
			productCompositeDTO.setProductBrand(brandCompositeDTO);
		}
	}
	
	private SKUCompositeVariantValuesDTO convertVariantDTO(SKUVariantValuesDTO skuVariantValuesDTO) {
		SKUCompositeVariantValuesDTO skuCompositeVariantValuesDTO = new SKUCompositeVariantValuesDTO();
		if (null != skuVariantValuesDTO) {
			skuCompositeVariantValuesDTO.setColor(skuVariantValuesDTO.getColorVariant());
			skuCompositeVariantValuesDTO.setFinish(skuVariantValuesDTO.getFinishVariant());
			skuCompositeVariantValuesDTO.setSize(skuVariantValuesDTO.getSizeVariant());
		}
		return skuCompositeVariantValuesDTO;
	}
	
	@SuppressWarnings("squid:S3776")
	private void populatePriceRangeDesc(ProductPriceCompositeDTO producCompositetPriceDTO, ProductPriceDTO productPriceDto) {
		double lowListPrice = 0.0d;
		double highListPrice = 0.0d;
		producCompositetPriceDTO.setPriceRangeDesc(modifyPriceRangeDesc(productPriceDto));
		if (isProductOnSale(productPriceDto)) {
			String salePriceRangeDesc = productPriceDto.getPriceRangeDescription();
			if (StringUtils.isNotEmpty(salePriceRangeDesc) && salePriceRangeDesc.contains("%L")) {
				String lowSalePriceString = String.valueOf(productPriceDto.getProductLowSalePrice());
				double lowSalePrice = 0.0d;
				if (StringUtils.isNotEmpty(lowSalePriceString)) {
					lowSalePrice = Double.parseDouble(lowSalePriceString);
				}
				if (lowSalePrice > 0d) {
					salePriceRangeDesc = salePriceRangeDesc.replace("%L", Double.toString(lowSalePrice));
				} else {
					salePriceRangeDesc = salePriceRangeDesc.replace("%L", Double.toString(lowListPrice));
				}
			}
			
			if (StringUtils.isNotEmpty(salePriceRangeDesc) && salePriceRangeDesc.contains("%H")) {
				String highSalePriceString = String.valueOf(productPriceDto.getProductHighSalePrice());
				double highSalePrice = 0.0d;
				if (StringUtils.isNotEmpty(highSalePriceString)) {
					highSalePrice = Double.parseDouble(highSalePriceString);
				}
				if (highSalePrice > 0d) {
					salePriceRangeDesc = salePriceRangeDesc.replace("%H", Double.toString(highSalePrice));
				} else {
					salePriceRangeDesc = salePriceRangeDesc.replace("%H", Double.toString(highListPrice));
				}
			}
			producCompositetPriceDTO.setSalePriceRangeDesc(salePriceRangeDesc);
		}
	}
	
	/**
	 * Helper method to replace the %L and %H characters
	 * 
	 * @param productPriceDto product price dto object
	 * @return value as String
	 */
	private String modifyPriceRangeDesc(ProductPriceDTO productPriceDto){
		String priceRangeDesc = productPriceDto.getPriceRangeDescription();
		double lowListPrice = 0.0d;
		double highListPrice = 0.0d;
		if (StringUtils.isNotEmpty(priceRangeDesc) && priceRangeDesc.contains("%L")) {
			String lowListPriceString = String.valueOf(productPriceDto.getProductLowListPrice());
			if (StringUtils.isNotEmpty(lowListPriceString)) {
				lowListPrice = Double.parseDouble(lowListPriceString);
			}
			priceRangeDesc = priceRangeDesc.replace("%L", Double.toString(lowListPrice));
		} 
		if (StringUtils.isNotEmpty(priceRangeDesc) && priceRangeDesc.contains("%H")) {
			String highListPriceString = String.valueOf(productPriceDto.getProductHighListPrice());
			if (StringUtils.isNotEmpty(highListPriceString)) {
				highListPrice = Double.parseDouble(highListPriceString);
			}
			priceRangeDesc =priceRangeDesc.replace("%H", Double.toString(highListPrice));
		}
		return priceRangeDesc;
	}
	
	private boolean isProductOnSale(ProductPriceDTO productPriceDto) {
		return (null != productPriceDto.getProductLowSalePrice() && productPriceDto.getProductLowSalePrice().compareTo(new BigDecimal(0)) > 0 ) ||
				(null != productPriceDto.getProductHighSalePrice() && productPriceDto.getProductHighSalePrice().compareTo(new BigDecimal(0)) > 0 );
	}

	private SkuPriceDTO getSkuPriceDtoOnId(List<SkuPriceDTO> skuPriceDtos, String skuId) {
		SkuPriceDTO skuPriceDtoPresent = null;
		for (SkuPriceDTO skuPriceDto: skuPriceDtos) {
			if (skuId.equalsIgnoreCase(skuPriceDto.getSkuId())) {
				skuPriceDtoPresent = skuPriceDto;
				break;
			}
		}
		return skuPriceDtoPresent;
	}
	
	/**
	 * Method to update the shipping Msg based on the prices
	 * 
	 * @param productPriceDto product price DTO obj
	 * @param productComposietDto product Composite DTO obj
	 */
	private void updateShippingMsg(ProductPriceDTO productPriceDto, ProductCompositeDTO productComposietDto) {
		if (Boolean.valueOf(shipMsgDisplayFlag)) {
			BigDecimal lowSalePrice = productPriceDto.getProductLowSalePrice();
			BigDecimal highSalePrice = productPriceDto.getProductHighSalePrice();
			
			BigDecimal lowPrice; 
			BigDecimal highPrice;
			
			if (null != lowSalePrice && lowSalePrice.compareTo(new BigDecimal(0)) > 0) {
				lowPrice = lowSalePrice;
				highPrice = highSalePrice;
			} else {
				lowPrice = productPriceDto.getProductLowListPrice();
				highPrice = productPriceDto.getProductHighListPrice();
			}
			
			double higherShipThreshhold; 
			if (null != higherShipThreshholdStr) {
				higherShipThreshhold = Double.parseDouble(higherShipThreshholdStr);
			} else {
				return;
			}
			setDisplayShipMsg(lowPrice, highPrice,higherShipThreshhold, productComposietDto);
		}
	}
			
	private void setDisplayShipMsg(BigDecimal lowPrice,BigDecimal highPrice,double higherShipThreshhold, ProductCompositeDTO productComposietDto){
			if (null != lowPrice && lowPrice.doubleValue() < higherShipThreshhold && null != highPrice && highPrice.doubleValue() > higherShipThreshhold) {
				productComposietDto.setDisplayShipMsg(freeshippingCollectionsProduct);
			} else if (null != lowPrice && lowPrice.doubleValue() > higherShipThreshhold) {
				productComposietDto.setDisplayShipMsg(freeshippingProduct);
			}
		}
	
	/**
	 * This method will create map with key as placeHolder and value as list of AttributeDTO
	 * @param attributeDTOList attribute dto list
	 * @return attributeMap attribute Map
	 */
	@SuppressWarnings("all")
	private Map<String, List<AttributeDTO>> createMapFromAttributes(List<AttributeDTO> attributeDTOList) {
		Map<String, List<AttributeDTO>> attributeMap = null;
		if (CollectionUtils.isNotEmpty(attributeDTOList)) {
			attributeMap = new HashMap<>();
			for (AttributeDTO attriButeDTO : attributeDTOList) {
				if(null != attriButeDTO.getPlaceHolder()) {
					if (attriButeDTO.getPlaceHolder().contains(",")) {
						String str[] = attriButeDTO.getPlaceHolder().split(",");
						for (String placeHolder : str) {
							populateMapForPlaceHolder(attriButeDTO, attributeMap, placeHolder);
						}
					} else {
						populateMapForPlaceHolder(attriButeDTO, attributeMap, attriButeDTO.getPlaceHolder());
					}
				}
			}
		}
		return attributeMap;
	}
	
	/**
	 * This method will populate a map with key as placeholder and value as list of attributes from list of attributes
	 * @param attriButeDTO attribute dto
	 * @param attributeMap attribute map
	 * @param placeHolder place holder
	 * @return attributeMap attribute map
	 */
	private Map<String,List<AttributeDTO>> populateMapForPlaceHolder(AttributeDTO attriButeDTO,Map<String
																		, List<AttributeDTO>> attributeMap,String placeHolder){
		if (attributeMap.get(placeHolder) == null) {
			List<AttributeDTO> attributeList = new ArrayList<>();
			attributeList.add(attriButeDTO);
			attributeMap.put(placeHolder, attributeList);
		} else {
			List<AttributeDTO> attributeDTO = attributeMap.get(placeHolder);
			attributeDTO.add(attriButeDTO);
			attributeMap.put(placeHolder, attributeDTO);
		}
		return attributeMap;
	}
}
