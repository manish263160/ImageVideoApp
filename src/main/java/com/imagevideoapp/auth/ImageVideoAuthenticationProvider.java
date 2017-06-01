package com.imagevideoapp.auth;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.imagevideoapp.Enums.STATUS;
import com.imagevideoapp.models.User;
import com.imagevideoapp.service.UserService;

/**
 * @author mukeshks
 * 
 * This Class is responsible for authentication and 
 * access control of users to cube root Admin module over http in extension of AuthenticationProvider interface of Spring web framework .   

 *
 */
@Component("imageVideoAuthenticationProvider")
public class ImageVideoAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(ImageVideoAuthenticationProvider.class);
	
	@Autowired UserService userService;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			logger.debug( "ImageVideoAuthenticationProvider.authenticate() authentication.getPrincipal(): " + authentication.getPrincipal());
			logger.debug( "ImageVideoAuthenticationProvider.authenticate() authentication.getCredentials(): " + authentication.getCredentials());
			
			String userName = authentication.getPrincipal().toString();
			String password = authentication.getCredentials().toString();
			
			User user = userService.userLogin(userName, password);

			if (user == null) {
				throw new UsernameNotFoundException(String.format(URLEncoder.encode("Invalid Email OR password", "UTF-8"), authentication.getPrincipal()));
			}
			
			if (STATUS.INACTIVE.ID == user.getStatus()) {
				throw new UsernameNotFoundException(String.format(URLEncoder.encode("You are not active", "UTF-8"), authentication.getPrincipal()));
			}
			
			if (STATUS.BLOCK.ID == user.getStatus()) {
				throw new UsernameNotFoundException(String.format(URLEncoder.encode("You are blocked. Please contact admin", "UTF-8"), authentication.getPrincipal()));
			}
			List<String> roles=null;
			if(user != null){
			 roles= userService.getUserRoles(user.getUserId());
			}
			List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
	        if(roles!= null)  {
	            for(String role: roles)  {
	                // ROLE_USER, ROLE_ADMIN,..
	                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
	                grantList.add(authority);
	            }
	        }  
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, grantList);
			return token;
		} catch (Exception e) {
			logger.error( "Error in ImageVideoAuthenticationProvider.authenticate()", e);
			throw new AuthenticationServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}