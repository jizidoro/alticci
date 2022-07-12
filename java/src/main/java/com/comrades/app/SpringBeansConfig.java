/* 
 * Este arquivo pertence a Petrobras e nao pode ser utilizado fora desta empresa 
 * sem previa autorizacao.
 * ----------------------------------
 * Esta classe segue o padrao PE-2T0-00250
 */
package com.comrades.app;


import com.comrades.app.core.bases.MessageBundle;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Profile("!tests")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.comrades.app.persistence.repositories")
@ComponentScan(basePackages = "com.comrades.app")
public class SpringBeansConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources
                = new ClassPathResource[]{new ClassPathResource("server/app.properties"),
                    new ClassPathResource("server/infra.properties")};
        pspc.setLocations(resources);
        pspc.setLocalOverride(true);
        pspc.setIgnoreUnresolvablePlaceholders(false);
        return pspc;
    }

    @Bean
    public DataSource dataSource() throws NamingException {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/comrades?schema=public");
        config.setUsername("postgres");
        config.setPassword("qwe123");
        config.setAutoCommit(true);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(25);
        config.setKeepaliveTime(60000);
        config.setIdleTimeout(120000);
        config.setLeakDetectionThreshold(150000);
        config.setMaxLifetime(180000);
        config.setConnectionTimeout(3000);
        config.setValidationTimeout(2500);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);

        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws NamingException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.comrades.app.domain.models");
        factory.setDataSource(dataSource());

        Map<String, Object> jpaProperties = new HashMap<>();
        // jpaProperties.put("hibernate.hbm2ddl.auto", "validate");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", true);
        factory.setJpaPropertyMap(jpaProperties);

        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource hikariDataSource) {
        return new JdbcTemplate(hikariDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws NamingException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        txManager.setDataSource(dataSource());
        return txManager;
    }

    @Bean
    public MessageSource messageSource() {
        String basename = "classpath:i18n/messages";
        MessageBundle messageSource = new MessageBundle(basename);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validatorFactory() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }

}
