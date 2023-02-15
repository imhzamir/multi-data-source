package com.zs.multidatasource.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "postgreSQLDBEntityManagerFactory",
        transactionManagerRef = "postgreSQLDBTransactionManager",
        basePackages = {"com.zs.multidatasource.repository.postgres"})
public class PostgreSQLDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "postgreSQLDBDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgreSQLDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("postgreSQLDBDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.zs.multidatasource.entity.postgres")
                .persistenceUnit("db1").build();
    }

    @Bean(name = "postgreSQLDBTransactionManager")
    public PlatformTransactionManager postgreSQLDBTransactionManager(@Qualifier("postgreSQLDBEntityManagerFactory") EntityManagerFactory postgreSQLDBEntityManagerFactory) {
        return new JpaTransactionManager(postgreSQLDBEntityManagerFactory);
    }
}
