package com.rm;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.rm.dynamicsource.DataSourceContextHolder;
import com.rm.dynamicsource.DynamicDataSourceRouter;

@Configuration
public class DataSourceConfiguration {
	private final static String MASTER_DATASOURCE_KEY = "masterDataSource";
    private final static String SLAVE_DATASOURCE_KEY = "slaveDataSource";

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);
    
    //@Value("${spring.datasource.type}")
    //private Class<? extends DataSource> dataSourceType;

    @Primary
    @Bean(value = MASTER_DATASOURCE_KEY)
    @Qualifier(MASTER_DATASOURCE_KEY)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
    	LOG.info("create master datasource...");
        //return DataSourceBuilder.create().type(dataSourceType).build();
    	return DataSourceBuilder.create().build();
    }

    @Bean(value = SLAVE_DATASOURCE_KEY)
    @Qualifier(SLAVE_DATASOURCE_KEY)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
    	LOG.info("create slave datasource...");
        //return DataSourceBuilder.create().type(dataSourceType).build();
    	return DataSourceBuilder.create().build();
    }

    //@Bean(name = "routingDataSource")
    public AbstractRoutingDataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
    DynamicDataSourceRouter proxy = new DynamicDataSourceRouter();
    Map<Object, Object> targetDataSources = new HashMap<>(2);
    targetDataSources.put("masterDataSource", masterDataSource);
    targetDataSources.put("slaveDataSource", slaveDataSource);

        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}
