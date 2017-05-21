package com.imagevideoapp.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for handling login failure
 * 
 * @author manishm
 *
 */
@Component("imageVideoAuthenticationFailureHandler")
public class ImageVideoAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = Logger.getLogger(ImageVideoAuthenticationFailureHandler.class);
	

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		handle(request, response, exception);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
		logger.info("handle(): Start" + request.getParameter("targetUrl") + "," + request.getHeader("referer"));
		String referer = request.getHeader("referer");
		if (!StringUtils.isBlank(referer)) {
			referer = referer.replaceAll("[&?]error.*?(?=&|\\?|$)", "");
			if (referer.contains("?"))
				redirectStrategy.sendRedirect(request, response, referer + "&error=" + exception.getMessage());
			else 
				redirectStrategy.sendRedirect(request, response, referer + "?error=" + exception.getMessage());
		} else {
			redirectStrategy.sendRedirect(request, response, "/home.htm" + "?error=" + exception.getMessage());
		}
	}
}
