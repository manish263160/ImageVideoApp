package com.imagevideoapp.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.imagevideoapp.auth.ImageVideoAuthenticationFailureHandler;
import com.imagevideoapp.auth.ImageVideoAuthenticationProvider;
import com.imagevideoapp.auth.MySimpleUrlAuthenticationSuccessHandler;
 
@Configuration
// @EnableWebSecurity = @EnableWebMVCSecurity + Extra features
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	@Autowired
	ImageVideoAuthenticationProvider imageVideoAuthenticationProvider;
    
    @Autowired
    ImageVideoAuthenticationFailureHandler imageVideoAuthenticationFailureHandler;
    
    @Autowired
    MySimpleUrlAuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler;
    
 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 
        // Users in memory.
 
      /*  auth.inMemoryAuthentication().withUser("user1").password("12345").roles("USER");
        auth.inMemoryAuthentication().withUser("admin1").password("12345").roles("USER, ADMIN");
 
        // For User in database.
        auth.userDetailsService(myDBAauthenticationService);*/
    	auth.authenticationProvider(imageVideoAuthenticationProvider);
    }
 
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/static/**")
            .antMatchers("/IMAGES/**");
       
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
//        http.csrf().disable();
 
        
        // The pages does not require login
    	http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);   
        http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout","/user/insertUser","/restcontroller/**").permitAll();
        http.authorizeRequests().antMatchers("/user/userregistration").anonymous();
 
        // /userInfo page requires login as USER or ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/**").authenticated();
        http.authorizeRequests().antMatchers("/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
 
        // For ADMIN only.
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
 
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will throw.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
 
        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
//                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .successHandler(mySimpleUrlAuthenticationSuccessHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(imageVideoAuthenticationFailureHandler)
                // Config for Logout Page
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/loginpage?logout")
                .and()
                	.csrf();
// 
    }
    
}