package com.zs.multidatasource.config;

import com.zaxxer.hikari.HikariDataSource;
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
@EnableJpaRepositories(entityManagerFactoryRef = "postgreSQLDBEntityManagerFactory",
        transactionManagerRef = "postgreSQLDBTransactionManager",
        basePackages = {"com.bank.db.bankdbexecuter.repositories.firstdb"})
public class MySQLDBConfig {

    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "postgreSQLDBDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource() {
        String dbType = env.getProperty("first.db.datasource.jdbc-url");
        if (!dbType.contains("jdbc:as400")) {
            return DataSourceBuilder.create().build();
        }
        //for DB2 AS400 datasource connection
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create().build();
        log.info("data-source {}", dataSource.getDataSourceProperties());
        dataSource.setConnectionTestQuery("values 1");
        return dataSource;
    }

    @Primary
    @Bean(name = "postgreSQLDBEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("postgreSQLDBDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.bank.db.bankdbexecuter.entity.firstdb")
                .persistenceUnit("db1").build();
    }

    @Primary
    @Bean(name = "postgreSQLDBTransactionManager")
    public PlatformTransactionManager postgreSQLDBTransactionManager(@Qualifier("postgreSQLDBEntityManagerFactory") EntityManagerFactory postgreSQLDBEntityManagerFactory) {
        return new JpaTransactionManager(postgreSQLDBEntityManagerFactory);
    }
}
