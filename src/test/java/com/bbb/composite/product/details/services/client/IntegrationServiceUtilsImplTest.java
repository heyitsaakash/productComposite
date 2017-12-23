package com.bbb.composite.product.details.services.client;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.bbb.composite.product.details.dto.sku.SKUVariantValuesDTO;
import com.bbb.core.dto.BaseServiceDTO;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class IntegrationServiceUtilsImplTest {
	
	@InjectMocks
	IntegrationServiceUtilsImpl integrationServiceUtilsImpl;
	@SuppressWarnings("rawtypes")
	@Mock
	private Appender mockAppender;
	@Captor
	private ArgumentCaptor<LoggingEvent> captorLoggingEvent;
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	/**
	 * Test case to validate the logging captured for 3xx error, 
	 * when responseEntity, productId, siteId and Channel value is passed
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void testExtractResponseBody3xxError(){
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(mockAppender);
		root.setLevel(Level.WARN);
		ResponseEntity<BaseServiceDTO<SKUVariantValuesDTO>> responseEntity = new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
		integrationServiceUtilsImpl.extractResponseBody(responseEntity, "product_id1","siteid_1",  "web");
	    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
	    LoggingEvent loggingEvent = captorLoggingEvent.getAllValues().get(0);
	    Assert.assertEquals("Received a {} as status code for siteId:{}, productId:{}, channel:{}. Was not expecting a redirection.", loggingEvent.getMessage());
	}
	
	/**
	 * Test case to validate the logging captured for 5xx error, 
	 * when responseEntity, productId, siteId and Channel value is passed
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testExtractResponseBody5xxError(){
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(mockAppender);
		root.setLevel(Level.WARN);
		ResponseEntity<BaseServiceDTO<SKUVariantValuesDTO>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		integrationServiceUtilsImpl.extractResponseBody(responseEntity, "product_id1","siteid_1",  "web");
	    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
	    LoggingEvent loggingEvent = captorLoggingEvent.getAllValues().get(0);
	    Assert.assertEquals("Received a {} as status code for siteId:{}, productId:{}, channel:{}", loggingEvent.getMessage());
	}

	/**
	 * Test case to validate the logging captured for 3xx error, 
	 * when responseEntity, childSkus, siteId and Channel value is passed
	 */
	
	@SuppressWarnings("unchecked")
	@Test
	public void test2ExtractResponseBody3xxError(){
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(mockAppender);
		root.setLevel(Level.WARN);
		ResponseEntity<BaseServiceDTO<SKUVariantValuesDTO>> responseEntity = new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
		integrationServiceUtilsImpl.extractResponseBody(responseEntity, Arrays.asList("212","123123"),"siteid_1",  "web");
	    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
	    LoggingEvent loggingEvent = captorLoggingEvent.getAllValues().get(0);
	    Assert.assertEquals("Received a {} as status code for siteId:{}, skus:{}, channel:{}. Was not expecting a redirection.", loggingEvent.getMessage());
	}
	
	/**
	 * Test case to validate the logging captured for 5xx error, 
	 * when responseEntity, childSkus, siteId and Channel value is passed
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test2ExtractResponseBody5xxError(){
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(mockAppender);
		root.setLevel(Level.WARN);
		ResponseEntity<BaseServiceDTO<SKUVariantValuesDTO>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		integrationServiceUtilsImpl.extractResponseBody(responseEntity, Arrays.asList("212","123123"),"siteid_1",  "web");
	    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
	    LoggingEvent loggingEvent = captorLoggingEvent.getAllValues().get(0);
	    Assert.assertEquals("Received a {} as status code for siteId:{}, skus:{}, channel:{}", loggingEvent.getMessage());
	}
}
