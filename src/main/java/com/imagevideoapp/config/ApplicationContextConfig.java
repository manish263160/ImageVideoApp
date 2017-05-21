package com.imagevideoapp.config;
 
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.imagevideoapp.cron.Cronjob;
import com.imagevideoapp.utils.ApplicationProperties;
 
@Configuration
@ComponentScan("com.imagevideoapp.*")
@EnableTransactionManagement
// Load to Environment.
@EnableWebMvc 
@EnableAsync
@EnableScheduling
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig {
 
	private static final Logger logger = Logger.getLogger(ApplicationContextConfig.class);
  // The Environment class serves as the property holder
  // and stores all the properties loaded by the @PropertySource
  @Autowired
  private Environment env;
 
  @Bean
  public ResourceBundleMessageSource messageSource() {
      ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
      // Load property in src/resources/message.properties
      rb.setBasenames(new String[] { "messages" });
      rb.setUseCodeAsDefaultMessage(true);
      return rb;
  }
 
  @Bean(name = "viewResolver")
  public InternalResourceViewResolver getViewResolver() {
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      return viewResolver;
  }
 
  @Bean(name = "dataSource")
  public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
 
      // See: datasouce-cfg.properties
      dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
      dataSource.setUrl(env.getProperty("ds.url"));
      dataSource.setUsername(env.getProperty("ds.username"));
      dataSource.setPassword(env.getProperty("ds.password"));
 
      logger.info("## getDataSource: " + dataSource);
 
      return dataSource;
  }
 
  // Transaction Manager
  @Autowired
  @Bean(name = "transactionManager")
  public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
      DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
 
      return transactionManager;
  }
 
  @Bean(initMethod="init")
  public ApplicationProperties  getMyBean() {
   return new ApplicationProperties();
  }
  
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
      multipartResolver.setMaxUploadSize(200000000);
      return new CommonsMultipartResolver();
  }
  
  @Bean
  public VelocityEngine velocityEngine() throws VelocityException, IOException{
  	VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
  	Properties props = new Properties();
  	props.put("resource.loader", "class");
  	props.put("class.resource.loader.class", 
  			  "org.apache.velocity.runtime.resource.loader." + 
  			  "ClasspathResourceLoader");
  	factory.setVelocityProperties(props);
  	
  	return factory.createVelocityEngine();
  }
  
  @Bean
  public JavaMailSender getMailSender(){
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
       
      //Using gmail
      mailSender.setHost(env.getProperty("mail.host"));
      mailSender.setPort((Integer.parseInt(env.getProperty("mail.port"))));
      mailSender.setUsername(env.getProperty("mail.username"));
      mailSender.setPassword(env.getProperty("mail.password"));
       
      Properties javaMailProperties = new Properties();
      javaMailProperties.put("mail.smtp.starttls.enable", "true");
      javaMailProperties.put("mail.smtp.auth", "true");
      javaMailProperties.put("mail.transport.protocol", "smtp");
      javaMailProperties.put("mail.debug", "true");//Prints out everything on screen
       
      mailSender.setJavaMailProperties(javaMailProperties);
      return mailSender;
  }
  
 /* @Bean
  public Executor taskExecutor() {
      ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
      executor.initialize();
      return executor;
  }

  @Bean
  
  public Executor taskScheduler() {
      // set properties if required 
      return new ThreadPoolTaskScheduler();
  }   
*/

  @Bean
  public Cronjob Cronjob() {
	  Cronjob className = new Cronjob();
     // set properties
     return className;
  }
}