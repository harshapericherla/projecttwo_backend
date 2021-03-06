package com.niit.backend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.backend.model.BlogComment;
import com.niit.backend.model.BlogPost;
import com.niit.backend.model.Friend;
import com.niit.backend.model.Job;
import com.niit.backend.model.ProfilePicture;
import com.niit.backend.model.User;

@Configuration
@EnableTransactionManagement

public class DBConfig {
    @Bean
	public SessionFactory getSessionFactory(){
        LocalSessionFactoryBuilder lsf = new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		Class classes[] = new Class[]{User.class,ProfilePicture.class,Job.class,BlogPost.class,BlogComment.class,Friend.class};
		lsf.addAnnotatedClasses(classes);
		return lsf.buildSessionFactory();
	}
    @Bean
    public DataSource getDataSource(){
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
    	dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    	dataSource.setUsername("DTPROJECT2");
    	dataSource.setPassword("harsha123");
    	return dataSource;
    }
	
}
