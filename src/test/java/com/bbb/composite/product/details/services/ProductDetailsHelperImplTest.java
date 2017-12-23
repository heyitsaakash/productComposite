package com.bbb.composite.product.details.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbb.composite.product.details.dto.AttributeDTO;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.SkuCompositeDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.product.BrandDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.ColorVariantDTO;
import com.bbb.composite.product.details.dto.sku.SKUVariantValuesDTO;
import com.bbb.composite.product.details.dto.sku.SizeVariantDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class ProductDetailsHelperImplTest {
	
	@Autowired
	private ProductDetailsHelper productDetailsHelper;
	
	@Test
	public void getProductDetailsTest() {
		String productId = "12345";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("121");
		createProductData(productId, childSKUs);
		ProductCompositeDTO productCompositeDTO = productDetailsHelper.getProductDetails(createProductData(productId, childSKUs));
		Assert.assertEquals(productId, productCompositeDTO.getProductId());
		Assert.assertNotNull(productCompositeDTO.getProductBrand().getBrandId());
		Assert.assertEquals(productCompositeDTO.getAttributesList().size(), 2);
	}
	
	@Test
	public void getProductDetailsTestNegative() {
		String productId = "12345";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("121");
		createProductData(productId, childSKUs);
		ProductCompositeDTO productCompositeDTO = productDetailsHelper.getProductDetails(null);
		Assert.assertNull(productCompositeDTO);
	}
	
	@Test
	public void getConvertSkuDataTest() {
		List<SkuDTO> skuDtos = new ArrayList<SkuDTO>();
		SkuDTO skuDTO = new SkuDTO();
		skuDTO.setSkuId("121");
		skuDTO.setSiteId("BedBathUS");
		
		String color = "RED";
		SKUVariantValuesDTO skuVariantValues = new SKUVariantValuesDTO();
		ColorVariantDTO colorVariant = new ColorVariantDTO();
		colorVariant.setColorCode(color);
		skuVariantValues.setColorVariant(colorVariant);

		skuDTO.setSkuVariantValues(skuVariantValues);
		skuDTO.setSkuAttributes(getAttributeList());
		skuDtos.add(skuDTO);
		
		
		List<SkuCompositeDTO> skuCompositeDTOs = productDetailsHelper.convertSkuData(skuDtos);
		Assert.assertNotNull(skuCompositeDTOs);
		Assert.assertFalse(skuCompositeDTOs.isEmpty());
		Assert.assertEquals(skuCompositeDTOs.get(0).getVariantValues().getColor().getColorCode(), color);
		Assert.assertEquals(skuCompositeDTOs.get(0).getSkuAttributes().size(),2);
	}
	
	@Test
	public void getConvertSkuDataTestNegative() {
		List<SkuCompositeDTO> skuCompositeDTOs = productDetailsHelper.convertSkuData(new ArrayList<SkuDTO>());
		Assert.assertNull(skuCompositeDTOs);
	}
	
	@Test
	public void addVariantOptionDetailsToProductTest() {
		List<SkuDTO> skuDtos = new ArrayList<SkuDTO>();
		SkuDTO skuDTO = new SkuDTO();
		skuDTO.setSkuId("121");
		skuDTO.setSiteId("BedBathUS");
		
		String color = "RED";
		SKUVariantValuesDTO skuVariantValues = new SKUVariantValuesDTO();
		ColorVariantDTO colorVariant = new ColorVariantDTO();
		colorVariant.setColorCode(color);
		skuVariantValues.setColorVariant(colorVariant);
		
		String size = "L";
		SizeVariantDTO sizeVariantDTO = new SizeVariantDTO();
		sizeVariantDTO.setSizeCode(size);
		skuVariantValues.setSizeVariant(sizeVariantDTO);

		skuDTO.setSkuVariantValues(skuVariantValues);
		skuDTO.setSkuAttributes(getAttributeList());
		skuDtos.add(skuDTO);
		
		ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
		productDetailsHelper.addVariantOptionDetailsToProduct(skuDtos, productCompositeDTO);
		Assert.assertFalse(productCompositeDTO.getVariantOptions().getColors().isEmpty());
		Assert.assertFalse(productCompositeDTO.getVariantOptions().getSizes().isEmpty());
	}
	
	@Test
	public void addVariantOptionDetailsToProductTestNegative() {
		ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
		productDetailsHelper.addVariantOptionDetailsToProduct(null, productCompositeDTO);
		Assert.assertNull(productCompositeDTO.getVariantOptions());
	}
	
	@Test
	public void populateProductPriceDataTest() {
		ProductPriceDTO productPriceDTO = new ProductPriceDTO();
		productPriceDTO.setProductId("121156");
		productPriceDTO.setProductLowListPrice(new BigDecimal(2.4));
		productPriceDTO.setProductHighListPrice(new BigDecimal(55));
		productPriceDTO.setProductLowSalePrice(new BigDecimal(2));
		productPriceDTO.setProductHighSalePrice(new BigDecimal(52));
		productPriceDTO.setPriceRangeDescription("%L - %H");
		
		ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
		productDetailsHelper.populateProductPriceData(productPriceDTO, productCompositeDTO);
		Assert.assertNotNull(productCompositeDTO.getDisplayShipMsg());
	}
	
	@Test
	public void populateProductPriceDataNegativeTest() {
		ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
		productDetailsHelper.populateProductPriceData(null, productCompositeDTO);
		Assert.assertNull(productCompositeDTO.getPrice());
	}
	
	@Test
	public void populateSkuPriceDataNegativeTest() {
		ProductCompositeDTO productCompositeDTO = new ProductCompositeDTO();
		productDetailsHelper.populateSkuPriceData(null, productCompositeDTO);
		Assert.assertNull(productCompositeDTO.getSkus());
	}
	
	private ProductDTO createProductData(String productId, List<String> childSKUs) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(productId);
		productDTO.setChildSKUs(childSKUs);
		
		BrandDTO brandDto = new BrandDTO();
		brandDto.setBrandId("12432");
		brandDto.setBrandDesc("Ralph Lauren");
		brandDto.setBrandImage("/Image");
		brandDto.setBrandName("Ralph Lauren");
		productDTO.setBrandDTO(brandDto);
		
		productDTO.setProductAttributes(getAttributeList());
		return productDTO;
	}
	
	private List<AttributeDTO> getAttributeList() {
		List<AttributeDTO> attributes = new ArrayList<>();
		AttributeDTO attributeDto = new AttributeDTO();
		attributeDto.setAttributeName("Shipping Msg");
		attributeDto.setPlaceHolder("PDPP,PDPC");
		attributeDto.setStartDate(Instant.now().minus(2, ChronoUnit.DAYS));
		attributeDto.setEndDate(Instant.now().plus(2, ChronoUnit.DAYS));
		attributes.add(attributeDto);
		
		AttributeDTO attributeDto1 = new AttributeDTO();
		attributeDto1.setAttributeName("Single Day Delivery");
		attributeDto1.setStartDate(Instant.now().minus(2, ChronoUnit.DAYS));
		attributeDto1.setEndDate(Instant.now().plus(2, ChronoUnit.DAYS));
		attributes.add(attributeDto1);

		return attributes;
	}
	
}
