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
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(entityManagerFactoryRef = "mySQLDBEntityManagerFactory",
        transactionManagerRef = "mySQLDBTransactionManager",
        basePackages = {"com.zs.mds.repository.mysqldb"})
public class MySQLDBConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "mySQLDBDataSource")
    @ConfigurationProperties(prefix = "db.mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mySQLDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("mySQLDBDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.zs.mds.entity.mysqldb")
                .persistenceUnit("db1").build();
    }

    @Primary
    @Bean(name = "mySQLDBTransactionManager")
    public PlatformTransactionManager mySQLDBTransactionManager(@Qualifier("mySQLDBEntityManagerFactory") EntityManagerFactory mySQLDBEntityManagerFactory) {
        return new JpaTransactionManager(mySQLDBEntityManagerFactory);
    }
}
