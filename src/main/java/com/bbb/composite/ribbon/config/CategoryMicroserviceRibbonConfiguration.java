package com.bbb.composite.ribbon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bbb.core.ribbon.BaseRibbonConfiguration;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

/**
 * Load balancer class for Category Micro Service
 * 
 * @author psh111
 *
 */
@Configuration
public class CategoryMicroserviceRibbonConfiguration extends BaseRibbonConfiguration {

	private String categoryMicroservice = "category-microservice";

	/**
	 * Bean creation for IClientConfig
	 * 
	 * 
	 * @return config obj of IClientConfig
	 */
    @Bean
    public IClientConfig ribbonClientConfig() {
        DefaultClientConfigImpl config = new DefaultClientConfigImpl();
        config.loadProperties(this.categoryMicroservice);
        return config;
    }

    @Bean
    ServerList<Server> ribbonServerList(IClientConfig config) {
        ConfigurationBasedServerList serverList = new ConfigurationBasedServerList();
        serverList.initWithNiwsConfig(config);
        return serverList;
    }
    
}
