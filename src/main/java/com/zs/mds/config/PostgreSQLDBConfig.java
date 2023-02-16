package com.zs.mds.config;

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
        basePackages = {"com.zs.mds.repository.postgresdb"})
public class PostgreSQLDBConfig {

    @Autowired
    private Environment env;

    @Bean(name = "postgreSQLDBDataSource")
    @ConfigurationProperties(prefix = "db.postgres.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgreSQLDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("postgreSQLDBDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.zs.mds.entity.postgresdb")
                .persistenceUnit("db2").build();
    }

    @Bean(name = "postgreSQLDBTransactionManager")
    public PlatformTransactionManager postgreSQLDBTransactionManager(@Qualifier("postgreSQLDBEntityManagerFactory") EntityManagerFactory postgreSQLDBEntityManagerFactory) {
        return new JpaTransactionManager(postgreSQLDBEntityManagerFactory);
    }
}
