package com.bbb.composite.product.details.services.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbb.composite.product.details.dto.CategoryDTO;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class CategoryIntegrationServiceImplTest {

	@Autowired
	CategoryIntegrationServiceImpl categoryIntegrationService;


	@SuppressWarnings("rawtypes")
	@Mock
	private Appender mockAppender;
	@Captor
	private ArgumentCaptor<LoggingEvent> captorLoggingEvent;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@SuppressWarnings({"unchecked" , "unused"})
	@Test
	public void testGetCategoriesFallBack() {
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childCategories = new ArrayList<>();
		childCategories.add("AA");
		childCategories.add("ACD");
		childCategories.add("AC");
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.addAppender(mockAppender);
		root.setLevel(Level.ERROR);
		try{
		List<CategoryDTO> categoryDTOList = categoryIntegrationService.getCategories(channel, siteId, childCategories);
		}
		catch (Exception e) {
			Assert.assertEquals("Hystrix fallback called. Failed to get Categories data for categorydIds[AA, ACD, AC], siteId siteId_2, channel web", e.getMessage());
		}
	}

}
