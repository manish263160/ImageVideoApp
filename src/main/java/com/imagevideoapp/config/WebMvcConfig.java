package com.imagevideoapp.config;
 
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imagevideoapp.BreadCumb.interceptor.BreadCrumbInterceptor;
 
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
 
   private static final Charset UTF8 = Charset.forName("UTF-8");
 
   @Autowired BreadCrumbInterceptor breadCrumbInterceptor;
   //Interceptor of mvc
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(breadCrumbInterceptor).addPathPatterns("/**");  //.excludePathPatterns("/admin/**")
   }
   
   // Config UTF-8 Encoding.
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
       stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
       converters.add(stringConverter);
 
       // Add other converters ...
       final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
       final ObjectMapper objectMapper = new ObjectMapper();
       objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
       converter.setObjectMapper(objectMapper);
       converters.add(converter);
       super.configureMessageConverters(converters);
   }
 
   // Static Resource Config
   // equivalents for <mvc:resources/> tags
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(31556926);
//       registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
//       registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
   }
 
   // Equivalent for <mvc:default-servlet-handler/> tag
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
       configurer.enable();
   }
 
}